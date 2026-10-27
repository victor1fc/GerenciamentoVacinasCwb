
package br.com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.springboot.bo.CarteiraDeVacinasBO;
import br.com.springboot.model.Paciente;
import br.com.springboot.model.CarteiraDeVacinas;
import br.com.springboot.model.DoseVacinaItem;
import br.com.springboot.model.Produto;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	private CarteiraDeVacinasBO carteiraDeVacinasBO;

   
    
    @Transactional
    @Scheduled(cron = "0 00 17 * * *")  // Executa todos os dias 12:00
    public void sendNoticeEmail() {
    	
    	
    	System.out.println("###################### Executando sendNoticeEmail !!!");	
    	System.out.println("######################");
    	System.out.println("######################");
    	Integer itensBuscados = 0;
    	LocalDateTime dateNow = LocalDateTime.now();
    	List<CarteiraDeVacinas> carteiraDeVacinas = carteiraDeVacinasBO.listaTodos();
    	for (CarteiraDeVacinas carteiraDeVacina : carteiraDeVacinas) 
    	{
    		List<DoseVacinaItem> itens = carteiraDeVacina.getItens();
    		for (DoseVacinaItem item : itens)
    		{
    			itensBuscados++;
    			System.out.println("Item quantidade: " + item.getQuantidade());
    			
    			Produto produto = item.getProdutoEstoque().getProduto();
    			LocalDateTime doseDate = item.getDataHora();
    			Integer maxDose = produto.getDoses();    			
    			LocalDateTime newDoseDate = doseDate.plusMonths(produto.getDoseInterval());
    			
    			System.out.println("maxDose: " + maxDose);
    			System.out.println("newDoseDate: " + newDoseDate);
    			
    			if (item.getQuantidade() < maxDose)
    			{
    				System.out.println("Data da próxima notificação: " + newDoseDate.minusDays(7).toLocalDate());
    				if (dateNow.toLocalDate().isEqual(newDoseDate.minusDays(7).toLocalDate())) // Notificar com 7 dias de antecedencia
        			{
    					
    					SimpleMailMessage message = new SimpleMailMessage();
    			       
    			        Paciente paciente = carteiraDeVacina.getPaciente();  
    			        
    			        System.out.println("E-mail do paciente: " + paciente.getEmail());	
    			        
    			        message.setFrom("vacinacwb@gmail.com");
    			        message.setTo(paciente.getEmail());    			        

    			        Integer nextDose = item.getQuantidade() + 1;
    			        message.setSubject("Aviso " +
    			        					nextDose.toString() +
    			        					"ª Dose - " +
    			        					produto.getNome());
    			        message.setText("A data da sua próxima dose de vacina está perto. Procure um posto de Saúde da sua região. \n"
    			        		+ "\nPaciente: "
    			        		+ paciente.getNome()
    			        		+ "\n\nDados da vacina: \n"
    			        		+ nextDose.toString() +
	        					"ª Dose - " +
	        					produto.getNome() +
	        					"\nData da próxima dose: "
	        					+ newDoseDate.toString()
	        					
    			        		);

    			        mailSender.send(message);

    			        System.out.println("E-mail enviado com sucesso!");	
        			}
    				
    			} 			
    		}
    	}
    	System.out.println("sendNoticeEmail ItensBuscados: " + itensBuscados);
    	System.out.println("######################  Executado !!!");	
    	System.out.println("######################");
    	System.out.println("######################");
    	
        
    }
    
    @Transactional
    @Scheduled(cron = "0 00 17 * * *")  // Executa todos os dias 12:00
    public void sendDayBeforeNoticeEmail() {
    	
    	
    	System.out.println("###################### Executando sendNoticeEmail !!!");	
    	System.out.println("######################");
    	System.out.println("######################");
    	Integer itensBuscados = 0;
    	LocalDateTime dateNow = LocalDateTime.now();
    	List<CarteiraDeVacinas> carteiraDeVacinas = carteiraDeVacinasBO.listaTodos();
    	for (CarteiraDeVacinas carteiraDeVacina : carteiraDeVacinas) 
    	{
    		List<DoseVacinaItem> itens = carteiraDeVacina.getItens();
    		for (DoseVacinaItem item : itens)
    		{
    			itensBuscados++;
    			System.out.println("Item quantidade: " + item.getQuantidade());
    			
    			Produto produto = item.getProdutoEstoque().getProduto();
    			LocalDateTime doseDate = item.getDataHora();
    			Integer maxDose = produto.getDoses();    			
    			LocalDateTime newDoseDate = doseDate.plusMonths(produto.getDoseInterval());
    			
    			System.out.println("maxDose: " + maxDose);
    			System.out.println("newDoseDate: " + newDoseDate);
    			
    			if (item.getQuantidade() < maxDose)
    			{
    				System.out.println("Data da próxima notificação: " + newDoseDate.minusDays(1).toLocalDate());
    				if (dateNow.toLocalDate().isEqual(newDoseDate.minusDays(1).toLocalDate())) // Notificar com 7 dias de antecedencia
        			{
    					
    					SimpleMailMessage message = new SimpleMailMessage();
    			       
    			        Paciente paciente = carteiraDeVacina.getPaciente();  
    			        
    			        System.out.println("E-mail do paciente: " + paciente.getEmail());	
    			        
    			        message.setFrom("vacinacwb@gmail.com");
    			        message.setTo(paciente.getEmail());    			        

    			        Integer nextDose = item.getQuantidade() + 1;
    			        message.setSubject("Aviso " +
    			        					nextDose.toString() +
    			        					"ª Dose - " +
    			        					produto.getNome());
    			        message.setText("A data da sua próxima dose de vacina está perto. Procure um posto de Saúde da sua região. \n"
    			        		+ "\nPaciente: "
    			        		+ paciente.getNome()
    			        		+ "\n\nDados da vacina: \n"
    			        		+ nextDose.toString() +
	        					"ª Dose - " +
	        					produto.getNome() +
	        					"\nData da próxima dose: "
	        					+ newDoseDate.toString()
	        					
    			        		);

    			        mailSender.send(message);

    			        System.out.println("E-mail enviado com sucesso!");	
        			}
    				
    			} 			
    		}
    	}
    	System.out.println("sendNoticeEmail ItensBuscados: " + itensBuscados);
    	System.out.println("######################  Executado !!!");	
    	System.out.println("######################");
    	System.out.println("######################");
    	
        
    }
}

