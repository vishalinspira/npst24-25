package com.monthly.pfmForm.util;

import com.daily.pfm.service.model.StatusOfNPSLite;
import com.daily.pfm.service.service.StatusOfNPSLiteLocalServiceUtil;
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

public class StatusNPSLite {

	private static Log _log = LogFactoryUtil.getLog(StatusNPSLite.class);
	
	public static JSONObject saveNPSLiteStatus(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx,
			boolean isexcelhaserror, JSONArray form1JsonArray, List<StatusOfNPSLite> form1List, DecimalFormat decimalFormat,
			long reportUploadLogId, String cra) {
		
		_log.info("saving it's data to DB");
		String sheetName = "Status of NPS Lite";
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
					StatusOfNPSLite npsLiteStatus = StatusOfNPSLiteLocalServiceUtil.createStatusOfNPSLite(CounterLocalServiceUtil.increment(StatusNPSLite.class.getName()));
					
					npsLiteStatus.setReportUploadLogId(reportUploadLogId);
					npsLiteStatus.setCreatedby(userId);
					npsLiteStatus.setCra(cra);
					
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
											Date asOnDate = cell.getDateCellValue();
											npsLiteStatus.setAs_on_date(asOnDate);
										} catch (Exception e) {
											_log.info("error parsing date---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
								}
								else if (i == 1) {
									try {
										int totalNumberOfAggregators = Integer.parseInt(val);
										npsLiteStatus.setTotal_number_of_aggregators(totalNumberOfAggregators);
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									try {
										int totalNoOfSubscribersReg = Integer.parseInt(val);
										npsLiteStatus.setTotal_no_of_subscribers_reg(totalNoOfSubscribersReg);
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										BigDecimal totalContributionAmount = CommonParser.parseBigDecimal(val);
										npsLiteStatus.setTotal_contribution_amount(totalContributionAmount);
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
										npsLiteStatus.setContrib_amt_matched_booked(contribAmtMatchedAndBooked);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									try {
										BigDecimal amtPendMatchingAndBooking = CommonParser.parseBigDecimal(val);
										npsLiteStatus.setAmt_pend_matching_booking(amtPendMatchingAndBooking);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal totalAUM = CommonParser.parseBigDecimal(val);
										npsLiteStatus.setTotal_aum(totalAUM);
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
					npsLiteStatus.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(npsLiteStatus.toString());
							form1JsonArray.put(jsonObject);
							form1List.add(npsLiteStatus);
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
