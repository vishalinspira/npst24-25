package nps.common.service.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalServiceUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkFlowTaskUtil {
	
	/**
	 * 
	 * @param className
	 * @param classPk
	 * @param companyId
	 * @param groupId
	 * @param userId
	 * @return 
	 * @throws WorkflowException
	 */
	public  static WorkflowTask getWorkFlowtask(String className,long classPk,long companyId,long groupId,long userId) throws WorkflowException {
		List<Integer> logTypes_assign = new ArrayList<Integer>();
		logTypes_assign.add(WorkflowLog.TASK_ASSIGN);
		logTypes_assign.add(WorkflowLog.TASK_COMPLETION);
		List<WorkflowLog> workflowLogs_assign=getWorkflowLogs(companyId, groupId, className,classPk,logTypes_assign);
		
		if (workflowLogs_assign.size() > 0) {
			workflowLogs_assign=workflowLogs_assign.stream().sorted(Comparator.comparing(WorkflowLog::getCreateDate).reversed()).collect(Collectors.toList());
		}		
		return WorkflowTaskManagerUtil.getWorkflowTask(companyId, workflowLogs_assign.get(0).getWorkflowTaskId());

	}
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public static  boolean isAssignable(long companyId,long userId, long taskId) {
		try {
		List<User> userList = WorkflowTaskManagerUtil.getAssignableUsers(companyId, taskId);
		if (userList.stream().anyMatch(u -> u.getUserId() == userId)) {
			return true;
		}}catch (Exception e) {
			_log.error(e.getMessage());
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param classPK
	 * @param className
	 * @return
	 */
	public static String getUserName(long classPK, String className) {
		long userId = 0;
		if(className.equalsIgnoreCase(Role.class.getName())) {
			long[] userIds = UserLocalServiceUtil.getRoleUserIds(classPK);
			userId = userIds.length > 0 ? userIds[0] : 0;
		}else if(className.equalsIgnoreCase(User.class.getName())) {
			userId = classPK;
		}
		if(userId > 0) {
			try {
				return UserLocalServiceUtil.getUser(userId).getFullName();
			} catch (PortalException e) {
				_log.error("Exception on getting user name : "+e.getMessage());
			}
		}
		
		return StringPool.BLANK;
	}
	
	
	/**
	 * 
	 * @param kaleoLogs
	 * @return
	 */
	public static String getComment(List<KaleoLog> kaleoLogs) {
		String comment=StringPool.BLANK;
		if(!kaleoLogs.isEmpty() && kaleoLogs.get(0).getComment() != null && !kaleoLogs.get(0).getComment().isEmpty()) {
			comment = kaleoLogs.get(0).getComment();
		}
		return comment;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param groupId
	 * @param className
	 * @param pk
	 * @return
	 * @throws PortalException
	 */
	private static Map<String, Serializable> getWorkflowContext(long companyId,long groupId,String className,long pk) throws PortalException{
		WorkflowInstanceLink wil = WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(
				companyId, groupId, className,pk);
		WorkflowInstance workflowInstance = WorkflowInstanceManagerUtil.getWorkflowInstance(companyId, wil.getWorkflowInstanceId());
		return workflowInstance.getWorkflowContext();
	}
	/**
	 * 
	 * @param companyId
	 * @param groupId
	 * @param className
	 * @param pk
	 * @param logTypes
	 * @return
	 */
	public static List<WorkflowLog> getWorkflowLogs(long companyId,long groupId,String className,long pk,List<Integer> logTypes){
		List<WorkflowLog> workflowLogs_assign=null;
		try {
		WorkflowInstanceLink wil = WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(
				companyId, groupId, className,pk);

		 workflowLogs_assign = WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				companyId, wil.getWorkflowInstanceId(), logTypes, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));
		}catch (Exception e) {
			_log.error(e.getMessage());
		}
		return workflowLogs_assign;
	}

	/**
	 * 
	 * @param kaleoLogs
	 * @param userId
	 * @return
	 */
	public static boolean isSelfAsignee(List<KaleoLog> kaleoLogs,long userId) {
		boolean isSelfAsignee=false;
		if(kaleoLogs.get(0).getCurrentAssigneeClassName().equalsIgnoreCase(User.class.getName()) && userId==kaleoLogs.get(0).getCurrentAssigneeClassPK()) {
			isSelfAsignee=true;
		}	
		return isSelfAsignee;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param workflowInstanceId
	 * @return
	 */
	public static boolean isSelfAsignee(long companyId,long userId,long workflowInstanceId ) {
		List<KaleoLog> kaleoLogs=getKaleoLogs(companyId, workflowInstanceId, "TASK_ASSIGNMENT");
		boolean isSelfAsignee=false;
		if(kaleoLogs.get(0).getCurrentAssigneeClassName().equalsIgnoreCase(User.class.getName()) && userId==kaleoLogs.get(0).getCurrentAssigneeClassPK()) {
			isSelfAsignee=true;
		}	
		return isSelfAsignee;
	}
	
	
	
	
	/**
	 * 
	 * @param companyId
	 * @param workflowInstanceId
	 * @param logType
	 * @return
	 */
	public static List<KaleoLog> getKaleoLogs(long companyId, long workflowInstanceId,String logType) {
		OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
		List<Integer> logTypesOne = new ArrayList<>();
		logTypesOne.add(KaleoLogUtil.convert(logType));
		return KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, workflowInstanceId, logTypesOne, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
	}
	
	static Log _log = LogFactoryUtil.getLog(WorkFlowTaskUtil.class);
}
