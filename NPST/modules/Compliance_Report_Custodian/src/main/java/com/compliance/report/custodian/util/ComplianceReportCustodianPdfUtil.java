package com.compliance.report.custodian.util;



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

import java.io.File;
import java.io.IOException;

public class ComplianceReportCustodianPdfUtil {
	

		
		public static File ComplianceReportCustodianPDF(long reportuploadlogid,String dueDate,String formDate, String remarks_i_i, String remarks_i_ii, String remarks_ii, String remarks_iii, 
				String remarks_iv, String remarks_v, String remarks_vi, String remarks_vii, String remarks_viii, String remarks_ix, String remarks_x, String remarks_xi, String remarks_xii,
				String remarks_xiii, String month, String signature, String employeeName, String designation, String date_3, String place)
		{
		File file=null;

		
				try  {  
			    file=FileUtil.createTempFile("Compliance Report Custodian.pdf");
			    PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
			    Text text1 = new Text("Quarterly Compliance Certificate");
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
			    
		
			    Text text6 = new Text("\n \n To, \n National Pension System Trust,\n Tower B, B-302, Third Floor, \n World Trade Center,\nNauroji Nagar,\n New Delhi-110029\n\n\n");		   
			    Paragraph para2 = new Paragraph(text6);
			     doc.add(para2);
			     
			    Table table1 = new Table(new float[] {0.5f, 2.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headers1 = {"S.No", "Description", "Remarks","NPS Trust Observations"};
			    for (int col = 0; col < 3; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
		            
		        }
			    doc.add(table1);
				
			    String[][] cellContent1 =	{
			    		{"1.","Whether custodian received clear funds from PFs on T+0 basis before entering deals.",remarks_i_i},
			    		{"","Number of cases where clear funds not received before entering deal and the reasons thereof.",remarks_i_ii},
			    		{"2.", "Number of cases where deal was not settled by custodian on settlement date and the reasons thereof.",remarks_ii},
			    		{"3.","Number of cases where DIP/DIS was not provided by the PF.",remarks_iii},
			    		{"4.","Details of the cases where the Custodian holding and PF as scheme holding differs.",remarks_iv},
			    		{"5.","Details of the cases where the custodian holding differs from NSDL/CSDL/RBI.",remarks_v},
			    		{"6.","Number of grievances/ complaints received from the PFs and time taken for their redressal.",remarks_vi},
			    		{"7.","Number of cases where corporate actions was late informed to PFs and the reasons thereof.",remarks_vii},
			    		{"8.","Whether custodian has requisites authorization/PoA from PFs.",remarks_viii},
				    	{"9.","Details of securities which are encumbered, pledged, hypothecated or any charge or lien marked.",remarks_ix},
				    	{"10.","Delay in receipt of the interest/redemption value/bonus/corporate actions in respect of the securities belonging to PFs.",remarks_x},
			    		{"11.","Details of TDS deducted on interest/coupon received.",remarks_xi},
			    		{"12.","Details of assignment or delegation of its function related to NPS.",remarks_xii},
			    		{"13.","Securities held by PFs not forming a part of F&O segment.",remarks_xiii}
				    	
				    		
			    };
			    
			    for (int row = 0; row < 14; row++) {
			        for (int col = 0; col < 3; col++) {
			            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
			            
			        }
			        }
			    table1.complete();
			   
			    
			  
			    Paragraph para4 = new Paragraph(new Text("\n Note: \n \n").setBold());
			    Text text9 = new Text("1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.\r\n\n" + 
			    	                  "2.This Compliance Certificate(s) shall be put up to the Board at its ensuing Board Meeting and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.\r\n\n"+
			    		              "remarks related thereto would be forwarded to NPS Trust on subsequently.\n\n");
			    	                 
			    	
			    	para4.add(text9);
			        doc.add(para4);
			        doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));			    
			        
			        Table table13 = new Table(new float[] {5f, 5f}, true)
			                .setWidth(UnitValue.createPercentValue(100));
				    String[] headers13 = {"Name:","Designation:"};
				    for (int col = 0; col < 2; col++) {
			            table13.addCell(new Cell().add(new Paragraph(headers13[col])).setBold().setBorder(null));
			            
			        }
				    doc.add(table13);
				    
				    String[] values1 = {employeeName,designation};
				    for (int col = 0; col < 2; col++) {
			            table13.addCell(new Cell().add(new Paragraph(values1[col])).setBorder(null));
			            
			        }
				    String[] headers14 = {"Date:","Place"};
				    for (int col = 0; col < 2; col++) {
			            table13.addCell(new Cell().add(new Paragraph(headers14[col])).setBold().setBorder(null));
			            
			        }
				    
				    
				    String[] values2 = {date_3,place};
				    for (int col = 0; col < 2; col++) {
			            table13.addCell(new Cell().add(new Paragraph(values2[col])).setBorder(null));
			            
			        }
				 
				    table13.complete();
			        
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

private static final Log log=LogFactoryUtil.getLog(ComplianceReportCustodianPdfUtil.class.getName()); 


}
