package com.valuationreports.resource;

import com.daily.average.service.model.ReportDebit;
import com.daily.average.service.model.ReportEquity;
import com.daily.average.service.model.ValuationRepoTb;
import com.daily.average.service.model.valuationRepSg;
import com.daily.average.service.service.ReportDebitLocalServiceUtil;
import com.daily.average.service.service.ReportEquityLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
import com.daily.average.service.service.ValuationRepoTbLocalServiceUtil;
import com.daily.average.service.service.valuationRepSgLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.valuationreports.constants.ValuationReportsPortletKeys;
import com.valuationreports.util.ValidateSheetName;
import com.valuationreports.util.ValuationCorpBonds;
import com.valuationreports.util.ValuationEquity;
import com.valuationreports.util.ValuationRepCG;
import com.valuationreports.util.ValuationRepDebt;
import com.valuationreports.util.ValuationRepSG;
import com.valuationreports.util.ValuationRepTB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import compliance.service.model.NonEq;
import compliance.service.model.valuationCg;
import compliance.service.service.NonEqLocalServiceUtil;
import compliance.service.service.valuationCgLocalServiceUtil;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + ValuationReportsPortletKeys.VALUATIONREPORTS,
		"mvc.command.name=" + ValuationReportsPortletKeys.valuationRep, 
		}, 
service = MVCResourceCommand.class)

