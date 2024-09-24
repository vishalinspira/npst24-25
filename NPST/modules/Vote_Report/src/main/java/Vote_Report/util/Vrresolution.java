package Vote_Report.util;

import com.daily.average.service.model.VotingReportNoOfResolution;
import com.daily.average.service.service.VotingReportNoOfResolutionLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.util.Validator;

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

public class Vrresolution {
	
private static Log _log = LogFactoryUtil.getLog(Vrresolution.class);
	
	public static void saveVrresolution(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray vrnoofresolutionArray, List<VotingReportNoOfResolution> vrnoofresolutionList,DecimalFormat decimalFormat, Long reportUploadLogId) {
		_log.info("Inside Vrresolution");
		
		VotingReportNoOfResolutionLocalServiceUtil.deleteVotingReportNoOfResolutionByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("No. of Resolutions");
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					VotingReportNoOfResolution vrr =VotingReportNoOfResolutionLocalServiceUtil.
							createVotingReportNoOfResolution(CounterLocalServiceUtil.increment(VotingReportNoOfResolution.class.getName()));
					
					vrr.setCreatedby(userId);
					vrr.setReportUploadLogId(reportUploadLogId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					int lastColumn = Math.max(row.getLastCellNum(), 0);
					
					for (int i = 0; i < 8; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						try {
						if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								//_log.info("Val: " + val);
								if (i == 0) {
									try {
									if (val != null) {
										
										BigDecimal cg =CommonParser.parseBigDecimal(val);
										//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
										vrr.setSno(cg);
										
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
									}catch (Exception e) {
										_log.error(e);
									}
								}
								else if (i == 1) {
									try {
									vrr.setCompany_name(val);
									}catch (Exception e) {
										_log.error(e);
									}
								}
								else if (i == 2) {
									try {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										
										vrr.setMeeting_Date(cell.getDateCellValue());
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "Error in date.");
											isError = true;
										}

//									Date date_1 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
//									vrr.setMeeting_Date(date_1);
									}catch (Exception e) {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "Error in date.");
										isError = true;
										_log.error(e);
									}
								} 
								else if (i == 3) {
									try {
									vrr.setResolutions(val);
									}catch (Exception e) {
										_log.error(e);
									}
								} 
								else if (i == 4) {
									try {
									vrr.setResolution_Details(val);
									}catch (Exception e) {
										_log.error(e);
									}
								} 
								else if (i == 5) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrr.setSes_id(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 5);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								
								else if (i == 6) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrr.setSes_resid(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 6);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i==7) {
									try {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										
										vrr.setReportingDate(cell.getDateCellValue());
									}
									 else {
											errorObj.put("cellno", 7);
											errorObj.put("msg", "Error in date.");
											isError = true;
										}
									}catch (Exception e) {
										errorObj.put("cellno", 5);
										errorObj.put("msg", "Error in date.");
										isError = true;
										_log.error(e);
									}
								}
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}catch (Exception e) {
						_log.error(e);
					}
					}
					
					vrr.setCreatedate(new Date());
						
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
//							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(vrr.toString());
//							vrnoofresolutionArray.put(jsonObject);
							vrnoofresolutionList.add(vrr);
						}
						rowCount++;
					}
				_log.info("vrresolution" +" rowcount"+rowCount);
				}
			}catch (Exception e) {
				 _log.error(e);
			}
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
