package com.trusteebank.annex11.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import compliance.service.model.QuarterlyAuditor;
import compliance.service.service.QuarterlyAuditorLocalService;

@Component(
		property = {"model.class.name=compliance.service.model.QuarterlyAuditor"},
		service = WorkflowHandler.class
)
public class QuarterlyAnnexureWorkFlowHandler extends BaseWorkflowHandler<QuarterlyAuditor>{

	private QuarterlyAuditorLocalService quarterlyAuditorLocalService;
	
	@Reference(unbind = "-")
	protected void setQuarterlyAuditorLocalService(QuarterlyAuditorLocalService quarterlyAuditorLocalService) {
		this.quarterlyAuditorLocalService = quarterlyAuditorLocalService;
	}
	
	@Override
	public String getClassName() {
		return QuarterlyAuditor.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "QuarterlyAuditor";
	}

	@Override
	public QuarterlyAuditor updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		QuarterlyAuditor quarterlyAuditors = quarterlyAuditorLocalService.updateQuarterlyAuditorStatus(id, userId, status, serviceContext);
		return quarterlyAuditors;
	}

}
