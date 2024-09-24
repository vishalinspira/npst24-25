package com.monthly.mis.report.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.text.DecimalFormat;
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

import compliance.service.model.MnReferralsHandledAtNps;
import compliance.service.service.MnReferralsHandledAtNpsLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class ReferralsHandledAtNps {
	
	private static Log _log = LogFactoryUtil.getLog(ReferralsHandledAtNps.class);
	
	public static JSONObject saveReferralsHandledAtNps(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray referralsHandledAtNpsJsonArray, List<MnReferralsHandledAtNps> referralsHandledAtNpsList, String cra, long reportUploadLogId) {
		_log.info("Inside saveReferralsHandledAtNps");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Referral Handled at NPS");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					MnReferralsHandledAtNps referralsHandledAtNps = MnReferralsHandledAtNpsLocalServiceUtil.
							createMnReferralsHandledAtNps(CounterLocalServiceUtil.increment(MnReferralsHandledAtNps.class.getName()));
					referralsHandledAtNps.setCra(cra);
					referralsHandledAtNps.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					int lastColumn = Math.max(row.getLastCellNum(), 0);
					
					for (int i = 0; i < lastColumn; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								_log.debug("Val: " + val);
								if (i == 0) {
									if (val != null) {
										referralsHandledAtNps.setReferrals(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									
									try {
										Date monthDate = cell.getDateCellValue();
										//referralsHandledAtNps.setMonth_(val);
										referralsHandledAtNps.setMonth_(monthDate);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									try {
										int iValue = Integer.parseInt(val);
										referralsHandledAtNps.setOutstanding_opening_balance(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										int iValue = Integer.parseInt(val);
										referralsHandledAtNps.setEscalated_to_npst(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										int iValue = Integer.parseInt(val);
										referralsHandledAtNps.setReferrals_recv_during_month(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 5) {
									try {
										int iValue = Integer.parseInt(val);
										referralsHandledAtNps.setReviewed_and_assigned_by_npst(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										int iValue = Integer.parseInt(val);
										referralsHandledAtNps.setReferrals_resol_during_month(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										int iValue = Integer.parseInt(val);
										referralsHandledAtNps.setOutstanding_at_end_of_month(iValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										 _log.error(e);
										 _log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					referralsHandledAtNps.setCreatedon(new Date());
					referralsHandledAtNps.setReportUploadLogId(reportUploadLogId);
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(referralsHandledAtNps.toString());
							//referralsHandledAtNpsJsonArray.put(jsonObject);
							referralsHandledAtNpsList.add(referralsHandledAtNps);
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
