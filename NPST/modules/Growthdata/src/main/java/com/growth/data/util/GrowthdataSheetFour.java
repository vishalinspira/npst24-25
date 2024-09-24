package com.growth.data.util;

import com.daily.pfm.service.model.GrowthDataFour;
import com.daily.pfm.service.service.GrowthDataFourLocalServiceUtil;
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

public class GrowthdataSheetFour {
	public static JSONObject saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx,
			boolean isexcelhaserror,List<GrowthDataFour> growthDataFours, JSONArray growthDataFourJsonArray,DecimalFormat decimalFormat,
			long reportUploadLogId, String cra, JSONObject resultJsonObject) {
		
		GrowthDataFourLocalServiceUtil.deleteGrowthDataFourByReportUploadLogId(reportUploadLogId);
		String sheetName = "PFM wise Scheme Growth";
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					GrowthDataFour growthDataFour = GrowthDataFourLocalServiceUtil.createGrowthDataFour(CounterLocalServiceUtil.increment(GrowthDataFour.class.getName()));
					growthDataFour.setReportUploadLogId(reportUploadLogId);
					growthDataFour.setCreatedby(userId);
					growthDataFour.setCra(cra);
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
								if (i == 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										try {
											growthDataFour.setSr_no(CommonParser.parseLong(val).intValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
								}
								else if (i == 1) {
									try {
										growthDataFour.setReporting_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
									
								}
								else if (i == 2) {
									growthDataFour.setPension_fund_name(val);
								}
								else if (i == 3) {
									growthDataFour.setScheme_name(val);
								}
								else if (i == 4) {
									growthDataFour.setMonth(val);
								} 
								else if (i == 5) {
									try {
										growthDataFour.setYear(CommonParser.parseLong(val));
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										growthDataFour.setAum_in_cr(CommonParser.parseBigDecimal(val));
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					growthDataFour.setCreatedate(new Date());
					
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
						growthDataFours.add(growthDataFour);
					}
					rowCount++;
				}
				_log.info(sheetName +" rowcount"+rowCount);
				
			}
		}catch (Exception e) {
			 _log.error(e);
			 resultJsonObject.put("status", false);
			resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
			return resultJsonObject;
		}
		return resultJsonObject;
	}
	static Log _log = LogFactoryUtil.getLog(GrowthdataSheetFour.class);
}
