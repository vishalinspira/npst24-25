package com.nps.pfm.hy.comp.cert.util;


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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

	public class HyCompPdfUtil {
		
		public static File hyCompCertificatePFMPDF(String reportDate,String year, String company_name, String emplolyee_name, 
				String roles, String date_3, String place ,JSONObject jsonObject) {

			File file=null;
			
			try  {  
				 file=FileUtil.createTempFile("pdf");
			
				   PdfWriter writer = new PdfWriter(file);
				    PdfDocument pdfDoc= new PdfDocument(writer);
				    Document doc = new Document(pdfDoc); 
				    Text text1 = new Text("Half Yearly Compliance Certificate");
				    text1.setBold();
				    Paragraph para1 = new Paragraph(text1);
				    doc.add(para1);
		            doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));
					            
				    Table tablex = new Table(new float[] {5f, 5f}, true)
			                .setWidth(UnitValue.createPercentValue(100));
				    String[] headersx = {"Report Due Date","For the half - year ended"};
				    for (int col = 0; col < 2; col++) {
			            tablex.addCell(new Cell().add(new Paragraph(headersx[col])).setBold().setBorder(null));
			            
			        }
				    doc.add(tablex);
				    String[][] cellContentx =	{
				    		{reportDate,year}
		             };
				    
				    for (int row = 0; row < 1; row++) {
				        for (int col = 0; col < 2; col++) {
				            tablex.addCell(new Cell().add(new Paragraph(cellContentx[row][col])).setBorder(null));
				            
				        }
				        }
				    tablex.complete();
				    
				    Text text5 = new Text("\n  Company Name: "+company_name).setBold();
				    Paragraph companyName = new Paragraph(text5);
				    doc.add(companyName);
				    Text text6 = new Text("\n \n To, \n National Pension System Trust,\n Tower B, B-302, Third Floor, \n World Trade Center,\nNauroji Nagar,\n New Delhi-110029\n");		   
					Paragraph para2 = new Paragraph(text6.setBold());
				    doc.add(para2);
				    int colsize=4;
				    Text text8 = new Text("\n Sir, \n \n In my opinion and to the best of my information and according to the examinations"
				    		+ " carried out by me and explanations furnished to me, I certify"
				    		+ " the following in respect of the month mentioned above \n \n");
				    Paragraph para3 = new Paragraph(text8);
				    doc.add(para3);
				    
				    Table table1 = new Table(new float[] {0.5f, 2.5f, 0.5f,1.5f}, true)
			                .setWidth(UnitValue.createPercentValue(100));
				    String[] headers1 = {"S.No", "Parameters ","Yes/No/NA", "PFM Remarks","NPST Remarks"};
				    for (int col = 0; col < colsize; col++) {
			            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
			            
			        }
				    doc.add(table1);
//					Cell cell1 = new Cell(1, colsize)
//				            .add(new Paragraph("1 . Purchase of securities")).setBold();
//				    table1.addCell(cell1);
				    log.info("call1");
				    log.info(jsonObject);
				    String[][] cellContent1 =	{
				    		{"1","Whether PFM has submitted copy of half-Yearly unaudited accounts of schemes within one month from the close of each half-year.",
				    			jsonObject.get("1_1").toString(),jsonObject.get("1_rem_1").toString(),jsonObject.get("1_npsrem_1").toString()},
				    		{"2","Whether PFM has disclosed on its website a copy of half-Yearly unaudited accounts of schemes along with scheme portfolio within one month from the close of each half-year.",
				    				jsonObject.get("2_1").toString(),jsonObject.get("2_rem_1").toString(),jsonObject.get("2_npsrem_1").toString()},
				    		{" 3","Whether any change in interest of Directors has been submitted to NPS Trust every six months.",
				    					jsonObject.get("3_1").toString(),jsonObject.get("3_rem_1").toString(),jsonObject.get("3_npsrem_1").toString()},
				    		{"4 ","Whether PFM has submitted half-yearly certification by the Internal Auditor after it has been considered by the Board of PFM.",
				    						jsonObject.get("4_1").toString(),jsonObject.get("4_rem_1").toString(),jsonObject.get("4_npsrem_1").toString()},
				    };
				    log.info("call2");
				    for (int row = 0; row < 4; row++) {
				        for (int col = 0; col < colsize; col++) {
				            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
				            
				        }
				        }
				    table1.complete();
				    doc.add(new Paragraph(""));

				    Paragraph para4 = new Paragraph(new Text("\n Note: \n \n").setBold());
				    Text text9 = new Text("1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.\r\n" + 
				    		"2) This Compliance Certificate(s)n shall be put up to the Board at its ensuing Board Meeting and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.\r\n" + 
				    		"3)Certified that the information given, herein are correct and complete to the best of my knowledge and belief.");
				    para4.add(text9);
				    doc.add(para4);
				    log.info("call3");
				    Table table5 = new Table(new float[] {5f, 5f}, true)
			                .setWidth(UnitValue.createPercentValue(100));
				    String[] headersx1 = {"Date:","Name"};
				    for (int col = 0; col < 1; col++) {
			            table5.addCell(new Cell().add(new Paragraph(headersx1[col])).setBold().setBorder(null));
			            
			        }
				    doc.add(table5);
				    //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				    //String strDate = dateFormat.format(date_3); 
				    String[] values1 = {date_3,emplolyee_name};
				    for (int col = 0; col < 1; col++) {
			            table5.addCell(new Cell().add(new Paragraph(values1[col])).setBorder(null));
			            
			        }
				    log.info("call4");
				    String[] headersy = {"Place:","Role"};
				    for (int col = 0; col < 1; col++) {
			            table5.addCell(new Cell().add(new Paragraph(headersy[col])).setBold().setBorder(null));
			            
			        }
				    log.info("call5");  
				    String[] values2 = {place,roles};
				    for (int col = 0; col < 1; col++) {
			            table5.addCell(new Cell().add(new Paragraph(values2[col])).setBorder(null));
			            
			        }
				    log.info("call6");
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

	private static final Log log=LogFactoryUtil.getLog(HyCompPdfUtil.class.getName()); 


	}
