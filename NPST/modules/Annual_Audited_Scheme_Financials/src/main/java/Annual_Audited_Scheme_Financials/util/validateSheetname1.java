package Annual_Audited_Scheme_Financials.util;

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


public class validateSheetname1 {
	
	private static Log _log = LogFactoryUtil.getLog(validateSheetname1.class);
	
public static List<String> ValidateExcelSheetName(File file, XSSFWorkbook workbook){
		
		List<String> errorList = new ArrayList<String>();
		
		errorList.add("Consolidated Balance Sheet");
		errorList.add("Notes of Combined Balance Sheet");
		errorList.add("Combined Income Statement");
		errorList.add("Balance Sheet");
		errorList.add("Income Statement");
		errorList.add("Note of Balance Sheet");
		errorList.add("Key Statistics");
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				
				Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
				
				while(sheetIterator.hasNext()) {
					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
					_log.info("name of the sheet ---> "+sheets.getSheetName());
					if(errorList.contains(sheets.getSheetName())) {
						errorList.remove(sheets.getSheetName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return errorList;
	}
}
