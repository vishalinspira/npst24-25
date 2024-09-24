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

import compliance.service.model.ChequeReject;
import compliance.service.service.ChequeRejectLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheeSixteen {
	
private static Log _log = LogFactoryUtil.getLog(AnsevensheeSixteen.class);	
	
public static JSONObject saveSheetSixteen(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray chequeRejectJsonArray, List<ChequeReject> chequeRejectList, String cra, JSONObject resultJsonObject, long reportUploadLogId) {
	
	ChequeRejectLocalServiceUtil.deleteChequeRejectByReportUploadLogId(reportUploadLogId);
	String sheetName = "Chq_Rejected";
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
				
				ChequeReject chequeReject = ChequeRejectLocalServiceUtil.
						createChequeReject(CounterLocalServiceUtil.increment(ChequeReject.class.getName()));
						
				chequeReject.setReportUploadLogId(reportUploadLogId);
				chequeReject.setCreatedby(userId);
				chequeReject.setCra(cra);
				
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
									//_log.info("cell " + cell.getCellType());
									
									if(val.equalsIgnoreCase("nil") || val.equalsIgnoreCase("na")) {
										break rowloop;
									}
									else {
										try {
											Date date= cell.getDateCellValue();
											chequeReject.setReceipt_date(date);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
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
								
								if(val.equalsIgnoreCase("nil") || val.equalsIgnoreCase("na")) {
										/*
										 * DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
										 * chequeReject.setRejection_date(cell.getDateCellValue());
										 */
								}
								else {
									try {
										Date date= cell.getDateCellValue();
										chequeReject.setRejection_date(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
							}
							else if (i == 2) {
								//_log.info("cell " + cell.getCellType());
								try {
									int iValue = Integer.parseInt(val);
									chequeReject.setSol_id(iValue);
								} catch (Exception e) {
									_log.info("error parsing int"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 3) {
								chequeReject.setName_(val);
							} 
							else if (i == 4) {
								try {
									int iValue = Integer.parseInt(val);
									chequeReject.setPao_ain(iValue);
								} catch (Exception e) {
									_log.info("error parsing int"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 5) {
								try {
									long lValue = Long.parseLong(val);
									chequeReject.setTran_id(lValue);
								} catch (Exception e) {
									_log.info("error parsing long"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 6) {
								//_log.info("cell " + cell.getCellType());
								try {
									long chqNo = Long.parseLong(val);
									chequeReject.setChq_no(chqNo);
								} catch (Exception e) {
									_log.info("error parsing long"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 7) {
								//_log.info("cell " + cell.getCellType());
								try {
									BigDecimal total_amt = CommonParser.parseBigDecimal(val);
									chequeReject.setTotal_amt(total_amt);
								} catch (Exception e) {
									_log.info("error parsing bigdec"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 8) {
								chequeReject.setDrawn_on_bank(val);
							} 
							else if (i == 9) {
								chequeReject.setDrawn_on_branch(val);
							} 
							else if (i == 10) {
								chequeReject.setReturn_desc(val);
							} 
							
						}
					} else if(i == 0 && rowCount > 1) {
						break rowloop;
					}
				}
				chequeReject.setCreatedon(new Date());
					
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
						JSONObject chequeRejectJsonObject = JSONFactoryUtil.createJSONObject(chequeReject.toString());
						chequeRejectJsonArray.put(chequeRejectJsonObject);
						chequeRejectList.add(chequeReject);
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
