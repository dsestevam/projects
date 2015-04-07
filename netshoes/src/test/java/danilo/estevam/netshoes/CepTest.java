package danilo.estevam.netshoes;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import danilo.estevam.netshoes.config.AppConfig;
import danilo.estevam.netshoes.exception.SingleException;
import danilo.estevam.netshoes.model.Cep;
import danilo.estevam.netshoes.repository.CepRepository;
import danilo.estevam.netshoes.service.CepService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class CepTest {
	
	private Cep cep;

	@Autowired
	private CepService service;
	
	@Autowired
	private CepRepository repository;
	
	@Before
	public void before() {
		cep = new Cep();
		cep.setBairro("Vila Aleatória");
		cep.setCep("12345000");
		cep.setCidade("São Paulo");
		cep.setEstado("SP");
		cep.setLogradouro("Rua Gerenal Randômico");
		
		repository.save(cep);
	}
	
	@After
	public void after() {
		repository.delete(cep);
	}
	
	@Test
	public void testGetCep() {
		//Teste: cep ok
		Cep check = null;
		
		try {
			check = service.getByCep("12345000");
		} catch (SingleException e) {
			Assert.assertTrue(false);
		}
		
		Assert.assertEquals(cep.getBairro(), check.getBairro());
		Assert.assertEquals(cep.getCep(), check.getCep());
		Assert.assertEquals(cep.getCidade(), check.getCidade());
		Assert.assertEquals(cep.getEstado(), check.getEstado());
		Assert.assertEquals(cep.getLogradouro(), check.getLogradouro());
		
		
		//Teste: cep numeros diferentes, ok
		try {
			check = service.getByCep("12345678");
		} catch (SingleException e) {
			Assert.assertTrue(false);
		}
		
		Assert.assertEquals(cep.getBairro(), check.getBairro());
		Assert.assertEquals(cep.getCep(), check.getCep());
		Assert.assertEquals(cep.getCidade(), check.getCidade());
		Assert.assertEquals(cep.getEstado(), check.getEstado());
		Assert.assertEquals(cep.getLogradouro(), check.getLogradouro());
		
		
		//Teste: cep nao existente
		try {
			check = service.getByCep("11111111");
			Assert.assertTrue(false);
		} catch (SingleException e) {
			Assert.assertEquals("CEP invalido.", e.getDetail().getMessage());
		}
		
		
		//Teste: cep invalido
		try {
			check = service.getByCep("ABCD");
			Assert.assertTrue(false);
		} catch (SingleException e) {
			Assert.assertEquals("CEP invalido.", e.getDetail().getMessage());
		}
	}
}