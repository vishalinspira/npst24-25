package Quarterlycompliancesforms.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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

import compliance.service.model.Quaeterlyfromoneb;
import compliance.service.model.Quaeterlyfromoneisin;
import compliance.service.service.QuaeterlyfromonebLocalServiceUtil;
import compliance.service.service.QuaeterlyfromoneisinLocalServiceUtil;

public class ParseFormoneisin {
	
	/*
	 * public static void saveSheetFormoneisin(File file, XSSFWorkbook workbook,
	 * long userId, JSONArray errorArray, XSSFSheet xx, boolean
	 * isexcelhaserror,DecimalFormat decimalFormat, JSONArray qformoneisinJsonArray,
	 * List<Quaeterlyfromoneisin> qformoneisinList) {
	 * _log.info("saveSheetFormoneisin::::::::::::::::::::::::");
	 * 
	 * try { if (file != null) { OPCPackage pkg = OPCPackage.open(file); workbook =
	 * new XSSFWorkbook(pkg); int sheetcount = 0; Iterator<Sheet> sheets =
	 * workbook.sheetIterator(); while(sheets.hasNext()) { XSSFSheet sheet =
	 * (XSSFSheet) sheets.next();
	 * 
	 * String sheetName = sheet.getSheetName(); boolean sheet1 =
	 * sheetName.equalsIgnoreCase("FORM 1(B) E-T-I ISIN"); boolean sheet2 =
	 * sheetName.equalsIgnoreCase("FORM 1 (B) Tax-T-II ISIN"); boolean sheet3 =
	 * sheetName.equalsIgnoreCase("FORM 1 (B) E-T-II ISIN");
	 * 
	 * //if(sheetcount>0) { if(sheet1 || sheet2 || sheet3 && sheets!=null) {
	 * Iterator<Row> rows = sheet.rowIterator(); int rowCount = 1; int errorRowCount
	 * = 2;
	 * 
	 * rowLoop: while (rows.hasNext()) { Quaeterlyfromoneisin
	 * qformoneisin=QuaeterlyfromoneisinLocalServiceUtil.createQuaeterlyfromoneisin(
	 * CounterLocalServiceUtil.increment(Quaeterlyfromoneisin.class.getName()));
	 * qformoneisin.setCreatedby(userId);
	 * 
	 * JSONObject errorObj = JSONFactoryUtil.createJSONObject();
	 * errorObj.put("rownum", rowCount); boolean isError = false; XSSFRow row =
	 * (XSSFRow) rows.next(); XSSFRow errorRow = null;
	 * 
	 * for (int i = 0; i < 7; i++) { XSSFCell cell =
	 * row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL); DataFormatter
	 * formatter = new DataFormatter(); String val =
	 * formatter.formatCellValue(cell); if (cell != null) { if (val != null) val =
	 * val.trim(); if(rowCount > 13) { //_log.info("rowCount " + rowCount);
	 * if (i == 0) { if (val != null && Validator.isNotNull(val) && val.length() >
	 * 0) { //Long srno=Long.parseLong(val); qformoneisin.setIsin_no(val); } else {
	 * errorObj.put("cellno", 2); errorObj.put("msg", "It is not a number");
	 * 
	 * isError = true; } } else if (i == 1) {
	 * qformoneisin.setIsin_categoryofinvestment(val); } else if (i == 2) {
	 * qformoneisin.setIsin_nameofsecurity_company(val); } else if (i == 3) {
	 * qformoneisin.setFisin(val); } else if (i == 4) {
	 * qformoneisin.setIsin_bookvalue(val); } else if (i == 5) {
	 * qformoneisin.setIsin_ofportfoliovalue(val); } else if (i == 6) {
	 * qformoneisin.setIsin_dateofpurchase_sale(val); }
	 * 
	 * } }else if(i==0 && rowCount > 13) { break rowLoop; } }
	 * qformoneisin.setCreatedate(new Date());
	 * 
	 * if (isError && rowCount > 13) { errorArray.put(errorObj); errorRow =
	 * xx.createRow(errorRowCount); errorRowCount++; XSSFCell rowCountCel =
	 * errorRow.createCell(1); rowCountCel.setCellValue(rowCount); XSSFCell
	 * cellError = errorRow.createCell(errorObj.getInt("cellno"));
	 * cellError.setCellValue(errorObj.getString("msg")); isexcelhaserror = true; }
	 * else if (rowCount > 13) { JSONObject formoneJsonObject =
	 * JSONFactoryUtil.createJSONObject(qformoneisin.toString());
	 * qformoneisinJsonArray.put(formoneJsonObject);
	 * qformoneisinList.add(qformoneisin); } rowCount++; } } sheetcount++; } }
	 * }catch (Exception e) {  _log.error(e); } }
	 */
}





