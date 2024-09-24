package com.monthly.pfm.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.MnForm13;
import compliance.service.service.MnForm13LocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form13 {
	
	private static Log _log = LogFactoryUtil.getLog(Form13.class);
	
	public static JSONObject saveForm13(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form13JsonArray, List<MnForm13> form13List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm13");
		MnForm13LocalServiceUtil.deleteMnForm13ByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 2 Portfolio Data";
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
					MnForm13 form13 = MnForm13LocalServiceUtil.
							createMnForm13(CounterLocalServiceUtil.increment(MnForm13.class.getName()));
					
					form13.setReportUploadLogId(reportUploadLogId);
					form13.setCreatedby(userId);
					
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
						if (cell != null && !val.equalsIgnoreCase("certification") && Validator.isNotNull(val)) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
//								_log.info("Val: " + val);
								if (i == 0) {
									if (val != null) {
										form13.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									form13.setScheme_name(val);
								}
								else if (i == 2) {
									form13.setTier_i_tier_ii(val);
								} 
								else if (i == 3) {
									//_log.info("cell2" + cell.getCellType());
									try {
									//	val = val.replaceAll("//", "-");
										Date date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form13.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									form13.setIsin(val);
								} 
								else if (i == 5) {
									form13.setSecurity_name(val);
								}
								else if (i == 6) {
									form13.setIssuer_name(val);
								}
								else if (i == 7) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);
										form13.setNic_code(bg);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									form13.setIndustry_classification(val);
								} 
								else if (i == 9) {
									form13.setSecurity_type(val);
								} 
								else if (i == 10) {
									
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);
										form13.setNo_of_shares_units(bg);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);//  (BigDecimal) decimalFormat.parse(val);
										form13.setMarket_price_per_unit(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 12) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form13.setMarket_value(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 13) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form13.setAum(bg);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 14) {
									form13.setPercentage_to_nav(val);
								} 
								else if (i == 15) {
									form13.setCoupon_rate(val);
								}
								else if (i == 16) {
									try {
										int iValue = Integer.parseInt(val);
										form13.setCoupon_payment_freq(iValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 17) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form13.setPurchase_price(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 18) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form13.setAmount_invested(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 19) {
									//_log.info("cell2" + cell.getCellType());
									try {
										Date date_2 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form13.setPut_date(date_2);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 20) {
									//_log.info("cell2" + cell.getCellType());
									try {
										Date date_3 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form13.setCall_date(date_3);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 21) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form13.setFace_value(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 22) {
									//_log.info("cell2" + cell.getCellType());
									try {
										Date date_4 =cell.getDateCellValue(); //cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form13.setMaturity_date(date_4);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 23) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form13.setResidual_maturity(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 24) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);//  (BigDecimal) decimalFormat.parse(val);
										form13.setModified_duration(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 25) {
									form13.setPurchase_ytm(val);
								}
								else if (i == 26) {
									form13.setCurrent_ytm(val);
								} 
								else if (i == 27) {
									try {
										BigDecimal bg =CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form13.setNse_closing_price(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 28) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(val);
										form13.setBse_closing_price(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 29) {
									form13.setLowest_rating(val);
								} 
								else if (i == 30) {
									form13.setLowest_rating_agency(val);
								} 
								else if (i == 31) {
									form13.setSecond_lowest_rating(val);
								} 
								else if (i == 32) {
									form13.setSecond_lowest_rating_agency(val);
								} 
								else if (i == 33) {
									try {
										long lValue = Long.parseLong(val);
										form13.setCode(lValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							//_log.info("done");
							break rowloop;
						}
					}
					form13.setCreatedon(new Date());
						
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form13.toString());
							//form13JsonArray.put(jsonObject);
							form13List.add(form13);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}catch(IOException e) {
				 _log.error(e);
			} catch (InvalidFormatException e) {
				_log.error(e);
			}
		return resultJsonObject; 
		
	}

}
