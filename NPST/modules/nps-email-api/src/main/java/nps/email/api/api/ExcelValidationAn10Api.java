package nps.email.api.api;

import com.liferay.portal.kernel.json.JSONObject;

import java.io.File;

import javax.portlet.PortletRequest;

public interface ExcelValidationAn10Api {
	public JSONObject validateExcelFile(File file, PortletRequest portletRequest);
}
