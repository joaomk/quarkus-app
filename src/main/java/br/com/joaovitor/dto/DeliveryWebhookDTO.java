package br.com.joaovitor.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

public class DeliveryWebhookDTO {
    @Schema(description = "card id")
    private String trackingId;

    @Schema(description = "Delivery types")
    private String deliveryStatus;

    private LocalDateTime deliveryDate;

    @Schema(description = "Delivery Return Reason")
    private String deliveryReturnReason;

    @Schema(description = "Address id")
    private String deliveryAddress;

    // Getters and Setters

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
