package com.valuationreports.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.math.BigDecimal;
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

import compliance.service.model.valuationCg;
import compliance.service.service.valuationCgLocalServiceUtil;

public class ValuationRepCG {
	
	private static Log _log = LogFactoryUtil.getLog(ValuationRepCG.class);
	
	
	public static void saveValuationCG(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray cgJsonArray, List<valuationCg> cgList, DecimalFormat decimalFormat,long reportUploadLogId) {
		
		_log.info("Inside saveValuationCG");
		
		valuationCgLocalServiceUtil.deletevaluationCgByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("valuation_cg");
				//XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					valuationCg valuation = valuationCgLocalServiceUtil.createvaluationCg(CounterLocalServiceUtil.increment(valuationCg.class.getName()));
					
					valuation.setReportUploadLogId(reportUploadLogId);
					valuation.setCreatedby(userId);
					
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
										valuation.setClient_code(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									valuation.setIsin(val);
								}
								else if (i == 2) {
									valuation.setIsin_desc(val);
								} 
								else if (i == 3) {
									try {
										Date date_1 = new SimpleDateFormat("dd-MMM-yy").parse(val);
										valuation.setMaturity_date(date_1);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 4) {
									try {
										long lValue = Long.parseLong(val);
										valuation.setFree_securities_rs(lValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 5) {
									try {
										long lValue = Long.parseLong(val);
										valuation.setPledge_value_held_rs(lValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 6) {
									try {
										long lValue = Long.parseLong(val);
										valuation.setSecurities_in_transit_rs(lValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 7) {
									try {
										long lValue = Long.parseLong(val);
										valuation.setTotal_face_value_held_rs(lValue);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
                                 else if (i == 8) {
                                	 try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										 valuation.setRate(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 9) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										valuation.setValue_(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					valuation.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(valuation.toString());
							cgJsonArray.put(jsonObject);
							cgList.add(valuation);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
	}

}
