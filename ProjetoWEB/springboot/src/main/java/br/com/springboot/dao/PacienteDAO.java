package br.com.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.springboot.model.Paciente;

@Repository
@Transactional
public class PacienteDAO implements CRUD<Paciente, Long> {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public Paciente pesquisaPeloId(Long id) {
		return entityManager.find(Paciente.class, id);
	}

	@Override
	public List<Paciente> listaTodos() {
		Query query = entityManager.createQuery("SELECT c FROM Paciente c");
	    return (List<Paciente>) query.getResultList();
	}

	@Override
	public void insere(Paciente paciente) {
		entityManager.persist(paciente);
	}

	@Override
	public void atualiza(Paciente paciente) {
		entityManager.merge(paciente);
	}

	@Override
	public void remove(Paciente paciente) {
		entityManager.remove(paciente);
	}
}