package com.mfc.logistics.cargo_management_api.service.implementation;

import com.mfc.logistics.cargo_management_api.dto.request.ShipmentCreateRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.ShipmentCreateResponseDTO;
import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.model.Shipment;
import com.mfc.logistics.cargo_management_api.model.User;
import com.mfc.logistics.cargo_management_api.repository.ShipmentRepository;
import com.mfc.logistics.cargo_management_api.service.ShipmentHistoryService;
import com.mfc.logistics.cargo_management_api.service.ShipmentService;
import com.mfc.logistics.cargo_management_api.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ShipmentHistoryService shipmentHistoryService;
    private final UserService userService;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, UserService userService, ShipmentHistoryService shipmentHistoryService) {
        this.shipmentRepository = shipmentRepository;
        this.userService = userService;
        this.shipmentHistoryService = shipmentHistoryService;
    }

    @Override
    @Transactional
    public ShipmentCreateResponseDTO createShipment(ShipmentCreateRequestDTO request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user  = userService.findByUsername(userDetails.getUsername());

        Shipment shipment = new Shipment(
            request.getOrigin(),
            request.getDestination(),
            user
        );

        shipmentRepository.save(shipment);

        shipmentHistoryService.createShipmentHistory(shipment, user, null, shipment.getStatus());

        return ShipmentCreateResponseDTO.from(shipment);
    }

    @Override
    @Transactional
    public void markShipmentDelivered(String trackingNumber) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User staff = userService.findByUsername(userDetails.getUsername());

        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber).orElseThrow();

        if (!shipment.getAssignedTo().getId().equals(staff.getId())) {
            throw new RuntimeException("Shipment is not assigned to the staff. Please check the status of the shipment. Shipment tracking no: " + shipment.getTrackingNumber());
        }

        ShipmentStatusEnum prevStatus = shipment.getStatus();
        if (!prevStatus.equals(ShipmentStatusEnum.IN_TRANSIT)) {
           throw new RuntimeException("Shipment is not in transit. Please check the status of the shipment. Shipment tracking no: " + shipment.getTrackingNumber());
        }

        shipment.setStatus(ShipmentStatusEnum.DELIVERED);
        shipmentRepository.save(shipment);

        shipmentHistoryService.createShipmentHistory(shipment, staff, prevStatus, ShipmentStatusEnum.DELIVERED);
    }
}
