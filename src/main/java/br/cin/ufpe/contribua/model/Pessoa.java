package br.cin.ufpe.contribua.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "pessoa")
public class Pessoa extends AbstractModel  implements Serializable {

    @Column(length = 100, nullable=false)
    private String nome;
    
    @Column(length = 100, nullable=false)
    private String email;
    
    @Column(length = 14, nullable=false)
    private String telefone;
    
    @Column(nullable=false)
    private double latitude;
    
    @Column(nullable=false)
    private double longitude;
    
    @Column(length = 100, nullable=false)
    private String endereco;
    
    @Column(length = 9, nullable=false)
    private String cep;
    
    @Column(nullable=false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataCadastro;
    
    @Column(nullable=false)
    private boolean aceitaReceberNoticias;
    
    @Lob
    @Column
    private byte[] imagem;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Cidade cidade;


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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAceitaReceberNoticias() {
        return aceitaReceberNoticias;
    }

    public void setAceitaReceberNoticias(boolean aceitaReceberNoticias) {
        this.aceitaReceberNoticias = aceitaReceberNoticias;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    

   
}
