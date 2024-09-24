package com.cra.report.upload.log.handler;

import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
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
		//property = {"model.class.name=com.daily.average.service.model.ReportUploadLogCRA"},
		service = WorkflowHandler.class
)
public class NpsCraReportUploadlogHandler extends BaseWorkflowHandler<ReportUploadLogCRA>{
	ReportUploadLogCRALocalService reportUploadLogCRALocalService;
	
	@Reference(unbind = "-")
	protected void setAccountStatementLocalService(ReportUploadLogCRALocalService reportUploadLogCRALocalService) {
		this.reportUploadLogCRALocalService = reportUploadLogCRALocalService;
	}

	@Override
	public String getClassName() {
		return ReportUploadLogCRA.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "ReportUploadLogCRA";
	}

	
	@Override
	public ReportUploadLogCRA updateStatus(int status, Map<String, Serializable> workflowContext)
			throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		_log.info("in ReportUploadLogCRA");
		_log.info("in ReportUploadLogCRA");
		
		//WorkflowConstants.
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		boolean isMaker = RoleServiceUtil.hasUserRole(userId, serviceContext.getCompanyId(), "Maker", false);
		if(isMaker) {
			//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(id);
			//reportUploadLog.setUploaded_i(0);
			_log.info("isMaker"+isMaker);
			//_log.info("in ReportUploadLogCRA"+ReportUploadLogLocalServiceUtil.updateReportUploadLog(0, id));			
		}
		
		ReportUploadLogCRA reportUploadLogMaker = reportUploadLogCRALocalService.updateReportUploadLogCRAStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));
		return reportUploadLogMaker;
	}
	
	
	

	Log _log = LogFactoryUtil.getLog(NpsCraReportUploadlogHandler.class);
}
