package com.fulfilment.application.monolith.warehouses.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArchiveWarehouseUseCaseTest {

    @Mock
    WarehouseStore warehouseStore;

    private ArchiveWarehouseUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new ArchiveWarehouseUseCase(warehouseStore);
    }

    private Warehouse warehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU001";
        warehouse.location = "IND";
        warehouse.capacity = 100;
        warehouse.stock = 50;
        warehouse.createdAt = LocalDateTime.now();
        return warehouse;
    }

    @Test
    void shouldArchiveWarehouseSuccessfully() {

        Warehouse warehouse = warehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(warehouse);

        useCase.archive(warehouse);

        verify(warehouseStore).update(warehouse);

        assertNotNull(warehouse.archivedAt);
    }

    @Test
    void shouldThrowWhenWarehouseDoesNotExist() {

        Warehouse warehouse = warehouse();

        when(warehouseStore.findByBusinessUnitCode("BU001"))
                .thenReturn(null);

        WebApplicationException ex = assertThrows(
                WebApplicationException.class,
                () -> useCase.archive(warehouse));

        assertEquals(404, ex.getResponse().getStatus());

        verify(warehouseStore, never()).update(any());
    }
}