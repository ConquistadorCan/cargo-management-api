package com.mfc.logistics.cargo_management_api.service.implementation;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.model.Shipment;
import com.mfc.logistics.cargo_management_api.model.ShipmentHistory;
import com.mfc.logistics.cargo_management_api.model.User;
import com.mfc.logistics.cargo_management_api.repository.ShipmentHistoryRepository;
import com.mfc.logistics.cargo_management_api.repository.UserRepository;
import com.mfc.logistics.cargo_management_api.service.ShipmentHistoryService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ShipmentHistoryServiceImpl implements ShipmentHistoryService {
    private final ShipmentHistoryRepository shipmentHistoryRepository;

    public ShipmentHistoryServiceImpl(ShipmentHistoryRepository shipmentHistoryRepository) {
        this.shipmentHistoryRepository = shipmentHistoryRepository;
    }

    @Override
    @Transactional
    public ShipmentHistory createShipmentHistory(Shipment shipment, User changedBy, ShipmentStatusEnum prevStatement, ShipmentStatusEnum nextStatement) {
        ShipmentHistory shipmentHistory = new ShipmentHistory(
                changedBy,
                prevStatement,
                nextStatement,
                shipment
        );

        shipmentHistoryRepository.save(shipmentHistory);
        return shipmentHistory;
    }
}
