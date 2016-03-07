package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.DiaSemanaManager;
import br.cin.ufpe.contribua.model.DiaSemana;

@ManagedBean
@ViewScoped
public class DiaSemanaBean extends AbstractBean<DiaSemana> {
	
    @EJB
    DiaSemanaManager diaSemanaManager;

    @Override
    public AbstractManager getManager() {
        return diaSemanaManager;
    }

    @Override
    public String getTitulo() {
        return "Dia Semana";
    }
}
