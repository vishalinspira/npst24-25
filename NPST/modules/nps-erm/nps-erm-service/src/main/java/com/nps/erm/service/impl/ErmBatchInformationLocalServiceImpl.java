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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.nps.erm.exception.NoSuchErmBatchInformationException;
import com.nps.erm.model.ErmBatchInformation;
import com.nps.erm.model.ErmInformation;
import com.nps.erm.service.ErmBatchInformationLocalServiceUtil;
import com.nps.erm.service.ErmInformationLocalServiceUtil;
import com.nps.erm.service.base.ErmBatchInformationLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the erm batch information local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.nps.erm.service.ErmBatchInformationLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ErmBatchInformationLocalServiceBaseImpl
 */

  @Component( property =
  "model.class.name=com.nps.erm.model.ErmBatchInformation"
  //,service = AopService.class 
  )
 
public class ErmBatchInformationLocalServiceImpl
	extends ErmBatchInformationLocalServiceBaseImpl {

public ErmBatchInformation addErmBatchInformation(String ermInformationIds,String batchType,Date batchTimePeriodFrom,Date batchTimePeriodTo,Date cutOffDate,String stipulatedTime,String remark ,long previousBatchId){
	ErmBatchInformation ermBatchInformation=null;
	try {
		Date date = new Date();
	    ermBatchInformation =ErmBatchInformationLocalServiceUtil.createErmBatchInformation(CounterLocalServiceUtil.increment(ErmBatchInformation.class.getName()));;
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
	
		ermBatchInformation.setModifiedDate(date);
		ermBatchInformation.setCreateDate(date);
		User user=UserLocalServiceUtil.getUser(serviceContext.getUserId());
	
		ermBatchInformation.setUserId(serviceContext.getUserId());
		ermBatchInformation.setGroupId(serviceContext.getScopeGroupId());
		ermBatchInformation.setCompanyId(serviceContext.getCompanyId());
		ermBatchInformation.setUserName(user.getFullName());
		
		ermBatchInformation.setBatchType(batchType);
		ermBatchInformation.setBatchTimePeriodFrom(batchTimePeriodFrom);
		ermBatchInformation.setBatchTimePeriodTo(batchTimePeriodTo);
		ermBatchInformation.setPreviousBatchId(previousBatchId);
		ermBatchInformation.setCutOffDate(cutOffDate);
		ermBatchInformation.setErmInformationIds(ermInformationIds);
		//ermBatchInformation.setStipulatedTime(stipulatedTime);
		ermBatchInformation.setRemark(remark);
		
		ermBatchInformation=ErmBatchInformationLocalServiceUtil.addErmBatchInformation(ermBatchInformation);	
	
	}catch (Exception e) {
		log.error(e.getMessage());
	}
	return ermBatchInformation;
}

public ErmBatchInformation findByBatchNo(long batchNo) {
	try {
		return ermBatchInformationPersistence.findByermBatchByBatchNo(batchNo);
	} catch (NoSuchErmBatchInformationException e) {
		log.error(e.getMessage());
	}
	return null;
}

public ErmBatchInformation deleteErmBatch(long ermBatchInformationId,int batchStatus){
	ErmBatchInformation batchInformation=null;
	try {
		batchInformation=ErmBatchInformationLocalServiceUtil.getErmBatchInformation(ermBatchInformationId);
		for(String ermId: batchInformation.getErmInformationIds().split(StringPool.COMMA)) {
			try {
			ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
			ermInformation.setBatchNo("");
			ermInformation.setBatchStatus(batchStatus);
			ErmInformationLocalServiceUtil.updateErmInformation(ermInformation);
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		batchInformation=ErmBatchInformationLocalServiceUtil.deleteErmBatchInformation(ermBatchInformationId);
	}catch (Exception e) {
		log.error(e.getMessage());
	}
	return batchInformation;
}

Log log=LogFactoryUtil.getLog(ErmBatchInformationLocalServiceImpl.class.getName());
}