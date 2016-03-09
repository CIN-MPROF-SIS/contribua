package br.cin.ufpe.contribua.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EventoSocial extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3623372317244989081L;

	@Column(length = 14, nullable = false)
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "causa_id")
	private Causa causa;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "publico_alvo_id")
	private PublicoAlvo publicoAlvo;

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

	public Causa getCausa() {
		return causa;
	}

	public void setCausa(Causa causa) {
		this.causa = causa;
	}

	public PublicoAlvo getPublicoAlvo() {
		return publicoAlvo;
	}

	public void setPublicoAlvo(PublicoAlvo publicoAlvo) {
		this.publicoAlvo = publicoAlvo;
	}
	
	
	

}
