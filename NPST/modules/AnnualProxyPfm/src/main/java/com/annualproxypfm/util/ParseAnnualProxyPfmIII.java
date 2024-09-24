package com.annualproxypfm.util;

import com.daily.average.service.model.AnnualProxyPfmIII;
import com.daily.average.service.service.AnnualProxyPfmIIILocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
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

public class ParseAnnualProxyPfmIII {
	static Log _log = LogFactoryUtil.getLog(ParseAnnualProxyPfmIII.class);
	public static void saveSheetThree(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,List<AnnualProxyPfmIII> annualProxyPfmIIIs, JSONArray annualProxyPfmIIIJsonArray, DecimalFormat decimalFormat, Long reportUploadLogId) {
		
		AnnualProxyPfmIIILocalServiceUtil.deleteAnnualProxyPfmIIIByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(2);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					AnnualProxyPfmIII annualProxyPfmIII = AnnualProxyPfmIIILocalServiceUtil.createAnnualProxyPfmIII(CounterLocalServiceUtil.increment(AnnualProxyPfmIII.class.getName()));
							
					annualProxyPfmIII.setCreatedby(userId);
					annualProxyPfmIII.setReportUploadLogId(reportUploadLogId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 10; i++) {
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
										
										annualProxyPfmIII.setPension_fund_name("");
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
									annualProxyPfmIII.setMeeting_date(dateFormat.parse(val));
								}
								else if (i == 2) {
									annualProxyPfmIII.setCompany_name(val);
								}
								
								else if (i == 3) {
									annualProxyPfmIII.setType_of_meeting(val);
								} 
								else if (i==4) {
									annualProxyPfmIII.setProposal_by_shareholder(val);
								}
								
								else if (i == 5) {
									annualProxyPfmIII.setResolution_description(val);
								}
								else if (i == 6) {
									annualProxyPfmIII.setInvestee_company_recomm(val);
								}
								else if (i == 7) {
									annualProxyPfmIII.setPf_voting_recomm(val);
								}
								else if (i == 8) {
									annualProxyPfmIII.setPf_rationale_for_voting_recomm(val);
								}
								else if (i == 9) {
									annualProxyPfmIII.setFinal_vote(val);
								}
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					annualProxyPfmIII.setCreatedate(new Date());
					
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
						JSONObject annualProxyPfmIIIJsonObject = JSONFactoryUtil.createJSONObject(annualProxyPfmIII.toString());
						annualProxyPfmIIIJsonArray.put(annualProxyPfmIIIJsonObject);
						annualProxyPfmIIIs.add(annualProxyPfmIII);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}
}
