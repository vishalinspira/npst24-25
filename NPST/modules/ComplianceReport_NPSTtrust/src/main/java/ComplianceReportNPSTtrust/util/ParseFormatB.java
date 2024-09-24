package ComplianceReportNPSTtrust.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

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

import compliance.service.model.FormatB;
import compliance.service.service.FormatBLocalServiceUtil;

public class ParseFormatB {
	static Log _log = LogFactoryUtil.getLog(ParseFormatB.class);
	public static void saveSheetFormatb(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray FormatbJsonArray, List<FormatB> formatbList) {
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
					
				FormatB formatb = FormatBLocalServiceUtil.createFormatB(CounterLocalServiceUtil.increment(FormatB.class.getName()));
							
				formatb.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false; 
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 6; i++) {
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
											formatb.setFormatb_sector(val);
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											
											isError = true;
										}
									}
									
									else if (i == 1) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										formatb.setPartialwithdrawal(val);
									} 
									else if (i == 2) {
										//Integer no_of_remittances=Integer.parseInt(val);
										formatb.setSurrenderofNPSbenefits(val);
									}
									else if (i == 3) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										formatb.setEligiblelumpsumwithdrawal(val);
									} 
									else if (i == 4) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										formatb.setPurchaseofannuity(val);
									} 
									else if (i == 5) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										formatb.setAllcitizenes(val);
									} 
									
								}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					formatb.setCreatedate(new Date());
						
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
						JSONObject annexTenaIVJsonObject = JSONFactoryUtil.createJSONObject(formatb.toString());
						FormatbJsonArray.put(annexTenaIVJsonObject);
						formatbList.add(formatb);
					}
					rowCount++;
				}
				
				}
			}catch (Exception e) {
			 _log.error(e);
			}
		}
	}
