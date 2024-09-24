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

import compliance.service.model.Quaeterlyfromfive;
import compliance.service.service.QuaeterlyfromfiveLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class ParseFormFive {
	
	private static Log _log = LogFactoryUtil.getLog(ParseFormFive.class);
	
	public static JSONObject saveSheetFormfive(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qformfiveJsonArray, List<Quaeterlyfromfive> qformfiveList,long reportUploadLogId) {
		_log.info("saveSheetFormfive:::::::::::::::::::::::::");
		QuaeterlyfromfiveLocalServiceUtil.deleteQuaeterlyfromfiveByReportUploadLogId(reportUploadLogId);
		String sheetName="Form5";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
//				int sheetcount = 24;
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					
				//	String sheetName = sheet.getSheetName();
					boolean sheet21 = sheetName.equalsIgnoreCase(sheetName);
					
					//if(sheetcount>0) {
					
					if(sheet21 && sheets!=null) {	
						
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
				
				rowLoop:
			//	while (rows.hasNext())
					for (int g=1; g<sheet.getPhysicalNumberOfRows() -1; g++){
					
							//NpsTrustFee npstrustfee =NpsTrustFeeLocalServiceUtil.createNpsTrustFee(CounterLocalServiceUtil.increment(NpsTrustFee.class.getName()));
					Quaeterlyfromfive qformfive=QuaeterlyfromfiveLocalServiceUtil.createQuaeterlyfromfive(CounterLocalServiceUtil.increment(Quaeterlyfromfive.class.getName()));
					qformfive.setCreatedby(userId);
					qformfive.setReportUploadLogId(reportUploadLogId);
					
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
											qformfive.setPension_fund_code(val);
										}
										else if (i == 1) {
											qformfive.setPension_fund_name(val);
										}
										else if (i == 2) {
											try {
												Date dateFormat= CommonParser.parseDate(val,cell);
												qformfive.setReporting_date(dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
											
										}
										else if (i == 3) {
											qformfive.setScheme_name(val);
										} 
										else if (i == 4) {
											qformfive.setSecurity_name(val);
										} 
										else if (i == 5) {
											qformfive.setInstrument_type(val);
										} 
										else if (i == 6) {
											try {
												//BigDecimal interest_rate = CommonParser.parseBigDecimal(val);
												qformfive.setInterest_rate_prcnt(val);
											} catch (Exception e) {
												 _log.error(e);
											}
										} 
										else if (i == 7) {
											qformfive.setHas_there_been_revision(val);
										}
										else if (i == 8) {
											try {
												BigDecimal total_book_value = CommonParser.parseBigDecimal(val);
												qformfive.setTotal_otstndgbook_value(total_book_value);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 9) {
											try {
												BigDecimal default_principal = CommonParser.parseBigDecimal(val);
												qformfive.setDefault_principal_bookvalue(default_principal);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 10) {
											try {
												BigDecimal default_interest = CommonParser.parseBigDecimal(val);
												qformfive.setDefault_interest_bookvalue(default_interest);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 11) {
											try {
												Date dateFormat= CommonParser.parseDate(val,cell);
												qformfive.setPrincipal_due_from(dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 12) {
											try {
												Date dateFormat= CommonParser.parseDate(val,cell);
												qformfive.setInterest_due_from(dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 13) {
											try {
												BigDecimal deferred_principal = CommonParser.parseBigDecimal(val);
												qformfive.setDeferred_principal(deferred_principal);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 14) {
											try {
												BigDecimal deferred_interest = CommonParser.parseBigDecimal(val);
												qformfive.setDeferred_interest(deferred_interest);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 15) {
											qformfive.setRolled_over(val);
										}
										else if (i == 16) {
											try {
												BigDecimal has_there_been_prin_waiver = CommonParser.parseBigDecimal(val);
												qformfive.setHas_there_been_prin_waiver(has_there_been_prin_waiver);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 17) {
											qformfive.setBoard_approval_reference(val);
										}
										else if (i == 18) {
											qformfive.setClassification(val);
										}
										else if (i == 19) {
											try {
												//BigDecimal provision_prcnt = CommonParser.parseBigDecimal(val);
												qformfive.setProvision_prcnt(val);
											} catch (Exception e) {
												 _log.error(e);
											}
										}
										else if(i == 20) {
											try {
												BigDecimal provision_amt = CommonParser.parseBigDecimal(val);
												qformfive.setProvision_amount(provision_amt);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							qformfive.setCreatedon(new Date());
							
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
								JSONObject formfiveJsonObject = JSONFactoryUtil.createJSONObject(qformfive.toString());
								qformfiveJsonArray.put(formfiveJsonObject);
								qformfiveList.add(qformfive);
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
