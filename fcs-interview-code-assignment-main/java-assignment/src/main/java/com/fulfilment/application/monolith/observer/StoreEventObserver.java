package com.fulfilment.application.monolith.observer;

import com.fulfilment.application.monolith.event.StoreCreatedEvent;
import com.fulfilment.application.monolith.event.StoreUpdatedEvent;
import com.fulfilment.application.monolith.stores.LegacyStoreManagerGateway;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;

@ApplicationScoped
public class StoreEventObserver {
	
	 @Inject
	    LegacyStoreManagerGateway gateway;

	    public void onStoreCreated(
	        @Observes(during = TransactionPhase.AFTER_SUCCESS)
	        StoreCreatedEvent event) {

	        gateway.createStoreOnLegacySystem(event.getStore());
	    }

	    public void onStoreUpdated(
	        @Observes(during = TransactionPhase.AFTER_SUCCESS)
	        StoreUpdatedEvent event) {

	        gateway.updateStoreOnLegacySystem(event.getStore());
	    }

	}
