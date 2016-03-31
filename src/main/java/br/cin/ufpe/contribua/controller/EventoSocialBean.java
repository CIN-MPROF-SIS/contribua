package br.cin.ufpe.contribua.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.Circle;
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
import br.cin.ufpe.contribua.manager.ParticipacaoManager;
import br.cin.ufpe.contribua.manager.PessoaJuridicaManager;
import br.cin.ufpe.contribua.manager.PublicoAlvoManager;
import br.cin.ufpe.contribua.manager.QualificacaoManager;
import br.cin.ufpe.contribua.manager.UsuarioManager;
import br.cin.ufpe.contribua.model.Causa;
import br.cin.ufpe.contribua.model.DiaSemana;
import br.cin.ufpe.contribua.model.EventoSocial;
import br.cin.ufpe.contribua.model.Habilidade;
import br.cin.ufpe.contribua.model.Meta;
import br.cin.ufpe.contribua.model.Participacao;
import br.cin.ufpe.contribua.model.PublicoAlvo;
import br.cin.ufpe.contribua.model.Qualificacao;
import br.cin.ufpe.contribua.model.Usuario;
import br.cin.ufpe.contribua.util.Utils;

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

	@EJB
	ParticipacaoManager participacaoManager;

	@EJB
	PessoaJuridicaManager pessoaJuridicaManager;

	private Usuario usuario;

	private List<Causa> causas;

	private List<PublicoAlvo> publicoAlvos;

	private List<Qualificacao> qualificacoes;

	private List<Habilidade> habilidades;

	private List<DiaSemana> diasSemana;

	private MapModel geoModel;

	private MapModel geoModelVinculacao;

	private String centerGeoMap = "41.850033, -87.6500523";

	private String centerGeoMapVinculacao = "41.850033, -87.6500523";

	private List<EventoSocial> eventosAbertos;

	private Marker marker;

	private EventoSocial eventoSocial;

	private boolean vinculado;

	private boolean mostrarVincular;
	
	private List<Participacao> participacoes;

	@PostConstruct
	@Override
	public void inicializar() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		usuario = (Usuario) session.getAttribute("usuario");

		this.limpar();
		incluindo = false;
		this.listaModel = eventoSocialManager.findByUsuario(this.usuario);
		participacoes = new ArrayList<Participacao>();
	}

	public void inicializarSelecao() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		usuario = (Usuario) session.getAttribute("usuario");

		this.geoModel = new DefaultMapModel();

		this.centerGeoMap = usuario.getPessoa().getLatitude() + ", " + usuario.getPessoa().getLongitude();
		LatLng coord = new LatLng(usuario.getPessoa().getLatitude(), usuario.getPessoa().getLongitude());
		// 1km
		Circle circle1 = new Circle(coord, 1000);
		circle1.setStrokeColor("yellow");
		circle1.setFillColor("yellow");
		circle1.setStrokeOpacity(0.35);
		circle1.setFillOpacity(0.35);

		// 3km
		Circle circle2 = new Circle(coord, 3000);
		circle2.setStrokeColor("green");
		circle2.setFillColor("green");
		circle2.setStrokeOpacity(0.25);
		circle2.setFillOpacity(0.25);

		// 5km
		Circle circle3 = new Circle(coord, 5000);
		circle3.setStrokeColor("blue");
		circle3.setFillColor("blue");
		circle3.setStrokeOpacity(0.15);
		circle3.setFillOpacity(0.15);

		Circle circle4 = new Circle(coord, 10000);
		circle4.setStrokeColor("red");
		circle4.setFillColor("red");
		circle4.setStrokeOpacity(0.05);
		circle4.setFillOpacity(0.05);

		this.geoModel.addOverlay(circle1);
		this.geoModel.addOverlay(circle2);
		// this.geoModel.addOverlay(circle3);
		// this.geoModel.addOverlay(circle4);

		// Buscar eventos abertos
		this.eventosAbertos = new ArrayList<EventoSocial>();
		this.eventosAbertos = this.eventoSocialManager.findAbertos();

		this.geoModel
				.addOverlay(new Marker(coord, "Eu", "0", "http://maps.google.com/mapfiles/ms/micons/blue-pushpin.png"));

		for (EventoSocial eventoSocial : this.eventosAbertos) {
			LatLng coordMarker = new LatLng(eventoSocial.getLatitude(), eventoSocial.getLongitude());

			if (eventoSocial.getMobilizador().equals(usuario))
				this.geoModel.addOverlay(new Marker(coordMarker, "Meu Evento: " + eventoSocial.getNome(),
						eventoSocial.getId(), "http://maps.google.com/mapfiles/ms/micons/green-dot.png"));
			else if (this.participacaoManager.findByUsuarioEvento(eventoSocial, this.usuario) != null)
				this.geoModel.addOverlay(new Marker(coordMarker, eventoSocial.getNome(), eventoSocial.getId(),
						"http://maps.google.com/mapfiles/ms/micons/red-pushpin.png"));
			else
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

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		usuario = (Usuario) session.getAttribute("usuario");

		this.model.setMobilizador(usuario);
		this.causas = causaManager.findAll();
		this.publicoAlvos = publicoAlvoManager.findAll();
		this.habilidades = habilidadeManager.findAll();
		this.qualificacoes = qualificacaoManager.findAll();

		geoModel = new DefaultMapModel();

		return retorno;
	}

	@Override
	public String exibirAlteracao() {
		String retorno = super.exibirAlteracao();

		this.causas = causaManager.findAll();
		this.publicoAlvos = publicoAlvoManager.findAll();
		this.habilidades = habilidadeManager.findAll();
		this.qualificacoes = qualificacaoManager.findAll();

		geoModel = new DefaultMapModel();
		adicionarMarcador();
		centerGeoMap = this.model.getLatitude() + ", " + this.model.getLongitude();

		return retorno;
	}

	public String exibirVinculacao() {
		super.exibirAlteracao();
		vinculado = false;
		mostrarVincular = true;
		this.geoModelVinculacao = new DefaultMapModel();

		this.causas = causaManager.findAll();
		this.publicoAlvos = publicoAlvoManager.findAll();
		this.habilidades = habilidadeManager.findAll();
		this.qualificacoes = qualificacaoManager.findAll();
		adicionarMarcadorVinculacao();
		this.centerGeoMapVinculacao = this.model.getLatitude() + ", " + this.model.getLongitude();

		// Busca participacao
		this.vinculado = this.participacaoManager.findByUsuarioEvento(this.model, this.usuario) != null;

		if (pessoaJuridicaManager.findByPessoa(this.usuario.getPessoa()) != null
				|| this.model.getMobilizador().equals(this.usuario))
			this.mostrarVincular = false;

		return "EventoSocialVincular";
	}

	public void vincular() {
		Participacao participacao = null;

		if (!this.vinculado) {
			System.out.println("incluindo");
			participacao = new Participacao();
			participacao.setEventoSocial(this.model);
			participacao.setUsuario(this.usuario);

			participacaoManager.create(participacao);

			vinculado = true;
		} else {
			System.out.println("excluindo");
			participacao = this.participacaoManager.findByUsuarioEvento(this.model, this.usuario);
			participacaoManager.remove(participacao);
			vinculado = false;
		}

		Utils.adicionarMensagem("Operação realizada com sucesso.", null, Utils.SUCESSO);
	}

	public String prepararFecharEvento() {
		
		

		return "/pages/private/eventoSocial/prestacaoContas.xhtml";
	}
	
	public String fecharEvento() {

		this.model.setFechado(true);

		Utils.adicionarMensagem("Operação realizada com sucesso.", null, Utils.SUCESSO);

		try {
				getManager().edit(this.model);
			
			Utils.adicionarMensagem("Operação Realizada Com Sucesso.", null, Utils.SUCESSO);
			return "/pages/public/eventoSocial/resumo.xhtml";
		} catch (Exception e) {
			Utils.adicionarMensagem("Erro ao Cadastrar Evento." + e.getMessage(), null, Utils.FATAL);
			e.printStackTrace();
		}

		return null;

	}
	
	public String detalheEvento() {

		participacoes = participacaoManager.findByEvento(this.model);
		
		return "/pages/public/eventoSocial/detalhe.xhtml";

	}
	

	@Override
	public String gravar() {

		return super.gravar();
	}

	public String adicionarMarcador() {
		Marker marker = new Marker(new LatLng(this.model.getLatitude(), this.model.getLongitude()), "Marcador");
		geoModel.addOverlay(marker);

		System.out.println("lat " + this.model.getLatitude() + " - log " + this.model.getLongitude());

		return null;
	}

	public String adicionarMarcadorVinculacao() {
		Marker marker = new Marker(new LatLng(this.model.getLatitude(), this.model.getLongitude()), "Marcador");
		geoModelVinculacao.addOverlay(marker);

		System.out.println("lat " + this.model.getLatitude() + " - log " + this.model.getLongitude());

		return null;
	}

	public void prepararInserirMeta() {

		Meta novaMeta = new Meta();
		novaMeta.setQuantidadeNecessaria(1);
		novaMeta.setQuantidadeObtida(0);
		novaMeta.setEvento(this.model);

		this.getModel().getMetas().add(novaMeta);

	}

	public void removeMeta(Meta meta) {

		this.getModel().getMetas().remove(meta);

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isVinculado() {
		return vinculado;
	}

	public void setVinculado(boolean vinculado) {
		this.vinculado = vinculado;
	}

	public MapModel getGeoModelVinculacao() {
		return geoModelVinculacao;
	}

	public void setGeoModelVinculacao(MapModel geoModelVinculacao) {
		this.geoModelVinculacao = geoModelVinculacao;
	}

	public String getCenterGeoMapVinculacao() {
		return centerGeoMapVinculacao;
	}

	public void setCenterGeoMapVinculacao(String centerGeoMapVinculacao) {
		this.centerGeoMapVinculacao = centerGeoMapVinculacao;
	}

	public boolean isMostrarVincular() {
		return mostrarVincular;
	}

	public void setMostrarVincular(boolean mostrarVincular) {
		this.mostrarVincular = mostrarVincular;
	}

	public List<Participacao> getParticipacoes() {
		return participacoes;
	}

	public void setParticipacoes(List<Participacao> participacoes) {
		this.participacoes = participacoes;
	}

}