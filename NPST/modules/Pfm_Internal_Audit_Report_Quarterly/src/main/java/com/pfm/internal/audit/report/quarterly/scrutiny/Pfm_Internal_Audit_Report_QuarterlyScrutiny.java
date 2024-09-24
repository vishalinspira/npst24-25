package com.pfm.internal.audit.report.quarterly.scrutiny;

import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.PFM_executive_summaryLocalServiceUtil;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.pfm.service.service.Pfm_Qr_IARScrutinyLocalService;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.pfm.internal.audit.report.quarterly.constants.pfm_Internal_Audit_Report_QuarterlyPortletKeys;
import com.pfm.internal.audit.report.quarterly.util.CompCerUtil;
import com.pfm.internal.audit.report.quarterly.util.PMFIARUtil;
import com.pfm.internal.audit.report.quarterly.util.PfmIarNonNps;
import com.pfm.internal.audit.report.quarterly.util.Qr_IAR_PDF_Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { 
		"javax.portlet.name=" + pfm_Internal_Audit_Report_QuarterlyPortletKeys.PFM_INTERNAL_AUDIT_REPORT_QUARTERLY,
		"mvc.command.name=" + pfm_Internal_Audit_Report_QuarterlyPortletKeys.PFM_INTERNAL_AUDIT_REPORT_QUARTERLY_SCRUTINY, 
		}, 
