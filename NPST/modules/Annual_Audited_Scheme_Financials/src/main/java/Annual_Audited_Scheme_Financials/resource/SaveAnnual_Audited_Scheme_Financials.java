package Annual_Audited_Scheme_Financials.resource;

import com.daily.average.service.model.annual_audited_scheme_financials_1;
import com.daily.average.service.model.annual_audited_scheme_financials_2;
import com.daily.average.service.model.annual_audited_scheme_financials_3;
import com.daily.average.service.model.annual_audited_scheme_financials_4;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.daily.average.service.service.annual_audited_scheme_financials_1LocalServiceUtil;
import com.daily.average.service.service.annual_audited_scheme_financials_2LocalServiceUtil;
import com.daily.average.service.service.annual_audited_scheme_financials_3LocalServiceUtil;
import com.daily.average.service.service.annual_audited_scheme_financials_4LocalServiceUtil;
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

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Annual_Audited_Scheme_Financials.constants.Annual_Audited_Scheme_FinancialsPortletKeys;
import Annual_Audited_Scheme_Financials.util.ConsolidatedFinancial;
import Annual_Audited_Scheme_Financials.util.ConsolidatedFinancial2;
import Annual_Audited_Scheme_Financials.util.ConsolidatedFinancial3;
import Annual_Audited_Scheme_Financials.util.ConsolidatedFinancial4;
import Annual_Audited_Scheme_Financials.util.ValidateFileName;
import Annual_Audited_Scheme_Financials.util.validateSheetname1;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name="  + Annual_Audited_Scheme_FinancialsPortletKeys.ANNUAL_AUDITED_SCHEME_FINANCIALS,
		"mvc.command.name=" + Annual_Audited_Scheme_FinancialsPortletKeys.AnnualAuditedSchemeFinancials,
		}, 
service = MVCResourceCommand.class)

public class SaveAnnual_Audited_Scheme_Financials implements MVCResourceCommand{

