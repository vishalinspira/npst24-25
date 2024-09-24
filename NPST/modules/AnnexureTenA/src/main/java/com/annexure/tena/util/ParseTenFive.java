package com.annexure.tena.util;



import com.daily.pfm.service.model.AnnexTenaIV;
import com.daily.pfm.service.service.AnnexTenaIVLocalServiceUtil;
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

import nps.common.service.util.CommonParser;

public class ParseTenFive {
public static JSONObject saveSheetFive(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray annexTenaIVJsonArray, List<AnnexTenaIV> annexTenaIVs, JSONObject resultJsonObject,JSONArray  errorSheetNameList,long reportUploadLogId, String cra) {
	
	AnnexTenaIVLocalServiceUtil.deleteAnnexTenaIVByReportUploadLogId(reportUploadLogId);
	try {
		if (file != null) {
			OPCPackage pkg = OPCPackage.open(file);
			workbook = new XSSFWorkbook(pkg);
			XSSFSheet sheet = workbook.getSheet("CRA_Hold_Amt_Ageing");
			String sheetName = sheet.getSheetName();
			if (sheet == null || !resultJsonObject.getBoolean("status")) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
				errorSheetNameList.put("CRA_Hold_Amt_Ageing");
				resultJsonObject.put("errorSheetNameList", errorSheetNameList);
				return resultJsonObject;
			} else {
			Iterator<Row> rows = sheet.rowIterator();
			int rowCount = 1;
			int errorRowCount = 2;
			
			rowLoop:
			while (rows.hasNext()) {
				
				AnnexTenaIV annexTenaIV = AnnexTenaIVLocalServiceUtil.createAnnexTenaIV(CounterLocalServiceUtil.increment(AnnexTenaIV.class.getName()));
				
				annexTenaIV.setReportUploadLogId(reportUploadLogId);
				annexTenaIV.setCreatedby(userId);
				annexTenaIV.setCra(cra);
				
				JSONObject errorObj = JSONFactoryUtil.createJSONObject();
				errorObj.put("rownum", rowCount);
				boolean isError = false; 
				XSSFRow row = (XSSFRow) rows.next();
				XSSFRow errorRow = null;
				
				for (int i = 0; i < 4; i++) {
					XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					if (cell != null) {

						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						if (val != null)
							val = val.trim();
						if(rowCount > 1) {
							_log.info(val);
							
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										
										annexTenaIV.setPeriod(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										//Integer no_of_remittances=Integer.parseInt(val);
										BigDecimal  no_of_remittances =  CommonParser.parseBigDecimal(val);
										annexTenaIV.setNo_of_remittances(no_of_remittances);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 2) {
									try {
										BigDecimal  amt =  CommonParser.parseBigDecimal(val);
										annexTenaIV.setAmt(amt);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										Date date= cell.getDateCellValue();
										annexTenaIV.setMonth(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										 
									}
								} 
							}
					}else if(i==0 && rowCount > 1) {
						break rowLoop;
					}
				}
				annexTenaIV.setCreatedate(new Date());
					
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
					//JSONObject annexTenaIVJsonObject = JSONFactoryUtil.createJSONObject(annexTenaIV.toString());
					//annexTenaIVJsonArray.put(annexTenaIVJsonObject);
					annexTenaIVs.add(annexTenaIV);
					resultJsonObject.put("status", true);
				}
				rowCount++;
			}
			_log.info(sheetName +" rowcount"+rowCount);
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
static Log _log = LogFactoryUtil.getLog(ParseTenFive.class);
}