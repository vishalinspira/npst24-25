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

import compliance.service.model.MnEntityWiseOutAnalysis;
import compliance.service.service.MnEntityWiseOutAnalysisLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class EntityWiseOutAnalysis {
	
private static Log _log = LogFactoryUtil.getLog(EntityWiseAgeing.class);
	
	public static JSONObject saveEntityWiseOutAnalysis(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, DecimalFormat decimalFormat, JSONArray entityWiseOutAnalysisJsonArray, List<MnEntityWiseOutAnalysis> entityWiseOutAnalysisList, String cra, long reportUploadLogId) {
		_log.info("Inside saveEntityWiseOutAnalysis");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("EntityWise Outstanding Analysis");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					MnEntityWiseOutAnalysis entityWiseOutAnalysis = MnEntityWiseOutAnalysisLocalServiceUtil.
							createMnEntityWiseOutAnalysis(CounterLocalServiceUtil.increment(MnEntityWiseOutAnalysis.class.getName()));
					entityWiseOutAnalysis.setCra(cra);
					entityWiseOutAnalysis.setCreatedby(userId);
					
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
										entityWiseOutAnalysis.setEntities(val);
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
										//entityWiseOutAnalysis.setMonth_(val);
										entityWiseOutAnalysis.setMonth_(monthDate);
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
										entityWiseOutAnalysis.setOutstanding_opening_balance(iValue);
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
										entityWiseOutAnalysis.setEscalated_to_npst(iValue);
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
										entityWiseOutAnalysis.setReferrals_recv_during_month(iValue);
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
										entityWiseOutAnalysis.setReviewed_and_assigned_by_npst(iValue);
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
										entityWiseOutAnalysis.setReferrals_resol_during_month(iValue);
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
										entityWiseOutAnalysis.setOutstanding_at_end_of_month(iValue);
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
					entityWiseOutAnalysis.setCreatedon(new Date());
					entityWiseOutAnalysis.setReportUploadLogId(reportUploadLogId);	
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(entityWiseOutAnalysis.toString());
							//entityWiseOutAnalysisJsonArray.put(jsonObject);
							entityWiseOutAnalysisList.add(entityWiseOutAnalysis);
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
