package br.com.springboot.model;

import java.time.LocalDate;


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

@Entity
@Table(name="produtos_estoque")
public class ProdutoEstoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	@NotNull
	private Produto produto;
	
	@NotNull(message = "informe o número total de doses no estoque")
	private Integer quantidade;	
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "data_validade", columnDefinition = "DATE")
	@NotNull(message = "Informe a data de validade")
	private LocalDate dataDeValidade;
	
	@NotNull(message = "informe a marca")	  
	@Enumerated(EnumType.STRING) private Marca marca;	
	
	private String nome;
	
	@NotNull(message = "informe o número do lote")
	private Integer numeroDoLote;
	

	public Integer getNumeroDoLote() {
		return numeroDoLote;
	}

	public void setNumeroDoLote(Integer numeroDoLote) {
		this.numeroDoLote = numeroDoLote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public LocalDate getDataDeValidade() {
		return dataDeValidade;
	}

	public void setDataDeValidade(LocalDate dataDeValidade) {
		this.dataDeValidade = dataDeValidade;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
