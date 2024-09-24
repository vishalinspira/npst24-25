package com.monthly.mis.report.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

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

import compliance.service.model.MnNpsCategoryWiseAgeing;
import compliance.service.service.MnNpsCategoryWiseAgeingLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class NpsCategoryWiseAgeing {
	
	private static Log _log = LogFactoryUtil.getLog(NpsCategoryWiseAgeing.class);
	
	public static JSONObject saveNpsCategoryWiseAgeing(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, 
			DecimalFormat decimalFormat, JSONArray npsCategoryWiseAgeingJsonArray, List<MnNpsCategoryWiseAgeing> npsCategoryWiseAgeingList, String cra, long reportUploadLogId) {
		
		_log.info("Inside saveNpsCategoryWiseAgeing");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("NPS Category wise ageing");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					MnNpsCategoryWiseAgeing npsCategoryWiseAgeing = MnNpsCategoryWiseAgeingLocalServiceUtil.
							createMnNpsCategoryWiseAgeing(CounterLocalServiceUtil.increment(MnNpsCategoryWiseAgeing.class.getName()));
					npsCategoryWiseAgeing.setCra(cra);		
					npsCategoryWiseAgeing.setCreatedby(userId);
					
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
								_log.debug("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										//_log.info("cell " + cell.getCellType());
										npsCategoryWiseAgeing.setCategory(val);
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											isError = true;
										}
								}
								else if (i == 1) {
									
									try {
										Date monthDate = cell.getDateCellValue();
										//npsCategoryWiseAgeing.setMonth_(val);
										npsCategoryWiseAgeing.setMonth_(monthDate);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									npsCategoryWiseAgeing.setScheme_name(val);
								} 
								else if (i == 3) {
									try {
										int iValue = Integer.parseInt(val);
										npsCategoryWiseAgeing.set_0_to_30_days(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										 
									}
								} 
								else if (i == 4) {
									try {
										int iValue = Integer.parseInt(val);
										npsCategoryWiseAgeing.set_31_to_60_days(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 5) {
									try {
										int iValue = Integer.parseInt(val);
										npsCategoryWiseAgeing.set_61_to_90_days(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 6) {
									try {
										int iValue = Integer.parseInt(val);
										npsCategoryWiseAgeing.set_91_to_365_days(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 7) {
									try {
										int iValue = Integer.parseInt(val);
										npsCategoryWiseAgeing.setMore_than_365_days(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 8) {
									try {
										int iValue = Integer.parseInt(val);
										npsCategoryWiseAgeing.setOutstanding_at_end_of_month(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
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
					npsCategoryWiseAgeing.setCreatedon(new Date());
					npsCategoryWiseAgeing.setReportUploadLogId(reportUploadLogId);	
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(npsCategoryWiseAgeing.toString());
							//npsCategoryWiseAgeingJsonArray.put(jsonObject);
							npsCategoryWiseAgeingList.add(npsCategoryWiseAgeing);
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
