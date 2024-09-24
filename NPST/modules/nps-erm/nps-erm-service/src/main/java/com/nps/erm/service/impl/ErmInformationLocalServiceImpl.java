/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.nps.erm.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.nps.erm.model.ErmInformation;
import com.nps.erm.service.ErmInformationLocalServiceUtil;
import com.nps.erm.service.base.ErmInformationLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the erm information local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>com.nps.erm.service.ErmInformationLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ErmInformationLocalServiceBaseImpl
 */

  @Component(property = "model.class.name=com.nps.erm.model.ErmInformation"
  //,service = AopService.class
		  )
 
public class ErmInformationLocalServiceImpl extends ErmInformationLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use
	 * <code>com.nps.erm.service.ErmInformationLocalService</code> via injection or
	 * a <code>org.osgi.util.tracker.ServiceTracker</code> or use
	 * <code>com.nps.erm.service.ErmInformationLocalServiceUtil</code>.
	 */

	public ErmInformation addErmInformation(long ermInformationId, String batchType, long transactedAmount,
			String pran, Date transactionDate, String transactionMode, Date transactionSettlementDate, String tokenNo,
			String rectificationRequestMode, Date rectificationDate, long remittedAmount, Date remittedDate,
			String tierType, String transactionType, long transferAmount,String transferAmount1,String transferAmount2, Date documentationsDate,
			long rectificationAmount, String caseNo, String subscriberName, String enpsOrderId,
			String rectificationType, Date rectificationRequestDate, Date grievanceReceivedDate, String grievanceText,
			String stipulated, String remark, long selfDeclarationFileId, long accountStatementFileId,
			long transactionsStatementFileId, long documentNameFileId,String pfmName) {
		ErmInformation ermInformation = null;
		try {
			Date date = new Date();
			
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			
			  if (ermInformationId > 0) { 
				  ermInformation = ErmInformationLocalServiceUtil.getErmInformation(ermInformationId);
			  ermInformation.setModifiedDate(date); 
			  } else { 
				  ermInformation = ErmInformationLocalServiceUtil.createErmInformation(CounterLocalServiceUtil.increment(ErmInformation.class.getName()));
			  ermInformation.setModifiedDate(date); ermInformation.setCreateDate(date); }
			 
			
			ermInformation = ErmInformationLocalServiceUtil.createErmInformation(
					CounterLocalServiceUtil.increment(ErmInformation.class.getName()));
			ermInformation.setModifiedDate(date);
			ermInformation.setCreateDate(date);
			User user=UserLocalServiceUtil.getUser(serviceContext.getUserId());
		
			ermInformation.setUserId(serviceContext.getUserId());
			ermInformation.setGroupId(serviceContext.getScopeGroupId());
			ermInformation.setCompanyId(serviceContext.getCompanyId());
			ermInformation.setUserName(user.getFullName());
			
			ermInformation.setBatchType(batchType);
			ermInformation.setTransactedAmount(transactedAmount);
			ermInformation.setPran(pran);
			ermInformation.setTransactionDate(transactionDate);
			ermInformation.setTransactionMode(transactionMode);
			ermInformation.setTransactionSettlementDate(transactionSettlementDate);
			ermInformation.setTokenNo(tokenNo);
			ermInformation.setRectificationRequestMode(rectificationRequestMode);
			ermInformation.setRectificationDate(rectificationDate);
			ermInformation.setRemittedAmount(remittedAmount);
			ermInformation.setRemittedDate(remittedDate);
			ermInformation.setTierType(tierType);
			ermInformation.setTransactionType(transactionType);
			ermInformation.setTransferAmount(transferAmount);
			ermInformation.setTransferAmount1(transferAmount1);
			ermInformation.setTransferAmount2(transferAmount2);
			ermInformation.setDocumentationsDate(documentationsDate);
			ermInformation.setRectificationAmount(rectificationAmount);
			ermInformation.setCaseNo(caseNo);
			ermInformation.setSubscriberName(subscriberName);
			ermInformation.setEnpsOrderId(enpsOrderId);
			ermInformation.setRectificationType(rectificationType);
			ermInformation.setRectificationRequestDate(rectificationRequestDate);
			ermInformation.setGrievanceReceivedDate(grievanceReceivedDate);
			ermInformation.setGrievanceText(grievanceText);
			ermInformation.setStipulated(stipulated);
			ermInformation.setRemark(remark);
			ermInformation.setSelfDeclarationFileId(selfDeclarationFileId);
			ermInformation.setAccountStatementFileId(accountStatementFileId);
			ermInformation.setTransactionsStatementFileId(transactionsStatementFileId);
			ermInformation.setDocumentNameFileId(documentNameFileId);
			ermInformation.setPfmName(pfmName);;
			
			ermInformation.setStatus(WorkflowConstants.STATUS_DRAFT);
			ermInformation.setStatusByUserId(user.getUserId());
			ermInformation.setStatusDate(date);
			ermInformation.setStatusByUserName(user.getFullName());
			ermInformation.setStatusByUserUuid(user.getUserUuid());
	        
	        
			ermInformation=	ErmInformationLocalServiceUtil.addErmInformation(ermInformation);
			
//			resourceLocalService.addResources(user.getCompanyId(), serviceContext.getScopeGroupId(), user.getUserId(), ErmInformation.class.getName(),
//					ermInformation.getErmInformationId(), false, true, true);
			


			 AssetEntry assetEntry = assetEntryLocalService.updateEntry( serviceContext.getUserId(), serviceContext.getScopeGroupId(), date,
			            date, ErmInformation.class.getName(),ermInformation.getErmInformationId(), ermInformation.getUuid(), 0, serviceContext.getAssetCategoryIds(), serviceContext.getAssetTagNames(), true, true, date, null,
			            date, null, ContentTypes.TEXT_HTML, ermInformation.getPran(), ermInformation.getPran(), null, null, null, 0, 0, null);
			       Indexer<ErmInformation> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ErmInformation.class);
			       indexer.reindex(ermInformation);
			       
			 
			       WorkflowHandlerRegistryUtil.startWorkflowInstance(ermInformation.getCompanyId(), ermInformation.getGroupId(), ermInformation.getUserId(), ErmInformation.class.getName(),
			    		   ermInformation.getPrimaryKey(), ermInformation, serviceContext);
			
			
			
			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ermInformation;

	}
	
	public ErmInformation updateErmInformation(long ermInformationId, String batchType, long transactedAmount,
			String pran, Date transactionDate, String transactionMode, Date transactionSettlementDate, String tokenNo,
			String rectificationRequestMode, Date rectificationDate, long remittedAmount, Date remittedDate,
			String tierType, String transactionType, long transferAmount,String transferAmount1,String transferAmount2, Date documentationsDate,
			long rectificationAmount, String caseNo, String subscriberName, String enpsOrderId,
			String rectificationType, Date rectificationRequestDate, Date grievanceReceivedDate, String grievanceText,
			String stipulated, String remark, long selfDeclarationFileId, long accountStatementFileId,
			long transactionsStatementFileId, long documentNameFileId) {
		ErmInformation ermInformation = null;
		try {
			Date date = new Date();
			
		//	ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			
			  if (ermInformationId > 0) { ermInformation =
					  ErmInformationLocalServiceUtil.getErmInformation(ermInformationId);
//			  ermInformation.setModifiedDate(date); } else { ermInformation =
//			  ermInformationLocalService.createErmInformation(
//			  CounterLocalServiceUtil.increment(ErmInformation.class.getName()));
			  ermInformation.setModifiedDate(date); 
			  //ermInformation.setCreateDate(date); 
			  }
			 

			//User user=UserLocalServiceUtil.getUser(serviceContext.getUserId());
		
//			ermInformation.setUserId(serviceContext.getUserId());
//			ermInformation.setGroupId(serviceContext.getScopeGroupId());
//			ermInformation.setCompanyId(serviceContext.getCompanyId());
//			ermInformation.setUserName(user.getFullName());
			
			ermInformation.setBatchType(batchType);
			ermInformation.setTransactedAmount(transactedAmount);
			ermInformation.setPran(pran);
			ermInformation.setTransactionDate(transactionDate);
			ermInformation.setTransactionMode(transactionMode);
			ermInformation.setTransactionSettlementDate(transactionSettlementDate);
			ermInformation.setTokenNo(tokenNo);
			ermInformation.setRectificationRequestMode(rectificationRequestMode);
			ermInformation.setRectificationDate(rectificationDate);
			ermInformation.setRemittedAmount(remittedAmount);
			ermInformation.setRemittedDate(remittedDate);
			ermInformation.setTierType(tierType);
			ermInformation.setTransactionType(transactionType);
			ermInformation.setTransferAmount(transferAmount);
			ermInformation.setTransferAmount1(transferAmount1);
			ermInformation.setTransferAmount2(transferAmount2);
			ermInformation.setDocumentationsDate(documentationsDate);
			ermInformation.setRectificationAmount(rectificationAmount);
			ermInformation.setCaseNo(caseNo);
			ermInformation.setSubscriberName(subscriberName);
			ermInformation.setEnpsOrderId(enpsOrderId);
			ermInformation.setRectificationType(rectificationType);
			ermInformation.setRectificationRequestDate(rectificationRequestDate);
			ermInformation.setGrievanceReceivedDate(grievanceReceivedDate);
			ermInformation.setGrievanceText(grievanceText);
			ermInformation.setStipulated(stipulated);
			ermInformation.setRemark(remark);
			ermInformation.setSelfDeclarationFileId(selfDeclarationFileId);
			ermInformation.setAccountStatementFileId(accountStatementFileId);
			ermInformation.setTransactionsStatementFileId(transactionsStatementFileId);
			ermInformation.setDocumentNameFileId(documentNameFileId);
			     
			ermInformation=	ErmInformationLocalServiceUtil.updateErmInformation(ermInformation);
				
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ermInformation;

	}
	

	
	
	
	public ErmInformation updateStatus(long userId,long ermInformationId,int status,ServiceContext serviceContext){
		 
		ErmInformation ermInformation = ermInformationPersistence.fetchByPrimaryKey(ermInformationId);
		 ermInformation.setStatus(status);
		 ermInformation.setStatusByUserId(userId);
		 ermInformation.setStatusDate(new Date());
		 User user = null;
		 try {
		      user = userLocalService.getUser(userId);
		      ermInformation.setStatusByUserName(user.getFullName());
		       ermInformation.setStatusByUserUuid(user.getUserUuid());
		 } catch (PortalException e) {
		     e.printStackTrace();
		 }
		  ermInformation = ermInformationPersistence.update(ermInformation);
		 try {
		 if (status == WorkflowConstants.STATUS_APPROVED) {  
		     // update the asset status to visibile
		    assetEntryLocalService.updateEntry(ErmInformation.class.getName(), ermInformationId, new Date(),null, true, true);
		 } else {
		     // set leave entity status to false
		     assetEntryLocalService.updateVisible(ErmInformation.class.getName(), ermInformationId, false);  
		 }
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		 return ermInformation;
		 }
	
	public List<ErmInformation> findByBatchNoAndBatchStatus(String batchNo,int batchStatus){
		return ermInformationPersistence.findBybatchNoAndBatchStatus(batchNo, batchStatus);

	}
	
	public List<ErmInformation> findByBatch(String batchNo){
		return ermInformationPersistence.findBybatchNo(batchNo);
	}
	
	public List<ErmInformation> findByPran(long groupId,String pran){
		return ermInformationPersistence.findBypran(groupId, pran);
	}
	
	
	public List<ErmInformation> findByUserId(long userId){
		return ermInformationPersistence.findByuserId(userId);

	}
	
	public List<ErmInformation> findByPfmName(String pfmName){
		return ermInformationPersistence.findBypfmName(pfmName);

	}
	Log logger = LogFactoryUtil.getLog(ErmInformationLocalServiceImpl.class);

}