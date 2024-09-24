package com.annexure.one.pfm.details.action;

import com.annexure.one.pfm.details.constants.Annexure_one_PFM_AUC_DetailsPortletKeys;
import com.annexure.one.pfm.details.util.AnnexureOnePFMDetailsUtil;
import com.annexure.one.pfm.details.util.CollateralDetailsSheet;
import com.annexure.one.pfm.details.util.Form1;
import com.annexure.one.pfm.details.util.Form2;
import com.annexure.one.pfm.details.util.ValidateSheetName;
import com.daily.average.service.model.CollateralDetails;
import com.daily.average.service.model.MnAssetNotUnderCustody;
import com.daily.average.service.model.MnAucSummary;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.CollateralDetailsLocalService;
import com.daily.average.service.service.CollateralDetailsLocalServiceUtil;
import com.daily.average.service.service.MnAssetNotUnderCustodyLocalService;
import com.daily.average.service.service.MnAssetNotUnderCustodyLocalServiceUtil;
import com.daily.average.service.service.MnAucSummaryLocalService;
import com.daily.average.service.service.MnAucSummaryLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalServiceUtil;
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
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
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
import com.liferay.portal.kernel.util.Validator;
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

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.framework.hooks.bundle.CollisionHook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + Annexure_one_PFM_AUC_DetailsPortletKeys.ANNEXURE_ONE_PFM_AUC_DETAILS,
				"mvc.command.name=save/AnnexureOnePFMAUcDetails/excel"
		},
		service = MVCResourceCommand.class
)
public class SaveAnnexureOnePFM_AUC_Details extends BaseMVCResourceCommand{
	
	private static final Log _log = LogFactoryUtil.getLog(SaveAnnexureOnePFM_AUC_Details.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("Annexure One PFM AUC Details started ::::");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			_log.error(e);
		}
			
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
		
		String fileName = uploadPortletRequest.getFileName("AnnexureOnePFM");

		File file = uploadPortletRequest.getFile("AnnexureOnePFM");

		String mimeType = uploadPortletRequest.getContentType("AnnexureOnePFM");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Form 1
		JSONArray form1JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnAucSummary> form1List = new ArrayList<MnAucSummary>();
		
		//Form 2
		JSONArray form2JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnAssetNotUnderCustody> form2List = new ArrayList<MnAssetNotUnderCustody>();
		
		JSONArray collDetailsJsonArray = JSONFactoryUtil.createJSONArray();
		List<CollateralDetails> collDetailsList = new ArrayList<CollateralDetails>();
		
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
			
			MnAucSummaryLocalServiceUtil.deleteMnAucSummaryByReportUploadLogId(reportUploadLogId);
			MnAssetNotUnderCustodyLocalServiceUtil.deleteMnAssetNotUnderCustodyByReportUploadLogId(reportUploadLogId);
			CollateralDetailsLocalServiceUtil.deleteCollateralDetailsByReportUploadLogId(reportUploadLogId);
			resultJsonObject = Form1.saveForm1(file, workbook, userId, errorArray, xx, isexcelhaserror, form1JsonArray, form1List, decimalFormat, reportUploadLogId, resultJsonObject);
			if(!resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = Form2.saveForm2(file, workbook, userId, errorArray, xx, isexcelhaserror, form2JsonArray, form2List, decimalFormat, reportUploadLogId, resultJsonObject);
			if(!resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = CollateralDetailsSheet.saveCollateralDetails(file, workbook, userId, errorArray, xx, isexcelhaserror, collDetailsJsonArray, collDetailsList, decimalFormat, reportUploadLogId, resultJsonObject);
			if(!resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			
			if (!isexcelhaserror) {
				
				try {
					for(MnAucSummary mas : form1List) {
						mnAucSummaryLocalService.addMnAucSummary(mas);
					}
					
					for(MnAssetNotUnderCustody masc : form2List) {
						mnAssetNotUnderCustodyLocalService.addMnAssetNotUnderCustody(masc);
					}
					for(CollateralDetails collDetails : collDetailsList) {
						collateralDetailsLocalService.addCollateralDetails(collDetails);
					}
				} catch (Exception e1) {
					 resultJsonObject.put("status", false);
						resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						return resultJsonObject;
				}
				JSONArray jsonArray=JSONFactoryUtil.createJSONArray();
				
				Long fileEntryId = 0l;
				fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				jsonArray.put(fileEntryId);
				try {
					errorExcel.close();
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
					List<ReportUploadFileLog> reportUploadFileLogs= ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					try {
						ReportUploadFileLog fileLog=null;
						if(!reportUploadFileLogs.isEmpty() && Validator.isNotNull(reportUploadFileLogs)) {
							fileLog=reportUploadFileLogs.get(reportUploadFileLogs.size()-1);
						}
						fileLog.setFileList(jsonArray.toString());
						ReportUploadFileLogLocalServiceUtil.updateReportUploadFileLog(fileLog);
					}catch (Exception e) {
						_log.error(e.getMessage());
					}
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("AnnexureOnePFM");

		File file = uploadPortletRequest.getFile("AnnexureOnePFM");

		String mimeType = uploadPortletRequest.getContentType("AnnexureOnePFM");

		String title = fileName;

		String description = "AnnexureOnePFM";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Quarterly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	private void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		
		ReportUploadLogPFMCustodianLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);
	}
	
	@Reference
	AnnexureOnePFMDetailsUtil annexureOnePFMDetailsUtil;
	
	@Reference
	MnAucSummaryLocalService mnAucSummaryLocalService;
	
	@Reference
	MnAssetNotUnderCustodyLocalService mnAssetNotUnderCustodyLocalService;
	
	@Reference
	CollateralDetailsLocalService collateralDetailsLocalService ;
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

}
