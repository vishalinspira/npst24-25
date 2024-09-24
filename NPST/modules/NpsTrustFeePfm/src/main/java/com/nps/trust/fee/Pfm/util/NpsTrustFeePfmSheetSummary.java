package com.nps.trust.fee.Pfm.util;

import com.daily.pfm.service.model.NpsTrustFeePfmsummary;
import com.daily.pfm.service.service.NpsTrustFeePfmsummaryLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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


public class NpsTrustFeePfmSheetSummary {
	
	private static Log _log = LogFactoryUtil.getLog(NpsTrustFeePfmSheetSummary.class);
	
	public static JSONObject saveNpsTrustFeePfmSheetSummary(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray NpsTrustFeePfmsummaryJsonArray, List<NpsTrustFeePfmsummary> summaryList, DecimalFormat decimalFormat,long reportUploadLogId) {
		_log.info("Inside saveNpsTrustFeePfmSheetSummary");
		NpsTrustFeePfmsummaryLocalServiceUtil.deleteNpsTrustFeePfmsummaryByReportUploadLogId(reportUploadLogId);
		String sheetName="NPST Fee Summary";
				JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					NpsTrustFeePfmsummary NpsTrustFeePfmsummary = NpsTrustFeePfmsummaryLocalServiceUtil.
							createNpsTrustFeePfmsummary(CounterLocalServiceUtil.increment(NpsTrustFeePfmsummary.class.getName()));
					
					NpsTrustFeePfmsummary.setReportUploadLogId(reportUploadLogId);
					NpsTrustFeePfmsummary.setCreatedby(userId);
					
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
									if (val != null) {
										NpsTrustFeePfmsummary.setMounth(cell.getDateCellValue());
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									NpsTrustFeePfmsummary.setPension_fund_name(val);
								}
								else if (i == 2) {
									NpsTrustFeePfmsummary.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										NpsTrustFeePfmsummary.setNps_trust_fee(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					NpsTrustFeePfmsummary.setCreatedate(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(NpsTrustFeePfmsummary.toString());
							NpsTrustFeePfmsummaryJsonArray.put(jsonObject);
							summaryList.add(NpsTrustFeePfmsummary);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
		return resultJsonObject;
	}

}
