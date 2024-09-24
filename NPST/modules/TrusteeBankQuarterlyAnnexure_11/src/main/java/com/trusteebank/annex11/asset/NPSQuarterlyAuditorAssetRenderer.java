package com.trusteebank.annex11.asset;

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

import compliance.service.model.QuarterlyAuditor;

public class NPSQuarterlyAuditorAssetRenderer extends BaseJSPAssetRenderer<QuarterlyAuditor>{
	
	static Log _log = LogFactoryUtil.getLog(NPSQuarterlyAuditorAssetRenderer.class);

	private final QuarterlyAuditor quarterlyAuditor;
	
	public NPSQuarterlyAuditorAssetRenderer(QuarterlyAuditor quarterlyAuditor) {
		this.quarterlyAuditor = quarterlyAuditor;
	}
	
	@Override
	public QuarterlyAuditor getAssetObject() {
		return quarterlyAuditor;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(quarterlyAuditor.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return quarterlyAuditor.getCreatedBy();
	}

	@Override
	public String getUserName() {
		try {
			return userLocalService.getUser(quarterlyAuditor.getCreatedBy()).getFullName();
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		return "NA";
	}

	@Override
	public String getUuid() {
		return quarterlyAuditor.getUuid();
	}

	@Override
	public String getClassName() {
		return QuarterlyAuditor.class.getName();
	}

	@Override
	public long getClassPK() {
		return quarterlyAuditor.getId();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return " Quarterly Audit by : "+getUserName();
	}
	
	@Override
	public int getStatus() {
		return quarterlyAuditor.getStatus();
	}

	@Override
	public String getTitle(Locale locale) {
		return " Quarterly Audit by : "+getUserName();
	}
	
	@Override
	public int getAssetRendererType() {
		return super.getAssetRendererType();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		return "/asset/Quarterly_Auditor_workflow_abstract_view.jsp";
	}
	
	@Override
	public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String template) throws Exception {
		httpServletRequest.setAttribute("quarterlyAuditor", quarterlyAuditor);
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
