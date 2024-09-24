package com.auditor.certificate.asset;

import com.daily.average.service.model.AuditorCertificate;
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

public class NPSAuditorCertificateAssetRender extends BaseJSPAssetRenderer<AuditorCertificate>{

	private final AuditorCertificate auditorCertificate;
	
	public NPSAuditorCertificateAssetRender(AuditorCertificate auditorCertificate) {
		this.auditorCertificate = auditorCertificate;
	}
	
	@Override
	public AuditorCertificate getAssetObject() {
		return auditorCertificate;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(auditorCertificate.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return auditorCertificate.getCreatedBy();
	}

	@Override
	public String getUserName() {
		try {
			return userLocalService.getUser(auditorCertificate.getCreatedBy()).getFullName();
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		return "NA";
	}

	@Override
	public String getUuid() {
		return auditorCertificate.getUuid();
	}

	@Override
	public String getClassName() {
		return AuditorCertificate.class.getName();
	}

	@Override
	public long getClassPK() {
		return auditorCertificate.getAuditid();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return " Auditor Certificate by : "+getUserName();
	}
	
	@Override
	public int getStatus() {
		return auditorCertificate.getStatus();
	}

	@Override
	public String getTitle(Locale locale) {
		return " Auditor Certificate by : "+getUserName();
	}
	
	@Override
	public int getAssetRendererType() {
		return super.getAssetRendererType();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		return "/asset/Auditor_Certificate_workflow_abstract_view.jsp";
	}
	
	@Override
	public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String template) throws Exception {
		httpServletRequest.setAttribute("auditorCertificate", auditorCertificate);
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
    
    static Log _log = LogFactoryUtil.getLog(NPSAuditorCertificateAssetRender.class);
}
