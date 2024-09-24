package com.quarter.ended.asset;

import com.daily.average.service.model.QuarterEnded;
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

public class NPSQuarterEndedAssetRenderer extends BaseJSPAssetRenderer<QuarterEnded> {

	private final QuarterEnded quarterEnded;
	
	public NPSQuarterEndedAssetRenderer(QuarterEnded quarterEnded) {
		this.quarterEnded = quarterEnded;
	}
	
	@Override
	public QuarterEnded getAssetObject() {
		return quarterEnded;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(quarterEnded.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return quarterEnded.getCreatedBy();
	}

	@Override
	public String getUserName() {
		try {
			return userLocalService.getUser(quarterEnded.getCreatedBy()).getFullName();
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		return "NA";
	}

	@Override
	public String getUuid() {
		return quarterEnded.getUuid();
	}

	@Override
	public String getClassName() {
		return QuarterEnded.class.getName();
	}

	@Override
	public long getClassPK() {
		return quarterEnded.getId();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return "Quarter Ended: " + quarterEnded.getOneIa() + 
			   " by " + getUserName();
	}
	
	@Override
    public int getStatus() {
        return quarterEnded.getStatus();
    }

	@Override
	public String getTitle(Locale locale) {
		return "Quarter Ended: " + quarterEnded.getOneIa() + 
				   " by " + getUserName();
	}
	
	@Override
    public int getAssetRendererType() {
        return super.getAssetRendererType();
    }

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		return "/asset/quarter_ended_workflow_abstract_view.jsp";
	}
	
	@Override
	public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String template) throws Exception {
		httpServletRequest.setAttribute("quarterEnded", quarterEnded);
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
    
    static Log _log = LogFactoryUtil.getLog(NPSQuarterEndedAssetRenderer.class);

}
