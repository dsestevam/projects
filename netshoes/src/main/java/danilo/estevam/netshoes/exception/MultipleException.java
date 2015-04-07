package danilo.estevam.netshoes.exception;

import java.util.List;

@SuppressWarnings("serial")
public class MultipleException extends Exception {
	private List<Detail> details;
	
	public MultipleException(List<Detail> details) {
		super();
		this.details = details;
	}

	public List<Detail> getDetails() {
		return details;
	}
}
