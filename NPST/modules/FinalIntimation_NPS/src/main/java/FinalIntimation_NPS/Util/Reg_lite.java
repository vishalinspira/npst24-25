package FinalIntimation_NPS.Util;

import com.daily.average.service.model.GrievanceFinalIntimreglite;
import com.daily.average.service.service.GrievanceFinalIntimregliteLocalServiceUtil;
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

public class Reg_lite {
	
	private static Log _log = LogFactoryUtil.getLog(Reg_lite.class);
	
	public static JSONObject saveReg_lite(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,
			JSONArray finalIntimliteArray, List<GrievanceFinalIntimreglite> finalIntimregliteList,DecimalFormat decimalFormat,String cra, long reportUploadLogId) {
		_log.info("Inside saveReg_lite");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Reg_Lite");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					
					GrievanceFinalIntimreglite freg = GrievanceFinalIntimregliteLocalServiceUtil.
							createGrievanceFinalIntimreglite(CounterLocalServiceUtil.increment(GrievanceFinalIntimreglite.class.getName()));
					
					freg.setCreatedby(userId);
					freg.setCra(cra);
					freg.setReportUploadLogId(reportUploadLogId);
					_log.info("cra ************" + cra);
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					_log.info("Inside while"+row);
					//int lastColumn = Math.max(row.getLastCellNum(), 0);
					
					for (int i = 0; i < 17; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						_log.info("row num: "+rowCount+" Val: " + val +" cell: "+cell);
						if (cell != null) {
							
							if (val != null) {
								val = val.trim();
							}else if(i == 0 && rowCount > 1) {
								_log.info("row num: "+rowCount+" Val: " + val);
								break rowloop;
							}
							if(rowCount > 1) {
								
								if (i == 0) {
									if (val != null) {
										freg.setPrao_reg_no(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									freg.setPrao_name(val);
								}
								else if (i == 2) {
									freg.setPrao_add_line1(val);
								} 
								else if (i == 3) {
									freg.setPrao_add_line2(val);
								} 
								else if (i == 4) {
									freg.setPrao_add_line3(val);
								} 
								else if (i == 5) {
									freg.setPrao_add_line4(val);
								}
								else if (i == 6) {
									int cg = Integer.parseInt(val);
									freg.setPrao_pin_code(cg);
								}
								else if (i == 7) {
									try {
										int iValue = Integer.parseInt(val);
										freg.setCount_of_prao_grievanc_pending(iValue);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 8) {
									freg.setPao_reg_no(val);
								}
								else if (i == 9) {
									freg.setPao_name(val);
								}
								else if (i == 10) {
									freg.setAdd_line1(val);
								}
								else if (i == 11) {
									freg.setAdd_line2(val);
								}
								else if (i == 12) {
									freg.setAdd_line3(val);
								}
								else if (i == 13) {
									freg.setAdd_line4(val);
								}
								else if (i == 14) {
									try {
										int cg = Integer.parseInt(val);
										freg.setPin_code(cg);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 15) {
									try {
										int iValue = Integer.parseInt(val);
										freg.setCount_of_pao_grievanc_pending(iValue);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}else if (i == 16) {
									freg.setSector(val);
								}
							}
						} else if(i == 0 && rowCount > 1) {
							_log.info("row num: "+rowCount+" Val: " + val);
							break rowloop;
						} else {
							_log.info("row num: "+rowCount+" Val: " + val);
						}
					}
					freg.setCreatedate(new Date());
						
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
						//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(freg.toString());
						//finalIntimliteArray.put(jsonObject);
						_log.info("row num: "+rowCount);
						_log.info("IfinalIntimlite"+freg);
						finalIntimregliteList.add(freg);
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
