package com.annexure.ten.monthly.resource;

import com.annexure.ten.monthly.constants.Annexure_Ten_MonthlyPortletKeys;
import com.annexure.ten.monthly.util.ParseAnnexFourA;
import com.annexure.ten.monthly.util.ParseAnnexFourB;
import com.annexure.ten.monthly.util.ParseAnnexFourC;
import com.annexure.ten.monthly.util.ParseAnnexFourD;
import com.annexure.ten.monthly.util.ParseAnnexSummary;
import com.annexure.ten.monthly.util.ParseReportFour;
import com.annexure.ten.monthly.util.ParseReportOne;
import com.annexure.ten.monthly.util.ParseReportSix;
import com.annexure.ten.monthly.util.ParseReportThree;
import com.annexure.ten.monthly.util.ParseReportTwo;
import com.annexure.ten.monthly.util.ParseReportV;
import com.daily.average.service.model.Annexureiva;
import com.daily.average.service.model.Annexureivb;
import com.daily.average.service.model.Annexureivc;
import com.daily.average.service.model.Annexureivd;
import com.daily.average.service.service.AnnexureivaLocalServiceUtil;
import com.daily.average.service.service.AnnexureivbLocalServiceUtil;
import com.daily.average.service.service.AnnexureivcLocalServiceUtil;
import com.daily.average.service.service.AnnexureivdLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.daily.pfm.service.model.ACSummeryX;
import com.daily.pfm.service.model.ReportI;
import com.daily.pfm.service.model.ReportII;
import com.daily.pfm.service.model.ReportIII;
import com.daily.pfm.service.model.ReportIVa;
import com.daily.pfm.service.model.ReportV;
import com.daily.pfm.service.model.ReportVI;
import com.daily.pfm.service.service.ACSummeryXLocalServiceUtil;
import com.daily.pfm.service.service.ReportIIILocalServiceUtil;
import com.daily.pfm.service.service.ReportIILocalServiceUtil;
import com.daily.pfm.service.service.ReportILocalServiceUtil;
import com.daily.pfm.service.service.ReportIVaLocalServiceUtil;
import com.daily.pfm.service.service.ReportVILocalServiceUtil;
import com.daily.pfm.service.service.ReportVLocalServiceUtil;
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

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { "javax.portlet.name=" + Annexure_Ten_MonthlyPortletKeys.ANNEXURE_TEN_MONTHLY,
"mvc.command.name=/annexureten/save" }, service = MVCResourceCommand.class)
public class SaveAnnexure_Ten_Monthly implements MVCResourceCommand{
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
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error(e);
		}
		
		Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		String cra = "";
		if(reportMasterId == 10) {
			cra = "PCRA";
		}else if(reportMasterId == 22) {
			cra = "KCRA";
		}else if(reportMasterId == 127) {
			cra = "CCRA";
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		String fileName = uploadPortletRequest.getFileName("annexuretenFile");

		File file = uploadPortletRequest.getFile("annexuretenFile");

		String mimeType = uploadPortletRequest.getContentType("annexuretenFile");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		int dataRowNum = 1;
		
		JSONArray reportiJsonArray= JSONFactoryUtil.createJSONArray();
		List<ReportI> reportis= new ArrayList<ReportI>();
		
		JSONArray reportiiJsonArray= JSONFactoryUtil.createJSONArray();
		List<ReportII> reportiis= new ArrayList<ReportII>();
		
		JSONArray reportiiiJsonArray= JSONFactoryUtil.createJSONArray();
		List<ReportIII> reportiiis= new ArrayList<ReportIII>();
		
		JSONArray reportivaJsonArray= JSONFactoryUtil.createJSONArray();
		List<ReportIVa> reportivas= new ArrayList<ReportIVa>();
		
		JSONArray reportvJsonArray= JSONFactoryUtil.createJSONArray();
		List<ReportV> reportvs= new ArrayList<ReportV>();
		
		JSONArray reportviJsonArray= JSONFactoryUtil.createJSONArray();
		List<ReportVI> reportvis= new ArrayList<ReportVI>();
		
		JSONArray annexurexivJsonArray= JSONFactoryUtil.createJSONArray();
		List<Annexureiva> annexureivas= new ArrayList<Annexureiva>();
		
		JSONArray annexureivbJsonArray= JSONFactoryUtil.createJSONArray();
		List<Annexureivb> annexureivbs= new ArrayList<Annexureivb>();
		
		JSONArray annexureivcJsonArray= JSONFactoryUtil.createJSONArray();
		List<Annexureivc> annexureivcs= new ArrayList<Annexureivc>();
		
		JSONArray annexureivdJsonArray= JSONFactoryUtil.createJSONArray();
		List<Annexureivd> annexureivds= new ArrayList<Annexureivd>();
		
		JSONArray acSummeryxJsonArray= JSONFactoryUtil.createJSONArray();
		List<ACSummeryX> acSummeryx= new ArrayList<ACSummeryX>();
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;
			
			try {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
			} catch (InvalidFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
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
			
			//parsing sheetone in util class
			
			JSONArray errorSheetNameList = JSONFactoryUtil.createJSONArray();
			
			resultJsonObject = ParseReportOne.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, reportiJsonArray, reportis, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseReportTwo.saveSheetTwo(file, workbook, userId, errorArray, xx, isexcelhaserror, reportiiJsonArray, reportiis, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseReportThree.saveSheetThree(file, workbook, userId, errorArray, xx, isexcelhaserror, reportiiiJsonArray, reportiiis, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseReportFour.saveSheetFour(file, workbook, userId, errorArray, xx, isexcelhaserror, reportivaJsonArray, reportivas, decimalFormat, dataRowNum,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseAnnexFourA.saveSheetFive(file, workbook, userId, errorArray, xx, isexcelhaserror, annexurexivJsonArray, annexureivas, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseAnnexFourB.saveSheetSix(file, workbook, userId, errorArray, xx, isexcelhaserror, annexureivbJsonArray, annexureivbs, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseAnnexFourC.saveSheetSeven(file, workbook, userId, errorArray, xx, isexcelhaserror, annexureivcJsonArray, annexureivcs, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseAnnexFourD.saveSheetEight(file, workbook, userId, errorArray, xx, isexcelhaserror, annexureivdJsonArray, annexureivds, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseReportV.saveSheetNine(file, workbook, userId, errorArray, xx, isexcelhaserror, reportvJsonArray, reportvs, dataRowNum, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = ParseReportSix.saveSheetTen(file, workbook, userId, errorArray, xx, isexcelhaserror, reportviJsonArray, reportvis,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			
			resultJsonObject = ParseAnnexSummary.saveSheetSummay(file, workbook, userId, errorArray, xx, isexcelhaserror, acSummeryxJsonArray, acSummeryx, decimalFormat,cra, resultJsonObject, errorSheetNameList,reportUploadLogId);
			if(resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			
			_log.info(reportiJsonArray.toString());
			if(resultJsonObject.getBoolean("status")) {
				if (!isexcelhaserror) {
					
					
					try {
						ReportILocalServiceUtil.addReportis(reportis);
						_log.info("reporti datacount"+reportis.size());
						
						ReportIILocalServiceUtil.addReportiis(reportiis);
						_log.info("reportii datacount"+reportiis.size());
						
						ReportIIILocalServiceUtil.addReportiiis(reportiiis);
						_log.info("reportiii datacount"+reportiiis.size());
						
						ReportIVaLocalServiceUtil.addreportivas(reportivas);
						_log.info("reportiva datacount"+reportivas.size());
						
						ReportVLocalServiceUtil.addReportvs(reportvs);
						_log.info("reportv datacount"+reportvs.size());
						
						ReportVILocalServiceUtil.addReportvis(reportvis);
						_log.info("reportvi datacount"+reportvis.size());
						
						AnnexureivaLocalServiceUtil.addAnnexureivas(annexureivas);
						_log.info("annexureiva datacount"+annexureivas.size());
						
						AnnexureivbLocalServiceUtil.addAnnexureivbs(annexureivbs);
						_log.info("annexureivb datacount"+annexureivbs.size());
						
						AnnexureivcLocalServiceUtil.addAnnexureivcs(annexureivcs);
						_log.info("annexureivc datacount"+annexureivcs.size());
						
						AnnexureivdLocalServiceUtil.addAnnexureivds(annexureivds);
						_log.info("annexureivd datacount"+annexureivds.size());
						
						ACSummeryXLocalServiceUtil.addACSummeryX(acSummeryx);
						_log.info("acSummeryx datacount"+acSummeryx.size());
						
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
						updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
						//resultJsonObject.put("pdfURL",  pdfTable(statusDayss, themeDisplay, resourceRequest));
					} catch (Exception e) {
						_log.error(e);
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

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("annexuretenFile");

		File file = uploadPortletRequest.getFile("annexuretenFile");

		String mimeType = uploadPortletRequest.getContentType("annexuretenFile");

		String title = fileName;

		String description = "Summarized data of Performance Report";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry  fileEntry=DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
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
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}

