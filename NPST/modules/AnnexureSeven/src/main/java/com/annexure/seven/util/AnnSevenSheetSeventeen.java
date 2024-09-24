package com.annexure.seven.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.util.Validator;

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

import compliance.service.model.ElectronicRejSummary;
import compliance.service.service.ElectronicRejSummaryLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnnSevenSheetSeventeen {
	
	private static Log _log = LogFactoryUtil.getLog(AnnSevenSheetSeventeen.class);
	
	public static JSONObject saveSheetSeventeen(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray eleRejSumJsonArray, List<ElectronicRejSummary> eleRejSummaryList, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject, long reportUploadLogId) {
		_log.info("saveSheetSeventeen");
		
		ElectronicRejSummaryLocalServiceUtil.deleteElectronicRejSummaryByReportUploadLogId(reportUploadLogId);
		String  sheetName="Electronic_Rejects_Summary";
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					
					ElectronicRejSummary eleRejSummary = ElectronicRejSummaryLocalServiceUtil.
							createElectronicRejSummary(CounterLocalServiceUtil.increment(ElectronicRejSummary.class.getName()));
					
					eleRejSummary.setReportUploadLogId(reportUploadLogId);
					eleRejSummary.setCreatedby(userId);
					eleRejSummary.setCra(cra);
					
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
						
						if (cell != null && !val.equalsIgnoreCase("grand total")) {
							
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								_log.info("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										try {
											Date date_1 = cell.getDateCellValue();
											_log.info("date_1" + date_1);
											eleRejSummary.setPayment_date(date_1);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											
											isError = true;
										}
								}
								else if (i == 1) {
									if(Validator.isNotNull(val)) {
										eleRejSummary.setNps_acc_name(val);
									} else {
										eleRejSummary.setNps_acc_name("NA");
									}
								}
								else if (i == 2) {
									try {
										int payId = Integer.parseInt(val);
										eleRejSummary.setCount_payment_id(payId);
									} catch (Exception e) {
										_log.info("error parsing bigdec"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										BigDecimal bg =  CommonParser.parseBigDecimal(val);
										eleRejSummary.setSum_utr_amt(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing bigdec"+val);
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
					eleRejSummary.setCreatedon(new Date());
						
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
							JSONObject eleRejSumJsonObject = JSONFactoryUtil.createJSONObject(eleRejSummary.toString());
							eleRejSumJsonArray.put(eleRejSumJsonObject);
							eleRejSummaryList.add(eleRejSummary);
						}
						rowCount++;
					}
				}
				
			}catch (Exception e) {
				_log.error(e);
				resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				 return resultJsonObject;
			}
		return resultJsonObject;
	}

	public static String formatDate(Date dateIn, String format) throws ParseException {
        String strDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(dateIn);
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date d1 = sdf3.parse(formattedDate);

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            strDate = formatter.format(d1);
        } catch (Exception e) {
            _log.error(e);
        }
        return strDate;
    }

}
