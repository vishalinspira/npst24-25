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
import java.text.DateFormat;
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

import compliance.service.model.CollectionSummary;
import compliance.service.service.CollectionSummaryLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnSevenSheetZero {
	
	private static Log _log = LogFactoryUtil.getLog(AnSevenSheetZero.class);
	
	public static JSONObject saveSheetZero(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray collectionSummaryJsonArray, DecimalFormat decimalFormat, String cra, List<CollectionSummary> collectionSummary,JSONObject resultJsonObject, long reportUploadLogId) {
		_log.info("Inside save saveSheetZero");
		String sheetName = "Collection_Summary";
		CollectionSummaryLocalServiceUtil.deleteCollectionSummaryByReportUploadLogId(reportUploadLogId);
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
					_log.info("Inside while");
					CollectionSummary collSummary = CollectionSummaryLocalServiceUtil.
							createCollectionSummary(CounterLocalServiceUtil.increment(CollectionSummary.class.getName()));
					
					collSummary.setReportUploadLogId(reportUploadLogId);
					collSummary.setCreatedby(userId);
					collSummary.setCra(cra);
					
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
								//_log.info("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										//Long srno=Long.parseLong(val);
										collSummary.setRemittance_mode(val);
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											
											isError = true;
										}
								}
								else if (i == 1) {
									try {
										long transNo = Long.parseLong(val);
										collSummary.setAuto_match_trans_no(transNo);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									try {
										BigDecimal bg = (BigDecimal)decimalFormat.parse(val);
										collSummary.setAuto_match_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										long transNo = Long.parseLong(val);
										collSummary.setManual_match_trans_no(transNo);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										collSummary.setManual_match_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									_log.info("cell" + cell.getCellType());
									//String value  = String.valueOf(cell.getNumericCellValue());
									//long transNo = Long.parseLong();
									try {
										long transNo = (long) cell.getNumericCellValue();
										collSummary.setTot_accepted_trans_no(transNo);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									_log.info("cell" + cell.getCellType());
									try {
										String value  = String.valueOf(cell.getNumericCellValue());
										BigDecimal bg = CommonParser.parseBigDecimal(value);
										collSummary.setTot_accepted_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										long transNo = Long.parseLong(val);
										collSummary.setTot_rejected_trans_no(transNo);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										collSummary.setTot_rejected_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									_log.info("cell" + cell.getCellType());
									try {
										long transNo = (long) cell.getNumericCellValue();
										collSummary.setGrand_tot_trans_no(transNo);
									} catch (Exception e) {
										_log.error(e);
										
									}
								} 
								else if (i == 10) {
									_log.info("cell" + cell.getCellType());
									try {
										String value  = String.valueOf(cell.getNumericCellValue());
										BigDecimal bg = CommonParser.parseBigDecimal(value);
										collSummary.setGrand_tot_amount(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									_log.info("cell" + cell.getCellType());
									try {
										String value  = String.valueOf(cell.getNumericCellValue());
										double percent = Double.parseDouble(value);
										collSummary.setPercentage_rejection(percent);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 12) {
									_log.info("cell" + cell.getCellType());
									try {
										String value  = String.valueOf(cell.getNumericCellValue());
										double percent = Double.parseDouble(value);
										collSummary.setPercentage_acceptance(percent);
									} catch (Exception e) {
										_log.info("error parsing double"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 13) {
									try {
										try {
											Date date = cell.getDateCellValue();
											collSummary.setMonth(date);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
										_log.info(collSummary);
									} catch (Exception e) {
										_log.error(e);
									}
									
								} 
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					collSummary.setCreatedon(new Date());
						
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
							JSONObject collSummaryJsonObject = JSONFactoryUtil.createJSONObject(collSummary.toString());
							collectionSummaryJsonArray.put(collSummaryJsonObject);
							collectionSummary.add(collSummary);
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
