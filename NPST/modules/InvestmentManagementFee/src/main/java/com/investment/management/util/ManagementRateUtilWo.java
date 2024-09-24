package com.investment.management.util;

import com.daily.average.service.model.ManagementRate;
import com.daily.average.service.service.ManagementRateLocalServiceUtil;
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

public class ManagementRateUtilWo {
	static Log _log = LogFactoryUtil.getLog(ManagementRateUtilWo.class);
	public static JSONObject saveManagementRateSheet(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray managementRateJsonArray, List<ManagementRate> managementRates, DecimalFormat decimalFormat, Long reportUploadLogId, JSONObject resultJsonObject) {
		
		ManagementRateLocalServiceUtil.deleteManagementRateByReportUploadLogId(reportUploadLogId);
		String sheetName="MGMT RATE";
		try {
			if (file != null) {
				XSSFSheet sheet = workbook.getSheet(sheetName);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				rowLoop:
				while (rows.hasNext()) {
					
					ManagementRate managementRate = ManagementRateLocalServiceUtil.createManagementRate(CounterLocalServiceUtil.increment(ManagementRate.class.getName()));
							
					managementRate.setCreatedby(userId);
					managementRate.setReportUploadLogId(reportUploadLogId);
					
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 4; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							_log.info("Val ParseAnnexFourA  "+val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0 && Validator.isNumber(val)) {
										Long sno=Long.parseLong(val);
										managementRate.setPension_fund_name(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									
									try {
										Date dateFormat= CommonParser.parseDate(val,cell);
										managementRate.setDate_(dateFormat);
									} catch (Exception e) {
//										managementRate.setDate_(cell.getDateCellValue());
//										_log.error("error parsing date"+val);
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									
									try {
										BigDecimal pfm_aum = CommonParser.parseBigDecimal(val);
										managementRate.setPfm_aum(pfm_aum);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 3) {
									
									try {
										BigDecimal management_rate = CommonParser.parseBigDecimal(val);
										managementRate.setManagement_rate(management_rate);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								
							}
						}else if (i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					managementRate.setCreatedate(new Date());
						
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
							managementRates.add(managementRate);
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
