package com.trusteebank.annex13.resource;

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

import ComplianceForm.constants.ComplianceFormPortletKeys;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import compliance.service.model.Compliance_Cert;
import compliance.service.service.Compliance_CertLocalService;
import nps.email.api.api.ComplianceEmailApi;

@Component(property = { 
		"javax.portlet.name=" + ComplianceFormPortletKeys.COMPLIANCEFORM,
		"mvc.command.name=" + ComplianceFormPortletKeys.complianceCertificate, 
		}, 
service = MVCResourceCommand.class)


public class ComplianceCertificateResource implements MVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(ComplianceCertificateResource.class);
	
	private static final String BLANK = "";
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		//emailApi.sendOTP("tewara2550@royins.com");
		complianceInfo(resourceRequest, resourceResponse);
		
		return false;
	}
	
	public void complianceInfo(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside compliance Info");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long userId = 0L;
		String userName = "NA";
		try {
			userId = themeDisplay.getUserId();
			userName = themeDisplay.getUser().getFullName();
		} catch(Exception e) {
			 _log.error(e);
		}
		
		//(1) for initial details 
		String number = ParamUtil.getString(resourceRequest, "number", BLANK);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date complianceDate = ParamUtil.getDate(resourceRequest, "complianceDate", dateFormat);
		Date compFYEndingDate = ParamUtil.getDate(resourceRequest, "compFYEndingDate", dateFormat);
		Date tbOfficialsFYEndingDate = ParamUtil.getDate(resourceRequest, "tbOfficialsFYEndingDate", dateFormat);
		
		_log.info("number: " + number + " complianceDate: " + complianceDate);
		_log.info("compFYEndingDate: " + compFYEndingDate + " tbOfficialsFYEndingDate " + tbOfficialsFYEndingDate);
		_log.info("===============================================================================================");
		
		//(1) for tb details 
		String tb_PFRDA_Act_complied = ParamUtil.getString(resourceRequest, "tr_1", BLANK);
		String tb_PFRDA_Act_remarks = ParamUtil.getString(resourceRequest, "trem_1", BLANK);
		String tb_review_complied = ParamUtil.getString(resourceRequest, "tr_2", BLANK);
		String tb_review_remarks = ParamUtil.getString(resourceRequest, "trem_2", BLANK);
		String tb_investment_complied = ParamUtil.getString(resourceRequest, "tr_3", BLANK);
		String tb_investment_remarks = ParamUtil.getString(resourceRequest, "trem_3", BLANK);
		String tb_communication_complied = ParamUtil.getString(resourceRequest, "tr_4", BLANK);
		String tb_communication_remarks = ParamUtil.getString(resourceRequest, "trem_4", BLANK);
		String tb_submitted_complied = ParamUtil.getString(resourceRequest, "tr_5", BLANK);
		String tb_submitted_remarks = ParamUtil.getString(resourceRequest, "trem_5", BLANK);
		String tb_observations_complied = ParamUtil.getString(resourceRequest, "tr_6", BLANK);
		String tb_observations_remarks = ParamUtil.getString(resourceRequest, "trem_6", BLANK);
		String tb_subscribers_complied = ParamUtil.getString(resourceRequest, "tr_7", BLANK);
		String tb_subscribers_remarks = ParamUtil.getString(resourceRequest, "trem_7", BLANK);
		
		_log.info("1.complied: " + tb_PFRDA_Act_complied + " 1.remarks: " + tb_PFRDA_Act_remarks);
		_log.info("2.complied: " + tb_review_complied + " 2.remarks: " + tb_review_remarks);
		_log.info("3.complied: " + tb_investment_complied + " 3.remarks: " + tb_investment_remarks);
		_log.info("4.complied: " + tb_communication_complied + " 4.remarks: " + tb_communication_remarks);
		_log.info("5.complied: " + tb_submitted_complied + " 5.remarks: " + tb_submitted_remarks);
		_log.info("6.complied: " + tb_observations_complied + " 6.remarks: " + tb_observations_remarks);
		_log.info("7.complied: " + tb_subscribers_complied + " 7.remarks: " + tb_subscribers_remarks);
		_log.info("===============================================================================================");
		
		//(2) for ma details 
		String ma_auditors_complied = ParamUtil.getString(resourceRequest, "au_1", BLANK);
		String ma_auditors_remarks = ParamUtil.getString(resourceRequest, "aurem_1", BLANK);
		String ma_compliance_complied = ParamUtil.getString(resourceRequest, "au_2", BLANK);
		String ma_compliance_remarks = ParamUtil.getString(resourceRequest, "aurem_2", BLANK);
		String ma_observations_complied = ParamUtil.getString(resourceRequest, "au_3", BLANK);
		String ma_observations_remarks = ParamUtil.getString(resourceRequest, "aurem_3", BLANK);
		
		_log.info("1.complied: " + ma_auditors_complied + " 1.remarks: " + ma_auditors_remarks);
		_log.info("2.complied: " + ma_compliance_complied + " 2.remarks: " + ma_compliance_remarks);
		_log.info("3.complied: " + ma_observations_complied + " 3.remarks: " + ma_observations_remarks);
		_log.info("===============================================================================================");
		
		//(3) for mo details 
		String mo_agreements_complied = ParamUtil.getString(resourceRequest, "mo_1", BLANK);
		String mo_agreements_remarks = ParamUtil.getString(resourceRequest, "morem_1", BLANK);
		String mo_found_complied = ParamUtil.getString(resourceRequest, "mo_2", BLANK);
		String mo_found_remarks = ParamUtil.getString(resourceRequest, "morem_2", BLANK);
		String mo_segregated_complied = ParamUtil.getString(resourceRequest, "mo_3", BLANK);
		String mo_segregated_remarks = ParamUtil.getString(resourceRequest, "morem_3", BLANK);
		String mo_diligence_complied = ParamUtil.getString(resourceRequest, "mo_4", BLANK);
		String mo_diligence_remarks = ParamUtil.getString(resourceRequest, "morem_4", BLANK);
		
		_log.info("1.complied: " + mo_agreements_complied + " 1.remarks: " + mo_agreements_remarks);
		_log.info("2.complied: " + mo_found_complied + " 2.remarks: " + mo_found_remarks);
		_log.info("3.complied: " + mo_segregated_complied + " 3.remarks: " + mo_segregated_remarks);
		_log.info("4.complied: " + mo_diligence_complied + " 4.remarks: " + mo_diligence_remarks);
		_log.info("===============================================================================================");
		
		//(4) for pa details 
		String pa_custody_complied = ParamUtil.getString(resourceRequest, "pa_1", BLANK);
		String pa_custody_remarks = ParamUtil.getString(resourceRequest, "parem_1", BLANK);
		String pa_protected_complied = ParamUtil.getString(resourceRequest, "pa_2", BLANK);
		String pa_protected_remarks = ParamUtil.getString(resourceRequest, "parem_2", BLANK);
		String pa_supervising_complied = ParamUtil.getString(resourceRequest, "pa_3", BLANK);
		String pa_supervising_remarks = ParamUtil.getString(resourceRequest, "parem_3", BLANK);
		String pa_accounts_complied = ParamUtil.getString(resourceRequest, "pa_4", BLANK);
		String pa_accounts_remarks = ParamUtil.getString(resourceRequest, "parem_4", BLANK);
		
		_log.info("1.complied: " + pa_custody_complied + " 1.remarks: " + pa_custody_remarks);
		_log.info("2.complied: " + pa_protected_complied + " 2.remarks: " + pa_protected_remarks);
		_log.info("3.complied: " + pa_supervising_complied + " 3.remarks: " + pa_supervising_remarks);
		_log.info("4.complied: " + pa_accounts_complied + " 4.remarks: " + pa_accounts_remarks);
		_log.info("===============================================================================================");
		
		//(5) for nto details 
		String nto_maintaining_complied = ParamUtil.getString(resourceRequest, "nto_1", BLANK);
		String nto_maintaining_remarks = ParamUtil.getString(resourceRequest, "ntorem_1", BLANK);
		String nto_adequate_steps_complied = ParamUtil.getString(resourceRequest, "nto_2", BLANK);
		String nto_adequate_steps_remarks = ParamUtil.getString(resourceRequest, "ntorem_2", BLANK);
		String nto_information_complied = ParamUtil.getString(resourceRequest, "nto_3", BLANK);
		String nto_information_remarks = ParamUtil.getString(resourceRequest, "ntorem_3", BLANK);
		String nto_grievances_complied = ParamUtil.getString(resourceRequest, "nto_4", BLANK);
		String nto_grievances_remarks = ParamUtil.getString(resourceRequest, "ntorem_4", BLANK);
		String nto_exit_complied = ParamUtil.getString(resourceRequest, "nto_5", BLANK);
		String nto_exit_remarks = ParamUtil.getString(resourceRequest, "ntorem_5", BLANK);
		
		_log.info("1.complied: " + nto_maintaining_complied + " 1.remarks: " + nto_maintaining_remarks);
		_log.info("2.complied: " + nto_adequate_steps_complied + " 2.remarks: " + nto_adequate_steps_remarks);
		_log.info("3.complied: " + nto_information_complied + " 3.remarks: " + nto_information_remarks);
		_log.info("4.complied: " + nto_grievances_complied + " 4.remarks: " + nto_grievances_remarks);
		_log.info("5.complied: " + nto_exit_complied + " 5.remarks: " + nto_exit_remarks);
		_log.info("===============================================================================================");
		
		//(6) for aa details 
		String aa_annual_report_complied = ParamUtil.getString(resourceRequest, "aa_1", BLANK);
		String aa_annual_report_remarks = ParamUtil.getString(resourceRequest, "aarem_1", BLANK);
		String aa_proper_books_complied = ParamUtil.getString(resourceRequest, "aa_2", BLANK);
		String aa_proper_books_remarks = ParamUtil.getString(resourceRequest, "aarem_2", BLANK);
		String aa_quarterly_info_complied = ParamUtil.getString(resourceRequest, "aa_3", BLANK);
		String aa_quarterly_info_remarks = ParamUtil.getString(resourceRequest, "aarem_3", BLANK);
		String aa_rules_complied = ParamUtil.getString(resourceRequest, "aa_4", BLANK);
		String aa_rules_remarks = ParamUtil.getString(resourceRequest, "aarem_4", BLANK);
		
		_log.info("1.complied: " + aa_annual_report_complied + " 1.remarks: " + aa_annual_report_remarks);
		_log.info("2.complied: " + aa_proper_books_complied + " 2.remarks: " + aa_proper_books_remarks);
		_log.info("3.complied: " + aa_quarterly_info_complied + " 3.remarks: " + aa_quarterly_info_remarks);
		_log.info("4.complied: " + aa_rules_complied + " 4.remarks: " + aa_rules_remarks);
		_log.info("===============================================================================================");
		
		//(7) for last details 
		String compOfficerName_1 = ParamUtil.getString(resourceRequest, "compOfficerName_1", BLANK);
		Date signDate_1 = ParamUtil.getDate(resourceRequest, "signDate_1", dateFormat);
		String place_1 = ParamUtil.getString(resourceRequest, "place_1", BLANK);
		
		String compOfficerName_2 = ParamUtil.getString(resourceRequest, "compOfficerName_2", BLANK);
		Date signDate_2 = ParamUtil.getDate(resourceRequest, "signDate_2", dateFormat);
		String place_2 = ParamUtil.getString(resourceRequest, "place_2", BLANK);
		
		_log.info("compOfficerName_1: " + compOfficerName_1 + " signDate_1: " + signDate_1 + " place_1: " + place_1);
		_log.info("compOfficerName_2: " + compOfficerName_2 + " signDate_2: " + signDate_2 + " place_2: " + place_2);
		_log.info("===============================================================================================");
		
		Compliance_Cert compCert = compLocalService.addComplIanceInfo(number, complianceDate, compFYEndingDate, tbOfficialsFYEndingDate, 
				tb_PFRDA_Act_complied, tb_PFRDA_Act_remarks, 
				tb_review_complied, tb_review_remarks, 
				tb_investment_complied, tb_investment_remarks, 
				tb_communication_complied, tb_communication_remarks, 
				tb_submitted_complied, tb_submitted_remarks, 
				tb_observations_complied, tb_observations_remarks, 
				tb_subscribers_complied, tb_subscribers_remarks, 
				ma_auditors_complied, ma_auditors_remarks, 
				ma_compliance_complied, ma_compliance_remarks, 
				ma_observations_complied, ma_observations_remarks, 
				mo_agreements_complied, mo_agreements_remarks, 
				mo_found_complied, mo_found_remarks, 
				mo_segregated_complied, mo_segregated_remarks,
				mo_diligence_complied, mo_diligence_remarks, 
				pa_custody_complied, pa_custody_remarks, 
				pa_protected_complied, pa_protected_remarks, 
				pa_supervising_complied, pa_supervising_remarks, 
				pa_accounts_complied, pa_accounts_remarks, 
				nto_maintaining_complied, nto_maintaining_remarks, 
				nto_adequate_steps_complied, nto_adequate_steps_remarks, 
				nto_information_complied, nto_information_remarks, 
				nto_grievances_complied, nto_grievances_remarks, 
				nto_exit_complied, nto_exit_remarks, 
				aa_annual_report_complied, aa_annual_report_remarks, 
				aa_proper_books_complied, aa_proper_books_remarks, 
				aa_quarterly_info_complied, aa_quarterly_info_remarks, 
				aa_rules_complied, aa_rules_remarks, 
				compOfficerName_1, signDate_1, place_1, 
				compOfficerName_2, signDate_2, place_2,
				 userId, new Date(), userId, new Date(), WorkflowConstants.STATUS_PENDING, userName); //workflow Arguments  
		
		_log.info("compCert : " + compCert);
		
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		if(Validator.isNotNull(compCert)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(themeDisplay.getUser().getUserId(),
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    Compliance_Cert .class.getName(),
	                    compCert.getCompid(),
	                    compCert.getUuid(),
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
	                    "Compliance Form Asset",
	                    "Compliance Form Asset with Id: " +  compCert.getCompid(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<Compliance_Cert> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Compliance_Cert.class);
				indexer.reindex(compCert);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(themeDisplay.getCompanyId(), themeDisplay.getScopeGroup().getGroupId(), themeDisplay.getUserId(), 
						Compliance_Cert.class.getName(), compCert.getCompid(), compCert, serviceContext);

			} catch (Exception e) {
				 _log.error(e);
				_log.error("Exception in Auditor Certificate Asset Creation : "+e.getMessage());
			}
		}
		
		try {
			createPdf(compCert,themeDisplay,resourceRequest);
		} catch (Exception e) {
			 _log.error(e);
		}
		

		emailApi.sendOTP("salago7157@3dinews.com"); 
		
		//smsApi.sendFormSubmitSuccessfulSMS("919945255124", "Deepak");
		
		
	}
	
	public void createPdf(Compliance_Cert compCert, ThemeDisplay themeDisplay,ResourceRequest resourceRequest) throws IOException {
		
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
        Cell<PDPage> cell = headerRow.createCell(100, "Annexure 13 - Compliance Certificate");
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
        Cell<PDPage> table2cell = table2row.createCell(100, "No. " + compCert.getCertnumber());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
//        table2.addHeaderRow(table2row);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "Date: " + dateFormat.format(compCert.getCompliancedate()));
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(30, "To");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
        
        String address = "Compliance Officer " +  
        		"NPS Trust " + 
        		"Chhatrapati Shivaji Bhavan, " +  
        		"B-14/A, Qutab Institutional Area " +
        		"Katwaria Sarai, New Delhi- 110 016";
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(30, address);
		table2cell.setAlign(HorizontalAlignment.LEFT);
		table2cell.setFontSize(10);
		table2cell = table2row.createCell(70, "");
        
		table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,"Subject: National Pension System Trust (NPS Trust) Compliance Certificate for the FY ending " + dateFormat.format(compCert.getCompfyendingdate()));
		table2cell.setAlign(HorizontalAlignment.LEFT);
		table2cell.setFontSize(10);
        table2cell.setFont(fontBold);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"I certify that the following are in accordance with the functions, duties and obligations of the Trustee Bank prescribed in the PFRDA Act, relevant regulations Trust Deed, Guidelines from the Authority and NPS Trust, issued from time to time, and are in accordance with the examination carried out by me/us and explanations furnished by Trustee Bank officials for FY ending "
						+ dateFormat.format(compCert.getTbfyendingdate()));
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2.draw();
        
        float tableHeight2 = table2.getHeaderAndDataHeight();
        _log.info("tableHeight2 = "+tableHeight2);
        
        float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30 - tableHeight2 - 20;
        BaseTable table3 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
        
        Row<PDPage> table3row = table3.createRow(20);
        Cell<PDPage> table3cell = table3row.createCell(10, "S.No");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(50, "Roles and Responsibilities");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15, "Complied (Yes/No/N.A.*)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(25, "Remarks, if any");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        
        //Section A
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "A.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(50,"Trustee Bank");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        
		table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(i)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank is ensuring that it is managed in accordance with the provisions of the PFRDA Act, Trust Deed and all relevant regulations, guidelines and directions issued by the Authority and NPS Trust?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getTbpfrdacomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getTbpfrdarem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
		table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(ii)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank is reviewing the schemes performance and transactions carried out by it, and has submitted all required exception reports along with its recommendations to the Authority and NPS Trust, as applicable?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getTbreviewcomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getTbreviewrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iii)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank has entered into an investment management agreement  with NPS Trust and all such other agreements as specified in the PFRDA guidelines issued from time to time, for the protection of subscribers interest and information?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getTbinvestcomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getTbinvestrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iv)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank acts upon communication in writing received from NPS Trust informing it of the deficiencies and reports to NPS Trust on the rectification of such deficiencies?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getTbcommcomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getTbcommrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(v)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank has submitted and on time the audited scheme financials, internal audit reports, inspection, compliance reports and any other reports, as specified by the Authority and / or NPS Trust, to the Authority / NPS Trust, as applicable?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getTbsubmitcomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getTbsubmitrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(vi)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether with observations and recommendations, if any, made by NPS Trust on the above referred reports were acted upon and / or responded to and on time?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getTbobservecomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getTbobserverem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
		table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(vii)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Neither any assets have been acquired out of subscribers funds nor any liability assumed which results in encumbrance of the property of the National Pension System Trust or that of the subscribers in any way.");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getTbsubscribecomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getTbsubscriberem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        //Section B
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "B.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(50,"Monitoring of Audit and Compliance reports of Intermediaries");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        
        table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(i)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether internal audit reports at regular intervals for audits conducted by auditors appointed by NPS Trust as well asÂ  by the Trustee Bank itself and the deviations mentioned therein have been shared with the Board and reported to PFRDA and NPS Trust, as applicable?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getMaauditorscomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getMaauditorsrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
		table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(ii)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether compliance certificates at regular intervals have been submitted to NPS Trust?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getMacompliancecomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getMacompliancerem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iii)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether NPS Trust observations on the reports of the auditor and compliance reports have been acted upon and / or responded to and on time?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getMaobservecomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getMaobserverem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        //Section C
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "C.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(50,"Monitoring of operations and service level activities of intermediaries");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        
        table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(i)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether all operational agreements under the National Pension System with NPS Trust and all other parties, as required, are executed and valid as on date?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getMoagreecomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getMoagreerem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
		
		table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(ii)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Where it has been observed/found by NPS Trust or the Trustee Bank itself that the conduct of its business is not in accordance with applicable regulations, guidelines, notifications, circular issued and operational agreements, whether any remedial steps have been taken and whether NPS Trust and PFRDA, as required, have been informed regarding violation and subsequent action, if any.");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getMofoundcomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getMofoundrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
		table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iii)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether segregated bank accounts and other records pertaining to activities under National Pension System are being maintained?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getMosegcomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getMosegrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
		
        table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(iv)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank is exercising all due diligence and vigilance in carrying out its duties and in protecting the rights and interests of the subscribers and is complying with the terms and conditions of Service Level Agreements (SLA) & guidelines issued by PFRDA and NPS Trust ?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getModilicomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getModilirem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
		
		//Section D
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "D.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(50,"Protection of Assets of Beneficial Owners");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		
        table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(i)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Where Trustee Bank has custody or under its control any property of the Trust, whether it is being held in the interest of beneficiaries?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getPacustodycomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getPacustodyrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
		table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(ii)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether Trustee Bank is ensuring that any NPS Trust property under its custody / control is properly protected, held and administered by the proper persons?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getPaprotectedcomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getPaprotectedrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iii)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Where the Trustee Bank is supervising the collection of any income due on assets held in the name of the National Pension System Trust, claiming any repayment of tax and holding any income received in trust for the beneficiaries, the same are in accordance with the Trust Deed and the regulations, guidelines or directions issued by the Authority and NPS Trust.");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getPasupervisecomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getPasuperviserem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iv)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank is giving true and accurate accounts of all income due to it in the course of its role in the management of the National Pension System or in relation to carrying out the objectives and purpose of the National Pension System ?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getPaaccountscomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getPaaccountsrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        //Section E
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "E.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(50,"NPS Trust Obligations");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        
        table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(i)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank is maintaining records of the decisions taken by its Board at its meetings and the minutes of the meetings?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getNtomaintaincomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getNtomaintainrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
		table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(ii)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Where applicable and required, the intermediary is taking adequate steps to spread awareness on various aspects of NPS to the subscribers.");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getNtoadequatecomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getNtoadequaterem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iii)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether Trustee Bank fulfills its duty to provide or cause to provide information to the beneficiaries, the Authority and NPS Trust, as may be required, from time to time?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getNtoinformcomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25, compCert.getNtoinformrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iv)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether subscriber grievances have been resolved in accordance with the Trustee Bank Regulatory and Development Authority (Redressal of Subscriber Grievance) Regulations, 2015?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getNtogrievancescomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25, compCert.getNtogrievancesrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(v)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Where applicable, whether the Exit /Withdrawal of the subscriber from the National Pension System has been undertaken as per the provisions of regulations and guidelines,and deviations reported to PFRDA and NPS Trust?");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getNtoexitcomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getNtoexitrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);

        //Section F
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "F.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(50,"Accounts and Administration related obligations");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,"");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        
        table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(i)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank prepares once every year, an annual report of its activities under the National Pension System? Whether a copy of the same along with the copy of the audited accounts with observations at the end of the financial year is being furnished to the Authority and NPS Trust before May 31st of the same calendar year?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getAaannualcomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getAaannualrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
		
		table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(ii)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50,
				"Whether the Trustee Bank is maintaining proper books of accounts and records with respect to all sums of money received and expended by it under the National Pension System?");
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getAabookscomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getAabooksrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);
		
		String str3 = "Whether the Trustee Bank is furnishing quarterly information to NPS Trust as mentioned below:" +
					"(i) report on the activities of the Trustee Bank;" +
					"(ii) a certificate stating that it has satisfied itself that there have been no instances of self-dealing or front running by any of the trustees, directors and key personnel of the Trustee Bank;" +
					"(iii) a certificate to the effect that the Trustee Bank has been managing its affairs under NPS independently of any other activities and that it has taken adequate steps to ensure that the interest of subscribers are protected;" + 
					"(iv) exception reports; and "+ 
					"(v) recommendations on further course of action on deviations?";
		
		table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "(iii)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50, str3);
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell = table3row.createCell(15, compCert.getAaquarterlycomp());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(25,compCert.getAaquarterlyrem());
        table3cell.setAlign(HorizontalAlignment.LEFT);
        
        String str4 = "(i) Whether the Trustee Bank has and is following its administrative & financial rules?" +
        		"(ii) All important communications/letters issued by GOI/ Authority, NPS Trust or other stakeholders have been attended to promptly and in a time-bound manner.";
        
        table3row = table3.createRow(20);
		table3cell = table3row.createCell(10, "(iv)");
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(50, str4);
		table3cell.setAlign(HorizontalAlignment.LEFT);
		table3cell = table3row.createCell(15, compCert.getAarulescomp());
		table3cell.setAlign(HorizontalAlignment.CENTER);
		table3cell = table3row.createCell(25, compCert.getAarulesrem());
		table3cell.setAlign(HorizontalAlignment.LEFT);

        table3.draw();
        
        float tableHeight3 = table3.getHeaderAndDataHeight();
        _log.info("tableHeight3 = "+tableHeight3);
        
        float table4YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 20 - (tableHeight3/2) - 20;
        BaseTable table4 = new BaseTable(table4YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
        
        String table4String = "Certified that the information given herein are correct and complete to the best of my knowledge and belief. All pending items/actions have been duly mentioned under remarks and their completion would be reported in the monthly progress report to be submitted by 10th of subsequent month till all the issues are resolved.";
        
        Row<PDPage> table4row = table4.createRow(20);
        Cell<PDPage> table4cell = table4row.createCell(100, table4String);
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(50, "Signature ");
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        table4cell = table4row.createCell(50, "Signature ");
        table4cell.setAlign(HorizontalAlignment.RIGHT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(50, "Compliance Officer, Trustee Bank");
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        table4cell = table4row.createCell(50, "Principal Officer, Trustee Bank");
        table4cell.setAlign(HorizontalAlignment.RIGHT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(50, "Name of the Compliance Officer " + compCert.getCompofficername1());
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        table4cell = table4row.createCell(50, "Name " + compCert.getCompofficername2());
        table4cell.setAlign(HorizontalAlignment.RIGHT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(50, "Date: " + dateFormat.format(compCert.getSigndate1()));
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        table4cell = table4row.createCell(50, "Date: " + dateFormat.format(compCert.getSigndate2()));
        table4cell.setAlign(HorizontalAlignment.RIGHT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(50, "Place: " + compCert.getPlace1());
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        table4cell = table4row.createCell(50, "Place: " + compCert.getPlace2());
        table4cell.setAlign(HorizontalAlignment.RIGHT);
        table4cell.setFontSize(10);
        
        table4.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
      	File Compliancefile = File.createTempFile("Compliance Form", "pdf");
      	outputStream = new FileOutputStream(Compliancefile); 
      	document.save(outputStream);
        String filepath = Compliancefile.getPath(); 
		String filename = Compliancefile.getName();
      	
        document.close();
        outputStream.close();
        
        try {
            pdfUpload(themeDisplay, filepath, filename, resourceRequest);
		} catch (Exception e) {
			_log.error("Exception in uploading Compliance Form PDF : "+e.getMessage());
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Annually");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	@Reference
	Compliance_CertLocalService compLocalService;
	
	@Reference
	ComplianceEmailApi emailApi;
	
//	@Reference
//	ComplianceSmsApi smsApi;
	

}
