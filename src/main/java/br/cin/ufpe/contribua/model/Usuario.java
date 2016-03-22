package br.cin.ufpe.contribua.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity(name = "usuario")
@NamedQuery(name = "Usuario.logar", query = "SELECT u FROM usuario u  WHERE u.login =:login and u.senha =:senha")
public class Usuario extends AbstractModel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5870803304069447561L;

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
