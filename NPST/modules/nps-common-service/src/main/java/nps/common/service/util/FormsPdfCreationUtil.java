package nps.common.service.util;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.AnnualCompCertificateScrutiny;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.MnCompCertificateScrutiny;
import com.daily.average.service.model.ScrutinyInputQuarterlyInterval;
import com.daily.average.service.service.MnCompCertificateScrutinyLocalServiceUtil;
import com.itextpdf.io.util.FileUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.UnitValue;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
//import com.liferay.portal.kernel.util.FileUtil;
//import java.io.File;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class FormsPdfCreationUtil {
	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public static File hyCompCertificatePFMPDF(JSONObject jsonObject,List<String> checkboxvalues,User user) {

		File file=null;
		
		try  {  
			 file=FileUtil.createTempFile("Half Yearly Compliance Certificate.pdf");
		log.info("jsonObject : "+jsonObject);
			   PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
			   // Paragraph header = new Paragraph("This is the Header Text");
	            //header.setVerticalAlignment(VerticalAlignment.TOP);
	            //doc.add(header);
			    Text text1 = new Text("Half Yearly Compliance Certificate");
			    text1.setBold();
			    Paragraph para1 = new Paragraph(text1);
			    doc.add(para1);
			    Text text5 = new Text("\n  Company Name: "+CommonRoleService.getCompanyName(user)).setBold();
			    Paragraph para2 = new Paragraph(text5);
			    doc.add(para2);
			    //LineSeparator line = new LineSeparator();
			   // Chunk linebreak = new Chunk(line);
			    //doc.add(linebreak);
			 //   doc.add((IBlockElement) new Chunk(line));

	            
//			    Text text2 = new Text("\n\n Report Due Date");
//			    para1.add(text2);
//			    Text text3 = new Text("\n "+reportDate);
//			    para1.add(text3);
//			    Text text4 = new Text("\n For the Month Ended");
//			    para1.add(text4);
//			    Text text5 = new Text("\n "+year);
//			    para1.add(text5);
//			    doc.add(para1);
//			    
//			    Text text6 = new Text("\n To, \n The Chief \n Executive \n Officer \n NPS Trust \n ");
//			    text6.setBold();
//			    Paragraph para2 = new Paragraph(text6);
//			    Text text7 = new Text("14th Floor, IFCI \n Tower, \n 61, Nehru \n Place, N. Delhi - \n 110019 \n");
//			    para2.add(text7);
//			    doc.add(para2);
//			    int colsize=4;
//			    Text text8 = new Text("\n Sir, \n \n In my opinion and to the best of my information and according to the examinations"
//			    		+ " carried out by me and explanations furnished to me, I certify"
//			    		+ " the following in respect of the month mentioned above \n \n");
//			    Paragraph para3 = new Paragraph(text8);
//			    doc.add(para3);
			    int colsize=5;
			    Table table1 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headers1 = {"S.No", "Parameters","Yes/No/NA", "PFM Remarks","NPST Remarks"};
			    for (int col = 0; col < colsize; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
		            
		        }
			    doc.add(table1);
//				Cell cell1 = new Cell(1, colsize)
//			            .add(new Paragraph("1 . Purchase of securities")).setBold();
//			    table1.addCell(cell1);
			    log.info("call1");
			    String[] cellContent1a = {"1","Whether PFM has submitted copy of half-Yearly unaudited accounts of schemes within one month from the close of each half-year.",
			    			jsonObject.get("1_1").toString(),jsonObject.get("1_rem_1").toString(),jsonObject.get("1_npsrem_1").toString()};
			    log.info("call2"+jsonObject.get("1_1").toString());
			    if(checkboxvalues.contains("1_1")) {
			    	table1=addRow(table1, 5, cellContent1a);}
			    String[] cellContent1b ={"2","Whether PFM has disclosed on its website a copy of half-Yearly unaudited accounts of schemes along with scheme portfolio within one month from the close of each half-year.",
			    				jsonObject.get("2_1").toString(),jsonObject.get("2_rem_1").toString(),jsonObject.get("2_npsrem_1").toString()};
			    if(checkboxvalues.contains("2_1")) {
			    	table1=addRow(table1, 5, cellContent1b);}
			    String[] cellContent1c ={"3","Whether any change in interest of Directors has been submitted to NPS Trust every six months.",
			    					jsonObject.get("3_1").toString(),jsonObject.get("3_rem_1").toString(),jsonObject.get("3_npsrem_1").toString()};
			    if(checkboxvalues.contains("3_1")) {
			    	table1=addRow(table1, 5, cellContent1c);}
			    String[] cellContent1d ={"4 ","Whether PFM has submitted half-yearly certification by the Internal Auditor after it has been considered by the Board of PFM.",
			    						jsonObject.get("4_1").toString(),jsonObject.get("4_rem_1").toString(),jsonObject.get("4_npsrem_1").toString()};
			    if(checkboxvalues.contains("4_1")) {
			    	table1=addRow(table1, 5, cellContent1d);}
			    
//			    for (int row = 0; row < 4; row++) {
//			        for (int col = 0; col < colsize; col++) {
//			            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
//			            
//			        }
//			        }
			    table1.complete();
			    
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

	
	
	
	public static File annualComplianeCertificateNPSPDF(AnnualCompCertificate ancompCert,AnnualCompCertificateScrutiny scrutiny,List<String> checkboxvalues){
		
				File file=null;
				try  {  
				file=FileUtil.createTempFile("Annual Compliance Certificate.pdf");
				   PdfWriter writer = new PdfWriter(file);
				    PdfDocument pdfDoc= new PdfDocument(writer);
				    Document doc = new Document(pdfDoc); 
				    
				    Text text1 = new Text("Annually Compliance Certificate");
				    text1.setBold();
				    Paragraph para1 = new Paragraph(text1);
				    doc.add(para1);
				    Text text5 = new Text("\n  Company Name: "+CommonRoleService.getCompanyName(ancompCert.getCreatedby())).setBold();
				    Paragraph para2 = new Paragraph(text5);
				    doc.add(para2);
				    
//				    Text text1 = new Text("Annual Compliance Certificate");
//				    text1.setBold();
//				    Paragraph para1 = new Paragraph(text1);
//		            doc.add(para1);
//		            doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));
//				    Table tablex = new Table(new float[] {5f, 5f}, true)
//			                .setWidth(UnitValue.createPercentValue(100));
//				    String[] headersx = {"Report Due Date","For the year Ended"};
//				    for (int col = 0; col < 2; col++) {
//			            tablex.addCell(new Cell().add(new Paragraph(headersx[col])).setBold().setBorder(null));            
//			        }
//				    doc.add(tablex);
//				    String[][] cellContentx =	{
//				    		{dueDate,date_1}
//		             };
//				    for (int row = 0; row < 1; row++) {
//				        for (int col = 0; col < 2; col++) {
//				            tablex.addCell(new Cell().add(new Paragraph(cellContentx[row][col])).setBorder(null));   
//				        }
//				        }
//				    tablex.complete();	
//	                Text text4 = new Text("\n\n To, \n The Chief \n Executive \n Officer \n NPS Trust").setBold();
//	                Paragraph para = new Paragraph(text4);
//				    Text text5 = new Text("\n\n Sir,\n\n In my opinion and to the best of my information and according to the examinations carried out by me and explanations furnished to me, I certify the following in respect to the year mentioned above. \n \n");
//				    para.add(text5);
//				    doc.add(para);
				    
//				    Text text5 = new Text("\n  Company Name: "+ancompCert.getC).setBold();
//				    Paragraph para2 = new Paragraph(text5);
//				    doc.add(para2);

				    Table table1 = new Table(new float[] {2.5f, 0.5f,1.5f,1.5f}, true).setWidth(UnitValue.createPercentValue(100));
				    String[] headers1 = {"Parameters","Yes/No/NA", "PFM Remarks","NPST Remarks"};
				    for (int col = 0; col < 4; col++) {
			            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());  
			        }
				    doc.add(table1);
				    
				//    Cell cell1 = new Cell(20,1).add(new Paragraph("1."));
				   // Cell cell2 = new Cell(1,3).add(new Paragraph("Eligibility requirement related").setBold());
				  //  table1.addCell(cell1);
				  //  table1.addCell(cell2);
				    
				    String[] cellContent1a= {"a) Whether Sponsor(s) and PFM are complying with the eligibility requirements of PFRDA (PF) regulations 2015 and certificate of registration issued to it by PFRDA.",
				    		ancompCert.getEligibilityia(),ancompCert.getEligibilityIa_rem(),scrutiny.getEligibilityia_rem()};	
				    if(checkboxvalues.contains("eligibilityIa")) {
				    	table1=addRow(table1, 4, cellContent1a);}
				    String[] cellContent1b=	{"b) Whether PFM is conducting its activities in accordance with the PFRDA Act, applicable regulations in force and any guidelines, notifications or circulars issued by the Authority along with the operational agreement executed between the NPS Trust and PFM.",
				    		ancompCert.getEligibilityib(),ancompCert.getEligibilityIb_rem(),scrutiny.getEligibilityib_rem()};
				    if(checkboxvalues.contains("eligibilityIb")) {
				    	table1=addRow(table1, 4, cellContent1b);}
				    String[] cellContent1c={"c) Whether Sponsor(s) is maintaining minimum Tangible Net-worth as stipulated by PFRDA",ancompCert.getEligibilityic(),ancompCert.getEligibilityIc_rem(),scrutiny.getEligibilityic_rem()};
				    if(checkboxvalues.contains("eligibilityIc")) {
				    	table1=addRow(table1, 4, cellContent1c);}
				    String[] cellContent1d=	{"d) Whether Sponsor(s) has contributed minimum Tangible Net-worth of the PFM as stipulated by the PFRDA",ancompCert.getEligibilityid(),ancompCert.getEligibilityId_rem(),scrutiny.getEligibilityid_rem()};
				    if(checkboxvalues.contains("eligibilityId")) {
				    	table1=addRow(table1, 4, cellContent1d);}
				    String[] cellContent1e={"e) Whether direct or indirect holding by a foreign company in the PFM is in compliance to PFRDA Act, regulations and other communications.",
				    		ancompCert.getEligibilityie(),ancompCert.getEligibilityIe_rem(),scrutiny.getEligibilityie_rem()};
				    if(checkboxvalues.contains("eligibilityIe")) {
				    	table1=addRow(table1, 4, cellContent1e);}
				    String[] cellContent1f={"f) Whether annual fee payable to Authority has been paid as specified by the Authority and within the timelines.",ancompCert.getEligibilityif(),ancompCert.getEligibilityIf_rem(),scrutiny.getEligibilityif_rem()};
				    if(checkboxvalues.contains("eligibilityIf")) {
				    	table1=addRow(table1, 4, cellContent1f);}
				    String[] cellContent1g={"g) Whether there is any change in the regulatory license (s) issued to the Sponsor(s). Statement showing current status of the sponsor(s) regulatory licenses is provided in Annexure1",
				    		ancompCert.getEligibilityig(),ancompCert.getEligibilityIg_rem(),scrutiny.getEligibilityig_rem()};
				    if(checkboxvalues.contains("eligibilityIg")) {
				    	table1=addRow(table1, 4, cellContent1g);}
				    String[] cellContent1h={"h) Whether there is any change in the name of the PFM or Sponsor(s)",ancompCert.getEligibilityih(),ancompCert.getEligibilityIh_rem(),scrutiny.getEligibilityih_rem()};
				    if(checkboxvalues.contains("eligibilityIh")) {
				    	table1=addRow(table1, 4, cellContent1h);}
				    String[] cellContent1i={"i) Whether sponsor(s) periodically review the activities of the pension fund.(Incase of irregularities sponsor shall immediately report to the Authority.)",
				    		ancompCert.getEligibilityii(),ancompCert.getEligibilityIi_rem(),scrutiny.getEligibilityii_rem()};
				    if(checkboxvalues.contains("eligibilityIi")) {
				    	table1=addRow(table1, 4, cellContent1i);}
				    String[] cellContent1j=	{"j) Whether the sponsor of a pension fund or the pension fund itself hold any equity stake in any other pensionfund regulated by the Authority",
				    		ancompCert.getEligibilityij(),ancompCert.getEligibilityIj_rem(),scrutiny.getEligibilityij_rem()};
				    if(checkboxvalues.contains("eligibilityIj")) {
				    	table1=addRow(table1, 4, cellContent1j);}
				    String[] cellContent1k=	{"k) Whether Sponsor(s) holds directly or indirectly more than permissible stake in CRA, Trustee Bank or Custodian. Statement of sponsor(s) holdingin intermediaries to be provide details in Annexure 2.",
				    		ancompCert.getEligibilityik(),ancompCert.getEligibilityIk_rem(),scrutiny.getEligibilityik_rem()};
				    if(checkboxvalues.contains("eligibilityIk")) {
				    	table1=addRow(table1, 4, cellContent1k);}
				    String[] cellContent1l={"l) Whether PFM has executed such agreements as specified by the Authority in the interest of subscribers with the parties, including other intermediaries, like Investment Management agreement and NDA with NPS Trust, Service contracts such as for custody arrangements and transfer agency of the securities etc., and copy of such agreements have been submitted to NPS Trust.",
				    		ancompCert.getEligibilityil(),ancompCert.getEligibilityIl_rem(),scrutiny.getEligibilityil_rem()};	
				    if(checkboxvalues.contains("eligibilityIl")) {
				    	table1=addRow(table1, 4, cellContent1l);}
				    String[] cellContent1m={"m) Whether PFM has failed to take prior approval from authority of any major change in management or ownership or shareholding pattern or any change in controlling interest of the Sponsor(s) of the pension fund.Statement regarding Net-worth of the Sponsor(s) and PFM is provided in Annexure 3.",
				    		ancompCert.getEligibilityim(),ancompCert.getEligibilityIm_rem(),scrutiny.getEligibilityim_rem()};
				    if(checkboxvalues.contains("eligibilityIm")) {
				    	table1=addRow(table1, 4, cellContent1m);}
				    String[] cellContent1n={"n) Whether the sponsor or pension fund or its principal officer or key management personnel has been convicted by a court forany offence involving moral turpitude, economic offence, securities laws or fraud;",
				    		ancompCert.getEligibilityin(),ancompCert.getEligibilityIn_rem(),scrutiny.getEligibilityin_rem()};
				    if(checkboxvalues.contains("eligibilityIn")) {
				    	table1=addRow(table1, 4, cellContent1n);}
				    String[] cellContent1o={"o) Whether an order of winding up has been passed against the Sponsor(s) or pension fund",ancompCert.getEligibilityio(),ancompCert.getEligibilityIo_rem(),scrutiny.getEligibilityio_rem()};
				    if(checkboxvalues.contains("eligibilityIo")) {
				    	table1=addRow(table1, 4, cellContent1o);}
				    String[] cellContent1p=	{"p) Whether Sponsor(s) or PFM or Key promoter has been declared insolvent",ancompCert.getEligibilityip(),ancompCert.getEligibilityIp_rem(),scrutiny.getEligibilityip_rem()};
				    if(checkboxvalues.contains("eligibilityIp")) {
				    	table1=addRow(table1, 4, cellContent1p);}
				    String[] cellContent1q=	{"q) Whether any order restraining, prohibiting or debarring the Sponsor(s) or PFM or its principal officer or key management personnel from dealing in securities in the capital market or from accessing the capital market has been passed by any regulatory authority or court",
				    		ancompCert.getEligibilityiq(),ancompCert.getEligibilityIq_rem(),scrutiny.getEligibilityiq_rem()};
				    if(checkboxvalues.contains("eligibilityIq")) {
				    	table1=addRow(table1, 4, cellContent1q);}
				    String[] cellContent1r=   {"r) Whether any order withdrawing or refusing to grant any license or approval to the sponsor or pension fund or its whole timedirector or managing partner which has a bearing on the capital market, has been passed by the concerned financialsector regulator or any other regulatory authority",
				    		ancompCert.getEligibilityir(),ancompCert.getEligibilityIr_rem(),scrutiny.getEligibilityir_rem()};
				    if(checkboxvalues.contains("eligibilityIr")) {
				    	table1=addRow(table1, 4, cellContent1r);}
				    String[] cellContent1s= {"s) Whether there is any notice served of any action or investigation or other proceedings of any nature whatsoever, against the sponsor orpension fund, or its Chief Executive Officer, any of its directors or employees, or a related group concern, by anygovernmental or statutory authority which would restrain, prohibit or otherwise challenge or impede the performance of obligations as sponsor or pension fund of the pension schemes regulated by theAuthority, and that there isadverse proceedings against it from anyfinancial sector regulator including the RBI, IRDA or SEBI, of a nature that couldadversely affect the ability to provide the services as sponsor or pension fund for the pension schemesregulated bythe Authority;",
				    		ancompCert.getEligibilityis(),ancompCert.getEligibilityIs_rem(),scrutiny.getEligibilityis_rem()};
				    if(checkboxvalues.contains("eligibilityIs")) {
				    	table1=addRow(table1, 4, cellContent1s);}
//				    for (int row = 0; row < 19; row++) {
//				        for (int col = 0; col < 3; col++) {
//				            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
//				            
//				        }
//				        }
				    table1.complete();
				    
				    
				    Table table2 = new Table(new float[] { 2.5f, 0.5f,1.5f,1.5f}, true)
			                .setWidth(UnitValue.createPercentValue(100));
				    doc.add(table2);
				  //  Cell cell3 = new Cell(1,4).add(new Paragraph("2. Books of Accounts, Financial statements, Annual and Periodic reports").setBold());
					   // Cell cell2 = new Cell(1,3).add(new Paragraph("Eligibility requirement related").setBold());
					//    table2.addCell(cell3);
					    //table2.addCell(cell2);
				    
					    String[] cellContent2a ={"a) Whether PFM has maintained books and records about the operation of pension schemes to ensure compliance with the provisions of the Income-tax Act, the companies Actor under any otherAct in force and in such manner as may be required or called for by the Authority",
					    		ancompCert.getBooka(),ancompCert.getBookIIa_rem(),scrutiny.getBooka_rem()};
					    if(checkboxvalues.contains("bookIIa")) {
					    	table2=addRow(table2, 4, cellContent2a);}
					    String[] cellContent2b ={"b) Whether PFM has prepared financial statements, annual report in compliance to regulation 19 (1) and schedule VII of PFRDA (PF) Regulations 2015 and PFRDA (Preparation of financial statements and auditors report of schemes under national pension system) guidelines 2012 and subsequent amendments.",
					    		ancompCert.getBookb(),ancompCert.getBookIIb_rem(),scrutiny.getBookb_rem()};
					    if(checkboxvalues.contains("bookIIb")) {
					    	table2=addRow(table2, 4, cellContent2b);}
					    String[] cellContent2c ={"c) Whether PFM has furnished to NPS Trust periodic reports including unaudited provisional financial statements (Balance Sheet, Revenue Account, notes and schedules) of each scheme and annual report within the specified time.",
					    		ancompCert.getBookc(),ancompCert.getBookIIc_rem(),scrutiny.getBookc_rem()};
					    if(checkboxvalues.contains("bookIIc")) {
					    	table2=addRow(table2, 4, cellContent2c);}		       
//				    for (int row = 0; row < 3; row++) {
//				        for (int col = 0; col < 4; col++) {
//				            table2.addCell(new Cell().add(new Paragraph(cellContent2[row][col])));    
//				        }
//				        }
				    table2.complete();
				    
				    Table table3 = new Table(new float[] { 2.5f, 0.5f,1.5f,1.5f}, true)
			                .setWidth(UnitValue.createPercentValue(100));
				    doc.add(table3);
				    //Cell cell4 = new Cell(4,1).add(new Paragraph("3. "));
					  //  Cell cell5 = new Cell(1,3).add(new Paragraph("Audit of Scheme Accounts").setBold());
					    //table3.addCell(cell4);
					    //table3.addCell(cell5);

					   String[] cellContent3a ={"a) Whether PFM has got its scheme accounts audited by the auditor appointed by the NPS Trust within specified timelines.",
							   ancompCert.getAudita(),ancompCert.getAudita_rem(),scrutiny.getAudita_rem()};
					   if(checkboxvalues.contains("audita")) {
					    	table3=addRow(table3, 4, cellContent3a);}
					  String[] cellContent3b =	{"b) Whether Audited Annual report and other information have been submitted to NPS Trust after approval of the board of directors of the pension fund, within specified timelines.",
							  ancompCert.getAuditb(),ancompCert.getAuditb_rem(),scrutiny.getAuditb_rem()};
					  if(checkboxvalues.contains("auditb")) {
					    	table3=addRow(table3, 4, cellContent3b);}
					  String[] cellContent3c = {"c) Whether latest audited annual report has been placed on PFMs website within specified timelines.",ancompCert.getAuditc(),ancompCert.getAuditc_rem(),scrutiny.getAuditc_rem()};
					  if(checkboxvalues.contains("auditc")) {
					    	table3=addRow(table1, 4, cellContent3c);}
//					    for (int row = 0; row < 3; row++) {
//					        for (int col = 0; col < 3; col++) {
//					            table3.addCell(new Cell().add(new Paragraph(cellContent3[row][col])));   
//					        }
//					        }
					    
					    
					    table3.complete();
				    
					    Table table4 = new Table(new float[] { 2.5f, 0.5f,1.5f,1.5f}, true)
				                .setWidth(UnitValue.createPercentValue(100));
					    doc.add(table4);
					    //Cell cell6= new Cell(4,1).add(new Paragraph("4. "));
						  //  Cell cell7 = new Cell(1,3).add(new Paragraph("Stewardship").setBold());
						   // table4.addCell(cell6);
						   // table4.addCell(cell7);

						   String[] cellContent4a =	{"a) Whether PFM has complied with circular PFRDA/2018/01/PF/01 on Common Stewardship Code dated 04.05.2018.",ancompCert.getStewardshipa(),ancompCert.getStewardshipa_rem(),scrutiny.getStewardshipa_rem()};
						   if(checkboxvalues.contains("stewardshipa")) {
						    	table4=addRow(table4, 4, cellContent4a);}
						   String[] cellContent4b = {"b) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust;and",ancompCert.getStewardshipb(),ancompCert.getStewardshipb_rem(),scrutiny.getStewardshipb_rem()};
						   if(checkboxvalues.contains("stewardshipb")) {
						    	table4=addRow(table4, 4, cellContent4b);}
						   String[] cellContent4c = 	{"c) Whether, annual voting report has been submitted to the NPS Trust.(Circular PFRDA/2017/17/PF/1 dated 20.04.2017)",
								   ancompCert.getStewardshipc(),ancompCert.getStewardshipc_rem(),scrutiny.getStewardshipc_rem()};
						   if(checkboxvalues.contains("stewardshipc")) {
						    	table4=addRow(table4, 4, cellContent4c);}
//						    for (int row = 0; row < 3; row++) {
//						        for (int col = 0; col < 3; col++) {
//						            table4.addCell(new Cell().add(new Paragraph(cellContent4[row][col])));    
//						        }
//						        }
						    
						    
						    table4.complete();
					    
						    Table table5 = new Table(new float[] { 2.5f, 0.5f,1.5f,1.5f}, true)
					                .setWidth(UnitValue.createPercentValue(100));
						    doc.add(table5);
						    //Cell cell8= new Cell(7,1).add(new Paragraph("5. "));
							  //  Cell cell9 = new Cell(1,3).add(new Paragraph("Others").setBold());
							    //table5.addCell(cell8);
							    //table5.addCell(cell9);

						String[] cellContent5a = {"a) Whether all interest, dividends or any such accrual income and proceeds of redemption/sale were collected on due dates and promptly credited to the scheme accounts.Statement showing amount of income accrued but not realized as on closing date of the financial year is provided in Annexure 4.",
								ancompCert.getOthersa(),ancompCert.getOthersa_rem(),scrutiny.getOthersa_rem()};
						if(checkboxvalues.contains("othersa")) {
					    	table5=addRow(table5, 4, cellContent5a);}
						String[] cellContent5b ={"b) Whether any of the core/non-core activities of the PFM, as defined in circular no. PFRDA/2017/30/PF/4 dated 09th October 2017, has been outsourced to a third party service provider by the PFM.Statement showing list of activities outsourced is provided in Annexure 5.",
								ancompCert.getOthersb(),ancompCert.getOthersb_rem(),scrutiny.getOthersb_rem()};
						if(checkboxvalues.contains("othersb")) {
					    	table5=addRow(table5, 4, cellContent5b);}
						String[] cellContent5c ={"c) Whether comprehensive service level agreements have been executed with outsourcing service providers covering terms of contracts in consonance with to the provisions of PFRDA Act, rules, regulations, guidelines and directions issued by the authority and copies of all such agreements have been submitted to NPS Trust.",
								ancompCert.getOthersc(),ancompCert.getOthersc_rem(),scrutiny.getOthersc_rem()};
						if(checkboxvalues.contains("othersc")) {
					    	table5=addRow(table5, 4, cellContent5c);}
						String[] cellContent5d={"d) Incase any award has been passed against the PFM under the Pension fund Regulatory and Development Authority (Redressal of Subscriber Grievance) Regulations, 2015, whether PFM has complied with such award.",
								ancompCert.getOthersd(),ancompCert.getOthersd_rem(),scrutiny.getOthersd_rem()};
						if(checkboxvalues.contains("othersd")) {
					    	table5=addRow(table5, 4, cellContent5d);}
						String[] cellContent5e={"e) Whether PFM has complied with Cyber Security policy for Intermediaries issued vide circular PFRDA/2019/2/REG dated 07.01.2019.",
								ancompCert.getOtherse(),ancompCert.getOtherse_rem(),scrutiny.getOtherse_rem()};
						if(checkboxvalues.contains("otherse")) {
					    	table5=addRow(table5, 4, cellContent5e);}
						String[] cellContent5f={"f) Whether PFM has ensured dissemination of adequate, accurate, explicit and timely information about the investment policies, investment objectives, financial position and general affairs of the scheme to the subscribers in a fairly simple language.",
								ancompCert.getOthersf(),ancompCert.getOthersf_rem(),scrutiny.getOthersf_rem()};	       
						if(checkboxvalues.contains("othersf")) {
					    	table5=addRow(table5, 4, cellContent5f);}
//							    for (int row = 0; row < 6; row++) {
//							        for (int col = 0; col < 3; col++) {
//							            table5.addCell(new Cell().add(new Paragraph(cellContent5[row][col])));   
//							        }
//							        }

							    table5.complete();
							    
							    Table table6 = new Table(new float[] { 2.5f, 0.5f,1.5f,1.5f}, true)
						                .setWidth(UnitValue.createPercentValue(100));
							    doc.add(table6);
							    //Cell cell8= new Cell(7,1).add(new Paragraph("5. "));
								  //  Cell cell9 = new Cell(1,3).add(new Paragraph("Others").setBold());
								    //table5.addCell(cell8);
								    //table5.addCell(cell9);

							String[] cellContent6a = {"Annexures 1: Current status of the sponsor(s) regulatory licenses",
									"","",scrutiny.getAnnexurei_rem()};
							if(checkboxvalues.contains("annexureI")) {
						    	table6=addRow(table6, 4, cellContent6a);}
							String[] cellContent6b = {"Annexures 2: Sponsor(s) holding in Intermediary",
									"","",scrutiny.getAnnexureii_rem()};
							if(checkboxvalues.contains("annexureII")) {
						    	table6=addRow(table6, 4, cellContent6b);}
							String[] cellContent6c = {"Annexures 3: Statement of Sponsor(s) and PFMs Net-worth",
									"","",scrutiny.getAnnexureiii_rem()};
							if(checkboxvalues.contains("annexureIII")) {
						    	table6=addRow(table6, 4, cellContent6c);}
							String[] cellContent6d = {"Annexures 4: Statement showing amount of income accrued but not realized as on closing date of the financial year.",
									"","",scrutiny.getAnnexureiv_rem()};
							if(checkboxvalues.contains("annexureIV")) {
						    	table6=addRow(table6, 4, cellContent6d);}
							String[] cellContent6e = {"Annexures 1: Current status of the sponsor(s) regulatory licenses",
									"","",scrutiny.getAnnexurev_rem()};
							if(checkboxvalues.contains("annexureIV")) {
						    	table6=addRow(table6, 4, cellContent6e);}
							
							
							
							    
//									    Table table6 = new Table(new float[] {5f, 5f}, true)
//								                .setWidth(UnitValue.createPercentValue(100));
//									    String[] headersx1 = {"Date:","Name"};
//									    for (int col = 0; col < 2; col++) {
//								            table6.addCell(new Cell().add(new Paragraph(headersx1[col])).setBold().setBorder(null)); 
//								        }
//									    doc.add(table6);
//									    String[] values1 = {date_2,employee_name};
//									    for (int col = 0; col < 2; col++) {
//								            table6.addCell(new Cell().add(new Paragraph(values1[col])).setBorder(null));   
//								        }
//									    String[] headersy = {"Place:","Role"};
//									    for (int col = 0; col < 2; col++) {
//								            table6.addCell(new Cell().add(new Paragraph(headersy[col])).setBold().setBorder(null));  
//								        }  
//									    String[] values2 = {place,roles};
//									    for (int col = 0; col < 2; col++) {
//								            table6.addCell(new Cell().add(new Paragraph(values2[col])).setBorder(null));
//								        }
//									    table6.complete();
									    
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

	
	
	
	
		
	/**
	 * 
	 * @param table
	 * @param colSize
	 * @param rowValue
	 * @return
	 */
	private static Table addRow(Table table,int colSize,String[] rowValue) {
		for(int col=0;col<colSize;col++) {
		table.addCell(new Cell().add(new Paragraph(rowValue[col])));
		}
		return table;
	}
	
	
	/**
	 * 
	 * @param inputQuarterlyInterval
	 * @param scrutinyInputQuarterlyInterval
	 * @return
	 */
	public static File QuarterlyComplianeCertificateNPSPDF(InputQuarterlyInterval inputQuarterlyInterval,ScrutinyInputQuarterlyInterval scrutinyInputQuarterlyInterval,List<String> checkboxvalues){
		File file=null;
		try  {  
			 file=FileUtil.createTempFile("Quarterly Compliane Certificate.pdf");
			PdfWriter writer = new PdfWriter(file);
		    PdfDocument pdfDoc= new PdfDocument(writer);
		    Document doc = new Document(pdfDoc); 
		    
//		    Text text1 = new Text("Quarterly Compliance Certificate");
//		    text1.setBold();
//		    Paragraph para1 = new Paragraph(text1);
//            doc.add(para1);
//            doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));
//		    Text text2 = new Text("\n Report Due Date                                                 For the Quarter Ended");
//		    Paragraph para = new Paragraph((text2).setBold());
//		    Text text3 = new Text("\n " +dateFormat.format(inputQuarterlyInterval.getReportDate())+"                                                         "+dateFormat.format(inputQuarterlyInterval.getFormDate()));
//		    para.add(text3);
//		    Text text5 = new Text("\n Compliance Certificate                                        Remarks").setBold();
//		    para.add(text5);
//		    Text text = new Text("\n File Name                                                            Remarks");
//		    para.add(text);
//		    doc.add(para);
//		    
//		    Text text6 = new Text("\n To, \n The Chief \n Executive \n Officer \n NPS Trust \n 14th Floor, IFCI Tower, \n 61, Nehru Place, N. Delhi - 110019 \n");		   
//		    Paragraph para2 = new Paragraph(text6);
//		    
//		    Text text8 = new Text("\n In my/our opinion and to the best of my/our information and according to the examinations carried out by me/us and explanations furnished to me/us, I/We certify the following in respect of the quarter mentioned above. \n \n");
//		     para2.add(text8);
//		     doc.add(para2);
		    Text text5 = new Text("\n  Company Name: "+inputQuarterlyInterval.getCompanies()).setBold();
		    Paragraph para2 = new Paragraph(text5);
		    doc.add(para2);
		    
		    Table table1 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    String[] headers1 = {"S.No", "Parameters","Yes/No/NA", "PFM Remarks","NPST Remarks"};
		    for (int col = 0; col < 5; col++) {
	            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
	            
	        }
		    doc.add(table1);
		    log.debug("table 1  ");
//			Cell cell1 = new Cell(1, 5).add(new Paragraph("1. Management ,Sponser, Net-worth")).setBold();
//		    table1.addCell(cell1);
		    String[] cellContentOneia =	
		    		{"(i)","a) Whether composition of the Board, Investment committee and Risk Management committeeis as per PFRDA (Pension Fund) regulations?",
		    			inputQuarterlyInterval.getOneia(),inputQuarterlyInterval.getOneia_remark(),scrutinyInputQuarterlyInterval.getOneia_rem()};
		    log.info("before table 1  ");
		    if(checkboxvalues.contains("1ia")) {
		    	table1=addRow(table1, 5, cellContentOneia);}
		    	 log.debug("after table 1  ");
		    	 String[] cellContentOneib={"","b) Whether Principal officer and key personnel have been appointed as per PFRDA (Pension Fund) regulations.",
		    			inputQuarterlyInterval.getOneib(),inputQuarterlyInterval.getOneib_remark(),scrutinyInputQuarterlyInterval.getOneib_rem()};
		    if(checkboxvalues.contains("1ib")) {
		    	 table1=addRow(table1, 5,cellContentOneib );}
		    	 String[] cellContentOneiia=	{"(ii)","a) Whether bio-data of all the directors along with their interest in other companies has been filed with the NPS Trust within 15 days of their appointment?",
		    			inputQuarterlyInterval.getOneiia(),inputQuarterlyInterval.getOneiia_remark(),scrutinyInputQuarterlyInterval.getOneiia_rem()};
		    if(checkboxvalues.contains("1iia")) {
		    		 table1=addRow(table1, 5, cellContentOneiia);}
		    	 String[] cellContentOneiib={" ","b) Whether subsequent change in the interest of the directors havebeen filed with the NPS Trust?",
		    			inputQuarterlyInterval.getOneiib(),inputQuarterlyInterval.getOneiib_remark(),scrutinyInputQuarterlyInterval.getOneiib_rem()};
		   if(checkboxvalues.contains("1iib")) {
		    	 table1=addRow(table1, 5,cellContentOneiib );}
		    	 String[] cellContentOneiic={" ","c) Whether minimum 51% of the directors have adequate professional experience in finance and financial services related fields.",
		    			inputQuarterlyInterval.getOneiic(),inputQuarterlyInterval.getOneiic_remark(),scrutinyInputQuarterlyInterval.getOneiic_rem()};
		  if(checkboxvalues.contains("1iic")) {
		    	 table1=addRow(table1, 5, cellContentOneiic);}
		    	 String[] cellContentOneiid={" ","d) Whether change in key personnel has been intimated to the PFRDA within 15days of the occurrence of such change.",
		    			inputQuarterlyInterval.getOneiid(),inputQuarterlyInterval.getOneiid_remark(),scrutinyInputQuarterlyInterval.getOneiid_rem()};
		   if(checkboxvalues.contains("1iid")) {
		    	 table1=addRow(table1, 5, cellContentOneiid);}
		    	 String[] cellContentOneiiia={"(iii)","a) Whether the Sponsor and the Pension Fund(PFM) fulfill and comply with the eligibility requirements as specified under the PFRDA (Pension Fund) Regulations?",
		    			inputQuarterlyInterval.getOneiiia(),inputQuarterlyInterval.getOneiiia_remark(),scrutinyInputQuarterlyInterval.getOneiiia_rem()};
		   if(checkboxvalues.contains("1iiia")) {
		    	 table1=addRow(table1, 5,cellContentOneiiia );}
		    	 String[] cellContentOneiiib={"","b) Whether there is any material change in the information or particulars previously furnished which have a bearing onPFMs certificate of registration?",
		    			inputQuarterlyInterval.getOneiiib(),inputQuarterlyInterval.getOneiiib_remark(),scrutinyInputQuarterlyInterval.getOneiiib_rem()};
				  if(checkboxvalues.contains("1iiib")) {
		    	 table1=addRow(table1, 5, cellContentOneiiib);}
		    	 String[] cellContentOneiva={"(iv) ","a) Whether there is any major change in the management or ownership or shareholding pattern or any change in controlling interest of the Sponsor?",
		    			inputQuarterlyInterval.getOneiva(),inputQuarterlyInterval.getOneiva_remark(),scrutinyInputQuarterlyInterval.getOneiva_rem()};
				  if(checkboxvalues.contains("1iva")) {
		    	 table1=addRow(table1, 5, cellContentOneiva);}
		    	 String[] cellContentOnev={"(v)","Whether PFM is maintaining minimum Tangible Net-worth as stipulated by PFRDA?",
		    			inputQuarterlyInterval.getOnev(),inputQuarterlyInterval.getOnev_remark(),scrutinyInputQuarterlyInterval.getOnev_rem()};
				  if(checkboxvalues.contains("1va")) {
		    	 table1=addRow(table1, 5, cellContentOnev);}

		    table1.complete();
		    doc.add(new Paragraph(""));
		    
		    
		    Table table2 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true).setWidth(UnitValue.createPercentValue(100));
		    doc.add(table2);
//			Cell cell2 = new Cell(1, 5).add(new Paragraph("2. Investment Policy")).setBold();
//		    table2.addCell(cell2);
		    log.debug("table 2  ");
		    String[] cellContentTwoia={"(i)","a) Whether Investment Policy has been drawn in accordance with the investment guidelines approved by the PFRDA and has been approved by the Board of Directors (BOD) of the PFM?",
		    		inputQuarterlyInterval.getTwoia(),inputQuarterlyInterval.getTwoia_remark(),scrutinyInputQuarterlyInterval.getTwoia_rem()};
			  if(checkboxvalues.contains("2ia")) {
		    table2=addRow(table2, 5,cellContentTwoia );}
		    String[] cellContentTwoib={"","b) Whether Investment Policy is being reviewed periodically (minimum half yearly basis) by the PFM?",
		    		inputQuarterlyInterval.getTwoib(),inputQuarterlyInterval.getTwoib_remark(),scrutinyInputQuarterlyInterval.getTwoib_rem()};
			  if(checkboxvalues.contains("2ib")) {
		    table2=addRow(table2, 5, cellContentTwoib);}
		    String[] cellContentTwoic={" ","c) Whether the PFM has submitted details of the Investment Policy reviewed by its board to the NPS Trust within 30days of such review.",
		    		inputQuarterlyInterval.getTwoic(),inputQuarterlyInterval.getTwoic_remark(),scrutinyInputQuarterlyInterval.getTwoic_rem()};
			  if(checkboxvalues.contains("2ic")) {
		    table2=addRow(table2, 5, cellContentTwoic);}

//		    Cell cellp = new Cell(5, 5)
//		            .add(new Paragraph("Please provide the following:\r\n" + 
//		            		"i) Initial BOD approval date\r\n" + 
//		            		"ii) Last BOD review date\r\n" + 
//		            		"iii) Frequency of review in a year as decided by the BOD"));
//		    table2.addCell(cellp);
		    table2.complete();	
		    	
		    
		    Table table = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true).setWidth(UnitValue.createPercentValue(100));
		    doc.add(table);
		    log.debug("table   ");
		    		//String[] cellContentTwoii={"(ii)","Please mention (individually), whether Investment Policy covers the following:","","",""};
		  		  //if(checkboxvalues.contains("2iia"))
		    		//table=addRow(table, 5, cellContentTwoii);
		    		String[] cellContentTwoiiOne={"","i) Prudential norms, Income recognition, Asset classification and Provisioning",
		    			inputQuarterlyInterval.getTwoiione(),inputQuarterlyInterval.getTwoiione_remark(),scrutinyInputQuarterlyInterval.getTwoiione_rem()};
		  		  if(checkboxvalues.contains("2iia")) {
		    		table=addRow(table, 5, cellContentTwoiiOne);}
		    		String[] cellContentTwoiiTwo={"","ii) Sector limits as stipulated in the Investment guidelines",
		    			inputQuarterlyInterval.getTwoiitwo(),inputQuarterlyInterval.getTwoiitwo_remark(),scrutinyInputQuarterlyInterval.getTwoiitwo_rem()};
		  		  if(checkboxvalues.contains("2iib")) {
		    		table=addRow(table, 5,cellContentTwoiiTwo );}
		    		String[] cellContentTwoiiThree={"","iii) Sponsor and Non-Sponsor Group limits as stipulated in the Investment guidelines",
		    			inputQuarterlyInterval.getTwoiithree(),inputQuarterlyInterval.getTwoiithree_remark(),scrutinyInputQuarterlyInterval.getTwoiithree_rem()};
		  		  if(checkboxvalues.contains("2iic")) {
		    		table=addRow(table, 5,cellContentTwoiiThree );}
		    		String[] cellContentTwoiiFOur=   {"","iv) Liquidity and Asset/liability management",
		    			inputQuarterlyInterval.getTwoiifour(),inputQuarterlyInterval.getTwoiifour_remark(),scrutinyInputQuarterlyInterval.getTwoiifour_rem()};
		  		  if(checkboxvalues.contains("2iid")) {
		    		table=addRow(table, 5, cellContentTwoiiFOur);}
		    		String[] cellContentTwoiiFive={"","v) Stop Loss Limits",
		    			inputQuarterlyInterval.getTwoiifive(),inputQuarterlyInterval.getTwoiifive_remark(),scrutinyInputQuarterlyInterval.getTwoiifive_rem()};
		  		  if(checkboxvalues.contains("2iie")) {
		    		table=addRow(table, 5,cellContentTwoiiFive );}
		    		String[] cellContentTwoiiSix=  {"","vi)Broker limit",
		    			inputQuarterlyInterval.getTwoiisix(),inputQuarterlyInterval.getTwoiisix_remark(),scrutinyInputQuarterlyInterval.getTwoiisix_rem()};
		  		  if(checkboxvalues.contains("2iif")) {
		    		table=addRow(table, 5,cellContentTwoiiSix );}
		    		String[] cellContentTwoiiSeven={"","vii) Investment audits",
		    			inputQuarterlyInterval.getTwoiiseven(),inputQuarterlyInterval.getTwoiiseven_remark(),scrutinyInputQuarterlyInterval.getTwoiiseven_rem()};
		  		  if(checkboxvalues.contains("2iig")) {
		    		table=addRow(table, 5,cellContentTwoiiSeven );}
		   
		   
		    table.complete();
		    doc.add(new Paragraph(""));
		    
		   Table table3 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table3);
//			Cell cell3 = new Cell(1, 5).add(new Paragraph("3. Risk Management Policy")).setBold();
//			 log.debug("table 3  ");
//		    table3.addCell(cell3);
		    String[] cellContentThreea={"","a) Whether Risk Management Policy hasbeen drawn in accordance with the guidelines approved by the PFRDA and has been approved by the Board of Directors?",
		    	inputQuarterlyInterval.getThreea(),inputQuarterlyInterval.getThreea_remark(),scrutinyInputQuarterlyInterval.getThreea_rem()};
			  if(checkboxvalues.contains("3ia")) {
		    table3=addRow(table3, 5, cellContentThreea);}
		    String[] cellContentThreeb	={" ","b) Whether Risk Management Policy is being reviewed periodically (minimum half yearly basis) by the PFM?",
		    	inputQuarterlyInterval.getThreeb(),inputQuarterlyInterval.getThreeb_remark(),scrutinyInputQuarterlyInterval.getThreeb_rem()};
			  if(checkboxvalues.contains("3ib")) {
		    table3=addRow(table3, 5, cellContentThreeb);}
		    String[] cellContentThreec={"","c) Whether the PFM has submitted details of the Risk Management Policy reviewed by its board to the NPS Trust?",
		    	inputQuarterlyInterval.getThreec(),inputQuarterlyInterval.getThreec_remark(),scrutinyInputQuarterlyInterval.getThreec_rem()};
			  if(checkboxvalues.contains("3ic")) {
		    table3=addRow(table3, 5, cellContentThreec);}
		    table3.complete();
		    
		    Table table4 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true).setWidth(UnitValue.createPercentValue(100));
		    doc.add(table4);
//			Cell cell4 = new Cell(1, 5).add(new Paragraph("Please mention (individually), whether Risk Management policy covers the following:"));
//		    table4.addCell(cell4);
		    log.debug("table 4 ");

		    String[] cellContentThreeci={"1.","Risk Management functions",
		    		inputQuarterlyInterval.getThreeci(),inputQuarterlyInterval.getThreeci_remark(),scrutinyInputQuarterlyInterval.getThreeci_rem()};
			  if(checkboxvalues.contains("3iia")) {
		    table4=addRow(table4, 5,cellContentThreeci );}
		    String[] cellContentThreecii={"2.","Disaster recovery and business continuity plans",
		    		inputQuarterlyInterval.getThreecii(),inputQuarterlyInterval.getThreecii_remark(),scrutinyInputQuarterlyInterval.getThreecii_rem()};
			  if(checkboxvalues.contains("3iib")) {
		    table4=addRow(table4, 5,cellContentThreecii );}
		    String[] cellContentThreeciii={"3.","Insurance cover against risks",
		    		inputQuarterlyInterval.getThreeciii(),inputQuarterlyInterval.getThreeciii_remark(),scrutinyInputQuarterlyInterval.getThreeciii_rem()};
			  if(checkboxvalues.contains("3iic")) {
		    table4=addRow(table4, 5,cellContentThreeciii );}
		    String[] cellContentThreeciv={"4."," Ensuring risk adjusted returns to subscribers consistent with the protection, safety and liquidity of such funds.",
		    		inputQuarterlyInterval.getThreeciv(),inputQuarterlyInterval.getThreeciv_remark(),scrutinyInputQuarterlyInterval.getThreeciv_rem()};
			  if(checkboxvalues.contains("3iid")) {
		    table4=addRow(table4, 5,cellContentThreeciv );}

		    table4.complete();
		    
		    Table table5 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table5);
