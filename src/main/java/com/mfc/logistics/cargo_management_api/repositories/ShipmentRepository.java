package com.mfc.logistics.cargo_management_api.repositories;

import com.mfc.logistics.cargo_management_api.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
