package br.cin.ufpe.contribua.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity(name = "disponibilidade")
public class Disponibilidade extends AbstractModel {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DiaSemana diaSemana;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Voluntario voluntario;
   
    @Column(length = 5, nullable=false)
    private String horarioInicio;
    
    @Column(length = 5, nullable=false)
    private String horarioTermino;
    
    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }
    
    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
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
