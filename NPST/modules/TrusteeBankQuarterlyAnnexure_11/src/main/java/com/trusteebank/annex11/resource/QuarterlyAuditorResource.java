package com.trusteebank.annex11.resource;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import TrusteeBankQuarterlyAnnexure_11.constants.TrusteeBankQuarterlyAnnexure_11PortletKeys;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import compliance.service.model.QuarterlyAuditor;
import compliance.service.service.QuarterlyAuditorLocalService;
import nps.email.api.api.QuarterlyAuditorEmailApi;

@Component(property = { 
		"javax.portlet.name=" + TrusteeBankQuarterlyAnnexure_11PortletKeys.TRUSTEEBANKQUARTERLYANNEXURE_11,
		"mvc.command.name=" + TrusteeBankQuarterlyAnnexure_11PortletKeys.quarterlyAuditor, 
		}, 
service = MVCResourceCommand.class)

public class QuarterlyAuditorResource implements MVCResourceCommand {
	
	
	private static Log _log = LogFactoryUtil.getLog(QuarterlyAuditorResource.class);
	
	private static final String BLANK = "";
	

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		
		_log.info("Serve Resource method");
		
		quartelyAuditorInfo(resourceRequest, resourceResponse);
		
		return false;
	}
	
	public void quartelyAuditorInfo(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
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
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date performantDate = ParamUtil.getDate(resourceRequest, "performantDate", dateFormat);
		String bankName = ParamUtil.getString(resourceRequest, "name", BLANK);
		String numberOfCases = ParamUtil.getString(resourceRequest, "number", BLANK);
		String amount = ParamUtil.getString(resourceRequest, "amount", BLANK);
		Date listOfAmountDate = ParamUtil.getDate(resourceRequest, "amountListDate", dateFormat);
		String companyName = ParamUtil.getString(resourceRequest, "companyName", BLANK);
		String designation = ParamUtil.getString(resourceRequest, "designation", BLANK);
		String regNo = ParamUtil.getString(resourceRequest, "regNo", BLANK);
		String partnerCompanyName = ParamUtil.getString(resourceRequest, "partnerCompanyName", BLANK);
		String partnerMemNo = ParamUtil.getString(resourceRequest, "partnerMemNo", BLANK);
		Date submitDate = ParamUtil.getDate(resourceRequest, "signDate", dateFormat);
		String place = ParamUtil.getString(resourceRequest, "place", BLANK);
		
		
		_log.info("performantDate: " + performantDate + " bankname: " + bankName + " numberofcases: " + numberOfCases);
		_log.info("amount: " + amount + " listOfAmountDate: " + listOfAmountDate + " companyname: " + companyName);
		_log.info("designation: " + designation + " regno: " + regNo + " partnercompanyname: " + partnerCompanyName);
		_log.info("partnermemno: " + partnerMemNo + " submitdate: " + submitDate + " place: " + place);
		
		QuarterlyAuditor auditor = quarterlyAuditorLocalService.addQuarterlyAuditor(performantDate, bankName, numberOfCases, amount, listOfAmountDate, companyName, designation, regNo, partnerCompanyName, partnerMemNo, submitDate, place, 
				userId, new Date(), userId, new Date(), WorkflowConstants.STATUS_PENDING, userName);
		
		_log.info("auditor: " + auditor);
		
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		if(Validator.isNotNull(auditor)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(themeDisplay.getUser().getUserId(),
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    QuarterlyAuditor .class.getName(),
	                    auditor.getId(),
	                    auditor.getUuid(),
	                    0,
	                    null,
	                    null,
	                    true,
	                    false,
	                    new Date(),
	                    null,
	                    new Date(),
	                    null,
	                    ContentTypes.TEXT_HTML,
	                    "Quarterly Auditor Asset",
	                    "Quarterly Auditor Asset with Id: " +  auditor.getId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<QuarterlyAuditor> indexer = IndexerRegistryUtil.nullSafeGetIndexer(QuarterlyAuditor.class);
				indexer.reindex(auditor);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(themeDisplay.getCompanyId(), themeDisplay.getScopeGroup().getGroupId(), themeDisplay.getUserId(), 
						QuarterlyAuditor.class.getName(), auditor.getId(), auditor, serviceContext);

			} catch (Exception e) {
				 _log.error(e);
				_log.error("Exception in Quarterly Auditor Asset Creation :"+e.getMessage());
			}
		}
		
		try {
			createPdf(auditor, themeDisplay, resourceRequest);
		} catch (Exception e) {
			 _log.error(e);
		}
		
		emailApi.sendOTP("titisoh951@pantabi.com"); 
		
		//smsApi.sendFormSubmitSuccessfulSMS("919945255124", "Deepak");
		
		
	}
	
	public void createPdf(QuarterlyAuditor auditor, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;
        
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        
//        PDPage page = null;
//        PDRectangle rect = null;
//        for(int i=0; i<2; i++) {
//        	page = new PDPage(PDRectangle.A4);
//        	rect = page.getMediaBox();
//        	document.addPage(page);
//        }

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
        Cell<PDPage> cell = headerRow.createCell(100, "Annexure 11 - Scope Of Audit And Format Of Quarterly Auditor's Certificate");
        cell.setAlign(HorizontalAlignment.CENTER);
        cell.setFontSize(20);
        cell.setFont(fontBold);
        table.addHeaderRow(headerRow);
        
        table.draw();

        float tableHeight = table.getHeaderAndDataHeight();
        _log.info("tableHeight = "+tableHeight);
        
        float table2YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 10;
        
        BaseTable table2 = new BaseTable(table2YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
        
        Row<PDPage> table2row = table2.createRow(20);
        Cell<PDPage> table2cell = table2row.createCell(100, "TB shall be required to get NPS accounts audited in terms of the regulation/ existing agreement and prescribed guidelines. The statutory Auditor's Report shall be submitted to the NPS Trust on an annual basis. This shall be presented by NPS Trust to the Authority with qualifications and recommendations on the deviations found if any.");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "Format of Auditor's report");
        table2cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFontSize(14);
        table2cell.setFont(fontBold);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "Date: " + dateFormat.format(auditor.getPerformantdate()));
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(30, "To");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
        
        String address = "The CEO-NPST " +  
        		"1st Floor, ICADR Building " + 
        		"Plot No. 6, Vasant Kunj Institutional Area " +  
        		"Phase-II, Vasant Kunj, New Delhi- 110070";
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(30, address);
		table2cell.setAlign(HorizontalAlignment.LEFT);
		table2cell.setFontSize(10);
		table2cell = table2row.createCell(70, "");
		
		table2row = table2.createRow(20);
        table2cell = table2row.createCell(30, "1. Certified that:");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
		
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(1)  We have examined the records relating to the NPS accounts maintained by"+
        auditor.getBankname()+"(name of the Trustee Bank)");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(2)  We have obtained all information and explanations which, to the best of our knowledge and belief, were necessary for the purpose of this audit,");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(3)  The NPS Trustee Bank has maintained all books and records of the Trust Account and has complied with the disclosure requirements as specified by PFRDA/NPS Trust from time to time.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(4)  NPS Trustee Bank has maintained books and records about the fund flow and information flow between NPS Trustee Bank, CRA(s), subscribers, Pension Fund Managers, etc to ensure compliance with the guidelines and has submitted regular reports at such intervals and in such manner as was required or called for by PFRDA/NPS Trust.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(5)  No fund pertaining to NPS is parked in suspense account/ transitory account.");
        table2cell.setFontSize(10);
        
		table2row = table2.createRow(20);
		table2cell = table2row.createCell(10, "");
		table2cell.setAlign(HorizontalAlignment.LEFT);
		table2cell.setFontSize(10);
		table2cell = table2row.createCell(90,
				"(6)  Trustee Bank is adhering to the Turn Around Time (TAT) as per the agreement/ prescribed guidelines on remittance to be returned and transfer of funds to various stakeholders for receipt of NPS contributions and payment of funds to ASPs and subscribers at the time of exit/ withdrawal. (Exceptions found, if any, and auditor's comments on that to be enclosed separately)");
		table2cell.setFontSize(10);
        
		table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(1)  In " + auditor.getNumberofcases()  + " no. of cases/instances the money was transferred on > T+1 basis.");
        table2cell.setFontSize(10);
		
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(7)  During the quarter under review the Trustee Bank has enjoyed an average daily float of Rs. " + auditor.getAmount());
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(8)  NPS funds are not being used for any other purpose.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(9)  Reconciliation is undertaken on a daily basis.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(10) Timely reports are being submitted to various stakeholders as per the prescribed guidelines.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(11)  Registers/ ledgers/ records are being properly maintained");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(12)  User Ids/ passwords are not being shared with any one by the officials.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(13)  The system of timely back-ups, offsite backups, contingency and disaster data recovery plan are being maintained properly.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(14)  Exception reports are being properly maintained and monitored.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(15)  Observations/ qualifications in previous audit reports of concurrent audit, internal audit, RBI inspection, etc. were studied and their compliance on time has been noted.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(16)  Examined the corrective steps taken by the Bank to minimize errors.");
        table2cell.setFontSize(10);
        
