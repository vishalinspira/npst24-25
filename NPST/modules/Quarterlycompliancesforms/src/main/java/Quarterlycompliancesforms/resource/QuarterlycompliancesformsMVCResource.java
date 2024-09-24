package Quarterlycompliancesforms.resource;

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

import Quarterlycompliancesforms.constants.QuarterlycompliancesformsPortletKeys;
import Quarterlycompliancesforms.util.ParseCashFlow;
import Quarterlycompliancesforms.util.ParseForm1A;
import Quarterlycompliancesforms.util.ParseFormFive;
import Quarterlycompliancesforms.util.ParseFormFour;
import Quarterlycompliancesforms.util.ParseFormThree;
import Quarterlycompliancesforms.util.ParseFormTwo;
import Quarterlycompliancesforms.util.ParseFormone;
import Quarterlycompliancesforms.util.ValidateSheetName;
import compliance.service.model.Quaeterlyfromfive;
import compliance.service.model.Quaeterlyfromfivenpa;
import compliance.service.model.Quaeterlyfromfour;
import compliance.service.model.Quaeterlyfromoneb;
import compliance.service.model.Quaeterlyfromthree;
import compliance.service.model.Quaeterlyfromtwo;
import compliance.service.model.QuarterlyForm1A;
import compliance.service.model.QuaterlyformCashFlow;
import compliance.service.service.QuaeterlyfromfiveLocalServiceUtil;
import compliance.service.service.QuaeterlyfromfourLocalServiceUtil;
import compliance.service.service.QuaeterlyfromonebLocalServiceUtil;
import compliance.service.service.QuaeterlyfromthreeLocalServiceUtil;
import compliance.service.service.QuaeterlyfromtwoLocalServiceUtil;
import compliance.service.service.QuarterlyForm1ALocalServiceUtil;
import compliance.service.service.QuaterlyformCashFlowLocalServiceUtil;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + QuarterlycompliancesformsPortletKeys.QUARTERLYCOMPLIANCESFORMS,
		"mvc.command.name=" + QuarterlycompliancesformsPortletKeys.SaveQuarterlyCompliancesForm,
		}, 
service = MVCResourceCommand.class)

