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

import br.com.springboot.bo.CarteiraDeVacinasBO;
import br.com.springboot.bo.PacienteBO;
import br.com.springboot.model.CarteiraDeVacinas;
import br.com.springboot.model.Paciente;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
	 
	@Autowired
	private PacienteBO bo;
	
	@Autowired
	private CarteiraDeVacinasBO carteiraDeVacinasBO;
	
	@RequestMapping(value = "/novo", method = RequestMethod.GET)
	public ModelAndView novo(ModelMap model) {
		model.addAttribute("paciente", new Paciente());
		return new ModelAndView("/paciente/formulario", model);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String salva(@Valid @ModelAttribute Paciente paciente, BindingResult result, RedirectAttributes attr ) {
		if (result.hasErrors()) {
			return "paciente/formulario";
		}
		if (paciente.getId() == null) {
			bo.insere(paciente);
			CarteiraDeVacinas carteiraDeVacinas = new CarteiraDeVacinas();
			carteiraDeVacinas.setPaciente(paciente);
			carteiraDeVacinasBO.insere(carteiraDeVacinas);
			
			attr.addFlashAttribute("feedback", "Paciente foi cadastrado com sucesso");
		}
		else {
			bo.atualiza(paciente);
			attr.addFlashAttribute("feedback", "Paciente foi atualizado com sucesso");
		}
		return "redirect:/pacientes";
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView lista(ModelMap model) {
		model.addAttribute("pacientes", bo.listaTodos());
		return new ModelAndView("/paciente/lista", model);
	}

	@RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
	public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("paciente", bo.pesquisaPeloId(id));
		return new ModelAndView("/paciente/formulario", model);
	}
	
	@RequestMapping(value = "/inativa/{id}", method = RequestMethod.GET)
	public String inativa(@PathVariable("id") Long id, RedirectAttributes attr) {
		Paciente paciente = bo.pesquisaPeloId(id);
		bo.inativa(paciente);
		attr.addFlashAttribute("feedback", "Paciente foi inativado");
		return "redirect:/pacientes";
		
	}

	@RequestMapping(value = "/ativa/{id}", method = RequestMethod.GET)
	public String ativa(@PathVariable("id") Long id, RedirectAttributes attr) {
		Paciente paciente = bo.pesquisaPeloId(id);
		bo.ativa(paciente);
		attr.addFlashAttribute("feedback", "Paciente foi ativado");
		return "redirect:/pacientes";
		
	}
	
	@RequestMapping(value = "/deleta/{id}", method = RequestMethod.GET)
	public String deleta(@PathVariable("id") Long id, RedirectAttributes attr) {
	    Paciente paciente = bo.pesquisaPeloId(id);
	    if (paciente == null) {
	        throw new RuntimeException("Paciente não encontrado para exclusão.");
	    }
	    bo.remove(paciente);
	    attr.addFlashAttribute("feedback", "Paciente foi deletado");
	    return "redirect:/pacientes";
	}

}

