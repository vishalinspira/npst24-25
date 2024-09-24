package Monthly_Custodian_TDS_Report.util;

import com.daily.average.service.model.MnCustTdsChallanDetail;
import com.daily.average.service.service.MnCustTdsChallanDetailLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.math.BigDecimal;
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

public class Form1 {
	
private static Log _log = LogFactoryUtil.getLog(Form1.class);
	
	public static JSONObject saveForm1(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form1JsonArray, List<MnCustTdsChallanDetail> form1List, DecimalFormat decimalFormat, Long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("Inside saveForm1");
		
		MnCustTdsChallanDetailLocalServiceUtil.deleteMnCustTdsChallanDetailByReportUploadLogId(reportUploadLogId);
		String sheetName="Challan Detail";
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
					MnCustTdsChallanDetail form1 = MnCustTdsChallanDetailLocalServiceUtil.createMnCustTdsChallanDetail(CounterLocalServiceUtil.increment(MnCustTdsChallanDetail.class.getName()));
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
										form1.setPension_fund_name(val);;
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
										form1.setDate(date_1);
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
										form1.setChallan_serial_no_401(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form1.setIncome_tax_402(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form1.setInterest_403(bg.stripTrailingZeros());
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
										form1.setFees_404(bg.stripTrailingZeros());
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
										form1.setOthers_or_penalty_405(bg.stripTrailingZeros());
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
										form1.setTax_deposited_or_book_adj_406(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									//_log.info("cell2" + cell.getCellType());
									form1.setDeposited_by_book_adj_407(val);
								}
								else if (i == 9) {
									form1.setBsr_code_408(val);
								}
								else if (i == 10) {
									try {
										Date date_1 = cell.getDateCellValue();
										form1.setTax_dep_or_transfer_dt_410(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 11) {
									form1.setChallan_or_ddo_of_form_24g_409(val);
								}
								else if (i == 12) {
									form1.setReceipt_no_of_form_24g_408(val);
								}
								else if (i == 13) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);
										form1.setMinor_head_of_challan_411(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 14) {
									form1.setCheque_or_dd_no(val);
								}
								else if (i == 15) {
									form1.setSection_code(val);
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
							form1List.add(form1);
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
