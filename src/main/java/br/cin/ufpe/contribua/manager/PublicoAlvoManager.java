package br.cin.ufpe.contribua.manager;

import javax.ejb.Stateless;

import br.cin.ufpe.contribua.model.PublicoAlvo;

@Stateless
public class PublicoAlvoManager extends AbstractManager<PublicoAlvo> {
    public PublicoAlvoManager() {
        super(PublicoAlvo.class);
    }
}
