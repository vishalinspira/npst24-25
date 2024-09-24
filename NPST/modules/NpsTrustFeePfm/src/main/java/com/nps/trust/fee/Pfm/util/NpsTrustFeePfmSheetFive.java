package com.nps.trust.fee.Pfm.util;

/*import com.daily.average.service.model.NpsTrustFeePfmFive;
import com.daily.average.service.service.NpsTrustFeePfmFiveLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/

public class NpsTrustFeePfmSheetFive {
	/*public static void saveSheetFive(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,List<NpsTrustFeePfmFive> npsTrustFeePfmFives, JSONArray npsTrustFeePfmFiveJsonArray, DecimalFormat decimalFormat) {
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
					
					NpsTrustFeePfmFive npsTrustFeePfmFive = NpsTrustFeePfmFiveLocalServiceUtil.createNpsTrustFeePfmFive(CounterLocalServiceUtil.increment(NpsTrustFeePfmFive.class.getName()));
							
					npsTrustFeePfmFive.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 2; i < 5; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 9) {
								if (i == 2) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										DateFormat dateFormat= new SimpleDateFormat("M/D/yyyy");
										npsTrustFeePfmFive.setDatene(dateFormat.parse(val));
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 3) {
									BigDecimal  totalaum =  (BigDecimal) decimalFormat.parse(val);
									npsTrustFeePfmFive.setTotalaum(totalaum);
								}
								else if (i == 4) {
									BigDecimal  npstrustfee =  (BigDecimal) decimalFormat.parse(val);
									npsTrustFeePfmFive.setNpstrustfee(npstrustfee);
								}
								
							}
						}else if(i==0 && rowCount > 9) {
							break rowLoop;
						}
					}
					npsTrustFeePfmFive.setCreatedate(new Date());
					
					if (isError && rowCount > 9) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount > 9) {
						JSONObject npsTrustFeePfmFiveJsonObject = JSONFactoryUtil.createJSONObject(npsTrustFeePfmFive.toString());
						npsTrustFeePfmFiveJsonArray.put(npsTrustFeePfmFiveJsonObject);
						npsTrustFeePfmFives.add(npsTrustFeePfmFive);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}*/
}
