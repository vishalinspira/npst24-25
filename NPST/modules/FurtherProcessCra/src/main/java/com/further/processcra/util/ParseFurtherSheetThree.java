package com.further.processcra.util;

import com.daily.pfm.service.model.FurtherProcessThree;
import com.daily.pfm.service.service.FurtherProcessThreeLocalServiceUtil;
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

public class ParseFurtherSheetThree {
	
	
	public static void saveSheetThree(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray furtherProcessThreeJsonArray, List<FurtherProcessThree> furtherProcessThrees) {
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
					
					FurtherProcessThree furtherProcessThree = FurtherProcessThreeLocalServiceUtil.createFurtherProcessThree(CounterLocalServiceUtil.increment(FurtherProcessThree.class.getName()));
							
					furtherProcessThree.setCreatedby(userId);
					
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
							if (val != null)
								val = val.trim();
							if(rowCount > 4) {
								_log.info("rowCount"+rowCount);
								_log.info("val"+val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										furtherProcessThree.setS_no(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									furtherProcessThree.setSchemes(val);
								}
								else if (i == 2) {
									furtherProcessThree.setEquitymar_xxi(val);
								}
								else if (i == 3) {
									furtherProcessThree.setEquitysep_xxi(val);
								}
								
								else if (i == 4) {
									furtherProcessThree.setCorporatemar_xxi(val);
								} 
								else if (i == 5) {
									furtherProcessThree.setCorporatesep_xxi(val);
								}
								else if (i == 6) {
									furtherProcessThree.setGovernmentmar_xxi(val);
								}
								
								else if (i == 7) {
									furtherProcessThree.setGovernmentsep_xxi(val);
								} 
								else if (i == 8) {
									furtherProcessThree.setFundmar_xxi(val);
								}
								else if (i == 9) {
									furtherProcessThree.setFundsep_xxi(val);
								}
								
								else if (i == 10) {
									furtherProcessThree.setOthermar_xxi(val);
								}
								else if (i == 11) {
									furtherProcessThree.setOthersep_xxi(val);
								}
							}
						}else if(i==0 && rowCount > 4) {
							break rowLoop;
						}
					}
					furtherProcessThree.setCreatedate(new Date());
					
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
						JSONObject furtherProcessThreeJsonObject = JSONFactoryUtil.createJSONObject(furtherProcessThree.toString());
						furtherProcessThreeJsonArray.put(furtherProcessThreeJsonObject);
						furtherProcessThrees.add(furtherProcessThree);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
		
		
	}
	static Log _log =LogFactoryUtil.getLog(ParseFurtherSheetThree.class);
	
	
}



