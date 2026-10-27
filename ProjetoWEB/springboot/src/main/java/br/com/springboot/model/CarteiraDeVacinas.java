package br.com.springboot.model;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="carteira_de_vacinas")
public class CarteiraDeVacinas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="paciente_id", nullable=false)
	private Paciente paciente;
	
	@OneToMany(mappedBy="carteiraDeVacinas", cascade = CascadeType.ALL)
	private List<DoseVacinaItem> itens = new ArrayList<DoseVacinaItem>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public LocalDate getDataHora() { return dataHora; }
	 * 
	 * public void setDataHora(LocalDate dataHora) { this.dataHora = dataHora; }
	 */

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}


	public List<DoseVacinaItem> getItens() {
		return this.itens;
	}

	public void setItens(List<DoseVacinaItem> itens) {
		this.itens = itens;
	}
}
