package br.com.springboot.controller;

import java.util.Arrays;
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

import br.com.springboot.bo.ProdutoBO;
import br.com.springboot.bo.ProdutoEstoqueBO;
import br.com.springboot.model.Marca;
import br.com.springboot.model.Produto;
import br.com.springboot.model.ProdutoEstoque;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {
    
    @Autowired
    ProdutoEstoqueBO produtoEstoqueBO;

    @Autowired
    private ProdutoBO produtoBO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String estoque() {
        return "estoque/lista";
    }

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model) {
        model.addAttribute("produtoEstoque", new ProdutoEstoque());
        model.addAttribute("produtos", produtoBO.listaTodos());
        model.addAttribute("marcas", Arrays.asList(Marca.values()));
        return new ModelAndView("estoque/formulario", model);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String salva(@Valid @ModelAttribute ProdutoEstoque produtoEstoque,
                        BindingResult result,
                        RedirectAttributes attr,
                        ModelMap model) {
    	
    	Produto produto = produtoBO.pesquisaPeloId(produtoEstoque.getProduto().getId());        	
    	
        Long produtoId = produtoEstoque.getProduto().getId();
        if (produtoId == null)
            result.rejectValue("produto", "field.required");        
        

        if (produtoEstoqueBO.itemJaAdicionado(produtoEstoque)) {
            result.rejectValue("produtoEstoque", "duplicate");
        }

        if (result.hasErrors()) {
            model.addAttribute("produtos", produtoBO.listaTodos());
            model.addAttribute("marcas", Arrays.asList(Marca.values()));
            return "estoque/formulario";
        }

        if (produtoEstoque.getId() == null) {
        	
        	String nomeEstoque = produto.getNome() + " - " 
        						+  produtoEstoque.getMarca().getDescricao() + " - "
        						+  produtoEstoque.getNumeroDoLote().toString();
        	
        	produtoEstoque.setNome(nomeEstoque);        	
            produtoEstoqueBO.insere(produtoEstoque);
            produtoEstoque.setProduto(produto);
            attr.addFlashAttribute("feedcack", "Estoque adicionado com sucesso!");
        } else {
        	produtoEstoque.setNome(produto.getNome() + " - " + produtoEstoque.getMarca().getDescricao());  
            produtoEstoqueBO.atualiza(produtoEstoque);
            attr.addFlashAttribute("feedcack", "Estoque atualizado com sucesso!");
        }

        return "redirect:/estoque";
    }

    @RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("produtoEstoque", produtoEstoqueBO.pesquisaPeloId(id));
        model.addAttribute("produtos", produtoBO.listaTodos());
        model.addAttribute("marcas", Arrays.asList(Marca.values()));
        return new ModelAndView("estoque/formulario", model);
    }
}
