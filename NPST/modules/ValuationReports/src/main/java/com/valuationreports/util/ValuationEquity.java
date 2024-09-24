package com.valuationreports.util;

import com.daily.average.service.model.ReportEquity;
import com.daily.average.service.service.ReportEquityLocalServiceUtil;
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

public class ValuationEquity {

private static Log _log = LogFactoryUtil.getLog(ValuationEquity.class);
	
	public static void saveValuationEquity(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray equityJsonArray, List<ReportEquity> equityList, DecimalFormat decimalFormat,long reportUploadLogId) {
		_log.info("Inside saveValuationEquity");

		ReportEquityLocalServiceUtil.deleteReportEquityByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("repo_eq");
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					ReportEquity reportequity = ReportEquityLocalServiceUtil.createReportEquity(CounterLocalServiceUtil.increment(ReportEquity.class.getName()));
					
					reportequity.setCreatedby(userId);
					reportequity.setReportUploadLogId(reportUploadLogId);
						
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					int lastColumn = Math.max(row.getLastCellNum(), 0);
					
					for (int i = 0; i < lastColumn; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								_log.info("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										try {
											DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
											reportequity.setDate(dateFormat.parse(val));
										} catch (Exception e) {
											 _log.error(e);
										}
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									reportequity.setFin(val);
								}
								else if (i == 2) {
									reportequity.setScheme(val);
								} 
								else if (i == 3) {
									reportequity.setCompany_name(val);
								}
								else if (i == 4) {
									reportequity.setSecurity_code(val);
								} 
								else if (i == 5) {
									reportequity.setIsin(val);
								}
								else if (i == 6) {
									reportequity.setSecdesc(val);
								} 
								else if (i == 7) {
									reportequity.setSec(val);
								}
								else if (i == 8) {
									reportequity.setCoupon(val);
								}
								else if (i == 9) {
									try {
										long faceval = Long.parseLong(val);
										reportequity.setFaceval(faceval);
									} catch (Exception e) {
										_log.error(e);
									}
								} 
								else if (i == 10) {
									try {
										BigDecimal custhold = (BigDecimal) decimalFormat.parse(val);
										reportequity.setCusthold(custhold);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 11) {
									try {
										BigDecimal transit = (BigDecimal) decimalFormat.parse(val);
										reportequity.setTransit(transit);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 12) {
									try {
										BigDecimal loghold = (BigDecimal) decimalFormat.parse(val);
										reportequity.setLoghold(loghold);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 13) {
									try {
										BigDecimal bse_rate = (BigDecimal) decimalFormat.parse(val);
										reportequity.setBse_rate(bse_rate);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 14) {
									try {
										BigDecimal bse_value = (BigDecimal) decimalFormat.parse(val);
										reportequity.setBse_value(bse_value);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 15) {
									try {
										BigDecimal nse_rate = (BigDecimal) decimalFormat.parse(val);
										reportequity.setNse_rate(nse_rate);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 16) {
									try {
										BigDecimal nse_value = (BigDecimal) decimalFormat.parse(val);
										reportequity.setNse_value(nse_value);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					reportequity.setCreatedate(new Date());
					
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
						JSONObject reportequityJsonObject = JSONFactoryUtil.createJSONObject(reportequity.toString());
						equityJsonArray.put(reportequityJsonObject);
						equityList.add(reportequity);
					}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
	}
	
}
