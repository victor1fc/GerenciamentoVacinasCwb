package br.com.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.springboot.model.DoseVacinaItem;

@Repository
@Transactional
public class DoseVacinaItemDAO implements CRUD<DoseVacinaItem, Long> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public DoseVacinaItem pesquisaPeloId(Long id) {
		return em.find(DoseVacinaItem.class, id);
	}

	@Override
	public List<DoseVacinaItem> listaTodos() {
		Query query = em.createQuery("select dvi from DoseVacinaItem dvi");
		return query.getResultList();
	}

	@Override
	public void insere(DoseVacinaItem doseVacinaItem) {
		em.persist(doseVacinaItem);
	}

	@Override
	public void atualiza(DoseVacinaItem doseVacinaItem) {
		em.merge(doseVacinaItem);
	}

	@Override
	public void remove(DoseVacinaItem doseVacinaItem) {
		em.remove(doseVacinaItem);
	}

	public List<DoseVacinaItem> listaItens(Long carteiraDeVacinasId) {
		Query query = em.createQuery("from DoseVacinaItem n where n.carteiraDeVacinas.id = :carteiraDeVacinasId")
						.setParameter("carteiraDeVacinasId", carteiraDeVacinasId);
		return query.getResultList();
	}
}
