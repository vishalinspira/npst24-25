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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.nps.erm.model.ErmBatchInformation;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ErmBatchInformation. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ErmBatchInformationLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ErmBatchInformationLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.nps.erm.service.impl.ErmBatchInformationLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the erm batch information local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ErmBatchInformationLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the erm batch information to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmBatchInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermBatchInformation the erm batch information
	 * @return the erm batch information that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ErmBatchInformation addErmBatchInformation(
		ErmBatchInformation ermBatchInformation);

	public ErmBatchInformation addErmBatchInformation(
		String ermInformationIds, String batchType, Date batchTimePeriodFrom,
		Date batchTimePeriodTo, Date cutOffDate, String stipulatedTime,
		String remark, long previousBatchId);

	/**
	 * Creates a new erm batch information with the primary key. Does not add the erm batch information to the database.
	 *
	 * @param ermBatchInformationId the primary key for the new erm batch information
	 * @return the new erm batch information
	 */
	@Transactional(enabled = false)
	public ErmBatchInformation createErmBatchInformation(
		long ermBatchInformationId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public ErmBatchInformation deleteErmBatch(
		long ermBatchInformationId, int batchStatus);

	/**
	 * Deletes the erm batch information from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmBatchInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermBatchInformation the erm batch information
	 * @return the erm batch information that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public ErmBatchInformation deleteErmBatchInformation(
		ErmBatchInformation ermBatchInformation);

	/**
	 * Deletes the erm batch information with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmBatchInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermBatchInformationId the primary key of the erm batch information
	 * @return the erm batch information that was removed
	 * @throws PortalException if a erm batch information with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ErmBatchInformation deleteErmBatchInformation(
			long ermBatchInformationId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmBatchInformationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmBatchInformationModelImpl</code>.
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
	public ErmBatchInformation fetchErmBatchInformation(
		long ermBatchInformationId);

	/**
	 * Returns the erm batch information matching the UUID and group.
	 *
	 * @param uuid the erm batch information's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erm batch information, or <code>null</code> if a matching erm batch information could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ErmBatchInformation fetchErmBatchInformationByUuidAndGroupId(
		String uuid, long groupId);

	public ErmBatchInformation findByBatchNo(long batchNo);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the erm batch information with the primary key.
	 *
	 * @param ermBatchInformationId the primary key of the erm batch information
	 * @return the erm batch information
	 * @throws PortalException if a erm batch information with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ErmBatchInformation getErmBatchInformation(
			long ermBatchInformationId)
		throws PortalException;

	/**
	 * Returns the erm batch information matching the UUID and group.
	 *
	 * @param uuid the erm batch information's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erm batch information
	 * @throws PortalException if a matching erm batch information could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ErmBatchInformation getErmBatchInformationByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the erm batch informations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmBatchInformationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erm batch informations
	 * @param end the upper bound of the range of erm batch informations (not inclusive)
	 * @return the range of erm batch informations
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ErmBatchInformation> getErmBatchInformations(
		int start, int end);

	/**
	 * Returns all the erm batch informations matching the UUID and company.
	 *
	 * @param uuid the UUID of the erm batch informations
	 * @param companyId the primary key of the company
	 * @return the matching erm batch informations, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ErmBatchInformation> getErmBatchInformationsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of erm batch informations matching the UUID and company.
	 *
	 * @param uuid the UUID of the erm batch informations
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of erm batch informations
	 * @param end the upper bound of the range of erm batch informations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching erm batch informations, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ErmBatchInformation> getErmBatchInformationsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ErmBatchInformation> orderByComparator);

	/**
	 * Returns the number of erm batch informations.
	 *
	 * @return the number of erm batch informations
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getErmBatchInformationsCount();

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
	 * Updates the erm batch information in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmBatchInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermBatchInformation the erm batch information
	 * @return the erm batch information that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ErmBatchInformation updateErmBatchInformation(
		ErmBatchInformation ermBatchInformation);

}