package br.com.joaovitor.repository;

import br.com.joaovitor.model.CvvWebhook;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CvvWebhookRepository implements PanacheRepositoryBase<CvvWebhook, Long> {
}
