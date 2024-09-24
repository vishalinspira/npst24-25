package nps.email.service.impl;



import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.UriBuilder;

import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.NameMappingConstants;
import nps.email.api.api.NpsSmsApi;
import nps.email.api.props.PropValues;
import nps.email.service.client.RestClient;

@Component(immediate = true, property = {

}, service = NpsSmsApi.class)

public class NpsSmsServiceImpl extends RestClient implements NpsSmsApi{

	

	@Override
	public void sendFormSubmitSuccessfulSMS(String mobileNo, String name, String message, String contentId) {
		 

	        String url = UriBuilder.fromPath(PropValues.ENDPOINT_SMS_GATEWAY)
	        		 .queryParam("username", PropValues.SMS_GATEWAY_USERNAME)
	        		 .queryParam("password", PropValues.SMS_GATEWAY_PASSWORD)
	                 .queryParam("mobile", mobileNo)
	                 .queryParam("message", message)
	        		 .queryParam("senderid", PropValues.SMS_GATEWAY_SENDERID)
	                 //.queryParam("peid", PropValues.SMS_GATEWAY_PEID)
	                 .queryParam("contentid", contentId).build().toString();
	         _log.debug("*******************************************sendFormSubmitSuccessfulSMS url =>" + url);
	        
	         //Testing for comment UAT
	       sendSms(url);
		
	}
	
	public void sendSMS1(String mobileNo, String name,String role_name,long reportUploadlogId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String message = PropValues.BODY_SMS1;
		//String message = "";
		String uploadedBY="";
		String dueDate="";
		String reportName="";
		try {
			ReportUploadLog reportUploadLog =ReportUploadLogLocalServiceUtil.fetchReportUploadLog(reportUploadlogId);
			uploadedBY=reportUploadLog.getIntermediaryname();
			try {
			ReportMaster reportMaster= ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLog.getReportMasterId());
			if(Validator.isNull(uploadedBY) || uploadedBY == "") {
				
				uploadedBY=reportMaster.getUploaderRole();
				
		}
			reportName=reportMaster.getReportName();
			reportName=reportName.substring(0, 29);
			}catch (Exception e) {
				_log.error(e.getMessage());
			}
			dueDate=dateFormat.format(reportUploadLog.getReportDate());
			uploadedBY=uploadedBY.replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW);
			role_name=role_name.replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW);
		}catch (Exception e) {
			_log.error(e);
		}
		
		//message= "Dear " +role_name+", report uploaded by " +uploadedBY+" for report date "+dueDate+" has been sent for Review. Request you to Kindly check and approve. Regards, NPS Trust.";
		message = message.replace("{#var1#}",role_name);
		message = message.replace("{#var2#}",reportName);
		message = message.replace("{#var3#}",uploadedBY);
		message = message.replace("{#var4#}",dueDate);
		_log.info("uploaded message : "+message);
		// message = message.replace("{#var1#}{#var2#}", name);
	     String contentId = PropValues.BODY_SMS1_CONTENTID;
	   //Testing for comment UAT
	    sendFormSubmitSuccessfulSMS(mobileNo, name, message, contentId);
	}

	public void sendSMS2(String mobileNo, String name,String role_name,long reportUploadlogId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String message = PropValues.BODY_SMS2;
		// message = message.replace("{#var1#}{#var2#}", name);
	     String contentId = PropValues.BODY_SMS2_CONTENTID;
			String uploadedBY="";
			String dueDate="";
			String reportName="";
			try {
				ReportUploadLog reportUploadLog =ReportUploadLogLocalServiceUtil.fetchReportUploadLog(reportUploadlogId);
				uploadedBY=reportUploadLog.getIntermediaryname();
				
				
				try {
					ReportMaster reportMaster= ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLog.getReportMasterId());
					if(Validator.isNull(uploadedBY) || uploadedBY == "") {
						
						uploadedBY=reportMaster.getUploaderRole();
						
				}
					reportName=reportMaster.getReportName();
					reportName=reportName.substring(0, 29);
					}catch (Exception e) {
						_log.error(e.getMessage());
					}
	
				dueDate=dateFormat.format(reportUploadLog.getReportDate());
				uploadedBY=uploadedBY.replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW);
				role_name=role_name.replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW);
			}catch (Exception e) {
				_log.error(e);
			}
			
			//message= "Dear " +role_name+", report uploaded by " +uploadedBY+" for report date "+dueDate+" has been Rejected. Request you to Kindly review and re-upload. Regards, NPS Trust.";
			message = message.replace("{#var1#}",role_name);
			message = message.replace("{#var2#}",reportName);
			message = message.replace("{#var3#}",uploadedBY);
			message = message.replace("{#var4#}",dueDate);
			_log.info("rejected message : "+message);
	   //Testing for comment UAT
	     sendFormSubmitSuccessfulSMS(mobileNo, name, message, contentId);
	}
	
	@Override
	public void sendRejectAndReviewSMS(long companyId, String role_name,String status, String name,long reportUploadlogId) {
		String mobileNo = "9851587480";
		
		
		List<Phone> phones = getMobList(companyId, role_name);
		for (Phone phone : phones) {
			_log.info(phone);
			if(status.trim().equalsIgnoreCase("Review")) {
				sendSMS1(phone.getNumber(), name,role_name,reportUploadlogId);
			}else if(status.trim().equalsIgnoreCase("Reject")) {
				sendSMS2(phone.getNumber(), name,role_name,reportUploadlogId);
			}
		}
	}
	
	public List<Phone> getMobList(long companyId, String role_name) {
		//List<String> emailList = new ArrayList<>();
		List<Phone> phones = new ArrayList<Phone>();
		try {
			Role role = RoleLocalServiceUtil.getRole(companyId, role_name);
			
			List<User> list_of_users = UserLocalServiceUtil.getRoleUsers(role.getRoleId());
			list_of_users=list_of_users.stream().filter(user->user.isActive()).map(user->user).collect(Collectors.toList());
			for(User lr_user : list_of_users) {
				phones.addAll(lr_user.getPhones());
			}
		} catch (PortalException e) {
			_log.error(e);
		}
		return phones;
	}
	 private static final Log _log = LogFactoryUtil.getLog(NpsSmsServiceImpl.class);

	
}
