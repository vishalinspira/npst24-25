package com.investment.management.util;

import com.daily.average.service.model.ManagementFee;
import com.daily.average.service.service.ManagementFeeLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class ManagementFeeUtil {
	public static JSONObject saveManagementFeeSheet(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray managementFeeJsonArray, List<ManagementFee> managementFees, DecimalFormat decimalFormat, Long reportUploadLogId, JSONObject resultJsonObject) {
		
		ManagementFeeLocalServiceUtil.deleteManagementFeeByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Management_Fee");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				rowLoop:
				while (rows.hasNext()) {
					
					ManagementFee managementFee = ManagementFeeLocalServiceUtil.createManagementFee(CounterLocalServiceUtil.increment());
							
					managementFee.setCreatedate(new Date());
					managementFee.setCreatedby(userId);
					managementFee.setReportUploadLogId(reportUploadLogId);
					
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 14; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							_log.debug("Management_Fee  -----------------"+val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								try {
									if (i == 0) {
										if ( Validator.isNotNull(val) ) {
											//Long sno=Long.parseLong(val);
											managementFee.setPension_fund_name(val);
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											
											isError = true;
										}
									}
									else if (i == 1) {
										
										managementFee.setScheme_name(val);
									}
									else if (i == 2) {
										
										managementFee.setMonth(val);
									}
									else if (i == 3) {
										
										try {
											managementFee.setDate_(cell.getDateCellValue());
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 4) {
										BigDecimal  bd =  CommonParser.parseBigDecimal(val);
										managementFee.setTotal_mgmt_fees(bd);
										
									}
									else if (i == 5) {
										BigDecimal  mutual_funds_except_liquid_mf =  CommonParser.parseBigDecimal(val);
										managementFee.setMutual_funds_except_liquid_mf(mutual_funds_except_liquid_mf);
										
									} 
									else if (i == 6) {
										BigDecimal  exchange_traded_funds =  CommonParser.parseBigDecimal(val);
										managementFee.setExchange_traded_funds(exchange_traded_funds);
									} 
									else if (i == 7) {
										BigDecimal  index_funds =  CommonParser.parseBigDecimal(val);
										managementFee.setIndex_funds(index_funds);
									} 
									else if (i == 8) {
										BigDecimal  aum_excl_investments =  CommonParser.parseBigDecimal(val);
										managementFee.setAum_excl_investments(aum_excl_investments);
									}
									else if (i == 9) {
										BigDecimal  total_aum =  CommonParser.parseBigDecimal(val);
										managementFee.setTotal_aum(total_aum);
									}
									else if (i == 10) {
										BigDecimal  mgmt_fees_percentage =  CommonParser.parseBigDecimal(val);
										managementFee.setMgmt_fees_percentage(mgmt_fees_percentage);
										
									}
									else if (i == 11) {
										BigDecimal  gst =  CommonParser.parseBigDecimal(val);
										managementFee.setGst(gst);
																			
									} 
									else if (i == 12) {
										BigDecimal  total_management_fees =  CommonParser.parseBigDecimal(val);
										managementFee.setTotal_management_fees(total_management_fees);	
										
									}
									else if (i == 13) {
										managementFee.setManagement_rate(val);
									}
									
								} catch (Exception e) {
									_log.info("error parsing long"+val);
									resultJsonObject.put("status", false);
									resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
									return resultJsonObject;
								} 
								
							}
						}else if (i==0 && rowCount > 1) {
							break rowLoop;
						}
					}						
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
							//JSONObject managementFeeJsonObject = JSONFactoryUtil.createJSONObject(managementFee.toString());
							//managementFeeJsonArray.put(managementFeeJsonObject);
							managementFees.add(managementFee);
						}
						rowCount++;
					}
					
				}
			}catch (Exception e) {
				 _log.error(e);
				resultJsonObject.put("status", false);
				resultJsonObject.put("msg", CommonParser.fileExceptionMsg +"Management_Fee");
				return resultJsonObject;
			}
		return resultJsonObject;
	}
	static Log _log = LogFactoryUtil.getLog(ManagementFeeUtil.class);
}