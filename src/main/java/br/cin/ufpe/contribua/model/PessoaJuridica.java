package br.cin.ufpe.contribua.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "pessoa_juridica")
public class PessoaJuridica extends AbstractModel {
    
    @Column(length = 20, nullable=true)
    private String cnpj;
    
    @Column(length = 100, nullable=false)
    private String pagina;
    
    @Column(length = 1000, nullable=false)
    private String descricao;
    
    @Column(length = 10, nullable=true)
    private String sigla;
    
    @Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFundacao;
    
    @Column(length = 500, nullable=false)
    private String diretoria;
    
    @Column(nullable=false)
    private boolean tituloMunicipal;
    
    @Column(nullable=false)
    private boolean tituloEstadual;
    
    @Column(nullable=false)
    private boolean tituloFederal;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Pessoa pessoa;
    

    public PessoaJuridica() {
        this.pessoa = new Pessoa();
    }
    
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Date getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(Date dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public String getDiretoria() {
        return diretoria;
    }

    public void setDiretoria(String diretoria) {
        this.diretoria = diretoria;
    }
    
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public boolean isTituloMunicipal() {
        return tituloMunicipal;
    }

    public void setTituloMunicipal(boolean tituloMunicipal) {
        this.tituloMunicipal = tituloMunicipal;
    }

    public boolean isTituloEstadual() {
        return tituloEstadual;
    }

    public void setTituloEstadual(boolean tituloEstadual) {
        this.tituloEstadual = tituloEstadual;
    }

    public boolean isTituloFederal() {
        return tituloFederal;
    }

    public void setTituloFederal(boolean tituloFederal) {
        this.tituloFederal = tituloFederal;
    }
    
}
