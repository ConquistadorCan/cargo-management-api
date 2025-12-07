package com.mfc.logistics.cargo_management_api.service.implementation;

import com.mfc.logistics.cargo_management_api.dto.request.ShipmentCreateRequestDTO;
import com.mfc.logistics.cargo_management_api.dto.response.ShipmentCreateResponseDTO;
import com.mfc.logistics.cargo_management_api.model.Shipment;
import com.mfc.logistics.cargo_management_api.model.User;
import com.mfc.logistics.cargo_management_api.repository.ShipmentRepository;
import com.mfc.logistics.cargo_management_api.repository.UserRepository;
import com.mfc.logistics.cargo_management_api.service.ShipmentHistoryService;
import com.mfc.logistics.cargo_management_api.service.ShipmentService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final UserRepository userRepository;
    private final ShipmentHistoryService shipmentHistoryService;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, UserRepository userRepository, ShipmentHistoryService shipmentHistoryService) {
        this.shipmentRepository = shipmentRepository;
        this.userRepository = userRepository;
        this.shipmentHistoryService = shipmentHistoryService;
    }

    @Override
    @Transactional
    public ShipmentCreateResponseDTO createShipment(ShipmentCreateRequestDTO request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user  = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Shipment shipment = new Shipment(
            request.getOrigin(),
            request.getDestination(),
            user
        );

        shipmentRepository.save(shipment);

        shipmentHistoryService.createShipmentHistory(shipment, user, null, shipment.getStatus());

        return ShipmentCreateResponseDTO.from(shipment);
    }
}
