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

import compliance.service.model.NoTID;
import compliance.service.service.NoTIDLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheeFifteen {
public static JSONObject saveSheetFifteen(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray noTIDJsonArray, List<NoTID> noTIDList, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList) {
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
				
				NoTID noTID = NoTIDLocalServiceUtil.
						createNoTID(CounterLocalServiceUtil.increment(NoTID.class.getName()));
						
				noTID.setCreatedby(userId);
				noTID.setCra(cra);
				
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
							
							if (i == 0) {
								if (val != null && Validator.isNotNull(val) && val.length() > 0) {
									
									try {
										long srno = Long.parseLong(val);
										noTID.setSr_no(srno);
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
								noTID.setPayment_id(val);
							}
							else if (i == 2) {
								noTID.setRet_ref_no(val);
							} 
							else if (i == 3) {
								noTID.setSource_acc_no_nodal(val);
							} 
							else if (i == 4) {
								noTID.setIfsc_source_no(val);
							} 
							else if (i == 5) {
								try {
									Date date= cell.getDateCellValue();
									noTID.setPayment_receipt_date(date);
								} catch (Exception e) {
									_log.info("error parsing date"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 6) {
								noTID.setPeriod_(val);
							}
							else if (i == 7) {
								noTID.setOriginal_utr(val);
							}
							else if (i == 8) {
								noTID.setMode_(val);
							} 
							else if (i == 9) {
								
								try {
									BigDecimal utr_amount = CommonParser.parseBigDecimal(val);
									noTID.setUtr_amount(utr_amount);
								} catch (Exception e) {
									_log.info("error parsing bigdec"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 10) {
								noTID.setBeneficiary_acc_no(val);
							} 
							else if (i == 11) {
								noTID.setNps_virtual_acc_no(val);
							} 
							else if (i == 12) {
								noTID.setNps_acc_name(val);
							} 
							else if (i == 13) {
								try {
									Date date= cell.getDateCellValue();
									noTID.setReturn_date(date);
								} catch (Exception e) {
									_log.info("error parsing date"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 14) {
								noTID.setReturned_utr(val);
							} 
							else if (i == 15) {
								noTID.setTid(val);
							} 
							else if (i == 16) {
								noTID.setReason_return(val);
							} 
							else if (i == 17) {
								noTID.setAdditional_comments(val);
							} 
							else if (i == 18) {
								noTID.setPao_name(val);
							} 
							else if (i == 19) {
								noTID.setEmail_id(val);
							} 
							
						}
					} else if(i == 0 && rowCount > 1) {
						break rowloop;
					}
				}
				noTID.setCreatedon(new Date());
					
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
						JSONObject noTIDJsonObject = JSONFactoryUtil.createJSONObject(noTID.toString());
						noTIDJsonArray.put(noTIDJsonObject);
						noTIDList.add(noTID);
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
static Log _log = LogFactoryUtil.getLog(AnsevensheeFifteen.class);
}
