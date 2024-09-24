package com.proxyvotingdashboard.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
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

import compliance.service.model.Votingreport;
import compliance.service.service.VotingreportLocalServiceUtil;

public class IIASVotingReport {
	private static Log _log = LogFactoryUtil.getLog(IIASVotingReport.class);
	
	public  static void saveIIASVotingReport(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray votingJsonArray, List<Votingreport> votingList, DecimalFormat decimalFormat,long reportUploadLogId) {
		_log.info("Inside IIASVotingReport util class");
		
		VotingreportLocalServiceUtil.deleteVotingreportByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("IIAS_Recommendation");
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					Votingreport votingdata=VotingreportLocalServiceUtil.createVotingreport(CounterLocalServiceUtil.increment(IIASVotingReport.class.getName()));
					
					votingdata.setReportUploadLogId(reportUploadLogId);
					votingdata.setCreatedby(userId);
					
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
								_log.info("Val: " + val);
								if (i == 0) {
								
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											//_log.info("cell2 " + cell.getCellType());
											//_log.info("cell " + cell.getStringCellValue());
											try {
												votingdata.setMeeting_date(cell.getDateCellValue());
											} catch (Exception e) {
											}
										}
										 else {
												errorObj.put("cellno", 2);
												errorObj.put("msg", "It is not a number");
												isError = true;
											}
									}
									else if (i == 1) {
										votingdata.setIsin(val);
									}
									else if (i == 2) {
										votingdata.setCompany_name(val);
									} 
									else if (i == 3) {
										votingdata.setMeeting_type(val);
									} 
									else if (i == 4) {
										votingdata.setResolution_category(val);
									} 
									else if (i == 5) {
										votingdata.setResolution_dtls(val);
									}
									else if (i == 6) {
										votingdata.setResolution_type(val);
									}
									else if (i == 7) {
										votingdata.setIias_vote_recommendation(val);
									}
	                                else if (i == 8) {
	                                	votingdata.setIias_rationale_for_vote_recom(val);
									}
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
						votingdata.setCreatedate(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(votingdata.toString());
							votingJsonArray.put(jsonObject);
							votingList.add(votingdata);
						}
						rowCount++;
					}
				}
			
		}catch (Exception e) {
				 _log.error(e);
			}
		
	}
}
