package com.nps.manpower.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.nps.manpower.constants.ManpowerCompositionCommitteeFieldName;
import com.nps.manpower.constants.ManpowerFormPortletKeys;
import com.nps.manpower.model.CompositionCommittee;
import com.nps.manpower.model.ManpowerEmployee;
import com.nps.manpower.service.CompositionCommitteeLocalService;
import com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSTRoleConstants;
import nps.email.api.api.NpsEmailApi;
import nps.email.service.impl.NpsEmailServiceImpl;
import npst.common.util.NpstCommonUtil;

@Component(immediate = true, property = {
		"javax.portlet.name="
				+ ManpowerFormPortletKeys.MANPOWERFORM,
		"mvc.command.name=saveCompositionCommittee",
}, service = MVCActionCommand.class)
public class CompositionCommitteeActionCommand extends BaseMVCActionCommand{
	
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		CompositionCommittee compositionCommittee=null;
		try {
			
		long compositionCommitteeId=ParamUtil.getLong(actionRequest, ManpowerCompositionCommitteeFieldName.MANPOWER_COMPOSITION_COMMITTEE_ID);
		long manpowerEmployeeId=ParamUtil.getLong(actionRequest, ManpowerCompositionCommitteeFieldName.MANPOWER_EMPLOYEE_ID);
		String designation =ParamUtil.getString(actionRequest,ManpowerCompositionCommitteeFieldName.DESIGNATION );
		long committeeMemberShipType =ParamUtil.getLong(actionRequest,ManpowerCompositionCommitteeFieldName.COMMITTEE_MEMBERSHIP_TYPE );
		String name =ParamUtil.getString(actionRequest,ManpowerCompositionCommitteeFieldName.NAME );
		String email =ParamUtil.getString(actionRequest, ManpowerCompositionCommitteeFieldName.EMAIL);
		String membershipType =ParamUtil.getString(actionRequest, ManpowerCompositionCommitteeFieldName.MEMBERSHIP_TYPE);
		Date committeeAppointmentDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ManpowerCompositionCommitteeFieldName.COMMITTEE_APPOINTMENT_DATE));
		int status=ParamUtil.getInteger(actionRequest, ManpowerCompositionCommitteeFieldName.STATUS);
//		Date fromDate=ManpowerUtil.getDate(ParamUtil.getString(actionRequest, ManpowerCompositionCommitteeFieldName.FROM_DATE));
//		Date toDate=ManpowerUtil.getDate(ParamUtil.getString(actionRequest, ManpowerCompositionCommitteeFieldName.TO_DATE));
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		logger.info("manpowerEmployeeId:   "+manpowerEmployeeId);
		compositionCommittee= compositionCommitteeLocalService.saveCompositionCommittee(compositionCommitteeId, manpowerEmployeeId,committeeMemberShipType, designation, name,email, membershipType, committeeAppointmentDate, status);
		ManpowerEmployee manpowerEmployee=ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
		manpowerEmployee.setCommitteeMembershipType((int)committeeMemberShipType);
		ManpowerEmployeeLocalServiceUtil.updateManpowerEmployee(manpowerEmployee);
		NpsEmailApi mail_api = new NpsEmailServiceImpl();
		if(compositionCommitteeId>0) {
			mail_api.sendManpowerEmail(serviceContext.getCompanyId(), NPSTRoleConstants.PFM_AM, manpowerEmployee.getPfmName(), manpowerEmployee.getName(), "Committee Type", "updated");
		}else {
			mail_api.sendManpowerEmail(serviceContext.getCompanyId(), NPSTRoleConstants.PFM_AM, manpowerEmployee.getPfmName(), manpowerEmployee.getName(), "Committee Type", "added");	
		}
		}catch (Exception e) {
			logger.error("error while saving composiotion committee "+e.getMessage());
		}
		actionRequest.setAttribute(ManpowerCompositionCommitteeFieldName.MANPOWER_EMPLOYEE_ID, compositionCommittee.getManpowerEmployeeId()+"");
		actionRequest.setAttribute(ManpowerCompositionCommitteeFieldName.MANPOWER_COMPOSITION_COMMITTEE_ID, compositionCommittee.getCompositionCommitteeId()+"");
		if(compositionCommittee!=null && !compositionCommittee.equals(null)) {
				actionResponse.setRenderParameter("mvcPath", "/save-composition.jsp");
			
		}
		
	//	super.processAction(actionRequest, actionResponse);
		
	}
	
	@Reference
	CompositionCommitteeLocalService compositionCommitteeLocalService;
	Log logger = LogFactoryUtil.getLog(CompositionCommitteeActionCommand.class);
}
