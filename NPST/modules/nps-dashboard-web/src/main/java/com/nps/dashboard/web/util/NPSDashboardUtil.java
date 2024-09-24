package com.nps.dashboard.web.util;

import com.daily.average.service.model.IntermediaryList;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCommonConstants;
import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;

@Component(immediate = true, service = NPSDashboardUtil.class)
public class NPSDashboardUtil {
	
	private static final Log LOG = LogFactoryUtil.getLog(NPSDashboardUtil.class);
	
	public void addReportsToDL(PortletRequest portletRequest, String uploadedContent, String folderName) {
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(portletRequest);
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		ServiceContext serviceContext = null;
				
		String mimeType = "";
		String fileName = uploadPortletRequest.getFileName(uploadedContent);
		
		InputStream inputStream = null;
		try {
			inputStream = uploadPortletRequest.getFileAsStream(uploadedContent);
		} catch (IOException e) {
			LOG.error("Error in getting " + uploadedContent + " ::::: " + e.getMessage());
		}
		
		if(Validator.isNotNull(inputStream)) {
			try{
				
				FileEntry fileEntry = null;
				Folder bulkUploadFolder = null;
				bulkUploadFolder = createFolder(portletRequest, themeDisplay,  folderName, folderName);
				
				if(Validator.isNotNull(bulkUploadFolder)) {
					mimeType = MimeTypesUtil.getContentType(fileName);
					serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), portletRequest);
					
					try {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(themeDisplay.getScopeGroupId(), bulkUploadFolder.getFolderId(), fileName);
						if(Validator.isNotNull(fileEntry)) {
							fileName = getRandomNumber() + "_" +fileName;
							fileEntry = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
									bulkUploadFolder.getFolderId(), fileName, mimeType, fileName, fileName, "",
									inputStream, 0, serviceContext);
						}
					} catch (Exception e) {
						fileEntry = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
								bulkUploadFolder.getFolderId(), fileName, mimeType, fileName, fileName, "",
								inputStream, 0, serviceContext);
					}						
				}
				
			} catch(Exception e) { 
					LOG.error("Error in storing " + fileName + " ::::: " +e.getMessage());
			}
		}
	}
	
	public Folder createFolder(PortletRequest portletRequest, ThemeDisplay themeDisplay, String name, String description) {
		long repositoryId = themeDisplay.getScopeGroupId();
		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		
		Folder folder = null;
		try {
			folder = DLAppLocalServiceUtil.getFolder(repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
		} catch (PortalException pe) {
			LOG.error(pe.getMessage());
		}
		
		if(Validator.isNull(folder)) {
			User adminUser = getAdmin();
			
			ServiceContext serviceContext = null;
			try {
				serviceContext = ServiceContextFactory.getInstance(DLFolder.class.getName(), portletRequest);
			} catch (PortalException e1) {
				e1.printStackTrace();
			}
			
			try {
				folder = DLAppLocalServiceUtil.addFolder(adminUser.getUserId(), repositoryId, parentFolderId, name, description, serviceContext);
			} catch (PortalException e) {
				LOG.error(e.getMessage());
			}
		}
		
		return folder;
	}
	
	public User getAdmin() {
		LOG.info("FETCHING ADMIN - START");
		
        long companyId = PortalUtil.getDefaultCompanyId();
        Role role = null;
        try {
        	role = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
            for (User admin : UserLocalServiceUtil.getRoleUsers(role.getRoleId())) {
                return admin;
            }
        } catch (Exception e) {
            LOG.error("Error while fetching the admin user"+e.getMessage());
        }
        
        LOG.info("FETCHING ADMIN - END");
        
        return null;
    }
		
	public int getRandomNumber() {
		Random random = new Random();
		int x = random.nextInt(900000) + 100000;
		return x;
	}
	
	/**
	 * This method return logged in user's company name
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public String getCompanyName(long companyId, long userId) {
		try {
			boolean npst = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.NPST, userId, Boolean.FALSE);
			if(npst)
				return NPSCompany.NPST;
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		try {
			boolean pfm = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM, userId, Boolean.FALSE);
			if(pfm)
				return NPSCompany.PFM;
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		try {
			boolean cra = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA, userId, Boolean.FALSE);
			if(cra)
				return NPSCompany.CRA;
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		try {
			boolean custodian = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN, userId, Boolean.FALSE);
			if(custodian)
				return NPSCompany.CUSTODIAN;
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		try {
			boolean grievances = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES, userId, Boolean.FALSE);
			if(grievances)
				return NPSCompany.GRIEVANCES;
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		return StringPool.BLANK;
	}
	
	public List<String> getUserDepartments(long companyId, long userId){
		List<String> userCompanies = new ArrayList<String>();
		try {
			boolean cra = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA, userId, Boolean.FALSE);
			if(cra)
				userCompanies.add(NPSCompany.CRA);
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		try {
			boolean custodian = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN, userId, Boolean.FALSE);
			if(custodian)
				userCompanies.add(NPSCompany.CUSTODIAN);
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		try {
			boolean grievances = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES, userId, Boolean.FALSE);
			if(grievances)
				userCompanies.add(NPSCompany.GRIEVANCES);
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		try {
			boolean npst = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.NPST, userId, Boolean.FALSE);
			if(npst)
				userCompanies.add(NPSCompany.NPST);
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		try {
			boolean pfm = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM, userId, Boolean.FALSE);
			if(pfm)
				userCompanies.add(NPSCompany.PFM);
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		
		
		
		return userCompanies;
	}
	
	public JSONObject getAssignedRoles(ThemeDisplay themeDisplay, List<String> departments) {
		JSONObject roleObject = JSONFactoryUtil.createJSONObject();
		for(String department: departments) {
			roleObject.put(department, getDepartmentAssignedRole(themeDisplay, department));
		}
			
		return roleObject;
	}
	
	public JSONObject getDepartmentAssignedRole(ThemeDisplay themeDisplay, String department) {
		boolean isAm = Boolean.FALSE;
		boolean isMgr = Boolean.FALSE;
		boolean isDgm = Boolean.FALSE;
		boolean isGm = Boolean.FALSE;
		boolean isMaker = Boolean.FALSE;
		boolean isChecker = Boolean.FALSE;
		boolean isFa = Boolean.FALSE;
		boolean isSupervisor = Boolean.FALSE;
		boolean isCraSupervisor = Boolean.FALSE;
		boolean isCraMaker = Boolean.FALSE;
		
		JSONObject assignedRoleObject = JSONFactoryUtil.createJSONObject();
		try {
			if(department.equalsIgnoreCase(NPSCompany.NPST)) {
				isAm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.NPST_AM, Boolean.FALSE);
				isDgm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.NPST_DGM, Boolean.FALSE);
				isGm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.NPST_GM, Boolean.FALSE);
				
				assignedRoleObject.put("isAm", isAm);
				assignedRoleObject.put("isDgm", isDgm);
				assignedRoleObject.put("isGm", isGm);
			}else if(department.equalsIgnoreCase(NPSCompany.PFM)) {
				isAm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_AM, Boolean.FALSE);
				isMgr = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_MGR, Boolean.FALSE);
				isDgm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_DGM, Boolean.FALSE);
				isGm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_GM, Boolean.FALSE);
				isMaker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_MAKER, Boolean.FALSE);
				isChecker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_CHECKER, Boolean.FALSE);
				isSupervisor = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, Boolean.FALSE);
				isCraSupervisor = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_SUPERVISOR, Boolean.FALSE);
				isCraMaker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_MAKER, Boolean.FALSE);
				assignedRoleObject.put("isAm", isAm);
				assignedRoleObject.put("isDgm", isDgm);
				assignedRoleObject.put("isMgr", isMgr);
				assignedRoleObject.put("isGm", isGm);
				assignedRoleObject.put("isMaker", isMaker);
				assignedRoleObject.put("isChecker", isChecker);
				assignedRoleObject.put("isSupervisor", isSupervisor);
				assignedRoleObject.put("isCraSupervisor", isCraSupervisor);
				assignedRoleObject.put("isCraMaker", isCraMaker);
			}else if(department.equalsIgnoreCase(NPSCompany.CRA)) {
				isAm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_AM, Boolean.FALSE);
				isDgm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_DGM, Boolean.FALSE);
				isGm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_GM, Boolean.FALSE);
				isMaker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_MAKER, Boolean.FALSE);
				isChecker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_CHECKER, Boolean.FALSE);
				assignedRoleObject.put("isAm", isAm);
				assignedRoleObject.put("isDgm", isDgm);
				assignedRoleObject.put("isGm", isGm);
				assignedRoleObject.put("isMaker", isMaker);
				assignedRoleObject.put("isChecker", isChecker);
			}else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				isAm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_AM, Boolean.FALSE);
				isDgm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_DGM, Boolean.FALSE);
				isGm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_GM, Boolean.FALSE);
				isMgr = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_MGR, Boolean.FALSE);
				isFa = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_FA, Boolean.FALSE);
				isMaker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_MAKER, Boolean.FALSE);
				isChecker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_CHECKER, Boolean.FALSE);
				isSupervisor = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_SUPERVISOR, Boolean.FALSE);
				assignedRoleObject.put("isAm", isAm);
				assignedRoleObject.put("isDgm", isDgm);
				assignedRoleObject.put("isGm", isGm);
				assignedRoleObject.put("isFa", isFa);
				assignedRoleObject.put("isMgr", isMgr);
				assignedRoleObject.put("isMaker", isMaker);
				assignedRoleObject.put("isChecker", isChecker);
				assignedRoleObject.put("isSupervisor", isSupervisor);
			}else if(department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				isAm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.GRIEVANCES_AM, Boolean.FALSE);
				isMgr = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.GRIEVANCES_MGR, Boolean.FALSE);
				isDgm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.GRIEVANCES_DGM, Boolean.FALSE);
				isGm = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.GRIEVANCES_GM, Boolean.FALSE);
				isMaker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_MAKER, Boolean.FALSE);
				isChecker = roleLocalService.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_CHECKER, Boolean.FALSE);
				assignedRoleObject.put("isAm", isAm);
				assignedRoleObject.put("isDgm", isDgm);
				assignedRoleObject.put("isGm", isGm);
				assignedRoleObject.put("isMaker", isMaker);
				assignedRoleObject.put("isChecker", isChecker);
				assignedRoleObject.put("isMgr", isMgr);
			}
		} catch (PortalException e) {
			LOG.error("Exception on setting up request attributes : "+e.getMessage());
		}
		
		return assignedRoleObject;
	}
	
	public String getUserName(long classPK, String className) {
		long userId = 0;
		if(className.equalsIgnoreCase(Role.class.getName())) {
			Role role = RoleLocalServiceUtil.fetchRole(classPK);
			if(Validator.isNotNull(role)) {
				return role.getName();
			}
			//long[] userIds = UserLocalServiceUtil.getRoleUserIds(classPK);
			//LOG.info("Length of role : "+Arrays.asList(userIds));
			//userId = userIds.length > 0 ? userIds[0] : 0;
		}else if(className.equalsIgnoreCase(User.class.getName())) {
			userId = classPK;
		}
		//LOG.info("UserId : "+userId);
		if(userId > 0) {
			try {
				return UserLocalServiceUtil.getUser(userId).getFullName();
			} catch (PortalException e) {
				LOG.info("Exception on getting user name : "+e.getMessage());
			}
		}
		
		return StringPool.BLANK;
	}
	
	public long getUserId(long classPK, String className) {
		long userId = 0;
		if(className.equalsIgnoreCase(Role.class.getName())) {
			long[] userIds = UserLocalServiceUtil.getRoleUserIds(classPK);
			//LOG.info("Length of role : "+Arrays.asList(userIds));
			userId = userIds.length > 0 ? userIds[0] : 0;
		}else if(className.equalsIgnoreCase(User.class.getName())) {
			userId = classPK;
		}
		//LOG.info("UserId : "+userId);
		if(userId > 0) {
			try {
				return userId;
			} catch (Exception e) {
				LOG.info("Exception on getting user id : "+e.getMessage());
			}
		}
		
		return 0l;
	}
	
	public String getIntermediaryName(long reportUploadLogId) {
		try {
			ReportUploadLog reportUploadLog= reportUploadLogLocalService.getReportUploadLog(reportUploadLogId);
			return reportUploadLog.getIntermediaryname();
		} catch (PortalException e) {
			LOG.error("Exception on fetching report upload log : "+e.getMessage());
		}
		
		return StringPool.BLANK;
	}
	
	public static JSONArray getIntermediaryList(User user, String department) {
		long intermediaryType = 0;
		boolean pfmMaker = Boolean.FALSE;
		try {
			pfmMaker = RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_MAKER, Boolean.FALSE);
		} catch (PortalException e) {
			LOG.error("Exception on fecthing role : "+e.getMessage());
		}
		if(department.equalsIgnoreCase(NPSCompany.PFM) || (pfmMaker && department.equalsIgnoreCase(NPSCompany.CUSTODIAN))) {
			intermediaryType = NPSCommonConstants.PFM_INTERMEDIARY_TYPE;
		}else if(department.equalsIgnoreCase(NPSCompany.CRA) || department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
			intermediaryType = NPSCommonConstants.CRA_INTERMEDIARY_TYPE;
		}else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
			intermediaryType = NPSCommonConstants.CUSTODIAN_INTERMEDIARY_TYPE;
		}
		List<IntermediaryList> intermediaryLists = IntermediaryListLocalServiceUtil.fetchIntermediaryListByTypeId(intermediaryType);
		
		JSONArray array = JSONFactoryUtil.createJSONArray();
		for(IntermediaryList intermediaryList: intermediaryLists) {
			JSONObject object = JSONFactoryUtil.createJSONObject();
			object.put("id", intermediaryList.getId());
			object.put("name", intermediaryList.getIntermediaryname());
			
			array.put(object);
		}
		
		return array;
	}
	
	public boolean isNonNpsUser(long userId) {
		boolean isNonNpsUser = false;
		User user = null;
		boolean inherited = Boolean.FALSE;
		long companyId = 0l;
		try {
			user = UserLocalServiceUtil.getUser(userId);
		} catch (Exception e) {
			LOG.error("Exception in getting user::" + e.getMessage());
		}
		if(Validator.isNotNull(user)) {
			companyId = user.getCompanyId();
			//LOG.info("user companyID::" + user.getCompanyId());
		}
		try {
			if(Validator.isNotNull(companyId)) {
				boolean pfmMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean pfmSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_SUPERVISOR, userId, inherited);
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
			}
			if(Validator.isNotNull(companyId) && !isNonNpsUser) {
				boolean craMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean craChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA_CHECKER, userId, inherited);
				boolean craSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA_SUPERVISOR, userId, inherited);
				isNonNpsUser = (craMaker || craChecker || craSupervisor);
			}
			if(Validator.isNotNull(companyId) && !isNonNpsUser) {
				boolean custodianMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_MAKER, userId, inherited);
				boolean custodianChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_CHECKER, userId, inherited);
				boolean custodianSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_SUPERVISOR, userId, inherited);
				boolean pfmMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean pfmSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_SUPERVISOR, userId, inherited);
				isNonNpsUser = (custodianMaker || custodianChecker || custodianSupervisor || pfmMaker || pfmChecker || pfmSupervisor);
			}
			if(Validator.isNotNull(companyId) && !isNonNpsUser) {
				boolean pfmMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.MAKER, userId, inherited);
				boolean pfmChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CHECKER, userId, inherited);
				boolean pfmSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.SUPERVISOR, userId, inherited);
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
			}
			if(Validator.isNotNull(companyId) && !isNonNpsUser) {
				boolean pfmMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_MAKER, userId, inherited);
				boolean pfmChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_CHECKER, userId, inherited);
				boolean pfmSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_SUPERVISOR, userId, inherited);
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
			}
		} catch (Exception e) {
			LOG.error("Exception in getting role::" + e.getMessage());
		}
		return isNonNpsUser;
	}
	
	public String getAnalyticURL(String emailAddress) {
		String analyticURL = StringPool.BLANK;
		if(emailAddress.equalsIgnoreCase("ceo@npstrust.org.in") || emailAddress.equalsIgnoreCase("ceo-npstrust@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.ceo");
		}else if(emailAddress.equalsIgnoreCase("gm2-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("gm2-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.gm2");
		}
		else if(emailAddress.equalsIgnoreCase("gm3-npst@npstrust.org.in") ||  emailAddress.equalsIgnoreCase("gm3-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.gm3");
	
		}else if(emailAddress.equalsIgnoreCase("dgm3-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("dgm3-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.dgm3");
		}
		else if(emailAddress.equalsIgnoreCase("dgm6-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("dgm6-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.dgm6");
		}
		else if(emailAddress.equalsIgnoreCase("dgm4-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("dgm4-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.dgm4");
		}
		else if(emailAddress.equalsIgnoreCase("dgm5-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("dgm5-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.dgm5");
		}
		else if(emailAddress.equalsIgnoreCase("mgr2-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("mgr2-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.mgr2");
		}
		else if(emailAddress.equalsIgnoreCase("mgr6-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("mgr6-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.mgr6");
		}
		else if(emailAddress.equalsIgnoreCase("am4-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("am4-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.am4");
		}
		else if(emailAddress.equalsIgnoreCase("am5-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("am5-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.am5");
		}
		else if(emailAddress.equalsIgnoreCase("am10-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("am10-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.am10");
		}
		else if(emailAddress.equalsIgnoreCase("am6-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("am6-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.am6");
		}
		else if(emailAddress.equalsIgnoreCase("am7-npst@npstrust.org.in") || emailAddress.equalsIgnoreCase("am7-npst@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.am7");
		}
		else if(emailAddress.equalsIgnoreCase("pensionfunds@npstrust.org.in") || emailAddress.equalsIgnoreCase("pensionfunds@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.pensionfunds");
		}
		else if(emailAddress.equalsIgnoreCase("pensionfunds-1@npstrust.org.in") || emailAddress.equalsIgnoreCase("pensionfunds-1@yopmail.com")) {
			analyticURL = PropsUtil.get("analytic.url.pensionfunds1");
		}else {
			analyticURL = PropsUtil.get("analytic.url");
		}
		
		return analyticURL;
	}
	
	
	@Reference
	private RoleLocalService roleLocalService;
	
	@Reference 
	private ReportUploadLogLocalService reportUploadLogLocalService;

	
	
}
