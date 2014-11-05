package local.data.coletor.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Field implements Serializable {
	private String label;
	private String type;
	private Boolean required;
	private Boolean readOnly;
	private String value;
	private Long maxLength;
	private String placeholder;
	private List<Radio> radios;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getRequired() {
		if(required == null) return false;
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getReadOnly() {
		if(readOnly == null) return false;
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public List<Radio> getRadios() {
		return radios;
	}

	public void setRadios(List<Radio> radios) {
		this.radios = radios;
	}
}
