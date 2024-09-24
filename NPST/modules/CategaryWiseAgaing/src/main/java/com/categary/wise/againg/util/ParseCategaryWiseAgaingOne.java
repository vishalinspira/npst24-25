package com.categary.wise.againg.util;

import com.daily.average.service.model.CategarywiseOne;
import com.daily.average.service.service.CategarywiseOneLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
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

public class ParseCategaryWiseAgaingOne {
	static Log _log = LogFactoryUtil.getLog(ParseCategaryWiseAgaingOne.class);
	public static JSONObject saveSheetOne(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,List<CategarywiseOne> categarywiseOnes,
				JSONArray categarywiseOneJsonArray, DecimalFormat decimalFormat, long reportUploadLogId, String cra) {
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(0);
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					CategarywiseOne categarywiseOne = CategarywiseOneLocalServiceUtil.createCategarywiseOne(CounterLocalServiceUtil.increment(CategarywiseOne.class.getName()));
							
					categarywiseOne.setCreatedby(userId);
					categarywiseOne.setReportUploadLogId(reportUploadLogId);
					categarywiseOne.setCra(cra);
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 11; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										
										categarywiseOne.setScheme_name(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										Date date= cell.getDateCellValue();
										categarywiseOne.setDate_(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//categarywiseOne.setDate_(cell.getDateCellValue());
									}
								}
								else if (i == 2) {
									
									categarywiseOne.setGrievance_category(val);
								}
								
								else if (i == 3) {
									try {
										Integer griev_outstanding_at_month_end= Integer.parseInt(val);
										categarywiseOne.setGriev_outstanding_at_month_end(griev_outstanding_at_month_end);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										Integer zero_seven= Integer.parseInt(val);
										categarywiseOne.setZero_seven(zero_seven);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 5) {
									try {
										Integer eight_fifteen= Integer.parseInt(val);
										categarywiseOne.setEight_fifteen(eight_fifteen);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										Integer sixteen_thirtyone= Integer.parseInt(val);
										categarywiseOne.setSixteen_thirtyone(sixteen_thirtyone);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										Integer thirtytwo_ninety= Integer.parseInt(val);
										categarywiseOne.setThirtytwo_ninety(thirtytwo_ninety);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									try {
										Integer ninetyone_oneeighty= Integer.parseInt(val);
										categarywiseOne.setNinetyone_oneeighty(ninetyone_oneeighty);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 9) {
									try {
										Integer oneeighttwo_threesixtyfive= Integer.parseInt(val);
										categarywiseOne.setOneeighttwo_threesixtyfive(oneeighttwo_threesixtyfive);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 10) {
									try {
										Integer morethan_threesixtysix= Integer.parseInt(val);
										categarywiseOne.setMorethan_threesixtysix(morethan_threesixtysix);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					categarywiseOne.setCreatedate(new Date());
					
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
						//JSONObject categarywiseOneJsonObject = JSONFactoryUtil.createJSONObject(categarywiseOne.toString());
						//categarywiseOneJsonArray.put(categarywiseOneJsonObject);
						categarywiseOnes.add(categarywiseOne);
					}
					rowCount++;
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
}
