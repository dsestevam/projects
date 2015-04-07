package danilo.estevam.netshoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import danilo.estevam.netshoes.exception.MultipleException;
import danilo.estevam.netshoes.exception.SingleException;
import danilo.estevam.netshoes.exception.Detail;
import danilo.estevam.netshoes.model.Cep;
import danilo.estevam.netshoes.model.Address;
import danilo.estevam.netshoes.service.CepService;
import danilo.estevam.netshoes.service.AddressService;

@Controller
@RequestMapping(value = "/address")
public class AddressController {

	@Autowired
	private CepService cepService;
	
	@Autowired
	private AddressService adressService;
	
	
	@RequestMapping(value = "/cep", method = RequestMethod.GET)
	public @ResponseBody Cep getCep(@RequestParam String cep) throws SingleException {
		return cepService.getByCep(cep);
	}
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody Address get(@RequestBody Address address) throws SingleException, MultipleException {
		return adressService.get(address);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody Address save(@RequestBody Address address) throws SingleException, MultipleException {
		return adressService.save(address);
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public @ResponseBody Address update(@RequestBody Address address) throws SingleException, MultipleException {
		return adressService.update(address);
	}
	
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public @ResponseBody Address delete(@RequestBody Address address) throws SingleException {
		adressService.delete(address);
		return address;
	}
	
	
	@ExceptionHandler({ SingleException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Detail processException(SingleException e) {
		return e.getDetail();
	}
	
	@ExceptionHandler({ MultipleException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody List<Detail> processException(MultipleException e) {
		return e.getDetails();
	}
}
