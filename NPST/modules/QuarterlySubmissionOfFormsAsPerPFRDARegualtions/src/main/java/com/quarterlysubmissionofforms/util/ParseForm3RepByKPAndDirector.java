package com.quarterlysubmissionofforms.util;

import com.daily.pfm.service.model.Form3RepByDirAndKP;
import com.daily.pfm.service.model.QuarterlySubForm1A;
import com.daily.pfm.service.service.Form3RepByDirAndKPLocalServiceUtil;
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

public class ParseForm3RepByKPAndDirector {
	
	private static Log _log = LogFactoryUtil.getLog(ParseForm3RepByKPAndDirector.class);
	
	public static JSONObject saveSheetForm3Rep(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx,
			boolean isexcelhaserror, JSONArray form3RepByDirAndKPJsonArray, List<Form3RepByDirAndKP> form3RepByDirAndKPList,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("ParseForm1A::::::::::::::::::::::");
		
		Form3RepByDirAndKPLocalServiceUtil.deleteForm3RepByDirAndKPByReportUploadLogId(reportUploadLogId);
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
					boolean sheet1 = sheetName.equalsIgnoreCase("Investment During the Period");
					boolean sheet2 = sheetName.equalsIgnoreCase("Self Dealing or Front Running");
					
					if(sheet1 || sheet2  && sheets!=null) {	
						_log.info("Inside if loop in second sheet");
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					_log.info("Inside while loop in second sheet");
					Form3RepByDirAndKP form3RepByDirAndKP = Form3RepByDirAndKPLocalServiceUtil.createForm3RepByDirAndKP(CounterLocalServiceUtil.increment(Form3RepByDirAndKP.class.getName()));
				       
					form3RepByDirAndKP.setReportUploadLogId(reportUploadLogId);
					form3RepByDirAndKP.setCreatedby(userId);
						
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
												form3RepByDirAndKP.setDate_(cell.getDateCellValue());
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
										form3RepByDirAndKP.setPension_fund_name(val);
									}
									else if (i == 2) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											form3RepByDirAndKP.setSno(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 3) {
										form3RepByDirAndKP.setType_purchase_or_sale(val);
									} 
									else if (i == 4) {
										form3RepByDirAndKP.setName_of_security(val);
									} 
									else if (i == 5) {
										form3RepByDirAndKP.setIsin(val);
									}
									else if (i == 6) {
										form3RepByDirAndKP.setName_of_issuer_or_company(val);
									} 
									else if (i == 7) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											form3RepByDirAndKP.setTransaction_cost(bg);
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 8) {
										try {
											form3RepByDirAndKP.setDate_of_transaction(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 9) {
										form3RepByDirAndKP.setName_(val);
									} 
									else if (i == 10) {
										form3RepByDirAndKP.setDesignation(val);
									} else if (i == 11) {
										form3RepByDirAndKP.setName_(val);
									} 
									
								}
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						form3RepByDirAndKP.setCreatedon(new Date());
						
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
							form3RepByDirAndKPList.add(form3RepByDirAndKP);
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
