package com.trusteebank.annex13.handler;


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

import compliance.service.model.Compliance_Cert;
import compliance.service.service.Compliance_CertLocalService;

@Component(
		property = {"model.class.name=compliance.service.model.Compliance_Cert"},
		service = WorkflowHandler.class
)
public class ComplianceFormWorkflowHandler extends BaseWorkflowHandler<Compliance_Cert>{
	
	private Compliance_CertLocalService compliance_CertLocalService;

	@Reference(unbind = "-")
	protected void setAccountStatementLocalService(Compliance_CertLocalService compliance_CertLocalService) {
		this.compliance_CertLocalService = compliance_CertLocalService;
	}
	
	@Override
	public String getClassName() {
		return Compliance_Cert.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "Compliance_Cert";
	}

	@Override
	public Compliance_Cert updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		Compliance_Cert compliance_Cert = compliance_CertLocalService.updateComplianceCertStatus(id, userId, status, serviceContext);
		return compliance_Cert;
	}

}
