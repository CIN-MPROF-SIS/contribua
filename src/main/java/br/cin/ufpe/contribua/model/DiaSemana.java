package br.cin.ufpe.contribua.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity(name = "dia_semana")
@NamedQuery(name = "DiaSemana.findAllOrder", query = "SELECT d FROM  dia_semana d order by d.ordem")
public class DiaSemana extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1225327150793190491L;

	private Integer ordem;
   
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

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
}
