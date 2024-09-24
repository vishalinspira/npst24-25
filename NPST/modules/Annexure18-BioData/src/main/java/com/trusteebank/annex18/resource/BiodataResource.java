package com.trusteebank.annex18.resource;

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

import Annexure18.BioData.constants.Annexure18BioDataPortletKeys;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.line.LineStyle;


@Component(property = { 
		"javax.portlet.name=" + Annexure18BioDataPortletKeys.ANNEXURE18BIODATA,
		"mvc.command.name=" + Annexure18BioDataPortletKeys.bioData, 
		}, 
service = MVCResourceCommand.class)

public class BiodataResource implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(BiodataResource.class);

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
        Cell<PDPage> cell = headerRow.createCell(100, "Annexure 18 - Biodata Of Directors");
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
				"Sub: Submission of details of interest of directors in other companies every six months and Bio¬data of newly appointed Directors (Registration_ No. - TB00_t_)");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell.setFont(fontBold);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"We refer to Para 2.a & 2.b of Schedule II of the agreement between Trustees of the NPS Trust and the Trustee Bank under National Pension System (NPS) wherein Trustee Bank is required to provide Bio-data of its directors along with their interest in other companies and Trustee Bank is required to provide details of interest of directors of Axis Bank in other companies every six months or for newly appointed director.");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"This is to inform you that Mr. Rajesh Dahiya, Executive Director (Corporate Centre) and KMP of the Bank, has sought early retirement from the services of the Bank, to pursue personal and professional interests outside his corporate career.");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"The Board of Directors (the Board) of the Bank at its meeting held today considering the aspirations of Mr. Rajesh Dahiya, accepted his decision to seek early retirement from the services of the Bank. The said retirement will be effective after expiry of three months viz. after the close of business hours on Friday, December 31, 2021.");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"Mr. Dahiya had joined the Bank in June 2010, after a successful stint of 20 years across various group companies of the Tata Group. He joined as President - HR and assumed the role of Group Executive - Corporate Centre in July 2014 and thereafter assumed charge as the Executive Director - Corporate Centre in August 2016. He is an integral member of the transformation journey over the last 3 years, and his responsibilities in addition to Corporate Centre, included overseeing the functioning of Axis Bank Foundation, the CSR arm of the Bank.");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"He is also a Director on fhe Boards of Axis Trustee Services Limited and Max Life Insurance Company Limited. Mr. Dahiya is also involved in several key projects being undertaken by the Bank viz. corporate governance, Sustainability & ESG, Customer Excellence, amongst others. The Bank has initiated the succession plan internally and continuity of initiatives has been ensured.");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"Mr. Dahiya stays committed to the growth ambitions of the Bank and he has consented to be closely associated with the Bank through specific projects / assignments that will help shape distinctiveness for the Bank. He will continue as a key board member for associates & subsidiaries (Axis Bank Foundation, Axis Trustee and Max Life).");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
		table2cell = table2row.createCell(100,
				"The Board places on record, their deep appreciation for his valuable contributions and his leadership during his association with the Bank. The Board wishes him the very best as he begins a new chapter outside his distinguished corporate career. You are requested to take the above on record and bring this to the notice of all concerned.");
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
		table2cell = table2row.createCell(30, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        table2cell.setFont(fontBold);
        table2cell = table2row.createCell(70, "");
        
        table2.draw();
        
        float tableHeight2 = table2.getHeaderAndDataHeight();
	    _log.info("tableHeight2 = "+tableHeight2);
	      
	    float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 20;
        BaseTable table3 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
	      
	    Row<PDPage> table3row = table3.createRow(20);
	    Cell<PDPage> table3cell = table3row.createCell(100, "Details of other directorships of Directors of Axis Bank");
	    table3cell.setAlign(HorizontalAlignment.CENTER);
	    table3cell.setFontSize(14);
	    table3.addHeaderRow(table3row);
	    
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "Sr.no.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFont(fontBold);
        table3cell = table3row.createCell(30, "Name of Director");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFont(fontBold);
        table3cell = table3row.createCell(60, "Other directorships along with nature of interest (as on 14.06.2021)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table2cell.setFont(fontBold);
        
        //1
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "1");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Shri Amitabh Chaudhry");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(i) Axis Capital Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(ii) Axis Finance Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(iii) Axis Asset Management Company Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //2
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "2");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Shri. S. Vishvanathan");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(i) Orient Paper & Industries Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(ii) The Clearing Corporation of.India Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //3
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "3");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Shri Rakesh Makhija");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(i) A.TREDS Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(ii) Castrol India Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //4
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "4");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Smt. Ketaki Bhagwati");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(i) Omniactive Health Technologies Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(ii) Bayer CropScience Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(iii) KPMG India, Senior Advisor");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //5
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "5");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Shri Stephen Pagliuca");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(i)Gartner Inc. {Delaware, USA), Director & Shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(ii) Virgin Australia Holdings Limited (Australia), Alternate Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(iii) Virgin Cruises Limited (Bermuda), Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(iv) The Boston Celtics (Massachusetts, USA), Shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(v) Bain Capital, LP(Delaware, USA), Co-Chair");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(vi) Bain Capital Private Equity, LP (Delaware, USA), Managing Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(vii) II-VI, Inc. (Pennsylvania, USA), Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //6
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "6");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Shri Girish Paranjpe");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(i) IBS Software Pte Limited, Singapore, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(ii) Dixcy Textile Pvt Ltd, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(iii)CRISIL Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(iv) ASK Investment Managers Ltd - Independent Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(v) Exfinity Venture Partners, LLP - General Partner and shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(vi) Prayaati Advisory Services, Founder - Sole proprietor and shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(vii) CRISIL lrevna UK Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(viii) Advent India PE Advisors Private Limited, Advisor");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);

        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(ix) Max Life Insurance Company Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //7
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "7");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Shri Rajesh Dahiya");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Axis Trustee Services Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "SES ESG Advisory Board, Member");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Max Life Insurance Company Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //8
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "8");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Shri Rajiv Anand");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(i) National Payments Corporation of India, Nominee Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(ii) Axis Bank UK Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(iii) A.Treds Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(iv) Axis Capital Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(v) Axis Securities Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);

        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "(vi) Max Life Insurance Company Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //9
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "9");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Tharavanat Chandrasekharan Suseelkumar");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "BSE Limited");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);

        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Lakshmi Machine Works Limited, Nominee Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //10
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "10");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Meena Ganesh");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "CRM Holdings Private Limited, Director & Shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Vriksha Realtors Private Limited, Director & Shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Healthvista India Private Limited, Managing Director & Shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Foodvista India Private Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Portea Medical Private Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Ezeesmart Education Private Limited, Director & Shareholder Takecare");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Technology Private Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Qtrove Services Private Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Hygiene Bigbite Private Limited, Director & Shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Starvista Celebrities Private Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Curated Marketplaces Private Limited, Director & Shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Manipalcigna Health Insurance Company Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Rocket Logistics Private Limited, Director Pfizer Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Procter & Gamble Hygiene and Healthcare Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "AEON Learning Private Limited, Member");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Glitz Blitz Promotions Private Limited, Member");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);

        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Super Market Grocery Supplies Private Limited, member");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //11
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "11");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Padmanabhan Gopalaraman");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Aditya Birla Sunlife Trustee Private Limited, Chairman");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Haldyn Glass Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "AZB and Partners, Advisor & Consultant");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "IFSCA, Advisor & Consultant");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Visa, Advisor & Consultant");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "SWIFT, Advisor & Consultant");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Stripe Inc, Advisor & Consultant");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Paypal, Advisor & Consultant");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Qwikcilver, Advisor & Consultant");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Zipcash Financial Services, Advisor & Consultant");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Kerala Infrastructure Investment Fund Board, Member, Fund Trustee and Advisory Commission,");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //12
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "12");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Ashish Kotecha");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Spyder (BC) Bidco Pty Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Spyder (BC) Midco Pty Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Spyder (BC) Holdco Pty Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Camp Australia Holdings Proprietary Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Nemo (BC) BidCo Pty Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Nemo (BC) MidCo Pty Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Nemo BC) HoldCo Pty Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Kotecha Investment Corporation Private Limited, Shareholder & Non-Executive Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Bombay Cotton & Yarn Company Private Limited, Shareholder & Non-Executive Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Kotecha & Company, Partner");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Kotecha Charitable Trust, Trustee");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //13
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "13");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Smt. Vasantha Govindan");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "UTI Infrastructure Technology & Services Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "National Financial Holdings Company Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Stock Holding Corporation of India Limited, Director");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "The Ayer Manis Rubber Estate Limited, shareholder");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        table3cell.setTopBorderStyle(bottomBorder);
        
        //14
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(10, "14");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        //table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(30, "Prof. S. Mahendra Dev");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        //table3cell.setBottomBorderStyle(bottomBorder);
        table3cell = table3row.createCell(60, "Indira Gandhi Institute of Development Research, Director & Vice Chancellor");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        //table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(100, "");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setLeftBorderStyle(bottomBorder);
        table3cell.setRightBorderStyle(bottomBorder);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(100, "Signature ");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setLeftBorderStyle(bottomBorder);
        table3cell.setRightBorderStyle(bottomBorder);
        table3cell.setTopBorderStyle(bottomBorder);
        table3cell.setBottomBorderStyle(bottomBorder);
        table3cell.setFontSize(10);

        table3.draw();
        
        cos.close();
        //document.save("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\blank.pdf");
        document.save("C:\\Users\\deepak\\Documents\\pdf\\biodataofdirectors.pdf");
        //document.save("E:\\pdf\\quartelyauditor.pdf");
        document.close();
        
	}

}
