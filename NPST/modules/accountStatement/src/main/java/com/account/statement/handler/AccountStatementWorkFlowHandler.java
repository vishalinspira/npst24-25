package com.account.statement.handler;

import com.daily.average.service.model.AccountStatement;
import com.daily.average.service.service.AccountStatementLocalService;
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
		property = {"model.class.name=com.daily.average.service.model.AccountStatement"},
		service = WorkflowHandler.class
)
public class AccountStatementWorkFlowHandler extends BaseWorkflowHandler<AccountStatement> {
	
	private AccountStatementLocalService accountStatementLocalService;
	
	@Reference(unbind = "-")
	protected void setAccountStatementLocalService(AccountStatementLocalService accountStatementLocalService) {
		this.accountStatementLocalService = accountStatementLocalService;
	}

	@Override
	public String getClassName() {
		return AccountStatement.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return "AccountStatement";
	}

	@Override
	public AccountStatement updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long id = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
		
		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");
		
		AccountStatement accountStatement = accountStatementLocalService.updateAccountStatementStatus(id, userId, status, serviceContext);
		
		return accountStatement;
	}

}
