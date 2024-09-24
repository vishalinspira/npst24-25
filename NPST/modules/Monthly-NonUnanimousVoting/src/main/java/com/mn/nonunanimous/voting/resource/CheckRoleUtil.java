package com.mn.nonunanimous.voting.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

public class CheckRoleUtil {
	
	private static Log _log = LogFactoryUtil.getLog(CheckRoleUtil.class);
	
	public static boolean isUserRole(HttpServletRequest renderRequest, String roleName) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        
        try {
			return RoleServiceUtil.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), roleName.trim(), false);
		} catch (PortalException e) {
			_log.error(e.getMessage(),e);
		}
        
        return false;
    }

}
