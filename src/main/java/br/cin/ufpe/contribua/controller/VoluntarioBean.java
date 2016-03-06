package br.cin.ufpe.contribua.controller;

import br.cin.ufpe.contribua.manager.AbstractManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.VoluntarioManager;
import br.cin.ufpe.contribua.model.Voluntario;
import java.awt.event.ActionEvent;
import java.util.List;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean
@ViewScoped
public class VoluntarioBean extends AbstractBean<Voluntario> {

    @EJB
    VoluntarioManager voluntarioManager;
    
    private MapModel geoModel;
    private String centerGeoMap = "41.850033, -87.6500523";

    @Override
    public String exibirInclusao(){
        String retorno = super.exibirInclusao();
        geoModel = new DefaultMapModel();
        
        return retorno;
    }
    
    @Override
    public String exibirAlteracao(){
        String retorno = super.exibirAlteracao();
        geoModel = new DefaultMapModel();
        adicionarMarcador();
        centerGeoMap = this.model.getLatitude() + ", " + this.model.getLongitude();
        
        return retorno;
    }
    
    @Override
    public AbstractManager getManager() {
        return voluntarioManager;
    }

    @Override
    public String getTitulo() {
        return "Voluntario";
    }
    
    public String adicionarMarcador() {
        Marker marker = new Marker(new LatLng(this.model.getLatitude(), this.model.getLongitude()), "Marcador");
        geoModel.addOverlay(marker);
        
        System.out.println("lat " + this.model.getLatitude() + " - log " + this.model.getLongitude());
        
        return null;
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
}
