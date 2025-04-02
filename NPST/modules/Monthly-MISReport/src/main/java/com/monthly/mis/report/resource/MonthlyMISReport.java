package com.monthly.mis.report.resource;

import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalServiceUtil;
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
import com.monthly.mis.report.constants.MonthlyMISReportPortletKeys;
import com.monthly.mis.report.util.EntityWiseAgeing;
import com.monthly.mis.report.util.EntityWiseOutAnalysis;
import com.monthly.mis.report.util.GrievanceOutstanding;
import com.monthly.mis.report.util.NpsCategoryWiseAgeing;
import com.monthly.mis.report.util.NpsCategoryWiseGrievance;
import com.monthly.mis.report.util.NpsEntityWiseAgeing;
import com.monthly.mis.report.util.NpsLiteCategoryWiseAgeing;
import com.monthly.mis.report.util.NpsLiteCategoryWiseGrievance;
import com.monthly.mis.report.util.NpsLiteEntityWiseAgeing;
import com.monthly.mis.report.util.NpsLiteSchemeMonthAnalysis;
import com.monthly.mis.report.util.NpsSchemeMonthAnalysis;
import com.monthly.mis.report.util.ReferralsHandledAtNps;
import com.monthly.mis.report.util.ResolutionSpeed;
import com.monthly.mis.report.util.TotalGrievanceReceived;
import com.monthly.mis.report.util.ValidateFileName;
import com.monthly.mis.report.util.ValidateSheetName;

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

import compliance.service.model.MnEntityWiseAgeing;
import compliance.service.model.MnEntityWiseOutAnalysis;
import compliance.service.model.MnGrievanceOutstanding;
import compliance.service.model.MnNpsCategoryWiseAgeing;
import compliance.service.model.MnNpsCategoryWiseGrievance;
import compliance.service.model.MnNpsEntityWiseAgeing;
import compliance.service.model.MnNpsLiteCategoryWiseAgeing;
import compliance.service.model.MnNpsLiteCategoryWiseGrievance;
import compliance.service.model.MnNpsLiteEntityWiseAgeing;
import compliance.service.model.MnNpsLiteSchemeMonthAnalysis;
import compliance.service.model.MnNpsSchemeMonthAnalysis;
import compliance.service.model.MnReferralsHandledAtNps;
import compliance.service.model.MnResolutionSpeed;
import compliance.service.model.MnTotalGrievanceReceived;
import compliance.service.service.MnEntityWiseAgeingLocalServiceUtil;
import compliance.service.service.MnEntityWiseOutAnalysisLocalServiceUtil;
import compliance.service.service.MnGrievanceOutstandingLocalServiceUtil;
import compliance.service.service.MnNpsCategoryWiseAgeingLocalServiceUtil;
import compliance.service.service.MnNpsCategoryWiseGrievanceLocalServiceUtil;
import compliance.service.service.MnNpsEntityWiseAgeingLocalServiceUtil;
import compliance.service.service.MnNpsLiteCategoryWiseAgeingLocalServiceUtil;
import compliance.service.service.MnNpsLiteCategoryWiseGrievanceLocalServiceUtil;
import compliance.service.service.MnNpsLiteEntityWiseAgeingLocalServiceUtil;
import compliance.service.service.MnNpsLiteSchemeMonthAnalysisLocalServiceUtil;
import compliance.service.service.MnNpsSchemeMonthAnalysisLocalServiceUtil;
import compliance.service.service.MnReferralsHandledAtNpsLocalServiceUtil;
import compliance.service.service.MnResolutionSpeedLocalServiceUtil;
import compliance.service.service.MnTotalGrievanceReceivedLocalServiceUtil;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + MonthlyMISReportPortletKeys.MONTHLYMISREPORT,
		"mvc.command.name=" + MonthlyMISReportPortletKeys.misReport, 
		}, 
service = MVCResourceCommand.class)

