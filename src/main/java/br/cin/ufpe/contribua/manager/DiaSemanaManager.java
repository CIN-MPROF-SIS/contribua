package br.cin.ufpe.contribua.manager;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import br.cin.ufpe.contribua.model.DiaSemana;

@Stateless
public class DiaSemanaManager extends AbstractManager<DiaSemana> {
	public DiaSemanaManager() {
		super(DiaSemana.class);
	}

	@Override
	public List<DiaSemana> findAll() {

		return findWithNamedQuery("DiaSemana.findAllOrder", new HashMap(), 0);
	}

}
