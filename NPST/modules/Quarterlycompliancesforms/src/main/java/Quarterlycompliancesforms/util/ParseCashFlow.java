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
import compliance.service.model.QuaterlyformCashFlow;
import compliance.service.service.QuarterlyForm1ALocalServiceUtil;
import compliance.service.service.QuaterlyformCashFlowLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class ParseCashFlow {
	
	private static Log _log = LogFactoryUtil.getLog(ParseCashFlow.class);
	
	public static JSONObject saveCashFlow(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qrForm1AJsonArray, List<QuaterlyformCashFlow> qrForm1AList,long reportUploadLogId) {
		_log.info("saveCashFlow  ::::::::::::::::::::::");
		QuarterlyForm1ALocalServiceUtil.deleteQuarterlyForm1AByReportUploadLogId(reportUploadLogId);
		String sheetName="Cashflow";
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
				while (rows.hasNext())
					for (int g=1; g<sheet.getPhysicalNumberOfRows() -1; g++){
					QuaterlyformCashFlow cashFlow = QuaterlyformCashFlowLocalServiceUtil.createQuaterlyformCashFlow(CounterLocalServiceUtil.increment(QuaterlyformCashFlow.class.getName()));
				       
					cashFlow.setReportUploadLogId(reportUploadLogId);
					cashFlow.setCreatedby(userId);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						 XSSFRow row = sheet.getRow(g);
					//	XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						int lastColumn = Math.max(row.getLastCellNum(), 0);
						
						for (int i = 0; i < lastColumn; i++) {
							XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							if (cell != null) {
								if (val != null)
									val = val.trim();
								_log.info("val:::"+val);
								if(rowCount > 1) {
									if (i == 0) {
										cashFlow.setPension_fund_name(val);
									}
									else if (i == 1) {
										cashFlow.setScheme_name(val);
									}
									else if (i == 2) {
										cashFlow.setPeriodicity_of_submission(val);
									} 
									else if (i == 3) {
										try {
											Date dateFormat= CommonParser.parseDate(val,cell);
											cashFlow.setStatement_as_on(dateFormat);
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
											cashFlow.setOpening_balance_mv(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 5) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setInflow_during_the_qtr(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 6) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setIncrease_decrease_value_of_inv(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 7) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setOutflow_during_the_qtr(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 8) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											cashFlow.setTotal_investible_funds_mv(bg.stripTrailingZeros());
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
							JSONObject formoneJsonObject = JSONFactoryUtil.createJSONObject(cashFlow.toString());
							qrForm1AJsonArray.put(formoneJsonObject);
							qrForm1AList.add(cashFlow);
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
