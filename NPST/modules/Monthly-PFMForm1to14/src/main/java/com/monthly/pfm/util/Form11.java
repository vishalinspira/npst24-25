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

import compliance.service.model.MnForm11;
import compliance.service.model.MnForm11_NonSponsor;
import compliance.service.service.MnForm11LocalServiceUtil;
import compliance.service.service.MnForm11_NonSponsorLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form11 {
	
	private static Log _log = LogFactoryUtil.getLog(Form11.class);
	
	public static JSONObject saveForm11(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form11JsonArray, List<MnForm11> form11List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm11");
		MnForm11LocalServiceUtil.deleteMnForm11ByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 8_Sponsor";
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
					MnForm11 form11 = MnForm11LocalServiceUtil.
							createMnForm11(CounterLocalServiceUtil.increment(MnForm11.class.getName()));
					
					form11.setReportUploadLogId(reportUploadLogId);
					form11.setCreatedby(userId);
					
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
										form11.setPension_fund_name(val);
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
										form11.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form11.setScheme_name(val);
								} 
								else if (i == 3) {
									form11.setName_of_group(val);
								} 
								else if (i == 4) {
									form11.setInvestee_company(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form11.setEq_perm_limit_of_paidupcapital(bg.stripTrailingZeros());
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
										form11.setEq_permissible_limit_of_aum(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
										form11.setEq_actual_investment(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form11.setEq_deviation_percentage(val);
								} 
								else if (i == 9) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form11.setDebt_limit_of_networth(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 10) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form11.setDebt_limit_of_aum(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form11.setDebt_actual_investment(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 12) {
									form11.setDebt_deviation_percentage(val);
								} 
//								else if (i == 13) {
//									BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
//									form11.setExposure_exceptions(bg.stripTrailingZeros());
//								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form11.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form11.toString());
							form11JsonArray.put(jsonObject);
							form11List.add(form11);
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
	
	public static JSONObject saveForm11NonSponsor(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form11_NonSponsorJsonArray, List<MnForm11_NonSponsor> form11_NonSponsorList, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm11NonSponsor");
		MnForm11_NonSponsorLocalServiceUtil.deleteMnForm11_NonSponsorByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 8_Non_Sponsored";
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
					MnForm11_NonSponsor form11_NonSponsor = MnForm11_NonSponsorLocalServiceUtil.
							createMnForm11_NonSponsor(CounterLocalServiceUtil.increment(MnForm11_NonSponsor.class.getName()));
					
					form11_NonSponsor.setReportUploadLogId(reportUploadLogId);
					form11_NonSponsor.setCreatedby(userId);
					
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
										form11_NonSponsor.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										Date date_1 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form11_NonSponsor.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form11_NonSponsor.setScheme_name(val);
								} 
								else if (i == 3) {
									form11_NonSponsor.setName_of_group(val);
								} 
								else if (i == 4) {
									form11_NonSponsor.setInvestee_company(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form11_NonSponsor.setEq_perm_limit_of_paidupcapital(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
										form11_NonSponsor.setEq_permissible_limit_of_aum(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
										form11_NonSponsor.setEq_actual_investment(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form11_NonSponsor.setEq_deviation_percentage(val);
								} 
								else if (i == 9) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form11_NonSponsor.setDebt_limit_of_networth(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 10) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
										form11_NonSponsor.setDebt_limit_of_aum(bg.stripTrailingZeros());
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
										form11_NonSponsor.setDebt_actual_investment(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 12) {
									form11_NonSponsor.setDebt_deviation_percentage(val);
								} 
//								else if (i == 13) {
//									BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
//									form11_NonSponsor.setExposure_exceptions(bg.stripTrailingZeros());
//								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form11_NonSponsor.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form11_NonSponsor.toString());
							form11_NonSponsorJsonArray.put(jsonObject);
							form11_NonSponsorList.add(form11_NonSponsor);
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
