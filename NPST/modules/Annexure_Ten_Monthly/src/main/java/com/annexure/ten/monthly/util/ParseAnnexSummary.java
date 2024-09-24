package com.annexure.ten.monthly.util;


import com.daily.pfm.service.model.ACSummeryX;
import com.daily.pfm.service.service.ACSummeryXLocalServiceUtil;
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

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ParseAnnexSummary {
	public static JSONObject saveSheetSummay(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,JSONArray acSummeryxJsonArray, List<ACSummeryX> acSummeryx, DecimalFormat decimalFormat, String cra, JSONObject resultJsonObject,JSONArray  errorSheetNameList, Long reportUploadLogId ) {
		
		ACSummeryXLocalServiceUtil.deleteACSummeryXByReportUploadLogId(reportUploadLogId);
		String sheetName = "Withdrawal_AC_Summary";
		try {
			if (file != null) {
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					//JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("Withdrawal_AC_Summary");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					ACSummeryX acsummeryx = ACSummeryXLocalServiceUtil.createACSummeryX(CounterLocalServiceUtil.increment(ACSummeryX.class.getName()));
					
					acsummeryx.setReportUploadLogId(reportUploadLogId);
					acsummeryx.setCreatedby(userId);
					acsummeryx.setCra(cra);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false; 
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 13; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 11) {
								_log.info(val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										//Integer no_of_accepted_tran=Integer.parseInt(val);
										//String str1 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
										try {
											acsummeryx.setInstruction_date(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										Long no_of_rejected_tran=Long.parseLong(val);
										acsummeryx.setPran_pao(no_of_rejected_tran);
									} catch (Exception e) {
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								}
								else if (i == 2) {
									
									//Integer total_transactions=Integer.parseInt(val);
									acsummeryx.setSubscriber_name(val);
								} 
								else if (i == 3) {
									//BigDecimal  rejection_prcnt =  (BigDecimal) decimalFormat.parse(val);
									acsummeryx.setPayee_name(val);
								}
								else if (i == 4) {
									acsummeryx.setPayee_bank_ifsc(val);
								} 
								else if (i == 5) {
									try {
										Integer payee_bank_acc_no =Integer.parseInt(val);
										acsummeryx.setPayee_bank_acc_no(payee_bank_acc_no);
									} catch (Exception e) {
										 _log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 6) {
									try {
										BigDecimal  rejection_prcnt =  CommonParser.parseBigDecimal(val);
										acsummeryx.setNet_amt(rejection_prcnt);
									} catch (Exception e) {
										 _log.info("error parsing big dec"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
									}
								} 
								else if (i == 7) {
									try {
										acsummeryx.setPay_in_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 8) {
									try {
										acsummeryx.setExecution_date(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									acsummeryx.setUtr_no(val);
								} 
								else if (i == 10) {
									acsummeryx.setStatus(val);
								} 
								else if (i == 11) {
									//BigDecimal  rejection_prcnt =  (BigDecimal) decimalFormat.parse(val);
									acsummeryx.setReturn_reason(val);
								} 
								else if (i == 12) {
									//BigDecimal  rejection_prcnt =  (BigDecimal) decimalFormat.parse(val);
									acsummeryx.setStandard_reason(val);
								} 
							}
						}else if(i==0 && rowCount > 11) {
							break rowLoop;
						}
					}
					acsummeryx.setCreatedate(new Date());
						
					if (isError && rowCount > 11) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount >11) {
						JSONObject reportiJsonObject = JSONFactoryUtil.createJSONObject(acsummeryx.toString());
						acSummeryxJsonArray.put(reportiJsonObject);
						acSummeryx.add(acsummeryx);
					}
					rowCount++;
				}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}
		}catch (Exception e) {
			_log.error(e);
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
	
	static Log _log = LogFactoryUtil.getLog(ParseAnnexSummary.class);
}
