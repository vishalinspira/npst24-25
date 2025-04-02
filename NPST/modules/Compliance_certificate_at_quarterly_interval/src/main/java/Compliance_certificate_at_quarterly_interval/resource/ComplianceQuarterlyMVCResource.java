package Compliance_certificate_at_quarterly_interval.resource;

import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringPool;
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

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import Compliance_certificate_at_quarterly_interval.constants.Compliance_certificate_at_quarterly_intervalPortletKeys;
import Compliance_certificate_at_quarterly_interval.portlet.QuarterlyCompCertificateCreatePdfUtil;
import Compliance_certificate_at_quarterly_interval.util.CompCerUtil;

@Component(property = { 
		"javax.portlet.name=" + Compliance_certificate_at_quarterly_intervalPortletKeys.COMPLIANCE_CERTIFICATE_AT_QUARTERLY_INTERVAL,
		"mvc.command.name=" + Compliance_certificate_at_quarterly_intervalPortletKeys.input_quarterly_intervalPortletKeys, 
		}, 
service = MVCResourceCommand.class)

public class ComplianceQuarterlyMVCResource implements MVCResourceCommand{
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("compliance_certificate_pdf_file -------------  Start ");
		
		 boolean isError = false;
		
		 ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		 try {
		 PrintWriter writer = resourceResponse.getWriter();
		    if(themeDisplay.isSignedIn()) {
		    	
		    DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
			Date formDate =ParamUtil.getDate(resourceRequest,"formDate",dateFormat); 
			
			_log.info("formDate Quartely::" + formDate);
			Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
			InputQuarterlyInterval inputQuarterlyInterval = null;
			try {
				inputQuarterlyInterval=InputQuarterlyIntervalLocalServiceUtil.getByReportUploadlogIdAndIssubmitted(reportUploadLogId, true).get(0);
			}catch (Exception e) {
				_log.error(e);
			}
			
			String oneia = ParamUtil.getString(resourceRequest, "oneia");
			String oneib = ParamUtil.getString(resourceRequest, "oneib");
			String oneiia = ParamUtil.getString(resourceRequest, "oneiia");
			String oneiib = ParamUtil.getString(resourceRequest, "oneiib");
			String oneiic = ParamUtil.getString(resourceRequest, "oneiic");
			String oneiid = ParamUtil.getString(resourceRequest, "oneiid");
			String oneiiia = ParamUtil.getString(resourceRequest, "oneiiia");
			String oneiiib = ParamUtil.getString(resourceRequest, "oneiiib");
			String oneiva = ParamUtil.getString(resourceRequest, "oneiva");
			String onev = ParamUtil.getString(resourceRequest, "onev");
			String twoia = ParamUtil.getString(resourceRequest, "twoia");
			String twoib = ParamUtil.getString(resourceRequest, "twoib");
			String twoic = ParamUtil.getString(resourceRequest, "twoic");
			String twoiione = ParamUtil.getString(resourceRequest, "twoiione");
			String twoiitwo = ParamUtil.getString(resourceRequest, "twoiitwo");
			String twoiithree = ParamUtil.getString(resourceRequest, "twoiithree");
			String twoiifour = ParamUtil.getString(resourceRequest, "twoiifour");
			String twoiifive = ParamUtil.getString(resourceRequest, "twoiifive");
			String twoiisix = ParamUtil.getString(resourceRequest, "twoiisix");
			String twoiiseven = ParamUtil.getString(resourceRequest, "twoiiseven");
			String threea = ParamUtil.getString(resourceRequest, "threea");
			String threeb = ParamUtil.getString(resourceRequest, "threeb");
			String threec = ParamUtil.getString(resourceRequest, "threec");
			String threeci = ParamUtil.getString(resourceRequest, "threeci");
			String threecii = ParamUtil.getString(resourceRequest, "threecii");
			String threeciii = ParamUtil.getString(resourceRequest, "threeciii");
			String threeciv = ParamUtil.getString(resourceRequest, "threeciv");
			String foura = ParamUtil.getString(resourceRequest, "foura");
			String fourb = ParamUtil.getString(resourceRequest, "fourb");
			String fourc = ParamUtil.getString(resourceRequest, "fourc");
			String fivei = ParamUtil.getString(resourceRequest, "fivei");
			String fiveii = ParamUtil.getString(resourceRequest, "fiveii");
			String fiveiii = ParamUtil.getString(resourceRequest, "fiveiii");
			String fiveiv = ParamUtil.getString(resourceRequest, "fiveiv");
			String fiveva = ParamUtil.getString(resourceRequest, "fiveva");
			String fivevb = ParamUtil.getString(resourceRequest, "fivevb");
			String fivevc = ParamUtil.getString(resourceRequest, "fivevc");
			String fivevia = ParamUtil.getString(resourceRequest, "fivevia");
			String fivevib = ParamUtil.getString(resourceRequest, "fivevib");
			String sixi = ParamUtil.getString(resourceRequest, "sixi");
			String sixiia = ParamUtil.getString(resourceRequest, "sixiia");
			String sixiib = ParamUtil.getString(resourceRequest, "sixiib");
			String sixiic = ParamUtil.getString(resourceRequest, "sixiic");
			String sevenia = ParamUtil.getString(resourceRequest, "sevenia");
			String sevenib = ParamUtil.getString(resourceRequest, "sevenib");
			String sevenic = ParamUtil.getString(resourceRequest, "sevenic");
			String sevenid = ParamUtil.getString(resourceRequest, "sevenid");
			String sevenie = ParamUtil.getString(resourceRequest, "sevenie");
			String eightia = ParamUtil.getString(resourceRequest, "eightia");
			String eightib = ParamUtil.getString(resourceRequest, "eightib");
			String eightii = ParamUtil.getString(resourceRequest, "eightii");
			String eightiii = ParamUtil.getString(resourceRequest, "eightiii");
			String eightiv = ParamUtil.getString(resourceRequest, "eightiv");
			String eightv = ParamUtil.getString(resourceRequest, "eightv");
			String eightvia = ParamUtil.getString(resourceRequest, "eightvia");
			String eightvib = ParamUtil.getString(resourceRequest, "eightvib");
			String eightviia = ParamUtil.getString(resourceRequest, "eightviia");
			
			String eightviib = ParamUtil.getString(resourceRequest, "eightviib");
			String eightviii = ParamUtil.getString(resourceRequest, "eightviii");
			String eightix = ParamUtil.getString(resourceRequest, "eightix");
			String eightx = ParamUtil.getString(resourceRequest, "eightx");
			
			String ninea = ParamUtil.getString(resourceRequest, "ninea");
			String nineb = ParamUtil.getString(resourceRequest, "nineb");
			String ten = ParamUtil.getString(resourceRequest, "ten");
			String elevena = ParamUtil.getString(resourceRequest, "elevena");
			String elevenb = ParamUtil.getString(resourceRequest, "elevenb");
			String twelve = ParamUtil.getString(resourceRequest, "twelve");
			
			
			String oneia_rem_intermediary = ParamUtil.getString(resourceRequest, "oneia_rem_intermediary");
			String oneib_rem_intermediary = ParamUtil.getString(resourceRequest, "oneib_rem_intermediary");
			String oneiia_rem_intermediary = ParamUtil.getString(resourceRequest, "oneiia_rem_intermediary");
			String oneiib_rem_intermediary = ParamUtil.getString(resourceRequest, "oneiib_rem_intermediary");
			String oneiic_rem_intermediary = ParamUtil.getString(resourceRequest, "oneiic_rem_intermediary");
			String oneiid_rem_intermediary = ParamUtil.getString(resourceRequest, "oneiid_rem_intermediary");
			String oneiiia_rem_intermediary = ParamUtil.getString(resourceRequest, "oneiiia_rem_intermediary");
			String oneiiib_rem_intermediary = ParamUtil.getString(resourceRequest, "oneiiib_rem_intermediary");
			String oneiva_rem_intermediary = ParamUtil.getString(resourceRequest, "oneiva_rem_intermediary");
			String onev_rem_intermediary = ParamUtil.getString(resourceRequest, "onev_rem_intermediary");
			String twoia_rem_intermediary = ParamUtil.getString(resourceRequest, "twoia_rem_intermediary");
			String twoib_rem_intermediary = ParamUtil.getString(resourceRequest, "twoib_rem_intermediary");
			String twoic_rem_intermediary = ParamUtil.getString(resourceRequest, "twoic_rem_intermediary");
			String twoiione_rem_intermediary = ParamUtil.getString(resourceRequest, "twoiione_rem_intermediary");
			String twoiitwo_rem_intermediary = ParamUtil.getString(resourceRequest, "twoiitwo_rem_intermediary");
			String twoiithree_rem_intermediary = ParamUtil.getString(resourceRequest, "twoiithree_rem_intermediary");
			String twoiifour_rem_intermediary = ParamUtil.getString(resourceRequest, "twoiifour_rem_intermediary");
			String twoiifive_rem_intermediary = ParamUtil.getString(resourceRequest, "twoiifive_rem_intermediary");
			String twoiisix_rem_intermediary = ParamUtil.getString(resourceRequest, "twoiisix_rem_intermediary");
			String twoiiseven_rem_intermediary = ParamUtil.getString(resourceRequest, "twoiiseven_rem_intermediary");
			String threea_rem_intermediary = ParamUtil.getString(resourceRequest, "threea_rem_intermediary");
			String threeb_rem_intermediary = ParamUtil.getString(resourceRequest, "threeb_rem_intermediary");
			String threec_rem_intermediary = ParamUtil.getString(resourceRequest, "threec_rem_intermediary");
			String threeci_rem_intermediary = ParamUtil.getString(resourceRequest, "threeci_rem_intermediary");
			String threecii_rem_intermediary = ParamUtil.getString(resourceRequest, "threecii_rem_intermediary");
			String threeciii_rem_intermediary = ParamUtil.getString(resourceRequest, "threeciii_rem_intermediary");
			String threeciv_rem_intermediary = ParamUtil.getString(resourceRequest, "threeciv_rem_intermediary");
			String foura_rem_intermediary = ParamUtil.getString(resourceRequest, "foura_rem_intermediary");
			String fourb_rem_intermediary = ParamUtil.getString(resourceRequest, "fourb_rem_intermediary");
			String fourc_rem_intermediary = ParamUtil.getString(resourceRequest, "fourc_rem_intermediary");
			String fivei_rem_intermediary = ParamUtil.getString(resourceRequest, "fivei_rem_intermediary");
			String fiveii_rem_intermediary = ParamUtil.getString(resourceRequest, "fiveii_rem_intermediary");
			String fiveiii_rem_intermediary = ParamUtil.getString(resourceRequest, "fiveiii_rem_intermediary");
			String fiveiv_rem_intermediary = ParamUtil.getString(resourceRequest, "fiveiv_rem_intermediary");
			String fiveva_rem_intermediary = ParamUtil.getString(resourceRequest, "fiveva_rem_intermediary");
			String fivevb_rem_intermediary = ParamUtil.getString(resourceRequest, "fivevb_rem_intermediary");
			String fivevc_rem_intermediary = ParamUtil.getString(resourceRequest, "fivevc_rem_intermediary");
			String fivevia_rem_intermediary = ParamUtil.getString(resourceRequest, "fivevia_rem_intermediary");
			String fivevib_rem_intermediary = ParamUtil.getString(resourceRequest, "fivevib_rem_intermediary");
			String sixi_rem_intermediary = ParamUtil.getString(resourceRequest, "sixi_rem_intermediary");
			String sixiia_rem_intermediary = ParamUtil.getString(resourceRequest, "sixiia_rem_intermediary");
			String sixiib_rem_intermediary = ParamUtil.getString(resourceRequest, "sixiib_rem_intermediary");
			String sixiic_rem_intermediary = ParamUtil.getString(resourceRequest, "sixiic_rem_intermediary");
			String sevenia_rem_intermediary = ParamUtil.getString(resourceRequest, "sevenia_rem_intermediary");
			String sevenib_rem_intermediary = ParamUtil.getString(resourceRequest, "sevenib_rem_intermediary");
			String sevenic_rem_intermediary = ParamUtil.getString(resourceRequest, "sevenic_rem_intermediary");
			String sevenid_rem_intermediary = ParamUtil.getString(resourceRequest, "sevenid_rem_intermediary");
			String sevenie_rem_intermediary = ParamUtil.getString(resourceRequest, "sevenie_rem_intermediary");
			String eightia_rem_intermediary = ParamUtil.getString(resourceRequest, "eightia_rem_intermediary");
			String eightib_rem_intermediary = ParamUtil.getString(resourceRequest, "eightib_rem_intermediary");
			String eightii_rem_intermediary = ParamUtil.getString(resourceRequest, "eightii_rem_intermediary");
			String eightiii_rem_intermediary = ParamUtil.getString(resourceRequest, "eightiii_rem_intermediary");
			String eightiv_rem_intermediary = ParamUtil.getString(resourceRequest, "eightiv_rem_intermediary");
			String eightv_rem_intermediary = ParamUtil.getString(resourceRequest, "eightv_rem_intermediary");
			String eightvia_rem_intermediary = ParamUtil.getString(resourceRequest, "eightvia_rem_intermediary");
			String eightvib_rem_intermediary = ParamUtil.getString(resourceRequest, "eightvib_rem_intermediary");
			String eightviia_rem_intermediary = ParamUtil.getString(resourceRequest, "eightviia_rem_intermediary");
			
			String eightviib_rem_intermediary = ParamUtil.getString(resourceRequest, "eightviib_rem_intermediary");
			String eightviii_rem_intermediary = ParamUtil.getString(resourceRequest, "eightviii_rem_intermediary");
			String eightix_rem_intermediary = ParamUtil.getString(resourceRequest, "eightix_rem_intermediary");
			String eightx_rem_intermediary = ParamUtil.getString(resourceRequest, "eightx_rem_intermediary");
			
			
			String ninea_rem_intermediary = ParamUtil.getString(resourceRequest, "ninea_rem_intermediary");
			String nineb_rem_intermediary = ParamUtil.getString(resourceRequest, "nineb_rem_intermediary");
			String ten_rem_intermediary = ParamUtil.getString(resourceRequest, "ten_rem_intermediary");
			String elevena_rem_intermediary = ParamUtil.getString(resourceRequest, "elevena_rem_intermediary");
			String elevenb_rem_intermediary = ParamUtil.getString(resourceRequest, "elevenb_rem_intermediary");
			String twelve_rem_intermediary = ParamUtil.getString(resourceRequest, "twelve_rem_intermediary");
			
			String annex_i_rem_intermediary = ParamUtil.getString(resourceRequest, "annex_i_rem_intermediary");
			String board_a_rem_intermediary =  ParamUtil.getString(resourceRequest, "board_a_rem_intermediary");
			String board_b_rem_intermediary =  ParamUtil.getString(resourceRequest, "board_b_rem_intermediary");
			String investment_a_rem_intermediary = ParamUtil.getString( resourceRequest, "investment_a_rem_intermediary");
			String investment_b_rem_intermediary = ParamUtil.getString(resourceRequest, "investment_b_rem_intermediary");
			String risk_a_rem_intermediary = ParamUtil.getString(resourceRequest, "risk_a_rem_intermediary");
			String risk_b_rem_intermediary = ParamUtil.getString(resourceRequest, "risk_b_rem_intermediary");
			String annex_ii_rem_intermediary = ParamUtil.getString(resourceRequest, "annex_ii_rem_intermediary");
			String annex_iii_rem_intermediary = ParamUtil.getString(resourceRequest, "annex_iii_rem_intermediary");
			String annex_iv_rem_intermediary = ParamUtil.getString(resourceRequest, "annex_iv_rem_intermediary");
			String annex_comp_certificate_rem_intermediary = ParamUtil.getString(resourceRequest, "annex_comp_certificate_rem_intermediary");
			
			
			
			String notedate = ParamUtil.getString(resourceRequest, "notedate");
			String companies = ParamUtil.getString(resourceRequest, "companies");
			String date_3 = ParamUtil.getString(resourceRequest, "date_3");
			String place = ParamUtil.getString(resourceRequest, "place");
			String ceoname = ParamUtil.getString(resourceRequest, "ceoname");
			String ceonameii = ParamUtil.getString(resourceRequest, "ceonameii");
			
			
			long annex_i = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "annex_i", StringPool.BLANK);
			long board_a =  CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "board_a", StringPool.BLANK);
//			long board_b =  CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "board_b", StringPool.BLANK);
//			long investment_a = CompCerUtil.uploadFILETOFOLDER(themeDisplay, resourceRequest, "investment_a", StringPool.BLANK);
//			long investment_b = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "investment_b", StringPool.BLANK);
//			long risk_a = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "risk_a", StringPool.BLANK);
//			long risk_b = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "risk_b", StringPool.BLANK);
//			long annex_ii = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "annex_ii", StringPool.BLANK);
//			long annex_iii = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "annex_iii", StringPool.BLANK);
//			long annex_iv = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "annex_iv", StringPool.BLANK);
//			long annex_comp_certificate = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "annex_comp_certificate", StringPool.BLANK);
			//_log.info("annex_comp_certificate ::::::: "+annex_comp_certificate);
			Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
			Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
			Long createdBy = themeDisplay.getUserId();
			String statusByUserName = themeDisplay.getUser().getFullName();
			ServiceContext serviceContext = null;
			try {
				serviceContext = ServiceContextFactory.getInstance(resourceRequest);
			} catch (PortalException e) {
				_log.error("Exception on getting servicecontext : "+e.getMessage(),e);
			}
//			int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
//			_log.info("rowcount " + rowCount);
//			String[] rowIndex = ParamUtil.getParameterValues(resourceRequest, "rowIndex[]");
//			String[] boardDiscription = ParamUtil.getParameterValues(resourceRequest, "boardDiscription[]");
//			String[] auditorObservation = ParamUtil.getParameterValues(resourceRequest, "auditorObservation[]");
//			String[] riskRating = ParamUtil.getParameterValues(resourceRequest, "riskRating[]");
//			String[] managementResponse = ParamUtil.getParameterValues(resourceRequest, "managementResponse[]");
//			String[] npstComment = ParamUtil.getParameterValues(resourceRequest, "npstComment[]");
//			_log.info("boardDiscription size:  "+boardDiscription.length);
//			PFM_executive_summaryLocalServiceUtil.deleteExecutiveSummaryByReportUplodLogId(reportUploadLogId);
//			for(int i=0; i<rowCount; i++) {
//				try {
//				_log.info("rowIndex :  "+rowIndex[i]+"   i  : "+i);
//				if(Integer.parseInt(rowIndex[i])>0) {
//					_log.info("boardDiscription " + boardDiscription[i] + " auditorObservation " + auditorObservation[i] +
//							" riskRating " + riskRating[i]);
//					PFM_executive_summaryLocalServiceUtil.addExecutiveSummary(reportUploadLogId, boardDiscription[i], auditorObservation[i], managementResponse[i],riskRating[i], npstComment[i]);
//				}else {
//					continue;
//				}
//				}catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			//PFM_executive_summaryLocalServiceUtil.deleteExecutiveSummaryByReportUplodLogId(reportUploadLogId);
//			for (int i = 0; i < 100; i++) {
//				long rowIndex=ParamUtil.getLong(resourceRequest, "rowIndex"+i);
			
			
//				if(rowIndex>0) {
//				String boardDiscription = ParamUtil.getString(resourceRequest, "boardDiscription" + i);
//				String auditorObservation = ParamUtil.getString(resourceRequest, "auditorObservation" + i);
//				String riskRating = ParamUtil.getString(resourceRequest, "riskRating" + i);
//				String managementResponse = ParamUtil.getString(resourceRequest, "managementResponse" + i);
//				String npstComment = ParamUtil.getString(resourceRequest, "npstComment" + i);
//				
//				_log.info("boardDiscription:  " + boardDiscription + "  auditorObservation:  " + auditorObservation
//						+ "  riskRating: " + riskRating + "  managementResponse  : " + managementResponse
//						+ "  npstComment :  " + npstComment +"  rowIndex:  "+rowIndex );
//				}else {
//					break;
//				}
//
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
			
