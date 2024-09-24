package com.further.processcra.util;

import com.daily.pfm.service.model.FurtherProcessFive;
import com.daily.pfm.service.service.FurtherProcessFiveLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
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

public class ParseFurtherSheetFive {
	public static void saveSheetFive(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray furtherProcessFiveJsonArray, List<FurtherProcessFive> furtherProcessFives) {
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(4);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					FurtherProcessFive furtherProcessFive = FurtherProcessFiveLocalServiceUtil.createFurtherProcessFive(CounterLocalServiceUtil.increment(FurtherProcessFive.class.getName()));
							
					furtherProcessFive.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 11; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 3) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										furtherProcessFive.setS_no(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									furtherProcessFive.setState_uts(val);
								}
								else if (i == 2) {
									furtherProcessFive.setNpsmar_xxi(val);
								}
								else if (i == 3) {
									furtherProcessFive.setNpssep_xxi(val);
								}
								else if (i == 4) {
									furtherProcessFive.setCorporatemar_xxi(val);
								} 
								else if (i == 5) {
									furtherProcessFive.setCorporatesep_xxi(val);
								}
								else if (i == 6) {
									furtherProcessFive.setLitemar_xxi(val);
								}
								
								else if (i == 7) {
									furtherProcessFive.setLitesep_xxi(val);
								}
								else if (i == 8) {
									furtherProcessFive.setApymar_xxi(val);
								}
								else if (i == 9) {
									furtherProcessFive.setApysep_xxi(val);
								}
							}
						}else if(i==0 && rowCount > 3) {
							break rowLoop;
						}
					}
					furtherProcessFive.setCreatedate(new Date());
					
					if (isError && rowCount > 3) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount > 3) {
						JSONObject furtherProcessFiveJsonObject = JSONFactoryUtil.createJSONObject(furtherProcessFive.toString());
						furtherProcessFiveJsonArray.put(furtherProcessFiveJsonObject);
						furtherProcessFives.add(furtherProcessFive);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}
	static Log _log = LogFactoryUtil.getLog(ParseFurtherSheetFive.class);
}

