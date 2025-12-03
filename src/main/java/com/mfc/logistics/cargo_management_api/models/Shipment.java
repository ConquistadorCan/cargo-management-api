package com.mfc.logistics.cargo_management_api.models;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String trackingNumber;

    private String origin;
    private String destination;

    @Enumerated(EnumType.STRING)
    private ShipmentStatusEnum status;

    @CreationTimestamp()
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="assigned_to")
    private User assignedTo;

    public Shipment() {}

    public Shipment(String trackingNumber, String origin, String destination, ShipmentStatusEnum status, LocalDateTime createdAt, User assigned_to) {
        this.trackingNumber = trackingNumber;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.createdAt = createdAt;
        this.assignedTo = assigned_to;
    }

    public Long getId() { return id;}

    public String getTrackingNumber() { return trackingNumber; }

    public String getOrigin() { return origin; }

    public String getDestination() { return destination; }

    public ShipmentStatusEnum getStatus() { return status; }
    public void setStatus(ShipmentStatusEnum status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public User getAssignedTo() { return assignedTo; }
}
