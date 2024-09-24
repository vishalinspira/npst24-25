package Quarterlycompliancesforms.util;

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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.Quaeterlyfromfivenpa;
import compliance.service.service.QuaeterlyfromfivenpaLocalServiceUtil;

public class ParseFormFivenpa {
	static Log _log = LogFactoryUtil.getLog(ParseFormFivenpa.class);
	public static void saveSheetFormFivenpa(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray qformfivenpsJsonArray, List<Quaeterlyfromfivenpa> qformfivenpsList) {
		_log.info("saveSheetFormFivenpa::::::::::::::::::::");
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				int sheetcount = 25;
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext())
				{
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					
					String sheetName = sheet.getSheetName();
					boolean sheet1 = sheetName.equalsIgnoreCase("FORM 5 NPA OLD FORMAT");
					
					//if(sheetcount>0) {
						
					if(sheet1 && sheets!=null) {	
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
							//NpsTrustFee npstrustfee =NpsTrustFeeLocalServiceUtil.createNpsTrustFee(CounterLocalServiceUtil.increment(NpsTrustFee.class.getName()));
					Quaeterlyfromfivenpa qformnpa=QuaeterlyfromfivenpaLocalServiceUtil.createQuaeterlyfromfivenpa(CounterLocalServiceUtil.increment(Quaeterlyfromfivenpa.class.getName()));
					qformnpa.setCreatedby(userId);
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							for (int i = 0; i < 12; i++) {
								XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								if (cell != null) {
		
									DataFormatter formatter = new DataFormatter();
									String val = formatter.formatCellValue(cell);
									if (val != null)
										val = val.trim();
									if(rowCount > 5) {
										if (i == 0) {
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												//Long srno=Long.parseLong(val);
												qformnpa.setSrno(val);
											}
											 else {
													errorObj.put("cellno", 2);
													errorObj.put("msg", "It is not a number");
													
													isError = true;
												}
										}
										else if (i == 1) {
											
											qformnpa.setNameofnpa(val);
										}
										else if (i == 2) {
											qformnpa.setNameofsecurity(val);
										} 
										else if (i == 3) {
											qformnpa.setDateofpurchase(val);
										} 
										else if (i == 4) {
											qformnpa.setTotal_facevalue(val);
										} 
										else if (i == 5) {
											qformnpa.setTotal_purchasevalue(val);
										} 
										else if (i == 6) {
											qformnpa.setValue(val);
										}
										else if (i == 7) {
											qformnpa.setNpasince(val);
										}
										else if (i == 8) {
											qformnpa.setBookvalue(val);
										}
										else if (i == 9) {
											qformnpa.setProvisionheld(val);
										}
										else if (i == 10) {
											qformnpa.setDd_interestpayment(val);
										}
										else if (i == 11) {
											qformnpa.setStepstakencomments(val);
										}
										
									}
								}else if(i==0 && rowCount > 5) {
									break rowLoop;
								}
							}
							qformnpa.setCreatedate(new Date());
							
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
								JSONObject formoneJsonObject = JSONFactoryUtil.createJSONObject(qformnpa.toString());
								qformfivenpsJsonArray.put(formoneJsonObject);
								qformfivenpsList.add(qformnpa);
							}
							rowCount++;
						}
						_log.info(sheetName +" rowcount"+rowCount);
					}
					sheetcount++;
				}
			}
		}catch (Exception e) {
				 _log.error(e);
			}
	}
}
