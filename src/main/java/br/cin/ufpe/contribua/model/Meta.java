package br.cin.ufpe.contribua.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Meta extends AbstractModel {

	
	private static final long serialVersionUID = -161773377612857664L;
	
	@Column(length = 100, nullable=false)
    private String descricao;
	
	private Integer quantidadeNecessaria;
	
	private Integer quantidadeObtida;
	
	@ManyToOne
	@JoinColumn(name = "evento_id")
	private EventoSocial evento;	
	
    public Integer getQuantidadeNecessaria() {
		return quantidadeNecessaria;
	}

	public void setQuantidadeNecessaria(Integer quantidadeNecessaria) {
		this.quantidadeNecessaria = quantidadeNecessaria;
	}

	public Integer getQuantidadeObtida() {
		return quantidadeObtida;
	}

	public void setQuantidadeObtida(Integer quantidadeObtida) {
		this.quantidadeObtida = quantidadeObtida;
	}

	public String getDescricao() {
		return descricao;
	}

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public EventoSocial getEvento() {
		return evento;
	}

	public void setEvento(EventoSocial evento) {
		this.evento = evento;
	}

	

}