//			Cell cell5 = new Cell(1, 5)
//		            .add(new Paragraph("4. Reporting of Investment Deviations")).setBold();
//		    table5.addCell(cell5);
		    
		    String[] cellContentFoura={"","a) Whether the PFM has ensured that all investments are made as per the investment guidelines?",
		    		inputQuarterlyInterval.getFoura(),inputQuarterlyInterval.getFoura_remark(),scrutinyInputQuarterlyInterval.getFoura_rem()};
			  if(checkboxvalues.contains("4a")) {
		    table5=addRow(table5, 5,cellContentFoura );}
		    String[] cellContentFourb={"","b) In case of a deviation (downgrade to a rating not permitted under the regulations for corporate bonds or any other non-compliance in any scheme/asset class post investment), whether the PFM has recorded an internal note justifying its decision to hold such securities where deviation has occurred.",
		    		inputQuarterlyInterval.getFourb(),inputQuarterlyInterval.getFourb(),scrutinyInputQuarterlyInterval.getFourb_rem()};
			  if(checkboxvalues.contains("4b")) {
		    table5=addRow(table5, 5,cellContentFourb );}
		    String[] cellContentFourc={"","c) Whether all such investment deviations are being reported to the Investment Committee and Board of the PFM for their approval to continue to remain invested in these securities.",
		    		inputQuarterlyInterval.getFourc(),inputQuarterlyInterval.getFourc_remark(),scrutinyInputQuarterlyInterval.getFourc_rem()};
			  if(checkboxvalues.contains("4c")) {
		    table5=addRow(table5, 5,cellContentFourc );}

