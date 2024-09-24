package com.monthly.compcertificate.scrutiny;

import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.daily.average.service.service.MnCompCertificateScrutinyLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.monthly.compcertificate.constants.MonthlyCompCertificatePortletKeys;
import com.monthly.compcertificate.util.CompCertificateUtil;
import com.monthly.compcertificate.util.MonthlyCompCertificateCreatePdfUtil;
import com.monthly.compcertificate.util.PFMMccNonNps;

import java.io.File;
import java.io.IOException;
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
		"javax.portlet.name=" + MonthlyCompCertificatePortletKeys.MONTHLYCOMPCERTIFICATESCRUTINY,
		"mvc.command.name=/monthly/saveMonthlyComCertificateForm" 
		}, 
service = MVCResourceCommand.class)

public class Save_Comp_Certificate_scrutiny implements MVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(Save_Comp_Certificate_scrutiny.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

	    _log.info("Save_Comp_Certificate_scrutiny:::::::::::::::::::::::::::::::");
	    try {
	    ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
	    Boolean isNonNPSUser = PFMMccNonNps.isNonNpsUser(themeDisplay.getUserId());
	    boolean isError = false;
	    long reportUploadLogId=ParamUtil.getLong(resourceRequest, "reportUploadLogId");
	    MnCompCertificate certificate = mnCompCertificateLocalService.fetchMnCompCertificate(reportUploadLogId);
	    
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date reportDate =certificate.getReportDate();
		
		//Date date_1 = ParamUtil.getDate(resourceRequest, "date_1", dateFormat);
		
		String purchase1 = ParamUtil.getString(resourceRequest, "purchase1");
		_log.info("purchase1:  "+purchase1);
		String purchase2_1 = ParamUtil.getString(resourceRequest, "purchase1-2");
		String purchase2_2 = ParamUtil.getString(resourceRequest, "purchaseb");
		String purchase2_3 = ParamUtil.getString(resourceRequest, "purchasec");
		String purchase2_4 = ParamUtil.getString(resourceRequest, "purchased");
		String purchase2_5 = ParamUtil.getString(resourceRequest, "purchasee");
		String purchase3 = ParamUtil.getString(resourceRequest, "purchase1-3");
		String purchase4 = ParamUtil.getString(resourceRequest, "purchase1-4");
		
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
		
		String securities1 = ParamUtil.getString(resourceRequest, "purchase2-1");
		String securities2_1 = ParamUtil.getString(resourceRequest, "purchase2-2a");
		String securities2_2 = ParamUtil.getString(resourceRequest, "purchase2-2b");
		String securities2_3 = ParamUtil.getString(resourceRequest, "purchase2-2c");
		String securities3 = ParamUtil.getString(resourceRequest, "purchase2-3");
		String securities4 = ParamUtil.getString(resourceRequest, "purchase2-4");
		String securities5 = ParamUtil.getString(resourceRequest, "purchase2-5");

		String sale1_1 = ParamUtil.getString(resourceRequest, "purchase3-1a");
		String sale1_2 = ParamUtil.getString(resourceRequest, "purchase3-1b");
		String sale2 = ParamUtil.getString(resourceRequest, "purchase3-2");
		
		String reports1_1 = ParamUtil.getString(resourceRequest, "purchase4-1a");
		String reports1_2 = ParamUtil.getString(resourceRequest, "purchase4-1b");
		String reports1_3 = ParamUtil.getString(resourceRequest, "purchase4-1c");
		String reports1_4 = ParamUtil.getString(resourceRequest, "purchase4-1d");
		String reports1_5 = ParamUtil.getString(resourceRequest, "purchase4-1e");
		String reports2_1 = ParamUtil.getString(resourceRequest, "purchase4-2a");
		String reports2_2 = ParamUtil.getString(resourceRequest, "purchase4-2b");
		
//		String date_2 = ParamUtil.getString(resourceRequest, "date_2");
//		String companies = ParamUtil.getString(resourceRequest, "companies");
//		String employeeName = ParamUtil.getString(resourceRequest, "employeeName");
//		String roles = ParamUtil.getString(resourceRequest, "roles");
//		String strDate = ParamUtil.getString(resourceRequest, "date_3");
//		Date date_3 = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
//		String place = ParamUtil.getString(resourceRequest, "place");
		
		String date_2 = certificate.getDate_2();
		String companies = certificate.getCompany_name();
		String employeeName = certificate.getEmplolyee_name();
		String roles = certificate.getRoles();
		//String strDate = certificate.getDate_3();
		Date date_3 = certificate.getDate_3();
		String place = certificate.getPlace();
		
		
//		String purchaseOfSecuritiesRemarks112_NPST= ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_1");
//		String purchaseOfSecuritiesRemarks12a2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2a");
//		String purchaseOfSecuritiesRemarks12b2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2b");
//		String purchaseOfSecuritiesRemarks12c2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2c");
//		String purchaseOfSecuritiesRemarks12d2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2d");
//		String purchaseOfSecuritiesRemarks12e2_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2e");
//		String purchaseOfSecuritiesRemarks132_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_3");
//		String purchaseOfSecuritiesRemarks142_NPST = ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_4");
//		String securitiesHeld212_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_1");
//		String securitiesHeld22a2_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_2a");
//		String securitiesHeld22b2_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_2b");
//		String securitiesHeld22c2_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_2c");
//		String securitiesHeld232_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_3");
//		String securitiesHeld242_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_4");
//		String securitiesHeld252_NPST = ParamUtil.getString(resourceRequest, "Securities_held_2_5");
//		String saleOfSecurities31a2_NPST = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1a");
//		String saleOfSecurities31b2_NPST = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1b");
//		String saleOfecurities322_NPST = ParamUtil.getString(resourceRequest, "Sale_of_securities_3_2");
//		String reports_andDisclosures41a2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1a");
//		String reportsAndDisclosures41b2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1b");
//		String reportsAndDisclosures41c2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1c");
//		String reportsAndDisclosures41d2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1d");
//		String reportsAndDisclosures41e2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1e");
//		String reportsAndDisclosures42a2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2a");
//		String reportsAndDisclosures42b2_NPST = ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2b");
		
		String purchase_of_securities_rem=ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_1");
		String detailed_investment_rem=ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2a");
		String investments_approved_rem=ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2b");
		String decision_of_investment_rem=ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2c");
		String invest_non_dematerialized_rem=ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2d");
		String all_investments_from_funds_rem=ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_2e");
	    String delivery_of_security_purch_rem=ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_3");
		String investment_done_in_ipo_rem=ParamUtil.getString(resourceRequest, "Purchase_of_securitiesremarks_1_4");
		String scheme_investments_rem=ParamUtil.getString(resourceRequest, "Securities_held_2_1");
		String stop_loss_trigger_rem=ParamUtil.getString(resourceRequest, "Securities_held_2_2a");
		String decision_appr_by_committee_rem=ParamUtil.getString(resourceRequest, "Securities_held_2_2b");
		String decision_prop_documented_rem=ParamUtil.getString(resourceRequest, "Securities_held_2_2c");
		String inter_scheme_transfer_rem=ParamUtil.getString(resourceRequest, "Securities_held_2_3");
		String investment_held_in_equity_rem=ParamUtil.getString(resourceRequest, "Securities_held_2_4");
		String invest_in_equity_shares_rem=ParamUtil.getString(resourceRequest, "Securities_held_2_5");
		String disinvestments_approved_rem=ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1a");
		String decision_of_disinvestment_rem=ParamUtil.getString(resourceRequest, "Sale_of_securities_3_1b");
		String delivery_of_security_sale_rem=ParamUtil.getString(resourceRequest, "Sale_of_securities_3_2");
		String all_investment_charges_rem=ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1a");
		String pfm_adhered_rem=ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1b");
        String records_of_audit_of_nav_rem=ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1c");
		String scheme_wise_nav_uploaded_rem=ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1d");
		String scheme_wise_nav_submitted_rem=ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_1e");
		String monthly_reports_submitted_rem=ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2a");
		String scrip_wise_details_rem=ParamUtil.getString(resourceRequest, "Reports_and_Disclosures_4_2b");
		
		long annexure_a_i = certificate.getAnnexure_a_i();
	    
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
		
		
		_log.info("isNonNPSUser========"+isNonNPSUser);
		if(isNonNPSUser) {
			try {
				
				File file=MonthlyCompCertificateCreatePdfUtil.MonthlyComplianeCertificatePFMPDF(dateFormat.format(reportDate), formDate1, purchase1, 
						purchase2_1, purchase2_2, purchase2_3, purchase2_4, purchase2_5, purchase3, purchase4,certificate.getReportUploadLogId(),purchaseOfSecuritiesRemarks112,
						purchaseOfSecuritiesRemarks12a2,purchaseOfSecuritiesRemarks12b2,purchaseOfSecuritiesRemarks12c2,purchaseOfSecuritiesRemarks12d2,purchaseOfSecuritiesRemarks12e2,
						purchaseOfSecuritiesRemarks132,purchaseOfSecuritiesRemarks142,securitiesHeld212,securitiesHeld22a2,securitiesHeld22b2,securitiesHeld22c2,securitiesHeld232,
						securitiesHeld242,securitiesHeld252,saleOfSecurities31a2,saleOfSecurities31b2,saleOfecurities322,reports_andDisclosures41a2,reportsAndDisclosures41b2,
						reportsAndDisclosures41c2,reportsAndDisclosures41d2,reportsAndDisclosures41e2,reportsAndDisclosures42a2,reportsAndDisclosures42b2,securities1, 
						securities2_1, securities2_2, securities2_3, securities3, securities4, securities5,sale1_1, sale1_2, sale2,reports1_1, 
						reports1_2, reports1_3, reports1_4, reports1_5, reports2_1, reports2_2,date_2, companies, employeeName, roles, date_3, place, purchase_of_securities_rem,
						detailed_investment_rem, investments_approved_rem, decision_of_investment_rem, invest_non_dematerialized_rem,
						all_investments_from_funds_rem, delivery_of_security_purch_rem, investment_done_in_ipo_rem, scheme_investments_rem, stop_loss_trigger_rem,
						decision_appr_by_committee_rem, decision_prop_documented_rem, inter_scheme_transfer_rem, investment_held_in_equity_rem, invest_in_equity_shares_rem, disinvestments_approved_rem, 
						decision_of_disinvestment_rem, delivery_of_security_sale_rem, all_investment_charges_rem, pfm_adhered_rem, records_of_audit_of_nav_rem, 
						scheme_wise_nav_uploaded_rem, scheme_wise_nav_submitted_rem, monthly_reports_submitted_rem, scrip_wise_details_rem);
				ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
				long fileEntryId=certificate.getFileEntryId();
				int isAmRejected=0;
				ReportUploadFileLog reportUploadFileLog =null;
				try {
					 reportUploadFileLog = ReportUploadFileLogLocalServiceUtil.findByFileEntryId(fileEntryId);
					FileEntry dlFile=DLAppLocalServiceUtil.getFileEntry(fileEntryId);
//				if(certificate.getIsAmRejected()==1 || Validator.isNotNull(reportUploadFileLog.getSignature())) {
					if(Validator.isNotNull(reportUploadFileLog.getSignature())) {
					fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
					
					ReportUploadFileLogLocalServiceUtil.addReportUploadFileLog(certificate.getReportUploadLogId(), fileEntryId, themeDisplay.getUserId());
					reportUploadFileLog = ReportUploadFileLogLocalServiceUtil.findByFileEntryId(fileEntryId);
					isAmRejected=2;
				}else {
					if(Validator.isNull(reportUploadFileLog.getSignature())) {
					DLVersionNumberIncrease dlVersionNumberIncrease = DLVersionNumberIncrease.AUTOMATIC;
					DLAppLocalServiceUtil.updateFileEntry(themeDisplay.getUserId(), fileEntryId, dlFile.getFileName(), dlFile.getMimeType(), dlFile.getTitle(), dlFile.getDescription(),
							"", dlVersionNumberIncrease, file, serviceContext);
					}
				}
				}catch (Exception e) {
					_log.error(e.getMessage());
				}
				
				if(Validator.isNull(reportUploadFileLog.getSignature())) {
					mnCompCertificateLocalService.addMnCompCertificate(certificate.getDate_1(), purchase1, 
							purchase2_1, purchase2_2, purchase2_3, purchase2_4, purchase2_5, purchase3, purchase4,certificate.getReportUploadLogId(),purchaseOfSecuritiesRemarks112,
							purchaseOfSecuritiesRemarks12a2,purchaseOfSecuritiesRemarks12b2,purchaseOfSecuritiesRemarks12c2,purchaseOfSecuritiesRemarks12d2,purchaseOfSecuritiesRemarks12e2,
							purchaseOfSecuritiesRemarks132,purchaseOfSecuritiesRemarks142,securitiesHeld212,securitiesHeld22a2,securitiesHeld22b2,securitiesHeld22c2,securitiesHeld232,
							securitiesHeld242,securitiesHeld252,saleOfSecurities31a2,saleOfSecurities31b2,saleOfecurities322,reports_andDisclosures41a2,reportsAndDisclosures41b2,
							reportsAndDisclosures41c2,reportsAndDisclosures41d2,reportsAndDisclosures41e2,reportsAndDisclosures42a2,reportsAndDisclosures42b2,securities1, 
							securities2_1, securities2_2, securities2_3, securities3, securities4, securities5,sale1_1, sale1_2, sale2,reports1_1, 
							reports1_2, reports1_3, reports1_4, reports1_5, reports2_1, reports2_2,date_2, companies, employeeName, roles, date_3, place,annexure_a_i, 0, 
							0, 0, 0, 0, 0, 0, 0, new Date(), themeDisplay.getUserId(),fileEntryId,isAmRejected);	
				}
				
			} catch (Exception e) {
				 _log.error(e);
			}
		}else {
			_log.info("reportUploadLogId --------------------------------------------------------- "+reportUploadLogId);

	      //document remarkes
			String annexure_a_i_rem=ParamUtil.getString(resourceRequest, "Document_Ai");
			String annexure_a_ii_rem=ParamUtil.getString(resourceRequest, "AnnexureAii");
			String annexure_b_rem=ParamUtil.getString(resourceRequest, "AnnexureB");
			String annexure_c_rem=ParamUtil.getString(resourceRequest, "AnnexureC");
			String annexure_d_rem=ParamUtil.getString(resourceRequest, "AnnexureD");
			String annexure_e_rem=ParamUtil.getString(resourceRequest, "AnnexureE");
			String annexure_f_rem=ParamUtil.getString(resourceRequest, "AnnexureF");
			String annexure_g_rem=ParamUtil.getString(resourceRequest, "AnnexureG");
			String annexure_h_rem=ParamUtil.getString(resourceRequest, "AnnexureH");
			String npsRemark=ParamUtil.getString(resourceRequest, "npsRemark");

			try {
				mnCompCertificateScrutinyLocalService.saveMnCompCertificateScrutiny(themeDisplay.getUser().getScreenName(),0, themeDisplay.getUserId(), purchase_of_securities_rem, detailed_investment_rem, 
						investments_approved_rem, decision_of_investment_rem, invest_non_dematerialized_rem, all_investments_from_funds_rem, delivery_of_security_purch_rem, 
						investment_done_in_ipo_rem, scheme_investments_rem, stop_loss_trigger_rem, decision_appr_by_committee_rem, decision_prop_documented_rem, inter_scheme_transfer_rem, 
						investment_held_in_equity_rem, invest_in_equity_shares_rem, disinvestments_approved_rem, decision_of_disinvestment_rem, delivery_of_security_sale_rem, 
						all_investment_charges_rem, pfm_adhered_rem, records_of_audit_of_nav_rem, scheme_wise_nav_uploaded_rem, scheme_wise_nav_submitted_rem, monthly_reports_submitted_rem,
						scrip_wise_details_rem, annexure_a_i_rem, annexure_a_ii_rem, annexure_b_rem, annexure_c_rem, annexure_d_rem, annexure_e_rem, annexure_f_rem, annexure_g_rem, annexure_h_rem, 
						new Date(), themeDisplay.getUserId(), reportUploadLogId,npsRemark);
			} catch (Exception e) {
				isError = true;
				_log.error(e.getMessage(), e);
			}	
		}
				PrintWriter writer = resourceResponse.getWriter();
				if(!isError) {
					writer.print("Success");
				}
				
			} catch (Exception e) {
				_log.error(e);
			}
			
		return false;
	}
	
	
	public void updateMnCompCertificate(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, MnCompCertificate certificate) {
		
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
		
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info(date_1);
		_log.info(purchase1 + " " + purchase2_1 + " " + purchase2_2 + " " + purchase2_3);
		_log.info(purchase2_4 + " " + purchase2_5 + " " + purchase3 + " " + purchase4);
		_log.info(purchaseOfSecuritiesRemarks112+" "+purchaseOfSecuritiesRemarks12a2+" "+securitiesHeld212);
		
		MnCompCertificate firstDetails = mnCompCertificateLocalService.addSection1(date_1, purchase1, 
				purchase2_1, purchase2_2, purchase2_3, purchase2_4, purchase2_5, purchase3, purchase4,reportUploadLogId,purchaseOfSecuritiesRemarks112,
				purchaseOfSecuritiesRemarks12a2,purchaseOfSecuritiesRemarks12b2,purchaseOfSecuritiesRemarks12c2,purchaseOfSecuritiesRemarks12d2,purchaseOfSecuritiesRemarks12e2,
				purchaseOfSecuritiesRemarks132,purchaseOfSecuritiesRemarks142,securitiesHeld212,securitiesHeld22a2,securitiesHeld22b2,securitiesHeld22c2,securitiesHeld232,
				securitiesHeld242,securitiesHeld252,saleOfSecurities31a2,saleOfSecurities31b2,saleOfecurities322,reports_andDisclosures41a2,reportsAndDisclosures41b2,
				reportsAndDisclosures41c2,reportsAndDisclosures41d2,reportsAndDisclosures41e2,reportsAndDisclosures42a2,reportsAndDisclosures42b2);
		
		
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
		
		_log.info(securities1 + " " + securities2_1 + " " + securities2_2 + " " + securities2_3);
		_log.info(securities3 + " " + securities4 + " " + securities5);
		
		MnCompCertificate section2 = mnCompCertificateLocalService.addSection2(sectionTwoDetails, securities1, 
				securities2_1, securities2_2, securities2_3, securities3, securities4, securities5);
		
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
		
		_log.info(reports1_1 + " " + reports1_2 + " " + reports1_3 + " " + reports1_4);
		_log.info(reports1_5 + " " + reports2_1 + " " + reports2_2);
		
		MnCompCertificate section4 = mnCompCertificateLocalService.addSection4(sectionFourDetails, reports1_1, 
				reports1_2, reports1_3, reports1_4, reports1_5, reports2_1, reports2_2);
		
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
	/*public MnCompCertificate section6(ResourceRequest resourceRequest, MnCompCertificate sectionSixDetails, ThemeDisplay themeDisplay) throws ParseException {
		
		
		long annexure_a_i = CompCertificateUtil.addDocuments(resourceRequest, "annexureA_I");
		long annexure_a_ii = CompCertificateUtil.addDocuments(resourceRequest, "annexureA_II");
		long annexure_b = CompCertificateUtil.addDocuments(resourceRequest, "annexureB");
		long annexure_c = CompCertificateUtil.addDocuments(resourceRequest, "annexureC");
		long annexure_d = CompCertificateUtil.addDocuments(resourceRequest, "annexureD");
		long annexure_e = CompCertificateUtil.addDocuments(resourceRequest, "annexureE");
		long annexure_f = CompCertificateUtil.addDocuments(resourceRequest, "annexureF");
		long annexure_g = CompCertificateUtil.addDocuments(resourceRequest, "annexureG");
		long annexure_h = CompCertificateUtil.addDocuments(resourceRequest, "annexureH");
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		_log.info("reportUploadID:::" + reportUploadLogId);
		_log.info(annexure_a_i + " " + annexure_a_ii + " " + annexure_b + " " + annexure_c);
		_log.info(annexure_d + " " + annexure_e + " " + annexure_f + " " + annexure_g);
		_log.info(annexure_h);
		
		
		MnCompCertificate section6 = mnCompCertificateLocalService.addSection6(sectionSixDetails, annexure_a_i, annexure_a_ii, 
				annexure_b, annexure_c, annexure_d, annexure_e, annexure_f, annexure_g, annexure_h, 
				new Date(), themeDisplay.getUserId());
		

		
		return section6;
			
	}*/
	
	@Reference
	MnCompCertificateLocalService mnCompCertificateLocalService;
	
	@Reference
	MnCompCertificateScrutinyLocalService mnCompCertificateScrutinyLocalService;
	
	@Reference(unbind = "-")
	protected void setMnCompCertificateScrutinyLocalService(MnCompCertificateScrutinyLocalService mnCompCertificateScrutinyLocalService) {
		this.mnCompCertificateScrutinyLocalService  = mnCompCertificateScrutinyLocalService;
	}
}
