/*
 * package br.com.springboot.controller;
 * 
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestParam;
 * 
 * import br.com.springboot.service.EmailService;
 * 
 * import javax.mail.MessagingException;
 * 
 * @Controller public class EmailController {
 * 
 * @Autowired private EmailService emailService;
 * 
 * @GetMapping("/email") public String formEmail() { return "email-form"; }
 * 
 * @PostMapping("/email") public String enviarEmail(
 * 
 * @RequestParam String para,
 * 
 * @RequestParam String assunto,
 * 
 * @RequestParam String mensagem, Model model) { try {
 * emailService.enviarEmail(para, assunto, mensagem);
 * model.addAttribute("success", "E-mail enviado com sucesso!"); } catch
 * (MessagingException e) { model.addAttribute("error",
 * "Erro ao enviar o e-mail: " + e.getMessage()); } return "email-form"; }
 * 
 * }
 */