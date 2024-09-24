package com.annexure.ten.monthly.util;

import com.daily.average.service.model.Annexureiva;
import com.daily.average.service.service.AnnexureivaLocalServiceUtil;
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

public class ParseAnnexFourA {
	public static JSONObject saveSheetFive(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray annexurexivJsonArray, List<Annexureiva> annexureivas, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId) {
		
		AnnexureivaLocalServiceUtil.deleteAnnexureivaByReportUploadLogId(reportUploadLogId);
		String sheetName = "Annexure_4A";
		try {
			if (file != null) {
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("Annexure_4A");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				rowLoop:
				while (rows.hasNext()) {
					
					Annexureiva annexureiva = AnnexureivaLocalServiceUtil.createAnnexureiva(CounterLocalServiceUtil.increment(Annexureiva.class.getName()));
					
					annexureiva.setReportUploadLogId(reportUploadLogId);
					annexureiva.setCreatedby(userId);
					annexureiva.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 12; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							_log.info("Val ParseAnnexFourA  "+val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0 && Validator.isNumber(val)) {
										try {
											Integer sno=Integer.parseInt(val);
											annexureiva.setSno(sno);
										} catch (Exception e) {
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
									
									try {
										Long pao_reg_no=Long.parseLong(val);
										annexureiva.setPao_reg_no(pao_reg_no);
									} catch (Exception e) {
										_log.error(e);
									}
								}
								else if (i == 2) {
									
									try {
										Long tran_id=Long.parseLong(val);
										annexureiva.setTran_id(tran_id);
									} catch (Exception e) {
										_log.error(e);
									}
								} 
								else if (i == 3) {
									
									try {
										BigDecimal  amt = CommonParser.parseBigDecimal(val);
										annexureiva.setAmt(amt);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									
									annexureiva.setFile_ref_no(val);
								} 
								else if (i == 5) {
									
									try {
										Integer record_no=Integer.parseInt(val);
										annexureiva.setRecord_no(record_no);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									
									try {
										annexureiva.setReceipt_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									
									try {
										annexureiva.setFund_realised_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									
									try {
										annexureiva.setFrc_upload_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									annexureiva.setTrust_bank_tat(val);
								} 
								else if (i == 10) {
									annexureiva.setMatching_type(val);
								}
								else if (i == 11) {
									
									try {
										Integer days=Integer.parseInt(val);
										annexureiva.setDays(days);
									} catch (Exception e) {
										_log.error(e);
									}
								} 
								
							}
						}else if (i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					annexureiva.setCreatedate(new Date());
						
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
							JSONObject annexureivaJsonObject = JSONFactoryUtil.createJSONObject(annexureiva.toString());
							annexurexivJsonArray.put(annexureivaJsonObject);
							annexureivas.add(annexureiva);
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
	static Log _log = LogFactoryUtil.getLog(ParseAnnexFourA.class);
	}