			File file=QuarterlyCompCertificateCreatePdfUtil.QuarterlyComplianeCertificatePFMPDF(reportUploadLogId,dateFormat.format(reportDate), formDate1,
					
					oneia, oneib, oneiia, oneiib, oneiic, oneiid, oneiiia,oneiiib, oneiva, onev, twoia, twoib, twoic, twoiione, twoiitwo, twoiithree, twoiifour, twoiifive,
					twoiisix, twoiiseven, threea, threeb, threec, threeci, threecii, threeciii, threeciv, foura, fourb, fourc, fivei, fiveii, fiveiii, fiveiv, fiveva, fivevb,
					fivevc, fivevia, fivevib, sixi, sixiia, sixiib, sixiic,sevenia, sevenib, sevenic, sevenid, sevenie, eightia, eightib, eightii, eightiii, eightiv, eightv,
					eightvia, eightvib, eightviia, eightviib, eightviii,eightix,eightx,ninea, nineb, ten, elevena, elevenb, twelve, 
					"","", "", "", "", "", "","", "", "", "","", "", "","", "", "", "", "", "","", "", "", "", "", "","", "", "", "", "", "",
					"", "", "", "", "", "","", "","", "", "", "","", "", "", "", "", "","", "", "", "", "", "","", "", "", "", "", "","", "", "","","",
					oneia_rem_intermediary, oneib_rem_intermediary, oneiia_rem_intermediary, oneiib_rem_intermediary, oneiic_rem_intermediary,
					oneiid_rem_intermediary, oneiiia_rem_intermediary,oneiiib_rem_intermediary, oneiva_rem_intermediary, onev_rem_intermediary, twoia_rem_intermediary, twoib_rem_intermediary, twoic_rem_intermediary, twoiione_rem_intermediary, twoiitwo_rem_intermediary, twoiithree_rem_intermediary, twoiifour_rem_intermediary,
					twoiifive_rem_intermediary, twoiisix_rem_intermediary, twoiiseven_rem_intermediary, threea_rem_intermediary, threeb_rem_intermediary, threec_rem_intermediary, threeci_rem_intermediary, threecii_rem_intermediary, threeciii_rem_intermediary,threeciv_rem_intermediary, foura_rem_intermediary, fourb_rem_intermediary, 
					fourc_rem_intermediary, fivei_rem_intermediary, fiveii_rem_intermediary, fiveiii_rem_intermediary, fiveiv_rem_intermediary, fiveva_rem_intermediary, fivevb_rem_intermediary,fivevc_rem_intermediary, fivevia_rem_intermediary, fivevib_rem_intermediary, sixi_rem_intermediary, sixiia_rem_intermediary,sixiib_rem_intermediary,
					sixiic_rem_intermediary, sevenia_rem_intermediary, sevenib_rem_intermediary, sevenic_rem_intermediary,sevenid_rem_intermediary, sevenie_rem_intermediary, eightia_rem_intermediary,eightib_rem_intermediary, eightii_rem_intermediary, eightiii_rem_intermediary, eightiv_rem_intermediary, eightv_rem_intermediary, eightvia_rem_intermediary,
					eightvib_rem_intermediary, eightviia_rem_intermediary, eightviib_rem_intermediary, eightviii_rem_intermediary,eightix_rem_intermediary,eightx_rem_intermediary, ninea_rem_intermediary, nineb_rem_intermediary, ten_rem_intermediary, elevena_rem_intermediary, elevenb_rem_intermediary, twelve_rem_intermediary,  notedate, companies, date_3, place, ceoname, ceonameii);
			long pdf_file_fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
			
