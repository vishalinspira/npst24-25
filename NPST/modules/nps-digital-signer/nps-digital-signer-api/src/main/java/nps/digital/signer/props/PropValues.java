package nps.digital.signer.props;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;


public class PropValues {
	public static final String DSC_DOMAIN = GetterUtil.getString(PropsUtil.get(PropKeys.DSC_DOMAIN_KEY), "");
	public static final String DSC_USER = GetterUtil.getString(PropsUtil.get(PropKeys.DSC_USER_KEY), "");
	public static final String DSC_PASS = GetterUtil.getString(PropsUtil.get(PropKeys.DSC_PASS_KEY), "");
}
