package com.DailyAverage.asset;

import com.daily.average.service.model.AccountStatement;
import com.daily.average.service.model.DailyAverage;
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

public class NPSDailyAverageAssetRenderer extends BaseJSPAssetRenderer<DailyAverage> {

	private final DailyAverage dailyAverage;
	private final Log _log = LogFactoryUtil.getLog(NPSDailyAverageAssetRenderer.class);
	
	public NPSDailyAverageAssetRenderer(DailyAverage dailyAverage) {
		this.dailyAverage = dailyAverage;
	}
	
	@Override
	public DailyAverage getAssetObject() {
		return dailyAverage;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(dailyAverage.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		return dailyAverage.getCreatedby();
	}

	@Override
	public String getUserName() {
		try {
			return userLocalService.getUser(dailyAverage.getCreatedby()).getFullName();
		} catch (PortalException e) {
			_log.error("Exception getting user full name : "+e.getMessage(), e);
		}
		
		return "NA";
	}

	@Override
	public String getUuid() {
		return dailyAverage.getUuid();
	}

	@Override
	public String getClassName() {
		return AccountStatement.class.getName();
	}

	@Override
	public long getClassPK() {
		return dailyAverage.getId();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return "Daily Average: " + dailyAverage.getNps_acc_name() + 
			   " by " + getUserName();
	}
	
	@Override
    public int getStatus() {
        return dailyAverage.getStatus();
    }

	@Override
	public String getTitle(Locale locale) {
		return "Daily Average: " + dailyAverage.getNps_acc_name() + 
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
		httpServletRequest.setAttribute("dailyAverage", dailyAverage);
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

}
