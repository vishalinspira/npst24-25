package com.annexure.seven.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.InvalidAcc;
import compliance.service.model.InvalidAccII;
import compliance.service.service.InvalidAccIILocalServiceUtil;
import compliance.service.service.InvalidAccLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheeTwelve {
	
private static Log _log = LogFactoryUtil.getLog(AnsevensheeTwelve.class);
	
public static JSONObject saveSheetTwelve(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray invalidAccountJsonArray, List<InvalidAcc> invalidAccountList, String cra,JSONObject resultJsonObject, long reportUploadLogId) {
//	try {
//		if (file != null) {
//			OPCPackage pkg = OPCPackage.open(file);
//			workbook = new XSSFWorkbook(pkg);
//			XSSFSheet sheet = workbook.getSheetAt(15); 
//			Iterator<Row> rows = sheet.rowIterator();
//			int rowCount = 1;
//			int errorRowCount = 2;
//			
//			rowloop:
//			while (rows.hasNext()) {
//				
//				InvalidAcc invalidAcc = InvalidAccLocalServiceUtil.
//						createInvalidAcc(CounterLocalServiceUtil.increment(InvalidAcc.class.getName()));
//						
//				invalidAcc.setCreatedby(userId);
//				invalidAcc.setCra(cra);
//				
//				JSONObject errorObj = JSONFactoryUtil.createJSONObject();
//				errorObj.put("rownum", rowCount);
//				boolean isError = false;
//				XSSFRow row = (XSSFRow) rows.next();
//				XSSFRow errorRow = null;
//				
//				int lastColumn = Math.max(row.getLastCellNum(), 0);
//				
//				for (int i = 0; i < 20; i++) {
//					XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//					
//					if (cell != null) {
//
//						DataFormatter formatter = new DataFormatter();
//						
//						String val = formatter.formatCellValue(cell);
//						if (val != null)
//							val = val.trim();
//						if(rowCount > 1) {
//							//log.info("Val: " + val);
//							if (i == 0) {
//								if (val != null && Validator.isNotNull(val) && val.length() > 0) {
//									//_log.info("cell " + cell.getCellType());
//									long srno = Long.parseLong(val);
//									invalidAcc.setSr_no(srno);
//								}
//								 else {
//										errorObj.put("cellno", 2);
//										errorObj.put("msg", "It is not a number");
//										
//										isError = true;
//									}
//							}
//							else if (i == 1) {
//								invalidAcc.setPayment_id(val);
//							}
//							else if (i == 2) {
//								invalidAcc.setRet_ref_no(val);
//							} 
//							else if (i == 3) {
//								invalidAcc.setSource_acc_no_nodal(val);
//							} 
//							else if (i == 4) {
//								invalidAcc.setIfsc_source_no(val);
//							} 
//							else if (i == 5) {
//								DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
//								invalidAcc.setPayment_receipt_date(cell.getDateCellValue());
//							}
//							else if (i == 6) {
//								invalidAcc.setPeriod_(val);
//							}
//							else if (i == 7) {
//								invalidAcc.setOriginal_utr(val);
//							}
//							else if (i == 8) {
//								invalidAcc.setMode_(val);
//							} 
//							else if (i == 9) {
//								BigDecimal utr_amount = (BigDecimal) decimalFormat.parse(val);
//								invalidAcc.setUtr_amount(utr_amount);
//							} 
//							else if (i == 10) {
//								invalidAcc.setBeneficiary_acc_no(val);
//							} 
//							else if (i == 11) {
//								invalidAcc.setNps_virtual_acc_no(val);
//							} 
//							else if (i == 12) {
//								invalidAcc.setNps_acc_name(val);
//							} 
//							else if (i == 13) {
//								DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
//								invalidAcc.setReturn_date(cell.getDateCellValue());
//							} 
//							else if (i == 14) {
//								invalidAcc.setReturned_utr(val);
//							} 
//							else if (i == 15) {
//								invalidAcc.setTid(val);
//							} 
//							else if (i == 16) {
//								invalidAcc.setReason_return(val);
//							} 
//							else if (i == 17) {
//								invalidAcc.setAdditional_comments(val);
//							} 
//							else if (i == 18) {
//								invalidAcc.setPao_name(val);
//							} 
//							else if (i == 19) {
//								invalidAcc.setEmail_id(val);
//							} 
//							
//						}
//					} else if(i == 0 && rowCount > 1) {
//						break rowloop;
//					}
//				}
//				invalidAcc.setCreatedon(new Date());
//				
//				if (isError && rowCount > 1) {
//					errorArray.put(errorObj);
//					errorRow = xx.createRow(errorRowCount);
//					errorRowCount++;
//					XSSFCell rowCountCel = errorRow.createCell(1);
//					rowCountCel.setCellValue(rowCount);
//					XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
//					cellError.setCellValue(errorObj.getString("msg"));
//					isexcelhaserror = true;
//				} else if (rowCount > 1) {
//					JSONObject invalidAccountJsonObject = JSONFactoryUtil.createJSONObject(invalidAcc.toString());
//					invalidAccountJsonArray.put(invalidAccountJsonObject);
//					invalidAccountList.add(invalidAcc);
//				}
//				rowCount++;
//			}
//			
//		}
//	}catch (Exception e) {
//		
//	}
	
	InvalidAccLocalServiceUtil.deleteInvalidAccByReportUploadLogId(reportUploadLogId);
	
	try {
		if (file != null) {
			OPCPackage pkg = OPCPackage.open(file);
			workbook = new XSSFWorkbook(pkg);
			//XSSFSheet sheet = workbook.getSheetAt(17); //17,18
			//Iterator<Row> rows = sheet.rowIterator();
			Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
			int rowCount = 1;
			int errorRowCount = 2;
			int sheetCount = 0;
			
			while(sheetIterator.hasNext()) {
				XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
				_log.info(sheets.getSheetName());
				Iterator<Row> rows = sheets.iterator();
				
				String sheetName = sheets.getSheetName();
				boolean sheet1 = sheetName.equalsIgnoreCase("Invalid_Acc_No");
				boolean sheet2 = sheetName.equalsIgnoreCase("Amount_Mismatch");
				boolean sheet3 = sheetName.equalsIgnoreCase("Expired_Tran_ID");
				boolean sheet4 = sheetName.equalsIgnoreCase("No_TranID_inward_msg");
				
//				if(sheetCount == 13 || sheetCount == 14 || sheetCount == 15 || sheetCount == 16 && sheets != null) {	
				if(sheet1 || sheet2 || sheet3 || sheet4 && sheets != null) {
					rowloop:
						while (rows.hasNext()) {
							
							InvalidAcc invalidAcc = InvalidAccLocalServiceUtil.
									createInvalidAcc(CounterLocalServiceUtil.increment(InvalidAcc.class.getName()));
									
							invalidAcc.setReportUploadLogId(reportUploadLogId);
							invalidAcc.setCreatedby(userId);
							invalidAcc.setCra(cra);
							
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
												//_log.info("cell " + cell.getCellType());
												if(val.equalsIgnoreCase("nil") || val.equalsIgnoreCase("na")) {
													break rowloop;
												}
												else {
													try {
														int iValue = Integer.parseInt(val);
														invalidAcc.setSr_no(iValue);
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
											try {
												Integer lValue = Integer.parseInt(val);
												invalidAcc.setPayment_id(lValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 2) {
											invalidAcc.setRet_ref_no(val);
										} 
										else if (i == 3) {
											try {
//												Integer iValue = Integer.parseInt(val);
//												invalidAcc.setSource_acc_no_nodal(iValue);
												invalidAcc.setSource_acc_no_nodal(val);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;											
											}
										} 
										else if (i == 4) {
											invalidAcc.setIfsc_source_no(val);
										} 
										else if (i == 5) {
											try {
												Date date = cell.getDateCellValue();
												invalidAcc.setPayment_receipt_date(date);
											} catch (Exception e) {
												_log.error(e);												
											}
										}
										else if (i == 6) {
											invalidAcc.setPeriod_(val);
										}
										else if (i == 7) {
											invalidAcc.setOriginal_utr(val);
										}
										else if (i == 8) {
											invalidAcc.setMode_(val);
										} 
										else if (i == 9) {
											try {
												BigDecimal utr_amount =CommonParser.parseBigDecimal(val);
												invalidAcc.setUtr_amount(utr_amount);
											} catch (Exception e) {
												_log.info("error parsing bigdec"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;											
											}
										} 
										else if (i == 10) {
											try {
												Integer lValue = Integer.parseInt(val);
												invalidAcc.setBeneficiary_acc_no(lValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;												
											}
										} 
										else if (i == 11) {
											try {
												Integer lValue = Integer.parseInt(val);
												invalidAcc.setNps_virtual_acc_no(lValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;										
											}
										} 
										else if (i == 12) {
											invalidAcc.setNps_acc_name(val);
										} 
										else if (i == 13) {
											try {
												Date date = cell.getDateCellValue();
												invalidAcc.setReturn_date(date);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;												
											}
										} 
										else if (i == 14) {
											invalidAcc.setReturned_utr(val);
										} 
										else if (i == 15) {
											try {
												Integer lValue = Integer.parseInt(val);
												invalidAcc.setTid(lValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;												
											}
										} 
										else if (i == 16) {
											invalidAcc.setReason_return(val);
										} 
										else if (i == 17) {
											invalidAcc.setAdditional_comments(val);
										} 
										else if (i == 18) {
											invalidAcc.setPao_name(val);
										} 
										else if (i == 19) {
											invalidAcc.setEmail_id(val);
										} 
										else if (i == 20) {
											invalidAcc.setReturn_tat_remarks(val);
										} 
										
									}
								} else if(i == 0 && rowCount > 1) {
									break rowloop;
								}
							}
							invalidAcc.setCreatedon(new Date());
							
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
								//JSONObject invalidAccountJsonObject = JSONFactoryUtil.createJSONObject(invalidAcc.toString());
								//invalidAccountJsonArray.put(invalidAccountJsonObject);
								_log.info(invalidAcc);
								invalidAccountList.add(invalidAcc);
							}
							rowCount++;
						}
						rowCount = 1;
					}
					sheetCount++;
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


public static JSONObject saveSheetTwelveB(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray invalidAccountIIJsonArray, List<InvalidAccII> invalidAccountIIList, String cra,JSONObject resultJsonObject, long reportUploadLogId) {
	_log.info("Inside save sheetTwelveB");
	
	InvalidAccIILocalServiceUtil.deleteInvalidAccIIByReportUploadLogId(reportUploadLogId);
	
	try {
		if (file != null) {
			OPCPackage pkg = OPCPackage.open(file);
			workbook = new XSSFWorkbook(pkg);
			//XSSFSheet sheet = workbook.getSheetAt(17); //17,18
			//Iterator<Row> rows = sheet.rowIterator();
			Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
			int rowCount = 1;
			int errorRowCount = 2;
			int sheetCount = 0;
			
			
			/*if(Validator.isNotNull(workbook.getSheet("Invalid_Acc_No")) || 
				Validator.isNotNull(workbook.getSheet("Amount_Mismatch"))) {
				
			}*/
			
			while(sheetIterator.hasNext()) {
				XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
				_log.info(sheets.getSheetName());
				Iterator<Row> rows = sheets.iterator();
				
				String sheetName = sheets.getSheetName();
				boolean sheet1 = sheetName.equalsIgnoreCase("Invalid_Acc_No");
				boolean sheet2 = sheetName.equalsIgnoreCase("Amount_Mismatch");
				boolean sheet3 = sheetName.equalsIgnoreCase("Expired_Tran_ID");
				boolean sheet4 = sheetName.equalsIgnoreCase("No_TranID_inward_msg");
				
				if(sheet1 || sheet2 || sheet3 || sheet4 && sheets != null) {
					rowloop:
						while (rows.hasNext()) {
							
							InvalidAccII invalidAccII = InvalidAccIILocalServiceUtil.
									createInvalidAccII(CounterLocalServiceUtil.increment(InvalidAccII.class.getName()));
									
							invalidAccII.setReportUploadLogId(reportUploadLogId);
							invalidAccII.setCreatedby(userId);
							invalidAccII.setCra(cra);
							
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
										//log.info("Val: " + val);
										if (i == 0) {
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												//_log.info("cell " + cell.getCellType());
												if(val.equalsIgnoreCase("nil") || val.equalsIgnoreCase("na")) {
													break rowloop;
												}
												else {
													try {
														int iValue = Integer.parseInt(val);
														invalidAccII.setSr_no(iValue);
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
											try {
												Integer lValue = Integer.parseInt(val);
												invalidAccII.setPayment_id(lValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
											
										}
										else if (i == 2) {
											invalidAccII.setRet_ref_no(val);
										} 
										else if (i == 3) {
											try {
												Integer lValue = Integer.parseInt(val);
												invalidAccII.setSource_acc_no_nodal(lValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
												
											}
										} 
										else if (i == 4) {
											invalidAccII.setIfsc_source_no(val);
										} 
										else if (i == 5) {
											try {
												Date date = cell.getDateCellValue();
												invalidAccII.setPayment_receipt_date(date);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 6) {
											invalidAccII.setPeriod_(val);
										}
										else if (i == 7) {
											invalidAccII.setOriginal_utr(val);
										}
										else if (i == 8) {
											invalidAccII.setMode_(val);
										} 
										else if (i == 9) {
											try {
												BigDecimal utr_amount = CommonParser.parseBigDecimal(val);
												invalidAccII.setUtr_amount(utr_amount);
											} catch (Exception e) {
												_log.info("error parsing bigdec"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 10) {
											try {
												Integer lValue = Integer.parseInt(val);
												invalidAccII.setBeneficiary_acc_no(lValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 11) {
											try {
												Integer iValue = Integer.parseInt(val);
												invalidAccII.setNps_virtual_acc_no(iValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 12) {
											invalidAccII.setNps_acc_name(val);
										} 
										else if (i == 13) {
											try {
												Date date = cell.getDateCellValue();
												invalidAccII.setReturn_date(date);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 14) {
											invalidAccII.setReturned_utr(val);
										} 
										else if (i == 15) {
											try {
												Integer lValue = Integer.parseInt(val);
												invalidAccII.setTid(lValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 16) {
											invalidAccII.setReason_return(val);
										} 
										else if (i == 17) {
											invalidAccII.setAdditional_comments(val);
										} 
										else if (i == 18) {
											invalidAccII.setPao_name(val);
										} 
										else if (i == 19) {
											invalidAccII.setEmail_id(val);
										} 
										else if (i == 20) {
											invalidAccII.setReturn_tat_remarks(val);
										} 
										
									}
								} else if(i == 0 && rowCount > 1) {
									break rowloop;
								}
							}
							invalidAccII.setCreatedon(new Date());
							
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
								//JSONObject invalidAccountIIJsonObject = JSONFactoryUtil.createJSONObject(invalidAccII.toString());
								//invalidAccountIIJsonArray.put(invalidAccountIIJsonObject);
								_log.info(invalidAccII);
								invalidAccountIIList.add(invalidAccII);
							}
							rowCount++;
						}
						rowCount = 1;
					}
					sheetCount++;
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
