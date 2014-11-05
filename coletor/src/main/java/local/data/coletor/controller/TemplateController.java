package local.data.coletor.controller;

import java.util.List;

import local.data.coletor.exception.Erro;
import local.data.coletor.exception.TemplateException;
import local.data.coletor.model.Data;
import local.data.coletor.model.Template;
import local.data.coletor.service.TemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/templates")
public class TemplateController {
	
	@Autowired
	private TemplateService service;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody List<Template> getTemplates() {
		return service.list();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Template getTemplateById(@PathVariable String id) throws TemplateException {
		return service.get(id);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody Template saveTemplate(@RequestBody Template template) throws TemplateException {
		return service.save(template);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Template updateTemplateById(@PathVariable String id, @RequestBody Template template) throws TemplateException {
		return service.update(id, template);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean deteleTemplateById(@PathVariable String id) throws TemplateException {
		return service.delete(id);
	}
	
	@RequestMapping(value = "/{id}/data", method = RequestMethod.GET)
	public @ResponseBody Template getTemplateDataById(@PathVariable String id) throws TemplateException {
		return service.getData(id);
	}
	
	@RequestMapping(value = "/{id}/data", method = RequestMethod.POST)
	public @ResponseBody Template updateTemplateDataById(@PathVariable String id, @RequestBody Data data) throws TemplateException {
		return service.updateData(id, data);
	}
	
	
	@ExceptionHandler({ TemplateException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Erro processException(TemplateException e) {
		return e.getErro();
	}
}
