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

import compliance.service.model.NPSAccepted;
import compliance.service.service.NPSAcceptedLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheetTwo {
	
	public static JSONObject saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray npsacceptJsonArray, List<NPSAccepted> npsaccept, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList) {
		//List<NPSAccepted> npsaccept
		String sheetName = "All_ENPS_Accepted";
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("All_ENPS_Accepted");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
					Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					
					rowloop:
					while (rows.hasNext()) {
						
						NPSAccepted npsAccepted = NPSAcceptedLocalServiceUtil.createNPSAccepted(CounterLocalServiceUtil.increment(NPSAccepted.class.getName()));
								
						npsAccepted.setCreatedby(userId);
						npsAccepted.setCra(cra);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						for (int i = 0; i < 13; i++) {
							XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							if (cell != null) {
	
								DataFormatter formatter = new DataFormatter();
								
								String val = formatter.formatCellValue(cell);
								if (val != null)
									val = val.trim();
								if(rowCount > 1) {
									if (i == 0) {
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											//Long srno=Long.parseLong(val);
											npsAccepted.setFile_name(val);
										}
										 else {
												errorObj.put("cellno", 2);
												errorObj.put("msg", "It is not a number");
												
												isError = true;
											}
									}
									else if (i == 1) {
										
										npsAccepted.setChannel(val);
									}
									else if (i == 2) {
										npsAccepted.setPao_fin(val);
									} 
									else if (i == 3) {
										try {
											long reg = Long.parseLong(val);
											npsAccepted.setPao_reg_no(reg);
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
											npsAccepted.setTrans_id(transId);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 5) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);
											npsAccepted.setAmt(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing bigdec"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 6) {
										npsAccepted.setFile_ref_no(val);
									}
									else if (i == 7) {
										try {
											int recordNo = Integer.parseInt(val);
											npsAccepted.setRecord_no(recordNo);
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
											npsAccepted.setReceipt_date(date_1);
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
											npsAccepted.setFund_realised_date(date_2);
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
											npsAccepted.setFrc_upload_date(date_3);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 11) {
										npsAccepted.setTrustee_bank_tat(val);
									} 
									else if (i == 12) {
										npsAccepted.setMatching_type(val);
									} 
									
								}
							} else if(i == 0 && rowCount > 1) {
								break rowloop;
							}
						}
						npsAccepted.setCreatedon(new Date());
							
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
								JSONObject npsAcceptedJsonObject = JSONFactoryUtil.createJSONObject(npsAccepted.toString());
								npsacceptJsonArray.put(npsAcceptedJsonObject);
								npsaccept.add(npsAccepted);
							}
							rowCount++;
						}
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
	
	static Log _log = LogFactoryUtil.getLog(AnsevensheetTwo.class);
}
