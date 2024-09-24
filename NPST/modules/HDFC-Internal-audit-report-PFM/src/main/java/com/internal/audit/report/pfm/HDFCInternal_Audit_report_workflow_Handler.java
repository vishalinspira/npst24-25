package com.internal.audit.report.pfm;

import com.daily.average.service.model.HDFCInternal_Audit_Report;
import com.daily.average.service.service.HDFCInternal_Audit_ReportLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {"model.class.name=com.daily.average.service.model.HDFCInternal_Audit_Report"},
		service = WorkflowHandler.class
)
public class HDFCInternal_Audit_report_workflow_Handler  extends BaseWorkflowHandler<HDFCInternal_Audit_Report>{
	
	private HDFCInternal_Audit_ReportLocalService auditReport;
	
	@Reference(unbind = "-")
	public void setHDFCInternal_Audit_ReportLocalService( HDFCInternal_Audit_ReportLocalService auditReport) {
		this.auditReport = auditReport;
	}

	@Override
	public String getClassName() {
		return HDFCInternal_Audit_Report.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "hdfc_internal_Audit_Report";
	}

	@Override
	public HDFCInternal_Audit_Report updateStatus(int status, Map<String, Serializable> workflowContext)
			throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		boolean isMaker = RoleServiceUtil.hasUserRole(userId, serviceContext.getCompanyId(), "Maker", false);
		if(isMaker) {
			//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(id);
			//reportUploadLog.setUploaded_i(0);
			_log.info("isMaker"+isMaker);
			//_log.info("in ReportUploadLogGrievances"+ReportUploadLogLocalServiceUtil.updateReportUploadLog(0, id));			
		}
		
		HDFCInternal_Audit_Report auditReportOBJECT = auditReport.updateHDFCInternal_Audit_ReportStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));
		
		return auditReportOBJECT;
	}
	Log _log = LogFactoryUtil.getLog(getClass());

}
