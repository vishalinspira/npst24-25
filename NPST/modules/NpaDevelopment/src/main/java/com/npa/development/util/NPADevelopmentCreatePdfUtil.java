package com.npa.development.util;

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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;

public class NPADevelopmentCreatePdfUtil {

public static File craeteNPAFormPDF(String reportDueDate,String company,String date1,JSONArray jsonArray) {

		log.info("reportDueDate :" +reportDueDate+"  date1:  "+date1+"   jsonarray:   "+jsonArray);
		File file=null;
		try  {  

				 file=FileUtil.createTempFile("Developmentindefaultsecurities.pdf");
			   PdfWriter writer = new PdfWriter(file);
			    PdfDocument pdfDoc= new PdfDocument(writer);
			    Document doc = new Document(pdfDoc); 
			    Text text1 = new Text("Development in Default securities");
			    text1.setBold();
			    Paragraph para = new Paragraph(text1);
	            doc.add(para);
	            doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));
	            Text text2 = new Text("\n Report Due Date");
	            Paragraph para1 = new Paragraph((text2).setBold());
	            Text text3 = new Text("\n "+reportDueDate);
	            para1.add(text3);
	    	    Text text4 = new Text("\n\n "+company);
	    	    para1.add(text4);
	    	    Text text5 = new Text("\n\n The script wise details of development in default securities and the measures taken by PF for recovery of dues are placed as under as on "+date1);
	    	    para1.add(text5);
	            doc.add(para1);
	            doc.add(new Paragraph(""));
	            
	            Table table = new Table(new float[] {0.6f, 2.5f, 2.5f,2.5f}, true)
		                .setWidth(UnitValue.createPercentValue(100));
	            
	            String[] headers = {"Sr.No", "Name of Security","Legal Case details", "Current Status"};
			    for (int col = 0; col < 4; col++) {
		            table.addCell(new Cell().add(new Paragraph(headers[col])).setBold());
		            
		        }
			
	            doc.add(table);
	           
	            
	            String[][] cellContent1 =	new String[jsonArray.length()][4];

			    
	            for(int row=0; row<jsonArray.length();row++){
	            	JSONObject object = jsonArray.getJSONObject(row);
	            	
	            
			    	cellContent1[row][0]=row+1+"";
			    	cellContent1[row][1]=object.getString("nameOfSecurity");
			    	cellContent1[row][2]=object.getString("legalCaseDetails");
			    	cellContent1[row][3]=object.getString("currentStatus");
	            	}
			    

//			    
//			    for (int row = 0; row < executive_summaries.size(); row++) {
//			        for (int col = 0; col < 6; col++) {
//			        	table13.addCell(new Cell().add(new Paragraph(cellContent131[row][col])));
//			            
//			        }
//			        }
	            
	            
//	            String[][] cellContent =	{
//			    		{"1.","Security Name1","test","Active"},
//			    		{"2.","Security Name2","test","Inactive"}
//        	  };
	            
	            for (int row = 0; row < jsonArray.length(); row++) {
	            	
	            	//log.info("cellContent1 " +cellContent1[row][1] != null && cellContent1[row][1].isEmpty() );
	            	//log.info("cellContent2 " +cellContent1[row][2] != null && cellContent1[row][2].isEmpty());
	            	//log.info("cellContent3 " +cellContent1[row][3] != null && cellContent1[row][3].isEmpty());
	            	if(cellContent1[row][1] != null && cellContent1[row][1].isEmpty()  && cellContent1[row][2] != null && cellContent1[row][2].isEmpty()  && cellContent1[row][3] != null && cellContent1[row][3].isEmpty() )
                   {
                	   log.info("Row is null " +row);
	            	}
                   else
                   {
			        for (int col = 0; col < 4; col++) {
			        				        
			            table.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
			        }   
			        }
			        }
			        
	            table.complete();
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

private static final Log log=LogFactoryUtil.getLog(NPADevelopmentCreatePdfUtil.class.getName()); 


}
