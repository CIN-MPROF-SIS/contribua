package br.cin.ufpe.contribua.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.AbstractManager;
import br.cin.ufpe.contribua.manager.EventoSocialManager;
import br.cin.ufpe.contribua.model.EventoSocial;

@ManagedBean
@ViewScoped
public class EventoSocialBean extends AbstractBean<EventoSocial> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6957865892901186766L;
	@EJB
    EventoSocialManager EventoSocialManager;

    @Override
    public AbstractManager getManager() {
        return EventoSocialManager;
    }

    @Override
    public String getTitulo() {
        return "Eventos";
    }
}
