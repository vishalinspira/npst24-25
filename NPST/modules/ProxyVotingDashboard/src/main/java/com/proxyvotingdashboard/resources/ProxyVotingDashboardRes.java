package com.proxyvotingdashboard.resources;

import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
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
import com.proxyvotingdashboard.constants.ProxyVotingDashboardPortletKeys;
import com.proxyvotingdashboard.util.IIASVotingReport;
import com.proxyvotingdashboard.util.NonUnanimous;
import com.proxyvotingdashboard.util.SesVoteMatrix;
import com.proxyvotingdashboard.util.ValidateSheetName;
import com.proxyvotingdashboard.util.ValidateSheetName1;
import com.proxyvotingdashboard.util.ValidateSheetName2;

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

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import compliance.service.model.MnNonUnanimous;
import compliance.service.model.VoteMatrix;
import compliance.service.model.Votingreport;
import compliance.service.service.MnNonUnanimousLocalServiceUtil;
import compliance.service.service.VoteMatrixLocalServiceUtil;
import compliance.service.service.VotingreportLocalServiceUtil;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name="+ ProxyVotingDashboardPortletKeys.PROXYVOTINGDASHBOARD ,
		"mvc.command.name=" + ProxyVotingDashboardPortletKeys.proxyVotingDashboard ,
		}, 
service = MVCResourceCommand.class)
public class ProxyVotingDashboardRes implements MVCResourceCommand {
private static Log _log = LogFactoryUtil.getLog(ProxyVotingDashboardRes.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
	
		_log.info(" Serve Resource method");
		
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
		//_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		//_log.info("reportUploadLogId" + reportUploadLogId);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("fileList", JSONFactoryUtil.createJSONArray());
		resultJsonObject = saveVoteFile(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext,resultJsonObject);
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveVotingReport(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext,resultJsonObject);
			
		}
		 
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveNonUnanimous(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext,resultJsonObject);
			
		}
		
		
		String fileList = resultJsonObject.getJSONArray("fileList").toString();
		long fileEntryId = resultJsonObject.getLong("fileEntryId");
		String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
		updateReportLog(userId, fileEntryId , true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks, fileList );
		return resultJsonObject;
	}
	
	public JSONObject saveVoteFile(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay, long userId, ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext, JSONObject resultJsonObject) {
		String fileName = uploadPortletRequest.getFileName("voteFile");

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		File file = uploadPortletRequest.getFile("voteFile");

		String mimeType = uploadPortletRequest.getContentType("voteFile");

		String title = fileName;
		
		//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray VMJsonArray = JSONFactoryUtil.createJSONArray();
		List<VoteMatrix> vmList = new ArrayList<VoteMatrix>();
		
		
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;

		/**
		 * Create error excel
		 */
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		XSSFRow xxx = xx.createRow(1);
		boolean isexcelhaserror = false;
		boolean isNoError = false;
		
		
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

				SesVoteMatrix.saveSesVoteMatrix(file, workbook, userId, errorArray, xx, isexcelhaserror, VMJsonArray, vmList, decimalFormat,reportUploadLogId);
				
				
				if (!isexcelhaserror && !isNoError) {
					VoteMatrixLocalServiceUtil.addVoteMatrix(vmList);
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "voteFile");
					JSONObject voteFileFileEntryJO = JSONFactoryUtil.createJSONObject();
					voteFileFileEntryJO.put("Name", "voteFile");
					voteFileFileEntryJO.put("fileEntryId", fileEntryId);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						
							fileList.put(voteFileFileEntryJO);
						resultJsonObject.put("fileList", fileList);
						//updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
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
	public JSONObject saveVotingReport(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay, long userId, ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext, JSONObject resultJsonObject) {
		String fileName = uploadPortletRequest.getFileName("votingReport");

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		File file = uploadPortletRequest.getFile("votingReport");

		String mimeType = uploadPortletRequest.getContentType("votingReport");

		String title = fileName;
		
		//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray votingJsonArray = JSONFactoryUtil.createJSONArray();
		List<Votingreport> votingList = new ArrayList<Votingreport>();
		
		
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;

		/**
		 * Create error excel
		 */
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		XSSFRow xxx = xx.createRow(1);
		boolean isexcelhaserror = false;
		boolean isNoError = false;
		
		
		List<String> errorList = ValidateSheetName1.ValidateExcelSheetName(file, workbook);
		
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

				IIASVotingReport.saveIIASVotingReport(file, workbook, userId, errorArray, xx, isexcelhaserror, votingJsonArray, votingList, decimalFormat,reportUploadLogId);
				
				if (!isexcelhaserror && !isNoError) {
					VotingreportLocalServiceUtil.addVotingreport(votingList);
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "votingReport");
					JSONObject votingReportFileEntryJO = JSONFactoryUtil.createJSONObject();
					votingReportFileEntryJO.put("Name", "votingReport");
					votingReportFileEntryJO.put("fileEntryId", fileEntryId);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						
						
						fileList.put(votingReportFileEntryJO);
						resultJsonObject.put("fileList", fileList);
						//updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
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
	
	
	
	public JSONObject saveNonUnanimous(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay, long userId, ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext, JSONObject resultJsonObject) {
		String fileName = uploadPortletRequest.getFileName("nonUnanimousFile");

		File file = uploadPortletRequest.getFile("nonUnanimousFile");

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		String mimeType = uploadPortletRequest.getContentType("nonUnanimousFile");

		String title = fileName;
		
		//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray NUJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnNonUnanimous> nuList = new ArrayList<MnNonUnanimous>();
		
		
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;

		/**
		 * Create error excel
		 */
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		XSSFRow xxx = xx.createRow(1);
		boolean isexcelhaserror = false;
		boolean isNoError = false;
		
		
		List<String> errorList = ValidateSheetName2.ValidateExcelSheetName(file, workbook);
		
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

				NonUnanimous.saveNonUnanimous(file, workbook, userId, errorArray, xx, isexcelhaserror, NUJsonArray, nuList, decimalFormat,reportUploadLogId);
				
				
				if (!isexcelhaserror && !isNoError) {
					MnNonUnanimousLocalServiceUtil.addMnNonUnanimous(nuList);
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "nonUnanimousFile");
					JSONObject nonUnanimousFileEntryJO = JSONFactoryUtil.createJSONObject();
					nonUnanimousFileEntryJO.put("Name", "nonUnanimousFile");
					nonUnanimousFileEntryJO.put("fileEntryId", fileEntryId);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						
						fileList.put(nonUnanimousFileEntryJO);
						resultJsonObject.put("fileList", fileList);
						//updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
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
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest, String document) {
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime() + uploadPortletRequest.getFileName(document);

		File file = uploadPortletRequest.getFile(document);

		String mimeType = uploadPortletRequest.getContentType(document);

		String title = fileName;

		String description = "";

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
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog2(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks, fileList);
		//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

	
	
	
	
}
