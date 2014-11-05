package local.data.coletor.repository;

import java.util.List;

import local.data.coletor.model.Template;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TemplateRepository extends MongoRepository<Template, String> {

	@Query(value="{},{data:0}")
	public List<Template> findTemplate();
	
	@Query(value="{_id:?0},{data:0}")
	public Template findTemplateById(String id);
	
	@Query(value="{_id:?0},{_id:0, title:0}")
	public Template findTemplateDataById(String id);
}
