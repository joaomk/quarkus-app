package br.com.joaovitor.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "cvv_webhook")
public class CvvWebhook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardId", nullable = false)
    private Card card;

    @Column(name = "nextCvv", nullable = false)
    private String nextCvv;

    @Column(name = "expirationDate", nullable = false)
    private LocalDate expirationDate;

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
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
