package com.compliance.report.custodian.scrutiny;

import com.compliance.report.custodian.constants.compliance_report_custodianPortletKeys;
import com.daily.average.service.service.CustodianCompFormScrutinyLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { 
		"javax.portlet.name=" + compliance_report_custodianPortletKeys.COMPLIANCE_REPORT_CUSTODIAN,
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK,
		"javax.portlet.name=" + compliance_report_custodianPortletKeys.COMPLIANCE_REPORT_CUSTODIAN_SCRUTINY,
"mvc.command.name=/compformcustodianscrutiny/save" }, service = MVCResourceCommand.class)
public class Compliance_Report_Custodian_Scrutiny  implements MVCResourceCommand{
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		 ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		
		 DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		    
		 Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		 
		 
			//String remarks_i_i = ParamUtil.getString(resourceRequest, "remarks_i_i");
			String observe_i_i = ParamUtil.getString(resourceRequest, "observe_i_i");
			//String remarks_i_ii = ParamUtil.getString(resourceRequest, "remarks_i_ii");
			String observe_i_ii = ParamUtil.getString(resourceRequest, "observe_i_ii");
			//String remarks_ii = ParamUtil.getString(resourceRequest, "remarks_ii");
			String observe_ii = ParamUtil.getString(resourceRequest, "observe_ii");
			//String remarks_iii = ParamUtil.getString(resourceRequest, "remarks_iii");
			String observe_iii = ParamUtil.getString(resourceRequest, "observe_iii");
			//String remarks_iv = ParamUtil.getString(resourceRequest, "remarks_iv");
			String observe_iv = ParamUtil.getString(resourceRequest, "observe_iv");
			//String remarks_v = ParamUtil.getString(resourceRequest, "remarks_v");
			String observe_v = ParamUtil.getString(resourceRequest, "observe_v");
			//String remarks_vi = ParamUtil.getString(resourceRequest, "remarks_vi");
			String observe_vi = ParamUtil.getString(resourceRequest, "observe_vi");
			//String remarks_vii = ParamUtil.getString(resourceRequest, "remarks_vii");
			String observe_vii = ParamUtil.getString(resourceRequest, "observe_vii");
			//String remarks_viii = ParamUtil.getString(resourceRequest, "remarks_viii");
			String observe_viii = ParamUtil.getString(resourceRequest, "observe_viii");
			//String remarks_ix = ParamUtil.getString(resourceRequest, "remarks_ix");
			String observe_ix = ParamUtil.getString(resourceRequest, "observe_ix");
			//String remarks_x = ParamUtil.getString(resourceRequest, "remarks_x");
			String observe_x = ParamUtil.getString(resourceRequest, "observe_x");
			//String remarks_xi = ParamUtil.getString(resourceRequest, "remarks_xi");
			String observe_xi = ParamUtil.getString(resourceRequest, "observe_xi");
			//String remarks_xii = ParamUtil.getString(resourceRequest, "remarks_xii");
			String observe_xii = ParamUtil.getString(resourceRequest, "observe_xii");
			//String remarks_xiii = ParamUtil.getString(resourceRequest, "remarks_xiii");
			String observe_xiii = ParamUtil.getString(resourceRequest, "observe_xiii");
			//String month = ParamUtil.getString(resourceRequest, "month");
			//String signature = ParamUtil.getString(resourceRequest, "signature");
			//String employeeName = ParamUtil.getString(resourceRequest, "employeeName");
			//String designation = ParamUtil.getString(resourceRequest, "designation");
			//String twoiifour = ParamUtil.getString(resourceRequest, "twoiifour");
			//String date_3 = ParamUtil.getString(resourceRequest, "date_3");
			//String place = ParamUtil.getString(resourceRequest, "place");
			//Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
			//Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
			Long createdBy = themeDisplay.getUserId();
			
			CustodianCompFormScrutinyLocalServiceUtil.addCustodianCompFormScrutiny(reportUploadLogId, observe_i_i, observe_i_ii, observe_ii,
					observe_iii, observe_iv, observe_v, observe_vi, observe_vii, observe_viii, observe_ix, observe_x, observe_xi, observe_xii, 
					observe_xiii);
			
			try {
				PrintWriter writer = resourceResponse.getWriter();
				writer.write(resultJsonObject.toString());
			} catch (IOException e) {
				 _log.error(e);
			}
			
			return false;
	  }
	 Log _log = LogFactoryUtil.getLog(getClass());
}
