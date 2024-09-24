package com.monthly.pfmForm.util;

import com.daily.pfm.service.model.StatusOfCentralGovernment;
import com.daily.pfm.service.model.StatusOfIndividualSubscriber;
import com.daily.pfm.service.service.StatusOfCentralGovernmentLocalServiceUtil;
import com.daily.pfm.service.service.StatusOfIndividualSubscriberLocalServiceUtil;
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

public class StatusIndividualSubscriber {
	
private static Log _log = LogFactoryUtil.getLog(StatusIndividualSubscriber.class);
	
	public static JSONObject saveIndividualSubscriberStatus(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx,
			boolean isexcelhaserror, JSONArray form1JsonArray, List<StatusOfIndividualSubscriber> form1List, DecimalFormat decimalFormat,
			long reportUploadLogId, String cra) {
		
		_log.info("saving it's data to DB");
		String sheetName = "Status of Individual Subscriber";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", Boolean.TRUE);
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
					_log.info("Inside while "+rowCount+"th time");
					StatusOfIndividualSubscriber individualSubscriberStatus = StatusOfIndividualSubscriberLocalServiceUtil.createStatusOfIndividualSubscriber(CounterLocalServiceUtil.increment(StatusIndividualSubscriber.class.getName()));
					
					individualSubscriberStatus.setReportUploadLogId(reportUploadLogId);
					individualSubscriberStatus.setCreatedby(userId);
					individualSubscriberStatus.setCra(cra);
					
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
										try {
											Date date = cell.getDateCellValue();
											individualSubscriberStatus.setAs_on_date(date);
										} catch (Exception e) {
											_log.info("error parsing date---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
								}
								else if (i == 1) {
									try {
										int totalNoOfRegPoP_SPsInCRA = Integer.parseInt(val);
										individualSubscriberStatus.setTot_no_of_reg_pop_sps_in_cra(totalNoOfRegPoP_SPsInCRA);
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
												
								}
								else if (i == 2) {
									try {
										int totalNoOfRegPopsInCRA = Integer.parseInt(val);
										individualSubscriberStatus.setTot_no_of_reg_pop_sps_in_cra(totalNoOfRegPopsInCRA);
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}	
								} 
								else if (i == 3) {
									try {
										int totalNoOfSubs = Integer.parseInt(val);
										individualSubscriberStatus.setTotal_no_of_subs(totalNoOfSubs);
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 4) {
									try {
										int totalNoOfActiveAccountSubs = Integer.parseInt(val);
										individualSubscriberStatus.setTot_no_of_active_account_subs(totalNoOfActiveAccountSubs);
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 5) {
									//_log.info("cell2" + cell.getCellType());
									try {
										BigDecimal totalAmtOfSubsContribMB = CommonParser.parseBigDecimal(val);
										individualSubscriberStatus.setTotal_amt_of_subs_contrib_m_b(totalAmtOfSubsContribMB);	
									} catch (Exception e) {
										_log.info("error parsing total ammount of subscribers contri M&B---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									try {
										BigDecimal totalAUM = CommonParser.parseBigDecimal(val);
										individualSubscriberStatus.setTotal_aum(totalAUM);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
										try {
										//	int tierItierII = Integer.parseInt(val);
											
											individualSubscriberStatus.setTieri_tierii(val);
										} catch (Exception e) {
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}	
										/*try {
											int totalNoOfSubs = Integer.parseInt(val);
											individualSubscriberStatus.setTotal_no_of_subs(totalNoOfSubs);
										} catch (Exception e) {
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
								}
								else if (i == 8) {					
										try {
											int totalNoOfActiveAccountSubs = Integer.parseInt(val);
											individualSubscriberStatus.setTot_no_of_active_account_subs(totalNoOfActiveAccountSubs);
										} catch (Exception e) {
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}							
								}
								else if (i == 9) {					
									try {
										BigDecimal totalAmtOfSubsContribMB = CommonParser.parseBigDecimal(val);
										individualSubscriberStatus.setTotal_amt_of_subs_contrib_m_b(totalAmtOfSubsContribMB);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}							
								}
								else if (i == 10) {					
									try {
										BigDecimal totalAUM = CommonParser.parseBigDecimal(val);
										individualSubscriberStatus.setTotal_aum(totalAUM);
									} catch (Exception e) {
										_log.info("error parsing big decimal---->"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}							
								}
								else if (i == 11) {					
									try {
										int tierItierII = Integer.parseInt(val);
										
										individualSubscriberStatus.setTieri_tierii(new Integer(tierItierII).toString());
									} catch (Exception e) {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}*/								
								}
							  
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					individualSubscriberStatus.setCreatedon(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(individualSubscriberStatus.toString());
							form1JsonArray.put(jsonObject);
							form1List.add(individualSubscriberStatus);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
		
		return resultJsonObject;
	}

}
