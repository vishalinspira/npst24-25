package com.annualevoting.mvcresource;

import com.annualevoting.util.AnnualPFVotingRec;
import com.annualevoting.util.FinalAnnualVoteCount;
import com.annualevoting.util.ValidateFileName;
import com.annualevoting.util.ValidateSheetName;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
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
import compliance.service.model.AnFinalAnnualVoteCount;
import compliance.service.model.AnPFVotingRec;
import compliance.service.service.AnFinalAnnualVoteCountLocalServiceUtil;
import compliance.service.service.AnPFVotingRecLocalServiceUtil;
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
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import nps.email.api.api.ExcelValidationAn10Api;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = {"javax.portlet.name=com_annualevoting_AnnualEvotingPortlet", "mvc.command.name=SaveAnnualEvoting"}, service = {MVCResourceCommand.class})
public class SaveAnnualEvoting implements MVCResourceCommand {
  private static Log _log = LogFactoryUtil.getLog(SaveAnnualEvoting.class);
  
  public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
    _log.info("Serve Resource method");
    ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute("LIFERAY_SHARED_THEME_DISPLAY");
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
    Long userId = Long.valueOf(themeDisplay.getUserId());
    UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest((PortletRequest)resourceRequest);
    String statusByUserName = themeDisplay.getUser().getFullName();
    ServiceContext serviceContext = null;
    try {
      serviceContext = ServiceContextFactory.getInstance((PortletRequest)resourceRequest);
    } catch (PortalException e) {
      _log.error((Throwable)e);
    } 
    String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
    _log.info("reportUploadLogIdString" + reportUploadLogIdString);
    Long reportUploadLogId = Long.valueOf(Long.parseLong(reportUploadLogIdString));
    _log.info("reportUploadLogId" + reportUploadLogId);
    String fileName = uploadPortletRequest.getFileName("annualEVotingFile");
    File file = uploadPortletRequest.getFile("annualEVotingFile");
    String mimeType = uploadPortletRequest.getContentType("annualEVotingFile");
    String title = fileName;
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator(',');
    symbols.setDecimalSeparator('.');
    String pattern = "#,##0.0#";
    DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
    decimalFormat.setParseBigDecimal(true);
    JSONArray finalAnnualVoteCountJsonArray = JSONFactoryUtil.createJSONArray();
    List<AnFinalAnnualVoteCount> finalAnnualVoteCountList = new ArrayList<>();
    JSONArray annualPFVotingRecJsonArray = JSONFactoryUtil.createJSONArray();
    List<AnPFVotingRec> annualPFVotingRecList = new ArrayList<>();
    JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
    if(ValidateFileName.isValidfile(fileName)) {
    resultJsonObject = this.excelValidationAn10Api.validateExcelFile(file, (PortletRequest)resourceRequest);
    if (resultJsonObject.getBoolean("status")) {
      JSONArray errorArray = JSONFactoryUtil.createJSONArray();
      XSSFWorkbook workbook = null;
      XSSFWorkbook errorExcel = new XSSFWorkbook();
      XSSFSheet xx = errorExcel.createSheet();
      XSSFRow xxx = xx.createRow(1);
      XSSFCell rowNumCel = xxx.createCell(1);
      rowNumCel.setCellValue("RowNum");
      XSSFCell fileNamecell = xxx.createCell(2);
      fileNamecell.setCellValue("Pension Fund");
      boolean isexcelhaserror = false;
      List<String> errorList = ValidateSheetName.ValidateExcelSheetName(file, workbook);
      if (errorList.size() > 0) {
        resultJsonObject.put("status", false);
        resultJsonObject.put("sheeterror", true);
        StringBuilder sb = new StringBuilder();
        for (String L : errorList) {
          if (errorList.size() == 1) {
            sb.append(L);
            continue;
          } 
          sb.append(L);
          sb.append(",");
        } 
        String errorString = sb.toString();
        resultJsonObject.put("errorSheetNameList", errorString);
        try {
          errorExcel.close();
        } catch (IOException e) {
          _log.error(e);
        } 
        return resultJsonObject;
      } 
      resultJsonObject = FinalAnnualVoteCount.saveFinalAnnualVoteCount(file, workbook, userId.longValue(), errorArray, xx, isexcelhaserror, finalAnnualVoteCountJsonArray, finalAnnualVoteCountList, decimalFormat, reportUploadLogId.longValue(), resultJsonObject);
      if (!resultJsonObject.getBoolean("status"))
        return resultJsonObject; 
      resultJsonObject = AnnualPFVotingRec.saveAnnualPFVotingRec(file, workbook, userId.longValue(), errorArray, xx, isexcelhaserror, annualPFVotingRecJsonArray, annualPFVotingRecList, decimalFormat, reportUploadLogId.longValue(), resultJsonObject);
      if (!resultJsonObject.getBoolean("status"))
        return resultJsonObject; 
      if (!isexcelhaserror) {
        AnFinalAnnualVoteCountLocalServiceUtil.addAnFinalAnnualVoteCount(finalAnnualVoteCountList);
        AnPFVotingRecLocalServiceUtil.addAnPFVotingRec(annualPFVotingRecList);
        Long fileEntryId = Long.valueOf(0L);
        fileEntryId = Long.valueOf(uploadFILETOFOLDER(themeDisplay, resourceRequest));
        try {
          errorExcel.close();
          String remarks = ParamUtil.getString((HttpServletRequest)uploadPortletRequest, "remarks");
          updateReportLog(userId.longValue(), fileEntryId.longValue(), true, reportUploadLogId.longValue(), 1, statusByUserName, serviceContext, remarks);
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
    }else {
		resultJsonObject.put("status", false);
		resultJsonObject.put("msg","Please upload files with a valid filename.");
		return resultJsonObject;
	}
    return resultJsonObject;
  }
  
  public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename, ResourceRequest resourceRequest) {
    File file = new File(filepath);
    String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    String title = filename + ".xlsx";
    String description = "This file is added via programatically";
    long repositoryId = themeDisplay.getScopeGroupId();
    String downloadUrl = "";
    try {
      ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), (PortletRequest)resourceRequest);
      FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, 0L, title, mimeType, title, description, "", file, serviceContext);
      FileVersion fileVersion = entry.getLatestFileVersion();
      downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, "");
    } catch (Exception e) {
      _log.info(e.getMessage());
      _log.error(e);
    } 
    return downloadUrl;
  }
  
  public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
    Date date = new Date();
    UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest((PortletRequest)resourceRequest);
    String fileName = date.getTime() + uploadPortletRequest.getFileName("annualEVotingFile");
    File file = uploadPortletRequest.getFile("annualEVotingFile");
    String mimeType = uploadPortletRequest.getContentType("annualEVotingFile");
    String title = fileName;
    String description = "annualEVotingFile";
    long repositoryId = themeDisplay.getScopeGroupId();
    try {
      Folder folder = getFolder(themeDisplay);
      ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), (PortletRequest)resourceRequest);
      FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description, "", file, serviceContext);
      return fileEntry.getFileEntryId();
    } catch (Exception e) {
      _log.info(e.getMessage());
      _log.error(e);
      return 0L;
    } 
  }
  
  private static long PARENT_FOLDER_ID = 0L;
  
  @Reference
  ExcelValidationAn10Api excelValidationAn10Api;
  
  public Folder getFolder(ThemeDisplay themeDisplay) {
    Folder folder = null;
    try {
      folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Annually");
      _log.info(folder);
    } catch (Exception e) {
      _log.info(e.getMessage());
    } 
    return folder;
  }
  
  public void updateReportLog(long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
    Date createDate = new Date();
    ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);
  }
}
