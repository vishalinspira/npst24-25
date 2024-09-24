package com.nps.erm.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.nps.erm.constants.ErmInformationPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = {
		"javax.portlet.name="
				+ErmInformationPortletKeys.ERMINFORMATION ,
		"mvc.command.name=/erm/batch/downloadygy",
}, service = MVCResourceCommand.class)
public class BatchDownloadResorceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
//		File file=mains();
//		 
//			try {
//				InputStream inputStream = new FileInputStream(file);
//				PortletResponseUtil.sendFile(resourceRequest, resourceResponse,file.getName(), inputStream, ContentTypes.APPLICATION_TEXT);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		return false;
	}
	
	/*
	 * public static File mains() { File file=FileUtil.createTempFile(".pdf"); try {
	 * //String file =
	 * "C:\\Users\\SaumyaRastogi\\OneDrive - Inspira Enterprise\\Desktop\\Newpdf.pdf"
	 * ;
	 * 
	 * 
	 * log.info("file   :  "+file);
	 * 
	 * PdfWriter writer = new PdfWriter(file); PdfDocument pdfDoc= new
	 * PdfDocument(writer); Document doc = new Document(pdfDoc); Text text1 = new
	 * Text("\n Subject:  Error rectification request received from KCRA");
	 * text1.setBold(); text1.setUnderline();
	 * 
	 * Paragraph para1 = new Paragraph("FOR NEW CASES"); para1.add(text1); para1.
	 * add("\n\n 1.The ERM cases received from KCRA is presented for approval. \n");
	 * doc.add(para1); // doc.add(para2);
	 * 
	 * 
	 * 
	 * log.info("Text added successfully..");
	 * 
	 * float[] columnWidths = {50,50 }; Table table = new
	 * Table(UnitValue.createPercentArray(columnWidths));
	 * 
	 * 
	 * String[][] cellContent = { {"CRA", "KCRA"},
	 * {"Batch considered for the weekly period ", "17.01.2023 to 25.01.2023"},
	 * {"Date of receipt of case","24.01.2023, 25.01.2023"},
	 * {"Cases received for approval", "4"} }; for (int row = 0; row < 4; row++) {
	 * for (int col = 0; col < 2; col++) { table.addCell(new Cell().add(new
	 * Paragraph(cellContent[row][col])));
	 * 
	 * } } doc.add(table); // Paragraph blankSpace = new
	 * Paragraph(" ").setHeight(20); Paragraph blankSpace = new Paragraph("\n");
	 * doc.add(blankSpace);
	 * 
	 * float[] columnWidths1 = {2,2,2,2,2,2,2}; Table table2 = new
	 * Table(UnitValue.createPercentArray(columnWidths1)); String[] headers =
	 * {"Case No", "Subscriber Name and PRAN",
	 * "Type of ERM request by the subscriber",
	 * "Amount to be rectified(In Rs.)","Request Raised with in stipulated time",
	 * "Documents submitted by CRA within stipulated time","Recommendation"};
	 * 
	 * for (int col = 0; col < 7; col++) { table2.addCell(new Cell().add(new
	 * Paragraph(headers[col])).setBold());
	 * 
	 * }
	 * 
	 * String[] cellContent2 = { "0001",
	 * "MUSKAN GUPTA 400080358053 C/….-……","Refund","50,000","Yes",
	 * "Will be calculated from pt 7 and Point d’ from annexure B Yes"
	 * ,"May be approved"};
	 * 
	 * for (int col = 0; col < 7; col++) { table2.addCell(new Cell().add(new
	 * Paragraph(cellContent2[col])).setBackgroundColor(ColorConstants.YELLOW));
	 * 
	 * }
	 * 
	 * doc.add(table2);
	 * 
	 * 
	 * Text text3 = new Text(" <add line option” Text Field  for remarks”>\r\n" +
	 * "2. On 18.01.2023, PFRDA accorded approval to the revised SOP for ERM of eNPS and D-remit transactions, refer (“text field”), based on which the above cases shall be processed.\r\n"
	 * +
	 * "3. On approval, KCRA may be advised to do execute the rectifications as per the approval under intimation to the subscriber and NPS Trust. The refund of the rectified amount may be transferred only to the bank account of the subscriber registered in CRA system.\r\n"
	 * + " \r\n" + "Proposed for approval. \r\n" + ""); Paragraph para3 = new
	 * Paragraph(text3); doc.add(para3);
	 * 
	 * AreaBreak areabreak=new AreaBreak(); doc.add(areabreak);
	 * 
	 * 
	 * Text text4= new Text("FOR OLD CASES" +
	 * "\n ERM Batch 125 ## Last Bach Processed +1 (New+Old) \n " +
	 * "Subject: Error rectification request received from PCRA ## Auto from CRA login \n "
	 * );
	 * 
	 * text4.setBold(); text4.setFontColor(ColorConstants.DARK_GRAY); Paragraph
	 * para4=new Paragraph(text4); Text text5= new
	 * Text("1. The following details in respect of processing of rectification request received from NSDL may be noted: \n"
	 * ); para4.add(text5); doc.add(para4);
	 * 
	 * float[] columnWidths2 = {10, 10}; Table table3 = new
	 * Table(UnitValue.createPercentArray(columnWidths2));
	 * 
	 * Cell cell1 = new Cell(1, 2) .add(new
	 * Paragraph("PCRA ##This Table data will be auto populated")).setBold(); Cell
	 * cell2 = new Cell(1, 2) .add(new
	 * Paragraph("Preceding batch of cases ")).setBold();
	 * table3.addHeaderCell(cell1); table3.addHeaderCell(cell2);
	 * 
	 * String[][] cellContent3 = { {"Time period considered for the batch 	",
	 * "27.12.2022 to 28.12.2022 "}, {"Cut-off date taken for the receipt  ",
	 * "28.12.2022"}, {"No. of new cases received ","2"},
	 * {"Date of conveyance of NPST’s decision ",
	 * "This is date on which  triggers response \n 02.01.2023"} }; for (int row =
	 * 0; row < 4; row++) { for (int col = 0; col < 2; col++) { table3.addCell(new
	 * Cell().add(new Paragraph(cellContent3[row][col])));
	 * 
	 * } } doc.add(table3);
	 * 
	 * Table table4 = new Table(UnitValue.createPercentArray(columnWidths2)); Cell
	 * cell3 = new Cell(1, 2) .add(new Paragraph("Current Batch")).setBold();
	 * table4.addHeaderCell(cell3);
	 * 
	 * String[][] cellContent4 = {
	 * {"Time period considered for the batch ( manual entry at the time of batch generation) 	"
	 * , "29.12.2022 to 04.01.2023 "},
	 * {"Cut-off date taken for the receipt ( manual entry at the time of batch generation) "
	 * , "4.01.2023 "}, {"No. of new cases received ","3"} }; for (int row = 0; row
	 * < 3; row++) { for (int col = 0; col < 2; col++) { table4.addCell(new
	 * Cell().add(new Paragraph(cellContent4[row][col])));
	 * 
	 * } } doc.add(table4);
	 * 
	 * Text text6=new
	 * Text("\n Those cases are considered in this batch for which grievance was raised by the subscriber before 18.01.2023 "
	 * +
	 * "i.e. before the approval of revised SOP for ERM by PFRDA. This batch shall be processed based on the SOP followed before the said latest approval from PFRDA.\r\n\n"
	 * + "## Auto fetch from system\r\n\n").setBold().setFontColor(ColorConstants.
	 * DARK_GRAY); Text text7=new
	 * Text("2. As per records, it may be noted that from 29.12.2022 to 04.01.2023,"
	 * +
	 * " NPS Trust has received Three rectification requests (2-eNPS, 1-D-remit)##This will be fetched from the Annexure B form on 03.01.2023(##This can be multiple dates using comma. Last date should be followed by ‘&’)"
	 * +
	 * " with necessary supplementary documents from subscribers through PCRA. The details received from PCRA, supplementary documents viz. self-declaration, bank account/credit card statement, NPS Statement of Transactions submitted by the subscriber"
	 * +
	 * " and the case details provided by PCRA are placed as per details in the table below. NPS Trust Board approved SOP for carrying out error rectifications is placed at (1/c – 3/c) for reference. \r\n"
	 * + "\r\n" +
	 * "  3.  The case was examined and following are the comments of the department: - \r\n"
	 * + "");
	 * 
	 * Paragraph para5=new Paragraph(text6); para5.add(text7); doc.add(para5);
	 * doc.add(areabreak);
	 * 
	 * doc.add(new Paragraph("Table1").setBold()); Table table5 = new Table(new
	 * float[] {0.4f, 1f, 1f,1f,1f,1f,1f,1f}, true)
	 * .setWidth(UnitValue.createPercentValue(100)); String[] headers1 = {"Sl No",
	 * "Subscriber Name","PRAN",
	 * "Type of ERM request (as per subscribers'declaration)",
	 * "Amount to be rectified(In Rs.)","Request Raised with in stipulated time",
	 * "Recommendation (## Static text will be edited by NPST user after downloading file or with in SAS)"
	 * ,"Remarks"};
	 * 
	 * for (int col = 0; col < 8; col++) { table5.addCell(new Cell().add(new
	 * Paragraph(headers1[col])).setBold());
	 * 
	 * }
	 * 
	 * doc.add(table5); String[][] cellContent5 = { {"1",
	 * "SHRI JITENDRA KUMAR SINGH (C/3415 -C/3423)  # this wil be edited by NPST User after downloading in doc or within SAS"
	 * ,"110151026245",
	 * "Refund","50,000","Y \n This will be calculated from point no 5 and 13\r\n" +
	 * "In input file submitted by maker\r\n" +
	 * "As YES (if raised within 7 days) or No\r\n" + "(pt13-pt5)\r\n",
	 * "May be approved.\r\n" +
	 * "PCRA may be requested to refund the rectification amount to the bank account of the subscriber registered in CRA system and subsequently inform the status of the request to the subscriber.\r\n"
	 * , "" }, {"2", "MR. ASHISH KUMAR RAVISHANKAR SUTHAR (C/3424 - C/3436) ",
	 * "110141713444 ","Transfer from Tier I to Tier II","31,000",
	 * "Y","May be approved.\r\n" + " \r\n" +
	 * "PCRA may be requested to transfer the contributed amount from Tier I to Tier II and subsequently inform the status of the request to the subscriber.\r\n"
	 * , ""}, {"3","SHRI DEEPAK SHARMA\r\n" + "(C/3437-C/3444)",
	 * "110164531160","Refund ","70,009","Y","May be approved.\r\n" + " \r\n" +
	 * "PCRA may be requested to refund the rectification amount to the bank account of the subscriber registered in CRA system and subsequently inform the status of the request to the subscriber."
	 * , ""} }; for (int row = 0; row < 3; row++) { for (int col = 0; col < 8;
	 * col++) { table5.addCell(new
	 * Cell().setTextAlignment(TextAlignment.CENTER).add(new
	 * Paragraph(cellContent5[row][col])).setBackgroundColor(ColorConstants.YELLOW))
	 * ;
	 * 
	 * } } table5.complete(); Text text8= new
	 * Text("\nNote: The required documents have been submitted for all the cases mentioned above.\r\n"
	 * + " \r\n" +
	 * "4. If approved, PCRA may be advised to do the rectifications as per our comments in the table 1 under intimation to NPS Trust. The draft email to PCRA is placed at \r\n"
	 * + "( DFA/1532  ). \r\n" + " \r\n" + "Submitted for approval please. \r\n" +
	 * "");
	 * 
	 * Paragraph para6=new Paragraph(text8); doc.add(para6); doc.close(); }
	 * 
	 * catch (FileNotFoundException e) { log.error("error while create PDF "+
	 * e.getMessage()); } return file; }
	 */
	private static final Log log=LogFactoryUtil.getLog(BatchDownloadResorceCommand.class.getName());

}