//		    Cell cell = new Cell(5, 5)
//		            .add(new Paragraph("(Please provide details of the deviations that occurred in the quarter in Annexure 2 along with details of justification note and Investment Committee & Board approval extracts)"));
//		    table5.addCell(cell);
		   table5.complete();
		    
		    
		    Table table6 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true).setWidth(UnitValue.createPercentValue(100));
		    doc.add(table6);
//			Cell cell6 = new Cell(1, 5).add(new Paragraph("5. Code of Conduct")).setBold();
//		    table6.addCell(cell6);
		    log.debug("table 5  ");

		    String[] cellContentFivei={"(i)","Whether the PFM is engaged in any other business activity except those relating to pension schemes or funds, regulated by the PFRDA.",
		    		inputQuarterlyInterval.getFivei(),inputQuarterlyInterval.getFivei_remark(),scrutinyInputQuarterlyInterval.getFivei_rem()};
			  if(checkboxvalues.contains("5ia")) {
		    table6=addRow(table6, 5,cellContentFivei );}
		    String[] cellContentFiveii={"(ii)","Whether the PFM has ensured that adequate disclosures are made to the PFRDA, the NPS Trust or subscriber in a comprehensible and timely manner.",
		    		inputQuarterlyInterval.getFiveii(),inputQuarterlyInterval.getFivei_remark(),scrutinyInputQuarterlyInterval.getFiveii_rem()};
			  if(checkboxvalues.contains("5ib")) {
				  log.info("5ib");
		    table6=addRow(table6, 5,cellContentFiveii );}
		    String[] cellContentFiveiii={"(iii)","Whether the PFM has ensured that there has not been any misrepresentation or submission of any misleading information to the PFRDA, NPS Trust or subscribers.",
		    		inputQuarterlyInterval.getFiveiii(),inputQuarterlyInterval.getFiveiii_remark(),scrutinyInputQuarterlyInterval.getFiveiii_rem()};
			  if(checkboxvalues.contains("5ic")) {
		    table6=addRow(table6, 5,cellContentFiveiii );}
		    String[] cellContentFiveiv={"(iv)","Whether the PFM has divulged to anybody, either orally or in writing, directly or indirectly any confidential information about the PFRDA, the NPS Trust or subscribers, which has come to its knowledge, without taking prior permission of the PFRDA,the NPS Trust except where such disclosures are required to be made in compliance with any law for the time being in force.",
		    		inputQuarterlyInterval.getFiveiv(),inputQuarterlyInterval.getFiveiv_remark(),scrutinyInputQuarterlyInterval.getFiveiv_rem()};
			  if(checkboxvalues.contains("5id")) {
				  log.info("5id");
		    table6=addRow(table6, 5,cellContentFiveiv );}
		    String[] cellContentFivea={"(v)","a) Whether the PFM has made adequate disclosures of potential areas of conflict of duties or interest to the PFRDA, the NPS Trust or subscribers.",
		    		inputQuarterlyInterval.getFiveva(),inputQuarterlyInterval.getFiveva_remark(),scrutinyInputQuarterlyInterval.getFiveva_rem()};
			  if(checkboxvalues.contains("5iia")) {
		    table6=addRow(table6, 5,cellContentFivea );}
		    String[] cellContentFivevb={"","b) Whether the PFM has established a mechanism to resolve any conflict of interest situation in an equitable manner, which may arise in the conduct of business.",
		    		inputQuarterlyInterval.getFivevb(),inputQuarterlyInterval.getFivevb_remark(),scrutinyInputQuarterlyInterval.getFivevb_rem()};
			  if(checkboxvalues.contains("5iib")) {
		    table6=addRow(table6, 5,cellContentFivevb );}
		    String[] cellContentFivevc={"","c) Whether the PFM is satisfied that there has been no instances of self-dealing or front running or insider trading by any of the directors and Key personnel through their accounts or through their family members, relatives or friends.",
		    		inputQuarterlyInterval.getFivevc(),inputQuarterlyInterval.getFivevc_remark(),scrutinyInputQuarterlyInterval.getFivevc_rem()};
			  if(checkboxvalues.contains("5iic")) {
		    table6=addRow(table6, 5,cellContentFivevc );}	

