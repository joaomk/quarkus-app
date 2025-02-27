package br.com.joaovitor.service;

import br.com.joaovitor.dto.CvvWebhookDTO;
import br.com.joaovitor.dto.DeliveryWebhookDTO;
import br.com.joaovitor.enums.DeliveryStatus;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.model.Card;
import br.com.joaovitor.model.CvvWebhook;
import br.com.joaovitor.model.DeliveryWebhook;
import br.com.joaovitor.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class WebhookService {

    @Inject
    CardRepository cardRepository;

    @Inject
    AccountRepository accountRepository;

    @Inject
    DeliveryWebhookRepository deliveryWebhookRepository;

    @Inject
    CvvWebhookRepository cvvWebhookRepository;

    public void processDeliveryWebhook(DeliveryWebhookDTO deliveryWebhookDTO) {

        DeliveryWebhook log = new DeliveryWebhook();
        log.setTrackingId(deliveryWebhookDTO.getTrackingId());
        log.setDeliveryStatus(deliveryWebhookDTO.getDeliveryStatus());
        log.setDeliveryDate(deliveryWebhookDTO.getDeliveryDate());
        log.setDeliveryReturnReason(deliveryWebhookDTO.getDeliveryReturnReason());
        log.setDeliveryAddress(deliveryWebhookDTO.getDeliveryAddress());
        deliveryWebhookRepository.persist(log);

        if ("DELIVERED".equalsIgnoreCase(deliveryWebhookDTO.getDeliveryStatus())) {

            Card card = cardRepository.findByIdOptional(Long.parseLong(deliveryWebhookDTO.getTrackingId())).orElseThrow(() -> new NotFoundException("Card not found"));

            card.setDeliveryStatus(DeliveryStatus.DELIVERED);
            cardRepository.persist(card);
        }

    }

    public void processCvvWebhook(CvvWebhookDTO cvvWebhookDTO) {

        Card card = cardRepository.findByIdOptional(cvvWebhookDTO.getCardId())
                .orElseThrow(() -> new NotFoundException("Card not found"));

        Account account = accountRepository.findByIdOptional(cvvWebhookDTO.getAccountId())
                .orElseThrow(() -> new NotFoundException("Card not found"));

        CvvWebhook cvvWebhook = new CvvWebhook();
        cvvWebhook.setAccount(account);
        cvvWebhook.setCard(card);
        cvvWebhook.setNextCvv(cvvWebhookDTO.getNextCvv());
        cvvWebhook.setExpirationDate(cvvWebhookDTO.getExpirationDate());
        cvvWebhookRepository.persist(cvvWebhook);

        card.setCvv(cvvWebhookDTO.getNextCvv());
        card.setExpirationDate(cvvWebhookDTO.getExpirationDate());
        cardRepository.persist(card);
    }

}
