package br.com.joaovitor.repository;

import br.com.joaovitor.model.DeliveryWebhook;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeliveryWebhookRepository implements PanacheRepositoryBase<DeliveryWebhook, Long> {

}
