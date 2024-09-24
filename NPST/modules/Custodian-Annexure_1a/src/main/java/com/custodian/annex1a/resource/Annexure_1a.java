package com.custodian.annex1a.resource;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Custodian.Annexure_1a.constants.CustodianAnnexure_1aPortletKeys;
import compliance.service.model.CustodianAnnex_1a;
import compliance.service.model.SESNonUnanimous;
import compliance.service.service.CustodianAnnex_1aLocalService;

@Component(property = { 
		"javax.portlet.name=" + CustodianAnnexure_1aPortletKeys.CUSTODIANANNEXURE_1A,
		"mvc.command.name=" + CustodianAnnexure_1aPortletKeys.annex_1a, 
		}, 
service = MVCResourceCommand.class)

public class Annexure_1a implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(Annexure_1a.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("Serve Resource method");
		
		annexOneA(resourceRequest, resourceResponse);
		
		return false;
	}
	
	public void annexOneA(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside annex 1a");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowcount");
		_log.info("rowcount " + rowCount);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		Date custodyDate = ParamUtil.getDate(resourceRequest, "custodyDate", dateFormat);
		
		_log.info("custodyDate " + custodyDate);
		
		String pensionName_0 = ParamUtil.getString(resourceRequest, "pension_name_0");
		double aucFaceValue_0 = ParamUtil.getDouble(resourceRequest, "auc_face_value_0");
		double aucMarketValue_0 = ParamUtil.getDouble(resourceRequest, "auc_market_value_0");
		double aum_0 = ParamUtil.getDouble(resourceRequest, "aum_0");
		double percentOfAum_0 = ParamUtil.getDouble(resourceRequest, "percent_of_aum_0");
		double assetNotUnderCustody_0 = ParamUtil.getDouble(resourceRequest, "asset_not_under_custody_0");
		
		_log.info("pensionName_0 " + pensionName_0 + " aucFaceValue_0 " + aucFaceValue_0 + " aucMarketValue_0 " + aucMarketValue_0);
		_log.info("aum_0 " + aum_0 + " percentOfAum_0 " + percentOfAum_0 + " assetNotUnderCustody_0 " + assetNotUnderCustody_0);
		
		CustodianAnnex_1a annex_1a = custodianAnnex_1aLocalService.addAnnexOneA(custodyDate, pensionName_0, aucFaceValue_0, 
				aucMarketValue_0, aum_0, percentOfAum_0, assetNotUnderCustody_0, themeDisplay.getUserId(), new Date());
		
		_log.info("annex_1a " + annex_1a.toString());
		
		for(int i=1; i<=rowCount; i++) {
			//_log.info("inside for");
			String pensionName = ParamUtil.getString(resourceRequest, "pension_name_"+String.valueOf(i));
			double aucFaceValue = ParamUtil.getDouble(resourceRequest, "auc_face_value_"+String.valueOf(i));
			double aucMarketValue = ParamUtil.getDouble(resourceRequest, "auc_market_value_"+String.valueOf(i));
			double aum = ParamUtil.getDouble(resourceRequest, "aum_"+String.valueOf(i));
			double percentOfAum = ParamUtil.getDouble(resourceRequest, "percent_of_aum_"+String.valueOf(i));
			double assetNotUnderCustody = ParamUtil.getDouble(resourceRequest, "asset_not_under_custody_"+String.valueOf(i));
			
			_log.info("pensionName " + pensionName + " aucFaceValue " + aucFaceValue + " aucMarketValue " + aucMarketValue);
			_log.info("aum " + aum + " percentOfAum " + percentOfAum + " assetNotUnderCustody " + assetNotUnderCustody);
			
			CustodianAnnex_1a annex_1a_Info = custodianAnnex_1aLocalService.addAnnexOneA(custodyDate, pensionName, aucFaceValue, 
					aucMarketValue, aum, percentOfAum, assetNotUnderCustody, themeDisplay.getUserId(), new Date());

			_log.info("annex_1a_Info " + annex_1a_Info.toString());
			
		}
		
	}
	
	@Reference
	CustodianAnnex_1aLocalService custodianAnnex_1aLocalService;

}
