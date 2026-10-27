package br.com.springboot.bo;

import java.time.LocalDate;


import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.springboot.model.Paciente;
import br.com.springboot.model.Sexo;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
final public class ClienteBOTest {
	
	@Autowired
	private PacienteBO bo;
	
	@Test 
	@Order(1)
	public void insere() {
		Paciente paciente = new Paciente();
		paciente.setNome("José da Silva");
		paciente.setCpf("01234567890");
		paciente.setDataDeNascimento(LocalDate.of(2000, 1, 8));
		paciente.setSexo(Sexo.MASCULINO);
		paciente.setTelefone("0123456789");
		paciente.setCelular("01234567890");
		paciente.setAtivo(true);
		paciente.setEmail("jose@gmail.com");
		bo.insere(paciente);
	}
	
	@Test
	@Order(2) 
	public void pesquisaPeloId() {
		Paciente paciente = bo.pesquisaPeloId(1L);
		System.out.println(paciente);
	}
	
	@Test
	@Order(3)
	public void atualiza() {
		Paciente paciente = bo.pesquisaPeloId(1L);
		paciente.setCpf("98765432100");
		bo.atualiza(paciente);
	}
}
