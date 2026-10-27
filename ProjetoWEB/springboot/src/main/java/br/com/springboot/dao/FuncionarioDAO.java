package br.com.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.springboot.model.Funcionario;

@Repository
@Transactional
public class FuncionarioDAO implements CRUD<Funcionario, Long> {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Funcionario pesquisaPeloId(Long id) {
		return entityManager.find(Funcionario.class, id);
	}

	@Override
	public List<Funcionario> listaTodos() {
		Query query = entityManager.createQuery("SELECT f FROM Funcionario f");
		return (List<Funcionario>) query.getResultList() ;
	}

	@Override
	public void insere(Funcionario funcionario) {
		entityManager.persist(funcionario);		
	}

	@Override
	public void atualiza(Funcionario funcionario) {
		entityManager.merge(funcionario);
		
	}

	@Override
	public void remove(Funcionario funcionario) {
		entityManager.remove(funcionario);
		
	}

}
