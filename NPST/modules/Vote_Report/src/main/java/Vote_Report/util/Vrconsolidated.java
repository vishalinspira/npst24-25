package Vote_Report.util;

import com.daily.average.service.model.VotingReportConsolidated;
import com.daily.average.service.service.VotingReportConsolidatedLocalServiceUtil;
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

public class Vrconsolidated {
	
private static Log _log = LogFactoryUtil.getLog(Vrconsolidated.class);
	
	public static void saveVrconsolidated(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray vrconsolidatedArray, List<VotingReportConsolidated> vrconsolidatedList,DecimalFormat decimalFormat, Long reportUploadLogId) {
		_log.info("Inside Vrconsolidated");
		
		VotingReportConsolidatedLocalServiceUtil.deleteVotingReportConsolidatedByReportUploadLogId(reportUploadLogId);
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Consolidated Voting Report");
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					VotingReportConsolidated vrc =VotingReportConsolidatedLocalServiceUtil.
							createVotingReportConsolidated(CounterLocalServiceUtil.increment(VotingReportConsolidated.class.getName()));
					
					vrc.setCreatedby(userId);
					vrc.setReportUploadLogId(reportUploadLogId);
					
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
										//int iValue = Integer.parseInt(val);
										vrc.setSno(val);
									}
									 else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "It is not a number");
										isError = true;
									}
								}
								else if (i == 1) {
									vrc.setCompany_name(val);
								}
								else if (i == 2) {
									vrc.setIsin(val);
								} 
								
							
								else if (i == 3) {
									try {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										
										vrc.setMeeting_date(cell.getDateCellValue());
									}
									 else {
											errorObj.put("cellno", 3);
											errorObj.put("msg", "Error in date.");
											isError = true;
										}
									}catch (Exception e) {
										errorObj.put("cellno", 3);
										errorObj.put("msg", "Error in date.");
										isError = true;
									}
//									Date date_1 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
//									vrc.setMeeting_date(date_1);
								} 
								else if (i == 4) {
									vrc.setMajority_vote(val);
								} 
								else if (i == 5) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setNo_of_resolution(cg);
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
									vrc.setSes_id(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 6);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								
								else if (i == 7) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setSes_resid(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 7);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								
								else if (i == 8) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setLic_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 8);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 9) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setLic_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 9);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 10) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setLic_pf_against(cg);
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
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setUti_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 11);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 12) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setUti_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 12);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 13) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setUti_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 13);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 14) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setSbi_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 14);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 15) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setSbi_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 15);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 16) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setSbi_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 16);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 17) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setHdfc_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 17);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 18) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setHdfc_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 18);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 19) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setHdfc_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 19);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 20) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setIcici_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 20);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 21) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setIcici_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 21);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 22) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setIcici_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 22);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 23) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setKotak_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 23);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 24) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setKotak_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 24);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 25) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setKotak_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 25);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 26) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setBirla_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 26);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 27) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setBirla_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 27);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 28) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setBirla_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 28);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								
								
								
								else if (i == 29) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setAxis_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 29);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 30) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setAxis_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 30);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 31) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setAxis_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 31);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								
								else if (i == 32) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setTata_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 32);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 33) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setTata_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 33);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 34) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setTata_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 34);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								
								else if (i == 35) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setMax_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 35);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 36) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setMax_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 36);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 37) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setMax_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 37);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 38) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setDsp_pf_for(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 38);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 39) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setDsp_pf_abstain(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 39);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i == 40) {
									try {
									BigDecimal cg =CommonParser.parseBigDecimal(val);
									//BigDecimal cg = (BigDecimal) decimalFormat.parse(val);
									vrc.setDsp_pf_against(cg);
								}catch (Exception e) {
									errorObj.put("cellno", 40);
									errorObj.put("msg", "It is not a number");
									isError = true;
									_log.error(e);
								}
								}
								else if (i==41) {
									try {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										
										vrc.setReportingDate(cell.getDateCellValue());
									}
									 else {
											errorObj.put("cellno", 41);
											errorObj.put("msg", "Error in date.");
											isError = true;
										}
								}catch (Exception e) {
									errorObj.put("cellno", 39);
									errorObj.put("msg", "Error in date.");
									isError = true;
								}
								}
								
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					vrc.setCreatedate(new Date());
						
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(vrc.toString());
							//vrconsolidatedArray.put(jsonObject);
							vrconsolidatedList.add(vrc);
						}
						rowCount++;
					}
				_log.info("vrconsolidated" +" rowcount"+rowCount);
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
