package local.data.coletor.exception;

@SuppressWarnings("serial")
public class TemplateException extends Exception {
	private Erro erro;
	
	public TemplateException(Erro erro) {
		super();
		this.erro = erro;
	}

	public Erro getErro() {
		return erro;
	}
}
