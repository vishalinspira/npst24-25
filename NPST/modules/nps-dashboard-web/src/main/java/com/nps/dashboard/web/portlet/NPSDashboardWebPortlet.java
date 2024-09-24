package com.nps.dashboard.web.portlet;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;
import com.nps.dashboard.web.util.NPSGraphReportService;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSTRoleConstants;

/**
 * @author Dell
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.single-page-application=false",
		"javax.portlet.display-name=NPSDashboardWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NPSDashboardWebPortlet extends MVCPortlet {
	
	private static final Log LOG = LogFactoryUtil.getLog(NPSDashboardWebPortlet.class);
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long repositoryId = themeDisplay.getScopeGroupId();
	
		long totalCount = 0;
		long approvedCount = 0;
		long pendingCount = 0;
		
		String[] folderNames = {"Daily", "Weekly", "Monthly", "Periodically", "Half-Yearly", "Quarterly", "Annually"};
		
//		for(String folderName : folderNames) {
//			Folder folder = null;
//			
//			try {
//				folder = DLAppServiceUtil.getFolder(repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderName);
//			} catch (PortalException e) {
//				LOG.error("No Folder exists " + e.getMessage());
//			}
//			
//			if(Validator.isNotNull(folder)) {
//				//List<DLFileEntry> dlFileEntries = DLFileEntryLocalServiceUtil.getFileEntries(themeDisplay.getScopeGroupId(), folder.getFolderId());
//				DynamicQuery dynamicquery=DLFileEntryLocalServiceUtil.dynamicQuery();
//				dynamicquery.add(PropertyFactoryUtil.forName("folderId").eq(folder.getFolderId()));
//				dynamicquery.add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId()));
//				totalCount=DLFileEntryLocalServiceUtil.dynamicQueryCount(dynamicquery);
//				dynamicquery.add(PropertyFactoryUtil.forName("status").eq(WorkflowConstants.STATUS_APPROVED));
//				approvedCount=DLFileEntryLocalServiceUtil.dynamicQueryCount(dynamicquery);
//
//				dynamicquery.add(PropertyFactoryUtil.forName("status").eq(WorkflowConstants.STATUS_PENDING));
//				pendingCount=DLFileEntryLocalServiceUtil.dynamicQueryCount(dynamicquery);
//				
////				for(DLFileEntry DLFE : dlFileEntries) {
////					if(DLFE.getStatus() == WorkflowConstants.STATUS_APPROVED) {
////						approvedCount++;
////					} else if(DLFE.getStatus() == WorkflowConstants.STATUS_PENDING) {
////						pendingCount++;
////					}
////					
////					totalCount++;
////				}
//			}
//		}
		
		renderRequest.setAttribute("allCount", totalCount < 10 ? "0"+totalCount : totalCount);
		renderRequest.setAttribute("pendingCount", pendingCount < 10 ? "0"+pendingCount : pendingCount);
		renderRequest.setAttribute("approvedCount", approvedCount < 10 ? "0"+approvedCount : approvedCount);
		
		String companyName = npsDashboardUtil.getCompanyName(themeDisplay.getCompanyId(), themeDisplay.getUserId());
		renderRequest.setAttribute("companyName", companyName);
		List<String> userDepartments = npsDashboardUtil.getUserDepartments(themeDisplay.getCompanyId(), themeDisplay.getUserId());
		renderRequest.setAttribute("userCompanies", userDepartments);
		String analyticURL = npsDashboardUtil.getAnalyticURL(themeDisplay.getUser().getEmailAddress());
		renderRequest.setAttribute("analyticURL", analyticURL);
		super.render(renderRequest, renderResponse);
	}
	
	
	@Resource(name="formsPDFCreationResourceCommand1")
	public void formsPDFCreationResourceCommand1() {
		// TODO Auto-generated method stub
		LOG.info("formsPDFCreationResourceCommand   1  callled ");

	}
	
	
	@Reference
	private NPSGraphReportService npsGraphReportService;
	
	@Reference
	private RoleLocalService roleLocalService;
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;
}