package nps.griev.am.pfrda.handler;

import com.daily.average.service.model.ReportUploadLogGrievAMPFRDA;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalService;
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
		property = {"model.class.name=com.daily.average.service.model.ReportUploadLogGrievAMPFRDA"},
		service = WorkflowHandler.class
)
public class ReportUploadLogGrievAMPFRDAWorkFlowHandler  extends BaseWorkflowHandler<ReportUploadLogGrievAMPFRDA>{
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	ReportUploadLogGrievAMPFRDALocalService reportUploadLogGrievAMPFRDALocalService;

	@Override
	public String getClassName() {
		return ReportUploadLogGrievAMPFRDA.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "ReportUploadLogGrievAMPFRDA";
	}

	@Override
	public ReportUploadLogGrievAMPFRDA updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		
		
		ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDALocalService.updateReportUploadLogGrievAMPFRDAStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));			
		return reportUploadLogGrievAMPFRDA;
	}
}
