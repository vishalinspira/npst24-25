package com.taggedby.nps.util;


import com.daily.average.service.model.TaggedbyNPSOne;
import com.daily.average.service.service.TaggedbyNPSOneLocalServiceUtil;
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

public class ParseSheetOne {
	static Log _log = LogFactoryUtil.getLog(ParseSheetOne.class);
	public static void saveSheetOne(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray taggedbyNPSOneJsonArray, List<TaggedbyNPSOne> taggedbynpsones) {
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
					
					TaggedbyNPSOne taggedbyNPSOne = TaggedbyNPSOneLocalServiceUtil.createTaggedbyNPSOne(CounterLocalServiceUtil.increment(TaggedbyNPSOne.class.getName()));
							
					taggedbyNPSOne.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 14; i++) {
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
										taggedbyNPSOne.setToken_number(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									taggedbyNPSOne.setPran(val);
								} 
								else if (i == 2) {
									taggedbyNPSOne.setStatusi(val);
								}
								else if (i == 3) {
									taggedbyNPSOne.setLogging_date(val);
								}
								else if (i == 4) {
									taggedbyNPSOne.setReisedby(val);
								} 
								else if (i == 5) {
									taggedbyNPSOne.setPao_reg_no(val);
								} 	
								else if (i == 6) {
									taggedbyNPSOne.setPao_name(val);
								}
								else if (i == 7) {
									taggedbyNPSOne.setPr_ao_no(val);
								} 
								else if (i == 8) {
									taggedbyNPSOne.setPr_ao_name(val);
								}
								else if (i == 9) {
									taggedbyNPSOne.setMinistry_name(val);
								}
								else if (i == 10) {
									taggedbyNPSOne.setSectori(val);
								}
								else if (i == 11) {
									taggedbyNPSOne.setState_name(val);
								}
								else if (i == 12) {
									taggedbyNPSOne.setCategory(val);
								}
								else if (i == 13) {
									taggedbyNPSOne.setText_(val);
								} 
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					taggedbyNPSOne.setCreatedate(new Date());
					
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
						JSONObject taggedbyNPSOneJsonObject = JSONFactoryUtil.createJSONObject(taggedbyNPSOne.toString());
						taggedbyNPSOneJsonArray.put(taggedbyNPSOneJsonObject);
						taggedbynpsones.add(taggedbyNPSOne);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}
}

