package br.cin.ufpe.contribua.model;

import javax.persistence.Column;

import javax.persistence.Entity;

@Entity(name = "dia_semana")
public class DiaSemana extends AbstractModel {
   
    @Column(length = 50, nullable=false)
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DiaSemana() {
    }
}
