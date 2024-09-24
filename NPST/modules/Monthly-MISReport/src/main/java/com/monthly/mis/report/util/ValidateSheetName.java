package com.monthly.mis.report.util;

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
		
		errorList.add("NPSLite Category wise grievance");
		errorList.add("NPSLite Entity wise ageing");
		errorList.add("NPSLite_Scheme_Month_Analysis");
		errorList.add("NPS_Scheme_Month_Analysis");
		errorList.add("Referral Handled at NPS");
		errorList.add("Resolution Speed Analysis");
		errorList.add("Total_Grievance_Received_Resolv");
		errorList.add("Entity wise ageing Analysis");
		errorList.add("EntityWise Outstanding Analysis");
		errorList.add("Grievances_Outstanding");	
		errorList.add("NPS Category wise ageing");
		errorList.add("NPS Category wise grievance");	
		errorList.add("NPS Entity wise ageing");
		errorList.add("NPSLite Category wise ageing");
		
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
