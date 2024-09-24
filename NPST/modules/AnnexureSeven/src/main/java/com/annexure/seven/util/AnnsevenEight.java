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

import compliance.service.model.ChequeClearance;
import compliance.service.service.ChequeClearanceLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class AnnsevenEight {
	public static JSONObject saveSheetEigth(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray chequeclearanceJsonArray, List<ChequeClearance> chequeclearance, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject, long reportUploadLogId) {
		
		ChequeClearanceLocalServiceUtil.deleteChequeClearanceByReportUploadLogId(reportUploadLogId);
		String  sheetName="Chq_Clearance_TAT";
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
					
					ChequeClearance chequeClearance = ChequeClearanceLocalServiceUtil.
							createChequeClearance(CounterLocalServiceUtil.increment(ChequeClearance.class.getName()));
							
					chequeClearance.setReportUploadLogId(reportUploadLogId);
					chequeClearance.setCreatedby(userId);
					chequeClearance.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					int lastColumn = Math.max(row.getLastCellNum(), 0);
					
					for (int i = 0; i < lastColumn; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										//Long srno=Long.parseLong(val);
										if(val.equalsIgnoreCase("nil") || val.equalsIgnoreCase("na")) {
											break rowloop;
										}
										else {
											try {
												int iValue = Integer.parseInt(val);
												chequeClearance.setEasypay_tran_id(iValue);
											} catch (Exception e) {
												_log.info("error parsing int"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											
											isError = true;
										}
								}
								else if (i == 1) {
									try {
										int iValue = Integer.parseInt(val);
										chequeClearance.setSol_id(iValue);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									chequeClearance.setAxis_bank_branch(val);
								} 
								else if (i == 3) {
									try {
										long lValue = Long.parseLong(val);
										chequeClearance.setCra_tran_id(lValue);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									chequeClearance.setBeneficiary_name(val);
								} 
								else if (i == 5) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										chequeClearance.setClearance_amt(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing BigDecimal"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										int iValue = Integer.parseInt(val);
										chequeClearance.setChq_no(iValue);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										//String str1 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
										Date date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(str1);
										chequeClearance.setReceipt_date(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									try {
										//String str2 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
										Date date_2 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(str2);
										chequeClearance.setClearance_date(date_2);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									chequeClearance.setClearance_tat(val);
								} 
								else if (i == 10) {
									try {
										int iValue = Integer.parseInt(val);
										chequeClearance.setDelay_period_above_5_days(iValue);
									} catch (Exception e) {
										_log.info("error parsing int"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									chequeClearance.setReason(val);
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					chequeClearance.setCreatedon(new Date());
						
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
							//JSONObject chequeClearanceJsonObject = JSONFactoryUtil.createJSONObject(chequeClearance.toString());
							//chequeclearanceJsonArray.put(chequeClearanceJsonObject);
							chequeclearance.add(chequeClearance);
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
	static Log _log = LogFactoryUtil.getLog(AnnsevenEight.class);
}
