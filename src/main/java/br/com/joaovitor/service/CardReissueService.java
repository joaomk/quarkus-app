package br.com.joaovitor.service;

import br.com.joaovitor.dto.CreateCardRequestDTO;
import br.com.joaovitor.enums.CardStatus;
import br.com.joaovitor.enums.CardType;
import br.com.joaovitor.model.Card;
import br.com.joaovitor.model.CardReissue;
import br.com.joaovitor.repository.CardRepository;
import br.com.joaovitor.repository.CardReissueRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;

@ApplicationScoped
public class CardReissueService {

    @Inject
    CardReissueRepository cardReissueRepository;

    @Inject
    CardRepository cardRepository;

    @Inject
    CardService cardService;

    public CardReissue requestReissue(Long cardId, String reason) {
        Card card = cardRepository.findById(cardId);
        if (card == null) {
            throw new NotFoundException("Card not found");
        }
        if (card.getType() == CardType.VIRTUAL) {
            throw new IllegalStateException("Virtual cards cannot be reissued, just cancel it and create a new one");
        }

        card.setStatus(CardStatus.CANCELLED);

        CreateCardRequestDTO createCardRequestDTO = new CreateCardRequestDTO();
        createCardRequestDTO.setAccountId(card.getAccount().getId());

        Card newCard =  cardService.createPhysicalCard(createCardRequestDTO);

        cardService.shipCard(newCard.getId());

        CardReissue reissueRequest = new CardReissue();
        reissueRequest.setCard(newCard);
        reissueRequest.setReason(reason);
        reissueRequest.setRequestDate(LocalDateTime.now());
        cardReissueRepository.persist(reissueRequest);

        return reissueRequest;
    }
}