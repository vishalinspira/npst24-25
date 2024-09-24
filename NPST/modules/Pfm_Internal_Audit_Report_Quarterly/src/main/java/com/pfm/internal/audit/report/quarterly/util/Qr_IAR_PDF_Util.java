package com.pfm.internal.audit.report.quarterly.util;


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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

public class Qr_IAR_PDF_Util {
	
	
	private static JSONObject getIAR_JSON_data(JSONObject iarDetails) {
		
		
		JSONObject jsonObject=JSONFactoryUtil.createJSONObject();
		JSONObject keyDetails=PMFIARUtil.getloopingContexOfDetailedAuditReport();
		try{
			if(Validator.isNotNull(iarDetails)) {
		
			//JSONObject iarDetails=JSONFactoryUtil.createJSONObject(pfm_qr_iar.getMaker_comment_details());

			//JSONObject iarScrutinyDetails=null;
//			if(Validator.isNotNull(iarScrutinyDetails)) {
//				try {
//				iarScrutinyDetails=JSONFactoryUtil.createJSONObject(qr_IARScrutiny.getNps_comments());
//				}catch (Exception e) {
//					_log.error("Error while parsing iarScrutinyDetails: "+e.getMessage());
//				}
//			}
			
			for(String key : keyDetails.keySet()) {
				try {
				long size = keyDetails.getLong(key);
				JSONObject observations=iarDetails.getJSONObject("observations_"+key+"_");
				JSONObject managemants=iarDetails.getJSONObject("management_"+key+"_");
				//JSONObject npst_remarks=null;
				//try {
				// npst_remarks=iarScrutinyDetails.getJSONObject(key+"_nps_rem_");
					// npst_remarks=iarScrutinyDetails.getJSONObject(key);
				//}catch (Exception e) {
					//log.error("Error while parsing iarScrutinyDetails: "+e.getMessage());
				//}
				
				for(int i=1;i<=size;i++) {
					try {
					if(Validator.isNotNull(observations)) {
					jsonObject.put("observations_"+key+"_"+i, observations.get("observations_"+key+"_"+i));
					}if(Validator.isNotNull(managemants)) {
					jsonObject.put("management_"+key+"_"+i, managemants.get("management_"+key+"_"+i));
					}
//					if(Validator.isNotNull(npst_remarks)) {
//					jsonObject.put(key+"_nps_rem_"+i, npst_remarks.get(key+"_nps_rem_"+i));
//					}
				}catch (Exception e) {
					log.error("error while getIAR_JSON_data:  "+e.getMessage());
				}
				}
				}catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			}}catch (Exception e) {
			log.error(e.getMessage());
		}
						
		return jsonObject;
		
		
	}
	
	
public static File Quarterly_IAR_PFMPDF(String dueDate,JSONObject jsonObject,JSONArray summariesArray) {

		//JSONObject jsonObject=null;
		File file=null;
		try  {  
			jsonObject=getIAR_JSON_data(jsonObject);
			//jsonObject=PMFIARUtil.getIAR_JSON_data(reportUploadlogId);
			 file=FileUtil.createTempFile("InternalAuditReport.pdf");
			PdfWriter writer = new PdfWriter(file);
		    PdfDocument pdfDoc= new PdfDocument(writer);
		    Document doc = new Document(pdfDoc); 
		    Text text1 = new Text("Quarterly Internal Audit Report"); 
		    text1.setBold();
		    Paragraph para = new Paragraph(text1);
            doc.add(para);
            doc.add(new Paragraph().setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f)).setMarginTop(5f).setWidth(UnitValue.createPercentValue(100)));
            Text text2 = new Text("\nReport Due Date");
            Paragraph para1 = new Paragraph((text2).setBold());
            Text text3 = new Text("\n "+dueDate);
            para1.add(text3);
            doc.add(para1);
            doc.add(new Paragraph(""));
            int colSize=4;
           
            Table table1 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
            doc.add(table1);
		    String[] headers1 = {"S.N", "Broad Description","Sampling", "Sub- description","Observations","Management Response"};
		    log.info("call 1");
		    for (int col = 0; col < headers1.length; col++) {
	            table1.addCell(new Cell().add(new Paragraph(headers1[col])).setBold());
	            
	        }
		    log.info("call 2");
		    table1.complete();
		    try { 
		    Table table1_1 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table1_1);
		    Cell cell1 = new Cell(5,1).add(new Paragraph("1.").setBold());
		    Cell cell2 = new Cell(5,1).add(new Paragraph("Board Meeting").setBold());
          
            table1_1.addCell(cell1);
            table1_1.addCell(cell2);
            log.info("call 3");
		    String[][] cellContent1 =	{
		       		{"100%","Composition of Board",jsonObject.getString("observations_1_1"),jsonObject.getString("management_1_1"),jsonObject.getString("1_nps_rem_1")},
		    		{"100%","Frequency of meeting",jsonObject.getString("observations_1_2"),jsonObject.getString("management_1_2"),jsonObject.getString("1_nps_rem_2")},
		    		{"100%","Minutes of meeting",jsonObject.getString("observations_1_3"),jsonObject.getString("management_1_3"),jsonObject.getString("1_nps_rem_3")},
		    		{"100%","Recommendations",jsonObject.getString("observations_1_4"),jsonObject.getString("management_1_4"),jsonObject.getString("1_nps_rem_4")},
		    		{"100%","Delegation of Authority",jsonObject.getString("observations_1_5"),jsonObject.getString("management_1_5"),jsonObject.getString("1_nps_rem_5")}
            };
		    log.info("call 4");
		    for (int row = 0; row < cellContent1.length; row++) { 
		    	try {
		    	//	log.info("celccontent 1:  "+cellContent1[row]);
		        for (int col = 0; col < colSize; col++) {
		        	log.info("celccontent: "+row +"   : "+col+ " "+  cellContent1[row][col]);
		            table1_1.addCell(new Cell().add(new Paragraph(cellContent1[row][col])));
		            
		        }
		    	}catch (Exception e) {
					log.info("error in table 1 "+e);
				}
		        }
		    log.info("call 5");
		    table1_1.complete();
		    log.info("call 6");
		   
            }catch (Exception e) {
				log.info("error while table 1 completion : "+e.getMessage());
			}
            try {
		    
		    Table table2 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table2);
		    Cell cell2_1 = new Cell(7,1).add(new Paragraph("2.").setBold());
		    Cell cell2_2 = new Cell(7,1).add(new Paragraph("Investment Operational Manual/Procedure").setBold());
		    table2.addCell(cell2_1);
		    table2.addCell(cell2_2);
		    String[][] cellContent2 =	{
		    	
		    		{"100%","To be approved by Board / IC",jsonObject.getString("observations_2_1"),jsonObject.getString("management_2_1"),jsonObject.getString("2_nps_rem_1")},
		    		{"100%","Amendments, if any, to be approved by the board",jsonObject.getString("observations_2_2"),jsonObject.getString("management_2_2"),jsonObject.getString("2_nps_rem_2")},
		    		{"100%","Frequency of review",jsonObject.getString("observations_2_3"),jsonObject.getString("management_2_3"),jsonObject.getString("2_nps_rem_3")},
		    		{"100%","Procedure for credit appraisal and market risk",jsonObject.getString("observations_2_4"),jsonObject.getString("management_2_4"),jsonObject.getString("2_nps_rem_4")},
		    		{"100%","Procedure for security documents execution",jsonObject.getString("observations_2_5"),jsonObject.getString("management_2_5"),jsonObject.getString("2_nps_rem_5")},
		    		{"100%","Income recognition policy - accruals",jsonObject.getString("observations_2_6"),jsonObject.getString("management_2_6"),jsonObject.getString("2_nps_rem_6")},
		    		{"100%","Periodic credit review",jsonObject.getString("observations_2_7"),jsonObject.getString("management_2_7"),jsonObject.getString("2_nps_rem_7")}
		    	};	
		    for (int row = 0; row < 7; row++) {
		    	
		    	try {
		        for (int col = 0; col < colSize; col++) {
		            table2.addCell(new Cell().add(new Paragraph(cellContent2[row][col])));		            
		        }
		    	}catch (Exception e) {
					log.info("error in table 2 "+e.getMessage());
				}
		        }
		    table2.complete();
            }catch (Exception e) {
				log.info("error while table 2 completion : "+e.getMessage());
			}
            try {
		    
		    Table table3 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table3);
		    Cell cell3_1 = new Cell(5,1).add(new Paragraph("3.").setBold());
		    Cell cell3_2 = new Cell(5,1).add(new Paragraph("Investment Committee (IC)").setBold());
		    table3.addCell(cell3_1);
		    table3.addCell(cell3_2);
		    
		    String[][] cellContent3 =	{		 
		   		    {"100%","Composition of IC",jsonObject.getString("observations_3_1"),jsonObject.getString("management_3_1"),jsonObject.getString("3_nps_rem_1")},
		    		{"100%","Frequency of meeting",jsonObject.getString("observations_3_2"),jsonObject.getString("management_3_2"),jsonObject.getString("3_nps_rem_2")},
		    		{"100%","Minutes of meeting",jsonObject.getString("observations_3_3"),jsonObject.getString("management_3_3"),jsonObject.getString("3_nps_rem_3")},
		    		{"100%","Recommendations",jsonObject.getString("observations_3_4"),jsonObject.getString("management_3_4"),jsonObject.getString("3_nps_rem_4")},
		    		{"100%","Delegation of Authority",jsonObject.getString("observations_3_5"),jsonObject.getString("management_3_5"),jsonObject.getString("3_nps_rem_5")}    		
	    	};	
	         for (int row = 0; row < 5; row++) {
			    	try {
	            for (int col = 0; col < colSize; col++) {
	              table3.addCell(new Cell().add(new Paragraph(cellContent3[row][col])));
	            
	        }
			    	}catch (Exception e) {
						log.info("error in table 3 "+e.getMessage());
					}
	        }
	    table3.complete();
            }catch (Exception e) {
				log.info("error while table 3 completion : "+e.getMessage());
			}
            try {
	    
	    Table table4 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
                .setWidth(UnitValue.createPercentValue(100));
	    doc.add(table4);
	    Cell cell4_1 = new Cell(8,1).add(new Paragraph("4.").setBold());
	    Cell cell4_2 = new Cell(8,1).add(new Paragraph("Investment Policy(IP)").setBold());
	    table4.addCell(cell4_1);
	    table4.addCell(cell4_2);
	    
	    String[][] cellContent4 =	{	
	  			    		  
		    		{ "100%","Approved by BOD",jsonObject.getString("observations_4_1"),jsonObject.getString("management_4_1"),jsonObject.getString("4_nps_rem_1")},
		    		{ "100%","Valuation principles",jsonObject.getString("observations_4_2"),jsonObject.getString("management_4_2"),jsonObject.getString("4_nps_rem_2")},
		    		{ "100%","Adherence to regulatory guidelines for valuation",jsonObject.getString("observations_4_3"),jsonObject.getString("management_4_3"),jsonObject.getString("4_nps_rem_3")},
		    		{ "100%","Frequency of review",jsonObject.getString("observations_4_4"),jsonObject.getString("management_4_4"),jsonObject.getString("4_nps_rem_4")},
		    		{ "100%","Liquidity",jsonObject.getString("observations_4_5"),jsonObject.getString("management_4_5"),jsonObject.getString("4_nps_rem_5")},
		    		{ "100%","Prudential norms",jsonObject.getString("observations_4_6"),jsonObject.getString("management_4_6"),jsonObject.getString("4_nps_rem_6")},
		    		{ "100%","Exposure limits",jsonObject.getString("observations_4_7"),jsonObject.getString("management_4_7"),jsonObject.getString("4_nps_rem_7")},
		    		{ "100%","Stop loss limits in securities trading",jsonObject.getString("observations_4_8"),jsonObject.getString("management_4_8"),jsonObject.getString("4_nps_rem_8")}
		    		
	        };	
	    	for (int row = 0; row < 8; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table4.addCell(new Cell().add(new Paragraph(cellContent4[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 4 "+e.getMessage());
				}
	    	}
	    	table4.complete();
            }catch (Exception e) {
				log.info("error while table 4 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table5 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table5);
		    Cell cell5_1 = new Cell(4,1).add(new Paragraph("5.").setBold());
		    Cell cell5_2 = new Cell(4,1).add(new Paragraph("Risk Management Committee").setBold());
		    table5.addCell(cell5_1);
		    table5.addCell(cell5_2);
		    
		    String[][] cellContent5 =	{	
		    		   		
		    		{ "100%","Composition of Risk Committee",jsonObject.getString("observations_5_1"),jsonObject.getString("management_5_1"),jsonObject.getString("5_nps_rem_1")},
		    		{ "100%","Frequency of meeting",jsonObject.getString("observations_5_2"),jsonObject.getString("management_5_2"),jsonObject.getString("5_nps_rem_2")},
		    		{ "100%","Minutes of meeting",jsonObject.getString("observations_5_3"),jsonObject.getString("management_5_3"),jsonObject.getString("5_nps_rem_3")},
		    		{ "100%","Recommendations",jsonObject.getString("observations_5_4"),jsonObject.getString("management_5_4"),jsonObject.getString("5_nps_rem_4")}
		    };	
	    	for (int row = 0; row < 4; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table5.addCell(new Cell().add(new Paragraph(cellContent5[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 5 "+e.getMessage());
				}
	    	}
	    	table5.complete();
            }catch (Exception e) {
				log.info("error while table 5 completion : "+e.getMessage());
			}
            try {
		
	    	Table table6 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table6);
		    Cell cell6_1 = new Cell(13,1).add(new Paragraph("6.").setBold());
		    Cell cell6_2 = new Cell(13,1).add(new Paragraph("Risk Management Policy").setBold());
		    table6.addCell(cell6_1);
		    table6.addCell(cell6_2);
		    
		    String[][] cellContent6 =	{	
		    		 		
		    		{"100%","Approved by BOD",jsonObject.getString("observations_6_1"),jsonObject.getString("management_6_1"),jsonObject.getString("6_nps_rem_1")},
		    		{ "100%","Frequency of review",jsonObject.getString("observations_6_2"),jsonObject.getString("management_6_2"),jsonObject.getString("6_nps_rem_2")},
		    		{ "100%","Disaster recovery strategy",jsonObject.getString("observations_6_3"),jsonObject.getString("management_6_3"),jsonObject.getString("6_nps_rem_3")},
		    		{ "100%","Business Continuity Plan",jsonObject.getString("observations_6_4"),jsonObject.getString("management_6_4"),jsonObject.getString("6_nps_rem_4")},
		    		{ "100%","IT System Audit",jsonObject.getString("observations_6_5"),jsonObject.getString("management_6_5"),jsonObject.getString("6_nps_rem_5")},
		    		{ "100%","Data Integrity",jsonObject.getString("observations_6_6"),jsonObject.getString("management_6_6"),jsonObject.getString("6_nps_rem_6")},
		    		{ "100%","Operational risk management",jsonObject.getString("observations_6_7"),jsonObject.getString("management_6_7"),jsonObject.getString("6_nps_rem_7")},
		    		{ "100%","Market risk management",jsonObject.getString("observations_6_8"),jsonObject.getString("management_6_8"),jsonObject.getString("6_nps_rem_8")},
		    		{ "100%","Credit Risk Management",jsonObject.getString("observations_6_9"),jsonObject.getString("management_6_9"),jsonObject.getString("6_nps_rem_9")},
		    		{ "100%","Counter party management",jsonObject.getString("observations_6_10"),jsonObject.getString("management_6_10"),jsonObject.getString("6_nps_rem_10")},
		    		{ "100%","Brokers Review",jsonObject.getString("observations_6_11"),jsonObject.getString("management_6_11"),jsonObject.getString("6_nps_rem_11")},
		    		{ "100%","Employee dealing Guidelines",jsonObject.getString("observations_6_12"),jsonObject.getString("management_6_12"),jsonObject.getString("6_nps_rem_12")},
		    		{ "100%","Insurance cover against risk",jsonObject.getString("observations_6_13"),jsonObject.getString("management_6_13"),jsonObject.getString("6_nps_rem_13")}
		    };	
	    	for (int row = 0; row < 13; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table6.addCell(new Cell().add(new Paragraph(cellContent6[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 6 "+e.getMessage());
				}
	    	}
	    	table6.complete();
            }catch (Exception e) {
				log.info("error while table 6 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table7 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table7);
		    Cell cell7_1 = new Cell(9,1).add(new Paragraph("7.").setBold());
		    Cell cell7_2 = new Cell(9,1).add(new Paragraph("Pattern of Investment").setBold());
		    table7.addCell(cell7_1);
		    table7.addCell(cell7_2);
		    
		    String[][] cellContent7 =	{	
		    			
		    		{ "100%","Verify pattern of investment :",jsonObject.getString("observations_7_1"),jsonObject.getString("management_7_1"),jsonObject.getString("7_nps_rem_1")},
		    		{ "100%","Scheme CG, SG,NPS LITE,APY",jsonObject.getString("observations_7_2"),jsonObject.getString("management_7_2"),jsonObject.getString("7_nps_rem_2")},
		    		{ "100%","*Scheme E Tier I & II",jsonObject.getString("observations_7_3"),jsonObject.getString("management_7_3"),jsonObject.getString("7_nps_rem_3")},
		    		{ "100%","*Scheme c Tier I & II",jsonObject.getString("observations_7_4"),jsonObject.getString("management_7_4"),jsonObject.getString("7_nps_rem_4")},
		    		{ "100%","*Scheme g Tier I & II",jsonObject.getString("observations_7_5"),jsonObject.getString("management_7_5"),jsonObject.getString("7_nps_rem_5")},
		    		{ "100%","* Corporate CG scheme",jsonObject.getString("observations_7_6"),jsonObject.getString("management_7_6"),jsonObject.getString("7_nps_rem_6")},
		    		{ "100%","APY Fund Scheme",jsonObject.getString("observations_7_7"),jsonObject.getString("management_7_7"),jsonObject.getString("7_nps_rem_7")},
		    		{ "100%","Scheme A Tier I",jsonObject.getString("observations_7_8"),jsonObject.getString("management_7_8"),jsonObject.getString("7_nps_rem_8")},
		    		{ "100%","Scheme TTS Tier II",jsonObject.getString("observations_7_9"),jsonObject.getString("management_7_9"),jsonObject.getString("7_nps_rem_9")}
		    };	
	    	for (int row = 0; row < 9; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table7.addCell(new Cell().add(new Paragraph(cellContent7[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 7 "+e.getMessage());
				}
	    	}
	    	table7.complete();
            }catch (Exception e) {
				log.info("error while table 7 completion : "+e.getMessage());
			}
            try {
		    		
	    	Table table8 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table8);
		    Cell cell8_1 = new Cell(4,1).add(new Paragraph("8.").setBold());
		    Cell cell8_2 = new Cell(4,1).add(new Paragraph("Dealing Procedure(Front office)").setBold());
		    table8.addCell(cell8_1);
		    table8.addCell(cell8_2);
		    
		    String[][] cellContent8 =	{	
		    		{ "100%","Installation of voice Recording Machine",jsonObject.getString("observations_8_1"),jsonObject.getString("management_8_1"),jsonObject.getString("8_nps_rem_1")},
		    		{ "100%","System and procedure of dealing in Equity and Debt",jsonObject.getString("observations_8_2"),jsonObject.getString("management_8_2"),jsonObject.getString("8_nps_rem_2")},
		    		{ "100%","Deployment of as Dealer / Fund Manager ",jsonObject.getString("observations_8_3"),jsonObject.getString("management_8_3"),jsonObject.getString("8_nps_rem_3")},
		    		{ "100%","Maintenance of records for investment justifications",jsonObject.getString("observations_8_4"),jsonObject.getString("management_8_4"),jsonObject.getString("8_nps_rem_4")}
		    };	
	    	for (int row = 0; row < 4; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table8.addCell(new Cell().add(new Paragraph(cellContent8[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 8  "+e.getMessage());
				}
	    	}
	    	table8.complete();
            }catch (Exception e) {
				log.info("error while table 8 completion : "+e.getMessage());
			}
            try {
	    	Table table9 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table9);
		    Cell cell9_1 = new Cell(4,1).add(new Paragraph("9.").setBold());
		    Cell cell9_2 = new Cell(4,1).add(new Paragraph("Back office procedure").setBold());
		    table9.addCell(cell9_1);
		    table9.addCell(cell9_2);
		    
		    String[][] cellContent9 =	{	
		    		{ "100%","Deployment of separate officials",jsonObject.getString("observations_9_1"),jsonObject.getString("management_9_1"),jsonObject.getString("9_nps_rem_1")},
		    		{ "100%","No password sharing between front office and back office",jsonObject.getString("observations_9_2"),jsonObject.getString("management_9_2"),jsonObject.getString("9_nps_rem_2")},
		    		{ "100%","Accounting and settlement of deal",jsonObject.getString("observations_9_3"),jsonObject.getString("management_9_3"),jsonObject.getString("9_nps_rem_3")},
		    		{ "100%","Deal execution through STP",jsonObject.getString("observations_9_4"),jsonObject.getString("management_9_4"),jsonObject.getString("9_nps_rem_4")}
		    };	
	    	for (int row = 0; row < 4; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table9.addCell(new Cell().add(new Paragraph(cellContent9[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 9 "+e.getMessage());
				}
	    	}
	    	table9.complete();
            }catch (Exception e) {
				log.info("error while table 9 completion : "+e.getMessage());
			}
            try {
		    		
	    	Table table10 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table10);
		    Cell cell10_1 = new Cell(6,1).add(new Paragraph("10.").setBold());
		    Cell cell10_2 = new Cell(6,1).add(new Paragraph("Exposure and Prudential norms").setBold());
		    table10.addCell(cell10_1);
		    table10.addCell(cell10_2);
		    
		    String[][] cellContent10 =	{	
		    		{ "100%","Investment in promoter group",jsonObject.getString("observations_10_1"),jsonObject.getString("management_10_1"),jsonObject.getString("10_nps_rem_1")},
		    		{ "100%","Stipulated norms - Investee co.",jsonObject.getString("observations_10_2"),jsonObject.getString("management_10_2"),jsonObject.getString("10_nps_rem_2")},
		    		{ "100%","Stipulated norms - Group Co.",jsonObject.getString("observations_10_3"),jsonObject.getString("management_10_3"),jsonObject.getString("10_nps_rem_3")},
		    		{ "100%","Stipulated norms - Industry group",jsonObject.getString("observations_10_4"),jsonObject.getString("management_10_4"),jsonObject.getString("10_nps_rem_4")},
		    		{ "100%","Limit monitoring - through system",jsonObject.getString("observations_10_5"),jsonObject.getString("management_10_5"),jsonObject.getString("10_nps_rem_5")},
		    		{ "100%","Limit and alert management - Internal Norms and Regulatory norms.",jsonObject.getString("observations_10_6"),jsonObject.getString("management_10_6"),jsonObject.getString("10_nps_rem_6")}		    		
		    };	
	    	for (int row = 0; row < 6; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table10.addCell(new Cell().add(new Paragraph(cellContent10[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 10 "+e.getMessage());
				}
	    	}
	    	table10.complete();
            }catch (Exception e) {
				log.info("error while table 10 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table11 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table11);
		    Cell cell11_1 = new Cell(2,1).add(new Paragraph("11.").setBold());
		    Cell cell11_2 = new Cell(2,1).add(new Paragraph("Custodian Controls").setBold());
		    table11.addCell(cell11_1);
		    table11.addCell(cell11_2);
		    
		    String[][] cellContent11 =	{	
		    			
		    		{ "100%","Reconciliation of securities with Custodian data",jsonObject.getString("observations_11_1"),jsonObject.getString("management_11_1"),jsonObject.getString("11_nps_rem_1")},
		    		{ "100%","Controls over Physical holding",jsonObject.getString("observations_11_2"),jsonObject.getString("management_11_2"),jsonObject.getString("11_nps_rem_2")}
		      };	
	    	for (int row = 0; row < 2; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table11.addCell(new Cell().add(new Paragraph(cellContent11[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 11 "+e.getMessage());
				}
	    	}
	    	table11.complete();
            }catch (Exception e) {
				log.info("error while table 11 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table12 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table12);
		    Cell cell12_1 = new Cell(2,1).add(new Paragraph("12.").setBold());
		    Cell cell12_2 = new Cell(2,1).add(new Paragraph("Verification of other investments").setBold());
		    table12.addCell(cell12_1);
		    table12.addCell(cell12_2);
		    
		    String[][] cellContent12 =	{	
		    			
		    		{ "End of reporting period","Reconciliation of Mutual Fund holding with Statement of Account received from MF",jsonObject.getString("observations_12_1"),jsonObject.getString("management_12_1"),jsonObject.getString("12_nps_rem_1")},
		    		{ "End of reporting period","Physical verification of fixed deposit receipts in respect of Fixed Deposits placed with Banks",jsonObject.getString("observations_12_2"),jsonObject.getString("management_12_2"),jsonObject.getString("12_nps_rem_2")}
		    };	
	    	for (int row = 0; row < 2; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table12.addCell(new Cell().add(new Paragraph(cellContent12[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 12 "+e.getMessage());
				}
	    	}
	    	table12.complete();	
            }catch (Exception e) {
				log.info("error while table 12 completion : "+e.getMessage());
			}
            try {
		    			
	    	Table table13 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table13);
		    Cell cell13_1 = new Cell(3,1).add(new Paragraph("13.").setBold());
		    Cell cell13_2 = new Cell(3,1).add(new Paragraph("Units accounting").setBold());
		    table13.addCell(cell13_1);
		    table13.addCell(cell13_2);
		    
		    String[][] cellContent13 =	{	
		    		{ "25%","Reconciliation of units with CRA on daily basis",jsonObject.getString("observations_13_1"),jsonObject.getString("management_13_1"),jsonObject.getString("13_nps_rem_1")},
		    		{ "100%","Correctness of NAV applied",jsonObject.getString("observations_13_2"),jsonObject.getString("management_13_2"),jsonObject.getString("")},
		    		{ "100%","Appropriateness of accounting of unit premium",jsonObject.getString("observations_13_3"),jsonObject.getString("management_13_3"),jsonObject.getString("13_nps_rem_2")}
		    };	
	    	for (int row = 0; row < 3; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table13.addCell(new Cell().add(new Paragraph(cellContent13[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 13 "+e.getMessage());
				}
	    	}
	    	table13.complete();	
            }catch (Exception e) {
				log.info("error while table 13 completion : "+e.getMessage());
			}
            try {
		    		
	    	Table table14 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table14);
		    Cell cell14_1 = new Cell(3,1).add(new Paragraph("14.").setBold());
		    Cell cell14_2 = new Cell(3,1).add(new Paragraph("Investment bank accounts").setBold());
		    table14.addCell(cell14_1);
		    table14.addCell(cell14_2);
		    
		    String[][] cellContent14 =	{	
		    		{ "25%","Bank Reconciliation on daily basis",jsonObject.getString("observations_14_1"),jsonObject.getString("management_14_1"),jsonObject.getString("14_nps_rem_1")},
		    		{ "25%","Identification of idle funds",jsonObject.getString("observations_14_2"),jsonObject.getString("management_14_2"),jsonObject.getString("14_nps_rem_2")},
		    		{ "25%","Timely deployment of fund",jsonObject.getString("observations_14_3"),jsonObject.getString("management_14_3"),jsonObject.getString("14_nps_rem_3")}
		    };	
	    	for (int row = 0; row < 3; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table14.addCell(new Cell().add(new Paragraph(cellContent14[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 14 "+e.getMessage());
				}
	    	}
	    	table14.complete();	
            }catch (Exception e) {
				log.info("error while table 14 completion : "+e.getMessage());
			}
            try {
	    	Table table15 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table15);
		    Cell cell15_1 = new Cell(2,1).add(new Paragraph("15.").setBold());
		    Cell cell15_2 = new Cell(2,1).add(new Paragraph("Appointment of Brokers").setBold());
		    table15.addCell(cell15_1);
		    table15.addCell(cell15_2);
		    
		    String[][] cellContent15 =	{	
		    		{ "100%","Guidelines on empanelment of brokers dated 14th Sep 2012",jsonObject.getString("observations_15_1"),jsonObject.getString("management_15_1"),jsonObject.getString("15_nps_rem_1")},
		    		{ "100%","Ceiling per broker as per IMA",jsonObject.getString("observations_15_2"),jsonObject.getString("management_15_2"),jsonObject.getString("15_nps_rem_2")}
		    };	
	    	for (int row = 0; row < 2; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table15.addCell(new Cell().add(new Paragraph(cellContent15[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 15 "+e.getMessage());
				}
	    	}
	    	table15.complete();
            }catch (Exception e) {
				log.info("error while table 15 completion : "+e.getMessage());
			}
            try {
		    		
	    	Table table16 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table16);
		    Cell cell16_1 = new Cell(3,1).add(new Paragraph("16.").setBold());
		    Cell cell16_2 = new Cell(3,1).add(new Paragraph("Non Performing Investments").setBold());
		    table16.addCell(cell16_1);
		    table16.addCell(cell16_2);
		    
		    String[][] cellContent16 =	{	
		    		{ "100%","Classification",jsonObject.getString("observations_16_1"),jsonObject.getString("management_16_1"),jsonObject.getString("16_nps_rem_1")},
		    		{ "100%","Income Recognition",jsonObject.getString("observations_16_2"),jsonObject.getString("management_16_2"),jsonObject.getString("16_nps_rem_2")},
		    		{ "100%","Provisions",jsonObject.getString("observations_16_3"),jsonObject.getString("management_16_3"),jsonObject.getString("16_nps_rem_3")}
		    };	
	    	for (int row = 0; row < 3; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table16.addCell(new Cell().add(new Paragraph(cellContent16[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 16 "+e.getMessage());
				}
	    	}
	    	table16.complete();	
            }catch (Exception e) {
				log.info("error while table 16 completion : "+e.getMessage());
			}
            try {
		    		
	    	Table table17 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table17);
		    Cell cell17_1 = new Cell(2,1).add(new Paragraph("17.").setBold());
		    Cell cell17_2 = new Cell(2,1).add(new Paragraph("Inter Scheme Transfer").setBold());
		    table17.addCell(cell17_1);
		    table17.addCell(cell17_2);
		    
		    String[][] cellContent17 =	{	
		    		{ "100%","Traded securities - Rates, authorisation and documentation",jsonObject.getString("observations_17_1"),jsonObject.getString("management_17_1"),jsonObject.getString("17_nps_rem_1")},
		    		{ "100%","Non traded securities - Need/Justification of off market transaction, fairness of price and internal authorisation",jsonObject.getString("observations_17_2"),jsonObject.getString("management_17_2"),jsonObject.getString("17_nps_rem_2")}
		    };	
	    	for (int row = 0; row < 2; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table17.addCell(new Cell().add(new Paragraph(cellContent17[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 17 "+e.getMessage());
				}
	    	}
	    	table17.complete();	
            }catch (Exception e) {
				log.info("error while table 17 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table18 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table18);
		    Cell cell18_1 = new Cell(11,1).add(new Paragraph("18.").setBold());
		    Cell cell18_2 = new Cell(11,1).add(new Paragraph("Investment Deals verification").setBold());
		    table18.addCell(cell18_1);
		    table18.addCell(cell18_2);
		    
		    String[][] cellContent18 =	{	
		    			
		    		{ "75%","Accuracy of calculation of Investable surplus",jsonObject.getString("observations_18_1"),jsonObject.getString("management_18_1"),jsonObject.getString("18_nps_rem_1")},
		    		{ "75%","Review of Daily sale purchase register",jsonObject.getString("observations_18_2"),jsonObject.getString("management_18_2"),jsonObject.getString("18_nps_rem_2")},
		    		{ "75%","Journal vouchers",jsonObject.getString("observations_18_3"),jsonObject.getString("management_18_3"),jsonObject.getString("18_nps_rem_3")},
		    		{ "75%","Investment Ledgers",jsonObject.getString("observations_18_4"),jsonObject.getString("management_18_4"),jsonObject.getString("18_nps_rem_4")},
		    		{ "75%","Verification of authorisation, price and documentation",jsonObject.getString("observations_18_5"),jsonObject.getString("management_18_5"),jsonObject.getString("18_nps_rem_5")},
		    		{ "75%","Counter party confirmation",jsonObject.getString("observations_18_6"),jsonObject.getString("management_18_6"),jsonObject.getString("18_nps_rem_6")},
		    		{ "75%","Contract note from brokers",jsonObject.getString("observations_18_7"),jsonObject.getString("management_18_7"),jsonObject.getString("18_nps_rem_7")},
		    		{ "75%","Brokers Bills",jsonObject.getString("observations_18_8"),jsonObject.getString("management_18_8"),jsonObject.getString("18_nps_rem_8")},
		    		{ "25%","Deal tickets",jsonObject.getString("observations_18_9"),jsonObject.getString("management_18_9"),jsonObject.getString("18_nps_rem_9")},
		    		{ "25%","DIS/DIP statement & Intimation to the custodian",jsonObject.getString("observations_18_10"),jsonObject.getString("management_18_10"),jsonObject.getString("18_nps_rem_10")},
		    		{ "25%","Verification of timely and accurate capturing of trades",jsonObject.getString("observations_18_11"),jsonObject.getString("management_18_11"),jsonObject.getString("18_nps_rem_11")}
		    };	
	    	for (int row = 0; row < 11; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table18.addCell(new Cell().add(new Paragraph(cellContent18[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 18 "+e.getMessage());
				}
	    	}
	    	table18.complete();	
            }catch (Exception e) {
				log.info("error while table 18 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table19 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table19);
		    Cell cell19_1 = new Cell(6,1).add(new Paragraph("19.").setBold());
		    Cell cell19_2 = new Cell(6,1).add(new Paragraph("Accounting").setBold());
		    table19.addCell(cell19_1);
		    table19.addCell(cell19_2);
		    
		    String[][] cellContent19 =	{	
		    		{ "100%","Compliance with accounting Standards",jsonObject.getString("observations_19_1"),jsonObject.getString("management_19_1"),jsonObject.getString("19_nps_rem_1")},
		    		{ "100%","Adherence to accounting policy",jsonObject.getString("observations_19_2"),jsonObject.getString("management_19_2"),jsonObject.getString("19_nps_rem_2")},
		    		{ "50%","Accounting of income receipt",jsonObject.getString("observations_19_3"),jsonObject.getString("management_19_3"),jsonObject.getString("19_nps_rem_3")},
		    		{ "25%","Corporate action-bonus, rights,dividend, interest receivable",jsonObject.getString("observations_19_4"),jsonObject.getString("management_19_4"),jsonObject.getString("19_nps_rem_4")},
		    		{ "25%","Reversal of brokerage on daily basis",jsonObject.getString("observations_19_5"),jsonObject.getString("management_19_5"),jsonObject.getString("19_nps_rem_5")},
		    		{ "100%","Outsourcing (100% of all items under the scope of 'accounting', if it is outsourced)",jsonObject.getString("observations_19_6"),jsonObject.getString("management_19_6"),jsonObject.getString("19_nps_rem_6")}
		    };	
	    	for (int row = 0; row < 6; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table19.addCell(new Cell().add(new Paragraph(cellContent19[row][col])));     
	    		}}catch (Exception e) {
					log.info("error in table 19 "+e.getMessage());
				}
	    	}
	    	table19.complete();	
            }catch (Exception e) {
				log.info("error while table 19 completion : "+e.getMessage());
			}
            try {
		    		
	    	Table table20 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table20);
		    Cell cell20_1 = new Cell(5,1).add(new Paragraph("20.").setBold());
		    Cell cell20_2 = new Cell(5,1).add(new Paragraph("NAV").setBold());
		    table20.addCell(cell20_1);
		    table20.addCell(cell20_2);
		    
		    String[][] cellContent20 =	{	
		    		{ "100%","Valuation of investments",jsonObject.getString("observations_20_1"),jsonObject.getString("management_20_1"),jsonObject.getString("20_nps_rem_1")},
		    		{ "50%","NAV Calculation",jsonObject.getString("observations_20_2"),jsonObject.getString("management_20_2"),jsonObject.getString("20_nps_rem_2")},
		    		{ "10%","Accruals of coupons",jsonObject.getString("observations_20_3"),jsonObject.getString("management_20_3"),jsonObject.getString("20_nps_rem_3")},
		    		{ "100%","Scrutiny of expenses charged to scheme on daily basis (i.e Investment Management fee, Custodian charges, SEBI Charges and CCIL Charges)",jsonObject.getString("observations_20_4"),jsonObject.getString("management_20_4"),jsonObject.getString("20_nps_rem_4")},
		    		{ "100%","Outsourcing (100% of all items under the scope of 'NAV', if it is outsourced)",jsonObject.getString("observations_20_5"),jsonObject.getString("management_20_5"),jsonObject.getString("20_nps_rem_5")}
		    };	
	    	for (int row = 0; row < 5; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table20.addCell(new Cell().add(new Paragraph(cellContent20[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 20 "+e.getMessage());
				}
	    	}
	    	table20.complete();	
            }catch (Exception e) {
				log.info("error while table 20 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table21 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table21);
		    Cell cell21_1 = new Cell(5,1).add(new Paragraph("21.").setBold());
		    Cell cell21_2 = new Cell(5,1).add(new Paragraph("Disclosure").setBold());
		    table21.addCell(cell21_1);
		    table21.addCell(cell21_2);
		    
		    String[][] cellContent21 =	{	
	    	
		    		{ "100%","Daily NAV disclosure/uploading to CRA",jsonObject.getString("observations_21_1"),jsonObject.getString("management_21_1"),jsonObject.getString("21_nps_rem_1")},
		    		{ "100%","NAV History",jsonObject.getString("observations_21_2"),jsonObject.getString("management_21_2"),jsonObject.getString("21_nps_rem_2")},
		    		{ "100%","Portfolio Disclosure",jsonObject.getString("observations_21_3"),jsonObject.getString("management_21_3"),jsonObject.getString("21_nps_rem_3")},
		    		{ "100%","Half Yearly Financial statement",jsonObject.getString("observations_21_4"),jsonObject.getString("management_21_4"),jsonObject.getString("21_nps_rem_4")},
		    		{ "100%","Annual report",jsonObject.getString("observations_21_5"),jsonObject.getString("management_21_5"),jsonObject.getString("21_nps_rem_5")}
		    };	
	    	for (int row = 0; row < 5; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table21.addCell(new Cell().add(new Paragraph(cellContent21[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 21 "+e.getMessage());
				}
	    	}
	    	table21.complete();	
            }catch (Exception e) {
				log.info("error while table 21 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table22 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table22);
		    Cell cell22_1 = new Cell(4,1).add(new Paragraph("22.").setBold());
		    Cell cell22_2 = new Cell(4,1).add(new Paragraph("Periodical returns to Authority / Trust").setBold());
		    table22.addCell(cell22_1);
		    table22.addCell(cell22_2);
		    
		    String[][] cellContent22 =	{	
		    				
		    		{ "100%","Timely submission",jsonObject.getString("observations_22_1"),jsonObject.getString("management_22_1"),jsonObject.getString("22_nps_rem_1")},
		    		{ "100%","Accuracy of data",jsonObject.getString("observations_22_2"),jsonObject.getString("management_22_2"),jsonObject.getString("22_nps_rem_2")},
		    		{ "100%","Procedure of generation of data and report",jsonObject.getString("observations_22_3"),jsonObject.getString("management_22_3"),jsonObject.getString("22_nps_rem_3")},
		    		{ "100%","procedure for capturing Down grading of investment",jsonObject.getString("observations_22_4"),jsonObject.getString("management_22_4"),jsonObject.getString("22_nps_rem_4")}
		    };	
	    	for (int row = 0; row < 4; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table22.addCell(new Cell().add(new Paragraph(cellContent22[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 22 "+e.getMessage());
				}
	    	}
	    	table22.complete();	
            }catch (Exception e) {
				log.info("error while table 22 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table23 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table23);
		    Cell cell23_1 = new Cell(5,1).add(new Paragraph("23.").setBold());
		    Cell cell23_2 = new Cell(5,1).add(new Paragraph("Compliances").setBold());
		    table23.addCell(cell23_1);
		    table23.addCell(cell23_2);
		    
		    String[][] cellContent23 =	{	
		    				
		    		{ "100%","Compliance to clauses of IMA",jsonObject.getString("observations_23_1"),jsonObject.getString("management_23_1"),jsonObject.getString("23_nps_rem_1")},
		    		{ "100%","Compliance to Guidelines and Guidance note",jsonObject.getString("observations_23_2"),jsonObject.getString("management_23_2"),jsonObject.getString("23_nps_rem_2")},
		    		{ "100%","Compliance to Internal Guidelines, Operational manual",jsonObject.getString("observations_23_3"),jsonObject.getString("management_23_3"),jsonObject.getString("23_nps_rem_3")},
		    		{ "100%","Adequacy and efficacy of Internal Control system and procedures",jsonObject.getString("observations_23_4"),jsonObject.getString("management_23_4"),jsonObject.getString("23_nps_rem_4")},
		    		{ "100%","Adequacy and efficacy of operating systems/software's including investment and accounting systems to ensure:a) System should be capable of maintaining access logs b) Auditor should review access logs and confirm no unauthorized access c) System modification/change request process is documented with proper delegation of authority and all modification requests and authorization records are maintained.",jsonObject.getString("observations_23_5"),jsonObject.getString("management_23_5"),jsonObject.getString("23_nps_rem_5")}
		    };	
	    	for (int row = 0; row <5; row++) {
		    	try {
	    		for (int col = 0; col < colSize; col++) {
	    			table23.addCell(new Cell().add(new Paragraph(cellContent23[row][col])));     
	    		}
		    	}catch (Exception e) {
					log.info("error in table 23 "+e.getMessage());
				}
	    	}
	    	table23.complete();	
            }catch (Exception e) {
				log.info("error while table 23 completion : "+e.getMessage());
			}
            try {
	    	
	    	Table table24 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table24);
		    Cell cell24_1 = new Cell().add(new Paragraph("24.").setBold());
		    Cell cell24_2 = new Cell().add(new Paragraph("Internal Audit/ PFM Audit/ Scheme Audit").setBold());
		    table24.addCell(cell24_1);
		    table24.addCell(cell24_2);
		    String[][] cellContent24 =	{	
		    		{"100%","To see the exceptions of audit & compliance there of",jsonObject.getString("observations_24_1"),jsonObject.getString("management_24_1"),jsonObject.getString("24_nps_rem_1")}		    			    		
            };
		    
		    for (int row = 0; row < 1; row++) {
		    	try {
		        for (int col = 0; col < colSize; col++) {
		            table24.addCell(new Cell().add(new Paragraph(cellContent24[row][col])));
		            
		        }
		    	}catch (Exception e) {
					log.info("error in table 24 "+e.getMessage());
				}
		        }
		    table24.complete();
            }catch (Exception e) {
				log.info("error while table 24 completion : "+e.getMessage());
			}
            try {
		   	
	    	
	    	Table table25 = new Table(new float[] {0.5f, 2.0f, 1.0f,1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table25);
		    Cell cell25_1 = new Cell().add(new Paragraph("25.").setBold());
		    Cell cell25_2 = new Cell().add(new Paragraph("Marketing and distribution").setBold());
		    table25.addCell(cell25_1);
		    table25.addCell(cell25_2);
		    String[][] cellContent25 =	{	
		  	
		    		{"100%","To see the exceptions of audit & compliance there of",jsonObject.getString("observations_25_1"),jsonObject.getString("management_25_1"),jsonObject.getString("25_nps_rem_1")}
            };
		    
		    for (int row = 0; row < 1; row++) {
		    	try {
		        for (int col = 0; col < colSize; col++) {
		            table25.addCell(new Cell().add(new Paragraph(cellContent25[row][col])));
		            
		        }
		    	}catch (Exception e) {
					log.info("error in table 25 "+e.getMessage());
				}
		        }
		    table25.complete();
            }catch (Exception e) {
				log.info("error while table 25 completion : "+e.getMessage());
			}
           try {
		    
			 // List<PFM_executive_summary>executive_summaries=  PFM_executive_summaryLocalServiceUtil.getExecutiveSummaryByReportUploadLogId(reportUploadlogId);
		    ///////////////
		    Table table26 = new Table(new float[] {1.0f, 2.0f, 1.5f,1.5f,1.5f}, true)
	                .setWidth(UnitValue.createPercentValue(100));
		    doc.add(table26);
			Cell cell26 = new Cell(1, 5)
		            .add(new Paragraph("Executive Summary")).setBold();
			table26.addCell(cell26);
			
			    String[] headersSumary = {"Sr.No", "Broad Description","Auditor Observation ", "Risk Rating","Management Response"};
			    for (int col = 0; col < 5; col++) {
			    	table26.addCell(new Cell().add(new Paragraph(headersSumary[col])).setBold());
		            
		        }
			   // doc.add(table26);
			log.info("summariesArray length:  "+summariesArray.length());
		    String[][] cellContent26 =	new String[summariesArray.length()][6];
		    
		    if(Validator.isNotNull(summariesArray)) {
		    	for(int row=0;row<summariesArray.length();row++) {
			    	try {
			    	JSONObject executive_summary=summariesArray.getJSONObject(row);
			    	cellContent26[row][0]=row+1+"";
			    	cellContent26[row][1]=executive_summary.getString("boardDiscription");
			    	cellContent26[row][2]=executive_summary.getString("auditorObservation");
			    	cellContent26[row][3]=executive_summary.getString("riskRating");
			    	cellContent26[row][4]=executive_summary.getString("managementResponse");
			    	//cellContent26[row][5]=executive_summary.getString("npstComment");
			    	}catch (Exception e) {
						log.info(e.getMessage());
					}
			    }
		    	for (int row = 0; row < summariesArray.length(); row++) {
			    	try {
			        for (int col = 0; col < 5; col++) {
			        	table26.addCell(new Cell().add(new Paragraph(cellContent26[row][col])));
			            
			        }
			    	}catch (Exception e) {
						log.info("error in table 26 "+e.getMessage());
					}
			        }
			    table26.complete();
			    
		    }
		}catch (Exception e) {
			log.info("error while table 1 completion : "+e.getMessage());
		}
 
            
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

private static final Log log=LogFactoryUtil.getLog(Qr_IAR_PDF_Util.class.getName());
}
