package com.mfc.logistics.cargo_management_api.service;


import com.mfc.logistics.cargo_management_api.dto.request.ShipmentCreateRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.ShipmentCreateResponseDTO;
import com.mfc.logistics.cargo_management_api.model.Shipment;

public interface ShipmentService {
    public ShipmentCreateResponseDTO createShipment(ShipmentCreateRequestDTO request);

    public void markShipmentDelivered(String trackingNumber);
}
