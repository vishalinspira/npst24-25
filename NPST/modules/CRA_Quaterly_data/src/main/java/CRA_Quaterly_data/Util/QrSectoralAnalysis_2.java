package CRA_Quaterly_data.Util;

import com.daily.pfm.service.model.QrSectoralAnalysis;
import com.daily.pfm.service.service.QrSectoralAnalysisLocalServiceUtil;
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

public class QrSectoralAnalysis_2 {
	
private static Log _log = LogFactoryUtil.getLog(QrSectoralAnalysis_2.class);
	
	public static JSONObject saveQrSectoralAnalysis(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray qrSectoralAnalysisArray, List<QrSectoralAnalysis> qrSectoralAnalysisList,DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveQrExitAndWithdrawal_2");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Sectoral Analysis");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					QrSectoralAnalysis qrsa =QrSectoralAnalysisLocalServiceUtil.
							createQrSectoralAnalysis(CounterLocalServiceUtil.increment(QrSectoralAnalysis.class.getName()));
					
					qrsa.setCreatedby(userId);
					qrsa.setReportUploadLogId(reportUploadLogId);
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
										qrsa.setYear(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									qrsa.setQuarter(val);
									
								}
								else if (i == 2) {
									qrsa.setCra(val);
								} 
								else if (i == 3) {
									qrsa.setSectoral_analysis(val);
								} 
								else if (i == 4) {
									try {
										BigDecimal cg = CommonParser.parseBigDecimal(val);
										qrsa.setCentral_government(cg);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								
								else if (i == 5) {
									try {
										BigDecimal cg = CommonParser.parseBigDecimal(val);
										qrsa.setState_government(cg);
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
										BigDecimal cg = CommonParser.parseBigDecimal(val);
										qrsa.setCorporate(cg);
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
										BigDecimal cg = CommonParser.parseBigDecimal(val);
										qrsa.setUos(cg);
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
										BigDecimal cg = CommonParser.parseBigDecimal(val);
										qrsa.setNps_lite(cg);
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
					qrsa.setCreatedon(new Date());
						
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
						//	JSONObject jsonObject = JSONFactoryUtil.createJSONObject(qrsa.toString());
							//qrSectoralAnalysisArray.put(jsonObject);
							qrSectoralAnalysisList.add(qrsa);
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
