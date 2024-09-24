package GrievanceTopFiveData_Monthly.util;

import com.daily.average.service.model.GrievanceTopFiveDetails;
import com.daily.average.service.service.GrievanceTopFiveDetailsLocalServiceUtil;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import GrievanceTopFiveData_Monthly.constants.GrievTop5MasterConstant;
import nps.common.service.util.CommonParser;

public class Grievancedetails {
	private static Log _log = LogFactoryUtil.getLog(Grievancedetails.class);
	public static JSONObject saveGdetails(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray grievanceDetailsArray, List<GrievanceTopFiveDetails> grievanceDetailsList,DecimalFormat decimalFormat,String cra, long reportUploadLogId, JSONObject resultJsonObject) {
		_log.info("Inside Grievancedetails");
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Grievance Details");
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					//_log.info("Inside while");
					GrievanceTopFiveDetails gd = GrievanceTopFiveDetailsLocalServiceUtil.
							createGrievanceTopFiveDetails(CounterLocalServiceUtil.increment(GrievanceTopFiveDetails.class.getName()));
					
					gd.setCreatedby(userId);
					gd.setCra(cra);
					gd.setReportUploadLogId(reportUploadLogId);
					
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
											int iValue = Integer.parseInt(val);
											gd.setSr_No(iValue);
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
									if (val != null) {
										try {
											//int iValue = Integer.parseInt(val);
											gd.setToken_number(val);
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
								    // gd.setToken_number(val);	
								}
								else if (i == 2) {
									gd.setPran(val);
								} 
								else if (i == 3) {
									try {
										Date date= cell.getDateCellValue();
										gd.setDate(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
										//gd.setDate(cell.getDateCellValue());
									}
								} 
								else if (i == 4) {
							        gd.setStatus(val);
								} 
								else if (i == 5) {
									try {
										Date date= cell.getDateCellValue();
										gd.setGrievance_logging_date(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 6) {
									gd.setRaised_by(val);
								}
						     else if (i == 7) {
									gd.setPao_reg_no(val);
								}
							 else if (i == 8) {
									gd.setPao_name(val);
								} 
							  else if (i == 9) {
										gd.setPr_ao_reg_no(val);
									} 
							  else if (i == 10) {
									gd.setPr_ao_name(val);
								} 
							  else if (i == 11) {
									gd.setMinistry_name(val);
								} 
							  else if (i == 12) {
								  if(GrievTop5MasterConstant.sectors.contains(val.trim().toUpperCase())) {
									  gd.setSector(val);
									}else {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg","Sheet name: "+sheetName+", column:Sector ,"+" master lookup failed. for row : "+rowCount +"for Value: "+val);
										return resultJsonObject;
									}
									//gd.setSector(val);
								} 
							  else if (i == 13) {
								  if(GrievTop5MasterConstant.sectorTypes.contains(val.trim().toUpperCase())) {
									  gd.setSector_Type(val);
									}else {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg","Sheet name: "+sheetName+", column:Sector Type ,"+" master lookup failed");
										return resultJsonObject;
									}
								   // gd.setSector_Type(val);
								} 
							  else if (i == 14) {
								     gd.setState_name(val);
								} 
							  else if (i == 15) {
								  if(GrievTop5MasterConstant.broadCategories.contains(val.trim().toUpperCase())) {
									  gd.setBroad_category(val);
									}else {
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg","Sheet name: "+sheetName+", column:Broad Category ,"+" master lookup failed");
										return resultJsonObject;
									}
								     //gd.setBroad_category(val);
								} 
							  else if (i == 16) {
									gd.setGrievance_text(val);
								} 
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					gd.setCreatedate(new Date());
						
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
							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject(gd.toString());
							//grievanceDetailsArray.put(jsonObject);
							grievanceDetailsList.add(gd);
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
	
	

}
