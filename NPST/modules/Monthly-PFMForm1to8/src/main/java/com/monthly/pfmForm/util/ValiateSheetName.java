package com.monthly.pfmForm.util;

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

public class ValiateSheetName {

	private static Log _log = LogFactoryUtil.getLog(ValiateSheetName.class);
	
public static List<String> validateExcelSheetName(File file, XSSFWorkbook workbook){
		
		List<String> errorList = new ArrayList<String>();
		
		errorList.add("Overall Status Sector wise");
		errorList.add("Overall Status of State Govt");
		errorList.add("Status of Central Government");
		errorList.add("Status of Individual Subscriber");
		errorList.add("Status of Corporate sector");
		errorList.add("Status of NPS Lite");
		errorList.add("Status of Atal Pension Scheme");
		
		
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
	
}
