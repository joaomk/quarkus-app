package br.com.joaovitor.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_webhook")
public class DeliveryWebhook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trackingId", nullable = false)
    private String trackingId;

    @Column(name = "deliveryStatus", nullable = false)
    private String deliveryStatus;

    @Column(name = "deliveryDate", nullable = false)
    private LocalDateTime deliveryDate;

    @Column(name = "deliveryReturnReason")
    private String deliveryReturnReason;

    @Column(name = "deliveryAddress", nullable = false)
    private String deliveryAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryReturnReason() {
        return deliveryReturnReason;
    }

    public void setDeliveryReturnReason(String deliveryReturnReason) {
        this.deliveryReturnReason = deliveryReturnReason;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
