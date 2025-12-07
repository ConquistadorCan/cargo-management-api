package com.mfc.logistics.cargo_management_api.service.implementation;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.model.Shipment;
import com.mfc.logistics.cargo_management_api.model.ShipmentHistory;
import com.mfc.logistics.cargo_management_api.model.User;
import com.mfc.logistics.cargo_management_api.repository.ShipmentHistoryRepository;
import com.mfc.logistics.cargo_management_api.repository.ShipmentRepository;
import com.mfc.logistics.cargo_management_api.repository.UserRepository;
import com.mfc.logistics.cargo_management_api.service.ShipmentAssignmentService;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ShipmentAssignmentServiceImpl implements ShipmentAssignmentService {
    private final UserRepository userRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShipmentHistoryRepository shipmentHistoryRepository;
    private final Random random = new Random();

    public ShipmentAssignmentServiceImpl(UserRepository userRepository, ShipmentRepository shipmentRepository, ShipmentHistoryRepository shipmentHistoryRepository, Dotenv dotenv) {
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.shipmentHistoryRepository = shipmentHistoryRepository;
    }

    @Override
    @Transactional
    public void assignCourierToShipment(Shipment shipment) {
        ShipmentStatusEnum prevStatus = shipment.getStatus();
        if (!prevStatus.equals(ShipmentStatusEnum.PENDING)) {
            throw new RuntimeException("Shipment is not pending. Please check the status of the shipment. Shipment tracking no: " + shipment.getTrackingNumber());
        }

        List<User> staffList = userRepository.findAvailableStaff();

        if (staffList.isEmpty()) {
            return;
        }

        User chosenStaff = staffList.get(random.nextInt(staffList.size()));

        shipment.setAssignedTo(chosenStaff);
        shipment.setStatus(ShipmentStatusEnum.IN_TRANSIT);
        shipmentRepository.save(shipment);

        ShipmentHistory shipmentHistory= new ShipmentHistory(null, prevStatus, ShipmentStatusEnum.IN_TRANSIT, shipment);
        shipmentHistoryRepository.save(shipmentHistory);
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
