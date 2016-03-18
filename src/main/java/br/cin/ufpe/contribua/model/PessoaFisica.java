package br.cin.ufpe.contribua.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "pessoa_fisica")
public class PessoaFisica extends AbstractModel {
    
    @Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;
    
    @Column(length = 1, nullable = false)
    private String sexo;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PessoaFisica indicador;
    
    @OneToOne(fetch = FetchType.EAGER)
    private Pessoa pessoa;
    
    @OneToMany(mappedBy = "pessoaFisica", fetch = FetchType.EAGER)
    private List<Disponibilidade> disponibilidades;
   
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="pessoa_fisica_habilidade", joinColumns={@JoinColumn(name="pessoa_fisica_id")}, inverseJoinColumns={@JoinColumn(name="habilidade_id")})
    private List<Habilidade> habilidades;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="pessoa_fisica_qualificacao", joinColumns={@JoinColumn(name="pessoa_fisica_id")}, inverseJoinColumns={@JoinColumn(name="qualificacao_id")})
    private List<Qualificacao> qualificacoes;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="pessoa_fisica_causa", joinColumns={@JoinColumn(name="pessoa_fisica_id")}, inverseJoinColumns={@JoinColumn(name="qualificacao_id")})
    private List<Causa> causas;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="pessoa_fisica_publico", joinColumns={@JoinColumn(name="pessoa_fisica_id")}, inverseJoinColumns={@JoinColumn(name="qualificacao_id")})
    private List<PublicoAlvo> publicos;
    
    public PessoaFisica() {
        this.disponibilidades = new ArrayList<Disponibilidade>();
        this.habilidades = new ArrayList<Habilidade>();
        this.qualificacoes = new ArrayList<Qualificacao>();
        this.pessoa = new Pessoa();
    }


    public PessoaFisica getIndicador() {
        return indicador;
    }

    public void setIndicador(PessoaFisica indicador) {
        this.indicador = indicador;
    }
    
    public List<Disponibilidade> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidade> horariosDisponiveis) {
        this.disponibilidades = horariosDisponiveis;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Causa> getCausas() {
        return causas;
    }

    public void setCausas(List<Causa> causas) {
        this.causas = causas;
    }

    public List<PublicoAlvo> getPublicos() {
        return publicos;
    }

    public void setPublicos(List<PublicoAlvo> publicos) {
        this.publicos = publicos;
    }
    
    
}
