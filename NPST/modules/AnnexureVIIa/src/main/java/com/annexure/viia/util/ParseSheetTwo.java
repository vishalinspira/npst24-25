package com.annexure.viia.util;

import com.daily.average.service.model.AnnexviiaKfintech;
import com.daily.average.service.service.AnnexviiaKfintechLocalServiceUtil;
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

public class ParseSheetTwo {
	public static JSONObject saveSheetTwo(File file, XSSFWorkbook workbook,JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, long userId, List<AnnexviiaKfintech> annexviiaKfintechs, JSONArray annexviiaKfintechJsonArray, DecimalFormat decimalFormat, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId) {
		
		AnnexviiaKfintechLocalServiceUtil.deleteAnnexviiaKfintechByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("KFINTECH");
				String sheetName = sheet.getSheetName();
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("KFINTECH");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					AnnexviiaKfintech annexviiaKfintech = AnnexviiaKfintechLocalServiceUtil.createAnnexviiaKfintech(CounterLocalServiceUtil.increment(AnnexviiaKfintech.class.getName()));
					
					annexviiaKfintech.setReportUploadLogId(reportUploadLogId);
					annexviiaKfintech.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 13; i++) {
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
										try {
											Long virtual_id = CommonParser.parseLong(val);
											annexviiaKfintech.setVirtual_id(virtual_id);
										} catch (Exception e) {
											 _log.error(e);
											 _log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
										}
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									annexviiaKfintech.setRequest_type(val);
								}
								else if (i == 2) {
									annexviiaKfintech.setSender_name(val);
								}
								else if (i == 3) {
									try {
										Integer pop_reg_no = Integer.parseInt(val);
										annexviiaKfintech.setPop_reg_no(pop_reg_no);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								
								else if (i == 4) {
									try {
										Integer pop_sp_reg_no = Integer.parseInt(val);
										annexviiaKfintech.setPop_sp_reg_no(pop_sp_reg_no);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 5) {
									try {
										Long nps_acc_number = CommonParser.parseLong(val);
										annexviiaKfintech.setNps_acc_number(nps_acc_number);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										Date date= cell.getDateCellValue();
										annexviiaKfintech.setTransaction_date(date);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										BigDecimal  transaction_amount =  CommonParser.parseBigDecimal(val);
										annexviiaKfintech.setTransaction_amount(transaction_amount);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 8) {
									
									annexviiaKfintech.setTransaction_ref_num(val);
								}
								else if (i == 9) {
									
									annexviiaKfintech.setTransaction_sts(val);
								}
							/*	else if (i == 10) {
									
									annexviiaKfintech.setFiller_ref1(val);
								}
								else if (i == 11) {
									
									annexviiaKfintech.setFiller_ref2(val);
								}
								else if (i == 12) {
									
									annexviiaKfintech.setFiller_ref3(val);
								}
								else if (i == 13) {
									
									annexviiaKfintech.setFiller_ref4(val);
								}*/
								else if (i == 10) {
									
									annexviiaKfintech.setComments(val);
								}
								else if (i == 11) {
									try {
										Date date= cell.getDateCellValue();
										annexviiaKfintech.setReturned_date(date);
									} catch (Exception e) {
										 _log.error(e);
										 _log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 12) {
									
									annexviiaKfintech.setCra(val);
								}
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					annexviiaKfintech.setCreatedate(new Date());
					
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
						//JSONObject annexviiaKfintechJsonObject = JSONFactoryUtil.createJSONObject(annexviiaKfintech.toString());
						//annexviiaKfintechJsonArray.put(annexviiaKfintechJsonObject);
						annexviiaKfintechs.add(annexviiaKfintech);
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
	static Log _log = LogFactoryUtil.getLog(ParseSheetTwo.class);
}

