package danilo.estevam.netshoes.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danilo.estevam.netshoes.exception.Detail;
import danilo.estevam.netshoes.exception.MultipleException;
import danilo.estevam.netshoes.exception.SingleException;
import danilo.estevam.netshoes.model.Address;
import danilo.estevam.netshoes.model.Cep;
import danilo.estevam.netshoes.repository.AddressRepository;

@Service
public class AddressService {
	
	@Autowired
	private CepService cepService;
	
	@Autowired
	private AddressRepository repository;

	public Address get(Address address) throws SingleException {
		validateForGet(address);
		return repository.findOne(address.getId());
	}

	public Address save(Address address) throws SingleException, MultipleException {
		validateForSave(address);
		
		Cep cep = cepService.getByCep(address.getCep());
		if(cep == null) {
			cep = new Cep(address);
			cepService.save(cep);
		}
		
		address.setCep(cep);
		
		return repository.save(address);
	}

	public Address update(Address address) throws SingleException, MultipleException {
		Address check = this.get(address);
		return this.save(check);
	}

	public void delete(Address address) throws SingleException {
		Address check = this.get(address);
		repository.delete(check.getId());
	}

	
	private void validateForGet(Address address) throws SingleException {
		if(address == null || StringUtils.isBlank(address.getId()))
			throw new SingleException("ID inválido.");
	}
	
	private void validateForSave(Address address) throws SingleException {
		if(address == null)
			throw new SingleException("Endereço inválido.");
		
		List<Detail> details = new ArrayList<Detail>();
		
		if(StringUtils.isBlank(address.getLogradouro()))
			details.add(new Detail("Campo Logradouro é obrigatório."));
		
		if(address.getNumero() == null || address.getNumero() <= 0)
			details.add(new Detail("Campo Número é obrigatório."));
		
		if(StringUtils.isBlank(address.getCep()))
			details.add(new Detail("Campo CEP é obrigatório."));
		
		if(StringUtils.isBlank(address.getCidade()))
			details.add(new Detail("Campo Cidade é obrigatório."));
		
		if(StringUtils.isBlank(address.getEstado()))
			details.add(new Detail("Campo Estado é obrigatório."));
	}
}
