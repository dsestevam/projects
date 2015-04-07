package danilo.estevam.netshoes;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import danilo.estevam.netshoes.config.AppConfig;
import danilo.estevam.netshoes.exception.MultipleException;
import danilo.estevam.netshoes.exception.SingleException;
import danilo.estevam.netshoes.model.Address;
import danilo.estevam.netshoes.model.Cep;
import danilo.estevam.netshoes.repository.AddressRepository;
import danilo.estevam.netshoes.repository.CepRepository;
import danilo.estevam.netshoes.service.AddressService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class AddressTest {
	
	private Cep cep;
	private Address address;

	@Autowired
	private AddressService service;
	
	@Autowired
	private CepRepository cepRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Before
	public void before() {
		cep = new Cep();
		cep.setBairro("Vila Aleatória");
		cep.setCep("12345000");
		cep.setCidade("São Paulo");
		cep.setEstado("SP");
		cep.setLogradouro("Rua Gerenal Randômico");
		cepRepository.save(cep);
		
		address = new Address();
		address.setCep(cep);
		address.setNumero(123);
		address.setComplemento("complemento");
		addressRepository.save(address);
	}
	
	@After
	public void after() {
		cepRepository.delete(cep);
		addressRepository.delete(address);
	}
	
	@Test
	public void testGet() {
		//Teste: ok
		Address check = null;
		try {
			check = service.get(address);
		} catch (SingleException e) {
			Assert.assertTrue(false);
		}
		
		Assert.assertEquals(address.getId(), check.getId());
		Assert.assertEquals(address.getBairro(), check.getBairro());
		Assert.assertEquals(address.getCep(), check.getCep());
		Assert.assertEquals(address.getCidade(), check.getCidade());
		Assert.assertEquals(address.getComplemento(), check.getComplemento());
		Assert.assertEquals(address.getEstado(), check.getEstado());
		Assert.assertEquals(address.getLogradouro(), check.getLogradouro());
		Assert.assertEquals(address.getNumero(), check.getNumero());
		
		
		//Teste: invalido
		try {
			service.get(new Address());
			Assert.assertTrue(false);
		} catch (SingleException e) {
			Assert.assertEquals("ID inválido.", e.getDetail().getMessage());
		}
	}
	
	@Test
	public void testSave() {
		//Teste: ok
		Address check = new Address();
		check.setCep(cep);
		check.setNumero(123);
		check.setComplemento("complemento");
		check.setBairro("Vila Aleatória");
		check.setCep("12345000");
		check.setCidade("São Paulo");
		check.setEstado("SP");
		check.setLogradouro("Rua Gerenal Randômico");
		
		Address confirm = null;
		try {
			confirm = service.save(check);
		} catch (SingleException | MultipleException e) {
			Assert.assertTrue(false);
		}
		
		Assert.assertEquals(check.getId(), confirm.getId());
		Assert.assertEquals(check.getBairro(), confirm.getBairro());
		Assert.assertEquals(check.getCep(), confirm.getCep());
		Assert.assertEquals(check.getCidade(), confirm.getCidade());
		Assert.assertEquals(check.getComplemento(), confirm.getComplemento());
		Assert.assertEquals(check.getEstado(), confirm.getEstado());
		Assert.assertEquals(check.getLogradouro(), confirm.getLogradouro());
		Assert.assertEquals(check.getNumero(), confirm.getNumero());
		
		//Teste: parametros pendentes
		try {
			service.save(new Address());
			Assert.assertTrue(false);
		} catch (SingleException e) {
			Assert.assertTrue(false);
		} catch (MultipleException e) {
			Assert.assertEquals(5, e.getDetails().size());
		}
		
		
		//Teste: Address nulo
		try {
			service.save(null);
			Assert.assertTrue(false);
		} catch (SingleException e) {
			Assert.assertEquals("Endereço inválido.", e.getDetail().getMessage());
		} catch (MultipleException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testUpdate() {
		//Teste: ok
		address.setNumero(123456);
		Address check = null;
		try {
			check = service.update(address);
		} catch (SingleException | MultipleException e) {
			Assert.assertTrue(false);
		}
		
		Assert.assertEquals(address.getLogradouro(), check.getLogradouro());
		Assert.assertEquals(address.getNumero(), check.getNumero());
		
		
		//Teste: parâmetros pendentes
		address.setCep("");
		address.setCidade(null);
		address.setEstado(null);
		address.setLogradouro(null);
		address.setNumero(null);
		
		try {
			check = service.save(new Address());
			Assert.assertTrue(false);
		} catch (SingleException e) {
			Assert.assertTrue(false);
		} catch (MultipleException e) {
			Assert.assertEquals(5, e.getDetails().size());
		}
		
		try {
			check = service.get(address);
		} catch (SingleException e) {
			Assert.assertTrue(false);
		}
		
		Assert.assertEquals(false, StringUtils.isBlank(check.getCep()));
		Assert.assertNotNull(check.getCidade());
		Assert.assertNotNull(check.getEstado());
		Assert.assertNotNull(check.getLogradouro());
		Assert.assertNotNull(check.getNumero());
		
		
		//Teste: id nulo
		try {
			check = service.update(new Address());
			Assert.assertTrue(false);
		} catch (SingleException e) {
			Assert.assertEquals("ID inválido.", e.getDetail().getMessage());
		} catch (MultipleException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testDelete() {
		//Teste: ok
		String id = address.getId();
		try {
			service.delete(address);
		} catch (SingleException e) {
			Assert.assertTrue(false);
		}
		
		Address check = new Address();
		Address confirm = null;
		check.setId(id);
		
		try {
			confirm = service.get(check);
		} catch (SingleException e) {
			Assert.assertTrue(false);
		}
		
		Assert.assertNull(confirm);
		
		
		//Teste: invalido
		try {
			service.delete(new Address());
			Assert.assertTrue(false);
		} catch (SingleException e) {
			Assert.assertEquals("ID inválido.", e.getDetail().getMessage());
		}
	}
}