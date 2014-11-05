package local.data.coletor.model;


public enum TypeEnum {
	text,
	color,
	date,
	datetime,
	datetime_local,
	email,
	month,
	number,
	tel,
	time,
	url,
	week,
	checkbox,
	radio;
	
	public static boolean contains(String check) {
		for(TypeEnum type : TypeEnum.values()) {
			if(type.toString().equals(check))
				return true;
		}
		
		return false;
	}
}
