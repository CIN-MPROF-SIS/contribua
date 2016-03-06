package br.cin.ufpe.contribua.model;


import javax.persistence.Entity;

@Entity(name = "instituicao")
public class Instituicao extends AbstractModel {

   
    private String paginaInstituicao;

    public String getPaginaInstituicao() {
        return paginaInstituicao;
    }

    public void setPaginaInstituicao(String paginaInstituicao) {
        this.paginaInstituicao = paginaInstituicao;
    }

    public Instituicao() {
    }
}
