package com.nps.erm.util;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.FileUtil;
import com.nps.erm.constants.ErmConstantValues;
import com.nps.erm.model.ErmBatchInformation;
import com.nps.erm.model.ErmInformation;
import com.nps.erm.service.ErmBatchInformationLocalServiceUtil;
import com.nps.erm.service.ErmInformationLocalServiceUtil;

import java.io.File;

import npst.common.constant.NpstCommonConstant;
import npst.common.util.NpstCommonUtil;

public class CreateBatchPDFUtil {

	
	
	
	
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param batchInformationId
	 * @param batchType
	 * @return
	 */
	public static File createBatchPDF(long companyId,long userId,long batchInformationId) {
		try {
		 ErmBatchInformation batchInformation= ErmBatchInformationLocalServiceUtil.getErmBatchInformation(batchInformationId);
		 //String craName=NpstCommonUtil.getExpandoValue(companyId, userId, User.class.getName(), NpstCommonConstant.GRIVANCES_TYPE);
		ErmInformation ermInformation= ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(batchInformation.getErmInformationIds().split(StringPool.COMMA)[0]));
		if(ermInformation.getBatchType().equalsIgnoreCase(ErmConstantValues.NEW_BATCH_TYPE)) {
			log.info("########   New Batch Type     ########");
			return createBatchPDFForNewBatchType(companyId, userId, batchInformation,ermInformation.getPfmName());
		}else if(ermInformation.getBatchType().equalsIgnoreCase(ErmConstantValues.OLD_BATCH_TYPE)) {
			log.info("########   New Batch Type     ########");
			return createBatchPDFForOldBatchType(companyId, userId, batchInformation,ermInformation.getPfmName());
		}
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param batchInformation
	 * @return
	 */
	public static File createBatchPDFForOldBatchType(long companyId,long userId,ErmBatchInformation batchInformation,String craName) {
		
		File file=FileUtil.createTempFile("pdf");
		try  {  
	    PdfWriter writer = new PdfWriter(file);
	    PdfDocument pdfDoc= new PdfDocument(writer);
	    Document doc = new Document(pdfDoc); 
	    
	    Text text4= new Text("ERM Batch "+batchInformation.getErmBatchInformationId()+" \n "+ "Subject: Error rectification request received from"+craName +"\n ");
	   
	    text4.setBold();
	    text4.setFontColor(ColorConstants.DARK_GRAY);
	    Paragraph para4=new Paragraph(text4);
	    Text text5= new Text("1. The following details in respect of processing of rectification request received from NSDL may be noted: \n");
	    para4.add(text5);
	    doc.add(para4);
	    
	    float[] columnWidths2 = {10, 10};
	    Table table3 = new Table(UnitValue.createPercentArray(columnWidths2));

	    Cell cell1 = new Cell(1, 2).add(new Paragraph(craName)).setBold();
	    Cell cell2 = new Cell(1, 2).add(new Paragraph("Preceding batch of cases ")).setBold();
	    table3.addHeaderCell(cell1);
	    table3.addHeaderCell(cell2);
	    ErmBatchInformation previousBatch=null;
	    try {
	    previousBatch=ErmBatchInformationLocalServiceUtil.getErmBatchInformation(batchInformation.getPreviousBatchId());
	    }catch (Exception e) {
	    	previousBatch=ErmBatchInformationLocalServiceUtil.getErmBatchInformation(batchInformation.getErmBatchInformationId());
			log.error(e.getMessage());
		}
	    String[][] cellContent3 = {
			    {"Time period considered for the batch 	", NpstCommonUtil.getDateString(previousBatch.getBatchTimePeriodFrom())+" , "+NpstCommonUtil.getDateString(previousBatch.getBatchTimePeriodTo())},
			    {"Cut-off date taken for the receipt  ", NpstCommonUtil.getDateString(previousBatch.getCutOffDate())},
			    {"No. of new cases received ",previousBatch.getErmInformationIds().split(StringPool.COMMA).length+""},
			    {"Date of conveyance of NPST’s decision ", NpstCommonUtil.getDateString(previousBatch.getSubmissionDate())}};
	    for (int row = 0; row < 4; row++) {
	    for (int col = 0; col < 2; col++) {
	        table3.addCell(new Cell().add(new Paragraph(cellContent3[row][col])));    
	    }
	    }
	    doc.add(table3);
	    
	    Table table4 = new Table(UnitValue.createPercentArray(columnWidths2));
	    Cell cell3 = new Cell(1, 2).add(new Paragraph("Current Batch")).setBold();
	    table4.addHeaderCell(cell3);
	    
	    String[][] cellContent4 = {
			    {"Time period considered for the batch ( manual entry at the time of batch generation) 	", NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodFrom())+" , "+NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodTo())},
			    {"Cut-off date taken for the receipt ( manual entry at the time of batch generation) ", NpstCommonUtil.getDateString(batchInformation.getCutOffDate())},
			    {"No. of new cases received ",batchInformation.getErmInformationIds().split(StringPool.COMMA).length+""} };
	    for (int row = 0; row < 3; row++) {
	        for (int col = 0; col < 2; col++) {
	            table4.addCell(new Cell().add(new Paragraph(cellContent4[row][col])));   
	        }
	        }
	        doc.add(table4);
	        
	        Text text6=new Text("\n Those cases are considered in this batch for which grievance was raised by the subscriber before 18.01.2023 "
	        		+ "i.e. before the approval of revised SOP for ERM by"+craName+". This batch shall be processed based on the SOP followed before the said latest approval from "+craName+".\r\n\n" + 
	        		"\r\n\n").setBold().setFontColor(ColorConstants.DARK_GRAY);
	        Text text7=new Text("2. As per records, it may be noted that from 29.12.2022 to 04.01.2023,"
	        		+ " NPS Trust has received Three rectification requests (2-eNPS, 1-D-remit)##This will be fetched from the Annexure B form on 03.01.2023(##This can be multiple dates using comma. Last date should be followed by ‘&’)"
	        		+ " with necessary supplementary documents from subscribers through "+craName+". The details received from "+craName+", supplementary documents viz. self-declaration, bank account/credit card statement, NPS Statement of Transactions submitted by the subscriber"
	        		+ " and the case details provided by "+craName+" are placed as per details in the table below. NPS Trust Board approved SOP for carrying out error rectifications is placed at (1/c – 3/c) for reference. \r\n" + 
	        		"\r\n" + "  3.  The case was examined and following are the comments of the department: - \r\n" + "");
	        AreaBreak areabreak=new AreaBreak();
	        Paragraph para5=new Paragraph(text6);
	        para5.add(text7);
	        doc.add(para5);
	        doc.add(areabreak);
	        
	        doc.add(new Paragraph("Table1").setBold());
			Table table5 = new Table(new float[] {0.4f, 1f, 1f,1f,1f,1f,1f,1f}, true).setWidth(UnitValue.createPercentValue(100));
	        String[] headers1 = {"Sl No", "Subscriber Name","PRAN", "Type of ERM request (as per subscribers'declaration)",
	       		 "Amount to be rectified(In Rs.)","Request Raised with in stipulated time","Recommendation","Remarks"};
	        
	        for (int col = 0; col < 8; col++) {
	            table5.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
	        }
	        
	        int index=0;
	 	   for(String ermId:batchInformation.getErmInformationIds().split(StringPool.COMMA)) {
	 		  try {
	 			  ErmInformation ermInformation= ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
	 			   String[] cellContent2 = {index+"", ermInformation.getSubscriberName(),ermInformation.getPran(),ermInformation.getTransactionType(),ermInformation.getRectificationAmount()+"",ermInformation.getSubmissionStipulatedTime(),ermInformation.getRecommendation(),ermInformation.getRemark()};
	 			  for (int col = 0; col < 8; col++) {
	 			        table3.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(cellContent2[col])).setBackgroundColor(ColorConstants.YELLOW));        
	 			    }	
	 			  index=index+1;
	 		  }catch (Exception e) {
	 			log.error(e.getMessage());
	 		}
	 	   }
	 	  doc.add(table5);
	 	   
	   table5.complete();
	       Text text8= new Text("\nNote: The required documents have been submitted for all the cases mentioned above.\r\n" + " \r\n" + 
	       		"4. If approved, "+craName+" may be advised to do the rectifications as per our comments in the table 1 under intimation to NPS Trust. The draft email to "+craName+" is placed at \r\n" + 
	       		"( DFA/1532  ). \r\n" + " \r\n" + "Submitted for approval please. \r\n" + "");

	       Paragraph para6=new Paragraph(text8);  
	       doc.add(para6);
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
	 * @param companyId
	 * @param userId
	 * @param batchInformation
	 * @return
	 */
	public static File createBatchPDFForNewBatchType(long companyId,long userId,ErmBatchInformation batchInformation,String craName){  
		File file=FileUtil.createTempFile("pdf");
		try  {  
	    PdfWriter writer = new PdfWriter(file);
	    PdfDocument pdfDoc= new PdfDocument(writer);
	    Document doc = new Document(pdfDoc);  
	    Text text1 = new Text("\n Subject:  Error rectification request received from "+craName);
	    text1.setBold();
	    text1.setUnderline();

	    Paragraph para1 = new Paragraph("FOR NEW CASES");
	    para1.add(text1);
	    para1.add("\n\n 1.The ERM cases received from KCRA is presented for approval. \n");
	    doc.add(para1);
	  //  doc.add(para2);
   
	    log.info("Text added successfully..");
	    
	    float[] columnWidths = {50,50 };
	    Table table = new Table(UnitValue.createPercentArray(columnWidths));
	    
	    String[][] cellContent = { {"CRA",craName },
			    {"Batch considered for the weekly period ", NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodFrom())+" , "+NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodTo())},
			    {"Date of receipt of case",ErmUtil.getReceiptDates(batchInformation.getErmBatchInformationId())},
			    {"Cases received for approval", batchInformation.getErmInformationIds().split(StringPool.COMMA).length+""}};
	    for (int row = 0; row < 4; row++) {
	    for (int col = 0; col < 2; col++) {
	        table.addCell(new Cell().add(new Paragraph(cellContent[row][col])));  
	    }
	    }
	    doc.add(table);
	   // Paragraph blankSpace = new Paragraph(" ").setHeight(20);
	    Paragraph blankSpace = new Paragraph("\n");
	    doc.add(blankSpace);
	    
	    float[] columnWidths1 = {2,2,2,2,2,2,2,2};	
	    Table table2 = new Table(UnitValue.createPercentArray(columnWidths1));
	    String[] headers = {"Case No", "Subscriber Name and PRAN", "Type of ERM request by the subscriber",
	   		 "Amount to be rectified(In Rs.)","Request Raised with in stipulated time","Documents submitted by CRA within stipulated time","Recommendation","Remark"};
	    
	    for (int col = 0; col < 8; col++) {
	        table2.addCell(new Cell().add(new Paragraph(headers[col])).setBold());
	    }

	   for(String ermId:batchInformation.getErmInformationIds().split(StringPool.COMMA)) {
		  try {
			  ErmInformation ermInformation= ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
			   String[] cellContent2 = {ermInformation.getCaseNo(), ermInformation.getSubscriberName() +"   "+ermInformation.getPran() +" C/….-……",ermInformation.getTransactionType(),ermInformation.getRectificationAmount()+"",ermInformation.getStipulated(),
					    	ermInformation.getSubmissionStipulatedTime(),ermInformation.getRecommendation(),ermInformation.getRemark()};
			  for (int col = 0; col < 8; col++) {
			        table2.addCell(new Cell().add(new Paragraph(cellContent2[col])).setBackgroundColor(ColorConstants.YELLOW));        
			    }	
		  }catch (Exception e) {
			log.error(e.getMessage());
		}
	   }
	    doc.add(table2);
	    Text text3 = new Text("2. On 18.01.2023, "+craName+"  accorded approval to the revised SOP for ERM of eNPS and D-remit transactions, refer (“C/...”), based on which the above cases shall be processed.\r\n" + 
	    		"3. On approval, "+craName +"may be advised to do execute the rectifications as per the approval under intimation to the subscriber and NPS Trust. The refund of the rectified amount may be transferred only to the bank account of the subscriber registered in CRA system.\r\n" + 
	    		" \r\n" + "Proposed for approval. \r\n" + "");
	    Paragraph para3 = new Paragraph(text3);
	    doc.add(para3);
	    
	    AreaBreak areabreak=new AreaBreak();
	    doc.add(areabreak);
	    
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
	private static final Log log=LogFactoryUtil.getLog(CreateBatchPDFUtil.class.getName()); 
}
