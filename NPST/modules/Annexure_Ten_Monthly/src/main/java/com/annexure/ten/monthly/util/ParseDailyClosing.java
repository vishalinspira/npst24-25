package com.annexure.ten.monthly.util;

import com.liferay.portal.kernel.json.JSONArray;

import java.io.File;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParseDailyClosing {
	public static void saveSheetEleven(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror) {
		/*try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(9);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					ReportVI reportvi = ReportVILocalServiceUtil.createReportVI(CounterLocalServiceUtil.increment(ReportVI.class.getName()));
							
					reportvi.setCreatedby(userId);
					reportvi.setCra(cra);
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 9; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 5) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										Integer  sno=Integer.parseInt(val);
										reportvi.setSno(sno);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									Long griev_raised_entity=Long.parseLong(val);
									reportvi.setGriev_raised_entity(griev_raised_entity);
								}
								else if (i == 2) {
									Long token_no=Long.parseLong(val);
									reportvi.setToken_no(token_no);
								} 
								else if (i == 3) {
									DateFormat dateFormat= new SimpleDateFormat("dd/mm/yyyy");
									reportvi.setGriev_logged_date(cell.getDateCellValue());
								}
								else if (i == 4) {
									DateFormat dateFormat= new SimpleDateFormat("dd/mm/yyyy");
									reportvi.setResolution_timestamp(cell.getDateCellValue());
								} 
								else if (i == 5) {
									reportvi.setGriev_particulars(val);
								}
								else if (i == 6) {
									Integer  griev_closure_tat=Integer.parseInt(val);
									reportvi.setGriev_closure_tat(griev_closure_tat);
								} 
								else if (i == 7) {
									reportvi.setSolution_provided(val);
								}
								else if (i == 8) {
									reportvi.setTb_remarks(val);
								} 
							}
						}else if(i==0 && rowCount > 5) {
							break rowLoop;
						}
					}
					reportvi.setCreatedate(new Date());
					
					if (isError && rowCount > 5) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount > 5) {
						JSONObject reportviJsonObject = JSONFactoryUtil.createJSONObject(reportvi.toString());
						reportviJsonArray.put(reportviJsonObject);
						reportvis.add(reportvi);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			_log.error(e);
		}
	}
}*/

	}
}
