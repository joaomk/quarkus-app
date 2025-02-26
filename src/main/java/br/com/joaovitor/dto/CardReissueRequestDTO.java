package br.com.joaovitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CardReissueRequestDTO {

    @NotNull(message = "Card ID cannot be null")
    private Long cardId;

    @NotBlank(message = "Reason must be provided")
    private String reason;

    // Getters and Setters
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
