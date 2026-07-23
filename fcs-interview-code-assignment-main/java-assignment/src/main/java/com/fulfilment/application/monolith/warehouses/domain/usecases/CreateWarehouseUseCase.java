package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class CreateWarehouseUseCase implements CreateWarehouseOperation {

  private final WarehouseStore warehouseStore;
  private final LocationResolver locationResolver;

  @Inject
  public CreateWarehouseUseCase(
          WarehouseStore warehouseStore,
          LocationResolver locationResolver) {

      this.warehouseStore = warehouseStore;
      this.locationResolver = locationResolver;
  }

  @Override
  public void create(Warehouse warehouse) {
	  // Validation 1: Business Unit Code should not already exist
	    if (warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode) != null) {
	      throw new WebApplicationException(
	          "Warehouse with business unit code '" + warehouse.businessUnitCode + "' already exists.", 
	          422);
	    }

	    // Validation 2: Location must be valid
	    var location = locationResolver.resolveByIdentifier(warehouse.location);
	    if (location == null) {
	      throw new WebApplicationException(
	          "Location '" + warehouse.location + "' does not exist.", 
	          422);
	    }

	    // Validation 3: Check if warehouse can be created at this location (max warehouses)
	    long warehouseCountAtLocation = warehouseStore.getAll().stream()
	        .filter(w -> w.location.equals(warehouse.location) && w.archivedAt == null)
	        .count();
	    
	    if (warehouseCountAtLocation >= location.maxNumberOfWarehouses) {
	      throw new WebApplicationException(
	          "Cannot create warehouse at location '" + warehouse.location 
	          + "'. Maximum number of warehouses (" + location.maxNumberOfWarehouses + ") already reached.", 
	          422);
	    }

	    // Validation 4: Capacity and Stock validation
	    if (warehouse.capacity == null || warehouse.capacity <= 0) {
	      throw new WebApplicationException("Warehouse capacity must be greater than 0.", 422);
	    }

	    if (warehouse.stock == null || warehouse.stock < 0) {
	      throw new WebApplicationException("Warehouse stock cannot be negative.", 422);
	    }

	    if (warehouse.stock > warehouse.capacity) {
	      throw new WebApplicationException(
	          "Warehouse stock (" + warehouse.stock + ") cannot exceed its capacity (" 
	          + warehouse.capacity + ").", 422);
	    }

	    // Validation 5: Total capacity check at location
	    int totalCapacityAtLocation = warehouseStore.getAll().stream()
	        .filter(w -> w.location.equals(warehouse.location) && w.archivedAt == null)
	        .mapToInt(w -> w.capacity != null ? w.capacity : 0)
	        .sum();
	    
	    if (totalCapacityAtLocation + warehouse.capacity > location.maxCapacity) {
	      throw new WebApplicationException(
	          "Warehouse capacity exceeds maximum total capacity for location '" + warehouse.location 
	          + "'. Available capacity: " + (location.maxCapacity - totalCapacityAtLocation) + ".", 
	          422);
	    }

	    // If all validations passed, create the warehouse
	    warehouseStore.create(warehouse);
  }
}
