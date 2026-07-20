package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class WarehouseRepository implements WarehouseStore, PanacheRepository<DbWarehouse> {

  @Override
  public List<Warehouse> getAll() {
    return this.listAll().stream().map(DbWarehouse::toWarehouse).toList();
  }

  @Override
  public void create(Warehouse warehouse) {
	  var dbWarehouse = new DbWarehouse();
	    dbWarehouse.businessUnitCode = warehouse.businessUnitCode;
	    dbWarehouse.location = warehouse.location;
	    dbWarehouse.capacity = warehouse.capacity;
	    dbWarehouse.stock = warehouse.stock;
	    dbWarehouse.createdAt = LocalDateTime.now();
	    this.persist(dbWarehouse);
  }

  @Override
  public void update(Warehouse warehouse) {
	  var existing = find("businessUnitCode", warehouse.businessUnitCode).firstResult();
	    if (existing != null) {
	      existing.capacity = warehouse.capacity;
	      existing.stock = warehouse.stock;
	      existing.location = warehouse.location;
	    }
  }

  @Override
  public void remove(Warehouse warehouse) {
	  delete("businessUnitCode", warehouse.businessUnitCode);
  }

  @Override
  public Warehouse findByBusinessUnitCode(String buCode) {
	  var result = find("businessUnitCode", buCode).firstResult();
	    return result != null ? result.toWarehouse() : null;
  }
}