public class MonthlyMISReport implements MVCResourceCommand {

	private static Log _log = LogFactoryUtil.getLog(MonthlyMISReport.class);
	
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
		String cra = IntermediaryListLocalServiceUtil.fetchIntermediaryByReportLogId(reportUploadLogId);
		String fileName = uploadPortletRequest.getFileName("misFile");

		File file = uploadPortletRequest.getFile("misFile");

		String mimeType = uploadPortletRequest.getContentType("misFile");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Grievances_Outstanding
		JSONArray grievanceOutstandingJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnGrievanceOutstanding> grievanceOutstandingList = new ArrayList<MnGrievanceOutstanding>();
		
		//Total_Grievance_Received_Resolv
		JSONArray totalGrievanceReceivedJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnTotalGrievanceReceived> totalGrievanceReceivedList = new ArrayList<MnTotalGrievanceReceived>();
		
		//NPS_Scheme_Month_Analysis
		JSONArray npsSchemeMonthAnalysisJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNpsSchemeMonthAnalysis> npsSchemeMonthAnalysisList = new ArrayList<MnNpsSchemeMonthAnalysis>();
		
		//NPSLite_Scheme_Month_Analysis
		JSONArray npsLiteSchemeMonthAnalysisJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNpsLiteSchemeMonthAnalysis> npsLiteSchemeMonthAnalysisList = new ArrayList<MnNpsLiteSchemeMonthAnalysis>();
		
		//Resolution Speed Analysis
		JSONArray resolutionSpeedJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnResolutionSpeed> resolutionSpeedList = new ArrayList<MnResolutionSpeed>();
		
		//Entity wise ageing Analysis
		JSONArray entityWiseAgeingJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnEntityWiseAgeing> entityWiseAgeingList = new ArrayList<MnEntityWiseAgeing>();
		
		//EntityWise Outstanding Analysis
		JSONArray entityWiseOutAnalysisJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnEntityWiseOutAnalysis> entityWiseOutAnalysisList = new ArrayList<MnEntityWiseOutAnalysis>();
		
		//NPS Entity wise ageing
		JSONArray npsEntityWiseAgeingJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNpsEntityWiseAgeing> npsEntityWiseAgeingList = new ArrayList<MnNpsEntityWiseAgeing>();
		
		//NPSLite Entity wise ageing
		JSONArray npsLiteEntityWiseAgeingJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNpsLiteEntityWiseAgeing> npsLiteEntityWiseAgeingList = new ArrayList<MnNpsLiteEntityWiseAgeing>();
		
		//NPS Category wise ageing
		JSONArray npsCategoryWiseAgeingJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNpsCategoryWiseAgeing> npsCategoryWiseAgeingList = new ArrayList<MnNpsCategoryWiseAgeing>();
		
		//NPSLite Category wise ageing
		JSONArray npsLiteCategoryWiseAgeingJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNpsLiteCategoryWiseAgeing> npsLiteCategoryWiseAgeingList = new ArrayList<MnNpsLiteCategoryWiseAgeing>();
		
		//NPS Category wise grievance
		JSONArray npsCategoryWiseGrievanceJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNpsCategoryWiseGrievance> npsCategoryWiseGrievanceList = new ArrayList<MnNpsCategoryWiseGrievance>();
		
		//NPSLite Category wise grievance
		JSONArray npsLiteCategoryWiseGrievanceJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNpsLiteCategoryWiseGrievance> npsLiteCategoryWiseGrievanceList = new ArrayList<MnNpsLiteCategoryWiseGrievance>();
		
		//Referral Handled at NPS
		JSONArray referralsHandledAtNpsJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnReferralsHandledAtNps> referralsHandledAtNpsList = new ArrayList<MnReferralsHandledAtNps>();

		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(ValidateFileName.isValidfile(fileName)) {
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
			fileNamecell.setCellValue("Pension Fund");
			
			boolean isexcelhaserror = false;
			
			//sheet validate
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

				return resultJsonObject;
			}
			
