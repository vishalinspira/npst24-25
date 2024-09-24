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

import compliance.service.model.Quaeterlyfromfour;
import compliance.service.service.QuaeterlyfromfourLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class ParseFormFour {
	
	private static Log _log = LogFactoryUtil.getLog(ParseFormFour.class);
	
	public static JSONObject saveSheetFormfour(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qformfourJsonArray, List<Quaeterlyfromfour> qformfourList,long reportUploadLogId) {
		_log.info("saveSheetFormfour:::::::::::::::::::::::::::");
		
		QuaeterlyfromfourLocalServiceUtil.deleteQuaeterlyfromfourByReportUploadLogId(reportUploadLogId);
		String sheetName="Form4";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
//				int sheetcount = 23;
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					
				//	String sheetName = sheet.getSheetName();
					boolean sheet20 = sheetName.equalsIgnoreCase(sheetName);
					//if(sheetcount>23) {
					if(sheet20 && sheets!=null) {
						
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
				
				rowLoop:
			//	while (rows.hasNext())
					for (int g=1; g<sheet.getPhysicalNumberOfRows() -1; g++){
					
							//NpsTrustFee npstrustfee =NpsTrustFeeLocalServiceUtil.createNpsTrustFee(CounterLocalServiceUtil.increment(NpsTrustFee.class.getName()));
					Quaeterlyfromfour qformfour=QuaeterlyfromfourLocalServiceUtil.createQuaeterlyfromfour(CounterLocalServiceUtil.increment(Quaeterlyfromfour.class.getName()));
					qformfour.setCreatedby(userId);
					qformfour.setReportUploadLogId(reportUploadLogId);	
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
										if (i == 0) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													qformfour.setSr_no(bg);
												} catch (Exception e) {
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
										}
										else if (i == 1) {
											qformfour.setPension_fund_code(val);
										}
										else if (i == 2) {
											//qformfour.setPension_fund_code(val);
											qformfour.setPension_fund_name(val);
										}
										else if (i == 3) {
											try {
												Date dateFormat= CommonParser.parseDate(val,cell);
												qformfour.setReporting_date(dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 4) {
											qformfour.setScheme_name(val);
										} 
										else if (i == 5) {
											try {
												BigDecimal market_value_as_on_quarter = CommonParser.parseBigDecimal(val);
												qformfour.setMarket_value_as_on_quarter(market_value_as_on_quarter);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 6) {
											try {
												BigDecimal market_value_as_on_prev_qrtr = CommonParser.parseBigDecimal(val);
												qformfour.setMarket_value_as_on_prev_qrtr(market_value_as_on_prev_qrtr);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 7) {
											try {
												BigDecimal market_val_as_on_2nd_prev_qrtr = CommonParser.parseBigDecimal(val);
												qformfour.setMarket_val_as_on_2nd_prev_qrtr(market_val_as_on_2nd_prev_qrtr);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 8) {
											try {
												BigDecimal market_val_as_on_3rd_prev_qrtr = CommonParser.parseBigDecimal(val);
												qformfour.setMarket_val_as_on_3rd_prev_qrtr(market_val_as_on_3rd_prev_qrtr);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 9) {
											try {
												BigDecimal _3_year_rolling_cagr = CommonParser.parseBigDecimal(val);
												qformfour.set_3_year_rolling_cagr(_3_year_rolling_cagr);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 10) {
											try {
												BigDecimal annualized_portfolio_return = CommonParser.parseBigDecimal(val);
												qformfour.setAnnualized_portfolio_ret_yield(annualized_portfolio_return);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
//										else if (i == 10) {
//											
//											
//										}
										
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							qformfour.setCreatedon(new Date());
							
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
								JSONObject formfourJsonObject = JSONFactoryUtil.createJSONObject(qformfour.toString());
								qformfourJsonArray.put(formfourJsonObject);
								qformfourList.add(qformfour);
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
