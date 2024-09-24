package com.monthly.pfm.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ValidateSheetName {
	
	public static List<String> ValidateExcelSheetName(File file, XSSFWorkbook workbook){
		
		List<String> errorList = new ArrayList<String>();
		
//		errorList.add("Form 1");
//		errorList.add("Form 2");
//		errorList.add("Form 3");
//		errorList.add("Form 4 ATAL PENSION YOJANA");
//		errorList.add("Form 4 CORP CG");
//		errorList.add("Form 4 NPS LITE");
//		errorList.add("Form 4 CG");
//		errorList.add("Form 4 SG");
//		errorList.add("Form 5 C-I");
//		errorList.add("Form 6 E-I");
//		errorList.add("Form 7A A-T I");
//		errorList.add("Form 8_as_on_date");
//		errorList.add("Form 8_during_the_month");
//		errorList.add("Form 9");
//		errorList.add("10 Industry");
//		errorList.add("Form_11_Sponsor");
//		errorList.add("12 issuer");
//		errorList.add("13 Portfolio Data");
//		errorList.add("14 Transaction Data");
		
		
		errorList.add("Form 1");
		errorList.add("Form_4");
		errorList.add("Form 2A");
		errorList.add("Form 2 APY");	
		errorList.add("Form 2 Corp CG");
		errorList.add("Form 2 NPS Lite");
		errorList.add("Form 2 CG");
		errorList.add("Form 2 SG");
		errorList.add("Form 2 C-I");
		errorList.add("Form 2 C-II");
		errorList.add("Form 2 E-I");
		errorList.add("Form 2 E-II");
		errorList.add("Form 2 G-I");
		errorList.add("Form 2 G-II");
		errorList.add("Form 2 A-T I");
		//errorList.add("Form 2 TTS");
		errorList.add("Form 2 Other schemes");
		errorList.add("Form 6_as_on_date");
		errorList.add("Form 6_during_the_month");
		errorList.add("Form 7");
		errorList.add("Form 8_Industry");
		errorList.add("Form 8_Non_Sponsored");
		errorList.add("Form 8_Sponsor");
		errorList.add("Form 8_issuer");
		errorList.add("Form 2 Portfolio Data");
		errorList.add("Form 3 Transactions Data");
		errorList.add("Form_9");
		errorList.add("Form 5 Group_PF Associates");
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				
				Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
				
				while(sheetIterator.hasNext()) {
					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
					
					if(errorList.contains(sheets.getSheetName())) {
						errorList.remove(sheets.getSheetName());
					}
				}
			}
		} catch (Exception e) {
			 _log.error(e);
		}
		
		return errorList;
	}
	static Log _log = LogFactoryUtil.getLog(ValidateSheetName.class);

}
