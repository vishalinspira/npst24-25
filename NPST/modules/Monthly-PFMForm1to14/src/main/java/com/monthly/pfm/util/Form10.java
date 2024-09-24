package com.monthly.pfm.util;

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

import compliance.service.model.MnForm10;
import compliance.service.service.MnForm10LocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form10 {
	
private static Log _log = LogFactoryUtil.getLog(Form10.class);
	
	public static JSONObject saveForm10(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form10JsonArray, List<MnForm10> form10List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm10");
		MnForm10LocalServiceUtil.deleteMnForm10ByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 8_Industry";
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
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					MnForm10 form10 = MnForm10LocalServiceUtil.
							createMnForm10(CounterLocalServiceUtil.increment(MnForm10.class.getName()));
					
					form10.setReportUploadLogId(reportUploadLogId);
					form10.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					int lastColumn = Math.max(row.getLastCellNum(), 0);
					
					for (int i = 0; i < lastColumn; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								//_log.info("rowCount: " + rowCount);
								//_log.info("Val: " + val);
								if (i == 0) {
									if (val != null) {
										form10.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										
										Date date_1 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form10.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form10.setScheme_name(val);
								} 
								else if (i == 3) {
									form10.setNic_code(val);
								} 
								else if (i == 4) {
									form10.setNic_industry_desc(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form10.setPermissible_limit_of_industry(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form10.setActual_investments(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form10.setDeviation(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									form10.setPercentage_of_deviation(val);
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form10.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form10.toString());
							form10JsonArray.put(jsonObject);
							form10List.add(form10);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}catch (Exception e) {
				 _log.error(e);
			}
		return resultJsonObject;
	}

}
