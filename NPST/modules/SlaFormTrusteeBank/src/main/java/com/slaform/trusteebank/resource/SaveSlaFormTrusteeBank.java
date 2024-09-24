package com.slaform.trusteebank.resource;

import com.daily.average.service.model.SrlForm;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.daily.average.service.service.SrlFormLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.slaform.trusteebank.constants.slaFormtrusteebankPortletKeys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import nps.email.api.api.ClosingBalanceEmailApi;

@Component(property = { 
		"javax.portlet.name=" + slaFormtrusteebankPortletKeys.SLAFORMTRUSTEEBANK,
		"mvc.command.name=/srlform/save" 
		}, 
service = MVCResourceCommand.class)

public class SaveSlaFormTrusteeBank implements MVCResourceCommand{
	
	private static final String BLANK = "";
	
	private static Log _log = LogFactoryUtil.getLog(SaveSlaFormTrusteeBank.class);

	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Inside serve resource");
		
		JSONObject resultJsonObject = srlFormInfo(resourceRequest, resourceResponse);
		
		try { 
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}
	
public JSONObject srlFormInfo(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside closing info");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long userId = 0L;
		String userName = "NA";
		try {
			userId = themeDisplay.getUserId();
			userName = themeDisplay.getUser().getFullName();
		} catch(Exception e) {
			 _log.error(e);
		}
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		
		String one = ParamUtil.getString(resourceRequest, "one");
		Date time_1 = ParamUtil.getDate(resourceRequest, "time_1", dateFormat, null);
		String remarks_1 = ParamUtil.getString(resourceRequest, "remarks_1");
		String two = ParamUtil.getString(resourceRequest, "two");
		Date time_2 = ParamUtil.getDate(resourceRequest, "time_2", dateFormat, null);
		String remarks_2 = ParamUtil.getString(resourceRequest, "remarks_2");
		String three = ParamUtil.getString(resourceRequest, "three");
		Date time_3 = ParamUtil.getDate(resourceRequest, "time_3", dateFormat, null);
		String remarks_3 = ParamUtil.getString(resourceRequest, "remarks_3");
		String four = ParamUtil.getString(resourceRequest, "four");
		Date time_4 = ParamUtil.getDate(resourceRequest, "time_4", dateFormat, null);
		String remarks_4 = ParamUtil.getString(resourceRequest, "remarks_4");
		String five = ParamUtil.getString(resourceRequest, "five");
		Date time_5 = ParamUtil.getDate(resourceRequest, "time_5", dateFormat, null);
		String remarks_5 = ParamUtil.getString(resourceRequest, "remarks_5");
		String six = ParamUtil.getString(resourceRequest, "six");
		Date time_6 = ParamUtil.getDate(resourceRequest, "time_6", dateFormat, null);
		String remarks_6 = ParamUtil.getString(resourceRequest, "remarks_6");
		String seven = ParamUtil.getString(resourceRequest, "seven");
		Date time_7 = ParamUtil.getDate(resourceRequest, "time_7", dateFormat, null);
		String remarks_7 = ParamUtil.getString(resourceRequest, "remarks_7");
		String eight = ParamUtil.getString(resourceRequest, "eight");
		Date time_8 = ParamUtil.getDate(resourceRequest, "time_8", dateFormat, null);
		String remarks_8 = ParamUtil.getString(resourceRequest, "remarks_8");
		
		SrlForm srlForm = SrlFormLocalServiceUtil.addSrlForm(one, time_1, remarks_1, two, time_2,
								remarks_2, three, time_3, remarks_3, four, time_4, remarks_4,
								five, time_5, remarks_5, six, time_6, remarks_6, seven, time_7,
								remarks_7, eight, time_8, remarks_8);
		try {
		srlForm=SrlFormLocalServiceUtil.getSrlForm(srlForm.getPrimaryKey());
		srlForm.setReportUploadLogId(reportUploadLogId);
		srlForm=SrlFormLocalServiceUtil.updateSrlForm(srlForm);
		_log.info("updated SRL FORM");
		}catch (Exception e) {
			_log.error(e.getMessage());
		}
		_log.info("reportUploadLogId"+reportUploadLogId);

		try {
			createPdf(srlForm, themeDisplay, resourceRequest, userId, reportUploadLogId, statusByUserName, serviceContext);
		} catch (Exception e) {
			 _log.error(e);
		}
		resultJsonObject.put("status", true);
		return resultJsonObject;
	}
	
	public void createPdf(SrlForm srlForm, ThemeDisplay themeDisplay, ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext2)throws IOException, JSONException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;
        
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        
        PDRectangle rect = page.getMediaBox();
        document.addPage(page);
        
        PDPageContentStream cos = new PDPageContentStream(document, page);
        
        float margin = 30;
        _log.info("rect.getHeight()"+rect.getHeight());
        
