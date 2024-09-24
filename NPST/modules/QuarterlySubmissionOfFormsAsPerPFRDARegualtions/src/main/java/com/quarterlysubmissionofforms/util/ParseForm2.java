package com.quarterlysubmissionofforms.util;

import com.daily.pfm.service.model.QuarterlySubCompForms_2;
import com.daily.pfm.service.service.QuarterlySubCompForms_2LocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ParseForm2 {
	
	private static Log _log = LogFactoryUtil.getLog(ParseForm2.class);
	
	public static JSONObject saveSheetForm2(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx,
			boolean isexcelhaserror, JSONArray qrsubCompForms_2JsonArray, List<QuarterlySubCompForms_2> subCompForms_2List,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("ParseQuarterlyCompForm::::::::::::::::::::::");
		QuarterlySubCompForms_2LocalServiceUtil.deleteQuarterlySubCompForms_2ByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
			//	XSSFSheet sheets = workbook.getSheet("Form 1A");
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					
					String sheetName = sheet.getSheetName();
					
					boolean sheet1 = sheetName.equalsIgnoreCase("Form 3_ Other_schemes");
					boolean sheet2 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - A - Tier- I");
					boolean sheet3 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - E - Tier - I");
					boolean sheet4 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - E - Tier - II");
					boolean sheet5 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - C - Tier - I");
					boolean sheet6 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - C - Tier - II");
					boolean sheet7 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - G - Tier - I");
					boolean sheet8 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - G - Tier - II");
					boolean sheet9 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - CG");
					boolean sheet10 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - SG");
					boolean sheet11 = sheetName.equalsIgnoreCase("FORM_3 - Scheme -Corp- CG");
					boolean sheet12 = sheetName.equalsIgnoreCase("FORM_3 - Scheme -APY");
					boolean sheet13 = sheetName.equalsIgnoreCase("FORM_3 - Scheme - NPS Lite");
					if(sheet1 || sheet2 || sheet3 || sheet4 ||
						sheet5|| sheet6 || sheet7 || sheet8 || sheet9 || sheet10 || sheet11 || sheet12 || sheet13  && sheets!=null) {
						
						_log.info("Inside if loop in second sheet");
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					_log.info("Inside while loop in second sheet");
					  QuarterlySubCompForms_2 subCompForms_2 = QuarterlySubCompForms_2LocalServiceUtil.createQuarterlySubCompForms_2((CounterLocalServiceUtil.increment(QuarterlySubCompForms_2.class.getName())));
					
					subCompForms_2.setReportUploadLogId(reportUploadLogId);
					subCompForms_2.setCreatedby(userId);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						int lastColumn = Math.max(row.getLastCellNum(), 0);
						
						for (int i = 0; i < lastColumn; i++) {
							XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							if (cell != null) {
								if (val != null)
									val = val.trim();
								if(rowCount > 1) {
									if (i == 0) {
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											//subCompForms_2.setPension_fund_code(val);
											subCompForms_2.setPension_fund_name(val);
										}
										 else {
												errorObj.put("cellno", 2);
												errorObj.put("msg", "It is not a number");
												isError = true;
											}
									}
									else if (i == 1) {
										subCompForms_2.setScheme_name(val);
									}
									else if (i == 2) {
										try {
											subCompForms_2.setReporting_date(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 3) {
										subCompForms_2.setParticulars(val);
									} 
									else if (i == 4) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_2.setOpbal_book_value(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 5) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_2.setOpbal_market_value(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 6) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_2.setPurchase_book_value(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 7) {
										subCompForms_2.setPur_prcnt_of_total_portfolio(val);
									}
									else if (i == 8) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_2.setSales_book_value(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 9) {
										subCompForms_2.setSales_prcnt_of_total_portfolio(val);
									}
									else if (i == 10) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_2.setAdj_book_value(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} else if (i == 11) {
										subCompForms_2.setAdj_Prcnt_of_Total_Portfolio(val);
									} else if (i == 12) {
											/*
											 * try { BigDecimal bg = CommonParser.parseBigDecimal(val);
											 * subCompForms_2.setAdj_market_value(bg.stripTrailingZeros()); } catch
											 * (Exception e) { _log.info("error parsing big dec"+val);
											 * resultJsonObject.put("status", false); resultJsonObject.put("msg",
											 * CommonParser.numberExceptionMsg+sheetName); return resultJsonObject; }
											 */
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											subCompForms_2.setClosingbal_market_value(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} else if (i == 13) {
										/*try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);*/
											subCompForms_2.setClosingbal_prcnt_tot_portfolio(val);
										/*} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}*/
									} /*else if (i == 14) {
										 
												//subCompForms_2.setClosingbal_prcnt_tot_portfolio(val);
									} */
										
								}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							subCompForms_2.setCreatedon(new Date());
							
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
								subCompForms_2List.add(subCompForms_2);
							}
							rowCount++;
						}
							rowCount = 1;
					}
					_log.info("isexcelhaserror ::::::::::"+isexcelhaserror);
				}
			}
		}catch (Exception e) {
			_log.error(e);
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg",  CommonParser.fileExceptionMsg);
			return resultJsonObject;
		}
		return resultJsonObject;
	}

}
