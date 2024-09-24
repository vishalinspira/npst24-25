package Vote_Report.mvc;

import com.daily.average.service.model.VotingReportConsolidated;
import com.daily.average.service.model.VotingReportNoOfResolution;
import com.daily.average.service.model.VotingReportSes;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.daily.average.service.service.VotingReportConsolidatedLocalServiceUtil;
import com.daily.average.service.service.VotingReportNoOfResolutionLocalServiceUtil;
import com.daily.average.service.service.VotingReportSesLocalServiceUtil;
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

import Vote_Report.constants.Vote_ReportPortletKeys;
import Vote_Report.util.ValidateSheetName;
import Vote_Report.util.Vrconsolidated;
import Vote_Report.util.Vrresolution;
import Vote_Report.util.Vrses;
import nps.email.api.api.ExcelValidationAn10Api;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" +  Vote_ReportPortletKeys.VOTE_REPORT,
				"mvc.command.name=/saveVoteReportfile/data"
		},
		service = MVCResourceCommand.class
)


public class Vote_ReportMVCResources implements MVCResourceCommand{
	private static Log _log = LogFactoryUtil.getLog(Vote_ReportMVCResources.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
			_log.info("Vote_ReportMVCResources Serve Resource method");
			
			_log.info("Vote_ReportMVCResources::::::::::::::::::::::::::");
			
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
		
		
		Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		
		String fileName = uploadPortletRequest.getFileName("votereportfile");

		File file = uploadPortletRequest.getFile("votereportfile");

		String mimeType = uploadPortletRequest.getContentType("votereportfile");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Form 1
		JSONArray vrconsolidatedArray = JSONFactoryUtil.createJSONArray();
		List<VotingReportConsolidated> vrconsolidatedList = new ArrayList<VotingReportConsolidated>();
		
		//Form 2
		JSONArray vrnoofresolutionArray = JSONFactoryUtil.createJSONArray();
		List<VotingReportNoOfResolution> vrnoofresolutionList = new ArrayList<VotingReportNoOfResolution>();
		
		//Form 3
		JSONArray vrsesArray = JSONFactoryUtil.createJSONArray();
		List<VotingReportSes> vrsesList = new ArrayList<VotingReportSes>();
		
	
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		 //resultJsonObject.put("status",true);
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
			
			List<String> errorList = ValidateSheetName.ValidateExcelSheetName(file, workbook);
			
			if (errorList.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				StringBuilder sb = new StringBuilder();
				for(String L : errorList) {
					if(errorList.size() == 1) {
						sb.append(L);
					}else {
						sb.append(L);
						sb.append(",");
					}
				}
				
				String errorString = sb.toString();
				resultJsonObject.put("errorSheetNameList", errorString);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				// return
				return resultJsonObject;
			}else {
				Vrconsolidated.saveVrconsolidated(file, workbook, userId, errorArray, xx, isexcelhaserror, vrconsolidatedArray, vrconsolidatedList,decimalFormat,reportUploadLogId);
				Vrresolution.saveVrresolution(file, workbook, userId, errorArray, xx, isexcelhaserror, vrnoofresolutionArray, vrnoofresolutionList,decimalFormat,reportUploadLogId);
				//Commented not save Vrses data in database
				Vrses.saveVrses(file, workbook, userId, errorArray, xx, isexcelhaserror, vrsesArray, vrsesList,decimalFormat,reportUploadLogId);
			
				if (!isexcelhaserror) {
					
					VotingReportConsolidatedLocalServiceUtil.addVotingReportConsolidated(vrconsolidatedList);
					_log.info("form 13 datacount"+vrconsolidatedList.size());
					
					VotingReportNoOfResolutionLocalServiceUtil.addVotingReportNoOfResolution(vrnoofresolutionList);
					_log.info("form 13 datacount"+vrnoofresolutionList.size());
					//Commented not save Vrses data in database
					VotingReportSesLocalServiceUtil.addVotingReportSes(vrsesList);
					_log.info("form 13 datacount"+vrsesList.size());
					
					
					
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("votereportfile");

		File file = uploadPortletRequest.getFile("votereportfile");

		String mimeType = uploadPortletRequest.getContentType("votereportfile");

		String title = fileName;

		String description = "votereportfile";

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
		ReportUploadLogPFMAMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}
