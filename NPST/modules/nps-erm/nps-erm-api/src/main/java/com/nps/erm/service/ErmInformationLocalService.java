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

package com.nps.erm.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.nps.erm.model.ErmInformation;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ErmInformation. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ErmInformationLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ErmInformationLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.nps.erm.service.impl.ErmInformationLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the erm information local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ErmInformationLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the erm information to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermInformation the erm information
	 * @return the erm information that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ErmInformation addErmInformation(ErmInformation ermInformation);

	public ErmInformation addErmInformation(
		long ermInformationId, String batchType, long transactedAmount,
		String pran, Date transactionDate, String transactionMode,
		Date transactionSettlementDate, String tokenNo,
		String rectificationRequestMode, Date rectificationDate,
		long remittedAmount, Date remittedDate, String tierType,
		String transactionType, long transferAmount, String transferAmount1,
		String transferAmount2, Date documentationsDate,
		long rectificationAmount, String caseNo, String subscriberName,
		String enpsOrderId, String rectificationType,
		Date rectificationRequestDate, Date grievanceReceivedDate,
		String grievanceText, String stipulated, String remark,
		long selfDeclarationFileId, long accountStatementFileId,
		long transactionsStatementFileId, long documentNameFileId,
		String pfmName);

	/**
	 * Creates a new erm information with the primary key. Does not add the erm information to the database.
	 *
	 * @param ermInformationId the primary key for the new erm information
	 * @return the new erm information
	 */
	@Transactional(enabled = false)
	public ErmInformation createErmInformation(long ermInformationId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the erm information from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermInformation the erm information
	 * @return the erm information that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public ErmInformation deleteErmInformation(ErmInformation ermInformation);

	/**
	 * Deletes the erm information with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermInformationId the primary key of the erm information
	 * @return the erm information that was removed
	 * @throws PortalException if a erm information with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ErmInformation deleteErmInformation(long ermInformationId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmInformationModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmInformationModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ErmInformation fetchErmInformation(long ermInformationId);

	/**
	 * Returns the erm information matching the UUID and group.
	 *
	 * @param uuid the erm information's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erm information, or <code>null</code> if a matching erm information could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ErmInformation fetchErmInformationByUuidAndGroupId(
		String uuid, long groupId);

	public List<ErmInformation> findByBatch(String batchNo);

	public List<ErmInformation> findByBatchNoAndBatchStatus(
		String batchNo, int batchStatus);

	public List<ErmInformation> findByPfmName(String pfmName);

	public List<ErmInformation> findByPran(long groupId, String pran);

	public List<ErmInformation> findByUserId(long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the erm information with the primary key.
	 *
	 * @param ermInformationId the primary key of the erm information
	 * @return the erm information
	 * @throws PortalException if a erm information with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ErmInformation getErmInformation(long ermInformationId)
		throws PortalException;

	/**
	 * Returns the erm information matching the UUID and group.
	 *
	 * @param uuid the erm information's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erm information
	 * @throws PortalException if a matching erm information could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ErmInformation getErmInformationByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the erm informations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmInformationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erm informations
	 * @param end the upper bound of the range of erm informations (not inclusive)
	 * @return the range of erm informations
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ErmInformation> getErmInformations(int start, int end);

	/**
	 * Returns all the erm informations matching the UUID and company.
	 *
	 * @param uuid the UUID of the erm informations
	 * @param companyId the primary key of the company
	 * @return the matching erm informations, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ErmInformation> getErmInformationsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of erm informations matching the UUID and company.
	 *
	 * @param uuid the UUID of the erm informations
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of erm informations
	 * @param end the upper bound of the range of erm informations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching erm informations, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ErmInformation> getErmInformationsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ErmInformation> orderByComparator);

	/**
	 * Returns the number of erm informations.
	 *
	 * @return the number of erm informations
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getErmInformationsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the erm information in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermInformation the erm information
	 * @return the erm information that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ErmInformation updateErmInformation(ErmInformation ermInformation);

	public ErmInformation updateErmInformation(
		long ermInformationId, String batchType, long transactedAmount,
		String pran, Date transactionDate, String transactionMode,
		Date transactionSettlementDate, String tokenNo,
		String rectificationRequestMode, Date rectificationDate,
		long remittedAmount, Date remittedDate, String tierType,
		String transactionType, long transferAmount, String transferAmount1,
		String transferAmount2, Date documentationsDate,
		long rectificationAmount, String caseNo, String subscriberName,
		String enpsOrderId, String rectificationType,
		Date rectificationRequestDate, Date grievanceReceivedDate,
		String grievanceText, String stipulated, String remark,
		long selfDeclarationFileId, long accountStatementFileId,
		long transactionsStatementFileId, long documentNameFileId);

	public ErmInformation updateStatus(
		long userId, long ermInformationId, int status,
		ServiceContext serviceContext);

}