package AnnualComplaincereport_FormScrutiny;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil;
import com.daily.average.service.service.AnnualCompCertificateScrutinyLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import AnnualComplaincereport_Form.constants.AnnualComplaincereport_FormPortletKeys;
import AnnualComplaincereport_Form.util.AnnualComplianceReportUtil;
import AnnualComplaincereport_Form.util.AnnualComplianceReport_FormCreatePdf;
import AnnualComplaincereport_Form.util.UploadDocumentUtil;

@Component(property = { 
		"javax.portlet.name=" + AnnualComplaincereport_FormPortletKeys.ANNUALCOMPLAINCEREPORT_FORM,
		"mvc.command.name=" + AnnualComplaincereport_FormPortletKeys.saveannualcomplainscrutinycereport, 
		
		}, 
service = MVCResourceCommand.class)

public class AnnualComplainceScrutinyreport_Form implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("welcome to saveAnnualComplainceScrutinyReport class::::::::::::::::::::");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		AnnualComplianceReportUtil  annualComplianceReportUtil = new AnnualComplianceReportUtil();
		boolean isNonNPSUser = annualComplianceReportUtil.isNonNpsUser(themeDisplay.getUserId());
		
		//eligibility
		String eligibilityia=ParamUtil.getString(resourceRequest, "eligibilityIa");
		String eligibilityib=ParamUtil.getString(resourceRequest, "eligibilityIb");
		String eligibilityic=ParamUtil.getString(resourceRequest, "eligibilityIc");
		String eligibilityid=ParamUtil.getString(resourceRequest, "eligibilityId");
		String eligibilityie=ParamUtil.getString(resourceRequest, "eligibilityIe");
		String eligibilityif=ParamUtil.getString(resourceRequest, "eligibilityIf");
		String eligibilityig=ParamUtil.getString(resourceRequest, "eligibilityIg");
		String eligibilityih=ParamUtil.getString(resourceRequest, "eligibilityIh");
		String eligibilityii=ParamUtil.getString(resourceRequest, "eligibilityIi");
		String eligibilityij=ParamUtil.getString(resourceRequest, "eligibilityIj");
		String eligibilityik=ParamUtil.getString(resourceRequest, "eligibilityIk");
		String eligibilityil=ParamUtil.getString(resourceRequest, "eligibilityIl");
		String eligibilityim=ParamUtil.getString(resourceRequest, "eligibilityIm");
		String eligibilityin=ParamUtil.getString(resourceRequest, "eligibilityIn");
		String eligibilityio=ParamUtil.getString(resourceRequest, "eligibilityIo");
		String eligibilityip=ParamUtil.getString(resourceRequest, "eligibilityIp");
		String eligibilityiq=ParamUtil.getString(resourceRequest, "eligibilityIq");
		String eligibilityir=ParamUtil.getString(resourceRequest, "eligibilityIr");
		String eligibilityis=ParamUtil.getString(resourceRequest, "eligibilityIs");
		
		String eligibilityia_rem=ParamUtil.getString(resourceRequest, "eligibilityIa_rem");
		String eligibilityib_rem=ParamUtil.getString(resourceRequest, "eligibilityIb_rem");
		String eligibilityic_rem=ParamUtil.getString(resourceRequest, "eligibilityIc_rem");
		String eligibilityid_rem=ParamUtil.getString(resourceRequest, "eligibilityId_rem");
		String eligibilityie_rem=ParamUtil.getString(resourceRequest, "eligibilityIe_rem");
		String eligibilityif_rem=ParamUtil.getString(resourceRequest, "eligibilityIf_rem");
		String eligibilityig_rem=ParamUtil.getString(resourceRequest, "eligibilityIg_rem");
		String eligibilityih_rem=ParamUtil.getString(resourceRequest, "eligibilityIh_rem");
		String eligibilityii_rem=ParamUtil.getString(resourceRequest, "eligibilityIi_rem");
		String eligibilityij_rem=ParamUtil.getString(resourceRequest, "eligibilityIj_rem");
		String eligibilityik_rem=ParamUtil.getString(resourceRequest, "eligibilityIk_rem");
		String eligibilityil_rem=ParamUtil.getString(resourceRequest, "eligibilityIl_rem");
		String eligibilityim_rem=ParamUtil.getString(resourceRequest, "eligibilityIm_rem");
		String eligibilityin_rem=ParamUtil.getString(resourceRequest, "eligibilityIn_rem");
		String eligibilityio_rem=ParamUtil.getString(resourceRequest, "eligibilityIo_rem");
		String eligibilityip_rem=ParamUtil.getString(resourceRequest, "eligibilityIp_rem");
		String eligibilityiq_rem=ParamUtil.getString(resourceRequest, "eligibilityIq_rem");
		String eligibilityir_rem=ParamUtil.getString(resourceRequest, "eligibilityIr_rem");
		String eligibilityis_rem=ParamUtil.getString(resourceRequest, "eligibilityIs_rem");
		String eligibilityia_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIa_npsrem");
		String eligibilityib_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIb_npsrem");
		String eligibilityic_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIc_npsrem");
		String eligibilityid_npsrem=ParamUtil.getString(resourceRequest, "eligibilityId_npsrem");
		String eligibilityie_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIe_npsrem");
		String eligibilityif_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIf_npsrem");
		String eligibilityig_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIg_npsrem");
		String eligibilityih_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIh_npsrem");
		String eligibilityii_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIi_npsrem");
		String eligibilityij_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIj_npsrem");
		String eligibilityik_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIk_npsrem");
		String eligibilityil_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIl_npsrem");
		String eligibilityim_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIm_npsrem");
		String eligibilityin_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIn_npsrem");
		String eligibilityio_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIo_npsrem");
		String eligibilityip_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIp_npsrem");
		String eligibilityiq_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIq_npsrem");
		String eligibilityir_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIr_npsrem");
		String eligibilityis_npsrem=ParamUtil.getString(resourceRequest, "eligibilityIs_npsrem");
		
		//book
		String booka=ParamUtil.getString(resourceRequest, "bookIIa");
		String bookb=ParamUtil.getString(resourceRequest, "bookIIb");
		String bookc=ParamUtil.getString(resourceRequest, "bookIIc");		
		String booka_rem=ParamUtil.getString(resourceRequest, "bookIIa_rem");
		String bookb_rem=ParamUtil.getString(resourceRequest, "bookIIb_rem");
		String bookc_rem=ParamUtil.getString(resourceRequest, "bookIIc_rem");
		String booka_npsrem=ParamUtil.getString(resourceRequest, "bookIIa_npsrem");
		String bookb_npsrem=ParamUtil.getString(resourceRequest, "bookIIb_npsrem");
		String bookc_npsrem=ParamUtil.getString(resourceRequest, "bookIIc_npsrem");
		//audit
		String audita = ParamUtil.getString(resourceRequest, "audita");
		String auditb = ParamUtil.getString(resourceRequest, "auditb");
		String auditc = ParamUtil.getString(resourceRequest, "auditc");
		String audita_rem = ParamUtil.getString(resourceRequest, "audita_rem");
		String auditb_rem = ParamUtil.getString(resourceRequest, "auditb_rem");
		String auditc_rem = ParamUtil.getString(resourceRequest, "auditc_rem");
		String audita_npsrem = ParamUtil.getString(resourceRequest, "audita_npsrem");
		String auditb_npsrem = ParamUtil.getString(resourceRequest, "auditb_npsrem");
		String auditc_npsrem = ParamUtil.getString(resourceRequest, "auditc_npsrem");
		
		//stewardship
		
		String stewardshipa = ParamUtil.getString(resourceRequest, "stewardshipa");
		String stewardshipb = ParamUtil.getString(resourceRequest, "stewardshipb");
		String stewardshipc = ParamUtil.getString(resourceRequest, "stewardshipc");
		String stewardshipa_rem = ParamUtil.getString(resourceRequest, "stewardshipa_rem");
		String stewardshipb_rem = ParamUtil.getString(resourceRequest, "stewardshipb_rem");
		String stewardshipc_rem = ParamUtil.getString(resourceRequest, "stewardshipc_rem");
		String stewardshipa_npsrem = ParamUtil.getString(resourceRequest, "stewardshipa_npsrem");
		String stewardshipb_npsrem = ParamUtil.getString(resourceRequest, "stewardshipb_npsrem");
		String stewardshipc_npsrem = ParamUtil.getString(resourceRequest, "stewardshipc_npsrem");
		
		//othres 
		String othersa = ParamUtil.getString(resourceRequest, "othersa");
		String othersb = ParamUtil.getString(resourceRequest, "othersb");
		String othersc = ParamUtil.getString(resourceRequest, "othersc");
		String othersd = ParamUtil.getString(resourceRequest, "othersd");
		String otherse = ParamUtil.getString(resourceRequest, "otherse");
		String othersf = ParamUtil.getString(resourceRequest, "othersf");
		String othersa_rem = ParamUtil.getString(resourceRequest, "othersa_rem");
		String othersb_rem = ParamUtil.getString(resourceRequest, "othersb_rem");
		String othersc_rem = ParamUtil.getString(resourceRequest, "othersc_rem");
		String othersd_rem = ParamUtil.getString(resourceRequest, "othersd_rem");
		String otherse_rem = ParamUtil.getString(resourceRequest, "otherse_rem");
		String othersf_rem = ParamUtil.getString(resourceRequest, "othersf_rem");
		String othersa_npsrem = ParamUtil.getString(resourceRequest, "othersa_npsrem");
		String othersb_npsrem = ParamUtil.getString(resourceRequest, "othersb_npsrem");
		String othersc_npsrem = ParamUtil.getString(resourceRequest, "othersc_npsrem");
		String othersd_npsrem = ParamUtil.getString(resourceRequest, "othersd_npsrem");
		String otherse_npsrem = ParamUtil.getString(resourceRequest, "otherse_npsrem");
		String othersf_npsrem = ParamUtil.getString(resourceRequest, "othersf_npsrem");
		
		//document annexureI
		String annexurei_rem = ParamUtil.getString(resourceRequest, "annexureI_rem");
		String annexureii_rem = ParamUtil.getString(resourceRequest, "annexureII_rem");
		String annexureiii_rem = ParamUtil.getString(resourceRequest, "annexureIII_rem");
		String annexureiv_rem = ParamUtil.getString(resourceRequest, "annexureIV_rem");
		String annexurev_rem = ParamUtil.getString(resourceRequest, "annexureV_rem");
		String npsRemark = ParamUtil.getString(resourceRequest, "npsRemark");
		
		String companies = ParamUtil.getString(resourceRequest, "companies");
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		AnnualCompCertificate annualCompCertificate = AnnualCompCertificateLocalServiceUtil.fetchAnnualCompCertificate(reportUploadLogId);
		    
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date reportDate =annualCompCertificate.getReportDate();
		String meetingdate = annualCompCertificate.getMeetingdate();
		String emplolyee_name = annualCompCertificate.getEmplolyee_name();
		//String company_name = ParamUtil.getString(resourceRequest, "");
		String roles = annualCompCertificate.getRoles();
		Date date_2 = annualCompCertificate.getDate_2();
		String place = annualCompCertificate.getPlace();
		
		//document annexureI
		long annexurei = annualCompCertificate.getAnnexurei();
		long annexureii =annualCompCertificate.getAnnexureii();
		long annexureiii = annualCompCertificate.getAnnexureiii();
		long annexureiv = annualCompCertificate.getAnnexureiv();
		long annexurev = annualCompCertificate.getAnnexurev();
		
		_log.info("eligibilityia::::::::::::::::::::::::::::::::::"+eligibilityia);
		_log.info("annexureV_rem::::::::::::::::::::::::::::::::::"+annexurev_rem);
		 _log.info("reportUploadLogId::::::::::::::::::::::::::"+reportUploadLogId);
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
			boolean isError = false;
		
		if(isNonNPSUser) {
			try {
				
				File file=AnnualComplianceReport_FormCreatePdf.annualComplianeCertificatePFMPDF(reportUploadLogId, dateFormat.format(reportDate),companies, formDate1, eligibilityia, eligibilityib, eligibilityic, eligibilityid, eligibilityie,
						eligibilityif,eligibilityig, eligibilityih, eligibilityii, eligibilityij, eligibilityik, eligibilityil, eligibilityim, eligibilityin, eligibilityio, eligibilityip, eligibilityiq,
						eligibilityir, eligibilityis, booka, bookb, bookc, audita, auditb, auditc, stewardshipa, stewardshipb, stewardshipc, othersa, othersb, othersc, othersd, otherse, othersf,
						eligibilityia_rem, eligibilityib_rem, eligibilityic_rem, eligibilityid_rem, eligibilityie_rem, eligibilityif_rem, eligibilityig_rem, eligibilityih_rem, eligibilityii_rem, eligibilityij_rem,
						eligibilityik_rem, eligibilityil_rem, eligibilityim_rem, eligibilityin_rem, eligibilityio_rem, eligibilityip_rem, eligibilityiq_rem, eligibilityir_rem, eligibilityis_rem, booka_rem,
						bookb_rem, bookc_rem, audita_rem, auditb_rem, auditc_rem, stewardshipa_rem, stewardshipb_rem, stewardshipc_rem, othersa_rem, othersb_rem, othersc_rem, othersd_rem, otherse_rem, 
						othersf_rem, meetingdate, emplolyee_name, roles,dateFormat.format(date_2), place);
				ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
				long fileEntryId=annualCompCertificate.getFileEntryId();
				int isAmRejected=0;
				ReportUploadFileLog reportUploadFileLog =null;
				try {
					 reportUploadFileLog = ReportUploadFileLogLocalServiceUtil.findByFileEntryId(fileEntryId);
					FileEntry dlFile=DLAppLocalServiceUtil.getFileEntry(fileEntryId);
//				if(certificate.getIsAmRejected()==1 || Validator.isNotNull(reportUploadFileLog.getSignature())) {
					if(Validator.isNotNull(reportUploadFileLog.getSignature())) {
					fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
					
					ReportUploadFileLogLocalServiceUtil.addReportUploadFileLog(annualCompCertificate.getReportUploadLogId(), fileEntryId, themeDisplay.getUserId());
					reportUploadFileLog = ReportUploadFileLogLocalServiceUtil.findByFileEntryId(fileEntryId);
					isAmRejected=2;
				}else {
					if(Validator.isNull(reportUploadFileLog.getSignature())) {
					DLVersionNumberIncrease dlVersionNumberIncrease = DLVersionNumberIncrease.AUTOMATIC;
					DLAppLocalServiceUtil.updateFileEntry(themeDisplay.getUserId(), fileEntryId, dlFile.getFileName(), dlFile.getMimeType(), dlFile.getTitle(), dlFile.getDescription(),
							"", dlVersionNumberIncrease, file, serviceContext);
					_log.info("File updated.");
					}
				}
				}catch (Exception e) {
					_log.error(e.getMessage());
				}
				
				if(Validator.isNull(reportUploadFileLog.getSignature())) {
					AnnualCompCertificate Annualcomp = AnnualCompCertificateLocalServiceUtil.saveAnnualCompCertificate(annualCompCertificate.getDate_1(), annualCompCertificate.getAddress(), eligibilityia, eligibilityib, eligibilityic, eligibilityid, eligibilityie, 
							eligibilityif, eligibilityig, eligibilityih, eligibilityii, eligibilityij, eligibilityik, eligibilityil, eligibilityim, eligibilityin, eligibilityio,
							eligibilityip, eligibilityiq, eligibilityir, eligibilityis, booka, bookb, bookc, audita, auditb, auditc, stewardshipa, stewardshipb, stewardshipc, othersa,
							othersb, othersc, othersd, otherse, othersf, meetingdate,eligibilityia_rem, eligibilityib_rem,eligibilityic_rem,eligibilityid_rem,eligibilityie_rem,eligibilityif_rem,eligibilityig_rem,
							eligibilityih_rem,eligibilityii_rem,eligibilityij_rem,eligibilityik_rem,eligibilityil_rem,eligibilityim_rem,eligibilityin_rem,eligibilityio_rem,eligibilityip_rem,eligibilityiq_rem,
							eligibilityir_rem,eligibilityis_rem,booka_rem,bookb_rem,bookc_rem,audita_rem,auditb_rem,auditc_rem,stewardshipa_rem,stewardshipb_rem,stewardshipc_rem,stewardshipc_rem,othersb_rem,
							othersc_rem,othersd_rem,otherse_rem,othersf_rem, emplolyee_name, roles, date_2, place, annexurei, annexureii, annexureiii, annexureiv, annexurev,
							new Date(),themeDisplay.getUserId(),reportUploadLogId,annualCompCertificate.getReportMasterId(), reportDate);
				}
				
			} catch (Exception e) {
				 _log.error(e);
			}
		}else {
			_log.info("reportUploadLogId --------------------------------------------------------- "+reportUploadLogId);
				try {
					 annualCompCertificateScrutinyLocalService.saveAnnualCompCertificateScrutiny(reportUploadLogId,themeDisplay.getUser().getScreenName(),0, themeDisplay.getUserId(),
							   eligibilityia_npsrem, eligibilityib_npsrem, eligibilityic_npsrem, eligibilityid_npsrem, eligibilityie_npsrem, eligibilityif_npsrem, eligibilityig_npsrem, 
							   eligibilityih_npsrem, eligibilityii_npsrem, eligibilityij_npsrem, eligibilityik_npsrem, eligibilityil_npsrem, eligibilityim_npsrem, eligibilityin_npsrem,
							   eligibilityio_npsrem, eligibilityip_npsrem, eligibilityiq_npsrem, eligibilityir_npsrem, eligibilityis_npsrem, booka_npsrem, bookb_npsrem, bookc_npsrem, 
							   audita_npsrem, auditb_npsrem, auditc_npsrem, stewardshipa_npsrem, stewardshipb_npsrem, stewardshipc_npsrem, othersa_npsrem, othersb_npsrem, othersc_npsrem,
							   othersd_npsrem, otherse_npsrem, othersf_npsrem, annexurei_rem, annexureii_rem, annexureiii_rem, annexureiv_rem, annexurev_rem, new Date(), themeDisplay.getUserId(),npsRemark);
				} catch (Exception e) {
					isError = true;
					_log.error(e.getMessage(), e);
				}	
			}
	   
	   try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				writer.print("Success");
			}
			
		} catch (IOException e) {
			_log.error(e);
		}
	   
		return false;
	}
	
	private long uploadFile(File file,ThemeDisplay themeDisplay,ResourceRequest resourceRequest){
		Date date=new Date();
	
		String fileName = date.getTime()+"_"+"Annual_compliance_certificate"+".pdf";
		String title = fileName; 
		String description = "Annual Compliance certificate PDF";
		String mimeType =  MimeTypesUtil.getContentType(file);
		long repositoryId = themeDisplay.getScopeGroupId();
	
		try {
			Folder folder = UploadDocumentUtil.getFolder(themeDisplay);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),resourceRequest);
			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,"", file, serviceContext);
			return fileEntry.getFileEntryId();
		}catch (Exception e) {
			_log.error("error while uploading file:  "+e.getMessage());
		}
		return repositoryId;
	}
	
	@Reference
	AnnualCompCertificateScrutinyLocalService annualCompCertificateScrutinyLocalService;
	
	@Reference(unbind = "-")
	protected void setAnnualCompCertificateScrutinyLocalService(AnnualCompCertificateScrutinyLocalService annualCompCertificateScrutinyLocalService) {
		this.annualCompCertificateScrutinyLocalService  = annualCompCertificateScrutinyLocalService;
	}
	
	Log _log = LogFactoryUtil.getLog(AnnualCompCertificate.class);
}
