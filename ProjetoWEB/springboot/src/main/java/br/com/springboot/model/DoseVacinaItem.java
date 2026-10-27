package br.com.springboot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="dose_vacina_itens")
public class DoseVacinaItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name="produtos_estoque")
	@NotNull
	private ProdutoEstoque produtoEstoque;		

	@ManyToOne
	@JoinColumn(name="funcionario_id")
	@NotNull
	private Funcionario funcionario;
		
	@ManyToOne
	@JoinColumn(name="carteira_de_vacinas_id")
	@NotNull	
	private CarteiraDeVacinas carteiraDeVacinas;
	
	@NotNull(message = "informe a quantidade")
	private Integer quantidade = 1;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(nullable=false, name="data_hora", columnDefinition = "DATETIME")
	private LocalDateTime dataHora = LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay();	


	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CarteiraDeVacinas getCarteiraDeVacinas() {
		return carteiraDeVacinas;
	}

	public void setCarteiraDeVacinas(CarteiraDeVacinas carteiraDeVacinas) {
		this.carteiraDeVacinas = carteiraDeVacinas;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public ProdutoEstoque getProdutoEstoque() {
		return produtoEstoque;
	}

	public void setProdutoEstoque(ProdutoEstoque produtoEstoque) {
		this.produtoEstoque = produtoEstoque;
	}


}
