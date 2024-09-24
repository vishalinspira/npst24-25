package Cra_Compliance_Report_NPST_Trust.utils;

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
		errorList.add("Status of Withdrawals reported");
		errorList.add("Special provision on Wthdrwl");
		errorList.add("Subs Annuity Purchase");
		errorList.add("Withdrawal Related Subs Griev");
		errorList.add("Reported Wthdrwl Aging Analysis");
		errorList.add("Griev Wthdrwl Aging Analysis");
		
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
	static Log _log = LogFactoryUtil.getLog(ValidateSheetName.class);

}
