package com.monthly.pfmForm.util;


import com.daily.pfm.service.model.OverallStatusOfStateGovt;
import com.daily.pfm.service.model.OverallStatusSectorWise;
import com.daily.pfm.service.service.OverallStatusSectorWiseLocalServiceUtil;
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

public class OverallStatusOfSector {

private static Log _log = LogFactoryUtil.getLog(OverallStatusOfSector.class);
	
	public static JSONObject saveSectorStatus(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, 
			boolean isexcelhaserror, JSONArray form1JsonArray, List<OverallStatusSectorWise> form1List, DecimalFormat decimalFormat, 
			long reportUploadLogId,String cra) {
		_log.info("saving it's data to DB");
		String sheetName = "Overall Status Sector wise";
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
					//_log.info("Inside while");
					OverallStatusSectorWise statusSectorWise = OverallStatusSectorWiseLocalServiceUtil.createOverallStatusSectorWise(CounterLocalServiceUtil.increment(OverallStatusOfSector.class.getName()));
					
					statusSectorWise.setReportUploadLogId(reportUploadLogId);
					statusSectorWise.setCreatedby(userId);
					statusSectorWise.setCra(cra);
					
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
						if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								
								if (i == 0) {
										try {
											Date date = cell.getDateCellValue();
											
											statusSectorWise.setAs_on_date(date);
										} catch (Exception e) {
											_log.info("error parsing date---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
								}
								else if (i == 1) {
									String sector = cell.getStringCellValue();
									statusSectorWise.setSector(sector);				
								}
								else if (i == 2) {
									try {
										int noOfSubscribers = (int)cell.getNumericCellValue();
										statusSectorWise.setNumber_of_subscriber(noOfSubscribers);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
										try {
											int subscriberRegDuringWeek = (int)cell.getNumericCellValue();
											statusSectorWise.setSubscriber_reg_during_week(subscriberRegDuringWeek);
										} catch (Exception e) {
											_log.info("error parsing big decimal---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}		
								} 
								else if (i == 4) {
									try {
										BigDecimal contributionM_and_B = CommonParser.parseBigDecimal(val);
										statusSectorWise.setContributionm_and_b(contributionM_and_B);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									//_log.info("cell2" + cell.getCellType());
									try {
										BigDecimal changeInContributionDuringWeek = CommonParser.parseBigDecimal(val);
										statusSectorWise.setChange_in_contrib_during_week(changeInContributionDuringWeek);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal pendingForM_and_B = CommonParser.parseBigDecimal(val);
										statusSectorWise.setPending_for_m_and_b(pendingForM_and_B);
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
										statusSectorWise.setTotal(bg);
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
										statusSectorWise.setNet_amount_invested(bg);								
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
										statusSectorWise.setTotal_assets_under_mgmt(bg);							
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
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
					statusSectorWise.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(statusSectorWise.toString());
							form1JsonArray.put(jsonObject);
							form1List.add(statusSectorWise);
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
