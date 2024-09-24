package nps.common.service.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;

import nps.common.service.constants.NPSTRoleConstants;

public class IntermediataryRoleUtil {
	public static String getUserIntermediataryRole(long companyId, long userId) {
		
		try {
			if(RoleLocalServiceUtil.hasUserRole(userId, companyId, NPSTRoleConstants.CAMS, false)) {
				return NPSTRoleConstants.CAMS;
			}else if (RoleLocalServiceUtil.hasUserRole(userId, companyId, NPSTRoleConstants.KCRA, false)) {
				return NPSTRoleConstants.KCRA;
			}else if (RoleLocalServiceUtil.hasUserRole(userId, companyId, NPSTRoleConstants.NCRA, false)) {
				return NPSTRoleConstants.NCRA;
			}
		} catch (PortalException e) {
			_log.error(e);
		}
		return null;
	}
	
	static Log _log = LogFactoryUtil.getLog(IntermediataryRoleUtil.class);
}
