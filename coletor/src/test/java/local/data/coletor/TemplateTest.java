package local.data.coletor;

import java.util.ArrayList;

import junit.framework.Assert;
import local.data.coletor.config.AppConfig;
import local.data.coletor.exception.TemplateException;
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
	public void testSave_TemplateInvalido() {
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
	}
}
