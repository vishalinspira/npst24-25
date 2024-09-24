package Annual_Audited_Scheme_Financials.util;

import com.daily.average.service.model.annual_audited_scheme_financials_2;
import com.daily.average.service.service.annual_audited_scheme_financials_2LocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConsolidatedFinancial2 {
	private static Log _log = LogFactoryUtil.getLog(ConsolidatedFinancial2.class);

	public static void saveConsolidatedFinancial2(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qrForm1AJsonArray, List<annual_audited_scheme_financials_2> quarterlySubForm1AList,long reportUploadLogId) {
		_log.info("ParseForm1A::::::::::::::::::::::");
		
	//	annual_audited_scheme_financials_2LocalServiceUtil.deleteannual_audited_scheme_financials_2ByReportUploadLogId(reportUploadLogId);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
			//	XSSFSheet sheets = workbook.getSheet("Form 1A");
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					
					String sheetName = sheet.getSheetName();
					boolean sheet4 = sheetName.equalsIgnoreCase("Balance Sheet");
					boolean sheet5 = sheetName.equalsIgnoreCase("Income Statement");
					
					if ( sheet4 || sheet5 ) {
						System.out.println("Inside if loop in second sheet");
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						
						_log.info("getPhysicalNumberOfRows ::: "+sheet.getPhysicalNumberOfRows());
						rowLoop:
							for (int k=0; k<sheet.getPhysicalNumberOfRows(); k++) {
								System.out.println("Inside while loop in second sheet");
								annual_audited_scheme_financials_2 annual_audited2 = annual_audited_scheme_financials_2LocalServiceUtil.createannual_audited_scheme_financials_2(CounterLocalServiceUtil.increment(annual_audited_scheme_financials_2.class.getName()));
								
								annual_audited2.setReportUploadLogId(reportUploadLogId);
								annual_audited2.setCreatedby(userId);
								annual_audited2.setCreatedate(new Date());
								
								JSONObject errorObj = JSONFactoryUtil.createJSONObject();
								errorObj.put("rownum", rowCount);
								boolean isError = false;
								XSSFRow row = (XSSFRow) rows.next();
								XSSFRow errorRow = null;
								
								int lastColumn = Math.max(row.getLastCellNum(), 0);
								_log.info("lastColumn ::: "+lastColumn);
								for (int i = 0; i < lastColumn; i++) {
									XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
									DataFormatter formatter = new DataFormatter();
									String val = formatter.formatCellValue(cell);
									_log.info("val :::: "+val);
									if (cell != null) {
										if (val != null) {
											
											val = val.trim();
										if(rowCount > 1) {
										
											if (i == 0) {
										     annual_audited2.setAs_on_date(val);
											}
											else if (i == 1) {
											 annual_audited2.setPension_fund_name(val);
											}
											else if (i == 2) {
											 annual_audited2.setScheme_name(val); 
											}
											else if (i == 3) {
												annual_audited2.setSl_no(val); 
											}
											else if (i == 4) {
												annual_audited2.setParticulars(val);
											}
											else if (i == 5) {
												annual_audited2.setCurrent_year(val);
											}
											else if (i == 6) {
											annual_audited2.setPrevious_year(val);
											}
										}
									}
									}else if(i==0 && rowCount > 1) {
										continue;
									}
								}
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
									JSONObject formoneJsonObject = JSONFactoryUtil.createJSONObject(annual_audited2.toString());
									qrForm1AJsonArray.put(formoneJsonObject);
									_log.info("qrForm1AJsonArray :::: "+qrForm1AJsonArray.toString());
									quarterlySubForm1AList.add(annual_audited2);
								}
								rowCount++;
							}
								rowCount = 1;
				}
				
				} 
				
			}
	}catch (Exception e) {
	e.printStackTrace();
}
}	
					
					
}
