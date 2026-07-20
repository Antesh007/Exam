package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

  private final WarehouseStore warehouseStore;

  public ReplaceWarehouseUseCase(WarehouseStore warehouseStore) {
    this.warehouseStore = warehouseStore;
  }

  @Override
  public void replace(Warehouse newWarehouse) {
	  // Find the existing warehouse to be replaced
	    var existingWarehouse = warehouseStore.findByBusinessUnitCode(newWarehouse.businessUnitCode);
	    
	    if (existingWarehouse == null) {
	      throw new WebApplicationException(
	          "Warehouse with business unit code '" + newWarehouse.businessUnitCode + "' not found.", 
	          404);
	    }

	    // Validation 1: Stock Matching - new warehouse must have same stock as old one
	    if (!existingWarehouse.stock.equals(newWarehouse.stock)) {
	      throw new WebApplicationException(
	          "Stock of new warehouse (" + newWarehouse.stock + ") must match the stock of the warehouse being replaced (" 
	          + existingWarehouse.stock + ").", 
	          422);
	    }

	    // Validation 2: Capacity Accommodation - new warehouse capacity must accommodate existing stock
	    if (newWarehouse.capacity < existingWarehouse.stock) {
	      throw new WebApplicationException(
	          "New warehouse capacity (" + newWarehouse.capacity + ") cannot accommodate existing stock (" 
	          + existingWarehouse.stock + ").", 
	          422);
	    }

	    // Update the warehouse
	    newWarehouse.createdAt = existingWarehouse.createdAt; // Preserve creation time
	    warehouseStore.update(newWarehouse);
  }
}
