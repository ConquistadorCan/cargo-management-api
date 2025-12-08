package com.mfc.logistics.cargo_management_api.service;


import com.mfc.logistics.cargo_management_api.dto.request.ShipmentCreateRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.ShipmentCreateResponseDTO;
import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;

public interface ShipmentService {
    public ShipmentCreateResponseDTO createShipment(ShipmentCreateRequestDTO request);

    public void markShipmentDelivered(String trackingNumber);

    public ShipmentStatusEnum getShipmentStatus(String trackingNumber);

    public void cancelShipment(String trackingNumber);
}
