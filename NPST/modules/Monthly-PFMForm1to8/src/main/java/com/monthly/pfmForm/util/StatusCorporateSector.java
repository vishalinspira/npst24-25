package com.monthly.pfmForm.util;

import com.daily.pfm.service.model.StatusOfCorporateSector;
import com.daily.pfm.service.service.StatusOfCorporateSectorLocalServiceUtil;
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

public class StatusCorporateSector {

private static Log _log = LogFactoryUtil.getLog(StatusCorporateSector.class);
	
	public static JSONObject saveCorporateStatus(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, 
			boolean isexcelhaserror, JSONArray form1JsonArray, List<StatusOfCorporateSector> form1List, DecimalFormat decimalFormat, 
			long reportUploadLogId, String cra) {
		
		_log.info("saving it's data to DB");
		String sheetName = "Status of Corporate sector";
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
					StatusOfCorporateSector corporateSectorStatus = StatusOfCorporateSectorLocalServiceUtil.createStatusOfCorporateSector(CounterLocalServiceUtil.increment(StatusCorporateSector.class.getName()));
					
					corporateSectorStatus.setReportUploadLogId(reportUploadLogId);
					corporateSectorStatus.setCreatedby(userId);
					corporateSectorStatus.setCra(cra);
					
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
										int totalNoOfSubRegCorpSector = (int)cell.getNumericCellValue();
										corporateSectorStatus.setTot_no_of_sub_reg_corp_sector(totalNoOfSubRegCorpSector);
								}
								else if (i == 1) {
									try {
										BigDecimal totalContribSCFUploaded = CommonParser.parseBigDecimal(val);
										corporateSectorStatus.setTotal_contrib_scf_uploaded(totalContribSCFUploaded);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}				
								}
								else if (i == 2) {
									try {
										BigDecimal contribAmtMatchedBooked = CommonParser.parseBigDecimal(val);
										corporateSectorStatus.setContrib_amt_matched_booked(contribAmtMatchedBooked);								
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										BigDecimal totalAmtPendMatchingBooking = CommonParser.parseBigDecimal(val);
										corporateSectorStatus.setTot_amt_pend_matching_booking(totalAmtPendMatchingBooking);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										BigDecimal totalAUM = CommonParser.parseBigDecimal(val);
										corporateSectorStatus.setTotal_aum(totalAUM);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									//_log.info("cell2" + cell.getCellType());
									int noOfCorpContribRemitted = (int)cell.getNumericCellValue();
									corporateSectorStatus.setNo_of_corp_contrib_remitted(noOfCorpContribRemitted);
								}							  
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					corporateSectorStatus.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(corporateSectorStatus.toString());
							form1JsonArray.put(jsonObject);
							form1List.add(corporateSectorStatus);
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
