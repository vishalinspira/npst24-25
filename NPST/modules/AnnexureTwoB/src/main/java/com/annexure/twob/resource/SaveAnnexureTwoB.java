package com.annexure.twob.resource;

import com.annexure.twob.constants.AnnexureTwoBPortletKeys;
import com.daily.average.service.service.AnnexureTwoBLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { 
		"javax.portlet.name=" +  AnnexureTwoBPortletKeys.ANNEXURETWOB,
		"mvc.command.name=/AnnexureTwoBForm/save" 
	}, service = MVCResourceCommand.class
)
public class SaveAnnexureTwoB implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		System.err.println("SaveAnnexureOneB.serveResource()");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		ArrayList<String> list = new ArrayList<String>();
		boolean isError = false;
		
		String file_number = ParamUtil.getString(resourceRequest, "file_number");
		String to_name = ParamUtil.getString(resourceRequest, "to_name");
		String to_address = ParamUtil.getString(resourceRequest, "to_address");
		String ended_date = ParamUtil.getString(resourceRequest, "ended_date");
		String by_the_custodian = ParamUtil.getString(resourceRequest, "by_the_custodian");
		String quarter = ParamUtil.getString(resourceRequest, "quarter");
		
		try {
			for(int i=1; i<=13; i++) {
				
				list.add(GetterUtil.getString(ParamUtil.getString(resourceRequest, "remarks"+String.valueOf(i)), "NA"));
				list.add(GetterUtil.getString(ParamUtil.getString(resourceRequest, "nps_trust_observation"+String.valueOf(i)), "NA"));
			}
		} catch (Exception e) {
			isError = true;
			_log.error("Exception in adding data to list : "+e.getMessage());
		}

		System.err.println(" First half => "+file_number+" "+to_name+" "+to_address+" "+ended_date+" "+by_the_custodian+" "+quarter);
		System.err.println(" Secound half => "+list.toString());
		System.err.println(" List size => "+list.size());
		
		if(!isError && !list.isEmpty() && list.size() == 26) {
			
			try {
				AnnexureTwoBLocalServiceUtil.addAnnexureTwoB(file_number, to_name, to_address, ended_date, by_the_custodian, quarter, new Date(), themeDisplay.getUserId(), list);
			} catch (Exception e) {
				isError = true;
				_log.error("Exception in persisting the annexure-two-B data : "+e.getMessage());
			}
		}
		
		
		PrintWriter out = null;
		if(!isError) {
			try {
				out = resourceResponse.getWriter();
				out.print("success");
			} catch (Exception e) {
				_log.error(e.getMessage());
			}
		}else {
			try {
				out = resourceResponse.getWriter();
				out.print("error");
			} catch (Exception e) {
				_log.error(e.getMessage());
			}
		}
		
		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(SaveAnnexureTwoB.class.getName());
}
