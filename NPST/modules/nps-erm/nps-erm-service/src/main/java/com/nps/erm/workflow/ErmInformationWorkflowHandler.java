package com.nps.erm.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.nps.erm.model.ErmInformation;
import com.nps.erm.service.ErmInformationLocalService;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(
		 property = {"model.class.name=com.nps.erm.model.ErmInformation"},
		 service = WorkflowHandler.class
		)
public class ErmInformationWorkflowHandler  extends BaseWorkflowHandler<ErmInformation> {

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return ErmInformation.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		// TODO Auto-generated method stub
		return "ERM";
		//return  _resourceActions.getModelResource(locale, getClassName());
	}

	@Override
	public ErmInformation updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		 long userId = GetterUtil.getLong(
		            (String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		        long resourcePrimKey = GetterUtil.getLong(
		            (String)workflowContext.get(
		                WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		        ServiceContext serviceContext = (ServiceContext)workflowContext.get(
		            "serviceContext");

		        return ermInformationLocalService.updateStatus(
		            userId, resourcePrimKey, status, serviceContext);
	}
	
	@Reference
	ErmInformationLocalService ermInformationLocalService;

	@Reference(unbind = "-")
    protected void setResourceActions(ResourceActions resourceActions) {

        _resourceActions = resourceActions;
    }

    private ResourceActions _resourceActions;
}
