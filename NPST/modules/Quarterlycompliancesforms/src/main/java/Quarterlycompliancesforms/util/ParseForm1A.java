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

import compliance.service.model.QuarterlyForm1A;
import compliance.service.service.QuarterlyForm1ALocalServiceUtil;
import nps.common.service.util.CommonParser;

public class ParseForm1A {
	
	private static Log _log = LogFactoryUtil.getLog(ParseForm1A.class);
	
	public static JSONObject saveSheetForm1A(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qrForm1AJsonArray, List<QuarterlyForm1A> qrForm1AList,long reportUploadLogId) {
		_log.info("saveSheetForm1A::::::::::::::::::::::");
		QuarterlyForm1ALocalServiceUtil.deleteQuarterlyForm1AByReportUploadLogId(reportUploadLogId);
		String sheetName="FORM 1B";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			if (file != null) {
				_log.info("saveSheetForm1A -- file != null::::::::::::::::::::::");
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					_log.info("saveSheetForm1A -- while::::::::::::::::::::::");
				QuarterlyForm1A qrForm1A = QuarterlyForm1ALocalServiceUtil.createQuarterlyForm1A(CounterLocalServiceUtil.increment(QuarterlyForm1A.class.getName()));
				       
					qrForm1A.setReportUploadLogId(reportUploadLogId);
					qrForm1A.setCreatedby(userId);
						
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
							_log.info("val :::: "+val);
							if (cell != null) {
								if (val != null)
									val = val.trim();
								if(rowCount > 1) {
									if (i == 0) {
											try {
												
												Date dateFormat= CommonParser.parseDate(val,cell);
												_log.info("dateFormat"+dateFormat);
												qrForm1A.setReporting_date(dateFormat);
												_log.info("dateFormat"+dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
									}
									else if (i == 1) {
										qrForm1A.setPension_fund_name(val);
									}
									else if (i == 2) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											qrForm1A.setSno(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 3) {
										qrForm1A.setType_purchase_or_sale(val);
									} 
									else if (i == 4) {
										qrForm1A.setCategory_of_investment(val);
									} 
									else if (i == 5) {
										qrForm1A.setIsin(val);
									}
									else if (i == 6) {
										qrForm1A.setName_of_security_or_company(val);
									} 
									else if (i == 7) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											qrForm1A.setBook_value_qtr(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 8) {
										try {
											qrForm1A.setPrcnt_of_portfolio_value_qtr(val);
										} catch (Exception e) {
											 _log.error(e);
										}
									} 
									else if (i == 9) {
										try {
											Date dateFormat= CommonParser.parseDate(val,cell);
											qrForm1A.setDate_of_purchase_or_sale_qtr(dateFormat);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 10) {
										qrForm1A.setName_(val);
									} 
									else if (i == 11) {
										qrForm1A.setDesignation(val);
									} 
									
								}
					
							
							
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						qrForm1A.setCreatedon(new Date());
						
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
							JSONObject formoneJsonObject = JSONFactoryUtil.createJSONObject(qrForm1A.toString());
							qrForm1AJsonArray.put(formoneJsonObject);
							qrForm1AList.add(qrForm1A);
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
