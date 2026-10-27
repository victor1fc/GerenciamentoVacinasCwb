package br.com.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.springboot.model.CarteiraDeVacinas;

@Repository
@Transactional
public class CarteiraDeVacinasDAO implements CRUD<CarteiraDeVacinas, Long> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public CarteiraDeVacinas pesquisaPeloId(Long id) {
		return em.find(CarteiraDeVacinas.class, id);
	}

	@Override
	public List<CarteiraDeVacinas> listaTodos() {
		Query query = em.createQuery("select cdv from CarteiraDeVacinas cdv");
		return query.getResultList();
	}

	@Override
	public void insere(CarteiraDeVacinas carteiraDeVacinas) {
		em.persist(carteiraDeVacinas);
	}

	@Override
	public void atualiza(CarteiraDeVacinas carteiraDeVacinas) {
		em.merge(carteiraDeVacinas);
	}

	@Override
	public void remove(CarteiraDeVacinas carteiraDeVacinas) {
		em.remove(carteiraDeVacinas);
	}
}
