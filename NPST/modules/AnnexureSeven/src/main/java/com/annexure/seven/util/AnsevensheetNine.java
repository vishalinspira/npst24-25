package com.annexure.seven.util;

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

import compliance.service.model.CollectionsTID;
import compliance.service.service.CollectionsTIDLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheetNine {
	private static Log _log = LogFactoryUtil.getLog(AnsevensheetNine.class);
	
	public static JSONObject saveSheetNine(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray collectionsTIDJsonArray, List<CollectionsTID> collectionsTIDList, String cra, JSONObject resultJsonObject, long reportUploadLogId) {
		
		CollectionsTIDLocalServiceUtil.deleteCollectionsTIDByReportUploadLogId(reportUploadLogId);
		String sheetName = "Collections_TID_ToBe_Enabled";
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
					
					CollectionsTID collectionsTID = CollectionsTIDLocalServiceUtil.
							createCollectionsTID(CounterLocalServiceUtil.increment(CollectionsTID.class.getName()));
							
					collectionsTID.setReportUploadLogId(reportUploadLogId);
					collectionsTID.setCreatedby(userId);
					collectionsTID.setCra(cra);
					
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
											collectionsTID.setTransaction_no(val);
										}
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											
											isError = true;
										}
								}
								else if (i == 1) {
									//log.info("cell " + cell.getCellType());
									try {
										int iValue = Integer.parseInt(val);
										collectionsTID.setSol_id(iValue);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									collectionsTID.setBranch_name(val);
								} 
								else if (i == 3) {
									collectionsTID.setName_(val);
								} 
								else if (i == 4) {
									try {
										int iValue = Integer.parseInt(val);
										collectionsTID.setPao_ain(iValue);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									//log.info("cell " + cell.getCellType());
									try {
										long tranId = Long.parseLong(val);
										collectionsTID.setTran_id(tranId);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									//log.info("cell " + cell.getCellType());
									try {
										BigDecimal total_amt =  CommonParser.parseBigDecimal(val);
										collectionsTID.setTotal_amt(total_amt);
									} catch (Exception e) {
										_log.info("error parsing bigdec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									//log.info("cell " + cell.getCellType());
									try {
										int iValue = Integer.parseInt(val);
										collectionsTID.setCheque_no(iValue);
									} catch (Exception e) {
										_log.error(e);
									}
								}
								else if (i == 8) {
									_log.info("cell " + cell.getCellType());
									try {
										Date date=cell.getDateCellValue();
										collectionsTID.setReceipt_date(date);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									_log.info("cell " + cell.getCellType());
									try {
										Date date=cell.getDateCellValue();
										collectionsTID.setClearance_date(date);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 10) {
									try {
										long creditNo = Long.parseLong(val);
										collectionsTID.setCredit_acc_no(creditNo);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									collectionsTID.setEmail_id(val);
								} 
								
							} 
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					collectionsTID.setCreatedon(new Date());
						
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
							JSONObject chequesFailedJsonObject = JSONFactoryUtil.createJSONObject(collectionsTID.toString());
							collectionsTIDJsonArray.put(chequesFailedJsonObject);
							collectionsTIDList.add(collectionsTID);
						}
						rowCount++;
					}
				}
				
			}catch (Exception e) {
				resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				 return resultJsonObject;
			}
		   return resultJsonObject;
	}
}
