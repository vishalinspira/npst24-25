package com.annexure.ten.monthly.util;

import com.daily.average.service.model.Annexureivb;
import com.daily.average.service.service.AnnexureivbLocalServiceUtil;
import com.daily.average.service.service.AnnexureivcLocalServiceUtil;
import com.daily.average.service.service.DailyAverageLocalServiceUtil;
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

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ParseAnnexFourB {
	public static JSONObject saveSheetSix(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,JSONArray annexureivbJsonArray, List<Annexureivb> annexureivbs, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId) {
		String sheetName = "Annexure_4B";
		AnnexureivcLocalServiceUtil.deleteAnnexureivcByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("Annexure_4B");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					Annexureivb annexureivb = AnnexureivbLocalServiceUtil.createAnnexureivb(CounterLocalServiceUtil.increment(Annexureivb.class.getName()));
					
					annexureivb.setReportUploadLogId(reportUploadLogId);
					annexureivb.setCreatedby(userId);
					annexureivb.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 15; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							
							_log.info("Val ParseAnnexFourb  "+val);
							
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										try {
											Integer sno=Integer.parseInt(val);
											annexureivb.setSno(sno);
										} catch (Exception e) {
											_log.info("error parsing int"+val);
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
									
									try {
										Long payment_id=Long.parseLong(val);
										annexureivb.setPayment_id(payment_id);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									
									try {
										Long source_acc_no_nodal=Long.parseLong(val);
										annexureivb.setSource_acc_no_nodal(source_acc_no_nodal);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									
									annexureivb.setIfsc_source_no(val);
								} 
								else if (i == 4) {
									try {
										annexureivb.setPayment_receipt_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									
									annexureivb.setUtr_no(val);
								}
								else if (i == 6) {
									annexureivb.setMode(val);
								}
								else if (i == 7) {
									
									try {
										BigDecimal  utr_amount =  CommonParser.parseBigDecimal(val);
										annexureivb.setUtr_amount(utr_amount);
									} catch (Exception e) {
										_log.info("error parsing big dec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									
									try {
										Long nps_acc_number=Long.parseLong(val);
										annexureivb.setNps_acc_number(nps_acc_number);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									Integer nps_virtual_acc_no=Integer.parseInt(val);
									annexureivb.setNps_virtual_acc_no(nps_virtual_acc_no);
								} 
								else if (i == 10) {
									
									try {
										annexureivb.setReturn_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 11) {
									
									annexureivb.setReturned_utr(val);
								}
								else if (i == 12) {
									Long tid=Long.parseLong(val);
									annexureivb.setTid(tid);
								} 
								else if (i == 13) {
									annexureivb.setReturn_reason(val);
								} 
								else if (i == 14) {
									annexureivb.setDelay_reason(val);
								} 
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					annexureivb.setCreatedate(new Date());
					
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
						JSONObject annexureivbJsonObject = JSONFactoryUtil.createJSONObject(annexureivb.toString());
						annexureivbJsonArray.put(annexureivbJsonObject);
						annexureivbs.add(annexureivb);
					}
					rowCount++;
				}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}
		}catch (Exception e) {
			_log.error(e);
		}
		return resultJsonObject;
	}
	static Log _log = LogFactoryUtil.getLog(ParseAnnexFourB.class);
}