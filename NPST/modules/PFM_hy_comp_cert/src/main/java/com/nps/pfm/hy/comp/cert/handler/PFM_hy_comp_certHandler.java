package com.nps.pfm.hy.comp.cert.handler;

import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.service.PFM_hy_comp_certLocalService;
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


public class PFM_hy_comp_certHandler extends BaseWorkflowHandler<PFM_hy_comp_cert>{
	
	PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;
	
	@Reference(unbind = "-")
	protected void setPFM_hy_comp_certLocalService(PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService) {
		this.pfm_hy_comp_certLocalService = pfm_hy_comp_certLocalService;
	}

	@Override
	public String getClassName() {
		return PFM_hy_comp_cert.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "PFM_hy_comp_cert";
	}

	@Override
	public PFM_hy_comp_cert updateStatus(int status, Map<String, Serializable> workflowContext)
			throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		_log.info("in ReportUploadLogGrievances");
		_log.info("in ReportUploadLogGrievances");
		
		//WorkflowConstants.
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		boolean isMaker = RoleServiceUtil.hasUserRole(userId, serviceContext.getCompanyId(), "Maker", false);
		if(isMaker) {
			//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(id);
			//reportUploadLog.setUploaded_i(0);
			_log.info("isMaker"+isMaker);
			//_log.info("in ReportUploadLogGrievances"+ReportUploadLogLocalServiceUtil.updateReportUploadLog(0, id));			
		}
		
		PFM_hy_comp_cert pfm_hy_comp_cert = pfm_hy_comp_certLocalService.updatePFM_hy_comp_certStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));
		return pfm_hy_comp_cert;
	}
	Log _log = LogFactoryUtil.getLog(PFM_hy_comp_certHandler.class);

}