	private static Log _log = LogFactoryUtil.getLog(SaveAnnual_Audited_Scheme_Financials.class.getName());
	
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
			e.printStackTrace();
		}
		return false;
	}

	private JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		System.out.println("fileUpload:::::::::::::::::::::::::::");
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			e.printStackTrace();
		}
        
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		List<String> fileNames=new ArrayList<String>();
		fileNames.add(uploadPortletRequest.getFileName("auditedSchemeFinancialsPDFFile"));
		fileNames.add(uploadPortletRequest.getFileName("consolidatedFinancialandkeystatsFile"));
		fileNames.add(uploadPortletRequest.getFileName("financialReportDocFile"));
		if(ValidateFileName.validatefileNames(fileNames)) {
		
		
		resultJsonObject = saveVoteFile(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
		
		if(resultJsonObject.getBoolean("status")) {
			
			Long fileEntryId = 0l;
			fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest ,"auditedSchemeFinancialsPDFFile");
			Long excel_1_fileEntryId = 0l;
			excel_1_fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest ,"consolidatedFinancialandkeystatsFile");
			Long doc_1_fileEntryId = 0l;
			doc_1_fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest ,"financialReportDocFile");
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			JSONObject auditedSchemeFinancialsPDFJO = JSONFactoryUtil.createJSONObject();
			auditedSchemeFinancialsPDFJO.put("Name", "Annual report and audited scheme financials");
			auditedSchemeFinancialsPDFJO.put("fileEntryId", fileEntryId);
			jsonArray.put(auditedSchemeFinancialsPDFJO);
			
			JSONObject consolidatedFinancialandkeystatsJO = JSONFactoryUtil.createJSONObject();
			consolidatedFinancialandkeystatsJO.put("Name", "Consolidated Financial, scheme wise financials, schedules and key stats");
			consolidatedFinancialandkeystatsJO.put("fileEntryId", excel_1_fileEntryId);
			jsonArray.put(consolidatedFinancialandkeystatsJO);
			
			JSONObject financialReportDocJO = JSONFactoryUtil.createJSONObject();
			financialReportDocJO.put("Name", "Annual report and notes to accounts");
			financialReportDocJO.put("fileEntryId", doc_1_fileEntryId);
			jsonArray.put(financialReportDocJO);
			
			
			try {
				String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
				updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks, jsonArray.toString());
			//	resultJsonObject.put("pdfURL",  pdfTable(annextureXas, themeDisplay, resourceRequest));
			} catch (Exception e) {
				e.printStackTrace();
			}
		//resultJsonObject.put("status", true);
		}
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg","Please upload files with a valid filename.");
			return resultJsonObject;
		}
	return resultJsonObject;
}

	@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest,String documentName) {
		
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

			System.out.println(e.getMessage());

			e.printStackTrace();
		}
		return 0;
	}
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			System.out.println(folder);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return folder;

	}
	
	
	
	public JSONObject saveVoteFile(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay,
			long userId, ResourceRequest resourceRequest, long reportUploadLogId,
			String statusByUserName, ServiceContext serviceContext, JSONObject resultJsonObject) {
		_log.info("inside saveVoteFile");
		String fileName = uploadPortletRequest.getFileName("consolidatedFinancialandkeystatsFile");
		_log.info("inside fileName ----> "+fileName);

		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		File file = uploadPortletRequest.getFile("consolidatedFinancialandkeystatsFile");

		String mimeType = uploadPortletRequest.getContentType("consolidatedFinancialandkeystatsFile");
		_log.info("type of the file ----> "+mimeType);
				
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		//resultJsonObject.put("status", true);
		if(resultJsonObject.getBoolean("status")) {
			
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray VMJsonArray = JSONFactoryUtil.createJSONArray();
		List<annual_audited_scheme_financials_1> vmList = new ArrayList<annual_audited_scheme_financials_1>();
		List<annual_audited_scheme_financials_2> vmList2 = new ArrayList<annual_audited_scheme_financials_2>();
		List<annual_audited_scheme_financials_3> vmList3 = new ArrayList<annual_audited_scheme_financials_3>();
		List<annual_audited_scheme_financials_4> vmList4 = new ArrayList<annual_audited_scheme_financials_4>();
		
		
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
		
		
		List<String> errorList = validateSheetname1.ValidateExcelSheetName(file, workbook);
		
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
					e.printStackTrace();
				}
				// return
				return resultJsonObject;
			}else {

				ConsolidatedFinancial.saveConsolidatedFinancial(file, workbook, userId, errorArray, xx, isexcelhaserror,decimalFormat, VMJsonArray, vmList,reportUploadLogId);
				_log.info("vmList ::::::::::::::::::::: "+vmList.toString());
				
				ConsolidatedFinancial2.saveConsolidatedFinancial2(file, workbook, userId, errorArray, xx, isexcelhaserror,decimalFormat, VMJsonArray, vmList2,reportUploadLogId);
				_log.info("vmList2 ::::::::::::::::::::: "+vmList2.toString());
				ConsolidatedFinancial3.saveConsolidatedFinancial3(file, workbook, userId, errorArray, xx, isexcelhaserror,decimalFormat, VMJsonArray, vmList3,reportUploadLogId);
				_log.info("vmList3 ::::::::::::::::::::: "+vmList3.toString());
				ConsolidatedFinancial4.saveConsolidatedFinancial4(file, workbook, userId, errorArray, xx, isexcelhaserror,decimalFormat, VMJsonArray, vmList4,reportUploadLogId);
				_log.info("vmList4 ::::::::::::::::::::: "+vmList4.toString());
				
				
				if (!isexcelhaserror && !isNoError) {
					
					try {
						annual_audited_scheme_financials_1LocalServiceUtil.addannual_audited_scheme_financials_1(vmList);
						annual_audited_scheme_financials_2LocalServiceUtil.addannual_audited_scheme_financials_2(vmList2);
						annual_audited_scheme_financials_3LocalServiceUtil.addannual_audited_scheme_financials_3(vmList3);
						annual_audited_scheme_financials_4LocalServiceUtil.addannual_audited_scheme_financials_4(vmList4);
					} catch (Exception e1) {
						_log.error(e1);
						resultJsonObject.put("status", false);
						resultJsonObject.put("msg", "Error while parsing file");
						return resultJsonObject;
					}
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "consolidatedFinancialandkeystats");
					JSONObject voteFileFileEntryJO = JSONFactoryUtil.createJSONObject();
					voteFileFileEntryJO.put("Name", "consolidatedFinancialandkeystats");
					voteFileFileEntryJO.put("fileEntryId", fileEntryId);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						fileList.put(voteFileFileEntryJO);
						resultJsonObject.put("fileList", fileList);
//						updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  
//						WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
//						resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
					} catch (Exception e) {
						e.printStackTrace();
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
						e.printStackTrace();
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
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return downloadUrl;
	}
	
	
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, 
			long reportUploadLogId, int status, String statusByUserName, 
			ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog2(createDate, createdBy,
		fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, 
		serviceContext,remarks,fileList);
//		(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}

		
