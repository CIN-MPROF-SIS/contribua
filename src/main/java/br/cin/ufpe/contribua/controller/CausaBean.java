package br.cin.ufpe.contribua.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.cin.ufpe.contribua.manager.AbstractManager;
import br.cin.ufpe.contribua.manager.CausaManager;
import br.cin.ufpe.contribua.model.Causa;

@ManagedBean
@ViewScoped
public class CausaBean extends AbstractBean<Causa> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7200014098752166020L;
	@EJB
	CausaManager causaManager;

    @Override
    public AbstractManager getManager() {
        return causaManager;
    }

    @Override
    public String getTitulo() {
        return "Causa";
    }
}
