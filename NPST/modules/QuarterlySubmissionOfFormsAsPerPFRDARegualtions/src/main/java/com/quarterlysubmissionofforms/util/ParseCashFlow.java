package com.quarterlysubmissionofforms.util;

import com.daily.pfm.service.model.CashFlow;
import com.daily.pfm.service.service.CashFlowLocalServiceUtil;
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

import nps.common.service.util.CommonParser;

public class ParseCashFlow {

private static Log _log = LogFactoryUtil.getLog(ParseCashFlow.class);
	
	public static JSONObject saveSheetCashFlow (File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx,
			boolean isexcelhaserror, JSONArray cashFlowJsonArray, List<CashFlow> cashFlowList,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("saveSheetCashFlow::::::::::::::::::::::");
		CashFlowLocalServiceUtil.deleteCashFlowByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Cashflow");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					_log.info("Inside while loop in Cashflow sheet");
					CashFlow cashFlow = CashFlowLocalServiceUtil.createCashFlow(CounterLocalServiceUtil.increment(CashFlow.class.getName()));
					cashFlow.setReportUploadLogId(reportUploadLogId);
					cashFlow.setCreatedby(userId);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						int lastColumn = Math.max(row.getLastCellNum(), 0);
						
						for (int i = 0; i < 10; i++) {
							XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info("val::::::::::::::::::::::"+val);
							if (cell != null) {
								if (val != null)
									val = val.trim();
								if(rowCount > 1) {	
									if (i == 0) {
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												cashFlow.setPension_fund_manager_name_code(val);
										}else {
												errorObj.put("cellno", 2);
												errorObj.put("msg", "It is not a number");
												isError = true;
										}
									}
									else if (i == 1) {
										cashFlow.setScheme_name(val);
									}
									else if (i == 2) {
										cashFlow.setPeriodicity_of_submission(val);
									} 
									else if (i == 3) {
										try {
											cashFlow.setStatement_as_on(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 4) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setOpening_balance_market_value(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 5) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setInflow_during_the_quarter(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 6) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setInc_dec_val_of_inv_net(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 7) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setOutflow_during_the_quarter(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
											
									}
									else if (i == 8) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setTotal_investible_fund_mkt_val(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing big dec"+val);
											 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									
								}
							}else if(i==0 && rowCount >= 1) {
								break rowLoop;
							}
						}
						cashFlow.setCreatedon(new Date());
						
						
						
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
							cashFlowList.add(cashFlow);
						}
						rowCount++;
					}
				_log.info("isexcelhaserror ::::::::::"+isexcelhaserror);
				_log.info("rowCount ::::::::::: ******************************** "+rowCount);
				}
				
		}catch (Exception e) {
			_log.error(e);
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg",  CommonParser.fileExceptionMsg);
			return resultJsonObject;
		}
		return resultJsonObject;
	}
}
