package com.mfc.logistics.cargo_management_api.service;


import com.mfc.logistics.cargo_management_api.dto.request.ShipmentCreateRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.ShipmentCreateResponseDTO;

public interface ShipmentService {
    public ShipmentCreateResponseDTO createShipment(ShipmentCreateRequestDTO request);
}
