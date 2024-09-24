package nps.email.api.api;

import com.liferay.portal.kernel.json.JSONObject;

import javax.portlet.PortletRequest;

public interface ExcelValidationApi {

	public JSONObject validateExcelFile(PortletRequest portletRequest, String excelFileName);
	
}
