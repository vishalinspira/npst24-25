package com.consolidated.financial.key.stats.resources;

import com.consolidated.financial.key.stats.constants.Consolidated_Financial_And_Key_StatsPortletKeys;
import com.consolidated.financial.key.stats.util.ParseBalanceSheet;
import com.consolidated.financial.key.stats.util.ParseCombinedIncomeStatementSheet;
import com.consolidated.financial.key.stats.util.ParseConsolidatedBalanceSheet;
import com.consolidated.financial.key.stats.util.ParseIncomeStatementSheet;
import com.consolidated.financial.key.stats.util.ParseKeyStatics;
import com.consolidated.financial.key.stats.util.ParseNoteOfBalanceSheet;
import com.consolidated.financial.key.stats.util.ParseNoteOfCombinedBalanceSheet;
import com.consolidated.financial.key.stats.util.ValidateSheetName;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.daily.pfm.service.model.BalanceSheet;
import com.daily.pfm.service.model.CombinedIncomeStatement;
import com.daily.pfm.service.model.ConsolidatedBalanceSheet;
import com.daily.pfm.service.model.IncomeStatement;
import com.daily.pfm.service.model.KeyStatistics;
import com.daily.pfm.service.model.NoteOfBalanceSheet;
import com.daily.pfm.service.model.NoteOfCombinedBalanceSheet;
import com.daily.pfm.service.service.BalanceSheetLocalServiceUtil;
import com.daily.pfm.service.service.CombinedIncomeStatementLocalServiceUtil;
import com.daily.pfm.service.service.ConsolidatedBalanceSheetLocalServiceUtil;
import com.daily.pfm.service.service.IncomeStatementLocalServiceUtil;
import com.daily.pfm.service.service.KeyStatisticsLocalServiceUtil;
import com.daily.pfm.service.service.NoteOfBalanceSheetLocalServiceUtil;
import com.daily.pfm.service.service.NoteOfCombinedBalanceSheetLocalServiceUtil;
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

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" +Consolidated_Financial_And_Key_StatsPortletKeys.CONSOLIDATED_FINANCIAL_AND_KEY_STATS,
		"mvc.command.name=" + Consolidated_Financial_And_Key_StatsPortletKeys.FINANCIAL_KEY_STATS,
		}, 
