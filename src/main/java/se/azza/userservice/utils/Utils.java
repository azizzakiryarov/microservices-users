package se.azza.userservice.utils;

public class Utils {
	
	public static boolean isNumeric(String strNum) {
	    return strNum.matches("-?\\d+(\\.\\d+)?");
	}
}
