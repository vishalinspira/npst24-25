package com.further.processcra.util;


import com.daily.pfm.service.model.FurtherProcessTwo;
import com.daily.pfm.service.service.FurtherProcessTwoLocalServiceUtil;
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

public class ParseFurtherSheetTwo {
	public static void saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray furtherProcessTwoJsonArray, List<FurtherProcessTwo> furtherProcessTwos) {
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(1);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					FurtherProcessTwo furtherProcessTwo = FurtherProcessTwoLocalServiceUtil.createFurtherProcessTwo(CounterLocalServiceUtil.increment(FurtherProcessTwo.class.getName()));
							
					furtherProcessTwo.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 2; i < 7; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 5) {
								if (i == 2) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										furtherProcessTwo.setSubscriber_class(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 3) {
									furtherProcessTwo.setMar_xi(val);
								}
								else if (i == 4) {
									furtherProcessTwo.setMar_xx(val);
								}
								else if (i == 5) {
									furtherProcessTwo.setMar_xxi(val);
								}
								
								else if (i == 6) {
									furtherProcessTwo.setSep_xxi(val);
								} 
							}
						}else if(i==2 && rowCount > 5) {
							break rowLoop;
						}
					}
					furtherProcessTwo.setCreatedate(new Date());
					
					if (isError && rowCount > 5) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount > 5) {
						JSONObject furtherProcessTwoJsonObject = JSONFactoryUtil.createJSONObject(furtherProcessTwo.toString());
						furtherProcessTwoJsonArray.put(furtherProcessTwoJsonObject);
						furtherProcessTwos.add(furtherProcessTwo);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}
	static Log _log = LogFactoryUtil.getLog(ParseFurtherSheetTwo.class);
}

