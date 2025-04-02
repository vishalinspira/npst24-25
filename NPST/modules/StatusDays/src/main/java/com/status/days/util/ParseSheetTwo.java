package com.status.days.util;



import com.daily.average.service.model.StatusDaysOne;
import com.daily.average.service.service.StatusDaysOneLocalServiceUtil;
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

public class ParseSheetTwo {
	static Log _log = LogFactoryUtil.getLog(ParseSheetTwo.class);
	public static JSONObject saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray statusDaysOneJsonArray,List<StatusDaysOne> statusdaysones, DecimalFormat decimalFormat, String cra,long reportUploadLogId) {
		_log.info("saveSheetTwo::::::::::::");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Pending Data_NPS");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					StatusDaysOne statusDaysOne = StatusDaysOneLocalServiceUtil.createStatusDaysOne(CounterLocalServiceUtil.increment(StatusDaysOne.class.getName()));
							
					statusDaysOne.setCreatedby(userId);
					statusDaysOne.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 20; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
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
											statusDaysOne.setSr_no(sr_no);
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
									try {
										statusDaysOne.setToken_number(val);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 2) {
									try {
										Long pran = CommonParser.parseLong(val);
										statusDaysOne.setPran_id_created_by(pran);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										Date date= cell.getDateCellValue();
										statusDaysOne.setCurrent_dates(date);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 4) {
									statusDaysOne.setStatus(val);
								}
								else if (i == 5) {
									try {
										Date date= cell.getDateCellValue();
										statusDaysOne.setGrievance_logging_date(date);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 6) {
									statusDaysOne.setRaised_by(val);
								} 
								else if (i == 7) {
									statusDaysOne.setPao_reg_no(val);
								} 	
								else if (i == 8) {
									statusDaysOne.setPao_name(val);
								}
								else if (i == 9) {
									statusDaysOne.setPr_ao_reg_no(val);
								} 
								else if (i == 10) {
									statusDaysOne.setPr_ao_name(val);
								} 	
								else if (i == 11) {
									statusDaysOne.setSector(val);
								}
								else if (i == 12) {
									statusDaysOne.setSector_type(val);
								}
								else if (i == 13) {
									statusDaysOne.setMinistry_name(val);
								} 
								else if (i == 14) {
									statusDaysOne.setState_name(val);
								}
								else if (i == 15) {
									statusDaysOne.setBroad_category(val);
								}
								else if (i == 16) {
									statusDaysOne.setGrievance_text(val);
								} 
								else if (i == 17) {
									try {
										Date date= cell.getDateCellValue();
										statusDaysOne.setFollowup_action_1(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//statusDaysOne.setFollowup_action_1(cell.getDateCellValue());
									}
								} 	
								else if (i == 18) {
									try {
										Date date= cell.getDateCellValue();
										statusDaysOne.setFollowup_action_2(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//statusDaysOne.setFollowup_action_2(cell.getDateCellValue());
									}
								}
								else if (i == 19) {
									
									
								} 
								 
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					statusDaysOne.setCreatedate(new Date());
					statusDaysOne.setCra(cra);
					statusDaysOne.setReportUploadLogId(reportUploadLogId);
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
						//JSONObject statusDaysOneJsonObject = JSONFactoryUtil.createJSONObject(statusDaysOne.toString());
						//statusDaysOneJsonArray.put(statusDaysOneJsonObject);
						statusdaysones.add(statusDaysOne);
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
