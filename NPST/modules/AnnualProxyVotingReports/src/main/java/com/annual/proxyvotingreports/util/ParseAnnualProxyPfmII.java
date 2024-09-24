package com.annual.proxyvotingreports.util;

import com.daily.average.service.model.AnnualProxyPfmII;
import com.daily.average.service.service.AnnualProxyPfmIILocalServiceUtil;
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

public class ParseAnnualProxyPfmII {
	static Log _log = LogFactoryUtil.getLog(ParseAnnualProxyPfmII.class);
	public static JSONObject saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,List<AnnualProxyPfmII> annualProxyPfmIIs, JSONArray annualProxyPfmIIJsonArray, DecimalFormat decimalFormat, Long reportUploadLogId, JSONObject resultJsonObject) {
		
		AnnualProxyPfmIILocalServiceUtil.deleteAnnualProxyPfmIIByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Consensus vote summary");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					AnnualProxyPfmII annualProxyPfmII = AnnualProxyPfmIILocalServiceUtil.createAnnualProxyPfmII(CounterLocalServiceUtil.increment(AnnualProxyPfmII.class.getName()));
							
					annualProxyPfmII.setCreatedby(userId);
					annualProxyPfmII.setReportUploadLogId(reportUploadLogId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 8; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								try {
									if (i == 0) {
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											BigDecimal  sr_no =  CommonParser.parseBigDecimal(val);
											annualProxyPfmII.setSr_no(sr_no);
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											
											isError = true;
										}
									}
									else if (i == 1) {
										
										annualProxyPfmII.setPension_fund_name(val);
									}
									else if (i == 2) {
										
										annualProxyPfmII.setYear(val);
									}
									
									else if (i == 3) {
										
										annualProxyPfmII.setQuarter(val);
									} 
									else if (i==4) {
										BigDecimal  total_number_of_resolution =  CommonParser.parseBigDecimal(val);
										annualProxyPfmII.setTotal_number_of_resolution(total_number_of_resolution);
									}
									
									else if (i == 5) {
										BigDecimal  for_ =  CommonParser.parseBigDecimal(val);
										annualProxyPfmII.setFor_(for_);
									}
									else if (i == 6) {
										BigDecimal  against =  CommonParser.parseBigDecimal(val);
										annualProxyPfmII.setAgainst(against);
									}
									else if (i == 7) {
										BigDecimal  abstained =  CommonParser.parseBigDecimal(val);
										annualProxyPfmII.setAbstained(abstained);
									}
								} catch (Exception e) {
									_log.info("error parsing number---->"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					annualProxyPfmII.setCreatedate(new Date());
					
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
						annualProxyPfmIIs.add(annualProxyPfmII);
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
