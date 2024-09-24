package GrievanceTopFiveData_Monthly.MVC;

import com.daily.average.service.model.GrievanceTopFiveDetails;
import com.daily.average.service.model.GrievanceTopFiveMinistrywise;
import com.daily.average.service.model.GrievanceTopFivechowise;
import com.daily.average.service.model.GrievanceTopFivepopwise;
import com.daily.average.service.model.GrievanceTopFivestatewise;
import com.daily.average.service.service.GrievanceTopFiveDetailsLocalServiceUtil;
import com.daily.average.service.service.GrievanceTopFiveMinistrywiseLocalServiceUtil;
import com.daily.average.service.service.GrievanceTopFivechowiseLocalServiceUtil;
import com.daily.average.service.service.GrievanceTopFivepopwiseLocalServiceUtil;
import com.daily.average.service.service.GrievanceTopFivestatewiseLocalServiceUtil;
import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalServiceUtil;
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

import GrievanceTopFiveData_Monthly.constants.GrievanceTopFiveData_MonthlyPortletKeys;
import GrievanceTopFiveData_Monthly.util.Grievancecho;
import GrievanceTopFiveData_Monthly.util.Grievancedetails;
import GrievanceTopFiveData_Monthly.util.Grievanceministry;
import GrievanceTopFiveData_Monthly.util.Grievancepop;
import GrievanceTopFiveData_Monthly.util.Grievancestate;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + GrievanceTopFiveData_MonthlyPortletKeys.GRIEVANCETOPFIVEDATA_MONTHLY,
				"mvc.command.name=" + GrievanceTopFiveData_MonthlyPortletKeys.UPLOAD_EXCEL_RESOURCE_COMMAND,
		},
		service = MVCResourceCommand.class
)

public class GrievanceTopFiveData_MonthlyMVCResource implements MVCResourceCommand{
	private static Log _log = LogFactoryUtil.getLog(GrievanceTopFiveData_MonthlyMVCResource.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
			_log.info("grivances Serve Resource method");
			
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
		String cra = IntermediaryListLocalServiceUtil.fetchIntermediaryByReportLogId(reportUploadLogId);
		
		_log.info("cra " + cra);
		
		String fileName = uploadPortletRequest.getFileName("reportsFileId");

		File file = uploadPortletRequest.getFile("reportsFileId");

		String mimeType = uploadPortletRequest.getContentType("reportsFileId");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Form 1
		JSONArray grievanceMinistryArray = JSONFactoryUtil.createJSONArray();
		List<GrievanceTopFiveMinistrywise> grievanceMinistryList = new ArrayList<GrievanceTopFiveMinistrywise>();
		
		//Form 2
		JSONArray grievancestatewiseArray = JSONFactoryUtil.createJSONArray();
		List<GrievanceTopFivestatewise> grievancestatewiseList = new ArrayList<GrievanceTopFivestatewise>();

		
		//Form 3
		JSONArray grievancepopwiseArray = JSONFactoryUtil.createJSONArray();
		List<GrievanceTopFivepopwise> grievancepopwiseList = new ArrayList<GrievanceTopFivepopwise>();
		
		//Form 4
		JSONArray grievancechowisAarray = JSONFactoryUtil.createJSONArray();
		List<GrievanceTopFivechowise> grievancechowisList = new ArrayList<GrievanceTopFivechowise>();
		
		//Form 5
		JSONArray grievanceDetailsArray = JSONFactoryUtil.createJSONArray();
		List<GrievanceTopFiveDetails> grievanceDetailsList = new ArrayList<GrievanceTopFiveDetails>();
		
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		//resultJsonObject.put("status", true);
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
			
			List<String> errorList = new ArrayList<String>();
			errorList.add("Ministry wise pending");
			errorList.add("State wise pending");
			errorList.add("POP wise pending");
			errorList.add("CHO wise pending");
			errorList.add("Grievance Details");
			
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
				_log.error(e);
			}
			
			boolean isexcelhaserror = false;
			
			//deleting old sheet data
			GrievanceTopFiveMinistrywiseLocalServiceUtil.deleteGrievanceTopFiveMinistrywiseByReportUploadLogId(reportUploadLogId);
			GrievanceTopFivestatewiseLocalServiceUtil.deleteGrievanceTopFivestatewiseByReportUploadLogId(reportUploadLogId);
			GrievanceTopFivepopwiseLocalServiceUtil.deleteGrievanceTopFivepopwiseByReportUploadLogId(reportUploadLogId);
			GrievanceTopFivechowiseLocalServiceUtil.deleteGrievanceTopFivechowiseByReportUploadLogId(reportUploadLogId);
			GrievanceTopFiveDetailsLocalServiceUtil.deleteGrievanceTopFiveDetailsByReportUploadLogId(reportUploadLogId);
			
			resultJsonObject = Grievanceministry.saveGministry(file, workbook, userId, errorArray, xx, isexcelhaserror, grievanceMinistryArray, grievanceMinistryList,cra,reportUploadLogId, resultJsonObject);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Grievancestate.saveGstate(file, workbook, userId, errorArray, xx, isexcelhaserror, grievancestatewiseArray, grievancestatewiseList,cra,reportUploadLogId, resultJsonObject);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Grievancepop.saveGpop(file, workbook, userId, errorArray, xx, isexcelhaserror, grievancepopwiseArray, grievancepopwiseList,cra,reportUploadLogId, resultJsonObject);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Grievancecho.saveGcho(file, workbook, userId, errorArray, xx, isexcelhaserror, grievancechowisAarray, grievancechowisList,cra,reportUploadLogId, resultJsonObject);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = Grievancedetails.saveGdetails(file, workbook, userId, errorArray, xx, isexcelhaserror, grievanceDetailsArray, grievanceDetailsList,decimalFormat,cra,reportUploadLogId, resultJsonObject);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			
			if (!isexcelhaserror) {
				
				try {
					GrievanceTopFiveMinistrywiseLocalServiceUtil.addGrievanceTopFiveMinistrywise(grievanceMinistryList);
					GrievanceTopFivestatewiseLocalServiceUtil.addGrievanceTopFivestatewise(grievancestatewiseList);
					GrievanceTopFivepopwiseLocalServiceUtil.addGrievanceTopFivepopwise(grievancepopwiseList);
					GrievanceTopFivechowiseLocalServiceUtil.addGrievanceTopFivechowise(grievancechowisList);
					GrievanceTopFiveDetailsLocalServiceUtil.addGrievanceTopFiveDetails(grievanceDetailsList);
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("reportsFileId");

		File file = uploadPortletRequest.getFile("reportsFileId");

		String mimeType = uploadPortletRequest.getContentType("reportsFileId");

		String title = fileName;

		String description = "reportsFileId";

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
		ReportUploadLogGrievancesLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
}
