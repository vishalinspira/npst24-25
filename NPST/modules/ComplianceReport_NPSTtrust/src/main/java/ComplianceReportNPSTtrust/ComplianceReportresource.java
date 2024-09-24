package ComplianceReportNPSTtrust;

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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
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

import ComplianceReportNPSTtrust.util.ParseFormatAI;
import ComplianceReportNPSTtrust.util.ParseFormatAII;
import ComplianceReportNPSTtrust.util.ParseFormatB;
import ComplianceReportNPSTtrust.util.ParseFormatC;
import ComplianceReportNPSTtrust.util.ParseFormatD;
import ComplianceReportNPSTtrust.util.ParseFormatE;
import ComplianceReportNPSTtrust.util.ParseFormata1;
import ComplianceReport_NPSTtrust.constants.ComplianceReport_NPSTtrustPortletKeys;
import compliance.service.model.FormatAI;
import compliance.service.model.FormatAII;
import compliance.service.model.FormatB;
import compliance.service.model.FormatC;
import compliance.service.model.FormatD;
import compliance.service.model.FormatE;
import compliance.service.model.FormatIA;
import compliance.service.service.FormatAIILocalServiceUtil;
import compliance.service.service.FormatAILocalServiceUtil;
import compliance.service.service.FormatBLocalServiceUtil;
import compliance.service.service.FormatCLocalServiceUtil;
import compliance.service.service.FormatDLocalServiceUtil;
import compliance.service.service.FormatELocalServiceUtil;
import compliance.service.service.FormatIALocalServiceUtil;

@Component(property = { 
		"javax.portlet.name=" + ComplianceReport_NPSTtrustPortletKeys.COMPLIANCEREPORT_NPSTTRUST,
		"mvc.command.name=" + ComplianceReport_NPSTtrustPortletKeys.ComplianceReportNPSTtrust, 
		}, 
service = MVCResourceCommand.class)


public class ComplianceReportresource implements MVCResourceCommand{

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
		
//		String statusByUserName = themeDisplay.getUser().getFullName();
//		ServiceContext serviceContext = null;
//		try {
//			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
//		} catch (PortalException e) {
//			 _log.error(e);
//		}
//		
//		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
//		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
//		
		String fileName = uploadPortletRequest.getFileName("reportnpst");

		File file = uploadPortletRequest.getFile("reportnpst");

		String mimeType = uploadPortletRequest.getContentType("reportnpst");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		int dataRowNum = 5;
		
		JSONArray formatiaJsonArray= JSONFactoryUtil.createJSONArray();
		List<FormatIA> formatia= new ArrayList<FormatIA>();
		
		JSONArray formataiJsonArray= JSONFactoryUtil.createJSONArray();
		List<FormatAI> formatai= new ArrayList<FormatAI>();
	
     	JSONArray FormataiiJsonArray= JSONFactoryUtil.createJSONArray();
		List<FormatAII> formataiiList= new ArrayList<FormatAII>();
		
		JSONArray FormatbJsonArray= JSONFactoryUtil.createJSONArray();
		List<FormatB> formatbList= new ArrayList<FormatB>();
		
		JSONArray FormatcJsonArray= JSONFactoryUtil.createJSONArray();
		List<FormatC> formatcList= new ArrayList<FormatC>();
		
		JSONArray FormatdJsonArray= JSONFactoryUtil.createJSONArray();
		List<FormatD> formatdList= new ArrayList<FormatD>();
		
		JSONArray FormateJsonArray= JSONFactoryUtil.createJSONArray();
		List<FormatE> formateList= new ArrayList<FormatE>();
//		
//		JSONArray annexTenaIVJsonArray= JSONFactoryUtil.createJSONArray();
//		List<AnnexTenaIV> annexTenaIVs= new ArrayList<AnnexTenaIV>();
		
		
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
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
		
		//parsing sheetone in util class
		
		
		
	    ParseFormata1.saveSheetFomartia(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, formatiaJsonArray, formatia);
     	ParseFormatAI.saveSheetFormatai(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, formataiJsonArray, formatai);
		ParseFormatAII.saveSheetformataii(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat,FormataiiJsonArray,formataiiList);
     	ParseFormatB.saveSheetFormatb(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, FormatbJsonArray, formatbList);
		ParseFormatC.saveSheetformatc(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, FormatcJsonArray, formatcList);
     	ParseFormatD.saveSheetformatd(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, FormatdJsonArray, formatdList);
     	ParseFormatE.saveSheetformate(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, FormateJsonArray, formateList);
		//_log.info(annexTenaJsonArray.toString());
		
		
		
		if (!isexcelhaserror) {
			
			
			FormatIALocalServiceUtil.addFormatIA(formatia);
			FormatAILocalServiceUtil.addFormatAI(formatai);
			FormatAIILocalServiceUtil.addFormatAII(formataiiList);
			FormatBLocalServiceUtil.addFormatB(formatbList);
			FormatCLocalServiceUtil.addFormatC(formatcList);
			FormatDLocalServiceUtil.addFormatD(formatdList);
			FormatELocalServiceUtil.addFormatE(formateList);
			
			uploadFILETOFOLDER(themeDisplay, resourceRequest);
			try {
				errorExcel.close();
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

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("reportnpst");

		File file = uploadPortletRequest.getFile("reportnpst");

		String mimeType = uploadPortletRequest.getContentType("reportnpst");

		String title = fileName;

		String description = "Compliance Report NPST Trust";

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
	
	Log _log = LogFactoryUtil.getLog(getClass());
}
