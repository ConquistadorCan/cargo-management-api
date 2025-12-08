package com.mfc.logistics.cargo_management_api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "shipment.exchange";
    public static final String ROUTING_KEY = "shipment.delivered";
    public static final String QUEUE = "shipment.delivered.queue";

    @Bean
    public DirectExchange shipmentExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue shipmentQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding shipmentBinding() {
        return BindingBuilder.bind(shipmentQueue()).to(shipmentExchange()).with(ROUTING_KEY);
    }
}
