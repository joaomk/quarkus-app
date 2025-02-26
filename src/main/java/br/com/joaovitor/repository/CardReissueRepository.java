package br.com.joaovitor.repository;

import br.com.joaovitor.model.CardReissue;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardReissueRepository implements PanacheRepositoryBase<CardReissue, Long> {
}
