package com.mfc.logistics.cargo_management_api;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.models.Shipment;
import com.mfc.logistics.cargo_management_api.models.User;
import com.mfc.logistics.cargo_management_api.repositories.ShipmentRepository;
import com.mfc.logistics.cargo_management_api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class ShipmentRepositoryTest {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindById() {
        User user = new User("Driver1", "driver1@example.com", "pass", null);
        userRepository.save(user);

        Shipment shipment = new Shipment("TRACK123", "Istanbul", "Ankara", ShipmentStatusEnum.PENDING, LocalDateTime.now(), user);
        shipmentRepository.save(shipment);

        Shipment found = shipmentRepository.findById(shipment.getId()).orElse(null);

        Assertions.assertNotNull(found);
        Assertions.assertEquals("TRACK123", found.getTrackingNumber());
        Assertions.assertEquals("Istanbul", found.getOrigin());
        Assertions.assertEquals("Ankara", found.getDestination());
        Assertions.assertEquals(ShipmentStatusEnum.PENDING, found.getStatus());
        Assertions.assertEquals(user.getId(), found.getAssignedTo().getId());
    }

    @Test
    public void testUpdate() {
        User user = new User("Driver2", "driver2@example.com", "pass", null);
        userRepository.save(user);

        Shipment shipment = new Shipment("TRACK456", "Izmir", "Bursa", ShipmentStatusEnum.PENDING, LocalDateTime.now(), user);
        shipmentRepository.save(shipment);

        shipment.setStatus(ShipmentStatusEnum.IN_TRANSIT);
        shipmentRepository.save(shipment);

        Shipment updated = shipmentRepository.findById(shipment.getId()).orElse(null);
        Assertions.assertEquals(ShipmentStatusEnum.IN_TRANSIT, updated.getStatus());
    }

    @Test
    public void testDelete() {
        Shipment shipment = new Shipment("TRACK789", "Antalya", "Mersin", ShipmentStatusEnum.PENDING, LocalDateTime.now(), null);
        shipmentRepository.save(shipment);

        Long id = shipment.getId();
        shipmentRepository.delete(shipment);

        Assertions.assertTrue(shipmentRepository.findById(id).isEmpty());
    }

    @Test
    public void testFindAll() {
        Shipment s1 = new Shipment("S1", "CityA", "CityB", ShipmentStatusEnum.PENDING, LocalDateTime.now(), null);
        Shipment s2 = new Shipment("S2", "CityC", "CityD", ShipmentStatusEnum.DELIVERED, LocalDateTime.now(), null);
        shipmentRepository.save(s1);
        shipmentRepository.save(s2);

        List<Shipment> shipments = shipmentRepository.findAll();
        Assertions.assertEquals(2, shipments.size());
    }

    @Test
    public void testShipmentAssignedToUser() {
        User user = new User("Driver", "driver@example.com", "pass", null);
        userRepository.save(user);

        Shipment shipment = new Shipment("TRACKREL", "City1", "City2", ShipmentStatusEnum.PENDING, LocalDateTime.now(), user);
        shipmentRepository.save(shipment);

        Shipment found = shipmentRepository.findById(shipment.getId()).orElse(null);
        Assertions.assertNotNull(found);
        Assertions.assertNotNull(found.getAssignedTo());
        Assertions.assertEquals(user.getId(), found.getAssignedTo().getId());
    }

}
