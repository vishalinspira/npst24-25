package com.griev.pending.util;

import com.daily.pfm.service.model.GrievPendingTwo;
import com.daily.pfm.service.service.GrievPendingTwoLocalServiceUtil;
import com.griev.pending.constants.GrievMasterConstants;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
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

public class ParseGrievPendingSheetTwo {
	
	static Log _log = LogFactoryUtil.getLog(ParseGrievPendingSheetTwo.class);
	public static JSONObject saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,JSONArray grievPendingTwoJsonArray, List<GrievPendingTwo> grievPendingTwos, DecimalFormat decimalFormat, String cra, long reportUploadLogId) {
		_log.info("inside saveSheetTwo:::::::");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(2);
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					GrievPendingTwo grievPendingTwo = GrievPendingTwoLocalServiceUtil.createGrievPendingTwo(CounterLocalServiceUtil.increment(GrievPendingTwo.class.getName()));
							
					grievPendingTwo.setCreatedby(userId);
					grievPendingTwo.setCra(cra);
					grievPendingTwo.setReportUploadLogId(reportUploadLogId);
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 21; i++) {
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
											grievPendingTwo.setSr_no(sr_no);
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
									/*if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										grievPendingTwo.setToken_number(null);
									}else {*/
										grievPendingTwo.setToken_number(val);
									//}
								}
								else if (i == 2) {
										try {
											grievPendingTwo.setPran(CommonParser.parseLong(val));
										} catch (Exception e) {
											_log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}	
								}
								
								else if (i == 3) {
									/*if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										grievPendingTwo.setSubscriber_name(null);
									}else {*/
										grievPendingTwo.setSubscriber_name(val);	
									//}
								} 
								else if (i == 4) {
									try {
										Date date= cell.getDateCellValue();
										grievPendingTwo.setDate_(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievPendingTwo.setDate_(cell.getDateCellValue());
									}
								}
								else if (i == 5) {
									grievPendingTwo.setStatus(val);
								}
								else if (i == 6) {
									try {
										Date date= cell.getDateCellValue();
										grievPendingTwo.setGrievance_logging_date(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievPendingTwo.setGrievance_logging_date(cell.getDateCellValue());
									}
								}
								else if (i == 7) {
									try {
										Date date= cell.getDateCellValue();
										grievPendingTwo.setDate_considered_for_ageing(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievPendingTwo.setDate_considered_for_ageing(cell.getDateCellValue());
									}
								}
								else if (i == 8) {
									try {
										Integer aging= Integer.parseInt(cell.getRawValue());
										grievPendingTwo.setAgeing(aging);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 9) {
									grievPendingTwo.setBucket(val);
								}
								else if (i == 10) {
									grievPendingTwo.setRaised_by(val);
								} 
								else if (i == 11) {
									/*if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										grievPendingTwo.setNlao_reg_no(null);
									}else {*/
										try {
											BigDecimal  nlao_reg_no =  CommonParser.parseBigDecimal(val);
											grievPendingTwo.setNlao_reg_no(nlao_reg_no);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									//}
								} 	
								else if (i == 12) {
									/*if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										grievPendingTwo.setNlao_name(null);
									}else {*/
										grievPendingTwo.setNlao_name(val);
									//}
								}
								else if (i == 13) {
									/*if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										grievPendingTwo.setNloo_reg_no(null);
									}else {*/
										grievPendingTwo.setNloo_reg_no(val);
									//}
								} 
								else if (i == 14) {
									grievPendingTwo.setNloo_name(val);	
								}
								else if (i == 15) {
									if(GrievMasterConstants.schemes.contains(val.trim())) {
										grievPendingTwo.setSector(val);
									}else {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg","Sheet name: "+sheetName+",  column: Scheme,"+" master lookup failed");
										return resultJsonObject;
									}
									//grievPendingTwo.setSector(val);
								}
								else if (i == 16) {
									if(GrievMasterConstants.broadCategories.contains(val.toUpperCase().trim())) {
										grievPendingTwo.setBroad_category(val);
									}else {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg","Sheet name: "+sheetName+", column: Broad Category :-"+val +" ,"+" master lookup failed");
										return resultJsonObject;
									}
									
									//grievPendingTwo.setBroad_category(val);
								}
								else if (i == 17) {
									grievPendingTwo.setGrievance_text(val);
								}
								else if (i == 18) {
									try {
										Date date= cell.getDateCellValue();
										grievPendingTwo.setFollowup_action_i(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievPendingTwo.setFollowup_action_i(cell.getDateCellValue());
									}
								}
								else if (i == 19) {
									try {
										Date date= cell.getDateCellValue();
										grievPendingTwo.setFollowup_action_ii(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievPendingTwo.setFollowup_action_ii(cell.getDateCellValue());
									}
								}
								
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					grievPendingTwo.setCreatedate(new Date());
					
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
						//JSONObject grievPendingTwoJsonObject = JSONFactoryUtil.createJSONObject(grievPendingTwo.toString());
						//grievPendingTwoJsonArray.put(grievPendingTwoJsonObject);
						grievPendingTwos.add(grievPendingTwo);
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

