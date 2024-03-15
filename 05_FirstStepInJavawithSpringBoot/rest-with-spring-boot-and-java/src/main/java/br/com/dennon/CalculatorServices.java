package br.com.dennon;

import br.com.dennon.exceptions.UnsupportedMathOperationException;

public class CalculatorServices {
	
	public static Double convertToDouble(String strNumber) {
		if (strNumber == null) return 0D;
		
		String number = strNumber.replaceAll(",", ".");
		if (isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}

	public static boolean isNumeric(String strNumber) {
		if(strNumber == null) return false;
		String number = strNumber.replaceAll(",", ".");
		
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
	public static void validationNumber(String n1, String n2) {
		if (n2 != null) {
			if (!isNumeric(n1) || !isNumeric(n2)) throw new UnsupportedMathOperationException("Please set a numerec value!");
		}
		else {
			if (!isNumeric(n1)) throw new UnsupportedMathOperationException("Please set a numerec value!");
		}
	}
	
	public static Double sum(String n1, String n2) {
		return convertToDouble(n1) + convertToDouble(n2);
	}
	
	public static Double sub(String n1, String n2) {
		return convertToDouble(n1) - convertToDouble(n2);
	}
	
	public static Double mul(String n1, String n2) {
		return convertToDouble(n1) * convertToDouble(n2);
	}
	
	public static Double div(String n1, String n2) {
		return convertToDouble(n1) / convertToDouble(n2);
	}
	
	public static Double avg(String n1, String n2) {
		return (convertToDouble(n1) + convertToDouble(n2)) / 2.0;
	}
	
	public static Double raiz(String number) {
		return Math.sqrt(convertToDouble(number));
	}
}
