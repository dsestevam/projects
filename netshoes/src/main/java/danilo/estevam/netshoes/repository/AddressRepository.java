package danilo.estevam.netshoes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import danilo.estevam.netshoes.model.Address;

public interface AddressRepository extends MongoRepository<Address, String> {

}
