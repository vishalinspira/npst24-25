package com.imf.nps.trust.fee.resource;

import com.daily.average.service.model.ManagementFee;
import com.daily.average.service.model.ManagementRate;
import com.daily.average.service.model.ManagementSummary;
import com.daily.average.service.service.ManagementFeeLocalServiceUtil;
import com.daily.average.service.service.ManagementSummaryLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.daily.pfm.service.model.NpsTrustFeePfmOne;
import com.daily.pfm.service.model.NpsTrustFeePfmsummary;
import com.daily.pfm.service.service.NpsTrustFeePfmOneLocalServiceUtil;
import com.daily.pfm.service.service.NpsTrustFeePfmsummaryLocalServiceUtil;
import com.imf.nps.trust.fee.constants.IMF_NPS_Trust_FeePortletKeys;
import com.investment.management.util.ManagementFeeUtil;
import com.investment.management.util.ManagementSummaryUtilwo;
import com.investment.management.util.NpsTrustFeePfmSheetOne;
import com.investment.management.util.NpsTrustFeePfmSheetSummary;
import com.investment.management.util.ValidateFileName;
import com.investment.management.util.ValidateSheetName;
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
import com.liferay.portal.kernel.search.ParseException;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + IMF_NPS_Trust_FeePortletKeys.IMF_NPS_TRUST_FEE,
				"mvc.command.name=/saveInvestmentManagement/data",
		},
		service = MVCResourceCommand.class
		)
