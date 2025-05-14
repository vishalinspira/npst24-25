package com.monthly.compcertificate.reeource;

import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.daily.average.service.service.MnCompCertificateLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.monthly.compcertificate.constants.MonthlyCompCertificatePortletKeys;
import com.monthly.compcertificate.util.CompCertificateUtil;
import com.monthly.compcertificate.util.MonthlyCompCertificateCreatePdfUtil;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;




@Component(property = { 
		"javax.portlet.name=" + MonthlyCompCertificatePortletKeys.MONTHLYCOMPCERTIFICATE,
		"mvc.command.name=" + MonthlyCompCertificatePortletKeys.mnCompCertificate, 
		}, 
service = MVCResourceCommand.class)

public class MonthlyCompCertificate implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(MonthlyCompCertificate.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("Serve Resource method");

		 ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
				try {
					 PrintWriter writer = resourceResponse.getWriter();
			if(themeDisplay.isSignedIn()) {
				monthlyCompCertificate(resourceRequest, resourceResponse);
			}else {
				writer.print("You have successfully logout.Please login.");

			}
			writer.close();
			
		} catch (Exception e) {
			 _log.error(e);
		}
		
		return false;
	}
	
	public void monthlyCompCertificate(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws ParseException {
		
		
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date_1 = ParamUtil.getDate(resourceRequest, "date_1", dateFormat);
		
		String purchase1 = ParamUtil.getString(resourceRequest, "purchase1");
		String purchase2_1 = ParamUtil.getString(resourceRequest, "purchase2_1");
		String purchase2_2 = ParamUtil.getString(resourceRequest, "purchase2_2");
		String purchase2_3 = ParamUtil.getString(resourceRequest, "purchase2_3");
		String purchase2_4 = ParamUtil.getString(resourceRequest, "purchase2_4");
		String purchase2_5 = ParamUtil.getString(resourceRequest, "purchase2_5");
		String purchase3 = ParamUtil.getString(resourceRequest, "purchase3");
		String purchase4 = ParamUtil.getString(resourceRequest, "purchase4");
		
		String purchaseOfSecuritiesRemarks112= ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_1_2");
		String purchaseOfSecuritiesRemarks12a2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2a_2");
		String purchaseOfSecuritiesRemarks12b2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2b_2");
		String purchaseOfSecuritiesRemarks12c2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2c_2");
		String purchaseOfSecuritiesRemarks12d2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2d_2");
		String purchaseOfSecuritiesRemarks12e2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2e_2");
		String purchaseOfSecuritiesRemarks132 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_3_2");
		String purchaseOfSecuritiesRemarks142 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_4_2");
		String securitiesHeld212 = ParamUtil.getString(resourceRequest, "Securities_held_2_1_2");
		String securitiesHeld22a2 = ParamUtil.getString(resourceRequest, "Securities_held_2_2a_2");
		String securitiesHeld22b2 = ParamUtil.getString(resourceRequest, "Securities_held_2_2b_2");
		String securitiesHeld22c2 = ParamUtil.getString(resourceRequest, "Securities_held_2_2c_2");
		String securitiesHeld232 = ParamUtil.getString(resourceRequest, "Securities_held_2_3_2");
		String securitiesHeld242 = ParamUtil.getString(resourceRequest, "Securities_held_2_4_2");
		String securitiesHeld252 = ParamUtil.getString(resourceRequest, "Securities_held_2_5_2");
		String securitiesHeld262 = ParamUtil.getString(resourceRequest, "Securities_held_2_6_2");
		String saleOfSecurities31a2 = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1a_2");
		String saleOfSecurities31b2 = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1b_2");
		String saleOfecurities322 = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_2_2");
		String reports_andDisclosures41a2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1a_2");
		String reportsAndDisclosures41b2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1b_2");
		String reportsAndDisclosures41c2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1c_2");
		String reportsAndDisclosures41d2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1d_2");
		String reportsAndDisclosures41e2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1e_2");
		String reportsAndDisclosures42a2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2a_2");
		String reportsAndDisclosures42b2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2b_2");
		
		String disclosureRequirement51a2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1a_2");
		String disclosureRequirement51b2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1b_2");
		String disclosureRequirement51c2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1c_2");
		String disclosureRequirement51d2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1d_2");
		String disclosureRequirement51e2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1e_2");
		String disclosureRequirement51f2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1f_2");
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		String securities1 = ParamUtil.getString(resourceRequest, "securities1");
		String securities2_1 = ParamUtil.getString(resourceRequest, "securities2_1");
		String securities2_2 = ParamUtil.getString(resourceRequest, "securities2_2");
		String securities2_3 = ParamUtil.getString(resourceRequest, "securities2_3");
		String securities3 = ParamUtil.getString(resourceRequest, "securities3");
		String securities4 = ParamUtil.getString(resourceRequest, "securities4");
		String securities5 = ParamUtil.getString(resourceRequest, "securities5");
		String securities6 = ParamUtil.getString(resourceRequest, "securities6");
		
		String sale1_1 = ParamUtil.getString(resourceRequest, "sale1_1");
		String sale1_2 = ParamUtil.getString(resourceRequest, "sale1_2");
		String sale2 = ParamUtil.getString(resourceRequest, "sale2");
		
		String reports1_1 = ParamUtil.getString(resourceRequest, "reports1_1");
		String reports1_2 = ParamUtil.getString(resourceRequest, "reports1_2");
		String reports1_3 = ParamUtil.getString(resourceRequest, "reports1_3");
		String reports1_4 = ParamUtil.getString(resourceRequest, "reports1_4");
		String reports1_5 = ParamUtil.getString(resourceRequest, "reports1_5");
		String reports2_1 = ParamUtil.getString(resourceRequest, "reports2_1");
		String reports2_2 = ParamUtil.getString(resourceRequest, "reports2_2");
		
		String disclosure_1_1 = ParamUtil.getString(resourceRequest, "disclosure_1_1");
		String disclosure_1_2 = ParamUtil.getString(resourceRequest, "disclosure_1_2");
		String disclosure_1_3 = ParamUtil.getString(resourceRequest, "disclosure_1_3");
		String disclosure_1_4 = ParamUtil.getString(resourceRequest, "disclosure_1_4");
		String disclosure_1_5 = ParamUtil.getString(resourceRequest, "disclosure_1_5");
		String disclosure_1_6 = ParamUtil.getString(resourceRequest, "disclosure_1_6");
		
		String date_2 = ParamUtil.getString(resourceRequest, "date_2");
		String companies = ParamUtil.getString(resourceRequest, "companies");
		String employeeName = ParamUtil.getString(resourceRequest, "employeeName");
		String roles = ParamUtil.getString(resourceRequest, "roles");
		String strDate = ParamUtil.getString(resourceRequest, "date_3");
		Date date_3 = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
		String place = ParamUtil.getString(resourceRequest, "place");
		long annexure_a_i = CompCertificateUtil.addDocuments(resourceRequest, "annexureA_I");
		Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
		
		
		
		String purchaseOfSecuritiesRemarks112_NPST= ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_1");
		String purchaseOfSecuritiesRemarks12a2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2a");
		String purchaseOfSecuritiesRemarks12b2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2b");
		String purchaseOfSecuritiesRemarks12c2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2c");
		String purchaseOfSecuritiesRemarks12d2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2d");
		String purchaseOfSecuritiesRemarks12e2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2e");
		String purchaseOfSecuritiesRemarks132_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_3");
		String purchaseOfSecuritiesRemarks142_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_4");
		String securitiesHeld212_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_1");
		String securitiesHeld22a2_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_2a");
		String securitiesHeld22b2_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_2b");
		String securitiesHeld22c2_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_2c");
		String securitiesHeld232_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_3");
		String securitiesHeld242_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_4");
		String securitiesHeld252_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_5");
		String securitiesHeld262_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_6");
	
		String saleOfSecurities31a2_NPST = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1a");
		String saleOfSecurities31b2_NPST = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1b");
		String saleOfecurities322_NPST = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_2");
		String reports_andDisclosures41a2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1a");
		String reportsAndDisclosures41b2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1b");
		String reportsAndDisclosures41c2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1c");
		String reportsAndDisclosures41d2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1d");
		String reportsAndDisclosures41e2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1e");
		String reportsAndDisclosures42a2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2a");
		String reportsAndDisclosures42b2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2b");
		
		String disclosureRequirement51a2_NPST = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1a");
		String disclosureRequirement51b2_NPST = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1b");
		String disclosureRequirement51c2_NPST = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1c");
		String disclosureRequirement51d2_NPST = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1d");
		String disclosureRequirement51e2_NPST = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1e");
		String disclosureRequirement51f2_NPST = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1f");
		
		
		
		MnCompCertificate certificate=null;
		try {
		 certificate = mnCompCertificateLocalService.getByReportUploadlogIdAndIssubmitted(reportUploadLogId, true).get(0);
		}catch (Exception e) {
			_log.error(e);
		}
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		
//		try {
//			certificate = section1(resourceRequest);
//			_log.info("firstDetails " + certificate.getReportUploadLogId());
//		} catch (Exception e) {
//			 _log.error(e);
//		}
		
	//	if(Validator.isNotNull(certificate)) {
//			try {
//				_log.info("firstDetails " + certificate.getReportUploadLogId());
//				section2(resourceRequest, certificate);
//				section3(resourceRequest, certificate);
//				section4(resourceRequest, certificate);
//				section5(resourceRequest, certificate);
//				certificate = section6(resourceRequest, certificate, themeDisplay);
//			} catch (Exception e) {
//				 _log.error(e);
//			}
		String dDate[]=dateFormat.format(reportDate).split("-");

		int month=Integer.parseInt(dDate[1]);
		int year=Integer.parseInt(dDate[0]);
		if(month==1){
			year=year-1;
			month=12;
		}else{
			month=month-1;	
		}
		String formDate1=month+"/"+year;
			
		File file=MonthlyCompCertificateCreatePdfUtil.MonthlyComplianeCertificatePFMPDF(dateFormat.format(reportDate), formDate1, purchase1, 
					purchase2_1, purchase2_2, purchase2_3, purchase2_4, purchase2_5, purchase3, purchase4,reportUploadLogId,purchaseOfSecuritiesRemarks112,
					purchaseOfSecuritiesRemarks12a2,purchaseOfSecuritiesRemarks12b2,purchaseOfSecuritiesRemarks12c2,purchaseOfSecuritiesRemarks12d2,purchaseOfSecuritiesRemarks12e2,
					purchaseOfSecuritiesRemarks132,purchaseOfSecuritiesRemarks142,securitiesHeld212,securitiesHeld22a2,securitiesHeld22b2,securitiesHeld22c2,securitiesHeld232,
					securitiesHeld242,securitiesHeld252,securitiesHeld262,saleOfSecurities31a2,saleOfSecurities31b2,saleOfecurities322,reports_andDisclosures41a2,reportsAndDisclosures41b2,
					reportsAndDisclosures41c2,reportsAndDisclosures41d2,reportsAndDisclosures41e2,reportsAndDisclosures42a2,reportsAndDisclosures42b2,disclosureRequirement51a2,
					disclosureRequirement51b2,disclosureRequirement51c2,disclosureRequirement51d2,disclosureRequirement51e2,disclosureRequirement51f2,securities1, 
					securities2_1, securities2_2, securities2_3, securities3, securities4, securities5,securities6,sale1_1, sale1_2, sale2,reports1_1, 
					reports1_2, reports1_3, reports1_4, reports1_5,reports2_1, reports2_2, disclosure_1_1,disclosure_1_2,disclosure_1_3,disclosure_1_4,disclosure_1_5,disclosure_1_6,
					date_2, companies, employeeName, roles, date_3, place, purchaseOfSecuritiesRemarks112_NPST,
					purchaseOfSecuritiesRemarks12a2_NPST, purchaseOfSecuritiesRemarks12b2_NPST, purchaseOfSecuritiesRemarks12c2_NPST, purchaseOfSecuritiesRemarks12d2_NPST,
					purchaseOfSecuritiesRemarks12e2_NPST, purchaseOfSecuritiesRemarks132_NPST, purchaseOfSecuritiesRemarks142_NPST, securitiesHeld212_NPST, securitiesHeld22a2_NPST,
					securitiesHeld22b2_NPST, securitiesHeld22c2_NPST, securitiesHeld232_NPST, securitiesHeld242_NPST, securitiesHeld252_NPST,securitiesHeld262_NPST, saleOfSecurities31a2_NPST, 
					saleOfSecurities31b2_NPST, saleOfecurities322_NPST, reports_andDisclosures41a2_NPST, reportsAndDisclosures41b2_NPST, reportsAndDisclosures41c2_NPST, 
					reportsAndDisclosures41d2_NPST, reportsAndDisclosures41e2_NPST, reportsAndDisclosures42a2_NPST, reportsAndDisclosures42b2_NPST,
					disclosureRequirement51a2_NPST,disclosureRequirement51b2_NPST,disclosureRequirement51c2_NPST,disclosureRequirement51d2_NPST,disclosureRequirement51e2_NPST,
					disclosureRequirement51f2_NPST);
		long pdf_file_fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
		//long pdf_file_fileEntryId=0;
		
		try {
			if(pdf_file_fileEntryId>0) {			
		
			boolean reupload = false;
			if(Validator.isNotNull(certificate)) {
				reupload = true;
			}
			
			MnCompCertificate mnComp= mnCompCertificateLocalService.addMnCompCertificate(date_1, purchase1, 
					purchase2_1, purchase2_2, purchase2_3, purchase2_4, purchase2_5, purchase3, purchase4,reportUploadLogId,purchaseOfSecuritiesRemarks112,
					purchaseOfSecuritiesRemarks12a2,purchaseOfSecuritiesRemarks12b2,purchaseOfSecuritiesRemarks12c2,purchaseOfSecuritiesRemarks12d2,purchaseOfSecuritiesRemarks12e2,
					purchaseOfSecuritiesRemarks132,purchaseOfSecuritiesRemarks142,securitiesHeld212,securitiesHeld22a2,securitiesHeld22b2,securitiesHeld22c2,securitiesHeld232,
					securitiesHeld242,securitiesHeld252,securitiesHeld262,saleOfSecurities31a2,saleOfSecurities31b2,saleOfecurities322,reports_andDisclosures41a2,reportsAndDisclosures41b2,
					reportsAndDisclosures41c2,reportsAndDisclosures41d2,reportsAndDisclosures41e2,reportsAndDisclosures42a2,reportsAndDisclosures42b2,disclosureRequirement51a2,
					disclosureRequirement51b2,disclosureRequirement51c2,disclosureRequirement51d2,disclosureRequirement51e2,disclosureRequirement51f2,securities1, 
					securities2_1, securities2_2, securities2_3, securities3, securities4, securities5,securities6,sale1_1, sale1_2, sale2,reports1_1, 
					reports1_2, reports1_3, reports1_4, reports1_5, reports2_1, reports2_2,disclosure_1_1,disclosure_1_2,disclosure_1_3,disclosure_1_4,disclosure_1_5,disclosure_1_6,
					date_2, companies, employeeName, roles, date_3, place,annexure_a_i, 0, 
					0, 0, 0, 0, 0, 0, 0, new Date(), themeDisplay.getUserId(),pdf_file_fileEntryId,0);
		
			mnComp.setIsSubmitted(true);
			MnCompCertificateLocalServiceUtil.updateMnCompCertificate(mnComp);
			if(!reupload) {
				mnCompCertificateLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), pdf_file_fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, themeDisplay.getUserId(), themeDisplay.getUser().getFullName(), new Date(), serviceContext, "",true);
			}else {
				mnCompCertificateLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), pdf_file_fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, themeDisplay.getUserId(), themeDisplay.getUser().getFullName(), new Date(), serviceContext, "",false);
			}
			}
		} catch (Exception e) {
			_log.error(e);
			_log.error("Exception in Persisting the table data one  : "+e.getMessage());
			//isError = true;
		}
		
		
		
		
