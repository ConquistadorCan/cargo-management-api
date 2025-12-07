package com.mfc.logistics.cargo_management_api.dto.response;

import com.mfc.logistics.cargo_management_api.model.Shipment;

public class ShipmentCreateResponseDTO {
    private String trackingNumber;
    private String origin;
    private String destination;

    public ShipmentCreateResponseDTO() {}

    public static ShipmentCreateResponseDTO from (Shipment shipment) {
        ShipmentCreateResponseDTO dto = new ShipmentCreateResponseDTO();
        dto.trackingNumber = shipment.getTrackingNumber();
        dto.origin = shipment.getOrigin();
        dto.destination = shipment.getDestination();
        return dto;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
}
