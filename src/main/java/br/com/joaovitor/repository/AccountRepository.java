package br.com.joaovitor.repository;

import br.com.joaovitor.enums.AccountStatus;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.model.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.Optional;

@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<Account, Long> {

    @Inject
    EntityManager entityManager;

    public Account findByDocument(String document) {
        Optional<Account> optionalAccount = entityManager.createQuery(
                        "SELECT a FROM Account a JOIN a.customer c WHERE c.document = :document", Account.class)
                .setParameter("document", document)
                .getResultStream()
                .findFirst();
        return optionalAccount.orElseThrow(() -> new NotFoundException("Account not found"));
    }


    @Transactional
    public Account deactivateAccount(String document) {
        Account account = findByDocument(document);
        if (account == null || account.getStatus() == AccountStatus.INACTIVE) {
            throw new BadRequestException("Account not found or already deactivated");
        }
        account.setStatus(AccountStatus.INACTIVE);
        return entityManager.merge(account);
    }


    public boolean existsByCustomer(Customer customer) {
        return entityManager.createQuery(
                        "SELECT COUNT(a) FROM Account a WHERE a.customer = :customer", Long.class)
                .setParameter("customer", customer)
                .getSingleResult() > 0;
    }
}