service = MVCResourceCommand.class)
public class ConsolidatedFinancialAndKeyStatsMVCResource implements MVCResourceCommand {
	private static Log _log = LogFactoryUtil.getLog(ConsolidatedFinancialAndKeyStatsMVCResource.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info(" Started ConsolidatedFinancialAndKeyStatsMVCResource.serveResource () >>>>> ");
		
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
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		
		String fileName = uploadPortletRequest.getFileName("financialAndKeyStatsFormXlsx");

		File file = uploadPortletRequest.getFile("financialAndKeyStatsFormXlsx");

		String mimeType = uploadPortletRequest.getContentType("financialAndKeyStatsFormXlsx");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Form 1
		JSONArray balanceSheetJsonArray = JSONFactoryUtil.createJSONArray();
		List<BalanceSheet> balanceSheetList = new ArrayList<BalanceSheet>();
		//Form 2
		JSONArray combinedIncomeStatementJsonArray = JSONFactoryUtil.createJSONArray();
		List<CombinedIncomeStatement> combinedIncomeStatementList = new ArrayList<CombinedIncomeStatement>();
		//Form 3
		JSONArray consolidatedBalanceSheetJsonArray = JSONFactoryUtil.createJSONArray();
		List<ConsolidatedBalanceSheet> consolidatedBalanceSheetList = new ArrayList<ConsolidatedBalanceSheet>();
		//Form 4
		JSONArray incomeStatementSheetJsonArray = JSONFactoryUtil.createJSONArray();
		List<IncomeStatement> incomeStatementSheetList = new ArrayList<IncomeStatement>();
		//Form 5
		JSONArray keyStatisticsJsonArray = JSONFactoryUtil.createJSONArray();
		List<KeyStatistics> keyStatisticsList = new ArrayList<KeyStatistics>();
		//Form 6
		JSONArray noteOfBalanceSheetJsonArray = JSONFactoryUtil.createJSONArray();
		List<NoteOfBalanceSheet> noteOfBalanceSheetList = new ArrayList<NoteOfBalanceSheet>();
		// Form 7
		JSONArray noteOfComibnedBalanceSheetJsonArray = JSONFactoryUtil.createJSONArray();
		List<NoteOfCombinedBalanceSheet> noteOfComibnedBalanceSheetList = new ArrayList<NoteOfCombinedBalanceSheet>();
				
		
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
		
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
			XSSFCell fileNamecell = xxx.createCell(2);
			fileNamecell.setCellValue("As_on_date");
			
			boolean isexcelhaserror = false;
			
			//sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, workbook);
			
			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				// return
				return resultJsonObject;
			}else {
				
				
				resultJsonObject = ParseBalanceSheet.saveBalanceSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, balanceSheetJsonArray, balanceSheetList, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = ParseCombinedIncomeStatementSheet.saveCombinedBalanceSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, combinedIncomeStatementJsonArray, combinedIncomeStatementList, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject =ParseConsolidatedBalanceSheet.saveConsolidatedBalanceSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, consolidatedBalanceSheetJsonArray, consolidatedBalanceSheetList, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject =ParseIncomeStatementSheet.saveInbcomeStatementSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, incomeStatementSheetJsonArray, incomeStatementSheetList, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject =ParseKeyStatics.saveKeyStaticsSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, keyStatisticsJsonArray, keyStatisticsList, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				ParseNoteOfBalanceSheet.savenoteOfBalanceSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, noteOfBalanceSheetJsonArray, noteOfBalanceSheetList, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				ParseNoteOfCombinedBalanceSheet.saveNoteOfCombinedBalanceSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, noteOfComibnedBalanceSheetJsonArray, noteOfComibnedBalanceSheetList, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				
				if(!resultJsonObject.getBoolean("status")) {
					return resultJsonObject;
				}else {
					resultJsonObject = pdfFileUpload(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
							
				}
				
				if(!resultJsonObject.getBoolean("status")) {
					return resultJsonObject;
				}else {
					resultJsonObject = pdfFileUpload(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
							
				}
				
				if (!isexcelhaserror) {
					
					try {
						BalanceSheetLocalServiceUtil.addBalanceSheet(balanceSheetList);
						CombinedIncomeStatementLocalServiceUtil.addCombinedIncomeStatement(combinedIncomeStatementList);
						ConsolidatedBalanceSheetLocalServiceUtil.addConsolidatedBalanceSheet(consolidatedBalanceSheetList);
						IncomeStatementLocalServiceUtil.addIncomeStatement(incomeStatementSheetList);
						KeyStatisticsLocalServiceUtil.addKeyStatistics(keyStatisticsList);
						NoteOfBalanceSheetLocalServiceUtil.addNoteOfBalanceSheet(noteOfBalanceSheetList);
						NoteOfCombinedBalanceSheetLocalServiceUtil.addNoteOfCombinedBalanceSheet(noteOfComibnedBalanceSheetList);
					
					} catch (Exception e1) {
						_log.error(e1);
						 resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						 return resultJsonObject;
					}
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest,title);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
						//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
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
	public JSONObject pdfFileUpload(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
        _log.info("pdfFileUpload:::::::::::::::::::::::::::");
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		Long pdfFileEntryId = 0l;
		pdfFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "Annual Audited Scheme Financials");
		_log.info("pdfFileEntryId: " + pdfFileEntryId);
		JSONObject pdfFileEntryJO = JSONFactoryUtil.createJSONObject();
		pdfFileEntryJO.put("Name", "Audited Scheme Financials");
		pdfFileEntryJO.put("fileEntryId", pdfFileEntryId);
		
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		_log.info("fileList---------"+fileList);
		fileList.put(pdfFileEntryJO);
		_log.info("fileList: " + fileList.toString());
		resultJsonObject.put("fileList", fileList);
		
		resultJsonObject.put("status", true);
		
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}
