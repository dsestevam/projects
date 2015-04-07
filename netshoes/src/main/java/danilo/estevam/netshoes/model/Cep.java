package danilo.estevam.netshoes.model;

public class Cep {

	private String id;
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;

	
	public Cep() {
		super();
	}
	
	public Cep(Address address) {
		this.cep = address.getCep();
		this.logradouro = address.getLogradouro();
		this.bairro = address.getBairro();
		this.cidade = address.getCidade();
		this.estado = address.getEstado();
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
