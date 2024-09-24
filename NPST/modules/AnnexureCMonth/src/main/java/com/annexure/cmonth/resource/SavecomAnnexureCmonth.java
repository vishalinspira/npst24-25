package com.annexure.cmonth.resource;

import com.annexure.cmonth.constants.AnnexureCMonthPortletKeys;
import com.annexure.cmonth.util.AnnexureCmonthUtil;
import com.daily.average.service.service.ReportUploadLogCRALocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import compliance.service.service.AnnexureCLocalService;
import compliance.service.service.Status_SGLocalService;
import nps.email.api.api.ExcelValidationAn7Api;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + AnnexureCMonthPortletKeys.ANNEXURECMONTH,
				"mvc.command.name=annexCResourceExcelURl/Save"
		},
		service = MVCResourceCommand.class
		)
public class SavecomAnnexureCmonth implements MVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(SavecomAnnexureCmonth.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		System.err.println("SavecomAnnexureCmonth.serveResource()");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Long userId = themeDisplay.getUserId();
		boolean isError = false;
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		File file = uploadPortletRequest.getFile("monthFile");
		
		//workflow methods
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
		//
		
		try {
			if(Validator.isNotNull(file)) {
				OPCPackage pkg = OPCPackage.open(file);
				XSSFWorkbook workbook  = new XSSFWorkbook(pkg);
				
				if(Validator.isNotNull(workbook.getSheet("Annexure C")))
					uploadSummaryOfAnnexureCSheet(annexureCmonthUtil.getRowsForAnnexureCSheet(), workbook.getSheet("Annexure C"), themeDisplay);
				
				if(Validator.isNotNull(workbook.getSheet("Overall Status_SG")))
					uploadSummaryOfStatusSheet(annexureCmonthUtil.getRowsForOverAllStatusSheet(), workbook.getSheet("Overall Status_SG"), themeDisplay);
			}
		} catch (Exception e) {
			isError = true;
			_log.error("Exception in file upload : "+e.getMessage());
		}finally {
			uploadPortletRequest.cleanUp();
		}
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				Long fileEntryId = 0l;
				fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
				} catch (Exception e) {
					_log.error(e);
				}
				writer.write("success");
			}else {
				writer.write("error");
			}
		} catch (IOException e) {
			_log.error("Exception in printwriter try catch : "+e.getMessage());
		}
		
		return false;
	}
	
	private void uploadSummaryOfAnnexureCSheet(List<Integer> rowsForAnnexureCExcel, XSSFSheet sheet, ThemeDisplay themeDisplay) {
		for(Integer i : rowsForAnnexureCExcel) {
			Integer rownum = i - 1;
			
			try {
				XSSFRow row = sheet.getRow(rownum);
				
				annexureCLocalService.addAnnexureC(row.getCell(1).toString().trim(), String.valueOf(row.getCell(2).getNumericCellValue()), row.getCell(3).toString().trim(), row.getCell(4).toString().trim(), 
													row.getCell(5).toString().trim(), row.getCell(6).toString().trim(), String.valueOf(row.getCell(7).getNumericCellValue()), String.valueOf(row.getCell(8).getNumericCellValue()), 
													String.valueOf(row.getCell(9).getNumericCellValue()), String.valueOf(row.getCell(10).getNumericCellValue()), String.valueOf(row.getCell(11).getNumericCellValue()), String.valueOf(row.getCell(12).getNumericCellValue()), 
													new Date(), themeDisplay.getUserId());
				
			} catch (Exception e) {
				_log.error("Exception in uploadSummaryOfAnnexureCSheet : "+e.getMessage());
			}
		}
	}
	
	private void uploadSummaryOfStatusSheet(List<Integer> rowsForAnnexureCExcel, XSSFSheet sheet, ThemeDisplay themeDisplay) {
		for(Integer i : rowsForAnnexureCExcel) {
			Integer rownum = i - 1;
			
			try {
				XSSFRow row = sheet.getRow(rownum);
				
				DataFormatter formatter = new DataFormatter();
				
				String valOne = formatter.formatCellValue(row.getCell(9));
				String valTwo = formatter.formatCellValue(row.getCell(10));
				
				status_SGLocalService.addStatusSG(row.getCell(2).toString().trim(), row.getCell(3).toString().trim(), 
						row.getCell(4).toString().trim(), row.getCell(5).toString().trim(),
						row.getCell(6).toString().trim(), row.getCell(7).toString().trim(),row.getCell(8).toString().trim(), valOne == "NUMERIC" ? String.valueOf(row.getCell(9).getNumericCellValue()) : row.getCell(9).toString().trim(), 
						valTwo == "NUMERIC" ? String.valueOf(row.getCell(10).getNumericCellValue()) : row.getCell(10).toString().trim(),new Date(), themeDisplay.getUserId());
				
			} catch (Exception e) {
				_log.error("Exception in uploadSummaryOfAnnexureCSheet : "+e.getMessage());
			}
		}
	}
	
//	public static String formatDate(Date dateIn, String format) throws ParseException {
//        String strDate = "";
//
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
//
//        String formattedDate = sdf.format(dateIn);
//        try {
//            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
//            Date d1 = sdf3.parse(formattedDate);
//
//            SimpleDateFormat formatter = new SimpleDateFormat(format);
//            strDate = formatter.format(d1);
//        } catch (Exception e) {
//            _log.error(e);
//        }
//        return strDate;
//    }
	
	@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		
		Date date =new Date();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime() + uploadPortletRequest.getFileName("monthFile");

		File file = uploadPortletRequest.getFile("monthFile");

		String mimeType = uploadPortletRequest.getContentType("monthFile");

		String title = fileName;

		String description = "monthFile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "CRA");
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
	AnnexureCmonthUtil annexureCmonthUtil;
	
	@Reference
	AnnexureCLocalService annexureCLocalService;
	
	@Reference
	Status_SGLocalService status_SGLocalService;
	
	@Reference
	ExcelValidationAn7Api excelValidationAn7Api;
}
