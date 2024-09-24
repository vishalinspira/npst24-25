package nps.common.service.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class CommonParser {
	
	public static String bigDecimalPattern = "#,##0.0#";
	public static String dateFormat = "dd-MM-yyyy";
	
	public static String dateExceptionMsg = "Error while parsing date in ";
	public static String numberExceptionMsg = "Error while parsing numer in ";
	public static String fileExceptionMsg = "Error while parsing file ";
	
	public static Date parseDate(String val, XSSFCell cell) throws Exception {
		Date date = null;
		try {
			 date =	new SimpleDateFormat("dd-MM-yyyy").parse(val);
		} catch (Exception e) {
				throw new Exception("Error parsing date");
		}
		
		return date;
	}
	
	public static BigDecimal parseBigDecimal(String val) throws ParseException {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat(bigDecimalPattern,symbols);
		decimalFormat.setParseBigDecimal(true);
		return  (BigDecimal) decimalFormat.parse(val);
	}
	
	public static Long parseLong(String val) throws NumberFormatException {
		return Long.parseLong(val);
	}
	
}
