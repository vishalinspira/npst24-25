package com.pfm.internal.audit.report.quarterly.resource;

import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.PFM_executive_summaryLocalServiceUtil;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.pfm.internal.audit.report.quarterly.constants.pfm_Internal_Audit_Report_QuarterlyPortletKeys;
import com.pfm.internal.audit.report.quarterly.util.CompCerUtil;
import com.pfm.internal.audit.report.quarterly.util.PMFIARUtil;
import com.pfm.internal.audit.report.quarterly.util.Qr_IAR_PDF_Util;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { 
		"javax.portlet.name=" + pfm_Internal_Audit_Report_QuarterlyPortletKeys.PFM_INTERNAL_AUDIT_REPORT_QUARTERLY,
		"mvc.command.name=/pfminternalauditreport/save" 
		}, 
service = MVCResourceCommand.class)
public class SavePfm_Internal_Audit_Report_Quarterly implements MVCResourceCommand{
	public Log _log = LogFactoryUtil.getLog(getClass());
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("pfm_internalaudit_pdf_file -------------  Start ");
		
		 boolean isError = false;
		
		 ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		 try {
			 PrintWriter writer = resourceResponse.getWriter();
			 if(themeDisplay.isSignedIn()) {
		 Long userId = themeDisplay.getUserId();
		 DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
			Date formDate =ParamUtil.getDate(resourceRequest,"formDate",dateFormat); 
			Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
			
			Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
			Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
			Long createdBy = themeDisplay.getUserId();
			String statusByUserName = themeDisplay.getUser().getFullName();
			ServiceContext serviceContext = null;
			
		 JSONObject iarDetails=getIARDetails(resourceRequest);
		 JSONArray exSummaries= getExecutiveSummary(resourceRequest);
		 File file=Qr_IAR_PDF_Util.Quarterly_IAR_PFMPDF(dateFormat.format(reportDate), iarDetails, exSummaries);
		 long pdf_file_fileEntryId = 0l;
			try {
				//pdf_file_fileEntryId = CompCerUtil.uploadFILETOFOLDER(themeDisplay,resourceRequest, "compliance_certificate_pdf_file", "pdf");
				pdf_file_fileEntryId = CompCerUtil.uploadFile(file, themeDisplay, resourceRequest);
				_log.info("pfm_internalaudit_pdf_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
			} catch (Exception e) {
				isError = true;
				_log.error(e.getMessage(), e);
			}
			
			
			
			
			Long annex_comp_certificate = PMFIARUtil.addDocuments(resourceRequest, "annex_comp_certificate", StringPool.BLANK);//ParamUtil.getLong(resourceRequest, "annex_comp_certificate");
			Long extracts_board_pdf = PMFIARUtil.addDocuments(resourceRequest, "extracts_board_pdf", StringPool.BLANK);//ParamUtil.getLong(resourceRequest, "extracts_board_pdf");
			Long annex_comp_certificate_i = PMFIARUtil.addDocuments(resourceRequest, "annex_comp_certificate_i", StringPool.BLANK);
			Long annex_comp_certificate_ii = PMFIARUtil.addDocuments(resourceRequest, "annex_comp_certificate_ii", StringPool.BLANK);
			Long annex_comp_certificate_iii = PMFIARUtil.addDocuments(resourceRequest, "annex_comp_certificate_iii", StringPool.BLANK);
			JSONArray jsonArray= JSONFactoryUtil.createJSONArray();
			
			try {
				DLFileEntry entry= DLFileEntryLocalServiceUtil.getDLFileEntry(pdf_file_fileEntryId);
				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
				jsonObj.put("Name", entry.getName());
				jsonObj.put("fileEntryId", entry.getFileEntryId());
				jsonArray.put(jsonObj);
			}catch (Exception e) {}
			
			try {
				DLFileEntry entry= DLFileEntryLocalServiceUtil.getDLFileEntry(annex_comp_certificate);
				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
				jsonObj.put("Name", entry.getName());
				jsonObj.put("fileEntryId", entry.getFileEntryId());
				jsonArray.put(jsonObj);
			}catch (Exception e) {}
			
			try {
				DLFileEntry entry= DLFileEntryLocalServiceUtil.getDLFileEntry(extracts_board_pdf);
				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
				jsonObj.put("Name", entry.getName());
				jsonObj.put("fileEntryId", entry.getFileEntryId());
				jsonArray.put(jsonObj);
			}catch (Exception e) {}
			
			try {
				DLFileEntry entry= DLFileEntryLocalServiceUtil.getDLFileEntry(annex_comp_certificate_i);
				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
				jsonObj.put("Name", entry.getName());
				jsonObj.put("fileEntryId", entry.getFileEntryId());
				jsonArray.put(jsonObj);
			}catch (Exception e) {}
			
			try {
				DLFileEntry entry= DLFileEntryLocalServiceUtil.getDLFileEntry(annex_comp_certificate_ii);
				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
				jsonObj.put("Name", entry.getName());
				jsonObj.put("fileEntryId", entry.getFileEntryId());
				jsonArray.put(jsonObj);
			}catch (Exception e) {}
			
			try {
				DLFileEntry entry= DLFileEntryLocalServiceUtil.getDLFileEntry(annex_comp_certificate_iii);
				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
				jsonObj.put("Name", entry.getName());
				jsonObj.put("fileEntryId", entry.getFileEntryId());
				jsonArray.put(jsonObj);
			}catch (Exception e) {}
			
			try {
				serviceContext = ServiceContextFactory.getInstance(resourceRequest);
			} catch (PortalException e) {
				_log.error("Exception on getting servicecontext : "+e.getMessage(),e);
			}
			
			try {
				Pfm_Qr_Internal_Audit_Report  pfm_Qr_Internal_Audit_Report = Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.fetchPfm_Qr_Internal_Audit_Report(reportUploadLogId);
				
				boolean reupload = false;
				if(Validator.isNotNull(pfm_Qr_Internal_Audit_Report)) {
					reupload = true;
				}
				Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.addPfm_Qr_Internal_Audit_Report(reportUploadLogId, new Date(), createdBy, reportMasterId, 
						annex_comp_certificate, extracts_board_pdf, annex_comp_certificate_i, annex_comp_certificate_ii, 
						annex_comp_certificate_iii, iarDetails.toJSONString());
				
				if(reupload) {
					_log.info(" reupload pfm_Qr_Internal_Audit_Report-------------"+pfm_Qr_Internal_Audit_Report);
					try {
						PFM_executive_summaryLocalServiceUtil.deleteExecutiveSummaryByReportUplodLogId(reportUploadLogId);
						saveExecutiveSummary(exSummaries, reportUploadLogId);
					} catch (Exception e) {
						_log.error("error while saving executive summary : "+e);
					}
					Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, reportUploadLogId,
							WorkflowConstants.STATUS_PENDING, userId, statusByUserName, new Date(), serviceContext, "", false, userId);
					
				}else {
					_log.info(" new upload pfm_Qr_Internal_Audit_Report-------------");
					try {
						saveExecutiveSummary(exSummaries, reportUploadLogId);
					} catch (Exception e) {
						_log.error("error while saving executive summary : "+e);
					}
					Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, reportUploadLogId,
							WorkflowConstants.STATUS_PENDING, userId, statusByUserName, new Date(), serviceContext, "", true, userId);
				}
				try {
					List<ReportUploadFileLog> fileLog= ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					ReportUploadFileLog reportFileLog=fileLog.get(fileLog.size()-1);
					reportFileLog.setFileList(jsonArray.toString());
					ReportUploadFileLogLocalServiceUtil.updateReportUploadFileLog(reportFileLog);
				}catch (Exception e) {
					_log.error("error while updating the file list");
				}
			} catch (Exception e) {
				_log.error(e);
				_log.error("Exception in Persisting the table data one  : "+e.getMessage());
				isError = true;
			}
			
			
			if(!isError) {
				writer.print("success");
			}else {
				writer.print("error");
			}
		 
			 }else {
				 writer.print("You have successfully logout.Please login and try again.");
			 }
			 writer.close();
			 }catch (Exception e) {
			 _log.error(e);
		}
		return false;
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
				_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
				jsonObject_two.put("observations_"+key+"_", jsonObject_three);
				jsonObject_two.put("management_"+key+"_", jsonObject_four);
			}

		}
		return jsonObject_two;
	}
	private void saveExecutiveSummary(JSONArray summariesArray,long reportUploadLogId ) {
		// TODO Auto-generated method stub
		 if(Validator.isNotNull(summariesArray)) {
		    	for(int row=0;row<summariesArray.length();row++) {
		    		try {
			    	JSONObject summary=summariesArray.getJSONObject(row);
			    	PFM_executive_summaryLocalServiceUtil.addExecutiveSummary(reportUploadLogId, summary.getString("boardDiscription"), summary.getString("auditorObservation"),summary.getString("managementResponse"), summary.getString("riskRating"), summary.getString("npstComment"));
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
			if(Integer.parseInt(rowIndex[i])>0) {
				JSONObject jsonObject=JSONFactoryUtil.createJSONObject();
				_log.info("boardDiscription " + boardDiscription[i] + " auditorObservation " + auditorObservation[i] +
						" riskRating " + riskRating[i]);
				jsonObject.put("boardDiscription", boardDiscription[i]);
				jsonObject.put("auditorObservation", auditorObservation[i]);
				jsonObject.put("riskRating", riskRating[i]);
				jsonObject.put("managementResponse", managementResponse[i]);
				//jsonObject.put("npstComment", npstComment[i]);
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
}
