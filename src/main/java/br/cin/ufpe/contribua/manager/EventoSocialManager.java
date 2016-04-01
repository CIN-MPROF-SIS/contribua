package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.EventoSocial;
import br.cin.ufpe.contribua.model.Usuario;
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
                + "WHERE :data <= es.dataFim "
                + "ORDER BY es.dataInicio ";
        
        Query query = this.getEntityManager().createQuery(sql);
        query.setParameter("data", new Date());
        
        return query.getResultList();
    }
    
    public List<EventoSocial> findByUsuario(Usuario usuario){
        String sql = "SELECT es "
                + "FROM EventoSocial es "
                + "WHERE mobilizador = :usuario "
                + "ORDER BY es.dataInicio ";
        
        Query query = this.getEntityManager().createQuery(sql);
        query.setParameter("usuario", usuario);
        
        return query.getResultList();
    }
}
