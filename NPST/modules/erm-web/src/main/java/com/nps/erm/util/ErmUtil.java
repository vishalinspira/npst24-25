package com.nps.erm.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalServiceUtil;
import com.nps.erm.constants.NpstErmConstant;
import com.nps.erm.modal.ErmBatchModal;
import com.nps.erm.modal.ErmModal;
import com.nps.erm.model.ErmBatchInformation;
import com.nps.erm.model.ErmInformation;
import com.nps.erm.service.ErmBatchInformationLocalServiceUtil;
import com.nps.erm.service.ErmInformationLocalServiceUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import nps.common.service.constants.NameMappingConstants;
import npst.common.constant.NpstCommonConstant;
import npst.common.constant.NpstRoleConstant;
import npst.common.util.NpstCommonUtil;

public class ErmUtil {
	
	
	private static final String COMMA = ",";
	private static int index = 0;
	
	private static String getExcelFormattedValue(String value) {
		StringBundler sb = new StringBundler(3);
		sb.append(CharPool.QUOTE);
		sb.append(StringUtil.replace(value, CharPool.QUOTE, StringPool.DOUBLE_QUOTE));
		sb.append(CharPool.QUOTE);
		return sb.toString();
	}
	
	public static File	exportExcel(List<ErmModal> ermModals){
		File file = null;
		try {
			 file=FileUtil.createTempFile("Export.csv");
			 Set<String> duplicatePran=ErmUtil.getDuplicatePran();
			 
			 
				StringBundler sb = new StringBundler();
				sb.append(getExcelFormattedValue(String.valueOf("Sr.No")));
				sb.append(COMMA);
				
				sb.append(getExcelFormattedValue(String.valueOf("CRA")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("PRAN")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Name of the Subscriber")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Transaction Date")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Amount Transacted in Rs.")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Amount required to be rectified in Rs.")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Type of Rectification")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Document Submission date")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("CRA submission date")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("CRA Remarks")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Bank Name")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Name on Bank Statement")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Transaction date in bank statemnet")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Self Declaration date")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Subscriber Sector")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Subscriber submission within timeline")));
				sb.append(COMMA);

				sb.append(getExcelFormattedValue(String.valueOf("CRA submission within timeline")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("NPST Remarks")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Status")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Date of decision")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Recommendation")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Pending with")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Duplicate PRAN")));
				sb.append(COMMA);
				
				sb.append(CharPool.NEW_LINE);
				
				 index = 0;
					
				for(ErmModal ermModal:ermModals) {
					  try {
						  ErmInformation ermInformation=ermModal.getErmInformation(); 
						  sb.setIndex(sb.index() - 1);
							sb.append(CharPool.NEW_LINE);

							sb.append(getExcelFormattedValue(String.valueOf(++index)));
							sb.append(COMMA);
							try {
								sb.append(getExcelFormattedValue(String.valueOf(ermModal.getIntermediaryName())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getPran())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getSubscriberName())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(NpstCommonUtil.getDateString(ermInformation.getTransactionDate()))));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getTransactedAmount())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getRectificationAmount())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getRectificationType())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(NpstCommonUtil.getDateString(ermInformation.getDocumentationsDate()))));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(NpstCommonUtil.getDateString(ermInformation.getFwdnpstDate()))));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getNoNpstRemark())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getBankName())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getBankStatmentName())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(NpstCommonUtil.getDateString(ermInformation.getTxnDate()))));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(NpstCommonUtil.getDateString(ermInformation.getSelfRectifiedDate()))));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getSubscriberSector())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getSubmissionStipulatedTime())));
								sb.append(COMMA);

								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getStipulated())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getNpstRemark())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getErmStatus())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(NpstCommonUtil.getDateString(ermInformation.getNpsDecisionDate()))));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getRecommendation())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(ermModal.getAssignedTo())));
								sb.append(COMMA);
								sb.append(getExcelFormattedValue(String.valueOf(duplicatePran.contains(ermInformation.getPran())?"Yes":"No")));
								sb.append(COMMA);

								
							} catch (Exception e) {
								logger.error(e.getMessage());
									}
							sb.setIndex(sb.index() - 1);
							sb.append(CharPool.NEW_LINE);
							
							FileWriter fw=new FileWriter(file);
							fw.write(sb.toString());
							fw.close();

					  }catch (Exception e) {
						logger.error(e.getMessage());
					}
				   }
				
				
			 
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return file;
	}
	
	public static File exportToExcel2(ErmBatchInformation batchInformation) {
		File file = null;
		try {
			 file=FileUtil.createTempFile("Excel2.csv");
			StringBundler sb = new StringBundler();
			sb.append(getExcelFormattedValue(String.valueOf("Sr.No")));
			sb.append(COMMA);
			
			sb.append(getExcelFormattedValue(String.valueOf("Case No")));
			sb.append(COMMA);
			sb.append(getExcelFormattedValue(String.valueOf("Subscriber Name and PRAN")));
			sb.append(COMMA);
			sb.append(getExcelFormattedValue(String.valueOf("Type of ERM request by the subscriber")));
			sb.append(COMMA);
			sb.append(getExcelFormattedValue(String.valueOf("Amount to be rectified(In Rs.)")));
			sb.append(COMMA);
			sb.append(getExcelFormattedValue(String.valueOf("Subscriber submission within timeline")));
			sb.append(COMMA);
			sb.append(getExcelFormattedValue(String.valueOf("CRA submission within timeline")));
			sb.append(COMMA);
			sb.append(getExcelFormattedValue(String.valueOf("Recommendation")));
			sb.append(COMMA);
			sb.append(getExcelFormattedValue(String.valueOf("Remark")));
			sb.append(COMMA);
			sb.append(CharPool.NEW_LINE);
			
			 index = 0;
				
			for(String ermId:batchInformation.getErmInformationIds().split(StringPool.COMMA)) {
				  try {
					  sb.setIndex(sb.index() - 1);
						sb.append(CharPool.NEW_LINE);

						sb.append(getExcelFormattedValue(String.valueOf(++index)));
						sb.append(COMMA);
					  ErmInformation ermInformation= ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
						try {
							char c = (char) (96+index);
							sb.append(getExcelFormattedValue(String.valueOf(batchInformation.getBatchNo()+"("+c+")")));
							sb.append(COMMA);
							sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getSubscriberName() +"   "+ermInformation.getPran())));
							sb.append(COMMA);
							sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getTransactionType())));
							sb.append(COMMA);
							sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getRectificationAmount())));
							sb.append(COMMA);
							sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getStipulated())));
							sb.append(COMMA);
							sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getSubmissionStipulatedTime())));
							sb.append(COMMA);
							sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getRecommendation())));
							sb.append(COMMA);
							sb.append(getExcelFormattedValue(String.valueOf(ermInformation.getNpstRemark())));
							sb.append(COMMA);
							
						} catch (Exception e) {
							logger.error(e.getMessage());
								}
						sb.setIndex(sb.index() - 1);
						sb.append(CharPool.NEW_LINE);
						

				  }catch (Exception e) {
					logger.error(e.getMessage());
				}
			   }


		//	byte[] bytes = sb.toString().getBytes();
			FileWriter fw=new FileWriter(file);
			fw.write(sb.toString());
			fw.close();
			//file = FileUtil.createTempFile
		} catch (Exception e) {
			logger.error("Exception While export excel file : " + e.getMessage());
		}

		return file;

	}
	
	
	
	
	
	public static File exportToExcel1(ErmBatchInformation batchInformation) {
		File file = null;
		try {
			 file=FileUtil.createTempFile("Excel1.csv");
			StringBundler sb = new StringBundler();
			sb.append(getExcelFormattedValue(String.valueOf("Sr.No")));
			sb.append(COMMA);
			
			sb.append(getExcelFormattedValue(String.valueOf("Particulars")));
			sb.append(COMMA);
			sb.append(getExcelFormattedValue(String.valueOf("Value")));
			sb.append(COMMA);
		

			sb.append(CharPool.NEW_LINE);

				sb.append(getExcelFormattedValue(String.valueOf(1)));
				sb.append(COMMA);
				
				sb.append(getExcelFormattedValue(String.valueOf("CRA")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf(NameMappingConstants.CRA_NAME_MAP.get( batchInformation.getPfmName()))));
				sb.append(COMMA);
				sb.append(CharPool.NEW_LINE);
				
//				sb.append(getExcelFormattedValue(String.valueOf(2)));
//				sb.append(COMMA);
//				sb.append(getExcelFormattedValue(String.valueOf("Batch considered for the weekly period")));
//				sb.append(COMMA);
//				sb.append(getExcelFormattedValue(String.valueOf(NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodFrom())+" , "+NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodTo()))));
//				sb.append(COMMA);
//				sb.append(CharPool.NEW_LINE);
				
				sb.append(getExcelFormattedValue(String.valueOf(2)));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Date of receipt of case")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf(ErmUtil.getReceiptDates(batchInformation.getErmBatchInformationId())).replace(COMMA, "")));
				sb.append(COMMA);
				sb.append(CharPool.NEW_LINE);
				
				sb.append(getExcelFormattedValue(String.valueOf(3)));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf("Cases received for approval")));
				sb.append(COMMA);
				sb.append(getExcelFormattedValue(String.valueOf(batchInformation.getErmInformationIds().split(StringPool.COMMA).length+"")));
				sb.append(COMMA);
				sb.append(CharPool.NEW_LINE);

			byte[] bytes = sb.toString().getBytes();
			FileWriter fw=new FileWriter(file);
			fw.write(sb.toString());
			fw.close();
			//file = FileUtil.createTempFile
		} catch (Exception e) {
			logger.error("Exception While export excel file : " + e.getMessage());
		}

		return file;

	}

	
	
	public static long getDaysBetween(Date from,Date to){
		
		long totalDays = 0;
		
	        // Ensure the start date is before the end date
		LocalDate start = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		LocalDate end = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        if (start.isAfter(end)) {
	        	logger.info("Start date must be before end date"); 
	        }
	        
	        LocalDate current = start;

	        while (!current.isAfter(end)) {
	                totalDays++;
	            current = current.plusDays(1);
	        }
	        logger.info("Date difference is :"+totalDays);
	        return totalDays;
	}
	
	
	/**
	 * Its count only working days.(Monday - Friday)
	 * @return
	 */
	public static long getBusinessDaysBetween(Date from,Date to){
	
		long totalDays = 0;
		try {
	        // Ensure the start date is before the end date
		LocalDate start = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		LocalDate end = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        if (start.isAfter(end)) {
	        	logger.info("Start date must be before end date"); 
	        }
	        
	        LocalDate current = start;

	        while (!current.isAfter(end)) {
	            DayOfWeek dayOfWeek = current.getDayOfWeek();
	            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
	                totalDays++;
	            }
	            current = current.plusDays(1);
	        }
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	        logger.info("Date difference is :"+totalDays);
	        return totalDays;
	}
	
	public static long getMaxBatchValue() {
		DynamicQuery dynamicQuery =ErmBatchInformationLocalServiceUtil.dynamicQuery() .setProjection(ProjectionFactoryUtil.max("batchNo"));
     //   DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ErmBatchInformation.class,ClassLoader.getSystemClassLoader()).setProjection(ProjectionFactoryUtil.max("batchNo"));

        List<Long> result = ErmBatchInformationLocalServiceUtil.dynamicQuery(dynamicQuery);
        return result.isEmpty() ? 0 : result.get(0);
    }

	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param isCompleted
	 * @return
	 */
	public static List<WorkflowTask> getCRAPendingBatchWorkflowTasks(long companyId,boolean isCompleted){
		List<WorkflowTask> list=null;
		try {
		 list=new ArrayList<WorkflowTask>();
		// long griev_maker_roleid=0;
		 long griev_kcrachecker_roleid=0;
		 long griev_ncrachecker_roleid=0;
		 long griev_camschecker_roleid=0;
		 try {
			 griev_kcrachecker_roleid= RoleLocalServiceUtil.getRole(companyId, "KCRA-"+NpstRoleConstant.CHECKER).getRoleId();
			 griev_ncrachecker_roleid= RoleLocalServiceUtil.getRole(companyId, "NCRA-"+NpstRoleConstant.CHECKER).getRoleId();
			 griev_camschecker_roleid= RoleLocalServiceUtil.getRole(companyId, "CAMS-"+NpstRoleConstant.CHECKER).getRoleId();
			 //griev_maker_roleid= RoleLocalServiceUtil.getRole(companyId, NpstRoleConstant.GRIEVANCES_MAKER).getRoleId();
		 }catch (Exception e) {
				logger.error(e.getMessage());
			}
		// List<WorkflowTask> grievMakerRoleWorkFlowTask = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, griev_maker_roleid, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
		 List<WorkflowTask> grievKCRACheckerRoleWorkFlowTask = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, griev_kcrachecker_roleid, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
		 List<WorkflowTask> grievNCRACheckerRoleWorkFlowTask = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, griev_ncrachecker_roleid, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
		 List<WorkflowTask> grievCAMSCheckerRoleWorkFlowTask = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, griev_camschecker_roleid, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
		 
		
		// list.addAll(grievMakerRoleWorkFlowTask);
		 list.addAll(grievKCRACheckerRoleWorkFlowTask);
		 list.addAll(grievNCRACheckerRoleWorkFlowTask);
		 list.addAll(grievCAMSCheckerRoleWorkFlowTask);
		 logger.info("myRoleWorkFlowTask :  "+list.size());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		 return list;
		}
	
	
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param isCompleted
	 * @return
	 */
	public static List<WorkflowTask> getMyAndMyRoleWorkflowTasks(long companyId,long userId,boolean isCompleted){
		List<WorkflowTask> list=null;
		try {
		 list=new ArrayList<WorkflowTask>();
		 List<WorkflowTask> myWorkFlowTask = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, userId, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
		 List<WorkflowTask> myRoleWorkFlowTask = WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(companyId,userId,isCompleted,QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
		logger.info("myRoleWorkFlowTask :  "+myRoleWorkFlowTask.size());
		 list.addAll(myWorkFlowTask);
		 list.addAll(myRoleWorkFlowTask);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		 return list;
		}

/**
 * 
 * @param companyId
 * @param userId
 * @return
 */
public static List<ErmBatchModal> getMyAndMyRolesBatch(long companyId,long userId){
	return getErmBatchModals(getMyAndMyRoleWorkflowTasks(companyId, userId, false),companyId, userId);	
}


/**
 * 
 * @param companyId
 * @param userId
 * @return
 */
public static List<ErmBatchModal> getCRAPendinBatch(long companyId,long userId){
	return getErmBatchModals(getCRAPendingBatchWorkflowTasks(companyId, false),companyId, userId);	
}




/**
 * 
 * @param companyId
 * @param userId
 * @return
 */
public static List<ErmBatchModal>  getErmBatch(long companyId,long userId) {
	List<WorkflowTask> workflowTasks=null;
	try {
	List<Long> roleIds=NpstCommonUtil.getRoleIds(companyId, NpstRoleConstant.NPST_ROLES);
	//roleIds.addAll(getRoleIds(companyId, NpstRoleConstant.NON_NPST_ROLES));
//if(!isNpstUser(userId, companyId)) {
//	roleIds.addAll(getRoleIds(companyId, NpstRoleConstant.NON_NPST_ROLES));
//}

//long gmRoleId=RoleLocalServiceUtil.getRole(companyId, NpstRoleConstant.NPST_GM).getRoleId();
//List<Long> gmRoleIds=new ArrayList<Long>();
//gmRoleIds.add(gmRoleId);

 workflowTasks= NpstCommonUtil.getWorkflowTaskByRoles(companyId, roleIds, false,false);
 
 workflowTasks.addAll(NpstCommonUtil.getWorkflowTaskByUser(companyId, NpstCommonUtil.getUserByRoleIds(companyId, roleIds), false, false));
 workflowTasks.addAll(NpstCommonUtil.getWorkflowTaskByUser(companyId, NpstCommonUtil.getUserByRoleIds(companyId, roleIds), true, false));
 //workflowTasks.addAll(getWorkflowTaskByUser(companyId, getUserByRoleIds(companyId, gmRoleIds), true, false));
	
}catch (Exception e) {
	logger.error(e.getMessage());
}
	return getErmBatchModals(workflowTasks, companyId, userId);
}

public static void updateErmStatus(long ermId,String ermStatus) {
	try {
		ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(ermId);
		logger.info("ermstatus:  "+ermStatus);
		ermInformation.setErmStatus(ermStatus);
		ermInformation=ErmInformationLocalServiceUtil.updateErmInformation(ermInformation);
	}catch (Exception e) {
		logger.error(e);
	}
}

public static void updateRemarks(long ermId,String remark,int isNpst) {
	try {
		ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(ermId);
		
		logger.info("remark:  "+remark);
		if(isNpst==1) {
			ermInformation.setNpstRemark(remark);
		}else {
			ermInformation.setNoNpstRemark(remark);	
		}
		ermInformation=ErmInformationLocalServiceUtil.updateErmInformation(ermInformation);
	}catch (Exception e) {
		logger.error(e);
	}
}



public static void forwardToNpst(long ermId,int isFwdToNpst,String ermStatus) {
	try {
		ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(ermId);
		ermInformation.setIsForwardToNpst(isFwdToNpst);
		ermInformation.setErmStatus(ermStatus);
		if(isFwdToNpst==1) {
			ermInformation.setFwdnpstDate(new Date());
		}
		
		ermInformation=ErmInformationLocalServiceUtil.updateErmInformation(ermInformation);
	}catch (Exception e) {
		logger.error(e);
	}
}

/**
 * 
 * @param requestIds
 * @param action
 * @param remarks
 * @param userId
 * @throws PortalException
 */
public static void performWorkflowTasks(String[] ermIds, String action, String remarks,String process,String recommendation, long userId,long companyId) throws PortalException {
	
	boolean isNpstUser=NpstCommonUtil.isNpstUser(userId, companyId);
	
	
	for (String ermId : ermIds) {
		
		try {
		ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
		String roles[]= {NpstRoleConstant.CHECKER,NpstRoleConstant.GRIEVANCES_CHECKER,ermInformation.getPfmName()+"-Checker"};
		boolean isChecker=NpstCommonUtil.hasPermission(roles, userId, companyId);
		
		
		/////////////////////
		
		if(isNpstUser) {
			if(recommendation!="" && recommendation!=null && !recommendation.equals(null)) {
				ermInformation.setRecommendation(recommendation);
			}
			ermInformation.setNpstRemark(remarks);
		}
		if(isNpstUser && action.equalsIgnoreCase(NpstErmConstant.NPST_REJECT_TRANS_NAME)){
		ermInformation.setBatchStatus(NpstErmConstant.BATCH_REJECTED);
			ermInformation.setBatchNo("");
		}
		if(isChecker && action.equalsIgnoreCase(NpstErmConstant.CHECKER_APPROVED_TRANS_NAME)){
			ermInformation.setIsForwardToNpst(NpstErmConstant.FORWARD_TO_NPST);	
		}
		if(isNpstUser && action.equalsIgnoreCase(NpstErmConstant.NPST_APPROVED_TRANS_NAME)){
			ermInformation.setNpstRemark(remarks);
			ermInformation.setBatchStatus(NpstErmConstant.NPST_FIRST_LEVEL_APPROVED);
		}
		if(!isNpstUser && process!="" && ermInformation.getBatchStatus()==NpstErmConstant.NPST_FIRST_LEVEL_APPROVED && action.equalsIgnoreCase(NpstErmConstant.NON_NPST_SECOND_LEVEL_APPROVE_TRANS_NAME)) {
			ermInformation.setProcess(process);
			ermInformation.setNoNpstRemark(remarks);
		}
		
		ermInformation=ErmInformationLocalServiceUtil.updateErmInformation(ermInformation);
		logger.info("#####################################");
		performWorkFlowACtion(ermInformation, action, remarks, userId);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}


/**
 * 
 * @param requestIds
 * @param action
 * @param remarks
 * @param userId
 * @throws PortalException
 */
public static void resubmitForm(long ermInformationId, String action, String remarks, long userId) throws PortalException {
	ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(ermInformationId);	
	performWorkFlowACtion(ermInformation, action, remarks, userId);
}


/**
 * 
 * @param pk
 * @param action
 * @param remarks
 * @param userId
 * @throws PortalException
 */
private static  void performWorkFlowACtion(ErmInformation ermInformation, String action, String remarks, long userId)throws PortalException {
	logger.info("performWorkFlowACtion start");

	List<Integer> logTypes_assign = new ArrayList<Integer>();
	logTypes_assign.add(WorkflowLog.TASK_ASSIGN);
	List<WorkflowLog> workflowLogs_assign =getWorkflowLogs(ermInformation.getCompanyId(),ermInformation.getGroupId(), ErmInformation.class.getName(), ermInformation.getPrimaryKey(), logTypes_assign);
	Map<String, Serializable> workflowContext=getWorkflowContext(ermInformation.getCompanyId(),ermInformation.getGroupId(), ErmInformation.class.getName(), ermInformation.getPrimaryKey());
	if (workflowLogs_assign.size() > 0) {

		//long taskId = workflowLogs_assign.get(workflowLogs_assign.size() - 1).getWorkflowTaskId();
		long taskId = workflowLogs_assign.get(0).getWorkflowTaskId();
		WorkflowTask task = WorkflowTaskManagerUtil.getWorkflowTask(ermInformation.getCompanyId(), taskId);

		List<String> nextTransitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(ermInformation.getCompanyId(), userId, taskId);
		for(String trName:nextTransitionNames) {
			logger.info("trans name::  "+trName);
		}
			if (!nextTransitionNames.contains(action)) {
				logger.info("*************************** invalid action- " + action+"    taskid:   "+taskId);
			}
			//if (task.getAssigneeUserId() <= 0) { // task is not assigned to user
				List<User> userList = WorkflowTaskManagerUtil.getAssignableUsers(ermInformation.getCompanyId(), taskId);
				if (userList.stream().anyMatch(u -> u.getUserId() == userId)) {
					WorkflowTaskManagerUtil.assignWorkflowTaskToUser(ermInformation.getCompanyId(), userId, taskId,userId, remarks, task.getDueDate(), workflowContext);
				}
				task = WorkflowTaskManagerUtil.getWorkflowTask(ermInformation.getCompanyId(), taskId);
			//}
			if (task.getAssigneeUserId() == userId) {
				logger.info("task self assignee user id:  "+task.getAssigneeUserId());
				WorkflowTaskManagerUtil.completeWorkflowTask(ermInformation.getCompanyId(), userId,taskId, action, remarks, workflowContext);
			} else {
				logger.info("Not Authenticated ......" + userId);
			}		 
	}
	logger.info("performWorkFlowACtion completed");
}

public static List<ErmModal> getErmModalsByCRA(long companyId,long userId ,String craName) throws PortalException{
	logger.info("CRA Name:  "+craName);
	List<ErmInformation> ermInformations=null;
	List<ErmModal> ermModels=new ArrayList<ErmModal>();
	if(craName.equalsIgnoreCase(NpstErmConstant.ALL_CRA) || craName==NpstErmConstant.ALL_CRA) {
		ermInformations=ErmInformationLocalServiceUtil.getErmInformations(-1, -1);
	}else {
		ermInformations=ErmInformationLocalServiceUtil.findByPfmName(craName);
	}
	for(ErmInformation ermInformation:ermInformations) {
		try {
		ermModels.add(getErmModal(ermInformation, companyId, userId));
		}catch (Exception e) {
		logger.error(e.getMessage());
	}
	}
		ermModels=ermModels.stream().filter(erm->(erm.isAssignable() && erm.getErmInformation().getBatchStatus()!=NpstErmConstant.BATCH_PENDING && erm.getErmInformation().getBatchStatus()!=NpstErmConstant.NPST_FIRST_LEVEL_APPROVED)).map(erm->erm).collect(Collectors.toList());
	logger.info("ermModels size: "+ermModels.size());
	ermModels=ermModels.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
		return ermModels;	
}

/**
 * 
 * @return
 */
public static Set<String> getDuplicatePran() {
	DynamicQuery dynamicQuery= ErmInformationLocalServiceUtil.dynamicQuery().setProjection(ProjectionFactoryUtil.property("pran"));
	List list=ErmInformationLocalServiceUtil.dynamicQuery(dynamicQuery);
	List<String>  duplicatePrans=new ArrayList<String>();
	for(Object o:list) {
		logger.debug("###########################          "+o);
		duplicatePrans.add(String.valueOf(o));
	}
	return duplicatePrans.stream().filter(i -> Collections.frequency(duplicatePrans, i) > 1).collect(Collectors.toSet());
	//Set<String>  duplicatePrans=	 list.stream().collect(Collectors.toSet());
	
//	List<ErmInformation> ermInformations=ErmInformationLocalServiceUtil.getErmInformations(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
//	List<String> prans=ermInformations.stream().map(erm->erm.getPran()).collect(Collectors.toList());
//	Set<String>  duplicatePrans=prans.stream().filter(i -> Collections.frequency(prans, i) > 1).collect(Collectors.toSet());
	//return duplicatePrans;	
}

/**
 * 
 * @param batchNo
 * @param companyId
 * @param userId
 * @return
 * @throws PortalException
 */
	public static  List<ErmModal> getErmModalBYBatchNo(String batchNo,long companyId, long userId)throws PortalException {
		logger.info("batchNo :-  "+batchNo);
		List<ErmModal> ermModals=new ArrayList<ErmModal>();
		ErmBatchInformation ermBatchInformation=ErmBatchInformationLocalServiceUtil.getErmBatchInformation(Long.valueOf(batchNo));
		//ErmBatchInformation ermBatchInformation=ErmBatchInformationLocalServiceUtil.findByBatchNo(Long.valueOf(batchNo));
		//List<ErmInformation> ermInformations=ErmInformationLocalServiceUtil.findByBatch(batchNo);
		for(String ermId:ermBatchInformation.getErmInformationIds().split(StringPool.COMMA)) {
			ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
		try {
		ermModals.add(getErmModal(ermInformation, companyId, userId));
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		}
		ermModals=ermModals.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
		return ermModals;
	}
	
		/**
		 * 
		 * @param companyId
		 * @param groupId
		 * @param userId
		 * @param pran
		 * @return
		 * @throws PortalException
		 */
		public static  List<ErmModal> getErmModalByPran(long companyId,long groupId,long userId,String pran)throws PortalException {
			List<ErmModal> ermModals=new ArrayList<ErmModal>();
			List<ErmInformation> ermInformations=ErmInformationLocalServiceUtil.findByPran(groupId, pran);
			for(ErmInformation ermInformation:ermInformations) {
			try {
				logger.info("pran: "+ermInformation.getPran());
			ermModals.add(getErmModal(ermInformation, companyId, userId));
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
			}
			ermModals=ermModals.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
			return ermModals;
		}
	
		public static  List<ErmModal> getErmByUserId(long companyId,long userId)throws PortalException {
			List<ErmModal> ermModals=new ArrayList<ErmModal>();
			List<ErmInformation> ermInformations=ErmInformationLocalServiceUtil.findByUserId(userId);
			for(ErmInformation ermInformation:ermInformations) {
			try {
			ermModals.add(getErmModal(ermInformation, companyId, userId));
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
			}
			ermModals=ermModals.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
			return ermModals;
		}
		
		
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public static List<ErmModal> getBatchCreationRecords(long companyId,long userId){
		List<ErmModal> ermModals=getErms(companyId, userId);
		ermModals=ermModals.stream().filter(erm->erm.isAssignable() && (erm.getErmInformation().getBatchStatus()!= NpstErmConstant.BATCH_PENDING)).map(erm->erm).collect(Collectors.toList());
		//ermInformation.getBatchStatus()== NpstErmConstant.BATCH_PENDING) && isNpstUser && isBatchCreation
		ermModals=ermModals.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
		return ermModals;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 * bckp 11-09-2023
	 */
	/*
	 * public static List<ErmModal> getErmStatusModal(long companyId,long userId){
	 * boolean isNpstUser=NpstCommonUtil.isNpstUser(userId, companyId);
	 * List<ErmModal> ermModals=getErms(companyId, userId); List<ErmModal>
	 * nonNpstErmModals=new ArrayList<ErmModal>();
	 * ermModals=ermModals.stream().filter(erm->erm.getErmInformation().
	 * getIsForwardToNpst()==NpstErmConstant.FORWARD_TO_NPST).map(erm->erm).collect(
	 * Collectors.toList()); if(!isNpstUser) {
	 * nonNpstErmModals=ermModals.stream().filter(erm->erm.isAssignable()).map(erm->
	 * erm).collect(Collectors.toList()); ermModals.addAll(nonNpstErmModals);
	 * ermModals=ermModals.stream().map(erm->erm).collect(Collectors.toSet()).stream
	 * ().map(erm->erm).collect(Collectors.toList()); } return ermModals; }
	 */
	
	
	public static List<ErmModal> getErmStatusModal(long companyId,long userId){
		boolean isNpstUser=NpstCommonUtil.isNpstUser(userId, companyId);
		List<ErmModal> ermModals=getErms(companyId, userId);
		//List<ErmModal> ermModals_copy=ermModals;
		//List<ErmModal> nonNpstErmModals=new ArrayList<ErmModal>();
		
		if(isNpstUser) {
//			String craName=NpstCommonUtil.getExpandoValue(companyId, userId, User.class.getName(), NpstCommonConstant.GRIVANCES_TYPE);
//			nonNpstErmModals=ermModals_copy.stream().filter(erm->erm.getIntermediaryName().equalsIgnoreCase(craName)).map(erm->erm).collect(Collectors.toList());
//			ermModals.addAll(nonNpstErmModals);
//			ermModals=ermModals.stream().map(erm->erm).collect(Collectors.toSet()).stream().map(erm->erm).collect(Collectors.toList());
			return ermModals.stream().filter(erm->erm.getErmInformation().getIsForwardToNpst()==NpstErmConstant.FORWARD_TO_NPST).map(erm->erm).collect(Collectors.toList());
		}
		ermModals=ermModals.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
		return ermModals;
	}
	
	public static List<ErmModal> getApprovedErm(long companyId,long userId) {
		List<ErmModal> ermModals=getErms(companyId, userId);
		//boolean isNpst=NpstCommonUtil.isNpstUser(userId, companyId);
		//ermModals=ermModals.stream().filter(erm->(erm.isCompleted()||(isNpst && erm.isAssignable() && erm.getErmInformation().getBatchStatus()==NpstErmConstant.NPST_FIRST_LEVEL_APPROVED))).map(erm->erm).collect(Collectors.toList());
		ermModals=ermModals.stream().filter(erm->(erm.isCompleted())).map(erm->erm).collect(Collectors.toList());
		ermModals=ermModals.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
		return ermModals;	
	}
	
	
	/**
	 * 
	 * @param companyId
	 * @param userId
	 */
	public static List<ErmModal> getAssignableErm(long companyId,long userId) {
		List<ErmModal> ermModals=getErms(companyId, userId);
		ermModals=ermModals.stream().filter(erm->erm.isAssignable()).map(erm->erm).collect(Collectors.toList());
		ermModals=ermModals.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
		return ermModals;
	}
	
	/**
	 * 
	 * @param ermInformation
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws WorkflowException
	 */
	public  static ErmModal getErmModal(ErmInformation ermInformation,long companyId,long userId) throws WorkflowException {
		String intermediaryName=StringPool.BLANK;
		//String currentlyAssignedUserName=StringPool.BLANK;
		List<Integer> logTypes_assign = new ArrayList<Integer>();
		logTypes_assign.add(WorkflowLog.TASK_ASSIGN);
		logTypes_assign.add(WorkflowLog.TASK_COMPLETION);
		List<WorkflowLog> workflowLogs_assign=getWorkflowLogs(ermInformation.getCompanyId(), ermInformation.getGroupId(), ErmInformation.class.getName(),ermInformation.getPrimaryKey(),logTypes_assign);
		
		if (workflowLogs_assign.size() > 0) {
			workflowLogs_assign=workflowLogs_assign.stream().sorted(Comparator.comparing(WorkflowLog::getWorkflowLogId).reversed()).collect(Collectors.toList());
		}		
		WorkflowTask workflowTask=WorkflowTaskManagerUtil.getWorkflowTask(companyId, workflowLogs_assign.get(0).getWorkflowTaskId());
	//	List<KaleoLog> kaleoLogsOne=getKaleoLogs(companyId, workflowTask.getWorkflowInstanceId(), "TASK_COMPLETION");
		List<KaleoLog> kaleoLogsTwo=	getKaleoLogs(companyId, workflowTask.getWorkflowInstanceId(), "TASK_ASSIGNMENT");
		
		
//		List<User> userListTest = WorkflowTaskManagerUtil.getAssignableUsers(ermInformation.getCompanyId(), workflowTask.getWorkflowTaskId());
//		
//		for(User  user:userListTest) {
//			logger.info("Assignable CRA users:  "+user.getUserId());
//		}
		
		// cra commant and NPST comment are used
		//String comment=getComment(kaleoLogsOne);
		//boolean isAssignable=isAssignable(kaleoLogsTwo.get(0).getCurrentAssigneeClassPK(), kaleoLogsTwo.get(0).getCurrentAssigneeClassName());
		boolean isAssignable=isAssignable(companyId,userId,workflowTask.getWorkflowTaskId());
		boolean isSelfAsignee=isSelfAsignee(kaleoLogsTwo, userId);
		boolean isCompleted=workflowTask.isCompleted();
		String currentlyAssignedUserName = getAssigneeName(kaleoLogsTwo.get(0).getCurrentAssigneeClassPK(), kaleoLogsTwo.get(0).getCurrentAssigneeClassName(),companyId,ermInformation.getPfmName());
		logger.info("currentlyAssignedUserName :"+currentlyAssignedUserName+"  isCompleted: "+isCompleted);
		return createErmModal(companyId, userId, ermInformation, workflowTask.getWorkflowTaskId(), "", intermediaryName, currentlyAssignedUserName, null, isSelfAsignee,isAssignable,isCompleted);
	}


/**
 * 
 * @param workflowTasks
 * @param companyId
 * @param userId
 * @return
 */
private static List<ErmBatchModal> getErmBatchModals(List<WorkflowTask> workflowTasks,long companyId,long userId ){
	List<ErmBatchModal>ermBatchModals=new ArrayList<ErmBatchModal>();
	List<Long> ermIds=new ArrayList<Long>();
	workflowTasks=workflowTasks.stream().sorted(Comparator.comparing(WorkflowTask::getWorkflowTaskId).reversed()).collect(Collectors.toList());
	ErmBatchInformation ermBatchInformation=null;
	
	for (WorkflowTask itr : workflowTasks) {
		try {
		ErmInformation ermInformation=null;
		Map<String, Serializable> maps = itr.getOptionalAttributes();
		String entryClassName = String.valueOf(maps.get("entryClassName"));
		long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
		if(!entryClassName.equalsIgnoreCase(ErmInformation.class.getName()) || ermIds.contains(applicationId)) {
			continue;	
		}
		ermInformation = ErmInformationLocalServiceUtil.getErmInformation(applicationId);
		//ermBatchInformation=ErmBatchInformationLocalServiceUtil.getErmBatchInformation(Long.valueOf(ermInformation.getBatchNo()));
		 ermBatchInformation=ErmBatchInformationLocalServiceUtil.findByBatchNo(Long.valueOf(ermInformation.getBatchNo()));
		 String batchStatus="Confirmation received from CRA";
		 for(String ermId:ermBatchInformation.getErmInformationIds().split(StringPool.COMMA)) {
			try {
			ErmInformation erm=	ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
			if(erm.getBatchStatus()!=NpstErmConstant.NPST_FIRST_LEVEL_APPROVED) {
				batchStatus="Pending for decision";
			}
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
			ermIds.add(Long.valueOf(ermId));
		}
		String dueDate = StringPool.BLANK;
		List<KaleoLog> kaleoLogsTwo=	getKaleoLogs(companyId, itr.getWorkflowInstanceId(), "TASK_ASSIGNMENT");
	String currentlyAssignedUserName = StringPool.BLANK;
	if(!kaleoLogsTwo.isEmpty()) {
		currentlyAssignedUserName = getAssigneeName(kaleoLogsTwo.get(0).getCurrentAssigneeClassPK(), kaleoLogsTwo.get(0).getCurrentAssigneeClassName(),companyId,ermInformation.getPfmName());
	}	
	boolean isSelfAsignee=isSelfAsignee(kaleoLogsTwo, userId);
	
	dueDate=NpstCommonUtil.getDateString(ermBatchInformation.getCreateDate());
	if(ermInformation.getIsForwardToNpst()==1) {
		logger.info("class name:"+entryClassName+"  application ID: "+applicationId);
	ermBatchModals.add(createErmBatchModal(companyId, userId,ermBatchInformation,itr.getWorkflowTaskId(), currentlyAssignedUserName, dueDate, isSelfAsignee,batchStatus));
	}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	ermBatchModals=ermBatchModals.stream().sorted(Comparator.comparing(ErmBatchModal::getBatchNo).reversed()).collect(Collectors.toList());
		return ermBatchModals;
}



	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws PortalException
	 */
		public static  List<ErmBatchModal> getAllErmBatchModal(long companyId, long userId)throws PortalException {
			List<ErmBatchModal> ermBatchModals=new ArrayList<ErmBatchModal>();
			
			for(ErmBatchInformation ermBatchInformation:ErmBatchInformationLocalServiceUtil.getErmBatchInformations(QueryUtil.ALL_POS,QueryUtil.ALL_POS) ) {
				logger.info("ermBatchInformation.getErmBatchInformationId()   :  "+ermBatchInformation.getErmBatchInformationId());
				//List<ErmInformation> ermInformations=ErmInformationLocalServiceUtil.findByBatch(ermBatchInformation.getErmBatchInformationId()+"");
				try {
					for(String ermId:ermBatchInformation.getErmInformationIds().split(StringPool.COMMA)) {
						try {
						ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
					//for(ErmInformation ermInformation:ermInformations) {
						
						String dueDate = StringPool.BLANK;
						String currentlyAssignedUserName=StringPool.BLANK;
						boolean isSelfAsignee=false;
					
							logger.info("ermInformation   "+ermInformation.getErmInformationId());
						WorkflowInstanceLink wil = WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(
								ermInformation.getCompanyId(), ermInformation.getGroupId(), ErmInformation.class.getName(),ermInformation.getErmInformationId());

						//List<KaleoLog> kaleoLogsOne=getKaleoLogs(companyId, wil.getWorkflowInstanceId(), "TASK_COMPLETION");
						List<KaleoLog> kaleoLogsTwo=getKaleoLogs(companyId, wil.getWorkflowInstanceId(), "TASK_ASSIGNMENT");

						//String comment=getComment(kaleoLogsOne);
						if(!kaleoLogsTwo.isEmpty()) {
						currentlyAssignedUserName = getAssigneeName(kaleoLogsTwo.get(0).getCurrentAssigneeClassPK(), kaleoLogsTwo.get(0).getCurrentAssigneeClassName(),companyId,ermInformation.getPfmName());
					}	
						 isSelfAsignee=isSelfAsignee(kaleoLogsTwo, userId);
					
						 dueDate=NpstCommonUtil.getDateString(ermBatchInformation.getCreateDate());
						ermBatchModals.add(createErmBatchModal(companyId, userId,ermBatchInformation,0, currentlyAssignedUserName, dueDate, isSelfAsignee,""));
						break;
						}catch (Exception e) {
							logger.error(e.getMessage());
						}
						}
				}catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
			ermBatchModals=ermBatchModals.stream().sorted(Comparator.comparing(ErmBatchModal::getBatchNo).reversed()).collect(Collectors.toList());
			return ermBatchModals;
		}


/**
 * 
 * @param ermInformation
 * @param workflowTaskId
 * @param comment
 * @param intermediaryName
 * @param currentlyAssignedUserName
 * @param dueDate
 * @param isSelfAsignee
 * @return
 * @throws WorkflowException 
 */
private static ErmModal createErmModal(long companyId,long userId,ErmInformation ermInformation,long workflowTaskId,String comment,String intermediaryName,String currentlyAssignedUserName,String dueDate,boolean isSelfAsignee,boolean isAssignable,boolean isCompleted) throws WorkflowException{
	
	ErmModal ermModel=new ErmModal();
	try {
		if(Validator.isNotNull(ermInformation)) {
			ermModel.setErmId(ermInformation.getErmInformationId());
			String craName=ermInformation.getPfmName().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW);
			ermModel.setIntermediaryName(craName);
		}
		try {
		//long subcriberCheck=	ErmUtil.getBusinessDaysBetween(ermInformation.getTransactionDate(), ermInformation.getDocumentationsDate());
		long subcriberCheck=	ErmUtil.getDaysBetween(ermInformation.getTransactionDate(), ermInformation.getDocumentationsDate());
		long craCheck=	ErmUtil.getBusinessDaysBetween(ermInformation.getDocumentationsDate(), ermInformation.getFwdnpstDate());
		if(subcriberCheck>7) {
			ermModel.setSubcriberCheck(true);
		}
		if(craCheck>3) {
			ermModel.setCraCheck(true);	
		}
		}catch (Exception e) {
			logger.error(e);
		}
	ermModel.setErmInformation(ermInformation);
	ermModel.setWorkflowTaskId(workflowTaskId+"");
	ermModel.setRemarks(comment);
	
	ermModel.setAssignedTo(currentlyAssignedUserName);
	ermModel.setDueDate(dueDate);	
	ermModel.setSelfAsignee(isSelfAsignee);
	ermModel.setAssignable(isAssignable);
	ermModel.setCompleted(isCompleted);
	try {
	List<String> transitionNames=WorkflowTaskManagerUtil.getNextTransitionNames(companyId, userId, workflowTaskId);
	ermModel.setTransitionNames(transitionNames);
	}catch (Exception e) {
		logger.error(e.getMessage());
	}
	}catch (Exception e) {
		logger.error(e.getMessage());
	}
	return ermModel;
}

/**
 * 
 * @param ermBatchInformation
 * @param currentlyAssignedUserName
 * @param dueDate
 * @param isSelfAsignee
 * @return
 * @throws WorkflowException 
 */
private static  ErmBatchModal createErmBatchModal(long companyId,long userId,ErmBatchInformation ermBatchInformation,long workflowTaskId,String currentlyAssignedUserName,String dueDate, boolean isSelfAsignee,String batchStatus) throws WorkflowException{
	ErmBatchModal ermBatchModal=new ErmBatchModal();
	List<String> transitionNames=new ArrayList<String>();
	try {
	ermBatchModal.setErmBatchInformation(ermBatchInformation);
	logger.info("batch ermIDS:"+ermBatchInformation.getErmInformationIds());
	//ermBatchModal.setWorkflowTaskId(itr.getWorkflowTaskId()+"");
	ermBatchModal.setAssignedTo(currentlyAssignedUserName);
	ermBatchModal.setBatchNo(ermBatchInformation.getBatchNo());
	ermBatchModal.setDueDate(dueDate);
	String ermIds[]=ermBatchInformation.getErmInformationIds().split(StringPool.COMMA);
	ermBatchModal.setReceivedCaseNo(ermIds.length);
	ermBatchModal.setSelfAsignee(isSelfAsignee);
	ermBatchModal.setBatchStatus(batchStatus);
	}catch (Exception e) {
		logger.error(e.getMessage());
	}
	try {
	transitionNames=WorkflowTaskManagerUtil.getNextTransitionNames(companyId, userId, workflowTaskId);
	}catch (Exception e) {
		logger.error(e.getMessage());
	}
	ermBatchModal.setTransitionNames(transitionNames);
	return ermBatchModal;	
}

/**
 * 
 * @param companyId
 * @param userId
 * @return
 */
private static  List<ErmModal> getErms(long companyId,long userId) {
	List<ErmModal> ermModals=new ArrayList<ErmModal>();
	List<ErmInformation> ermInformations=null;
	boolean isNpstUser=NpstCommonUtil.isNpstUser(userId, companyId);
	/////////////////////////////////
	if(isNpstUser) {
		 ermInformations=ErmInformationLocalServiceUtil.getErmInformations(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}else {
		String craName=NpstCommonUtil.getExpandoValue(companyId, userId, User.class.getName(), NpstCommonConstant.GRIVANCES_TYPE);
		 ermInformations=ErmInformationLocalServiceUtil.findByPfmName(craName);
	}
	/////////////////////////////
	
	for(ErmInformation ermInformation: ermInformations) {
		try {
			logger.info("pran no.  :-"+ermInformation.getPran()+"   pfm:  "+ermInformation.getPfmName());
			ermModals.add(getErmModal(ermInformation,companyId,userId));
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	ermModals=ermModals.stream().sorted(Comparator.comparing(ErmModal::getErmId).reversed()).collect(Collectors.toList());
	return ermModals;
}

/**
 * 
 * @param batchId
 * @return
 */
public static String getReceiptDates(long batchId) {
	Set<String> set=new HashSet<String>();
	String receiptDate="";
	try {
	ErmBatchInformation batchInformation=ErmBatchInformationLocalServiceUtil.getErmBatchInformation(batchId);

	for(String ermId:batchInformation.getErmInformationIds().split(StringPool.COMMA)) {
		try {
		ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
		set.add(NpstCommonUtil.getDateString(ermInformation.getCreateDate()));
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	for(String str:set) {
		receiptDate=receiptDate+" "+str+", ";
	}
	}catch (Exception e) {
		logger.error(e.getMessage());
	}
	return receiptDate;
	}


/**
 * 
 * @param companyId
 * @param userId
 * @param taskId
 * @return
 */
public static boolean isAssignable(long companyId,long userId, long taskId) {
	try {
	List<User> userList = WorkflowTaskManagerUtil.getAssignableUsers(companyId, taskId);
	if (userList.stream().anyMatch(u -> u.getUserId() == userId)) {
		return true;
	}}catch (Exception e) {
		logger.error(e.getMessage());
	}
	return false;
}


/**
 * 
 * @param classPK
 * @param className
 * @return
 */
	/*
	 * public static boolean isAssignable(long classPK, String className) {
	 * logger.info("Assignable user class PK:  "+classPK); ServiceContext
	 * serviceContext=ServiceContextThreadLocal.getServiceContext();
	 * if(className.equalsIgnoreCase(Role.class.getName())) { long[] userIds =
	 * UserLocalServiceUtil.getRoleUserIds(classPK); for(long roleUserId:userIds) {
	 * if(roleUserId==serviceContext.getUserId()) { return true; } } }else
	 * if(className.equalsIgnoreCase(User.class.getName())) {
	 * if(serviceContext.getUserId()==classPK) { return true; } }
	 * 
	 * return false; }
	 */

/**
 * 
 * @param classPK
 * @param className
 * @return
 */
public static String getAssigneeName(long classPK, String className,long companyId,String craName) {
	long userId = 0;
	String assigneeName="";
	if(className.equalsIgnoreCase(Role.class.getName())) {
		long[] userIds = UserLocalServiceUtil.getRoleUserIds(classPK);
		userId = userIds.length > 0 ? userIds[0] : 0;
	}else if(className.equalsIgnoreCase(User.class.getName())) {
		userId = classPK;
	}
	if(userId > 0) {
		try {
			if(NpstCommonUtil.isNpstUser(userId, companyId)) {
				assigneeName= "NPST";
			}else if(RoleLocalServiceUtil.hasUserRole(userId, companyId, craName+"-Checker", false)) {
				assigneeName=craName.replace(NameMappingConstants.CAMS_OLD,NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW)+"-Checker";
			}else if(RoleLocalServiceUtil.hasUserRole(userId, companyId, craName+"-Maker", false)) {
				assigneeName=craName.replace(NameMappingConstants.CAMS_OLD,NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW)+"-Maker";
			}
			else {
				logger.info("class pk : "+classPK+ "classname: "+className+"   error");
			}
			//return UserLocalServiceUtil.getUser(userId).getFullName();
		} catch (PortalException e) {
			logger.error("Exception on getting user name : "+e.getMessage());
		}
	}
	
	return assigneeName;
}


	
	/**
	 * 
	 * @param companyId
	 * @param workflowInstanceId
	 * @param logType
	 * @return
	 */
	private static List<KaleoLog> getKaleoLogs(long companyId, long workflowInstanceId,String logType) {
		OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
		List<Integer> logTypesOne = new ArrayList<>();
		logTypesOne.add(KaleoLogUtil.convert(logType));
		
		List<KaleoLog> kaleoLogs= KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, workflowInstanceId, logTypesOne, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
		kaleoLogs=kaleoLogs.stream().sorted(Comparator.comparing(KaleoLog::getKaleoLogId).reversed()).collect(Collectors.toList());
		return kaleoLogs;
	}

	/**
	 * 
	 * @param companyId
	 * @param groupId
	 * @param className
	 * @param pk
	 * @param logTypes
	 * @return
	 */
	private static List<WorkflowLog> getWorkflowLogs(long companyId,long groupId,String className,long pk,List<Integer> logTypes){
		List<WorkflowLog> workflowLogs_assign=null;
		try {
		WorkflowInstanceLink wil = WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(
				companyId, groupId, className,pk);

		 workflowLogs_assign = WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				companyId, wil.getWorkflowInstanceId(), logTypes, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));
		 workflowLogs_assign=workflowLogs_assign.stream().sorted(Comparator.comparing(WorkflowLog::getWorkflowLogId).reversed()).collect(Collectors.toList());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return workflowLogs_assign;
	}
	
	/**
	 * 
	 * @param kaleoLogs
	 * @return
	 */
	private static String getComment(List<KaleoLog> kaleoLogs) {
		String comment=StringPool.BLANK;
		if(!kaleoLogs.isEmpty() && kaleoLogs.get(0).getComment() != null && !kaleoLogs.get(0).getComment().isEmpty()) {
			comment = kaleoLogs.get(0).getComment();
		}
		return comment;
	}

	/**
	 * 
	 * @param kaleoLogs
	 * @param userId
	 * @return
	 */
	private static boolean isSelfAsignee(List<KaleoLog> kaleoLogs,long userId) {
		boolean isSelfAsignee=false;
		if(kaleoLogs.get(0).getCurrentAssigneeClassName().equalsIgnoreCase(User.class.getName()) && userId==kaleoLogs.get(0).getCurrentAssigneeClassPK()) {
			isSelfAsignee=true;
		}	
		return isSelfAsignee;
	}
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

/**
 * 
 * @param companyId
 * @param groupId
 * @param className
 * @param pk
 * @return
 * @throws PortalException
 */
private static Map<String, Serializable> getWorkflowContext(long companyId,long groupId,String className,long pk) throws PortalException{
	WorkflowInstanceLink wil = WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(
			companyId, groupId, className,pk);
	WorkflowInstance workflowInstance = WorkflowInstanceManagerUtil.getWorkflowInstance(companyId, wil.getWorkflowInstanceId());
	return workflowInstance.getWorkflowContext();
}


/**
 * 
 * @param kaleoLogsTwo
 * @return
 */
//private static String getUserName(List<KaleoLog> kaleoLogsTwo ) {
//	String userName = "NA";
//	if(kaleoLogsTwo.size() >= 2) {
//		if(kaleoLogsTwo.get(1).getCurrentAssigneeClassName().equals(User.class.getName())) {
//			userName = kaleoLogsTwo.get(1).getUserName();
//		} else if(kaleoLogsTwo.size() >= 3 && kaleoLogsTwo.get(2).getCurrentAssigneeClassName().equals(User.class.getName())) {
//			userName = kaleoLogsTwo.get(2).getUserName();
//		}
//	}
//	if(userName.equals("NA") && !kaleoLogsTwo.isEmpty()) {
//		userName = kaleoLogsTwo.get(0).getUserName();
//	}
//	return userName;
//}


/**
 * 
 * @param portletRequest
 * @param themeDisplay
 * @return
 * @throws Exception
 */
//@SuppressWarnings("deprecation")
//public static String getWorkflowUrl(HttpServletRequest httpRequest, ThemeDisplay themeDisplay,String workflowTaskId,WindowState windowState) throws Exception {
//    PortletURL renderURL = PortletURLFactoryUtil.create(httpRequest, PortletKeys.MY_WORKFLOW_TASK, PortletRequest.RENDER_PHASE);
//
//    renderURL.setWindowState(windowState);
//   
//    renderURL.setPortletMode(PortletMode.VIEW);
//    renderURL.setParameter("mvcPath", "/edit_workflow_task.jsp");
//    renderURL.setParameter("backURL", themeDisplay.getURLCurrent());
//    renderURL.setParameter("workflowTaskId", workflowTaskId);
//
//    String workflowUrl = renderURL.toString();
//    String[] url = workflowUrl.split("\\?");
//    String panelUrl = "/web" + themeDisplay.getScopeGroup().getFriendlyURL() + "/manage";
//    String queryParam = url[1];
//
//    workflowUrl = panelUrl + "?" + queryParam;
//    return workflowUrl;
//}


public static void sendEmailToERMUsers(String status,long id,String roleName,long companyId) {
	
	logger.info("Email send to user                status: "+status +  "  id:   "+id+"  role name :   "+roleName);
	
}




	private static Log logger = LogFactoryUtil.getLog(ErmUtil.class);
}
