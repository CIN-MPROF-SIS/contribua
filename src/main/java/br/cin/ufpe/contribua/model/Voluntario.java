package br.cin.ufpe.contribua.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "voluntario")
public class Voluntario extends AbstractModel {
    
    @Column(length = 100, nullable=false)
    private String nome;
    
    @Column(length = 100, nullable=false)
    private String email;
    
    @Column(length = 14, nullable=false)
    private String telefone;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Voluntario indicador;
    
    @OneToMany(mappedBy = "voluntario", fetch = FetchType.LAZY)
    private List<HorarioDisponivel> horariosDisponiveis;
   
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="voluntario_habilidade", joinColumns={@JoinColumn(name="voluntario_id")}, inverseJoinColumns={@JoinColumn(name="habilidade_id")})
    private List<Habilidade> habilidades;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="voluntario_qualificacao", joinColumns={@JoinColumn(name="voluntario_id")}, inverseJoinColumns={@JoinColumn(name="qualificacao_id")})
    private List<Qualificacao> qualificacoes;
    
    public Voluntario() {
        this.horariosDisponiveis = new ArrayList<HorarioDisponivel>();
        this.habilidades = new ArrayList<Habilidade>();
        this.qualificacoes = new ArrayList<Qualificacao>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

        public Voluntario getIndicador() {
        return indicador;
    }

    public void setIndicador(Voluntario indicador) {
        this.indicador = indicador;
    }
    
    public List<HorarioDisponivel> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    public void setHorariosDisponiveis(List<HorarioDisponivel> horariosDisponiveis) {
        this.horariosDisponiveis = horariosDisponiveis;
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

}
