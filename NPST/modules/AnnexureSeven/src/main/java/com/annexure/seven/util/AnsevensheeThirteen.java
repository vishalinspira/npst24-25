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

import compliance.service.model.AmtMismatch;
import compliance.service.service.AmtMismatchLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheeThirteen {
public static JSONObject saveSheetThirteen(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray amtMismatchJsonArray, List<AmtMismatch> amtMismatchList, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList) {
	if(!resultJsonObject.getBoolean("status")) {
		return resultJsonObject;
	}
	String sheetName = "Invalid_Acc_No";
	try {
		if (file != null) {
			OPCPackage pkg = OPCPackage.open(file);
			workbook = new XSSFWorkbook(pkg);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
				errorSheetNameList.put("Invalid_Acc_No");
				resultJsonObject.put("errorSheetNameList", errorSheetNameList);
				return resultJsonObject;
			} else {
			Iterator<Row> rows = sheet.rowIterator();
			int rowCount = 1;
			int errorRowCount = 2;
			
			rowloop:
			while (rows.hasNext()) {
				
				AmtMismatch amtMismatch = AmtMismatchLocalServiceUtil.
						createAmtMismatch(CounterLocalServiceUtil.increment(AmtMismatch.class.getName()));
						
				amtMismatch.setCreatedby(userId);
				amtMismatch.setCra(cra);
				
				JSONObject errorObj = JSONFactoryUtil.createJSONObject();
				errorObj.put("rownum", rowCount);
				boolean isError = false;
				XSSFRow row = (XSSFRow) rows.next();
				XSSFRow errorRow = null;
				
				for (int i = 0; i < 20; i++) {
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
									//_log.info("cell " + cell.getCellType());
									if(val.equalsIgnoreCase("nil") || val.equalsIgnoreCase("na")) {
										break rowloop;
									}
									else {
										try {
											long srno = Long.parseLong(val);
											amtMismatch.setSr_no(srno);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
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
								amtMismatch.setPayment_id(val);
							}
							else if (i == 2) {
								amtMismatch.setRet_ref_no(val);
							} 
							else if (i == 3) {
								amtMismatch.setSource_acc_no_nodal(val);
							} 
							else if (i == 4) {
								amtMismatch.setIfsc_source_no(val);
							} 
							else if (i == 5) {
								try {
									Date date= cell.getDateCellValue();
									amtMismatch.setPayment_receipt_date(cell.getDateCellValue());
								} catch (Exception e) {
									_log.info("error parsing int"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 6) {
								amtMismatch.setPeriod_(val);
							}
							else if (i == 7) {
								amtMismatch.setOriginal_utr(val);
							}
							else if (i == 8) {
								amtMismatch.setMode_(val);
							} 
							else if (i == 9) {
								
								try {
									BigDecimal utr_amount = CommonParser.parseBigDecimal(val);
									amtMismatch.setUtr_amount(utr_amount);
								} catch (Exception e) {
									_log.info("error parsing int"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;								}
							} 
							else if (i == 10) {
								amtMismatch.setBeneficiary_acc_no(val);
							} 
							else if (i == 11) {
								amtMismatch.setNps_virtual_acc_no(val);
							} 
							else if (i == 12) {
								amtMismatch.setNps_acc_name(val);
							} 
							else if (i == 13) {
								try {
									Date date = cell.getDateCellValue();
									amtMismatch.setReturn_date(date);
								} catch (Exception e) {
									_log.info("error parsing date"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 14) {
								amtMismatch.setReturned_utr(val);
							} 
							else if (i == 15) {
								amtMismatch.setTid(val);
							} 
							else if (i == 16) {
								amtMismatch.setReason_return(val);
							} 
							else if (i == 17) {
								amtMismatch.setAdditional_comments(val);
							} 
							else if (i == 18) {
								amtMismatch.setPao_name(val);
							} 
							else if (i == 19) {
								amtMismatch.setEmail_id(val);
							} 
							
						}
					} else if(i == 0 && rowCount > 1) {
						break rowloop;
					}
				}
				amtMismatch.setCreatedon(new Date());
					
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
						JSONObject amtMismatchJsonObject = JSONFactoryUtil.createJSONObject(amtMismatch.toString());
						amtMismatchJsonArray.put(amtMismatchJsonObject);
						amtMismatchList.add(amtMismatch);
					}
					rowCount++;
				}
			}
			}
		}catch (Exception e) {
			resultJsonObject.put("status", false);
			 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
			 return resultJsonObject;
		}
	   return resultJsonObject;
	}
static Log _log = LogFactoryUtil.getLog(AnsevensheeThirteen.class);
}
