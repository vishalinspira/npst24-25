package nps.email.api.props;




import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

public class PropValues {
	public static final String BODY_SMS1 = GetterUtil.getString(PropsUtil.get(PropKeys.BODY_SMS1), "");
	public static final String BODY_SMS1_CONTENTID = GetterUtil.getString(PropsUtil.get(PropKeys.BODY_SMS1_CONTENTID), "");
	
	public static final String BODY_SMS2 = GetterUtil.getString(PropsUtil.get(PropKeys.BODY_SMS2), "");
	public static final String BODY_SMS2_CONTENTID = GetterUtil.getString(PropsUtil.get(PropKeys.BODY_SMS2_CONTENTID), "");
	
	
	public static final String ENDPOINT_SMS_GATEWAY = GetterUtil.getString(PropsUtil.get(PropKeys.ENDPOINT_SMS_GATEWAY), "");
	 public static final String SMS_GATEWAY_USERNAME = GetterUtil.getString(PropsUtil.get(PropKeys.SMS_GATEWAY_USERNAME), "");
	    public static final String SMS_GATEWAY_PASSWORD = GetterUtil.getString(PropsUtil.get(PropKeys.SMS_GATEWAY_PASSWORD), "");
	    public static final String SMS_GATEWAY_SENDERID = GetterUtil.getString(PropsUtil.get(PropKeys.SMS_GATEWAY_SENDERID), "");
	    public static final String SMS_GATEWAY_PEID = GetterUtil.getString(PropsUtil.get(PropKeys.SMS_GATEWAY_PEID), "");
	    public static final String ENDPOINT_GIS_AUTH_USERNAME = GetterUtil.getString(PropsUtil.get(PropKeys.ENDPOINT_GIS_AUTH_USERNAME), "");
	    public static final String ENDPOINT_GIS_AUTH_PASSWORD = GetterUtil.getString(PropsUtil.get(PropKeys.ENDPOINT_GIS_AUTH_PASSWORD), "");
	    
	public static final Long COMPANY_ID = GetterUtil.getLong(PropsUtil.get(PropKeys.COMPANY_ID), 0);
	public static final Long GROUP_ID = GetterUtil.getLong(PropsUtil.get(PropKeys.COMPANY_ID), 0);
   
	public static final String ERROR_API_URL = GetterUtil.getString(PropsUtil.get(PropKeys.ERROR_API_URL), "");
	public static final String ERROR_API_USER = GetterUtil.getString(PropsUtil.get(PropKeys.ERROR_API_USER), "");
	public static final String ERROR_API_PASS = GetterUtil.getString(PropsUtil.get(PropKeys.ERROR_API_PASS), "");
	
}
