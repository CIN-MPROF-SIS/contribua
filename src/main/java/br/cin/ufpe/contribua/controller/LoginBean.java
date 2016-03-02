package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.util.Utils;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private String logon;
    private String password;
    private boolean log;

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
            if (logon.equals("123")) {
                setLog(true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("autenticado", true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", logon);
                return "instituicao?faces-redirect=true";
            } else {
                setLog(false);
                Utils.adicionarMensagem("Login ou senha errados! Verifique se você possui permissões para acessar o sistema", null, Utils.ERROR);
            }
        }
        return "index";
    }
}