public class ValuationReports implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(ValuationReports.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		_log.info("Serve Resource method");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}

		return false;
	}
	
	@SuppressWarnings("deprecation")
	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("fileList", JSONFactoryUtil.createJSONArray());
		
		resultJsonObject = saveCG(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveSG(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
		}
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveDebt(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
					
		}
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveCorpBonds(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
					
		}
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveEquity(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
					
		}
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveTB(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
					
		}
		
		String fileList = resultJsonObject.getJSONArray("fileList").toString();
		long fileEntryId = resultJsonObject.getLong("fileEntryId");
		String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
		updateReportLog(userId, fileEntryId , true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks, fileList);
		return resultJsonObject;
	}
	
	public JSONObject saveCG(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
//		String CG_FileName = uploadPortletRequest.getFileName("valuation_rep_cg");
		File CG_File = uploadPortletRequest.getFile("valuation_rep_cg");
//		String mimeType = uploadPortletRequest.getContentType("valuation_rep_cg");
//		String title = CG_FileName;

		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);

		resultJsonObject = excelValidationAn10Api.validateExcelFile(CG_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {
			_log.info("Inside resultJsonObject");
			
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			// ValuationCG
			JSONArray cgJsonArray = JSONFactoryUtil.createJSONArray();
			List<valuationCg> cgList = new ArrayList<valuationCg>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;
			
			List<String> sheetNamefileList = new ArrayList<>();
			sheetNamefileList.add("valuation_cg");
			
			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(CG_File, workbook, sheetNamefileList);

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

				ValuationRepCG.saveValuationCG(CG_File, workbook, userId, errorArray, xx, isexcelhaserror, cgJsonArray,
						cgList, decimalFormat, reportUploadLogId);

				if (!isexcelhaserror) {

					valuationCgLocalServiceUtil.addValuationCg(cgList);
					
					Long cgFileEntryId = 0l;
					cgFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "valuation_rep_cg");
					_log.info("cgFileEntryId: " + cgFileEntryId);
					JSONObject cgFileEntryJO = JSONFactoryUtil.createJSONObject();
					cgFileEntryJO.put("Name", "Valuation Report CG");
					cgFileEntryJO.put("fileEntryId", cgFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
						_log.info("Above json array");
						
						_log.info("fileList---------"+fileList);
						fileList.put(cgFileEntryJO);
						_log.info("fileList: " + fileList.toString());
						resultJsonObject.put("fileList", fileList);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	
	public JSONObject saveSG(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
//		String SG_FileName = uploadPortletRequest.getFileName("valuation_rep_sg");
		File SG_File = uploadPortletRequest.getFile("valuation_rep_sg");
		
		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);

		resultJsonObject = excelValidationAn10Api.validateExcelFile(SG_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			//ValuationSG
			JSONArray sgJsonArray = JSONFactoryUtil.createJSONArray();
			List<valuationRepSg> sgList = new ArrayList<valuationRepSg>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;
			
			List<String> sheetNamefileList = new ArrayList<>();
			sheetNamefileList.add("valuation_sg");

			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(SG_File, workbook, sheetNamefileList);

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

				ValuationRepSG.saveValuationSG(SG_File, workbook, userId, errorArray, xx, isexcelhaserror, sgJsonArray, sgList, decimalFormat, reportUploadLogId);

				if (!isexcelhaserror) {

					valuationRepSgLocalServiceUtil.addValuationRepSg(sgList);
					
					Long sgFileEntryId = 0l;
					sgFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "valuation_rep_sg");
					JSONObject sgFileEntryJO = JSONFactoryUtil.createJSONObject();
					sgFileEntryJO.put("Name", "Valuation Report SG");
					sgFileEntryJO.put("fileEntryId", sgFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
						
						fileList.put(sgFileEntryJO);
						resultJsonObject.put("fileList", fileList);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	
	public JSONObject saveDebt(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
//		String SG_FileName = uploadPortletRequest.getFileName("valuation_rep_debt");
		File DEBT_File = uploadPortletRequest.getFile("valuation_rep_debt");
		
		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(DEBT_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			//ValuationDebt
			JSONArray debtJsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportDebit> debtList = new ArrayList<ReportDebit>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;
			
			List<String> sheetNamefileList = new ArrayList<>();
			sheetNamefileList.add("repo_debt");

			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(DEBT_File, workbook, sheetNamefileList);

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

				ValuationRepDebt.saveValuationDebt(DEBT_File, workbook, userId, errorArray, xx, isexcelhaserror, debtJsonArray, debtList, decimalFormat, reportUploadLogId);

				if (!isexcelhaserror) {

					ReportDebitLocalServiceUtil.addReportDebits(debtList);
					
					Long debtFileEntryId = 0l;
					debtFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "valuation_rep_debt");
					JSONObject debtFileEntryJO = JSONFactoryUtil.createJSONObject();
					debtFileEntryJO.put("Name", "Valuation Report Debt");
					debtFileEntryJO.put("fileEntryId", debtFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
						
						fileList.put(debtFileEntryJO);
						resultJsonObject.put("fileList", fileList);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	
	public JSONObject saveCorpBonds(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
//		String SG_FileName = uploadPortletRequest.getFileName("valuation_rep_corp_bonds");
		File CORP_BONDS_File = uploadPortletRequest.getFile("valuation_rep_corp_bonds");
		
		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);

		resultJsonObject = excelValidationAn10Api.validateExcelFile(CORP_BONDS_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			//ValuationCorpBonds
			JSONArray nonEqJsonArray = JSONFactoryUtil.createJSONArray();
			List<NonEq> nonEqList = new ArrayList<NonEq>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;

			List<String> sheetNamefileList = new ArrayList<>();
			sheetNamefileList.add("NONEQ");
			
			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(CORP_BONDS_File, workbook, sheetNamefileList);

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

				ValuationCorpBonds.saveValuationCorpBonds(CORP_BONDS_File, workbook, userId, errorArray, xx, isexcelhaserror, nonEqJsonArray, nonEqList, decimalFormat, reportUploadLogId);

				if (!isexcelhaserror) {

					NonEqLocalServiceUtil.addNonEq(nonEqList);
					
					Long corpBondsFileEntryId = 0l;
					corpBondsFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "valuation_rep_corp_bonds");
					JSONObject corpBondsFileEntryJO = JSONFactoryUtil.createJSONObject();
					corpBondsFileEntryJO.put("Name", "Valuation Report Corporate Bonds");
					corpBondsFileEntryJO.put("fileEntryId", corpBondsFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
						
						fileList.put(corpBondsFileEntryJO);
						resultJsonObject.put("fileList", fileList);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	
	public JSONObject saveEquity(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
//		String SG_FileName = uploadPortletRequest.getFileName("valuation_rep_equity"));
		File EQUITY_File = uploadPortletRequest.getFile("valuation_rep_equity");
		
		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);

		resultJsonObject = excelValidationAn10Api.validateExcelFile(EQUITY_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			//ValuationEquity
			JSONArray equityJsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportEquity> equityList = new ArrayList<ReportEquity>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;

			List<String> sheetNamefileList = new ArrayList<>();
			sheetNamefileList.add("repo_eq");
			
			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(EQUITY_File, workbook, sheetNamefileList);

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

				ValuationEquity.saveValuationEquity(EQUITY_File, workbook, userId, errorArray, xx, isexcelhaserror, equityJsonArray, equityList, decimalFormat, reportUploadLogId);

				if (!isexcelhaserror) {

					ReportEquityLocalServiceUtil.addReportEquitys(equityList);
					
					Long equityFileEntryId = 0l;
					equityFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "valuation_rep_equity");
					JSONObject equityFileEntryJO = JSONFactoryUtil.createJSONObject();
					equityFileEntryJO.put("Name", "Valuation Report Equity");
					equityFileEntryJO.put("fileEntryId", equityFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
						
						fileList.put(equityFileEntryJO);
						resultJsonObject.put("fileList", fileList);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	
	public JSONObject saveTB(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
//		String SG_FileName = uploadPortletRequest.getFileName("valuation_rep_tb");
		File TB_File = uploadPortletRequest.getFile("valuation_rep_tb");
		
		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);

		resultJsonObject = excelValidationAn10Api.validateExcelFile(TB_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			//ValuationTB
			JSONArray tbJsonArray = JSONFactoryUtil.createJSONArray();
			List<ValuationRepoTb> tbList = new ArrayList<ValuationRepoTb>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;

			List<String> sheetNamefileList = new ArrayList<>();
			sheetNamefileList.add("weekly_valuation_rep_tb");
			
			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(TB_File, workbook, sheetNamefileList);

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

				ValuationRepTB.saveValuationTB(TB_File, workbook, userId, errorArray, xx, isexcelhaserror, tbJsonArray, tbList, decimalFormat, reportUploadLogId);

				if (!isexcelhaserror) {

					ValuationRepoTbLocalServiceUtil.addValuationRepoTb(tbList);
					
					Long tbFileEntryId = 0l;
					tbFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "valuation_rep_tb");
					JSONObject tbFileEntryJO = JSONFactoryUtil.createJSONObject();
					tbFileEntryJO.put("Name", "Valuation Report TB");
					tbFileEntryJO.put("fileEntryId", tbFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
						
						fileList.put(tbFileEntryJO);
						resultJsonObject.put("fileList", fileList);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	
	@SuppressWarnings("deprecation")
	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest) {

		File file = new File(filepath);
		String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String title = filename + ".xlsx";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, 0, title, mimeType, title, description, "",
					file, serviceContext);
			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			_log.info(e.getMessage());
			 _log.error(e);
		}
		return downloadUrl;
	}
	
	@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest, String documentName) {
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName =date.getTime()+ uploadPortletRequest.getFileName(documentName);

		File file = uploadPortletRequest.getFile(documentName);

		String mimeType = uploadPortletRequest.getContentType(documentName);

		String title = fileName;

		String description = documentName;

		long repositoryId = themeDisplay.getScopeGroupId();
	
		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();

		} catch (Exception e) {

			_log.info(e.getMessage());

			 _log.error(e);
		}
		return 0;
	}
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Weekly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogPFMAMLocalServiceUtil.updateReportUploadLog2(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks, fileList);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

}
