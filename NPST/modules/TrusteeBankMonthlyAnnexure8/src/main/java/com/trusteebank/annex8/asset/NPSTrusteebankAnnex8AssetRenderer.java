package com.trusteebank.annex8.asset;

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

import compliance.service.model.ClosingBal;

public class NPSTrusteebankAnnex8AssetRenderer extends BaseJSPAssetRenderer<ClosingBal> {
	
	static Log _log = LogFactoryUtil.getLog(NPSTrusteebankAnnex8AssetRenderer.class);

	private final ClosingBal closingBal; 
	
	public NPSTrusteebankAnnex8AssetRenderer(ClosingBal closingBal) {
		this.closingBal = closingBal;
	}
	
	@Override
	public ClosingBal getAssetObject() {
		return closingBal;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(closingBal.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return closingBal.getCreatedBy();
	}

	@Override
	public String getUserName() {
		try {
			return userLocalService.getUser(closingBal.getCreatedBy()).getFullName();
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		return "NA";
	}

	@Override
	public String getUuid() {
		return closingBal.getUuid();
	}

	@Override
	public String getClassName() {
		return ClosingBal.class.getName();
	}

	@Override
	public long getClassPK() {
		return closingBal.getExitid();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return "Closing Balance: " + closingBal.getAddressline1()+ 
			   " by " + getUserName();
	}
	
	@Override
    public int getStatus() {
        return closingBal.getStatus();
    }

	@Override
	public String getTitle(Locale locale) {
		return "closingBal: " + closingBal.getAddressline1() + 
				   " by " + getUserName();
	}
	
	@Override
    public int getAssetRendererType() {
        return super.getAssetRendererType();
    }

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		return "/asset/closing_bal_workflow_abstract_view.jsp";
	}
	
	@Override
	public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String template) throws Exception {
		httpServletRequest.setAttribute("closingBal", closingBal);
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
