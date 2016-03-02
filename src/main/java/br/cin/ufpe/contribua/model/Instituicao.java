package br.cin.ufpe.contribua.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "instituicao")
public class Instituicao implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1522664288404086550L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String paginaInstituicao;

    public String getPaginaInstituicao() {
        return paginaInstituicao;
    }

    public void setPaginaInstituicao(String paginaInstituicao) {
        this.paginaInstituicao = paginaInstituicao;
    }

    public Instituicao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Instituicao other = (Instituicao) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
