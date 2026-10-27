package br.com.springboot.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.dao.CRUD;
import br.com.springboot.dao.DoseVacinaItemDAO;
import br.com.springboot.model.DoseVacinaItem;

@Service
public class DoseVacinaItemBO implements CRUD<DoseVacinaItem, Long> {

	@Autowired
	private DoseVacinaItemDAO dao;
	
	@Override
	public DoseVacinaItem pesquisaPeloId(Long id) {
		return dao.pesquisaPeloId(id);
	}

	@Override
	public List<DoseVacinaItem> listaTodos() {
		return dao.listaTodos();
	}

	@Override
	public void insere(DoseVacinaItem doseVacinaItem) {
		dao.insere(doseVacinaItem);
	}

	@Override
	public void atualiza(DoseVacinaItem doseVacinaItem) {
		dao.atualiza(doseVacinaItem);
	}

	@Override
	public void remove(DoseVacinaItem doseVacinaItem) {
		dao.remove(doseVacinaItem);
	}
	
	public boolean itemJaAdicionado(DoseVacinaItem doseVacinaItem) {
		/*
		 * Long carteiraDeVacinasId = doseVacinaItem.getCarteiraDeVacinas().getId();
		 * List<DoseVacinaItem> itens = dao.listaItens(carteiraDeVacinasId);
		 * 
		 * Long produtoId = doseVacinaItem.getProduto().getId();
		 * 
		 * if (doseVacinaItem.getId() == null) { for (DoseVacinaItem item : itens) { if
		 * (item.getProduto().getId() == produtoId) { return true; } } } else { Long
		 * notaEntradaItemId = doseVacinaItem.getId(); for (DoseVacinaItem item : itens)
		 * { if (item.getProduto().getId() == produtoId && notaEntradaItemId !=
		 * item.getId()) { return true; } } }
		 */
		return false;
	}
	
}
