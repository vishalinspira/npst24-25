package com.annual.compliance.report.resource;

import com.annual.compliance.report.constants.AnnualComplianceReportPortletKeys;
import com.daily.average.service.service.AnnualComplianceReportLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + AnnualComplianceReportPortletKeys.ANNUALCOMPLIANCEREPORT,
				"mvc.command.name=/AnnualCoplianceReport/save",
		},
		service = MVCResourceCommand.class
		)
public class SaveAnnualComplianceReport implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		System.err.println("SaveAnnualComplianceReport.serveResource()");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isError = false;
		
		String rowcount_one = ParamUtil.getString(resourceRequest, "rowcount_one");
		String rowcount_two = ParamUtil.getString(resourceRequest, "rowcount_two");
		String rowcount_three = ParamUtil.getString(resourceRequest, "rowcount_three");
		String rowcount_four = ParamUtil.getString(resourceRequest, "rowcount_four");
		String rowcount_five = ParamUtil.getString(resourceRequest, "rowcount_five");
		String rowcount_six = ParamUtil.getString(resourceRequest, "rowcount_six");
		String rowcount_seven = ParamUtil.getString(resourceRequest, "rowcount_seven");
		
		if(!rowcount_one.trim().equals("") && rowcount_one != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_one.trim()); i++) {
				String detailsOne = ParamUtil.getString(resourceRequest, "detailsOne"+String.valueOf(i));
				String information_submittedOne = ParamUtil.getString(resourceRequest, "information_submittedOne"+String.valueOf(i));
				String commentsOne = ParamUtil.getString(resourceRequest, "commentsOne"+String.valueOf(i));
				
				try {
					AnnualComplianceReportLocalServiceUtil.addAnnualComplianceReport("SBI Pension Funds Pvt. Ltd", detailsOne, information_submittedOne, commentsOne, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Annual Compliance Report SBI add method : "+e.getMessage());
				}
			}
		}
		
		if(!rowcount_two.trim().equals("") && rowcount_two != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_two.trim()); i++) {
				String detailsTwo = ParamUtil.getString(resourceRequest, "detailsTwo"+String.valueOf(i));
				String information_submittedTwo = ParamUtil.getString(resourceRequest, "information_submittedTwo"+String.valueOf(i));
				String commentsTwo = ParamUtil.getString(resourceRequest, "commentsTwo"+String.valueOf(i));
				
				try {
					AnnualComplianceReportLocalServiceUtil.addAnnualComplianceReport("UTI Retirement Solutions Ltd", detailsTwo, information_submittedTwo, commentsTwo, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Annual Compliance Report UTI add method : "+e.getMessage());
				}
			}
		}
		
		if(!rowcount_three.trim().equals("") && rowcount_three != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_three.trim()); i++) {
				String detailsThree = ParamUtil.getString(resourceRequest, "detailsThree"+String.valueOf(i));
				String information_submittedThree = ParamUtil.getString(resourceRequest, "information_submittedThree"+String.valueOf(i));
				String commentsThree = ParamUtil.getString(resourceRequest, "commentsThree"+String.valueOf(i));
				
				try {
					AnnualComplianceReportLocalServiceUtil.addAnnualComplianceReport("LIC Pension Fund", detailsThree, information_submittedThree, commentsThree, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Annual Compliance Report LIC add method : "+e.getMessage());
				}
			}
		}
		
		if(!rowcount_four.trim().equals("") && rowcount_four != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_four.trim()); i++) {
				String detailsFour = ParamUtil.getString(resourceRequest, "detailsFour"+String.valueOf(i));
				String information_submittedFour = ParamUtil.getString(resourceRequest, "information_submittedFour"+String.valueOf(i));
				String commentsFour = ParamUtil.getString(resourceRequest, "commentsFour"+String.valueOf(i));
				
				try {
					//AnnualComplianceReportLocalServiceUtil.addAnnualComplianceReport("HDFC Pension Management Company Limited", detailsFour, information_submittedFour, commentsFour, new Date(), themeDisplay.getUserId());
					AnnualComplianceReportLocalServiceUtil.addAnnualComplianceReport("HDFC Pension Fund Management Limited", detailsFour, information_submittedFour, commentsFour, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Annual Compliance Report HDFC add method : "+e.getMessage());
				}
			}
		}
		
		if(!rowcount_five.trim().equals("") && rowcount_five != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_five.trim()); i++) {
				String detailsFive = ParamUtil.getString(resourceRequest, "detailsFive"+String.valueOf(i));
				String information_submittedFive = ParamUtil.getString(resourceRequest, "information_submittedFive"+String.valueOf(i));
				String commentsFive = ParamUtil.getString(resourceRequest, "commentsFive"+String.valueOf(i));
				
				try {
					AnnualComplianceReportLocalServiceUtil.addAnnualComplianceReport("ICICI Prudential Pension Funds Management Co. Ltd", detailsFive, information_submittedFive, commentsFive, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Annual Compliance Report SBI add method : "+e.getMessage());
				}
			}
		}
		
		if(!rowcount_six.trim().equals("") && rowcount_six != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_six.trim()); i++) {
				String detailsSix = ParamUtil.getString(resourceRequest, "detailsSix"+String.valueOf(i));
				String information_submittedSix = ParamUtil.getString(resourceRequest, "information_submittedSix"+String.valueOf(i));
				String commentsSix = ParamUtil.getString(resourceRequest, "commentsSix"+String.valueOf(i));
				
				try {
					AnnualComplianceReportLocalServiceUtil.addAnnualComplianceReport("Aditya Birla Sun Life Pension Management Limited", detailsSix, information_submittedSix, commentsSix, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Annual Compliance Report Aditya add method : "+e.getMessage());
				}
			}
		}
		
		if(!rowcount_seven.trim().equals("") && rowcount_seven != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_seven.trim()); i++) {
				String detailsSeven = ParamUtil.getString(resourceRequest, "detailsSeven"+String.valueOf(i));
				String information_submittedSeven = ParamUtil.getString(resourceRequest, "information_submittedSeven"+String.valueOf(i));
				String commentsSeven = ParamUtil.getString(resourceRequest, "commentsSeven"+String.valueOf(i));
				
				try {
					AnnualComplianceReportLocalServiceUtil.addAnnualComplianceReport("Kotak Mahindra Pension Fund Ltd", detailsSeven, information_submittedSeven, commentsSeven, new Date(), themeDisplay.getUserId());
				} catch (Exception e) {
					isError = true;
					_log.error("Exception in Annual Compliance Report Kotak add method : "+e.getMessage());
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
	
	private static Log _log = LogFactoryUtil.getLog(SaveAnnualComplianceReport.class.getName());

}
