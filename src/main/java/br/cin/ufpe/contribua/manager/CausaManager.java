package br.cin.ufpe.contribua.manager;

import javax.ejb.Stateless;

import br.cin.ufpe.contribua.model.Causa;

@Stateless
public class CausaManager extends AbstractManager<Causa> {
    public CausaManager() {
        super(Causa.class);
    }
}
