package com.monthly.pfmForm.util;

import com.daily.pfm.service.model.StatusOfAtalPensionScheme;
import com.daily.pfm.service.service.StatusOfAtalPensionSchemeLocalServiceUtil;
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

public class StatusAtalPensionScheme {
	
private static Log _log = LogFactoryUtil.getLog(StatusAtalPensionScheme.class);
	
	public static JSONObject saveAtalPensionSchemeSubscriberStatus(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, 
			XSSFSheet xx, boolean isexcelhaserror, JSONArray form1JsonArray, List<StatusOfAtalPensionScheme> form1List, 
			DecimalFormat decimalFormat, long reportUploadLogId, String cra) {
		_log.info("saving it's data to DB");
		String sheetName = "Status of Atal Pension Scheme";
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
					StatusOfAtalPensionScheme atalPensionScheme = StatusOfAtalPensionSchemeLocalServiceUtil.createStatusOfAtalPensionScheme(CounterLocalServiceUtil.increment(StatusAtalPensionScheme.class.getName()));
					
					atalPensionScheme.setReportUploadLogId(reportUploadLogId);
					atalPensionScheme.setCreatedby(userId);
					atalPensionScheme.setCra(cra);
					
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
								//_log.info("Val: " + val);
								if (i == 0) {
										try {
											Date date = cell.getDateCellValue();
											atalPensionScheme.setAs_on_date(date);
										} catch (Exception e) {
											_log.info("error parsing date---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
								}
								else if (i == 1) {
									try {
										int totalNoOfBanksRegUnderAPY = Integer.parseInt(val);
										atalPensionScheme.setTot_no_of_banks_reg_under_apy(totalNoOfBanksRegUnderAPY);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}				
								}
								else if (i == 2) {
									try {
										int totalNumOfSubsReg = Integer.parseInt(val);
										atalPensionScheme.setTotal_num_of_subs_reg(totalNumOfSubsReg);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										BigDecimal totalContributionAmount = CommonParser.parseBigDecimal(val);
										atalPensionScheme.setTotal_contribution_amount(totalContributionAmount);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										BigDecimal contribAmtMatchedAndBooked = CommonParser.parseBigDecimal(val);
										atalPensionScheme.setContrib_amt_matched_booked(contribAmtMatchedAndBooked);
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
										BigDecimal amtPendMatchingAndBooking = CommonParser.parseBigDecimal(val);
										atalPensionScheme.setAmt_pend_matching_booking(amtPendMatchingAndBooking);	
									} catch (Exception e) {
										_log.info("error parsing total ammount of subscribers contri M&B---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal totalAUM = CommonParser.parseBigDecimal(val);
										atalPensionScheme.setTotal_aum(totalAUM);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}							  
								
							}
						} else if(i == 0 && rowCount > 1) {
							_log.info("took all the data");
							break rowloop;
						}
					}
					atalPensionScheme.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(atalPensionScheme.toString());
							form1JsonArray.put(jsonObject);
							form1List.add(atalPensionScheme);
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
