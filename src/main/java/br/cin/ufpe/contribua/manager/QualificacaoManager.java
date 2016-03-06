package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.Qualificacao;
import javax.ejb.Stateless;

@Stateless
public class QualificacaoManager extends AbstractManager<Qualificacao> {
    public QualificacaoManager() {
        super(Qualificacao.class);
    }
}
