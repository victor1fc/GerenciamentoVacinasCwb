package br.com.springboot.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.dao.CRUD;
import br.com.springboot.dao.ProdutoDAO;
import br.com.springboot.model.Produto;
import br.com.springboot.model.ProdutoEstoque;

@Service
public class ProdutoBO implements CRUD<Produto, Long> {

	@Autowired
	private ProdutoDAO dao;
	
	@Override
	public Produto pesquisaPeloId(Long id) {
		return dao.pesquisaPeloId(id);
	}

	@Override
	public List<Produto> listaTodos() {
		return dao.listaTodos();
	}

	@Override
	public void insere(Produto produto) {
		dao.insere(produto);
		
	}

	@Override
	public void atualiza(Produto produto) {
		dao.atualiza(produto);
	}

	@Override
	public void remove(Produto produto) {
		dao.remove(produto);
	}

	public void ativa(Produto produto) {
		produto.setAtivo(true);
		dao.atualiza(produto);
	}
	
	public void inativa(Produto produto) {
		produto.setAtivo(false);
		dao.atualiza(produto);
	}
	
	public boolean itemJaAdicionado(Produto produto) {
		
			
		List<Produto> produtos = dao.listaTodos();
		
		System.out.println("##### produto get nome: " + produto.getNome() );
		for (Produto item : produtos) 
		{
			System.out.println("##### item get nome: " + item.getNome() );
			if (produto.getNome().equals(item.getNome()))
			{
				System.out.println("item duplicado!! ");
				return true;
			}
		}		
		  
		 
		return false;
	}
	
}
