package com.nps.pfm.hy.comp.cert.resource;

import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil;
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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.nps.pfm.hy.comp.cert.constants.PFM_hy_comp_certPortletKeys;
import com.nps.pfm.hy.comp.cert.util.CompCerUtil;
import com.nps.pfm.hy.comp.cert.util.HyCompPdfUtil;

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

import nps.common.service.util.PfmHyccUtil;

@Component(property = { 
		"javax.portlet.name=" + PFM_hy_comp_certPortletKeys.PFM_HY_COMP_CERT,
		"mvc.command.name=/half_yearly_comp/save" 
		}, 
service = MVCResourceCommand.class)


public class SavePFM_hy_comp_cert implements MVCResourceCommand{
	public Log _log = LogFactoryUtil.getLog(getClass());

	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
			
			_log.info("pfm_internalaudit_pdf_file -------------  Start ");
			 boolean isError = false;
			 ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);	 
			 Long userId = themeDisplay.getUserId();
				
				DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
				//Date formDate =ParamUtil.getDate(resourceRequest,"formDate",dateFormat); 
				
				Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
				String year = ParamUtil.getString(resourceRequest, "year");
				Date date1 = ParamUtil.getDate(resourceRequest,"date1",dateFormat);
				Date date2 = ParamUtil.getDate(resourceRequest,"date2",dateFormat);
				Date date3 = ParamUtil.getDate(resourceRequest,"date3",dateFormat);
				String hycc_place = ParamUtil.getString(resourceRequest,"place");
				Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
				Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
				String companies = ParamUtil.getString(resourceRequest, "companies");
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
				 long pdf_file_fileEntryId = 0l;
				 long hyccFile=0;
					try {
						JSONObject jsonObject_pdf=	PfmHyccUtil.getHyComp_JSON_PDFdata(jsonObject_two);
						File file=HyCompPdfUtil.hyCompCertificatePFMPDF(dateFormat.format(reportDate), year,companies, "", "", dateFormat.format(date3), hycc_place, jsonObject_pdf);
						pdf_file_fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
						hyccFile = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "hy_comp_certificate_file", "pdf");
						_log.info("pfm_hycc_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
					} catch (Exception e) {
						isError = true;
						_log.error(e.getMessage(), e);
					}
				Long createdBy = themeDisplay.getUserId();
				String statusByUserName = themeDisplay.getUser().getFullName();
				ServiceContext serviceContext = null;
				
				try {
					serviceContext = ServiceContextFactory.getInstance(resourceRequest);
				} catch (PortalException e) {
					_log.error("Exception on getting servicecontext : "+e.getMessage(),e);
				}
				
				try {
					PFM_hy_comp_cert  pfm_hy_comp_cert = PFM_hy_comp_certLocalServiceUtil.fetchPFM_hy_comp_cert(reportUploadLogId);
					
					boolean reupload = false;
					if(Validator.isNotNull(pfm_hy_comp_cert)) {
						reupload = true;
					}
				
			PFM_hy_comp_certLocalServiceUtil.addHy_comp_cert(reportUploadLogId, year, jsonObject_two.toJSONString(), date1, date2, date3, hycc_place, hyccFile );
		
			if(reupload) {
				_log.info("pfm_hy_comp_cert-------------"+pfm_hy_comp_cert);
				PFM_hy_comp_certLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, reportUploadLogId,
						WorkflowConstants.STATUS_PENDING, userId, statusByUserName, new Date(), serviceContext, "", false);
					//WorkflowConstants.STATUS_PENDING, createdBy, statusByUserName, new Date(), serviceContext, "", false);
			}else {
				PFM_hy_comp_certLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, reportUploadLogId,
						WorkflowConstants.STATUS_PENDING, userId, statusByUserName, new Date(), serviceContext, "", true);
			}
		} catch (Exception e) {
			_log.error(e);
			_log.error("Exception in Persisting the table data one  : "+e.getMessage());
			isError = true;
		}
		
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				writer.print("success");
			}else {
				writer.print("error");
			}
			
		} catch (IOException e) {
			_log.error(e);
		}
		
		
		
	return false;
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

}
