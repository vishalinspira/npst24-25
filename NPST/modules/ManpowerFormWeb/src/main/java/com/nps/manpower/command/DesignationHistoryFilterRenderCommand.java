package com.nps.manpower.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.nps.manpower.constants.ManpowerEmployeeFieldName;
import com.nps.manpower.constants.ManpowerFormPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
@Component(immediate = true, property = {
		"javax.portlet.name="
				+ ManpowerFormPortletKeys.MANPOWERFORM,
		"mvc.command.name=/employee/designation/filter",
}, service = MVCRenderCommand.class)
public class DesignationHistoryFilterRenderCommand  implements MVCRenderCommand{
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		logger.info("********* render command called");
		String  designation=ParamUtil.getString(renderRequest, ManpowerEmployeeFieldName.DESIGNATION);
		String pfmName=ParamUtil.getString(renderRequest, ManpowerEmployeeFieldName.PFM_NAME);
		//String jspName=ParamUtil.getString(renderRequest, "jspName");
		String jspName="/designation-history.jsp";
		
		logger.info("PFM Name : "+pfmName +"    Designation:  "+designation);
		renderRequest.setAttribute(ManpowerEmployeeFieldName.DESIGNATION, designation);
	
		renderRequest.setAttribute(ManpowerEmployeeFieldName.PFM_NAME, pfmName);

			return jspName;
	}
	Log logger = LogFactoryUtil.getLog(DesignationHistoryFilterRenderCommand.class);

}
