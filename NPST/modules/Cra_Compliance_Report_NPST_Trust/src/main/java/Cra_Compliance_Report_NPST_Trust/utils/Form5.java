package Cra_Compliance_Report_NPST_Trust.utils;

import com.daily.average.service.model.MnReportWthdrwlAgingAnalys;
import com.daily.average.service.service.MnReportWthdrwlAgingAnalysLocalServiceUtil;
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

public class Form5 {
	
private static Log _log = LogFactoryUtil.getLog(Form5.class);
	
	public static JSONObject saveForm5C_1(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form5C_1JsonArray, List<MnReportWthdrwlAgingAnalys> form5C_1List, DecimalFormat decimalFormat, long reportUploadLogId) {
		_log.info("Inside saveForm5C_1");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Reported Wthdrwl Aging Analysis");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					MnReportWthdrwlAgingAnalys form5 = MnReportWthdrwlAgingAnalysLocalServiceUtil.
							createMnReportWthdrwlAgingAnalys(CounterLocalServiceUtil.increment(MnReportWthdrwlAgingAnalys.class.getName()));
					
					form5.setCreatedby(userId);
					form5.setReportUploadLogId(reportUploadLogId);
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
											form5.setDate_(date);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
											 //form5.setDate_(cell.getDateCellValue());
										}
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									form5.setCra(val);
								}
								else if (i == 2) {
									form5.setWithdrawal_sector(val);
								}
								else if (i == 3) {
									form5.setType_(val);
								}
								else if (i == 4) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form5.setZero_to_therty_days(bg);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form5.setThertyone_to_ninty_days(bg);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 6) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form5.setNintyone_to_onetwenty_days(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 7) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form5.setOnetwentyone_to_oneeighty_days(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form5.setOneeighty_to_three65_days(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 9) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form5.setMore_than_one_year(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 10) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form5.setWthdrwl_outstand_ated_of_month(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					form5.setCreatedon(new Date());
						
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form5.toString());
							//form5C_1JsonArray.put(jsonObject);
							form5C_1List.add(form5);
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
