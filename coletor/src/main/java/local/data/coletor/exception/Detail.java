package local.data.coletor.exception;

public class Detail {
	private String message;
	private String category;
	
	public Detail(String message, String category) {
		this.message = message;
		this.category = category;
	}

	public String getMessage() {
		return message;
	}

	public String getCategory() {
		return category;
	}
}
