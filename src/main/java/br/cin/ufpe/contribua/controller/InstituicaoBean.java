package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.InstituicaoManager;
import br.cin.ufpe.contribua.model.Instituicao;

@ManagedBean
@ViewScoped
public class InstituicaoBean extends AbstractBean<Instituicao> {

    @EJB
    InstituicaoManager instituicaoManager;

    @Override
    public AbstractManager getManager() {
        return instituicaoManager;
    }

    @Override
    public String getTitulo() {
        return "Instituição";
    }
}
