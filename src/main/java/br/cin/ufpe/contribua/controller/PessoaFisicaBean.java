package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;
import br.cin.ufpe.contribua.manager.CausaManager;
import br.cin.ufpe.contribua.manager.CidadeManager;
import br.cin.ufpe.contribua.manager.DiaSemanaManager;
import br.cin.ufpe.contribua.manager.EstadoManager;
import br.cin.ufpe.contribua.manager.HabilidadeManager;
import br.cin.ufpe.contribua.manager.PessoaFisicaManager;
import br.cin.ufpe.contribua.manager.PublicoAlvoManager;
import br.cin.ufpe.contribua.manager.QualificacaoManager;
import br.cin.ufpe.contribua.model.Causa;
import br.cin.ufpe.contribua.model.Cidade;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.model.DiaSemana;
import br.cin.ufpe.contribua.model.Disponibilidade;
import br.cin.ufpe.contribua.model.Estado;
import br.cin.ufpe.contribua.model.Habilidade;
import br.cin.ufpe.contribua.model.PessoaFisica;
import br.cin.ufpe.contribua.model.PublicoAlvo;
import br.cin.ufpe.contribua.model.Qualificacao;
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
public class PessoaFisicaBean extends AbstractBean<PessoaFisica> {

    @EJB
    PessoaFisicaManager pessoaFisicaManager;
    
    @EJB
    HabilidadeManager habilidadeManager;
    
    @EJB
    QualificacaoManager qualificacaoManager;
    
    @EJB
    CausaManager causaManager;
    
    @EJB
    PublicoAlvoManager publicoAlvoManager;
    
    @EJB
    DiaSemanaManager diaSemanaManager;
    
    @EJB
    EstadoManager estadoManager;
    
    @EJB
    CidadeManager cidadeManager;
    
    private MapModel geoModel;
    private String centerGeoMap = "41.850033, -87.6500523";
    
    private List<Habilidade> habilidades;
    
    private List<Qualificacao> qualificacoes;
    
    private List<Causa> causas;
    
    private List<PublicoAlvo> publicos;
    
    private List<Disponibilidade> disponibilidades;
    
    private List<DiaSemana> diasSemana;
    
    private List<Estado> estados;
    private Estado estado;
    
    private List<Cidade> cidades;
    
    private Usuario usuario;
    
    private String confirmacaoSenha;
    
    private UploadedFile imagem;
    
    private ArrayList<String> exts;
            
    
    public PessoaFisicaBean(){
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
        this.model = new PessoaFisica();
        
        geoModel = new DefaultMapModel();
        
        this.habilidades = habilidadeManager.findAll();
        this.qualificacoes = qualificacaoManager.findAll();
        this.causas = causaManager.findAll();
        this.publicos = publicoAlvoManager.findAll();
        this.estados = estadoManager.findAll();
        this.estado = null;
        this.cidades = new ArrayList<Cidade>();
        this.confirmacaoSenha = "";
        
        this.montarDisponibilidades();
    }

    @Override
    public String exibirInclusao(){
        String retorno = super.exibirInclusao();
        geoModel = new DefaultMapModel();
        
        this.habilidades = habilidadeManager.findAll();
        this.qualificacoes = qualificacaoManager.findAll();
        this.causas = causaManager.findAll();
        this.publicos = publicoAlvoManager.findAll();
        this.estados = estadoManager.findAll();
        this.estado = null;
        this.cidades = new ArrayList<Cidade>();
        
        this.montarDisponibilidades();
        
        return retorno;
    }
    
    @Override
    public String exibirAlteracao(){
        String retorno = super.exibirAlteracao();
        
        this.habilidades = habilidadeManager.findAll();
        this.qualificacoes = qualificacaoManager.findAll();
        this.causas = causaManager.findAll();
        this.publicos = publicoAlvoManager.findAll();
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
        return pessoaFisicaManager;
    }

    @Override
    public String getTitulo() {
        return "Pessoa Fisica";
    }
    
