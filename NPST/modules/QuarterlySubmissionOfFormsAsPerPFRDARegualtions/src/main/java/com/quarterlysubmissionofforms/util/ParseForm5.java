package com.quarterlysubmissionofforms.util;

import com.daily.pfm.service.model.QuarterlySubCompForms_5;
import com.daily.pfm.service.service.QuarterlySubCompForms_5LocalServiceUtil;
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

public class ParseForm5 {

private static Log _log = LogFactoryUtil.getLog(ParseForm1A.class);
	
	public static JSONObject saveSheetForm5(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, 
			boolean isexcelhaserror, JSONArray subCompForms_5JsonArray, List<QuarterlySubCompForms_5> subCompForms_5List,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("saveSheetForm1A::::::::::::::::::::::");
	//	QuarterlyForm1ALocalServiceUtil.deleteQuarterlyForm1AByReportUploadLogId(reportUploadLogId);
		QuarterlySubCompForms_5LocalServiceUtil.deleteQuarterlySubCompForms_5ByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Form 5");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
			//	QuarterlyForm1A subCompForms_5 = QuarterlyForm1ALocalServiceUtil.createQuarterlyForm1A(CounterLocalServiceUtil.increment(QuarterlyForm1A.class.getName()));
					QuarterlySubCompForms_5 subCompForms_5 = QuarterlySubCompForms_5LocalServiceUtil.createQuarterlySubCompForms_5(CounterLocalServiceUtil.increment(QuarterlySubCompForms_5.class.getName()));
					subCompForms_5.setReportUploadLogId(reportUploadLogId);
					subCompForms_5.setCreatedby(userId);
						
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
											//subCompForms_5.setPension_fund_code(val);
											subCompForms_5.setPension_fund_name(val);
										}
										 else {
												errorObj.put("cellno", 2);
												errorObj.put("msg", "It is not a number");
												isError = true;
											}
									}
									else if (i == 1) {
										try {
											subCompForms_5.setReporting_date(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 2) {
										subCompForms_5.setScheme_name(val);
									} 
									else if (i == 3) {
										subCompForms_5.setSecurity_name(val);
									} 
									else if (i == 4) {
										subCompForms_5.setInstrument_type(val);
									} 
									else if (i == 5) {
										subCompForms_5.setInterest_rate_prcnt(val);
									}
									else if (i == 6) {
										subCompForms_5.setHas_there_been_revision(val);
									} 
									else if (i == 7) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_5.setTotal_otstndgbook_value(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 8) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_5.setDefault_principal_bookvalue(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 9) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_5.setDefault_interest_bookvalue(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 10) {
										try {
											subCompForms_5.setPrincipal_due_from(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 11) {
										try {
											subCompForms_5.setInterest_due_from(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 12) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_5.setDeferred_principal(bg.stripTrailingZeros());
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
											subCompForms_5.setDeferred_interest(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 14) {
										subCompForms_5.setRolled_over(val);
									} 
									else if (i == 15) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_5.setPrincipal_waiver_amt(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 16) {
										subCompForms_5.setPrin_waiver_board_apprv_ref(val);
									}
									else if (i == 17) {
										subCompForms_5.setClassification(val);
									} 
									else if (i == 18) {
										subCompForms_5.setProvision_prcnt(val);
									}
									else if (i == 19) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_5.setProvision_amount(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									/*else if (i == 20) {
										
									}*/
									
									
								}
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						subCompForms_5.setCreatedon(new Date());
						
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
							subCompForms_5List.add(subCompForms_5);
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
