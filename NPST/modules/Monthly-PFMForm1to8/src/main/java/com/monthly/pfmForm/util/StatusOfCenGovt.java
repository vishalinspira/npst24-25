package com.monthly.pfmForm.util;

import com.daily.pfm.service.model.OverallStatusOfStateGovt;
import com.daily.pfm.service.model.StatusOfCentralGovernment;
import com.daily.pfm.service.service.OverallStatusOfStateGovtLocalServiceUtil;
import com.daily.pfm.service.service.StatusOfCentralGovernmentLocalServiceUtil;
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

import nps.common.service.util.CommonParser;

public class StatusOfCenGovt {

	private static Log _log = LogFactoryUtil.getLog(StatusOfCenGovt.class);
	
	public static JSONObject saveCentralGovrnmentStatus(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx,
			boolean isexcelhaserror, JSONArray form1JsonArray, List<StatusOfCentralGovernment> form1List, DecimalFormat decimalFormat,
			long reportUploadLogId, String cra) {
		
		_log.info("saving it's data to DB");
		String sheetName = "Status of Central Government";
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
					_log.info("Inside while "+rowCount+"th time");
					StatusOfCentralGovernment centralGovernmentStatus = StatusOfCentralGovernmentLocalServiceUtil.createStatusOfCentralGovernment(CounterLocalServiceUtil.increment(StatusOfCenGovt.class.getName()));
					
					centralGovernmentStatus.setReportUploadLogId(reportUploadLogId);
					centralGovernmentStatus.setCreatedby(userId);
					centralGovernmentStatus.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					int lastColumn = Math.max(row.getLastCellNum(), 0);
					
					for (int i = 0; i < lastColumn; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						DataFormatter formatter = new DataFormatter();
						_log.info("cell---->"+cell);
						String val = formatter.formatCellValue(cell);
						_log.info("val---->"+val);
						if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								_log.info("Val has a value");
								_log.info("cell has a value");
								if (i == 0) {
										try {
											Date date = cell.getDateCellValue();
											centralGovernmentStatus.setAs_on_date(date);
										} catch (Exception e) {
											_log.info("error parsing date---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
								}
								else if (i == 1) {
									String accountingFormation = cell.getStringCellValue();
									centralGovernmentStatus.setAccounting_formation(accountingFormation);				
								}
								else if (i == 2) {
									try {
										int changeSubsDuringThePastWeek = Integer.parseInt(val);
										centralGovernmentStatus.setChange_subs_during_past_week(changeSubsDuringThePastWeek);
									} catch (Exception e) {
										_log.info("error parsing date---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										int totalNoOfSubscribers = Integer.parseInt(val);
										centralGovernmentStatus.setTotal_no_of_subscribers(totalNoOfSubscribers);
									} catch (Exception e) {
										_log.info("error parsing date---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										//int noOfNonIRACompliantSubs = Integer.parseInt(val);
										int noOfNonIRACompliantSubs = Integer.parseInt(val);
										centralGovernmentStatus.setNo_of_non_ira_compliant_subs(noOfNonIRACompliantSubs);
									} catch (Exception e) {
										_log.info("error parsing date---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									//_log.info("cell2" + cell.getCellType());
									try {
										int nonIRASubsFlaggedInSystem = Integer.parseInt(val);
										centralGovernmentStatus.setNon_ira_subs_flagged_in_system(nonIRASubsFlaggedInSystem);
									} catch (Exception e) {
										_log.info("error parsing date---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal contributionAmtForSCFsMB = CommonParser.parseBigDecimal(val);
										centralGovernmentStatus.setContribution_amt_for_scfs_m_b(contributionAmtForSCFsMB);
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
										centralGovernmentStatus.setContrib_amt_for_scfs_pend_m_b(bg);
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
										centralGovernmentStatus.setAum(bg);								
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
							  
								
							}
						} else if(i == 0 && rowCount > 1) {
							_log.info("data has been taken....");
							break rowloop;
						}
					}
					centralGovernmentStatus.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(centralGovernmentStatus.toString());
							form1JsonArray.put(jsonObject);
							form1List.add(centralGovernmentStatus);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
		
		return resultJsonObject;
		
	}
}
