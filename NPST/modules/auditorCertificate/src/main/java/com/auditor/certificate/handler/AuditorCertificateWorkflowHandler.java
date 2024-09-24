package com.auditor.certificate.handler;

import com.daily.average.service.model.AuditorCertificate;
import com.daily.average.service.service.AuditorCertificateLocalService;
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
		property = {"model.class.name=com.daily.average.service.model.AuditorCertificate"},
		service = WorkflowHandler.class
)
public class AuditorCertificateWorkflowHandler extends BaseWorkflowHandler<AuditorCertificate>{

	private AuditorCertificateLocalService auditorCertificateLocalService;
	
	@Reference(unbind = "-")
	protected void setAccountStatementLocalService(AuditorCertificateLocalService auditorCertificateLocalService) {
		this.auditorCertificateLocalService = auditorCertificateLocalService;
	}
	
	@Override
	public String getClassName() {
		return AuditorCertificate.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "AuditorCertificate";
	}

	@Override
	public AuditorCertificate updateStatus(int status, Map<String, Serializable> workflowContext)
			throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		AuditorCertificate auditorCertificateAsset = auditorCertificateLocalService.updateAuditorCertificateStatus(id, userId, status, serviceContext);
		
		return auditorCertificateAsset;
	}

}
