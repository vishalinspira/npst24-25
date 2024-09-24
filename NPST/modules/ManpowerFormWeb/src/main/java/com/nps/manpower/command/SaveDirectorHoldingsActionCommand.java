package com.nps.manpower.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.nps.manpower.constants.ManpowerDirectorHoldingFieldName;
import com.nps.manpower.constants.ManpowerFormPortletKeys;
import com.nps.manpower.model.ManpowerDirectorHolding;
import com.nps.manpower.model.ManpowerEmployee;
import com.nps.manpower.service.ManpowerDirectorHoldingLocalService;
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
		"mvc.command.name=saveShareHolding",
}, service = MVCActionCommand.class)
public class SaveDirectorHoldingsActionCommand extends BaseMVCActionCommand {
	
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		ManpowerDirectorHolding manpowerDirectorHolding=null;
		try {
		long manpowerDirectorHoldingId=ParamUtil.getLong(actionRequest, ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID);
		long manpowerEmployeeId=ParamUtil.getLong(actionRequest, ManpowerDirectorHoldingFieldName.MANPOWER_EMPLOYEE_ID);
		String concern =ParamUtil.getString(actionRequest,ManpowerDirectorHoldingFieldName.CONCERN );
		String companyName =ParamUtil.getString(actionRequest,ManpowerDirectorHoldingFieldName.COMPANY_NAME );
		String shareHolding =ParamUtil.getString(actionRequest, ManpowerDirectorHoldingFieldName.SHARE_HOLDING);
		//Date concernDate=ParamUtil.getDate(actionRequest, ManpowerDirectorHoldingFieldName.CONCERN, dateFormat);
		Date concernDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ManpowerDirectorHoldingFieldName.CONCERN_DATE));
		int status=ParamUtil.getInteger(actionRequest, ManpowerDirectorHoldingFieldName.STATUS);
//		Date fromDate=ManpowerUtil.getDate(ParamUtil.getString(actionRequest, ManpowerDirectorHoldingFieldName.FROM_DATE));
//		Date toDate=ManpowerUtil.getDate(ParamUtil.getString(actionRequest, ManpowerDirectorHoldingFieldName.TO_DATE));
		
		logger.info("manpowerEmployeeId:   "+manpowerEmployeeId);
		ManpowerEmployee manpowerEmployee=ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
		manpowerDirectorHolding= manpowerDirectorHoldingLocalService.saveShareHolding(manpowerDirectorHoldingId, manpowerEmployeeId, companyName, concern, shareHolding, concernDate, status);
		NpsEmailApi mail_api = new NpsEmailServiceImpl();
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		if(manpowerDirectorHoldingId>0) {
			mail_api.sendManpowerEmail(serviceContext.getCompanyId(), NPSTRoleConstants.PFM_AM, manpowerEmployee.getPfmName(), manpowerEmployee.getName(), "Director Share Holding", "updated");
		}else {
			mail_api.sendManpowerEmail(serviceContext.getCompanyId(), NPSTRoleConstants.PFM_AM, manpowerEmployee.getPfmName(), manpowerEmployee.getName(), "Director Share Holding", "added");	
		}
		}catch (Exception e) {
			logger.error("error while saving manpower "+e.getMessage());
		}
		actionRequest.setAttribute(ManpowerDirectorHoldingFieldName.MANPOWER_EMPLOYEE_ID, manpowerDirectorHolding.getManpowerEmployeeId()+"");
		actionRequest.setAttribute(ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID, manpowerDirectorHolding.getManpowerDirectorHoldingId()+"");
		if(manpowerDirectorHolding!=null && !manpowerDirectorHolding.equals(null)) {
				actionResponse.setRenderParameter("mvcPath", "/save-shareholding.jsp");
			
		}
		
	//	super.processAction(actionRequest, actionResponse);
		
	}
	
	@Reference
	ManpowerDirectorHoldingLocalService manpowerDirectorHoldingLocalService;
	Log logger = LogFactoryUtil.getLog(SaveDirectorHoldingsActionCommand.class);

}
