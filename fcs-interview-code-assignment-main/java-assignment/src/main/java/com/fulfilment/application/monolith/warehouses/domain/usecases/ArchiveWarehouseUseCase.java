package com.fulfilment.application.monolith.warehouses.domain.usecases;

import java.time.LocalDateTime;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class ArchiveWarehouseUseCase implements ArchiveWarehouseOperation {

  private final WarehouseStore warehouseStore;

  public ArchiveWarehouseUseCase(WarehouseStore warehouseStore) {
    this.warehouseStore = warehouseStore;
  }

  @Override
  public void archive(Warehouse warehouse) {
   
	  // Find the warehouse to archive
	    var existingWarehouse = warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode);
	    
	    if (existingWarehouse == null) {
	      throw new WebApplicationException(
	          "Warehouse with business unit code '" + warehouse.businessUnitCode + "' not found.", 
	          404);
	    }

	    // Set archive timestamp
	    existingWarehouse.archivedAt = LocalDateTime.now();
    warehouseStore.update(warehouse);
  }
}