//	    Cell newcell = new Cell(9, 5)
//		            .add(new Paragraph("Kindly provide list of directors and key personnel with status of submissions of self-declarations in Annexure 3."));
//		    table6.addCell(newcell);
//		  table6.complete();
		    
		  Table table7 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table7);
			
		   
		    String[] cellContentFiveia={"(vi)","a) Whether PFM has promptly informed the PFRDA and the NPS Trust, if there is any change in the registration status or any penal action taken by any Authority or any material change in financials which may adversely affect the interest of the subscribers.",
		    	inputQuarterlyInterval.getFivevia(),inputQuarterlyInterval.getFivevia_remark(),scrutinyInputQuarterlyInterval.getFivevia_rem()};
		    if(checkboxvalues.contains("5iiia")) {
		    table7=addRow(table7, 5,cellContentFiveia );}
		    String[] cellContentFiveib={"","b) Whether the PFM has promptly informed the PFRDA and the NPS Trust about any action, legal proceedings initiated against it in respect of any material breach or non-compliance by it of any law, rules, regulations and directions of the PFRDA or any other regulatory body.",
		    	inputQuarterlyInterval.getFivevib(),inputQuarterlyInterval.getFivevib_remark(),scrutinyInputQuarterlyInterval.getFivevib_rem()};						
		    if(checkboxvalues.contains("5iiib")) {
		    table7=addRow(table7, 5,cellContentFiveib );}
		    table7.complete();
		    
		    Table table8 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table8);
