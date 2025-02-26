package br.com.joaovitor.service;

import br.com.joaovitor.dto.CreateCardRequestDTO;
import br.com.joaovitor.enums.CardStatus;
import br.com.joaovitor.enums.CardType;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.model.Card;
import br.com.joaovitor.model.CardReissue;
import br.com.joaovitor.repository.CardReissueRepository;
import br.com.joaovitor.repository.CardRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class CardReissueServiceTest {

    @InjectMocks
    CardReissueService cardReissueService;

    @Mock
    CardRepository cardRepository;

    @Mock
    CardReissueRepository cardReissueRepository;

    @Mock
    CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void requestReissueSuccessfully() {
        Long cardId = 1L;
        String reason = "Lost card";

        Card card = mock(Card.class);
        Account account = mock(Account.class);

        when(cardRepository.findById(cardId)).thenReturn(card);
        when(card.getType()).thenReturn(CardType.PHYSICAL);
        when(card.getStatus()).thenReturn(CardStatus.ACTIVE);
        when(card.getAccount()).thenReturn(account);
        when(account.getId()).thenReturn(1L);
        when(cardService.createPhysicalCard(any(CreateCardRequestDTO.class))).thenReturn(new Card());

        CardReissue reissueRequest = cardReissueService.requestReissue(cardId, reason);

        assertNotNull(reissueRequest);
        assertEquals(reason, reissueRequest.getReason());
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardService, times(1)).createPhysicalCard(any(CreateCardRequestDTO.class));
        verify(cardReissueRepository, times(1)).persist(any(CardReissue.class));
    }

    @Test
    void requestReissueCardNotFound() {
        Long cardId = 1L;
        String reason = "Lost card";

        when(cardRepository.findById(cardId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> cardReissueService.requestReissue(cardId, reason));
        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    void requestReissueVirtualCard() {
        Long cardId = 1L;
        String reason = "Lost card";
        Card card = new Card();
        card.setId(cardId);
        card.setType(CardType.VIRTUAL);

        when(cardRepository.findById(cardId)).thenReturn(card);

        assertThrows(IllegalStateException.class, () -> cardReissueService.requestReissue(cardId, reason));
        verify(cardRepository, times(1)).findById(cardId);
    }
}
