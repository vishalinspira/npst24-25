package com.tds.report.pfm.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.math.BigDecimal;
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

import compliance.service.model.MnPFsDeducteeDetail;
import compliance.service.service.MnPFsDeducteeDetailLocalServiceUtil;
import nps.common.service.util.CommonParser;

public class DeducteeDetails {
	
private static Log _log = LogFactoryUtil.getLog(DeducteeDetails.class);
	
	public static JSONObject saveDeducteeDetails(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray deducteeDetailsJsonArray, List<MnPFsDeducteeDetail> deducteeDetailsList, DecimalFormat decimalFormat,long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("Inside saveDeducteeDetails");
		
		MnPFsDeducteeDetailLocalServiceUtil.deleteMnPFsDeducteeDetailByReportUploadLogId(reportUploadLogId);
		String sheetName="Pfs_Deductee_Detail";
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
					MnPFsDeducteeDetail deducteeDetails = MnPFsDeducteeDetailLocalServiceUtil.
							createMnPFsDeducteeDetail(CounterLocalServiceUtil.increment(MnPFsDeducteeDetail.class.getName()));
				
					deducteeDetails.setReportUploadLogId(reportUploadLogId);
					deducteeDetails.setCreatedby(userId);
					
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
								_log.info("Val: " + val);
								if (i == 0) {
									if (val != null) {
										deducteeDetails.setPension_fund_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									try {
										Date date_1 = cell.getDateCellValue();
										deducteeDetails.setDate_(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									deducteeDetails.setDeductee_identf_no(val);
									//deducteeDetails.setChallan_serial_no(bg.stripTrailingZeros());
								} 
								else if (i == 3) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										deducteeDetails.setDeductee_code_414(bg);
									//deducteeDetails.setSection_code(val);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									deducteeDetails.setPan_of_deductee_415(val);
									//deducteeDetails.setType_of_rent(val);
								} 
								else if (i == 5) {
									deducteeDetails.setDeductee_refno_invalid_pan_413(val);
								}
								else if (i == 6) {
									deducteeDetails.setName_of_deductee_416(val);
								}
								else if (i == 7) {
									try {
										Date date_2 = cell.getDateCellValue();
										deducteeDetails.setAmt_paid_or_credited_date_418(date_2);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									try {
										Date date_3 = cell.getDateCellValue();
										deducteeDetails.setTax_deducted_date_422(date_3);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										deducteeDetails.setAmt_of_payment_419(bg.stripTrailingZeros());
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
										deducteeDetails.setTax_deduction_rate_423(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										deducteeDetails.setAmount_of_tax_deducted(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 12) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										deducteeDetails.setTotal_tax_deposited_421(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 13) {
									deducteeDetails.setReason_for_non_deduction_424(val);
								} 
								else if (i == 14) {
									deducteeDetails.setNon_deduct_certno_issue_ao_425(val);
								} 
								else if (i == 15) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										deducteeDetails.setDeductee_flat_no(bg);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 16) {
									deducteeDetails.setDeductee_building_name(val);
								} 
								else if (i == 17) {
									deducteeDetails.setDeductee_street_name(val);
								} 
								else if (i == 18) {
									deducteeDetails.setDeductee_area(val);
								} 
								else if (i == 19) {
									deducteeDetails.setDeductee_city_or_town(val);
								} 
								else if (i == 20) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										deducteeDetails.setDeductee_pin(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 21) {
									deducteeDetails.setDeductee_state(val);
								} 
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					deducteeDetails.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(deducteeDetails.toString());
							deducteeDetailsJsonArray.put(jsonObject);
							deducteeDetailsList.add(deducteeDetails);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}catch (Exception e) {
				_log.info(e);
				resultJsonObject.put("status", false);
				resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				return resultJsonObject;
			}
		return resultJsonObject;
	}

}
