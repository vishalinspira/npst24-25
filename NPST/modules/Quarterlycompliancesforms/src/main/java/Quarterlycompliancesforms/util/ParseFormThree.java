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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import compliance.service.model.Quaeterlyfromthree;
import compliance.service.service.QuaeterlyfromthreeLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class ParseFormThree {
	
	private static Log _log = LogFactoryUtil.getLog(ParseFormThree.class);
	
	public static JSONObject saveSheetFormthree(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qformthreeJsonArray, List<Quaeterlyfromthree> qformthreeList,long reportUploadLogId) {
		_log.info("saveSheetFormthree:::::::::::::::::::::::::::");
		QuaeterlyfromthreeLocalServiceUtil.deleteQuaeterlyfromthreeByReportUploadLogId(reportUploadLogId);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
//				int sheetcount = 15;
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					
					String sheetName = sheet.getSheetName();
					boolean sheet12 = sheetName.equalsIgnoreCase("FORM 3 Scheme - A - Tier -  I");
					boolean sheet13 = sheetName.equalsIgnoreCase("FORM 3 Scheme -TAX-T2");
					boolean sheet14 = sheetName.equalsIgnoreCase("FORM 3 Scheme - E - Tier - I");
					boolean sheet15 = sheetName.equalsIgnoreCase("FORM 3 Scheme - E - Tier - II");
					boolean sheet16 = sheetName.equalsIgnoreCase("FORM 3 Scheme - C - Tier - I");
					boolean sheet17 = sheetName.equalsIgnoreCase("FORM 3 Scheme - C - Tier - II");
					boolean sheet18 = sheetName.equalsIgnoreCase("FORM 3 Scheme - G - Tier - I");
					boolean sheet19 = sheetName.equalsIgnoreCase("FORM 3 Scheme - G - Tier - II");
					
					boolean sheet20 = sheetName.equalsIgnoreCase("FORM 3 Scheme - CG");
					boolean sheet21 = sheetName.equalsIgnoreCase("FORM 3 Scheme - SG");
					boolean sheet22 = sheetName.equalsIgnoreCase("FORM 3 Scheme - Corp CG");
					boolean sheet23 = sheetName.equalsIgnoreCase("FORM 3 Scheme - APY");
					boolean sheet24 = sheetName.equalsIgnoreCase("FORM 3 Scheme - NPS Lite");
					
					//if(sheetcount>15) {
					
					if(sheet12 || sheet13 || sheet14 || sheet15 ||sheet16 || sheet17 || sheet18 || sheet19 
						|| sheet20||sheet21 || sheet22|| sheet23|| sheet24 && sheets!=null) {	
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
				
				rowLoop:
			//	while (rows.hasNext())
					for (int g=1; g<sheet.getPhysicalNumberOfRows() -1; g++){
						_log.info("Inside while loop in third sheet");
							//NpsTrustFee npstrustfee =NpsTrustFeeLocalServiceUtil.createNpsTrustFee(CounterLocalServiceUtil.increment(NpsTrustFee.class.getName()));
					Quaeterlyfromthree qformthree=QuaeterlyfromthreeLocalServiceUtil.createQuaeterlyfromthree(CounterLocalServiceUtil.increment(Quaeterlyfromthree.class.getName()));
					qformthree.setReportUploadLogId(reportUploadLogId);
					qformthree.setCreatedby(userId);
							
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
									if(rowCount >= 1) {
										if (i == 0) {
												qformthree.setPension_fund_code(val);
										}
										else if (i == 1) {
											qformthree.setPension_fund_name(val);
										}
										else if (i == 2) {
											qformthree.setScheme_name(val);
										}
										else if (i == 3) {
											try {
												Date dateFormat= CommonParser.parseDate(val,cell);
												qformthree.setReporting_date(dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 4) {
											qformthree.setParticulars(val);
										} 
										else if (i == 5) {
											try {
												BigDecimal opening_bal_units = CommonParser.parseBigDecimal(val);
												qformthree.setOpening_bal_units(opening_bal_units);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 6) {
											try {
												BigDecimal opening_bal_amount = CommonParser.parseBigDecimal(val);
												qformthree.setOpening_bal_amount(opening_bal_amount);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i ==7) {
											try {
												BigDecimal purchase_units = CommonParser.parseBigDecimal(val);
												qformthree.setPurchase_units(purchase_units);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 8) {
											try {
												BigDecimal purchase_book_value = CommonParser.parseBigDecimal(val);
												qformthree.setPurchase_book_value(purchase_book_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 9) {
											try {
												//BigDecimal pur_prcnt_of_total_portfolio = CommonParser.parseBigDecimal(val);
												qformthree.setPur_prcnt_of_total_portfolio(val);
											} catch (Exception e) {
												 _log.error(e);
											}
										}
										else if (i == 10) {
											try {
												BigDecimal sales_units = CommonParser.parseBigDecimal(val);
												qformthree.setSales_units(sales_units);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 11) {
											try {
												BigDecimal sales_book_value = CommonParser.parseBigDecimal(val);
												qformthree.setSales_book_value(sales_book_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 12) {
											try {
												//BigDecimal sales_prcnt_of_total_portfolio = CommonParser.parseBigDecimal(val);
												qformthree.setSales_prcnt_of_total_portfolio(val);
											} catch (Exception e) {
												 _log.error(e);
											}
										}
										else if (i == 13) {
											try {
												BigDecimal closing_bal_units = CommonParser.parseBigDecimal(val);
												qformthree.setClosing_bal_units(closing_bal_units);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 14) {
											try {
												BigDecimal closing_bal_book_value = CommonParser.parseBigDecimal(val);
												qformthree.setClosing_bal_book_value(closing_bal_book_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 15) {
											try {
												BigDecimal closing_bal_market_value = CommonParser.parseBigDecimal(val);
												qformthree.setClosing_bal_market_value(closing_bal_market_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 16) {
											try {
												//BigDecimal closingbal_prcnt_tot_portfolio = CommonParser.parseBigDecimal(val);
												qformthree.setClosingbal_prcnt_tot_portfolio(val);
											} catch (Exception e) {
												 _log.error(e);
											}
										}
//										else if (i == 16) {
//											
//										}
										
									}
								}else if(i==0 && rowCount >= 1) {
									break rowLoop;
								}
							}
							qformthree.setCreatedon(new Date());
							
							if (isError && rowCount >= 1) {
								errorArray.put(errorObj);
								errorRow = xx.createRow(errorRowCount);
								errorRowCount++;
								XSSFCell rowCountCel = errorRow.createCell(1);
								rowCountCel.setCellValue(rowCount);
								XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
								cellError.setCellValue(errorObj.getString("msg"));
								isexcelhaserror = true;
							} else if (rowCount >= 1) {
								JSONObject formthreeJsonObject = JSONFactoryUtil.createJSONObject(qformthree.toString());
								qformthreeJsonArray.put(formthreeJsonObject);
								qformthreeList.add(qformthree);
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
