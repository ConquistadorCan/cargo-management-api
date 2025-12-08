package com.mfc.logistics.cargo_management_api.service.implementation;

import com.mfc.logistics.cargo_management_api.config.RabbitMQConfig;
import com.mfc.logistics.cargo_management_api.service.ShipmentDeliveredListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShipmentDeliveredListenerImpl implements ShipmentDeliveredListener {
    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleDeliveredShipment(String message) {
        System.out.println("MAIL SENT SIMULATION: " + message);
    }
}
