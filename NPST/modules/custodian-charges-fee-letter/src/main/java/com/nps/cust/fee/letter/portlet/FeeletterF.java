package com.nps.cust.fee.letter.portlet;

import com.itextpdf.io.util.FileUtil;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.nps.cust.fee.letter.constants.ColumnName;
import com.nps.cust.fee.letter.constants.PFMConstants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FeeletterF {
	
	public static String getDateString(Date date){
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
		try {
			if(date!=null && !date.equals(null) && !date.equals("")) {
				return dateFormat.format(date);			
			}else {
				return "";
			}
		}catch (Exception e) {
			log.error("error while convert date to string : "+e.getCause()+"  : "+e.getMessage());
		}
		return "";
			
	}
	

public static File generateFeeLetter(String pfmName,JSONArray jsonArray,String letterDate,String receivedDate,String  quarter,String signeturies) throws IOException {

		File file=null;
		try  {  
			log.info("json array length:: "+jsonArray.length());
			//String companyName=CommonRoleService.getCompanyName(pfmName);
			 file=FileUtil.createTempFile(pfmName+" Fee Letter.pdf");
			   PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
			   // PdfFont standardFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
			    doc.setFontSize(10);
			    doc.setRightMargin(60);
			    doc.setLeftMargin(60);
			    
			    Text text01 = new Text("\n \n \n \n \n ");
			    Paragraph para01 = new Paragraph(text01);
			    doc.add(para01);
			    Text text1 = new Text("F. No. NPST-22017/1/2020-CUSTODIAN                                                                Date:"+letterDate).setBold();
			    text1.setBold();
			    Paragraph para1 = new Paragraph(text1).setTextAlignment(TextAlignment.LEFT);
	            doc.add(para1);
			   
                Text text4 = new Text("The Chief Executive Officer \n ");
                Paragraph para = new Paragraph(text4);
			    Text text5 = new Text(pfmName+"\n").setBold();
			    para.add(text5);
			    if(pfmName.equalsIgnoreCase(PFMConstants.DSP_ADDRESS)) {
			    	Text text6 = new Text("Mafatlal Centre,10th floor,\n Nariman Point \n  Mumbai - 400021\n");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.DSP_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    	 
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.MAX_ADDRESS)) {
			    	Text text6 = new Text("3rd Floor, Plot 90C \n Sector 18, Urban Estate, \n  Gurugram, Haryana - 122001\n");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.MAX_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.ABS_ADDRESS)) {
			    	Text text6 = new Text(" One India bulls Centre, Tower 16th Floor \n Jupiter Mill Compound, 841, Senapati Bapat Marg \n Elphinstone Road, Mumbai - 400013\n ");
			    	para.add(text6);
				    doc.add(para);
				    Text text7 =new Text ("Kind Attn: "+PFMConstants.ABS_NAME).setBold();
				    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
				    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.TATA_ADDRESS)) {
			    	Text text6 = new Text(" 1901 Parinee Crescenzo, G Block \n BKC, Bandra East \n Mumbai - 400051\n");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.TATA_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.APF_ADDRESS)) {
			    	Text text6 = new Text("Axis House, 1st Floor, C-2 Wadia Centre,\n Pandurang Budhkar Marg, Worli  \n Mumbai - 400025\n");
			    	para.add(text6);
				    doc.add(para);
				    Text text7 =new Text ("Kind Attn: "+PFMConstants.APF_NAME).setBold();
				    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
				    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.KOTAK_ADDRESS)) {
			    	Text text6 = new Text(" 6th Floor, Kotak Infiniti, Building No 21, Infinity Park \n  Off Western Express Highway, General A.K Vaidya Marg \n Goregaon Mulund Link Road, Malad E,Mumbai - 400097\n ");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.KOTAK_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.ICICI_ADDRESS)) {
			    	Text text6 = new Text("1089 ICICI Prulife Towers, \n Appasaheb Marathe Marg, Prabhadevi, \n Maharashtra - 400025\n ");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.ICICI_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.HDFC_ADDRESS)) {
			    	Text text6 = new Text("14th Floor, Lodha Excelus \n Apollo Mills Compound, N M joshi Marg, Mahalaxmi \n Mumbai- 400011\n ");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.HDFC_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.UTI_ADDRESS)) {
			    	Text text6 = new Text("First Floor, Unit No. 2 Block ‘B’, JVPD Scheme \n Gulmohar Cross Road No. 9, Andheri (West) \n Mumbai – 400049\n ");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.UTI_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.LIC_ADDRESS)) {
			    	Text text6 = new Text("1st Floor, East Wing \n Industrial Assurance Building Veer Nariman Road \n Churchgate, Mumbai – 400020\n");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.LIC_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    }else if(pfmName.equalsIgnoreCase(PFMConstants.SBI_ADDRESS)) {
			    	Text text6 = new Text(" 1904, 19th floor, B-Wing, Parinee Crescenzo \n G Block, Plot No C-38/39 Bandra Kurla Complex \n Bandra East – Mumbai 400051\n");
			    	 para.add(text6);
					    doc.add(para);
					    Text text7 =new Text ("Kind Attn: "+PFMConstants.SBI_NAME).setBold();
					    Paragraph para1_1 = new Paragraph(text7).setTextAlignment(TextAlignment.RIGHT);
					    doc.add(para1_1);
			    }

			    Text text8 = new Text("\n Dear Sir/Madam,\n\n ");
			    Paragraph para2 = new Paragraph(text8);
			    Text text9 = new Text("Subject: Payment of Custody, NSDL and CBRICS/CCIL charges to Deutsche Bank for the quarter ended "+quarter+" \n\n").setBold();
			    para2.add(text9);
			    Text text10 = new Text("The Competent Authority has accorded approval for payment of custody and related charges in respect of all schemes managed by the Pension Fund for the quarter ended "+quarter+" as under:\r\n \n");
			    para2.add(text10);
			    
			    doc.add(para2);
			    //doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			    
			    Text text12 = new Text("(Amount in Rs.)").setBold();
			    Paragraph para3 = new Paragraph(text12);
			    para3.setTextAlignment(TextAlignment.RIGHT);
			   // para3.setMarginLeft(10);
			   // para3.setMarginRight(10);
			    doc.add(para3);
			    
			    Table table1 = new Table(new float[] {5f, 2.5f, 2.5f,2.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			   // table1.setMarginLeft(10);
			   // table1.setMarginRight(10);
			    
//			    Cell cell1 = new Cell(2, 1)
//			            .add(new Paragraph("Scheme_Name")).setTextAlignment(TextAlignment.CENTER).setBold();
//			    table1.addCell(cell1); 
//			    Cell cell2 = new Cell(1, 3)
//			            .add(new Paragraph("Investment Management Fee")).setTextAlignment(TextAlignment.CENTER).setBold();
//			    table1.addCell(cell2); 
//			    Cell cell3 = new Cell(2, 1)
//			            .add(new Paragraph("NPS Trust Fee")).setTextAlignment(TextAlignment.CENTER).setBold();
//			    table1.addCell(cell3); 
			    String[] headers1 = { ColumnName.SCHEME_NAME, ColumnName.CUST_BILL_AMT,ColumnName.PFM_BILL_AMT,ColumnName.APPROVAL_AMT};
			    for (int col = 0; col < 4; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold().setTextAlignment(TextAlignment.CENTER));
		        }
			    
//			    String[] headers1 = {"Scheme Name", "IMF","GST", "Total","NPS Trust Fee"};
//			    for (int col = 0; col < 5; col++) {
//		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
//		            
//		        }
			    doc.add(table1);
			   
			    
			    
			    Table table2 = new Table(new float[] {5f, 2.5f, 2.5f,2.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			  //  table2.setMarginLeft(10);
			   // table2.setMarginRight(10);
//			    Cell cell21 = new Cell(2, 1)
//			            .add(new Paragraph("Scheme Name")).setTextAlignment(TextAlignment.CENTER).setBold();
//			    table2.addCell(cell21); 
//			    Cell cell22 = new Cell(1, 3)
//			            .add(new Paragraph("Investment Management Fee")).setTextAlignment(TextAlignment.CENTER).setBold();
//			    table2.addCell(cell22); 
//			    Cell cell23 = new Cell(2, 1)
//			            .add(new Paragraph("NPS Trust Fee")).setTextAlignment(TextAlignment.CENTER).setBold();
//			    table2.addCell(cell23); 
			    String[] headers21 =  { ColumnName.SCHEME_NAME, ColumnName.CUST_BILL_AMT,ColumnName.PFM_BILL_AMT,ColumnName.APPROVAL_AMT};
			    for (int col = 0; col < 4; col++) {
		            table2.addCell(new Cell().add(new Paragraph(headers21[col])).setBold().setTextAlignment(TextAlignment.CENTER));
		        }
			    log.info("before loop");
			    boolean isNewtable=false;
			    int tbale1row=1;
			    for (int row = 1; row < jsonArray.length(); row++) {
			    	try {
			    		tbale1row=tbale1row+1;
			    	JSONObject jsonObject=jsonArray.getJSONObject(row);
			       if(row<8) {
			    	for (int col = 0; col < 4; col++) {
			        	if(col==0) {
			        		if(row==jsonArray.length()-1) {
			        		table1.addCell(new Cell().add(new Paragraph("Total"))).setTextAlignment(TextAlignment.CENTER);
			        		}else {
			        			
			        			table1.addCell(new Cell().add(new Paragraph(jsonObject.getString("schemeName"))).setTextAlignment(TextAlignment.CENTER));
			        		}
			        	}else if(col==1) {
			        		log.info("approval_amt::: "+jsonObject.getString("approval_amt"));
			        		table1.addCell(new Cell().add(new Paragraph(jsonObject.getString("approval_amt"))).setTextAlignment(TextAlignment.RIGHT));
			        	}else if(col==2) {
			        		table1.addCell(new Cell().add(new Paragraph(jsonObject.getString("cust_bill_amt"))).setTextAlignment(TextAlignment.RIGHT));
			        	}else if(col==3) {
			        		table1.addCell(new Cell().add(new Paragraph(jsonObject.getString("pfm_bill_amt"))).setTextAlignment(TextAlignment.RIGHT));
			        	}
			        }
			       }else {
			    	   isNewtable=true;
			    	   break;
			       }
			    	}catch (Exception e) {
						log.error(e);
					}
			    	
			        }
			    try {
			    
	            	for (int col = 0; col < 5; col++) {
	            		if(!isNewtable) {
	            			log.info("tbale1row "+tbale1row);
            		    Cell cell = table1.getCell(tbale1row, col);
	 		                cell.setBold();		
	            		}
		            
		               // Cell cellHeader = table1.getCell(0, col);
		                //cellHeader.setBold();
		            }
	            }catch (Exception e) {
					log.error(e);
				}
			    table1.complete();
			    
			    if(isNewtable) {
			    	doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			    	 Text text02 = new Text("\n \n  ");
					    Paragraph para02 = new Paragraph(text02);
					    doc.add(para02);
					    
			    	doc.add(table2);
			    	int tbale2row=1;
				    for (int row = 8; row < jsonArray.length(); row++) {
				    	try {
				    		tbale2row=tbale2row+1;
				    	JSONObject jsonObject=jsonArray.getJSONObject(row);
				    	for (int col = 0; col < 4; col++) {
				        	if(col==0) {
				        		if(row==jsonArray.length()-1) {
				        		table2.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER));
				        		}else {
				        			table2.addCell(new Cell().add(new Paragraph(jsonObject.getString("schemeName"))).setTextAlignment(TextAlignment.CENTER));
				        		}
				        	}else if(col==1) {
				        		log.info("approval_amt::: "+jsonObject.getString("approval_amt"));
				        		table2.addCell(new Cell().add(new Paragraph(jsonObject.getString("approval_amt"))).setTextAlignment(TextAlignment.RIGHT));
				        	}else if(col==2) {
				        		table2.addCell(new Cell().add(new Paragraph(jsonObject.getString("cust_bill_amt"))).setTextAlignment(TextAlignment.RIGHT));
				        	}else if(col==3) {
				        		table2.addCell(new Cell().add(new Paragraph(jsonObject.getString("pfm_bill_amt"))).setTextAlignment(TextAlignment.RIGHT));
				        	}

				        }
				      
				    	}catch (Exception e) {
							log.error(e);
						}
				        }
				    
				    	log.info("tbale2row: "+tbale2row);
		            	for (int col = 0; col < 4; col++) {
		            		try {
			                Cell cell = table2.getCell(tbale2row-1, col);
			                cell.setBold();
			             //   Cell cellHeader = table2.getCell(0, col);
			               // cellHeader.setBold();
		            		}catch (Exception e) {
								log.error("col : " +col +"   "+ e.getMessage());
							}
			            }
		            
				    table2.complete();
			    }
			    Paragraph para20 = new Paragraph();
			    Text text20 = new Text("\r\n \n 2.	The approval for the above said payment is conveyed, subject to: \n ");
			    para20.add(text20);
			    Text text21 = new Text("\u00A0 \u00A0 \u00A0 \u00A0 \u00A0 i.	Scrutiny by auditors (Internal & Scheme) to verify and validate the payments.\n");
			    para20.add(text21);
			    Text text22 = new Text("\u00A0 \u00A0 \u00A0 \u00A0 \u00A0 ii.	The payments must be in accordance with the charges prescribed in the respective Agreements/ Schedule to agreements, entered between the intermediaries.\r\n \n");
			    para20.add(text22);
			    
			    doc.add(para20);
//			    Table table3 = new Table(new float[] {1f, 10f}, true)  .setWidth(UnitValue.createPercentValue(100));
//			    Color borderColor = new DeviceRgb(255, 255, 255); // White color
//			    table3.setBorder(new SolidBorder(borderColor, 1));
//			    doc.add(table3);
//			    
//		            table3.addCell(new Cell().add(new Paragraph("")).setBold().setTextAlignment(TextAlignment.CENTER)).setBorder(new SolidBorder(borderColor, 1));
//		            table3.addCell(new Cell().add(new Paragraph("i.	Scrutiny by auditors (Internal & Scheme) to verify and validate the payments.")).setTextAlignment(TextAlignment.CENTER)).setBorder(new SolidBorder(borderColor, 1));
//		            table3.addCell(new Cell().add(new Paragraph("")).setBold().setTextAlignment(TextAlignment.CENTER)).setBorder(new SolidBorder(borderColor, 1));
//		            table3.addCell(new Cell().add(new Paragraph("ii.	The payments must be in accordance with the charges prescribed in the respective Agreements/ Schedule to agreements, entered between the intermediaries.")).setTextAlignment(TextAlignment.CENTER)).setBorder(new SolidBorder(borderColor, 1));
//		            table3.complete();
//		            
		            Paragraph para30 = new Paragraph();
		            Text text30 = new Text("3.	It is requested to kindly ensure that correct amounts should be charged in the schemes and there is no repeat/ double payment on this account.\r\n \n");
		            para30.add(text30);
			    doc.add(para30);
			    
			    Text text13 = new Text("\n\nYours sincerely,\n \n \n \n ");
			    Paragraph para4 = new Paragraph(text13);
			    Text text14 = new Text("("+signeturies+") ").setBold();
			    para4.add(text14);
			    Text text15 = new Text("\n"+PFMConstants.SIGNETURIES_MAP.get(signeturies));
			    para4.add(text15);
			    para4.setTextAlignment(TextAlignment.RIGHT);
			    doc.add(para4);
			    
			    
			    Paragraph para40 = new Paragraph();
			    Text text40 = new Text("CC to: Shri. Hiren Shah, Director-Head Domestic Sales & Client Management, M/s Deutsche Bank AG, Nirlon Knowledge Park, Block B1, 4th floor, Western Express Highway, Goregaon (E),  Mumbai – 400063 \r\n \n \n ");
			    para40.add(text40);
			    doc.add(para40);
			    
			    
			    
			  
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

private static final Log log=LogFactoryUtil.getLog(FeeLetterPDF.class.getName()); 


}

