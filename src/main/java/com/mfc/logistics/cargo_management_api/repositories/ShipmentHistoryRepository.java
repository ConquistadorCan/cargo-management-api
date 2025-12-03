package com.mfc.logistics.cargo_management_api.repositories;

import com.mfc.logistics.cargo_management_api.models.ShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentHistoryRepository extends JpaRepository<ShipmentHistory, Long> {
}