//			Cell cell8 = new Cell(1, 5)
//		            .add(new Paragraph("6. Internal Auditors")).setBold();
//		    table8.addCell(cell8);
		    
		    String[] cellContentSixi={"(i)","Whether appointment of the Internal Auditor and scope of internal audit is as per the Regulations/Guidance note issued by the PFRDA?",
		    		inputQuarterlyInterval.getSixi(),inputQuarterlyInterval.getSixi_remark(),scrutinyInputQuarterlyInterval.getSixi_rem()};
			  if(checkboxvalues.contains("6ia")) {
		    table8=addRow(table8, 5,cellContentSixi );}
		    String[] cellContentSixiia={"(ii)","a) Whether the PFM has produced to the Auditors such books, accounts, records and other documents in its custody or control and furnish such statement and information relating to its activities entrusted to its by the PFRDA, as it or he may require, within such reasonable time may be specified.",
		    		inputQuarterlyInterval.getSixiia(),inputQuarterlyInterval.getSixiia_remark(),scrutinyInputQuarterlyInterval.getSixiia_rem()};
			  if(checkboxvalues.contains("6iia")) {
		    table8=addRow(table8, 5,cellContentSixiia );}
		    String[] cellContentSixiib={"","b) Whether the PFM has allowed Auditor's reasonable access to the premises occupied by it and also extend reasonable facility for examining any books, records, documents and computer data in the possession of the PFM.",
		    		inputQuarterlyInterval.getSixiib(),inputQuarterlyInterval.getSixiib_remark(),scrutinyInputQuarterlyInterval.getSixiib_rem()};
			  if(checkboxvalues.contains("6iib")) {
		    table8=addRow(table8, 5,cellContentSixiib );}
		    String[] cellContentSixiic={"","c)Whether audit observations till previous quarter have been closed and suggestions of PFRDA/NPS Trust thereto have been complied with?",
		    		inputQuarterlyInterval.getSixiic(),inputQuarterlyInterval.getSixiic_remark(),scrutinyInputQuarterlyInterval.getSixiic_rem()};			    		
			  if(checkboxvalues.contains("6iic")) {
		    table8=addRow(table8, 5,cellContentSixiic );}

		    table8.complete();
		    
		    Table table9 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table9);
