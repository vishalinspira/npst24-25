package com.monthly.pfm.util;

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

import compliance.service.model.MnForm3;
import compliance.service.service.MnForm3LocalServiceUtil;
import nps.common.service.util.CommonParser;

public class Form3 {
	
private static Log _log = LogFactoryUtil.getLog(Form3.class);
	
	public static JSONObject saveForm3(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form3JsonArray, List<MnForm3> form3List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm3");
		MnForm3LocalServiceUtil.deleteMnForm3ByReportUploadLogId(reportUploadLogId);
		String sheetName="Form 2A";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
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
					MnForm3 form3 = MnForm3LocalServiceUtil.
							createMnForm3(CounterLocalServiceUtil.increment(MnForm3.class.getName()));
					
					form3.setReportUploadLogId(reportUploadLogId);
					form3.setCreatedby(userId);
					
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
									if (val != null) {
										form3.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										Date date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form3.setReporting_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									form3.setParticulars(val);
								} 
								else if (i == 3) {
									//_log.info("cell2" + cell.getCellType());
									String s = String.valueOf(cell.getNumericCellValue());
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(s);
									form3.setScheme_cg(s);
								} 
								else if (i == 4) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_sg(val);
								} 
								else if (i == 5) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setNps_lite(val);
								}
								else if (i == 6) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setCorporate_cg_scheme(val);
								}
								else if (i == 7) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setApy(val);
								}
								else if (i == 8) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_e_tier_i(val);
								} 
								else if (i == 9) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_c_tier_i(val);
								}
								else if (i == 10) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_g_tier_i(val);
								}
								else if (i == 11) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_e_tier_ii(val);
								}
								else if (i == 12) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_c_tier_ii(val);
								} 
								else if (i == 13) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_g_tier_ii(val);
								}
								else if (i == 14) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_a_tier_i(val);
								}
								else if (i == 15) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setScheme_tax_saver_tier_ii(val);
								} 
								else if (i == 16) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setApy_fund_scheme(val);
								} 
								else if (i == 17) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									form3.setTier2_composite(val);
								} 
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form3.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form3.toString());
							form3JsonArray.put(jsonObject);
							form3List.add(form3);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}catch (Exception e) {
				 _log.error(e);
			}
		return resultJsonObject;
	}

}
