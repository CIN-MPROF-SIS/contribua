package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.EventoSocial;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EventoSocialManager extends AbstractManager<EventoSocial> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoSocialManager() {
        super(EventoSocial.class);
    }
}
