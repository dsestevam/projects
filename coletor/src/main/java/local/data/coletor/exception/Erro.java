package local.data.coletor.exception;

import java.util.List;

public class Erro {
	private List<Detail> errors;
	
	public Erro(List<Detail> errors) {
		this.errors = errors;
	}

	public List<Detail> getErrors() {
		return errors;
	}
}
