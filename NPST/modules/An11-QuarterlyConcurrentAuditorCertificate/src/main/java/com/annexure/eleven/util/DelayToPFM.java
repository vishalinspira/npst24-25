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
import java.math.BigDecimal;
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

import compliance.service.model.AccountList;
import compliance.service.model.DelayToPFMAcc;
import compliance.service.service.AccountListLocalServiceUtil;
import compliance.service.service.DelayToPFMAccLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class DelayToPFM {
	
private static Log _log = LogFactoryUtil.getLog(DelayToPFM.class);
	
	public static JSONObject saveDelayToPFMAcc(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray delayToPFMJsonArray, List<DelayToPFMAcc> delayToPFMList, DecimalFormat decimalFormat, String cra, long reportUploadLogId) {
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		DelayToPFMAccLocalServiceUtil.deleteDelayToPFMAccByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				//XSSFSheet sheet = workbook.getSheetAt(17); //17,18
				//Iterator<Row> rows = sheet.rowIterator();
				Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				//int sheetCount = 0;
				
				while(sheetIterator.hasNext()) {
					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
					_log.info(sheets.getSheetName());
					//Iterator<Row> rows = sheets.iterator();
					
					String sheetName = sheets.getSheetName();
					boolean sheet1 = sheetName.equalsIgnoreCase("NCRA_Delay_To_PFM_Acc");
					boolean sheet2 = sheetName.equalsIgnoreCase("KCRA_Delay_To_PFM_Acc");
					
					if((sheet1 || sheet2) && sheets != null) {

						XSSFSheet sheet = workbook.getSheet(sheets.getSheetName());
						Iterator<Row> Sheetrows = sheet.rowIterator();
						
						rowloop:
							while (Sheetrows.hasNext()) {
								
								DelayToPFMAcc delayToPFM = DelayToPFMAccLocalServiceUtil.
										createDelayToPFMAcc(CounterLocalServiceUtil.increment(DelayToPFMAcc.class.getName()));
										
								delayToPFM.setReportUploadLogId(reportUploadLogId);
								delayToPFM.setCreatedby(userId);
								delayToPFM.setCra(cra);
								
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
														delayToPFM.setSno(sno);
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
													long tid = CommonParser.parseLong(val);
													delayToPFM.setTid(tid);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													_log.error(e);
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 2) {
												try {
													BigDecimal bg = CommonParser.parseBigDecimal(val);
													delayToPFM.setAmt(bg.stripTrailingZeros());
												} catch (Exception e) {
													// TODO Auto-generated catch block
													_log.error(e);
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 3) {
												try {
													//String str1 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
													Date date= cell.getDateCellValue();
													delayToPFM.setFunds_receipt_date(date);
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
													//String str2 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
													Date date= cell.getDateCellValue();
													delayToPFM.setTransfer_date(date);
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
													//String str3 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
													Date date= cell.getDateCellValue();
													delayToPFM.setFrc_upload_date(date);
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
												delayToPFM.setDelay(val);
											}
											else if (i == 7) {
												delayToPFM.setAuditee_comment(val);
											}
											
										}
									} else if(i == 0 && rowCount > 1) {
										break rowloop;
									}
								}
								delayToPFM.setCreatedon(new Date());
									
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
										JSONObject delayToPFMJsonObject = JSONFactoryUtil.createJSONObject(delayToPFM.toString());
										delayToPFMJsonArray.put(delayToPFMJsonObject);
										delayToPFMList.add(delayToPFM);
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
