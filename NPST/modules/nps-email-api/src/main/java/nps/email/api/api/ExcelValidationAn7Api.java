package nps.email.api.api;

import com.liferay.portal.kernel.json.JSONObject;

import java.io.File;

public interface ExcelValidationAn7Api {
	public JSONObject validateExcelFile(File file, String fileName);
}
