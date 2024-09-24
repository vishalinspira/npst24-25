package com.nps.manpower.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.nps.manpower.constants.ManpowerDirectorHoldingFieldName;
import com.nps.manpower.constants.ManpowerEmployeeFieldName;
import com.nps.manpower.constants.ManpowerFormPortletKeys;
import com.nps.manpower.constants.ManpowerStatusConstant;
import com.nps.manpower.model.ManpowerEmployee;
import com.nps.manpower.service.ManpowerDirectorHoldingLocalServiceUtil;
import com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.ProcessAction;

import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.NPSTRoleConstants;
import nps.email.api.api.NpsEmailApi;
import nps.email.service.impl.NpsEmailServiceImpl;

/**
 * @author VishalKumar
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ManpowerForm",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ManpowerFormPortletKeys.MANPOWERFORM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ManpowerFormPortlet extends MVCPortlet {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
	@ProcessAction(name = "deleteManpowerEmployee")
	public void deleteManpowerEmployee(ActionRequest actionRequest,ActionResponse actionResponse) {
		try {
		long manpoweEmployeeId=ParamUtil.getLong(actionRequest, ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
		Date resignationDate=ParamUtil.getDate(actionRequest, ManpowerEmployeeFieldName.RESIGNATION_DATE,dateFormat);
		ManpowerEmployee manpowerEmployee=ManpowerEmployeeLocalServiceUtil.deleteManpowerEmployee(manpoweEmployeeId,Integer.parseInt(ManpowerStatusConstant.NON_ACTIVE_EMPLOYEE),resignationDate);
		NpsEmailApi mail_api = new NpsEmailServiceImpl();
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
			mail_api.sendManpowerEmail(serviceContext.getCompanyId(), NPSTRoleConstants.PFM_AM, manpowerEmployee.getPfmName(), manpowerEmployee.getName(), "Employee", "deleted");	
		logger.info(manpowerEmployee.getIsDirector()+"  ---  "+manpoweEmployeeId);
		if(manpowerEmployee.getIsDirector()>0) {
			actionResponse.setRenderParameter("mvcPath", "/view-manpower-director.jsp");
		}else {
			actionResponse.setRenderParameter("mvcPath", "/view-manpower-employee.jsp");
		}
		
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@ProcessAction(name = "deleteShareHolding")
	public void deleteShareHolding(ActionRequest actionRequest,ActionResponse actionResponse) {
		try {
			long manpowerEmployeeId=ParamUtil.getLong(actionRequest, ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
			ManpowerEmployee manpowerEmployee=	ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
			long manpowerDirectorHoldingId=ParamUtil.getLong(actionRequest, ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID);
			ManpowerDirectorHoldingLocalServiceUtil.deleteShareHolding(manpowerDirectorHoldingId, Integer.parseInt(ManpowerStatusConstant.NON_ACTIVE_EMPLOYEE));
			NpsEmailApi mail_api = new NpsEmailServiceImpl();
			ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
				mail_api.sendManpowerEmail(serviceContext.getCompanyId(), NPSTRoleConstants.PFM_AM, manpowerEmployee.getPfmName(), manpowerEmployee.getName(), "Director Share Holdings", "deleted");
			actionRequest.setAttribute(ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID, manpowerEmployeeId+"");
				actionResponse.setRenderParameter("mvcPath", "/view-shareholding.jsp");
			
			}catch (Exception e) {
				logger.error(e.getMessage());
			}

	}
	
Log logger = LogFactoryUtil.getLog(ManpowerFormPortlet.class);

}