package com.valuationreports.util;

import com.daily.average.service.model.valuationRepSg;
import com.daily.average.service.service.valuationRepSgLocalService;
import com.daily.average.service.service.valuationRepSgLocalServiceUtil;
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
import org.osgi.service.component.annotations.Reference;

public class ValuationRepSG {
	
	private static Log _log = LogFactoryUtil.getLog(ValuationRepSG.class);
	
	public static void saveValuationSG(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray sgJsonArray, List<valuationRepSg> sgList, DecimalFormat decimalFormat,long reportUploadLogId) {
		
		_log.info("Inside saveValuationSG");
		
		valuationRepSgLocalServiceUtil.deletevaluationRepSgByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("valuation_sg");
				//XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					valuationRepSg valuationRepSg = (valuationRepSg) valuationRepSgLocalServiceUtil.
							createvaluationRepSg(CounterLocalServiceUtil.increment(valuationRepSg.class.getName()));
					
					valuationRepSg.setReportUploadLogId(reportUploadLogId);
					valuationRepSg.setCreatedby(userId);
					
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
										valuationRepSg.setClient_code(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									valuationRepSg.setIsin(val);
								} 
								else if (i == 2) {
									valuationRepSg.setIsin_desc(val);
								} 
								else if (i == 3) {
									try {
										Date date_1 = new SimpleDateFormat("dd-MMM-yy").parse(val);
										valuationRepSg.setMaturity_date(date_1);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 4) {
									try {
										Long longVal = Long.parseLong(val);
										valuationRepSg.setFree_securities_rs(longVal);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 5) {
									try {
										Long longVal = Long.parseLong(val);
										valuationRepSg.setPledge_value_held_rs(longVal);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 6) {
									try {
										Long longVal = Long.parseLong(val);
										valuationRepSg.setSecurities_in_transit_rs(longVal);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 7) {
									try {
										Long longVal = Long.parseLong(val);
										valuationRepSg.setTotal_face_value_held_rs(longVal);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 8) {
									try {
										BigDecimal parsedStringValue = (BigDecimal) decimalFormat.parse(val);
										valuationRepSg.setRate(parsedStringValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 9) {
									try {
										BigDecimal parsedStringValue = (BigDecimal) decimalFormat.parse(val);
										valuationRepSg.setValue_(parsedStringValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					valuationRepSg.setCreatedate(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(valuationRepSg.toString());
							sgJsonArray.put(jsonObject);
							sgList.add(valuationRepSg);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
	}
	
	@Reference
	valuationRepSgLocalService valuationRepSgLocalService;

}
