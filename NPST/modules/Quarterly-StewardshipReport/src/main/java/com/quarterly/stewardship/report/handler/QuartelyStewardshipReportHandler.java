package com.quarterly.stewardship.report.handler;

import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
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
public class QuartelyStewardshipReportHandler extends BaseWorkflowHandler<QtrStewardshipReport> {
	
	QtrStewardshipReportLocalService qtrStewardshipReportLocalService;
	
	@Reference(unbind = "-")
	public void setQtrStewardshipReportLocalService(QtrStewardshipReportLocalService qtrStewardshipReportLocalService) {
		this.qtrStewardshipReportLocalService = qtrStewardshipReportLocalService;
	}

	@Override
	public String getClassName() {
		return QtrStewardshipReport.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "QtrStewardshipReport";
	}

	@Override
	public QtrStewardshipReport updateStatus(int status, Map<String, Serializable> workflowContext)
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
		
		//StewardshipReportImpl implObject = new StewardshipReportImpl(this);
		QtrStewardshipReport stewardshipReport = qtrStewardshipReportLocalService.updateQtrStewardshipReportStatus(id, userId, status, serviceContext, WorkflowContextUtil.convert(workflowContext));

		return stewardshipReport;
	}
	
	Log _log = LogFactoryUtil.getLog(QuartelyStewardshipReportHandler.class);

}
