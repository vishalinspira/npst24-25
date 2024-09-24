package AnnualCompCertificatereport_Form.handler;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.service.AnnualCompCertificateLocalService;
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

public class AnnualCompCertificatereport_FormHandler extends BaseWorkflowHandler<AnnualCompCertificate> {
	
	AnnualCompCertificateLocalService annualCompCertificateLocalService;
	
	@Reference(unbind = "-")
	protected void setAnnualCompCertificateLocalService(AnnualCompCertificateLocalService annualCompCertificateLocalService) {
		this.annualCompCertificateLocalService = annualCompCertificateLocalService;
	}
	
	@Override
	public String getClassName() {
		return AnnualCompCertificate.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "AnnualCompCertificate";
	}

	@Override
	public AnnualCompCertificate updateStatus(int status, Map<String, Serializable> workflowContext)
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
		
		AnnualCompCertificate annualCompCertificate = annualCompCertificateLocalService.updateAnnualCompCertificateStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));
		return annualCompCertificate;
	}
	Log _log = LogFactoryUtil.getLog(AnnualCompCertificatereport_FormHandler.class);
}
