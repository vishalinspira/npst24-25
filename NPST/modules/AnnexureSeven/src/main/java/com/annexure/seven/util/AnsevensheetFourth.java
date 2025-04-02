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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.ElectronicRejects;
import compliance.service.service.ElectronicRejectsLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnsevensheetFourth {
	
private static Log _log = LogFactoryUtil.getLog(AnsevensheetFourth.class);
	
public static JSONObject saveSheetFourth(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray erejectionJsonArray, List<ElectronicRejects> electronicrej, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject, long reportUploadLogId) {
	
	ElectronicRejectsLocalServiceUtil.deleteElectronicRejectsByReportUploadLogId(reportUploadLogId);
	String sheetName = "All_Electronic_Rejects";
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
				
				ElectronicRejects eleRejects = ElectronicRejectsLocalServiceUtil.
						createElectronicRejects(CounterLocalServiceUtil.increment(ElectronicRejects.class.getName()));
						
				eleRejects.setReportUploadLogId(reportUploadLogId);
				eleRejects.setCreatedby(userId);
				eleRejects.setCra(cra);
				
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
									try {
										int srno = Integer.parseInt(val);
										eleRejects.setSr_no(srno);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
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
								try {
									long lValue = Long.parseLong(val);
									eleRejects.setPayment_id(lValue);
								} catch (Exception e) {
									_log.info("error parsing long"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 2) {
								eleRejects.setRet_ref_no(val);
							} 
							else if (i == 3) {
								try {
									//long lValue = Long.parseLong(val);
									eleRejects.setSource_acc_no_nodal(val);
								} catch (Exception e) {
									_log.info("error parsing long"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 4) {
								eleRejects.setSource_acc_no_ifsc(val);
							} 
							else if (i == 5) {
								try {
									Date date_1 = cell.getDateCellValue();
									eleRejects.setPayment_receipt_date(date_1);
								} catch (Exception e) {
									_log.info("error parsing date"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
									return resultJsonObject;
								}
							}
							else if (i == 6) {
								eleRejects.setPeriod_(val);
							}
							else if (i == 7) {
								
								eleRejects.setUtr_no(val);
							}
							else if (i == 8) {
								eleRejects.setMode_(val);
							} 
							else if (i == 9) {
								//Double amt = Double.parseDouble();
								try {
									eleRejects.setUtr_amount(cell.getNumericCellValue());
								} catch (Exception e) {
									_log.info("error parsing decimal"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 10) {
								try {
									long lValue = Long.parseLong(val);
									eleRejects.setBeneficiary_acc_no(lValue);
								} catch (Exception e) {
									_log.info("error parsing int"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 11) {
								try {
									int iValue = Integer.parseInt(val);
									eleRejects.setNps_virtual_acc_no(iValue);
								} catch (Exception e) {
									_log.info("error parsing int"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 12) {
								eleRejects.setNps_acc_name(val);
							} 
							else if (i == 13) {
								try {
									Date date_2 = cell.getDateCellValue();
									eleRejects.setReturn_date(date_2);
								} catch (Exception e) {
									_log.info("error parsing date"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 14) {
								eleRejects.setReturned_utr(val);
							} 
							else if (i == 15) {
								try {
									long lValue = Long.parseLong(val);
									eleRejects.setTid(lValue);
								} catch (Exception e) {
									_log.info("error parsing long"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								}
							} 
							else if (i == 16) {
								eleRejects.setReason_return(val);
							} 
							else if (i == 17) {
								eleRejects.setAdditional_comments(val);
							} 
							else if (i == 18) {
								eleRejects.setPao_name(val);
							} 
							else if (i == 19) {
								eleRejects.setEmail_id(val);
							} 
							else if (i == 20) {
								eleRejects.setDelay_remarks(val);
							} 
							
						}
					} else if(i == 0 && rowCount > 3) {
						break rowloop;
					}
				}
				eleRejects.setCreatedon(new Date());
					
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
						JSONObject eleRejectsJsonObject = JSONFactoryUtil.createJSONObject(eleRejects.toString());
						erejectionJsonArray.put(eleRejectsJsonObject);
						electronicrej.add(eleRejects);
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
