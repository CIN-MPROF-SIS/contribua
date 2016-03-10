package br.cin.ufpe.contribua.model;



import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;	

@Entity
@Table(name = "estado")
public class Estado extends AbstractModel  implements Serializable {

	
	private static final long serialVersionUID = 573007577929532184L;
	private String nome;
	private String uf;

	@OneToMany(mappedBy = "estado")
	private List<Cidade> cidades;



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	

}
