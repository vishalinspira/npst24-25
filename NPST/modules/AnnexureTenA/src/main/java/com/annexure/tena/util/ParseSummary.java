
package com.annexure.tena.util;


import com.daily.pfm.service.model.AnnexTenaSummery;
import com.daily.pfm.service.service.AnnexTenaSummeryLocalServiceUtil;
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

public class ParseSummary {

	public static JSONObject saveSheetSummary(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray annextenasummaryArray, List<AnnexTenaSummery> annexTenaSummeryList, JSONObject resultJsonObject,JSONArray  errorSheetNameList,long reportUploadLogId, String cra) {
		
		AnnexTenaSummeryLocalServiceUtil.deleteAnnexTenaSummeryByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("NPST_Withdrawal_Summary");
				String sheetName = sheet.getSheetName();
				if (sheet == null || !resultJsonObject.getBoolean("status")) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("NPST_Withdrawal_Summary");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
					Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					
					rowLoop:
					while (rows.hasNext()) {
						
						AnnexTenaSummery annextenasummery = AnnexTenaSummeryLocalServiceUtil.createAnnexTenaSummery(CounterLocalServiceUtil.increment(AnnexTenaSummery.class.getName()));
						
						annextenasummery.setReportUploadLogId(reportUploadLogId);
						annextenasummery.setCreatedby(userId);
						annextenasummery.setCra(cra);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false; 
						XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						for (int i = 0; i < 4; i++) {
							XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							if (cell != null) {
	
								DataFormatter formatter = new DataFormatter();
								
								String val = formatter.formatCellValue(cell);
								if (val != null)
									val = val.trim();
								if(rowCount > 1) {
									_log.info(val);
									
										if (i == 0) {
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												try {
													Integer sno=Integer.parseInt(val);
													annextenasummery.setSno(sno);
												} catch (Exception e) {
													 _log.error(e);
													 _log.info("error parsing integer"+val);
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
											//Integer no_of_remittances=Integer.parseInt(val);
											annextenasummery.setParticulars(val);
										}
										else if (i == 2) {
											try {
												BigDecimal  amt =  CommonParser.parseBigDecimal(val);
												annextenasummery.setAmt(amt);
											} catch (Exception e) {
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 3) {
											try {
												//val = val.replaceAll("//", "-");
												//DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
												Date date= cell.getDateCellValue();
												annextenasummery.setMonth(date);
											} catch (Exception e) {
												 _log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
									}
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						annextenasummery.setCreatedate(new Date());
							
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
							//JSONObject annexTenaIVJsonObject = JSONFactoryUtil.createJSONObject(annextenasummery.toString());
							//annextenasummaryArray.put(annexTenaIVJsonObject);
							annexTenaSummeryList.add(annextenasummery);
							resultJsonObject.put("status", true);
						}
						rowCount++;
					}
					_log.info(sheetName +" rowcount"+rowCount);
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
	static Log _log = LogFactoryUtil.getLog(ParseSummary.class);
	}