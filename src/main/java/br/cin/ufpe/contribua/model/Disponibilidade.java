package br.cin.ufpe.contribua.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity(name = "disponibilidade")
public class Disponibilidade extends AbstractModel {

	private static final long serialVersionUID = 6742096581425384640L;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private DiaSemana diaSemana;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private PessoaFisica pessoaFisica;

	@Column(length = 5, nullable = false)
	private String horarioInicio;

	@Column(length = 5, nullable = false)
	private String horarioTermino;

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

        public PessoaFisica getPessoaFisica() {
            return pessoaFisica;
        }

        public void setPessoaFisica(PessoaFisica pessoaFisica) {
            this.pessoaFisica = pessoaFisica;
        }

	public String getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(String horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public String getHorarioTermino() {
		return horarioTermino;
	}

	public void setHorarioTermino(String horarioTermino) {
		this.horarioTermino = horarioTermino;
	}

	public Disponibilidade() {
	}
}
