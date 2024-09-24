package com.report.director.kp.util;

import com.daily.pfm.service.model.Form3ReportByDirAndKp;
import com.daily.pfm.service.service.Form3ReportByDirAndKpLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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


public class Form3Sheets {
	private static Log _log = LogFactoryUtil.getLog(Form3Sheets.class);

	public static JSONObject saveSheetForm3(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray form3reportJsonArray, List<Form3ReportByDirAndKp> form3List,long reportUploadLogId) {
		_log.info("saveSheetForm3   ::::::::::::  ");
		Form3ReportByDirAndKpLocalServiceUtil.deleteForm3ReportByDirAndKpByReportUploadLogId(reportUploadLogId); 
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();

					String sheetName = sheet.getSheetName();
					boolean sheet1 = sheetName.equalsIgnoreCase("Investment During the Period");
					boolean sheet2 = sheetName.equalsIgnoreCase("Self Dealing or Front Running");

					if(sheet1 || sheet2 && sheet!=null) {	

						Iterator<Row> rows = sheet.rowIterator();
						
						int rowCount = 2;
						int errorRowCount = 2;

						rowLoop:
							while (rows.hasNext()) {
								
							//	_log.info("Inside while loop in  sheet");

								Form3ReportByDirAndKp form3rep = Form3ReportByDirAndKpLocalServiceUtil.createForm3ReportByDirAndKp(CounterLocalServiceUtil.increment(Form3ReportByDirAndKp.class.getName()));
								form3rep.setReportUploadLogId(reportUploadLogId);    
								form3rep.setCreatedby(userId);

								JSONObject errorObj = JSONFactoryUtil.createJSONObject();
								errorObj.put("rownum", rowCount);
								boolean isError = false;
								XSSFRow row = (XSSFRow) rows.next();
								
								XSSFRow errorRow = null;

								int lastColumn = Math.max(row.getLastCellNum(), 0);

								forloopend:
								for (int i = 0; i < lastColumn; i++) {
									
									XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
									if (cell != null) {

										DataFormatter formatter = new DataFormatter();
										String val = formatter.formatCellValue(cell);
										if (val != null)
											val = val.trim();
										if(rowCount > 1) {
											//_log.info("val   ::::::::::::  "+val);
											if(val.trim().equalsIgnoreCase("Date")) {
												rowCount++;
												break forloopend;
											}
												
											if (i == 0) {
												
												if (val != null && Validator.isNotNull(val) && val.length() > 0) {
													try {
														Date dateFormat= CommonParser.parseDate(val,cell);
														form3rep.setDate(dateFormat);
													} catch (Exception e) {
														_log.info("error parsing date"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
														return resultJsonObject;
													}
												}

											}
											else if (i == 1) {

												form3rep.setPension_fund_name(val);
											}
											else if (i == 2) {
												try {
													BigDecimal sl = CommonParser.parseBigDecimal(val);
													form3rep.setSno(sl.stripTrailingZeros());;
												} catch (Exception e) {
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 3) {
												form3rep.setType_purchase_or_sale(val);
											} 
											else if (i == 4) {
												form3rep.setName_of_security(val);
											} 
											else if (i == 5) {
												form3rep.setIsin(val);
											} 
											else if (i == 6) {
												form3rep.setName_of_issuer_or_company(val);
											} 
											else if (i == 7) {
												try {
													BigDecimal trxnCost = CommonParser.parseBigDecimal(val);
													form3rep.setTransaction_cost(trxnCost);
												} catch (Exception e) {
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 8) {
												try {
													Date dtrxn= CommonParser.parseDate(val,cell);
													form3rep.setDate_of_transaction(dtrxn);
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 9) {
												form3rep.setName(val);
											}
											else if (i == 10) {
												form3rep.setDesignation(val);
											}

										}else if(i==0 && rowCount > 1 ){
											break rowLoop;
										}
									}
									form3rep.setCreatedon(new Date());

									if (isError && rowCount > 1) {
										errorArray.put(errorObj);
										errorRow = xx.createRow(errorRowCount);
										errorRowCount++;
										XSSFCell rowCountCel = errorRow.createCell(1);
										rowCountCel.setCellValue(rowCount);
										XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
										cellError.setCellValue(errorObj.getString("msg"));
										isexcelhaserror = true;
									} else if (rowCount > 2) {
										JSONObject form3JsonObject = JSONFactoryUtil.createJSONObject(form3rep.toString());
										form3reportJsonArray.put(form3JsonObject);
										form3List.add(form3rep);
									}
									rowCount++;
								}
								//rowCount = 1;
							}

					}
				}
			}
		}
		catch (Exception e) {
			 _log.error(e);
		}
		return resultJsonObject;
	}

}
