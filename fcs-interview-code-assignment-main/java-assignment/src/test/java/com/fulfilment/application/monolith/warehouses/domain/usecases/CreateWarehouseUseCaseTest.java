package com.fulfilment.application.monolith.warehouses.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CreateWarehouseUseCaseTest {

    @Mock
    WarehouseStore warehouseStore;

    @Mock
    LocationResolver locationResolver;

    private CreateWarehouseUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new CreateWarehouseUseCase(
                warehouseStore,
                locationResolver);
    }

    private Warehouse warehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU001";
        warehouse.location = "IND";
        warehouse.capacity = 100;
        warehouse.stock = 20;
        return warehouse;
    }

    private Location location() {
        return new Location("IND", 5, 1000);
    }

    @Test
    void shouldCreateWarehouseSuccessfully() {

        Warehouse warehouse = warehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(location());

        when(warehouseStore.getAll())
                .thenReturn(new ArrayList<>());

        useCase.create(warehouse);

        verify(warehouseStore).create(warehouse);
    }

    @Test
    void shouldThrowWhenBusinessUnitAlreadyExists() {

        Warehouse warehouse = warehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(new Warehouse());

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.create(warehouse));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenLocationDoesNotExist() {

        Warehouse warehouse = warehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(null);

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.create(warehouse));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenMaximumWarehousesReached() {

        Warehouse warehouse = warehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(new Location("IND",1,1000));

        Warehouse existing = warehouse();
        existing.businessUnitCode = "BU999";

        when(warehouseStore.getAll())
                .thenReturn(List.of(existing));

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.create(warehouse));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenCapacityIsInvalid() {

        Warehouse warehouse = warehouse();
        warehouse.capacity = 0;

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(location());

        when(warehouseStore.getAll())
                .thenReturn(new ArrayList<>());

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.create(warehouse));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenStockIsNegative() {

        Warehouse warehouse = warehouse();
        warehouse.stock = -1;

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(location());

        when(warehouseStore.getAll())
                .thenReturn(new ArrayList<>());

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.create(warehouse));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenStockExceedsCapacity() {

        Warehouse warehouse = warehouse();
        warehouse.capacity = 100;
        warehouse.stock = 120;

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(location());

        when(warehouseStore.getAll())
                .thenReturn(new ArrayList<>());

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.create(warehouse));

        assertEquals(422, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowWhenLocationCapacityExceeded() {

        Warehouse warehouse = warehouse();
        warehouse.capacity = 600;

        Warehouse existing = warehouse();
        existing.businessUnitCode = "BU999";
        existing.capacity = 500;

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        when(locationResolver.resolveByIdentifier("IND"))
                .thenReturn(new Location("IND",5,1000));

        when(warehouseStore.getAll())
                .thenReturn(List.of(existing));

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.create(warehouse));

        assertEquals(422, ex.getResponse().getStatus());
    }
}