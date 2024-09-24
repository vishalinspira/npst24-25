package com.quarterlysubmissionofforms.util;

import com.daily.pfm.service.model.QuarterlySubCompForms;
import com.daily.pfm.service.service.QuarterlySubCompFormsLocalServiceUtil;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ParseForm1b {

private static Log _log = LogFactoryUtil.getLog(ParseForm1A.class);
	
	public static JSONObject saveSheetForm1b(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, 
			XSSFSheet xx, boolean isexcelhaserror, JSONArray subCompFormsJsonArray, List<QuarterlySubCompForms> subCompFormsList,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("saveSheetForm1A::::::::::::::::::::::");
		QuarterlySubCompFormsLocalServiceUtil.deleteQuarterlySubCompFormsByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Form_2");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
			//	QuarterlyForm1A subCompForms = QuarterlyForm1ALocalServiceUtil.createQuarterlyForm1A(CounterLocalServiceUtil.increment(QuarterlyForm1A.class.getName()));
					QuarterlySubCompForms subCompForms = QuarterlySubCompFormsLocalServiceUtil.createQuarterlySubCompForms(CounterLocalServiceUtil.increment(QuarterlySubCompForms.class.getName()));
					subCompForms.setReportUploadLogId(reportUploadLogId);
					subCompForms.setCreatedby(userId);
						
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
												subCompForms.setDate_(cell.getDateCellValue());
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
										subCompForms.setPension_fund_name(val);
									}
									else if (i == 2) {
										subCompForms.setScheme_name(val);
										
									} 
									else if (i == 3) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms.setSno(bg.stripTrailingZeros());
											} catch (Exception e) {
												_log.info("error parsing big dec"+val);
												 resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										
									} 
									else if (i == 4) {
										subCompForms.setType_purchase_or_sale(val);
										
									} 
									else if (i == 5) {
										subCompForms.setCategory_of_investment(val);
										
									}
									else if (i == 6) {
										subCompForms.setIsin(val);
										
									} 
									else if (i == 7) {
										subCompForms.setName_of_security_or_company(val);
										
										
									}else if(i == 8) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms.setBook_value_qtr(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 9) {
										//try {
											//BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms.setPrcnt_of_portfolio_value_qtr(val);
										/*} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}*/
										
									} else if (i == 10){
										try {
											subCompForms.setDate_of_purchase_or_sale_qtr(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									
									
								}
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						subCompForms.setCreatedon(new Date());
						
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
							subCompFormsList.add(subCompForms);
						}
						rowCount++;
					}
				_log.info("isexcelhaserror ::::::::::"+isexcelhaserror);
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
