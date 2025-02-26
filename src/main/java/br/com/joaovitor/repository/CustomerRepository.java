package br.com.joaovitor.repository;

import br.com.joaovitor.model.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;

import java.util.Optional;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, Long> {

    @Inject
    EntityManager entityManager;

    public Optional<Customer> findByDocument(String document) {
        return entityManager.createQuery(
                        "SELECT c FROM Customer c WHERE c.document = :document", Customer.class)
                .setParameter("document", document)
                .getResultStream()
                .findFirst();
    }
}
