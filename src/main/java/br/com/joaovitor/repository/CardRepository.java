package br.com.joaovitor.repository;

import br.com.joaovitor.enums.CardStatus;
import br.com.joaovitor.enums.CardType;
import br.com.joaovitor.model.Card;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CardRepository implements PanacheRepositoryBase<Card, Long> {

    @Inject
    EntityManager entityManager;

    public Optional<Card> findActivePhysicalCardByAccountId(Long accountId) {
        return entityManager.createQuery(
                        "SELECT c FROM Card c WHERE c.account.id = :accountId AND c.type = :type AND c.status = :status", Card.class)
                .setParameter("accountId", accountId)
                .setParameter("type", CardType.PHYSICAL)
                .setParameter("status", CardStatus.ACTIVE)
                .getResultStream()
                .findFirst();
    }

    public List<Card> findCardsByAccountId(Long accountId) {
        return entityManager.createQuery(
                        "SELECT c FROM Card c WHERE c.account.id = :accountId", Card.class)
                .setParameter("accountId", accountId)
                .getResultList();
    }

    public void persist(Card card) {
        entityManager.persist(card);
    }

    public Card findById(Long cardId) {
        return entityManager.find(Card.class, cardId);
    }

}

