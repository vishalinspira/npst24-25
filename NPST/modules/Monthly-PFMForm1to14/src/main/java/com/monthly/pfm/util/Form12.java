package com.monthly.pfm.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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

import compliance.service.model.MnForm12;
import compliance.service.service.MnForm12LocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form12 {
	
	private static Log _log = LogFactoryUtil.getLog(Form12.class);
	
	public static JSONObject saveForm12(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form12JsonArray, List<MnForm12> form12List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm12");
		MnForm12LocalServiceUtil.deleteMnForm12ByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 8_issuer";
				JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
				resultJsonObject.put("status", true);
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
					//_log.info("Inside while");
					MnForm12 form12 = MnForm12LocalServiceUtil.
							createMnForm12(CounterLocalServiceUtil.increment(MnForm12.class.getName()));
					
					form12.setReportUploadLogId(reportUploadLogId);
					form12.setCreatedby(userId);
					
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
								//_log.info("Val: " + val);
								if (i == 0) {
									if (val != null) {
										form12.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
									//	val = val.replaceAll("//", "-");
										Date date_1 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form12.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form12.setScheme_name(val);
								} 
								else if (i == 3) {
									form12.setName_of_the_issuer(val);
								} 
								else if (i == 4) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form12.setEquity_perm_limit_aum_prcnt(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
										form12.setEquity_perm_limit_aum_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form12.setEquity_actual_investment(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form12.setEquity_deviation_percentage(val);
								}
								else if (i == 8) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form12.setEquity_deviation_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form12.setDebt_perm_limit_aum_percentage(val);
								} 
								else if (i == 10) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
										form12.setDebt_perm_limit_of_aum_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
										form12.setDebt_actual_investment(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 12) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form12.setDebt_deviation_percentage(val);
								} 
								else if (i == 13) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form12.setDebt_deviation_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
//								else if (i == 14) {
//									form12.setExposure_exceptions(val);
//								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form12.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form12.toString());
							form12JsonArray.put(jsonObject);
							form12List.add(form12);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}catch (Exception e) {
				 _log.error(e);
			}
		return resultJsonObject;
		
	}

}
