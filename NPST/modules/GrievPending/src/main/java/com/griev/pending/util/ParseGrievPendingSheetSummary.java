package com.griev.pending.util;

import com.daily.pfm.service.model.GrievPendingSummary;
import com.daily.pfm.service.service.GrievPendingSummaryLocalServiceUtil;
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

public class ParseGrievPendingSheetSummary {
	
	static Log _log = LogFactoryUtil.getLog(ParseGrievPendingSheetSummary.class);
	public static JSONObject saveSheetSummary(File file, XSSFWorkbook workbook, Long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,List<GrievPendingSummary> grievPendingSummarys, JSONArray grievPendingSummaryJsonArray, DecimalFormat decimalFormat,  String cra, long reportUploadLogId) {
		_log.info("inside saveSheetSummary:::::::");

		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(0);
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					_log.info("within while loop");
					GrievPendingSummary grievPendingSummary = GrievPendingSummaryLocalServiceUtil.createGrievPendingSummary(CounterLocalServiceUtil.increment(GrievPendingSummary.class.getName()));
							
					grievPendingSummary.setCreatedby(userId);
					grievPendingSummary.setCra(cra);
					grievPendingSummary.setReportUploadLogId(reportUploadLogId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 7; i++) {
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
//										if(val.equalsIgnoreCase("total")) {
//											break rowLoop;
//										}
//										grievPendingSummary.setSector_type(val);
//										
//									} else {
//										errorObj.put("cellno", 2);
//										errorObj.put("msg", "it is not a number");
//										
//										isError = true;
//									}
										if(GrievMasterConstants.sectorTypes.contains(val.trim())) {
											grievPendingSummary.setSector_type(val);
										}else {
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg","Sheet name: "+sheetName+", column:Sector Type,"+" master lookup failed");
											return resultJsonObject;
										}}
								}
								else if (i == 1) {
									grievPendingSummary.setName_of_ministry_central_gov(val);
								}
								else if (i == 2) {
									try {
										Date date= cell.getDateCellValue();
										grievPendingSummary.setDate_(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//grievPendingSummary.setDate_(cell.getDateCellValue());
									}
									
								}
								else if (i == 3) {
									try {
										Integer reported_referrals= Integer.parseInt(val);
										grievPendingSummary.setReported_referrals(reported_referrals);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
										
									}
									
								} 
								else if (i==4) {
									try {
										Integer sixty_oneeighty= Integer.parseInt(val);
										grievPendingSummary.setSixty_oneeighty(sixty_oneeighty);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
									
								
								}
								
								else if (i == 5) {
									try {
										Integer oneeighty_threesixtyfive= Integer.parseInt(val);
											grievPendingSummary.setOneeighty_threesixtyfive(oneeighty_threesixtyfive);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
									
								}else if(i == 6) {
									try {
										Integer morethan_threesixtyfive= Integer.parseInt(val);
										grievPendingSummary.setMorethan_threesixtyfive(morethan_threesixtyfive);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								
								
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					grievPendingSummary.setCreatedate(new Date());
					
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
						//JSONObject grievPendingSummaryJsonObject = JSONFactoryUtil.createJSONObject(grievPendingSummary.toString());
						//grievPendingSummaryJsonArray.put(grievPendingSummaryJsonObject);
						grievPendingSummarys.add(grievPendingSummary);
					}
					rowCount++;
				}
				_log.info("grievPendingSummarys size : "+grievPendingSummarys.size());
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


