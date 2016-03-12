package br.cin.ufpe.contribua.manager;

import br.cin.ufpe.contribua.model.PessoaJuridica;
import br.cin.ufpe.contribua.model.Usuario;
import java.util.Date;
import javax.ejb.Stateless;

@Stateless
public class PessoaJuridicaManager extends AbstractManager<PessoaJuridica> {
    public PessoaJuridicaManager() {
        super(PessoaJuridica.class);
    }
    
    @Override
    public void create(PessoaJuridica pessoaJuridica) {
        pessoaJuridica.getPessoa().setDataCadastro(new Date());
        getEntityManager().persist(pessoaJuridica.getPessoa());
        getEntityManager().persist(pessoaJuridica);
    }

    @Override
    public void edit(PessoaJuridica pessoaJuridica) {
        getEntityManager().merge(pessoaJuridica.getPessoa());
        getEntityManager().merge(pessoaJuridica);
    }

    @Override
    public void remove(PessoaJuridica pessoaJuridica) {
        getEntityManager().remove(getEntityManager().merge(pessoaJuridica));
        getEntityManager().remove(pessoaJuridica.getPessoa());
    }
    
    public void gravarUsuario(PessoaJuridica pessoaJuridica, Usuario usuario) {
        pessoaJuridica.getPessoa().setDataCadastro(new Date());
        getEntityManager().persist(pessoaJuridica.getPessoa());
        getEntityManager().persist(pessoaJuridica);
        
        usuario.setPessoa(pessoaJuridica.getPessoa());
        getEntityManager().merge(usuario);
    }
}
