package com.mfc.logistics.cargo_management_api.model;

import com.mfc.logistics.cargo_management_api.enums.ShipmentStatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @JoinColumn(name="created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="assigned_to")
    private User assignedTo;

    public Shipment() {}

    public Shipment(String origin, String destination, User createdBy) {
        this.trackingNumber = UUID.randomUUID().toString();
        this.origin = origin;
        this.destination = destination;
        this.createdBy = createdBy;
        this.status = ShipmentStatusEnum.PENDING;
    }

    public Long getId() { return id; }

    public String getTrackingNumber() { return trackingNumber; }

    public String getOrigin() { return origin; }

    public String getDestination() { return destination; }

    public User getCreatedBy() { return createdBy; }

    public ShipmentStatusEnum getStatus() { return status; }
    public void setStatus(ShipmentStatusEnum status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public User getAssignedTo() { return assignedTo; }
    public void setAssignedTo(User staff) { this.assignedTo = staff; }
}
