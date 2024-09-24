package com.trusteebank.annex9.handler;

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

import compliance.service.model.PerformanceRep;
import compliance.service.service.PerformanceRepLocalService;

@Component(
		property = {"model.class.name=com.daily.average.service.model.PerformanceRep"},
		service = WorkflowHandler.class
)
public class WorkFlowHandler extends BaseWorkflowHandler<PerformanceRep> {
	
	private PerformanceRepLocalService performanceRepLocalService;
	
	@Reference(unbind = "-")
	protected void setPerformanceRepLocalService(PerformanceRepLocalService performanceRepLocalService) {
		this.performanceRepLocalService = performanceRepLocalService;
	}

	@Override
	public String getClassName() {
		return PerformanceRep.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "Performance Rep";
	}

	@Override
	public PerformanceRep updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		PerformanceRep performanceRep = performanceRepLocalService.updatePerformanceRepStatus(id, userId, status, serviceContext);
		
		return performanceRep;
	}

}
