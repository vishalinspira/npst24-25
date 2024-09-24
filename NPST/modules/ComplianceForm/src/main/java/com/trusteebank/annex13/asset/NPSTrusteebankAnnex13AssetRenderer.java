package com.trusteebank.annex13.asset;

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


import compliance.service.model.Compliance_Cert;

public class NPSTrusteebankAnnex13AssetRenderer extends BaseJSPAssetRenderer<Compliance_Cert> {

	private final Compliance_Cert compliance_Cert;
	
	public NPSTrusteebankAnnex13AssetRenderer(Compliance_Cert compliance_Cert) {
		this.compliance_Cert = compliance_Cert;
	}
	
	@Override
	public Compliance_Cert getAssetObject() {
		return compliance_Cert;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(compliance_Cert.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return compliance_Cert.getCreatedBy();
	}

	@Override
	public String getUserName() {
		try {
			return userLocalService.getUser(compliance_Cert.getCreatedBy()).getFullName();
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		return "NA";
	}

	@Override
	public String getUuid() {
		return compliance_Cert.getUuid();
	}

	@Override
	public String getClassName() {
		return Compliance_Cert.class.getName();
	}

	@Override
	public long getClassPK() {
		return compliance_Cert.getCompid();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return "compliance Cert: " + compliance_Cert.getCompofficername1() + 
			   " by " + getUserName();
	}
	
	@Override
    public int getStatus() {
        return compliance_Cert.getStatus();
    }

	@Override
	public String getTitle(Locale locale) {
		return "compliance Cert: " + compliance_Cert.getCompofficername1() + 
				   " by " + getUserName();
	}
	
	@Override
    public int getAssetRendererType() {
        return super.getAssetRendererType();
    }

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		return "/asset/compliance_cert_workflow_abstract_view.jsp";
	}
	
	@Override
	public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String template) throws Exception {
		httpServletRequest.setAttribute("compliance_Cert", compliance_Cert);
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
