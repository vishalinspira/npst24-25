package com.trusteebank.annex8.resource;

import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

import TrusteeBankMonthlyAnnexure8.constants.TrusteeBankMonthlyAnnexure8PortletKeys;
import TrusteeBankMonthlyAnnexure8.portlet.TrusteeBankMonthlyAnnexure8Portlet;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import compliance.service.model.ClosingBal;
import compliance.service.model.PerformanceRep;
import compliance.service.service.ClosingBalLocalService;
import compliance.service.service.ClosingBalLocalServiceUtil;
import nps.email.api.api.ClosingBalanceEmailApi;
import nps.email.api.api.ClosingBalanceSmsApi;

@Component(property = { 
		"javax.portlet.name=" + TrusteeBankMonthlyAnnexure8PortletKeys.TRUSTEEBANKMONTHLYANNEXURE8,
		"mvc.command.name=/closingbal/save" 
		}, 
service = MVCResourceCommand.class)

public class ClosingBalanceResource implements MVCResourceCommand {
	
	private static final String BLANK = "";
	
	private static Log _log = LogFactoryUtil.getLog(ClosingBalanceResource.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Inside serve resource");
		
		closingBalanceInfo(resourceRequest, resourceResponse);
		
		return false;
	}
	
	public void closingBalanceInfo(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
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
		
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date closingdate = ParamUtil.getDate(resourceRequest, "cloBal", dateFormat);
		String addressLine1 = ParamUtil.getString(resourceRequest, "address1", BLANK);
		String addressLine2 = ParamUtil.getString(resourceRequest, "address2", BLANK);
		String addressLine3 = ParamUtil.getString(resourceRequest, "address3", BLANK);
		Date confirmDate = ParamUtil.getDate(resourceRequest, "date2", dateFormat);
		String tableArrayString = ParamUtil.getString(resourceRequest, "tableArray", "[]");
		String yoursFaithfully = ParamUtil.getString(resourceRequest, "personName", BLANK);
		String designation = ParamUtil.getString(resourceRequest, "desig", BLANK);
		
		_log.info("closingdate: " + closingdate + " addressLine1: " + addressLine1 + " addressLine2: " + addressLine2);
		_log.info("addressLine3: " + addressLine3 + " confirmDate: " + confirmDate);
		_log.info("yoursFaithfully: " + yoursFaithfully + " designation: " + designation);
		
		_log.info("tableArrayString: " + tableArrayString);
		
		//ClosingBal closingBal = ClosingBalLocalServiceUtil.addClosingBal(closingdate, addressLine1, addressLine2, addressLine3, confirmDate, tableArrayString, yoursFaithfully, designation);
		
		//_log.info("closingBal: " + closingBal);
		
		try {
			pdfUpload(themeDisplay, resourceRequest, reportUploadLogId, statusByUserName);
			//createPdf(closingBal, themeDisplay, resourceRequest, userId, reportUploadLogId, statusByUserName, serviceContext);
		} catch (Exception e) {
			 _log.error(e);
		}
		
		emailApi.sendOTP("titisoh951@pantabi.com"); 
		
//		String mobileNo = "919945255124";
//		String name = "Deepak";
//		smsApi.sendFormSubmitSuccessfulSMS(mobileNo, name);
		
		
	}
	