			try {
				if(pdf_file_fileEntryId>0) {			
				_log.info(inputQuarterlyInterval);
				boolean reupload = false;
				if(Validator.isNotNull(inputQuarterlyInterval)) {
					reupload = true;
				}
				
				
				InputQuarterlyInterval iqr=InputQuarterlyIntervalLocalServiceUtil.addInputQuarterlyInterval(reportUploadLogId, formDate, oneia, oneib, oneiia, oneiib, oneiic, oneiid, oneiiia, oneiiib,
						oneiva, onev, twoia, twoib, twoic, twoiione, twoiitwo, twoiithree, twoiifour, twoiifive, twoiisix, twoiiseven, threea, threeb, threec, threeci, 
						threecii, threeciii, threeciv, foura, fourb, fourc, fivei, fiveii, fiveiii, fiveiv, fiveva, fivevb, fivevc, fivevia, fivevib, sixi, sixiia, sixiib, 
						sixiic, sevenia, sevenib, sevenic, sevenid, sevenie, eightia, eightib, eightii, eightiii, eightiv, eightv, eightvia, eightvib, eightviia, eightviib, 
						eightviii,eightix,eightx, ninea, nineb, ten, elevena, elevenb, twelve, oneia_rem_intermediary, oneib_rem_intermediary, oneiia_rem_intermediary, oneiib_rem_intermediary, oneiic_rem_intermediary, oneiid_rem_intermediary,
						oneiiia_rem_intermediary, oneiiib_rem_intermediary, oneiva_rem_intermediary, onev_rem_intermediary, twoia_rem_intermediary, twoib_rem_intermediary, twoic_rem_intermediary, twoiione_rem_intermediary, twoiitwo_rem_intermediary, twoiithree_rem_intermediary,
						twoiifour_rem_intermediary, twoiifive_rem_intermediary, twoiisix_rem_intermediary, twoiiseven_rem_intermediary, threea_rem_intermediary, threeb_rem_intermediary, threec_rem_intermediary, threeci_rem_intermediary, threecii_rem_intermediary, 
						threeciii_rem_intermediary, threeciv_rem_intermediary, foura_rem_intermediary, fourb_rem_intermediary, fourc_rem_intermediary, fivei_rem_intermediary, fiveii_rem_intermediary, fiveiii_rem_intermediary, fiveiv_rem_intermediary, fiveva_rem_intermediary,
						fivevb_rem_intermediary, fivevc_rem_intermediary, fivevia_rem_intermediary, fivevib_rem_intermediary, sixi_rem_intermediary, sixiia_rem_intermediary, sixiib_rem_intermediary, sixiic_rem_intermediary, sevenia_rem_intermediary, sevenib_rem_intermediary, 
						sevenic_rem_intermediary, sevenid_rem_intermediary, sevenie_rem_intermediary, eightia_rem_intermediary, eightib_rem_intermediary, eightii_rem_intermediary, eightiii_rem_intermediary, eightiv_rem_intermediary, eightv_rem_intermediary, eightvia_rem_intermediary,
						eightvib_rem_intermediary, eightviia_rem_intermediary, eightviib_rem_intermediary, eightviii_rem_intermediary,eightix_rem_intermediary,eightx_rem_intermediary, ninea_rem_intermediary, nineb_rem_intermediary, ten_rem_intermediary, elevena_rem_intermediary, elevenb_rem_intermediary, twelve_rem_intermediary, 
						board_a_rem_intermediary, board_b_rem_intermediary, investment_a_rem_intermediary, investment_b_rem_intermediary, risk_a_rem_intermediary, risk_b_rem_intermediary, annex_i_rem_intermediary, annex_ii_rem_intermediary, annex_iii_rem_intermediary, annex_iv_rem_intermediary, 
						annex_comp_certificate_rem_intermediary, notedate, companies, date_3, place, ceoname, ceonameii, board_a, 0, 0, 0, 0, 0, 0,
						0, 0, 0, new Date(), createdBy, annex_i,pdf_file_fileEntryId,0);
				iqr.setIsSubmitted(true);
				InputQuarterlyIntervalLocalServiceUtil.updateInputQuarterlyInterval(iqr);
				//ReportUploadFileLogLocalServiceUtil.addReportUploadFileLog(reportUploadLogId, pdf_file_fileEntryId, createdBy);
				/*InputQuarterlyIntervalLocalServiceUtil.addInputQuarterlyInterval(reportUploadLogId, formDate, oneia, oneib, oneiia, oneiib, oneiic,
						oneiid, oneiiia, oneiiib, oneiva, onev, twoia, twoib, twoic, twoiione, twoiitwo, twoiithree, twoiifour, twoiifive, twoiisix,
						twoiiseven, threea, threeb, threec, threeci, threecii, threeciii, threeciv, foura, fourb, fourc, fivei, fiveii, fiveiii, fiveiv,
						fiveva, fivevb, fivevc, fivevia, fivevib, sixi, sixiia, sixiib, sixiic, sevenia, sevenib, sevenic, sevenid, sevenie, eightia, eightib,
						eightii, eightiii, eightiv, eightv, eightvia, eightvib, eightviia, eightviib, eightviii, ninea, nineb, ten, elevena, elevenb, twelve, 
						notedate, companies, date_3, place, ceoname, ceonameii, board_a, board_b, investment_a, investment_b, risk_a, risk_b, annex_ii, annex_iii,
						annex_iv, annex_comp_certificate, new Date(), createdBy, annex_i);*/
				
				if(reupload) {
					InputQuarterlyIntervalLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, reportUploadLogId,
						WorkflowConstants.STATUS_PENDING, createdBy, statusByUserName, new Date(), serviceContext, "", false);
				}else {
					InputQuarterlyIntervalLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, reportUploadLogId,
							WorkflowConstants.STATUS_PENDING, createdBy, statusByUserName, new Date(), serviceContext, "", true);
				}
			
