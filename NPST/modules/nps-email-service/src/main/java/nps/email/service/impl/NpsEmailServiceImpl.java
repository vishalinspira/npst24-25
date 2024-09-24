package nps.email.service.impl;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.NameMappingConstants;
import nps.email.api.api.NpsEmailApi;

@Component(immediate = true, property = {

}, service = NpsEmailApi.class)

public class NpsEmailServiceImpl implements NpsEmailApi {
	
	public NpsEmailServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sendEmail(String from, String to, String subject, String body) {
		try {

			MailMessage mailMessage = new MailMessage();

			try {

				mailMessage.setFrom(new InternetAddress(from));
				mailMessage.setTo(new InternetAddress(to));
				mailMessage.setBCC(new InternetAddress("noreply@npstrust.net"));
				//mailMessage.setBCC(new InternetAddress("himanshu.rai@inspiraenterprise.com"));
				//mailMessage.setBCC(new InternetAddress("ajay.kumar@inspiraenterprise.com"));
				
				mailMessage.setSubject(subject);
				mailMessage.setBody(body);
				mailMessage.setHTMLFormat(true);
				//comment for UAT testing
				MailServiceUtil.sendEmail(mailMessage);
				_log.info("Mail sent to user at ::=> " + to);
				//_log.info("Mail sent to bcc at ::=> noreply@npstrust.net");

			} catch (AddressException e1) {
				_log.error("Mail sent to 'add' 'lp' User failed ::", e1);
			}
		} catch (Exception e) {
			_log.error("Email notification could not be sent", e);
		}

	}

//	@Override
//	public String sendOTP(String emailAddress) {
//		String body = "<p>Hi,</p>" 
//				+ "<p>Form submited successfully</p>" 
//				+ "<p>Regards,<br/>Priyanka</p>";
//		String subject = "Account Statement form submited";
//		sendEmail("priyankkamld@gmail.com", emailAddress, subject, body);
//
//		return body;
//	}
	
	@Override
	public String sendOTP(String emailAddress) {
		String body = "<p>Hi,</p>" 
				+ "<p>Form submited successfully</p>" 
				+ "<p>Regards,<br/>Deepak S</p>";
		String subject = "Form 1 (A) Report of transaction in securities by KP form submited";
		sendEmail("noreply@npstrust.net", emailAddress, subject, body);

		return body;
	}

	private static final Log _log = LogFactoryUtil.getLog(NpsEmailServiceImpl.class);
	
