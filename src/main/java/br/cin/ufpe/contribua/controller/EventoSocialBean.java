package br.cin.ufpe.contribua.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.cin.ufpe.contribua.manager.AbstractManager;
import br.cin.ufpe.contribua.manager.CausaManager;
import br.cin.ufpe.contribua.manager.DiaSemanaManager;
import br.cin.ufpe.contribua.manager.EventoSocialManager;
import br.cin.ufpe.contribua.manager.HabilidadeManager;
import br.cin.ufpe.contribua.manager.PublicoAlvoManager;
import br.cin.ufpe.contribua.manager.QualificacaoManager;
import br.cin.ufpe.contribua.manager.UsuarioManager;
import br.cin.ufpe.contribua.model.Causa;
import br.cin.ufpe.contribua.model.DiaSemana;
import br.cin.ufpe.contribua.model.Disponibilidade;
import br.cin.ufpe.contribua.model.EventoSocial;
import br.cin.ufpe.contribua.model.Habilidade;
import br.cin.ufpe.contribua.model.PublicoAlvo;
import br.cin.ufpe.contribua.model.Qualificacao;
import br.cin.ufpe.contribua.model.Usuario;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.Circle;

@ManagedBean
@ViewScoped
public class EventoSocialBean extends AbstractBean<EventoSocial> {


    private static final long serialVersionUID = -6957865892901186766L;
    @EJB
    EventoSocialManager eventoSocialManager;

    @EJB
    HabilidadeManager habilidadeManager;

    @EJB
    PublicoAlvoManager publicoAlvoManager;

    @EJB
    QualificacaoManager qualificacaoManager;

    @EJB
    CausaManager causaManager;

    @EJB
    DiaSemanaManager diaSemanaManager;
    
    @EJB
    UsuarioManager usuarioManager;
	
    private List<Causa> causas;
    
    private List<PublicoAlvo> publicoAlvos;
    
    private List<Disponibilidade> disponibilidades;
    
    private List<Qualificacao> qualificacoes;
    
    private List<Habilidade> habilidades;
    
    private List<DiaSemana> diasSemana;

    private MapModel geoModel;

    private String centerGeoMap = "41.850033, -87.6500523";
    
    private List<EventoSocial> eventosAbertos;
    
    private Marker marker;
    
    private EventoSocial eventoSocial;
    