//			Cell cell9 = new Cell(1, 5)
//		            .add(new Paragraph("7. Related Party engagement / transaction")).setBold();
//		    table9.addCell(cell9);
		   
		    String[] cellContentSevenia={"(i)","a) Whether any transactions or engagement have been carried out by the PFM with a related party except investments of National Pension SystemTrust funds?",
		    		inputQuarterlyInterval.getSevenia(),inputQuarterlyInterval.getSevenia_remark(),scrutinyInputQuarterlyInterval.getSevenia_rem()};
			  if(checkboxvalues.contains("7ia")) {
		    table9=addRow(table9, 5,cellContentSevenia );}
		    String[] cellContentSevenib={"","b) Whether prior permission of the NPS Trust was taken before entering into such engagement/transaction?",
		    		inputQuarterlyInterval.getSevenib(),inputQuarterlyInterval.getSevenib_remark(),scrutinyInputQuarterlyInterval.getSevenib_rem()};
			  if(checkboxvalues.contains("7ib")) {
		    table9=addRow(table9, 5,cellContentSevenib );}
		    String[] cellContentSevenic={"","c) Whether such engagement/transactions have been disclosed to the NPS Trust in its periodic reports.",
		    		inputQuarterlyInterval.getSevenic(),inputQuarterlyInterval.getSevenic_remark(),scrutinyInputQuarterlyInterval.getSevenic_rem()};
			  if(checkboxvalues.contains("7ic")) {
		    table9=addRow(table9, 5,cellContentSevenic );}
		    String[] cellContentSevenid={"","d) Whether such related party engagements / transactions aredone at fair market price?",
		    		inputQuarterlyInterval.getSevenid(),inputQuarterlyInterval.getSevenid_remark(),scrutinyInputQuarterlyInterval.getSevenid_rem()};
		    
			  if(checkboxvalues.contains("7id")) {
		    table9=addRow(table9, 5,cellContentSevenid );}
		    String[] cellContentSevenie={"","e) Whether such transaction is recurring in nature?",
		    		inputQuarterlyInterval.getSevenie(),inputQuarterlyInterval.getSevenie_remark(),scrutinyInputQuarterlyInterval.getSevenie_rem()}	;
			  if(checkboxvalues.contains("7ea")) {
		    table9=addRow(table9, 5,cellContentSevenie );}

		    table9.complete();
		    
		    Table table10 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true).setWidth(UnitValue.createPercentValue(100));
		    doc.add(table10);
//			Cell cell10 = new Cell(1, 5).add(new Paragraph("8. Operations / Data Security / Infrastructure")).setBold();
//		    table10.addCell(cell10);
		    
		    String[] cellContentEeightia={"(i)","a)Whether the PFM has complied with circular no. PFRDA/2017/30/PF/4 dated09.10.2017 onguidelines on outsourcing of activities by the Pension Fund?",
		    		inputQuarterlyInterval.getEightia(),inputQuarterlyInterval.getEightia_remark(),scrutinyInputQuarterlyInterval.getEightia_rem()};
			  if(checkboxvalues.contains("8ia")) {
		    table10=addRow(table10, 5,cellContentEeightia );}
		    String[] cellContentEeightib={"","b) Whether the PFM has complied with the reporting requirements of the circular no. PFRDA/2017/30/PF/4 dated 09.10.2017.",
		    		inputQuarterlyInterval.getEightib(),inputQuarterlyInterval.getEightib_remark(),scrutinyInputQuarterlyInterval.getEightib_rem()};
			  if(checkboxvalues.contains("8ib")) {
		    table10=addRow(table10, 5,cellContentEeightib );}
		    String[] cellContentEeightii={"(ii)","Whether all investments are held in the name of NPS Trust?",
		    		inputQuarterlyInterval.getEightii(),inputQuarterlyInterval.getEightii_remark(),scrutinyInputQuarterlyInterval.getEightii_rem()};
			  if(checkboxvalues.contains("8iia")) {
		    table10=addRow(table10, 5,cellContentEeightii );}
		    String[] cellContentEeightiii={"(iii)","Whether PFM has pledged or hypothecated or lent any scheme assets which would amount to leverage on schemes portfolio?",
		    		inputQuarterlyInterval.getEightiii(),inputQuarterlyInterval.getEightiii_remark(),scrutinyInputQuarterlyInterval.getEightiii_rem()};
			  if(checkboxvalues.contains("8iiia")) {
		    table10=addRow(table10, 5,cellContentEeightiii );}
		    String[] cellContentEeightiv={"(iv)","Whether all charges/fees debited to the schemes aredeterminedas stipulated by the PFRDA?",
		    		inputQuarterlyInterval.getEightiv(),inputQuarterlyInterval.getEightiv_remark(),scrutinyInputQuarterlyInterval.getEightiv_rem()};
			  if(checkboxvalues.contains("8iva")) {
		    table10=addRow(table10, 5,cellContentEeightiv );}
		    String[] cellContentEeightv={"(v)","Whether all interest,dividendsor any such accrual income and proceeds of redemption/sale were collected on due dates and promptly credited to the scheme accounts?",
		    		inputQuarterlyInterval.getEightv(),inputQuarterlyInterval.getEightv_remark(),scrutinyInputQuarterlyInterval.getEightv_rem()};
			  if(checkboxvalues.contains("8va")) {
		    table10=addRow(table10, 5,cellContentEeightv );}
		    String[] cellContentEeightvia={"(vi)","a) Whether the PFM has taken adequate and necessary steps to ensure that the data and records pertaining to its activities are maintained and are intact.",
		    		inputQuarterlyInterval.getEightvia(),inputQuarterlyInterval.getEightvia_remark(),scrutinyInputQuarterlyInterval.getEightvia_rem()};
			  if(checkboxvalues.contains("8via")) {
		    table10=addRow(table10, 5,cellContentEeightvia );}
		    String[] cellContentEeightvib={"","b) Whether the PFM has ensured that for electronic records and data, up-to-date backup is always available with it.",
		    		inputQuarterlyInterval.getEightvib(),inputQuarterlyInterval.getEightvib_remark(),scrutinyInputQuarterlyInterval.getEightvib_rem()};
			  if(checkboxvalues.contains("8vib")) {
		    table10=addRow(table10, 5,cellContentEeightvib );}
		    String[] cellContentEeightviia={"(vii)","a) Whether the PFM has maintained adequate infrastructure facilities to be able to discharge its services to the satisfaction of the PFRDA, the NPS Trust.",
		    		inputQuarterlyInterval.getEightviia(),inputQuarterlyInterval.getEightviia_remark(),scrutinyInputQuarterlyInterval.getEightviia_rem()};
			  if(checkboxvalues.contains("8viia")) {
		    table10=addRow(table10, 5,cellContentEeightviia );}
		    String[] cellContentEeightviib={"","b) Whether the operating procedures and systems of the PFM are well documented and backed by operation manuals.",
		    		inputQuarterlyInterval.getEightviib(),inputQuarterlyInterval.getEightviib_remark(),scrutinyInputQuarterlyInterval.getEightviib_rem()};
			  if(checkboxvalues.contains("8viib")) {
		    table10=addRow(table10, 5,cellContentEeightviib );}
		    String[] cellContentEeightviii={"(viii)","Whether the PFM has informed the entities in which investment of NPS funds have been made that interest received on the said investment is not liable for deduction of tax at source under the Income Tax Act, 1961",
		    		inputQuarterlyInterval.getEightviii(),inputQuarterlyInterval.getEightviii_remark(),scrutinyInputQuarterlyInterval.getEightviii_rem()};
			  if(checkboxvalues.contains("8viiia")) {
		    table10=addRow(table10, 5,cellContentEeightviii );}
			  
			  
			  String[] cellContentEeightix={"(ix)","In case any Income tax has been deducted on the investment of NPS funds made by PFM, whether PFM has collected refund of such tax deducted within the same financial year.\r\n"+"In case any Income Tax has been deducted on the investment of NPS funds and PFM has not obtained the refund of such tax within the same financial year at its own cost and expense, the PFM fund shall reimburse the NPS Trust, of the said amounts, being deducted as tax, upon being notified by the NPS Trust.",
					  inputQuarterlyInterval.getEightix(),inputQuarterlyInterval.getEightix_remark(),scrutinyInputQuarterlyInterval.getEightix_rem()};
				  if(checkboxvalues.contains("8ix")) {
			    table10=addRow(table10, 5,cellContentEeightix );}
			    String[] cellContentEeightx={"(x)","Whether proper amount of tax has been deducted and deposited on payment of investment management fees and the custodian fees by the PFM on behalf of NPS Trust and within the prescribed timelines.",
			    		inputQuarterlyInterval.getEightx(),inputQuarterlyInterval.getEightx_remark(),scrutinyInputQuarterlyInterval.getEightx_rem()};
				  if(checkboxvalues.contains("8x")) {
			    table10=addRow(table10, 5,cellContentEeightx );}
				  
				  


			  
			  
		   
		    table10.complete();
		    
		    
		    Table table11 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table11);
//			Cell cell11 = new Cell(1, 5)
//		            .add(new Paragraph("9. Brokers empanelment:")).setBold();
//		    table11.addCell(cell11);
		    
		    String[] cellContentNinea={"","* Whether Brokers empanelment is done in accordance to the guidelines issued by the PFRDA?",
		    		inputQuarterlyInterval.getNinea(),inputQuarterlyInterval.getNinea_remark(),scrutinyInputQuarterlyInterval.getNinea_rem()};
			  if(checkboxvalues.contains("9ia")) {
		    table11=addRow(table11, 5,cellContentNinea );}
		    String[] cellContentNineb={"","* Whether prescribed limit of percentage of transactions through any single broker is complied with?",
		    		inputQuarterlyInterval.getNineb(),inputQuarterlyInterval.getNineb_remark(),scrutinyInputQuarterlyInterval.getNineb_rem()};
			  if(checkboxvalues.contains("9ib")) {
		    table11=addRow(table11, 5,cellContentNineb );}


//			Cell cell12 = new Cell(4, 5).add(new Paragraph("10. Inter-Scheme Investment Parameter")).setBold();
//		    table11.addCell(cell12);
		    String[] cellContentTen={"","Whether all such Inter-Scheme transfers are in conformity with the investment objective of the scheme to which such transfer has been made?",
		    			inputQuarterlyInterval.getTen(),inputQuarterlyInterval.getTen_remark(),scrutinyInputQuarterlyInterval.getTen_rem()};	    		    		
			  if(checkboxvalues.contains("10ia")) {
		    table11=addRow(table11, 5,cellContentTen );}
		    table11.complete();
		    
		    Table table12 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true).setWidth(UnitValue.createPercentValue(100));
		    doc.add(table12);
