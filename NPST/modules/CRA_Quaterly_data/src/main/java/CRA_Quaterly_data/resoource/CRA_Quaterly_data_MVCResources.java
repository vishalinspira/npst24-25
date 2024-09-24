package CRA_Quaterly_data.resoource;

import com.daily.average.service.service.ReportUploadLogCRALocalServiceUtil;
import com.daily.pfm.service.model.QrAnnuityRequest;
import com.daily.pfm.service.model.QrAnnuityRequestAgeing;
import com.daily.pfm.service.model.QrCauseWiseAgeingAnalysis;
import com.daily.pfm.service.model.QrCauseWisePatternAnalysis;
import com.daily.pfm.service.model.QrExercisedExitOption;
import com.daily.pfm.service.model.QrExitAndWithdrawal;
import com.daily.pfm.service.model.QrGapAnalysis;
import com.daily.pfm.service.model.QrSectorWiseAgeingAnalysis;
import com.daily.pfm.service.model.QrSectoralAnalysis;
import com.daily.pfm.service.service.QrAnnuityRequestAgeingLocalServiceUtil;
import com.daily.pfm.service.service.QrAnnuityRequestLocalServiceUtil;
import com.daily.pfm.service.service.QrCauseWiseAgeingAnalysisLocalServiceUtil;
import com.daily.pfm.service.service.QrCauseWisePatternAnalysisLocalServiceUtil;
import com.daily.pfm.service.service.QrExercisedExitOptionLocalServiceUtil;
import com.daily.pfm.service.service.QrExitAndWithdrawalLocalServiceUtil;
import com.daily.pfm.service.service.QrGapAnalysisLocalServiceUtil;
import com.daily.pfm.service.service.QrSectorWiseAgeingAnalysisLocalServiceUtil;
import com.daily.pfm.service.service.QrSectoralAnalysisLocalServiceUtil;
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
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import CRA_Quaterly_data.Util.QrAnnuityRequestAgeing_7;
import CRA_Quaterly_data.Util.QrAnnuityRequest_6;
import CRA_Quaterly_data.Util.QrCauseWiseAgeingAnalysis_5;
import CRA_Quaterly_data.Util.QrCauseWisePatternAnalysis_3;
import CRA_Quaterly_data.Util.QrExercisedExitOption_8;
import CRA_Quaterly_data.Util.QrExitAndWithdrawal_1;
import CRA_Quaterly_data.Util.QrGapAnalysis_9;
import CRA_Quaterly_data.Util.QrSectorWiseAgeingAnalysis_4;
import CRA_Quaterly_data.Util.QrSectoralAnalysis_2;
import CRA_Quaterly_data.constants.CRA_Quaterly_dataPortletKeys;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" +  CRA_Quaterly_dataPortletKeys.CRA_QUATERLY_DATA,
				"mvc.command.name=" + CRA_Quaterly_dataPortletKeys.SAVE_CRA_QUATERLY_DATA,
		},
		service = MVCResourceCommand.class
)