service = MVCResourceCommand.class)
public class Pfm_Internal_Audit_Report_QuarterlyScrutiny extends BaseMVCResourceCommand {
	private static Log _log = LogFactoryUtil.getLog(Pfm_Internal_Audit_Report_QuarterlyScrutiny.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("Serve Resource method 2");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean isError = false;
		PfmIarNonNps pfmIarNonNps = new PfmIarNonNps();
		Boolean isNonNPSUser = pfmIarNonNps.isNonNpsUser(themeDisplay.getUserId());
		
		_log.info("isNonNPSUser========"+isNonNPSUser);
		if(isNonNPSUser) {
			updatePFMQRIAR(resourceRequest,themeDisplay);
		}else {
		
			String annex_comp_certificate_rem = ParamUtil.getString(resourceRequest, "annex_comp_certificate_rem");
			String extracts_board_pdf_rem = ParamUtil.getString(resourceRequest, "extracts_board_pdf_rem");
			String annex_comp_certificate_rem_i = ParamUtil.getString(resourceRequest, "annex_comp_certificate_rem_i");
			String annex_comp_certificate_rem_ii = ParamUtil.getString(resourceRequest, "annex_comp_certificate_rem_ii");
			String annex_comp_certificate_rem_iii = ParamUtil.getString(resourceRequest, "annex_comp_certificate_rem_iii");
			
			long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
			
			_log.info("reportUploadLogId: " + reportUploadLogId );
			
			JSONObject jsonObject = PMFIARUtil.getloopingContexOfDetailedAuditReport();
			
			JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
			
			for(String key : jsonObject.keySet()) {
				
				JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
				long size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, key+"_nps_rem_", size);
				/*
				//1to5
				if(key.equalsIgnoreCase("Obligations and Authorisations")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "obligations_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Code of Conduct")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "conduct_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Management Fees")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "fees_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Audit")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "audit_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Cyber Security, Information Technology & Infrastructure")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "security_nps_rem_", size);
				}
				
				//6to10
				if(key.equalsIgnoreCase("Foreign Holding")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "foreign_holding_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Legal Proceedings")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "legal_proceedings_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Informed")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "informed_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Other Matters")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "other_matters_nps_rem_", size);
				}
				*/
				//In end
				if(Validator.isNotNull(jsonObject_three)) {
					//data save
					_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
					jsonObject_two.put(key, jsonObject_three);
				}
				
			}
			
			try {
				pfm_Qr_IARScrutinyLocalService.addPfm_Qr_IARScrutiny
				(reportUploadLogId, themeDisplay.getUser().getScreenName(),
						0, themeDisplay.getUserId(), annex_comp_certificate_rem, 
						annex_comp_certificate_rem_i, annex_comp_certificate_rem_ii, 
						annex_comp_certificate_rem_iii, extracts_board_pdf_rem, jsonObject_two.toString());
				
				//(reportUploadLogId, themeDisplay.getUser().getScreenName(), 0, themeDisplay.getUserId(),
				//		jsonObject_two.toString());
				/*addDARScrutiny(themeDisplay.getUser().getScreenName(), 0, 
						themeDisplay.getUserId(), dar_pdf_rem_1, dar_pdf_rem_2,  jsonObject_two.toString(),
						new Date(), themeDisplay.getUserId(), reportUploadLogId);*/
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
	
	private void saveExecutiveSummary(JSONArray summariesArray,long reportUploadLogId ) {
		// TODO Auto-generated method stub
		 if(Validator.isNotNull(summariesArray)) {
		    	for(int row=0;row<summariesArray.length();row++) {
		    		try {
			    	JSONObject summary=summariesArray.getJSONObject(row);
			    	PFM_executive_summaryLocalServiceUtil.addExecutiveSummary(reportUploadLogId, summary.getString("boardDiscription"), summary.getString("auditorObservation"), summary.getString("managementResponse"),summary.getString("riskRating"), summary.getString("npstComment"));
		    		}catch (Exception e) {
						_log.error(e);
					}
			    }
		    }
	}
	
	private JSONArray getExecutiveSummary(ResourceRequest resourceRequest) {
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		JSONArray jsonArray=JSONFactoryUtil.createJSONArray();
		
		String[] rowIndex = ParamUtil.getParameterValues(resourceRequest, "rowIndex[]");
		String[] boardDiscription = ParamUtil.getParameterValues(resourceRequest, "boardDiscription[]");
		String[] auditorObservation = ParamUtil.getParameterValues(resourceRequest, "auditorObservation[]");
		String[] riskRating = ParamUtil.getParameterValues(resourceRequest, "riskRating[]");
		String[] managementResponse = ParamUtil.getParameterValues(resourceRequest, "managementResponse[]");
		String[] npstComment = ParamUtil.getParameterValues(resourceRequest, "npstComment[]");
		_log.info("boardDiscription size:  "+boardDiscription.length);
		//PFM_executive_summaryLocalServiceUtil.deleteExecutiveSummaryByReportUplodLogId(reportUploadLogId);
		for(int i=0; i<rowCount; i++) {
			try {
			_log.info("rowIndex :  "+rowIndex[i]+"   i  : "+i);
			int index=Integer.parseInt(rowIndex[i]);
			_log.info("index: "+index);
			if(index>0) {
				JSONObject jsonObject=JSONFactoryUtil.createJSONObject();
				_log.info("boardDiscription " + boardDiscription[i] + " auditorObservation " + auditorObservation[i] +
						" riskRating " + riskRating[i]);
				jsonObject.put("boardDiscription", boardDiscription[i]);
				jsonObject.put("auditorObservation", auditorObservation[i]);
				jsonObject.put("riskRating", riskRating[i]);
				jsonObject.put("managementResponse", managementResponse[i]);
				jsonObject.put("npstComment", npstComment[i]);
				//PFM_executive_summaryLocalServiceUtil.addExecutiveSummary(reportUploadLogId, boardDiscription[i], auditorObservation[i], managementResponse[i],riskRating[i], npstComment[i]);
			jsonArray.put(jsonObject);
			}else {
				continue;
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonArray;
	}
	
	private JSONObject getJsonData(ResourceRequest resourceRequest,String auditorComments, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String auditor = ParamUtil.getString(resourceRequest, auditorComments+String.valueOf(i));
			
			jsonObject.put(auditorComments+i, auditor);
		}
		
		return jsonObject;
		
	}
	
private JSONObject getIARDetails(ResourceRequest resourceRequest){
		
		JSONObject jsonObject = PMFIARUtil.getloopingContexOfDetailedAuditReport();

		JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();

		for(String key : jsonObject.keySet()) {

			JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
			JSONObject jsonObject_four = JSONFactoryUtil.createJSONObject();
			long size = jsonObject.getLong(key);	
			jsonObject_three = getJsonData(resourceRequest, "observations_"+key+"_", size);
			jsonObject_four = getJsonData(resourceRequest, "management_"+key+"_", size);
			if(Validator.isNotNull(jsonObject_three)) {
				//data save
				_log.debug("* name : " +key + " " + " value : "+jsonObject_three.toString());
				jsonObject_two.put("observations_"+key+"_", jsonObject_three);
				jsonObject_two.put("management_"+key+"_", jsonObject_four);
			}

		}
		return jsonObject_two;
	}

	private void updatePFMQRIAR(ResourceRequest resourceRequest,ThemeDisplay themeDisplay) {
		DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		try {
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		_log.info("reportupload logid:  "+reportUploadLogId);
		Pfm_Qr_Internal_Audit_Report qr_IAR= Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.getPfm_Qr_Internal_Audit_Report(reportUploadLogId);
		 JSONObject iarDetails= getIARDetails(resourceRequest);
				 
		 _log.info("iarDetails: "+iarDetails);
		 JSONArray exSummaries= getExecutiveSummary(resourceRequest);
		 File file=Qr_IAR_PDF_Util.Quarterly_IAR_PFMPDF(dateFormat.format(qr_IAR.getReportDate()), iarDetails, exSummaries);
		 
		 long fileEntryId=qr_IAR.getFileEntryId();
			int isAmRejected=0;
			ReportUploadFileLog reportUploadFileLog =null;
			try {
				 reportUploadFileLog = ReportUploadFileLogLocalServiceUtil.findByFileEntryId(fileEntryId);
				FileEntry dlFile=DLAppLocalServiceUtil.getFileEntry(fileEntryId);
			if(qr_IAR.getIsAmRejected()==1 || Validator.isNotNull(reportUploadFileLog.getSignature())) {
				fileEntryId=CompCerUtil.uploadFile(file, themeDisplay, resourceRequest);
				
				ReportUploadFileLogLocalServiceUtil.addReportUploadFileLog(reportUploadLogId, fileEntryId, themeDisplay.getUserId());
				reportUploadFileLog = ReportUploadFileLogLocalServiceUtil.findByFileEntryId(fileEntryId);
				isAmRejected=2;
			}else {
				if(Validator.isNull(reportUploadFileLog.getSignature())) {
				DLVersionNumberIncrease dlVersionNumberIncrease = DLVersionNumberIncrease.AUTOMATIC;
				DLAppLocalServiceUtil.updateFileEntry(themeDisplay.getUserId(), fileEntryId, dlFile.getFileName(), dlFile.getMimeType(), dlFile.getTitle(), dlFile.getDescription(),
						"", dlVersionNumberIncrease, file, serviceContext);
				
				}
				
			//	fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
			}
			}catch (Exception e) {
				_log.error(e.getMessage());
			}
		
//		JSONObject jsonObject = PMFIARUtil.getloopingContexOfDetailedAuditReport();
//
//		JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
//
//		for(String key : jsonObject.keySet()) {
//
//			JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
//			JSONObject jsonObject_four = JSONFactoryUtil.createJSONObject();
//			long size = jsonObject.getLong(key);	
//			jsonObject_three = getJsonData(resourceRequest, "observations_"+key+"_", size);
//			jsonObject_four = getJsonData(resourceRequest, "management_"+key+"_", size);
//			if(Validator.isNotNull(jsonObject_three)) {
//				//data save
//				_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
//				jsonObject_two.put("observations_"+key+"_", jsonObject_three);
//				jsonObject_two.put("management_"+key+"_", jsonObject_four);
//			}
//
//		}
			if(Validator.isNull(reportUploadFileLog.getSignature())) {
		_log.info("json : "+iarDetails);
		Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.updatePfm_Qr_Internal_Audit_Report(reportUploadLogId,fileEntryId, iarDetails.toJSONString(),isAmRejected);
		try {
			PFM_executive_summaryLocalServiceUtil.deleteExecutiveSummaryByReportUplodLogId(reportUploadLogId);
			saveExecutiveSummary(exSummaries, reportUploadLogId);
		} catch (Exception e) {
			_log.error("error while saving executive summary : "+e);
		}
			}
		}catch (Exception e) {
			_log.error(e);
		}
		}
	
	@Reference
	Pfm_Qr_IARScrutinyLocalService pfm_Qr_IARScrutinyLocalService;

}
