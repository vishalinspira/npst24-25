package com.monthly.mis.report.util;

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

import compliance.service.model.MnNpsLiteSchemeMonthAnalysis;
import compliance.service.service.MnNpsLiteSchemeMonthAnalysisLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class NpsLiteSchemeMonthAnalysis {
	
	private static Log _log = LogFactoryUtil.getLog(NpsLiteSchemeMonthAnalysis.class);
	
	public static JSONObject saveNpsLiteSchemeMonthAnalysis(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray npsLiteSchemeMonthAnalysisJsonArray, List<MnNpsLiteSchemeMonthAnalysis> npsLiteSchemeMonthAnalysisList, String cra, long reportUploadLogId) {
		_log.info("Inside saveNpsLiteSchemeMonthAnalysis");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("NPSLite_Scheme_Month_Analysis");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					MnNpsLiteSchemeMonthAnalysis npsLiteSchemeMonthAnalysis = MnNpsLiteSchemeMonthAnalysisLocalServiceUtil.
							createMnNpsLiteSchemeMonthAnalysis(CounterLocalServiceUtil.increment(MnNpsLiteSchemeMonthAnalysis.class.getName()));
							
					npsLiteSchemeMonthAnalysis.setCreatedby(userId);
					npsLiteSchemeMonthAnalysis.setCra(cra);
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
								_log.debug("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										//_log.info("cell " + cell.getCellType());
										npsLiteSchemeMonthAnalysis.setParticulars(val);
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											isError = true;
										}
								}
								else if (i == 1) {
									npsLiteSchemeMonthAnalysis.setYear_(val);
								}
								else if (i == 2) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setApr(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setMay(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setJun(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 5) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setJul(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 6) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setAug(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 7) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setSep(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 8) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setOct(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 9) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setNov(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 10) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setDec(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 11) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setJan(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 12) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setFeb(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 13) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										npsLiteSchemeMonthAnalysis.setMar(bg.stripTrailingZeros());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
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
					npsLiteSchemeMonthAnalysis.setCreatedon(new Date());
					npsLiteSchemeMonthAnalysis.setReportUploadLogId(reportUploadLogId);
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(npsLiteSchemeMonthAnalysis.toString());
							//npsLiteSchemeMonthAnalysisJsonArray.put(jsonObject);
							npsLiteSchemeMonthAnalysisList.add(npsLiteSchemeMonthAnalysis);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
				 resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
				 return resultJsonObject;
			}
			return resultJsonObject;
		
		
	}
	
	

}
