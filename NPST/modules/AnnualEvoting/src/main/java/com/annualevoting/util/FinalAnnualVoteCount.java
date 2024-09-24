package com.annualevoting.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import compliance.service.model.AnFinalAnnualVoteCount;
import compliance.service.service.AnFinalAnnualVoteCountLocalServiceUtil;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import nps.common.service.util.CommonParser;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FinalAnnualVoteCount {
  private static Log _log = LogFactoryUtil.getLog(FinalAnnualVoteCount.class);
  
  public static JSONObject saveFinalAnnualVoteCount(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray finalAnnualVoteCountJsonArray, List<AnFinalAnnualVoteCount> finalAnnualVoteCountList, DecimalFormat decimalFormat, long reportUploadLogId, JSONObject resultJsonObject) {
    _log.info("Inside saveFinalAnnualVoteCount");
    AnFinalAnnualVoteCountLocalServiceUtil.deleteAnFinalAnnualVoteCountByReportUploadLogId(Long.valueOf(reportUploadLogId));
    //String sheetName = "Final Annual Vote Count";
    String sheetName =  "Ann Final Majority Vote Count";
    try {
      if (file != null) {
        OPCPackage pkg = OPCPackage.open(file);
        workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        Iterator<Row> rows = sheet.rowIterator();
        int rowCount = 1;
        int errorRowCount = 2;
        while (rows.hasNext()) {
          AnFinalAnnualVoteCount finalAnnualVoteCount = AnFinalAnnualVoteCountLocalServiceUtil.createAnFinalAnnualVoteCount(CounterLocalServiceUtil.increment(AnFinalAnnualVoteCount.class.getName()));
          finalAnnualVoteCount.setReportUploadLogId(reportUploadLogId);
          finalAnnualVoteCount.setCreatedby(userId);
          finalAnnualVoteCount.setPension_fund_name("");
          JSONObject errorObj = JSONFactoryUtil.createJSONObject();
          errorObj.put("rownum", rowCount);
          boolean isError = false;
          XSSFRow row = (XSSFRow)rows.next();
          XSSFRow errorRow = null;
          int lastColumn = Math.max(row.getLastCellNum(), 0);
          for (int i = 0; i < lastColumn; i++) {
            XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            DataFormatter formatter = new DataFormatter();
            String val = formatter.formatCellValue((Cell)cell);
            if (cell != null) {
              if (val != null)
                val = val.trim(); 
              if (rowCount > 1) {
                _log.info("Val: " + val);
                if (i == 0) {
                  if (val != null) {
                    finalAnnualVoteCount.setPension_fund_name(val);
                  } else {
                    errorObj.put("cellno", 2);
                    errorObj.put("msg", "It is not a number");
                    isError = true;
                  } 
                } else if (i == 1) {
                  finalAnnualVoteCount.setFinancial_year(val);
                } else if (i == 2) {
                  try {
                    Date date_1 = cell.getDateCellValue();
                    finalAnnualVoteCount.setQuarter(date_1);
                  } catch (Exception e) {
                    _log.info("error parsing date" + val);
                    resultJsonObject.put("status", false);
                    resultJsonObject.put("msg", CommonParser.dateExceptionMsg + sheetName);
                    return resultJsonObject;
                  } 
                } else if (i == 3) {
                  try {
                    BigDecimal bg = CommonParser.parseBigDecimal(val);
                    finalAnnualVoteCount
                      .setTotal_number_of_resolution(bg.stripTrailingZeros());
                  } catch (Exception e) {
                    _log.info("error parsing long" + val);
                    resultJsonObject.put("status", false);
                    resultJsonObject.put("msg", CommonParser.numberExceptionMsg + sheetName);
                    return resultJsonObject;
                  } 
                } else if (i == 4) {
                  try {
                    BigDecimal bg = CommonParser.parseBigDecimal(val);
                    finalAnnualVoteCount.setFor_(bg.stripTrailingZeros());
                  } catch (Exception e) {
                    _log.info("error parsing long" + val);
                    resultJsonObject.put("status", false);
                    resultJsonObject.put("msg", CommonParser.numberExceptionMsg + sheetName);
                    return resultJsonObject;
                  } 
                } else if (i == 5) {
                  try {
                    BigDecimal bg = CommonParser.parseBigDecimal(val);
                    finalAnnualVoteCount.setAgainst(bg.stripTrailingZeros());
                  } catch (Exception e) {
                    _log.info("error parsing long" + val);
                    resultJsonObject.put("status", false);
                    resultJsonObject.put("msg", CommonParser.numberExceptionMsg + sheetName);
                    return resultJsonObject;
                  } 
                } else if (i == 6) {
                  try {
                    BigDecimal bg = CommonParser.parseBigDecimal(val);
                    finalAnnualVoteCount.setAbstain(bg.stripTrailingZeros());
                  } catch (Exception e) {
                    _log.info("error parsing long" + val);
                    resultJsonObject.put("status", false);
                    resultJsonObject.put("msg", CommonParser.numberExceptionMsg + sheetName);
                    return resultJsonObject;
                  } 
                } 
              } 
            } else if (i == 0 && rowCount > 1) {
              // Byte code: goto -> 1007
            } 
          } 
          finalAnnualVoteCount.setCreatedon(new Date());
          if (isError && rowCount > 1) {
            errorArray.put(errorObj);
            errorRow = xx.createRow(errorRowCount);
            errorRowCount++;
            XSSFCell rowCountCel = errorRow.createCell(1);
            rowCountCel.setCellValue(rowCount);
            XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
            cellError.setCellValue(errorObj.getString("msg"));
            isexcelhaserror = true;
          } else if (rowCount > 1) {
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject(finalAnnualVoteCount.toString());
            finalAnnualVoteCountJsonArray.put(jsonObject);
            finalAnnualVoteCountList.add(finalAnnualVoteCount);
          } 
          rowCount++;
        } 
      } 
    } catch (Exception e) {
      _log.error(e);
    } 
    return resultJsonObject;
  }
}
