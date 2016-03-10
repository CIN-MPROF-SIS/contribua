package br.cin.ufpe.contribua.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity(name = "usuario")
public class Usuario extends AbstractModel {
    
    @Column(length = 50, nullable=false)
    private String login;
    
    @Column(length = 500, nullable=false)
    private String senha;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Pessoa pessoa;

    public Usuario() {
        this.pessoa = new Pessoa();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
}
