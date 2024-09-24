package Cra_Compliance_Report_NPST_Trust.utils;

import com.daily.average.service.model.MnWidrawalRelatedSubsGriev;
import com.daily.average.service.service.MnWidrawalRelatedSubsGrievLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;


public class Form4 {
	
private static Log _log = LogFactoryUtil.getLog(Form4.class);
	
	public static JSONObject saveForm4Atal(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form4AtalJsonArray, List<MnWidrawalRelatedSubsGriev> form4AtalList, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm4Atal");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Withdrawal Related Subs Griev");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					MnWidrawalRelatedSubsGriev form4 = MnWidrawalRelatedSubsGrievLocalServiceUtil.
							createMnWidrawalRelatedSubsGriev(CounterLocalServiceUtil.increment(MnWidrawalRelatedSubsGriev.class.getName()));
					
					form4.setCreatedby(userId);
					form4.setReportUploadLogId(reportUploadLogId);
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
										try {
											Date date= cell.getDateCellValue();
											form4.setDate_(date);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
											 //form4.setDate_(cell.getDateCellValue());
										}
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									form4.setCra(val);
								}
								else if (i == 2) {
									form4.setGrievance_statistics_for_cgms(val);
								}
								else if (i == 3) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form4.setGrievances_count(bg);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 4) {
									form4.setRemarks(val);
								} 
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form4.setCreatedon(new Date());
						
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form4.toString());
							//form4AtalJsonArray.put(jsonObject);
							form4AtalList.add(form4);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
				 resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
				 return resultJsonObject;
			}
			return resultJsonObject;
	}
	
}
	
	
	
	