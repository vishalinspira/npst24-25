package com.monthly.compcertificate.util;

import com.itextpdf.io.util.FileUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonthlyCompCertificateCreatePdfUtil {
	
	public static File MonthlyComplianeCertificatePFMPDF(String reportDate,String date_1, String purchase_of_securities, 
			String detailed_investment, String investments_approved, String decision_of_investment, String investments_non_dematerialized, 
			String all_investments_from_funds, String delivery_of_securities_purch, 
			String investment_done_in_ipo, long reportUploadLogId,String Purchase_of_sec_rem_1_1_2,String Purchase_of_sec_rem_1_2a_2,String Purchase_of_sec_rem_1_2b_2,String Purchase_of_sec_rem_1_2c_2,
			String Purchase_of_sec_rem_1_2d_2,String Purchase_of_sec_rem_1_2e_2,String Purchase_of_sec_rem_1_3_2,String Purchase_of_secu_rem_1_4_2,String Securities_held_2_1_2,
			String Securities_held_2_2a_2,String Securities_held_2_2b_2,String Securities_held_2_2c_2,String Securities_held_2_3_2,String Securities_held_2_4_2,String Securities_held_2_5_2,String Securities_held_2_6_2,
			String Sale_of_securities_3_1a_2,String Sale_of_securities_3_1b_2,String Sale_of_securities_3_2_2,String Reports_and_Disclosure_4_1a_2,String Reports_and_Disclosure_4_1b_2,
			String Reports_and_Disclosure_4_1c_2,String Reports_and_Disclosure_4_1d_2,String Reports_and_Disclosure_4_1e_2,String Reports_and_Disclosure_4_2a_2,
			String Reports_and_Disclosure_4_2b_2,String Disclosure_requirements_5_1a_2,String Disclosure_requirements_5_1b_2,String Disclosure_requirements_5_1c_2,
			String Disclosure_requirements_5_1d_2,String Disclosure_requirements_5_1e_2,String Disclosure_requirements_5_1f_2,String scheme_investments, 
			String stop_loss_trigger, String decision_approved_by_committee, String decision_properly_documented, String inter_scheme_transfer, 
			String investment_held_in_equity, String investment_in_equity_shares,String active_passive_breaches, String disinvestments_approved, 
			String decision_of_disinvestment, String delivery_of_securities_sale,String all_investment_charges, 
			String pfm_adhered, String records_of_the_audit_of_nav, String scheme_wise_nav_uploaded, String scheme_wise_nav_submitted, 
			String monthly_reports_submitted, String scrip_wise_details,String scheme_wise,String scheme_wise_daily,String periodic_reports_monthly, String scrip_wise_details_portfolio,
			String pension_fund_published, String pension_fund_disclosed,String date_2, String company_name, String emplolyee_name, 
			String roles, Date date_3, String place,
			String purchaseOfSecuritiesRemarks112_NPST,String purchaseOfSecuritiesRemarks12a2_NPST,String purchaseOfSecuritiesRemarks12b2_NPST,String purchaseOfSecuritiesRemarks12c2_NPST,
			String purchaseOfSecuritiesRemarks12d2_NPST,String purchaseOfSecuritiesRemarks12e2_NPST,String purchaseOfSecuritiesRemarks132_NPST,	String purchaseOfSecuritiesRemarks142_NPST,
			String securitiesHeld212_NPST,String securitiesHeld22a2_NPST,String securitiesHeld22b2_NPST,String securitiesHeld22c2_NPST,String securitiesHeld232_NPST,
			String securitiesHeld242_NPST,String securitiesHeld252_NPST,String securitiesHeld262_NPST,String saleOfSecurities31a2_NPST,String saleOfSecurities31b2_NPST,String saleOfecurities322_NPST,
			String reports_andDisclosures41a2_NPST,String reportsAndDisclosures41b2_NPST ,String reportsAndDisclosures41c2_NPST,String reportsAndDisclosures41d2_NPST,
			String reportsAndDisclosures41e2_NPST,String reportsAndDisclosures42a2_NPST,String reportsAndDisclosures42b2_NPST,String disclosureRequirement51a2_NPST,
			String disclosureRequirement51b2_NPST, String disclosureRequirement51c2_NPST, String disclosureRequirement51d2_NPST, String disclosureRequirement51e2_NPST, String disclosureRequirement51f2_NPST
			) {

		File file=null;
		
		try  {  
			 file=FileUtil.createTempFile("pdf");
		
			   PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
                 Text text1 = new Text("Monthly Compliance Certificate");
			    text1.setBold();
			    Paragraph para1 = new Paragraph(text1);
			    Text text4 = new Text("\n For the Month Ended " + date_1);  
			    para1.add(text4.setBold());
			    para1.setTextAlignment(TextAlignment.CENTER);
			    doc.add(para1);
			
			    /*    Text text2 = new Text("\n\n Report Due Date");
			    para1.add(text2);
			    Text text3 = new Text("\n "+reportDate);
			    para1.add(text3);
			    */
			    
			 //   Text text5 = new Text("\n "+date_1);
			   // para1.add(text5);

			    
			    
			    Text textcomp = new Text("\n  Company Name: "+company_name);
			    Paragraph para2 = new Paragraph(textcomp);
			    
			    
			    Text text6 = new Text("\n \n To,\n The Chief Executive Officer \n National Pension System Trust \n B-302, Third Floor, \n Tower B, World Trade Center,\nNauroji Nagar, New Delhi-110029\n");		   
			    para2.add(text6);   
			    doc.add(para2);
			    int colsize=4;
			    Text text8 = new Text("\n Sir, \n \n In my opinion and to the best of my information and according to the examinations"
			    		+ " carried out by me and explanations furnished to me, I certify"
			    		+ " the following in respect of the month mentioned above \n \n");
			    Paragraph para3 = new Paragraph(text8);
			    doc.add(para3);
			    
			    Table table1 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headers1 = {"S.No", "Parameters","Yes/No/NA", "PFM Remarks","NPST Remarks"};
			    for (int col = 0; col < colsize; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
		            
		        }
			    doc.add(table1);
				Cell cell1 = new Cell(1, colsize)
			            .add(new Paragraph("1 . Purchase of securities")).setBold();
			    table1.addCell(cell1);
			    String[][] cellContent1 =	{
			    		{"1.1","Whether purchase of securities adhere to the Investment guidelines issued by PFRDA. (prescribed securities/ percentage/ limit/ prudential & exposure norms) Details of deviations provided in annexure A.",
			    			purchase_of_securities,Purchase_of_sec_rem_1_1_2,purchaseOfSecuritiesRemarks112_NPST},
			    		{"1.2","(a) Whether a detailed Investment process manual is prepared and approved by Board / board delegated authority and whether actual process is established as per approved manual.",
			    				detailed_investment,Purchase_of_sec_rem_1_2a_2,purchaseOfSecuritiesRemarks12a2_NPST},
			    		{" ","(b) Whether investments are approved by the committee/competent authority as per Approval delegation matrix",
			    					investments_approved,Purchase_of_sec_rem_1_2b_2,purchaseOfSecuritiesRemarks12b2_NPST},
			    		{" ","(c) Whether each decision of investment is properly documented and record is maintained at individual transaction level. (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)",
			    						decision_of_investment,Purchase_of_sec_rem_1_2c_2,purchaseOfSecuritiesRemarks12c2_NPST},
			    		{" ","(d) Whether investments for non-dematerialized securities are supported by certificates/ statements.",
			    							investments_non_dematerialized,Purchase_of_sec_rem_1_2d_2,purchaseOfSecuritiesRemarks12d2_NPST},
			    		{" ","(e) Whether all investments from funds received under NPS schemes are held in the name of NPS Trust",
			    								all_investments_from_funds,Purchase_of_sec_rem_1_2e_2,purchaseOfSecuritiesRemarks12e2_NPST},
			    		{"1.3","Whether delivery of securities is taken immediately on purchase as per settlement cycle/ terms for each transaction. \n Details of exceptions to normal settlement procedure such as hand delivery, short delivery, trade reversal etc. provided in Annexure B.",
			    									delivery_of_securities_purch,Purchase_of_sec_rem_1_3_2,purchaseOfSecuritiesRemarks132_NPST},
			    		{"1.4","Whether any application/investment is done in Initial Public Offer (IPO), Follow on Public Offer (FPO) and/or Offer for sale (OFS) during the period? \r\n" + 
			    			
			    				"Details of Investments provided in Annexure C.",
			    				investment_done_in_ipo,Purchase_of_secu_rem_1_4_2,purchaseOfSecuritiesRemarks142_NPST}
			    		
			    };
			    
			    for (int row = 0; row < 8; row++) {
			        for (int col = 0; col < colsize; col++) {
			            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
			            
			        }
			        }
			    table1.complete();
			    doc.add(new Paragraph(""));
			    
			    
			    Table table2 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table2);
				Cell cell2 = new Cell(1, colsize)
			            .add(new Paragraph("2 . Securities held")).setBold();
			    table2.addCell(cell2);
			    
			    String[][] cellContent2 =	{
			    		{"2.1","Whether scheme investments adhere to the Investment guidelines issued by PFRDA. (prescribed securities/ percentage/ limit/ prudential & exposure norms)\r\n" + 
			    				"Details of deviations provided in annexure A.",
			    				scheme_investments,Securities_held_2_1_2,securitiesHeld212_NPST},
			    		{"2.2","a) Whether stop loss/ any other review trigger has occurred for any security (equity/debt/alternate) during the month as per Investment policy of the Pension Fund.",
			    					stop_loss_trigger,Securities_held_2_2a_2,securitiesHeld22a2_NPST},
			    		{" ","b) Whether decision in such a scenario is approved by the committee/competent authority as per Approval delegation matrix",
			    						decision_approved_by_committee,Securities_held_2_2b_2,securitiesHeld22b2_NPST},
			    		{" ","c) Whether each decision along with rationale is properly documented and record is maintained at individual scrip level.\n (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)\r\n" +  
			    				"Details of stop loss/review triggered during the month and its decision provided in Annexure D.",
			    				decision_properly_documented,Securities_held_2_2c_2,securitiesHeld22c2_NPST},
			    		{"2.3","Whether inter-scheme transfer of securities complies with Investment Guidelines issued by PFRDA.\r\n" + 
			    				"Details of inter scheme transfer provided in Annexure E.",
			    				inter_scheme_transfer,Securities_held_2_3_2,securitiesHeld232_NPST},
			    		{"2.4","Whether investment is held in any equity shares of a body corporate which is not listed in top 200 list of stocks prepared by NPS Trust and is pending for rebalancing. If yes, whether the decision to hold such stocks has been approved by the investment committee and informed to the Board of Pension Fund\r\n" +  
			    				"Details provided in Annexure F",
			    				investment_held_in_equity,Securities_held_2_4_2,securitiesHeld242_NPST},
			    		{"2.5","Whether investment in any equity shares through IPO/FPO/OFS does not fulfil the market capitalization condition prescribed under investment guidelines post listing.\r\n" + 
			    				"Details provided in Annexure G.",
			    				investment_in_equity_shares,Securities_held_2_5_2,securitiesHeld252_NPST},
			    		{"2.6","Whether the Pension Fund has segregated the deviations/breaches into active and passive as per PFRDA Circular No. PFRDA/2022/04/SUP-PF/01 dated 04.02.2022 and regularized the active breaches/deviations immediately within 3 business days from the date of active deviation happened.\r\n" + 
			    				"Details provided in Annexure H.",
			    				active_passive_breaches,Securities_held_2_6_2,securitiesHeld262_NPST}
			    		
			    };
			    for (int row = 0; row < 8; row++) {
			        for (int col = 0; col < colsize; col++) {
			            table2.addCell(new Cell().add(new Paragraph(cellContent2[row][col])));
			            
			        }
			        }
			    table2.complete();
			    doc.add(new Paragraph(""));
			    
			    Table table3 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table3);
				Cell cell3 = new Cell(1, colsize)
			            .add(new Paragraph("3 . Sale of securities")).setBold();
			    table3.addCell(cell3);
			    String[][] cellContent3 =	{
			    		{"3.1","(a) Whether disinvestments made are approved by the committee/competent authority as per delegation matrix",
			    			disinvestments_approved,Sale_of_securities_3_1a_2,saleOfSecurities31a2_NPST},
			    		{" ","(b) Whether each decision of disinvestment is properly documented and record is maintained at individual transaction level.\n (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)",
			    				decision_of_disinvestment,Sale_of_securities_3_1b_2,saleOfSecurities31b2_NPST},
			    		{"3.2","Whether delivery of securities is given immediately on sale as per settlement cycle/ terms for each transaction. Please provide details of exceptions, if any, to normal settlement procedure such as hand delivery, short delivery, trade reversal etc.",
			    					delivery_of_securities_sale,Sale_of_securities_3_2_2,saleOfecurities322_NPST},
			    };
			    for (int row = 0; row < 3; row++) {
			        for (int col = 0; col < colsize; col++) {
			            table3.addCell(new Cell().add(new Paragraph(cellContent3[row][col])));
			            
			        }
			        }
			    table3.complete();
			    doc.add(new Paragraph(""));
			    
			    Table table4 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table4);
				Cell cell4 = new Cell(1, colsize)
			            .add(new Paragraph("4 . NAV calculations and daily reconciliations ")).setBold();
			    table4.addCell(cell4);
			    String[][] cellContent4 =	{
			    		{"4.1","(a)Whether scheme-wise number of units are tallied with CRA records on daily basis.",
			    			all_investment_charges,Reports_and_Disclosure_4_1a_2,reports_andDisclosures41a2_NPST},
			    		{" ","(b) Whether the securities held in the schemes are tallied with the Custodian records on a daily basis.",
			    				pfm_adhered,Reports_and_Disclosure_4_1b_2,reportsAndDisclosures41b2_NPST},
			    		{" ","(c) Whether all investment charges (Investment management fees, Custody and related charges, SEBI charges, NPS Trust fees etc.) are loaded onto the scheme-wise NAV on a daily basis",
			    					records_of_the_audit_of_nav,Reports_and_Disclosure_4_1c_2,reportsAndDisclosures41c2_NPST},
			    		{" ","(d) Whether the Pension Fund has adhered to instructions of PFRDA to get the scheme-wise NAV verified by the auditors on a daily basis.",
			    						scheme_wise_nav_uploaded,Reports_and_Disclosure_4_1d_2,reportsAndDisclosures41d2_NPST},
			    		{" ","(e) Whether the records of the audit of scheme-wise NAV have been maintained by the pension fund for future inspection. ",
			    							scheme_wise_nav_submitted,Reports_and_Disclosure_4_1e_2,reportsAndDisclosures41e2_NPST}
			    	/*	{"4.2","(a) Whether monthly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the month.",
			    								monthly_reports_submitted,Reports_and_Disclosure_4_2a_2,reportsAndDisclosures42a2_NPST},
			    		{" ","(b) Whether scrip wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.",
			    									scrip_wise_details,Reports_and_Disclosure_4_2b_2,reportsAndDisclosures42b2_NPST}
			    									*/
			    };
			    
			    for (int row = 0; row < 5; row++) {
			        for (int col = 0; col < colsize; col++) {
			            table4.addCell(new Cell().add(new Paragraph(cellContent4[row][col])));
			            
			        }
			        }
			    table4.complete();
 doc.add(new Paragraph(""));
			    
			    Table table5 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table5);
				Cell cell5 = new Cell(1, colsize)
			            .add(new Paragraph("5 . Reports and Disclosure requirements ")).setBold();
			    table5.addCell(cell5);
			    String[][] cellContent5 =	{
			    		{"5.1","(a)Whether scheme-wise NAV (latest & historical) is uploaded daily on the Pension Fund's website within the prescribed time limit.",
			    			scheme_wise,Disclosure_requirements_5_1a_2,disclosureRequirement51a2_NPST},
			    		{" ","(b) Whether scheme-wise NAV is submitted daily to all the CRA's within the prescribed time limit.\r\n"
			    				+ "Instances of delays during the month in uploading NAV on Pension Fund's website and submission to CRA with the reasons provided in Annexure I.",
			    				scheme_wise_daily,Disclosure_requirements_5_1b_2,disclosureRequirement51b2_NPST},
			    		{" ","(c) Whether monthly periodic reports as per schedule V of PFRDA (PF) Regulations, 2015 and subsequent amendments are submitted to NPS Trust within 10 days from the end of the month.",
			    					periodic_reports_monthly,Disclosure_requirements_5_1c_2,disclosureRequirement51c2_NPST},
			    		{" ","(d)Whether scrip wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.",
			    						scrip_wise_details_portfolio,Disclosure_requirements_5_1d_2,disclosureRequirement51d2_NPST},
			    		{" ","(e) Whether the pension fund has published on its website a list of its group companies and those of its sponsor.",
			    							pension_fund_published,Disclosure_requirements_5_1e_2,disclosureRequirement51e2_NPST},
			    		{" ","(f) Whether the pension fund has disclosed the scheme returns in the manner and in the format as available in public domain hosted by National Pension System Trust",
			    								pension_fund_disclosed,Disclosure_requirements_5_1f_2,disclosureRequirement51f2_NPST}
			    		
			    };
			    
			    for (int row = 0; row < 6; row++) {
			        for (int col = 0; col < colsize; col++) {
			            table5.addCell(new Cell().add(new Paragraph(cellContent5[row][col])));
			            
			        }
			        }
			    table5.complete();
			    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			   // String date2 = dateFormat.format(date_2); 
			    
			    Paragraph para4 = new Paragraph(new Text("\n Note: \n \n").setBold());
			    Text text9 = new Text("1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.\r\n" + 
			    		"2. This Compliance Certificate shall be put up to the Board at its ensuing Board Meeting and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.\r\n" + 
			    		"Certified that the information given, herein are correct and complete to the best of my knowledge and belief.");
			    para4.add(text9);
			    Text text_comp = new Text("\n  For: "+company_name);
			    para4.add(text_comp);
			    doc.add(para4);
			    
			    Table table6 = new Table(new float[] {5f, 5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headersx1 = {"Date:","Name"};
			    for (int col = 0; col < 2; col++) {
		            table6.addCell(new Cell().add(new Paragraph(headersx1[col])).setBold().setBorder(null));
		            
		        }
			    doc.add(table6);
			   
			    String strDate = dateFormat.format(date_3); 
			    String[] values1 = {strDate,emplolyee_name};
			    for (int col = 0; col < 2; col++) {
		            table6.addCell(new Cell().add(new Paragraph(values1[col])).setBorder(null));
		            
		        }
			    String[] headersy = {"Place:","Role"};
			    for (int col = 0; col < 2; col++) {
		            table6.addCell(new Cell().add(new Paragraph(headersy[col])).setBold().setBorder(null));
		            
		        }
			       
			    String[] values2 = {place,roles};
			    for (int col = 0; col < 2; col++) {
		            table6.addCell(new Cell().add(new Paragraph(values2[col])).setBorder(null));
		            
		        }
			 
			    table6.complete();
			    
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

private static final Log log=LogFactoryUtil.getLog(MonthlyCompCertificateCreatePdfUtil.class.getName()); 


}
