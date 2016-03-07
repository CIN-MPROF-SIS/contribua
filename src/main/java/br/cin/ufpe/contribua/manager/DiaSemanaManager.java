package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.DiaSemana;
import javax.ejb.Stateless;

@Stateless
public class DiaSemanaManager extends AbstractManager<DiaSemana> {
    public DiaSemanaManager() {
        super(DiaSemana.class);
    }
}
