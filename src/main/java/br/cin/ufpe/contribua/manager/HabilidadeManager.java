package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.Habilidade;
import javax.ejb.Stateless;

@Stateless
public class HabilidadeManager extends AbstractManager<Habilidade> {

    public HabilidadeManager() {
        super(Habilidade.class);
    }
}
