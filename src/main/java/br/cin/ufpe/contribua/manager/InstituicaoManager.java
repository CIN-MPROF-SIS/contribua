package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.Instituicao;
import javax.ejb.Stateless;

@Stateless
public class InstituicaoManager extends AbstractManager<Instituicao> {
    public InstituicaoManager() {
        super(Instituicao.class);
    }

}
