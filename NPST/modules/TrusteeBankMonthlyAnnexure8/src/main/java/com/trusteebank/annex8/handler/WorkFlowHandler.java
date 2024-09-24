package com.trusteebank.annex8.handler;

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

import compliance.service.model.ClosingBal;
import compliance.service.service.ClosingBalLocalService;

@Component(
		property = {"model.class.name=com.daily.average.service.model.ClosingBal"},
		service = WorkflowHandler.class
)
public class WorkFlowHandler extends BaseWorkflowHandler<ClosingBal> {
	
	private ClosingBalLocalService closingBalLocalService;
	
	
	@Reference(unbind = "-")
	protected void setAccountStatementLocalService(ClosingBalLocalService closingBalLocalService) {
		this.closingBalLocalService = closingBalLocalService;
	}

	@Override
	public String getClassName() {
		return ClosingBal.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "Closing Balance";
	}

	@Override
	public ClosingBal updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		ClosingBal closingBal = closingBalLocalService.updateClosingBalStatus(id, userId, status, serviceContext);
		
		return closingBal;
	}

}
