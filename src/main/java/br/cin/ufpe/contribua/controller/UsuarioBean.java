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
    
    public String cadastrarVoluntario(){    	
    	
    	return "/pages/public/pessoaFisica/usuario.xhtml?faces-redirect=true";
    }
    
    public String cadastrarOrganizacao(){
    	
    	
    	
    	return "pages/public/pessoaJuridica/usuario.xhtml?faces-redirect=true";
    }
    
    @Override
    public String gravar() {
        if(!this.model.getSenha().equals(this.confirmacaoSenha)){
            Utils.adicionarMensagem("Senhas não conferem.", null, Utils.FATAL);
            return null;
        }
        
        try {
            if (this.model.getId() != null) {
                getManager().edit(this.model);
            } else {
                getManager().create(this.model);
            }
            Utils.adicionarMensagem("Operação Realizada Com Sucesso.", null, Utils.SUCESSO);
            this.limpar();
           return "Home";
        } catch (Exception e) {
            Utils.adicionarMensagem("Erro ao Cadastrar Qualificação." + e.getMessage(), null, Utils.FATAL);
            e.printStackTrace();
        }
        
        
        
        return "Home";
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }
    
    
}
