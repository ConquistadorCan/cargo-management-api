package com.mfc.logistics.cargo_management_api.service;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import com.mfc.logistics.cargo_management_api.model.Shipment;
import com.mfc.logistics.cargo_management_api.model.ShipmentHistory;
import com.mfc.logistics.cargo_management_api.model.User;

public interface ShipmentHistoryService {
    public ShipmentHistory createShipmentHistory(Shipment shipment, User changedBy, ShipmentStatusEnum prevStatement, ShipmentStatusEnum nextStatement);
}
