package com.nps.manpower.command;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.nps.manpower.constants.ManpowerEmployeeFieldName;
import com.nps.manpower.constants.ManpowerFormPortletKeys;
import com.nps.manpower.model.ManpowerEmployee;
import com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil;

import java.io.File;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.NPSTRoleConstants;
import nps.email.api.api.NpsEmailApi;
import nps.email.service.impl.NpsEmailServiceImpl;
import npst.common.constant.NpstCommonConstant;
import npst.common.util.NpstCommonUtil;

@Component(immediate = true, property = {
		"javax.portlet.name="
				+ ManpowerFormPortletKeys.MANPOWERFORM,
		"mvc.command.name=saveManpowerEmployee",
		"mvc.command.name=saveManpowerDirector",
}, service = MVCActionCommand.class)
public class SaveManpowerActionCommand extends BaseMVCActionCommand{

	
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		ManpowerEmployee manpowerEmployee=null;
		try {
			ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
			
		
		long manpowerEmployeeId=ParamUtil.getLong(actionRequest, ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
		String designation =ParamUtil.getString(actionRequest,ManpowerEmployeeFieldName.DESIGNATION );
		String name =ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.NAME);
		//Date appointmentDate=ParamUtil.getDate(actionRequest, ManpowerEmployeeFieldName.APPOINTMENTDATE, dateFormat);
		Date appointmentDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.APPOINTMENTDATE));
		String contactNo=ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.CONTACT_NO);
		String email =ParamUtil.getString(actionRequest,ManpowerEmployeeFieldName.EMAIL );
		String qualification=ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.QUALIFICATION);
		//String experience=ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.EXPERIENCE);
		String experience=ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.YEAR)+StringPool.COLON+ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.MONTH);
		//String experience="";
		String deputation=ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.DEPUTATION);
		String linkedinId=ParamUtil.getString(actionRequest,ManpowerEmployeeFieldName.LINKEDINID );
		Date approvingAppointmentDate =NpstCommonUtil.getDate(ParamUtil.getString(actionRequest,ManpowerEmployeeFieldName.APPROVING_APPOINTMENT_DATE));
		int committeeMembershipType=ParamUtil.getInteger(actionRequest, ManpowerEmployeeFieldName.COMMITTEE_MEMBERSHIP_TYPE);
		String din =ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.DIN);
		String directorType=ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.DIRECTOR_TYPE);
		String membershipType=ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.MEMBERSHIP_TYPE);
		String dependency=ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.DEPENDENCY);
		int status=ParamUtil.getInteger(actionRequest, ManpowerEmployeeFieldName.STATUS);
		int isDirector=ParamUtil.getInteger(actionRequest, ManpowerEmployeeFieldName.IS_DIRECTOR);
		logger.info("action is Director   "+isDirector);
		
//		Date fromDate=ManpowerUtil.getDate(ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.FROM_DATE));
//		Date toDate=ManpowerUtil.getDate(ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.TO_DATE));
		Date resignationDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ManpowerEmployeeFieldName.RESIGNATION_DATE));
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		File biodataFile=uploadRequest.getFile(ManpowerEmployeeFieldName.BIODATA_FILE_ID);
		File formMbpFile=uploadRequest.getFile(ManpowerEmployeeFieldName.FORM_MBP);
		long biodataFileId=0;
		long formMbpFileId=0;
		long parentFolderId=NpstCommonUtil.createOrGetFolderId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, ManpowerEmployeeFieldName.MANPOWER_FOLDER, serviceContext);
		long formMbpFolderId=NpstCommonUtil.createOrGetFolderId(parentFolderId, ManpowerEmployeeFieldName.FORM_MBP_FOLDER, serviceContext);
		long biodatFolderId=NpstCommonUtil.createOrGetFolderId(parentFolderId, ManpowerEmployeeFieldName.BIODATA_FOLDER, serviceContext);
		if(manpowerEmployeeId>0) {
			 manpowerEmployee=ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
			 committeeMembershipType=manpowerEmployee.getCommitteeMembershipType();
		}
		try {
		String mimeType = uploadRequest.getContentType(ManpowerEmployeeFieldName.FORM_MBP);
		formMbpFileId =NpstCommonUtil.fileUpload(formMbpFile, formMbpFolderId, mimeType, formMbpFile.getName(), serviceContext);
		}catch (Exception e) {
			if(manpowerEmployee!=null && !manpowerEmployee.equals(null)) {
			formMbpFileId=manpowerEmployee.getFormMbp();
			}
			logger.error("error while uploading file  "+e.getMessage());
		}
		try {
			String mimeType = uploadRequest.getContentType(ManpowerEmployeeFieldName.BIODATA_FILE_ID);
			 biodataFileId =NpstCommonUtil.fileUpload(biodataFile, biodatFolderId, mimeType, biodataFile.getName(), serviceContext);
			}catch (Exception e) {
				if(manpowerEmployee!=null && !manpowerEmployee.equals(null)) {
				biodataFileId=manpowerEmployee.getBiodataFileId();
				}
				logger.error("error while uploading file  "+e.getMessage());
			}
		String pfmName=NpstCommonUtil.getExpandoValue(serviceContext.getCompanyId(), serviceContext.getUserId(), User.class.getName(), NpstCommonConstant.PFM_NAME);
		manpowerEmployee= ManpowerEmployeeLocalServiceUtil.saveManpowerEmployee(manpowerEmployeeId, designation, name, appointmentDate, contactNo, email, qualification, experience, deputation, linkedinId, approvingAppointmentDate, biodataFileId, committeeMembershipType, din, directorType, membershipType, dependency, formMbpFileId, status, isDirector, resignationDate,pfmName);
		NpsEmailApi mail_api = new NpsEmailServiceImpl();
		String empType="";
		if(manpowerEmployee.getIsDirector()>0) {
			empType="Director Detail";
		}else {
			empType="Key Personnel Detail";
		}
		if(manpowerEmployeeId>0) {
			mail_api.sendManpowerEmail(serviceContext.getCompanyId(), NPSTRoleConstants.PFM_AM, manpowerEmployee.getPfmName(), manpowerEmployee.getName(), empType, "updated");
		}else {
			mail_api.sendManpowerEmail(serviceContext.getCompanyId(), NPSTRoleConstants.PFM_AM, manpowerEmployee.getPfmName(), manpowerEmployee.getName(), empType, "added");	
		}
		}catch (Exception e) {
			logger.error("error while saving manpower "+e.getMessage());
		}
		actionRequest.setAttribute(ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID, manpowerEmployee.getManpowerEmployeeId()+"");
		logger.info("manpowe employeee id  --- "+manpowerEmployee.getManpowerEmployeeId());
		if(manpowerEmployee!=null && !manpowerEmployee.equals(null)) {
			if(manpowerEmployee.getIsDirector()>0) {
				actionResponse.setRenderParameter("mvcPath", "/save-manpower-director.jsp");
			}else {
				actionResponse.setRenderParameter("mvcPath", "/save-manpower-employee.jsp");
			}
			
		}
		
		
	//	super.processAction(actionRequest, actionResponse);
		
	}

	
	

	
	Log logger = LogFactoryUtil.getLog(SaveManpowerActionCommand.class);
}
