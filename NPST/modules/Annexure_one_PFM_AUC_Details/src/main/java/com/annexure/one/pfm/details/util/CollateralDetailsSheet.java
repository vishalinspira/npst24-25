package com.annexure.one.pfm.details.util;

	import com.daily.average.service.model.CollateralDetails;
import com.daily.average.service.service.CollateralDetailsLocalServiceUtil;
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

	public class CollateralDetailsSheet {

		private static Log _log = LogFactoryUtil.getLog(CollateralDetailsSheet.class);
		
		public static JSONObject saveCollateralDetails(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray collDetailsJsonArray, List<CollateralDetails> collDetailsList, DecimalFormat decimalFormat, long reportUploadLogId, JSONObject resultJsonObject) {
			_log.info("Inside Collateral Details");
			String sheetName="Collateral details";
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
						CollateralDetails collDetails = CollateralDetailsLocalServiceUtil.createCollateralDetails(CounterLocalServiceUtil.increment(CollateralDetails.class.getName()));
						collDetails.setCreatedby(userId);
						collDetails.setReportUploadLogId(reportUploadLogId);
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
											collDetails.setSr_no(val);
										}
										 
									}
									else if (i == 1) {
										
											collDetails.setCcil_collateral(val);
										
										 
									}
									else if (i == 2) {
										try {
											BigDecimal gsec = CommonParser.parseBigDecimal(val);
											collDetails.setGsec(gsec);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}	
									}
									
									else if (i == 3) {
										try {
											BigDecimal treps = CommonParser.parseBigDecimal(val);
											collDetails.setTreps(treps);
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
						collDetails.setCreatedon(new Date());
							
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
								collDetailsList.add(collDetails);
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

	}

