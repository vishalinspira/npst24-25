package Monthly_Custodian_TDS_Report.util;

import com.daily.average.service.model.MnCustTdsNonsalary;
import com.daily.average.service.service.MnCustTdsNonsalaryLocalServiceUtil;
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

import nps.common.service.util.CommonParser;

public class Form2 {
	
private static Log _log = LogFactoryUtil.getLog(Form2.class);
	
	public static JSONObject saveForm2(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form2JsonArray, List<MnCustTdsNonsalary> form2List, DecimalFormat decimalFormat, Long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("Inside saveForm2");
		
		MnCustTdsNonsalaryLocalServiceUtil.deleteMnCustTdsNonsalaryByReportUploadLogId(reportUploadLogId);
		String sheetName="Deductee Detail";
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
					//_log.info("Inside while");
					MnCustTdsNonsalary form2 = MnCustTdsNonsalaryLocalServiceUtil.createMnCustTdsNonsalary(CounterLocalServiceUtil.increment(MnCustTdsNonsalary.class.getName()));
					form2.setCreatedby(userId);
					form2.setReportUploadLogId(reportUploadLogId);
					
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
										form2.setPension_fund_name(val);
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
										form2.setDate(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setChallan_serial_no(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									form2.setSection_code(val);
								} 
								else if (i == 4) {
									form2.setType_of_rent(val);
								} 
								else if (i == 5) {
									form2.setDeductee_identf_no(val);
								}
								else if (i == 6) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setDeductee_code_414(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									form2.setPan_of_deductee_415(val);
								}
								else if (i == 8) {
									//_log.info("cell2" + cell.getCellType());
									form2.setDeductee_refno_invalid_pan_413(val);
								}
								else if (i == 9) {
									form2.setName_of_deductee_416(val);
								}
								else if (i == 10) {
									try {
										Date date_1 = cell.getDateCellValue();
										form2.setAmt_paid_or_credited_date_418(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 11) {
									try {
										Date date_1 = cell.getDateCellValue();
										form2.setTax_deducted_date_422(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 12) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setAmt_of_payment_419(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 13) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setTax_deduction_rate_423(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 14) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setAmount_of_tax_deducted(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 15) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setTotal_tax_deposited_421(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 16) {
									form2.setReason_for_non_deduction_424(val);
								}
								else if (i == 17) {
									form2.setNon_deduct_certno_issue_ao_425(val);
								}
								else if (i == 18) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setDeductee_flat_no(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 19) {
									form2.setDeductee_building_name(val);
								}
								else if (i == 20) {
									form2.setDeductee_street_name(val);
								}
								else if (i == 21) {
									form2.setDeductee_area(val);
								}
								else if (i == 22) {
									form2.setDeductee_city_or_town(val);
								}
								else if (i == 23) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setDeductee_pin(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 24) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form2.setDeductee_state(bg.stripTrailingZeros());
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
					form2.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form2.toString());
							form2JsonArray.put(jsonObject);
							form2List.add(form2);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
			}catch (Exception e) {
				 _log.error(e);
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
					return resultJsonObject;
			}
		return resultJsonObject;
	}
	

}