//        PDPage page2 = new PDPage(PDRectangle.A4);
//        document.addPage(page2);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(30, "2. It is further certified that:-");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
		
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(1)  Funds have been maintained in accordance with the guidelines issued by the Authority/ NPS Trust;");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(2)  No window dressing in the accounts has taken place.");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(3)  The system, procedures and safeguards followed by the NPS Trustee Bank are adequate;");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(4)  The provisions of registration certificate by Authority, SLA agreement signed with the Trust are being complied with by the Trustee Bank;");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(5)  Directions issued by the Authority/ NPS Trust from time to time or any other statutory requirements have been followed;");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(6)  Affairs of the Trustee Bank are being conducted in a manner which is in the interest of the subscribers;");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(10, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(90, "(7)  List of amount lying in various accounts of NPS maintained with Trustee Bank as on " + dateFormat.format(auditor.getListofamountdate()) + " is enclosed");
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, auditor.getCompanyname());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100,auditor.getDesignation());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100,auditor.getRegno());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100,auditor.getPartnercompanyname());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100,auditor.getPartnermemno());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100,dateFormat.format(auditor.getSubmitdate()));
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100,auditor.getPlace());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File quarterlyAuditorFile = File.createTempFile("Quarterly Auditor", "pdf");
		outputStream = new FileOutputStream(quarterlyAuditorFile);
		document.save(outputStream);
        String filepath = quarterlyAuditorFile.getPath(); 
		String filename = quarterlyAuditorFile.getName();
        
		document.close();
        outputStream.close();
        
        //document.save("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\blank.pdf");
        //document.save("C:\\Users\\deepak\\Documents\\pdf\\quarterlyauditor.pdf");
        //document.save("E:\\pdf\\quartelyauditor.pdf");
        //document.close();
        
        try {
            pdfUpload(themeDisplay, filepath, filename, resourceRequest);
		} catch (Exception e) {
			_log.error("Exception in uploading Quarterly Auditor Form PDF: " + e.getMessage());
		}
        
	}
	
	@SuppressWarnings("deprecation")
	public String pdfUpload(ThemeDisplay themeDisplay, String filepath, String filename,
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Quarterly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	@Reference
	QuarterlyAuditorLocalService quarterlyAuditorLocalService;
	
	@Reference
	QuarterlyAuditorEmailApi emailApi;
	
//	@Reference
//	QuarterlyAuditorSmsApi smsApi;
	
	

}
