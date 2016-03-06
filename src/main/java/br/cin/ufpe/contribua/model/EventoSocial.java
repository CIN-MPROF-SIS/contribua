package br.cin.ufpe.contribua.model;

import javax.persistence.Entity;

@Entity
public class EventoSocial extends AbstractModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3623372317244989081L;
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
