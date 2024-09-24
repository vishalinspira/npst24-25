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

import compliance.service.model.ConsoCollReport;
import compliance.service.service.ConsoCollReportLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheetTen {
	
private static Log _log = LogFactoryUtil.getLog(AnsevensheetTen.class);
	
public static JSONObject saveSheetTen(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray collRepJsonArray, List<ConsoCollReport> collRepList, String cra, JSONObject resultJsonObject, long reportUploadLogId) {
	
	ConsoCollReportLocalServiceUtil.deleteConsoCollReportByReportUploadLogId(reportUploadLogId);
	String sheetName = "Consolidated_Collection_Report";
	try {
		if (file != null) {
			OPCPackage pkg = OPCPackage.open(file);
			workbook = new XSSFWorkbook(pkg);
			//XSSFSheet sheet = workbook.getSheetAt(11); 
			XSSFSheet sheet = workbook.getSheet(sheetName);
			
			Iterator<Row> rows = sheet.rowIterator();
			int rowCount = 1;
			int errorRowCount = 2;
			
			rowloop:
			while (rows.hasNext()) {
				
				ConsoCollReport consoCollRep = ConsoCollReportLocalServiceUtil.
						createConsoCollReport(CounterLocalServiceUtil.increment(ConsoCollReport.class.getName()));
						
				consoCollRep.setReportUploadLogId(reportUploadLogId);
				consoCollRep.setCreatedby(userId);
				consoCollRep.setCra(cra);
				
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
									consoCollRep.setFile_name(val);
								}
								 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										
										isError = true;
									}
							}
							else if (i == 1) {
								
								consoCollRep.setChannel(val);
							}
							else if (i == 2) {
								consoCollRep.setPaofin(val);
							} 
							else if (i == 3) {
								try {
									Long reg = Long.parseLong(val);
									consoCollRep.setPao_reg_no(reg);
								} catch (Exception e) {
									_log.info("error parsing long"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 4) {
								//double trans_id = Double.parseDouble(val);
								try {
									double trans_id = Double.valueOf(val);
									consoCollRep.setTrans_id(trans_id);
								} catch (Exception e) {
									_log.info("error parsing double"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
								//consoCollRep.setTrans_id((double) deciFormatter.parse(val));
							} 
							else if (i == 5) {
								//double amt = Double.parseDouble(val);
								//double amt = Double.valueOf(val);
								//String str = val.replaceAll(",", "");
								//BigDecimal bg = new BigDecimal(str).setScale(2,RoundingMode.HALF_EVEN);
								try {
									BigDecimal amount = CommonParser.parseBigDecimal(val);
									consoCollRep.setAmount(amount);
								} catch (Exception e) {
									_log.info("error parsing bigdec"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
								//consoCollRep.setAmount(cell.getNumericCellValue());
							}
							else if (i == 6) {
								consoCollRep.setFile_ref_no(val);
							}
							else if (i == 7) {
								try {
									int record = Integer.parseInt(val);
									consoCollRep.setRecord_no(record);
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
									consoCollRep.setReceipt_date(date_1);
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
									consoCollRep.setFund_realised_date(date_2);
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
									consoCollRep.setFrc_upload_date(date_3);
								} catch (Exception e) {
									_log.info("error parsing date"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 11) {
								consoCollRep.setTrustee_bank_tat(val);
							} 
							else if (i == 12) {
								consoCollRep.setMatching_type(val);
							} 
							
						}
					} else if(i == 0 && rowCount > 1) {
						break rowloop;
					}
				}
				consoCollRep.setCreatedon(new Date());
					
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
						//JSONObject collRepJsonObject = JSONFactoryUtil.createJSONObject(consoCollRep.toString());
						//collRepJsonArray.put(collRepJsonObject);
						_log.info(consoCollRep);
						collRepList.add(consoCollRep);
					}
					rowCount++;
				}
			}
			
		}catch (Exception e) {
			resultJsonObject.put("status", false);
			 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
			 return resultJsonObject;
		}
	   return resultJsonObject;
		
	}


}
