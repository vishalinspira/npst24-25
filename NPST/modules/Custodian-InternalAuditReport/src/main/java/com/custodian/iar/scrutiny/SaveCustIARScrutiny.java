package com.custodian.iar.scrutiny;

import com.custodian.iar.constants.CustodianInternalAuditReportPortletKeys;
import com.custodian.iar.util.CustodianIARUtil;
import com.daily.average.service.service.CustIARScrutinyLocalService;
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

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { 
		"javax.portlet.name=" + CustodianInternalAuditReportPortletKeys.CUSTODIANINTERNALAUDITREPORT,
		"mvc.command.name=" + CustodianInternalAuditReportPortletKeys.custodianIARScrutiny,
		}, 
service = MVCResourceCommand.class)

public class SaveCustIARScrutiny extends BaseMVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(SaveCustIARScrutiny.class);

	@Override
	public void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("Serve Resource method 2");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PrintWriter writer = resourceResponse.getWriter();
		if(themeDisplay.isSignedIn()) {
			boolean isError = false;
			
			String iar_rem = ParamUtil.getString(resourceRequest, "iar_rem");
			
			long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
			
			_log.info(iar_rem);
			
			JSONObject jsonObject = CustodianIARUtil.getloopingContexOfCustodianInternalAuditReport();
			
			JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
			
			for(String key : jsonObject.keySet()) {
				
				JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
				long size = 0;
				
				//1to5
				if(key.equalsIgnoreCase("Board Meeting")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "boardMeeting_Comm_", size);
				}
				if(key.equalsIgnoreCase("Operational Manual/Procedure")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "operational_Comm_", size);
				}
				if(key.equalsIgnoreCase("Risk Management Policy")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "risk_Comm_", size);
				}
				if(key.equalsIgnoreCase("Risk Management Committee")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "riskCommitee_Comm_", size);
				}
				if(key.equalsIgnoreCase("Adequacy of Infrastructure")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "infra_Comm_", size);
				}
				
				//6to10
				if(key.equalsIgnoreCase("Settlement Processing")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "processing_Comm_", size);
				}
				if(key.equalsIgnoreCase("Scheme wise reconciliation of Holdings for each Pension Funds")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "holdings_Comm_", size);
				}
				if(key.equalsIgnoreCase("Dealing Procedure (Front office) for deals executed by Pension funds")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "deals_Comm_", size);
				}
				if(key.equalsIgnoreCase("Back office Procedure")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "office_Comm_", size);
				}
				if(key.equalsIgnoreCase("Safe Keeping")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "safe_Comm_", size);
				}
				
				//11to15
				if(key.equalsIgnoreCase("Asset Servicing")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "asset_Comm_", size);
				}
				if(key.equalsIgnoreCase("Reporting to Pension Funds/NPS Trust")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "reportingToPF_Comm_", size);
				}
				if(key.equalsIgnoreCase("Reporting Pension Funds / NPS Trust")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "reportingToNps_Comm_", size);
				}
				if(key.equalsIgnoreCase("Accounting")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "accounting_Comm_", size);
				}
				if(key.equalsIgnoreCase("Valuation of Asset Under Custody (AUC)")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "auc_Comm_", size);
				}
				
				//16to19
				if(key.equalsIgnoreCase("Disclosure")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "disclosure_Comm_", size);
				}
				if(key.equalsIgnoreCase("Periodical returns to Authority/ NPS Trust")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "periodical_Comm_", size);
				}
				if(key.equalsIgnoreCase("Compliances")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "compliances_Comm_", size);
				}
				if(key.equalsIgnoreCase("Internal Audit/ Custodian/ Scheme Audit")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "internalAudit_Comm_", size);
				}
				
				//In end
				if(Validator.isNotNull(jsonObject_three)) {
					//data save
//					_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
					jsonObject_two.put(key, jsonObject_three);
				}
				
			}
			
			try {
				custIARScrutinyLocalService.saveCustodianIARScrutiny(themeDisplay.getUser().getScreenName(), 0, 
						themeDisplay.getUserId(), iar_rem,  jsonObject_two.toString(),
						new Date(), themeDisplay.getUserId(), reportUploadLogId);
			} catch (Exception e) {
				isError = true;
				_log.error("Exception in Persisting the table data one  : "+e.getMessage());
			}
			
			if(!isError) {
				writer.print("Success");
			}else {
				writer.print("error");
			}

		}else {
			writer.print("You have loged out.");
		}
	}
	
	private JSONObject getJsonData(ResourceRequest resourceRequest,String comments, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String observe = ParamUtil.getString(resourceRequest, comments+String.valueOf(i));
			jsonObject.put(comments+i, observe);
		}
		
		return jsonObject;
		
	}
	
	@Reference
	CustIARScrutinyLocalService custIARScrutinyLocalService;

}
