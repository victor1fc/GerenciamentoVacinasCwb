package br.com.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "informe o nome")
	@Column(nullable=false, length = 50)
	private String nome;
	
	/*
	 * @NotNull(message = "informe a marca")
	 * 
	 * @Enumerated(EnumType.STRING) private Marca marca;
	 */
	
	@NotNull(message = "informe o número total de doses que deve ser aplicada")
	private Integer doses=1;
	
	private Integer doseInterval;
	
	public Integer getDoses() {
		return doses;
	}

	public void setDoses(Integer doses) {
		this.doses = doses;
	}

	public Integer getDoseInterval() {
		return doseInterval;
	}

	public void setDoseInterval(Integer doseInterval) {
		this.doseInterval = doseInterval;
	}
	
	private boolean ativo;
	
	public Produto() {
		this.ativo = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/*
	 * public Marca getMarca() { return marca; }
	 * 
	 * public void setMarca(Marca marca) { this.marca = marca; }
	 */

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
