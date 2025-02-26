package br.com.joaovitor.service;

import br.com.joaovitor.dto.CreateCardRequestDTO;
import br.com.joaovitor.enums.CardStatus;
import br.com.joaovitor.enums.CardType;
import br.com.joaovitor.enums.DeliveryStatus;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.model.Card;
import br.com.joaovitor.model.Customer;
import br.com.joaovitor.repository.CardRepository;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPhysicalCardSuccessfully() {
        CreateCardRequestDTO requestDTO = new CreateCardRequestDTO();
        requestDTO.setAccountId(1L);

        Account account = new Account();
        when(accountService.findById(requestDTO.getAccountId())).thenReturn(account);

        Card card = new Card();
        Customer customer = mock(Customer.class);
        card.setAccount(account);
        card.getAccount().setCustomer(customer);
        doNothing().when(cardRepository).persist(any(Card.class));

        Card createdCard = cardService.createPhysicalCard(requestDTO);

        assertNotNull(createdCard);
        verify(cardRepository, times(1)).persist(any(Card.class));
    }

    @Test
    void createVirtualCardSuccessfully() {
        CreateCardRequestDTO requestDTO = new CreateCardRequestDTO();
        requestDTO.setAccountId(1L);

        Account account = new Account();
        when(accountService.findById(requestDTO.getAccountId())).thenReturn(account);

        when(cardRepository.findActivePhysicalCardByAccountId(account.getId())).thenReturn(Optional.of(new Card()));

        Card card = new Card();
        Customer customer = mock(Customer.class);
        card.setAccount(account);
        card.getAccount().setCustomer(customer);
        doNothing().when(cardRepository).persist(any(Card.class));

        Card createdCard = cardService.createVirtualCard(requestDTO);

        assertNotNull(createdCard);
        verify(cardRepository, times(1)).persist(any(Card.class));
    }

    @Test
    void createVirtualCardThrowsIllegalStateException() {
        CreateCardRequestDTO requestDTO = new CreateCardRequestDTO();
        requestDTO.setAccountId(1L);

        Account account = new Account();
        when(accountService.findById(requestDTO.getAccountId())).thenReturn(account);

        when(cardRepository.findActivePhysicalCardByAccountId(account.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            cardService.createVirtualCard(requestDTO);
        });
    }

    @Test
    void activateCardSuccessfully() {
        Long cardId = 1L;

        Card card = new Card();
        card.setStatus(CardStatus.PENDING);
        card.setType(CardType.PHYSICAL);
        card.setDeliveryStatus(DeliveryStatus.DELIVERED);
        when(cardRepository.findById(cardId)).thenReturn(card);

        Card activatedCard = cardService.activateCard(cardId);

        assertEquals(CardStatus.ACTIVE, activatedCard.getStatus());
        verify(cardRepository, times(1)).persist(card);
    }

    @Test
    void activateCardThrowsNotFoundException() {
        Long cardId = 1L;

        when(cardRepository.findById(cardId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            cardService.activateCard(cardId);
        });
    }

    @Test
    void blockCardSuccessfully() {
        Long cardId = 1L;

        Card card = new Card();
        card.setStatus(CardStatus.ACTIVE);
        when(cardRepository.findById(cardId)).thenReturn(card);

        Card blockedCard = cardService.blockCard(cardId);

        assertEquals(CardStatus.BLOCKED, blockedCard.getStatus());
        verify(cardRepository, times(1)).persist(card);
    }

    @Test
    void blockCardThrowsNotFoundException() {
        Long cardId = 1L;

        when(cardRepository.findById(cardId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            cardService.blockCard(cardId);
        });
    }

    @Test
    void shipCardSuccessfully() {
        Long cardId = 1L;

        Card card = new Card();
        card.setStatus(CardStatus.PENDING);
        when(cardRepository.findById(cardId)).thenReturn(card);

        Card shippedCard = cardService.shipCard(cardId);

        assertEquals(DeliveryStatus.SHIPPED, shippedCard.getDeliveryStatus());
        verify(cardRepository, times(1)).persist(card);
    }

    @Test
    void confirmCardReceiptSuccessfully() {
        Long cardId = 1L;

        Card card = new Card();
        card.setDeliveryStatus(DeliveryStatus.SHIPPED);
        when(cardRepository.findById(cardId)).thenReturn(card);

        Card deliveredCard = cardService.confirmCardReceipt(cardId);

        assertEquals(DeliveryStatus.DELIVERED, deliveredCard.getDeliveryStatus());
        verify(cardRepository, times(1)).persist(card);
    }

    @Test
    void unblockCardSuccessfully() {
        Long cardId = 1L;

        Card card = new Card();
        card.setStatus(CardStatus.BLOCKED);
        when(cardRepository.findById(cardId)).thenReturn(card);

        Card unblockedCard = cardService.unblockCard(cardId);

        assertEquals(CardStatus.ACTIVE, unblockedCard.getStatus());
        verify(cardRepository, times(1)).persist(card);
    }
}