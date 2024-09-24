package CRA_Quaterly_data.Util;

import com.daily.pfm.service.model.QrCauseWisePatternAnalysis;
import com.daily.pfm.service.service.QrCauseWisePatternAnalysisLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
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

public class QrCauseWisePatternAnalysis_3 {
	
private static Log _log = LogFactoryUtil.getLog(QrCauseWisePatternAnalysis_3.class);
	
	public static JSONObject saveQrCauseWisePatternAnalysis(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray qrCauseWisePatternAnalysisArray, List<QrCauseWisePatternAnalysis> qrCauseWisePatternAnalysisList,DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveQrCauseWisePatternAnalysis_3");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Cause-wise Pattern Analysis");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					QrCauseWisePatternAnalysis qrcwpa =QrCauseWisePatternAnalysisLocalServiceUtil.
							createQrCauseWisePatternAnalysis(CounterLocalServiceUtil.increment(QrCauseWisePatternAnalysis.class.getName()));
					
					qrcwpa.setCreatedby(userId);
					qrcwpa.setReportUploadLogId(reportUploadLogId);
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
										qrcwpa.setCra(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									qrcwpa.setYear(val);
								}
								else if (i == 2) {
									qrcwpa.setQuarter(val);
								} 
								else if (i == 3) {
									qrcwpa.setParticulars(val);
								} 
								else if (i == 4) {
									try {
										qrcwpa.setPresent_quarter_death(CommonParser.parseBigDecimal(val));
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								
								else if (i == 5) {
									try {
										qrcwpa.setPresent_quarter_premature(CommonParser.parseBigDecimal(val));
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								
								else if (i == 6) {
								     try {
										qrcwpa.setPresent_quarter_superannuation(CommonParser.parseBigDecimal(val));
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing integer"+val);
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
					qrcwpa.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(qrcwpa.toString());
							qrCauseWisePatternAnalysisArray.put(jsonObject);
							qrCauseWisePatternAnalysisList.add(qrcwpa);
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
