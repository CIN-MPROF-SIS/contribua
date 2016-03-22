package br.cin.ufpe.contribua.controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.cin.ufpe.contribua.manager.UsuarioManager;
import br.cin.ufpe.contribua.model.Usuario;
import br.cin.ufpe.contribua.util.Utils;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8399524016914970561L;
	private String logon;
	private String password;
	private boolean log;

	@EJB
	UsuarioManager usuarioManager;

	public LoginBean() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogon() {
		return logon;
	}

	public void setLogon(String logon) {
		this.logon = logon;
	}

	public boolean getLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public String doLogin() throws SQLException, ClassNotFoundException, IOException {

		if ((logon == null || logon.equals("")) && (password == null || password.equals(""))) {
			Utils.adicionarMensagem("Erro! O login e a senha não podem ser vazios!", null, Utils.ERROR);
		} else {
			Usuario usuario = usuarioManager.logar(this.logon, this.password);

			if (usuario != null) {
				System.out.println(usuario.getLogin());
				setLog(true);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("autenticado", true);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
				
				return "/pages/private/home.xhtml?faces-redirect=true";
			} else {
				setLog(false);
				Utils.adicionarMensagem(
						"Login ou senha errados! Verifique se você possui permissões para acessar o sistema", null,
						Utils.ERROR);
			}
		}
		return "index.xhtml?faces-redirect=true";
	}

	public String doLogout() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "index.xhtml?faces-redirect=true";
	}

}