public class SaveIMF_NPS_Trust_Fee implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		//_log.debug("SaveInvestmentManagement.serveResource()");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			_log.debug("resultJsonObject: "+resultJsonObject.toString());
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
		List<String> fileNames=new ArrayList<String>();
		fileNames.add(uploadPortletRequest.getFileName("InvestmentManagementFile"));
		fileNames.add(uploadPortletRequest.getFileName("nps_trust_fee"));
		fileNames.add(uploadPortletRequest.getFileName("tax_invoicefor_imf"));
		fileNames.add(uploadPortletRequest.getFileName("audit_cert_vdaum"));
		
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		_log.debug("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.debug("reportUploadLogId" + reportUploadLogId);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(ValidateFileName.validatefileNames(fileNames)) {
		resultJsonObject.put("fileList", JSONFactoryUtil.createJSONArray());
		
		resultJsonObject = saveIMF(uploadPortletRequest, themeDisplay, userId, resourceRequest, 
				reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
		_log.debug("resultJsonObject: "+resultJsonObject.toString());
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveNpsTrust(uploadPortletRequest, themeDisplay, userId, resourceRequest, 
					reportUploadLogId, statusByUserName, serviceContext,resultJsonObject);
		}
		_log.debug("resultJsonObject: "+resultJsonObject.toString());
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = pdfUpload(uploadPortletRequest, themeDisplay, resourceRequest, resultJsonObject);
		}
		_log.debug("resultJsonObject: "+resultJsonObject.toString());
		String fileList = resultJsonObject.getJSONArray("fileList").toString();
		long fileEntryId = resultJsonObject.getLong("fileEntryId");
		String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
		updateReportLog(userId, fileEntryId , true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks, fileList );
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg","Please upload files with a valid filename");
			return resultJsonObject;
		}
		
		return resultJsonObject;
	}
	
	public JSONObject saveIMF(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay, long userId, 
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext, JSONObject resultJsonObject) {
		String fileName = uploadPortletRequest.getFileName("InvestmentManagementFile");

		File file = uploadPortletRequest.getFile("InvestmentManagementFile");

		String mimeType = uploadPortletRequest.getContentType("InvestmentManagementFile");

		String title = fileName;
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		_log.debug("resultJsonObject:  "+resultJsonObject);
		if(resultJsonObject.getBoolean("status")) {
			
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray managementFeeJsonArray= JSONFactoryUtil.createJSONArray();
		List<ManagementFee> managementFees= new ArrayList<ManagementFee>();
		
		JSONArray managementRateJsonArray= JSONFactoryUtil.createJSONArray();
		List<ManagementRate> managementRates= new ArrayList<ManagementRate>();
		
		JSONArray managementSummaryJsonArray= JSONFactoryUtil.createJSONArray();
		List<ManagementSummary> managementSummaries= new ArrayList<ManagementSummary>();
		
		
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
		List<String> errorList = new ArrayList<String>();
		errorList.add("Fee Summary");
		errorList.add("Management_Fee");
		//errorList.add("Management rate");
		
		List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, workbook, errorList);
		
			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				StringBuilder sb = new StringBuilder();
				for(String L : errorlist) {
					if(errorlist.size() == 1) {
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
				resultJsonObject =  ManagementFeeUtil.saveManagementFeeSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, managementFeeJsonArray, managementFees, decimalFormat,reportUploadLogId, resultJsonObject);
				if(!resultJsonObject.getBoolean("status")) 
					return resultJsonObject;
				//ManagementRateUtilWo.saveManagementRateSheet(file, workbook, userId, errorArray, xx, isexcelhaserror, managementRateJsonArray, managementRates, decimalFormat,reportUploadLogId);
				resultJsonObject =  ManagementSummaryUtilwo.saveManagementSummarySheet(file, workbook, userId, errorArray, xx, isexcelhaserror, managementSummaryJsonArray, managementSummaries, decimalFormat,reportUploadLogId, resultJsonObject);
				if(!resultJsonObject.getBoolean("status")) 
					return resultJsonObject;
			
				
				if (!isexcelhaserror && !isNoError) {
					//ManagementRateLocalServiceUtil.addManagementRates(managementRates);
					try {
						ManagementFeeLocalServiceUtil.addManagementFees(managementFees);
						ManagementSummaryLocalServiceUtil.addManagementSummarys(managementSummaries);
					} catch (Exception e1) {
						_log.error(e1);
						resultJsonObject.put("status", false);
						resultJsonObject.put("msg", CommonParser.fileExceptionMsg +"managementFees or managementSummaries");
						return resultJsonObject;
					}
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "InvestmentManagementFile");
					JSONObject imfFileEntryJO = JSONFactoryUtil.createJSONObject();
					imfFileEntryJO.put("Name", "Investment Management Fee");
					imfFileEntryJO.put("fileEntryId", fileEntryId);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						
						
						fileList.put(imfFileEntryJO);
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
	
	public JSONObject saveNpsTrust(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay, long userId, ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext, JSONObject resultJsonObject) {
		
		String fileName = uploadPortletRequest.getFileName("nps_trust_fee");

		File file = uploadPortletRequest.getFile("nps_trust_fee");

		String mimeType = uploadPortletRequest.getContentType("nps_trust_fee");

		String title = fileName;
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		
		
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		_log.debug("resultJsonObject: "+resultJsonObject.toString());
		if(resultJsonObject.getBoolean("status")) {
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray npsTrustFeePfmOneJsonArray= JSONFactoryUtil.createJSONArray();
		List<NpsTrustFeePfmOne> npsTrustFeePfmOnes= new ArrayList<NpsTrustFeePfmOne>();
		List<NpsTrustFeePfmsummary> npsTrustFeePfmsummary = new ArrayList<NpsTrustFeePfmsummary>();
		
		JSONArray npsTrustFeePfmsummaryJsonArray= JSONFactoryUtil.createJSONArray();
		

		
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
		List<String> errorList = new ArrayList<String>();
		errorList.add("nps_trust_fee");
		//sheet validation
		List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, workbook, errorList);
		
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
			_log.debug("errorlist: "+errorList);
			return resultJsonObject;
		}else {
			
			//parsing sheetone in util class
			_log.debug("resultJsonObject1: "+resultJsonObject.toString());
			resultJsonObject = NpsTrustFeePfmSheetOne.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmOnes, npsTrustFeePfmOneJsonArray,decimalFormat, reportUploadLogId, resultJsonObject);
			_log.debug("resultJsonObject2: "+resultJsonObject.toString());
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = NpsTrustFeePfmSheetSummary.saveNpsTrustFeePfmSheetSummary(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmsummaryJsonArray, npsTrustFeePfmsummary, decimalFormat, reportUploadLogId, resultJsonObject);
			_log.debug("resultJsonObject3: "+resultJsonObject.toString());
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			if (!isexcelhaserror) {
				_log.debug("no error in excel: "+resultJsonObject.toString());
				
				try {
					NpsTrustFeePfmOneLocalServiceUtil.addNpsTrustFeePfmOnes(npsTrustFeePfmOnes);
					NpsTrustFeePfmsummaryLocalServiceUtil.addNpsTrustFeePfmsummary(npsTrustFeePfmsummary);
				} catch (Exception e1) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.fileExceptionMsg +"nps_trust_fee");
					return resultJsonObject;
				}
				
				
				Long fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest,"nps_trust_fee");
				JSONObject ntfFileEntryJO = JSONFactoryUtil.createJSONObject();
				ntfFileEntryJO.put("Name", "NPS Trust Fee");
				ntfFileEntryJO.put("fileEntryId", fileEntryId);
				try {
					errorExcel.close();
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					
					//String statusByUserName = themeDisplay.getUser().getFullName();
					//ServiceContext serviceContext = null;
					try {
						serviceContext = ServiceContextFactory.getInstance(resourceRequest);
					} catch (PortalException e) {
						 _log.error(e);
					}
					
					fileList.put(ntfFileEntryJO);
					resultJsonObject.put("fileList", fileList);
					//updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
					
					//resultJsonObject.put("pdfURL",  pdfTable(statusDayss, themeDisplay, resourceRequest));
				} catch (Exception e) {
					 _log.error(e);
					 resultJsonObject.put("status", false);
						resultJsonObject.put("msg", CommonParser.fileExceptionMsg +"nps_trust_fee");
						return resultJsonObject;
				}
				resultJsonObject.put("status", true);
			} else {
				_log.debug("downloadurl: "+resultJsonObject.toString());
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

	public JSONObject pdfUpload(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay, ResourceRequest resourceRequest, JSONObject resultJsonObject) {
		
		Long userId = themeDisplay.getUserId();
		 
		//UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		//JSONArray filelist =JSONFactoryUtil.createJSONArray();
		long fileidi = uploadFILETOFOLDER(themeDisplay, resourceRequest,"tax_invoicefor_imf");
		
		Long fileEntryId = 0l;
		fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest,"audit_cert_vdaum");
		
		//long feeFilePDFId=uploadFILETOFOLDER(themeDisplay, resourceRequest, "nps_trust_fee_pdf");
		
		JSONObject uafd = JSONFactoryUtil.createJSONObject();
		uafd.put("Name", "Tax invoice for IMF");
		uafd.put("fileEntryId", fileidi);
		
		JSONObject acvdaum = JSONFactoryUtil.createJSONObject();
		acvdaum.put("Name", "Auditor's certificate verifying the daily Aum");
		acvdaum.put("fileEntryId", fileEntryId);
		
//		JSONObject feeFilePDF = JSONFactoryUtil.createJSONObject();
//		feeFilePDF.put("Name", "Fee Letter");
//		feeFilePDF.put("fileEntryId", feeFilePDFId);
		
		String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
		//updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks,filelist.toString());
		
		//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		fileList.put(uafd);
		fileList.put(acvdaum);
		//fileList.put(feeFilePDF);
		resultJsonObject.put("fileList", fileList);
		
		resultJsonObject.put("status", true);
		resultJsonObject.put("fileEntryId", fileEntryId);
		return resultJsonObject;
	}
	
	
	
	public String formatDate(Date dateIn, String format) throws ParseException {
        String strDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(dateIn);
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date d1 = sdf3.parse(formattedDate);

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            strDate = formatter.format(d1);
        } catch (Exception e) {
             _log.error(e);
        }
        return strDate;
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
			_log.debug(e.getMessage());
			 _log.error(e);
		}
		return downloadUrl;
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

			_log.debug(e.getMessage());

			 _log.error(e);
		}
		return 0;
	}

	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.debug(folder);

		} catch (Exception e) {

			_log.debug(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog2(createDate, createdBy, fileEntryId, 
				uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks, fileList);
		//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

	
	
	private static Log _log = LogFactoryUtil.getLog(SaveIMF_NPS_Trust_Fee.class);
	

}