				/*
				 * JSONArray fileList = JSONFactoryUtil.createJSONArray(); JSONObject jsonFile =
				 * JSONFactoryUtil.createJSONObject(); jsonFile.put("Name", file.getName());
				 * jsonFile.put("fileEntryId", pdf_file_fileEntryId); fileList.put(jsonFile);
				 * 
				 * for(JSONObject jsonObject:reportUploadFileLog.getFileList() ) {
				 * 
				 * }
				 */
				}
			} catch (Exception e) {
				_log.error(e);
				_log.error("Exception in Persisting the table data one  : "+e.getMessage());
				isError = true;
			}
			
			
			if(!isError) {
				writer.print("success");
			}else {
				writer.print("An error occured while submitting the form. Please try again");
			}
		    }else {
		    	writer.print("You have successfully logout.Please login.");
		    }
		    writer.close();
		 }catch (Exception e) {
			_log.error("error while submit form : "+e);
		}
			
		return false;
  }
	
	private long uploadFile(File file,ThemeDisplay themeDisplay,ResourceRequest resourceRequest){
		Date date=new Date();
	
		String fileName = date.getTime()+"_"+"Compliance_certificate_at_quarterly_interval"+".pdf";
		String title = fileName; 
		String description = "Compliance certificate at quarterly interval PDF";
		

		String mimeType =  MimeTypesUtil.getContentType(file);

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = CompCerUtil.getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();
		}catch (Exception e) {
			_log.error("error while uploading file:  "+e.getMessage());
		}
		return 0;
			

		
	}
	private static Log _log = LogFactoryUtil.getLog(ComplianceQuarterlyMVCResource.class.getName());
}
