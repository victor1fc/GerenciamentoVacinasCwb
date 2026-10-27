package br.com.springboot.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.springboot.dao.CRUD;
import br.com.springboot.dao.PacienteDAO;
import br.com.springboot.model.Paciente;

@Service
public class PacienteBO implements CRUD<Paciente, Long>{
	
	@Autowired
	private PacienteDAO dao;

	@Override
	public Paciente pesquisaPeloId(Long id) {
		
		return dao.pesquisaPeloId(id);
	}

	@Override
	public List<Paciente> listaTodos() {
			return dao.listaTodos();
	}

	@Override
	public void insere(Paciente paciente) {
		dao.insere(paciente);
		
	}

	@Override
	public void atualiza(Paciente paciente) {
		dao.atualiza(paciente);
		
	}

	@Override
	public void remove(Paciente paciente) {
		dao.remove(paciente);
		
	}
	
	public void inativa(Paciente paciente) {
		paciente.setAtivo(false);
		dao.atualiza(paciente);
	}
	
	public void ativa(Paciente paciente) {
		paciente.setAtivo(true);
		dao.atualiza(paciente);
	}
	
}

