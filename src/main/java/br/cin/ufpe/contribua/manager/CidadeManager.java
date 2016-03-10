package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.Cidade;
import br.cin.ufpe.contribua.model.Estado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class CidadeManager extends AbstractManager<Cidade> {

    public CidadeManager() {
        super(Cidade.class);
    }
    
    public List<Cidade> findByEstado(Estado estado){
        String sql = "SELECT c "
                + "FROM Cidade c "
                + "WHERE c.estado = :estado "
                + "ORDER BY c.nome ";
        
        Query query = this.getEntityManager().createQuery(sql);
        query.setParameter("estado", estado);
        
        return query.getResultList();
    }
}
