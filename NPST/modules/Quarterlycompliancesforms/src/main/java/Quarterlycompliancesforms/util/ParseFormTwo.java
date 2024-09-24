package Quarterlycompliancesforms.util;

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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.Quaeterlyfromtwo;
import compliance.service.service.QuaeterlyfromtwoLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class ParseFormTwo {
	
	private static Log _log = LogFactoryUtil.getLog(ParseFormTwo.class);
	
	public static JSONObject saveSheetFormtwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qformtwoJsonArray, List<Quaeterlyfromtwo> qformtwoList,long reportUploadLogId) {
		_log.info("saveSheetFormtwo:::::::::::::::::::::::::::");
		QuaeterlyfromtwoLocalServiceUtil.deleteQuaeterlyfromtwoByReportUploadLogId(reportUploadLogId);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
//				int sheetcount = 7;
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					
					String sheetName = sheet.getSheetName();
					boolean sheet4 = sheetName.equalsIgnoreCase("FORM 2 -Scheme -TAX-T2");
					boolean sheet5 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - A - Tier- I");
					boolean sheet6 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - E - Tier - I");
					boolean sheet7 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - E - Tier - II");
					boolean sheet8 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - C - Tier - I");
					boolean sheet9 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - C - Tier - II");
					boolean sheet10 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - G - Tier - I");
					boolean sheet11 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - G - Tier - II");
					
					boolean sheet12 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - CG");
					boolean sheet13 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - SG");
					boolean sheet14 = sheetName.equalsIgnoreCase("FORM 2 - Scheme -Corp- CG");
					boolean sheet15 = sheetName.equalsIgnoreCase("FORM 2 - Scheme -APY");
					boolean sheet16 = sheetName.equalsIgnoreCase("FORM 2 - Scheme - NPS Lite");
					
					//if(sheetcount>7) {
					if(sheet4 || sheet5 || sheet6 || sheet7 ||sheet8 || sheet9 || sheet10 || sheet11
						||sheet12 ||sheet13 ||sheet14 ||sheet15||sheet16	
							&& sheets!=null) {	
						_log.info("Inside if loop in second sheet");
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
				
				rowLoop:
			//	while (rows.hasNext()) 
					for (int g=1; g<sheet.getPhysicalNumberOfRows() -1; g++){
					_log.info("Inside while loop in second sheet");
							//NpsTrustFee npstrustfee =NpsTrustFeeLocalServiceUtil.createNpsTrustFee(CounterLocalServiceUtil.increment(NpsTrustFee.class.getName()));
					Quaeterlyfromtwo qformtwo=QuaeterlyfromtwoLocalServiceUtil.createQuaeterlyfromtwo(CounterLocalServiceUtil.increment(Quaeterlyfromtwo.class.getName()));
					        
					qformtwo.setReportUploadLogId(reportUploadLogId);    
					qformtwo.setCreatedby(userId);
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							 XSSFRow row = sheet.getRow(g);
						//	XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							int lastColumn = Math.max(row.getLastCellNum(), 0);
							
							for (int i = 0; i < lastColumn; i++) {
								XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								if (cell != null) {
		
									DataFormatter formatter = new DataFormatter();
									String val = formatter.formatCellValue(cell);
									if (val != null)
										val = val.trim();
									_log.info("val:::"+val);
									if(rowCount > 1) {
										//_log.info("rowCount " + rowCount);
										if (i == 0) {
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												//Long srno=Long.parseLong(val);
												//qformtwo.setPension_fund_code(val);
												qformtwo.setPension_fund_name(val);
											}
											 else {
													errorObj.put("cellno", 2);
													errorObj.put("msg", "It is not a number");
													
													isError = true;
												}
										}
										else if (i == 1) {
											qformtwo.setScheme_name(val);
										}
										else if (i == 2) {
											try {
												Date dateFormat= CommonParser.parseDate(val,cell);
												qformtwo.setReporting_date(dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 3) {
											qformtwo.setParticulars(val);
										} 
										else if (i == 4) {
											try {
												BigDecimal book_value = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setOpbal_book_value(book_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 5) {
											try {
												BigDecimal market_value = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setOpbal_market_value(market_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 6) {
											try {
												BigDecimal purchase_book_value = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setPurchase_book_value(purchase_book_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 7) {
											try {
												//BigDecimal pur_prcnt_of_total_portfolio = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setPur_prcnt_of_total_portfolio(val);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 8) {
											try {
												BigDecimal sales_book_value = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setSales_book_value(sales_book_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 9) {
											try {
												//BigDecimal sales_prcnt_of_total_portfolio = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setSales_prcnt_of_total_portfolio(val);
											} catch (Exception e) {
												 _log.error(e);
											}
										}
										else if (i == 10) {
											try {
												BigDecimal adj_book_value = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setAdj_book_value(adj_book_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 11) {
											try {
												BigDecimal adj_market_value = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setAdj_market_value(adj_market_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 12) {
											try {
												BigDecimal closingbal_market_value = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setClosingbal_market_value(closingbal_market_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 13) {
											try {
												//BigDecimal closingbal_prcnt_tot_portfolio = (BigDecimal) decimalFormat.parse(val);
												qformtwo.setClosingbal_prcnt_tot_portfolio(val);
											} catch (Exception e) {
												 _log.error(e);
											}
										}
//										else if (i == 14) {
//											
//										}
										
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							qformtwo.setCreatedon(new Date());
							
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
								JSONObject formtwoJsonObject = JSONFactoryUtil.createJSONObject(qformtwo.toString());
								qformtwoJsonArray.put(formtwoJsonObject);
								qformtwoList.add(qformtwo);
							}
							rowCount++;
						}
						_log.info(sheetName +" rowcount"+rowCount);
						rowCount = 1;
					}
//					sheetcount++;
				}
			}
		}catch (Exception e) {
				 _log.error(e);
			}
		return resultJsonObject;
	}

}