//			Cell cell13 = new Cell(1, 5).add(new Paragraph("11. Voting Obligation")).setBold();
//		    table12.addCell(cell13);
		    
		    String[] cellContentElevena={"","a) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust?",
		    		inputQuarterlyInterval.getElevena(),inputQuarterlyInterval.getElevena_remark(),scrutinyInputQuarterlyInterval.getElevena_rem()};
			  if(checkboxvalues.contains("11ia")) {
		    table12=addRow(table12, 5,cellContentElevena );}
		    String[] cellContentElevenb={"","b)Whether, quarterly voting report has been submitted to the NPS Trust in compliance to Circular PFRDA/2017/17/PF/1 dated 20.04.2017?",
		    		inputQuarterlyInterval.getElevenb(),inputQuarterlyInterval.getElevenb_remark(),scrutinyInputQuarterlyInterval.getElevenb_rem()};
			  if(checkboxvalues.contains("11ib")) {
		    table12=addRow(table12, 5,cellContentElevenb );}
//		    Cell cell14 = new Cell(4, 5).add(new Paragraph("12. Reports and Disclosures as per Schedule V of PFRDA (PF) Regulations")).setBold();
//		    table12.addCell(cell14);

		    String[] cellContentTwelve={"","Whether quarterly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the quarter.",
		    			inputQuarterlyInterval.getTwelve(),inputQuarterlyInterval.getTwelve_remark(),scrutinyInputQuarterlyInterval.getTwelve_rem()};		    		    		
			  if(checkboxvalues.contains("12ia")) {
		    table12=addRow(table12, 5,cellContentTwelve );}
		    table12.complete();
		    
//		    Paragraph para4 = new Paragraph(new Text("\n Note: \n \n").setBold());
//		    log.info("date 3: "+inputQuarterlyInterval.getDate_3());
//		    Text text9 = new Text("1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.\r\n\n" + 
//		    	                  "2.This Compliance Certificate(s)n shall be put up to the Board at its ensuing Board Meeting and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.\r\n\n"+
//		    		              "and the Remarks if any related thereto would be forwarded to NPS Trust subsequently.\r\n\n"+
//		    	                  "Certified that the Information given, herein is correct and complete to the best of my/our knowledge and belief.");
//		    	
//		    		para4.add(text9);
//		    doc.add(para4);
		    
		        doc.close();
		        pdfDoc.close();
		        writer.close();
		        log.info("file created successfully");
	}
	
		
		
	
		catch (Exception e)  
		{  
			e.printStackTrace();
		log.error("error while create PDF "+ e); 
		}
		return file;  
			
		
	}
	
	/**
	 * 
	 * @param mnCompCertificate
	 * @param mnCompCertificateScrutiny
	 * @param checkboxvalues
	 * @return
	 */
	public static File MonthlyComplianeCertificateNPS_PDF(MnCompCertificate mnCompCertificate,MnCompCertificateScrutiny mnCompCertificateScrutiny,List<String> checkboxvalues){
		
		
	File file=null;
		if(Validator.isNull(mnCompCertificateScrutiny)) {
			mnCompCertificateScrutiny =MnCompCertificateScrutinyLocalServiceUtil.createMnCompCertificateScrutiny(0);
		}
		try  {  
			 file=FileUtil.createTempFile("Monthly Compliane Certificate.pdf");
		
			   PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
			    Text text5 = new Text("\n  Company Name: "+mnCompCertificate.getCompany_name()).setBold();
			    Paragraph para2 = new Paragraph(text5);
			    doc.add(para2);
			    Table table1 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headers1 = {"S.No", "Parameters","Yes/No/NA", "PFM Remarks","NPST Remarks"};
			    for (int col = 0; col < 5; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
		            
		        }
			    doc.add(table1);
//				Cell cell1 = new Cell(1, 5)
//			            .add(new Paragraph("1 . Purchase of securities")).setBorder(null).setBold();
//			    table1.addCell(cell1);
			    String[] cellContent11 =	{"1.1","Whether purchase of securities adhere to the Investment guidelines issued by PFRDA. (prescribed securities/ percentage/ limit/ prudential & exposure norms) Details of deviations provided in annexure A (i).",
			    			mnCompCertificate.getPurchase_of_securities(),mnCompCertificate.getPurchase_of_sec_rem_1_1_2(),mnCompCertificateScrutiny.getPurchase_of_securities_rem()};
			    if(checkboxvalues.contains("1.1a")) {
			    	table1=addRow(table1, 5, cellContent11);
			    }
			    String[] cellContent12 =	{"1.2","(a) Whether a detailed Investment process manual is prepared and approved by Board / board delegated authority and whether actual process is established as per approved manual.",
			    				mnCompCertificate.getDetailed_investment(),mnCompCertificate.getPurchase_of_sec_rem_1_2a_2(),mnCompCertificateScrutiny.getDetailed_investment_rem()};
			    if(checkboxvalues.contains("1.2a")) {
			    	table1=addRow(table1, 5, cellContent12);
			    }
			    String[] cellContent13 =	{" ","(b) Whether investments are approved by the committee/competent authority as per Approval delegation matrix",
			    					mnCompCertificate.getInvestments_approved(),mnCompCertificate.getPurchase_of_sec_rem_1_2b_2(),mnCompCertificateScrutiny.getInvestments_approved_rem()};
			    if(checkboxvalues.contains("1.2b")) {
			    	table1=addRow(table1, 5, cellContent13);
			    }
			    String[] cellContent14 =	{" ","(c) Whether each decision of investment is properly documented and record is maintained at individual transaction level. (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)",
			    						mnCompCertificate.getDecision_of_investment(),mnCompCertificate.getPurchase_of_sec_rem_1_2c_2(),mnCompCertificateScrutiny.getDecision_of_investment_rem()};
			    if(checkboxvalues.contains("1.2c")) {
			    	table1=addRow(table1, 5, cellContent14);
			    }
			    String[] cellContent15 =	{" ","(d) Whether investments for non-dematerialized securities are supported by certificates/ statements.",
			    							mnCompCertificate.getInvestments_non_dematerialized(),mnCompCertificate.getPurchase_of_sec_rem_1_2d_2(),mnCompCertificateScrutiny.getInvest_non_dematerialized_rem()};
			    if(checkboxvalues.contains("1.2d")) {
			    	table1=addRow(table1, 5, cellContent15);
			    }
			    String[] cellContent16 =	{" ","(e) Whether all investments from funds received under NPS schemes are held in the name of NPS Trust",
			    								mnCompCertificate.getAll_investments_from_funds(),mnCompCertificate.getPurchase_of_sec_rem_1_2e_2(),mnCompCertificateScrutiny.getAll_investments_from_funds_rem()};
			    if(checkboxvalues.contains("1.2e")) {
			    	table1=addRow(table1, 5, cellContent16);
			    }
			    String[] cellContent17 =	{"1.3","Whether delivery of securities is taken immediately on purchase as per settlement cycle/ terms for each transaction.Details of exceptions to normal settlement procedure such as hand delivery, short delivery, trade reversal etc. provided in Annexure B.",
			    									mnCompCertificate.getDelivery_of_securities_purch(),mnCompCertificate.getPurchase_of_sec_rem_1_3_2(),mnCompCertificateScrutiny.getDelivery_of_security_purch_rem()};
			    if(checkboxvalues.contains("1.3a")) {
			    	table1=addRow(table1, 5, cellContent17);
			    }	
			    String[] cellContent18 =	{"1.4","Whether any application/investment is done in Initial Public Offer (IPO), Follow on Public Offer (FPO) and/or Offer for sale (OFS) during the period? Details of Investments provided in Annexure C.\r\n" + 
			    			"Details of Investments provided in Annexure B.",mnCompCertificate.getInvestment_done_in_ipo(),mnCompCertificate.getPurchase_of_secu_rem_1_4_2(),mnCompCertificateScrutiny.getInvestment_done_in_ipo_rem()};
			    if(checkboxvalues.contains("1.4a")) {
			    	table1=addRow(table1, 5, cellContent18);
			    }		
			    
			    
//			    for (int row = 0; row < 8; row++) {
//			        for (int col = 0; col < 5; col++) {
//			            table1.addCell(new Cell().setBorder(null).add(new Paragraph(cellContent1[row][col])));
//			            
//			        }
//			        }
			    table1.complete();
			    doc.add(new Paragraph(""));
			    
			    
			    Table table2 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table2);
//				Cell cell2 = new Cell(1, 5)
//			            .add(new Paragraph("2 . Securities held")).setBorder(null).setBold();
//			    table2.addCell(cell2);
			    
			    String[] cellContent21 =	{"2.1","Whether scheme investments adhere to the Investment guidelines issued by PFRDA.(prescribed securities/ percentage/ limit/ prudential & exposure norms). Details of deviations provided in annexure A.",mnCompCertificate.getScheme_investments(),mnCompCertificate.getSecurities_held_2_1_2(),mnCompCertificateScrutiny.getScheme_investments_rem()};
			    if(checkboxvalues.contains("2.1a")) {
			    	table2=addRow(table2, 5, cellContent21);
			    }
			    String[] cellContent22 =	{"2.2","a) Whether stop loss/ any other review trigger has occurred for any security (equity/debt/alternate) during the month as per Investment policy of the Pension Fund.",
			    					mnCompCertificate.getStop_loss_trigger(),mnCompCertificate.getSecurities_held_2_2a_2(),mnCompCertificateScrutiny.getStop_loss_trigger_rem()};
			    if(checkboxvalues.contains("2.2a")) {
			    	table2=addRow(table2, 5, cellContent22);
			    }
			    String[] cellContent23 =	{" ","b) Whether decision in such a scenario is approved by the committee/competent authority as per Approval delegation matrix",
			    						mnCompCertificate.getDecision_approved_by_committee(),mnCompCertificate.getSecurities_held_2_2b_2(),mnCompCertificateScrutiny.getDecision_appr_by_committee_rem()};
			    if(checkboxvalues.contains("2.2b")) {
			    	table2=addRow(table2, 5, cellContent23);
			    }
			    String[] cellContent24 =	 {" ","c) Whether each decision along with rationale is properly documented and record is maintained at individual scrip level. (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision). Details of stop loss/review triggered during the month and its decision provided in Annexure D.",mnCompCertificate.getDecision_properly_documented(),mnCompCertificate.getSecurities_held_2_2c_2(),mnCompCertificateScrutiny.getDecision_prop_documented_rem()};
			    if(checkboxvalues.contains("2.2c")) {
			    	table2=addRow(table2, 5, cellContent24);
			    }
			    String[] cellContent25 =	{"2.3","Whether inter-scheme transfer of securities complies with Investment Guidelines issued by PFRDA.Details of inter scheme transfer provided in Annexure E.",mnCompCertificate.getInter_scheme_transfer(),mnCompCertificate.getSecurities_held_2_3_2(),mnCompCertificateScrutiny.getInter_scheme_transfer_rem()};
			    if(checkboxvalues.contains("2.3a")) {
			    	table2=addRow(table2, 5, cellContent25);
			    }
			    String[] cellContent26 =	{"2.4","Whether investment is held in any equity shares of a body corporate which is not listed in top 200 list of stocks prepared by NPS Trust and is pending for rebalancing. If yes, whether the decision to hold such stocks has been approved by the investment committee and informed to the Board of Pension Fund. Details provided in Annexure F",mnCompCertificate.getInvestment_held_in_equity(),mnCompCertificate.getSecurities_held_2_4_2(),mnCompCertificateScrutiny.getInvestment_held_in_equity_rem()};
			    if(checkboxvalues.contains("2.4a")) {
			    	table2=addRow(table2, 5, cellContent26);
			    }
			    String[] cellContent27 =	{"2.5","Whether investment in any equity shares through IPO/FPO/OFS does not fulfil the market capitalization condition prescribed under investment guidelines post listing.Details provided in Annexure G.",mnCompCertificate.getInvestment_in_equity_shares(),mnCompCertificate.getSecurities_held_2_5_2(),mnCompCertificateScrutiny.getInvest_in_equity_shares_rem()};
			    if(checkboxvalues.contains("2.5a")) {
			    	table2=addRow(table2, 5, cellContent27);
			    }		
			   
			    String[] cellContent28 =	{"2.5","Whether investment in any equity shares through IPO/FPO/OFS does not fulfil the market capitalization condition prescribed under investment guidelines post listing.Details provided in Annexure G.",mnCompCertificate.getActive_passive_breaches(),mnCompCertificate.getSecurities_held_2_6_2(),mnCompCertificateScrutiny.getActive_passive_breaches_rem()};
			    if(checkboxvalues.contains("2.5b")) {
			    	table2=addRow(table2, 5, cellContent27);
			    }
			    
//			    for (int row = 0; row < 7; row++) {
//			        for (int col = 0; col < 5; col++) {
//			            table2.addCell(new Cell().setBorder(null).add(new Paragraph(cellContent2[row][col])));
//			            
//			        }
//			        }
			    table2.complete();
			    doc.add(new Paragraph(""));
			    
			    Table table3 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table3);
