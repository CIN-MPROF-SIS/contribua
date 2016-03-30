package br.cin.ufpe.contribua.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class EventoSocial extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3623372317244989081L;

	@Column(length = 150, nullable = false)
	private String nome;
	
	@Column(length = 150, nullable = false)
	private String descricao; 
	
	@Column(length = 150)
	private String beneficiado; 
	
	@Column(length = 150)
	private String meta; 
	
	@Column(length = 150)
	private String caracteristicas; 
	
	@Temporal(javax.persistence.TemporalType.TIME)
	private Date horarioInicio;

	@Temporal(javax.persistence.TemporalType.TIME)
	private Date horarioTermino;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "causa_id")
	private Causa causa;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "publico_alvo_id")
	private PublicoAlvo publicoAlvo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mobilizador_id")
	private Usuario mobilizador;

        @Temporal(javax.persistence.TemporalType.DATE)
	private Date dataInicio;

        @Temporal(javax.persistence.TemporalType.DATE)
	private Date dataFim;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "eventoSocial_habilidade", joinColumns = {
			@JoinColumn(name = "eventoSocial_id") }, inverseJoinColumns = { @JoinColumn(name = "habilidade_id") })
	private List<Habilidade> habilidades;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "EventoSocial_qualificacao", joinColumns = {
			@JoinColumn(name = "eventoSocial_id") }, inverseJoinColumns = { @JoinColumn(name = "qualificacao_id") })
	private List<Qualificacao> qualificacoes;
	
	
	 public EventoSocial() {
	        this.habilidades = new ArrayList<Habilidade>();
	        this.qualificacoes = new ArrayList<Qualificacao>();
	    }

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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public List<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public List<Qualificacao> getQualificacoes() {
		return qualificacoes;
	}

	public void setQualificacoes(List<Qualificacao> qualificacoes) {
		this.qualificacoes = qualificacoes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getBeneficiado() {
		return beneficiado;
	}

	public void setBeneficiado(String beneficiado) {
		this.beneficiado = beneficiado;
	}

	public Date getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(Date horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public Date getHorarioTermino() {
		return horarioTermino;
	}

	public void setHorarioTermino(Date horarioTermino) {
		this.horarioTermino = horarioTermino;
	}
	
	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}
	
	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public Usuario getMobilizador() {
		return mobilizador;
	}

	public void setMobilizador(Usuario mobilizador) {
		this.mobilizador = mobilizador;
	}

}
