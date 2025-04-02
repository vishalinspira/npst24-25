package com.asp.file.upload.util;

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
		errorList.add("ASP Report");
		errorList.add("Edelweiss Tokio");
		errorList.add("Bajaj Allianz");
		errorList.add("Canara HSBC");
		errorList.add("HDFC Life");
		errorList.add("ICICI Prudential");
		errorList.add("Indian Life");
		errorList.add("Kotak Life");
		errorList.add("LIC OF India");
		errorList.add("Max Life");
		errorList.add("SBI Life");
		errorList.add("Star Union");
		errorList.add("TATA AIA");
		
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				
				Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
				
				while(sheetIterator.hasNext()) {
					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
					_log.info("sheet names: " + sheets.getSheetName());
					if(errorList.contains(sheets.getSheetName())) {
						_log.info("sheet names: " + sheets.getSheetName());
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