//				Cell cell3 = new Cell(1, 5)
//			            .add(new Paragraph("3 . Sale of securities")).setBorder(null).setBold();
//			    table3.addCell(cell3);
			    String[] cellContent31 =	{"3.1","(a) Whether disinvestments made are approved by the committee/competent authority as per delegation matrix",
			    			mnCompCertificate.getDisinvestments_approved(),mnCompCertificate.getSale_of_securities_3_1a_2(),mnCompCertificateScrutiny.getDisinvestments_approved_rem()};
			    if(checkboxvalues.contains("3.1a")) {
			    	table3=addRow(table3, 5, cellContent31);
			    }
			    String[] cellContent32 =	{" ","(b) Whether each decision of disinvestment is properly documented and record is maintained at individual transaction level.\r\n" + 
			    		"(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)",
			    				mnCompCertificate.getDecision_of_disinvestment(),mnCompCertificate.getSale_of_securities_3_1b_2(),mnCompCertificateScrutiny.getDecision_of_disinvestment_rem()};
			    if(checkboxvalues.contains("3.1b")) {
			    	table3=addRow(table3, 5, cellContent32);
			    }
			    String[] cellContent33 =	{"3.2","Whether delivery of securities is given immediately on sale as per settlement cycle/ terms for each transaction. Please provide details of exceptions, if any, to normal settlement procedure such as hand delivery, short delivery, trade reversal etc.",
			    					mnCompCertificate.getDelivery_of_securities_sale(),mnCompCertificate.getSale_of_securities_3_2_2(),mnCompCertificateScrutiny.getDelivery_of_security_sale_rem()};
			    if(checkboxvalues.contains("3.2a")) {
			    	table3=addRow(table3, 5, cellContent33);
			    }
			    
			    
//			    for (int row = 0; row < 3; row++) {
//			        for (int col = 0; col < 5; col++) {
//			            table3.addCell(new Cell().setBorder(null).add(new Paragraph(cellContent3[row][col])));
//			            
//			        }
//			        }
			    table3.complete();
			    
			    
			    doc.add(new Paragraph(""));
			    
			    Table table4 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table4);
//				Cell cell4 = new Cell(1, 5)
//			            .add(new Paragraph("4 . Reports and Disclosures as per Schedule V of PFRDA (PF) Regulations")).setBorder(null).setBold();
//			    table4.addCell(cell4);
			    String[] cellContent41 =	{"4.1","(a) Whether scheme-wise number of units are tallied with CRA records on daily basis.",
			    			mnCompCertificate.getAll_investment_charges(),mnCompCertificate.getReports_and_Disclosure_4_1a_2(),mnCompCertificateScrutiny.getAll_investment_charges_rem()};
			    if(checkboxvalues.contains("4.1a")) {
			    	table4=addRow(table4, 5, cellContent41);
			    }
			    String[] cellContent42 =	{" ","(b) Whether the securities held in the schemes are tallied with the Custodian records on a daily basis.",
			    				mnCompCertificate.getPfm_adhered(),mnCompCertificate.getReports_and_Disclosure_4_1b_2(),mnCompCertificateScrutiny.getPfm_adhered_rem()};
			    if(checkboxvalues.contains("4.1b")) {
			    	table4=addRow(table4, 5, cellContent42);
			    }
			    String[] cellContent43 =	{" ","(c) Whether all investment charges (Investment management fees, Custody and related charges, SEBI charges, NPS Trust fees etc.) are loaded onto the scheme-wise NAV on a daily basis.",
			    					mnCompCertificate.getRecords_of_the_audit_of_nav(),mnCompCertificate.getReports_and_Disclosure_4_1c_2(),mnCompCertificateScrutiny.getRecords_of_audit_of_nav_rem()};
			    if(checkboxvalues.contains("4.1c")) {
			    	table4=addRow(table4, 5, cellContent43);
			    }
			    String[] cellContent44 =		{" ","(d) Whether the Pension Fund has adhered to instructions of PFRDA to get the scheme-wise NAV verified by the auditors on a daily basis.",
			    						mnCompCertificate.getScheme_wise_nav_uploaded(),mnCompCertificate.getReports_and_Disclosure_4_1d_2(),mnCompCertificateScrutiny.getScheme_wise_nav_uploaded_rem()};
			    if(checkboxvalues.contains("4.1d")) {
			    	table4=addRow(table4, 5, cellContent44);
			    }
			    String[] cellContent45 =		{" ","(e) Whether the records of the audit of scheme-wise NAV have been maintained by the pension fund for future inspection.",
			    							mnCompCertificate.getScheme_wise_nav_submitted(),mnCompCertificate.getReports_and_Disclosure_4_1e_2(),mnCompCertificateScrutiny.getScheme_wise_nav_submitted_rem()};
			    if(checkboxvalues.contains("4.1e")) {
			    	table4=addRow(table4, 5, cellContent45);
			    }
			    
			    
			    
			    
//			    String[] cellContent46 =		{"4.2","(a) Whether monthly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the month.",
//			    								mnCompCertificate.getMonthly_reports_submitted(),mnCompCertificate.getReports_and_Disclosure_4_2a_2(),mnCompCertificateScrutiny.getMonthly_reports_submitted_rem()};
//			    if(checkboxvalues.contains("4.2a")) {
//			    	table4=addRow(table4, 5, cellContent46);
//			    }
//			    String[] cellContent47 =		{" ","(b) Whether scrip wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.",
//			    									mnCompCertificate.getScrip_wise_details(),mnCompCertificate.getReports_and_Disclosure_4_2b_2(),mnCompCertificateScrutiny.getScrip_wise_details_rem()};
//			    if(checkboxvalues.contains("4.2b")) {
//			    	table4=addRow(table4, 5, cellContent47);
//			    }
			    
//			    for (int row = 0; row < 7; row++) {
//			        for (int col = 0; col < 5; col++) {
//			            table4.addCell(new Cell().setBorder(null).add(new Paragraph(cellContent4[row][col])));
//			            
//			        }
//			        }
			    table4.complete();

    
			    
			    
			    
			    doc.add(new Paragraph(""));
			    
			    Table table5 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f,1.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    doc.add(table5);

			    String[] cellContent51 =	{"5.1","(a) Whether scheme-wise NAV (latest & historical) is uploaded daily on the Pension Fund's website within the prescribed time limit.",
			    			mnCompCertificate.getScheme_wise(),mnCompCertificate.getDisclosure_requirements_5_1a_2(),mnCompCertificateScrutiny.getScheme_wise_rem()};
			    if(checkboxvalues.contains("5.1a")) {
			    	table5=addRow(table5, 5, cellContent51);
			    }
			    String[] cellContent52 =	{" ","(b) Whether scheme-wise NAV is submitted daily to all the CRA's within the prescribed time limit.Instances of delays during the month in uploading NAV on Pension Fund's website and submission to CRA with the reasons provided in Annexure I.",
			    				mnCompCertificate.getScheme_wise_daily(),mnCompCertificate.getDisclosure_requirements_5_1b_2(),mnCompCertificateScrutiny.getScheme_wise_daily_rem()};
			    if(checkboxvalues.contains("5.1b")) {
			    	table5=addRow(table5, 5, cellContent52);
			    }
			    String[] cellContent53 =	{" ","(c) Whether monthly periodic reports as per schedule V of PFRDA (PF) Regulations, 2015 and subsequent amendments are submitted to NPS Trust within 10 days from the end of the month.",
			    					mnCompCertificate.getPeriodic_reports_monthly(),mnCompCertificate.getDisclosure_requirements_5_1c_2(),mnCompCertificateScrutiny.getPeriodic_reports_monthly_rem()};
			    if(checkboxvalues.contains("5.1c")) {
			    	table5=addRow(table5, 5, cellContent53);
			    }
			    String[] cellContent54 =		{" ","(d) Whether scrip wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.",
			    						mnCompCertificate.getScrip_wise_details_portfolio(),mnCompCertificate.getDisclosure_requirements_5_1d_2(),mnCompCertificateScrutiny.getScrip_wise_details_pf_rem()};
			    if(checkboxvalues.contains("5.1d")) {
			    	table5=addRow(table5, 5, cellContent54);
			    }
			    String[] cellContent55 =		{" ","(e)  Whether the pension fund has published on its website a list of its group companies and those of its sponsor.",
			    							mnCompCertificate.getPension_fund_published(),mnCompCertificate.getDisclosure_requirements_5_1e_2(),mnCompCertificateScrutiny.getPension_fund_published_rem()};
			    if(checkboxvalues.contains("5.1e")) {
			    	table5=addRow(table5, 5, cellContent55);
			    }
			    
			    String[] cellContent56 =		{" ","(f) Whether the pension fund has disclosed the scheme returns in the manner and in the format as available in public domain hosted by National Pension System Trust",
						mnCompCertificate.getPension_fund_disclosed(),mnCompCertificate.getDisclosure_requirements_5_1f_2(),mnCompCertificateScrutiny.getPension_fund_disclosed_rem()};
			    if(checkboxvalues.contains("5.1e")) {
			    	table5=addRow(table5, 5, cellContent56);
			    }
			    
			    table5.complete();

			    
			    
			    
			    
			    
			    

			    
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


private static final Log log=LogFactoryUtil.getLog(FormsPdfCreationUtil.class.getName()); 


}
