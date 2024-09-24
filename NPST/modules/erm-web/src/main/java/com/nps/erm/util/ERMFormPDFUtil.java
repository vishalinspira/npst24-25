package com.nps.erm.util;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.nps.erm.model.ErmInformation;
import com.nps.erm.service.ErmInformationLocalServiceUtil;

import java.io.File;
import java.io.IOException;

import npst.common.util.NpstCommonUtil;


public class ERMFormPDFUtil {
	

public static File createFormPDF(long ermId) throws IOException {

		
	File file=null;
		try  {  
			 file=FileUtil.createTempFile("formpdf");
			 ErmInformation ermInformation=  ErmInformationLocalServiceUtil.getErmInformation(ermId);
			 
			   PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
			    Text text1 = new Text("Section A: Case Summary: \n\n");
			    Paragraph para1 = new Paragraph(text1).setBold().setTextAlignment(TextAlignment.CENTER);
			    doc.add(para1);
                Text text4 = new Text("a) The Subscriber made a contribution of Rs. "+ermInformation.getTransactedAmount()+" in their PRAN "+ermInformation.getPran()+" on "+NpstCommonUtil.getDateString(ermInformation.getTransactionDate())+" through "+ermInformation.getTransactionMode()+".");
                Paragraph para2 = new Paragraph(text4);
			    Text text5 = new Text("\n b) The contribution was settled in CRA system i.e., units got credited in the said PRAN on "+NpstCommonUtil.getDateString(ermInformation.getTransactionSettlementDate())+ ".");
			    para2.add(text5);
			    Text text6 = new Text("\n c) The subscriber has raised a grievance request in CRA grievance system through "+ermInformation.getRectificationRequestMode()+" dated "+NpstCommonUtil.getDateString(ermInformation.getRectificationDate())+" for rectification of erroneous contribution stating that they mistakenly remitted Rs. "+ermInformation.getRemittedAmount()+" on "+NpstCommonUtil.getDateString(ermInformation.getRemittedDate())+" in Tier I "+ermInformation.getTierType()+". Hence subscriber has requested to refund the amount of Rs. "+ermInformation.getTransferAmount()+" "+ermInformation.getTransferAmount1());
			    para2.add(text6); 
			    Text text7 =new Text ("\n d) The subscriber has provided "+ermInformation.getTransferAmount2()+" complete documentation on "+NpstCommonUtil.getDateString(ermInformation.getDocumentationsDate()));
			    para2.add(text7); 
			    Text text8 = new Text("\n e) As per the request, error rectification needs to be carried out for Rs "+ermInformation.getRectificationAmount());
			    para2.add(text8);
			    doc.add(para2);
			    Text text10 = new Text("\n \n Section B: Transaction Details");
			    Paragraph para3 = new Paragraph(text10).setBold().setTextAlignment(TextAlignment.CENTER);
			    doc.add(para3);
			   
			  
			    
			   
			    Table table1 = new Table(new float[] {1f, 4f, 4f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
			    String[] headers1 = {"Sr No", "Particulars","Details"};
			    for (int col = 0; col < 3; col++) {
		            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
		            
		        }
			    doc.add(table1);
			   
			    
			    String[][] cellContent1 =	{
			    		{"1","Case No",ermInformation.getCaseNo()},
			    		{"2","PRAN",ermInformation.getPran()},
			    		{"3","Name of the Subscriber",ermInformation.getSubscriberName()},
			    		{"4","Transaction Mode (eNPS/D-Remit)",ermInformation.getTransactionMode()},
			    		{"5","Transaction Date",NpstCommonUtil.getDateString(ermInformation.getTransactionDate())},
			    		{"6","eNPS Order ID/Ref NO (ACK_ID)",ermInformation.getEnpsOrderId()},
			    		{"7","Transaction Settlement Date on CRA",NpstCommonUtil.getDateString(ermInformation.getTransactionSettlementDate())},
			    		{"8","Amount Transacted in Rs.",ermInformation.getTransactedAmount()+""},
			    		{"9","Tier Type (Tier I / Tier II)",ermInformation.getTierType()},
			    		{"10","Type of Rectification",ermInformation.getRectificationType()},
			    		{"11","Amount required to be rectified in Rs.",ermInformation.getRectificationAmount()+""},
			    		{"12","Rectification Request Mode (CGMS/Direct Mail-Letter from sub/Req from NPST-PFRDA)",ermInformation.getRectificationRequestMode()},
			    		{"13","Rectification Request Received Date",NpstCommonUtil.getDateString(ermInformation.getRectificationRequestDate())}
			    				
			    };
			    
			    for (int row = 0; row < 13; row++) {
			        for (int col = 0; col < 3; col++) {
			            table1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
			            
			        }
			        }
			    
			    table1.complete();
			  doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			    Text text9 = new Text("\n\n Section C: Details for Reference");
			    Paragraph para4 = new Paragraph(text9).setBold().setTextAlignment(TextAlignment.CENTER);
			    doc.add(para4);
			    Text text11 = new Text("\n \n a) Grievance received by CRA in CGMS / mail on (date):"+ NpstCommonUtil.getDateString(ermInformation.getGrievanceReceivedDate()));
			    Paragraph para5 = new Paragraph(text11);
			    Text text12 = new Text("\n \n b) Grievance Text raised by Subscriber:");
			    para5.add(text12);
			    Text text13 = new Text("\n \n "+ermInformation.getGrievanceText());
			    para5.add(text13);
			    doc.add(para5);
			  
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

private static final Log log=LogFactoryUtil.getLog(ERMFormPDFUtil.class.getName()); 


}



