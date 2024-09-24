package com.trusteebank.annex16.resource;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

import Annexure16.InternalAuditReport.constants.Annexure16InternalAuditReportPortletKeys;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;

@Component(property = { 
		"javax.portlet.name=" + Annexure16InternalAuditReportPortletKeys.ANNEXURE16INTERNALAUDITREPORT,
		"mvc.command.name=" + Annexure16InternalAuditReportPortletKeys.auditReport, 
		}, 
service = MVCResourceCommand.class)

public class AuditResource implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(AuditResource.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		_log.info("Serve Resource method");
		
		try {
			createPdf(resourceRequest);
		} catch (Exception e) {
			_log.error(e);
		}
		
		return false;
	}
	
	public void createPdf(ResourceRequest resourceRequest) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;
        LineStyle bottomBorder = new LineStyle(Color.white, 0);
        
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
        Cell<PDPage> cell = headerRow.createCell(100, "Annexure 16 - Internal Audit Report");
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
        Cell<PDPage> table2cell = table2row.createCell(100, "Ref. - AXB/2021-22/GEN/17");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "Date - 11th October 2021");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        String name = "Shri Rajiv Raj," + 
        		"Deputy General Manager,"+ 
        		"National Pension System Trust,";
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(30, name);
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell.setFont(fontBold);
        table2cell = table2row.createCell(70, "");
        
        String address = "14th Floor," + 
        		"IFCI Tower, 61," + 
        		"Nehru Place, New Delhi - 110 019";
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(30, address);
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(30, "Dear Sir,");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(70, "");
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"Sub:  Submission of internal audit report for period October 2020 to May 2021 (Registration No. -TB001)");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell.setFont(fontBold);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"We refer to the requirement of submission of Internal audit reports of NPS Trustee Bank activities to NPS Trust. In this regard, we hereby submit last conducted Internal audit report for period October 2020 to May 2021 for your reference.");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"Kindly acknowledge receipt of the same.");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(40,
				"Yours faithfully ");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(60, "");
        
        String name2 = "Alok Srivastava "  + 
        		" Vice President " + 
        		"Centralized Collection & Payment Hub";
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(30, name2);
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell.setFont(fontBold);
        table2cell = table2row.createCell(70, "");
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(40,
				"Encls. As above ");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell = table2row.createCell(60, "");
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"Copy to: Shri. Vikas Kumar Singh, General Manager, Pension Fund Regulatory and Development Authority, Third Floor, Chhatrapati Shivaji Bhawan, B-14/A, Qutab Institutional Area, New Delhi - 110016. For information");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(30, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell.setFont(fontBold);
        table2cell = table2row.createCell(70, "");
        
        table2.draw();
        
        float tableHeight2 = table2.getHeaderAndDataHeight();
	    _log.info("tableHeight2 = "+tableHeight2);
	      
	    float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 400;
        BaseTable table3 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
	      
	    Row<PDPage> table3row = table3.createRow(20);
	    Cell<PDPage> table3cell = table3row.createCell(100, "AXIS BANK");
	    table3cell.setAlign(HorizontalAlignment.CENTER);
	    table3cell.setFontSize(14);
	    table2cell.setFont(fontBold);
	    //table3.addHeaderRow(table3row);
	    
	    table3row = table3.createRow(20);
	    table3cell = table3row.createCell(100, "INTERNAL AUDIT DEPARTMENT");
	    table3cell.setFontSize(14);
	    table3cell.setAlign(HorizontalAlignment.CENTER);
	    table2cell.setFont(fontBold);
	    
	    table3row = table3.createRow(20);
	    table3cell = table3row.createCell(100, "CORPORATE OFFICE, MUMBAI");
	    table3cell.setFontSize(14);
	    table3cell.setAlign(HorizontalAlignment.CENTER);
	    table2cell.setFont(fontBold);
	    
	    table3row = table3.createRow(20);
	    table3cell = table3row.createCell(100, "INTERNAL AUDIT REPORT OF NPS - TRUSTEE BANK Dated: 03.08.2021");
	    table3cell.setFontSize(14);
	    table3cell.setAlign(HorizontalAlignment.CENTER);
	    table2cell.setFont(fontBold);
	    
	    table3.draw();
	    
	    float tableHeight3 = table3.getHeaderAndDataHeight();
	    _log.info("tableHeight3 = "+tableHeight3);
      
	    float table4YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 20 - (tableHeight3) - 1000;
	    BaseTable table4 = new BaseTable(table4YPosition, yStartNewPage,topMargin,
              bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
      
	    Row<PDPage> table4row = table4.createRow(20);
	    Cell<PDPage> table4cell = table4row.createCell(100, "Date: 03.08.2021");
	    table4cell.setAlign(HorizontalAlignment.LEFT);
	    table4cell.setLeftBorderStyle(bottomBorder);
	    table4cell.setRightBorderStyle(bottomBorder);
	    table4cell.setTopBorderStyle(bottomBorder);
	    table4cell.setBottomBorderStyle(bottomBorder);
	    table4cell.setFontSize(10);
      
	    String str1 = 
	    			"AXIS Bank Ltd., Corporate Office," +
	    			"Wadia International Centre," +
	    			"P.B. Marg," + 
	    			"Worli, Mumbai - 400 025,";
	    
	    table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(30, "Chief Audit Executive ");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell.setFont(fontBold);
	  	table4cell = table4row.createCell(70, "");
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	    
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(30, str1);
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell = table4row.createCell(70, "");
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(30, "Dear Sir,");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell = table4row.createCell(70, "");
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "Audit Report of NPS - Trustee Bank");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "We have carried out the internal audit of NPS - Trustee Bank. A report of the review conducted is enclosed for your perusal.");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "Copy of this letter has been sent along with the audit report to the Vice President -the NPS -Trustee Bank");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "The audit observations are based on the files, documents, papers, information and . explanations furnished by the concerned officials to the audit team.");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "The findings of the report have been discussed with the concerned officials. We undertake to keep the findings confidential and these would not be disclosed / shared with unauthorized persons.");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "Yours faithfully,");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(25, "Aditya Pawaskar ");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell.setFont(fontBold);
	  	table4cell = table4row.createCell(25, "Sujata Rao");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell.setFont(fontBold);
	  	table4cell = table4row.createCell(25, "Yogesh Oturkur");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell.setFont(fontBold);
	  	table4cell = table4row.createCell(25, "Shaishavi Dave");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell.setFont(fontBold);
	  	
	  	String aditya = "Senior Vice President" + 
	  					"(Internal Audit)";
	  	
	  	String sujatha = "Vice President " +
	  					"(Internal Audit)";
	  	
	  	String yogesh = "Deputy Vice President" +
	  					"(Internal Audit)";
	  	
	  	String shaishavi = "Assistant Vice President" +
	  					"(Internal Audit)";
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(25, aditya);
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell = table4row.createCell(25, sujatha);
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell = table4row.createCell(25, yogesh);
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	table4cell = table4row.createCell(25, shaishavi);
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	String str2 = " Copy forwarded, for information and necessary action to Mr. Rahul Choudhary (Executive Vice President - CCPH).";
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "\u2022" + str2);
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	String str3 = " A copy of the internal Audit report is enclosed. Please take the necessary action immediately and submit compliance report to the Internal Audit Department through Controllers (wherever applicable) within the requisite timeline.";
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "\u2022" + str3);
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	String str4 = " The time norms for receipt of compliance and closure of the audit issues are based on the risk classification of the audit issue mentioned in the report. Accordingly the times norms for closure of audit findings is as follows:-";
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "\u2022" + str4);
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(100, "");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell.setLeftBorderStyle(bottomBorder);
	  	table4cell.setRightBorderStyle(bottomBorder);
	  	table4cell.setTopBorderStyle(bottomBorder);
	  	//table4cell.setBottomBorderStyle(bottomBorder);
	  	table4cell.setFontSize(10);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(50, "Nature of the Audit Issue");
	  	table4cell.setAlign(HorizontalAlignment.CENTER);
	  	table4cell.setFontSize(10);
	  	table4cell.setFont(fontBold);
	  	table4cell = table4row.createCell(50, "Time Norms for submission of the compliance by the Auditee Unit");
	  	table4cell.setAlign(HorizontalAlignment.CENTER);
	  	table4cell.setFontSize(10);
	  	table4cell.setFont(fontBold);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(50, "Extremely High Risk / Very High Risk / High Risk");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell = table4row.createCell(50, "40 Working Days");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(50, "Medium Risk");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell = table4row.createCell(50, "45 Working Days");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	
	  	table4row = table4.createRow(20);
	  	table4cell = table4row.createCell(50, "Low Risk");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);
	  	table4cell = table4row.createCell(50, "60 Working Days");
	  	table4cell.setAlign(HorizontalAlignment.LEFT);

	    table4.draw();
	    
	    float tableHeight4 = table4.getHeaderAndDataHeight();
	    _log.info("tableHeight4 = "+tableHeight4);
      
	    float table5YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 20 - tableHeight3 - 1000 - tableHeight4 - 400;
	    BaseTable table5 = new BaseTable(table5YPosition, yStartNewPage,topMargin,
              bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
      
	    Row<PDPage> table5row = table5.createRow(20);
	    Cell<PDPage> table5cell = table5row.createCell(100, "AXIS BANK");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Internal Audit Department Central Office");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Audit Report - NPS - Trustee Bank");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "1. Background");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
		String str5 = "Axis Bank has been appointed by Pension Fund Regulatory and Development Authority (PFRDA) as the Trustee Bank for National Pension System (NPS) effective from lst July, 2015 for a period of 5 years. Axis Bank was re-appointed by PFRDA as the Trustee Bank from October 2020 for period of 5 years";

	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, str5);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    String str6 = "Trustee Bank as an intermediary receives NPS funds from all Nodal Offices and transfers the same to the Pension Funds / Annuity Service Providers/Other Intermediaries as per instructions received from the Central Recordkeeping Agency (CRA). The funds with incomplete information are returned back to the respective Nodal Offices of accredited banks for credit to the source account.";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, str6);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    String str7 = "Trustee Bank ensures daily/weekly reporting of the amounts received from various nodal offices towards NPS, remittances to Pension Fund Managers (PFMs), processing of withdrawal request, redressal of grievances and other allied activities based on instructions received from CRA.";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, str7);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "2. Scope of Audit");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Adherence to the agreed terms of Service Level Agreement signed with NPS Trust.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Review the process of collections and transaction matching in Electronic Payment Hub (EPH) system.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);

	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Reconciliation, accounting and reporting of NPS funds received from Nodal offices");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Remittance of funds to Pension Fund Managers based on instructions received from CRA.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Processing of funds to subscriber individual account based on withdrawal request.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Regulatory reporting to PFRDA/NPS Trust/Other Regulators.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Compliance testing of last year's audit report.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Scope exclusion:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Opening of NPS accounts, NPS activities carried out at CRS Unit and NPS product features covered under NPS POP Audit.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "3. Audit Period:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "01.10.2020 to 31.05.2021");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "4. Audit Rating:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "I. Operating Effectiveness :  ");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setLeftBorderStyle(bottomBorder);
	  	table5cell.setRightBorderStyle(bottomBorder);
	  	table5cell.setTopBorderStyle(bottomBorder);
	  	table5cell.setBottomBorderStyle(bottomBorder);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	table5cell = table5row.createCell(50, "Partially Effective");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(100, "II. Residual Risk Assessment  ");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setLeftBorderStyle(bottomBorder);
	  	table5cell.setRightBorderStyle(bottomBorder);
	  	table5cell.setTopBorderStyle(bottomBorder);
	  	table5cell.setBottomBorderStyle(bottomBorder);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	
	  	table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(100, "");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setLeftBorderStyle(bottomBorder);
	  	table5cell.setRightBorderStyle(bottomBorder);
	  	table5cell.setTopBorderStyle(bottomBorder);
	  	//table5cell.setBottomBorderStyle(bottomBorder);
	  	table5cell.setFontSize(10);
	  	
	  	table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(50, "Risk Type");
	  	table5cell.setAlign(HorizontalAlignment.CENTER);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	table5cell = table5row.createCell(50, "Risk Category");
	  	table5cell.setAlign(HorizontalAlignment.CENTER);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	
	  	table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(50, "Inherent Risk Rating");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell = table5row.createCell(50, "Very High Risk");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	
	  	table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(50, "Control Score and Rating");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell = table5row.createCell(50, "High Risk");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	
	  	table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(50, "Residual Risk Rating");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell = table5row.createCell(50, "High Risk");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	
	  	table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	  	
	  	table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(100, "III. Heat Map");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    //table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(100, "Inherent Risk Rating");
	  	table5cell.setValign(VerticalAlignment.TOP);
	  	table5cell.setTextRotated(true);
	  	//table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	
	    //row 1
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(16, "Extremely High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Very High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Extremely High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Extremely High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	
	  	//row 2
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(16, "Very High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	table5cell = table5row.createCell(14, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Very High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Extremely High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	
	  	//row 3
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(16, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	table5cell = table5row.createCell(14, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Very High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	
	  	//row 4
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(16, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	table5cell = table5row.createCell(14, "Low");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	
	  	//row 5
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(16, "Low");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	table5cell = table5row.createCell(14, "Low");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Low");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Low");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	
	  	//row 6
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(16, "");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell.setFont(fontBold);
	  	table5cell = table5row.createCell(14, "Low");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Medium");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Very High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	table5cell = table5row.createCell(14, "Extremely High");
	  	table5cell.setAlign(HorizontalAlignment.LEFT);
	  	table5cell.setFontSize(10);
	  	
	  	//row 7
	    table5row = table5.createRow(20);
	  	table5cell = table5row.createCell(100, "Control Risk Rating");
	  	table5cell.setAlign(HorizontalAlignment.CENTER);
	  	table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "5. Audit Observations, Recommendation and Remedial Action Plan");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "5.1 Audit of NPS Trustee Bank");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "1. Process to ensure timely intimation of change in officers to Central Record Agency (CRA) was not effective (since complied)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Background:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " As per the Service Level Agreement (SLA) entered between NPS Trustee Bank and NPS Trust");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Trustee Bank is required to provide complete details of all the officers including compliance and alternate compliance officer and register them with the CRA.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " If there is any change in the information, which is provided at the time of registration with CRA, the Trustee Bank should intimate the same to CRA within 7 days.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Observation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " There was delay in submission of details to CRA of the changes in below officers:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Change in Alternate Compliance Officer - delay of 22 days");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Separation of Officer Mr Rajat Varghese from the Bank - delay of 11 days");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Category: ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Regulatory");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Risk Rating:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Very High");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Recommendation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " Unit to periodically monitor that any change in officers is submitted to CRA within 7 days");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Remedial Action Plan:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "CCPH Response:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Weekly tracker is now being submitted to Principal officer every Tuesday.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Action By:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Trustee bank (CCPH)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    //2
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "2. Controls to ensure timely and accurate submission of information related to Directors to NPS Trust were not in place (since complied)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Background:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " PFRDA Rules 2015 stipulates that the Trustee Bank shall file the following with National Pension System Trust");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Bio-data of all its directors along with their interest in other companies within fifteen days of their appointment and");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " any change in the interests of directors at every six months");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Observation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " On verification of submission of director's details made on 29th January 2021 to NPS Trust, noted that the Bio-data and interests of the following Director were not submitted");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    //table start
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "Director");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(50, "Date of Appointment");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "Ms Vasantha Govindan");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "27th January 2021");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    //table end
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    //table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Category: ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Regulatory");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Risk Rating:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Very High");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Recommendation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " Unit to periodically obtain the information from the CS team and submit to the Trust on timely basis to ensure compliance with regulatory requirements");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Remedial Action Plan:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "CCPH Response:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Weekly tracker is now submitted to Principal Officer every Tuesday.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Also, weekly mail is now being sent to CS team by the Unit.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " We have already shared the details with NPS trust on 31st July 2021. Evidence already submitted");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Action By:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Trustee bank (CCPH)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    //3
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "3. Delay in upload of Fund Receipt Confirmation(FRC) file due to technical issues (Repeat Observation)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Background:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " As per point 10 of Schedule IV of SLA with NPS Trust, Trustee Bank Business Activities of the agreement");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, " \"Trustee Bank shall upload the Fund Receipt Confirmation (FRC) file on T+ 1 day on CRA portal (by 9:15 am) of realization of funds. The file has to be uploaded as per the format provided by CRA(s)\".");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Observation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    String str8 = " On review of FRCs uploaded during the period lst October 2020 till 31st May 2021, delays were noted in 3 cases where FRC were uploaded with delays ranging between 30 minutes to 1 hour (beyond 9:15 a.m. on T+1 day).";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + str8);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + "Reasons for the above delays were as follows");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    //table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    //table start
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "No of Instances");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(50, "Date of Appointment");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "2");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "Technical issue at Trustee Bank");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "1");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "Huge Transaction volume");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);

	    //table end
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    //table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "(Refer Annexure : Delay in FRC");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Category: ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Regulatory");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Risk Rating:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Very High");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Recommendation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " Unit to take up with the IT team to identify the root cause and implement preventive controls");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Remedial Action Plan:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "CCPH Response:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " Technical issue at-Trustee Bank - There was technical issue and report from Proplus system (reporting utility) was delayed. This was rectified.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " Huge transaction volume - On huge volumes, the matching took time as it was manual activity. This has now been automated effective 4th June 2021.(implemented)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Action By:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Trustee bank (CCPH)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "4. Comprehensive Digital Signature Certificate (DSC) details not submitted to CRA (Repeat Observation) (since complied)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Background:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Unit provides details of separated employees to CRA agencies for de-activation of Digital Signature Certificate (DSC) of separated employees.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Observation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " On verification of DSC details shared with CRA, following gaps were noted:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Relieving date of Mr Rajat Varghese (Officer) was 10th April 2021. There was delay in intimation of separation to NSDL, which was sent on 6 May 2021");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Confirmation of de-activation of DSC of Mr. Rajat was received from NSDL on 27th May 2021.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Category: ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Non-Regulatory");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Risk Rating:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Medium");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Recommendation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " Control to be put in place to ensure timely intimation and de-activation of DSC of separated employees.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Remedial Action Plan:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "Noted. We have already deactivated the token for Mr. Rajat Varghese. This is now included in the weekly tracker for monitoring");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Action By:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Trustee bank (CCPH)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    //5
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "5. Structured Grievance Redressal Mechanism was not in place:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Background:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " As per Para 3 (o) of the agreement entered between National Pension Scheme (NPS) Trust and Axis Bank - Trustee Bank on 30 June 2015");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\"The Trustee Bank shall redress the grievances of the subscribers in accordance with the PFRDA (Redressal of Subscriber Grievance) Regulations 2015, in force and shall provide data/information, to the NPS Trust, in relation to any transaction, which is subject matter of any grievances of the subscribers, against any other intermediary, to the extent held by the bank.\"");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Observation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " The subscriber complaints received from CRA portal and through emails were addressed and tracked by Trustee bank. However the complaints received on emails were not being reported to the NPS Trust.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Category: ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Regulatory");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Risk Rating:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Very High");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Recommendation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " Process to be put in place to ensure that complaints from all sources are reported to NPS Trust in a comprehensive manner.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Remedial Action Plan:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "CCPH Response:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " While grievance reported on the CRA portal is already reported to the NPS trust, going forward any grievance received on email of npstrusti,oxisbank.com and redirected to CRA for actionable or at Trustee Bank shall be reported to NPS Trust. It will be part of performance report (Timelines: 10th August 2021)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Action By:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Trustee bank (CCPH)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "6. Operational Manual not adequately documented and approved");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Observation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " On review of the operational manual of NPS Trustee Bank, noted that it was not updated and in line with the current process");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " New process of De-remit was not documented");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " The operational manual was not been approved by the Senior Management");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Category: ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Non - Regulatory");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Risk Rating:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Medium");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Recommendation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " The operational manual to be comprehensively documented and approved by Unit Head");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Remedial Action Plan:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " We have prepared the New SOP and same is in the process of approval. We shall submit the same once approved (Time lines: 10th August 2021)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "IAD Comments:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Noted");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Action By:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Trustee bank (CCPH)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "7. Process of timely submission of periodic reports (MIS) to NPS Trust was not effective (since complied)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Background:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Various reports are required to be submitted to NPS Trust on periodic basis as per the prescribed PFRDA guidelines.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Observation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    String str9 = "During the period under review, the Trustee Bank had submitted reports to various stakeholders as per the prescribed guidelines. There were delays of 1 to 2 days in submission of reports in 8 instances:";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " \"" + str9 + "\"");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    //table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    //table start
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(30, "Name of Report");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(30, "Periodicity");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(20, "Due date");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(20, "Actually submitted on");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(30, "Daily Average Balance Report");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(30, "Daily");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(20, "17.03.2021");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(20, "18.03.2021");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    
	    //row 2
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(30, "Weekly Average Balance Report");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(30, "Weekly every Tuesday");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(20, "16.02.2021");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(20, "17.02.2021");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
        table5cell = table5row.createCell(30, "");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setTopBorderStyle(bottomBorder);
        table5cell.setBottomBorderStyle(bottomBorder);
        table5cell = table5row.createCell(30, "");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setTopBorderStyle(bottomBorder);
        table5cell.setBottomBorderStyle(bottomBorder);
        table5cell = table5row.createCell(20, "26.01.2021");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setFontSize(10);
        table5cell = table5row.createCell(20, "28.01.2021");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setFontSize(10);
	    
        table5row = table5.createRow(20);
        table5cell = table5row.createCell(30, "");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell = table5row.createCell(30, "");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell = table5row.createCell(20, "03.11.2020");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setFontSize(10);
        table5cell = table5row.createCell(20, "04.11.2020");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setFontSize(10);
	    
        //row 3
        table5row = table5.createRow(20);
	    table5cell = table5row.createCell(30, "Consolidated collection report");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(30, "Weekly every Tuesday");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(20, "16.02.21");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(20, "17.02.2021");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
        table5cell = table5row.createCell(30, "");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setTopBorderStyle(bottomBorder);
        table5cell.setBottomBorderStyle(bottomBorder);
        table5cell = table5row.createCell(30, "");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setTopBorderStyle(bottomBorder);
        table5cell.setBottomBorderStyle(bottomBorder);
        table5cell = table5row.createCell(20, "26.01.2021");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setFontSize(10);
        table5cell = table5row.createCell(20, "28.01.2021");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setFontSize(10);
	    
        table5row = table5.createRow(20);
        table5cell = table5row.createCell(30, "");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell = table5row.createCell(30, "");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell = table5row.createCell(20, "03.11.2020");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setFontSize(10);
        table5cell = table5row.createCell(20, "04.11.2020");
        table5cell.setAlign(HorizontalAlignment.LEFT);
        table5cell.setFontSize(10);
        
        //row 4
        table5row = table5.createRow(20);
	    table5cell = table5row.createCell(30, "New Branch Opening");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(30, "Weekly every Tuesday");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(20, "06.10.2020");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(20, "7.10.2020");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Category: ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Regulatory");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Risk Rating:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Very High");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
        
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Recommendation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "\u2022" + " Compliance tracker for regulatory submissions to be maintained and reviewed periodically by the Principal officer to ensure timely submission of reports.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Remedial Action Plan:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(95, "Weekly tracker is now being maintained and monitored with maker-checker and no delays have been noted after 01.04.2021 (implemented)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Action By:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Trustee bank (CCPH)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);

	    //table end
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    //table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "8. Instructions for concurrent audit report to be sig_ned by CISA qualified auditor not adhered to: (persisting, irregularity) (since complied)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Background:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    String str10 = "As per the PFRDA (Trustee Bank) Regulations 2015 and Para 3(e) of agreement entered between National Pension Scheme (NPS) Trust and Axis Bank - Trustee Bank on 30 June 2015, : The Trustee Bank shall file the following periodic reports with the Authority (PFRDA) and National Pension System Trust (NPS Trust):";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + str10);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Extracts of internal audit report with respect to the National Pension System;");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Concurrent audit report to be submitted every quarter;");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " External audit report of all the National Pension System accounts maintained with the Trustee Bank to be submitted annually");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " Further, internal/concurrent report should be certified by CISA qualified auditors");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Observation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Concurrent audit reports are certified by DISA qualified auditor instead of CISA qualified auditor.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Category: ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Regulatory");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Risk Rating:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Very High");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Recommendation:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Concurrent Audit report to be certified by CISA qualified auditor in line with NPS Trust guidelines.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Remedial Action Plan:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "CCPH Response:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    String str11 = "Responsibility of appointing CISA qualified auditors were with IAD. CCPH has done the follow up with IAD. However, there was delay on appointment at IAD end due to Covid situation. Further, CISA qualified auditors have been appointed and they are commencing their operations.from 1st July 2021. All the future reports shall be certified by CISA qualified auditor. This point should be tagged to IAD.";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, str11);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(20, "Action By:  ");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(80, "Trustee bank (CCPH)");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Statement of Facts");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(14);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "1. Delay in upload of FRC resulting into payment of penalty");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Compensation of Rs 31384 was paid by NPS Trustee Bank in January 2021 for delay in upload of FRC, return of funds and delay in processing of withdrawals due to technical issues in 2019.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    String str12 = "The delay happened as there were certain transactions that remained unreconciled when 24*7 NEFT went live for the first time. The transactions were received continuously from RBI and EOD could not happen on time.";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + str12);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    String str13 = "Treasury operations immediately identified the issue and changed the EOD timing to align it with the new process of NEFT transactions received 24*7. The control is put in place whereby now EPH system EOD is being processed before Finacle EOD to ensure that all the transactions that are getting captured in EPH are also captured in the same day in Finacle EOD. No such instances have been reported post this change.";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + str13);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "2. Processing of Withdrawal of Funds");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Background:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    String str14 = "SOP on NPS Trustee Bank process stipulates that CRA provides instructions to Trustee Bank to transfer the funds to NPS Trust- Withdrawal A/c (for withdrawal/ redemption request submitted by the subscriber). Based on the instruction received from CRA, funds are required to be transferred to withdrawal account as a part of Pay- in process. After transfer to withdrawal account, Trustee Bank is required to give confirmation for fund transfer of funds on CRA website. CRA further provides instruction to transfer of funds from withdrawal account to respective beneficiaries. Based on the instruction received from CRA, TrUstee Bank carries out transfer of funds to respective beneficiaries on the same day either through direct credit in case subscriber is maintaining account with Axis Bank (Trustee Bank) or through NEFT in case subscriber is maintaining account with other Banks. After successful transfer of withdrawal of Funds, a report is sent to CRA/PFRDA giving UTR detail.";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + str14);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "Statement of Fact:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    String str15 = "As on 24th June 2021, amount of Rs.5.71 crore (due to issues with beneficiary accounts) and Rs.3.38 crore (due to pending instructions from CRA) were lying in the withdrawal accounts and not remitted to beneficiary accounts as instructions were not received from the CRA. Ageing of the same is as under:";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + str15);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    //table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    //table start
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "Ageing (in days)");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(50, "Rs (in cr)");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "15-100");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "3.09");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "101-500");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "1.52");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "501-1000");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "0.78");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "1001-2000");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "0.33");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "greater than 2000");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "0.00");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "Total");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(50, "5.72");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);

	    //table end
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    //table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    //table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    //table start
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "Ageing (in days)");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(50, "Rs (in cr)");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "15-100");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "0.31");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "101-500");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "2.01");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "501-1000");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "0.37");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "1001-2000");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell = table5row.createCell(50, "0.68");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(50, "Total");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    table5cell = table5row.createCell(50, "3.38");
	    table5cell.setAlign(HorizontalAlignment.CENTER);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);

	    //table end
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    //table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " Details of the above un-remitted amounts are as follows:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " In 3929 instances there was delay in payment of withdrawal remittances.");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(5, "");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell = table5row.createCell(95, "\u2022" + " In 5905 instances transfer to beneficiaries were returned and pending for re-execution");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "3. Delay in upload of Fund Receipt Confirmation(FRC) file due to technical issues at CRA");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + " On review of FRCS uploaded during the period 1st October 2020 till 31st May 2021 delays were noted in 52 instances due to technical issues at CRA portal");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "4. Long Outstanding balance in Omni Bus Current Account:");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    String str16 = " The Omnibus current account was created when the funds pertaining to erstwhile Trustee Bank (B01) were transferred to Axis Bank ânew trustee Bank appointed. The NPS fund cannot be used for any other purpose. The NPS Trustee Bank has utilised the fund as per the instructions of CRA. There are funds available in NPS Trust A/C-OMNI BUS amounting to Rs. 35.23 lakhs which is reported to the NPS Trust.";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + str16);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    String str17 = " Hence, the funds will be utilised as and when the details for the same will be received from Nodal Offices/CRA-NSDL/PFRDA/NPS Trust";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + str17);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "5. Manual transfer of files between Trustee Bank & CRA");
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);
	    table5cell.setFont(fontBold);
	    
	    String str18 = " Certain files like withdrawal files are received on emails from the CRA. Additionally, transaction ID files, FRC related files are first downloaded from CRA portal and then uploaded in EPH. Feasibility of SFTP I STP / automation of file transfers to be explored.";
	    
	    table5row = table5.createRow(20);
	    table5cell = table5row.createCell(100, "\u2022" + str18);
	    table5cell.setAlign(HorizontalAlignment.LEFT);
	    table5cell.setLeftBorderStyle(bottomBorder);
	    table5cell.setRightBorderStyle(bottomBorder);
	    table5cell.setTopBorderStyle(bottomBorder);
	    table5cell.setBottomBorderStyle(bottomBorder);
	    table5cell.setFontSize(10);

	    table5.draw();

        cos.close();
        //document.save("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\blank.pdf");
        document.save("C:\\Users\\deepak\\Documents\\pdf\\internal audit report.pdf");
        //document.save("E:\\pdf\\quartelyauditor.pdf");
        document.close();
        
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
