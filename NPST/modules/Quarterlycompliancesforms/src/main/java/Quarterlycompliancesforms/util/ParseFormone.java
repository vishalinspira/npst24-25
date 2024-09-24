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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.Quaeterlyfromoneb;
import compliance.service.service.QuaeterlyfromonebLocalServiceUtil;
import nps.common.service.util.CommonParser;


public class ParseFormone {
	
	private static Log _log = LogFactoryUtil.getLog(ParseFormone.class);
	
	public static JSONObject saveSheetFormone(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qformoneJsonArray, List<Quaeterlyfromoneb> qformoneList,long reportUploadLogId) {
		_log.info("saveSheetFormone::::::::::::::::::::::");
		QuaeterlyfromonebLocalServiceUtil.deleteQuaeterlyfromonebByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 1B";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
				        Quaeterlyfromoneb qformoneb=QuaeterlyfromonebLocalServiceUtil.createQuaeterlyfromoneb(CounterLocalServiceUtil.increment(Quaeterlyfromoneb.class.getName()));
				       
				        qformoneb.setReportUploadLogId(reportUploadLogId);
				        qformoneb.setCreatedby(userId);
						
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
											try {
												Date dateFormat= CommonParser.parseDate(val,cell);
												qformoneb.setReporting_date(dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										 else {
												errorObj.put("cellno", 2);
												errorObj.put("msg", "It is not a number");
												isError = true;
											}
									}
									else if (i == 1) {
										qformoneb.setPension_fund_name(val);
									}
									else if (i == 2) {
										qformoneb.setScheme_name(val);
									} 
									else if (i == 3) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											qformoneb.setSno(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 4) {
										qformoneb.setType_purchase_or_sale(val);
									} 
									else if (i == 5) {
										qformoneb.setCategory_of_investment(val);
									}
									else if (i == 6) {
										qformoneb.setIsin(val);
									} 
									else if (i == 7) {
										qformoneb.setName_of_security_or_company(val);
									}
									else if (i == 8) {
										try {
											BigDecimal book_value = CommonParser.parseBigDecimal(val);
											qformoneb.setBook_value_qtr(book_value);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 9) {
										try {
											qformoneb.setPrcnt_of_portfolio_value(val);
										} catch (Exception e) {
											 _log.error(e);
										}
									}
									else if (i == 10) {
										try {
											Date dateFormat= CommonParser.parseDate(val,cell);
											qformoneb.setDate_of_purchase_or_sale_qtr(dateFormat);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									
								}
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						qformoneb.setCreatedon(new Date());
						
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
							JSONObject formoneJsonObject = JSONFactoryUtil.createJSONObject(qformoneb.toString());
							qformoneJsonArray.put(formoneJsonObject);
							qformoneList.add(qformoneb);
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
