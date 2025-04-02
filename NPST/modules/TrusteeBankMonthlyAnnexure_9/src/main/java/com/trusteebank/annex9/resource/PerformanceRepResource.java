package com.trusteebank.annex9.resource;

import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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

import TrusteeBankMonthlyAnnexure_9.constants.TrusteeBankMonthlyAnnexure_9PortletKeys;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import compliance.service.model.PerformanceRep;
import compliance.service.service.PerformanceRepLocalService;
import nps.email.api.api.PerformanceRepEmailApi;
import nps.email.api.api.PerformanceRepSmsApi;

//@Component(property = { 
//		"javax.portlet.name=" + TrusteeBankMonthlyAnnexure_9PortletKeys.TRUSTEEBANKMONTHLYANNEXURE_9,
//		"mvc.command.name=/performancerep/save" 
//		}, 
//service = MVCResourceCommand.class)

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + TrusteeBankMonthlyAnnexure_9PortletKeys.TRUSTEEBANKMONTHLYANNEXURE_9,
				"mvc.command.name=" +  TrusteeBankMonthlyAnnexure_9PortletKeys.performanceRep, 
		},
		service = MVCResourceCommand.class
	)

public class PerformanceRepResource implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(PerformanceRepResource.class);
	
	private static final String BLANK = "";

	private String statusByUserName;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		_log.info("Serve Resource method");
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		resultJsonObject.put("msg","");
		
				if(ValidateFileName.isValidfile(uploadPortletRequest.getFileName("perfromanceRepFile"))) {
					performanceRepInfo(resourceRequest, resourceResponse);	
				}else {
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg","Please upload files with a valid filename.");
				}
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		
		
		
		return false;
	}
	
	
	public void performanceRepInfo(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside performance info");
		
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
		
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date reportdate = ParamUtil.getDate(resourceRequest, "repdate", dateFormat);
	//	String addressLine1 = ParamUtil.getString(resourceRequest, "address1", BLANK);
		//String addressLine2 = ParamUtil.getString(resourceRequest, "address2", BLANK);
		//String addressLine3 = ParamUtil.getString(resourceRequest, "address3", BLANK);
		//String trusteeBankName = ParamUtil.getString(resourceRequest, "trusteeName", BLANK);
		//Date date2 = ParamUtil.getDate(resourceRequest, "date2", dateFormat);
		//String officerName = ParamUtil.getString(resourceRequest, "officername", BLANK);
		//Date submitDate = ParamUtil.getDate(resourceRequest, "submitdate", dateFormat);
		//String place = ParamUtil.getString(resourceRequest, "place", BLANK);
		
		//_log.info("reportdate: " + reportdate + " addressLine1: " + addressLine1 + " addressLine2: " + addressLine2);
		//_log.info("addressLine3: " + addressLine3 + " trusteeBankName: " + trusteeBankName + " date2: " + date2);
		//_log.info("officerName: " + officerName + " submitDate: " + submitDate + " place: " + place);
		
		//PerformanceRep performanceRep = performanceRepLocalService.addPerformanceRep(reportdate, addressLine1, addressLine2, addressLine3, trusteeBankName, date2, officerName, submitDate, place);
		
		//_log.info("performanceRep: " + performanceRep);
		
		try {
			pdfUpload(themeDisplay, resourceRequest, reportUploadLogId, serviceContext, statusByUserName);
			//createPdf(performanceRep, themeDisplay, resourceRequest, userId, reportUploadLogId, serviceContext, userName);
		} catch (Exception e) {
			 _log.error(e);
		}
		
		emailApi.sendOTP("titisoh951@pantabi.com"); 
		
//		String mobileNo = "919945255124";
//		String name = "Deepak";
//		smsApi.sendFormSubmitSuccessfulSMS(mobileNo, name);
		
		
	}
	
	public void createPdf1(PerformanceRep performanceRep, ThemeDisplay themeDisplay, ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, ServiceContext serviceContext2, String userName) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;
        
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        
        // PDRectangle.LETTER and others are also possible
        PDRectangle rect = page.getMediaBox();
        
        // rect can be used to get the page width and height
        document.addPage(page);

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos = new PDPageContentStream(document, page);
        //cos.b
	    //Dummy Table
        float margin = 30;
        _log.info("rect.getHeight()"+rect.getHeight());
        
        // starting y position is whole page height subtracted by top and bottom margin
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
        Cell<PDPage> cell = headerRow.createCell(100, "Annexure 9 - Format Of Performance Report And Deviations From The Trustee Bank To NPS Trust");
        
        //cell.setFont(fontBold);
        cell.setFontSize(20);
       
        cell.setAlign(HorizontalAlignment.CENTER);
        
        table.addHeaderRow(headerRow);
        table.draw();

        float tableHeight = table.getHeaderAndDataHeight();
        _log.info("tableHeight = "+tableHeight);
        
        float table2YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) -30;
        
        BaseTable table2 = new BaseTable(table2YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
        
        Row<PDPage> table2row = table2.createRow(20);
        Cell<PDPage> table2cell = table2row.createCell(30, "Date: " + dateFormat.format(performanceRep.getReportdate()));
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell = table2row.createCell(70, "");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2.addHeaderRow(table2row);
        
        table2row = table2.createRow(20);
        table2row.createCell(30, "To");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell = table2row.createCell(70, "");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        
        table2row = table2.createRow(20);
        table2row.createCell(30, "The Chief Executive Officer");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell = table2row.createCell(70, "");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        
        table2row = table2.createRow(20);
        table2row.createCell(40, "National Pension System (NPS) Trust");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(60, "");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2row.createCell(30, performanceRep.getAddressline1());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2row.createCell(30, performanceRep.getAddressline2());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2row.createCell(30, performanceRep.getAddressline3());
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(10);
        
        table2.draw();
        
        float tableHeight2 = table2.getHeaderAndDataHeight();
        _log.info("tableHeight2 = "+tableHeight2);
        
        float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 20;
        BaseTable table3 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
        
        Row<PDPage> table3row = table3.createRow(20);
        Cell<PDPage> table3cell = table3row.createCell(90, "Sub: Performance report of the Trustee Bank i.e. " + performanceRep.getTrusteebankname() +  " for the month ending " + dateFormat.format(performanceRep.getMonthendingdate()));
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFont(fontBold);
        table3cell.setFontSize(10);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFont(fontBold);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(30, "Sir/Madam");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFont(fontBold);
        table3cell.setFontSize(10);
        table3cell = table3row.createCell(70, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(60, "Name and registration no. of the Trustee Bank:");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFont(fontBold);
        table3cell.setFontSize(10);
        table3cell = table3row.createCell(60, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(100, "2. Trustee Bank had ensured that it has provided banking facilities in accordance with the provisions of the directions issued by the Authority in terms of the Act, the rules and the regulations made there under, operational service level agreements executed with the National Pension System Trust and the approved standard operating procedure based on the Authority's guidelines.");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(100, "3. All the reports have been submitted to Authority/NPS Trust as prescribed by the Authority in the regulations/guidelines.");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(100, "4. The summarized data of Trustee Bank's collection/rejection report, transaction details, average balance and balances of various accounts along with report of any deviation/incident is submitted as per Annexure 10 for information of the NPS Trust");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(30, "Signature ");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell = table3row.createCell(70, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(40, "Name of the officer " + performanceRep.getOfficername());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell = table3row.createCell(60, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(30, "Date " + dateFormat.format(performanceRep.getSubmitdate()));
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell = table3row.createCell(70, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(30, "Place " + performanceRep.getPlace());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell = table3row.createCell(70, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File performanceFile = File.createTempFile("Performance Report", "pdf");
		outputStream = new FileOutputStream(performanceFile);
		document.save(outputStream);
        String filepath = performanceFile.getPath(); 
		String filename = performanceFile.getName();
        
		document.close();
        outputStream.close();

        //document.save("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\blank.pdf");
        //document.save("C:\\Users\\deepak\\Documents\\pdf\\performancereport.pdf");
        //document.save("E:\\pdf\\blank.pdf");
        //document.close();
        
        try {
            pdfUpload(themeDisplay, resourceRequest, reportUploadLogId, serviceContext2, userName);
		} catch (Exception e) {
			_log.error("Exception in uploading Performance Report Form PDF: " + e.getMessage());
		}
        
	}
	
	@SuppressWarnings("deprecation")
	public String pdfUpload(ThemeDisplay themeDisplay,
			ResourceRequest resourceRequest, long reportUploadLogId, ServiceContext serviceContext2, String userName) {
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		Date date=new Date();
		File file = uploadPortletRequest.getFile("perfromanceRepFile");
		String fileName =date.getTime()+ file.getName();
		String mimeType = uploadPortletRequest.getContentType("perfromanceRepFile");

		String title = fileName;
		String description = fileName;
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {
			Folder folder = getFolder(themeDisplay);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), title, mimeType, title, description, "",
					file, serviceContext);
			
			updateReportLog(themeDisplay.getUserId(), entry.getFileEntryId(), true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, userName, serviceContext2);
			
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	@Reference
	PerformanceRepLocalService performanceRepLocalService;
	
	@Reference
	PerformanceRepEmailApi emailApi;
	
//	@Reference
//	PerformanceRepSmsApi smsApi;
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext) {
		Date createDate = new Date();
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,"");//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	

}
