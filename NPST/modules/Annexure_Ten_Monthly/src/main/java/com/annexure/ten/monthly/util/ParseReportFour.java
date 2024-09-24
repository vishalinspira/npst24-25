package com.annexure.ten.monthly.util;


import com.daily.pfm.service.model.ReportIVa;
import com.daily.pfm.service.service.ReportIVaLocalServiceUtil;
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

public class ParseReportFour {
	public static JSONObject saveSheetFour(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray reportivaJsonArray, List<ReportIVa> reportivas, DecimalFormat decimalFormat, int dataRowNum, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId) {
		
		ReportIVaLocalServiceUtil.deleteReportIVaByReportUploadLogId(reportUploadLogId);
		String sheetName = "NCRA_Remittance_Report";
		try {
			if (file != null) {
				
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("NCRA_Remittance_Report");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					ReportIVa reportiva = ReportIVaLocalServiceUtil.createReportIVa(CounterLocalServiceUtil.increment(ReportIVa.class.getName()));
					
					reportiva.setReportUploadLogId(reportUploadLogId);
					reportiva.setCreatedby(userId);
					reportiva.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;

					for (int i = 0; i < 5; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							if (val != null) {
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val)) {
										
										reportiva.setRemittence_cat(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is empty");
										
										isError = true;
									}
								}
								else if (i == 1) {
									
									reportiva.setTimelines(val);
								}
								else if (i == 2) {
									
									try {
										Integer no_of_instances=Integer.parseInt(val);
										reportiva.setNo_of_instances(no_of_instances);
									} catch (Exception e) {
										 _log.info("error parsing big dec"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										BigDecimal  amt_transferred =  CommonParser.parseBigDecimal(val);
										reportiva.setAmt_transferred(amt_transferred);
									} catch (Exception e) {
										 _log.info("error parsing big dec"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 4) {
									try {
										reportiva.setMonth(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								/*else if (i == 4) {
									reportiva.setCompensation(val);;
								} 
								else if (i == 5) {
									reportiva.setRemarks(val);;
								} */
							}}
						}else if(i == 0  && rowCount > 1) {
							break rowLoop;
						}
					}
					reportiva.setCreatedate(new Date());
					
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
						JSONObject reportivaJsonObject = JSONFactoryUtil.createJSONObject(reportiva.toString());
						reportivaJsonArray.put(reportivaJsonObject);
						reportivas.add(reportiva);
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
