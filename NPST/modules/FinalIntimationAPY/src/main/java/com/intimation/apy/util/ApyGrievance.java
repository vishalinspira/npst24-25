package com.intimation.apy.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.math.BigDecimal;
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

import compliance.service.model.MnAPYGrievance;
import compliance.service.service.MnAPYGrievanceLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class ApyGrievance {
	
	private static Log _log = LogFactoryUtil.getLog(ApyGrievance.class);
	
	public static JSONObject saveApyGrievance(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, 
			JSONArray apyGrievanceJsonArray, List<MnAPYGrievance> apyGrievanceList, DecimalFormat decimalFormat, String cra, long reportUploadLogId) {
		_log.info("Inside saveApyGrievance");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("APY_Grievance");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					MnAPYGrievance apyGrievance = MnAPYGrievanceLocalServiceUtil.
							createMnAPYGrievance(CounterLocalServiceUtil.increment(MnAPYGrievance.class.getName()));
					
					apyGrievance.setCreatedby(userId);
					
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
								_log.info("Val: " + val);
								
								boolean isValid = (val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("- 0"));
								
								if (i == 0) {
									if (val != null) {
										try {
											int iValue = Integer.parseInt(cell.getRawValue());
											apyGrievance.setSr_no(iValue);
										} catch (Exception e) {
											 _log.info("error parsing integer"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
										}
									}
//									 else {
//										errorObj.put("cellno", 2);
//										errorObj.put("msg", "It is not a number");
//										isError = true;
//									}
								}
								else if (i == 1) {
									try {
										
										apyGrievance.setApy_sp_reg_no(val);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 2) {
									apyGrievance.setApy_sp_name(val);
								} 
								else if (i == 3) {
									try {
										
										apyGrievance.setToken_number(val);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 4) {
									try {
										Date date= cell.getDateCellValue();
										apyGrievance.setGrievance_logging_date(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//apyGrievance.setGrievance_logging_date(cell.getDateCellValue());
									}
								} 
								else if (i == 5) {
									//_log.info("cell2" + cell.getCellType());
									//String s = String.valueOf(cell.getNumericCellValue());
									try {
										long pran = CommonParser.parseLong(val);
										apyGrievance.setPran(pran);
									} catch (Exception e) {
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 6) {
									apyGrievance.setSubscriber_name(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					apyGrievance.setCreatedon(new Date());
					apyGrievance.setCra(cra);
					apyGrievance.setReportUploadLogId(reportUploadLogId);
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(apyGrievance.toString());
							//apyGrievanceJsonArray.put(jsonObject);
							apyGrievanceList.add(apyGrievance);
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
