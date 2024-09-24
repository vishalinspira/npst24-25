package com.quarter.ended.portlet.resource;



import com.daily.average.service.model.AuditorCertificate;
import com.daily.average.service.model.QuarterEnded;
import com.daily.average.service.service.QuarterEndedLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.quarter.ended.constants.quarterEndedPortletKeys;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.line.LineStyle;
import nps.email.api.api.QuaterEndedEmailApi;
import nps.email.api.api.QuaterEndedSmsApi;

@Component(property = { "javax.portlet.name=" +  quarterEndedPortletKeys.QUARTERENDED,
"mvc.command.name=/quarterended/save" }, service = MVCResourceCommand.class)
public class SaveQuaterEnded  implements MVCResourceCommand {
 
	@Reference
	QuarterEndedLocalService quarterEndedLocalService;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		_log.info("In serveResource /quarterended/save");
		JSONObject resultJsonObject = quaterendedUpload(resourceRequest, resourceResponse,themeDisplay);
		try { 
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}
	
	public JSONObject quaterendedUpload(ResourceRequest resourceRequest,ResourceResponse resourceResponse,ThemeDisplay themeDisplay) {
		String OneIa = ParamUtil.getString(resourceRequest, "OneIa");
		String OneIb = ParamUtil.getString(resourceRequest, "OneIb");
		String OneIIa = ParamUtil.getString(resourceRequest, "OneIIa");
		String OneIIb = ParamUtil.getString(resourceRequest, "OneIIb");
		String OneIIc = ParamUtil.getString(resourceRequest, "OneIIc");
		String OneIId = ParamUtil.getString(resourceRequest, "OneIId");
		String OneIIIa = ParamUtil.getString(resourceRequest, "OneIIIa");
		String OneIIIb = ParamUtil.getString(resourceRequest, "OneIIIb");
		String OneIVa = ParamUtil.getString(resourceRequest, "OneIVa");
		String OneV = ParamUtil.getString(resourceRequest, "OneV");
		String TwoIa = ParamUtil.getString(resourceRequest, "TwoIa");
		String TwoIb = ParamUtil.getString(resourceRequest, "TwoIb");
		String TwoIc = ParamUtil.getString(resourceRequest, "TwoIc");
		String TwoII = ParamUtil.getString(resourceRequest, "TwoII");
		String ThreeA = ParamUtil.getString(resourceRequest, "ThreeA");
		String ThreeB = ParamUtil.getString(resourceRequest, "ThreeB");
		String ThreeC = ParamUtil.getString(resourceRequest, "ThreeC");
		String ThreeCi = ParamUtil.getString(resourceRequest, "ThreeCi");
		String ThreeCii = ParamUtil.getString(resourceRequest, "ThreeCii");
		String ThreeCiii = ParamUtil.getString(resourceRequest, "ThreeCiii");
		String ThreeCiv = ParamUtil.getString(resourceRequest, "ThreeCiv");
		String FourA = ParamUtil.getString(resourceRequest, "FourA");
		String FourB = ParamUtil.getString(resourceRequest, "FourB");
		String FourC = ParamUtil.getString(resourceRequest, "FourC");
		String FiveI = ParamUtil.getString(resourceRequest, "FiveI");
		String FiveII = ParamUtil.getString(resourceRequest, "FiveII");
		String FiveIII = ParamUtil.getString(resourceRequest, "FiveIII");
		String FiveIV = ParamUtil.getString(resourceRequest, "FiveIV");
		String FiveVa = ParamUtil.getString(resourceRequest, "FiveVa");
		String FiveVb = ParamUtil.getString(resourceRequest, "FiveVb");
		String FiveVc = ParamUtil.getString(resourceRequest, "FiveVc");
		String FiveVIa = ParamUtil.getString(resourceRequest, "FiveVIa");
		String FiveVIb = ParamUtil.getString(resourceRequest, "FiveVIb");
		String SixI = ParamUtil.getString(resourceRequest, "SixI");
		String SixIIa = ParamUtil.getString(resourceRequest, "SixIIa");
		String SixIIb = ParamUtil.getString(resourceRequest, "SixIIb");
		String SevenIa = ParamUtil.getString(resourceRequest, "SevenIa");
		String SevenIb = ParamUtil.getString(resourceRequest, "SevenIb");
		String SevenIc = ParamUtil.getString(resourceRequest, "SevenIc");
		String SevenId = ParamUtil.getString(resourceRequest, "SevenId");
		String SevenIe = ParamUtil.getString(resourceRequest, "SevenIe");
		String EightIa = ParamUtil.getString(resourceRequest, "EightIa");
		String EightIb = ParamUtil.getString(resourceRequest, "EightIb");
		String EightII = ParamUtil.getString(resourceRequest, "EightII");
		String EightIII = ParamUtil.getString(resourceRequest, "EightIII");
		String EightIV = ParamUtil.getString(resourceRequest, "EightIV");
		String EightV = ParamUtil.getString(resourceRequest, "EightV");
		String EightVIa = ParamUtil.getString(resourceRequest, "EightVIa");
		String EightVIb = ParamUtil.getString(resourceRequest, "EightVIb");
		String EightVIIa = ParamUtil.getString(resourceRequest, "EightVIIa");
		String EightVIIb = ParamUtil.getString(resourceRequest, "EightVIIb");
		String EightVIII = ParamUtil.getString(resourceRequest, "EightVIII");
		String NineA = ParamUtil.getString(resourceRequest, "NineA");
		String NineB = ParamUtil.getString(resourceRequest, "NineB");
		String Ten = ParamUtil.getString(resourceRequest, "Ten");
		String ElevenA = ParamUtil.getString(resourceRequest, "ElevenA");
		String ElevenB = ParamUtil.getString(resourceRequest, "ElevenB");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		QuarterEnded quaterended = quarterEndedLocalService.addQuarterEnded(ElevenB, ElevenA, Ten, NineB, NineA, EightVIIb, EightVIII,
				EightVIIa, EightVIb, EightVIa, EightVIII, EightIV, EightIII, EightIII, EightIb, EightIa, SevenIe,
				SevenId, SevenIc, SevenIb, SevenIa, SixIIb, SixIIa, SixIIb, FiveVIb, FiveVIa, FiveVc, FiveVb, FiveVa,
				FiveIV, FiveIII, FiveII, FiveI, FourC, FourB, FourA, ThreeCiv, ThreeCiii, ThreeCii, ThreeCi,
				ThreeC, ThreeB, ThreeA, TwoII, TwoIc, TwoIb, TwoIa, OneV, OneIVa, OneIIIb, OneIIIa, OneIId, OneIIc,
				OneIIb, OneIIa, OneIb, OneIa);
		try {
			resultJsonObject.put("pdfURL",  pdf1Table(quaterended,themeDisplay, resourceRequest));
			resultJsonObject.put("status", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 _log.error(e);
		}
		quaterEndedEmailApi.sendOTP("priyanka@yopmail.com");
		String mobileNo="918637002546";
		String name="Priyanka";
		quaterEndedSmsApi.sendFormSubmitSuccessfulSMS(mobileNo, name);
		
		
		_log.info(resultJsonObject);
		return resultJsonObject;
	}
	@Reference
	QuaterEndedEmailApi quaterEndedEmailApi;
	@Reference
	QuaterEndedSmsApi quaterEndedSmsApi;
	
	
	public String pdf1Table(QuarterEnded quaterended,ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
	      PDFont fontPlain = PDType1Font.HELVETICA;
	        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
	        PDFont fontMono = PDType1Font.COURIER;
	        float width = 0;
			LineStyle noBorder = new LineStyle(Color.WHITE, width );
	       
	        PDDocument document = new PDDocument();
	        PDPage page = new PDPage(PDRectangle.A4);
	        PDRectangle rect = page.getMediaBox();
	        document.addPage(page);
	        PDPageContentStream cos = new PDPageContentStream(document, page);
	        float margin = 10;
	        _log.info("rect.getHeight()"+rect.getHeight());
	        float yStartNewPage = rect.getUpperRightY() - (2 * margin); 
	        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

	        boolean drawContent = true;
	        float yStart = yStartNewPage;
	        float topMargin = 0;
	        float bottomMargin = 10;
	        float yPosition = rect.getUpperRightY()-margin;
	        
	        BaseTable table;
			table = new BaseTable(yPosition, yStartNewPage,topMargin,
				    bottomMargin, tableWidth, margin, document, page, false, drawContent);
			
	        Row<PDPage> headerRow = table.createRow(20);
	        Cell<PDPage> cell = headerRow.createCell(100, "For The Quater Ended September 2021");
	        cell.setFontSize(20);
	        cell.setAlign(HorizontalAlignment.CENTER);
	        table.addHeaderRow(headerRow);
	        table.draw();
	        
	        float tableHeight = table.getHeaderAndDataHeight();
	        _log.info("tableHeight = "+tableHeight);
	        
	        float table2YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) -30;
	        BaseTable table2 = new BaseTable(table2YPosition, yStartNewPage,topMargin,
	                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
	        
	        Row<PDPage> table2row = table2.createRow(20);
	        Cell<PDPage> table2cell = table2row.createCell(20,"SL.NO Parameters ");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(60,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(20);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(20,"Complied");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        table2.addHeaderRow(table2row);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(20,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(60,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(20);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(20,"Yes or No");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"1. Management ,Sponser, Net-worth");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(i)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"a) Whether composition of the Board, Investment committee and Risk Management committeeis as per PFRDA (Pension Fund) regulations?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getOneIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder );
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"b) Whether Principal officer and key personnel have been appointed as per PFRDA (Pension Fund) regulations");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getOneIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	     
	        table2cell.setTopBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(ii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"a) Whether bio-data of all the directors along with their interest in other companies has been filed with the NPS Trust within 15 days of their appointment?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getOneIIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder );
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"b) Whether subsequent change in the interest of the directors havebeen filed with the NPS Trust");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getOneIIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell.setTopBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"c) Whether minimum 51% of the directors have adequate professional experience in finance and financial services related fields");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getOneIIc()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell.setTopBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"d) Whether change in key personnel has been intimated to the PFRDA within 15days of the occurrence of such change");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getOneIId()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(iii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"a) Whether the Sponsor and the Pension Fund(PFM) fulfill and comply with the eligibility requirements as specified under the PFRDA (Pension Fund) Regulations?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getOneIIIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"b) Whether there is any material change in the information or particulars previously furnished which have a bearing onPFMs certificate of registration?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getOneIIIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(iv)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"a) Whether there is any major change in the management or ownership or shareholding pattern or any change in controlling interest of the Sponsor?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getOneIVa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(v)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether PFM is maintaining minimum Tangible Net-worth as stipulated by PFRDA?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getOneV()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"2. Investment Policy");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(i)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"a) Whether Investment Policyhasbeen drawn in accordance with the investment guidelines approved by the PFRDA and has been approved by the Board of Directors (BOD) of the PFM?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getTwoIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);;
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"b) Whether Investment Policy is being reviewed periodically (minimum half yearly basis) by the PFM?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getTwoIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"c) Whether the PFM has submitted details of the Investment Policy reviewed by its board to the NPS Trust within 30days of such review");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getTwoIc()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,""+""+"Please provide the following:");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,""+""+""+"i) Initial BOD approval date");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,""+""+""+"ii) Last BOD review date");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,""+""+""+"iii) Frequency of review in a year as decided by the BOD");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(ii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"Please mention (individually), whether Investment Policy covers the following:");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getTwoII()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"i) Prudential norms, Income recognition, Asset classification and Provisioning");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"ii) Sector limits as stipulated in the Investment guidelines ");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"iii) Sponsor and Non-Sponsor Group limits as stipulated in the Investment guidelines");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"iv) Liquidity and Asset/liability management");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"v) Stop Loss Limits");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"vi)Broker limit");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"vii) Investment audits");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	       
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"3. Risk Management Policy");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"a) Whether Risk Management Policy hasbeen drawn in accordance with the guidelines approved by the PFRDA and has been approved by the Board of Directors?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getThreeA()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"b) Whether Risk Management Policy is being reviewed periodically (minimum half yearly basis) by the PFM?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getThreeB()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"c) Whether the PFM has submitted details of the Risk Management Policy reviewed by its board to the NPS Trust?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getThreeC()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"Please mention (individually), whether Risk Management policy covers the following:");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"1. Risk Management functions");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getThreeCi()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"2. Disaster recovery and business continuity plans");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getThreeCii()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"3. Insurance cover against risks");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getThreeCiii()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"4. Ensuring risk adjusted returns to subscribers consistent with the protection, safety and liquidity of such funds.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getThreeCiv()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"4. Reporting of Investment Deviations");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"a) Whether the PFM has ensured that all investments are made as per the investment guidelines?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFourA()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"b) In case of a deviation (downgrade to a rating not permitted under the regulations for corporate bonds or any other non-compliance in any scheme/asset class post investment), whether the PFM has recorded an internal note justifying its decision to hold such securities where deviation has occurred. ");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFourB()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"c) Whether all such investment deviations are being reported to the Investment Committee and Board of the PFM for their approval to continue to remain invested in these securities.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFourC()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        ///////
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"(Please provide details of the deviations that occurred in the quarter in Annexure 2 along with details of justification note and Investment Committee & Board approval extracts)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	       
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"5. Code of Conduct");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(i)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether the PFM is engaged in any other business activity except those relating to pension schemes or funds, regulated by the PFRDA.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getFiveI()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(ii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether the PFM has ensured that adequate disclosures are made to the PFRDA, the NPS Trust or subscriber in a comprehensible and timely manner.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getFiveII()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(iii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether the PFM has ensured that there has not been any misrepresentation or submission of any misleading information to the PFRDA, NPS Trust or subscribers.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getFiveIII()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(iv)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"Whether the PFM has divulged to anybody, either orally or in writing, directly or indirectly any confidential information about the PFRDA, the NPS Trust or subscribers, which has come to its knowledge, without taking prior permission of the PFRDA,the NPS Trust except where such disclosures are required to be made in compliance with any law for the time being in force.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFiveIV()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(v)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"a) Whether the PFM has made adequate disclosures of potential areas of conflict of duties or interest to the PFRDA, the NPS Trust or subscribers.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFiveVa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"b) Whether the PFM hasestablished a mechanism to resolve any conflict of interest situation in an equitable manner, which may arise in the conduct of busines");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFiveVb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"c) Whether the PFM is satisfied that there has been no instances of self-dealing or front running or insider trading by any of the directors and Key personnel through their accounts or through their family members, relatives or friends. ");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFiveVc()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"Kindly provide list of directors and key personnel with status of submissions of self-declarations in Annexure 3.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,""); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(vi)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"a) Whether PFM has promptly informed the PFRDA and the NPS Trust, if there is any change in the registration status or any penal action taken by any Authority or any material change in financials which may adversely affect the interest of the subscriber");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFiveVIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell.setBottomBorderStyle(noBorder);
	        table2cell = table2row.createCell(80,"b) Whether the PFM has promptly informed the PFRDA and the NPS Trust about any action, legal proceedings initiated against it in respect of any material breach or non-compliance by it of any law, rules, regulations and directions of the PFRDA or any other regulatory body.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        table2cell = table2row.createCell(10,quaterended.getFiveVIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder);
	        


	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"6. Internal Auditors");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(i)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether appointment of the Internal Auditor and scope of internal audit is as per the Regulations/Guidance note issued by the PFRDA?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getSixI()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);			
			
			
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(ii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"a)Whether the PFM has produced to the Auditors such books, accounts, records and other documents in its custody or control and furnish such statement and information relating to its activities entrusted to its by the PFRDA, as it or he may require, within such reasonable time may be specified.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getSixIIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);	
			table2cell.setBottomBorderStyle(noBorder );		

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"Whether the PFM has allowed Auditors reasonable access to the premises occupied by it and also extend reasonable facility for examining any books, records, documents and computer data in the possession of the PFM.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getSixIIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);	
			table2cell.setTopBorderStyle(noBorder );						
			
			
			
			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"7.Related Party engagement / transaction");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(i)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"a) Whether any transactions or engagement have been carried out by the PFM with a related party except investments of National Pension SystemTrust funds? ");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getSevenIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);	
			table2cell.setBottomBorderStyle(noBorder );			

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"b) Whether prior permission of the NPS Trust was taken before entering into such engagement/transaction?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getSevenIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"c) Whether such engagement/transactions have been disclosed to the NPS Trust in its periodic reports.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getSevenIc()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"d) Whether such related party engagements / transactions aredone at fair market price?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getSevenId()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		
			table2cell.setBottomBorderStyle(noBorder );
			table2cell.setTopBorderStyle(noBorder );
			
			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"e) Whether such transaction is recurring in nature?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getSevenIe()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		
			table2cell.setTopBorderStyle(noBorder );		



			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"8.Operations / Data Security / Infrastructure");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        

	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(i)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"a) Whether the PFM has complied with circular no. PFRDA/2017/30/PF/4 dated09.10.2017 onguidelines on outsourcing of activities by the Pension Fund?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getEightIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);			
			table2cell.setBottomBorderStyle(noBorder );

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"b)Whether the PFM has complied with the reporting requirements of the circular no. PFRDA/2017/30/PF/4 dated 09.10.2017.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getEightIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);			
			table2cell.setTopBorderStyle(noBorder );

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(ii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether all investments are held in the name of NPS Trust?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getEightII()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		
			
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(iii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether PFM has pledged or hypothecated or lent any scheme assets which would amount to leverage on schemes portfolio?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getEightIII()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);					

	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(iv)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether all charges/fees debited to the schemes aredeterminedas stipulated by the PFRDA?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getEightIV()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		

	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(v)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether all interest,dividendsor any such accrual income and proceeds of redemption/sale were collected on due dates and promptly credited to the scheme accounts?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getEightV()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		

	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(vi)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"a.Whether the PFM has taken adequate and necessary steps to ensure that the data and records pertaining to its activities are maintained and are intact.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getEightVIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);	
			table2cell.setBottomBorderStyle(noBorder );

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"b.Whether the PFM has ensured that for electronic records and data, up-to-date backup is always available with it.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getEightVIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);	
			table2cell.setTopBorderStyle(noBorder );

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(vii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"a.Whether the PFM has maintained adequate infrastructure facilities to be able to discharge its services to the satisfaction of the PFRDA, the NPS Trust.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getEightVIIa()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);	
			table2cell.setBottomBorderStyle(noBorder );
			
			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"b.Whether the operating procedures and systems of the PFM are well documented and backed by operation manuals.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getEightVIIb()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);	
			table2cell.setTopBorderStyle(noBorder );

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"(viii)");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether the PFM has informed relevant institutions that no tax has to be deducted at source under the Income Tax Act.");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getEightVIII()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);	
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"9.Brokers empanelment:");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	      
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"*Whether Brokers empanelment is done in accordance to the guidelines issued by the PFRDA?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getNineA()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		
			table2cell.setBottomBorderStyle(noBorder );
	
			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"*Whether prescribed limit of percentage of transactions through any single broker is complied with?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
			table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getNineB()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);		
			table2cell.setTopBorderStyle(noBorder );
		
	

			table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"10. Inter-Scheme Investment Parameter");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
			
	        
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(80,"Whether all such Inter-Scheme transfers are in conformity with the investment objective of the scheme to which such transfer has been made?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell = table2row.createCell(10,quaterended.getTen()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	       
	
	

	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(100,"11.Voting Obligation");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(15);
	       
	
	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"a.Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getElevenA()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setBottomBorderStyle(noBorder );

	        table2row = table2.createRow(20);
	        table2cell = table2row.createCell(10,"");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(80,"b.Whether, voting report has been submitted to the NPS Trust?");
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder );
	        table2cell = table2row.createCell(10,quaterended.getElevenB()); 
	        table2cell.setAlign(HorizontalAlignment.LEFT);
	        table2cell.setFontSize(10);
	        table2cell.setTopBorderStyle(noBorder );
	        
	        
	        
	        table2.draw();
	        cos.close();
	        
	        OutputStream outputStream = null;
			File quaterendedTempfile = File.createTempFile("QuaterEnded"+quaterended.getId(), "pdf");
			outputStream = new FileOutputStream(quaterendedTempfile);
	        document.save(outputStream);
	        String filepath = quaterendedTempfile.getPath();
			String filename = quaterendedTempfile.getName();
	        //document.save("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\QuaterEnded.pdf");
	        document.close();
	        return fileUpload(themeDisplay, filepath, filename, resourceRequest);
	}
	@SuppressWarnings("deprecation")
	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest) {

		File file = new File(filepath);
		String mimeType = "application/pdf";
		String title = filename + ".pdf";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {
			Folder folder = getFolder(themeDisplay);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), title, mimeType, title, description, "",
					file, serviceContext);
			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			_log.info(e.getMessage());
			 _log.error(e);
		}
		return downloadUrl;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "NPSPDF");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	Log _log = LogFactoryUtil.getLog(getClass());
}
