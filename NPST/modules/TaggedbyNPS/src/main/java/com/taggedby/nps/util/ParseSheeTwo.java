package com.taggedby.nps.util;


import com.daily.average.service.model.TaggedbyNPSTwo;
import com.daily.average.service.service.TaggedbyNPSTwoLocalServiceUtil;
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

public class ParseSheeTwo {
	static Log _log = LogFactoryUtil.getLog(ParseSheeTwo.class);
	public static void saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray taggedbyNPSTwoJsonArray, List<TaggedbyNPSTwo> taggedbynpsTwos) {
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(2);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					TaggedbyNPSTwo taggedbyNPSTwo = TaggedbyNPSTwoLocalServiceUtil.createTaggedbyNPSTwo(CounterLocalServiceUtil.increment(TaggedbyNPSTwo.class.getName()));
							
					taggedbyNPSTwo.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 12; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										taggedbyNPSTwo.setToken_number(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									taggedbyNPSTwo.setPran(val);
								} 
								else if (i == 2) {
									taggedbyNPSTwo.setStatusi(val);
								}
								else if (i == 3) {
									taggedbyNPSTwo.setLogging_date(val);
								}
								else if (i == 4) {
									taggedbyNPSTwo.setReisedby(val);
								} 
								else if (i == 5) {
									taggedbyNPSTwo.setNlao_reg_no(val);
								} 	
								else if (i == 6) {
									taggedbyNPSTwo.setNlao_name(val);
								}
								else if (i == 7) {
									taggedbyNPSTwo.setNloo_no(val);
								} 
								else if (i == 8) {
									taggedbyNPSTwo.setNloo_name(val);
								}
								else if (i == 9) {
									taggedbyNPSTwo.setSectori(val);
								}
								else if (i == 10) {
									taggedbyNPSTwo.setCategory(val);
								}
								else if (i == 11) {
									taggedbyNPSTwo.setText_(val);
								} 
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					taggedbyNPSTwo.setCreatedate(new Date());
					
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
						JSONObject taggedbyNPSTwoJsonObject = JSONFactoryUtil.createJSONObject(taggedbyNPSTwo.toString());
						taggedbyNPSTwoJsonArray.put(taggedbyNPSTwoJsonObject);
						taggedbynpsTwos.add(taggedbyNPSTwo);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}
}

