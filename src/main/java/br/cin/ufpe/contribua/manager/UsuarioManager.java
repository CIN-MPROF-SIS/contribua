package br.cin.ufpe.contribua.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.cin.ufpe.contribua.model.Pessoa;
import br.cin.ufpe.contribua.model.Usuario;

@Stateless
public class UsuarioManager extends AbstractManager<Usuario> {
	public UsuarioManager() {
		super(Usuario.class);
	}

	@Override
	public void create(Usuario usuario) {
		// getEntityManager().merge(usuario.getPessoa());
		getEntityManager().merge(usuario);
	}

	@Override
	public void edit(Usuario usuario) {
		getEntityManager().merge(usuario.getPessoa());
		getEntityManager().merge(usuario);
	}

	public Usuario logar(String login, String senha) {

		Map parameters = new HashMap();
		parameters.put("login", login);
		parameters.put("senha", senha);

		return findWithNamedQueryUniq("Usuario.logar", parameters);
	}

	public List<Pessoa> findPessoa(String nome) {
		String sql = "SELECT p " + "FROM Pessoa p " + "WHERE LOWER(p.nome) like '%" + nome.toLowerCase() + "%' "
				+ "ORDER BY p.nome ";

		Query query = this.getEntityManager().createQuery(sql);

		return query.getResultList();
	}
}
