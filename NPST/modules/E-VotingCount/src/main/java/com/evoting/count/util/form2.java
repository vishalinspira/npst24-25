package com.evoting.count.util;

import com.daily.average.service.model.QrPfVotingRecommCount;
import com.daily.average.service.service.QrPfVotingRecommCountLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.ParseException;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class form2 {
	
	private static Log _log = LogFactoryUtil.getLog(form2.class);

	public static JSONObject saveForm2(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx,
			boolean isexcelhaserror, JSONArray form1JsonArray, List<QrPfVotingRecommCount> form2List, DecimalFormat decimalFormat,List<String> errorlist, Long reportUploadLogId) {
		_log.info("Inside saveForm2");
		
		QrPfVotingRecommCountLocalServiceUtil.deleteQrPfVotingRecommCountByReportUploadLogId(reportUploadLogId);
		String sheetName="PF Voting Recomm Count";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				
				if(null != sheet) {
					Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					
					rowloop:
					while (rows.hasNext()) {
						//_log.info("Inside while");
						
						QrPfVotingRecommCount form1 = QrPfVotingRecommCountLocalServiceUtil.createQrPfVotingRecommCount(CounterLocalServiceUtil.increment(QrPfVotingRecommCount.class.getName()));
						
						form1.setCreatedby(userId);
						form1.setReportUploadLogId(reportUploadLogId);
						
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
											form1.setPension_fund_name(val);
										}
										 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											isError = true;
										}
									}
									else if (i == 1) {
										form1.setFinancial_year(val);
										
									}
									else if (i == 2) {
										try {
											Date date_1 = cell.getDateCellValue();
											form1.setQuarter(date_1); //(formatDate(cell.getDateCellValue()));
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									} 
									else if (i == 3) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(String.valueOf(cell.getNumericCellValue()));
											form1.setTotal_number_of_resolution(bg);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									} 
									else if (i == 4) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(String.valueOf(cell.getNumericCellValue()));
											form1.setFor_(bg);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									} 
									else if (i == 5) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(String.valueOf(cell.getNumericCellValue()));
											form1.setAgainst(bg);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									}else if (i == 6) {
										try {
											BigDecimal bg = CommonParser.parseBigDecimal(val);// (BigDecimal) decimalFormat.parse(String.valueOf(cell.getNumericCellValue()));
											form1.setAbstain(bg);
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
						form1.setCreatedon(new Date());
							
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
								JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form1.toString());
								form1JsonArray.put(jsonObject);
								form2List.add(form1);
							}
							rowCount++;
						}
					_log.info(sheetName +" rowcount"+rowCount);
				}else {
					errorlist.add("PF Voting Recomm Count");
				}

				}
			}catch (Exception e) {
				 _log.error(e);
			}
		return resultJsonObject;
	}
	
    private static  Date formatDate(Date dateIn) throws ParseException {
    	Date d1 = null;

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(dateIn);
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            d1 = sdf3.parse(formattedDate);

        } catch (Exception e) {
             _log.error(e);
        }
        return d1;
    }
	
}
