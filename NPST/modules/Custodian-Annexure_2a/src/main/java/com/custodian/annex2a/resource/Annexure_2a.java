package com.custodian.annex2a.resource;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Custodian.Annexure_2a.constants.CustodianAnnexure_2aPortletKeys;
import compliance.service.model.CustodianAnnex_2a;
import compliance.service.service.CustodianAnnex_2aLocalService;

@Component(property = { 
		"javax.portlet.name=" + CustodianAnnexure_2aPortletKeys.CUSTODIANANNEXURE_2A,
		"mvc.command.name=" + CustodianAnnexure_2aPortletKeys.annex_2a, 
		}, 
service = MVCResourceCommand.class)

public class Annexure_2a implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(Annexure_2a.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		annexTwoA(resourceRequest, resourceResponse);
		
		return false;
	}
	
	public void annexTwoA(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside annex 2a");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String[] description = ParamUtil.getParameterValues(resourceRequest, "description[]");
		String[] remarks = ParamUtil.getParameterValues(resourceRequest, "remarks[]");
		
		for(int i=0; i<rowCount; i++) {
			//_log.info("inside for");
			
			_log.info("description " + description[i] + " remarks " + remarks[i]);
			
			CustodianAnnex_2a annex_2a_Info = custodianAnnex_2aLocalService.addAnnexTwoA(description[i], remarks[i], 
					themeDisplay.getUserId(), new Date());

			_log.info("annex_2a_Info " + annex_2a_Info.toString());
			
		}
		
		//_log.info("exit for");
		
	}
	
	@Reference
	CustodianAnnex_2aLocalService custodianAnnex_2aLocalService;
	

}
