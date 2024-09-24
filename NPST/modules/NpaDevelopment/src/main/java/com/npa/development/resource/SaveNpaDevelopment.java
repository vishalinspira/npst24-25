package com.npa.development.resource;

import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.service.MnNpaDevelopmentLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.npa.development.constants.NpaDevelopmentPortletKeys;
import com.npa.development.util.NPADevelopmentCreatePdfUtil;
import com.npa.development.util.NPADevlopmentUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { 
		"javax.portlet.name=" + NpaDevelopmentPortletKeys.NPADEVELOPMENT,
		"mvc.command.name=" + NpaDevelopmentPortletKeys.npa,
		}, 
service = MVCResourceCommand.class)

public class SaveNpaDevelopment extends BaseMVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(SaveNpaDevelopment.class);
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("Serve Resource method");
		
		boolean isError = false;
		
		try {
			
			npaDev(resourceRequest, resourceResponse);
			
		} catch (Exception e) {
			isError = true;
			_log.error(e);
		}
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				_log.info("Success");
				writer.print("Success");
			}
			else {
				_log.info("Error");
				writer.print("Error");
			}
		} catch (Exception e) {
			_log.error(e);
		}	

	}
	
	public void npaDev(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside npaDev");
				
		//int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		int rowCount=8;
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		String companies = ParamUtil.getString(resourceRequest, "companies");
		String strDate = ParamUtil.getString(resourceRequest, "date_1");
	//	String[] nameOfSecurity = ParamUtil.getParameterValues(resourceRequest, "securityName[]");
	//	String[] legalCaseDetails = ParamUtil.getParameterValues(resourceRequest, "caseDetails[]");
	//	String[] currentStatus = ParamUtil.getParameterValues(resourceRequest, "currentStatus[]");
		
		String[] nameOfSecurity=new String[8]; 
		String[] legalCaseDetails=new String[8]; 
		String[] currentStatus=new String[8]; 
		
		 nameOfSecurity[0] = ParamUtil.getString(resourceRequest, "securityName1");
		 legalCaseDetails[0] = ParamUtil.getString(resourceRequest, "caseDetails1");
		 currentStatus[0] = ParamUtil.getString(resourceRequest, "currentStatus1");
		
		 nameOfSecurity[1] = ParamUtil.getString(resourceRequest, "securityName2");
		 legalCaseDetails[1] = ParamUtil.getString(resourceRequest, "caseDetails2");
		 currentStatus[1] = ParamUtil.getString(resourceRequest, "currentStatus2");
		
		 nameOfSecurity[2] = ParamUtil.getString(resourceRequest, "securityName3");
		 legalCaseDetails[2] = ParamUtil.getString(resourceRequest, "caseDetails3");
		 currentStatus[2] = ParamUtil.getString(resourceRequest, "currentStatus3");
		
		 nameOfSecurity[3] = ParamUtil.getString(resourceRequest, "securityName4");
		 legalCaseDetails[3] = ParamUtil.getString(resourceRequest, "caseDetails4");
		 currentStatus[3] = ParamUtil.getString(resourceRequest, "currentStatus4");
		 
		 nameOfSecurity[4] = ParamUtil.getString(resourceRequest, "securityName5");
		 legalCaseDetails[4] = ParamUtil.getString(resourceRequest, "caseDetails5");
		 currentStatus[4] = ParamUtil.getString(resourceRequest, "currentStatus5");
		 
		 nameOfSecurity[5] = ParamUtil.getString(resourceRequest, "securityName6");
		 legalCaseDetails[5] = ParamUtil.getString(resourceRequest, "caseDetails6");
		 currentStatus[5] = ParamUtil.getString(resourceRequest, "currentStatus6");
		 
		 nameOfSecurity[6] = ParamUtil.getString(resourceRequest, "securityName7");
		 legalCaseDetails[6] = ParamUtil.getString(resourceRequest, "caseDetails7");
		 currentStatus[6] = ParamUtil.getString(resourceRequest, "currentStatus7");
		 
		 nameOfSecurity[7] = ParamUtil.getString(resourceRequest, "securityName8");
		 legalCaseDetails[7] = ParamUtil.getString(resourceRequest, "caseDetails8");
		 currentStatus[7] = ParamUtil.getString(resourceRequest, "currentStatus8");
		
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		
		String reportDate=ParamUtil.getString(resourceRequest, "reportDate");
		//"npadevelopment_pdf_file"
		
		long fileEntryId = 0l;
		
		for(int i=0; i<rowCount; i++) {
			_log.info("inside for");
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			_log.info("description " + nameOfSecurity[i] + " remarks " + legalCaseDetails[i] +
					" currentStatus " + currentStatus[i]);
			
			if(nameOfSecurity[i]=="" || legalCaseDetails[i]=="" || currentStatus[i]=="") {
				continue;
			}else {
				jsonObject.put("nameOfSecurity", nameOfSecurity[i]);
				jsonObject.put("legalCaseDetails", legalCaseDetails[i]);
				jsonObject.put("currentStatus", currentStatus[i]);
				jsonArray.put(jsonObject);
			}

			_log.info("jsonArray " + jsonArray.toString());
			
		}
		try {
			File file=NPADevelopmentCreatePdfUtil.craeteNPAFormPDF(reportDate,companies, strDate, jsonArray);
			fileEntryId = NPADevlopmentUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "pdf",file);
			//fileEntryId = NPADevlopmentUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "npadevelopment_pdf_file", "pdf");
			_log.info("pfm_hycc_file -------------  pdf_file_fileEntryId  :::: "+fileEntryId);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		
		long createdBy = themeDisplay.getUserId();
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		try {
			
			MnNpaDevelopment npaDevelopment = mnNpaDevelopmentLocalService.addMnNpaDevelopment(reportUploadLogId, companies, 
					strDate, jsonArray.toString(), new Date(), themeDisplay.getUserId());
			_log.info("npaDevelopment saved"+ reportUploadLogId);
			
			//long fileEntryId = createPdf(npaDevelopment, themeDisplay, resourceRequest, themeDisplay.getUserId(), reportUploadLogId, statusByUserName, serviceContext);
			
			if(npaDevelopment.getWorkflowContext().trim().equals("")) {
				_log.info("Inside if block1");
				mnNpaDevelopmentLocalService.updateReportUploadLog(new Date(), createdBy, 
						fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
						createdBy, statusByUserName, 
						new Date(), serviceContext, "", true);
				_log.info("Inside if block");
			}else {
				_log.info("Inside else block 1");
				mnNpaDevelopmentLocalService.updateReportUploadLog(new Date(), createdBy, 
						fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
						createdBy, statusByUserName, 
						new Date(), serviceContext, "",false);
				_log.info("Inside else block");
			}
			
			
		} catch (Exception e) {
			 _log.error(e);
		}
		
		//_log.info("npaDevelopment " + npaDevelopment.toString());
		
	}
	
	/*public long createPdf(MnNpaDevelopment npaDevelopment, ThemeDisplay themeDisplay, ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext2) throws IOException, JSONException {
		
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
        Cell<PDPage> cell = headerRow.createCell(100, "NPA Development");
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
        Cell<PDPage> table2cell = table2row.createCell(100, "Report Due Date: " + dateFormat.format(new Date()));
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "PFM Name: " + npaDevelopment.getCompanies());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "The script wise details of NPA and the measures taken by PF for recovery of dues are placed as under as on " + npaDevelopment.getDate_1());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2.draw();
        
        float tableHeight2 = table2.getHeaderAndDataHeight();
        _log.info("tableHeight2 = "+tableHeight2);
        
        float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 20;
        BaseTable table3 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
        
        Row<PDPage> table3row = table3.createRow(20);
        Cell<PDPage> table3cell = table3row.createCell(25, "Sr.no");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25, "Name of Security");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25, "Legal Case details");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25, "Current Status");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        
        String tableDetails = npaDevelopment.getTabledetails();
        _log.info("tableDetails: " + tableDetails);
        JSONArray array = JSONFactoryUtil.createJSONArray(tableDetails);
        _log.info("JSON array: "+ array);
        
        int count = 1;
        
        for(int i=0; i < array.length(); i++) {
        	table3row = table3.createRow(20);
        	JSONObject json = array.getJSONObject(i); 
        	_log.info(json.getString("nameOfSecurity"));
        	_log.info(json.getString("legalCaseDetails"));  
        	_log.info(json.getString("currentStatus"));
        	
        	table3cell = table3row.createCell(25,String.valueOf(count));
	        table3cell.setAlign(HorizontalAlignment.CENTER);
        	table3cell = table3row.createCell(25,json.getString("nameOfSecurity"));
	        table3cell.setAlign(HorizontalAlignment.CENTER);
	        table3cell = table3row.createCell(25,json.getString("legalCaseDetails"));
	        table3cell.setAlign(HorizontalAlignment.CENTER);
	        table3cell = table3row.createCell(25,json.getString("currentStatus"));
	        table3cell.setAlign(HorizontalAlignment.CENTER);
	        count++;
        }
		
        table3.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File npaDevelopmentFile = File.createTempFile("NPA Development", "pdf");
		outputStream = new FileOutputStream(npaDevelopmentFile);
		document.save(outputStream);
        String filepath = npaDevelopmentFile.getPath(); 
		String filename = npaDevelopmentFile.getName();
		
//		document.save("E:\\pdf\\npaDevelopment.pdf");
        
		document.close();
        outputStream.close();
        long fileentryid = 0l;
        try {
        	fileentryid = pdfUpload(themeDisplay, filepath, filename, resourceRequest, createdBy, reportUploadLogId, statusByUserName, serviceContext2);
		} catch (Exception e) {
			_log.error("Exception in uploading Closing Balance Form PDF: " + e.getMessage());
		}
		return fileentryid;
	}*/
	
	/*@SuppressWarnings("deprecation")
	public long pdfUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext2) {
		Date date = new Date();
		File file = new File(filepath);
		String mimeType = "application/pdf";
		String title = "NPA Development" +date.getTime()+ ".pdf";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {
			Folder folder = getFolder(themeDisplay);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), title, mimeType, title, description, "",
					file, serviceContext);
			return entry.getFileEntryId();
			//FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			//downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			_log.info(e.getMessage());
			 _log.error(e);
		}
		return 0l;
	}*/
	
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
	MnNpaDevelopmentLocalService mnNpaDevelopmentLocalService;
	
}	
