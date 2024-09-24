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

import compliance.service.model.MnForm5_C_I;
import compliance.service.model.MnForm5_C_II;
import compliance.service.service.MnForm5_C_IILocalServiceUtil;
import compliance.service.service.MnForm5_C_ILocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form5 {
	
private static Log _log = LogFactoryUtil.getLog(Form5.class);
	
	public static JSONObject saveForm5C_1(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form5C_1JsonArray, List<MnForm5_C_I> form5C_1List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm5C_1");
		String sheetName="Form 2 C-I";
				JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		MnForm5_C_ILocalServiceUtil.deleteMnForm5_C_IByReportUploadLogId(reportUploadLogId);
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
					MnForm5_C_I form5C_1 = MnForm5_C_ILocalServiceUtil.
							createMnForm5_C_I(CounterLocalServiceUtil.increment(MnForm5_C_I.class.getName()));
					
					form5C_1.setReportUploadLogId(reportUploadLogId);
					form5C_1.setCreatedby(userId);
					
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
										form5C_1.setPension_fund_name(val);
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
										Date date_1 =cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form5C_1.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form5C_1.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										long lValue =CommonParser.parseLong(val);// Long.parseLong(val);
										form5C_1.setCode(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									//_log.info("cell2" + cell.getCellType());
									form5C_1.setDescription_of_securities(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val); // (BigDecimal) decimalFormat.parse(val);
										form5C_1.setAmount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form5C_1.setPercentage_of_portfolio(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form5C_1.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form5C_1.toString());
							form5C_1JsonArray.put(jsonObject);
							form5C_1List.add(form5C_1);
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
	
	public static JSONObject saveForm5C_2(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form5C_2JsonArray, List<MnForm5_C_II> form5C_2List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm5C_2");
		String sheetName="Form 2 C-II";
				JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		MnForm5_C_IILocalServiceUtil.deleteMnForm5_C_IIByReportUploadLogId(reportUploadLogId);
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
					MnForm5_C_II form5C_2 = MnForm5_C_IILocalServiceUtil.
							createMnForm5_C_II(CounterLocalServiceUtil.increment(MnForm5_C_II.class.getName()));
					
					form5C_2.setReportUploadLogId(reportUploadLogId);
					form5C_2.setCreatedby(userId);
					
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
										form5C_2.setPension_fund_name(val);
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
										Date date_1 = cell.getDateCellValue(); //new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form5C_2.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form5C_2.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										long lValue = CommonParser.parseLong(val);
										form5C_2.setCode(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									//_log.info("cell2" + cell.getCellType());
									form5C_2.setDescription_of_securities(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val); // (BigDecimal) decimalFormat.parse(val);
										form5C_2.setAmount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form5C_2.setPercentage_of_portfolio(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form5C_2.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form5C_2.toString());
							form5C_2JsonArray.put(jsonObject);
							form5C_2List.add(form5C_2);
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
