package danilo.estevam.netshoes.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import danilo.estevam.netshoes.exception.MultipleException;
import danilo.estevam.netshoes.exception.SingleException;
import danilo.estevam.netshoes.model.Cep;
import danilo.estevam.netshoes.repository.CepRepository;

@Service
public class CepService {

	@Autowired
	private CepRepository repository;
	
	public Cep getByCep(String code) throws SingleException {
		this.validateCep(code);
		Cep cep = repository.findByCep(code);
		
		if(cep == null)
			return this.replaceCepByZero(code);
		
		return cep;
	}
	
	public List<Cep> list() {
		return repository.findAll();
	}
	
	public Cep get(Cep cep) throws SingleException {
		this.validateCep(cep);
		return repository.findOne(cep.getId());
	}
	

	public Cep save(Cep cep) throws SingleException, MultipleException {
		return repository.save(cep);
	}
	
	
	private void validateCep(String cep) throws SingleException {
		if(StringUtils.isBlank(cep) || !NumberUtils.isNumber(cep) || cep.length() != 8 || NumberUtils.createInteger(cep) == 0)
			throw new SingleException("CEP invalido.");
	}
	
	private void validateCep(Cep cep) throws SingleException {
		if(cep == null || StringUtils.isBlank(cep.getId()))
			throw new SingleException("ID não informado.");
	}
	
	private Cep replaceCepByZero(String cep) throws SingleException {
		int countZerosNoFinal = 1;
		String[] chars = cep.split("");
		
		for(int i = chars.length - 1; i > 0; i--) {
			if(!chars[i].equals("0"))
				break;
			
			countZerosNoFinal++;
		}
		
		return this.getByCep(cep.substring(0, cep.length() - countZerosNoFinal) + StringUtils.leftPad("", countZerosNoFinal, "0"));
	}
}