	/*
	 * public void createPdf(ClosingBal closingBal, ThemeDisplay themeDisplay,
	 * ResourceRequest resourceRequest, long createdBy, long reportUploadLogId,
	 * String statusByUserName, ServiceContext serviceContext2) throws IOException,
	 * JSONException {
	 * 
	 * DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * PDFont fontPlain = PDType1Font.HELVETICA; PDFont fontBold =
	 * PDType1Font.HELVETICA_BOLD; PDFont fontItalic =
	 * PDType1Font.HELVETICA_OBLIQUE; PDFont fontMono = PDType1Font.COURIER;
	 * 
	 * // Create a document and add a page to it PDDocument document = new
	 * PDDocument(); PDPage page = new PDPage(PDRectangle.A4);
	 * 
	 * // PDRectangle.LETTER and others are also possible PDRectangle rect =
	 * page.getMediaBox();
	 * 
	 * // rect can be used to get the page width and height document.addPage(page);
	 * 
	 * // Start a new content stream which will "hold" the to be created content
	 * PDPageContentStream cos = new PDPageContentStream(document, page); //cos.b
	 * //Dummy Table float margin = 30;
	 * _log.info("rect.getHeight()"+rect.getHeight());
	 * 
	 * // starting y position is whole page height subtracted by top and bottom
	 * margin float yStartNewPage = rect.getUpperRightY() - (2 * margin);
	 * 
	 * // we want table across whole page width (subtracted by left and right margin
	 * ofcourse) float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
	 * 
	 * boolean drawContent = true; float yStart = yStartNewPage; float topMargin =
	 * 0; float bottomMargin = 10;
	 * 
	 * // y position is your coordinate of top left corner of the table float
	 * yPosition = rect.getUpperRightY()-margin;
	 * 
	 * BaseTable table = new BaseTable(yPosition, yStartNewPage,topMargin,
	 * bottomMargin, tableWidth, margin, document, page, false, drawContent); //new
	 * Baset
	 * 
	 * // the parameter is the row height Row<PDPage> headerRow =
	 * table.createRow(20);
	 * 
	 * // the first parameter is the cell width Cell<PDPage> cell =
	 * headerRow.createCell(100,
	 * "Annexure 8 - Closing Balance Confirmation For NPS Trust Collection Accounts"
	 * ); cell.setFontSize(20); cell.setAlign(HorizontalAlignment.CENTER);
	 * table.addHeaderRow(headerRow);
	 * 
	 * table.draw();
	 * 
	 * float tableHeight = table.getHeaderAndDataHeight();
	 * _log.info("tableHeight = "+tableHeight);
	 * 
	 * float table2YPosition = rect.getUpperRightY() - tableHeight - (2 * margin)
	 * -30;
	 * 
	 * BaseTable table2 = new BaseTable(table2YPosition, yStartNewPage,topMargin,
	 * bottomMargin, (tableWidth-100), (margin +50), document, page, false,
	 * drawContent);
	 * 
	 * Row<PDPage> table2row = table2.createRow(20); Cell<PDPage> table2cell =
	 * table2row.createCell(100, "Date: " +
	 * dateFormat.format(closingBal.getClosingdate()));
	 * table2cell.setAlign(HorizontalAlignment.LEFT); table2cell.setFontSize(10);
	 * 
	 * table2row = table2.createRow(20); table2cell = table2row.createCell(30,
	 * "The Chief Executive Officer");
	 * table2cell.setAlign(HorizontalAlignment.LEFT); table2cell.setFontSize(10);
	 * table2cell = table2row.createCell(70, "");
	 * 
	 * table2row = table2.createRow(20); table2cell = table2row.createCell(30,
	 * "National Pension System (NPS) Trust");
	 * table2cell.setAlign(HorizontalAlignment.LEFT); table2cell.setFontSize(10);
	 * table2cell = table2row.createCell(70, "");
	 * 
	 * table2row = table2.createRow(20); table2cell = table2row.createCell(40,
	 * closingBal.getAddressline1()); table2cell.setAlign(HorizontalAlignment.LEFT);
	 * table2cell.setFontSize(10); table2cell = table2row.createCell(60, "");
	 * 
	 * table2row = table2.createRow(20); table2cell = table2row.createCell(40,
	 * closingBal.getAddressline2()); table2cell.setAlign(HorizontalAlignment.LEFT);
	 * table2cell.setFontSize(10); table2cell = table2row.createCell(60, "");
	 * 
	 * table2row = table2.createRow(20); table2cell = table2row.createCell(40,
	 * closingBal.getAddressline3()); table2cell.setAlign(HorizontalAlignment.LEFT);
	 * table2cell.setFontSize(10); table2cell = table2row.createCell(60, "");
	 * 
	 * table2row = table2.createRow(20); table2cell = table2row.createCell(30,
	 * "Dear Sir,"); table2cell.setAlign(HorizontalAlignment.LEFT);
	 * table2cell.setFontSize(10); table2cell = table2row.createCell(70, "");
	 * 
	 * table2row = table2.createRow(20); table2cell = table2row.createCell(100,
	 * "Sub: Bank Balance Confirmation As On");
	 * table2cell.setAlign(HorizontalAlignment.CENTER); table2cell.setFontSize(14);
	 * table2cell.setFont(fontBold);
	 * 
	 * table2row = table2.createRow(20); table2cell = table2row.createCell(100,
	 * "We hereby confirm availability of bank balance as per detail mentioned below in NPS Trust A/C - CENTRAL GOVERNMENT account maintained with our Corporate Banking Branch as on "
	 * + dateFormat.format(closingBal.getConfirmdate()));
	 * table2cell.setAlign(HorizontalAlignment.LEFT); table2cell.setFontSize(10);
	 * 
	 * table2.draw();
	 * 
	 * float tableHeight2 = table2.getHeaderAndDataHeight();
	 * _log.info("tableHeight2 = "+tableHeight2);
	 * 
	 * float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) -
	 * 30- tableHeight2 - 20; BaseTable table3 = new BaseTable(table3YPosition,
	 * yStartNewPage,topMargin, bottomMargin, (tableWidth-100), (margin +50),
	 * document, page, true, drawContent);
	 * 
	 * Row<PDPage> table3row = table3.createRow(20); Cell<PDPage> table3cell =
	 * table3row.createCell(20, "Account Number");
	 * table3cell.setAlign(HorizontalAlignment.CENTER); table3cell =
	 * table3row.createCell(20, "Name Of Account Holder");
	 * table3cell.setAlign(HorizontalAlignment.CENTER); table3cell =
	 * table3row.createCell(20, "Total Balance (Amount In Rs.)");
	 * table3cell.setAlign(HorizontalAlignment.CENTER); table3cell =
	 * table3row.createCell(20, "Cleared Balance (Amount In Rs.)");
	 * table3cell.setAlign(HorizontalAlignment.CENTER); table3cell =
	 * table3row.createCell(20, "Uncleared Balance (Amount In Rs.)");
	 * table3cell.setAlign(HorizontalAlignment.CENTER);
	 * 
	 * String accDetails = closingBal.getAccountdetails(); _log.info("accDetails: "
	 * + accDetails); JSONArray array = JSONFactoryUtil.createJSONArray(accDetails);
	 * _log.info("JSON array: "+ array);
	 * 
	 * for(int i=0; i < array.length(); i++) { table3row = table3.createRow(20);
	 * JSONObject json = array.getJSONObject(i);
	 * _log.info(json.getString("accNoDetail"));
	 * _log.info(json.getString("accHolderDetail"));
	 * _log.info(json.getString("totBalDetail"));
	 * _log.info(json.getString("cleaBalDetail"));
	 * _log.info(json.getString("uncleaBalDetail"));
	 * 
	 * table3cell = table3row.createCell(20,json.getString("accNoDetail"));
	 * table3cell.setAlign(HorizontalAlignment.CENTER); table3cell =
	 * table3row.createCell(20,json.getString("accHolderDetail"));
	 * table3cell.setAlign(HorizontalAlignment.CENTER); table3cell =
	 * table3row.createCell(20,json.getString("totBalDetail"));
	 * table3cell.setAlign(HorizontalAlignment.CENTER); table3cell =
	 * table3row.createCell(20,json.getString("cleaBalDetail"));
	 * table3cell.setAlign(HorizontalAlignment.CENTER); table3cell =
	 * table3row.createCell(20,json.getString("uncleaBalDetail"));
	 * table3cell.setAlign(HorizontalAlignment.CENTER);
	 * 
	 * }
	 * 
	 * table3.draw();
	 * 
	 * //create another table
	 * 
	 * float tableHeight3 = table3.getHeaderAndDataHeight();
	 * _log.info("tableHeight3 = "+tableHeight3);
	 * 
	 * float table4YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) -
	 * 30- tableHeight2 - 20 - tableHeight3 - 20; BaseTable table4 = new
	 * BaseTable(table4YPosition, yStartNewPage,topMargin, bottomMargin,
	 * (tableWidth-100), (margin +50), document, page, false, drawContent);
	 * 
	 * Row<PDPage> table4row = table4.createRow(20); Cell<PDPage> table4cell =
	 * table4row.createCell(100, "Yours faithfully " +
	 * closingBal.getYoursfaithfully());
	 * table4cell.setAlign(HorizontalAlignment.LEFT); table4cell.setFontSize(10);
	 * 
	 * table4row = table4.createRow(20); table4cell = table4row.createCell(100,
	 * "Designation " + closingBal.getDesignation());
	 * table4cell.setAlign(HorizontalAlignment.LEFT); table4cell.setFontSize(10);
	 * 
	 * table4.draw();
	 * 
	 * cos.close();
	 * 
	 * OutputStream outputStream = null; File closingBalanceFile =
	 * File.createTempFile("ClosingBalance", "pdf"); outputStream = new
	 * FileOutputStream(closingBalanceFile); document.save(outputStream); String
	 * filepath = closingBalanceFile.getPath(); String filename =
	 * closingBalanceFile.getName();
	 * 
	 * document.close(); outputStream.close();
	 * 
	 * //
	 * document.save("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\blank.pdf");
	 * //document.save("C:\\Users\\deepak\\Documents\\pdf\\closingbalance.pdf"); //
	 * document.save("E:\\pdf\\closingbalance.pdf"); //document.close();
	 * 
	 * try { pdfUpload(themeDisplay, filepath, filename, resourceRequest, createdBy,
	 * reportUploadLogId, statusByUserName, serviceContext2); } catch (Exception e)
	 * { _log.error("Exception in uploading Closing Balance Form PDF: " +
	 * e.getMessage()); }
	 * 
	 * }
	 */
	
	@SuppressWarnings("deprecation")
	public String pdfUpload(ThemeDisplay themeDisplay,ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName) {
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		ServiceContext serviceContext2 =ServiceContextThreadLocal.getServiceContext();
		Date date=new Date();
		
		File file = uploadPortletRequest.getFile("anx_8");
		String fileName =date.getTime()+ file.getName();
		String mimeType = uploadPortletRequest.getContentType("anx_8");
		long createdBy=serviceContext2.getUserId();
		String title = fileName;

		String description = file.getName();

		long repositoryId = themeDisplay.getScopeGroupId();
		
		
		//File file = new File(filepath);
		//String mimeType = "application/pdf";
		//String title = filename + ".pdf";
		//String description = "This file is added via programatically";
		//long repositoryId = themeDisplay.getScopeGroupId();
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	/*@Reference
	ClosingBalLocalService closingBalLocalService;*/
	
	@Reference
	ClosingBalanceEmailApi emailApi;
//	
//	@Reference
//	ClosingBalanceSmsApi smsApi;
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext) {
		Date createDate = new Date();
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, "");//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
}
