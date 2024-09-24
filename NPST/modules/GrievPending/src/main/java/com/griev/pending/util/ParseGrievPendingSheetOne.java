package com.griev.pending.util;

import com.daily.pfm.service.model.GrievPendingOne;
import com.daily.pfm.service.service.GrievPendingOneLocalServiceUtil;
import com.griev.pending.constants.GrievMasterConstants;
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
import java.util.ArrayList;
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

public class ParseGrievPendingSheetOne {
	static Log _log = LogFactoryUtil.getLog(ParseGrievPendingSheetOne.class);
	
	
	public static JSONObject saveSheetOne(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,List<GrievPendingOne> grievpendingones, JSONArray grievpendingOneJsonArray, DecimalFormat decimalFormat, String cra, long reportUploadLogId) {
		_log.info("inside saveSheetOne:::::::");

		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(1);
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					GrievPendingOne grievpendingOne = GrievPendingOneLocalServiceUtil.createGrievPendingOne(CounterLocalServiceUtil.increment(GrievPendingOne.class.getName()));
							
					grievpendingOne.setCreatedby(userId);
					grievpendingOne.setCra(cra);
					grievpendingOne.setReportUploadLogId(reportUploadLogId);
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 24; i++) {
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
											grievpendingOne.setSr_no(sr_no);
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
								else if (val != null && i == 1) {

									if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										grievpendingOne.setToken_number(null);
									}else {
										grievpendingOne.setToken_number(val);
									}
								}
								else if (i == 2) {
									try {
										grievpendingOne.setPran(CommonParser.parseLong(val));
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								
								else if (i == 3) {
									grievpendingOne.setSubscriber_name(val);
								} 
								else if (i==4) {
									try {
										Date date= cell.getDateCellValue();
										grievpendingOne.setDate_(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievpendingOne.setDate_(cell.getDateCellValue());
									}
								}
								
								else if (i == 5) {
									grievpendingOne.setStatus(val);
								}
								else if (i == 6) {
									try {
										Date date= cell.getDateCellValue();
										grievpendingOne.setGrievance_logging_date(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievpendingOne.setGrievance_logging_date(cell.getDateCellValue());
									}
								}
								else if (i == 7) {
									try {
										Date date= cell.getDateCellValue();
										grievpendingOne.setDate_considered_for_ageing(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievpendingOne.setDate_considered_for_ageing(cell.getDateCellValue());
									}
								}
								else if (i == 8) {
									try {
										Integer aging= Integer.parseInt(cell.getRawValue());
										grievpendingOne.setAgeing(aging);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 9) {
									grievpendingOne.setBucket(val);
								}
								else if (i == 10) {
									grievpendingOne.setRaised_by(val);
								}
								else if (i == 11) {
									/*if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										grievpendingOne.setPao_reg_no(null);
									}else {*/
										grievpendingOne.setPao_reg_no(val);
									//}
								} 	
								else if (i == 12) {
									grievpendingOne.setPao_name(val);
								}
								else if (i == 13) {
									/*if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										grievpendingOne.setPr_ao_reg_no(null);
									}else {*/
										grievpendingOne.setPr_ao_reg_no(val);
									//}
								} 
								else if (i == 14) {
									grievpendingOne.setPr_ao_name(val);
								}
								else if (i == 15) {
									if(GrievMasterConstants.sectors.contains(val.trim())) {
										grievpendingOne.setSector(val);
									}else {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg","Sheet name: "+sheetName+", column:Sector,"+" master lookup failed");
										return resultJsonObject;
									}
									
									grievpendingOne.setSector(val);
								}
								else if (i == 16) {
									if(GrievMasterConstants.sectorTypes.contains(val.trim())) {
										grievpendingOne.setSector_type(val);
									}else {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg","Sheet name: "+sheetName+", column:Sector Type,"+" master lookup failed");
										return resultJsonObject;
									}
									//grievpendingOne.setSector_type(val);
								}
								else if (i == 17) {
									grievpendingOne.setMinistry_name(val);
								}
								
								else if (i == 18) {
									grievpendingOne.setState_name(val);
								}
								else if (i == 19) {
									if(GrievMasterConstants.broadCategories.contains(val.toUpperCase().trim())) {
										grievpendingOne.setBroad_category(val);
									}else {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg","Sheet name: "+sheetName+", column: Broad Category :-"+val +" ,"+" master lookup failed");
										return resultJsonObject;
									}
									//grievpendingOne.setBroad_category(val);
								}
								else if (i == 20) {
									grievpendingOne.setGrievance_text(val);
								}
								else if (i == 21) {
									try {
										Date date= cell.getDateCellValue();
										grievpendingOne.setFollowup_action_i(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievpendingOne.setFollowup_action_i(cell.getDateCellValue());
									}
								}
								else if (i == 22) {
									try {
										Date date= cell.getDateCellValue();
										grievpendingOne.setFollowup_action_ii(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievpendingOne.setFollowup_action_ii(cell.getDateCellValue());
									}
								}
								 
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					grievpendingOne.setCreatedate(new Date());
					
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
						//JSONObject grievpendingOneJsonObject = JSONFactoryUtil.createJSONObject(grievpendingOne.toString());
						//grievpendingOneJsonArray.put(grievpendingOneJsonObject);
						grievpendingones.add(grievpendingOne);
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

