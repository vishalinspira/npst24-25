package com.investment.management.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ValidateSheetName {
	
public static List<String> ValidateExcelSheetName(File file, XSSFWorkbook workbook, List<String> errorList){
		
		
		
		/*errorList.add("IMF Scheme Quarterly Report");
		errorList.add("MGMT RATE");
		errorList.add("IMF Scheme Summary");
		errorList.add("IMF Scheme E I");
		errorList.add("IMF Scheme G I");
		errorList.add("IMF Scheme C I");
		errorList.add("IMF Scheme E II");
		errorList.add("IMF Scheme C II");
		errorList.add("IMF Scheme G II");
		errorList.add("IMF Scheme A I");
		errorList.add("IMF Scheme Tax T II");*/
		
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
