package com.fulfilment.application.monolith.event;

import com.fulfilment.application.monolith.stores.Store;

public class StoreUpdatedEvent {
	
	private final Store store;

    public StoreUpdatedEvent(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }
}
