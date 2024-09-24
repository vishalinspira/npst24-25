package com.nps.trust.fee.Pfm.resource;


import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.daily.pfm.service.model.NpsTrustFeePfmOne;
import com.daily.pfm.service.model.NpsTrustFeePfmsummary;
import com.daily.pfm.service.service.NpsTrustFeePfmOneLocalServiceUtil;
import com.daily.pfm.service.service.NpsTrustFeePfmsummaryLocalServiceUtil;
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
import com.nps.trust.fee.Pfm.constants.NpsTrustFeePfmPortletKeys;
import com.nps.trust.fee.Pfm.util.NpsTrustFeePfmSheetOne;
import com.nps.trust.fee.Pfm.util.NpsTrustFeePfmSheetSummary;
import com.nps.trust.fee.Pfm.util.ValidateSheetName;

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

import nps.email.api.api.ExcelValidationAn10Api;


@Component(property = { "javax.portlet.name=" + NpsTrustFeePfmPortletKeys.NPSTRUSTFEEPFM,
"mvc.command.name=/npstrustfeepfm/save" }, service = MVCResourceCommand.class)
public class SaveNpsTrustFeePfm implements MVCResourceCommand{
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
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

		String fileName = uploadPortletRequest.getFileName("npstrustfeepfmFile");

		File file = uploadPortletRequest.getFile("npstrustfeepfmFile");

		String mimeType = uploadPortletRequest.getContentType("npstrustfeepfmFile");

		String title = fileName;
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray npsTrustFeePfmOneJsonArray= JSONFactoryUtil.createJSONArray();
		List<NpsTrustFeePfmOne> npsTrustFeePfmOnes= new ArrayList<NpsTrustFeePfmOne>();
		
		JSONArray npsTrustFeePfmsummaryJsonArray= JSONFactoryUtil.createJSONArray();
		List<NpsTrustFeePfmsummary> npsTrustFeePfmsummary= new ArrayList<NpsTrustFeePfmsummary>();
		
//		JSONArray npsTrustFeePfmTwoJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmTwo> npsTrustFeePfmTwos= new ArrayList<NpsTrustFeePfmTwo>();
//		
//		JSONArray npsTrustFeePfmThreeJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmThree> npsTrustFeePfmThrees= new ArrayList<NpsTrustFeePfmThree>();
//		
//		JSONArray npsTrustFeePfmFourJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmFour> npsTrustFeePfmFours= new ArrayList<NpsTrustFeePfmFour>();
//		
//		JSONArray npsTrustFeePfmFiveJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmFive> npsTrustFeePfmFives= new ArrayList<NpsTrustFeePfmFive>();
//		
//		JSONArray npsTrustFeePfmSixJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmSix> npsTrustFeePfmSixs= new ArrayList<NpsTrustFeePfmSix>();
//		
//		JSONArray npsTrustFeePfmSevenJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmSeven> npsTrustFeePfmsSevens= new ArrayList<NpsTrustFeePfmSeven>();
//		
//		JSONArray npsTrustFeePfmEightJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmEight> npsTrustFeePfmEights= new ArrayList<NpsTrustFeePfmEight>();
//		
//		JSONArray npsTrustFeePfmNineJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmNine> npsTrustFeePfmNines= new ArrayList<NpsTrustFeePfmNine>();
//		
//		JSONArray npsTrustFeePfmTenJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmTen> npsTrustFeePfmTens= new ArrayList<NpsTrustFeePfmTen>();
//		
//		JSONArray npsTrustFeePfmElevenJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmEleven> npsTrustFeePfmElevens= new ArrayList<NpsTrustFeePfmEleven>();
//		
//		JSONArray npsTrustFeePfmTwelveJsonArray= JSONFactoryUtil.createJSONArray();
//		List<NpsTrustFeePfmTwelve> npsTrustFeePfmTwelves= new ArrayList<NpsTrustFeePfmTwelve>();
		
		
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
			
			//parsing sheetone in util class
			
