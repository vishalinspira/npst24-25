package com.annexure.ten.monthly.util;



import com.daily.pfm.service.model.ReportII;
import com.daily.pfm.service.service.ReportIILocalServiceUtil;
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

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ParseReportTwo {
	public static JSONObject saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray reportiiJsonArray, List<ReportII> reportiis, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId) {
		
		ReportIILocalServiceUtil.deleteReportIIByReportUploadLogId(reportUploadLogId);
		String sheetName = "Monthly_Avg_Bal";
		try {
			if (file != null) {
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("Monthly_Avg_Bal");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					ReportII reportii = ReportIILocalServiceUtil.createReportII(CounterLocalServiceUtil.increment(ReportII.class.getName()));
					
					reportii.setReportUploadLogId(reportUploadLogId);
					reportii.setCreatedby(userId);
					reportii.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 10; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										try {
											Integer sr_no=Integer.parseInt(val);
											reportii.setSr_no(sr_no);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										Long nps_acc_number=Long.parseLong(val);
										reportii.setNps_acc_number(nps_acc_number);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									reportii.setNps_acc_name(val);
								} 
								else if (i == 3) {
									try {
										BigDecimal  avg_bal_week1 =  CommonParser.parseBigDecimal(val);
										reportii.setAvg_bal_week1(avg_bal_week1);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 4) {
									try {
										BigDecimal  avg_bal_week2 =  CommonParser.parseBigDecimal(val);
										reportii.setAvg_bal_week2(avg_bal_week2);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									try {
										BigDecimal  avg_bal_week3 =  CommonParser.parseBigDecimal(val);
										reportii.setAvg_bal_week3(avg_bal_week3);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 6) {
									try {
										BigDecimal  avg_bal_week4 =  CommonParser.parseBigDecimal(val);
										reportii.setAvg_bal_week4(avg_bal_week4);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										BigDecimal  avg_bal_week5 =  CommonParser.parseBigDecimal(val);
										reportii.setAvg_bal_week5(avg_bal_week5);
									} catch (Exception e) {
										_log.error(e);
									}
								}
								else if (i == 8) {
									try {
										BigDecimal  avg_bal_week6 =  CommonParser.parseBigDecimal(val);
										reportii.setAvg_bal_week6(avg_bal_week6);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 9) {
									try {
										reportii.setMonth(cell.getDateCellValue());
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
					reportii.setCreatedate(new Date());
						
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
						JSONObject reportiiJsonObject = JSONFactoryUtil.createJSONObject(reportii.toString());
						reportiiJsonArray.put(reportiiJsonObject);
						reportiis.add(reportii);
					}
					rowCount++;
				}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}
		}catch (Exception e) {
			_log.error(e);
		}
		return resultJsonObject;
	}
	static Log _log = LogFactoryUtil.getLog(ParseReportFour.class);
}
