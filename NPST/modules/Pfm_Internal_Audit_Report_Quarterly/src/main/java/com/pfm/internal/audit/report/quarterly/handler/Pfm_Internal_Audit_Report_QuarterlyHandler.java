package com.pfm.internal.audit.report.quarterly.handler;

import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalService;
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
		//property = {"model.class.name=com.daily.average.service.model.ReportUploadLogGrievances"},
		service = WorkflowHandler.class
)


public class Pfm_Internal_Audit_Report_QuarterlyHandler extends BaseWorkflowHandler<Pfm_Qr_Internal_Audit_Report>{
	
	Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService;
	
	@Reference(unbind = "-")
	protected void setPfm_Qr_Internal_Audit_ReportLocalService(Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService) {
		this.pfm_Qr_Internal_Audit_ReportLocalService = pfm_Qr_Internal_Audit_ReportLocalService;
	}

	@Override
	public String getClassName() {
		return Pfm_Qr_Internal_Audit_Report.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "Pfm_Qr_Internal_Audit_Report";
	}

	@Override
	public Pfm_Qr_Internal_Audit_Report updateStatus(int status, Map<String, Serializable> workflowContext)
			throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		_log.info("in ReportUploadLogGrievances");
		_log.info("in ReportUploadLogGrievances");
		
		//WorkflowConstants.
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		boolean isMaker = RoleServiceUtil.hasUserRole(userId, serviceContext.getCompanyId(), "Maker", false);
		if(isMaker) {
			//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(id);
			//reportUploadLog.setUploaded_i(0);
			_log.info("isMaker"+isMaker);
			//_log.info("in ReportUploadLogGrievances"+ReportUploadLogLocalServiceUtil.updateReportUploadLog(0, id));			
		}
		
		Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportLocalService.updatePfm_Qr_Internal_Audit_ReportStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));
		return pfm_Qr_Internal_Audit_Report;
	}
	Log _log = LogFactoryUtil.getLog(Pfm_Internal_Audit_Report_QuarterlyHandler.class);

}
