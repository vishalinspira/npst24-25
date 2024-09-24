package com.annexure.ten.monthly.util;


import com.daily.pfm.service.model.ReportV;
import com.daily.pfm.service.service.ReportVLocalServiceUtil;
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

public class ParseReportV {
	public static JSONObject saveSheetNine(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray reportvJsonArray, List<ReportV> reportvs, int dataRowNum, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId) {
		
		ReportVLocalServiceUtil.deleteReportVByReportUploadLogId(reportUploadLogId);
		String sheetName = "TB_Paid_Monetary_Damage";
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("TB_Paid_Monetary_Damage");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					ReportV reportv = ReportVLocalServiceUtil.createReportV(CounterLocalServiceUtil.increment(ReportV.class.getName()));
					reportv.setCra(cra);
					
					reportv.setReportUploadLogId(reportUploadLogId);
					reportv.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;

					for (int i = 0; i < 6; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										try {
											Integer sno=Integer.parseInt(val);
											reportv.setSno(sno);
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
									
									reportv.setNps_acc_name(val);
								}
								else if (i == 2) {
									try {
										BigDecimal  amt_involved =  CommonParser.parseBigDecimal(val);
										reportv.setAmt_involved(amt_involved);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										Integer delay_days=Integer.parseInt(val);
										reportv.setDelay_days(delay_days);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 4) {
									
									try {
										BigDecimal  comp_paid =  CommonParser.parseBigDecimal(val);
										reportv.setComp_paid(comp_paid);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									reportv.setRemarks(val);
								} 
							}
						}else if(i == 0  && rowCount > 1) {
							break rowLoop;
						}
					}
					reportv.setCreatedate(new Date());
					
					if (isError && rowCount > dataRowNum) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount > 1) {
						JSONObject reportvJsonObject = JSONFactoryUtil.createJSONObject(reportv.toString());
						reportvJsonArray.put(reportvJsonObject);
						reportvs.add(reportv);
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
