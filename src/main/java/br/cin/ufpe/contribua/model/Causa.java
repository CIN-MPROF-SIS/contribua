package br.cin.ufpe.contribua.model;

import javax.persistence.Column;

import javax.persistence.Entity;

@Entity(name = "causa")
public class Causa extends AbstractModel {
   
   	private static final long serialVersionUID = -7984246395515003274L;
	@Column(length = 100, nullable=false)
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Causa() {
    }
}
