package com.quarterlysubmissionofforms.util;

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
	private static Log _log = LogFactoryUtil.getLog(ValidateSheetName.class);
	
	public static List<String> ValidateExcelSheetName(File file, XSSFWorkbook workbook, List<String> errorList){
		
//		List<String> errorList = new ArrayList<String>();
//		
//		errorList.add("Pfs_TDS_Challan");
//		errorList.add("Pfs_Deductee_Detail");
		
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
		_log.info("errorList"+errorList);
		return errorList;
	}

}
