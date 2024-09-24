package com.auditor.certificate.resource;

import com.auditor.certificate.constants.auditorCertificatePortletKeys;
import com.daily.average.service.model.AuditorCertificate;
import com.daily.average.service.service.AuditorCertificateLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
														 
										   
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
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
import java.util.Date;
import java.io.OutputStream;
import java.io.PrintWriter;

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
import nps.email.api.api.AuditorCertificateEmailapi;
import nps.email.api.api.AuditorCertificateSmsApi;
import nps.email.api.api.NpsEmailApi;
import nps.email.api.api.NpsSmsApi;
import nps.email.api.api.QuaterEndedEmailApi;
import nps.email.api.api.QuaterEndedSmsApi;

@Component(property = { "javax.portlet.name=" + auditorCertificatePortletKeys.AUDITORCERTIFICATE,
		"mvc.command.name=/auditorcertificate/save" }, service = MVCResourceCommand.class)
public class SaveAuditorCertificate implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = auditorCertificateUpload(resourceRequest, resourceResponse, themeDisplay);
		try { 
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}

	public JSONObject auditorCertificateUpload(ResourceRequest resourceRequest, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) {
		long userId = 0L;
		String userName = "NA";
		try {
			userId = themeDisplay.getUserId();
			userName = themeDisplay.getUser().getFullName();
		} catch(Exception e) {
			 _log.error(e);
		}
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		String name = ParamUtil.getString(resourceRequest, "name");
		String amount = ParamUtil.getString(resourceRequest, "amount");
		String compname = ParamUtil.getString(resourceRequest, "compname");
		String characc = ParamUtil.getString(resourceRequest, "characc");
		String regno = ParamUtil.getString(resourceRequest, "regno");
		String compartner = ParamUtil.getString(resourceRequest, "compartner");
		String memberno = ParamUtil.getString(resourceRequest, "memberno");
		String date = ParamUtil.getString(resourceRequest, "date");
		String place = ParamUtil.getString(resourceRequest, "place");
		_log.info(name + "in auditorCertificateUpload");
		AuditorCertificate auditorCertificate = auditorCertificateLocalService.addAuditorCertificate(name, amount, compname, characc, regno, compartner,
				memberno, date, place, userId, new Date(), userId, new Date(), WorkflowConstants.STATUS_PENDING, userName);
		
		_log.info("auditorCertificate : " + auditorCertificate);
		
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		if(Validator.isNotNull(auditorCertificate)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(themeDisplay.getUser().getUserId(),
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    AuditorCertificate .class.getName(),
	                    auditorCertificate.getAuditid(),
	                    auditorCertificate.getUuid(),
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
	                    "Auditor Certificate Asset",
	                    "Auditor Certificate Asset with Id: " +  auditorCertificate.getAuditid(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<AuditorCertificate> indexer = IndexerRegistryUtil.nullSafeGetIndexer(AuditorCertificate.class);
				indexer.reindex(auditorCertificate);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(themeDisplay.getCompanyId(), themeDisplay.getScopeGroup().getGroupId(), themeDisplay.getUserId(), 
						AuditorCertificate.class.getName(), auditorCertificate.getAuditid(), auditorCertificate, serviceContext);

			} catch (Exception e) {
				 _log.error(e);
				_log.error("Exception in Auditor Certificate Asset Creation : "+e.getMessage());
			}
		}

		auditorCertificateEmailapi.sendOTP("priyanka@yopmail.com");
		auditorCertificateSmsApi.sendFormSubmitSuccessfulSMS("919851587480", "Abhi");
		
		
		try {
			resultJsonObject.put("pdfURL", pdfTable(auditorCertificate ,themeDisplay,resourceRequest));
		} catch (IOException e) {
			 _log.error(e);
		}
		
		try {
			resultJsonObject.put("pdfURL", pdfTableii(auditorCertificate,themeDisplay,resourceRequest));
		} catch (IOException e) {
			
			 _log.error(e);
		}
		resultJsonObject.put("status", true);
		return resultJsonObject;
	}

	@Reference
	AuditorCertificateLocalService auditorCertificateLocalService;

	@Reference
	AuditorCertificateEmailapi auditorCertificateEmailapi;
	@Reference
	AuditorCertificateSmsApi auditorCertificateSmsApi;
	
	public String pdfTable(AuditorCertificate auditorCertificate, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
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
	        Cell<PDPage> cell = headerRow.createCell(100, "Annexure-12");
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
	        Cell<PDPage> table2cell = table2row.createCell(100,"Scope of audit and format of Auditor's certificate to be submitted annually");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(20);
	        table2.addHeaderRow(table2row);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100, "Trustee Bank shall be required to get NPS accounts audited in terms of the regulation/ existing agreement and prescribed guidelines. The statutory Auditor's Report shall be submitted to the NPS Trust on an annual basis. This shall be presented by NPS Trust to the Authority with qualifications and recommendations on the deviations found if any.");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100, "Format of Auditor's report");
	        table2cell.setAlign(HorizontalAlignment.CENTER);
	        table2cell.setFontSize(20);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(30, "To");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(70, "");
	        
		
			table2row = table2.createRow(20);
			table2cell = table2row.createCell(30, "The CEO-NPST" 
					+ "1st Floor, ICADR Building"
					+ "Plot No. 6, Vasant Kunj Institutional Area" 
					+ "Phase-II, Vasant Kunj, New Delhi- 110070");
			table2cell.setAlign(HorizontalAlignment.CENTER);
			table2cell.setFontSize(10);
			table2cell = table2row.createCell(70, "");
			
			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(30, "1.  Certified that:");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(70, "");
	        

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(90, "(1)  We have examined the records relating to the NPS accounts maintained by"+
	        auditorCertificate.getName()+"(name of the Trustee Bank)");
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
	        table2cell = table2row.createCell(90, "(3)  Reports and audit- The Trustee Bank has filed the following periodic reports with the National Pension System Trust - ");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80, "(a)   extracts of internal audit report from the independent auditors with respect to the National Pension System Trust Accounts, compliance certificates and subscriber complaints reports at regular intervals;");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80, "(b)  quarterly concurrent audit report;");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80, "(c)  external audit report of all the National Pension System accounts maintained with the Trustee Bank is being submitted ");
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(90, "(4)  At the end of each financial year, the Trustee   Bank has furnished a statement of the income and expenditure account and a balance sheet reflecting the position of the Trustee Bank as on the closing date of the financial year to the National Pension System Trust");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(90, "(5)  No fund pertaining to NPS is parked in suspense account/ transitory account. ");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(90, "(6)  Trustee Bank is adhering to the Turn around Time (TAT) as per the agreement/ prescribed guidelines on remittance to be returned and transfer of funds to various stakeholders for receipt of NPS contributions and payment of funds to ASPs and subscribers at the time of exit/ withdrawal. (Exceptions found, if any, and auditor's comments on that to be enclosed separately) ");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(90, "(7)  No fund pertaining to NPS is parked in suspense account/ transitory account. ");
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80, "(1)  Observations/ qualifications in previous audit reports of concurrent audit, internal audit, RBI inspection, etc. were verified and their compliance has been noted. ");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80, "(2)   Examined the corrective steps taken by the Bank to minimize errors.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(90, "2.  It is further certified that: ");
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80, "(1)  Funds have been maintained in accordance with the guidelines issued by the Authority/ NPS Trust; ");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80, "(2)  The system, procedures and safeguards followed by the NPS Trustee Bank are adequate; ");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10, "");
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80, "(3)  The provisions of registration certificate by Authority, SLA agreement signed with the Trust are being complied with by the Trustee Bank; ");
	        table2cell.setFontSize(10);
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        
	        table2.draw();
	        
	        cos.close();
	        OutputStream outputStream = null;
			File accountStatementtempfile = File.createTempFile("auditorcert1"+auditorCertificate.getAuditid(), "pdf");
			
			outputStream = new FileOutputStream(accountStatementtempfile);
	        document.save(outputStream);
	        String filepath = accountStatementtempfile.getPath();
			String filename = accountStatementtempfile.getName();
	        
	        document.close();
	        outputStream.close();
	        return fileUpload(themeDisplay, filepath, filename, resourceRequest);
	}
	        
	public String pdfTableii(AuditorCertificate auditorCertificate, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
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

		        BaseTable table4;
				table4 = new BaseTable(yPosition, yStartNewPage,topMargin,
					    bottomMargin, tableWidth, margin, document, page, false, drawContent);
				Row<PDPage> headerRow = table4.createRow(20);
		        Cell<PDPage> cell = headerRow.createCell(100, "Rest of Annexure-12");
		        cell.setFontSize(20);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        table4.addHeaderRow(headerRow);
		        table4.draw();
				
		        float tableHeight = table4.getHeaderAndDataHeight();
		        _log.info("tableHeight = "+tableHeight);
		        
		        float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) -30;
		        BaseTable table3 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
		                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
		        
		        Row<PDPage> table3row = table3.createRow(20);
		        Cell<PDPage> table3cell = table3row.createCell(20, "");
		        table3cell = table3row.createCell(10, "");
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        table3cell.setFontSize(10);
		        table3cell = table3row.createCell(10, "");
		        table3cell.setFontSize(10);
		        table3cell = table3row.createCell(80, "(4)  List of amount lying in various accounts of NPS maintained with Trustee Bank as on "+auditorCertificate.getAmount()+" is enclosed");
		        table3cell.setFontSize(10);
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        
		        table3row = table3.createRow(20);
		        table3cell = table3row.createCell(100,auditorCertificate.getCompname());
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        table3cell.setFontSize(10);
		        
		        table3row = table3.createRow(20);
		        table3cell = table3row.createCell(100,auditorCertificate.getCharacc());
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        table3cell.setFontSize(10);
		        
		        table3row = table3.createRow(20);
		        table3cell = table3row.createCell(100,auditorCertificate.getRegno());
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        table3cell.setFontSize(10);
		        
		        table3row = table3.createRow(20);
		        table3cell = table3row.createCell(100,auditorCertificate.getCompartner());
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        table3cell.setFontSize(10);
		        
		        table3row = table3.createRow(20);
		        table3cell = table3row.createCell(100,auditorCertificate.getMemberno());
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        table3cell.setFontSize(10);
		        
		        table3row = table3.createRow(20);
		        table3cell = table3row.createCell(100,auditorCertificate.getDate());
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        table3cell.setFontSize(10);
		        
		        table3row = table3.createRow(20);
		        table3cell = table3row.createCell(100,auditorCertificate.getPlace());
		        table3cell.setAlign(HorizontalAlignment.LEFT);
		        table3cell.setFontSize(10);
		   
		        table3.draw();
	        
	        cos.close();
	        OutputStream outputStream = null;
			File auditorcerttempfile = File.createTempFile("auditorcert2"+auditorCertificate.getAuditid(), "pdf");
			
			outputStream = new FileOutputStream(auditorcerttempfile);
	        document.save(outputStream);
	        String filepath = auditorcerttempfile.getPath();
			String filename = auditorcerttempfile.getName();
	        
	        document.close();
	        outputStream.close();
	        return fileUpload(themeDisplay, filepath, filename, resourceRequest);
	       
		 
	}
	@SuppressWarnings("deprecation")
	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Annually");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	private static Log _log = LogFactoryUtil.getLog(SaveAuditorCertificate.class);
}