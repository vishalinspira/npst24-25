package com.monthly.pfmForm.util;

import com.daily.pfm.service.model.OverallStatusOfStateGovt;
import com.daily.pfm.service.service.OverallStatusOfStateGovtLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.math.BigDecimal;
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
//import org.osgi.service.component.annotations.Reference;


import nps.common.service.util.CommonParser;



public class OverallStatusStateGovernment {

	private static Log _log = LogFactoryUtil.getLog(OverallStatusStateGovernment.class);
	
	public static JSONObject saveStateGovernmentStatus(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,
			JSONArray form1JsonArray, List<OverallStatusOfStateGovt> form1List, DecimalFormat decimalFormat, long reportUploadLogId,String cra) {
		_log.info("saving it's data to DB");
		String sheetName = "Overall Status of State Govt";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", Boolean.TRUE);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					_log.info("Inside while "+rowCount+"th time....");
					OverallStatusOfStateGovt stateGovernmentStatus = OverallStatusOfStateGovtLocalServiceUtil.createOverallStatusOfStateGovt(CounterLocalServiceUtil.increment(OverallStatusOfStateGovt.class.getName()));
					
					stateGovernmentStatus.setReportUploadLogId(reportUploadLogId);
					stateGovernmentStatus.setCreatedby(userId);
					stateGovernmentStatus.setCra(cra);
					
					
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
						_log.info("Val: " + val);
						_log.info("cell--->"+cell);
						 if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								
								if (i == 0) {
										try {
											_log.info("as on date--->"+val);
											Date date = cell.getDateCellValue();
											_log.info("as on date--->"+date);
											stateGovernmentStatus.setAs_on_date(date);
										} catch (Exception e) {
											_log.info("error parsing date---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
								}
								else if (i == 1) {
									String state = cell.getStringCellValue();
									_log.info("state--->"+state);
									stateGovernmentStatus.setState_govt(state);				
								}
								else if (i == 2) {
									try {
										Date date = cell.getDateCellValue();
										_log.info("date of notification---->"+date);
										stateGovernmentStatus.setDate_of_notification(date);
									} catch (Exception e) {
										_log.info("error parsing date---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										Date date = cell.getDateCellValue();
										_log.info("date of adoption---->"+date);
										stateGovernmentStatus.setDate_of_adoption(date);
									} catch (Exception e) {
										_log.info("error parsing date---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										int total_no_of_subscribers = Integer.parseInt(val);
										_log.info("total no of subscribers--->"+total_no_of_subscribers);
										stateGovernmentStatus.setTotal_no_of_subscriber(total_no_of_subscribers);
									} catch (Exception e) {

										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									//_log.info("cell2" + cell.getCellType());
									try {
										int noOfNonIraSubscribers = Integer.parseInt(val);
										_log.info("no of non ira subscribers--->"+noOfNonIraSubscribers);
										stateGovernmentStatus.setNo_of_non_ira_subscribers(noOfNonIraSubscribers);
									} catch (Exception e) {
										 _log.error(e);
										 resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										_log.info("registration during this week---->"+bg);
										stateGovernmentStatus.setReg_during_this_week(bg);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										_log.info("total contribution---->"+bg);
										stateGovernmentStatus.setTotal_contribution(bg);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									//_log.info("cell2" + cell.getCellType());
									try {					
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										_log.info("contribution m and b--->"+bg);
										stateGovernmentStatus.setContribution_m_b(bg);								
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 9) {
									//_log.info("cell2" + cell.getCellType());
									try {					
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										_log.info("change in contribution--->"+bg);
										stateGovernmentStatus.setChange_contrib_m_b_during_week(bg);							
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 10) {
									//_log.info("cell2" + cell.getCellType());
									try {					
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										_log.info("aum---->"+bg);
										stateGovernmentStatus.setAum(bg);								
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
							  
								
							}
						} else if(i == 0 && rowCount > 1) {
							_log.info("breaking the loop......");
							break rowloop;
						}
					}
					stateGovernmentStatus.setCreatedon(new Date());
						
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(stateGovernmentStatus.toString());
							//form1JsonArray.put(jsonObject);
							_log.info("state government status--->"+resultJsonObject.get("status"));
							form1List.add(stateGovernmentStatus);			
							_log.info("matter to be add to the list--->"+form1List);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
		
		return resultJsonObject;
	}
	
	/*
	 * @Reference private OverallStatusOfStateGovt _overallStatusStateGovernment;
	 */
}