//			if(certificate.getReportUploadLogId() != 0) {
//				MnCompCertificate Finalsection = mnCompCertificateLocalService.fetchMnCompCertificate(certificate.getReportUploadLogId());
//				
//				_log.info("-------------- Finalsection -------------------"+Finalsection.toString());
//				
//				ServiceContext serviceContext = null;
//				try {
//					serviceContext = ServiceContextFactory.getInstance(resourceRequest);
//				} catch (PortalException e) {
//					 _log.error(e);
//				}
//				
//				_log.info("Finalsection.getWorkflowContext() ::::::: "+Finalsection.getWorkflowContext());
//				
//
//				try {
//					long fileEntryId = UploadDocumentUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "monthly_comp_certificate", "pdf");
//				
//					if (Finalsection.getWorkflowContext().trim().equals("")) {
//						_log.info("if ----------------------");
//						mnCompCertificateLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), fileEntryId, true, certificate.getReportUploadLogId(), WorkflowConstants.STATUS_PENDING, themeDisplay.getUserId(), themeDisplay.getUser().getFullName(), new Date(), serviceContext, "",true);
//					}else {
//						_log.info("else ----------------------");
//						mnCompCertificateLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), fileEntryId, true, certificate.getReportUploadLogId(), WorkflowConstants.STATUS_PENDING, themeDisplay.getUserId(), themeDisplay.getUser().getFullName(), new Date(), serviceContext, "",false);			
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					 _log.error(e);
//				} 
				
				
				
			//}
