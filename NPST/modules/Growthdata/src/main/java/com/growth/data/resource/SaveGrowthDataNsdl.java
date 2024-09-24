package com.growth.data.resource;

import com.daily.pfm.service.model.GrowthDataOne;
import com.daily.pfm.service.model.GrowthDataTwo;
import com.daily.pfm.service.service.GrowthDataOneLocalServiceUtil;
import com.daily.pfm.service.service.GrowthDataTwoLocalServiceUtil;
import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalServiceUtil;
import com.daily.pfm.service.model.GrowthDataFive;
import com.daily.pfm.service.model.GrowthDataFour;
import com.daily.pfm.service.model.GrowthDataThree;
import com.daily.pfm.service.service.GrowthDataFiveLocalServiceUtil;
import com.daily.pfm.service.service.GrowthDataFourLocalServiceUtil;
import com.daily.pfm.service.service.GrowthDataThreeLocalServiceUtil;
import com.growth.data.constants.GrowthdataPortletKeys;
import com.growth.data.util.GrowthdataSheetFive;
import com.growth.data.util.GrowthdataSheetFour;
import com.growth.data.util.GrowthdataSheetOne;
import com.growth.data.util.GrowthdataSheetThree;
import com.growth.data.util.GrowthdataSheetTwo;
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

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;


@Component(property = { "javax.portlet.name=" + GrowthdataPortletKeys.GROWTHDATA,
"mvc.command.name=/growthdata/save" }, service = MVCResourceCommand.class)
public class SaveGrowthDataNsdl implements MVCResourceCommand{
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

		String fileName = uploadPortletRequest.getFileName("growthdataFile");

		File file = uploadPortletRequest.getFile("growthdataFile");

		String mimeType = uploadPortletRequest.getContentType("growthdataFile");

		String title = fileName;
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		String cra = IntermediaryListLocalServiceUtil.fetchIntermediaryByReportLogId(reportUploadLogId);
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray growthdataOneJsonArray= JSONFactoryUtil.createJSONArray();
		List<GrowthDataOne> growthdataOnes= new ArrayList<GrowthDataOne>();
		
		JSONArray growthDataTwoJsonArray= JSONFactoryUtil.createJSONArray();
		List<GrowthDataTwo> growthDataTwos= new ArrayList<GrowthDataTwo>();
		
		JSONArray growthDataThreeJsonArray= JSONFactoryUtil.createJSONArray();
		List<GrowthDataThree> growthDataThrees= new ArrayList<GrowthDataThree>();
		
		JSONArray growthDataFourJsonArray= JSONFactoryUtil.createJSONArray();
		List<GrowthDataFour> growthDataFours= new ArrayList<GrowthDataFour>();
		
		JSONArray growthDataFiveJsonArray= JSONFactoryUtil.createJSONArray();
		List<GrowthDataFive> growthDataFives= new ArrayList<GrowthDataFive>();
		
		
		
		
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
		
		ArrayList <String> list = new ArrayList<String>();
	      list.add("PFM AUM Growth");
	      list.add("Schemewise Growth");
	      list.add("PFM wise Scheme Growth");
	      list.add("Sector wise Scheme Growth");
		
		boolean isexcelhaserror = false;
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					Iterator<Sheet> sheets1 = workbook.sheetIterator();
					
					while(sheets1.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets1.next();
						if(list.contains(sheet.getSheetName())) {
							list.remove(sheet.getSheetName());
						}
					}
					
					if (list.size()>0) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray(list);
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
						
					}
				}
			}catch (Exception e) {
			}
			
			//parsing sheetone in util class
			resultJsonObject = GrowthdataSheetOne.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, growthdataOnes, growthdataOneJsonArray, decimalFormat, reportUploadLogId, cra, resultJsonObject);
			if(!resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			//GrowthdataSheetTwo.saveSheetTwo(file, workbook, userId, errorArray, xx, isexcelhaserror, growthDataTwos, growthDataTwoJsonArray, decimalFormat,reportUploadLogId, cra, resultJsonObject);
			resultJsonObject = GrowthdataSheetThree.saveSheetThree(file, workbook, userId, errorArray, xx, isexcelhaserror, growthDataThrees, growthDataThreeJsonArray, decimalFormat,reportUploadLogId, cra, resultJsonObject);
			if(!resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = GrowthdataSheetFour.saveSheetTwo(file, workbook, userId, errorArray, xx, isexcelhaserror, growthDataFours, growthDataFourJsonArray, decimalFormat,reportUploadLogId, cra, resultJsonObject);
			if(!resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			resultJsonObject = GrowthdataSheetFive.saveSheetFive(file, workbook, userId, errorArray, xx, isexcelhaserror, growthDataFives, growthDataFiveJsonArray, decimalFormat,reportUploadLogId, cra, resultJsonObject);
			if(!resultJsonObject.getBoolean("status")) 
				return resultJsonObject;
			_log.info(growthDataTwoJsonArray.toString());
			
			if (!isexcelhaserror) {
				
				try {
					GrowthDataOneLocalServiceUtil.addGrowthDataOnes(growthdataOnes);
					_log.info("growthdataOnes datacount"+growthdataOnes.size());
					
					/*
					 * GrowthDataTwoLocalServiceUtil.addGrowthDataTwos(growthDataTwos);
					 * _log.info("growthDataTwos datacount"+growthDataTwos.size());
					 */
					
					GrowthDataThreeLocalServiceUtil.addGrowthDataThrees(growthDataThrees);
					_log.info("growthDataThrees datacount"+growthDataThrees.size());
					
					GrowthDataFourLocalServiceUtil.addGrowthDataFours(growthDataFours);
					_log.info("growthDataFours datacount"+growthDataFours.size());
					
					GrowthDataFiveLocalServiceUtil.addGrowthDataFives(growthDataFives);
					_log.info("growthDataFives datacount"+growthDataFives.size());
				} catch (Exception e1) {
					_log.error(e1);
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.fileExceptionMsg );
					return resultJsonObject;
				}
				
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

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("growthdataFile");

		File file = uploadPortletRequest.getFile("growthdataFile");

		String mimeType = uploadPortletRequest.getContentType("growthdataFile");

		String title = fileName;

		String description = "growthdataFile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "PFM");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogPFMCRALocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	Log _log = LogFactoryUtil.getLog(getClass());
}
