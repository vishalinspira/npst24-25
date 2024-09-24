package com.nps.dashboard.web.mvc;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.ADD_REMARKS_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class AddRemarksMVCResourceCommand extends BaseMVCResourceCommand {

	private static final Log LOG = LogFactoryUtil.getLog(AddRemarksMVCResourceCommand.class);
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId", 0);
		String remarks = ParamUtil.getString(resourceRequest, "remarks", "NA");
		
		try {
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId);
			dlFileEntry.setDescription(remarks);
			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

}
