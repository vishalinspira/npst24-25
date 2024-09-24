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

package com.nps.manpower.service;

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

import com.nps.manpower.model.ChangeShareHolding;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ChangeShareHolding. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeShareHoldingLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ChangeShareHoldingLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.nps.manpower.service.impl.ChangeShareHoldingLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the change share holding local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ChangeShareHoldingLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the change share holding to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeShareHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeShareHolding the change share holding
	 * @return the change share holding that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ChangeShareHolding addChangeShareHolding(
		ChangeShareHolding changeShareHolding);

	/**
	 * Creates a new change share holding with the primary key. Does not add the change share holding to the database.
	 *
	 * @param changeShareHoldingId the primary key for the new change share holding
	 * @return the new change share holding
	 */
	@Transactional(enabled = false)
	public ChangeShareHolding createChangeShareHolding(
		long changeShareHoldingId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the change share holding from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeShareHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeShareHolding the change share holding
	 * @return the change share holding that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public ChangeShareHolding deleteChangeShareHolding(
		ChangeShareHolding changeShareHolding);

	/**
	 * Deletes the change share holding with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeShareHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeShareHoldingId the primary key of the change share holding
	 * @return the change share holding that was removed
	 * @throws PortalException if a change share holding with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ChangeShareHolding deleteChangeShareHolding(
			long changeShareHoldingId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeShareHoldingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeShareHoldingModelImpl</code>.
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
	public ChangeShareHolding fetchChangeShareHolding(
		long changeShareHoldingId);

	/**
	 * Returns the change share holding matching the UUID and group.
	 *
	 * @param uuid the change share holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching change share holding, or <code>null</code> if a matching change share holding could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeShareHolding fetchChangeShareHoldingByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeShareHolding> getAllByManpowerAndStatus(
		long manpowerEmployeeId, int status);

	/**
	 * @param long manpowerEmployeeId
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeShareHolding> getAllByManpowerId(long manpowerEmployeeId);

	/**
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeShareHolding> getAllByStatus(int status);

	/**
	 * Returns the change share holding with the primary key.
	 *
	 * @param changeShareHoldingId the primary key of the change share holding
	 * @return the change share holding
	 * @throws PortalException if a change share holding with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeShareHolding getChangeShareHolding(long changeShareHoldingId)
		throws PortalException;

	/**
	 * Returns the change share holding matching the UUID and group.
	 *
	 * @param uuid the change share holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching change share holding
	 * @throws PortalException if a matching change share holding could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeShareHolding getChangeShareHoldingByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the change share holdings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeShareHoldingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of change share holdings
	 * @param end the upper bound of the range of change share holdings (not inclusive)
	 * @return the range of change share holdings
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeShareHolding> getChangeShareHoldings(int start, int end);

	/**
	 * Returns all the change share holdings matching the UUID and company.
	 *
	 * @param uuid the UUID of the change share holdings
	 * @param companyId the primary key of the company
	 * @return the matching change share holdings, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeShareHolding> getChangeShareHoldingsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of change share holdings matching the UUID and company.
	 *
	 * @param uuid the UUID of the change share holdings
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of change share holdings
	 * @param end the upper bound of the range of change share holdings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching change share holdings, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeShareHolding> getChangeShareHoldingsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ChangeShareHolding> orderByComparator);

	/**
	 * Returns the number of change share holdings.
	 *
	 * @return the number of change share holdings
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChangeShareHoldingsCount();

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
	 * @param long manpowerDirectorHoldingId
	 * @param long manpowerEmployeeId
	 * @param String companyName
	 * @param String concern
	 * @param String shareHolding
	 * @param Date concernDate
	 * @param int status
	 * @param Date fromDate
	 * @param Date toDate
	 */
	public ChangeShareHolding saveChangeInShareHolding(
		long manpowerEmployeeId, String companyName, String concern,
		String shareHolding, Date concernDate, int status);

	/**
	 * Updates the change share holding in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeShareHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeShareHolding the change share holding
	 * @return the change share holding that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ChangeShareHolding updateChangeShareHolding(
		ChangeShareHolding changeShareHolding);

}