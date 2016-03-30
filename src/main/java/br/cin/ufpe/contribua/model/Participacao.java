package br.cin.ufpe.contribua.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"eventosocial_id" , "usuario_id"})})
public class Participacao extends AbstractModel {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "eventosocial_id")
    private EventoSocial eventoSocial;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
	
	
    public Participacao() {

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

    
}
