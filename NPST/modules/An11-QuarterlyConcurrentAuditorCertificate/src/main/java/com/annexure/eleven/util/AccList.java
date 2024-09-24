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
import org.osgi.service.component.annotations.Reference;

import compliance.service.model.AccountList;
import compliance.service.model.ChequeClearance;
import compliance.service.service.AccountListLocalService;
import compliance.service.service.AccountListLocalServiceUtil;
import compliance.service.service.ChequeClearanceLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AccList {
	
	private static Log _log = LogFactoryUtil.getLog(AccList.class);
	
	public static JSONObject saveAccountList(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray accountJsonArray, List<AccountList> accountList, DecimalFormat decimalFormat, String cra, long reportUploadLogId) {
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		AccountListLocalServiceUtil.deleteAccountListByReportUploadLogId(reportUploadLogId);
		
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
					
					String sheetName = sheets.getSheetName();
					boolean sheet1 = sheetName.equalsIgnoreCase("NCRA_Acc_List");
					boolean sheet2 = sheetName.equalsIgnoreCase("KCRA_Acc_List");
					
					if((sheet1 || sheet2) && sheets != null) {
							
							XSSFSheet sheet = workbook.getSheet(sheets.getSheetName());
							Iterator<Row> Sheetrows = sheet.rowIterator();
							
							rowloop:
							while (Sheetrows.hasNext()) {
								
								AccountList accList = AccountListLocalServiceUtil.
										createAccountList(CounterLocalServiceUtil.increment(AccountList.class.getName()));
										
								accList.setReportUploadLogId(reportUploadLogId);
								accList.setCreatedby(userId);
								accList.setCra(cra);
								
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
														accList.setSno(sno);
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
													long accNo = CommonParser.parseLong(val);
													accList.setNps_acc_no(accNo);
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
												accList.setNps_acc_name(val);
											} 
											
										}
									} else if(i == 0 && rowCount > 1) {
										break rowloop;
									}
								}
								accList.setCreatedon(new Date());
									
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
										//JSONObject accListJsonObject = JSONFactoryUtil.createJSONObject(accList.toString());
										//accountJsonArray.put(accListJsonObject);
										accountList.add(accList);
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
	
	@Reference
	AccountListLocalService accountListLocalService;
	
	
	
}