    public void inicializarSelecao(){
        //Trocar pelo usuário logado
        Usuario usuario = usuarioManager.find(1);
        
        this.geoModel = new DefaultMapModel();
        
        this.centerGeoMap = usuario.getPessoa().getLatitude() + ", " + usuario.getPessoa().getLongitude();
        LatLng coord = new LatLng(usuario.getPessoa().getLatitude(), usuario.getPessoa().getLongitude());
        //1km
        Circle circle1 = new Circle(coord, 1000);
        circle1.setStrokeColor("#00ff00");
        circle1.setFillColor("#00ff00");
        circle1.setStrokeOpacity(0.35);
        circle1.setFillOpacity(0.35);
 
        //3km
        Circle circle2 = new Circle(coord, 3000);
        circle2.setStrokeColor("#00ff00");
        circle2.setFillColor("#00ff00");
        circle2.setStrokeOpacity(0.25);
        circle2.setFillOpacity(0.25);
        
        //5km
        Circle circle3 = new Circle(coord, 5000);
        circle3.setStrokeColor("#00ff00");
        circle3.setFillColor("#00ff00");
        circle3.setStrokeOpacity(0.15);
        circle3.setFillOpacity(0.15);
        
        Circle circle4 = new Circle(coord, 10000);
        circle4.setStrokeColor("#00ff00");
        circle4.setFillColor("#00ff00");
        circle4.setStrokeOpacity(0.05);
        circle4.setFillOpacity(0.05);
        
        this.geoModel.addOverlay(circle1);
        this.geoModel.addOverlay(circle2);
        this.geoModel.addOverlay(circle3);
        this.geoModel.addOverlay(circle4);
        
        
        //Buscar eventos abertos
        this.eventosAbertos = new ArrayList<EventoSocial>();
        this.eventosAbertos = this.eventoSocialManager.findAbertos();
        
        this.geoModel.addOverlay(new Marker(coord, "Eu", "0", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
        
        for(EventoSocial eventoSocial : this.eventosAbertos){
            LatLng coordMarker = new LatLng(eventoSocial.getLatitude(), eventoSocial.getLongitude());
            this.geoModel.addOverlay(new Marker(coordMarker, eventoSocial.getNome(), eventoSocial.getId()));
        }
        
    }
    
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        this.eventoSocial = eventoSocialManager.find(marker.getData());
    }

	@Override
	public String exibirInclusao() {
		String retorno = super.exibirInclusao();
		
		this.causas = causaManager.findAll();
        this.publicoAlvos = publicoAlvoManager.findAll();
        this.habilidades = habilidadeManager.findAll();
        this.qualificacoes = qualificacaoManager.findAll();
        
        this.montarDisponibilidades();
		
		geoModel = new DefaultMapModel();

		return retorno;
	}
	
	 @Override
	    public String exibirAlteracao(){
	        String retorno = super.exibirAlteracao();	
                this.montarDisponibilidades();
	        
	        this.causas = causaManager.findAll();
	        this.publicoAlvos = publicoAlvoManager.findAll();
	        this.habilidades = habilidadeManager.findAll();
	        this.qualificacoes = qualificacaoManager.findAll();
	        
	       
	        geoModel = new DefaultMapModel();
	        adicionarMarcador();
	        centerGeoMap = this.model.getLatitude() + ", " + this.model.getLongitude();
	        
	        return retorno;
	    }
	 
	 @Override
        public String gravar() {

            this.model.getDisponibilidades().removeAll(this.model.getDisponibilidades());

            for(Disponibilidade disponibilidade : this.disponibilidades){
                if(disponibilidade.getHorarioInicio() != null && disponibilidade.getHorarioTermino() != null && 
                        !disponibilidade.getHorarioInicio().equals("") && !disponibilidade.getHorarioTermino().equals(""))
                    this.model.getDisponibilidades().add(disponibilidade);
            }

            return super.gravar();
        }

	 
	 public String adicionarMarcador() {
	        Marker marker = new Marker(new LatLng(this.model.getLatitude(), this.model.getLongitude()), "Marcador");
	        geoModel.addOverlay(marker);
	        
	        System.out.println("lat " + this.model.getLatitude() + " - log " + this.model.getLongitude());
	        
	        return null;
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
	 
	 private void montarDisponibilidades(){
	        this.disponibilidades = new ArrayList<Disponibilidade>();
	        this.diasSemana = diaSemanaManager.findAll();
	        
	        for(DiaSemana diaSemana : this.diasSemana){
	            Disponibilidade disponibilidade = new Disponibilidade();
	            disponibilidade.setEventoSocial(this.model);
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
	 
	@Override
	public AbstractManager getManager() {
		return eventoSocialManager;
	}

	@Override
	public String getTitulo() {
		return "Eventos";
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

	public List<Causa> getCausas() {
		return causas;
	}

	public void setCausas(List<Causa> causas) {
		this.causas = causas;
	}

	public List<PublicoAlvo> getPublicoAlvos() {
		return publicoAlvos;
	}

	public void setPublicoAlvos(List<PublicoAlvo> publicoAlvos) {
		this.publicoAlvos = publicoAlvos;
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

    public List<EventoSocial> getEventosAbertos() {
        return eventosAbertos;
    }

    public void setEventosAbertos(List<EventoSocial> eventosAbertos) {
        this.eventosAbertos = eventosAbertos;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public EventoSocial getEventoSocial() {
        return eventoSocial;
    }

    public void setEventoSocial(EventoSocial eventoSocial) {
        this.eventoSocial = eventoSocial;
    }

}