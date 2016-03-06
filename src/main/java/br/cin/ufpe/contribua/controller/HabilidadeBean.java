package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.HabilidadeManager;
import br.cin.ufpe.contribua.model.Habilidade;

@ManagedBean
@ViewScoped
public class HabilidadeBean extends AbstractBean<Habilidade> {
	
    @EJB
    HabilidadeManager habilidadeManager;

    @Override
    public AbstractManager getManager() {
        return habilidadeManager;
    }

    @Override
    public String getTitulo() {
        return "Habilidade";
    }
}
