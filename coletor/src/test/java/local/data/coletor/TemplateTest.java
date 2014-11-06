package local.data.coletor;

import java.util.ArrayList;

import junit.framework.Assert;
import local.data.coletor.config.AppConfig;
import local.data.coletor.exception.TemplateException;
import local.data.coletor.model.Data;
import local.data.coletor.model.Field;
import local.data.coletor.model.Template;
import local.data.coletor.service.TemplateService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class TemplateTest {

	@Autowired
	private TemplateService service;
	
	@Test
	public void testList() {
		Assert.assertNotNull(service.list());
	}
	
	
	@Test
	public void testSaveGetUpdateDelete_ok() throws TemplateException  {
		Template template = new Template();
		template.setTitle("Teste");
		template.setFields(new ArrayList<Field>());

		template.getFields().add(new Field());
		template.getFields().get(0).setLabel("teste");
		template.getFields().get(0).setType("text");
		
		service.save(template);
		Assert.assertNotNull(template.getId());
		
		Template test = service.get(template.getId());
		Assert.assertEquals(template.getTitle(), test.getTitle());
		Assert.assertEquals(template.getFields().get(0).getLabel(), test.getFields().get(0).getLabel());
		Assert.assertEquals(template.getFields().get(0).getType(), test.getFields().get(0).getType());
		
		template.getFields().get(0).setType("number");
		service.save(template);
		
		test = service.get(template.getId());
		Assert.assertEquals(template.getTitle(), test.getTitle());
		Assert.assertEquals(template.getFields().get(0).getLabel(), test.getFields().get(0).getLabel());
		Assert.assertEquals(template.getFields().get(0).getType(), test.getFields().get(0).getType());
		
		String id = template.getId();
		boolean isTrue = service.delete(id);
		Assert.assertTrue(isTrue);
		
		try {
			service.get(id);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void testSave_Invalido() {
		Template template = null;
		
		try {
			service.save(template);
			Assert.assertTrue(false);
		} catch (TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		template = new Template();
		template.setTitle("Teste");
		template.setFields(new ArrayList<Field>());
		template.getFields().add(new Field());
		
		try {
			service.save(template);
			Assert.assertTrue(false);
		} catch (TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		template.getFields().get(0).setLabel("teste");
		template.getFields().get(0).setType("teste");
		
		try {
			service.save(template);
			Assert.assertTrue(false);
		} catch (TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		template.getFields().get(0).setLabel("teste");
		template.getFields().get(0).setType("radio");
		
		try {
			service.save(template);
			Assert.assertTrue(false);
		} catch (TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
	}
	
	@Test
	public void testUpdate_Invalido() throws TemplateException {
		String idError = null;
		Template templateError = null;
		
		Template templateOk = new Template();
		templateOk.setTitle("Teste");
		templateOk.setFields(new ArrayList<Field>());
		templateOk.getFields().add(new Field());
		templateOk.getFields().get(0).setLabel("teste");
		templateOk.getFields().get(0).setType("text");
		
		
		try {
			service.update(idError, templateOk);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		idError = "error";
		try {
			service.update(idError, templateOk);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		templateOk = service.save(templateOk);
		String idOk = templateOk.getId();
		
		try {
			service.update(idOk, templateError);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		templateError = new Template();
		
		try {
			service.update(idOk, templateError);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
	}
	
	@Test
	public void testGet_Invalido() {
		String id = null;

		try {
			service.get(id);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		id = "error";
		try {
			service.get(id);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
	}
	
	@Test
	public void testDelete_Invalido() {
		String id = null;

		try {
			service.delete(id);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		id = "error";
		try {
			service.delete(id);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
	}
	
	@Test
	public void testSaveGetUpdateData_ok() throws TemplateException {
		Template template = new Template();
		template.setTitle("Teste");
		template.setFields(new ArrayList<Field>());
		template.getFields().add(new Field());
		template.getFields().get(0).setLabel("teste");
		template.getFields().get(0).setType("text");

		template = service.save(template);
		String id = template.getId();
		
		Data data = new Data();
		data.put("teste", "ok");
		
		template = service.updateData(id, data);
		
		Template check = service.getData(id);
		Assert.assertEquals(data, check.getData().get(0));
		
		template.getFields().add(new Field());
		template.getFields().get(1).setLabel("numeros");
		template.getFields().get(1).setType("number");
		template = service.update(id, template);
		
		Data another = new Data();
		another.put("teste", 123);
		service.updateData(id, another);
		
		check = service.getData(id);
		Assert.assertEquals(another, check.getData().get(1));
	}
	
	@Test
	public void testGetData_Invalido() {
		String id = null;

		try {
			service.getData(id);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
		
		id = "error";
		try {
			service.getData(id);
			Assert.assertTrue(false);
		} catch(TemplateException e) {
			Assert.assertNotNull(e.getErro());
		}
	}
	
	@Test
	public void testUpdateData_Invalido() {
		Template template = new Template();
		template.setTitle("Teste");
		template.setFields(new ArrayList<Field>());
		template.getFields().add(new Field());
		template.getFields().get(0).setLabel("teste");
		template.getFields().get(0).setType("text");
		template.getFields().get(0).setMaxLength(Long.valueOf(10));

		try {
			template = service.save(template);
			Assert.assertTrue(true);
		} catch (TemplateException e) {
			Assert.assertTrue(false);
		}
		
		String id = template.getId();
		
		Data data = new Data();
		data.put("teste", "teste com mais de dez dígitos");
		
		try {
			service.updateData(id, data);
			Assert.assertTrue(false);
		} catch (TemplateException e) {
			Assert.assertTrue(true);
		}
	}
}