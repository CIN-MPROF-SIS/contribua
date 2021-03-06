package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;
import br.cin.ufpe.contribua.manager.CidadeManager;
import br.cin.ufpe.contribua.manager.DiaSemanaManager;
import br.cin.ufpe.contribua.manager.EstadoManager;
import br.cin.ufpe.contribua.manager.PessoaJuridicaManager;
import br.cin.ufpe.contribua.model.Cidade;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.model.Estado;
import br.cin.ufpe.contribua.model.PessoaJuridica;
import br.cin.ufpe.contribua.model.Usuario;
import br.cin.ufpe.contribua.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean
@ViewScoped
public class PessoaJuridicaBean extends AbstractBean<PessoaJuridica> {

    @EJB
    PessoaJuridicaManager pessoaJuridicaManager;
    
    @EJB
    DiaSemanaManager diaSemanaManager;
    
    @EJB
    EstadoManager estadoManager;
    
    @EJB
    CidadeManager cidadeManager;
    
    private Usuario usuario;
    
    private String confirmacaoSenha;
    
    private MapModel geoModel;
    private String centerGeoMap = "41.850033, -87.6500523";
    
    private List<Estado> estados;
    private Estado estado;
    
    private List<Cidade> cidades;
    
    private UploadedFile imagem;
    
    private ArrayList<String> exts;
    
    public PessoaJuridicaBean(){
        exts = new ArrayList<String>();
        exts.add("png");
        exts.add("jpg");
        exts.add("gif");
        exts.add("jpeg");
    }
    
    @PostConstruct
    public void inicializar(){
        super.inicializar();
        this.usuario = new Usuario();
        this.model = new PessoaJuridica();
        
        geoModel = new DefaultMapModel();
        
        this.estados = estadoManager.findAll();
        this.estado = null;
        this.cidades = new ArrayList<Cidade>();
        this.confirmacaoSenha = "";
        
    }

    @Override
    public String exibirInclusao(){
        String retorno = super.exibirInclusao();
        geoModel = new DefaultMapModel();
        
        this.estados = estadoManager.findAll();
        this.estado = null;
        this.cidades = new ArrayList<Cidade>();
        
        return retorno;
    }
    
    @Override
    public String exibirAlteracao(){
        String retorno = super.exibirAlteracao();
        
        this.estados = estadoManager.findAll();
        this.estado = this.model.getPessoa().getCidade().getEstado();
        
        this.selecionarEstado();
        
        geoModel = new DefaultMapModel();
        adicionarMarcador();
        centerGeoMap = this.model.getPessoa().getLatitude() + ", " + this.model.getPessoa().getLongitude();
        
        return retorno;
    }
    
    @Override
    public AbstractManager getManager() {
        return pessoaJuridicaManager;
    }

    @Override
    public String getTitulo() {
        return "Pessoa Jurídica";
    }
    
    @Override
    public String gravar() {
        
        if(this.imagem != null && !this.imagem.getFileName().isEmpty()){

            String[] file = this.imagem.getFileName().split("\\.");

            if(exts.contains(file[file.length - 1]))
                this.model.getPessoa().setImagem(this.imagem.getContents());
            else{
                Utils.adicionarMensagem("Tipo de arquivo não permitido.", null, Utils.FATAL);
                return null;
            }
                
        }
        
        return super.gravar();
    }
    
    public String gravarUsuario(){
        
        if(this.imagem != null && !this.imagem.getFileName().isEmpty()){

            String[] file = this.imagem.getFileName().split("\\.");

            if(exts.contains(file[file.length - 1]))
                this.model.getPessoa().setImagem(this.imagem.getContents());
            else{
                Utils.adicionarMensagem("Tipo de arquivo não permitido.", null, Utils.FATAL);
                return null;
            }
                
        }
        
        if(!this.usuario.getSenha().equals(this.confirmacaoSenha)){
            Utils.adicionarMensagem("Senhas não conferem.", null, Utils.FATAL);
            return null;
        }
        
        this.pessoaJuridicaManager.gravarUsuario(this.model, this.usuario);
        
        Utils.adicionarMensagem("Operação Realizada Com Sucesso.", null, Utils.SUCESSO);
        this.limpar();
            
                
       return "index";
    }
    
    public String adicionarMarcador() {
        Marker marker = new Marker(new LatLng(this.model.getPessoa().getLatitude(), this.model.getPessoa().getLongitude()), "Marcador");
        geoModel.addOverlay(marker);
        
        System.out.println("lat " + this.model.getPessoa().getLatitude() + " - log " + this.model.getPessoa().getLongitude());
        
        return null;
    }
    
    public String mostrarImagem() throws IOException {
        if(this.model.getPessoa().getImagem() != null){

            return Base64.getEncoder().encodeToString(this.model.getPessoa().getImagem());
        }
        return "";
    }
    
    public void selecionarEstado(){
        this.cidades = new ArrayList<Cidade>();
        
        if(this.estado != null)
            this.cidades = this.cidadeManager.findByEstado(this.estado);
    }
    
    
    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }
    
    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
         
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerGeoMap = center.getLat() + "," + center.getLng();
             
            /*for (GeocodeResult result : results) {
                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }*/
        }
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public UploadedFile getImagem() {
        return imagem;
    }

    public void setImagem(UploadedFile imagem) {
        this.imagem = imagem;
    }
    
    
    
}