	@Override
	public void sendRejectEmail(long companyId, String role_name, String status, String reportName,long reportUploadlogId) {
		
		_log.info("companyId " + companyId + " role_name " + role_name+" status " + status + " reportName " + reportName);
		//_log.info("status " + status + " reportName " + reportName);
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			
			//String emailAddress = "piyush@yopmail.com";
			
			String body ="";
			String subject = reportName+" form submited";
			String uploadedBY="";
			String dueDate="";
			try {
				ReportUploadLog reportUploadLog =ReportUploadLogLocalServiceUtil.fetchReportUploadLog(reportUploadlogId);
				uploadedBY=reportUploadLog.getIntermediaryname();
				if(Validator.isNull(uploadedBY) || uploadedBY == "") {
					ReportMaster reportMaster= ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLog.getReportMasterId());
					uploadedBY=reportMaster.getUploaderRole();
			}
				dueDate=dateFormat.format(reportUploadLog.getReportDate());
				uploadedBY=uploadedBY.replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW);
				role_name=role_name.replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW);
			}catch (Exception e) {
				_log.error(e);
			}
				//	dateFormat.format(null);
			if(status.trim().equalsIgnoreCase("Review")) {
				body = "<p>Dear "+role_name+" ,</p>" 
						+ "<p>"+reportName+" report uploaded by "+uploadedBY+" for report date "+dueDate+" has been sent for Review.</p>" 
						+ "<p>Request you to Kindly check and approve.</p>"
						+"<p>Regards,</p>"
						+"<br/>NPS Trust.</p>";
			}else if(status.trim().equalsIgnoreCase("Reject")) {
				if(role_name.equalsIgnoreCase("INSPIRA")) {
					try {
					body = "<p>Dear "+role_name+" ,</p>" 
							+ "<p>"+reportName+" report has been Rejected for the reportuploadlogId " +reportUploadlogId+"</p>" 
							+ "<p>Request you to Kindly review and re-upload.</p>"
							+"<p>Regards,</p>"
							+"<br/>NPS Trust.</p>";
					}catch (Exception e) {
						_log.error(e);
					}
				}else {
				body = "<p>Dear "+role_name+" ,</p>" 
						+ "<p>"+reportName+" report uploaded by " +uploadedBY+" for report date " +dueDate+" has been Rejected.</p>" 
						+ "<p>Request you to Kindly review and re-upload.</p>"
						+"<p>Regards,</p>"
						+"<br/>NPS Trust.</p>";
				}
			}
			
			List<String> emailList = getEmail(companyId, role_name);
			for (Iterator<String> iterator = emailList.iterator(); iterator.hasNext();) {
				
				String email = (String) iterator.next();
				sendEmail("noreply@npstrust.net", email, subject, body);
			}
			//sendEmail("noreply@npstrust.net", emailAddress, subject, body);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		_log.info("######################### mail sent ##################################");
	}
	
	
	@Override
	public void sendManpowerEmail(long companyId, String roleName,String pfmName, String userName, String entityName, String action) {

		_log.info("companyId " + companyId + " role_name " + roleName +" pfmName "+pfmName +" userName "+userName+" entityName "+entityName+" action "+action);
		try {
			//String emailAddress = "piyush@yopmail.com";
			
			String body ="";
			String subject = "Manpower Detail";
			
				body = "<p>Dear "+roleName+" ,</p>" 
						+ "<p>"+entityName+" has been "+action+" related to "+userName+" for "+pfmName+" pension fund.</p>" 
						+ "<p>Request you to Kindly check.</p>"
						+"<p>Regards,</p>"
						+"<br/>NPS Trust.</p>";
	
			
			List<String> emailList = getEmail(companyId, roleName);
			for (Iterator<String> iterator = emailList.iterator(); iterator.hasNext();) {
				String email = (String) iterator.next();
				sendEmail("noreply@npstrust.net", email, subject, body);
			}
			//sendEmail("noreply@npstrust.net", emailAddress, subject, body);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		_log.info("######################### mail sent ##################################");
		
	}
	
	private List<String> getEmail(long companyId, String role_name) {
		List<String> emailList = new ArrayList<>();
		try {
			Role role = RoleLocalServiceUtil.getRole(companyId, role_name);
			
			List<User> list_of_users = UserLocalServiceUtil.getRoleUsers(role.getRoleId());
			list_of_users=list_of_users.stream().filter(user->user.isActive()).map(user->user).collect(Collectors.toList());
			for(User lr_user : list_of_users) {
				emailList.add(lr_user.getEmailAddress().trim());
				//sendOTP(lr_user.getEmailAddress().trim());
			}
			
		} catch (PortalException e) {
			_log.error(e);
		}
		return emailList;
	}
	
	/**
	 * @param companyId
	 * @param role_name
	 * @param status
	 * @param reportName
	 */
	@Override
	public void sendAlertEmail(long companyId, String role_name, String status, String reportName) {
		_log.info("companyId " + companyId + " role_name " + role_name);
		_log.info("status " + status + " reportName " + reportName);
		try {
			//String emailAddress = "piyush@yopmail.com";
			
			String body ="";
			String subject = reportName+" form submited";
			
			if(status.trim().equalsIgnoreCase("Intimation")) {
				body = "<p>Dear "+role_name+" ,</p>" 
						+ "<p>"+reportName+" report is pending for submission to NPS Trust.</p>" 
						+ "<p>Please submit the report on or before the due date.</p>"
						+"<p>Regards,</p>"
						+"<br/>NPS Trust.</p>";
			}else if(status.trim().equalsIgnoreCase("Alert")) {
				body = "<p>Dear "+role_name+" ,</p>" 
						+ "<p>"+reportName+" report is due for submission to NPS Trust.</p>" 
						+ "<p>Please submit the report.</p>"
						+"<p>Regards,</p>"
						+"<br/>NPS Trust.</p>";
			}
			
			List<String> emailList = getEmail(companyId, role_name);
			for (Iterator<String> iterator = emailList.iterator(); iterator.hasNext();) {
				String email = (String) iterator.next();
				//sendEmail("noreply@npstrust.net", emailAddress, subject, body);
			}
			//sendEmail("noreply@npstrust.net", emailAddress, subject, body);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		_log.info("######################### mail sent ##################################");
		
	}


}
