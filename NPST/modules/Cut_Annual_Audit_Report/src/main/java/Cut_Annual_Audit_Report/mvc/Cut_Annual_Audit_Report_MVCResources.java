package Cut_Annual_Audit_Report.mvc;

import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.service.CustAnnualAuditReportLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Cut_Annual_Audit_Report.Util.Cut_Annual_Audit_ReportDocumentUtil;
import Cut_Annual_Audit_Report.constants.Cut_Annual_Audit_ReportPortletKeys;

@Component(property = { 
		"javax.portlet.name=" + Cut_Annual_Audit_ReportPortletKeys.CUT_ANNUAL_AUDIT_REPORT,
		"mvc.command.name=" + Cut_Annual_Audit_ReportPortletKeys.SAVE_CUT_ANNUAL_AUDIT_REPORT, 
		}, 
service = MVCResourceCommand.class)

public class Cut_Annual_Audit_Report_MVCResources extends BaseMVCResourceCommand{

	private static Log _log = LogFactoryUtil.getLog(Cut_Annual_Audit_Report_MVCResources.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("wlecome to Cut_Annual_Audit_Report_MVCResources:::::::::::::::");
		
		boolean isError = false;

		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long pdf_file_fileEntryId = 0l;
		try {
			_log.info("pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
			pdf_file_fileEntryId = Cut_Annual_Audit_ReportDocumentUtil.addDocuments(resourceRequest, "Custodian_Annual_Audit_Report_pdf_file", "pdf");
			_log.info("Custodian_Annual_Audit_Report_pdf_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
		} catch (Exception e) {
			isError = true;
			_log.error(e.getMessage(), e);
		}
		
		String name = ParamUtil.getString(resourceRequest, "employeeName");
		String designation = ParamUtil.getString(resourceRequest, "roles");
		String date_ = ParamUtil.getString(resourceRequest, "date_2");
		String place = ParamUtil.getString(resourceRequest, "place1");
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId::::::::::::::::::::::::::"+reportUploadLogId);
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("Regulatory parameters", 9);
		jsonObject.put("Operational parameters", 22);
		jsonObject.put("Timely Reporting", 13);
		jsonObject.put("Custodian Billing", 2);
		jsonObject.put("Custodian", 5);
		jsonObject.put("Infrastructure", 12);
		jsonObject.put("Protection", 6);
		jsonObject.put("Record maintenance", 4);
		jsonObject.put("Grievance Redressal", 2);
		jsonObject.put("Other parameters", 2);
		jsonObject.put("Governance Structure", 4);
		
		JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
		
		 for(String key : jsonObject.keySet()) {
				JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
				long size = 0;
				
				//Regulatory parameters 1to9
				if(key.equalsIgnoreCase("Regulatory parameters")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_regulatorya_", "remarks_regulatorya_","management_regulatorya_", size);
				}
				
				
				//Operational parameters 1to 22
				if(key.equalsIgnoreCase("Operational parameters")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_operationalb_", "remarks_operationalb_","management_operationalb_", size);
				}
				
				//Timely Reporting "C" 1to 13
				if(key.equalsIgnoreCase("Timely Reporting")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_timelyc_", "remarks_timelyc_","management_timelyc_", size);
				}
				
				
				//Custodian Billing "D" 1 to 2
				if(key.equalsIgnoreCase("Custodian Billing")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_billingd_", "remarks_billingd_","management_billingd_", size);
				}
				
				
				//Custodian Administration "E" 1 to 5
				if(key.equalsIgnoreCase("Custodian")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_custodiane_", "remarks_custodiane_","management_custodiane_", size);
				}
				
				
				//Infrastructure "F" 1 to 12
				if(key.equalsIgnoreCase("Infrastructure")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_infrastructuref_", "remarks_infrastructuref_","management_infrastructuref_", size);
				}
				
				
				//Protection of Assets of  Beneficial Owners "g" 1 to 6
				if(key.equalsIgnoreCase("Protection")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_protectiong_", "remarks_protectiong_","management_protectiong_", size);
				}
				
				
				//Record maintenance and storage "H" 1 to 4
				if(key.equalsIgnoreCase("Record maintenance")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_recordh_", "remarks_recordh_","management_recordh_", size);
				}
				
				
				//Grievance Redressal "i" 1 to 2
				if(key.equalsIgnoreCase("Grievance Redressal")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "commets_grievancei_", "remarks_grievancei_","management_grievancei_", size);
				}
				
				
				//Other Parameters "j" 1 to 2
				if(key.equalsIgnoreCase("Other parameters")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_otherj_", "remarks_otherj_","management_otherj_", size);
				}
				
				
				//Governance Structure of Custodian "K" 1to 4
				if(key.equalsIgnoreCase("Governance Structure")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "comments_governancek_", "remarks_governancek_","management_governancek_", size);
				}
				
				_log.info("jsonObject_three"+jsonObject_three.toString());
				if(Validator.isNotNull(jsonObject_three)) {
					jsonObject_two.put(key, jsonObject_three);
				
					
				}
				
	       }
		 
		 long audit_pdf_fileentry_id = Cut_Annual_Audit_ReportDocumentUtil.addDocuments(resourceRequest, "annualreport", StringPool.BLANK);
		 _log.info("audit_pdf_fileentry_id " + audit_pdf_fileentry_id);
 
		 
		 try {
			 CustAnnualAuditReport custAnnual = custAnnualAuditReportLocalService.fetchCustAnnualAuditReport(reportUploadLogId);
			 
				boolean reupload = false;
				if(Validator.isNotNull(custAnnual)) {
					_log.info(custAnnual);
					reupload = true;
				}
			 
			 	//add the save data
				CustAnnualAuditReport auditReport = custAnnualAuditReportLocalService.saveCustAnnualAuditReport(reportUploadLogId, audit_pdf_fileentry_id,jsonObject_two.toString(),
						name, designation, date_, place, new Date(), themeDisplay.getUserId(),new Date());
				_log.info("reportUploadLogId::::" + reportUploadLogId);
				
				
			if(custAnnual != null && reupload) {
				_log.info("custAnnual ------- "+custAnnual.toString());
				_log.info("custAnnual not null::::");
				custAnnualAuditReportLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), pdf_file_fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, 
						themeDisplay.getUserId(), statusByUserName, new Date(), serviceContext, "", false);
			}else {
				_log.info("auditReport ------- "+auditReport.toString());
				_log.info("custAnnual else::::");
				custAnnualAuditReportLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), pdf_file_fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, 
						themeDisplay.getUserId(), statusByUserName, new Date(), serviceContext, "", true);
			}
			 
		 }catch (Exception e) {
			 //isError = true;
			 _log.error("Exception in Persisting the table data one  : "+e.getMessage());
			  _log.error(e);
		 }
			try {
				PrintWriter writer = resourceResponse.getWriter();
				writer.write(resultJsonObject.toString());
			} catch (IOException e) {
				 _log.error(e);
			}
//			
//		 try {
//				PrintWriter writer = resourceResponse.getWriter();
//				if(!isError) {
//					writer.print("Success");
//				}
//				
//			} catch (IOException e) {
//				_log.error(e);
//			}
	}
	
	private JSONObject getJsonData(ResourceRequest resourceRequest,String comment,String remark, 
			String managementResponse,  long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String comments = ParamUtil.getString(resourceRequest, comment+String.valueOf(i));
			String remarks = ParamUtil.getString(resourceRequest, remark+String.valueOf(i));
			String management = ParamUtil.getString(resourceRequest, managementResponse+String.valueOf(i));
			
			jsonObject.put(comment+i, comments);
			jsonObject.put(remark+i, remarks);
			jsonObject.put(managementResponse+i, management);
			
		}
		return jsonObject;
		
	}

	
	@Reference
	CustAnnualAuditReportLocalService custAnnualAuditReportLocalService;
}
