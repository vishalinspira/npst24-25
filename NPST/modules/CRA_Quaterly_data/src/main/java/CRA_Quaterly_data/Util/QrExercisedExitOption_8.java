package CRA_Quaterly_data.Util;

import com.daily.pfm.service.model.QrExercisedExitOption;
import com.daily.pfm.service.service.QrExercisedExitOptionLocalServiceUtil;
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

public class QrExercisedExitOption_8 {
	
private static Log _log = LogFactoryUtil.getLog(QrExercisedExitOption_8.class);
	
	public static JSONObject saveQrExercisedExitOption(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray qrExercisedExitOptionArray, List<QrExercisedExitOption> qrExercisedExitOptionList,DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside SaveQrExercisedExitOption_8");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Exercised Exit Option");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					QrExercisedExitOption qeeo = QrExercisedExitOptionLocalServiceUtil.
							createQrExercisedExitOption(CounterLocalServiceUtil.increment(QrExercisedExitOption.class.getName()));
					
					qeeo.setCreatedby(userId);
					qeeo.setReportUploadLogId(reportUploadLogId);
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
										qeeo.setCra(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									qeeo.setYear(val);
								}
								else if (i == 2) {
									qeeo.setQuarter(val);
								} 
								else if (i == 3) {
									qeeo.setSector(val);
								} 
								else if (i == 4) {
									try {
										BigDecimal dcg = CommonParser.parseBigDecimal(val);
										qeeo.setPartial_withdrawal_req_prcd(dcg);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								
								else if (i == 5) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											qeeo.setGsec_subs_exercised_family_pn(null);
										}else {
											BigDecimal cg = CommonParser.parseBigDecimal(val);
											qeeo.setGsec_subs_exercised_family_pn(cg);
										}
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								
								else if (i == 6) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											qeeo.setSubscriber_deferred_lumpsum(null);
										}else {
											BigDecimal cg = CommonParser.parseBigDecimal(val);
											qeeo.setSubscriber_deferred_lumpsum(cg);
										}
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								
								else if (i == 7) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											qeeo.setSubscriber_who_deferred_anuty(null);
										}else {
											BigDecimal cg = CommonParser.parseBigDecimal(val);
											qeeo.setSubscriber_who_deferred_anuty(cg);
										}
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								
								else if (i == 8) {
									try {
										if(val.equalsIgnoreCase("- 0") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											qeeo.setVoluntarily_continued_sub(null);
										}else {
											BigDecimal cg = CommonParser.parseBigDecimal(val);
											qeeo.setVoluntarily_continued_sub(cg);
										}
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								
								else if (i == 9) {
									try {
										if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
											qeeo.setAuto_continued_subscriber(null);
										}else {
											BigDecimal cg = CommonParser.parseBigDecimal(val);
											qeeo.setAuto_continued_subscriber(cg);
										}
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
					qeeo.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(qeeo.toString());
							qrExercisedExitOptionArray.put(jsonObject);
							qrExercisedExitOptionList.add(qeeo);
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
