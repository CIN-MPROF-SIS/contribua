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

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;

import br.cin.ufpe.contribua.manager.AbstractManager;
import br.cin.ufpe.contribua.manager.CausaManager;
import br.cin.ufpe.contribua.manager.DiaSemanaManager;
import br.cin.ufpe.contribua.manager.EventoSocialManager;
import br.cin.ufpe.contribua.manager.HabilidadeManager;
import br.cin.ufpe.contribua.manager.PublicoAlvoManager;
import br.cin.ufpe.contribua.manager.QualificacaoManager;
import br.cin.ufpe.contribua.model.Causa;
import br.cin.ufpe.contribua.model.DiaSemana;
import br.cin.ufpe.contribua.model.Disponibilidade;
import br.cin.ufpe.contribua.model.EventoSocial;
import br.cin.ufpe.contribua.model.Habilidade;
import br.cin.ufpe.contribua.model.PublicoAlvo;
import br.cin.ufpe.contribua.model.Qualificacao;

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
	
    private List<Causa> causas;
    
    private List<PublicoAlvo> publicoAlvos;
    
    private List<Disponibilidade> disponibilidades;
    
    private List<Qualificacao> qualificacoes;
    
    private List<Habilidade> habilidades;
    
    private List<DiaSemana> diasSemana;

	private MapModel geoModel;

	private String centerGeoMap = "41.850033, -87.6500523";

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
	        
	        this.causas = causaManager.findAll();
	        this.publicoAlvos = publicoAlvoManager.findAll();
	        this.habilidades = habilidadeManager.findAll();
	        this.qualificacoes = qualificacaoManager.findAll();
	        
	       
	        geoModel = new DefaultMapModel();
	        adicionarMarcador();
	        centerGeoMap = this.model.getLatitude() + ", " + this.model.getLongitude();
	        
	        
	        GeocodingResult geoCodeResult; 
	        
	      
	        
	        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCCXuxQpgClaoUzVeW9C8S0PqQpegU4cz8");
			GeocodingApiRequest results;
			try {
				 

				results = GeocodingApi.reverseGeocode(context,new com.google.maps.model.LatLng(this.model.getLatitude(), this.model.getLongitude())) ;
				System.out.println(results.await());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
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
	
	
	

}
