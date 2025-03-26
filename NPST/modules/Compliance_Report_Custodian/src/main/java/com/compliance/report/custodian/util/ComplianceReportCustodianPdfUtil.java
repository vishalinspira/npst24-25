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
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;

public class ComplianceReportCustodianPdfUtil {
	

		
		public static File ComplianceReportCustodianPDF(long reportuploadlogid,String dueDate,String formDate, String remarks_i_i, String remarks_ii, String remarks_iii, 
				String remarks_iv, String remarks_v, String remarks_vi, String remarks_vii, String remarks_viii, String remarks_ix, String remarks_x, String remarks_xi, String remarks_xii,
				String remarks_xiii,String remarks_xiv,String remarks_xv,String remarks_xvi,String remarks_xvii,String remarks_xviii, String month, String signature, String employeeName, String designation, String date_3, String place)
		{
		File file=null;

		
				try  {  
			    file=FileUtil.createTempFile("Compliance Report Custodian.pdf");
			    PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
			    Text text1 = new Text("Quarterly Compliance Certificates (on the letterhead of custodian)").setBold();
			    Paragraph para1 = new Paragraph(text1);
			    Text text2 = new Text("\n For the Quarter Ended " +  formDate).setBold();
			    para1.add(text2);
			    Text text3 = new Text("\n (To be submitted within 10 days of the end of each calendar quarter)");
			    para1.add(text3);
			    para1.setTextAlignment(TextAlignment.CENTER);
	            doc.add(para1);
	            
	            
	          //  doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));
	      /*      Table tablex = new Table(new float[] {5f, 5f}, true)
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
			    
		*/
			    Text text6 = new Text("\n \n To,\n The Chief Executive Officer  \n National Pension System Trust,\n  B-302, Third Floor, \n Tower B, World Trade Center,\nNauroji Nagar, New Delhi-110029\n\n\n");		   
			    Paragraph para2 = new Paragraph(text6);
			    Text text7 = new Text("Dear Sir/Madam, \n\n In my/our opinion and to the best of my/our information and according to the examinations carried out by me/us and explanations furnished to me/us, I/We certify the following in respect of the quarter mentioned above.\n\n");
			    para2.add(text7); 
			    doc.add(para2);
			     
			    Table table1 = new Table(new float[] {0.5f, 2.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headers1 = {"S.No", "Description", "Remarks","NPS Trust Observations"};
			    for (int col = 0; col < 3; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
		            
		        }
			    doc.add(table1);
				
			    String[][] cellContent1 =	{
			    		{"1.","Whether the custodian is compliant with the eligibility criteria as stipulated in the Regulation 8 of the PFRDA (Custodian of Securities) Regulations, 2015 and subsequent amendments thereof.",remarks_i_i},
			    		//{"","Number of cases where clear funds not received before entering deal and the reasons thereof.",remarks_i_ii},
			    		{"2.", "Whether custodian has requisite authorisation/ POA from Pension Funds.",remarks_ii},
			    		{"3.","Whether upon receipt of instructions and clear funds from the Pension Funds for purchase of securities, the custodian has made the payment and settled the transaction as per the standard settlement process. Please provide details of the deals settled otherwise.",remarks_iii},
			    		{"4.","Whether the custodian has received deal instructions from the Pension Funds as per the agreed procedure? Please provide details of deal instructions received otherwise.",remarks_iv},
			    		{"5.","Whether the custodian has ensured that the individual holdings of securities in the pension scheme accounts are daily reconciled with the depository holdings and Constituents' Subsidiary General Ledger (CGSL) account. Please provide, details of deviations, if any.",remarks_v},
			    		{"6.","Whether the custodian has kept the securities held under NPS Trust segregated from the other securities of the custodian/ other clients",remarks_vi},
			    		{"7.","Whether the custodian has encumbered, pledged, hypothecated or marked any charge or lien on the securities held under NPS, except pursuant to instructions from the Pension Funds and in accordance to guidelines issued by the Authority",remarks_vii},
			    		{"8.","Whether the custodian of securities has informed the issuer of securities in a timely manner, the exemption from income tax available to NPS Trust. Please provide details of TDS deducted, if any, on interest/coupon received.",remarks_viii},
				    	{"9.","Whether the custodian has, as per entitlements/ instructions from Pension Funds collected, received and deposited in the designated NPS account sale proceeds, interest, redemption value, and other corporate actions due on the holdings in respect of the securities under NPS as per the agreed timeline. Please provide details of deviations, if any.",remarks_ix},
				    	{"10.","Whether the custodian has timely informed to the Pension Funds regarding the interest, redemption and other corporate actions due on their respective holdings in respect of the securities under NPS. Please provide details of delay in intimation, if any.",remarks_x},
			    		{"11.","Whether the custodian has furnished to the Pension Funds, scheme-wise holding and  transaction wise details of all purchases and sales of securities relating to the pension scheme accounts at frequencies and timeline as agreed upon ",remarks_xi},
			    		{"12.","Whether the custodian has assigned or delegated its duties/function under NPS to any third party. If yes, please provide details.",remarks_xii},
			    		{"13.","Whether there are securities (equity) held by Pension Funds not forming part of Top 200 stocks published by NPS Trust",remarks_xiii},
			    		{"14.","Whether any grievances/ complaints have been received from the Pension Funds. If yes, please provide details of such grievances/complaints and time taken for their redressal.",remarks_xiv},
			    		{"15.","Whether the invoice raised by the custodian to the Pension Funds for the services rendered by it for the invoicing period is in terms of Regulation 16 of the PFRDA (Custodian of securities) Regulations, 2015 and subsequent amendments thereof and terms of appointment.",remarks_xv},
			    		{"16.","Whether the custodian has complied with the Code of Conduct as specified in the PFRDA (Custodian of securities) Regulations, 2015 and subsequent amendments thereof.",remarks_xvi},
			    		{"17.","Whether the custodian has adhered to the voting policy, cyber security policy and policy on adoption of cloud services issued by the Authority",remarks_xvii},
			    		{"18.","Whether the custodian has taken all measures necessary for prevention of fraud and has developed and implemented a fraud prevention and mitigation policy in accordance with Regulation 19(19) of PFRDA (Custodian of Securities) Regulations, 2015 and subsequent amendments thereof and guidelines issued by the Authority.",remarks_xviii}
			    		
				    	
				    		
			    };
			    
			    for (int row = 0; row < 18; row++) {
			        for (int col = 0; col < 3; col++) {
			            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
			            
			        }
			        }
			    table1.complete();
			   
			    
			  
			    Paragraph para4 = new Paragraph(new Text("\n Note: \n \n").setBold());
			    Text text9 = new Text("1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.\r\n\n" + 
			    	                  "2.This Compliance Certificate(s) shall be put up to the Board on "+ month + " and the remarks related thereto would be forwarded to NPS Trust on subsequently. \r\n\n"+
			    		              "Certified that the Information given, herein are correct and complete to the best of my/our knowledge and belief. \n\n");
			    	                 
			    	
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
