package br.com.joaovitor.service;

import br.com.joaovitor.dto.CreateCardRequestDTO;
import br.com.joaovitor.enums.CardStatus;
import br.com.joaovitor.enums.CardType;
import br.com.joaovitor.enums.DeliveryStatus;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.model.Card;
import br.com.joaovitor.repository.CardRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class CardService {

    @Inject
    CardRepository cardRepository;

    @Inject
    AccountService accountService;

    public Card createPhysicalCard(CreateCardRequestDTO createCardRequestDTO) {

        Account account = accountService.findById(createCardRequestDTO.getAccountId());

        Card card = new Card();
        card.setCardHolder(account.getCustomer().getName());
        card.setNumber(generateCardNumber());
        card.setExpirationDate(LocalDate.now().plusYears(5));
        card.setCvv(generateCVV());
        card.setType(CardType.PHYSICAL);
        card.setStatus(CardStatus.PENDING);
        card.setAccount(account);

        cardRepository.persist(card);
        return card;
    }

    public Card createVirtualCard(CreateCardRequestDTO createCardRequestDTO) {

        Account account = accountService.findById(createCardRequestDTO.getAccountId());

        boolean hasActivePhysicalCard = cardRepository.findActivePhysicalCardByAccountId(account.getId()).isPresent();

        if (!hasActivePhysicalCard) {
            throw new IllegalStateException("A virtual card can only be created after a physical card is active");
        }

        Card card = new Card();
        card.setCardHolder(account.getCustomer().getName());
        card.setNumber(generateCardNumber());
        card.setExpirationDate(LocalDate.now().plusYears(5));
        card.setCvv(generateCVV());
        card.setType(CardType.VIRTUAL);
        card.setStatus(CardStatus.PENDING);
        card.setAccount(account);

        cardRepository.persist(card);
        return card;
    }

    private String generateCardNumber() {
        return String.valueOf((long) (Math.random() * 1_0000_0000_0000_0000L));
    }

    private String generateCVV() {
        return String.valueOf((int) (Math.random() * 900) + 100);
    }

    public List<Card> listCardsByAccountId(Long accountId) {
        return cardRepository.findCardsByAccountId(accountId);
    }

    public Card activateCard(Long cardId) {
        Card card = cardRepository.findById(cardId);
        if (card == null) {
            throw new NotFoundException("Card not found");
        }
        if (card.getStatus() != CardStatus.PENDING) {
            throw new IllegalStateException("Only pending cards can be activated");
        }
        if (card.getType() == CardType.PHYSICAL && card.getDeliveryStatus() != DeliveryStatus.DELIVERED) {
            throw new IllegalStateException("Card must be delivered before it can be activated");
        }
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.persist(card);
        return card;
    }

    public Card blockCard(Long cardId) {
        Card card = cardRepository.findById(cardId);
        if (card == null) {
            throw new NotFoundException("Card not found");
        }
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new IllegalStateException("Only active cards can be blocked");
        }
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.persist(card);
        return card;
    }

    public Card shipCard(Long cardId) {
        Card card = cardRepository.findById(cardId);
        if (card == null) {
            throw new NotFoundException("Card not found");
        }
        if (card.getStatus() != CardStatus.PENDING) {
            throw new IllegalStateException("Only pending cards can be shipped");
        }
        card.setDeliveryStatus(DeliveryStatus.SHIPPED);
        cardRepository.persist(card);
        return card;
    }

    public Card confirmCardReceipt(Long cardId) {
        Card card = cardRepository.findById(cardId);
        if (card == null) {
            throw new NotFoundException("Card not found");
        }
        if (card.getDeliveryStatus() != DeliveryStatus.SHIPPED) {
            throw new IllegalStateException("Only shipped cards can be confirmed as received");
        }
        card.setDeliveryStatus(DeliveryStatus.DELIVERED);
        cardRepository.persist(card);
        return card;
    }

    public Card unblockCard(Long cardId) {
        Card card = cardRepository.findById(cardId);
        if (card == null) {
            throw new NotFoundException("Card not found");
        }
        if (card.getStatus() != CardStatus.BLOCKED) {
            throw new IllegalStateException("Only blocked cards can be unblocked");
        }
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.persist(card);
        return card;
    }
}
