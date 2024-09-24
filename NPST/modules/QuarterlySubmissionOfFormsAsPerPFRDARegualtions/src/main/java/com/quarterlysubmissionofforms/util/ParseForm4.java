package com.quarterlysubmissionofforms.util;

import com.daily.pfm.service.model.QuarterlySubCompForms_4;
import com.daily.pfm.service.service.QuarterlySubCompForms_4LocalServiceUtil;
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

public class ParseForm4 {

private static Log _log = LogFactoryUtil.getLog(ParseForm1A.class);
	
	public static JSONObject saveSheetForm4(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, 
			boolean isexcelhaserror, JSONArray subCompForms_4JsonArray, List<QuarterlySubCompForms_4> subCompForms_4List,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("saveSheetForm1A::::::::::::::::::::::");

		
		QuarterlySubCompForms_4LocalServiceUtil.deleteQuarterlySubCompForms_4ByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Form 4");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
			//	QuarterlyForm1A subCompForms_4 = QuarterlyForm1ALocalServiceUtil.createQuarterlyForm1A(CounterLocalServiceUtil.increment(QuarterlyForm1A.class.getName()));
					QuarterlySubCompForms_4 subCompForms_4 = QuarterlySubCompForms_4LocalServiceUtil.createQuarterlySubCompForms_4(CounterLocalServiceUtil.increment(QuarterlySubCompForms_4.class.getName()));
					subCompForms_4.setReportUploadLogId(reportUploadLogId);
					subCompForms_4.setCreatedby(userId);
						
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
												BigDecimal bg = CommonParser.parseBigDecimal(val);
												subCompForms_4.setSr_no(bg.stripTrailingZeros());
											} catch (Exception e) {
												_log.info("error parsing big dec"+val);
												 resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
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
										//subCompForms_4.setPension_fund_code(val);
										subCompForms_4.setPension_fund_name(val);
									}
									else if (i == 2) {
										try {
											subCompForms_4.setReporting_date(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 3) {
										subCompForms_4.setScheme_name(val);
									} 
									else if (i == 4) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_4.setMarket_value_as_on_quarter(bg.stripTrailingZeros());
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
											subCompForms_4.setMarket_value_as_on_prev_qrtr(bg.stripTrailingZeros());
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
											subCompForms_4.setMarket_val_as_on_2nd_prev_qrtr(bg.stripTrailingZeros());
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
											subCompForms_4.setMarket_val_as_on_3rd_prev_qrtr(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 8) {
										/*try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);*/
											subCompForms_4.set_3_year_rolling_cagr(val);
										/*} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}*/
									}
									else if (i == 9) {
										/*try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);*/
											subCompForms_4.setAnnualized_portfolio_ret_yield(val);
										/*} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}*/
									} 
									/*else if (i == 10) {
										
									}*/
									
								}
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						subCompForms_4.setCreatedon(new Date());
						
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
							subCompForms_4List.add(subCompForms_4);
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
