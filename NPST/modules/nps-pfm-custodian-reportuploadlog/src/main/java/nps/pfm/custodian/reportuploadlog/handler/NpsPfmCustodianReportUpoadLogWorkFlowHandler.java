package nps.pfm.custodian.reportuploadlog.handler;

import com.daily.average.service.model.ReportUploadLogPFMCustodian;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
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
		property = {"model.class.name=com.daily.average.service.model.ReportUploadLogPFMCustodian"},
		service = WorkflowHandler.class
)
public class NpsPfmCustodianReportUpoadLogWorkFlowHandler extends BaseWorkflowHandler<ReportUploadLogPFMCustodian> {
	

	ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService;
	
	@Reference(unbind = "-")
	protected void setAccountStatementLocalService(ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService) {
		this.reportUploadLogPFMCustodianLocalService = reportUploadLogPFMCustodianLocalService;
	}

	@Override
	public String getClassName() {
		return ReportUploadLogPFMCustodian.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "ReportUploadLogPFMCustodian";
	}

	
	@Override
	public ReportUploadLogPFMCustodian updateStatus(int status, Map<String, Serializable> workflowContext)
			throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		_log.info("in ReportUploadLogPFMCustodian");
		_log.info("in ReportUploadLogPFMCustodian");
		
		//WorkflowConstants.
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		boolean isMaker = RoleServiceUtil.hasUserRole(userId, serviceContext.getCompanyId(), "CRA-Supervisor", false);
		if(isMaker) {
			//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(id);
			//reportUploadLog.setUploaded_i(0);
			_log.info("isMaker"+isMaker);
			//_log.info("in ReportUploadLogPFMCustodian"+ReportUploadLogLocalServiceUtil.updateReportUploadLog(0, id));			
		}
		
		ReportUploadLogPFMCustodian reportUploadLogPFMCustodian = reportUploadLogPFMCustodianLocalService.updateReportUploadLogPFMCustodianStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));
		return reportUploadLogPFMCustodian;
	}
	
	
	

	Log _log = LogFactoryUtil.getLog(NpsPfmCustodianReportUpoadLogWorkFlowHandler.class);
}

