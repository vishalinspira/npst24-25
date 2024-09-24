package com.further.processcra.resource;

import com.daily.pfm.service.model.FurtherProcessFive;
import com.daily.pfm.service.model.FurtherProcessFour;
import com.daily.pfm.service.model.FurtherProcessOne;
import com.daily.pfm.service.model.FurtherProcessSix;
import com.daily.pfm.service.model.FurtherProcessThree;
import com.daily.pfm.service.model.FurtherProcessTwo;
import com.daily.pfm.service.service.FurtherProcessFiveLocalServiceUtil;
import com.daily.pfm.service.service.FurtherProcessFourLocalServiceUtil;
import com.daily.pfm.service.service.FurtherProcessOneLocalServiceUtil;
import com.daily.pfm.service.service.FurtherProcessSixLocalServiceUtil;
import com.daily.pfm.service.service.FurtherProcessThreeLocalServiceUtil;
import com.daily.pfm.service.service.FurtherProcessTwoLocalServiceUtil;
import com.further.processcra.constants.FurtherProcessCraPortletKeys;
import com.further.processcra.util.ParseFurtherSheetFive;
import com.further.processcra.util.ParseFurtherSheetFour;
import com.further.processcra.util.ParseFurtherSheetOne;
import com.further.processcra.util.ParseFurtherSheetSix;
import com.further.processcra.util.ParseFurtherSheetThree;
import com.further.processcra.util.ParseFurtherSheetTwo;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
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
import nps.email.api.api.ExcelValidationAn7Api;

@Component(property = { "javax.portlet.name=" + FurtherProcessCraPortletKeys.FURTHERPROCESSCRA,
"mvc.command.name=/furtherprocess/save" }, service = MVCResourceCommand.class)
public class SaveFurtherProcessCra implements MVCResourceCommand{
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

		String fileName = uploadPortletRequest.getFileName("furtherprocessFile");

		File file = uploadPortletRequest.getFile("furtherprocessFile");
		//excelValidationAn10Api.validateExcelFile(file, resourceRequest);

		String mimeType = uploadPortletRequest.getContentType("furtherprocessFile");

		String title = fileName;
		
		JSONArray furtherProcessOneJsonArray= JSONFactoryUtil.createJSONArray();
		List<FurtherProcessOne> furtherProcessOnes= new ArrayList<FurtherProcessOne>();
		
		JSONArray furtherProcessTwoJsonArray= JSONFactoryUtil.createJSONArray();
		List<FurtherProcessTwo> furtherProcessTwos= new ArrayList<FurtherProcessTwo>();
		
		JSONArray furtherProcessThreeJsonArray= JSONFactoryUtil.createJSONArray();
		List<FurtherProcessThree> furtherProcessThrees= new ArrayList<FurtherProcessThree>();
		
		JSONArray furtherProcessFourJsonArray= JSONFactoryUtil.createJSONArray();
		List<FurtherProcessFour> furtherProcessFours= new ArrayList<FurtherProcessFour>();
		
		JSONArray furtherProcessFiveJsonArray= JSONFactoryUtil.createJSONArray();
		List<FurtherProcessFive> furtherProcessFives= new ArrayList<FurtherProcessFive>();
		
		JSONArray furtherProcessSixJsonArray= JSONFactoryUtil.createJSONArray();
		List<FurtherProcessSix> furtherProcessSixs= new ArrayList<FurtherProcessSix>();
		
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
		
		ParseFurtherSheetOne.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, furtherProcessOneJsonArray, furtherProcessOnes);
		ParseFurtherSheetTwo.saveSheetTwo(file, workbook, userId, errorArray, xx, isexcelhaserror, furtherProcessTwoJsonArray, furtherProcessTwos);
		ParseFurtherSheetThree.saveSheetThree(file, workbook, userId, errorArray, xx, isexcelhaserror, furtherProcessThreeJsonArray, furtherProcessThrees);
		ParseFurtherSheetFour.saveSheetFour(file, workbook, userId, errorArray, xx, isexcelhaserror, furtherProcessFourJsonArray, furtherProcessFours);
		ParseFurtherSheetFive.saveSheetFive(file, workbook, userId, errorArray, xx, isexcelhaserror, furtherProcessFiveJsonArray, furtherProcessFives);
		ParseFurtherSheetSix.saveSheetSix(file, workbook, userId, errorArray, xx, isexcelhaserror, furtherProcessSixJsonArray, furtherProcessSixs);
		
		_log.info(furtherProcessOneJsonArray.toString());
		
		if (!isexcelhaserror) {
			
			
			FurtherProcessOneLocalServiceUtil.addFurtherProcessOnes(furtherProcessOnes);
			FurtherProcessTwoLocalServiceUtil.addFurtherProcessTwos(furtherProcessTwos);
			FurtherProcessThreeLocalServiceUtil.addFurtherProcessThrees(furtherProcessThrees);
			FurtherProcessFourLocalServiceUtil.addFurtherProcessFours(furtherProcessFours);
			FurtherProcessFiveLocalServiceUtil.addFurtherProcessFives(furtherProcessFives);
			FurtherProcessSixLocalServiceUtil.addFurtherProcessSixs(furtherProcessSixs);
			
			uploadFILETOFOLDER(themeDisplay, resourceRequest);
			try {
				errorExcel.close();
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
	public void uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("furtherprocessFile");

		File file = uploadPortletRequest.getFile("furtherprocessFile");

		String mimeType = uploadPortletRequest.getContentType("furtherprocessFile");

		String title = fileName;

		String description = "furtherprocessFile";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);

		} catch (Exception e) {

			_log.info(e.getMessage());

			 _log.error(e);
		}
	}
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Cra");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());

}
