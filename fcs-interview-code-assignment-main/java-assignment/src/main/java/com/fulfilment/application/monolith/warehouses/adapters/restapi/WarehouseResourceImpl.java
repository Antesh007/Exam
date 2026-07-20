package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.usecases.ArchiveWarehouseUseCase;
import com.fulfilment.application.monolith.warehouses.domain.usecases.CreateWarehouseUseCase;
import com.fulfilment.application.monolith.warehouses.domain.usecases.ReplaceWarehouseUseCase;
import com.warehouse.api.WarehouseResource;
import com.warehouse.api.beans.Warehouse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;

@RequestScoped
public class WarehouseResourceImpl implements WarehouseResource {

	 @Inject private WarehouseRepository warehouseRepository;
	 @Inject private CreateWarehouseUseCase createWarehouseUseCase;
	 @Inject private ReplaceWarehouseUseCase replaceWarehouseUseCase;
	 @Inject private ArchiveWarehouseUseCase archiveWarehouseUseCase;

  @Override
  public List<Warehouse> listAllWarehousesUnits() {
    return warehouseRepository.getAll()
    		.stream()
    		.filter(w -> w.archivedAt == null) // Only active warehouses
    		.map(this::toWarehouseResponse)
    		.toList();
  }

  @Override
  @Transactional
  public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
    
	  	// Convert API bean to domain model
	  var domainWarehouse = toDomainWarehouse(data);    
	    // Call use case with validations
	    createWarehouseUseCase.create(domainWarehouse);
	    // Return the API response
	    return data;
  }

  @Override
  public Warehouse getAWarehouseUnitByID(String id) {
    
	  var warehouse = warehouseRepository.findByBusinessUnitCode(id);
	    if (warehouse == null || warehouse.archivedAt != null) {
	      throw new WebApplicationException(
	          "Warehouse with id '" + id + "' not found.", 
	          404);
	    }
	    return toWarehouseResponse(warehouse);
  }

  @Override
  @Transactional
  public void archiveAWarehouseUnitByID(String id) {
    
	  var warehouse = warehouseRepository.findByBusinessUnitCode(id);
	    if (warehouse == null) {
	      throw new WebApplicationException(
	          "Warehouse with id '" + id + "' not found.", 
	          404);
	    }
	    archiveWarehouseUseCase.archive(warehouse);
  }

  @Override
  @Transactional
  public Warehouse replaceTheCurrentActiveWarehouse(
      String businessUnitCode, @NotNull Warehouse data) {
    
	  var existingWarehouse = warehouseRepository.findByBusinessUnitCode(businessUnitCode);
	    if (existingWarehouse == null) {
	      throw new WebApplicationException(
	          "Warehouse with business unit code '" + businessUnitCode + "' not found.", 
	          404);
	    }
	    
	    // Convert API bean to domain model
	    var newDomainWarehouse = toDomainWarehouse(data);
	    newDomainWarehouse.businessUnitCode = businessUnitCode; // Ensure code is preserved
	    
	    // Call use case with validations
	    replaceWarehouseUseCase.replace(newDomainWarehouse);
	    
	    return data;
  }

  private Warehouse toWarehouseResponse(
      com.fulfilment.application.monolith.warehouses.domain.models.Warehouse warehouse) {
    var response = new Warehouse();
    response.setBusinessUnitCode(warehouse.businessUnitCode);
    response.setLocation(warehouse.location);
    response.setCapacity(warehouse.capacity);
    response.setStock(warehouse.stock);

    return response;
  }
  
  private com.fulfilment.application.monolith.warehouses.domain.models.Warehouse toDomainWarehouse(@NotNull Warehouse data) {
	    var warehouse = new com.fulfilment.application.monolith.warehouses.domain.models.Warehouse();
	    warehouse.businessUnitCode = data.getBusinessUnitCode();
	    warehouse.location = data.getLocation();
	    warehouse.capacity = data.getCapacity();
	    warehouse.stock = data.getStock();
	    return warehouse;
	  }
}
