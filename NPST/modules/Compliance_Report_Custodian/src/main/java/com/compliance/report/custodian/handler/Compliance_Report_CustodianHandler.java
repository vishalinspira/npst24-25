package com.compliance.report.custodian.handler;

import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.service.CustodianCompFormLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.RoleServiceUtil;
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
		immediate = true,
		//property = {"model.class.name=com.daily.average.service.model.ReportUploadLogGrievances"},
		service = WorkflowHandler.class
)

public class Compliance_Report_CustodianHandler extends BaseWorkflowHandler<CustodianCompForm>{
	
	CustodianCompFormLocalService custodianCompFormLocalService;
	
	@Reference(unbind = "-")
	protected void setCustodianCompFormLocalService(CustodianCompFormLocalService custodianCompFormLocalService) {
		this.custodianCompFormLocalService = custodianCompFormLocalService;
	}

	@Override
	public String getClassName() {
		return CustodianCompForm.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "CustodianCompForm";
	}

	@Override
	public CustodianCompForm updateStatus(int status, Map<String, Serializable> workflowContext)
			throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		
		
		//WorkflowConstants.
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		boolean isMaker = RoleServiceUtil.hasUserRole(userId, serviceContext.getCompanyId(), "Maker", false);
		if(isMaker) {
			//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(id);
			//reportUploadLog.setUploaded_i(0);
			_log.info("isMaker"+isMaker);
			//_log.info("in ReportUploadLogGrievances"+ReportUploadLogLocalServiceUtil.updateReportUploadLog(0, id));			
		}
		
		CustodianCompForm custodianCompForm = custodianCompFormLocalService.updateCustodianCompFormStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));
		return custodianCompForm;
	}
	
		Log _log =LogFactoryUtil.getLog(Compliance_Report_CustodianHandler.class);
}

