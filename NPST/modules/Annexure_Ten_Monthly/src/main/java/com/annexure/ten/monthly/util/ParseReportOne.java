package com.annexure.ten.monthly.util;


import com.daily.pfm.service.model.ReportI;
import com.daily.pfm.service.service.ReportILocalServiceUtil;
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

public class ParseReportOne {
	public static JSONObject saveSheetOne(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,JSONArray reportiJsonArray, List<ReportI> reportis, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId) {
		
		ReportILocalServiceUtil.deleteReportIByReportUploadLogId(reportUploadLogId);
		String sheetName = "TB_Tran_of_NCRA";
		try {
			if (file != null) {
				XSSFSheet sheet = workbook.getSheet("TB_Tran_of_NCRA");
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put(sheetName);
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					ReportI reporti = ReportILocalServiceUtil.createReportI(CounterLocalServiceUtil.increment(ReportI.class.getName()));
					
					reporti.setReportUploadLogId(reportUploadLogId);
					reporti.setCreatedby(userId);
					reporti.setCra(cra);
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false; 
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 5; i++) {
						Integer no_of_rejected_tran=0;
						Integer no_of_accepted_tran=0;
						Integer total_transactions=0;
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								_log.info(val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										 try {
											no_of_accepted_tran=Integer.parseInt(val);
											reporti.setNo_of_accepted_tran(no_of_accepted_tran);
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
										no_of_rejected_tran=Integer.parseInt(val);
										reporti.setNo_of_rejected_tran(no_of_rejected_tran);
									} catch (Exception e) {
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 2) {
									
									 try {
										total_transactions=no_of_accepted_tran+no_of_rejected_tran;
										reporti.setTotal_transactions(total_transactions);
									} catch (Exception e) {
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										BigDecimal  rejection_prcnt =  new BigDecimal(0);
										if(no_of_accepted_tran >0 && total_transactions >0)
											rejection_prcnt = BigDecimal.valueOf(no_of_rejected_tran).divide(BigDecimal.valueOf(total_transactions));//total_transactions;
										reporti.setRejection_prcnt(rejection_prcnt);
									} catch (Exception e) {
										 _log.info("error parsing big dec"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 4) {
									try {
										reporti.setMonth(cell.getDateCellValue());
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
					reporti.setCreatedate(new Date());
						
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
						JSONObject reportiJsonObject = JSONFactoryUtil.createJSONObject(reporti.toString());
						reportiJsonArray.put(reportiJsonObject);
						reportis.add(reporti);
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
	
	static Log _log = LogFactoryUtil.getLog(ParseReportOne.class);
}
