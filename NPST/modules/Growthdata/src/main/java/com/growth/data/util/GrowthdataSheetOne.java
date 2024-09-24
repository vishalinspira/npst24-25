package com.growth.data.util;


import com.daily.pfm.service.model.GrowthDataOne;
import com.daily.pfm.service.service.GrowthDataOneLocalServiceUtil;
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

public class GrowthdataSheetOne {
	public static JSONObject saveSheetOne(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, 
			boolean isexcelhaserror,List<GrowthDataOne> growthdataOnes, JSONArray growthdataOneJsonArray, DecimalFormat decimalFormat,
			long reportUploadLogId, String cra, JSONObject resultJsonObject) {
		String sheetName = "PFM AUM Growth";
		GrowthDataOneLocalServiceUtil.deleteGrowthDataOneByReportUploadLogId(reportUploadLogId);
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
					
					GrowthDataOne growthdataOne = GrowthDataOneLocalServiceUtil.createGrowthDataOne(CounterLocalServiceUtil.increment(GrowthDataOne.class.getName()));
					
					growthdataOne.setReportUploadLogId(reportUploadLogId);
					growthdataOne.setCreatedby(userId);
					growthdataOne.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 7; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									try {
										growthdataOne.setSr_no(CommonParser.parseLong(val).intValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 1) {
									try {
										growthdataOne.setReporting_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									growthdataOne.setPension_fund_name(val);
									
								}
								else if (i == 3) {
									growthdataOne.setMonth(val);
								}
								
								else if (i == 4) {
									
									try {
										growthdataOne.setYear(CommonParser.parseLong(val));
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									try {
										growthdataOne.setAum_in_cr(CommonParser.parseBigDecimal(val));
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
					growthdataOne.setCreatedate(new Date());
					
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
						growthdataOnes.add(growthdataOne);
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
	static Log _log = LogFactoryUtil.getLog(GrowthdataSheetOne.class);
}
