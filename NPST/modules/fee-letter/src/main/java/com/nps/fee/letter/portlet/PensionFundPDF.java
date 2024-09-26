package com.nps.fee.letter.portlet;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.util.FileUtil;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
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
import com.nps.fee.letter.constants.PFMConstants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PensionFundPDF {
	
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
	

public static File generateFeeLetter(String pfmName,JSONArray jsonArray,Date letterDate,Date receivedDate,String  month,String signeturies) throws IOException {

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
			    Text text1 = new Text("F. No: NPST-20/19/11/1/2024-PENSION FUND                                                                Date:"+getDateString(letterDate)).setBold();
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

			    Text text8 = new Text("\n Dear Sir,\n\n ");
			    Paragraph para2 = new Paragraph(text8);
			    Text text9 = new Text("Subject: Approval of Investment Management Fees and NPS Trust Fee for the month of  "+month+" \n\n").setBold().setUnderline();
			    para2.add(text9);
			    Text text10 = new Text("1. This is with reference to letters submitted on DCMS portal on "+getDateString(receivedDate)+" seeking approval for your claim of Investment Management Fees and NPST fees for the mentioned period.\r\n \n");
			    para2.add(text10);
			    
			    Text text11 = new Text("2. The Competent Authority has accorded approval for the payment of Investment Management Fees to the Pension Fund and NPS Trust fee/ charges to the  NPS Trust from the schemes managed by the Pension Fund, for the period as under:- \n ");
			    para2.add(text11);
			    doc.add(para2);
			    //doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			    
			    Text text12 = new Text("(Amount in Rs.)").setBold();
			    Paragraph para3 = new Paragraph(text12);
			    para3.setTextAlignment(TextAlignment.RIGHT);
			    para3.setMarginLeft(20);
			    para3.setMarginRight(20);
			    doc.add(para3);
			    
			    Table table1 = new Table(new float[] {4f, 2f, 2f,2f,2f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    table1.setMarginLeft(20);
			    table1.setMarginRight(20);
			    
			    Cell cell1 = new Cell(2, 1)
			            .add(new Paragraph("Scheme Name")).setTextAlignment(TextAlignment.CENTER).setBold();
			    table1.addCell(cell1); 
			    Cell cell2 = new Cell(1, 3)
			            .add(new Paragraph("Investment Management Fee")).setTextAlignment(TextAlignment.CENTER).setBold();
			    table1.addCell(cell2); 
			    Cell cell3 = new Cell(2, 1)
			            .add(new Paragraph("NPS Trust Fee")).setTextAlignment(TextAlignment.CENTER).setBold();
			    table1.addCell(cell3); 
			    String[] headers1 = { "IMF","GST", "Total"};
			    for (int col = 0; col < 3; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold().setTextAlignment(TextAlignment.CENTER));
		        }
			    
//			    String[] headers1 = {"Scheme Name", "IMF","GST", "Total","NPS Trust Fee"};
//			    for (int col = 0; col < 5; col++) {
//		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
//		            
//		        }
			    doc.add(table1);
			   
			    
			    
			    
			    Table table2 = new Table(new float[] {4f, 2f, 2f,2f,2f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    table2.setMarginLeft(20);
			    table2.setMarginRight(20);
			    Cell cell21 = new Cell(2, 1)
			            .add(new Paragraph("Scheme Name")).setTextAlignment(TextAlignment.CENTER).setBold();
			    table2.addCell(cell21); 
			    Cell cell22 = new Cell(1, 3)
			            .add(new Paragraph("Investment Management Fee")).setTextAlignment(TextAlignment.CENTER).setBold();
			    table2.addCell(cell22); 
			    Cell cell23 = new Cell(2, 1)
			            .add(new Paragraph("NPS Trust Fee")).setTextAlignment(TextAlignment.CENTER).setBold();
			    table2.addCell(cell23); 
			    String[] headers21 = { "IMF","GST", "Total"};
			    for (int col = 0; col < 3; col++) {
		            table2.addCell(new Cell().add(new Paragraph(headers21[col])).setBold().setTextAlignment(TextAlignment.CENTER));
		        }
			    log.info("before loop");
			    boolean isNewtable=false;
			    for (int row = 1; row < jsonArray.length(); row++) {
			    	try {
			    	JSONObject jsonObject=jsonArray.getJSONObject(row);
			       if(row<8) {
			    	for (int col = 0; col < 5; col++) {
			        	if(col==0) {
			        		if(row==jsonArray.length()-1) {
			        		table1.addCell(new Cell().add(new Paragraph("Total"))).setBold();
			        		}else {
			        			table1.addCell(new Cell().add(new Paragraph(jsonObject.getString("schemeName"))).setTextAlignment(TextAlignment.CENTER)).setBold();
			        		}
			        	}else if(col==1) {
			        		log.info("imf::: "+jsonObject.getString("imf"));
			        		table1.addCell(new Cell().add(new Paragraph(jsonObject.getString("imf"))).setTextAlignment(TextAlignment.RIGHT)).setBold();
			        	}else if(col==2) {
			        		table1.addCell(new Cell().add(new Paragraph(jsonObject.getString("gst"))).setTextAlignment(TextAlignment.RIGHT)).setBold();
			        	}else if(col==3) {
			        		table1.addCell(new Cell().add(new Paragraph(jsonObject.getString("total"))).setTextAlignment(TextAlignment.RIGHT)).setBold();
			        	}else if(col==4) {
			        		table1.addCell(new Cell().add(new Paragraph(Validator.isNull(jsonObject.getString("trustfee"))?"-":jsonObject.getString("trustfee"))).setTextAlignment(TextAlignment.RIGHT)).setBold();
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
		            //    Cell cell = table1.getCell(numRows, col);
		               // cell.setBold();
		                Cell cellHeader = table1.getCell(0, col);
		                cellHeader.setBold();
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
				    for (int row = 8; row < jsonArray.length(); row++) {
				    	try {
				    	JSONObject jsonObject=jsonArray.getJSONObject(row);
				    	for (int col = 0; col < 5; col++) {
				        	if(col==0) {
				        		if(row==jsonArray.length()-1) {
				        		table2.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER)).setBold();
				        		}else {
				        			table2.addCell(new Cell().add(new Paragraph(jsonObject.getString("schemeName"))).setTextAlignment(TextAlignment.CENTER)).setBold();
				        		}
				        	}else if(col==1) {
				        		log.info("imf::: "+jsonObject.getString("imf"));
				        		table2.addCell(new Cell().add(new Paragraph(jsonObject.getString("imf"))).setTextAlignment(TextAlignment.RIGHT)).setBold();
				        	}else if(col==2) {
				        		table2.addCell(new Cell().add(new Paragraph(jsonObject.getString("gst"))).setTextAlignment(TextAlignment.RIGHT)).setBold();
				        	}else if(col==3) {
				        		table2.addCell(new Cell().add(new Paragraph(jsonObject.getString("total"))).setTextAlignment(TextAlignment.RIGHT)).setBold();
				        	}else if(col==4) {
				        		table2.addCell(new Cell().add(new Paragraph(Validator.isNull(jsonObject.getString("trustfee"))?"-":jsonObject.getString("trustfee"))).setTextAlignment(TextAlignment.RIGHT)).setBold();
				        	}

				        }
				      
				    	}catch (Exception e) {
							log.error(e);
						}
				        }
				    try {
		            	for (int col = 0; col < 5; col++) {
			              //  Cell cell = table1.getCell(numRows, col);
			               // cell.setBold();
			                Cell cellHeader = table2.getCell(0, col);
			                cellHeader.setBold();
			            }
		            }catch (Exception e) {
						log.error(e.getMessage());
					}
				    table2.complete();
			    }
			    
			    
			    
			    
//			    for (int row = 0; row < 15; row++) {
//			        for (int col = 0; col < 5; col++) {
//			            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
//			            
//			        }
//			        }
			    
			    //int numRows = jsonArray.length()-1; // Including the header row
//	            try {
//	            	for (int col = 0; col < 5; col++) {
//		            //    Cell cell = table1.getCell(numRows, col);
//		               // cell.setBold();
//		                Cell cellHeader = table1.getCell(0, col);
//		                cellHeader.setBold();
//		            }
//	            }catch (Exception e) {
//					log.error(e);
//				}
//			    table1.complete();
			    
			    Text text13 = new Text("\n\nYours sincerely,\n \n \n \n ");
			    Paragraph para4 = new Paragraph(text13);
			    Text text14 = new Text("("+signeturies+") ").setBold();
			    para4.add(text14);
			    Text text15 = new Text("\n"+PFMConstants.SIGNETURIES_MAP.get(signeturies));
			    para4.add(text15);
			    para4.setTextAlignment(TextAlignment.RIGHT);
			    doc.add(para4);
			  
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

private static final Log log=LogFactoryUtil.getLog(PensionFundPDF.class.getName()); 


}

