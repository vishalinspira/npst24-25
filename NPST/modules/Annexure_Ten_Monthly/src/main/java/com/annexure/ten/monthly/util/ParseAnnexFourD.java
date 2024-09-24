package com.annexure.ten.monthly.util;

import com.daily.average.service.model.Annexureivd;
import com.daily.average.service.service.AnnexureivdLocalServiceUtil;
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

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ParseAnnexFourD {
	public static JSONObject saveSheetEight(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray annexureivdJsonArray, List<Annexureivd> annexureivds, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId) {
		String sheetName = "Annexure_4D";
		AnnexureivdLocalServiceUtil.deleteAnnexureivdByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("Annexure_4D");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				rowLoop:
				while (rows.hasNext()) {
					
					Annexureivd annexureivd = AnnexureivdLocalServiceUtil.createAnnexureivd(CounterLocalServiceUtil.increment(Annexureivd.class.getName()));
					
					annexureivd.setReportUploadLogId(reportUploadLogId);
					annexureivd.setCreatedby(userId);
					annexureivd.setCra(cra);
					
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
							_log.info("Val ParseAnnexFourD  "+val);
							
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										try {
											Integer sno=Integer.parseInt(val);
											annexureivd.setSno(sno);
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
										Long std_pran=Long.parseLong(val);
										annexureivd.setStd_pran(std_pran);
									} catch (Exception e) {
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 2) {
									annexureivd.setType(val);
								} 
								else if (i == 3) {
									annexureivd.setSubscriber_name(val);
								} 
								else if (i == 4) {
									annexureivd.setBeneficiary_name(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal  net_amt = CommonParser.parseBigDecimal(val);
										annexureivd.setNet_amt(net_amt);
									} catch (Exception e) {
										 _log.info("error parsing big dec"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 6) {
									
									try {
										annexureivd.setPay_in_date(cell.getDateCellValue());
									} catch (Exception e) {
										 _log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										annexureivd.setExecution_date(cell.getDateCellValue());
									} catch (Exception e) {
										 _log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 8) {
									annexureivd.setUtr_no(val);
								} 
								else if (i == 9) {
									annexureivd.setInitial_status(val);
								} 
								else if (i == 10) {
									annexureivd.setReturn_reason(val);
								}
								else if (i == 11) {
									
									annexureivd.setRemarks(val);
								}
								else if (i == 12) {
									annexureivd.setDelay(val);
								} 
							}
						}else if (i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					annexureivd.setCreatedate(new Date());
						
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
						JSONObject annexureivdJsonObject = JSONFactoryUtil.createJSONObject(annexureivd.toString());
						annexureivdJsonArray.put(annexureivdJsonObject);
						annexureivds.add(annexureivd);
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
	static Log _log = LogFactoryUtil.getLog(ParseAnnexFourD.class);
}
