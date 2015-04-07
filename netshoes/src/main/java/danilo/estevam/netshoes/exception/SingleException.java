package danilo.estevam.netshoes.exception;

@SuppressWarnings("serial")
public class SingleException extends Exception {
	private Detail detail;
	
	public SingleException(String message) {
		super();
		this.detail = new Detail(message);
	}

	public Detail getDetail() {
		return detail;
	}
}
