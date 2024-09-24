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

import compliance.service.model.MnForm4_AtalPensionYojana;
import compliance.service.model.MnForm4_CG;
import compliance.service.model.MnForm4_CorpCG;
import compliance.service.model.MnForm4_NpsLite;
import compliance.service.model.MnForm4_SG;
import compliance.service.service.MnForm4_AtalPensionYojanaLocalServiceUtil;
import compliance.service.service.MnForm4_CGLocalServiceUtil;
import compliance.service.service.MnForm4_CorpCGLocalServiceUtil;
import compliance.service.service.MnForm4_NpsLiteLocalServiceUtil;
import compliance.service.service.MnForm4_SGLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form4 {
	
private static Log _log = LogFactoryUtil.getLog(Form4.class);
	
	public static JSONObject saveForm4Atal(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form4AtalJsonArray, List<MnForm4_AtalPensionYojana> form4AtalList, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm4Atal");
		MnForm4_AtalPensionYojanaLocalServiceUtil.deleteMnForm4_AtalPensionYojanaByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 2 APY";
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
					MnForm4_AtalPensionYojana form4Atal = MnForm4_AtalPensionYojanaLocalServiceUtil.
							createMnForm4_AtalPensionYojana(CounterLocalServiceUtil.increment(MnForm4_AtalPensionYojana.class.getName()));
					
					form4Atal.setReportUploadLogId(reportUploadLogId);
					form4Atal.setCreatedby(userId);
					
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
										form4Atal.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										Date date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form4Atal.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form4Atal.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										long lValue =CommonParser.parseLong(val);
										form4Atal.setCode(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									form4Atal.setDescription_of_securities(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
										form4Atal.setAmount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form4Atal.setPercentage_of_portfolio(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form4Atal.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form4Atal.toString());
							form4AtalJsonArray.put(jsonObject);
							form4AtalList.add(form4Atal);
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
	
	public static JSONObject saveForm4CorpCG(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form4CorpCGJsonArray, List<MnForm4_CorpCG> form4CorpCGList, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm4CorpCG");
		MnForm4_CorpCGLocalServiceUtil.deleteMnForm4_CorpCGByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 2 Corp CG";
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
					MnForm4_CorpCG form4CorpCG = MnForm4_CorpCGLocalServiceUtil.
							createMnForm4_CorpCG(CounterLocalServiceUtil.increment(MnForm4_CorpCG.class.getName()));
					
					form4CorpCG.setReportUploadLogId(reportUploadLogId);
					form4CorpCG.setCreatedby(userId);
					
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
										form4CorpCG.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										//val = val.replaceAll("//", "-");
										Date date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form4CorpCG.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form4CorpCG.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										long lValue = CommonParser.parseLong(val);//Long.parseLong(val);
										form4CorpCG.setCode(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									form4CorpCG.setDescription_of_securities(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form4CorpCG.setAmount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form4CorpCG.setPercentage_of_portfolio(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form4CorpCG.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form4CorpCG.toString());
							form4CorpCGJsonArray.put(jsonObject);
							form4CorpCGList.add(form4CorpCG);
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
	
	public static JSONObject saveForm4NpsLite(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form4NpsLiteJsonArray, List<MnForm4_NpsLite> form4NpsLiteList, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm4NpsLite");
		MnForm4_NpsLiteLocalServiceUtil.deleteMnForm4_NpsLiteByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 2 NPS Lite";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Form 4 NPS LITE");
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					MnForm4_NpsLite form4NpsLite = MnForm4_NpsLiteLocalServiceUtil.
							createMnForm4_NpsLite(CounterLocalServiceUtil.increment(MnForm4_NpsLite.class.getName()));
					
					form4NpsLite.setReportUploadLogId(reportUploadLogId);
					form4NpsLite.setCreatedby(userId);
					
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
										form4NpsLite.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										val = val.replaceAll("//", "-");
										Date date_1 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form4NpsLite.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form4NpsLite.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										long lValue =CommonParser.parseLong(val);// Long.parseLong(val);
										form4NpsLite.setCode(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									form4NpsLite.setDescription_of_securities(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val); // (BigDecimal) decimalFormat.parse(val);
										form4NpsLite.setAmount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form4NpsLite.setPercentage_of_portfolio(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form4NpsLite.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form4NpsLite.toString());
							form4NpsLiteJsonArray.put(jsonObject);
							form4NpsLiteList.add(form4NpsLite);
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
	
	public static JSONObject saveForm4CG(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form4CGJsonArray, List<MnForm4_CG> form4CGList, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm4CG");
		MnForm4_CGLocalServiceUtil.deleteMnForm4_CGByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 2 CG";
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
					MnForm4_CG form4CG = MnForm4_CGLocalServiceUtil.
							createMnForm4_CG(CounterLocalServiceUtil.increment(MnForm4_CG.class.getName()));
					
					form4CG.setReportUploadLogId(reportUploadLogId);
					form4CG.setCreatedby(userId);
					
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
										form4CG.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										val = val.replaceAll("//", "-");
										Date date_1 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form4CG.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form4CG.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										long lValue =CommonParser.parseLong(val);// Long.parseLong(val);
										form4CG.setCode(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									form4CG.setDescription_of_securities(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form4CG.setAmount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form4CG.setPercentage_of_portfolio(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form4CG.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form4CG.toString());
							form4CGJsonArray.put(jsonObject);
							form4CGList.add(form4CG);
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
	
	public static JSONObject saveForm4SG(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form4SGJsonArray, List<MnForm4_SG> form4SGList, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm4SG");
		MnForm4_SGLocalServiceUtil.deleteMnForm4_SGByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 2 SG";
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
					MnForm4_SG form4SG = MnForm4_SGLocalServiceUtil.
							createMnForm4_SG(CounterLocalServiceUtil.increment(MnForm4_SG.class.getName()));
					
					form4SG.setReportUploadLogId(reportUploadLogId);
					form4SG.setCreatedby(userId);
					
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
										form4SG.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										val = val.replaceAll("//", "-");
										Date date_1 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form4SG.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form4SG.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										long lValue =CommonParser.parseLong(val);// Long.parseLong(val);
										form4SG.setCode(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									form4SG.setDescription_of_securities(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val); // (BigDecimal) decimalFormat.parse(val);
										form4SG.setAmount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form4SG.setPercentage_of_portfolio(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form4SG.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form4SG.toString());
							form4SGJsonArray.put(jsonObject);
							form4SGList.add(form4SG);
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
