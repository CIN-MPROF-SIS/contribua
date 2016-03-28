package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.model.AbstractModel;
import br.cin.ufpe.contribua.util.Utils;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

@ManagedBean
@ViewScoped
public abstract class AbstractBean<Entidade extends AbstractModel> implements Serializable {
	   
    protected Entidade model;
    
    protected List<AbstractModel> listaModel;
    
    protected List<Entidade> entidadesSelecionadas;
    
    private Class<Entidade> clazzEntidade;
    
    protected boolean incluindo;
    
    public AbstractBean(){
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        //this.manager = (Class<Manager>) parameterizedType.getActualTypeArguments()[0];
        this.clazzEntidade = (Class<Entidade>) parameterizedType.getActualTypeArguments()[0];
    }
    
    @PostConstruct
    public void inicializar(){
        this.limpar();
        incluindo = false;
        this.listaModel= getManager().findAll();
    }
    
    public void limpar(){
        try {
            //implementação padrão do metodo limparDadosForm()
            this.model = clazzEntidade.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
	public String gravar() {
        try {
            if (this.model.getId() != null) {
                getManager().edit(this.model);
            } else {
                getManager().create(this.model);
            }
            Utils.adicionarMensagem("Operação Realizada Com Sucesso.", null, Utils.SUCESSO);
            this.limpar();
            return this.exibirLista();
        } catch (Exception e) {
            Utils.adicionarMensagem("Erro ao Cadastrar Qualificação." + e.getMessage(), null, Utils.FATAL);
            e.printStackTrace();
        }
        return null;
    }
    
    public void excluirSelecionados() {
        try {
            if(entidadesSelecionadas != null){
                for(Entidade entidade : entidadesSelecionadas)
                    this.getManager().remove(entidade);

                this.listaModel= getManager().findAll();

                Utils.adicionarMensagem("Operação Realizada Com Sucesso.", null, Utils.SUCESSO);
            }
        } catch (Exception e) {
            Utils.adicionarMensagem("Não é possível excluir registro pois ele está sendo utilizado.", null, Utils.FATAL);
            e.printStackTrace();
        }
    }
    
    public void excluir() {
        try {
            this.getManager().remove(this.model);

            this.listaModel= getManager().findAll();

            Utils.adicionarMensagem("Operação Realizada Com Sucesso.", null, Utils.SUCESSO);
        } catch (Exception e) {
            Utils.adicionarMensagem("Não é possível excluir registro pois ele está sendo utilizado.", null, Utils.FATAL);
            e.printStackTrace();
        }
    }
    
    public String exibirLista(){
        this.limpar();
        incluindo = false;
        this.listaModel= getManager().findAll();

        return getPaginaLista();
    }
    
    public String exibirInclusao(){
        this.limpar();
        incluindo = true;

        return getPaginaManutencao();
    }
    
    public String exibirAlteracao(){
        incluindo = false;
        //this.model = (Entidade) this.getManager().find(this.model.getId());

        return getPaginaManutencao();
    }
    
    public boolean isIncluindo() {
        return incluindo;
    }

    public void setIncluindo(boolean incluindo) {
        this.incluindo = incluindo;
    }
    
    public String getPaginaManutencao(){
        String pag = clazzEntidade.getSimpleName() + "Manter";
        return pag;
    }
    
    public String getPaginaLista(){
        String pag = clazzEntidade.getSimpleName() + "Listar";
        return pag;
    }
    
    public abstract AbstractManager getManager();
    
    public abstract String getTitulo();
    
    public List<Entidade> getEntidadesSelecionadas() {
        return entidadesSelecionadas;
    }

    public void setEntidadesSelecionadas(List<Entidade> entidadesSelecionadas) {
        this.entidadesSelecionadas = entidadesSelecionadas;
    }
    
    public Entidade getModel() {
        if(this.model == null){
            try {
                //implementação padrão do metodo limparDadosForm()
                this.model = clazzEntidade.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
        }
        return model;
    }
    
    public void setModel(Entidade model) {
        this.model = model;
    }

    public List<AbstractModel> getListaModel() {
        return listaModel;
    }

    public void setListaModel(List<AbstractModel> listaModel) {
        this.listaModel = listaModel;
    }
}
