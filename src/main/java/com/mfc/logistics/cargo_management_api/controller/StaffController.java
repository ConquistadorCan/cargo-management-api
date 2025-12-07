package com.mfc.logistics.cargo_management_api.controller;

import com.mfc.logistics.cargo_management_api.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final ShipmentService shipmentService;

    public StaffController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/deliver/{trackingNumber}")
    public ResponseEntity<?> markAsDelivered(@PathVariable String trackingNumber) {
        shipmentService.markShipmentDelivered(trackingNumber);
        return ResponseEntity.ok("Shipment marked as delivered successfully");
    }
}
