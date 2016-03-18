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
        
        String telefone = pessoaFisica.getPessoa().getTelefone().replaceAll("\\(", "").replaceAll("\\)", "");
        String cep = pessoaFisica.getPessoa().getCep().replaceAll("\\-", "");
        
        pessoaFisica.getPessoa().setTelefone(telefone);
        pessoaFisica.getPessoa().setCep(cep);
        
        getEntityManager().persist(pessoaFisica.getPessoa());
        getEntityManager().persist(pessoaFisica);
    }

    @Override
    public void edit(PessoaFisica pessoaFisica) {
        String telefone = pessoaFisica.getPessoa().getTelefone().replaceAll("\\(", "").replaceAll("\\)", "");
        String cep = pessoaFisica.getPessoa().getCep().replaceAll("\\-", "");
        
        pessoaFisica.getPessoa().setTelefone(telefone);
        pessoaFisica.getPessoa().setCep(cep);
        
        getEntityManager().merge(pessoaFisica.getPessoa());
        getEntityManager().merge(pessoaFisica);
    }

    @Override
    public void remove(PessoaFisica pessoaFisica) {
        getEntityManager().remove(getEntityManager().merge(pessoaFisica));
        getEntityManager().remove(pessoaFisica.getPessoa());
    }
    
    public void gravarUsuario(PessoaFisica pessoaFisica, Usuario usuario) {
        String telefone = pessoaFisica.getPessoa().getTelefone().replaceAll("\\(", "").replaceAll("\\)", "");
        String cep = pessoaFisica.getPessoa().getCep().replaceAll("\\-", "");
        
        pessoaFisica.getPessoa().setTelefone(telefone);
        pessoaFisica.getPessoa().setCep(cep);
        
        pessoaFisica.getPessoa().setDataCadastro(new Date());
        getEntityManager().persist(pessoaFisica.getPessoa());
        getEntityManager().persist(pessoaFisica);
        
        usuario.setPessoa(pessoaFisica.getPessoa());
        getEntityManager().merge(usuario);
    }
}
