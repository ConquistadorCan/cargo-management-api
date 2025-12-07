package com.mfc.logistics.cargo_management_api.service.implementation;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.model.Shipment;
import com.mfc.logistics.cargo_management_api.model.User;
import com.mfc.logistics.cargo_management_api.repository.ShipmentRepository;
import com.mfc.logistics.cargo_management_api.service.ShipmentAssignmentService;
import com.mfc.logistics.cargo_management_api.service.ShipmentHistoryService;
import com.mfc.logistics.cargo_management_api.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ShipmentAssignmentServiceImpl implements ShipmentAssignmentService {
    private final ShipmentRepository shipmentRepository;
    private final UserService userService;
    private final ShipmentHistoryService shipmentHistoryService;
    private final Random random = new Random();

    public ShipmentAssignmentServiceImpl(UserService userService, ShipmentRepository shipmentRepository, ShipmentHistoryService shipmentHistoryService, Dotenv dotenv) {
        this.userService = userService;
        this.shipmentRepository = shipmentRepository;
        this.shipmentHistoryService = shipmentHistoryService;
    }

    @Override
    @Transactional
    public void assignCourierToShipment(Shipment shipment) {
        ShipmentStatusEnum prevStatus = shipment.getStatus();
        if (!prevStatus.equals(ShipmentStatusEnum.PENDING)) {
            throw new RuntimeException("Shipment is not pending. Please check the status of the shipment. Shipment tracking no: " + shipment.getTrackingNumber());
        }

        List<User> staffList = userService.findAvailableStaff();

        if (staffList.isEmpty()) {
            return;
        }

        User chosenStaff = staffList.get(random.nextInt(staffList.size()));

        shipment.setAssignedTo(chosenStaff);
        shipment.setStatus(ShipmentStatusEnum.IN_TRANSIT);
        shipmentRepository.save(shipment);

        shipmentHistoryService.createShipmentHistory(shipment, null, prevStatus, ShipmentStatusEnum.IN_TRANSIT);
    }

    @Override
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void autoAssignUnnassignedShipments() {
        List<Shipment> unassignedShipments = shipmentRepository.findByStatus(ShipmentStatusEnum.PENDING);

        for (Shipment shipment : unassignedShipments) {
            assignCourierToShipment(shipment);
        }
    }
}
