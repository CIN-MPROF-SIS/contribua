package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.Estado;
import javax.ejb.Stateless;

@Stateless
public class EstadoManager extends AbstractManager<Estado> {

    public EstadoManager() {
        super(Estado.class);
    }
}
