package com.annualevoting.util;

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
  public static List<String> ValidateExcelSheetName(File file, XSSFWorkbook workbook) {
    List<String> errorList = new ArrayList<>();
   // errorList.add("Final Annual Vote Count");
    //errorList.add("Annual PF Voting Recomm");
    
    errorList.add("Ann Final Majority Vote Count");
    errorList.add("Annual PF Voting Recomm");
    try {
      if (file != null) {
        OPCPackage pkg = OPCPackage.open(file);
        workbook = new XSSFWorkbook(pkg);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
          XSSFSheet sheets = (XSSFSheet)sheetIterator.next();
          _log.info("sheet names: " + sheets.getSheetName());
          if (errorList.contains(sheets.getSheetName()))
            errorList.remove(sheets.getSheetName()); 
        } 
      } 
    } catch (Exception e) {
      _log.error(e);
    } 
    return errorList;
  }
  
  static Log _log = LogFactoryUtil.getLog(ValidateSheetName.class);
}
