package com.mfc.logistics.cargo_management_api.service;

import com.mfc.logistics.cargo_management_api.model.Shipment;

public interface ShipmentAssignmentService {
    void assignCourierToShipment(Shipment shipment);
    void autoAssignUnnassignedShipments();
}
