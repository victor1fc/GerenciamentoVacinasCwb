package br.com.springboot.controller;

import java.util.ArrayList;
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
import br.com.springboot.bo.PacienteBO;
import br.com.springboot.bo.FuncionarioBO;
import br.com.springboot.bo.CarteiraDeVacinasBO;
import br.com.springboot.bo.ProdutoBO;
import br.com.springboot.bo.ProdutoEstoqueBO;
import br.com.springboot.model.CarteiraDeVacinas;
import br.com.springboot.model.DoseVacinaItem;
import br.com.springboot.model.Marca;
import br.com.springboot.model.Produto;
import br.com.springboot.model.ProdutoEstoque;

@Controller
@RequestMapping("/carteira-de-vacinas")
public class CarteiraDeVacinasController {

	@Autowired
	private CarteiraDeVacinasBO carteiraDeVacinasBO;

	@Autowired
	private PacienteBO pacienteBO;

	@Autowired
	private ProdutoBO produtoBO;
	
	@Autowired
	private ProdutoEstoqueBO produtoEstoqueBO;

	@Autowired
	private FuncionarioBO funcionarioBO;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String salva(@Valid @ModelAttribute CarteiraDeVacinas carteiraDeVacinas, BindingResult result,
			RedirectAttributes attr, ModelMap model) {		
		
		 if (carteiraDeVacinas.getPaciente().getId() == null)
		 {
			 result.rejectValue("paciente", "field.required");			 
		 }
		  
		 if (result.hasErrors()) 
		 {
		  
		  model.addAttribute("pacientes", pacienteBO.listaTodos());	
		  return "/carteira-de-vacinas/formulario"; 
		}
		
		if (carteiraDeVacinas.getId() == null) {
			carteiraDeVacinasBO.insere(carteiraDeVacinas);			
			attr.addFlashAttribute("feedback", "A carteira de vacinação foi cadastrada com sucesso");
		} else {
			carteiraDeVacinasBO.atualiza(carteiraDeVacinas);			
			attr.addFlashAttribute("feedback", "Os dados foram atualizados com sucesso");
		}

		return "redirect:/carteira-de-vacinas";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView lista(ModelMap model) {
		
		List<CarteiraDeVacinas> lista = carteiraDeVacinasBO.listaTodos();		
		model.addAttribute("carteiras", lista);
		return new ModelAndView("/carteira-de-vacinas/lista", model);
	}

	@RequestMapping(value = "/{id}/item", method = RequestMethod.GET)
	public ModelAndView produto(@PathVariable("id") Long id, ModelMap model) {
		DoseVacinaItem dvi = new DoseVacinaItem();
		CarteiraDeVacinas cdv = carteiraDeVacinasBO.pesquisaPeloId(id);
		dvi.setCarteiraDeVacinas(cdv);
		model.addAttribute("doseVacinaItem", dvi);
		model.addAttribute("produtoEstoque", produtoEstoqueBO.listaTodos());
		model.addAttribute("produtos", produtoBO.listaTodos());
		model.addAttribute("funcionarios", funcionarioBO.listaTodos());
		model.addAttribute("marcas", Arrays.asList(Marca.values()));
		return new ModelAndView("/dose-vacina-item/formulario", model);
	}

	@RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
	public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("doseVacinaItem", new DoseVacinaItem());
		model.addAttribute("pacientes", pacienteBO.listaTodos());
		model.addAttribute("carteiraDeVacinas", carteiraDeVacinasBO.pesquisaPeloId(id));
		model.addAttribute("produtoEstoque", produtoEstoqueBO.listaTodos());
		model.addAttribute("produtos", produtoBO.listaTodos());
		model.addAttribute("marcas", Arrays.asList(Marca.values()));

		List<Produto> produtos = produtoBO.listaTodos();
		CarteiraDeVacinas cdv = carteiraDeVacinasBO.pesquisaPeloId(id);
		List<DoseVacinaItem> itens = cdv.getItens();

		List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

		List<DoseVacinaItem> itens_ordenados;

		List<List<DoseVacinaItem>> vacinas = new ArrayList<>();

		for (Integer i : numeros) {

			itens_ordenados = new ArrayList<>();

			Integer qtdeProdutos = produtos.size();

			for (Integer k = 0; k < qtdeProdutos; k++) {
				itens_ordenados.add(new DoseVacinaItem());
			}

			for (DoseVacinaItem item : itens) {
				if (item.getQuantidade() == i) {
					Integer indiceProdutos = 0;
					for (Produto produto : produtos) {
						
						Long itemProdutoEstoqueId = item.getProdutoEstoque().getId();
						ProdutoEstoque itemProdutoEstoque = produtoEstoqueBO.pesquisaPeloId(itemProdutoEstoqueId);	
						Long itemProdutoId = itemProdutoEstoque.getProduto().getId();
						Produto itemProduto = produtoBO.pesquisaPeloId(itemProdutoId);
						
						if (itemProduto.getNome() == produto.getNome()) {
							itens_ordenados.set(indiceProdutos, item);
							System.out.println("itens_ordenados: " + itens_ordenados);
						}
						indiceProdutos++;
					}
				}

			}
			vacinas.add(itens_ordenados);

			System.out.println("vacinas: " + vacinas);
		}
		model.addAttribute("vacinas", vacinas);

		return new ModelAndView("/carteira-de-vacinas/formulario", model);
	}

}
