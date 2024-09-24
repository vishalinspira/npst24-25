package com.DailyAverage.handler;

import com.daily.average.service.model.DailyAverage;
import com.daily.average.service.service.DailyAverageLocalService;
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
		property = {"model.class.name=com.daily.average.service.model.DailyAverage"},
		service = WorkflowHandler.class
)
public class WorkFlowHandler extends BaseWorkflowHandler<DailyAverage> {
	
	private DailyAverageLocalService dailyAverageLocalService; 
	
	@Reference(unbind = "-")
	protected void setDailyAverageLocalService(DailyAverageLocalService dailyAverageLocalService) {
		this.dailyAverageLocalService = dailyAverageLocalService;
	}

	@Override
	public String getClassName() {
		return DailyAverage.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "Daily Average";
	}

	@Override
	public DailyAverage updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		DailyAverage dailyAverage = dailyAverageLocalService.updateDailyAverageStatus(id, userId, status, serviceContext);
		
		return dailyAverage;
	}

}
