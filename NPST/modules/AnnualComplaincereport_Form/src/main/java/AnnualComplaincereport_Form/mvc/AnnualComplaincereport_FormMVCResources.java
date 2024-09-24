package AnnualComplaincereport_Form.mvc;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.service.AnnualCompCertificateLocalService;
import com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil;
import com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
import AnnualComplaincereport_Form.util.AnnualComplaincereport_FormDocumentUtil;
import AnnualComplaincereport_Form.util.AnnualComplianceReport_FormCreatePdf;
import AnnualComplaincereport_Form.util.UploadDocumentUtil;


@Component(property = { 
		"javax.portlet.name=" + AnnualComplaincereport_FormPortletKeys.ANNUALCOMPLAINCEREPORT_FORM,
		"mvc.command.name=" + AnnualComplaincereport_FormPortletKeys.annualcomplaincereport, 
		}, 
service = MVCResourceCommand.class)

public class AnnualComplaincereport_FormMVCResources implements MVCResourceCommand{
	private static Log _log = LogFactoryUtil.getLog(AnnualComplaincereport_FormMVCResources.class);
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("welcome to resources class::::::::::::::::::::");
		try {
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		 PrintWriter writer = resourceResponse.getWriter();
		 if(themeDisplay.isSignedIn()) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
		long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		_log.info("reportUploadLogId:::" + reportUploadLogId);
		Date date_1 = ParamUtil.getDate(resourceRequest, "date_1", dateFormat);
		String address = ParamUtil.getString(resourceRequest, "address");
		
		AnnualCompCertificate annualCompCertificate =null;
		try {
		 annualCompCertificate = AnnualCompCertificateLocalServiceUtil.getByReportUploadlogIdAndIssubmitted(reportUploadLogId, true).get(0);
		}catch (Exception e) {
			_log.error(e);
		}
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
		
		String  eligibilityIaRem= ParamUtil.getString(resourceRequest, "eligibilityIa_rem");
		String eligibilityIbRem = ParamUtil.getString(resourceRequest, "eligibilityIb_rem");
		String eligibilityIcRem = ParamUtil.getString(resourceRequest, "eligibilityIc_rem");
		String eligibilityIdRem = ParamUtil.getString(resourceRequest, "eligibilityId_rem");
		String eligibilityIeRem = ParamUtil.getString(resourceRequest, "eligibilityIe_rem");
		String eligibilityIfRem = ParamUtil.getString(resourceRequest, "eligibilityIf_rem");
		String eligibilityIgRem = ParamUtil.getString(resourceRequest, "eligibilityIg_rem");
		String eligibilityIhRem = ParamUtil.getString(resourceRequest, "eligibilityIh_rem");
		String eligibilityIiRem = ParamUtil.getString(resourceRequest, "eligibilityIi_rem");
		String eligibilityIjRem = ParamUtil.getString(resourceRequest, "eligibilityIj_rem");
		String eligibilityIkRem = ParamUtil.getString(resourceRequest, "eligibilityIk_rem");
		String eligibilityIlRem = ParamUtil.getString(resourceRequest, "eligibilityIl_rem");
		String eligibilityImRem = ParamUtil.getString(resourceRequest, "eligibilityIm_rem");
		String eligibilityInRem = ParamUtil.getString(resourceRequest, "eligibilityIn_rem");
		String eligibilityIoRem = ParamUtil.getString(resourceRequest, "eligibilityIo_rem");
		String eligibilityIpRem = ParamUtil.getString(resourceRequest, "eligibilityIp_rem");
		String eligibilityIqRem = ParamUtil.getString(resourceRequest, "eligibilityIq_rem");
		String eligibilityIrRem = ParamUtil.getString(resourceRequest, "eligibilityIr_rem");
		String eligibilityIsrRem = ParamUtil.getString(resourceRequest, "eligibilityIs_rem");
		
		_log.info(eligibilityIaRem+" -"+eligibilityIbRem+" "+eligibilityIcRem+" "+eligibilityIdRem+" "+eligibilityIeRem+" "+eligibilityIfRem+" "+eligibilityIgRem+" "+eligibilityIhRem
				+" "+eligibilityIiRem+" "+eligibilityIjRem+" "+eligibilityIkRem+" "+eligibilityIlRem+" "+eligibilityImRem+" "+eligibilityInRem+" "+eligibilityIoRem+" "+eligibilityIpRem+" "+
				eligibilityIqRem+" "+eligibilityIrRem+" "+eligibilityIsrRem);
		//book
		String booka=ParamUtil.getString(resourceRequest, "bookIIa");
		String bookb=ParamUtil.getString(resourceRequest, "bookIIb");
		String bookc=ParamUtil.getString(resourceRequest, "bookIIc");
		
		String bookIIaRem = ParamUtil.getString(resourceRequest, "bookIIa_rem");
		String bookIIbRem = ParamUtil.getString(resourceRequest, "bookIIb_rem");
		String bookIIcRem = ParamUtil.getString(resourceRequest, "bookIIc_rem");
		
		_log.info(bookIIaRem+"- "+bookIIbRem+" -"+bookIIcRem);
		//audit
		String audita = ParamUtil.getString(resourceRequest, "audita");
		String auditb = ParamUtil.getString(resourceRequest, "auditb");
		String auditc = ParamUtil.getString(resourceRequest, "auditc");
		
		String auditaRem = ParamUtil.getString(resourceRequest, "audita_rem");
		String auditbRem = ParamUtil.getString(resourceRequest, "auditb_rem");
		String auditcRem = ParamUtil.getString(resourceRequest, "auditc_rem");
		
		_log.info(auditaRem+"- "+auditbRem+" -"+auditcRem);
		//stewardship
		String stewardshipa = ParamUtil.getString(resourceRequest, "stewardshipa");
		String stewardshipb = ParamUtil.getString(resourceRequest, "stewardshipb");
		String stewardshipc = ParamUtil.getString(resourceRequest, "stewardshipc");
		
		String stewardshipaRem = ParamUtil.getString(resourceRequest, "stewardshipa_rem");
		String stewardshipbRem = ParamUtil.getString(resourceRequest, "stewardshipb_rem");
		String stewardshipcRem = ParamUtil.getString(resourceRequest, "stewardshipc_rem");
		
		_log.info(stewardshipaRem+"- "+stewardshipbRem+"- "+stewardshipcRem);
		
		//othres 
		String othersa = ParamUtil.getString(resourceRequest, "othersa");
		String othersb = ParamUtil.getString(resourceRequest, "othersb");
		String othersc = ParamUtil.getString(resourceRequest, "othersc");
		String othersd = ParamUtil.getString(resourceRequest, "othersd");
		String otherse = ParamUtil.getString(resourceRequest, "otherse");
		String othersf = ParamUtil.getString(resourceRequest, "othersf");
		
		String othersaRem = ParamUtil.getString(resourceRequest, "othersa_rem");
		String othersbRem = ParamUtil.getString(resourceRequest, "othersb_rem");
		String otherscRem = ParamUtil.getString(resourceRequest, "othersc_rem");
		String othersdRem = ParamUtil.getString(resourceRequest, "othersd_rem");
		String otherseRem = ParamUtil.getString(resourceRequest, "otherse_rem");
		String othersfRem = ParamUtil.getString(resourceRequest, "othersf_rem");
		
		_log.info(othersaRem+" -"+othersbRem+"- "+otherscRem+" -"+othersdRem+" -"+otherseRem+" -"+othersfRem);
		//details 
		String meetingdate = ParamUtil.getString(resourceRequest, "meetingdate");
		String emplolyee_name = ParamUtil.getString(resourceRequest, "employeeName");
		//String company_name = ParamUtil.getString(resourceRequest, "");
		String roles = ParamUtil.getString(resourceRequest, "roles");
		Date date_2 = ParamUtil.getDate(resourceRequest,"date_2", dateFormat);
		String place = ParamUtil.getString(resourceRequest, "place1");
		
		//document annexureI
		long annexurei = AnnualComplaincereport_FormDocumentUtil.addDocuments(resourceRequest, "annexureI");
		long annexureii = AnnualComplaincereport_FormDocumentUtil.addDocuments(resourceRequest, "annexureII");
		long annexureiii = AnnualComplaincereport_FormDocumentUtil.addDocuments(resourceRequest, "annexureIII");
		long annexureiv = AnnualComplaincereport_FormDocumentUtil.addDocuments(resourceRequest, "annexureIV");
		long annexurev = AnnualComplaincereport_FormDocumentUtil.addDocuments(resourceRequest, "annexureV");
		String companies = ParamUtil.getString(resourceRequest, "companies");
		
	//	String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
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
		
		long pdf_file_fileEntryId = 0l;
		File file=AnnualComplianceReport_FormCreatePdf.annualComplianeCertificatePFMPDF(reportUploadLogId, dateFormat.format(reportDate),companies, formDate1, eligibilityia, eligibilityib, eligibilityic, eligibilityid, eligibilityie,
				eligibilityif,eligibilityig, eligibilityih, eligibilityii, eligibilityij, eligibilityik, eligibilityil, eligibilityim, eligibilityin, eligibilityio, eligibilityip, eligibilityiq,
				eligibilityir, eligibilityis, booka, bookb, bookc, audita, auditb, auditc, stewardshipa, stewardshipb, stewardshipc, othersa, othersb, othersc, othersd, otherse, othersf,
				eligibilityIaRem, eligibilityIbRem, eligibilityIcRem, eligibilityIdRem, eligibilityIeRem, eligibilityIfRem, eligibilityIgRem, eligibilityIhRem, eligibilityIiRem, eligibilityIjRem,
				eligibilityIkRem, eligibilityIlRem, eligibilityImRem, eligibilityInRem, eligibilityIoRem, eligibilityIpRem, eligibilityIqRem, eligibilityIrRem, eligibilityIsrRem, bookIIaRem,
				bookIIbRem, bookIIcRem, auditaRem, auditbRem, auditcRem, stewardshipaRem, stewardshipbRem, stewardshipcRem, othersaRem, othersbRem, otherscRem, othersdRem, otherseRem, 
				othersfRem, meetingdate, emplolyee_name, roles,dateFormat.format(date_2), place);
		try {
			pdf_file_fileEntryId = uploadFile(file, themeDisplay, resourceRequest);
			_log.info("annual_compliance_report_pdf_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
		
			if(pdf_file_fileEntryId>0) {			
			_log.debug(annualCompCertificate);
			boolean reupload = false;
			if(Validator.isNotNull(annualCompCertificate)) {
				reupload = true;
			}
			//AnnualCompCertificate Annualcomp = annualCompCertificateLocalService.fetchAnnualCompCertificate(reportUploadLogId);
			//add the data to table::::::::::::::::
			AnnualCompCertificate annualcomp = annualCompCertificateLocalService.saveAnnualCompCertificate(date_1, address, eligibilityia, eligibilityib, eligibilityic, eligibilityid, eligibilityie, 
					eligibilityif, eligibilityig, eligibilityih, eligibilityii, eligibilityij, eligibilityik, eligibilityil, eligibilityim, eligibilityin, eligibilityio,
					eligibilityip, eligibilityiq, eligibilityir, eligibilityis, booka, bookb, bookc, audita, auditb, auditc, stewardshipa, stewardshipb, stewardshipc, othersa,
					othersb, othersc, othersd, otherse, othersf, meetingdate,eligibilityIaRem,eligibilityIbRem,eligibilityIcRem,eligibilityIdRem,eligibilityIeRem,eligibilityIfRem,eligibilityIgRem,
					eligibilityIhRem,eligibilityIiRem,eligibilityIjRem,eligibilityIkRem,eligibilityIlRem,eligibilityImRem,eligibilityInRem,eligibilityIoRem,eligibilityIpRem,eligibilityIqRem,
					eligibilityIrRem,eligibilityIsrRem,bookIIaRem,bookIIbRem,bookIIcRem,auditaRem,auditbRem,auditcRem,stewardshipaRem,stewardshipbRem,stewardshipcRem,stewardshipcRem,othersbRem,
					otherscRem,othersdRem,otherseRem,othersfRem, emplolyee_name, roles, date_2, place, annexurei, annexureii, annexureiii, annexureiv, annexurev,
					new Date(),themeDisplay.getUserId(),reportUploadLogId,reportMasterId, reportDate);
			annualcomp.setIsSubmitted(true);
			AnnualCompCertificateLocalServiceUtil.updateAnnualCompCertificate(annualcomp);
			_log.info("Annualcomp ::" + annualcomp);
			if(reupload) {
				//add the updateReportUploadLog
				_log.info("reupoad "+reupload);
				annualCompCertificateLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), pdf_file_fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, themeDisplay.getUserId(), themeDisplay.getUser().getFullName(), new Date(), serviceContext, "",false);	
			}else {
				_log.info("reupoad "+reupload);
				annualCompCertificateLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), pdf_file_fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, themeDisplay.getUserId(), themeDisplay.getUser().getFullName(), new Date(), serviceContext, "",true);	
			}}
		} catch (Exception e) {
			_log.error("Exception in getting Annualcomp::" + e.getMessage());
		}
		
		
		try {
			 writer = resourceResponse.getWriter();
			//writer.write(resultJsonObject.toString());
			 writer.print("Success");
		} catch (IOException e) {
			_log.error("Exception in sending data::" + e.getMessage());
		}
		 }else {
			 writer.print("You have successfully logout.Please login.");
		 }
		 writer.close();
		}catch (Exception e) {
			_log.error(e.getMessage());
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
	AnnualCompCertificateLocalService annualCompCertificateLocalService;
}
