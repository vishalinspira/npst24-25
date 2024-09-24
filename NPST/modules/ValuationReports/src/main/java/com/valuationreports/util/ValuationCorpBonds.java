package com.valuationreports.util;

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

import compliance.service.model.NonEq;
import compliance.service.service.NonEqLocalServiceUtil;

public class ValuationCorpBonds {
	
	private static Log _log = LogFactoryUtil.getLog(ValuationCorpBonds.class);
	
	public static void saveValuationCorpBonds(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray nonEqJsonArray, List<NonEq> nonEqList, DecimalFormat decimalFormat,long reportUploadLogId) {
		
		_log.info("Inside saveValuationCorpBonds");
		
		NonEqLocalServiceUtil.deleteNonEqByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("NONEQ");
				//XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					NonEq repoNonEq = (NonEq) NonEqLocalServiceUtil.
							createNonEq(CounterLocalServiceUtil.increment(NonEq.class.getName()));
					
					repoNonEq.setReportUploadLogId(reportUploadLogId);
					repoNonEq.setCreatedby(userId);
					
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
								_log.info("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										try {
											Date date = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setDate_(date);
										} catch (Exception e) {
											 _log.error(e);
										}
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									repoNonEq.setFin(val);
								}
								else if (i == 2) {
									repoNonEq.setScheme(val);
								} 
								else if (i == 3) {
									repoNonEq.setCompany_name(val);
								} 
								else if (i == 4) {
									repoNonEq.setSecurity_code(val);
								} 
								else if (i == 5) {
									repoNonEq.setIsin(val);
								}
								else if (i == 6) {
									repoNonEq.setSecdesc(val);
								}
								else if (i == 7) {
									repoNonEq.setSec(val);
								}
								else if (i == 8) {
									repoNonEq.setCoupon(val);
								} 
								else if (i == 9) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setFaceval(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 10) {
									try {
										long lValue = Long.parseLong(val);
										repoNonEq.setCusthold(lValue);
									} catch (NumberFormatException e) {
										 _log.error(e);
									}
								} 
								else if (i == 11) {
									try {
										long lValue = Long.parseLong(val);
										repoNonEq.setTransit(lValue);
									} catch (NumberFormatException e) {
										 _log.error(e);
									}
								} 
								else if (i == 12) {
									try {
										long lValue = Long.parseLong(val);
										repoNonEq.setLoghold(lValue);
									} catch (Exception e) {
									}
								} 
								else if (i == 13) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setPrice(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 14) {
									repoNonEq.setSource_(val);
								} 
								else if (i == 15) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setValue_(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 16) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_2 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setTradedate(date_2);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 17) {
									repoNonEq.setMethod_(val);
								} 
								else if (i == 18) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setGross_price(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 19) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setAccured_interest(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 20) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setModified_duration(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 21) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setYtm(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 22) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setNse_price(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 23) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										repoNonEq.setTenor_in_years(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 24) {
									repoNonEq.setValue_of_security(val);
								} 
								else if (i == 25) {
									repoNonEq.setIssuerclass(val);
								} 
								else if (i == 26) {
									repoNonEq.setCoupontype(val);
								} 
								else if (i == 27) {
									try {
										int iValue = Integer.parseInt(val);
										repoNonEq.setCouponpaymentfrequency(iValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 28) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_3 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setAllotmentdate(date_3);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 29) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_4 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setFirstipdate(date_4);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 30) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_5 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setLastipdate(date_5);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 31) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_6 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setFinalredemptiondate(date_6);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 32) {
									repoNonEq.setRedemppremium(val);
								} 
								else if (i == 33) {
									repoNonEq.setRating(val);
								} 
								else if (i == 34) {
									repoNonEq.setIlliquiditydiscount(val);
								} 
								else if (i == 35) {
									repoNonEq.setHigherrating(val);
								} 
								else if (i == 36) {
									repoNonEq.setPtc_amort_method(val);
								} 
								else if (i == 37) {
									repoNonEq.setCoupon_form(val);
								} 
								else if (i == 38) {
									repoNonEq.setCompounding_frequency(val);
								} 
								else if (i == 39) {
									repoNonEq.setMatrix_type(val);
								} 
								else if (i == 40) {
									repoNonEq.setSector(val);
								} 
								else if (i == 41) {
									try {
										if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_7 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setCall_date(date_7);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 42) {
									try {
										if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_8 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setPut_date(date_8);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 43) {
									try {
										if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_9 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setSettlement_date(date_9);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 44) {
									try {
										if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_10 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setBenchmark_date(date_10);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 45) {
									try {
										if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											repoNonEq.setTradedate(null);
										}
										else {
											Date date_11 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
											repoNonEq.setConversion_date(date_11);
										}
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 46) {
									repoNonEq.setGoi_serviced(val);
								} 
								else if (i == 47) {
									repoNonEq.setSecured_unsecured(val);
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					repoNonEq.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(repoNonEq.toString());
							nonEqJsonArray.put(jsonObject);
							nonEqList.add(repoNonEq);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
	}

}
