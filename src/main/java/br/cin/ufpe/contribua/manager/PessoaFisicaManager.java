package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.PessoaFisica;
import br.cin.ufpe.contribua.model.Usuario;
import java.util.Date;
import javax.ejb.Stateless;

@Stateless
public class PessoaFisicaManager extends AbstractManager<PessoaFisica> {
    public PessoaFisicaManager() {
        super(PessoaFisica.class);
    }
    
    @Override
    public void create(PessoaFisica pessoaFisica) {
        pessoaFisica.getPessoa().setDataCadastro(new Date());
        getEntityManager().persist(pessoaFisica.getPessoa());
        getEntityManager().persist(pessoaFisica);
    }

    @Override
    public void edit(PessoaFisica pessoaFisica) {
        getEntityManager().merge(pessoaFisica.getPessoa());
        getEntityManager().merge(pessoaFisica);
    }

    @Override
    public void remove(PessoaFisica pessoaFisica) {
        getEntityManager().remove(getEntityManager().merge(pessoaFisica));
        getEntityManager().remove(pessoaFisica.getPessoa());
    }
    
    public void gravarUsuario(PessoaFisica pessoaFisica, Usuario usuario) {
        pessoaFisica.getPessoa().setDataCadastro(new Date());
        getEntityManager().persist(pessoaFisica.getPessoa());
        getEntityManager().persist(pessoaFisica);
        
        usuario.setPessoa(pessoaFisica.getPessoa());
        getEntityManager().merge(usuario);
    }
}
