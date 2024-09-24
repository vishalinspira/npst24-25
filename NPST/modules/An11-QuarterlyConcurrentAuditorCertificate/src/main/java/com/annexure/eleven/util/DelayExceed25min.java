package com.annexure.eleven.util;

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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.DelayExceed_25min;
import compliance.service.service.DelayExceed_25minLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class DelayExceed25min {
	
private static Log _log = LogFactoryUtil.getLog(DelayExceed25min.class);
	
	public static JSONObject saveDelayExceed25min(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray delayExceed25minJsonArray, List<DelayExceed_25min> delayExceed25minList, DecimalFormat decimalFormat, String cra, long reportUploadLogId) {
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		DelayExceed_25minLocalServiceUtil.deleteDelayExceed_25minByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				//int sheetCount = 0;
				
				while(sheetIterator.hasNext()) {
					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
					_log.info(sheets.getSheetName());
					
					String sheetName = sheets.getSheetName();
					boolean sheet1 = sheetName.equalsIgnoreCase("NCRA_PayIn_Delay_Exceed_25mins");
					boolean sheet2 = sheetName.equalsIgnoreCase("KCRA_PayIn_Delay_Exceed_25mins");
					
					//if(sheetCount == 3 || sheetCount == 4 && sheets != null) {
					if((sheet1 || sheet2) && sheets != null) {
						
						XSSFSheet sheet = workbook.getSheet(sheets.getSheetName());
						Iterator<Row> Sheetrows = sheet.rowIterator();
						
						rowloop:
							while (Sheetrows.hasNext()) {
								
								DelayExceed_25min delayExceed25min = DelayExceed_25minLocalServiceUtil.
										createDelayExceed_25min(CounterLocalServiceUtil.increment(DelayExceed_25min.class.getName()));
										
								delayExceed25min.setReportUploadLogId(reportUploadLogId);
								delayExceed25min.setCreatedby(userId);
								delayExceed25min.setCra(cra);
								
								JSONObject errorObj = JSONFactoryUtil.createJSONObject();
								errorObj.put("rownum", rowCount);
								boolean isError = false;
								XSSFRow row = (XSSFRow) Sheetrows.next();
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
														long sno = CommonParser.parseLong(val);
														delayExceed25min.setSno(sno);
													} catch (Exception e) {
														// TODO Auto-generated catch block
														_log.error(e);
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
												try {
													//String str1 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
													Date date= cell.getDateCellValue();
													delayExceed25min.setDate_(date);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													_log.error(e);
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 2) {
												try {
													//String str2 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
													Date date= cell.getDateCellValue();
													delayExceed25min.setPayin_file_timestamp(date);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													_log.error(e);
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 3) {
												try {
													//String str3 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
													Date date= cell.getDateCellValue();
													delayExceed25min.setFund_transfer_timestamp(date);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													_log.error(e);
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 4) {
												try {
													//String str4 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
													Date date= cell.getDateCellValue();
													delayExceed25min.setTat_time(date);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													_log.error(e);
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 5) {
												try {
													//String str5 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
													Date date= cell.getDateCellValue();
													delayExceed25min.setDelay(date);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													_log.error(e);
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 6) {
												delayExceed25min.setAuditee_comments(val);
											}
											
										}
									} else if(i == 0 && rowCount > 1) {
										break rowloop;
									}
								}
								delayExceed25min.setCreatedon(new Date());
									
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
										//JSONObject delayExceed25minJsonObject = JSONFactoryUtil.createJSONObject(delayExceed25min.toString());
										//delayExceed25minJsonArray.put(delayExceed25minJsonObject);
										delayExceed25minList.add(delayExceed25min);
									}
									rowCount++;
								}
							rowCount = 1;
							_log.info(sheetName +" rowcount"+rowCount);
						}
						//sheetCount++;
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