        float yStartNewPage = rect.getUpperRightY() - (2 * margin);
        
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float topMargin = 0;
        float bottomMargin = 10;
        
        // y position is your coordinate of top left corner of the table
        float yPosition = rect.getUpperRightY()-margin;

        BaseTable table = new BaseTable(yPosition, yStartNewPage,topMargin,
            bottomMargin, tableWidth, margin, document, page, false, drawContent);
        //new Baset

        // the parameter is the row height
        Row<PDPage> headerRow = table.createRow(20);
        
        // the first parameter is the cell width        
        Cell<PDPage> cell = headerRow.createCell(100, "SLA Form");
        cell.setFontSize(20);
        cell.setAlign(HorizontalAlignment.CENTER);
        table.addHeaderRow(headerRow);
        
        table.draw();

        float tableHeight = table.getHeaderAndDataHeight();
        _log.info("tableHeight = "+tableHeight);
        
        float table2YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) -30;
        
        BaseTable table2 = new BaseTable(table2YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
        
        Row<PDPage> table2row = table2.createRow(20);
        Cell<PDPage> table2cell = table2row.createCell(10,"S.No");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        table2.addHeaderRow(table2row);
        
        table2cell = table2row.createCell(20, "Nature Of Activity");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Cut-Off Time");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, "Activity completed within timeline");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Actual Time in case of deviation");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, "Reason for deviation");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "1.");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Return of unidentified Funds");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "T+1 day(For funds received on T day)");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getOne());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, (Validator.isNotNull(srlForm.getTime_1()))?dateFormat2.format(srlForm.getTime_1()):"");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getRemarks_1());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "2.");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Upload of Normal Fund receipt confirmation file");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "By 9:15 AM on T+1 day(for clear funds received on T Day)");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getTwo());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, (Validator.isNotNull(srlForm.getTime_2()))?dateFormat2.format(srlForm.getTime_2()):"");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getRemarks_2());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "3.");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Upload of D- Remit Fund receipt confirmation file");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "By 10:30 AM on T Day(For clear funds received between 9:30:01 AM on T-1 and 9:30:00 AM on T)");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getThree());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, (Validator.isNotNull(srlForm.getTime_3()))? dateFormat2.format(srlForm.getTime_3()):"");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getRemarks_3());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "4.");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Download of Pay in instruction files");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "By 11:30 AM");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getFour());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, (Validator.isNotNull(srlForm.getTime_4()))?dateFormat2.format(srlForm.getTime_4()):"");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getRemarks_4());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "5.");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Cut-Off time for confirmation of transfer of funds to PFs");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "PF Transaction processing: 1:30PM");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getFive());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, (Validator.isNotNull(srlForm.getTime_5()))?dateFormat2.format(srlForm.getTime_5()):"");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getRemarks_5());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "6.");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Cut-Off time for confirmation of transfer of funds to withdrawal account");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "EOD");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getSix());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, (Validator.isNotNull(srlForm.getTime_6()))?dateFormat2.format(srlForm.getTime_6()):"");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getRemarks_6());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "7.");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Transfer of Matched and Booked funds to PFs");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Within 25 min from download of pay-in");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getSeven());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, (Validator.isNotNull(srlForm.getTime_7()))?dateFormat2.format(srlForm.getTime_7()):"");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getRemarks_7());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "8.");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "Upload of statements and closing balance of various accounts");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, "EOD");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getEight());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(20, (Validator.isNotNull(srlForm.getTime_8()))?dateFormat2.format(srlForm.getTime_8()):"");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2cell = table2row.createCell(15, srlForm.getRemarks_8());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File closingBalanceFile = File.createTempFile("SLAForm", "pdf");
		outputStream = new FileOutputStream(closingBalanceFile);
		document.save(outputStream);
        String filepath = closingBalanceFile.getPath(); 
		String filename = closingBalanceFile.getName();
		//document.save("D:\\blank.pdf");
		document.close();
        outputStream.close();
        
        try {
            pdfUpload(themeDisplay, filepath, filename, resourceRequest, createdBy, reportUploadLogId, statusByUserName, serviceContext2);
		} catch (Exception e) {
			_log.error("Exception in uploading SLA Form PDF: " + e.getMessage());
		}

	}
	
	@SuppressWarnings("deprecation")
	public String pdfUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext2) {

		File file = new File(filepath);
		String mimeType = "application/pdf";
		String title = filename + ".pdf";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {
			Folder folder = getFolder(themeDisplay);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), title, mimeType, title, description, "",
					file, serviceContext);
			
			updateReportLog(createdBy, entry.getFileEntryId(), true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext2);
			
			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			_log.info(e.getMessage());
			 _log.error(e);
		}
		return downloadUrl;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Daily");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext) {
		Date createDate = new Date();
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, "");//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	

}