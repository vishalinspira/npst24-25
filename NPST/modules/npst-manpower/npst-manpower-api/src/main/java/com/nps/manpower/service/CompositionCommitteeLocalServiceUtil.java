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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.nps.manpower.model.CompositionCommittee;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CompositionCommittee. This utility wraps
 * <code>com.nps.manpower.service.impl.CompositionCommitteeLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CompositionCommitteeLocalService
 * @generated
 */
public class CompositionCommitteeLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.nps.manpower.service.impl.CompositionCommitteeLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
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
	public static CompositionCommittee addCompositionCommittee(
		CompositionCommittee compositionCommittee) {

		return getService().addCompositionCommittee(compositionCommittee);
	}

	/**
	 * Creates a new composition committee with the primary key. Does not add the composition committee to the database.
	 *
	 * @param compositionCommitteeId the primary key for the new composition committee
	 * @return the new composition committee
	 */
	public static CompositionCommittee createCompositionCommittee(
		long compositionCommitteeId) {

		return getService().createCompositionCommittee(compositionCommitteeId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * @param long manpowerEmployeeId
	 * @param int currentStatus
	 * @param int deletedStatus
	 */
	public static CompositionCommittee deleteAllCompositionCommittee(
			long manpowerEmployeeId, int currentStatus, int deletedStatus)
		throws PortalException {

		return getService().deleteAllCompositionCommittee(
			manpowerEmployeeId, currentStatus, deletedStatus);
	}

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
	public static CompositionCommittee deleteCompositionCommittee(
		CompositionCommittee compositionCommittee) {

		return getService().deleteCompositionCommittee(compositionCommittee);
	}

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
	public static CompositionCommittee deleteCompositionCommittee(
			long compositionCommitteeId)
		throws PortalException {

		return getService().deleteCompositionCommittee(compositionCommitteeId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * @param long compositionCommitteeId
	 * @param int status
	 */
	public static CompositionCommittee deleteShareHolding(
			long compositionCommitteeId, int status)
		throws PortalException {

		return getService().deleteShareHolding(compositionCommitteeId, status);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CompositionCommittee fetchCompositionCommittee(
		long compositionCommitteeId) {

		return getService().fetchCompositionCommittee(compositionCommitteeId);
	}

	/**
	 * Returns the composition committee matching the UUID and group.
	 *
	 * @param uuid the composition committee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching composition committee, or <code>null</code> if a matching composition committee could not be found
	 */
	public static CompositionCommittee
		fetchCompositionCommitteeByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchCompositionCommitteeByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * @param String designation
	 */
	public static List<CompositionCommittee> getAllByDesignation(
		String designation) {

		return getService().getAllByDesignation(designation);
	}

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	public static List<CompositionCommittee> getAllByManpowerAndStatus(
		long manpowerEmployeeId, int status) {

		return getService().getAllByManpowerAndStatus(
			manpowerEmployeeId, status);
	}

	/**
	 * @param int status
	 */
	public static List<CompositionCommittee> getAllByStatus(int status) {
		return getService().getAllByStatus(status);
	}

	/**
	 * Returns the composition committee with the primary key.
	 *
	 * @param compositionCommitteeId the primary key of the composition committee
	 * @return the composition committee
	 * @throws PortalException if a composition committee with the primary key could not be found
	 */
	public static CompositionCommittee getCompositionCommittee(
			long compositionCommitteeId)
		throws PortalException {

		return getService().getCompositionCommittee(compositionCommitteeId);
	}

	/**
	 * Returns the composition committee matching the UUID and group.
	 *
	 * @param uuid the composition committee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching composition committee
	 * @throws PortalException if a matching composition committee could not be found
	 */
	public static CompositionCommittee getCompositionCommitteeByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getCompositionCommitteeByUuidAndGroupId(
			uuid, groupId);
	}

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
	public static List<CompositionCommittee> getCompositionCommittees(
		int start, int end) {

		return getService().getCompositionCommittees(start, end);
	}

	/**
	 * Returns all the composition committees matching the UUID and company.
	 *
	 * @param uuid the UUID of the composition committees
	 * @param companyId the primary key of the company
	 * @return the matching composition committees, or an empty list if no matches were found
	 */
	public static List<CompositionCommittee>
		getCompositionCommitteesByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().getCompositionCommitteesByUuidAndCompanyId(
			uuid, companyId);
	}

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
	public static List<CompositionCommittee>
		getCompositionCommitteesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<CompositionCommittee> orderByComparator) {

		return getService().getCompositionCommitteesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of composition committees.
	 *
	 * @return the number of composition committees
	 */
	public static int getCompositionCommitteesCount() {
		return getService().getCompositionCommitteesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

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
	public static CompositionCommittee saveCompositionCommittee(
		long compositionCommitteeId, long manpowerEmployeeId,
		long committeeMemberShipType, String designation, String name,
		String email, String membershipType,
		java.util.Date committeeAppointmentDate, int status) {

		return getService().saveCompositionCommittee(
			compositionCommitteeId, manpowerEmployeeId, committeeMemberShipType,
			designation, name, email, membershipType, committeeAppointmentDate,
			status);
	}

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
	public static CompositionCommittee updateCompositionCommittee(
		CompositionCommittee compositionCommittee) {

		return getService().updateCompositionCommittee(compositionCommittee);
	}

	public static CompositionCommitteeLocalService getService() {
		return _service;
	}

	private static volatile CompositionCommitteeLocalService _service;

}