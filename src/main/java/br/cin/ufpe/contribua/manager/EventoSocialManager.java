package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.EventoSocial;
import br.cin.ufpe.contribua.model.Pessoa;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    public List<EventoSocial> findAbertos(){
        String sql = "SELECT es "
                + "FROM EventoSocial es "
                + "WHERE :data BETWEEN es.dataInicio AND es.dataFim "
                + "ORDER BY es.dataInicio ";
        
        Query query = this.getEntityManager().createQuery(sql);
        query.setParameter("data", new Date());
        
        return query.getResultList();
    }
}
