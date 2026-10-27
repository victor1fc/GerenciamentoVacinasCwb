package br.com.springboot.controller;

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
import br.com.springboot.model.Funcionario;



@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioBO bo;
	
	@RequestMapping( value = "/novo", method = RequestMethod.GET)
	public ModelAndView novo(ModelMap model) {
		model.addAttribute("funcionario", new Funcionario());
		return new ModelAndView("/funcionario/formulario", model);
				
	}
	
	@RequestMapping( value = "", method = RequestMethod.GET)
	public ModelAndView lista(ModelMap model) {
		model.addAttribute("funcionarios", bo.listaTodos());
		return new ModelAndView("/funcionario/lista", model);
				
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String salva(@Valid @ModelAttribute Funcionario funcionario, BindingResult result, RedirectAttributes attr) {
			
			if(result.hasErrors()) {
				return "funcionario/formulario";
			}
		
			if (funcionario.getId() == null) {
				bo.insere(funcionario);
				attr.addFlashAttribute("feedback", "Funcionário foi cadastrado com sucesso");
			} else {
				bo.atualiza(funcionario);
				attr.addFlashAttribute("feedback", "Funcionário foi atualizado com sucesso");
			}
			
			return "redirect:/funcionarios";	
	}
	
	@RequestMapping(value = "/ativa/{id}", method = RequestMethod.GET)
	public String ativa(@PathVariable("id") Long id, RedirectAttributes attr) {
		try {
				Funcionario funcionario = bo.pesquisaPeloId(id);	
				bo.ativa(funcionario);
				attr.addFlashAttribute("feedback", "Funcionário foi ativado");				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/funcionarios";
				
	}

	@RequestMapping(value = "/inativa/{id}", method = RequestMethod.GET)
	public String inativa(@PathVariable("id") Long id, RedirectAttributes attr) {
		try {
				Funcionario funcionario = bo.pesquisaPeloId(id);
				bo.inativa(funcionario);
				attr.addFlashAttribute("feedback", "Funcionário foi inativado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/funcionarios";
	}
	
	@RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
	public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
		try {
				model.addAttribute("funcionario", bo.pesquisaPeloId(id));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/funcionario/formulario", model);
	}
	
	@RequestMapping(value = "/deleta/{id}", method = RequestMethod.GET)
	public String deleta(@PathVariable("id") Long id, RedirectAttributes attr) {
		try {
				Funcionario funcionario = bo.pesquisaPeloId(id);
				bo.remove(funcionario);
				attr.addFlashAttribute("feedback", "Funcionário foi removido");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/funcionarios";
	}
}
