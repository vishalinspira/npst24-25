package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.AnnualCompCertificateScrutiny;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.MnCompCertificateScrutiny;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ScrutinyInputQuarterlyInterval;
import com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil;
import com.daily.average.service.service.AnnualCompCertificateScrutinyLocalServiceUtil;
import com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil;
import com.daily.average.service.service.MnCompCertificateLocalServiceUtil;
import com.daily.average.service.service.MnCompCertificateScrutinyLocalServiceUtil;
import com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.average.service.service.ScrutinyInputQuarterlyIntervalLocalServiceUtil;
import com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny;
import com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.ReportsNameConstants;
import nps.common.service.util.FormsPdfCreationUtil;
import nps.common.service.util.PfmHyccUtil;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FORMS_PDF_CREATION_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class FormsPDFCreationMVCResourceCommand implements MVCResourceCommand {
	
	static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		log.info("PDF creation resource command call");
//		String checkBoxValues=ParamUtil.getString(resourceRequest, "pdfCheckBox");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
//		long reportUploadlogId=ParamUtil.getLong(resourceRequest, "reportUploadlogId");
//		log.info("reportUploadlogId  : "+reportUploadlogId);
	
//		//InputStream inputStream = new FileInputStream(file);
//		//long fileId=NpstCommonUtil.fileUpload(file, 0, ContentTypes.APPLICATION_TEXT, file.getName(), serviceContext);
//		//PortletResponseUtil.sendFile(resourceRequest, resourceResponse,file.getName(), inputStream, ContentTypes.APPLICATION_TEXT);
//		String pdfCheckBox=ParamUtil.getString(resourceRequest, "pdfCheckBox");
//		log.info("pdf check box value: "+pdfCheckBox);
//		
//		
//
		
		
		try {
			long reportUploadlogId=ParamUtil.getLong(resourceRequest, "reportUploadlogId");
			String checkBoxValues=ParamUtil.getString(resourceRequest, "pdfCheckBox");
			log.info("reportUploadlogId  : "+reportUploadlogId);
			//File file = TestCreatePdfUtil.createPDF();
			//InputStream inputStream = new FileInputStream(file);
			List<String> pdfValues=getCheckBoxList(checkBoxValues);
			ReportUploadLog reportUploadLog =ReportUploadLogLocalServiceUtil.getReportUploadLog(reportUploadlogId);
			String reportName=ReportMasterLocalServiceUtil.getReportMaster(reportUploadLog.getReportMasterId()).getReportName();
			log.info("report name:"+reportName);
			if(reportName.equalsIgnoreCase(ReportsNameConstants.QUARTERLY_COMPLIANCE_CERTIFICATE)) {
				InputQuarterlyInterval inputQuarterlyInterval= InputQuarterlyIntervalLocalServiceUtil.fetchInputQuarterlyInterval(reportUploadlogId);
				ScrutinyInputQuarterlyInterval scrutinyInputQuarterlyInterval= null;
				try {
				List<ScrutinyInputQuarterlyInterval> scrutinyInputQuarterlyIntervals=ScrutinyInputQuarterlyIntervalLocalServiceUtil.findByReportUploadLogId(reportUploadlogId);
				 scrutinyInputQuarterlyInterval= scrutinyInputQuarterlyIntervals.get(scrutinyInputQuarterlyIntervals.size()-1);
			}catch (Exception e) {
				log.error(e);
			}
				File file=FormsPdfCreationUtil.QuarterlyComplianeCertificateNPSPDF(inputQuarterlyInterval, scrutinyInputQuarterlyInterval, pdfValues);
				InputStream inputStream = new FileInputStream(file);
				//DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), 0, file.getName()+date.getTime(), ContentTypes.APPLICATION_TEXT, file.getName(), file.getName(), null, file, serviceContext);
				log.info("file: uploaded successfully  ----------------        ");
				PortletResponseUtil.sendFile(resourceRequest, resourceResponse,file.getName(), inputStream, ContentTypes.APPLICATION_TEXT);
			}else if(reportName.equalsIgnoreCase(ReportsNameConstants.MONTHLY_COMPLIANCE_CERTIFICATE)) {
				MnCompCertificate mnCompCertificate=MnCompCertificateLocalServiceUtil.fetchMnCompCertificate(reportUploadlogId);
				MnCompCertificateScrutiny mnCompCertificateScrutiny=null;
				try {
				List<MnCompCertificateScrutiny> mnCompCertificateScrutinyies=MnCompCertificateScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadlogId);
				 mnCompCertificateScrutiny= mnCompCertificateScrutinyies.get(mnCompCertificateScrutinyies.size()-1);
			}catch (Exception e) {
				log.error(e);
			}
				File file=FormsPdfCreationUtil.MonthlyComplianeCertificateNPS_PDF(mnCompCertificate, mnCompCertificateScrutiny, pdfValues);
				InputStream inputStream = new FileInputStream(file);
				//DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), 0, file.getName()+date.getTime(), ContentTypes.APPLICATION_TEXT, file.getName(), file.getName(), null, file, serviceContext);
				log.info("file: uploaded successfully  ----------------        ");
				PortletResponseUtil.sendFile(resourceRequest, resourceResponse,file.getName(), inputStream, ContentTypes.APPLICATION_TEXT);
			}else if(reportName.equalsIgnoreCase(ReportsNameConstants.HY_COMPLIANCE_CERTIFICATE)) {
				PFM_hy_comp_cert hy_comp_cert= PFM_hy_comp_certLocalServiceUtil.fetchPFM_hy_comp_cert(reportUploadlogId);
//				PFM_hy_comp_cert_Scrutiny cert_Scrutiny=null;
//				try {
//				List<PFM_hy_comp_cert_Scrutiny> cert_Scrutinies= PFM_hy_comp_cert_ScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadlogId);
//				cert_Scrutinies.get(cert_Scrutinies.size()-1);
//				}catch (Exception e) {
//					log.error(e);
//				}
				User user=null;
				try {
				user=	UserLocalServiceUtil.getUser(hy_comp_cert.getCreatedby());
				}catch (Exception e) {
					log.error(e);
				}
				JSONObject jsonObject=PfmHyccUtil.getHyComp_JSON_data(reportUploadlogId);
				File file=FormsPdfCreationUtil.hyCompCertificatePFMPDF(jsonObject, pdfValues,user);
				InputStream inputStream = new FileInputStream(file);
				log.info("file: uploaded successfully  ----------------        ");
				PortletResponseUtil.sendFile(resourceRequest, resourceResponse,file.getName(), inputStream, ContentTypes.APPLICATION_TEXT);
			}else if(reportName.equalsIgnoreCase(ReportsNameConstants.ANNUALLY_COMPLIANCE_CERTIFICATE)) {
				AnnualCompCertificate annualCompCertificate= AnnualCompCertificateLocalServiceUtil.fetchAnnualCompCertificate(reportUploadlogId);
				AnnualCompCertificateScrutiny certificateScrutiny=null;
				try {
				List<AnnualCompCertificateScrutiny> annualCompCertScrutinyies=  AnnualCompCertificateScrutinyLocalServiceUtil.findAnnualCompCertificateScrutinyByReportUplaodLogId(reportUploadlogId);
				 certificateScrutiny=annualCompCertScrutinyies.get(annualCompCertScrutinyies.size()-1);
			}catch (Exception e) {
				log.error(e);
			}
				File file=FormsPdfCreationUtil.annualComplianeCertificateNPSPDF(annualCompCertificate, certificateScrutiny, pdfValues);
				InputStream inputStream = new FileInputStream(file);
				log.info("file: uploaded successfully  ----------------        ");
				PortletResponseUtil.sendFile(resourceRequest, resourceResponse,file.getName(), inputStream, ContentTypes.APPLICATION_TEXT);
			}
			
			//PortletResponseUtil.sendFile(resourceRequest, resourceResponse, file.getName(), new FileInputStream(file));
		
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		//DLFileEntry dlFileEntry= DLFileEntryLocalServiceUtil.getDLFileEntry(453767);
	
		
//		try {
//		File file = TestCreatePdfUtil.createPDF();
//		OutputStream portletOutputStream=null;
//		portletOutputStream = (OutputStream) resourceResponse.getPortletOutputStream();
//		FileOutputStream fout = new FileOutputStream(file);
//		//ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
//		//InputStream inputStream = new FileInputStream(file);
//		resourceResponse.setContentType("application/pdf");
//
//		resourceResponse.setProperty(HttpHeaders.CONTENT_DISPOSITION,"attachement;filename="+file.getName());
//
//		resourceResponse.addProperty(HttpHeaders.CACHE_CONTROL,"max-age=3600, must-revalidate");
//		
//		portletOutputStream.close();
//		fout.write(FileUtil.getBytes(file));
//		fout.flush();
//		fout.close();
//		log.info("success");
//		}catch (Exception e) {
//			log.error(e.getMessage());
//		}
		return false;
	}
	
	/**
	 * 
	 * @param checkBoxValues
	 */
	private List<String> getCheckBoxList(String checkBoxValues) {
		List<String> values=new ArrayList<String>();
		
		if(Validator.isNotNull(checkBoxValues)) {
			for(String value:checkBoxValues.split(StringPool.COMMA)) {
				try {
					values.add(value);
				}catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		return values;
	}
	private static final  Log log=LogFactoryUtil.getLog(FormsPDFCreationMVCResourceCommand.class.getName());

}
