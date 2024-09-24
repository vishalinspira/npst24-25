package com.unanimous.report.resource;

import com.daily.average.service.service.NonUnanimousReportTableOneLocalServiceUtil;
import com.daily.average.service.service.NonUnanimousReportTableTwoLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.unanimous.report.constants.Non_unanimous_reportPortletKeys;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { 
		"javax.portlet.name=" +  Non_unanimous_reportPortletKeys.NON_UNANIMOUS_REPORT,
		"mvc.command.name=/nonUnanimousReport/save" 
	}, service = MVCResourceCommand.class
)
public class SaveNonUnanimousReport implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		System.err.println("SaveNonUnanimousReport.SaveNonUnanimousReport()");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String rowcount_first_table = ParamUtil.getString(resourceRequest, "rowcount_first_table");
		String rowcount_secound_table = ParamUtil.getString(resourceRequest, "rowcount_secound_table");
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		boolean isError = false;
		
		if(!rowcount_first_table.trim().equals("") || rowcount_first_table != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_first_table.trim()); i++) {
				
				String name_of_the_pfm = ParamUtil.getString(resourceRequest, "name_of_the_pfm"+String.valueOf(i));
				long no_of_resolutions = ParamUtil.getLong(resourceRequest, "no_of_resolutions"+String.valueOf(i));
				long voted_for = ParamUtil.getLong(resourceRequest, "voted_for"+String.valueOf(i));
				long abstained = ParamUtil.getLong(resourceRequest, "abstained"+String.valueOf(i));
				long voted_against = ParamUtil.getLong(resourceRequest, "voted_against"+String.valueOf(i));
				
				try {
					NonUnanimousReportTableOneLocalServiceUtil.addNonUnanimousReportTableOne(name_of_the_pfm, no_of_resolutions, voted_for, abstained, voted_against, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Persisting the table data one  : "+e.getMessage());
				}
			}
		}
				
		if(!rowcount_secound_table.trim().equals("") || rowcount_secound_table != null) {
			for(int j = 1; j <= Long.parseLong(rowcount_secound_table.trim()); j++) {
				
				String name_of_the_company = ParamUtil.getString(resourceRequest, "name_of_the_company"+String.valueOf(j));
				Date meeting_date = ParamUtil.getDate(resourceRequest, "meeting_date"+String.valueOf(j),dateFormat);
				String resolution = ParamUtil.getString(resourceRequest, "resolution"+String.valueOf(j));
				String ses_recommendation = ParamUtil.getString(resourceRequest, "ses_recommendation"+String.valueOf(j));
				String ses_reason = ParamUtil.getString(resourceRequest, "ses_reason"+String.valueOf(j));
				String pfs_voted_for = ParamUtil.getString(resourceRequest, "pfs_voted_for"+String.valueOf(j));
				String pfs_voted_abstain = ParamUtil.getString(resourceRequest, "pfs_voted_abstain"+String.valueOf(j));
				String pfs_voted_against = ParamUtil.getString(resourceRequest, "pfs_voted_against"+String.valueOf(j));
				String final_vote = ParamUtil.getString(resourceRequest, "final_vote"+String.valueOf(j));
				String pfs_reason = ParamUtil.getString(resourceRequest, "pfs_reason"+String.valueOf(j));
				
				try {
					NonUnanimousReportTableTwoLocalServiceUtil.addNonUnanimousReportTableTwo(name_of_the_company, meeting_date, resolution, ses_recommendation, ses_reason, pfs_voted_for, pfs_voted_abstain, pfs_voted_against, final_vote, pfs_reason, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Persisting the table data two  : "+e.getMessage());
				}
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
	
	private static Log _log = LogFactoryUtil.getLog(SaveNonUnanimousReport.class.getName());

}
