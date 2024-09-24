package com.valuationreports.util;

import com.daily.average.service.model.ReportDebit;
import com.daily.average.service.service.ReportDebitLocalServiceUtil;
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

public class ValuationRepDebt {
	
	private static Log _log = LogFactoryUtil.getLog(ValuationRepDebt.class);
	
	public static void saveValuationDebt(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray debtJsonArray, List<ReportDebit> debtList, DecimalFormat decimalFormat,long reportUploadLogId) {
		
		_log.info("Inside saveValuationDebt");
		
		ReportDebitLocalServiceUtil.deleteReportDebitByReportUploadLogId(reportUploadLogId );
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("repo_debt");
				//XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					ReportDebit reportdebit = ReportDebitLocalServiceUtil.createReportDebit(CounterLocalServiceUtil.increment(ReportDebit.class.getName()));
					
					reportdebit.setReportUploadLogId(reportUploadLogId);
					reportdebit.setCreatedby(userId);
					
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
											DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
											reportdebit.setDate(dateFormat.parse(val));
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
									reportdebit.setFin(val);
								}
								else if (i == 2) {
									reportdebit.setScheme(val);
								} 
								else if (i == 3) {
									reportdebit.setCompany_name(val);
								}
								else if (i == 4) {
									reportdebit.setSecurity_code(val);
								} 
								else if (i == 5) {
									reportdebit.setIsin(val);
								}
								else if (i == 6) {
									reportdebit.setSecdesc(val);
								} 
								else if (i == 7) {
									reportdebit.setSec(val);
								}
								else if (i == 8) {
									reportdebit.setCoupon(val);
								}
								else if (i == 9) {
									try {
										long faceval = Long.parseLong(val);
										reportdebit.setFaceval(faceval);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 10) {
									try {
										BigDecimal custhold = (BigDecimal) decimalFormat.parse(val);
										reportdebit.setCusthold(custhold);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 11) {
									try {
										BigDecimal transit = (BigDecimal) decimalFormat.parse(val);
										reportdebit.setTransit(transit);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 12) {
									try {
										BigDecimal loghold = (BigDecimal) decimalFormat.parse(val);
										reportdebit.setLoghold(loghold);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 13) {
									try {
										BigDecimal bse_rate = (BigDecimal) decimalFormat.parse(val);
										reportdebit.setBse_rate(bse_rate);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 14) {
									try {
										BigDecimal bse_value = (BigDecimal) decimalFormat.parse(val);
										reportdebit.setBse_value(bse_value);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 15) {
									try {
										BigDecimal nse_rate = (BigDecimal) decimalFormat.parse(val);
										reportdebit.setNse_rate(nse_rate);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 16) {
									try {
										BigDecimal nse_value = (BigDecimal) decimalFormat.parse(val);
										reportdebit.setNse_value(nse_value);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					reportdebit.setCreatedate(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(reportdebit.toString());
							debtJsonArray.put(jsonObject);
							debtList.add(reportdebit);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
	}

}
