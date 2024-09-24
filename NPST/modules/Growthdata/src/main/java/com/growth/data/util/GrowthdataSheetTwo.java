package com.growth.data.util;

import com.daily.pfm.service.model.GrowthDataTwo;
import com.daily.pfm.service.service.GrowthDataTwoLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

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

public class GrowthdataSheetTwo {
	public static void saveSheetTwo(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,List<GrowthDataTwo> growthDataTwos, JSONArray growthDataTwoJsonArray,DecimalFormat decimalFormat,long reportUploadLogId, String cra) {
		
		GrowthDataTwoLocalServiceUtil.deleteGrowthDataTwoByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(1);
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					GrowthDataTwo growthDataTwo = GrowthDataTwoLocalServiceUtil.createGrowthDataTwo(CounterLocalServiceUtil.increment(GrowthDataTwo.class.getName()));
				
					growthDataTwo.setReportUploadLogId(reportUploadLogId);
					growthDataTwo.setCreatedby(userId);
					growthDataTwo.setCra(cra);
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 28; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										if(val.equalsIgnoreCase("total")) {
											break rowLoop;
										}
										
										//growthDataTwo.setSr_no(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									growthDataTwo.setMonth(val);
								}
								else if (i == 2) {
									growthDataTwo.setPension_fund_name(val);
								}
								
								/*else if (i == 3) {
									BigDecimal thirteen_fourteen = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setThirteen_fourteen(thirteen_fourteen);
								} 
								else if (i == 4) {
									BigDecimal fourteen_fifteen = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setFourteen_fifteen(fourteen_fifteen);
								}
								else if (i == 5) {
									BigDecimal fifteen_sixteen = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setFifteen_sixteen(fifteen_sixteen);
								}
								else if (i == 6) {
									BigDecimal sixteen_seventeen = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setSixteen_seventeen(sixteen_seventeen);
								}
								else if (i == 7) {
									BigDecimal seventeen_eighteen = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setSeventeen_eighteen(seventeen_eighteen);
								}
								else if (i == 8) {
									BigDecimal eighteen_nineteen = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setEighteen_nineteen(eighteen_nineteen);
								}
								else if (i == 9) {
									BigDecimal nineteen_twenty = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setNineteen_twenty(nineteen_twenty);
								}
								else if (i == 10) {
									BigDecimal twenty_twentyone = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwenty_twentyone(twenty_twentyone);
								}
								else if (i == 11) {
									BigDecimal twentyone_twentytwo = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwentyone_twentytwo(twentyone_twentytwo);
								}
								else if (i == 12) {
									BigDecimal twentytwo_twentythree = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwentytwo_twentythree(twentytwo_twentythree);
								}
								else if (i == 13) {
									BigDecimal twentythree_twentyfour = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwentythree_twentyfour(twentythree_twentyfour);
								}
								else if (i == 14) {
									BigDecimal twentyfour_twentyfive = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwentyfour_twentyfive(twentyfour_twentyfive);
								}
								else if (i == 15) {
									BigDecimal twentyfive_twentysix = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwentyfive_twentysix(twentyfive_twentysix);
								}
								else if (i == 16) {
									BigDecimal twentysix_twentyseven = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwentysix_twentyseven(twentysix_twentyseven);
								}
								else if (i == 17) {
									BigDecimal twentyseven_twentyeight = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwentyseven_twentyeight(twentyseven_twentyeight);
								}
								else if (i == 18) {
									BigDecimal twentyeight_twentynine = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setTwentyeight_twentynine(twentyeight_twentynine);
								}
								else if (i == 19) {
									BigDecimal prev_year_curr_month = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setPrev_year_curr_month(prev_year_curr_month);
								}
								else if (i == 20) {
									BigDecimal curr_year_curr_month = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setCurr_year_curr_month(curr_year_curr_month);
								}
								else if (i == 21) {
									BigDecimal gr_amt_curr_fy_over_prev_fy = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setGr_amt_curr_fy_over_prev_fy(gr_amt_curr_fy_over_prev_fy);
								}
								else if (i == 22) {
									BigDecimal gr_prnt_curr_fy_over_prev_fy = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setGr_prnt_curr_fy_over_prev_fy(gr_prnt_curr_fy_over_prev_fy);
								}
								else if (i == 23) {
									BigDecimal previous_fy_amt = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setPrevious_fy_amt(previous_fy_amt);
								}
								else if (i == 24) {
									BigDecimal previous_fy_prcnt = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setPrevious_fy_prcnt(previous_fy_prcnt);
								}
								else if (i == 25) {
									BigDecimal current_fy_amt = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setCurrent_fy_amt(current_fy_amt);
								}
								else if (i == 26) {
									BigDecimal current_fy_prcnt = (BigDecimal) decimalFormat.parse(val);
									growthDataTwo.setCurrent_fy_prcnt(current_fy_prcnt);
								}
								else if (i == 27) {
									
								}*/
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					growthDataTwo.setCreatedate(new Date());
					
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
						JSONObject growthDataTwoJsonObject = JSONFactoryUtil.createJSONObject(growthDataTwo.toString());
						growthDataTwoJsonArray.put(growthDataTwoJsonObject);
						growthDataTwos.add(growthDataTwo);
					}
					rowCount++;
				}
				_log.info(sheetName +" rowcount"+rowCount);
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}
	static Log _log = LogFactoryUtil.getLog(GrowthdataSheetTwo.class);
}
