package com.investment.management.util;


import com.daily.pfm.service.model.NpsTrustFeePfmOne;
import com.daily.pfm.service.service.NpsTrustFeePfmOneLocalServiceUtil;
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

import nps.common.service.util.CommonParser;

public class NpsTrustFeePfmSheetOne {
	static Log _log = LogFactoryUtil.getLog(NpsTrustFeePfmSheetOne.class);
	public static JSONObject saveSheetOne(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,List<NpsTrustFeePfmOne> npsTrustFeePfmOnes, JSONArray npsTrustFeePfmOneJsonArray, DecimalFormat decimalFormat,long reportUploadLogId, JSONObject resultJsonObject) {
		NpsTrustFeePfmOneLocalServiceUtil.deleteNpsTrustFeePfmOneByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("nps_trust_fee");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					NpsTrustFeePfmOne npsTrustFeePfmOne = NpsTrustFeePfmOneLocalServiceUtil.createNpsTrustFeePfmOne(CounterLocalServiceUtil.increment(NpsTrustFeePfmOne.class.getName()));
				
					npsTrustFeePfmOne.setReportUploadLogId(reportUploadLogId);
					npsTrustFeePfmOne.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 5; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.debug(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								try {
									if (i == 0) {
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											if(val.equalsIgnoreCase("total")) {
												break rowLoop;
											}
											
											npsTrustFeePfmOne.setPension_fund_name(val);
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											
											isError = true;
										}
									}
									else if (i == 1) {
										
										
										npsTrustFeePfmOne.setScheme_name(val);
									}
									else if (i == 2) {
										try {
											npsTrustFeePfmOne.setDate_(cell.getDateCellValue());
										} catch (Exception e) {
											 _log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
										}
									}
									else if (i == 3) {
										BigDecimal  total_aum =  (BigDecimal) decimalFormat.parse(val);
										npsTrustFeePfmOne.setTotal_aum(total_aum);
									}
									else if (i == 4) {
										BigDecimal  nps_trust_fees =  (BigDecimal) decimalFormat.parse(val);
										npsTrustFeePfmOne.setNps_trust_fees(nps_trust_fees);
									}
								} catch (Exception e) {
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					npsTrustFeePfmOne.setCreatedate(new Date());
					
					if (isError && rowCount > 9) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount > 1) {
						//JSONObject npsTrustFeePfmOneJsonObject = JSONFactoryUtil.createJSONObject(npsTrustFeePfmOne.toString());
						//npsTrustFeePfmOneJsonArray.put(npsTrustFeePfmOneJsonObject);
						npsTrustFeePfmOnes.add(npsTrustFeePfmOne);
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

