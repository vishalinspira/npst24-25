package com.cra.enps.mvc;

import com.cra.enps.constants.CRAEnpsPortletKeys;
import com.daily.average.service.model.CraEnps;
import com.daily.average.service.service.CraEnpsLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogCRALocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;

@Component(property = { "javax.portlet.name=" + CRAEnpsPortletKeys.CRAForm,
"mvc.command.name=/craenps/save" }, service = MVCResourceCommand.class)
public class SaveCRAEnpsResourceCommand  implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = saveCRAEnps(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}

	private JSONObject saveCRAEnps(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		int [] srno = ParamUtil.getIntegerValues(resourceRequest, "srno[]");
		long [] pran = ParamUtil.getLongValues(resourceRequest, "pran[]");
		String [] subName = ParamUtil.getStringValues(resourceRequest, "subName[]");
		String [] claimentName = ParamUtil.getStringValues(resourceRequest, "claimentName[]");
		Date [] authClaimDate = ParamUtil.getDateValues(resourceRequest, "authClaimDate[]", dateFormat);
		Date [] claimDispatchDate = ParamUtil.getDateValues(resourceRequest, "claimDispatchDate[]", dateFormat);
		String [] trackId = ParamUtil.getStringValues(resourceRequest, "trackId[]");
		long [] id = ParamUtil.getLongValues(resourceRequest, "id[]");
		Date [] receiptAuthFormDate = ParamUtil.getDateValues(resourceRequest, "receiptAuthFormDate[]", dateFormat);
		Date [] claimProcessDate = ParamUtil.getDateValues(resourceRequest, "claimProcessDate[]", dateFormat);
		int [] tat = ParamUtil.getIntegerValues(resourceRequest, "tat[]");
		List<CraEnps> craEnps = new ArrayList<CraEnps>();
		
		for (int i = 0; i < trackId.length; i++) {
			CraEnps craEnps2 = CraEnpsLocalServiceUtil.fetchCraEnps(id[i]);
			/*craEnps2.setSrno(i+1);
			craEnps2.setPran(pran[i]);
			craEnps2.setSubName(subName[i]);
			craEnps2.setClaimentName(claimentName[i]);
			craEnps2.setAuthClaimDate(authClaimDate[i]);
			craEnps2.setClaimDispatchDate(claimDispatchDate[i]);
			craEnps2.setTrackId(trackId[i]);
			craEnps2.setReportUploadLogId(reportUploadLogId);*/
			craEnps2.setReceiptAuthFormDate(receiptAuthFormDate[i]);
			craEnps2.setClaimProcessDate(claimProcessDate[i]);
			craEnps2.setTat(tat[i]);
			_log.info("craEnps2"+craEnps2);
			craEnps.add(craEnps2);
			CraEnpsLocalServiceUtil.updateCraEnps(craEnps2);
		}
		try {
			long fileEntryId = pdfTable(craEnps, themeDisplay, resourceRequest);
			updateReportLog(userId, fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, "");
		} catch (IOException e) {
			 _log.error(e);
		}
		//CraEnpsLocalServiceUtil.addCraEnpsList(craEnps);
		/*
		 * ReportUploadLog reportUploadLog =
		 * ReportUploadLogLocalServiceUtil.fetchReportUploadLog(reportUploadLogId);
		 * reportUploadLog.setUploaded_i(1);
		 * ReportUploadLogLocalServiceUtil.updateReportUploadLog(reportUploadLog);
		 */
		return resultJsonObject;
	}
    
	public long pdfTable(List<CraEnps> craEnps, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
	      PDFont fontPlain = PDType1Font.HELVETICA;
	        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
	        PDFont fontMono = PDType1Font.COURIER;

	       
	        PDDocument document = new PDDocument();
	        PDPage page = new PDPage(PDRectangle.A4);
	        PDRectangle rect = page.getMediaBox();
	        document.addPage(page);
	        PDPageContentStream cos = new PDPageContentStream(document, page);
	        float margin = 10;
	        _log.info("rect.getHeight()"+rect.getHeight());
	        float yStartNewPage = rect.getUpperRightY() - (2 * margin); 
	        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

	        boolean drawContent = true;
	        float yStart = yStartNewPage;
	        float topMargin = 0;
	        float bottomMargin = 10;
	        float yPosition = rect.getUpperRightY()-margin;

	        BaseTable table;
			table = new BaseTable(yPosition, yStartNewPage,topMargin,
				    bottomMargin, tableWidth, margin, document, page, false, drawContent);
			
	        Row<PDPage> headerRow = table.createRow(20);
	        Cell<PDPage> cell = headerRow.createCell(100, "Cra Enps");
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
	        Cell<PDPage> table2cell = table2row.createCell(10,"Sr no.");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	        table2.addHeaderRow(table2row);
	        
	        //table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "PRAN");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	        
	        //table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "Name of Subscriber");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	        
	        //table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "Name of claimant");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	        
	        
		
			//table2row = table2.createRow(20);
			table2cell = table2row.createCell(10, "Date of Authorisation of claim ");
			table2cell.setAlign(HorizontalAlignment.CENTER);
			table2cell.setFontSize(10);
			
			
			//table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "Date of dispatch of claim form");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	       
	        

			//table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "Tracking ID");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	        
	        
	        
	        //table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "Date of receipt of authorised form");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	       
	        
	        //table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "Date of claim processing");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	        
	        
	        //table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "TAT");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        
	        for (CraEnps craEnps2 : craEnps) {
	        	table2row = table2.createRow(20);
	        	table2cell = table2row.createCell(10,  String.valueOf(craEnps2.getSrno()) );
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
		        table2.addHeaderRow(table2row);
		        
		        //table2row = table2.createRow(20);
		        table2cell = table2row.createCell(10, String.valueOf(craEnps2.getPran()));
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
		        
		        //table2row = table2.createRow(20);
		        table2cell = table2row.createCell(10,craEnps2.getSubName() );
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
		        
		        //table2row = table2.createRow(20);
		        table2cell = table2row.createCell(10, craEnps2.getClaimentName());
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
		        
		        
			
				//table2row = table2.createRow(20);
				table2cell = table2row.createCell(10, dateFormat.format(craEnps2.getAuthClaimDate()));
				table2cell.setAlign(HorizontalAlignment.CENTER);
				table2cell.setFontSize(10);
				
				
				//table2row = table2.createRow(20);
		        table2cell = table2row.createCell(10, dateFormat.format(craEnps2.getClaimDispatchDate()));
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
		       
		        

				//table2row = table2.createRow(20);
		        table2cell = table2row.createCell(10, String.valueOf(craEnps2.getTrackId()));
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
		        
		        
		        
		        //table2row = table2.createRow(20);
		        table2cell = table2row.createCell(10, dateFormat.format(craEnps2.getReceiptAuthFormDate()));
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
		       
		        
		        //table2row = table2.createRow(20);
		        table2cell = table2row.createCell(10, dateFormat.format(craEnps2.getClaimProcessDate()));
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
		        
		        
		        //table2row = table2.createRow(20);
		        table2cell = table2row.createCell(10,String.valueOf(craEnps2.getTat()));
		        table2cell.setAlign(HorizontalAlignment.CENTER);
		        table2cell.setFontSize(10);
			}
	        
	        
	        
	        
	       
	        
	        table2.draw();
	        
	        cos.close();
	        OutputStream outputStream = null;
			File accountStatementtempfile = File.createTempFile("CraEnps"+new Date().getTime(), "pdf");
			
			outputStream = new FileOutputStream(accountStatementtempfile);
	        document.save(outputStream);
	        String filepath = accountStatementtempfile.getPath();
			String filename = accountStatementtempfile.getName();
	        
	        document.close();
	        outputStream.close();
	        return fileUpload(themeDisplay, filepath, filename, resourceRequest);
	}
	
	@SuppressWarnings("deprecation")
	public long fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest) {

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
			return entry.getFileEntryId();
			//FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			//downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Annually");
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
	
	Log _log = LogFactoryUtil.getLog(SaveCRAEnpsResourceCommand.class);
}
