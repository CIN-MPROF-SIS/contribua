package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.UsuarioManager;
import br.cin.ufpe.contribua.model.Pessoa;
import br.cin.ufpe.contribua.model.Usuario;
import br.cin.ufpe.contribua.util.Utils;
import java.util.List;

@ManagedBean
@ViewScoped
public class UsuarioBean extends AbstractBean<Usuario> {
	
    @EJB
    UsuarioManager usuarioManager;
    
    private String confirmacaoSenha;

    @Override
    public AbstractManager getManager() {
        return usuarioManager;
    }

    @Override
    public String getTitulo() {
        return "Usuário";
    }
    
    public List<Pessoa> listarUsuarios(String nome) {
        List<Pessoa> usuarios = usuarioManager.findPessoa(nome);
       
        return usuarios;
    }
    
    @Override
    public String gravar() {
        if(!this.model.getSenha().equals(this.confirmacaoSenha)){
            Utils.adicionarMensagem("Senhas não conferem.", null, Utils.FATAL);
            return null;
        }
        return super.gravar();
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }
    
    
}
