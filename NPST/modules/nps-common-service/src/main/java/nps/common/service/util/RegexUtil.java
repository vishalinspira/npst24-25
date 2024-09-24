package nps.common.service.util;

public class RegexUtil {

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String removeStartingDigits(String str) {
		char[] charArray=str.toCharArray();
		boolean flag=false;
		String updateStr="";
		for(char character:charArray) {
			if((!Character.isDigit(character) && character!='_') || flag) {
				flag=true;
				updateStr=updateStr+character;
			}
		}
		return updateStr;
	}
}
