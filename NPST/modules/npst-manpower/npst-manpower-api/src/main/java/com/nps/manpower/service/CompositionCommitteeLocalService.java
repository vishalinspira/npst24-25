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

import com.nps.manpower.model.CompositionCommittee;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CompositionCommittee. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see CompositionCommitteeLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CompositionCommitteeLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.nps.manpower.service.impl.CompositionCommitteeLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the composition committee local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CompositionCommitteeLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the composition committee to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompositionCommitteeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param compositionCommittee the composition committee
	 * @return the composition committee that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CompositionCommittee addCompositionCommittee(
		CompositionCommittee compositionCommittee);

	/**
	 * Creates a new composition committee with the primary key. Does not add the composition committee to the database.
	 *
	 * @param compositionCommitteeId the primary key for the new composition committee
	 * @return the new composition committee
	 */
	@Transactional(enabled = false)
	public CompositionCommittee createCompositionCommittee(
		long compositionCommitteeId);

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
	public CompositionCommittee deleteAllCompositionCommittee(
			long manpowerEmployeeId, int currentStatus, int deletedStatus)
		throws PortalException;

	/**
	 * Deletes the composition committee from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompositionCommitteeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param compositionCommittee the composition committee
	 * @return the composition committee that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public CompositionCommittee deleteCompositionCommittee(
		CompositionCommittee compositionCommittee);

	/**
	 * Deletes the composition committee with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompositionCommitteeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param compositionCommitteeId the primary key of the composition committee
	 * @return the composition committee that was removed
	 * @throws PortalException if a composition committee with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CompositionCommittee deleteCompositionCommittee(
			long compositionCommitteeId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * @param long compositionCommitteeId
	 * @param int status
	 */
	public CompositionCommittee deleteShareHolding(
			long compositionCommitteeId, int status)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.CompositionCommitteeModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.CompositionCommitteeModelImpl</code>.
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
	public CompositionCommittee fetchCompositionCommittee(
		long compositionCommitteeId);

	/**
	 * Returns the composition committee matching the UUID and group.
	 *
	 * @param uuid the composition committee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching composition committee, or <code>null</code> if a matching composition committee could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CompositionCommittee fetchCompositionCommitteeByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * @param String designation
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CompositionCommittee> getAllByDesignation(String designation);

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CompositionCommittee> getAllByManpowerAndStatus(
		long manpowerEmployeeId, int status);

	/**
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CompositionCommittee> getAllByStatus(int status);

	/**
	 * Returns the composition committee with the primary key.
	 *
	 * @param compositionCommitteeId the primary key of the composition committee
	 * @return the composition committee
	 * @throws PortalException if a composition committee with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CompositionCommittee getCompositionCommittee(
			long compositionCommitteeId)
		throws PortalException;

	/**
	 * Returns the composition committee matching the UUID and group.
	 *
	 * @param uuid the composition committee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching composition committee
	 * @throws PortalException if a matching composition committee could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CompositionCommittee getCompositionCommitteeByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the composition committees.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.CompositionCommitteeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of composition committees
	 * @param end the upper bound of the range of composition committees (not inclusive)
	 * @return the range of composition committees
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CompositionCommittee> getCompositionCommittees(
		int start, int end);

	/**
	 * Returns all the composition committees matching the UUID and company.
	 *
	 * @param uuid the UUID of the composition committees
	 * @param companyId the primary key of the company
	 * @return the matching composition committees, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CompositionCommittee>
		getCompositionCommitteesByUuidAndCompanyId(String uuid, long companyId);

	/**
	 * Returns a range of composition committees matching the UUID and company.
	 *
	 * @param uuid the UUID of the composition committees
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of composition committees
	 * @param end the upper bound of the range of composition committees (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching composition committees, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CompositionCommittee>
		getCompositionCommitteesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<CompositionCommittee> orderByComparator);

	/**
	 * Returns the number of composition committees.
	 *
	 * @return the number of composition committees
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompositionCommitteesCount();

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
	 * @param long compositionCommitteeId
	 * @param long manpowerEmployeeId
	 * @param designation
	 * @param name
	 * @param String email
	 * @param membershipType
	 * @param committeeAppointmentDate
	 * @param status
	 * @param fromDate
	 * @param toDate
	 */
	public CompositionCommittee saveCompositionCommittee(
		long compositionCommitteeId, long manpowerEmployeeId,
		long committeeMemberShipType, String designation, String name,
		String email, String membershipType, Date committeeAppointmentDate,
		int status);

	/**
	 * Updates the composition committee in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompositionCommitteeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param compositionCommittee the composition committee
	 * @return the composition committee that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CompositionCommittee updateCompositionCommittee(
		CompositionCommittee compositionCommittee);

}