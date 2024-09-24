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

import compliance.service.model.MnForm8;
import compliance.service.service.MnForm8LocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form8 {
	
private static Log _log = LogFactoryUtil.getLog(Form8.class);
	
	public static JSONObject saveForm8(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form8JsonArray, List<MnForm8> form8List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm8");
		MnForm8LocalServiceUtil.deleteMnForm8ByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 6_as_on_date";
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
					MnForm8 form8 = MnForm8LocalServiceUtil.
							createMnForm8(CounterLocalServiceUtil.increment(MnForm8.class.getName()));
					
					form8.setReportUploadLogId(reportUploadLogId);
					form8.setCreatedby(userId);
					
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
										form8.setPension_fund_name(val);
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
										Date date_1 = cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form8.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									try {
										String s = String.valueOf(cell.getNumericCellValue()).replaceAll("\\.\\d+$", "");
										Integer sl_no=Integer.parseInt(s);
										form8.setSl_no(sl_no);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									form8.setSecurity_name(val);
								} 
								else if (i == 4) {
									form8.setIsin_code(val);
								} 
								else if (i == 5) {
									form8.setScheme(val);
								}
								else if (i == 6) {
									form8.setIssuer_name(val);
								}
								else if (i == 7) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form8.setMarket_value_reporting_date(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form8.setPurchase_value(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									try {
										//val = val.replaceAll("//", "-");
										Date date_2 = cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form8.setDate_of_purchase(date_2);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 10) {
									form8.setRating_at_time_purchase(val);
								} 
								else if (i == 11) {
									form8.setRating_agency_time_of_purchase(val);
								} 
								else if (i == 12) {
									try {
								//		val = val.replaceAll("//", "-");
										Date date_3 =  cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form8.setRating_date_of_purchase(date_3);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 13) {
									form8.setFirst_downgraded_rating(val);
								} 
								else if (i == 14) {
									try {
									//	val = val.replaceAll("//", "-");
										Date date_4 = cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form8.setDate_of_first_downgrade(date_4);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 15) {
									form8.setRating_agency_downgraded(val);
								}
								else if (i == 16) {
									form8.setCurrent_rating(val);
								}
								else if (i == 17) {
									form8.setCurrent_rating_agency(val);
								}
								else if (i == 18) {
									try {
								//		val = val.replaceAll("//", "-");
										Date date_5 = cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form8.setCurrent_rating_date(date_5);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form8.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form8.toString());
							form8JsonArray.put(jsonObject);
							form8List.add(form8);
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
