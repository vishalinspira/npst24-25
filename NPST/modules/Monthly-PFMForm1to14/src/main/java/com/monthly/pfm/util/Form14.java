package com.monthly.pfm.util;

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

import compliance.service.model.MnForm14;
import compliance.service.service.MnForm14LocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form14 {
	
private static Log _log = LogFactoryUtil.getLog(Form14.class);
	
	public static JSONObject saveForm14(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form14JsonArray, List<MnForm14> form14List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm14");
		MnForm14LocalServiceUtil.deleteMnForm14ByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 3 Transactions Data";
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
					MnForm14 form14 = MnForm14LocalServiceUtil.
							createMnForm14(CounterLocalServiceUtil.increment(MnForm14.class.getName()));
					
					form14.setReportUploadLogId(reportUploadLogId);
					form14.setCreatedby(userId);
					
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
								//_log.info("Val: " + val);
								if (i == 0) {
									if (val != null) {
										form14.setPension_fund_name(val);
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
										_log.debug("Reporting_date" + val+ "date"+date_1.toString());
										if(Validator.isNull(date_1)) {
											throw new Exception();
										}
										form14.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form14.setScheme_name(val);
								}
								else if (i == 3) {
									form14.setTier_i_tier_ii(val);
								} 
								else if (i == 4) {
									form14.setTransaction_type(val);
								} 
								else if (i == 5) {
									try {
										//val = val.replaceAll("//", "-");
										Date date_2 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form14.setTransaction_date(date_2);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 6) {
									form14.setIsin(val);
								}
								else if (i == 7) {
									form14.setSecurity_name(val);
								}
								else if (i == 8) {
									form14.setIssuer_name(val);
								} 
								else if (i == 9) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form14.setNic_code(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 10) {
									form14.setIndustry_classification(val);
								} 
								else if (i == 11) {
									form14.setSecurity_type(val);
								} 
								else if (i == 12) {
									try {
										BigDecimal bg =  CommonParser.parseBigDecimal(val);
										form14.setNo_of_shares(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 13) {
									//BigDecimal bg =  CommonParser.parseBigDecimal(val);
									form14.setPurchase_ytm(val);
								}
								else if (i == 14) {
									try {
										BigDecimal bg =  CommonParser.parseBigDecimal(val);
										form14.setAmount_purchase_or_sale(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 15) {
									try {
										BigDecimal bg =  CommonParser.parseBigDecimal(val);
										form14.setCoupon_rate_percent(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 16) {
									//BigDecimal bg =  CommonParser.parseBigDecimal(val);
									try {
										int iValue = Integer.parseInt(val);
										form14.setCoupon_frequency(iValue);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 17) {
									try {
										Date date_3 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form14.setPut_date(date_3);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 18) {
									try {
										Date date_4 =cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form14.setCall_date(date_4);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 19) {
									try {
										BigDecimal bg =  CommonParser.parseBigDecimal(val);
										form14.setFace_value(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 20) {
									try {
										Date date_5 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form14.setMaturity_date(date_5);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 21) {
									//Date date_6 = new SimpleDateFormat("dd-MMM-yyyy").parse(val);
									try {
										BigDecimal bg =  CommonParser.parseBigDecimal(val);
										form14.setResidual_maturity_date(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 22) {
									try {
										BigDecimal bg =  CommonParser.parseBigDecimal(val);
										form14.setModified_duration_on_tran_date(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 23) {
									form14.setPurchase_ytm(val);
								}
								else if (i == 24) {
									form14.setLowest_rating(val);
								} 
								else if (i == 25) {
									form14.setLowest_rating_agency(val);
								} 
								else if (i == 26) {
									form14.setSecond_lowest_rating(val);
								} 
								else if (i == 27) {
									form14.setSecond_lowest_rating_agency(val);
								} 
								else if (i == 28) {
									try {
										long lValue = CommonParser.parseLong(val);
										form14.setCode(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 29) {
									form14.setBroker_name(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							//_log.info("done");
							break rowloop;
						}
					}
					form14.setCreatedon(new Date());
						
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form14.toString());
							//form14JsonArray.put(jsonObject);
							form14List.add(form14);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}catch (Exception e) {
				 _log.error(e);
				resultJsonObject.put("status", false);
				resultJsonObject.put("msg", CommonParser.fileExceptionMsg +sheetName);
				return resultJsonObject;
			}
		return resultJsonObject;
		
	}

}
