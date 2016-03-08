package br.cin.ufpe.contribua.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.AbstractManager;
import br.cin.ufpe.contribua.manager.PublicoAlvoManager;
import br.cin.ufpe.contribua.model.PublicoAlvo;

@ManagedBean
@ViewScoped
public class PublicoAlvoBean extends AbstractBean<PublicoAlvo> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7200014098752166020L;
	@EJB
	PublicoAlvoManager publicoAlvoManager;

    @Override
    public AbstractManager getManager() {
        return publicoAlvoManager;
    }

    @Override
    public String getTitulo() {
        return "Público Alvo";
    }
}
