package com.further.processcra.util;

import com.daily.pfm.service.model.FurtherProcessSix;
import com.daily.pfm.service.service.FurtherProcessSixLocalServiceUtil;
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

public class ParseFurtherSheetSix {
	public static void saveSheetSix(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray furtherProcessSixJsonArray, List<FurtherProcessSix> furtherProcessSixs) {
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(5);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					FurtherProcessSix furtherProcessSix = FurtherProcessSixLocalServiceUtil.createFurtherProcessSix(CounterLocalServiceUtil.increment(FurtherProcessSix.class.getName()));
							
					furtherProcessSix.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 1; i < 11; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 4) {
								if (i == 1) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										furtherProcessSix.setS_no(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 2) {
									furtherProcessSix.setState_name(val);
								}
								else if (i == 3) {
									furtherProcessSix.setNpsmar_xxi(val);
								}
								else if (i == 4) {
									furtherProcessSix.setNpssep_xxi(val);
								}
								else if (i == 5) {
									furtherProcessSix.setCorporatemar_xxi(val);
								} 
								else if (i == 6) {
									furtherProcessSix.setCorporatesep_xxi(val);
								}
								else if (i == 7) {
									furtherProcessSix.setLitemar_xxi(val);
								}
								else if (i == 8) {
									furtherProcessSix.setLitesep_xxi(val);
								}
								else if (i == 9) {
									furtherProcessSix.setApymar_xxi(val);
								}
								else if (i == 10) {
									furtherProcessSix.setApysep_xxi(val);
								}
							}
						}else if(i==1 && rowCount > 4) {
							break rowLoop;
						}
					}
					furtherProcessSix.setCreatedate(new Date());
					
					if (isError && rowCount > 4) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount > 4) {
						JSONObject furtherProcessSixJsonObject = JSONFactoryUtil.createJSONObject(furtherProcessSix.toString());
						furtherProcessSixJsonArray.put(furtherProcessSixJsonObject);
						furtherProcessSixs.add(furtherProcessSix);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}
	static Log _log = LogFactoryUtil.getLog(ParseFurtherSheetSix.class);
}