			//deleting old sheet data
			MnGrievanceOutstandingLocalServiceUtil.deleteMnGrievanceOutstandingByReportUploadLogId(reportUploadLogId);
			MnTotalGrievanceReceivedLocalServiceUtil.deleteMnTotalGrievanceReceivedByReportUploadLogId(reportUploadLogId);
			MnNpsSchemeMonthAnalysisLocalServiceUtil.deleteMnNpsSchemeMonthAnalysisByReportUploadLogId(reportUploadLogId);
			MnNpsLiteSchemeMonthAnalysisLocalServiceUtil.deleteMnNpsLiteSchemeMonthAnalysisByReportUploadLogId(reportUploadLogId);
			MnResolutionSpeedLocalServiceUtil.deleteMnResolutionSpeedByReportUploadLogId(reportUploadLogId);
			MnEntityWiseAgeingLocalServiceUtil.deleteMnEntityWiseAgeingByReportUploadLogId(reportUploadLogId);
			MnEntityWiseOutAnalysisLocalServiceUtil.deleteMnEntityWiseOutAnalysisByReportUploadLogId(reportUploadLogId);
			MnNpsEntityWiseAgeingLocalServiceUtil.deleteMnNpsEntityWiseAgeingByReportUploadLogId(reportUploadLogId);
			MnNpsLiteEntityWiseAgeingLocalServiceUtil.deleteMnNpsLiteEntityWiseAgeingByReportUploadLogId(reportUploadLogId);
			MnNpsCategoryWiseAgeingLocalServiceUtil.deleteMnNpsCategoryWiseAgeingByReportUploadLogId(reportUploadLogId);
			MnNpsCategoryWiseGrievanceLocalServiceUtil.deleteMnNpsCategoryWiseGrievanceByReportUploadLogId(reportUploadLogId);
			MnNpsLiteCategoryWiseGrievanceLocalServiceUtil.deleteMnNpsLiteCategoryWiseGrievanceByReportUploadLogId(reportUploadLogId);
			MnReferralsHandledAtNpsLocalServiceUtil.deleteMnReferralsHandledAtNpsByReportUploadLogId(reportUploadLogId);
			MnNpsLiteCategoryWiseAgeingLocalServiceUtil.deleteMnNpsLiteCategoryWiseAgeingByReportUploadLogId(reportUploadLogId);
			
			
			
