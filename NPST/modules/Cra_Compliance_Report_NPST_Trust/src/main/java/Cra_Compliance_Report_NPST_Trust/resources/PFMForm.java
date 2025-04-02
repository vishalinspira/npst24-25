package Cra_Compliance_Report_NPST_Trust.resources;

import com.daily.average.service.model.MnGrievWthdrwlAgingAnalysis;
import com.daily.average.service.model.MnReportWthdrwlAgingAnalys;
import com.daily.average.service.model.MnSpecialProvisionOnWthdrwl;
import com.daily.average.service.model.MnStatusOfWidrawalReported;
import com.daily.average.service.model.MnSubsAnnuityPurchase;
import com.daily.average.service.model.MnWidrawalRelatedSubsGriev;
import com.daily.average.service.service.MnGrievWthdrwlAgingAnalysisLocalServiceUtil;
import com.daily.average.service.service.MnReportWthdrwlAgingAnalysLocalServiceUtil;
import com.daily.average.service.service.MnSpecialProvisionOnWthdrwlLocalServiceUtil;
import com.daily.average.service.service.MnStatusOfWidrawalReportedLocalServiceUtil;
import com.daily.average.service.service.MnSubsAnnuityPurchaseLocalServiceUtil;
import com.daily.average.service.service.MnWidrawalRelatedSubsGrievLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogCRALocalServiceUtil;
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

import Cra_Compliance_Report_NPST_Trust.constants.Cra_Compliance_Report_NPST_TrustPortletKeys;
import Cra_Compliance_Report_NPST_Trust.utils.Form1;
import Cra_Compliance_Report_NPST_Trust.utils.Form2;
import Cra_Compliance_Report_NPST_Trust.utils.Form3;
import Cra_Compliance_Report_NPST_Trust.utils.Form4;
import Cra_Compliance_Report_NPST_Trust.utils.Form5;
import Cra_Compliance_Report_NPST_Trust.utils.Form6;
import Cra_Compliance_Report_NPST_Trust.utils.ValidateFileName;
import Cra_Compliance_Report_NPST_Trust.utils.ValidateSheetName;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + Cra_Compliance_Report_NPST_TrustPortletKeys.CRA_COMPLIANCE_REPORT_NPST_TRUST,
		"mvc.command.name=" + Cra_Compliance_Report_NPST_TrustPortletKeys.pfm, 
		}, 
service = MVCResourceCommand.class)

public class PFMForm implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(PFMForm.class);

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
		
		String fileName = uploadPortletRequest.getFileName("complianceReportNPSTFile");

		File file = uploadPortletRequest.getFile("complianceReportNPSTFile");

		String mimeType = uploadPortletRequest.getContentType("complianceReportNPSTFile");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Form 1
		JSONArray form1JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnStatusOfWidrawalReported> form1List = new ArrayList<MnStatusOfWidrawalReported>();
		
		//Form 2
		JSONArray form2JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnSpecialProvisionOnWthdrwl> form2List = new ArrayList<MnSpecialProvisionOnWthdrwl>();
		
		//Form 3
		JSONArray form3JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnSubsAnnuityPurchase> form3List = new ArrayList<MnSubsAnnuityPurchase>();
		
		//Form 4
		JSONArray form4JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnWidrawalRelatedSubsGriev> form4List = new ArrayList<MnWidrawalRelatedSubsGriev>();
		
		//Form 5
		JSONArray form5JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnReportWthdrwlAgingAnalys> form5List = new ArrayList<MnReportWthdrwlAgingAnalys>();
		
		//Form 6
		JSONArray form6JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnGrievWthdrwlAgingAnalysis> form6List = new ArrayList<MnGrievWthdrwlAgingAnalysis>();
		
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
			
			MnStatusOfWidrawalReportedLocalServiceUtil.deleteMnStatusOfWidrawalReportedByReportUploadLogId(reportUploadLogId);
			MnSpecialProvisionOnWthdrwlLocalServiceUtil.deleteMnSpecialProvisionOnWthdrwlByReportUploadLogId(reportUploadLogId);
			MnSubsAnnuityPurchaseLocalServiceUtil.deleteMnSubsAnnuityPurchaseByReportUploadLogId(reportUploadLogId);
			MnWidrawalRelatedSubsGrievLocalServiceUtil.deleteMnWidrawalRelatedSubsGrievByReportUploadLogId(reportUploadLogId);
			MnReportWthdrwlAgingAnalysLocalServiceUtil.deleteMnReportWthdrwlAgingAnalysByReportUploadLogId(reportUploadLogId);
			MnGrievWthdrwlAgingAnalysisLocalServiceUtil.deleteMnGrievWthdrwlAgingAnalysisByReportUploadLogId(reportUploadLogId);
			
			resultJsonObject = Form1.saveForm1(file, workbook, userId, errorArray, xx, isexcelhaserror, form1JsonArray, form1List, decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Form2.saveForm2(file, workbook, userId, errorArray, xx, isexcelhaserror, form2JsonArray, form2List, decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Form3.saveForm3(file, workbook, userId, errorArray, xx, isexcelhaserror, form3JsonArray, form3List, decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Form4.saveForm4Atal(file, workbook, userId, errorArray, xx, isexcelhaserror, form4JsonArray, form4List, decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Form5.saveForm5C_1(file, workbook, userId, errorArray, xx, isexcelhaserror, form5JsonArray, form5List, decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Form6.saveForm6E_1(file, workbook, userId, errorArray, xx, isexcelhaserror, form6JsonArray, form6List, decimalFormat,reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			if (!isexcelhaserror) {
				
				Long fileEntryId = 0l;
				fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					MnStatusOfWidrawalReportedLocalServiceUtil.addMnStatusOfWidrawalReported(form1List);
					MnSpecialProvisionOnWthdrwlLocalServiceUtil.addMnSpecialProvisionOnWthdrwl(form2List);
					MnSubsAnnuityPurchaseLocalServiceUtil.addMnSubsAnnuityPurchase(form3List);
					MnWidrawalRelatedSubsGrievLocalServiceUtil.addMnWidrawalRelatedSubsGriev(form4List);
					MnReportWthdrwlAgingAnalysLocalServiceUtil.addMnReportWthdrwlAgingAnalys(form5List);
					MnGrievWthdrwlAgingAnalysisLocalServiceUtil.addMnGrievWthdrwlAgingAnalysis(form6List);
					
					errorExcel.close();
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
					//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
					resultJsonObject.put("status", true);
				} catch (Exception e) {
					 _log.error(e);
					 resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
					 return resultJsonObject;
				}
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("complianceReportNPSTFile");

		File file = uploadPortletRequest.getFile("complianceReportNPSTFile");

		String mimeType = uploadPortletRequest.getContentType("complianceReportNPSTFile");

		String title = fileName;

		String description = "complianceReportNPSTFile";

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
		ReportUploadLogCRALocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
}
