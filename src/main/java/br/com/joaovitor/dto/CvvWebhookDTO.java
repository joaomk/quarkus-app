package br.com.joaovitor.dto;

import java.time.LocalDate;

public class CvvWebhookDTO {
    private Long accountId;
    private Long cardId;
    private String nextCvv;
    private LocalDate expirationDate;

    // Getters and Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getNextCvv() {
        return nextCvv;
    }

    public void setNextCvv(String nextCvv) {
        this.nextCvv = nextCvv;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}