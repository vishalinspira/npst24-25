package nps.cust.am.pfrda.reportuploadlog.handler;

import com.daily.average.service.model.ReportUploadLogCustAMPFRDA;
import com.daily.average.service.service.ReportUploadLogCustAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.liferay.portal.kernel.exception.PortalException;
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
		property = {"model.class.name=com.daily.average.service.model.ReportUploadLogCustAMPFRDA"},
		service = WorkflowHandler.class
)
public class ReportUploadLogCustAMPFRDAWorkFlowHandler  extends BaseWorkflowHandler<ReportUploadLogCustAMPFRDA>{
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	ReportUploadLogCustAMPFRDALocalService reportUploadLogCustAMPFRDALocalService;

	@Override
	public String getClassName() {
		return ReportUploadLogCustAMPFRDA.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "ReportUploadLogCustAMPFRDA";
	}

	@Override
	public ReportUploadLogCustAMPFRDA updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		
		
		ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA = reportUploadLogCustAMPFRDALocalService.updateReportUploadLogPFMCustAMPFRDAStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));			
		return reportUploadLogCustAMPFRDA;
	}

}
