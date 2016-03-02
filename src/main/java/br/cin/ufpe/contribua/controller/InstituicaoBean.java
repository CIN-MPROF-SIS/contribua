package br.cin.ufpe.contribua.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.InstituicaoManager;
import br.cin.ufpe.contribua.model.Instituicao;
import br.cin.ufpe.contribua.util.Utils;

@ManagedBean
@ViewScoped
public class InstituicaoBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2972615100151727488L;
	
	@EJB
    InstituicaoManager instituicaoManager;
    private Instituicao instituicao = new Instituicao();

    public void gravar() {
        try {
            if (instituicao.getId() != null) {
                instituicaoManager.edit(this.instituicao);
            } else {
                instituicaoManager.create(this.instituicao);
            }
            Utils.adicionarMensagem("Operação Realizada Com Sucesso.", null, Utils.SUCESSO);
        } catch (Exception e) {
            Utils.adicionarMensagem("Erro ao Cadastrar Instituição.", null, Utils.FATAL);
            e.printStackTrace();
        }
        this.instituicao = new Instituicao();
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
}
