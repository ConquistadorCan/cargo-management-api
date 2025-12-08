package com.mfc.logistics.cargo_management_api.service;

public interface ShipmentDeliveredListener {
    public void handleDeliveredShipment(String message);
}
