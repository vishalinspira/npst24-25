package com.quarterlysubmissionofforms.util;

import com.daily.pfm.service.model.QuarterlySubCompForms_3;
import com.daily.pfm.service.service.QuarterlySubCompForms_3LocalServiceUtil;
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

public class ParseForm3Scheme {
	
	private static Log _log = LogFactoryUtil.getLog(ParseForm3Scheme.class);
	
	public static JSONObject saveSheetForm3(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, 
			boolean isexcelhaserror, JSONArray subCompForms_3JsonArray, List<QuarterlySubCompForms_3> subCompForms_3List,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("ParseForm1A::::::::::::::::::::::");
		
		QuarterlySubCompForms_3LocalServiceUtil.deleteQuarterlySubCompForms_3ByReportUploadLogId(reportUploadLogId);
		//subCompForms_3LocalServiceUtil.deletesubCompForms_3ByReportUploadLogId(reportUploadLogId);
		//QuarterlySubForm1ALocalServiceUtil.deleteQuarterlyForm1AByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
				//	XSSFSheet sheets = workbook.getSheet("Form 1A");
					Iterator<Sheet> sheets = workbook.sheetIterator();
					while(sheets.hasNext()) {
							XSSFSheet sheet = (XSSFSheet) sheets.next();
							
							String sheetName = sheet.getSheetName();
							boolean sheet1 = sheetName.equalsIgnoreCase("FORM 3 Scheme - A - Tier -  I");
							boolean sheet2 = sheetName.equalsIgnoreCase("FORM 3 Scheme -TAX-T2");
							boolean sheet3 = sheetName.equalsIgnoreCase("FORM 3 Scheme - E - Tier - I");
							boolean sheet4 = sheetName.equalsIgnoreCase("FORM 3 Scheme - E - Tier - II");
							boolean sheet5 = sheetName.equalsIgnoreCase("FORM 3 Scheme - C - Tier - I");
							boolean sheet6 = sheetName.equalsIgnoreCase("FORM 3 Scheme - C - Tier - II");
							boolean sheet7 = sheetName.equalsIgnoreCase("FORM 3 Scheme - G - Tier - I");
							boolean sheet8 = sheetName.equalsIgnoreCase("FORM 3 Scheme - G - Tier - II");
							boolean sheet9 = sheetName.equalsIgnoreCase("FORM 3 Scheme - CG");
							boolean sheet10 = sheetName.equalsIgnoreCase("FORM 3 Scheme - SG");
							boolean sheet11 = sheetName.equalsIgnoreCase("FORM 3 Scheme - Corp CG");
							boolean sheet12 = sheetName.equalsIgnoreCase("FORM 3 Scheme - APY");
							boolean sheet13 = sheetName.equalsIgnoreCase("FORM 3 Scheme - NPS Lite");
								
							if(sheet1 || sheet2 || sheet3 || sheet4 ||
								sheet5|| sheet6 || sheet7 || sheet8 || sheet9 || sheet10 || sheet11 || sheet12 || sheet13 && sheets!=null) {	
								_log.info("Inside if loop in second sheet");
								Iterator<Row> rows = sheet.rowIterator();
								int rowCount = 1;
								int errorRowCount = 2;
						
						rowLoop:
						while (rows.hasNext()) {
							_log.info("Inside while loop in second sheet");
						//	subCompForms_3 subCompForms_3 = subCompForms_3LocalServiceUtil.createsubCompForms_3(CounterLocalServiceUtil.increment(subCompForms_3.class.getName()));
							QuarterlySubCompForms_3 subCompForms_3 = QuarterlySubCompForms_3LocalServiceUtil.createQuarterlySubCompForms_3(CounterLocalServiceUtil.increment(QuarterlySubCompForms_3.class.getName())) ;
							subCompForms_3.setReportUploadLogId(reportUploadLogId);
							subCompForms_3.setCreatedby(userId);
								
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
													//subCompForms_3.setPension_fund_code(val);
													subCompForms_3.setPension_fund_name(val);
												}
												 else {
														errorObj.put("cellno", 2);
														errorObj.put("msg", "It is not a number");
														isError = true;
													}
											}
											else if (i == 1) {
												subCompForms_3.setScheme_name(val);
											}
											else if (i == 2) {
												try {
													subCompForms_3.setReporting_date(cell.getDateCellValue());
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 3) {
												subCompForms_3.setParticulars(val);
											} 
											else if (i == 4) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setOpening_bal_units(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 5) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setOpening_bal_amount(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 6) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setPurchase_units(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 7) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setPurchase_book_value(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 8) {
												subCompForms_3.setPur_prcnt_of_total_portfolio(val);
											}
											else if (i == 9) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setSales_units(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 10) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setSales_book_value(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 11) {
												subCompForms_3.setSales_prcnt_of_total_portfolio(val);
											}
											else if (i ==12) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setClosing_bal_units(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 13) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setClosing_bal_book_value(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} else if (i == 14) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													subCompForms_3.setClosing_bal_market_value(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing big dec"+val);
													 resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 15) {
												subCompForms_3.setClosingbal_prcnt_tot_portfolio(val);
											} 
											/*else if (i == 16) {
												
											} */
											
										}
									}else if(i==0 && rowCount > 1) {
										break rowLoop;
									}
								}
								subCompForms_3.setCreatedon(new Date());
								
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
									subCompForms_3List.add(subCompForms_3);
								}
								rowCount++;
							}
								rowCount = 1;
						}
							_log.info("isexcelhaserror ::::::::::"+isexcelhaserror);
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
