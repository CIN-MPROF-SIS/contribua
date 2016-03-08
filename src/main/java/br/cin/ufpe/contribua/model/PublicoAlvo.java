package br.cin.ufpe.contribua.model;

import javax.persistence.Column;

import javax.persistence.Entity;

@Entity(name = "publico_alvo")
public class PublicoAlvo extends AbstractModel {
   
   	private static final long serialVersionUID = -7984246395515003274L;
	@Column(length = 100, nullable=false)
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PublicoAlvo() {
    }
}
