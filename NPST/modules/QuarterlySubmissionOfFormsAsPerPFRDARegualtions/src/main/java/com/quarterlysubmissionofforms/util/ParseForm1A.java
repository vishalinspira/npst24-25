package com.quarterlysubmissionofforms.util;

import com.daily.pfm.service.model.QuarterlySubForm1A;
import com.daily.pfm.service.service.QuarterlySubForm1ALocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ParseForm1A {
	
	private static Log _log = LogFactoryUtil.getLog(ParseForm1A.class);
	
	public static JSONObject saveSheetForm1A(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray qrForm1AJsonArray, List<QuarterlySubForm1A> quarterlySubForm1AList,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("ParseForm1A::::::::::::::::::::::");
		
			QuarterlySubForm1ALocalServiceUtil.deleteQuarterlyForm1AByReportUploadLogId(reportUploadLogId);
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
				//	XSSFSheet sheets = workbook.getSheet("Form 1A");
					Iterator<Sheet> sheets = workbook.sheetIterator();
					while(sheets.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets.next();
						
						String sheetName = sheet.getSheetName();
						boolean sheet1 = sheetName.equalsIgnoreCase("Form_1");
//						boolean sheet1 = sheetName.equalsIgnoreCase("Form 1A");
//						boolean sheet2 = sheetName.equalsIgnoreCase("Form 1A_2");
//						boolean sheet3 = sheetName.equalsIgnoreCase("Form 1A_3");
//						boolean sheet4 = sheetName.equalsIgnoreCase("Form 1A_4");
//						boolean sheet5 = sheetName.equalsIgnoreCase("Form 1A_5");
//						boolean sheet6 = sheetName.equalsIgnoreCase("Form 1A_6");
//						boolean sheet7 = sheetName.equalsIgnoreCase("Form 1A_7");
//						boolean sheet8 = sheetName.equalsIgnoreCase("Form 1A_8");
//						boolean sheet9 = sheetName.equalsIgnoreCase("Form 1A_9");
//						boolean sheet10 = sheetName.equalsIgnoreCase("Form 1A_10");
						
//						if(sheet1 || sheet2 || sheet3 || sheet4 ||
//							sheet5|| sheet6 || sheet7 || sheet8 || sheet9 ||sheet10  && sheets!=null) {	
						if(sheet1  && sheets!=null) {	

//							_log.info("Inside if loop in second sheet");
							Iterator<Row> rows = sheet.rowIterator();
							int rowCount = 1;
							int errorRowCount = 2;
					
					rowLoop:
					while (rows.hasNext()) {
						_log.info("Inside while loop in second sheet");
						QuarterlySubForm1A subForm1A = QuarterlySubForm1ALocalServiceUtil.createQuarterlySubForm1A(CounterLocalServiceUtil.increment(QuarterlySubForm1A.class.getName()));
					       
						subForm1A.setReportUploadLogId(reportUploadLogId);
						subForm1A.setCreatedby(userId);
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							int lastColumn = Math.max(row.getLastCellNum(), 0);
							
							for (int i = 0; i < lastColumn; i++) {
								XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								DataFormatter formatter = new DataFormatter();
								String val = formatter.formatCellValue(cell);
								if (cell != null) {
									if (val != null)
										val = val.trim();
									if(rowCount > 1) {
										if (i == 0) {
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												try {
													subForm1A.setDate_(cell.getDateCellValue());
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											 else {
													errorObj.put("cellno", 2);
													errorObj.put("msg", "It is not a number");
													isError = true;
												}
										}
										else if (i == 1) {
											subForm1A.setPension_fund_name(val);
										}
										else if (i == 2) {
											try {
												BigDecimal bg = CommonParser.parseBigDecimal(val);
												subForm1A.setSno(bg.stripTrailingZeros());
											} catch (Exception e) {
												_log.info("error parsing big dec"+val);
												 resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 3) {
											subForm1A.setType_purchase_or_sale(val);
										} 
										else if (i == 4) {
											subForm1A.setCategory_of_investment(val);
										} 
										else if (i == 5) {
											subForm1A.setIsin(val);
										}
										else if (i == 6) {
											subForm1A.setName_of_security_or_company(val);
										} 
										else if (i == 7) {
											try {
												BigDecimal bg = CommonParser.parseBigDecimal(val);
												subForm1A.setBook_value_qtr(bg.stripTrailingZeros());
											} catch (Exception e) {
												_log.info("error parsing big dec"+val);
												 resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 8) {
												subForm1A.setPrcnt_of_portfolio_value_qtr(val);
										} 
										else if (i == 9) {
											try {
												subForm1A.setDate_of_purchase_or_sale_qtr(cell.getDateCellValue());
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 10) {
											subForm1A.setName_(val);
										} 
										else if (i == 11) {
											subForm1A.setDesignation(val);
										} 
										else if (i == 12) {
											//subForm1A.setDesignation(val);
										} 
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							subForm1A.setCreatedon(new Date());
							
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
								//JSONObject formoneJsonObject = JSONFactoryUtil.createJSONObject(subForm1A.toString());
								//qrForm1AJsonArray.put(formoneJsonObject);
								quarterlySubForm1AList.add(subForm1A);
							}
							rowCount++;
						}
							_log.info("isexcelhaserror ::::::::::"+isexcelhaserror);
							rowCount = 1;
					}
				}
			}
			}catch (Exception e) {
				_log.error(e);
				resultJsonObject.put("status", false);
				resultJsonObject.put("msg",  CommonParser.fileExceptionMsg);
				return resultJsonObject;
			}
			return resultJsonObject;
	}

}
