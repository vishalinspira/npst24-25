package com.account.statement.asset;

import com.daily.average.service.model.AccountStatement;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

public class NPSAccountStatementAssetRenderer extends BaseJSPAssetRenderer<AccountStatement> {

	private final AccountStatement accountStatement;
	
	public NPSAccountStatementAssetRenderer(AccountStatement accountStatement) {
		this.accountStatement = accountStatement;
	}
	
	@Override
	public AccountStatement getAssetObject() {
		return accountStatement;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(accountStatement.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return accountStatement.getCreatedBy();
	}

	@Override
	public String getUserName() {
		try {
			return userLocalService.getUser(accountStatement.getCreatedBy()).getFullName();
		} catch (PortalException e) {
			_log.error(e);
		}
		
		return "NA";
	}

	@Override
	public String getUuid() {
		return accountStatement.getUuid();
	}

	@Override
	public String getClassName() {
		return AccountStatement.class.getName();
	}

	@Override
	public long getClassPK() {
		return accountStatement.getId();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return "Account Statement: " + accountStatement.getAccountname() + 
			   " by " + getUserName();
	}
	
	@Override
    public int getStatus() {
        return accountStatement.getStatus();
    }

	@Override
	public String getTitle(Locale locale) {
		return "Account Statement: " + accountStatement.getAccountname() + 
				   " by " + getUserName();
	}
	
	@Override
    public int getAssetRendererType() {
        return super.getAssetRendererType();
    }

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		return "/asset/account_statement_workflow_abstract_view.jsp";
	}
	
	@Override
	public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String template) throws Exception {
		httpServletRequest.setAttribute("accountStatement", accountStatement);
		return super.include(httpServletRequest, httpServletResponse, template);
	}
	
	@Override
    public String getURLView(LiferayPortletResponse liferayPortletResponse, WindowState windowState) throws Exception {
        return super.getURLView(liferayPortletResponse, windowState);
    }

    @Override
    public boolean isPrintable() {
        return true;
    }
    
    @Reference
    UserLocalService userLocalService; 
    Log _log = LogFactoryUtil.getLog(getClass());
}
