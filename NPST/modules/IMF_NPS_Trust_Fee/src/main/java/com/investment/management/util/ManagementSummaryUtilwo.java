package com.investment.management.util;

import com.daily.average.service.model.ManagementSummary;
import com.daily.average.service.service.ManagementSummaryLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

public class ManagementSummaryUtilwo {
	static Log _log = LogFactoryUtil.getLog(ManagementSummaryUtilwo.class);
	public static JSONObject saveManagementSummarySheet(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray managementSummaryJsonArray, List<ManagementSummary> managementSummaries, DecimalFormat decimalFormat, Long reportUploadLogId, JSONObject resultJsonObject) {
		
		ManagementSummaryLocalServiceUtil.deleteManagementSummaryByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Fee Summary");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				rowLoop:
				while (rows.hasNext()) {
					
					ManagementSummary managementSummary = ManagementSummaryLocalServiceUtil.createManagementSummary(CounterLocalServiceUtil.increment(ManagementSummary.class.getName()));
							
					managementSummary.setCreatedby(userId);
					managementSummary.setReportUploadLogId(reportUploadLogId);
					
					
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
							_log.debug("Val ParseAnnexFourA  "+val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								try {
									if (i == 0) {
										if (Validator.isNotNull(val)) {
											try {
												managementSummary.setMonth(cell.getDateCellValue());
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											isError = true;
										}
									}
									else if (i == 1) {
										
										managementSummary.setPension_fund_name(val);
									}
									else if (i == 2) {
										
										managementSummary.setScheme_name(val);
									}
									else if (i == 3) {
										
										try {
											BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
											managementSummary.setManagement_fee(amt);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 4) {
										try {
											BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
											
											managementSummary.setGst(amt);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 5) {
										try {
											BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
											managementSummary.setTotal_management_fee(amt);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									
									}else if (i == 6) {
										
										try {
											BigDecimal  tds =  (BigDecimal) decimalFormat.parse(val);
											managementSummary.setTds(tds);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
								} catch (Exception e) {
									_log.info("error parsing long"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
								
								
							}
						}else if (i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					managementSummary.setCreatedate(new Date());
						
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
							//JSONObject managementSummaryJsonObject = JSONFactoryUtil.createJSONObject(managementSummary.toString());
							//managementSummaryJsonArray.put(managementSummaryJsonObject);
							managementSummaries.add(managementSummary);
						}
						rowCount++;
					}
					
				}
			}catch (Exception e) {
				 _log.error(e);
				resultJsonObject.put("status", false);
				resultJsonObject.put("msg", CommonParser.fileExceptionMsg +"Fee Summary");
				return resultJsonObject;
			}
		return resultJsonObject;
	}

}