//		}

	}
	
	
	public MnCompCertificate section1(ResourceRequest resourceRequest) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date_1 = ParamUtil.getDate(resourceRequest, "date_1", dateFormat);
		
		String purchase1 = ParamUtil.getString(resourceRequest, "purchase1");
		String purchase2_1 = ParamUtil.getString(resourceRequest, "purchase2_1");
		String purchase2_2 = ParamUtil.getString(resourceRequest, "purchase2_2");
		String purchase2_3 = ParamUtil.getString(resourceRequest, "purchase2_3");
		String purchase2_4 = ParamUtil.getString(resourceRequest, "purchase2_4");
		String purchase2_5 = ParamUtil.getString(resourceRequest, "purchase2_5");
		String purchase3 = ParamUtil.getString(resourceRequest, "purchase3");
		String purchase4 = ParamUtil.getString(resourceRequest, "purchase4");
		
		String purchaseOfSecuritiesRemarks112= ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_1_2");
		String purchaseOfSecuritiesRemarks12a2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2a_2");
		String purchaseOfSecuritiesRemarks12b2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2b_2");
		String purchaseOfSecuritiesRemarks12c2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2c_2");
		String purchaseOfSecuritiesRemarks12d2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2d_2");
		String purchaseOfSecuritiesRemarks12e2 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2e_2");
		String purchaseOfSecuritiesRemarks132 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_3_2");
		String purchaseOfSecuritiesRemarks142 = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_4_2");
		String securitiesHeld212 = ParamUtil.getString(resourceRequest, "Securities_held_2_1_2");
		String securitiesHeld22a2 = ParamUtil.getString(resourceRequest, "Securities_held_2_2a_2");
		String securitiesHeld22b2 = ParamUtil.getString(resourceRequest, "Securities_held_2_2b_2");
		String securitiesHeld22c2 = ParamUtil.getString(resourceRequest, "Securities_held_2_2c_2");
		String securitiesHeld232 = ParamUtil.getString(resourceRequest, "Securities_held_2_3_2");
		String securitiesHeld242 = ParamUtil.getString(resourceRequest, "Securities_held_2_4_2");
		String securitiesHeld252 = ParamUtil.getString(resourceRequest, "Securities_held_2_5_2");
		String securitiesHeld262 = ParamUtil.getString(resourceRequest, "Securities_held_2_6_2");
		String saleOfSecurities31a2 = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1a_2");
		String saleOfSecurities31b2 = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1b_2");
		String saleOfecurities322 = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_2_2");
		String reports_andDisclosures41a2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1a_2");
		String reportsAndDisclosures41b2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1b_2");
		String reportsAndDisclosures41c2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1c_2");
		String reportsAndDisclosures41d2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1d_2");
		String reportsAndDisclosures41e2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1e_2");
		String reportsAndDisclosures42a2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2a_2");
		String reportsAndDisclosures42b2 = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2b_2");
		String disclosureRequirement51a2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1a_2");
		String disclosureRequirement51b2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1b_2");
		String disclosureRequirement51c2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1c_2");
		String disclosureRequirement51d2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1d_2");
		String disclosureRequirement51e2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1e_2");
		String disclosureRequirement51f2 = ParamUtil.getString(resourceRequest, "Disclosure_requirement_5_1f_2");
		
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info(date_1);
		_log.info(purchase1 + " " + purchase2_1 + " " + purchase2_2 + " " + purchase2_3);
		_log.info(purchase2_4 + " " + purchase2_5 + " " + purchase3 + " " + purchase4);
		_log.info(purchaseOfSecuritiesRemarks112+" "+purchaseOfSecuritiesRemarks12a2+" "+securitiesHeld212);
		
		MnCompCertificate firstDetails = mnCompCertificateLocalService.addSection1(date_1, purchase1, 
				purchase2_1, purchase2_2, purchase2_3, purchase2_4, purchase2_5, purchase3, purchase4,reportUploadLogId,purchaseOfSecuritiesRemarks112,
				purchaseOfSecuritiesRemarks12a2,purchaseOfSecuritiesRemarks12b2,purchaseOfSecuritiesRemarks12c2,purchaseOfSecuritiesRemarks12d2,purchaseOfSecuritiesRemarks12e2,
				purchaseOfSecuritiesRemarks132,purchaseOfSecuritiesRemarks142,securitiesHeld212,securitiesHeld22a2,securitiesHeld22b2,securitiesHeld22c2,securitiesHeld232,
				securitiesHeld242,securitiesHeld252,securitiesHeld262,saleOfSecurities31a2,saleOfSecurities31b2,saleOfecurities322,reports_andDisclosures41a2,reportsAndDisclosures41b2,
				reportsAndDisclosures41c2,reportsAndDisclosures41d2,reportsAndDisclosures41e2,reportsAndDisclosures42a2,reportsAndDisclosures42b2,disclosureRequirement51a2,
				disclosureRequirement51b2,disclosureRequirement51c2,disclosureRequirement51d2,disclosureRequirement51e2,disclosureRequirement51f2);
		
		
		return firstDetails;
		
	}

	public MnCompCertificate section2(ResourceRequest resourceRequest, MnCompCertificate sectionTwoDetails) {
			
		String securities1 = ParamUtil.getString(resourceRequest, "securities1");
		String securities2_1 = ParamUtil.getString(resourceRequest, "securities2_1");
		String securities2_2 = ParamUtil.getString(resourceRequest, "securities2_2");
		String securities2_3 = ParamUtil.getString(resourceRequest, "securities2_3");
		String securities3 = ParamUtil.getString(resourceRequest, "securities3");
		String securities4 = ParamUtil.getString(resourceRequest, "securities4");
		String securities5 = ParamUtil.getString(resourceRequest, "securities5");
		String securities6 = ParamUtil.getString(resourceRequest, "securities6");
		
		_log.info(securities1 + " " + securities2_1 + " " + securities2_2 + " " + securities2_3);
		_log.info(securities3 + " " + securities4 + " " + securities5+" "+securities6);
		
		MnCompCertificate section2 = mnCompCertificateLocalService.addSection2(sectionTwoDetails, securities1, 
				securities2_1, securities2_2, securities2_3, securities3, securities4, securities5,securities6);
		
		return section2;
			
	}
	
	public MnCompCertificate section3(ResourceRequest resourceRequest, MnCompCertificate sectionThreeDetails) {
		
		String sale1_1 = ParamUtil.getString(resourceRequest, "sale1_1");
		String sale1_2 = ParamUtil.getString(resourceRequest, "sale1_2");
		String sale2 = ParamUtil.getString(resourceRequest, "sale2");
		
		_log.info(sale1_1 + " " + sale1_2 + " " + sale2);
		
		MnCompCertificate section3 = mnCompCertificateLocalService.addSection3(sectionThreeDetails, sale1_1, 
				sale1_2, sale2);
		
		return section3;
			
	}
	
	public MnCompCertificate section4(ResourceRequest resourceRequest, MnCompCertificate sectionFourDetails) {
		
		String reports1_1 = ParamUtil.getString(resourceRequest, "reports1_1");
		String reports1_2 = ParamUtil.getString(resourceRequest, "reports1_2");
		String reports1_3 = ParamUtil.getString(resourceRequest, "reports1_3");
		String reports1_4 = ParamUtil.getString(resourceRequest, "reports1_4");
		String reports1_5 = ParamUtil.getString(resourceRequest, "reports1_5");
		String reports2_1 = ParamUtil.getString(resourceRequest, "reports2_1");
		String reports2_2 = ParamUtil.getString(resourceRequest, "reports2_2");
		
		//Adding Section 5 here
		
		String disclosure_1_1 = ParamUtil.getString(resourceRequest, "disclosure_1_1");
		String disclosure_1_2 = ParamUtil.getString(resourceRequest, "disclosure_1_2");
		String disclosure_1_3 = ParamUtil.getString(resourceRequest, "disclosure_1_3");
		String disclosure_1_4 = ParamUtil.getString(resourceRequest, "disclosure_1_4");
		String disclosure_1_5 = ParamUtil.getString(resourceRequest, "disclosure_1_5");
		String disclosure_1_6 = ParamUtil.getString(resourceRequest, "disclosure_1_6");
		
		_log.info(reports1_1 + " " + reports1_2 + " " + reports1_3 + " " + reports1_4);
		_log.info(reports1_5 + " " + reports2_1 + " " + reports2_2);
		_log.info("Section 5 ");
		_log.info(disclosure_1_1 + " " + disclosure_1_2 + " " + disclosure_1_3 + " " + disclosure_1_4 +" " + disclosure_1_5 + " " + disclosure_1_6);
		
		MnCompCertificate section4 = mnCompCertificateLocalService.addSection4(sectionFourDetails, reports1_1, 
				reports1_2, reports1_3, reports1_4, reports1_5, reports2_1, reports2_2,disclosure_1_1,disclosure_1_2,
				disclosure_1_3,disclosure_1_4,disclosure_1_5,disclosure_1_6);
		
		return section4;
			
	}
	
	public MnCompCertificate section5(ResourceRequest resourceRequest, MnCompCertificate sectionFiveDetails) throws ParseException {
		
		String date_2 = ParamUtil.getString(resourceRequest, "date_2");
		String companies = ParamUtil.getString(resourceRequest, "companies");
		String employeeName = ParamUtil.getString(resourceRequest, "employeeName");
		String roles = ParamUtil.getString(resourceRequest, "roles");
		String strDate = ParamUtil.getString(resourceRequest, "date_3");
		Date date_3 = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
		String place = ParamUtil.getString(resourceRequest, "place");
		
		_log.info(date_2 + " " + companies + " " + employeeName + " " + roles);
		_log.info(date_3 + " " + place);
		
		MnCompCertificate section5 = mnCompCertificateLocalService.addSection5(sectionFiveDetails, date_2, 
				companies, employeeName, roles, date_3, place);
		
		return section5;
			
	}
	
	public MnCompCertificate section6(ResourceRequest resourceRequest, MnCompCertificate sectionSixDetails, ThemeDisplay themeDisplay) throws ParseException {
		
		
		long annexure_a_i = CompCertificateUtil.addDocuments(resourceRequest, "annexureA_I");
//		long annexure_a_ii = CompCertificateUtil.addDocuments(resourceRequest, "annexureA_II");
//		long annexure_b = CompCertificateUtil.addDocuments(resourceRequest, "annexureB");
//		long annexure_c = CompCertificateUtil.addDocuments(resourceRequest, "annexureC");
//		long annexure_d = CompCertificateUtil.addDocuments(resourceRequest, "annexureD");
//		long annexure_e = CompCertificateUtil.addDocuments(resourceRequest, "annexureE");
//		long annexure_f = CompCertificateUtil.addDocuments(resourceRequest, "annexureF");
//		long annexure_g = CompCertificateUtil.addDocuments(resourceRequest, "annexureG");
//		long annexure_h = CompCertificateUtil.addDocuments(resourceRequest, "annexureH");
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		_log.info("reportUploadID:::" + reportUploadLogId);
//		_log.info(annexure_a_i + " " + annexure_a_ii + " " + annexure_b + " " + annexure_c);
//		_log.info(annexure_d + " " + annexure_e + " " + annexure_f + " " + annexure_g);
//		_log.info(annexure_h);
		
		
//		MnCompCertificate section6 = mnCompCertificateLocalService.addSection6(sectionSixDetails, annexure_a_i, annexure_a_ii, 
//				annexure_b, annexure_c, annexure_d, annexure_e, annexure_f, annexure_g, annexure_h, 
//				new Date(), themeDisplay.getUserId());
		MnCompCertificate section6 = mnCompCertificateLocalService.addSection6(sectionSixDetails, annexure_a_i, 0, 
				0, 0, 0, 0, 0, 0, 0, 
				new Date(), themeDisplay.getUserId());
		

		
		return section6;
			
	}
	/*public long createPdf(MnCompCertificate certificate , ThemeDisplay themeDisplay, 
			ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String 
			statusByUserName, ServiceContext serviceContext2) throws IOException, JSONException  {
		
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;
        
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        
        // PDRectangle.LETTER and others are also possible
        PDRectangle rect = page.getMediaBox();
        
        // rect can be used to get the page width and height
        document.addPage(page);

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos = new PDPageContentStream(document, page);
        //cos.b
	    //Dummy Table
        float margin = 30;
        _log.info("rect.getHeight()"+rect.getHeight());
        
        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = rect.getUpperRightY() - (2 * margin);
        
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float topMargin = 0;
        float bottomMargin = 10;
        
        // y position is your coordinate of top left corner of the table
        float yPosition = rect.getUpperRightY()-margin;

        BaseTable table = new BaseTable(yPosition, yStartNewPage,topMargin,
            bottomMargin, tableWidth, margin, document, page, false, drawContent);
        //new Baset

        // the parameter is the row height
        Row<PDPage> headerRow = table.createRow(20);
        
        // the first parameter is the cell width        
        Cell<PDPage> cell = headerRow.createCell(100, "Monthly Compliance Certificate");
        cell.setFontSize(20);
        cell.setAlign(HorizontalAlignment.CENTER);
        table.addHeaderRow(headerRow);
        
        table.draw();

        float tableHeight = table.getHeaderAndDataHeight();
        _log.info("tableHeight = "+tableHeight);
        
        float table2YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) -30;
        
        BaseTable table2 = new BaseTable(table2YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
        
        Row<PDPage> table2row = table2.createRow(20);
        Cell<PDPage> table2cell = table2row.createCell(100, "Report Due Date: " + dateFormat.format(new Date()));
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "For the Month Ended: " + certificate.getDate_1());
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "To,");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "The Chief Executive Officer");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "NPS Trust");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "14th Floor, IFCI Tower,");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "61, Nehru Place, N. Delhi - 110019");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "Sir,");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2row = table2.createRow(20);
        table2cell = table2row.createCell(100, "In my opinion and to the best of my information and according to the examinations carried out by me and explanations furnished to me, I certify the following in respect of the month mentioned above");
        table2cell.setAlign(HorizontalAlignment.LEFT);
        table2cell.setFontSize(10);
        
        table2.draw();
        
        float tableHeight2 = table2.getHeaderAndDataHeight();
        _log.info("tableHeight2 = "+tableHeight2);
        
        float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 20;
        BaseTable table3 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
        
        Row<PDPage> table3row = table3.createRow(20);
        Cell<PDPage> table3cell = table3row.createCell(100, "1 . Purchase of securities");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "1.1");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55, "Whether purchase of securities adhere to the Investment guidelines issued by PFRDA.(prescribed securities/ percentage/ limit/ prudential & exposure norms Details of deviations provided in annexure A (i).");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getPurchase_of_securities());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "1.2");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55, "(a) Whether a detailed Investment process manual is prepared and approved by Board / board delegated authority and whether actual process is established as per approved manual.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getDetailed_investment());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "Current Status");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(b) Whether investments are approved by the committee/competent authority as per Approval delegation matrix");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getInvestments_approved());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(c) Whether each decision of investment is properly documented and record is maintained at individual transaction level.(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getDecision_of_investment());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(d) Whether investments for non-dematerialized securities are supported by physical certificates");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getInvestments_non_dematerialized());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(e) Whether all investments from funds received under NPS schemes are held in the name of NPS Trust");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getAll_investments_from_funds());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "1.3");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"Whether delivery of securities is taken immediately on purchase as per settlement cycle/ terms for each transaction.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getDelivery_of_securities_purch());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "1.4");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"Whether any application/investment is done in Initial Public Offer (IPO), Follow on Public Offer (FPO) and/or Offer for sale (OFS) during the period? (As per PFRDA circular no. PFRDA/2021/32/REG-PF/4 dated 27.07.2021, such investments to be reported to NPS Trust within 30 days of making such investments)Details of Investments provided in Annexure B.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getInvestment_done_in_ipo());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(100, "2 . Securities held");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "2.1");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55," Whether scheme investments adhere to the Investment guidelines issued by PFRDA.(prescribed securities/ percentage/ limit/ prudential & exposure norms)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getScheme_investments());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "2.2");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"a) Whether stop loss trigger has occurred for any security during the month as per Investment policy of the PFM.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getStop_loss_trigger());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55," b) Whether decision in such scenario is approved by the committee/competent authority as per Approval delegation matrix ");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getDecision_approved_by_committee());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"c) Whether each decision along with rationale is properly documented and record is maintained at individual scrip level.(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)Details of stop loss occurred during the month and its decision provided in Annexure C.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getDecision_properly_documented());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "2.3");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"Whether inter-scheme transfer of securities complies with point 14 of the Investment Guideline circular number PFRDA/2021/29/REG-PF/3 dated 20.07.2021.Details of inter scheme transfer provided in Annexure D.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getInter_scheme_transfer());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "2.4");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"Whether investment is held in any equity shares of a body corporate which is not listed in top 200 list of stocks prepared by NPS Trust and is pending for rebalancing.Details provided in Annexure E");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10,certificate.getInvestment_held_in_equity());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "2.5");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"Whether investment in any equity shares through IPO/FPO/OFS does not fulfil the market capitalization condition prescribed under investment guidelines post listing.Details provided in Annexure F.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getInvestment_in_equity_shares());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(100, "3 . Sale of securities");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "3.1");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(a) Whether disinvestments made are approved by the committee/competent authority as per delegation matrix");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getDisinvestments_approved());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(b) Whether each decision of disinvestment is properly documented and record is maintained at individual transaction level.(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10,certificate.getDecision_of_disinvestment() );
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"Whether delivery of securities is given immediately on sale as per settlement cycle/ terms for each transaction.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getDelivery_of_securities_sale());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(100, "4 . Reports and Disclosures as per Schedule V of PFRDA (PF) Regulations");
        table3cell.setAlign(HorizontalAlignment.LEFT);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "4.1");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(a) Whether all investment charges (Investment management fees, Custodian charges, SEBI charges, NPS Trust fees etc.) are loaded onto the NAV on a daily basis");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getAll_investment_charges());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(b) Whether PFM has adhered to instructions of PFRDA to get the NAV verified by the auditors on a daily basis.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getPfm_adhered());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(c) Whether the records of the audit of NAV have been maintained by the pension fund for future inspection.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getRecords_of_the_audit_of_nav());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(d) Whether scheme-wise NAV (latest & historical) is uploaded daily on the PFM's website within the prescribed time limit.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getScheme_wise_nav_uploaded());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(e) Whether scheme-wise NAV is submitted daily to all the CRA's within the prescribed time limit.Instances of delays during the month in uploading NAV on PFM's website and submission to CRA with the reasons provided in Annexure G.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getScheme_wise_nav_submitted());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "4.2");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(a) Whether monthly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the month.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getMonthly_reports_submitted());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3row = table3.createRow(20);
        table3cell = table3row.createCell(5, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(55,"(b) Whether scrip wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(10, certificate.getScrip_wise_details());
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell = table3row.createCell(30, "");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFontSize(10);
        
        table3.draw();
        
        float tableHeight3 = table3.getHeaderAndDataHeight();
        _log.info("tableHeight3 = "+tableHeight3);
        
        float table4YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight3 - 20;
        BaseTable table4 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
        
        Row<PDPage> table4row = table4.createRow(20);
        Cell<PDPage> table4cell = table4row.createCell(100, "Note:");
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(100, "1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.");
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(100, "2) This Compliance Certificate(s) shall be put up to the Board at its meeting to be held on" + certificate.getDate_2() +  "and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.");
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(100, "3) The details of latest development in the NPA are mentioned in 'Annexure H'.Certified that the information given, herein are correct and complete to the best of my knowledge and belief.");
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(100,certificate.getCompany_name() );
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(100, "Name:" + certificate.getEmplolyee_name());
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(100, "Role:" + certificate.getRoles());
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(100, "Date:" + certificate.getDate_3());
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        table4row = table4.createRow(20);
        table4cell = table4row.createCell(100, "Place:" + certificate.getPlace());
        table4cell.setAlign(HorizontalAlignment.LEFT);
        table4cell.setFontSize(10);
        
        
     
        table4.draw();
        
        
        cos.close();
        
        OutputStream outputStream = null;
		File npaDevelopmentFile = File.createTempFile("Monthly Compliance Certificate", "pdf");
		outputStream = new FileOutputStream(npaDevelopmentFile);
		document.save(outputStream);
        String filepath = npaDevelopmentFile.getPath(); 
		String filename = npaDevelopmentFile.getName();
		
//		document.save("E:\\pdf\\npaDevelopment.pdf");
        
		document.close();
        outputStream.close();
        long fileentryid = 0l;
        try {
        	fileentryid = pdfUpload(themeDisplay, filepath, filename, resourceRequest, createdBy, reportUploadLogId, statusByUserName, serviceContext2);
		} catch (Exception e) {
			_log.error("Exception in uploading Closing Balance Form PDF: " + e.getMessage());
		}
		return fileentryid;
		
		
	}*/
	
	
	private long uploadFile(File file,ThemeDisplay themeDisplay,ResourceRequest resourceRequest){
		Date date=new Date();
	
		String fileName = date.getTime()+"_"+"Monthly Comp Certificate"+".pdf";
		String title = fileName; 
		String description = "Monthly Comp Certificate PDF";
		

		String mimeType =  MimeTypesUtil.getContentType(file);

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();
		}catch (Exception e) {
			_log.error("error while uploading file:  "+e.getMessage());
		}
		return repositoryId;
			

		
	}
	
//	@SuppressWarnings("deprecation")
//	public long pdfUpload(ThemeDisplay themeDisplay, String filepath, String filename,
//			ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext2) {
//		Date date = new Date();
//		File file = new File(filepath);
//		String mimeType = "application/pdf";
//		String title = "Monthly Comp Certificate" +date.getTime()+ ".pdf";
//		String description = "This file is added via programatically";
//		long repositoryId = themeDisplay.getScopeGroupId();
//		String downloadUrl = StringPool.BLANK;
//		try {
//			Folder folder = getFolder(themeDisplay);
//			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
//					resourceRequest);
//			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), title, mimeType, title, description, "",
//					file, serviceContext);
//			return entry.getFileEntryId();
//			//FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
//			//downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
//		} catch (Exception e) {
//			_log.info(e.getMessage());
//			 _log.error(e);
//		}
//		return 0l;
//	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	

	@Reference
	MnCompCertificateLocalService mnCompCertificateLocalService;

}
