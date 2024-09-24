package Cut_Annual_Audit_Report.Scrutiny;

import com.daily.average.service.model.CustAnnualReportscrutiny;
import com.daily.average.service.service.CustAnnualReportscrutinyLocalService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Cut_Annual_Audit_Report.constants.Cut_Annual_Audit_ReportPortletKeys;

@Component(property = { 
		"javax.portlet.name=" + Cut_Annual_Audit_ReportPortletKeys.CUT_ANNUAL_AUDIT_REPORT,
		"mvc.command.name=" + Cut_Annual_Audit_ReportPortletKeys.SAVE_CUT_ANNUAL_AUDITSCRUTINY_REPORT, 
		}, 
service = MVCResourceCommand.class)

public class SaveCut_Annual_Audit_ReportScrutiny extends BaseMVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(SaveCut_Annual_Audit_ReportScrutiny.class);
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("welcome to save ::::::::::::::");
		
		boolean isError = false;
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("Regulatory parameters", 9);
		jsonObject.put("Operational parameters", 22);
		jsonObject.put("Timely Reporting", 13);
		jsonObject.put("Custodian Billing", 2);
		jsonObject.put("Custodian", 5);
		jsonObject.put("Infrastructure", 12);
		jsonObject.put("Protection", 6);
		jsonObject.put("Record maintenance", 4);
		jsonObject.put("Grievance Redressal", 2);
		jsonObject.put("Other parameters", 2);
		jsonObject.put("Governance Structure", 4);
		
		JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
		for(String key : jsonObject.keySet()) {
			JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
			long size = 0;
			
			//Regulatory parameters 1to9
			if(key.equalsIgnoreCase("Regulatory parameters")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "regulatory_rem_", size);
			}
			
			
			//Operational parameters 1to 22
			if(key.equalsIgnoreCase("Operational parameters")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "operational_rem_", size);
			}
			
			//Timely Reporting "C" 1to 13
			if(key.equalsIgnoreCase("Timely Reporting")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "timely_rem_", size);
			}
			
			
			//Custodian Billing "D" 1 to 2
			if(key.equalsIgnoreCase("Custodian Billing")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "billingd_rem_", size);
			}
			
			
			//Custodian Administration "E" 1 to 5
			if(key.equalsIgnoreCase("Custodian")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "custodiane_rem_", size);
			}
			
			
			//Infrastructure "F" 1 to 12
			if(key.equalsIgnoreCase("Infrastructure")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "infrastructuref_rem_", size);
			}
			
			
			//Protection of Assets of  Beneficial Owners "g" 1 to 6
			if(key.equalsIgnoreCase("Protection")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "protectiong_rem_", size);
			}
			
			
			//Record maintenance and storage "H" 1 to 4
			if(key.equalsIgnoreCase("Record maintenance")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "recordh_rem_", size);
			}
			
			
			//Grievance Redressal "i" 1 to 2
			if(key.equalsIgnoreCase("Grievance Redressal")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "grievancei_rem_", size);
			}
			
			
			//Other Parameters "j" 1 to 2
			if(key.equalsIgnoreCase("Other parameters")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "otherj_rem_", size);
			}
			
			
			//Governance Structure of Custodian "K" 1to 4
			if(key.equalsIgnoreCase("Governance Structure")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "governancek_rem_", size);
			}
			
			
			if(Validator.isNotNull(jsonObject_three)) {
				//data save
				//_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
				jsonObject_two.put(key, jsonObject_three);
			}
       }
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId"); 
		_log.info("reportUploadLogId:::" + reportUploadLogId);
		String audit_pdf_file_remarks = ParamUtil.getString(resourceRequest, "audit_pdf_file_remarks");
		try {
			
			CustAnnualReportscrutiny annualReportscrutiny = custAnnualReportscrutinyLocalService.saveCustAnnualReportscrutiny
			(reportUploadLogId,themeDisplay.getUser().getScreenName(), 0, themeDisplay.getUserId(), jsonObject_two.toString(), audit_pdf_file_remarks, new Date(), themeDisplay.getUserId());
			_log.info("annualReportscrutiny " + annualReportscrutiny);
		}catch (Exception e) {
			_log.error("Exception in Persisting the table data one  : "+e.getMessage());
		}
		

		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				writer.print("Success");
			}
			
		} catch (IOException e) {
			_log.error(e);
		}
		
	}
	
	private JSONObject getJsonData(ResourceRequest resourceRequest,String remark, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String remarks = ParamUtil.getString(resourceRequest, remark+String.valueOf(i));
			jsonObject.put(remark+i, remarks);
		}
		return jsonObject;
		
	}
	
	@Reference
	CustAnnualReportscrutinyLocalService custAnnualReportscrutinyLocalService;
}
