package FinalIntimation_NPS.Util;

import com.daily.average.service.model.GrievanceFinalIntimPao;
import com.daily.average.service.service.GrievanceFinalIntimPaoLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.ParseException;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nps.common.service.util.CommonParser;

public class PaoGrievances {
	
private static Log _log = LogFactoryUtil.getLog(PaoGrievances.class);
	
	public static JSONObject savePaoGrievances(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, 
			JSONArray finalIntimPaoArray, List<GrievanceFinalIntimPao> finalIntimPaoList,DecimalFormat decimalFormat,String cra, long reportUploadLogId) {
		_log.info("Inside savePaoGrievances");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("PAO_Grievance");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					GrievanceFinalIntimPao fpao =GrievanceFinalIntimPaoLocalServiceUtil.
							createGrievanceFinalIntimPao(CounterLocalServiceUtil.increment(GrievanceFinalIntimPao.class.getName()));
					
					fpao.setCreatedby(userId);
					fpao.setCra(cra);
					fpao.setReportUploadLogId(reportUploadLogId);
					_log.info("cra ************" + cra);
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
										try {
											int iValue = (int) cell.getNumericCellValue();
											fpao.setSr_no(iValue);
										} catch (Exception e) {
											_log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									fpao.setPrao_reg_no(val);
								}
								else if (i == 2) {
									fpao.setPao_reg_no(val);
								} 
								else if (i == 3) {
									fpao.setPao_name(val);
								} 
								else if (i == 4) {
									fpao.setToken_number(val);
								} 
								else if (i == 5) {
									//String str1 = formatDate(cell.getDateCellValue(), "dd-MM-yy");
									//Date date_1 = new SimpleDateFormat("dd-MM-yy").parse(str1);
									try {
										Date date= cell.getDateCellValue();
										fpao.setGrievance_logging_date(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//fpao.setGrievance_logging_date(cell.getDateCellValue());
									}
								}
								else if (i == 6) {
									try {
										long pran = CommonParser.parseLong(val);
										fpao.setPran(pran);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 7) {
									fpao.setSubscriber_name(val);
								}
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					fpao.setCreatedate(new Date());
						
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
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(fpao.toString());
							finalIntimPaoArray.put(jsonObject);
							finalIntimPaoList.add(fpao);
						}
						rowCount++;
					}
				}
			}catch (Exception e) {
				
				 _log.error(e);
				 resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
				 return resultJsonObject;
			}
		return resultJsonObject;


	}
	
	public static String formatDate(Date dateIn, String format) throws ParseException {
        String strDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(dateIn);
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date d1 = sdf3.parse(formattedDate);

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            strDate = formatter.format(d1);
        } catch (Exception e) {
             _log.error(e);
        }
        return strDate;
    }

}
