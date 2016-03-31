package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.EventoSocial;
import br.cin.ufpe.contribua.model.Participacao;
import br.cin.ufpe.contribua.model.Usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Stateless
public class ParticipacaoManager extends AbstractManager<Participacao> {

    public ParticipacaoManager() {
        super(Participacao.class);
    }
    
    public Participacao findByUsuarioEvento(EventoSocial eventoSocial, Usuario usuario){
        try {
            String sql = "SELECT p "
                    + "FROM Participacao p "
                    + "WHERE p.eventoSocial = :eventoSocial "
                    + " AND p.usuario = :usuario ";

            Query query = this.getEntityManager().createQuery(sql);
            query.setParameter("eventoSocial", eventoSocial);
            query.setParameter("usuario", usuario);

            return (Participacao)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
    
    public List<Participacao> findByEvento(EventoSocial eventoSocial){
        try {
            String sql = "SELECT p "
                    + "FROM Participacao p "
                    + "WHERE p.eventoSocial = :eventoSocial";

            Query query = this.getEntityManager().createQuery(sql);
            query.setParameter("eventoSocial", eventoSocial);
   
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }
}
