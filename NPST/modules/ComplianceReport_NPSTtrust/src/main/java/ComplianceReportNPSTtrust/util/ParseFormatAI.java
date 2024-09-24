package ComplianceReportNPSTtrust.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.math.BigDecimal;
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

import compliance.service.model.FormatAI;
import compliance.service.service.FormatAILocalServiceUtil;

public class ParseFormatAI {
	static Log _log = LogFactoryUtil.getLog(ParseFormatAI.class);
	public static void saveSheetFormatai(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror,DecimalFormat decimalFormat, JSONArray formataiJsonArray, List<FormatAI> formatai) {
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(1);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					FormatAI format2 = FormatAILocalServiceUtil.createFormatAI(CounterLocalServiceUtil.increment(FormatAI.class.getName()));
							
					format2.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false; 
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 8; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 13) {
								_log.info(val);
								
									if (i == 0) {
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											
											format2.setFormatiaisector(val);
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											
											isError = true;
										}
									}
									else if (i == 1) {
										//Integer no_of_remittances=Integer.parseInt(val);
										format2.setFormatiawithdrawaltype(val);
									}
									else if (i == 2) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										format2.setFormatiapendingbeginning(val);
									} 
									else if (i == 3) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										format2.setFormatiaclaimsreceivedduring(val);
									} 
									else if (i == 4) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										format2.setFormatiaclaimsauthorisedduring(val);
									} 
									else if (i == 5) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										format2.setFormatiaclaimsrejectedduring(val);
									} 
									else if (i == 6) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										format2.setFormatiaclaimsoutstanding(val);
									} 
									else if (i == 7) {
										//BigDecimal  amt =  (BigDecimal) decimalFormat.parse(val);
										format2.setFormataiclaimssettledduring(val);
									} 
								}
						}else if(i==0 && rowCount > 13) {
							break rowLoop;
						}
					}
					format2.setCreatedate(new Date());
						
					if (isError && rowCount > 13) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					} else if (rowCount > 13) {
						JSONObject annexTenaIVJsonObject = JSONFactoryUtil.createJSONObject(format2.toString());
						formataiJsonArray.put(annexTenaIVJsonObject);
						formatai.add(format2);
					}
					rowCount++;
				}
				
				}
			}catch (Exception e) {
			 _log.error(e);
			}
		}
	}
