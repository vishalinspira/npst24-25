package com.nps.erm.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.nps.erm.constants.ErmFieldName;
import com.nps.erm.constants.ErmInformationPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

	@Component(immediate = true, property = {
			"javax.portlet.name="
					+ErmInformationPortletKeys.ERMINFORMATION ,
			"mvc.command.name=/erm/filter",
	}, service = MVCRenderCommand.class)
	public class BatchCreationFilterRenderCommand implements MVCRenderCommand {

		@Override
		public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
			logger.info("********* render command called");
			String craName=ParamUtil.getString(renderRequest, ErmFieldName.CRA_NAME);
			String jspName=ParamUtil.getString(renderRequest, "jspName");
		
			renderRequest.setAttribute(ErmFieldName.CRA_NAME, craName);

				return jspName;
		}
		Log logger = LogFactoryUtil.getLog(BatchCreationFilterRenderCommand.class);

	}