    @Override
    public String gravar() {
        this.model.getDisponibilidades().removeAll(this.model.getDisponibilidades());

        for(Disponibilidade disponibilidade : this.disponibilidades){
            if(disponibilidade.getHorarioInicio() != null && disponibilidade.getHorarioTermino() != null && 
                    !disponibilidade.getHorarioInicio().equals("") && !disponibilidade.getHorarioTermino().equals(""))
                this.model.getDisponibilidades().add(disponibilidade);
        }
        
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
        
        for(Disponibilidade disponibilidade : this.disponibilidades){
            if(disponibilidade.getHorarioInicio() != null && disponibilidade.getHorarioTermino() != null && 
                    !disponibilidade.getHorarioInicio().equals("") && !disponibilidade.getHorarioTermino().equals(""))
                this.model.getDisponibilidades().add(disponibilidade);
        }
        
        this.pessoaFisicaManager.gravarUsuario(this.model, this.usuario);
        
        Utils.adicionarMensagem("Operação Realizada Com Sucesso.", null, Utils.SUCESSO);
        this.limpar();
            
                
        return "/pages/private/home?faces-redirect=true";
    }
    
    private void montarDisponibilidades(){
        this.disponibilidades = new ArrayList<Disponibilidade>();
        this.diasSemana = diaSemanaManager.findAll();
        
        for(DiaSemana diaSemana : this.diasSemana){
            Disponibilidade disponibilidade = new Disponibilidade();
            disponibilidade.setPessoaFisica(this.model);
            for(Disponibilidade disponibilidadeModel : this.model.getDisponibilidades()){
                if(disponibilidadeModel.getDiaSemana().equals(diaSemana)){
                    disponibilidade = disponibilidadeModel;
                    break;
                }
            }
            
            disponibilidade.setDiaSemana(diaSemana);
            
            this.disponibilidades.add(disponibilidade);
        }
    }
    
    public String adicionarMarcador() {
        Marker marker = new Marker(new LatLng(this.model.getPessoa().getLatitude(), this.model.getPessoa().getLongitude()), "Marcador");
        geoModel.addOverlay(marker);
        
        System.out.println("lat " + this.model.getPessoa().getLatitude() + " - log " + this.model.getPessoa().getLongitude());
        
        return null;
    }
    
    public void selecionarEstado(){
        this.cidades = new ArrayList<Cidade>();
        
        if(this.estado != null)
            this.cidades = this.cidadeManager.findByEstado(this.estado);
    }
    
    public String mostrarImagem() throws IOException {
        if(this.model.getPessoa().getImagem() != null){
            //HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getR‌esponse();
            //response.setContentType("image");
            //response.setContentLength(this.model.getPessoa().getImagem().length);
            //response.getOutputStream().write(this.model.getPessoa().getImagem());
            //return StringUtils.newStringUtf8(Base64.encodeToString(this.model.getPessoa().getImagem(), false));
            //return Base64.encodeBase64URLSafeString(this.model.getPessoa().getImagem());
            return Base64.getEncoder().encodeToString(this.model.getPessoa().getImagem());
        }
        return "";
    }
    
    public List<Disponibilidade> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidade> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public List<DiaSemana> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<DiaSemana> diasSemana) {
        this.diasSemana = diasSemana;
    }
    
    public List<Habilidade> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidade> habilidades) {
        this.habilidades = habilidades;
    }

    public List<Qualificacao> getQualificacoes() {
        return qualificacoes;
    }

    public void setQualificacoes(List<Qualificacao> qualificacoes) {
        this.qualificacoes = qualificacoes;
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
             
            for (GeocodeResult result : results) {
                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
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

    public List<Causa> getCausas() {
        return causas;
    }

    public void setCausas(List<Causa> causas) {
        this.causas = causas;
    }

    public List<PublicoAlvo> getPublicos() {
        return publicos;
    }

    public void setPublicos(List<PublicoAlvo> publicos) {
        this.publicos = publicos;
    }

    public UploadedFile getImagem() {
        return imagem;
    }

    public void setImagem(UploadedFile imagem) {
        this.imagem = imagem;
    }
    
    
    
}
