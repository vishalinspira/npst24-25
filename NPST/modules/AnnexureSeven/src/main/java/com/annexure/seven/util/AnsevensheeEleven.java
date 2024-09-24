package com.annexure.seven.util;

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

import compliance.service.model.PAOMistakes;
import compliance.service.service.PAOMistakesLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheeEleven {
	
private static Log _log = LogFactoryUtil.getLog(AnsevensheeEleven.class);
	
public static JSONObject saveSheetEleven(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray paoMistakesJsonArray, List<PAOMistakes> paoMistakesList, String cra, JSONObject resultJsonObject, long reportUploadLogId) {
	_log.info("Inside saveSheetEleven");
	
	PAOMistakesLocalServiceUtil.deletePAOMistakesByReportUploadLogId(reportUploadLogId);
	String  sheetName="PAO_Repeated_mistakes";
	try {
		if (file != null) {
			OPCPackage pkg = OPCPackage.open(file);
			workbook = new XSSFWorkbook(pkg);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			
			Iterator<Row> rows = sheet.rowIterator();
			int rowCount = 1;
			int errorRowCount = 2;
			
			rowloop:
			while (rows.hasNext()) {
				
				PAOMistakes paoMistakes = PAOMistakesLocalServiceUtil.
						createPAOMistakes(CounterLocalServiceUtil.increment(PAOMistakes.class.getName()));
						
				paoMistakes.setReportUploadLogId(reportUploadLogId);
				paoMistakes.setCreatedby(userId);
				paoMistakes.setCra(cra);
				
				JSONObject errorObj = JSONFactoryUtil.createJSONObject();
				errorObj.put("rownum", rowCount);
				boolean isError = false;
				XSSFRow row = (XSSFRow) rows.next();
				XSSFRow errorRow = null;
				
				int lastColumn = Math.max(row.getLastCellNum(), 0);
				
				for (int i = 0; i < lastColumn; i++) {
					XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					if (cell != null) {

						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						if (val != null)
							val = val.trim();
						if(rowCount > 1) {
							_log.info("Val: " + val);
							if (i == 0) {
								if (val != null && Validator.isNotNull(val) && val.length() > 0) {
									//Long srno=Long.parseLong(val);
									if(val.equalsIgnoreCase("nil") || val.equalsIgnoreCase("na")) {
										break rowloop;
									}
									else {
										try {
											int iValue = Integer.parseInt(val);
											paoMistakes.setRow_labels(iValue);
										} catch (Exception e) {
											_log.info("error parsing int"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
								}
								 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										
										isError = true;
									}
							}
							else if (i == 1) {
								//_log.info("cell " + cell.getCellType());
								try {
									int virtualAccNo = Integer.parseInt(val);
									paoMistakes.setCount_nps_virtual_account_no(virtualAccNo);
								} catch (Exception e) {
									_log.info("error parsing int"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 2) {
								paoMistakes.setPao_name(val);
							} 
							else if (i == 3) {
								paoMistakes.setEmail_id(val);
							} 
							else if (i == 4) {
								paoMistakes.setAddress_line1(val);
							} 
							else if (i == 5) {
								paoMistakes.setAddress_line2(val);
							}
							else if (i == 6) {
								//log.info("cell " + cell.getCellType());
								try {
									int iValue = Integer.parseInt(val);
									paoMistakes.setPin(iValue);
								} catch (Exception e) {
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
				paoMistakes.setCreatedon(new Date());
					
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
						JSONObject paoMistakesJsonObject = JSONFactoryUtil.createJSONObject(paoMistakes.toString());
						paoMistakesJsonArray.put(paoMistakesJsonObject);
						paoMistakesList.add(paoMistakes);
					}
					rowCount++;
				}
			}
			
		}catch (Exception e) {
			_log.error(e);
			resultJsonObject.put("status", false);
			 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
			 return resultJsonObject;
		}
		return resultJsonObject;
	}
}