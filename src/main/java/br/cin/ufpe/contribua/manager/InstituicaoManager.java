package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.Instituicao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InstituicaoManager extends AbstractManager<Instituicao> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstituicaoManager() {
        super(Instituicao.class);
    }
}
