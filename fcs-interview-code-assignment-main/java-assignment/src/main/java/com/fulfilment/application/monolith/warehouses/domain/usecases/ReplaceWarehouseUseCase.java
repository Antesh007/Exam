package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

  private final WarehouseStore warehouseStore;
  private final LocationResolver locationResolver;
  
  @Inject
  public ReplaceWarehouseUseCase(
          WarehouseStore warehouseStore,
          LocationResolver locationResolver) {

      this.warehouseStore = warehouseStore;
      this.locationResolver = locationResolver;
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

	    // Validation 3: Location must be valid
	    var location = locationResolver.resolveByIdentifier(newWarehouse.location);
	    if (location == null) {
	      throw new WebApplicationException(
	          "Location '" + newWarehouse.location + "' does not exist.", 
	          422);
	    }

	    // Validation 4: Total capacity check at location (excluding current warehouse)
	    int totalCapacityAtLocation = warehouseStore.getAll().stream()
	        .filter(w -> w.location.equals(newWarehouse.location) 
	            && w.archivedAt == null 
	            && !w.businessUnitCode.equals(newWarehouse.businessUnitCode))
	        .mapToInt(w -> w.capacity != null ? w.capacity : 0)
	        .sum();
	    
	    if (totalCapacityAtLocation + newWarehouse.capacity > location.maxCapacity) {
	      throw new WebApplicationException(
	          "Warehouse capacity exceeds maximum total capacity for location '" + newWarehouse.location 
	          + "'. Available capacity: " + (location.maxCapacity - totalCapacityAtLocation) + ".", 
	          422);
	    }

	    // Update the warehouse
	    newWarehouse.createdAt = existingWarehouse.createdAt; // Preserve creation time
	    warehouseStore.update(newWarehouse);
  }
}
