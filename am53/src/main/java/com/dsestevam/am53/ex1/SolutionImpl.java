package com.dsestevam.am53.ex1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SolutionImpl implements Solution {

	public boolean isUnique(String input) {
		//Agrupar cada caractere em um Set, que não permite nenhum registro repetido.
		Set<String> check = new HashSet<String>(Arrays.asList(input.split("")));
		
		//Validação se o tamanho do set é igual à quantidade de caracteres do input. 
		return check.size() == input.length();
	}
}
