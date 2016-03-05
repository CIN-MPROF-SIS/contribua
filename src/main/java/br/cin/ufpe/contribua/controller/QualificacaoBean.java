package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.QualificacaoManager;
import br.cin.ufpe.contribua.model.Qualificacao;

@ManagedBean
@ViewScoped
public class QualificacaoBean extends AbstractBean<Qualificacao> {
	
    @EJB
    QualificacaoManager qualificacaoManager;

    @Override
    public AbstractManager getManager() {
        return qualificacaoManager;
    }

    @Override
    public String getTitulo() {
        return "Qualificação";
    }
}
