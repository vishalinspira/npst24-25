package Vote_Report.util;

import com.daily.average.service.model.VotingReportSes;
import com.daily.average.service.service.VotingReportSesLocalServiceUtil;
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

public class Vrses {
	
private static Log _log = LogFactoryUtil.getLog(Vrses.class);
	
	public static void saveVrses(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray vrsesArray, List<VotingReportSes> vrsesList,DecimalFormat decimalFormat, Long reportUploadLogId) {
		_log.info("Inside Vrses");
		
		VotingReportSesLocalServiceUtil.deleteVotingReportSesByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("SES Vote Matrix");
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					VotingReportSes vrs =VotingReportSesLocalServiceUtil.
							createVotingReportSes(CounterLocalServiceUtil.increment(VotingReportSes.class.getName()));
					
					vrs.setCreatedby(userId);
					vrs.setReportUploadLogId(reportUploadLogId);
					
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
										vrs.setCompany_name(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									vrs.setIsin(val);
								}
								else if (i == 2) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										
										vrs.setMeet_date(cell.getDateCellValue());
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "Error in date.");
											isError = true;
										}
//									Date date_1 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
//									vrs.setMeet_date(date_1);
								} 
								else if (i == 3) {
									vrs.setMeeting_type(val);
								} 
								else if (i == 4) {
									vrs.setEvoting_platform(val);
								} 
								else if (i == 5) {
									BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrs.setResolution_sr_no(cg);
								}
								else if (i == 6) {
									vrs.setResolution_type(val);
								}
								else if (i == 7) {
									vrs.setResolution_dtls(val);
								}
								else if (i == 8) {
									vrs.setSes_recomm(val);
								}
								else if (i == 9) {
									vrs.setRationale(val);
								}
								else if (i == 10) {
									try {
										BigDecimal cg =CommonParser.parseBigDecimal(val);
										vrs.setSes_id(cg);
									}catch (Exception e) {
										errorObj.put("cellno", 10);
										errorObj.put("msg", "It is not a number");
										isError = true;
										_log.error(e);
									}
								}
								else if (i == 11) {
									try {
										BigDecimal cg =CommonParser.parseBigDecimal(val);
										vrs.setSes_resid(cg);
									}catch (Exception e) {
										errorObj.put("cellno", 11);
										errorObj.put("msg", "It is not a number");
										isError = true;
										_log.error(e);
									}
								}
								
								else if (i == 12) {
									vrs.setLic_pf(val);
								}
								else if (i == 13) {
									vrs.setUti_pf(val);
								}
								else if (i == 14) {
									vrs.setSbi_pf(val);
								}
								else if (i == 15) {
									vrs.setHdfc_pf(val);
								}
								else if (i == 16) {
									vrs.setIcici_pf(val);
								}
								else if (i == 17) {
									vrs.setKotak_pf(val);
								}
								else if (i == 18) {
									vrs.setBirla_pf(val);
								}
								
								else if (i == 19) {
									vrs.setAxis_pf(val);
								}
								else if (i == 20) {
									vrs.setTata_pf(val);
								}
								else if (i == 21) {
									vrs.setMax_pf(val);
								}
								else if (i == 22) {
									vrs.setDsp_pf(val);
								}
								
								else if (i == 23) {
									vrs.setMajority_vote(val);
								}
								
								else if(i==24) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										
										vrs.setReportingDate(cell.getDateCellValue());
									}
									 else {
											errorObj.put("cellno", 24);
											errorObj.put("msg", "Error in date.");
											isError = true;
										}
								}
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					vrs.setCreatedate(new Date());
						
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(vrs.toString());
							//vrsesArray.put(jsonObject);
							vrsesList.add(vrs);
						}
						rowCount++;
					}
				_log.info("vrses" +" rowcount"+rowCount);
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
