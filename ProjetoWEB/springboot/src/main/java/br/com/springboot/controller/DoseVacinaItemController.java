package br.com.springboot.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.springboot.bo.FuncionarioBO;
import br.com.springboot.bo.CarteiraDeVacinasBO;
import br.com.springboot.bo.DoseVacinaItemBO;
import br.com.springboot.bo.ProdutoBO;
import br.com.springboot.bo.ProdutoEstoqueBO;
import br.com.springboot.model.CarteiraDeVacinas;
import br.com.springboot.model.DoseVacinaItem;
import br.com.springboot.model.Marca;
import br.com.springboot.model.ProdutoEstoque;

@Controller
@RequestMapping("/dose-vacina-item")
public class DoseVacinaItemController {

	@Autowired
	private ProdutoEstoqueBO produtoEstoqueBO;
	
	@Autowired
	private ProdutoBO produtoBO;
	
	@Autowired
	private CarteiraDeVacinasBO carteiraDeVacinasBO;
	
	@Autowired
	private FuncionarioBO funcionarioBO;
	
	@Autowired
	private DoseVacinaItemBO doseVacinaItemBO;
	
	@RequestMapping(value="", method = RequestMethod.POST)
	public String salva(@Valid @ModelAttribute DoseVacinaItem doseVacinaItem,
			BindingResult result,
			RedirectAttributes attr,
			ModelMap model) {
		
		Long produtoEstoqueId = doseVacinaItem.getProdutoEstoque().getId();
		
		if (produtoEstoqueId == null)
			result.rejectValue("produtoEstoque", "field.required");
		
		ProdutoEstoque produtoEstoque = produtoEstoqueBO.pesquisaPeloId(produtoEstoqueId);	
		
		Long produtoId = produtoEstoque.getProduto().getId();				
		
		Integer produtoMaxDose = produtoBO.pesquisaPeloId(produtoId).getDoses(); 
		
		Long fornecedorId = doseVacinaItem.getFuncionario().getId();		
		if (fornecedorId == null)
			result.rejectValue("funcionario", "field.required");

		
		CarteiraDeVacinas carteiraDeVacinas = carteiraDeVacinasBO.pesquisaPeloId(doseVacinaItem.getCarteiraDeVacinas().getId());
		doseVacinaItem.setCarteiraDeVacinas(carteiraDeVacinas);
		
		List<DoseVacinaItem> itens = doseVacinaItem.getCarteiraDeVacinas().getItens();	
		
		System.out.println("produto id: " + produtoId);
		
		for (DoseVacinaItem item : itens)
		{
			
			Long itemProdutoEstoqueId = item.getProdutoEstoque().getId();
			ProdutoEstoque itemProdutoEstoque = produtoEstoqueBO.pesquisaPeloId(itemProdutoEstoqueId);	
			Long itemProdutoId = itemProdutoEstoque.getProduto().getId();
			
			System.out.println("item produto id: " + itemProdutoId);
			if (itemProdutoId == produtoId)
			{				 
				doseVacinaItem.setQuantidade(item.getQuantidade() + 1);
				System.out.println("encontrado quantidade:" + doseVacinaItem.getQuantidade()); 
			}
		}	
		
		
		
		if (doseVacinaItem.getQuantidade() <= 0)
		{
			doseVacinaItem.setQuantidade(1);
		}
		
		Integer quantidade =  doseVacinaItem.getQuantidade();
		System.out.println("Quantidade carteira item:" + quantidade); 
		
		
		System.out.println("produtoMaxDose: " + produtoMaxDose); 		
		
		
		if (quantidade > produtoMaxDose) {
		    model.addAttribute("mensagemErro", "Número máximo de doses tomadas excedido!");
		    model.addAttribute("produtoEstoque", produtoEstoqueBO.listaTodos());
		    model.addAttribute("funcionarios", funcionarioBO.listaTodos());
		    model.addAttribute("marcas", Arrays.asList(Marca.values()));
		    return "/dose-vacina-item/formulario";
		}		
		
		if (produtoEstoque.getQuantidade() < 1) {
			model.addAttribute("mensagemErro", "Não há mais doses dessa vacina!");
		    model.addAttribute("produtoEstoque", produtoEstoqueBO.listaTodos());
		    model.addAttribute("funcionarios", funcionarioBO.listaTodos());		    
		    return "/dose-vacina-item/formulario";
		}

		if (result.hasErrors()) {
			model.addAttribute("produtoEstoque", produtoEstoqueBO.listaTodos());
		    model.addAttribute("funcionarios", funcionarioBO.listaTodos());		    
		    return "/dose-vacina-item/formulario";
		}

		
		if (doseVacinaItem.getId() == null) {
			doseVacinaItemBO.insere(doseVacinaItem);
			produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() - 1);
			produtoEstoqueBO.atualiza(produtoEstoque);
			attr.addFlashAttribute("feedback", "Dose de vacina adicionada com sucesso!");
		} else {
			doseVacinaItemBO.atualiza(doseVacinaItem);
			attr.addFlashAttribute("feedback", "Dose de vacina editada com sucesso!");
		}
		
		

		Long carteiraDeVacinasId = doseVacinaItem.getCarteiraDeVacinas().getId();
		return "redirect:/carteira-de-vacinas/edita/" + carteiraDeVacinasId;
	}
	
	@RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
	public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("doseVacinaItem", doseVacinaItemBO.pesquisaPeloId(id));
		model.addAttribute("produtoEstoque", produtoEstoqueBO.listaTodos());
		model.addAttribute("funcionarios", funcionarioBO.listaTodos());		
		return new ModelAndView("/dose-vacina-item/formulario", model);
	}
	
	@RequestMapping(value="/remove/{id}", method=RequestMethod.GET)
	public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {
		Long carteiraDeVacinasId = 0L;
		DoseVacinaItem doseVacinaItem = doseVacinaItemBO.pesquisaPeloId(id);
		carteiraDeVacinasId = doseVacinaItem.getCarteiraDeVacinas().getId();	
		doseVacinaItemBO.remove(doseVacinaItem);
		
		ProdutoEstoque produtoEstoque = produtoEstoqueBO.pesquisaPeloId(doseVacinaItem.getProdutoEstoque().getId());
		produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + 1);
		produtoEstoqueBO.atualiza(produtoEstoque);
		
		attr.addAttribute("feedback", "Item removido com sucesso!");
		return "redirect:/carteira-de-vacinas/edita/" + carteiraDeVacinasId;
	}
	
}
