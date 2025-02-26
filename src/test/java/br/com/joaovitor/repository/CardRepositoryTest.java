package br.com.joaovitor.repository;

import br.com.joaovitor.enums.AccountStatus;
import br.com.joaovitor.enums.CardStatus;
import br.com.joaovitor.enums.CardType;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.model.Address;
import br.com.joaovitor.model.Card;
import br.com.joaovitor.model.Customer;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CardRepositoryTest {

    @Inject
    CardRepository cardRepository;

    @Inject
    EntityManager entityManager;

    @Test
    @Transactional
    void findActivePhysicalCardByAccountIdSuccessfully() {
        Customer customer = createTestCustomer();
        entityManager.persist(customer);

        Account account = createTestAccount(customer);
        entityManager.persist(account);

        Card card = createTestCard(account, CardType.PHYSICAL, CardStatus.ACTIVE);
        entityManager.persist(card);
        entityManager.flush();

        Optional<Card> foundCard = cardRepository.findActivePhysicalCardByAccountId(account.getId());

        assertTrue(foundCard.isPresent());
        assertEquals(card.getId(), foundCard.get().getId());
    }


    @Test
    @Transactional
    void findCardsByAccountIdSuccessfully() {
        Customer customer = createTestCustomer();
        entityManager.persist(customer);
        Account account = createTestAccount(customer);
        entityManager.persist(account);
        entityManager.flush();

        Card card1 = createTestCard(account, CardType.PHYSICAL, CardStatus.ACTIVE);
        Card card2 = createTestCard(account, CardType.VIRTUAL, CardStatus.PENDING);
        entityManager.persist(card1);
        entityManager.persist(card2);
        entityManager.flush();

        List<Card> cards = cardRepository.findCardsByAccountId(account.getId());

        assertEquals(2, cards.size());
        assertTrue(cards.contains(card1));
        assertTrue(cards.contains(card2));
    }

    @Test
    @Transactional
    void persistCardSuccessfully() {
        Customer customer = createTestCustomer();
        entityManager.persist(customer);

        Account account = createTestAccount(customer);
        entityManager.persist(account);

        Card card = createTestCard(account, CardType.PHYSICAL, CardStatus.PENDING);
        cardRepository.persist(card);
        entityManager.flush();

        Card foundCard = entityManager.find(Card.class, card.getId());
        assertNotNull(foundCard);
        assertEquals(card.getId(), foundCard.getId());
    }

    @Test
    @Transactional
    void findByIdSuccessfully() {
        Customer customer = createTestCustomer();
        Address address = createTestAddress();
        customer.setAddress(address);
        Account account = createTestAccount(customer);
        Card card = createTestCard(account, CardType.PHYSICAL, CardStatus.ACTIVE);

        entityManager.persist(address);
        entityManager.persist(customer);
        entityManager.persist(account);
        entityManager.persist(card);
        entityManager.flush();

        Card foundCard = cardRepository.findById(card.getId());

        assertNotNull(foundCard);
        assertEquals(card.getId(), foundCard.getId());
        assertEquals(account.getId(), foundCard.getAccount().getId());
    }

    private Card createTestCard(Account account, CardType type, CardStatus status) {
        Card card = new Card();
        card.setAccount(account);
        card.setType(type);
        card.setStatus(status);
        card.setCardHolder("John Doe");
        card.setCvv(generateCVV());
        card.setNumber(generateCardNumber());
        card.setExpirationDate(LocalDate.now().plusYears(3));
        return card;
    }

    private Customer createTestCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setDocument(generateDocument());
        customer.setEmail("johndoe@example.com");
        customer.setPhone("11999999999");
        return customer;
    }

    private Address createTestAddress() {
        Address address = new Address();
        address.setStreet("Test Street");
        address.setCity("Test City");
        address.setZipCode("12345678");
        address.setState("TS");
        address.setCountry("Test Country");
        return address;
    }

    private Account createTestAccount(Customer customer) {
        Account account = new Account(customer);
        account.setStatus(AccountStatus.ACTIVE);
        return account;
    }

    private String generateCardNumber() {
        return String.valueOf((long) (Math.random() * 1_0000_0000_0000_0000L));
    }

    private String generateCVV() {
        return String.valueOf((int) (Math.random() * 900) + 100);
    }

    private String generateDocument() {
        return String.valueOf((long) (Math.random() * 10000000000L));
    }
}