			NpsTrustFeePfmSheetOne.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmOnes, npsTrustFeePfmOneJsonArray,decimalFormat, reportUploadLogId);
			NpsTrustFeePfmSheetSummary.saveNpsTrustFeePfmSheetSummary(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmsummaryJsonArray, npsTrustFeePfmsummary, decimalFormat, reportUploadLogId);
			
			
			/*NpsTrustFeePfmSheetTwo.saveSheetTwo(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmTwos, npsTrustFeePfmTwoJsonArray,decimalFormat);
			NpsTrustFeePfmSheetThree.saveSheetThree(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmThrees, npsTrustFeePfmThreeJsonArray,decimalFormat);
			NpsTrustFeePfmSheetFour.saveSheetFour(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmFours, npsTrustFeePfmFourJsonArray,decimalFormat);
			NpsTrustFeePfmSheetFive.saveSheetFive(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmFives, npsTrustFeePfmFiveJsonArray,decimalFormat);
			NpsTrustFeePfmSheetSix.saveSheetSix(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmSixs, npsTrustFeePfmSixJsonArray,decimalFormat);
			NpsTrustFeePfmSheetSeven.saveSheetSeven(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmsSevens, npsTrustFeePfmSevenJsonArray,decimalFormat);
			NpsTrustFeePfmSheetEight.saveSheetEight(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmEights, npsTrustFeePfmEightJsonArray,decimalFormat);
			NpsTrustFeePfmSheetNine.saveSheetNine(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmNines, npsTrustFeePfmNineJsonArray,decimalFormat);
			NpsTrustFeePfmSheetTen.saveSheetTen(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmTens, npsTrustFeePfmTenJsonArray,decimalFormat);
			NpsTrustFeePfmSheetEleven.saveSheetEleven(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmElevens, npsTrustFeePfmElevenJsonArray,decimalFormat);
			NpsTrustFeePfmSheetTwelve.saveSheetTwelve(file, workbook, userId, errorArray, xx, isexcelhaserror, npsTrustFeePfmTwelves, npsTrustFeePfmTwelveJsonArray,decimalFormat);*/
			
//			_log.info(npsTrustFeePfmTwelveJsonArray.toString());
			
			if (!isexcelhaserror) {
				
				
				NpsTrustFeePfmOneLocalServiceUtil.addNpsTrustFeePfmOnes(npsTrustFeePfmOnes);
				NpsTrustFeePfmsummaryLocalServiceUtil.addNpsTrustFeePfmsummary(npsTrustFeePfmsummary);
//				NpsTrustFeePfmTwoLocalServiceUtil.addNpsTrustFeePfmTwos(npsTrustFeePfmTwos);
//				NpsTrustFeePfmThreeLocalServiceUtil.addNpsTrustFeePfmThrees(npsTrustFeePfmThrees);
//				NpsTrustFeePfmFourLocalServiceUtil.addNpsTrustFeePfmFours(npsTrustFeePfmFours);
//				NpsTrustFeePfmFiveLocalServiceUtil.addNpsTrustFeePfmFives(npsTrustFeePfmFives);
//				NpsTrustFeePfmSixLocalServiceUtil.addNpsTrustFeePfmSixs(npsTrustFeePfmSixs);
//				NpsTrustFeePfmSevenLocalServiceUtil.addNpsTrustFeePfmSevens(npsTrustFeePfmsSevens);
//				NpsTrustFeePfmEightLocalServiceUtil.addNpsTrustFeePfmEights(npsTrustFeePfmEights);
//				NpsTrustFeePfmNineLocalServiceUtil.addNpsTrustFeePfmNines(npsTrustFeePfmNines);
//				NpsTrustFeePfmTenLocalServiceUtil.addNpsTrustFeePfmTens(npsTrustFeePfmTens);
//				NpsTrustFeePfmElevenLocalServiceUtil.addNpsTrustFeePfmElevens(npsTrustFeePfmElevens);
//				NpsTrustFeePfmTwelveLocalServiceUtil.addNpsTrustFeePfmTwelves(npsTrustFeePfmTwelves);
				
				Long fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					errorExcel.close();
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					
					String statusByUserName = themeDisplay.getUser().getFullName();
					ServiceContext serviceContext = null;
					try {
						serviceContext = ServiceContextFactory.getInstance(resourceRequest);
					} catch (PortalException e) {
						 _log.error(e);
					}
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
					
					//resultJsonObject.put("pdfURL",  pdfTable(statusDayss, themeDisplay, resourceRequest));
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

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("npstrustfeepfmFile");

		File file = uploadPortletRequest.getFile("npstrustfeepfmFile");

		String mimeType = uploadPortletRequest.getContentType("npstrustfeepfmFile");

		String title = fileName;

		String description = "npstrustfeepfmFile";

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
	
	Log _log = LogFactoryUtil.getLog(getClass());
}

