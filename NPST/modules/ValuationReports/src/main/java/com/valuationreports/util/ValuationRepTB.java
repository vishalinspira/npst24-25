package com.valuationreports.util;

import com.daily.average.service.model.ValuationRepoTb;
import com.daily.average.service.service.ValuationRepoTbLocalServiceUtil;
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

public class ValuationRepTB {

private static Log _log = LogFactoryUtil.getLog(ValuationRepTB.class);
	
	public static void saveValuationTB(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray tbJsonArray, List<ValuationRepoTb> tbList, DecimalFormat decimalFormat,long reportUploadLogId) {
		_log.info("Inside saveValuationTB");

		ValuationRepoTbLocalServiceUtil.deleteValuationRepoTbByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("weekly_valuation_rep_tb");
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					ValuationRepoTb valuationRepoTb = ValuationRepoTbLocalServiceUtil.createValuationRepoTb(CounterLocalServiceUtil.increment(ValuationRepoTb.class.getName()));
					
					valuationRepoTb.setCreatedby(userId);
					valuationRepoTb.setReportUploadLogId(reportUploadLogId);
						
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
										valuationRepoTb.setClient_code(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									valuationRepoTb.setIsin(val);
								}
								else if (i == 2) {
									valuationRepoTb.setIsin_desc(val);
								} 
								else if (i == 3) {
									try {
										DateFormat dateFormat= new SimpleDateFormat("dd-MMM-yy");
										valuationRepoTb.setMaturity_date(dateFormat.parse(val));
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 4) {
									try {
										long free_securities_rs = Long.parseLong(val);
										valuationRepoTb.setFree_securities_rs(free_securities_rs);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 5) {
									try {
										long pledge_value_held_rs = Long.parseLong(val);
										valuationRepoTb.setPledge_value_held_rs(pledge_value_held_rs);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 6) {
									try {
										long securities_in_transit_rs = Long.parseLong(val);
										valuationRepoTb.setSecurities_in_transit_rs(securities_in_transit_rs);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 7) {
									try {
										long total_face_value_held_rs = Long.parseLong(val);
										valuationRepoTb.setTotal_face_value_held_rs(total_face_value_held_rs);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 8) {
									try {
										BigDecimal rate = (BigDecimal) decimalFormat.parse(val);
										valuationRepoTb.setRate(rate);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 9) {
									try {
										BigDecimal value = (BigDecimal) decimalFormat.parse(val);
										valuationRepoTb.setValue(value);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					valuationRepoTb.setCreatedate(new Date());
					
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
						JSONObject valuationRepoTbJsonObject = JSONFactoryUtil.createJSONObject(valuationRepoTb.toString());
						tbJsonArray.put(valuationRepoTbJsonObject);
						tbList.add(valuationRepoTb);
					}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
	}
	
}
