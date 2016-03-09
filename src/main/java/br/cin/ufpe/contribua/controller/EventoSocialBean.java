package br.cin.ufpe.contribua.controller;

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
import br.cin.ufpe.contribua.manager.EventoSocialManager;
import br.cin.ufpe.contribua.manager.PublicoAlvoManager;
import br.cin.ufpe.contribua.model.Causa;
import br.cin.ufpe.contribua.model.EventoSocial;
import br.cin.ufpe.contribua.model.PublicoAlvo;

@ManagedBean
@ViewScoped
public class EventoSocialBean extends AbstractBean<EventoSocial> {


	private static final long serialVersionUID = -6957865892901186766L;
	@EJB
	EventoSocialManager eventoSocialManager;
	
	@EJB
	PublicoAlvoManager publicoAlvoManager;
	
	@EJB
	CausaManager causaManager;
	
    private List<Causa> causas;
    
    private List<PublicoAlvo> publicoAlvos;

	private MapModel geoModel;

	private String centerGeoMap = "41.850033, -87.6500523";

	@Override
	public String exibirInclusao() {
		String retorno = super.exibirInclusao();
		
		this.causas = causaManager.findAll();
        this.publicoAlvos = publicoAlvoManager.findAll();
		
		geoModel = new DefaultMapModel();

		return retorno;
	}
	
	 @Override
	    public String exibirAlteracao(){
	        String retorno = super.exibirAlteracao();	
	      
	        
	        this.causas = causaManager.findAll();
	        this.publicoAlvos = publicoAlvoManager.findAll();
	        
	       
	        geoModel = new DefaultMapModel();
	        adicionarMarcador();
	        centerGeoMap = this.model.getLatitude() + ", " + this.model.getLongitude();
	        
	        return retorno;
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
	
	

}
