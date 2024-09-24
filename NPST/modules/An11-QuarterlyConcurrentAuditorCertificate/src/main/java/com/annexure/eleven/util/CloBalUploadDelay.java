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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.ClosingBalUploadDelay;
import compliance.service.service.ClosingBalUploadDelayLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class CloBalUploadDelay {
	
	private static Log _log = LogFactoryUtil.getLog(CloBalUploadDelay.class);
	
	public static JSONObject saveCloBalUploadDelay(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray closingBalUploadJsonArray, List<ClosingBalUploadDelay> closingBalUploadList, DecimalFormat decimalFormat, long reportUploadLogId) {
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		ClosingBalUploadDelayLocalServiceUtil.deleteClosingBalUploadDelayByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				//XSSFSheet sheet = workbook.getSheetAt(19); 
				XSSFSheet sheet = workbook.getSheet("Closing_Bal_Upload_Delay"); 
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					
					ClosingBalUploadDelay closingBalUpload = ClosingBalUploadDelayLocalServiceUtil.
							createClosingBalUploadDelay(CounterLocalServiceUtil.increment(ClosingBalUploadDelay.class.getName()));
							
					closingBalUpload.setReportUploadLogId(reportUploadLogId);
					closingBalUpload.setCreatedby(userId);
					
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
											long sno = CommonParser.parseLong(val);
											closingBalUpload.setSno(sno);
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
									closingBalUpload.setFile_name(val);
								}
								else if (i == 2) {
									closingBalUpload.setUploaded_by(val);
								} 
								else if (i == 3) {
									try {
										//String str1 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
										Date date= cell.getDateCellValue();
										closingBalUpload.setUploaded_date(date);
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
										closingBalUpload.setUploaded_time(date);
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
										closingBalUpload.setTat(date);
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
									try {
										//String str4 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
										Date date= cell.getDateCellValue();
										closingBalUpload.setDelay(date);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										_log.error(e);
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									closingBalUpload.setStatus(val);
								}
								else if (i == 8) {
									closingBalUpload.setCra(val);
								} 
								else if (i == 9) {
									closingBalUpload.setAuditees_comment(val);
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					closingBalUpload.setCreatedon(new Date());
						
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
							//JSONObject closingBalUploadJsonObject = JSONFactoryUtil.createJSONObject(closingBalUpload.toString());
							//closingBalUploadJsonArray.put(closingBalUploadJsonObject);
							closingBalUploadList.add(closingBalUpload);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);

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
