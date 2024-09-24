package npst.common.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;

import npst.common.constant.NpstCommonConstant;
import npst.common.constant.NpstRoleConstant;

public class NpstCommonUtil {

	/**
	 * 
	 * @param companyId
	 * @param roleNames
	 * @return
	 */
	public static List<Long> getRoleIds(long companyId,String roleNames[]){
		List<Long> roleIds=new ArrayList<Long>();
		for(String roleName:roleNames) {
			try {
				roleIds.add(RoleLocalServiceUtil.getRole(companyId,roleName).getRoleId());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return roleIds;
	}
	
	/**
	 * 
	 * @param parentFolderId
	 * @param folderName
	 * @param serviceContext
	 * @return
	 */
	public static long createOrGetFolderId(long parentFolderId,String folderName,ServiceContext serviceContext) {
		Folder folder=null;
		try {
			folder=DLAppLocalServiceUtil.getFolder(serviceContext.getScopeGroupId(), parentFolderId, folderName);
			if(folder==null || folder.equals(null)) {
				folder=DLAppLocalServiceUtil.addFolder(serviceContext.getUserId(), serviceContext.getScopeGroupId(), parentFolderId, folderName, folderName, serviceContext);
			}
		}catch (Exception e) {
			try {
				folder=DLAppLocalServiceUtil.addFolder(serviceContext.getUserId(), serviceContext.getScopeGroupId(), parentFolderId, folderName, folderName, serviceContext);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}	
		}
		return folder.getFolderId();
		
	}
	
	/**
	 * 
	 * @param file
	 * @param folderId
	 * @param mimeType
	 * @param fileName
	 * @param actionRequest
	 * @param serviceContext
	 * @return
	 * @throws FileNotFoundException 
	 * @throws PortalException
	 * @throws IOException
	 */
	public static  long fileUpload(UploadRequest uploadRequest, long folderId,String fileFieldName,ServiceContext serviceContext) throws PortalException, FileNotFoundException {
	
			File file=uploadRequest.getFile(fileFieldName);
			String fileName=file.getName();
			return fileUpload(file, folderId, uploadRequest.getContentType(fileFieldName), fileName, serviceContext);
	}
	
	/**
	 * 
	 * @param file
	 * @param folderId
	 * @param mimeType
	 * @param fileName
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @throws FileNotFoundException
	 */
	public static  long fileUpload(File file, long folderId, String mimeType, String fileName,ServiceContext serviceContext) throws  FileNotFoundException, PortalException  {
		long time =(new Date()).getTime();
		String str[]=fileName.split("\\.");
		fileName=str[0]+"_"+time+"."+str[1];
		logger.info("fileName:-  "+fileName);
		long repositoryId = serviceContext.getScopeGroupId();
		String changeLog = "hi";// TODO to be identified
		InputStream inputStream = new FileInputStream(file);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folderId, fileName, mimeType, fileName,
				file.getName().split("\\.")[0], changeLog, inputStream, file.length(), serviceContext);
		addPermission(serviceContext.getCompanyId(), DLFileEntry.class.getName(), fileEntry.getFileEntryId()+"");
		return fileEntry.getFileEntryId();
	}
	
	/**
	 * 
	 * @param companyId
	 * @param className
	 * @param pk
	 */
	public static void addPermission(long companyId,String className,String pk){
		try {
		Role role=RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST);
		String[] actionIds = new String[] { ActionKeys.VIEW };
		 ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, className, ResourceConstants.SCOPE_INDIVIDUAL, pk, role.getRoleId(), actionIds);
		}catch (Exception e) {
			logger.error("error while set file permission: "+e.getMessage());
		}
		}
	
	/**
	 * 
	 * @param fileEntryId
	 * @param themeDisplay
	 * @return
	 */
	public static String getFileDownloadURL(long fileEntryId,ThemeDisplay themeDisplay ) {
		try {
		String fileUrl="";
		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId);
		fileUrl = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/"
				+ themeDisplay.getScopeGroupId() + "/" + fileEntry.getFolderId() + "/"
				+ fileEntry.getTitle() + "/" + fileEntry.getUuid() + "?t=" + fileEntry.getFileEntryId()
				+ "&download=true";
		return fileUrl;
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(String date){
		try {
		SimpleDateFormat dateFormat=new SimpleDateFormat(NpstCommonConstant.DATE_FORMAT);
		if(date!=null && !date.equals(null) && date!="" && !date.equals("")) {
			return dateFormat.parse(date);
		}
		}catch (Exception e) {
			logger.error("error while parsing date: -"+e.getMessage());
		}
		return null;
	}

	/**
	 * This method retrun date as a string. if date is null, return blank string
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date){
		SimpleDateFormat dateFormat=new SimpleDateFormat(NpstCommonConstant.DATE_FORMAT);
		try {
			if(date!=null && !date.equals(null) && !date.equals("")) {
				return dateFormat.format(date);			
			}else {
				return "";
			}
		}catch (Exception e) {
			logger.error("error while convert date to string : "+e.getCause()+"  : "+e.getMessage());
		}
		return "";
			
	}
	
	
	public static String getDateString(Date date,String dateformat){
		SimpleDateFormat dateFormat=new SimpleDateFormat(dateformat);
		try {
			if(date!=null && !date.equals(null) && !date.equals("")) {
				return dateFormat.format(date);			
			}else {
				return "";
			}
		}catch (Exception e) {
			logger.error("error while convert date to string : "+e.getCause()+"  : "+e.getMessage());
		}
		return "";
			
	}
	
	/**
	 * 
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public static boolean isNpstUser(long userId,long companyId) {
		boolean isNpstUser=false;
		try {
			isNpstUser=RoleLocalServiceUtil.hasUserRoles(userId, companyId, NpstRoleConstant.NPST_ROLES, false);
		} catch (PortalException e) {
		logger.error(e.getMessage());
		}
		return isNpstUser;
	}

	public static boolean isNpstUser(long companyId) {
		boolean isNpstUser=false;
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		try {
			isNpstUser=RoleLocalServiceUtil.hasUserRoles(serviceContext.getUserId(), companyId, NpstRoleConstant.NPST_ROLES, false);
		} catch (PortalException e) {
		logger.error(e.getMessage());
		}
		return isNpstUser;
	}
	
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param className
	 * @param columnName
	 * @return
	 */
	public static String getExpandoValue(long companyId,long userId,String className,String columnName) {
		
		try {
	         ExpandoValue expandoObiect=ExpandoValueLocalServiceUtil.getValue(companyId, className, ExpandoTableConstants.DEFAULT_TABLE_NAME,columnName, userId);
			return expandoObiect.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ""; 
	}
	
	/**
	 * 
	 * @param companyId
	 * @param roles
	 * @param columnName
	 * @return
	 */
	public static Set<String> getUserType(long companyId,String roles[],String columnName){
		Set<String> userType=new  HashSet<String>();
		Set<User> users=getUserByRoleIds(companyId, getRoleIds(companyId, roles));
		for(User user:users) {
			try {
			String value=getExpandoValue(companyId, user.getUserId(), User.class.getName(), columnName);
			if(value!="" && value!=null && !value.equalsIgnoreCase(null) && !value.equalsIgnoreCase("")) {
			userType.add(value);
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		}
		return userType;
		
	}
	/**
	 * 
	 * @param companyId
	 * @param roleIds
	 * @return
	 */
	public static Set<User> getUserByRoleIds(long companyId,List<Long> roleIds) {
		Set<User> users=new HashSet<User>();
		for(long roleId:roleIds) {
			try {
			users.addAll(UserLocalServiceUtil.getRoleUsers(roleId));
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return users;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param roleIds
	 * @param isCompleted
	 * @return
	 */
	public static List<WorkflowTask> getWorkflowTaskByRoles(long companyId,List<Long> roleIds,boolean isCompleted,boolean isDateCmprator) {
		List<WorkflowTask> workflowTasks=new ArrayList<WorkflowTask>();
			for(long roleId:roleIds) {
				try {
				workflowTasks.addAll(WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, roleId, isCompleted,QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(isDateCmprator)));
				}catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		return workflowTasks;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param users
	 * @param isCompleted
	 * @return
	 */
	public static List<WorkflowTask> getWorkflowTaskByUser(long companyId,Set<User> users,boolean isCompleted,boolean isDateCmprator) {
		List<WorkflowTask> workflowTasks=new ArrayList<WorkflowTask>();
			for(User user:users) {
				try {
				workflowTasks.addAll(WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted,QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(isDateCmprator)));
				}catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		return workflowTasks;
	}
	

	/**
	 * 
	 * @param roles
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public static boolean hasPermission(String[] roles,long userId,long companyId) {
		boolean hasRole=false;
		try {
			hasRole=RoleLocalServiceUtil.hasUserRoles(userId, companyId, roles, false);
		} catch (PortalException e) {
			logger.error("while checking user role: "+e.getMessage());
		}
		return hasRole;
	}
	
	private static Log logger = LogFactoryUtil.getLog(NpstCommonUtil.class);
}
