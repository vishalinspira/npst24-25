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

import compliance.service.model.ExpTranId;
import compliance.service.service.ExpTranIdLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheeFourteen {
public static JSONObject saveSheetFourteen(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray expTranIdJsonArray, List<ExpTranId> expTranIdList, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList) {
	if(!resultJsonObject.getBoolean("status")) {
		return resultJsonObject;
	}
	String sheetName = "CL BAL";
	try {
		if (file != null) {
			OPCPackage pkg = OPCPackage.open(file);
			workbook = new XSSFWorkbook(pkg);
			XSSFSheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
				errorSheetNameList.put("CL BAL");
				resultJsonObject.put("errorSheetNameList", errorSheetNameList);
				return resultJsonObject;
			} else {
			Iterator<Row> rows = sheet.rowIterator();
			int rowCount = 1;
			int errorRowCount = 2;
			
			rowloop:
			while (rows.hasNext()) {
				
				ExpTranId expTranId = ExpTranIdLocalServiceUtil.
						createExpTranId(CounterLocalServiceUtil.increment(ExpTranId.class.getName()));
						
				expTranId.setCreatedby(userId);
				expTranId.setCra(cra);
				
				JSONObject errorObj = JSONFactoryUtil.createJSONObject();
				errorObj.put("rownum", rowCount);
				boolean isError = false;
				XSSFRow row = (XSSFRow) rows.next();
				XSSFRow errorRow = null;
				
				int lastColumn = Math.max(row.getLastCellNum(), 0);
				
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
									try {
										long srno = Long.parseLong(val);
										expTranId.setSr_no(srno);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										
										isError = true;
									}
							}
							else if (i == 1) {
								expTranId.setPayment_id(val);
							}
							else if (i == 2) {
								expTranId.setRet_ref_no(val);
							} 
							else if (i == 3) {
								expTranId.setSource_acc_no_nodal(val);
							} 
							else if (i == 4) {
								expTranId.setIfsc_source_no(val);
							} 
							else if (i == 5) {
								try {
									Date date= cell.getDateCellValue();
									expTranId.setPayment_receipt_date(date);
								} catch (Exception e) {
									_log.info("error parsing date"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 6) {
								expTranId.setPeriod_(val);
							}
							else if (i == 7) {
								expTranId.setOriginal_utr(val);
							}
							else if (i == 8) {
								expTranId.setMode_(val);
							} 
							else if (i == 9) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									expTranId.setUtr_amount(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error parsing bigdec"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 10) {
								expTranId.setBeneficiary_acc_no(val);
							} 
							else if (i == 11) {
								expTranId.setNps_virtual_acc_no(val);
							} 
							else if (i == 12) {
								expTranId.setNps_acc_name(val);
							} 
							else if (i == 13) {
								try {
									Date date= cell.getDateCellValue();
									expTranId.setReturn_date(date);
								} catch (Exception e) {
									_log.info("error parsing date"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 14) {
								expTranId.setReturned_utr(val);
							} 
							else if (i == 15) {
								expTranId.setTid(val);
							} 
							else if (i == 16) {
								expTranId.setReason_return(val);
							} 
							else if (i == 17) {
								expTranId.setAdditional_comments(val);
							} 
							else if (i == 18) {
								expTranId.setPao_name(val);
							} 
							else if (i == 19) {
								expTranId.setEmail_id(val);
							} 
							
						}
					} else if(i == 0 && rowCount > 1) {
						break rowloop;
					}
				}
				expTranId.setCreatedon(new Date());
					
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
						JSONObject expTranIdJsonObject = JSONFactoryUtil.createJSONObject(expTranId.toString());
						expTranIdJsonArray.put(expTranIdJsonObject);
						expTranIdList.add(expTranId);
					}
					rowCount++;
				}
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
static Log _log = LogFactoryUtil.getLog(AnsevensheeFourteen.class);
}
