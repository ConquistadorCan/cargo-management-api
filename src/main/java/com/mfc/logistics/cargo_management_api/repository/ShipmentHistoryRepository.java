package com.mfc.logistics.cargo_management_api.repository;

import com.mfc.logistics.cargo_management_api.model.ShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentHistoryRepository extends JpaRepository<ShipmentHistory, Long> {
}
