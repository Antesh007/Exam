package com.fulfilment.application.monolith.warehouses.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ReplaceWarehouseUseCaseTest {

    @Mock
    WarehouseStore warehouseStore;

    @Mock
    LocationResolver locationResolver;

    private ReplaceWarehouseUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new ReplaceWarehouseUseCase(
                warehouseStore,
                locationResolver);
    }

    private Warehouse existingWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU001";
        warehouse.location = "IND";
        warehouse.capacity = 100;
        warehouse.stock = 50;
        warehouse.createdAt = LocalDateTime.now();
        return warehouse;
    }

    private Warehouse replacementWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU001";
        warehouse.location = "IND";
        warehouse.capacity = 120;
        warehouse.stock = 50;
        return warehouse;
    }

    @Test
    void shouldReplaceWarehouseSuccessfully() {

        Warehouse existing = existingWarehouse();
        Warehouse replacement = replacementWarehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(existing);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(new Location("IND", 5, 1000));

        when(warehouseStore.getAll())
                .thenReturn(new ArrayList<>());

        useCase.replace(replacement);

        verify(warehouseStore).update(replacement);
    }

    @Test
    void shouldThrowWhenWarehouseDoesNotExist() {

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.replace(replacementWarehouse()));

        assertEquals(404, ex.getResponse().getStatus());

        verify(warehouseStore, never()).update(any());
    }

    @Test
    void shouldThrowWhenStockDoesNotMatch() {

        Warehouse existing = existingWarehouse();
        Warehouse replacement = replacementWarehouse();
        replacement.stock = 80;

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(existing);

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.replace(replacement));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenCapacityIsLessThanExistingStock() {

        Warehouse existing = existingWarehouse();
        Warehouse replacement = replacementWarehouse();
        replacement.capacity = 40;

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(existing);

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.replace(replacement));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenLocationDoesNotExist() {

        Warehouse existing = existingWarehouse();
        Warehouse replacement = replacementWarehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(existing);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(null);

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.replace(replacement));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenLocationCapacityExceeded() {

        Warehouse existing = existingWarehouse();
        Warehouse replacement = replacementWarehouse();
        replacement.capacity = 700;

        Warehouse another = new Warehouse();
        another.businessUnitCode = "BU002";
        another.location = "IND";
        another.capacity = 400;
        another.stock = 100;

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(existing);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(new Location("IND", 5, 1000));

        when(warehouseStore.getAll())
                .thenReturn(List.of(another));

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.replace(replacement));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldPreserveCreatedAtTimestamp() {

        Warehouse existing = existingWarehouse();
        Warehouse replacement = replacementWarehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(existing);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(new Location("IND", 5, 1000));

        when(warehouseStore.getAll())
                .thenReturn(new ArrayList<>());

        useCase.replace(replacement);

        verify(warehouseStore).update(replacement);
        org.junit.jupiter.api.Assertions.assertEquals(
                existing.createdAt,
                replacement.createdAt);
    }
}