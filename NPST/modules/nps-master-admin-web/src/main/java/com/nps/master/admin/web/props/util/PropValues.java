package com.nps.master.admin.web.props.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;


public class PropValues {
	public static final String COMPANY_ID = GetterUtil.getString(PropsUtil.get(PropKeys.COMPANY_ID),"");
	public static final String GROUP_ID = GetterUtil.getString(PropsUtil.get(PropKeys.COMPANY_ID),"");
}
