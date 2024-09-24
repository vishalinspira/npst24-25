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

import compliance.service.model.MnForm1;
import compliance.service.service.MnForm1LocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form1 {
	
private static Log _log = LogFactoryUtil.getLog(Form1.class);
	
	public static JSONObject saveForm1(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form1JsonArray, List<MnForm1> form1List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm1");
		MnForm1LocalServiceUtil.deleteMnForm1ByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 1";
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
					MnForm1 form1 = MnForm1LocalServiceUtil.
							createMnForm1(CounterLocalServiceUtil.increment(MnForm1.class.getName()));
					
					form1.setReportUploadLogId(reportUploadLogId);
					form1.setCreatedby(userId);
					
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
								//_log.info("Val: " + val);
								if (i == 0) {
									if (val != null) {
										form1.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										Date date_1 = cell.getDateCellValue(); // new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form1.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form1.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form1.setAum_pfm(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form1.setAum_cra(bg.stripTrailingZeros());
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									//_log.info("cell2" + cell.getCellType());
									try {
										String s = String.valueOf(cell.getNumericCellValue());
										BigDecimal bg = CommonParser.parseBigDecimal(s);
										form1.setAum_diff(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
										 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form1.setUnits_outstanding_pfm(bg.stripTrailingZeros());
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form1.setUnits_outstanding_cra(bg.stripTrailingZeros());
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									//_log.info("cell2" + cell.getCellType());
									try {
										String s = String.valueOf(cell.getNumericCellValue());
										BigDecimal bg = CommonParser.parseBigDecimal(s);
										form1.setUnits_outstanding_diff(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
										 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
						form1.setCreatedon(new Date());
						
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
							form1List.add(form1);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}catch (Exception e) {
				 _log.error(e);
				 resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
					return resultJsonObject;
			}
		
		return resultJsonObject;
	}

}
