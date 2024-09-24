package com.sesnonunanimous.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import SES.NonUnanimousVoting.constants.SESNonUnanimousVotingPortletKeys;
import compliance.service.model.SESNonUnanimous;
import compliance.service.service.ClosingBalLocalService;
import compliance.service.service.SESNonUnanimousLocalService;

@Component(property = { 
		"javax.portlet.name=" + SESNonUnanimousVotingPortletKeys.SESNONUNANIMOUSVOTING,
		"mvc.command.name=" + SESNonUnanimousVotingPortletKeys.sesNonUnanimous, 
		}, 
service = MVCResourceCommand.class)

public class SESNonUnanimousVoting implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(SESNonUnanimousVoting.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("Serve Resource method");
		
		try {
			sesNonUnanimousInfo(resourceRequest, resourceResponse);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		return false;
	}
	
	public void sesNonUnanimousInfo(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortalException {
		
		_log.info("Inside sesNonUnanimous");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowcount");
		_log.info("rowcount " + rowCount);
		
		//int countRows = ParamUtil.getInteger(resourceRequest, "countrows");
		//_log.info("countRows " + countRows);
		
		//List<SESNonUnanimous> sesNonUnanimousList = new ArrayList<SESNonUnanimous>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		//if(rowCount == 0) {
			String companyName_0 = ParamUtil.getString(resourceRequest, "company_name_0");
			Date meetingDate_0 = ParamUtil.getDate(resourceRequest, "meeting_date_0", dateFormat);
			String resolution_0 = ParamUtil.getString(resourceRequest, "resolution_0");
			String votedFor_0 = ParamUtil.getString(resourceRequest, "voted_for_0");
			String votedAbstain_0 = ParamUtil.getString(resourceRequest, "voted_abstain_0");
			String votedAgainst_0 = ParamUtil.getString(resourceRequest, "voted_against_0");
			String finalVote_0 = ParamUtil.getString(resourceRequest, "final_vote_0");
			
			_log.info("companyName " + companyName_0 + " meetingDate " + meetingDate_0 + " resolution " + resolution_0);
			_log.info("votedFor " + votedFor_0 + " votedAbstain " + votedAbstain_0 + " votedAgainst " + votedAgainst_0);
			_log.info("finalVote " + finalVote_0);
			
			SESNonUnanimous sesNonUnanimous = sesNonUnanimousLocalService.addSesUnanimous(companyName_0, meetingDate_0, resolution_0, 
					votedFor_0, votedAbstain_0, votedAgainst_0, finalVote_0);
			
			sesNonUnanimous.setCreatedon(new Date());
			sesNonUnanimous.setCreatedby(themeDisplay.getUserId());
			
			sesNonUnanimousLocalService.updateSESNonUnanimous(sesNonUnanimous);
			
			_log.info("sesNonUnanimous " + sesNonUnanimous.toString());
			
		//}
		//else if(rowCount > 0) {
			for(int i=1; i<=rowCount; i++) {
				_log.info("inside for");
				String companyName = ParamUtil.getString(resourceRequest, "company_name_"+String.valueOf(i));
				Date meetingDate = ParamUtil.getDate(resourceRequest, "meeting_date_"+String.valueOf(i), dateFormat);
				String resolution = ParamUtil.getString(resourceRequest, "resolution_"+String.valueOf(i));
				String votedFor = ParamUtil.getString(resourceRequest, "voted_for_"+String.valueOf(i));
				String votedAbstain = ParamUtil.getString(resourceRequest, "voted_abstain_"+String.valueOf(i));
				String votedAgainst = ParamUtil.getString(resourceRequest, "voted_against_"+String.valueOf(i));
				String finalVote = ParamUtil.getString(resourceRequest, "final_vote_"+String.valueOf(i));
				
				//ServiceContext serviceContext = ServiceContextFactory.getInstance(SESNonUnanimousVoting.class.getName(), resourceRequest);
				
				//serviceContext.setCreateDate(new Date());
				//serviceContext.setUserId(themeDisplay.getUserId());
				
				_log.info("companyName " + companyName + " meetingDate " + meetingDate + " resolution " + resolution);
				_log.info("votedFor " + votedFor + " votedAbstain " + votedAbstain + " votedAgainst " + votedAgainst);
				_log.info("finalVote " + finalVote);
				
				SESNonUnanimous sesNonUnanimousInfo = sesNonUnanimousLocalService.addSesUnanimous(companyName, meetingDate, resolution, 
						votedFor, votedAbstain, votedAgainst, finalVote);
				
				sesNonUnanimousInfo.setCreatedon(new Date());
				sesNonUnanimousInfo.setCreatedby(themeDisplay.getUserId());
				
				sesNonUnanimousLocalService.updateSESNonUnanimous(sesNonUnanimousInfo);

				_log.info("sesNonUnanimous " + sesNonUnanimousInfo.toString());
				
			}
		//}

		_log.info("exit for");

	}
	
	@Reference
	SESNonUnanimousLocalService sesNonUnanimousLocalService;

}
