package Annexure_IV_PFM.utils;

import com.daily.average.service.model.QrAnnexureFourCustodian;
import com.daily.average.service.service.QrAnnexureFourCustodianLocalServiceUtil;
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
	
	public static JSONObject saveForm1(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray form1JsonArray, List<QrAnnexureFourCustodian> form1List, DecimalFormat decimalFormat, long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("Inside saveForm1");
		String sheetName="Custodian Charges by PFM";
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
					QrAnnexureFourCustodian form1 = QrAnnexureFourCustodianLocalServiceUtil.createQrAnnexureFourCustodian(CounterLocalServiceUtil.increment(QrAnnexureFourCustodian.class.getName()));
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
								boolean isValid = (val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("- 0"));
								
								if (i == 0) {
									if (val != null) {
										form1.setScheme_name(val);
									}
								}
								else if (i == 1) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val); //(isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setTransaction_charges_dem_trades(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val); // (isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setCustody_charges_dema_holdings(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setNsccl_ccil_charges(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setSebi_charges(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								
								else if (i == 5) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);//(isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setGross_bill_as_per_pf(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setBill_value_excluding_gst(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);//(isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setCgst(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 8) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setSgst(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 9) {
									try {
										BigDecimal bg = CommonParser.parseBigDecimal(val);// (isValid==true) ? BigDecimal.ZERO : (BigDecimal) decimalFormat.parse(val);
										form1.setBill_value_including_gst(bg.stripTrailingZeros());
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 10) {
									try {
										Date date_1 = cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
										form1.setMonth_(date_1);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									BigDecimal bg =CommonParser.parseBigDecimal(val);//(BigDecimal) decimalFormat.parse(val);
									form1.setAccount_number(bg.stripTrailingZeros());
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(form1.toString());
							//form1JsonArray.put(jsonObject);
							QrAnnexureFourCustodianLocalServiceUtil.addQrAnnexureFourCustodian(form1);
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
			}
		return resultJsonObject;
	}

}
