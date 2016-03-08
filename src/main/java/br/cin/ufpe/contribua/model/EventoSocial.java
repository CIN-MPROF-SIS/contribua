package br.cin.ufpe.contribua.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class EventoSocial extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3623372317244989081L;
	
	@Column(length = 14, nullable = false)
	private String nome;

	@Column(nullable = false)
	private double latitude;

	@Column(nullable = false)
	private double longitude;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