			resultJsonObject = GrievanceOutstanding.saveGrievanceOutstanding(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, grievanceOutstandingJsonArray, grievanceOutstandingList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = TotalGrievanceReceived.saveTotalGrievanceReceived(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, totalGrievanceReceivedJsonArray, totalGrievanceReceivedList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsSchemeMonthAnalysis.saveNpsSchemeMonthAnalysis(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, npsSchemeMonthAnalysisJsonArray, npsSchemeMonthAnalysisList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsLiteSchemeMonthAnalysis.saveNpsLiteSchemeMonthAnalysis(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, npsLiteSchemeMonthAnalysisJsonArray, npsLiteSchemeMonthAnalysisList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = ResolutionSpeed.saveResolutionSpeed(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, resolutionSpeedJsonArray, resolutionSpeedList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = EntityWiseAgeing.saveEntityWiseAgeing(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, entityWiseAgeingJsonArray, entityWiseAgeingList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = EntityWiseOutAnalysis.saveEntityWiseOutAnalysis(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, entityWiseOutAnalysisJsonArray, entityWiseOutAnalysisList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsEntityWiseAgeing.saveNpsEntityWiseAgeing(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, npsEntityWiseAgeingJsonArray, npsEntityWiseAgeingList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsLiteEntityWiseAgeing.saveNpsLiteEntityWiseAgeing(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, npsLiteEntityWiseAgeingJsonArray, npsLiteEntityWiseAgeingList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsCategoryWiseAgeing.saveNpsCategoryWiseAgeing(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, npsCategoryWiseAgeingJsonArray, npsCategoryWiseAgeingList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsLiteCategoryWiseAgeing.saveNpsLiteCategoryWiseAgeing(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, npsLiteCategoryWiseAgeingJsonArray, npsLiteCategoryWiseAgeingList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsCategoryWiseGrievance.saveNpsCategoryWiseGrievance(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, npsCategoryWiseGrievanceJsonArray, npsCategoryWiseGrievanceList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsLiteCategoryWiseGrievance.saveNpsLiteCategoryWiseGrievance(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, npsLiteCategoryWiseGrievanceJsonArray, npsLiteCategoryWiseGrievanceList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = ReferralsHandledAtNps.saveReferralsHandledAtNps(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, referralsHandledAtNpsJsonArray, referralsHandledAtNpsList, cra, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			
			if (!isexcelhaserror) {
				
				try {
					MnGrievanceOutstandingLocalServiceUtil.addMnGrievanceOutstanding(grievanceOutstandingList);
					MnTotalGrievanceReceivedLocalServiceUtil.addMnTotalGrievanceReceived(totalGrievanceReceivedList);
					MnNpsSchemeMonthAnalysisLocalServiceUtil.addMnNpsSchemeMonthAnalysis(npsSchemeMonthAnalysisList);
					MnNpsLiteSchemeMonthAnalysisLocalServiceUtil.addMnNpsLiteSchemeMonthAnalysis(npsLiteSchemeMonthAnalysisList);
					MnResolutionSpeedLocalServiceUtil.addMnResolutionSpeed(resolutionSpeedList);
					MnEntityWiseAgeingLocalServiceUtil.addMnEntityWiseAgeing(entityWiseAgeingList);
					MnEntityWiseOutAnalysisLocalServiceUtil.addMnEntityWiseOutAnalysis(entityWiseOutAnalysisList);
					MnNpsEntityWiseAgeingLocalServiceUtil.addMnNpsEntityWiseAgeing(npsEntityWiseAgeingList);
					MnNpsLiteEntityWiseAgeingLocalServiceUtil.addMnNpsLiteEntityWiseAgeing(npsLiteEntityWiseAgeingList);
					MnNpsCategoryWiseAgeingLocalServiceUtil.addMnNpsCategoryWiseAgeing(npsCategoryWiseAgeingList);
					MnNpsLiteCategoryWiseAgeingLocalServiceUtil.addMnNpsLiteCategoryWiseAgeing(npsLiteCategoryWiseAgeingList);
					MnNpsCategoryWiseGrievanceLocalServiceUtil.addMnNpsCategoryWiseGrievance(npsCategoryWiseGrievanceList);
					MnNpsLiteCategoryWiseGrievanceLocalServiceUtil.addMnNpsLiteCategoryWiseGrievance(npsLiteCategoryWiseGrievanceList);
					MnReferralsHandledAtNpsLocalServiceUtil.addMnReferralsHandledAtNps(referralsHandledAtNpsList);
				} catch (Exception e1) {
					resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
					 return resultJsonObject;
				}

				Long fileEntryId = 0l;
				fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					errorExcel.close();
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
					//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
				} catch (Exception e) {
					_log.error("error while update report upload log :  "+e);
					resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
					 return resultJsonObject;
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
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg","Please upload files with a valid filename.");
			return resultJsonObject;
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
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Date date =new Date();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime() + uploadPortletRequest.getFileName("misFile");

		File file = uploadPortletRequest.getFile("misFile");

		String mimeType = uploadPortletRequest.getContentType("misFile");

		String title = fileName;

		String description = "misFile";

		long repositoryId = themeDisplay.getScopeGroupId();
	
		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getPrimaryKey();

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
		ReportUploadLogGrievancesLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	

}
