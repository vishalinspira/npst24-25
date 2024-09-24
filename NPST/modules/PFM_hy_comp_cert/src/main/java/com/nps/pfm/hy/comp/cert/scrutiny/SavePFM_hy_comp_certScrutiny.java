package com.nps.pfm.hy.comp.cert.scrutiny;

import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny;
import com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalService;
import com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
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
import com.nps.pfm.hy.comp.cert.constants.PFM_hy_comp_certPortletKeys;
import com.nps.pfm.hy.comp.cert.util.CompCerUtil;
import com.nps.pfm.hy.comp.cert.util.HyCompPdfUtil;
import com.nps.pfm.hy.comp.cert.util.PfmHyccNonNps;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.PfmHyccUtil;

@Component(property = { 
		"javax.portlet.name=" + PFM_hy_comp_certPortletKeys.PFM_HY_COMP_CERT,
		"mvc.command.name=" + PFM_hy_comp_certPortletKeys.PFM_HY_COMP_CERT_Scrutiny 
		}, 
service = MVCResourceCommand.class)


public class SavePFM_hy_comp_certScrutiny  extends BaseMVCResourceCommand {
	private static Log _log = LogFactoryUtil.getLog(SavePFM_hy_comp_certScrutiny.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("Serve Resource method 2");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		boolean isError = false;
		PfmHyccNonNps pfmHyccNonNps = new PfmHyccNonNps();
		Boolean isNonNPSUser = pfmHyccNonNps.isNonNpsUser(themeDisplay.getUserId());
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		String year = ParamUtil.getString(resourceRequest, "year");
		Date date1 = ParamUtil.getDate(resourceRequest,"date1",dateFormat);
		Date date2 = ParamUtil.getDate(resourceRequest,"date2",dateFormat);
		Date date3 = ParamUtil.getDate(resourceRequest,"date3",dateFormat);
		String hycc_place = ParamUtil.getString(resourceRequest,"place");
		String companies = ParamUtil.getString(resourceRequest, "companies");
		JSONObject jsonObject = PfmHyccUtil.getloopingContexOfDetailedAuditReport();
		PFM_hy_comp_cert  pfm_hy_comp_cert = PFM_hy_comp_certLocalServiceUtil.fetchPFM_hy_comp_cert(reportUploadLogId);
		_log.info("isNonNPSUser========"+isNonNPSUser);
		if(isNonNPSUser) {
			JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
			
			for(String key : jsonObject.keySet()) {
				
				JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
				long size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, key+"_", size);

				if(Validator.isNotNull(jsonObject_three)) {
					_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
					jsonObject_two.put(key, jsonObject_three);
				}	
			}
			try {	
				JSONObject jsonObject_pdf=	PfmHyccUtil.getHyComp_JSON_PDFdata(jsonObject_two);
				File file=HyCompPdfUtil.hyCompCertificatePFMPDF(dateFormat.format(pfm_hy_comp_cert.getReportDate()), year, companies, "", "", dateFormat.format(date3), hycc_place, jsonObject_pdf);
				ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
				long fileEntryId=pfm_hy_comp_cert.getFileEntryId();
				int isAmRejected=0;
				ReportUploadFileLog reportUploadFileLog =null;
				try {
					 reportUploadFileLog = ReportUploadFileLogLocalServiceUtil.findByFileEntryId(fileEntryId);
					FileEntry dlFile=DLAppLocalServiceUtil.getFileEntry(fileEntryId);
//				if(certificate.getIsAmRejected()==1 || Validator.isNotNull(reportUploadFileLog.getSignature())) {
					if(Validator.isNotNull(reportUploadFileLog.getSignature())) {
					fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
					
					ReportUploadFileLogLocalServiceUtil.addReportUploadFileLog(pfm_hy_comp_cert.getReportUploadLogId(), fileEntryId, themeDisplay.getUserId());
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
					PFM_hy_comp_certLocalServiceUtil.addHy_comp_cert(reportUploadLogId, year, jsonObject_two.toJSONString(), date1, date2, date3, hycc_place, pfm_hy_comp_cert.getHyccfile() );
					pfm_hy_comp_cert=PFM_hy_comp_certLocalServiceUtil.getPFM_hy_comp_cert(pfm_hy_comp_cert.getReportUploadLogId());
					pfm_hy_comp_cert.setFileEntryId(fileEntryId);
					PFM_hy_comp_certLocalServiceUtil.updatePFM_hy_comp_cert(pfm_hy_comp_cert);
				}
			} catch (Exception e) {
				 _log.error(e);
			}
			//updatePFM_hy_comp_cert(resourceRequest);
		}else {
			String npsRemark=ParamUtil.getString(resourceRequest, "npsRemark");
			JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
			
			for(String key : jsonObject.keySet()) {
				
				JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
				long size = jsonObject.getLong(key);	
				jsonObject_three = getJsonDataScr(resourceRequest, key+"_npsrem_", size);

				if(Validator.isNotNull(jsonObject_three)) {
					_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
					jsonObject_two.put(key, jsonObject_three);
				}
				
			}
			
			try {
				PFM_hy_comp_cert_Scrutiny comp_cert_Scrutiny= pfm_hy_comp_cert_ScrutinyLocalService.addPFM_hy_comp_cert_Cert_Scrutiny
				(reportUploadLogId, themeDisplay.getUser().getScreenName(),
						0, themeDisplay.getUserId(), jsonObject_two.toString());
				comp_cert_Scrutiny.setNps_comments(npsRemark);
				PFM_hy_comp_cert_ScrutinyLocalServiceUtil.updatePFM_hy_comp_cert_Scrutiny(comp_cert_Scrutiny);
			} catch (Exception e) {
				isError = true;
				_log.error("Exception in Persisting the table data one  : "+e.getMessage());
			}
			
			try {
				PrintWriter writer = resourceResponse.getWriter();
				if(!isError) {
					writer.print("Success");
				}else {
					writer.print("error");
				}
				
			} catch (IOException e) {
				_log.error(e);
			}
		
		}
		
	}
	private JSONObject getJsonDataScr(ResourceRequest resourceRequest,String auditorComments, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String remark = ParamUtil.getString(resourceRequest, auditorComments+String.valueOf(i));
			jsonObject.put(auditorComments+i, remark);
		}		
		return jsonObject;
		
	}
	
	private JSONObject getJsonData(ResourceRequest resourceRequest,String auditorComments, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String auditor = ParamUtil.getString(resourceRequest, auditorComments+String.valueOf(i));
			String remark = ParamUtil.getString(resourceRequest, auditorComments+"rem_"+String.valueOf(i));		
			jsonObject.put(auditorComments+i, auditor);
			jsonObject.put(auditorComments+"rem_"+i, remark);
		}	
		return jsonObject;
		
	}
	
	public void updatePFM_hy_comp_cert(ResourceRequest resourceRequest) {
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		JSONObject jsonObject = PfmHyccUtil.getloopingContexOfDetailedAuditReport();
		
		JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
		
		for(String key : jsonObject.keySet()) {
			
			JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
			long size = jsonObject.getLong(key);	
			jsonObject_three = getJsonData(resourceRequest, key+"_", size);
			if(Validator.isNotNull(jsonObject_three)) {
				//data save
				_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
				jsonObject_two.put(key, jsonObject_three);
			}
		}
		PFM_hy_comp_cert  pfm_hy_comp_cert = PFM_hy_comp_certLocalServiceUtil.fetchPFM_hy_comp_cert(reportUploadLogId);
		pfm_hy_comp_cert.setHycc_details(jsonObject_two.toJSONString());
		PFM_hy_comp_certLocalServiceUtil.updatePFM_hy_comp_cert(pfm_hy_comp_cert);
	}
	
	
	private long uploadFile(File file,ThemeDisplay themeDisplay,ResourceRequest resourceRequest){
		Date date=new Date();
	
		String fileName = date.getTime()+"_"+"Half Yearly Comp Certificate"+".pdf";
		String title = fileName; 
		String description = "Half Yearly Comp Certificate PDF";
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
		return repositoryId;
	}
	
	@Reference
	PFM_hy_comp_cert_ScrutinyLocalService pfm_hy_comp_cert_ScrutinyLocalService;
	
	

}
