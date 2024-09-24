package com.supervisor.log.handler;

import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalService;
import com.liferay.portal.kernel.exception.PortalException;
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
		property = {"model.class.name=com.daily.average.service.model.ReportUploadLogSupervisor"},
		service = WorkflowHandler.class
)
public class NpsSupervisorReportUpoadLogWorkFlowHandler  extends BaseWorkflowHandler<ReportUploadLogSupervisor>{
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	ReportUploadLogSupervisorLocalService reportUploadLogSupervisorLocalService;

	@Override
	public String getClassName() {
		return ReportUploadLogSupervisor.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "ReportUploadLogSupervisor";
	}

	@Override
	public ReportUploadLogSupervisor updateStatus(int status, Map<String, Serializable> workflowContext)
			throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		
		//WorkflowConstants.
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		boolean isMaker = RoleServiceUtil.hasUserRole(userId, serviceContext.getCompanyId(), "Supervisor", false);
		if(isMaker) {
			ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(id);
			reportUploadLog.setUploaded_i(0);
			
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
		}
		
		ReportUploadLogSupervisor reportUploadSupervisorLog = reportUploadLogSupervisorLocalService.updateReportUploadLogSupervisorStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));			
		return reportUploadSupervisorLog;
	}

}
