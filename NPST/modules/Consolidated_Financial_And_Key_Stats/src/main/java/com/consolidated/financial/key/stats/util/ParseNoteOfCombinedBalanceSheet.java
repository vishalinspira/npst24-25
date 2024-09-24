package com.consolidated.financial.key.stats.util;

import com.daily.pfm.service.model.NoteOfCombinedBalanceSheet;
import com.daily.pfm.service.service.NoteOfCombinedBalanceSheetLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ParseNoteOfCombinedBalanceSheet {

	private static Log _log = LogFactoryUtil.getLog(ParseNoteOfCombinedBalanceSheet.class);
	public static JSONObject saveNoteOfCombinedBalanceSheet (File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,
			DecimalFormat decimalFormat, JSONArray noteOfComibnedBalanceSheetJsonArray, List<NoteOfCombinedBalanceSheet> noteOfComibnedBalanceSheetList,long reportUploadLogId) {
		_log.info(" <<< ConsolidatedBalanceSheet saveConsolidatedBalanceSheet >>>");
		String sheetName="Notes of Combined Balance Sheet";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				for (int g=1; g<sheet.getPhysicalNumberOfRows() -1; g++) {
					NoteOfCombinedBalanceSheet noteOfCombinedBalanceSheet = NoteOfCombinedBalanceSheetLocalServiceUtil.createNoteOfCombinedBalanceSheet(CounterLocalServiceUtil.increment(NoteOfCombinedBalanceSheet.class.getName()));
					noteOfCombinedBalanceSheet.setReportUploadLogId(reportUploadLogId);
					noteOfCombinedBalanceSheet.setCreatedby(userId);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						XSSFRow row = sheet.getRow(g);
						XSSFRow errorRow = null;
						
						int lastColumn = Math.max(row.getLastCellNum(), 0);
						
						for (int i = 0; i < lastColumn; i++) {
							XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info("val::::::::::::::::::::::"+val);
							if (cell != null) {
								if (val != null)
									val = val.trim();
								if(rowCount >= 1) {
									if (i == 0) {
										try {
											Date dateFormat= CommonParser.parseDate(val,cell);
											noteOfCombinedBalanceSheet.setAs_on_date(dateFormat);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 1) {
										noteOfCombinedBalanceSheet.setPension_fund_name(val);
									}
									else if (i == 2) {
										noteOfCombinedBalanceSheet.setSno(val);
									} 
									else if (i == 3) {
										
										noteOfCombinedBalanceSheet.setParticulars(val);
									} 
									else if (i == 4) {
										noteOfCombinedBalanceSheet.setSchedule(val);
									} 
									else if (i == 5) {
										try {
											BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
											noteOfCombinedBalanceSheet.setCurrent_year(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 6) {
										try {
											BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
											noteOfCombinedBalanceSheet.setPrevious_tear(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
								}
							}else if(i==0 && rowCount >= 1) {
								break rowLoop;
							}
						}
						noteOfCombinedBalanceSheet.setCreatedon(new Date());
						
						_log.info("rowCount ::::::::::: ******************************** "+rowCount);
						
						if (isError && rowCount >= 1) {
							errorArray.put(errorObj);
							errorRow = xx.createRow(errorRowCount);
							errorRowCount++;
							XSSFCell rowCountCel = errorRow.createCell(1);
							rowCountCel.setCellValue(rowCount);
							XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
							cellError.setCellValue(errorObj.getString("msg"));
							isexcelhaserror = true;
						} else if (rowCount >= 1) {
							JSONObject formoneJsonObject = JSONFactoryUtil.createJSONObject(noteOfCombinedBalanceSheet.toString());
							noteOfComibnedBalanceSheetJsonArray.put(formoneJsonObject);
							_log.info("noteOfCombinedBalanceSheet :::: "+noteOfComibnedBalanceSheetJsonArray.toString());
							noteOfComibnedBalanceSheetList.add(noteOfCombinedBalanceSheet);
						}
						rowCount++;
					}
				}

		}catch (Exception e) {
				 _log.error(e);
			}
		return resultJsonObject;
	}
}
