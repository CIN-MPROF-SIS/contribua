package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.Qualificacao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class QualificacaoManager extends AbstractManager<Qualificacao> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QualificacaoManager() {
        super(Qualificacao.class);
    }
}
