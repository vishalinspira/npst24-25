package com.trusteebank.annex9.asset;

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

import compliance.service.model.PerformanceRep;

public class NPSTrusteebankAnnex9AssetRenderer extends BaseJSPAssetRenderer<PerformanceRep> {
	static Log _log = LogFactoryUtil.getLog(NPSTrusteebankAnnex9AssetRenderer.class);

	private final PerformanceRep performanceRep;
	
	public NPSTrusteebankAnnex9AssetRenderer(PerformanceRep performanceRep) {
		this.performanceRep = performanceRep;
	}
	
	@Override
	public PerformanceRep getAssetObject() {
		return performanceRep;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(performanceRep.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return performanceRep.getCreatedBy();
	}

	@Override
	public String getUserName() {
		try {
			return userLocalService.getUser(performanceRep.getCreatedBy()).getFullName();
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		return "NA";
	}

	@Override
	public String getUuid() {
		return performanceRep.getUuid();
	}

	@Override
	public String getClassName() {
		return PerformanceRep.class.getName();
	}

	@Override
	public long getClassPK() {
		return performanceRep.getId();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return "Performance Rep: " + performanceRep.getTrusteebankname() + 
			   " by " + getUserName();
	}
	
	@Override
    public int getStatus() {
        return performanceRep.getStatus();
    }

	@Override
	public String getTitle(Locale locale) {
		return "Performance Rep: " + performanceRep.getTrusteebankname() + 
				   " by " + getUserName();
	}
	
	@Override
    public int getAssetRendererType() {
        return super.getAssetRendererType();
    }

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		return "/asset/performance_rep_workflow_abstract_view.jsp";
	}
	
	@Override
	public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String template) throws Exception {
		httpServletRequest.setAttribute("performanceRep", performanceRep);
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
