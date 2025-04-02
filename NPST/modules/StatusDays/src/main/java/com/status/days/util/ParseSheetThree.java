package com.status.days.util;


import com.daily.average.service.model.StatusDaysTwo;
import com.daily.average.service.service.StatusDaysTwoLocalServiceUtil;
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

public class ParseSheetThree {
	static Log _log = LogFactoryUtil.getLog(ParseSheetThree.class);
	public static JSONObject saveSheetThree(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray statusDaysTwoJsonArray,List<StatusDaysTwo> statusdaystwos, DecimalFormat decimalFormat, String cra,long reportUploadLogId ) {
		_log.info("saveSheetThree::::::::::::");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Pending Data_APY");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					StatusDaysTwo statusDaysTwo = StatusDaysTwoLocalServiceUtil.createStatusDaysTwo(CounterLocalServiceUtil.increment(StatusDaysTwo.class.getName()));
					statusDaysTwo.setCra(cra);
					statusDaysTwo.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 17; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							//_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										try {
											Integer sr_no= Integer.parseInt(val);
											statusDaysTwo.setSr_no(sr_no);
										} catch (Exception e) {
											_log.info("error parsing integer"+val);
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
									if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										statusDaysTwo.setToken_number(null);
									}else {
										statusDaysTwo.setToken_number(val);
									}
								}
								else if (i == 2) {
									try {
										long pran = CommonParser.parseLong(val);
										statusDaysTwo.setPran_id_created_by(pran);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										Date date= cell.getDateCellValue();
										statusDaysTwo.setCurrent_dates(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//statusDaysTwo.setCurrent_dates(cell.getDateCellValue());
									}
								}
								else if (i == 4) {
									statusDaysTwo.setStatus(val);
								}
								else if (i == 5) {
									try {
										Date date= cell.getDateCellValue();
										statusDaysTwo.setGrievance_logging_date(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//statusDaysTwo.setGrievance_logging_date(cell.getDateCellValue());
									}
								}
								else if (i == 6) {
									statusDaysTwo.setRaised_by(val);
								} 
								else if (i == 7) {
									if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										statusDaysTwo.setNlao_reg_no(null);
									}else {
										statusDaysTwo.setNlao_reg_no(val);
									}
								} 	
								else if (i == 8) {
									statusDaysTwo.setNlao_name(val);
								}
								else if (i == 9) {
									if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										statusDaysTwo.setNloo_reg_no(null);
									}else {
										statusDaysTwo.setNloo_reg_no(val);
									}
								} 
								else if (i == 10) {
									statusDaysTwo.setNloo_name(val);
								} 	
								else if (i == 11) {
									statusDaysTwo.setSector(val);
								}
								else if (i == 12) {
									statusDaysTwo.setBroad_category(val);
								}
								else if (i == 13) {
									statusDaysTwo.setGrievance_text(val);
								} 
								else if (i == 14) {
									try {
										Date date= cell.getDateCellValue();
										statusDaysTwo.setFollowup_action_1(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//statusDaysTwo.setFollowup_action_1(cell.getDateCellValue());
									}
								} 	
								else if (i == 15) {
									try {
										Date date= cell.getDateCellValue();
										statusDaysTwo.setFollowup_action_2(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//statusDaysTwo.setFollowup_action_2(cell.getDateCellValue());
									}
								}
								 
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					statusDaysTwo.setCreatedate(new Date());
					statusDaysTwo.setCra(cra);
					statusDaysTwo.setReportUploadLogId(reportUploadLogId);
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
						//JSONObject statusDaysTwoJsonObject = JSONFactoryUtil.createJSONObject(statusDaysTwo.toString());
						//statusDaysTwoJsonArray.put(statusDaysTwoJsonObject);
						statusdaystwos.add(statusDaysTwo);
						
					}
					rowCount++;
				}
				
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