public class CRA_Quaterly_data_MVCResources implements MVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(CRA_Quaterly_data_MVCResources.class.getName());
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("welcome to CRA_Quaterly_data_MVCResources::::::::::::::::::");
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
		
		String fileName = uploadPortletRequest.getFileName("craQuaterlyfile");

		File file = uploadPortletRequest.getFile("craQuaterlyfile");

		String mimeType = uploadPortletRequest.getContentType("craQuaterlyfile");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Form 1:::QrExitAndWithdrawal:::::
		JSONArray qrExitAndWithdrawalArray = JSONFactoryUtil.createJSONArray();
		List<QrExitAndWithdrawal> qrExitAndWithdrawalList = new ArrayList<QrExitAndWithdrawal>();
		
		//Form 2::::QrSectoralAnalysis:::::
		JSONArray qrSectoralAnalysisArray = JSONFactoryUtil.createJSONArray();
		List<QrSectoralAnalysis> qrSectoralAnalysisList = new ArrayList<QrSectoralAnalysis>();
		
		//Form 3::::QrCauseWisePatternAnalysis:::::
		JSONArray qrCauseWisePatternAnalysisArray = JSONFactoryUtil.createJSONArray();
		List<QrCauseWisePatternAnalysis> qrCauseWisePatternAnalysisList = new ArrayList<QrCauseWisePatternAnalysis>();
		
		
		//Form 4
		JSONArray qrSectorWiseAgeingAnalysisArray = JSONFactoryUtil.createJSONArray();
		List<QrSectorWiseAgeingAnalysis> qrSectorWiseAgeingAnalysisList = new ArrayList<QrSectorWiseAgeingAnalysis>();
		
		//Form 5
		JSONArray qrCauseWiseAgeingAnalysisArray = JSONFactoryUtil.createJSONArray();
		List<QrCauseWiseAgeingAnalysis> qrCauseWiseAgeingAnalysisList = new ArrayList<QrCauseWiseAgeingAnalysis>();
		
		//Form 6
		JSONArray qrAnnuityRequestArray = JSONFactoryUtil.createJSONArray();
		List<QrAnnuityRequest> qrAnnuityRequestList = new ArrayList<QrAnnuityRequest>();
		
		
		//Form 7
		JSONArray qrAnnuityRequestAgeingArray = JSONFactoryUtil.createJSONArray();
		List<QrAnnuityRequestAgeing> qrAnnuityRequestAgeingList = new ArrayList<QrAnnuityRequestAgeing>();
		
		//Form 8
		JSONArray qrExercisedExitOptionArray = JSONFactoryUtil.createJSONArray();
		List<QrExercisedExitOption> qrExercisedExitOptionList = new ArrayList<QrExercisedExitOption>();
		
		//Form 9
		JSONArray qrGapAnalysisArray = JSONFactoryUtil.createJSONArray();
		List<QrGapAnalysis> qrGapAnalysisList = new ArrayList<QrGapAnalysis>();
		
	
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		//resultJsonObject.put("status",true);
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
		
			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;
			
			/**
			 * Create error excel
			 */
			XSSFWorkbook  errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell fileNamecell = xxx.createCell(2);
			fileNamecell.setCellValue("Pension Fund");
			
			List<String> errorList = new ArrayList<String>();
			errorList.add("Exit and Withdrawal");
			errorList.add("Sectoral Analysis");
			errorList.add("Cause-wise Pattern Analysis");
			errorList.add("Sector-wise Ageing analysis");
			errorList.add("Cause-wise Ageing analysis");
			errorList.add("Annuity Request");
			errorList.add("Annuity request ageing");
			errorList.add("Exercised Exit Option");
			errorList.add("Gap Analysis");
			
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					//int sheetcount = 0;
					Iterator<Sheet> sheets = workbook.sheetIterator();
					while (sheets.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets.next();
						if (errorList.contains(sheet.getSheetName())) {
							errorList.remove(sheet.getSheetName());
						}
					}
				
					if (errorList.size()>0) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray(errorList);
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
						
					}
				}
			}catch (Exception e) {
			}
			
			boolean isexcelhaserror = false;
			
			//delete old data first
			QrExitAndWithdrawalLocalServiceUtil.deleteQrExitAndWithdrawalByReportUploadLogId(reportUploadLogId);
			QrSectoralAnalysisLocalServiceUtil.deleteQrSectoralAnalysisByReportUploadLogId(reportUploadLogId);
			QrCauseWisePatternAnalysisLocalServiceUtil.deleteQrCauseWisePatternAnalysisByReportUploadLogId(reportUploadLogId);
			QrCauseWiseAgeingAnalysisLocalServiceUtil.deleteQrCauseWiseAgeingAnalysisByReportUploadLogId(reportUploadLogId);
			QrAnnuityRequestLocalServiceUtil.deleteQrAnnuityRequestByReportUploadLogId(reportUploadLogId);
			QrAnnuityRequestAgeingLocalServiceUtil.deleteQrAnnuityRequestAgeingByReportUploadLogId(reportUploadLogId);
			QrExercisedExitOptionLocalServiceUtil.deleteQrExercisedExitOptionByReportUploadLogId(reportUploadLogId);
			QrGapAnalysisLocalServiceUtil.deleteQrGapAnalysisByReportUploadLogId(reportUploadLogId);
			QrSectorWiseAgeingAnalysisLocalServiceUtil.deleteQrSectorWiseAgeingAnalysisByReportUploadLogId(reportUploadLogId);
			
			resultJsonObject = QrExitAndWithdrawal_1.saveQrExitAndWithdrawal(file, workbook, userId, errorArray, xx, isexcelhaserror, qrExitAndWithdrawalArray, qrExitAndWithdrawalList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = QrSectoralAnalysis_2.saveQrSectoralAnalysis(file, workbook, userId, errorArray, xx, isexcelhaserror, qrSectoralAnalysisArray, qrSectoralAnalysisList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = QrCauseWisePatternAnalysis_3.saveQrCauseWisePatternAnalysis(file, workbook, userId, errorArray, xx, isexcelhaserror, qrCauseWisePatternAnalysisArray, qrCauseWisePatternAnalysisList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = QrSectorWiseAgeingAnalysis_4.saveQrSectorWiseAgeingAnalysis(file, workbook, userId, errorArray, xx, isexcelhaserror, qrSectorWiseAgeingAnalysisArray, qrSectorWiseAgeingAnalysisList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = QrCauseWiseAgeingAnalysis_5.saveQrCauseWiseAgeingAnalysis(file, workbook, userId, errorArray, xx, isexcelhaserror, qrCauseWiseAgeingAnalysisArray, qrCauseWiseAgeingAnalysisList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = QrAnnuityRequest_6.saveQrAnnuityRequest(file, workbook, userId, errorArray, xx, isexcelhaserror, qrAnnuityRequestArray, qrAnnuityRequestList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = QrAnnuityRequestAgeing_7.saveQrAnnuityRequestAgeing(file, workbook, userId, errorArray, xx, isexcelhaserror, qrAnnuityRequestAgeingArray, qrAnnuityRequestAgeingList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = QrExercisedExitOption_8.saveQrExercisedExitOption(file, workbook, userId, errorArray, xx, isexcelhaserror, qrExercisedExitOptionArray, qrExercisedExitOptionList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = QrGapAnalysis_9.saveQrGapAnalysis(file, workbook, userId, errorArray, xx, isexcelhaserror, qrGapAnalysisArray, qrGapAnalysisList,decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			if (!isexcelhaserror) {
				
		//add the data to the tables ::::::::::::::::::::
				try {
					QrExitAndWithdrawalLocalServiceUtil.addQrExitAndWithdrawal(qrExitAndWithdrawalList);
					QrSectoralAnalysisLocalServiceUtil.addQrSectoralAnalysis(qrSectoralAnalysisList);
					QrCauseWisePatternAnalysisLocalServiceUtil.addQrCauseWisePatternAnalysis(qrCauseWisePatternAnalysisList);
					QrSectorWiseAgeingAnalysisLocalServiceUtil.addQrSectorWiseAgeingAnalysis(qrSectorWiseAgeingAnalysisList);
					QrCauseWiseAgeingAnalysisLocalServiceUtil.addQrCauseWiseAgeingAnalysis(qrCauseWiseAgeingAnalysisList);
					QrAnnuityRequestLocalServiceUtil.addQrAnnuityRequest(qrAnnuityRequestList);
					QrAnnuityRequestAgeingLocalServiceUtil.addQrAnnuityRequestAgeing(qrAnnuityRequestAgeingList);;
					QrExercisedExitOptionLocalServiceUtil.addQrExercisedExitOption(qrExercisedExitOptionList);
					QrGapAnalysisLocalServiceUtil.addQrGapAnalysis(qrGapAnalysisList);
				} catch (Exception e) {
					_log.error(e);
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

		String fileName =date.getTime()+ uploadPortletRequest.getFileName("craQuaterlyfile");

		File file = uploadPortletRequest.getFile("craQuaterlyfile");

		String mimeType = uploadPortletRequest.getContentType("craQuaterlyfile");

		String title = fileName;

		String description = "craQuaterlyfile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Annually");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogCRALocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
}