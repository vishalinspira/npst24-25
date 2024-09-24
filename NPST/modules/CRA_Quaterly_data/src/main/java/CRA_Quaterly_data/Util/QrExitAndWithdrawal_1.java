package CRA_Quaterly_data.Util;

import com.daily.pfm.service.model.QrExitAndWithdrawal;
import com.daily.pfm.service.service.QrExitAndWithdrawalLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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

import nps.common.service.util.CommonParser;

public class QrExitAndWithdrawal_1 {
	
	private static Log _log = LogFactoryUtil.getLog(QrExitAndWithdrawal_1.class);
	
	public static JSONObject saveQrExitAndWithdrawal(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray qrExitAndWithdrawalArray, List<QrExitAndWithdrawal> qrExitAndWithdrawalList,DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveQrExitAndWithdrawal_1");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Exit and Withdrawal");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					QrExitAndWithdrawal qreaw =QrExitAndWithdrawalLocalServiceUtil.
							createQrExitAndWithdrawal(CounterLocalServiceUtil.increment(QrExitAndWithdrawal.class.getName()));
					
					qreaw.setCreatedby(userId);
					qreaw.setReportUploadLogId(reportUploadLogId);
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
						if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								//_log.info("Val: " + val);
								if (i == 0) {
									if (val != null) {
										//int iValue = (int) cell.getNumericCellValue();
										qreaw.setFiscal_year(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									qreaw.setCra(val);
								}
								else if (i == 2) {
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									qreaw.setExits_and_withdrawals_during(val);
								} 
								else if (i == 3) {
									qreaw.setQuarter(val);
								} 
								else if (i == 4) {
									try {
										BigDecimal iValue =  CommonParser.parseBigDecimal(val);
										qreaw.setValues(iValue);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					qreaw.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(qreaw.toString());
							qrExitAndWithdrawalArray.put(jsonObject);
							qrExitAndWithdrawalList.add(qreaw);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				
				 _log.error(e);
				 resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
				 return resultJsonObject;
			}
			return resultJsonObject;

  }
}
