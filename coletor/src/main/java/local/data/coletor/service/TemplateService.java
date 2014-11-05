package local.data.coletor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import local.data.coletor.exception.Detail;
import local.data.coletor.exception.Erro;
import local.data.coletor.exception.TemplateException;
import local.data.coletor.model.Data;
import local.data.coletor.model.Field;
import local.data.coletor.model.Template;
import local.data.coletor.model.TypeEnum;
import local.data.coletor.repository.TemplateRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

	@Autowired
	private TemplateRepository repository;
	
	/**
	 * Obter a lista de todos os templates cadastrados
	 */
	public List<Template> list() {
		return repository.findTemplate();
	}

	/**
	 * Obter um template específico através do id
	 * @param id
	 * @return o template obtido
	 * @throws TemplateException
	 */
	public Template get(String id) throws TemplateException {
		this.check(id);
		Template template = repository.findTemplateById(id);
		this.check(template);
		
		return template;
	}
	
	/**
	 * Cadastrar um novo template
	 * @param template
	 * @return o novo template cadastrado
	 * @throws TemplateException
	 */
	public Template save(Template template) throws TemplateException {
		this.check(template);
		repository.save(template);
		
		return template;
	}
	
	/**
	 * Atualizar um template através do id
	 * @param id
	 * @param template
	 * @return o template atualizado
	 * @throws TemplateException
	 */
	public Template update(String id, Template template) throws TemplateException {
		this.get(id);
		this.check(template);
		
		template.setId(id);
		repository.save(template);
		
		return template;
	}
	
	/**
	 * Excluir um template através do id
	 * @param id
	 * @return true se excluido com sucesso.
	 * @throws TemplateException
	 */
	public boolean delete(String id) throws TemplateException {
		Template template = this.get(id);
		repository.delete(template);
		
		return true;
	}
	
	public Template getData(String id) throws TemplateException {
		this.check(id);
		Template template = repository.findTemplateDataById(id);
		
		if(template == null) {
			List<Detail> erros = new ArrayList<Detail>();
			erros.add(new Detail("Template não encontrado", "template"));
			throw new TemplateException(new Erro(erros));
		}
		
		return template;
	}
	
	public Template updateData(String id, Data data) throws TemplateException {
		this.check(id);
		Template template = repository.findOne(id);
		this.check(template, data);
		
		if(template.getData() == null)
			template.setData(new ArrayList<Data>());
		
		template.getData().add(data);
		repository.save(template);
		
		
		return template;
	}
	
	
	private void check(String id) throws TemplateException {
		List<Detail> erros = new ArrayList<Detail>();
		if(StringUtils.isBlank(id)) {
			erros.add(new Detail("ID não pode ser nulo", "id"));
			throw new TemplateException(new Erro(erros));
		}
	}
	
	private void check(Template template) throws TemplateException {
		List<Detail> erros = new ArrayList<Detail>();
		
		if(template == null) {
			erros.add(new Detail("Template não encontrado", "template"));
			throw new TemplateException(new Erro(erros));
		}
		
		
		if(StringUtils.isBlank(template.getTitle()))
			erros.add(new Detail("O título não pode ser nulo", "title"));
		
		if(template.getFields() == null || template.getFields().isEmpty()) {
			erros.add(new Detail("Deve conter pelo menos um campo", "fields"));
			throw new TemplateException(new Erro(erros));
		}
		
		for(Field field : template.getFields()) {
			if(field == null)
				erros.add(new Detail("O campo não pode ser nulo", "field"));
			else {
				if(StringUtils.isBlank(field.getLabel()))
					erros.add(new Detail("O label não pode ser nulo", "field.label"));
				
				if(StringUtils.isBlank(field.getType()))
					erros.add(new Detail("O tipo não pode ser nulo", "field.type"));
				else if(!TypeEnum.contains(field.getType()))
					erros.add(new Detail("O tipo possui valor inválido", "field.type"));
				
				if("radio".equals(field.getType()) && (field.getRadios() == null || field.getRadios().isEmpty()))
					erros.add(new Detail("Os radios não podem estar vazios para campos tipo radio", "field.radios"));
			}
		}
		
		
		if(!erros.isEmpty())
			throw new TemplateException(new Erro(erros));
	}
	
	
	private void check(Template template, Data data) throws TemplateException {
		List<Detail> erros = new ArrayList<Detail>();
		if(template == null) {
			erros.add(new Detail("Template não encontrado", "template"));
			throw new TemplateException(new Erro(erros));
		}
		
		if(template.getFields() == null || template.getFields().isEmpty()) {
			erros.add(new Detail("Deve conter pelo menos um campo", "fields"));
			throw new TemplateException(new Erro(erros));
		}
		
		//Validar campos existentes
		Map<String, Field> mapFields = new HashMap<String, Field>();
		for(Field field : template.getFields()) {
			mapFields.put(field.getLabel(), field);
			
			if(!data.containsKey(field.getLabel()) && field.getRequired()) {
				erros.add(new Detail("Campo não encontrado", field.getLabel()));
			} else if(data.containsKey(field.getLabel())) {
				Object value = data.get(field.getLabel());
				if(value != null) {
					if(data.toString().length() > field.getMaxLength())
						erros.add(new Detail("Limite de tamanho excedido", field.getLabel()));
					
					if(field.getType().equals(TypeEnum.number.toString()) && !NumberUtils.isNumber(value.toString()))
						erros.add(new Detail("Campo deve ser numérico", field.getLabel()));
					
				}
			}
		}
		
		for(String label : data.keySet()) {
			if(!mapFields.containsKey(label))
				erros.add(new Detail("Campo inexistente", "field." + label));
		}
		
		
		if(!erros.isEmpty())
			throw new TemplateException(new Erro(erros));
	}
}
