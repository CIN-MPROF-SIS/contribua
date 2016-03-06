package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.Voluntario;
import javax.ejb.Stateless;

@Stateless
public class VoluntarioManager extends AbstractManager<Voluntario> {
    public VoluntarioManager() {
        super(Voluntario.class);
    }
}
