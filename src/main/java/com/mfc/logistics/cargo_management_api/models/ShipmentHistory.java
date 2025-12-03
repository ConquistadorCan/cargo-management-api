package com.mfc.logistics.cargo_management_api.models;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_history")
public class ShipmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User changedBy;

    @ManyToOne
    private Shipment shipment;

    @Enumerated(EnumType.STRING)
    private ShipmentStatusEnum prevStatement;
    @Enumerated(EnumType.STRING)
    private ShipmentStatusEnum nextStatement;

    @CreationTimestamp()
    private LocalDateTime changedAt;

    public ShipmentHistory() {}

    public ShipmentHistory(User changedBy, ShipmentStatusEnum prevStatement, ShipmentStatusEnum nextStatement, LocalDateTime changedAt, Shipment shipment) {
        this.changedBy = changedBy;
        this.prevStatement = prevStatement;
        this.nextStatement = nextStatement;
        this.changedAt = changedAt;
        this.shipment = shipment;
    }

    public Long getId() {return id;}

    public User getChangedBy() { return changedBy; }

    public ShipmentStatusEnum getPrevStatement() { return prevStatement; }

    public ShipmentStatusEnum getNextStatement() { return nextStatement; }

    public LocalDateTime getChangedAt() { return changedAt; }

    public Shipment getShipment() { return shipment; }
}
