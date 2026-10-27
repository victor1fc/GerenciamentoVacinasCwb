package br.com.springboot.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.dao.CRUD;
import br.com.springboot.dao.CarteiraDeVacinasDAO;
import br.com.springboot.model.CarteiraDeVacinas;

@Service
public class CarteiraDeVacinasBO implements CRUD<CarteiraDeVacinas, Long> {

	@Autowired
	private CarteiraDeVacinasDAO carteiraDeVacinasDAO;
	
	@Override
	public CarteiraDeVacinas pesquisaPeloId(Long id) {
		return carteiraDeVacinasDAO.pesquisaPeloId(id);
	}

	@Override
	public List<CarteiraDeVacinas> listaTodos() {
		return carteiraDeVacinasDAO.listaTodos();
	}

	@Override
	public void insere(CarteiraDeVacinas carteiraDeVacinas) {
		carteiraDeVacinasDAO.insere(carteiraDeVacinas);
	}

	@Override
	public void atualiza(CarteiraDeVacinas carteiraDeVacinas) {
		carteiraDeVacinasDAO.atualiza(carteiraDeVacinas);
	}

	@Override
	public void remove(CarteiraDeVacinas carteiraDeVacinas) {
		carteiraDeVacinasDAO.remove(carteiraDeVacinas);
	}
}
