package com.quarterly.stewardship.report.util;

import com.itextpdf.io.util.FileUtil;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.UnitValue;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


import java.io.File;

public class QuartelyStewardshipReportPdfUtil {
	public static File QuartelyStewardshipReportPdf(long reportuploadlogid,String dueDate,String formDate,String conflict,String conflictRem1,
			String monitoring,String monitoringRem1,String resolutions,String resolutionsRem1,String insInvestorSituation,String insInvestorSituationRem1,String adversealert,String adversealertRem1,String resolutionsVoted1,
			String resolutionsVotedRem1,String companies,String date_2,String place,String employeeName,String roles)
	{
		File file=null;
		try  {  
			 file=FileUtil.createTempFile("Quarterly Stewardship Report.pdf");
			PdfWriter writer = new PdfWriter(file);
		    PdfDocument pdfDoc= new PdfDocument(writer);
		    Document doc = new Document(pdfDoc); 
		    Text text1 = new Text("Quarterly Stewardship Report");
		    text1.setBold();
		    Paragraph para1 = new Paragraph(text1);
            doc.add(para1);
            doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));
		
            Table tablex = new Table(new float[] {5f, 5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    String[] headersx = {"Report Due Date","For the Quarter Ended"};
		    for (int col = 0; col < 2; col++) {
	            tablex.addCell(new Cell().add(new Paragraph(headersx[col])).setBold().setBorder(null));
	            
	        }
		    doc.add(tablex);
		    String[][] cellContentx =	{
		    		{dueDate,formDate}
             };
		    
		    for (int row = 0; row < 1; row++) {
		        for (int col = 0; col < 2; col++) {
		            tablex.addCell(new Cell().add(new Paragraph(cellContentx[row][col])).setBorder(null));
		            
		        }
		        }
		    tablex.complete();
            
            
            
		    Text text5 = new Text("\n  Company Name: "+companies).setBold();
		    Paragraph para2 = new Paragraph(text5);
		    Text text6 = new Text("\n \n To, \n National Pension System Trust,\n Tower B, B-302, Third Floor, \n World Trade Center,\nNauroji Nagar,\n New Delhi-110029\n");		   
		    para2.add(text6); 
		    Text text8 = new Text("\n Sir, \n \n In my opinion and to the best of my information,I report the following in respect of the quarter mentioned above: \n \n");
		     para2.add(text8);
		     doc.add(para2);
		     int colsize=4;
		    
		   //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		     
		     try {
		            // Convert strings to Date objects
		        /*    Date reportDueDate = sdf.parse(dueDate);
		            Date cutoffDate = sdf.parse("2025-03-27");
		            log.info("reportDueDate is"+ reportDueDate);
		    */
		    Table table1 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    String[] headers1 = {"S.No", "Parameters","Yes/No/NA", "PFM Remarks","NPST Remarks"};
		   
		    for (int col = 0; col < colsize; col++) {
	            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
	            
	        }
		    doc.add(table1);
		  //  if (reportDueDate.before(cutoffDate))  {
		    if (adversealert != null && !adversealert.trim().isEmpty()) {
		    String[][] cellContent1 =	{
		    		{"1.","Did any *conflict of interest situation occurred during the quarter? \n \n *Refer Schedule VI-Code of Conduct of PFRDA (Pension Fund) Regulations, 2015. \n \n (As per principle 2, institutional investor should have a policy on how they manage conflicts of interest situation in fulfilling their stewardship responsibilities and publicly disclose it.\r\n" + 
		    				"\r\n" + 
		    				" The policy has to address the identification of possible situations where conflict of interest may arise and procedure in case such a situation arises.)(Details to be provided in Annexure A) ",conflict,conflictRem1},
		    		{"2.","Did any monitoring situation occur during the quarter in respect of any investee company for Equity or Debt investments? \n (As per principle 3 of common stewardship code issued by PFRDA,\r\n" + 
		    				"\r\n" + 
		    				" institutional investor should have a policy on continuous monitoring of their investee companies in respect of all aspects they consider important.) \n (Details to be provided in Annexure B)",monitoring,monitoringRem1},
		    		{"3.","Did PFM engage with any investee company during the quarter? \n \n (As per principle 4 of common stewardship code issued by PFRDA, institutional investor should have a policy identifying circumstances for active intervention in the investee companies and the manner of such interventions.) \n (Details to be provided in Annexure C)",resolutions,resolutionsRem1},
		    		{"4.","Did any situation occurred during the quarter requiring collaboration with other institutional investors? \n (As per principle 4 of common stewardship code issued by PFRDA, institutional investor should have a policy for collaboration with other institutional investors, to preserve the interest of the ultimate investors.) \n (Details to be provided in Annexure D)",
		    			insInvestorSituation,insInvestorSituationRem1},
		    		{"5.","Was there any adverse alert during the quarter relating to any of the investee company in Pension funds' portfolio? \n (Details to be provided in Annexure E)",adversealert,adversealertRem1},
		    		{"6.","For the resolutions voted during the quarter have you abstained for any of the resolution except for conflict of interest resolutions like common directors, group company etc. If yes, provide details of such resolutions with detailed rationale.\n (Details to be provided in Annexure F)",resolutionsVoted1,resolutionsVotedRem1}
			    		
		    };
		    
		    for (int row = 0; row < 6; row++) {
		        for (int col = 0; col < colsize; col++) {
		            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
		            
		        }
		        }
		    table1.complete();
		     }
		     else {
		    	log.info("inside else");
					
				    String[][] cellContent1 =	{
				    		{"1.","Did any *conflict of interest situation occurred during the quarter? \n \n *Refer Schedule VI-Code of Conduct of PFRDA (Pension Fund) Regulations, 2015. \n \n (As per principle 2, institutional investor should have a policy on how they manage conflicts of interest situation in fulfilling their stewardship responsibilities and publicly disclose it.\r\n" + 
				    				"\r\n" + 
				    				" The policy has to address the identification of possible situations where conflict of interest may arise and procedure in case such a situation arises.)(Details to be provided in Annexure A) ",conflict,conflictRem1},
				    		{"2.","Did any monitoring situation occur during the quarter in respect of any investee company for Equity or Debt investments? \n (As per principle 3 of common stewardship code issued by PFRDA,\r\n" + 
				    				"\r\n" + 
				    				" institutional investor should have a policy on continuous monitoring of their investee companies in respect of all aspects they consider important.) \n (Details to be provided in Annexure B)",monitoring,monitoringRem1},
				    		{"3.","Did PFM engage with any investee company during the quarter? \n \n (As per principle 4 of common stewardship code issued by PFRDA, institutional investor should have a policy identifying circumstances for active intervention in the investee companies and the manner of such interventions.) \n (Details to be provided in Annexure C)",resolutions,resolutionsRem1},
				    		{"4.","Did any situation occurred during the quarter requiring collaboration with other institutional investors? \n (As per principle 4 of common stewardship code issued by PFRDA, institutional investor should have a policy for collaboration with other institutional investors, to preserve the interest of the ultimate investors.) \n (Details to be provided in Annexure D)",
				    			insInvestorSituation,insInvestorSituationRem1},
				    		//{"5.","Was there any adverse alert during the quarter relating to any of the investee company in Pension funds' portfolio? \n (Details to be provided in Annexure E)",adversealert,adversealertRem1},
				    		{"5.","For the resolutions voted during the quarter have you abstained for any of the resolution except for conflict of interest resolutions like common directors, group company etc. If yes, provide details of such resolutions with detailed rationale.\n (Details to be provided in Annexure E)",resolutionsVoted1,resolutionsVotedRem1}
					    		
				    };
				    
				    for (int row = 0; row < 5; row++) {
				        for (int col = 0; col < colsize; col++) {
				            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
				            
				        }
				        }
				    table1.complete();
		     
		     }
		    doc.add(new Paragraph(""));
		    
		     }
		     catch (Exception e) {
		    	 log.error("error while creating table1 "+ e.getMessage()); 
		     }
		    Table table2 = new Table(new float[] {5f, 5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    String[] headers2 = {"Date:","Name"};
		    for (int col = 0; col < 2; col++) {
	            table2.addCell(new Cell().add(new Paragraph(headers2[col])).setBold().setBorder(null));
	            
	        }
		    doc.add(table2);
		    
		    String[] values1 = {date_2,employeeName};
		    for (int col = 0; col < 2; col++) {
	            table2.addCell(new Cell().add(new Paragraph(values1[col])).setBorder(null));
	            
	        }
		    String[] headers3 = {"Place:","Role"};
		    for (int col = 0; col < 2; col++) {
	            table2.addCell(new Cell().add(new Paragraph(headers3[col])).setBold().setBorder(null));
	            
	        }
		    
		    
		    String[] values2 = {place,roles};
		    for (int col = 0; col < 2; col++) {
	            table2.addCell(new Cell().add(new Paragraph(values2[col])).setBorder(null));
	            
	        }
		 
		    table2.complete();
		    
   
		        doc.close();
		        pdfDoc.close();
		        writer.close();
		        log.info("file created successfully");
	}
	
		
		
	
		catch (Exception e)  
		{  
		log.error("error while create PDF "+ e.getMessage()); 
		}
		return file;  
			
		
	}

private static final Log log=LogFactoryUtil.getLog(QuartelyStewardshipReportPdfUtil.class.getName()); 


}
