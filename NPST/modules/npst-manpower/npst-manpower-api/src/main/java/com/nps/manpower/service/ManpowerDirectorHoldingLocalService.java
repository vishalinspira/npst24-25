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

import com.nps.manpower.model.ManpowerDirectorHolding;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ManpowerDirectorHolding. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ManpowerDirectorHoldingLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ManpowerDirectorHoldingLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.nps.manpower.service.impl.ManpowerDirectorHoldingLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the manpower director holding local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ManpowerDirectorHoldingLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the manpower director holding to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManpowerDirectorHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manpowerDirectorHolding the manpower director holding
	 * @return the manpower director holding that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ManpowerDirectorHolding addManpowerDirectorHolding(
		ManpowerDirectorHolding manpowerDirectorHolding);

	/**
	 * Creates a new manpower director holding with the primary key. Does not add the manpower director holding to the database.
	 *
	 * @param manpowerDirectorHoldingId the primary key for the new manpower director holding
	 * @return the new manpower director holding
	 */
	@Transactional(enabled = false)
	public ManpowerDirectorHolding createManpowerDirectorHolding(
		long manpowerDirectorHoldingId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * @param long manpowerEmployeeId
	 * @param int currentStatus
	 * @param int deletedStatus
	 */
	public ManpowerDirectorHolding deleteAllDirectorShareHolding(
			long manpowerEmployeeId, int currentStatus, int deletedStatus)
		throws PortalException;

	/**
	 * Deletes the manpower director holding with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManpowerDirectorHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manpowerDirectorHoldingId the primary key of the manpower director holding
	 * @return the manpower director holding that was removed
	 * @throws PortalException if a manpower director holding with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ManpowerDirectorHolding deleteManpowerDirectorHolding(
			long manpowerDirectorHoldingId)
		throws PortalException;

	/**
	 * Deletes the manpower director holding from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManpowerDirectorHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manpowerDirectorHolding the manpower director holding
	 * @return the manpower director holding that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public ManpowerDirectorHolding deleteManpowerDirectorHolding(
		ManpowerDirectorHolding manpowerDirectorHolding);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * @param long manpowerDirectorHoldingId
	 * @param int status
	 */
	public ManpowerDirectorHolding deleteShareHolding(
			long manpowerDirectorHoldingId, int status)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerDirectorHoldingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerDirectorHoldingModelImpl</code>.
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
	public ManpowerDirectorHolding fetchManpowerDirectorHolding(
		long manpowerDirectorHoldingId);

	/**
	 * Returns the manpower director holding matching the UUID and group.
	 *
	 * @param uuid the manpower director holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower director holding, or <code>null</code> if a matching manpower director holding could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManpowerDirectorHolding fetchManpowerDirectorHoldingByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerDirectorHolding> getAllByManpowerAndStatus(
		long manpowerEmployeeId, int status);

	/**
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerDirectorHolding> getAllByStatus(int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the manpower director holding with the primary key.
	 *
	 * @param manpowerDirectorHoldingId the primary key of the manpower director holding
	 * @return the manpower director holding
	 * @throws PortalException if a manpower director holding with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManpowerDirectorHolding getManpowerDirectorHolding(
			long manpowerDirectorHoldingId)
		throws PortalException;

	/**
	 * Returns the manpower director holding matching the UUID and group.
	 *
	 * @param uuid the manpower director holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower director holding
	 * @throws PortalException if a matching manpower director holding could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManpowerDirectorHolding getManpowerDirectorHoldingByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the manpower director holdings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerDirectorHoldingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of manpower director holdings
	 * @param end the upper bound of the range of manpower director holdings (not inclusive)
	 * @return the range of manpower director holdings
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerDirectorHolding> getManpowerDirectorHoldings(
		int start, int end);

	/**
	 * Returns all the manpower director holdings matching the UUID and company.
	 *
	 * @param uuid the UUID of the manpower director holdings
	 * @param companyId the primary key of the company
	 * @return the matching manpower director holdings, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerDirectorHolding>
		getManpowerDirectorHoldingsByUuidAndCompanyId(
			String uuid, long companyId);

	/**
	 * Returns a range of manpower director holdings matching the UUID and company.
	 *
	 * @param uuid the UUID of the manpower director holdings
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of manpower director holdings
	 * @param end the upper bound of the range of manpower director holdings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching manpower director holdings, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerDirectorHolding>
		getManpowerDirectorHoldingsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<ManpowerDirectorHolding> orderByComparator);

	/**
	 * Returns the number of manpower director holdings.
	 *
	 * @return the number of manpower director holdings
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getManpowerDirectorHoldingsCount();

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
	public ManpowerDirectorHolding saveShareHolding(
		long manpowerDirectorHoldingId, long manpowerEmployeeId,
		String companyName, String concern, String shareHolding,
		Date concernDate, int status);

	/**
	 * Updates the manpower director holding in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManpowerDirectorHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manpowerDirectorHolding the manpower director holding
	 * @return the manpower director holding that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ManpowerDirectorHolding updateManpowerDirectorHolding(
		ManpowerDirectorHolding manpowerDirectorHolding);

}