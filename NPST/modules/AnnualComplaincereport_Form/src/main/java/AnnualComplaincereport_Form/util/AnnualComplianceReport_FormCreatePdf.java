package AnnualComplaincereport_Form.util;

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
public class AnnualComplianceReport_FormCreatePdf {
	

public static File annualComplianeCertificatePFMPDF(long reportuploadlogid,String dueDate,String companyName,String date_1,String eligibilityia,String eligibilityib,String eligibilityic,String eligibilityid,String eligibilityie,String eligibilityif
		,String eligibilityig,String eligibilityih,String eligibilityii,String eligibilityij,String eligibilityik,String eligibilityil,String eligibilityim,String eligibilityin,String eligibilityio,String eligibilityip,String eligibilityiq
		,String eligibilityir,String eligibilityis,String booka,String bookb,String bookc,String audita,String auditb,String auditc,String stewardshipa,String stewardshipb,String stewardshipc,String othersa,String othersb,
		String othersc,String othersd,String otherse,String othersf,String eligibilityia_rem,String eligibilityib_rem,String eligibilityic_rem,String eligibilityid_rem,String eligibilityie_rem,String eligibilityif_rem,
		String eligibilityig_rem,String eligibilityih_rem,String eligibilityii_rem,String eligibilityij_rem,String eligibilityik_rem,String eligibilityil_rem,String eligibilityim_rem,String eligibilityin_rem,String eligibilityio_rem,
		String eligibilityip_rem,String eligibilityiq_rem,String eligibilityir_rem,String eligibilityis_rem,String booka_rem,String bookb_rem,String bookc_rem,String audita_rem,String auditb_rem,String auditc_rem,String stewardshipa_rem,
		String stewardshipb_rem,String stewardshipc_rem,String othersa_rem,String othersb_rem,String othersc_rem,String othersd_rem,String otherse_rem,String othersf_rem,String meetingdate
		,String employee_name,String roles,String date_2,String place){
	
			File file=null;
			try  {  
			file=FileUtil.createTempFile("Annual Compliance Certificate.pdf");
			   PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
			    Text text1 = new Text("Annual Compliance Certificate");
			    text1.setBold();
			    Paragraph para1 = new Paragraph(text1);
	            doc.add(para1);
	            doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));
			    Table tablex = new Table(new float[] {5f, 5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headersx = {"Report Due Date","For the year Ended"};
			    for (int col = 0; col < 2; col++) {
		            tablex.addCell(new Cell().add(new Paragraph(headersx[col])).setBold().setBorder(null));
		            
		        }
			    doc.add(tablex);
			    String[][] cellContentx =	{
			    		{dueDate,date_1}
	             };
			    
			    for (int row = 0; row < 1; row++) {
			        for (int col = 0; col < 2; col++) {
			            tablex.addCell(new Cell().add(new Paragraph(cellContentx[row][col])).setBorder(null));
			            
			        }
			        }
			    tablex.complete();		 
			    

			    
			    
			    Text text5 = new Text("\n  Company Name: "+companyName).setBold();
			    Paragraph paraAddress = new Paragraph(text5);
			    Text text6 = new Text("\n \n To, \n National Pension System Trust,\n Tower B, B-302, Third Floor, \n World Trade Center,\nNauroji Nagar,\n New Delhi-110029\n");		   
			    paraAddress.add(text6); 
			    Text text8 = new Text("\n In my/our opinion and to the best of my/our information and according to the examinations carried out by me/us and explanations furnished to me/us, I/We certify the following in respect of the quarter mentioned above. \n \n");
			    paraAddress.add(text8);
			     doc.add(paraAddress);
			    
			    Table table1 = new Table(new float[] {0.5f, 3.5f, 0.5f,2f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headers1 = {"S.No", "Parameters","Yes/No/NA", "PFM Remarks"};
			    for (int col = 0; col < 4; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
		            
		        }
			    doc.add(table1);
			    
			    Cell cell1 = new Cell(20,1).add(new Paragraph("1."));
			    Cell cell2 = new Cell(1,3).add(new Paragraph("Eligibility requirement related").setBold());
			    table1.addCell(cell1);
			    table1.addCell(cell2);
			    
			    String[][] cellContent1 =	{
			    		{"a) Whether Sponsor(s) and PFM are complying with the eligibility requirements of PFRDA (PF) regulations 2015 and certificate of registration issued to it by PFRDA.",
			    			eligibilityia,eligibilityia_rem},
			    		{"b) Whether PFM is conducting its activities in accordance with the PFRDA Act, applicable regulations in force and any guidelines, notifications or circulars issued by the Authority along with the operational agreement executed between the NPS Trust and PFM.",
			    				eligibilityib,eligibilityib_rem},
			    		{"c) Whether Sponsor(s) is maintaining minimum Tangible Net-worth as stipulated by PFRDA",
			    					eligibilityic,eligibilityic_rem},
			    		{"d) Whether Sponsor(s) has contributed minimum Tangible Net-worth of the PFM as stipulated by the PFRDA",
			    						eligibilityid,eligibilityid_rem},
			    		{"e) Whether direct or indirect holding by a foreign company in the PFM is in compliance to PFRDA Act, regulations and other communications.",
			    							eligibilityie,eligibilityie_rem},
			    		{"f) Whether annual fee payable to Authority has been paid as specified by the Authority and within the timelines.",
			    								eligibilityif,eligibilityif_rem},
			    		{"g) Whether there is any change in the regulatory license (s) issued to the Sponsor(s). Statement showing current status of the sponsor(s) regulatory licenses is provided in Annexure1",
			    									eligibilityig,eligibilityig_rem},
			    		{"h) Whether there is any change in the name of the PFM or Sponsor(s)",
			    										eligibilityih,eligibilityih_rem},
			    		{"i) Whether sponsor(s) periodically review the activities of the pension fund.(Incase of irregularities sponsor shall immediately report to the Authority.)",
			    											eligibilityii,eligibilityii_rem},
				    	{"j) Whether the sponsor of a pension fund or the pension fund itself hold any equity stake in any other pensionfund regulated by the Authority",
			    												eligibilityij,eligibilityij_rem},
				    	{"k) Whether Sponsor(s) holds directly or indirectly more than permissible stake in CRA, Trustee Bank or Custodian. Statement of sponsor(s) holdingin intermediaries to be provide details in Annexure 2.",
			    													eligibilityik,eligibilityik_rem},
						{"l) Whether PFM has executed such agreements as specified by the Authority in the interest of subscribers with the parties, including other intermediaries, like Investment Management agreement and NDA with NPS Trust, Service contracts such as for custody arrangements and transfer agency of the securities etc., and copy of such agreements have been submitted to NPS Trust.",
			    														eligibilityil,eligibilityil_rem},	
						{"m) Whether PFM has failed to take prior approval from authority of any major change in management or ownership or shareholding pattern or any change in controlling interest of the Sponsor(s) of the pension fund.Statement regarding Net-worth of the Sponsor(s) and PFM is provided in Annexure 3.",
			    															eligibilityim,eligibilityim_rem},
						{"n) Whether the sponsor or pension fund or its principal officer or key management personnel has been convicted by a court forany offence involving moral turpitude, economic offence, securities laws or fraud;",
			    																eligibilityin,eligibilityin_rem},
						{"o) Whether an order of winding up has been passed against the Sponsor(s) or pension fund",
			    																	eligibilityio,eligibilityio_rem},
			    		{"p) Whether Sponsor(s) or PFM or Key promoter has been declared insolvent",
			    																		eligibilityip,eligibilityip_rem},
			    		{"q) Whether any order restraining, prohibiting or debarring the Sponsor(s) or PFM or its principal officer or key management personnel from dealing in securities in the capital market or from accessing the capital market has been passed by any regulatory authority or court",
			    																			eligibilityiq,eligibilityiq_rem},
					    {"r) Whether any order withdrawing or refusing to grant any license or approval to the sponsor or pension fund or its whole timedirector or managing partner which has a bearing on the capital market, has been passed by the concerned financialsector regulator or any other regulatory authority",
			    																				eligibilityir,eligibilityir_rem},
					    {"s) Whether there is any notice served of any action or investigation or other proceedings of any nature whatsoever, against the sponsor orpension fund, or its Chief Executive Officer, any of its directors or employees, or a related group concern, by anygovernmental or statutory authority which would restrain, prohibit or otherwise challenge or impede the performance of obligations as sponsor or pension fund of the pension schemes regulated by theAuthority, and that there isadverse proceedings against it from anyfinancial sector regulator including the RBI, IRDA or SEBI, of a nature that couldadversely affect the ability to provide the services as sponsor or pension fund for the pension schemesregulated bythe Authority;",
			    																					eligibilityis,eligibilityis_rem}
						
			    };
			    
			    for (int row = 0; row < 19; row++) {
			        for (int col = 0; col < 3; col++) {
			            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
			            
			        }
			        }
			    table1.complete();
			    
			    
			    Table table2 = new Table(new float[] {0.5f, 3.5f, 0.5f,2f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table2);
			    Cell cell3 = new Cell(1,4).add(new Paragraph("2. Books of Accounts, Financial statements, Annual and Periodic reports").setBold());
				   // Cell cell2 = new Cell(1,3).add(new Paragraph("Eligibility requirement related").setBold());
				    table2.addCell(cell3);
				    //table1.addCell(cell2);
			    String[][] cellContent2 =	{
			    		
			    		{"","a) Whether PFM has maintained books and records about the operation of pension schemes to ensure compliance with the provisions of the Income-tax Act, the companies Actor under any otherAct in force and in such manner as may be required or called for by the Authority",
			    			booka,booka_rem},
			    		{"","b) Whether PFM has prepared financial statements, annual report in compliance to regulation 19 (1) and schedule VII of PFRDA (PF) Regulations 2015 and PFRDA (Preparation of financial statements and auditors report of schemes under national pension system) guidelines 2012 and subsequent amendments.",
			    				bookb,bookb_rem},
			    		{"","c) Whether PFM has furnished to NPS Trust periodic reports including unaudited provisional financial statements (Balance Sheet, Revenue Account, notes and schedules) of each scheme and annual report within the specified time.",
			    					bookc,bookc_rem}
							       
			    };
			    for (int row = 0; row < 3; row++) {
			        for (int col = 0; col < 4; col++) {
			            table2.addCell(new Cell().add(new Paragraph(cellContent2[row][col])));
			            
			        }
			        }
			    table2.complete();
			    
			    Table table3 = new Table(new float[] {0.5f, 3.5f, 0.5f,2f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table3);
			    Cell cell4 = new Cell(4,1).add(new Paragraph("3. "));
				    Cell cell5 = new Cell(1,3).add(new Paragraph("Audit of Scheme Accounts").setBold());
				    table3.addCell(cell4);
				    table3.addCell(cell5);
				    String[][] cellContent3 =	{
				    		
				    		{"a) Whether PFM has got its scheme accounts audited by the auditor appointed by the NPS Trust within specified timelines.",
				    			audita,audita_rem},
				    		{"b) Whether Audited Annual report and other information have been submitted to NPS Trust after approval of the board of directors of the pension fund, within specified timelines.",
				    				auditb,auditb_rem},
				    		{"c) Whether latest audited annual report has been placed on PFMs website within specified timelines.",
				    					auditc,auditc_rem}
								       
				    };
				    for (int row = 0; row < 3; row++) {
				        for (int col = 0; col < 3; col++) {
				            table3.addCell(new Cell().add(new Paragraph(cellContent3[row][col])));
				            
				        }
				        }
				    
				    
				    table3.complete();
			    
				    Table table4 = new Table(new float[] {0.5f, 3.5f, 0.5f,2f}, true)
			                .setWidth(UnitValue.createPercentValue(100));
				    doc.add(table4);
				    Cell cell6= new Cell(4,1).add(new Paragraph("4. "));
					    Cell cell7 = new Cell(1,3).add(new Paragraph("Stewardship").setBold());
					    table4.addCell(cell6);
					    table4.addCell(cell7);
					    String[][] cellContent4 =	{
					    		
					    		{"a) Whether PFM has complied with circular PFRDA/2018/01/PF/01 on Common Stewardship Code dated 04.05.2018.",
					    			stewardshipa,stewardshipa_rem},
					    		{"b) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust;and",
					    				stewardshipb,stewardshipb_rem},
					    		{"c) Whether, annual voting report has been submitted to the NPS Trust.(Circular PFRDA/2017/17/PF/1 dated 20.04.2017)",
					    					stewardshipc,stewardshipc_rem}
									       
					    };
					    for (int row = 0; row < 3; row++) {
					        for (int col = 0; col < 3; col++) {
					            table4.addCell(new Cell().add(new Paragraph(cellContent4[row][col])));
					            
					        }
					        }
					    
					    
					    table4.complete();
				    
					    Table table5 = new Table(new float[] {0.5f, 3.5f, 0.5f,2f}, true)
				                .setWidth(UnitValue.createPercentValue(100));
					    doc.add(table5);
					    Cell cell8= new Cell(7,1).add(new Paragraph("5. "));
						    Cell cell9 = new Cell(1,3).add(new Paragraph("Others").setBold());
						    table5.addCell(cell8);
						    table5.addCell(cell9);
						    String[][] cellContent5 =	{
						    		
						    		{"a) Whether all interest, dividends or any such accrual income and proceeds of redemption/sale were collected on due dates and promptly credited to the scheme accounts.Statement showing amount of income accrued but not realized as on closing date of the financial year is provided in Annexure 4.",
						    			othersa,othersa_rem},
						    		{"b) Whether any of the core/non-core activities of the PFM, as defined in circular no. PFRDA/2017/30/PF/4 dated 09th October 2017, has been outsourced to a third party service provider by the PFM.Statement showing list of activities outsourced is provided in Annexure 5.",
						    				othersb,othersb_rem},
						    		{"c) Whether comprehensive service level agreements have been executed with outsourcing service providers covering terms of contracts in consonance with to the provisions of PFRDA Act, rules, regulations, guidelines and directions issued by the authority and copies of all such agreements have been submitted to NPS Trust.",
						    					othersc,othersc_rem},
						    		{"d) Incase any award has been passed against the PFM under the Pension fund Regulatory and Development Authority (Redressal of Subscriber Grievance) Regulations, 2015, whether PFM has complied with such award.",
						    						othersd,othersd_rem},
									{"e) Whether PFM has complied with Cyber Security policy for Intermediaries issued vide circular PFRDA/2019/2/REG dated 07.01.2019.",
						    							otherse,otherse_rem},
									{"f) Whether PFM has ensured dissemination of adequate, accurate, explicit and timely information about the investment policies, investment objectives, financial position and general affairs of the scheme to the subscribers in a fairly simple language.",
						    								otherse,othersf_rem}	       
						    };
						    for (int row = 0; row < 6; row++) {
						        for (int col = 0; col < 3; col++) {
						            table5.addCell(new Cell().add(new Paragraph(cellContent5[row][col])));
						            
						        }
						        }
						    
						    
						    table5.complete();
					    
						    Paragraph para2 = new Paragraph(new Text("\n \n Note: \n").setBold());
						    Text text9 = new Text("1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.\r\n" + 		    	        
						    		              "2.This Compliance Certificate(s)n shall be put up to the Board at its meeting to be held on date \r"+
						    		               "and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.");
						    	                  
						    		para2.add(text9);
						            doc.add(para2);	
						            
						            
						            
								    Table table6 = new Table(new float[] {5f, 5f}, true)
							                .setWidth(UnitValue.createPercentValue(100));
								    String[] headersx1 = {"Date:","Name"};
								    for (int col = 0; col < 2; col++) {
							            table6.addCell(new Cell().add(new Paragraph(headersx1[col])).setBold().setBorder(null));
							            
							        }
								    doc.add(table6);
								    
								    String[] values1 = {date_2,employee_name};
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

private static final Log log=LogFactoryUtil.getLog(AnnualComplianceReport_FormCreatePdf.class.getName()); 


}

