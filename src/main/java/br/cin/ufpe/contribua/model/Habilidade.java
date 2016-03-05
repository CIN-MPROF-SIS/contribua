package br.cin.ufpe.contribua.model;

import javax.persistence.Column;

import javax.persistence.Entity;

@Entity(name = "habilidade")
public class Habilidade extends AbstractModel {
   
    @Column(length = 100, nullable=false)
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Habilidade() {
    }
}
