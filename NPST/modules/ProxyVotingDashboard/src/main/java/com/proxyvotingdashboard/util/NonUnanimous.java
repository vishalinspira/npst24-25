package com.proxyvotingdashboard.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Reference;

import compliance.service.model.MnNonUnanimous;
import compliance.service.service.MnNonUnanimousLocalService;
import compliance.service.service.MnNonUnanimousLocalServiceUtil;

public class NonUnanimous {
	private static Log _log = LogFactoryUtil.getLog(NonUnanimous.class);
	
	public static  void  saveNonUnanimous(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray unanimousJsonArray, List<MnNonUnanimous> mnUnanimousList, DecimalFormat decimalFormat,long reportUploadLogId) {
		_log.info("Inside NonUnanimous util class");

		MnNonUnanimousLocalServiceUtil.deleteMnNonUnanimousByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("mn_non_unanimous_voting");
				//XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					MnNonUnanimous mnUnanimous = MnNonUnanimousLocalServiceUtil.createMnNonUnanimous(CounterLocalServiceUtil.increment(NonUnanimous.class.getName()));
					
					mnUnanimous.setReportUploadLogId(reportUploadLogId);
					mnUnanimous.setCreatedby(userId);
					
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
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										//_log.info("cell2 " + cell.getCellType());
										//_log.info("cell " + cell.getStringCellValue());
										int iValue = Integer.parseInt(val);
										mnUnanimous.setS_no(iValue);
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											isError = true;
										}
								}
								else if (i == 1) {
									mnUnanimous.setName_of_the_company(val);
								}
								else if (i == 2) {
									//_log.info("cell2 " + row.getCell(i));
									if(DateUtil.isCellDateFormatted(row.getCell(i))) {
										String strDate = String.valueOf(row.getCell(i));
										//_log.info("strDate " + strDate);
										Date date_1 = new Date();//SimpleDateFormat("M/d/yyyy").parse(strDate);
										mnUnanimous.setMeeting_date(date_1);
									}
								} 
								else if (i == 3) {
									mnUnanimous.setResolution(val);
								} 
								else if (i == 4) {
									mnUnanimous.setSes_recommendation(val);
								} 
								else if (i == 5) {
									mnUnanimous.setSes_reason_for_recommendation(val);
								}
								else if (i == 6) {
									mnUnanimous.setPfs_voted_for(val);
								}
								else if (i == 7) {
									mnUnanimous.setPfs_voted_abstain(val);
								}
                                else if (i == 8) {
                                	mnUnanimous.setPfs_voted_against(val);
								}
                                else if (i == 9) {
                                	mnUnanimous.setFinal_vote(val);
								}
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					mnUnanimous.setCreatedon(new Date());
					
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
						JSONObject jsonObject = JSONFactoryUtil.createJSONObject(mnUnanimous.toString());
						unanimousJsonArray.put(jsonObject);
						mnUnanimousList.add(mnUnanimous);
					}
					rowCount++;
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
	}
	
	@Reference
	MnNonUnanimousLocalService mnNonUnanimousLocalService;

}
