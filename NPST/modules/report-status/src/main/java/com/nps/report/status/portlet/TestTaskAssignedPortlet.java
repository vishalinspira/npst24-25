package com.nps.report.status.portlet;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.nps.report.status.constants.ReportStatusPortletKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author VishalKumar
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=TestTaskAssigned",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ReportStatusPortletKeys.TESTTASKASSIGNED,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TestTaskAssignedPortlet extends MVCPortlet {
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		//super.doView(renderRequest, renderResponse);
		try {
			
			//ReportUploadLogPFM report=ReportUploadLogPFMLocalServiceUtil.getReportUploadLogPFM(20715);
			List<Integer> logTypes_assign = new ArrayList<Integer>();
			logTypes_assign.add(WorkflowLog.TASK_ASSIGN);
			long reportlogId=25477;
			
			//String className="com.daily.average.service.model.ReportUploadLogPFM";
			
			String className="com.daily.average.service.model.QtrStewardshipReport";
			//String className="com.daily.average.service.model.InputQuarterlyInterval";
			WorkflowInstanceLink wil = WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(20097, 20121,className,reportlogId);

			List<WorkflowLog> workflowLogs_assign = WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(20097, wil.getWorkflowInstanceId(), logTypes_assign, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));
			
			LOG.info("great"+workflowLogs_assign.size());
			if (workflowLogs_assign.size() > 0) {

				long taskId = workflowLogs_assign.get(workflowLogs_assign.size() - 1).getWorkflowTaskId();
				//WorkflowTask task = WorkflowTaskManagerUtil.getWorkflowTask(20097, taskId);
				LOG.info(taskId);

				//List<String> nextTransitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(20097, context.getUserId(), taskId);
					
						List<User> userList = WorkflowTaskManagerUtil.getAssignableUsers(20097, taskId);
						LOG.info(userList.size());
						for(User user:userList) {
							LOG.info(user.getUserId()+"  :  "+user.getFullName());
						}		 
			}
			}
			catch (Exception e) {
				LOG.info(e);
			}
	}
	private static final Log LOG = LogFactoryUtil.getLog(TestTaskAssignedPortlet.class);
}