public class QuarterlycompliancesformsMVCResource implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("welcome to ::::::::::::::::::::::");
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

		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		String fileName = uploadPortletRequest.getFileName("quarterlycompform");

		File file = uploadPortletRequest.getFile("quarterlycompform");
		
		String mimeType = uploadPortletRequest.getContentType("quarterlycompform");

		String title = fileName;
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		int dataRowNum = 5;
		
		JSONArray qForm1AJsonArray= JSONFactoryUtil.createJSONArray();
		List<QuarterlyForm1A> qForm1AList= new ArrayList<QuarterlyForm1A>();
		
		JSONArray qformoneJsonArray= JSONFactoryUtil.createJSONArray();
		List<Quaeterlyfromoneb> qformoneList= new ArrayList<Quaeterlyfromoneb>();
		
		/*
		 * JSONArray qformoneisinJsonArray= JSONFactoryUtil.createJSONArray();
		 * List<Quaeterlyfromoneisin> qformoneisinList= new
		 * ArrayList<Quaeterlyfromoneisin>();
		 */
		JSONArray qformtwoJsonArray= JSONFactoryUtil.createJSONArray();
		List<Quaeterlyfromtwo> qformtwoList= new ArrayList<Quaeterlyfromtwo>();
		
		JSONArray qformthreeJsonArray= JSONFactoryUtil.createJSONArray();
		List<Quaeterlyfromthree> qformthreeList= new ArrayList<Quaeterlyfromthree>();
		
		JSONArray qformfourJsonArray= JSONFactoryUtil.createJSONArray();
		List<Quaeterlyfromfour> qformfourList= new ArrayList<Quaeterlyfromfour>();
		
		JSONArray qformfiveJsonArray= JSONFactoryUtil.createJSONArray();
		List<Quaeterlyfromfive> qformfiveList= new ArrayList<Quaeterlyfromfive>();
		
		JSONArray qformfivenpsJsonArray= JSONFactoryUtil.createJSONArray();
		List<Quaeterlyfromfivenpa> qformfivenpsList= new ArrayList<Quaeterlyfromfivenpa>();
		
		JSONArray qformCashFlowJsonArray= JSONFactoryUtil.createJSONArray();
		List<QuaterlyformCashFlow> qformCashFlowList= new ArrayList<QuaterlyformCashFlow>();
		
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
		
		List<String> errorList = ValidateSheetName.ValidateExcelSheetName(file, workbook);
		
		if (errorList.size() > 0) {
			resultJsonObject.put("status", false);
			resultJsonObject.put("sheeterror",true);
			JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorList);
			resultJsonObject.put("errorSheetNameList", arrayList);
			try {
				errorExcel.close();
			} catch (IOException e) {
				 _log.error(e);
			}
			// return
			return resultJsonObject;
		} else {
			//parsing sheetone in util class
		//	ParseForm1A.saveSheetForm1A(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qForm1AJsonArray, qForm1AList, reportUploadLogId);
		    ParseFormone.saveSheetFormone(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qformoneJsonArray, qformoneList,reportUploadLogId); 
	        ParseFormTwo.saveSheetFormtwo(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qformtwoJsonArray, qformtwoList,reportUploadLogId);
	        ParseFormThree.saveSheetFormthree(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qformthreeJsonArray, qformthreeList,reportUploadLogId);
	        ParseFormFour.saveSheetFormfour(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qformfourJsonArray, qformfourList,reportUploadLogId);
	        ParseFormFive.saveSheetFormfive(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qformfiveJsonArray, qformfiveList,reportUploadLogId);
	        ParseCashFlow.saveCashFlow(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qformCashFlowJsonArray, qformCashFlowList, reportUploadLogId);
	        //ParseFormoneisin.saveSheetFormoneisin(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qformoneisinJsonArray, qformoneisinList);
	        //ParseFormFivenpa.saveSheetFormFivenpa(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, qformfivenpsJsonArray, qformfivenpsList);
	        //_log.info(annexTenaJsonArray.toString());
			if (!isexcelhaserror) {
			//	QuarterlyForm1ALocalServiceUtil.addQuarterlyForm1A(qForm1AList);
				QuaeterlyfromonebLocalServiceUtil.addQuaeterlyfromoneb(qformoneList);
				_log.info("form 1 datacount"+qformoneList.size());
				
				QuaeterlyfromtwoLocalServiceUtil.addQuarterlyformtwo(qformtwoList);
				_log.info("form 2 datacount"+qformtwoList.size());
				
				QuaeterlyfromthreeLocalServiceUtil.addQuarterlyformthree(qformthreeList);
				_log.info("form 3 datacount"+qformthreeList.size());
				
				QuaeterlyfromfourLocalServiceUtil.addQuarterlyformfour(qformfourList);
				_log.info("form 4 datacount"+qformfourList.size());
				
				QuaeterlyfromfiveLocalServiceUtil.addQuarterlyformfive(qformfiveList);
				_log.info("form 5 datacount"+qformfiveList.size());
				
				QuaterlyformCashFlowLocalServiceUtil.addQuarterlyformCashFlow(qformCashFlowList);
				_log.info("form CashFlow datacount"+qformCashFlowList.size());
//				not considered for parsing since not present as per new excel and data dictionary
				
//				QuaeterlyfromoneisinLocalServiceUtil.addQuaeterlyfromoneisin(qformoneisinList);
//				QuaeterlyfromfivenpaLocalServiceUtil.addQuarterlyformfivenpa(qformfivenpsList);
				
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

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("quarterlycompform");

		File file = uploadPortletRequest.getFile("quarterlycompform");

		String mimeType = uploadPortletRequest.getContentType("quarterlycompform");

		String title = fileName;

		String description = "Quarterly Compliances under PFRDA Regulations - Form5_NPA";

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
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());
	
}	