package com.mfc.logistics.cargo_management_api.repository;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByStatus(ShipmentStatusEnum status);
}
