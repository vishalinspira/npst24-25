package com.asp.file.upload.util;


import com.daily.pfm.service.model.Asp_report;
import com.daily.pfm.service.service.Asp_reportLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

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

public class Asp_File_Upload_Excel_util {
	static Log _log = LogFactoryUtil.getLog(Asp_File_Upload_Excel_util.class);
	public static JSONObject  saveAsp_reportSheet(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray asp_reportJsonArray, List<Asp_report> asp_reports, DecimalFormat decimalFormat, Long reportUploadLogId, JSONObject resultJsonObject) {
		
		Asp_reportLocalServiceUtil.deleteAsp_reportByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null ) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					String sheetName = sheet.getSheetName();
					Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					rowLoop:
					while (rows.hasNext()) {
						
						Asp_report asp_report = Asp_reportLocalServiceUtil.createAsp_report(CounterLocalServiceUtil.increment());
								
						asp_report.setCreatedate(new Date());
						asp_report.setCreatedby(userId);
						asp_report.setReportUploadLogId(reportUploadLogId);
						
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						for (int i = 0; i < 5; i++) {
							XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							if (cell != null) {
	
								DataFormatter formatter = new DataFormatter();
								
								String val = formatter.formatCellValue(cell);
								_log.info("Val ParseAnnexFourA  "+val);
								if (val != null)
									val = val.trim();
								if(rowCount > 1) {
									if (i == 0) {
										if (Validator.isNotNull(val)) {
											//Long sno=Long.parseLong(val);
											asp_report.setSection(val);
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											
											isError = true;
										}
									}
									else if (i == 1) {
										
										asp_report.setParticulars(val);
									}
									else if (i == 2) {
										
										asp_report.setValue(val);
									}
									else if (i == 3) {
										
										asp_report.setEntity_name(val);
									}
									else if (i == 4) {
										
										try {
											asp_report.setMonth_(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									
								}
							}else if (i==0 && rowCount > 1) {
								break rowLoop;
							}
						}						
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
								//JSONObject asp_reportJsonObject = JSONFactoryUtil.createJSONObject(asp_report.toString());
								//asp_reportJsonArray.put(asp_reportJsonObject);
								_log.info("asp_report   "+asp_report);
								asp_reports.add(asp_report);
							}
							_log.info("rowCount   "+rowCount);
							rowCount++;
						}
					}
				}
			}catch (Exception e) {
				 _log.error(e);
				 resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
					return resultJsonObject;
			}
			//return asp_reports;
		return resultJsonObject;
	}
	
	
}
