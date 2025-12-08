package com.mfc.logistics.cargo_management_api.controller;

import com.mfc.logistics.cargo_management_api.dto.request.ShipmentCreateRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.ShipmentCreateResponseDTO;
import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipment")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<ShipmentCreateResponseDTO> createShipment(@RequestBody ShipmentCreateRequestDTO request) {
        ShipmentCreateResponseDTO response = shipmentService.createShipment(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{trackingNumber}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ShipmentStatusEnum> getShipmentStatus(@PathVariable String trackingNumber) {
        ShipmentStatusEnum status = shipmentService.getShipmentStatus(trackingNumber);

        return ResponseEntity.ok(status);
    }

    @PutMapping("/cancel/{trackingNumber}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> cancelShipment(@PathVariable String trackingNumber) {
        shipmentService.cancelShipment(trackingNumber);

        return ResponseEntity.ok(null);
    }

}
