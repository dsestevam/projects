package danilo.estevam.netshoes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import danilo.estevam.netshoes.model.Cep;

public interface CepRepository extends MongoRepository<Cep, String> {

	@Query(value="{cep:?0}")
	public Cep findByCep(String cep);

}
