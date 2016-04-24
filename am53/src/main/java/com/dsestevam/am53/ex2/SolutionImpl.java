package com.dsestevam.am53.ex2;

import java.util.Map;
import java.util.TreeMap;

public class SolutionImpl implements Solution {

	public int candy(int[] ratings) {
		
		//Separar notas por numero de repetições por ordem de nota.
		Map<Integer, Integer> check = new TreeMap<Integer, Integer>(); 
		
		for(Integer rating : ratings) {
			if(!check.containsKey(rating))
				check.put(rating, 0);
			
			Integer newValue = check.get(rating) + 1;
			check.put(rating, newValue); 
		}
		
		
		//Calcular quantidade de doces multiplicando cada número de repetição pelo fator de iteração.
		int response = 0;
		int factor = 1;
		
		for(Integer key : check.keySet()) {
			response += (check.get(key) * factor);
			factor++;
		}
		
		return response;
	}

}
