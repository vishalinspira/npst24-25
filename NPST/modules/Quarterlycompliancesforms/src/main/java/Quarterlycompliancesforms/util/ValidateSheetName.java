package Quarterlycompliancesforms.util;

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
	static Log _log = LogFactoryUtil.getLog(ValidateSheetName.class);
	public static List<String> ValidateExcelSheetName(File file, XSSFWorkbook workbook){
		List<String> errorList = new ArrayList<String>();
		
		//errorList.add("FORM_1A_1B"); //previously (form 1a,1b) was only one form
	//	errorList.add("Form 1A");
		errorList.add("FORM 1B");
		errorList.add("FORM 2 -Scheme -TAX-T2");
		errorList.add("FORM 2 - Scheme - A - Tier- I");
		errorList.add("FORM 2 - Scheme - E - Tier - I");
		errorList.add("FORM 2 - Scheme - E - Tier - II");
		errorList.add("FORM 2 - Scheme - C - Tier - I");
		errorList.add("FORM 2 - Scheme - C - Tier - II");
		errorList.add("FORM 2 - Scheme - G - Tier - I");
		errorList.add("FORM 2 - Scheme - G - Tier - II");
		errorList.add("FORM 3 Scheme - A - Tier -  I");
		errorList.add("FORM 3 Scheme -TAX-T2");
		errorList.add("FORM 3 Scheme - E - Tier - I");
		errorList.add("FORM 3 Scheme - E - Tier - II");
		errorList.add("FORM 3 Scheme - C - Tier - I");
		errorList.add("FORM 3 Scheme - C - Tier - II");
		errorList.add("FORM 3 Scheme - G - Tier - I");
		errorList.add("FORM 3 Scheme - G - Tier - II");
		errorList.add("Form4");
		errorList.add("Form5");
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				
				Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
				
				while(sheetIterator.hasNext()) {
					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
					
					_log.info("sheet names: " + sheets.getSheetName());
					
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
