package br.com.springboot.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.springboot.bo.CarteiraDeVacinasBO;
import br.com.springboot.model.CarteiraDeVacinas;


@Controller
@RequestMapping("/historico")
public class HistoricoController {
    
    @Autowired
    private CarteiraDeVacinasBO carteiraDeVacinasBO;  

    @RequestMapping(value = "", method=RequestMethod.GET)
	public ModelAndView lista(ModelMap model) {
    	
    	List<CarteiraDeVacinas> lista_cdv = carteiraDeVacinasBO.listaTodos();     
    	model.addAttribute("carteiraDeVacinas", lista_cdv);
		return new ModelAndView("/historico/lista", model);  	
    }

  
}