package com.annexure.tena.util;


import com.daily.pfm.service.model.AnnexTena;
import com.daily.pfm.service.service.AnnexTenaLocalServiceUtil;
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

public class ParseTenOne {
	public static JSONObject saveSheetOne(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray annexTenaJsonArray, List<AnnexTena> annexTenas, JSONObject resultJsonObject,JSONArray  errorSheetNameList) {
		/*try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("");
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("Returned_Remittances");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					AnnexTena annexTena = AnnexTenaLocalServiceUtil.createAnnexTena(CounterLocalServiceUtil.increment(AnnexTena.class.getName()));
							
					annexTena.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false; 
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 3; i++) {
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
										Integer sno=Integer.parseInt(val);
										annexTena.setSno(sno);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									
									annexTena.setParticulars(val);
								}
								else if (i == 2) {
									BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
									annexTena.setAmt(amt);
								} 
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					annexTena.setCreatedate(new Date());
						
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
						JSONObject annexTenaJsonObject = JSONFactoryUtil.createJSONObject(annexTena.toString());
						annexTenaJsonArray.put(annexTenaJsonObject);
						annexTenas.add(annexTena);
					}
					rowCount++;
				}
				}
			}
		}catch (Exception e) {
			 _log.error(e);
		}*/
		return resultJsonObject;
	}
	static Log _log = LogFactoryUtil.getLog(ParseTenOne.class);
}
