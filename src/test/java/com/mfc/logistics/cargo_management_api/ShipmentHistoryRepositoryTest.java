package com.mfc.logistics.cargo_management_api;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.models.Shipment;
import com.mfc.logistics.cargo_management_api.models.ShipmentHistory;
import com.mfc.logistics.cargo_management_api.models.User;
import com.mfc.logistics.cargo_management_api.repositories.ShipmentHistoryRepository;
import com.mfc.logistics.cargo_management_api.repositories.ShipmentRepository;
import com.mfc.logistics.cargo_management_api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class ShipmentHistoryRepositoryTest {

    @Autowired
    private ShipmentHistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Test
    public void testSaveAndFindById() {
        User user = new User("Admin", "admin@example.com", "pass", null);
        userRepository.save(user);

        Shipment shipment = new Shipment("TRACKHIST", "CityX", "CityY", ShipmentStatusEnum.PENDING, LocalDateTime.now(), null);
        shipmentRepository.save(shipment);

        ShipmentHistory history = new ShipmentHistory(user, ShipmentStatusEnum.PENDING, ShipmentStatusEnum.IN_TRANSIT, LocalDateTime.now(), shipment);
        historyRepository.save(history);

        ShipmentHistory found = historyRepository.findById(history.getId()).orElse(null);

        Assertions.assertNotNull(found);
        Assertions.assertEquals(user.getId(), found.getChangedBy().getId());
        Assertions.assertEquals(shipment.getId(), found.getShipment().getId());
        Assertions.assertEquals(ShipmentStatusEnum.PENDING, found.getPrevStatement());
        Assertions.assertEquals(ShipmentStatusEnum.IN_TRANSIT, found.getNextStatement());
    }

    @Test
    public void testUpdate() {
        User user = new User("Admin2", "admin2@example.com", "pass", null);
        userRepository.save(user);

        Shipment shipment = new Shipment("TRACKUPD", "CityA", "CityB", ShipmentStatusEnum.PENDING, LocalDateTime.now(), null);
        shipmentRepository.save(shipment);

        ShipmentHistory history = new ShipmentHistory(user, ShipmentStatusEnum.PENDING, ShipmentStatusEnum.IN_TRANSIT, LocalDateTime.now(), shipment);
        historyRepository.save(history);

        history = historyRepository.findById(history.getId()).orElse(null);
        history = new ShipmentHistory(user, history.getPrevStatement(), ShipmentStatusEnum.DELIVERED, LocalDateTime.now(), shipment);
        historyRepository.save(history);

        ShipmentHistory updated = historyRepository.findById(history.getId()).orElse(null);
        Assertions.assertEquals(ShipmentStatusEnum.DELIVERED, updated.getNextStatement());
    }

    @Test
    public void testDelete() {
        ShipmentHistory history = new ShipmentHistory(null, ShipmentStatusEnum.PENDING, ShipmentStatusEnum.IN_TRANSIT, LocalDateTime.now(), null);
        historyRepository.save(history);

        Long id = history.getId();
        historyRepository.delete(history);

        Assertions.assertTrue(historyRepository.findById(id).isEmpty());
    }

    @Test
    public void testFindAll() {
        ShipmentHistory h1 = new ShipmentHistory(null, ShipmentStatusEnum.PENDING, ShipmentStatusEnum.IN_TRANSIT, LocalDateTime.now(), null);
        ShipmentHistory h2 = new ShipmentHistory(null, ShipmentStatusEnum.IN_TRANSIT, ShipmentStatusEnum.DELIVERED, LocalDateTime.now(), null);
        historyRepository.save(h1);
        historyRepository.save(h2);

        List<ShipmentHistory> histories = historyRepository.findAll();
        Assertions.assertEquals(2, histories.size());
    }

    @Test
    public void testShipmentHistoryRelations() {
        User admin = new User("Admin", "admin@example.com", "pass", null);
        userRepository.save(admin);

        Shipment shipment = new Shipment("TRACKHIST2", "CityA", "CityB", ShipmentStatusEnum.PENDING, LocalDateTime.now(), null);
        shipmentRepository.save(shipment);

        ShipmentHistory history = new ShipmentHistory(admin, ShipmentStatusEnum.PENDING, ShipmentStatusEnum.IN_TRANSIT, LocalDateTime.now(), shipment);
        historyRepository.save(history);

        ShipmentHistory found = historyRepository.findById(history.getId()).orElse(null);
        Assertions.assertNotNull(found);
        Assertions.assertEquals(admin.getId(), found.getChangedBy().getId());
        Assertions.assertEquals(shipment.getId(), found.getShipment().getId());
    }

}
