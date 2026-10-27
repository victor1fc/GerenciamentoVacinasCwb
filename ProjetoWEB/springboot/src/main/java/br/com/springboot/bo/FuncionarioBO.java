package br.com.springboot.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.dao.CRUD;
import br.com.springboot.dao.FuncionarioDAO;
import br.com.springboot.model.Funcionario;

@Service
public class FuncionarioBO implements CRUD<Funcionario, Long>{

	@Autowired
	private FuncionarioDAO dao;
	
	@Override
	public Funcionario pesquisaPeloId(Long id) {
		return dao.pesquisaPeloId(id);
	}

	@Override
	public List<Funcionario> listaTodos() {
		return dao.listaTodos();
	}

	@Override
	public void insere(Funcionario funcionario) {
		dao.insere(funcionario);
		
	}

	@Override
	public void atualiza(Funcionario funcionario) {
		dao.atualiza(funcionario);
		
	}

	@Override
	public void remove(Funcionario funcionario) {
		dao.remove(funcionario);		
	}
	
	public void ativa(Funcionario funcionario) {
		funcionario.setAtivo(true);
		dao.atualiza(funcionario);
	}
	
	public void inativa(Funcionario funcionario) {
		funcionario.setAtivo(false);
		dao.atualiza(funcionario);
	}
	

}
