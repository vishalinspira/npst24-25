package com.annexure.seven.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.ElectronicAccepted;
import compliance.service.service.ElectronicAcceptedLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheetOne {
	
	private static Log _log = LogFactoryUtil.getLog(AnsevensheetOne.class);
	
	public static JSONObject saveSheetOne(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray electonicJsonArray, List<ElectronicAccepted> electronicaccepted, DecimalFormat decimalFormat, String cra,JSONObject resultJsonObject, long reportUploadLogId) {
		
//		try {
//			if (file != null) {
//				OPCPackage pkg = OPCPackage.open(file);
//				workbook = new XSSFWorkbook(pkg);
//				XSSFSheet sheet = workbook.getSheetAt(1);
//				Iterator<Row> rows = sheet.rowIterator();
//				int rowCount = 1;
//				int errorRowCount = 2;
//				
//				rowloop:
//				while (rows.hasNext()) {
//					
//					ElectronicAccepted electronicsAccepted = ElectronicAcceptedLocalServiceUtil.createElectronicAccepted(CounterLocalServiceUtil.increment(ElectronicAccepted.class.getName()));
//							
//					electronicsAccepted.setCreatedby(userId);
//					electronicsAccepted.setCra(cra);
//					
//					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
//					errorObj.put("rownum", rowCount);
//					boolean isError = false;
//					XSSFRow row = (XSSFRow) rows.next();
//					XSSFRow errorRow = null;
//					
//					for (int i = 0; i < 13; i++) {
//						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//						if (cell != null) {
//
//							DataFormatter formatter = new DataFormatter();
//							
//							String val = formatter.formatCellValue(cell);
//							if (val != null)
//								val = val.trim();
//							if(rowCount > 1) {
//								if (i == 0) {
//									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
//										//Long srno=Long.parseLong(val);
//										electronicsAccepted.setFile_name(val); 
//									}
//									 else {
//											errorObj.put("cellno", 2);
//											errorObj.put("msg", "It is not a number");
//											
//											isError = true;
//										}
//								}
//								else if (i == 1) {
//									
//									electronicsAccepted.setChannel(val);
//								}
//								else if (i == 2) {
//									electronicsAccepted.setPao_fin(val);
//								} 
//								else if (i == 3) {
//									long reg = Long.parseLong(val);
//									electronicsAccepted.setPao_reg_no(reg);
//								} 
//								else if (i == 4) {
//									long transId = Long.parseLong(val);
//									electronicsAccepted.setTrans_id(transId);
//								} 
//								else if (i == 5) {
//									BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
//									electronicsAccepted.setAmt(bg.stripTrailingZeros());
//								}
//								else if (i == 6) {
//									electronicsAccepted.setFile_ref_no(val);
//								}
//								else if (i == 7) {
//									int recordNo = Integer.parseInt(val);
//									electronicsAccepted.setRecord_no(recordNo);
//								}
//								else if (i == 8) {
//									Date date_1 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
//									electronicsAccepted.setReceipt_date(date_1);
//								} 
//								else if (i == 9) {
//									Date date_2 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
//									electronicsAccepted.setFund_realised_date(date_2);
//								} 
//								else if (i == 10) {
//									Date date_3 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
//									electronicsAccepted.setFrc_upload_date(date_3);
//								} 
//								else if (i == 11) {
//									electronicsAccepted.setTrustee_bank_tat(val);
//								} 
//								else if (i == 12) {
//									electronicsAccepted.setMatching_type(val);
//								} 
//								
//							}
//						} else if(i == 0 && rowCount > 1) {
//							break rowloop;
//						}
//					}
//					electronicsAccepted.setCreatedon(new Date());
//						
//						if (isError && rowCount > 1) {
//							errorArray.put(errorObj);
//							errorRow = xx.createRow(errorRowCount);
//							errorRowCount++;
//							XSSFCell rowCountCel = errorRow.createCell(1);
//							rowCountCel.setCellValue(rowCount);
//							XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
//							cellError.setCellValue(errorObj.getString("msg"));
//							isexcelhaserror = true;
//						} else if (rowCount > 1) {
//							JSONObject eleAcceptedJsonObject = JSONFactoryUtil.createJSONObject(electronicsAccepted.toString());
//							electonicJsonArray.put(eleAcceptedJsonObject);
//							electronicaccepted.add(electronicsAccepted);
//						}
//						rowCount++;
//					}
//					
//				}
//			}catch (Exception e) {
//				_log.error(e);
//			}
		
		_log.info("Inside save sheetOne");
		
		ElectronicAcceptedLocalServiceUtil.deleteElectronicAcceptedByReportUploadLogId(reportUploadLogId);
		
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
//				ArrayList <String> list = new ArrayList<String>();
//			      list.add("All_Electronic_Accepted");
//				  list.add("All_ENPS_Accepted");
//				  list.add("All_DRemit_Accepted");
//				  list.add("All_Chq_Accepted");
//				
//				while(sheetIterator.hasNext()) {
//					XSSFSheet sheet = (XSSFSheet) sheetIterator.next();
//					if(list.contains(sheet.getSheetName())) {
//						list.remove(sheet.getSheetName());
//					}
//				}
				
				
				while(sheetIterator.hasNext()) {
					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
					
					Iterator<Row> rows = sheets.iterator();
					
					String sheetName = sheets.getSheetName();
					boolean sheet1 = sheetName.equalsIgnoreCase("All_Electronic_Accepted");
					boolean sheet2 = sheetName.equalsIgnoreCase("All_ENPS_Accepted");
					boolean sheet3 = sheetName.equalsIgnoreCase("All_DRemit_Accepted");
					boolean sheet4 = sheetName.equalsIgnoreCase("All_Chq_Accepted");
					
					
					if(sheet1 || sheet2 || sheet3 || sheet4 && sheets != null) {
						rowloop:
							while (rows.hasNext()) {
								
								ElectronicAccepted electronicsAccepted = ElectronicAcceptedLocalServiceUtil.createElectronicAccepted(CounterLocalServiceUtil.increment(ElectronicAccepted.class.getName()));
										
								electronicsAccepted.setReportUploadLogId(reportUploadLogId);
								electronicsAccepted.setCreatedby(userId);
								electronicsAccepted.setCra(cra);
								
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
											//_log.info("Val: " + val);
											if (i == 0) {
												if (val != null && Validator.isNotNull(val) && val.length() > 0) {
													//Long srno=Long.parseLong(val);
													electronicsAccepted.setFile_name(val); 
												}
												 else {
														errorObj.put("cellno", 2);
														errorObj.put("msg", "It is not a number");
														
														isError = true;
													}
											}
											else if (i == 1) {
												
												electronicsAccepted.setChannel(val);
											}
											else if (i == 2) {
												//_log.info("cell" + cell.getCellType());
												electronicsAccepted.setPao_fin(val);
											} 
											else if (i == 3) {
												try {
													long reg = Long.parseLong(val);
													electronicsAccepted.setPao_reg_no(reg);
												} catch (Exception e) {
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 4) {
												try {
													long transId = Long.parseLong(val);
													electronicsAccepted.setTrans_id(transId);
												} catch (Exception e) {
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 5) {
												try {
													BigDecimal bg =CommonParser.parseBigDecimal(val);
													electronicsAccepted.setAmt(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.info("error parsing igdec"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 6) {
												electronicsAccepted.setFile_ref_no(val);
											}
											else if (i == 7) {
												try {
													int recordNo = Integer.parseInt(val);
													electronicsAccepted.setRecord_no(recordNo);
												} catch (Exception e) {
													_log.info("error parsing int"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 8) {
												try {
													Date date_1 = cell.getDateCellValue();
													electronicsAccepted.setReceipt_date(date_1);
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 9) {
												try {
													Date date_2 = cell.getDateCellValue();
													electronicsAccepted.setFund_realised_date(date_2);
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 10) {
												try {
													Date date_3 = cell.getDateCellValue();
													electronicsAccepted.setFrc_upload_date(date_3);
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 11) {
												electronicsAccepted.setTrustee_bank_tat(val);
											} 
											else if (i == 12) {
												electronicsAccepted.setMatching_type(val);
											} 
											
										}
									} else if(i == 0 && rowCount > 1) {
										break rowloop;
									}
								}
								electronicsAccepted.setCreatedon(new Date());
									
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
										JSONObject eleAcceptedJsonObject = JSONFactoryUtil.createJSONObject(electronicsAccepted.toString());
										electonicJsonArray.put(eleAcceptedJsonObject);
										electronicaccepted.add(electronicsAccepted);
									}
									rowCount++;
								}
								rowCount = 1;
						}
						sheetCount++;
					}
				
				}
			}catch (Exception e) {
				resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
				 return resultJsonObject;
			}
		   return resultJsonObject;
		
	}
	
	public static String formatDate(Date dateIn, String format) throws ParseException {
        String strDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(dateIn);
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date d1 = sdf3.parse(formattedDate);

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            strDate = formatter.format(d1);
        } catch (Exception e) {
            _log.error(e);
        }
        return strDate;
    }
	
}

