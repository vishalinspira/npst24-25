package com.quarter.ended.handler;

import com.daily.average.service.model.QuarterEnded;
import com.daily.average.service.service.QuarterEndedLocalService;
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

@Component(
		property = {"model.class.name=com.daily.average.service.model.QuarterEnded"},
		service = WorkflowHandler.class
)
public class WorkFlowHandler extends BaseWorkflowHandler<QuarterEnded> {
	
	private QuarterEndedLocalService quarterEndedLocalService;
	
	@Reference(unbind = "-")
	protected void setQuarterEndedLocalService(QuarterEndedLocalService quarterEndedLocalService) {
		this.quarterEndedLocalService = quarterEndedLocalService;
	}

	@Override
	public String getClassName() {
		return QuarterEnded.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "Quarter Ended";
	}

	@Override
	public QuarterEnded updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		QuarterEnded quarterEnded = quarterEndedLocalService.updateQuarterEndedStatus(id, userId, status, serviceContext);
		
		return quarterEnded;
	}

}
