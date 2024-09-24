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

import com.nps.manpower.model.ManpowerDirectorHolding;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ManpowerDirectorHolding. This utility wraps
 * <code>com.nps.manpower.service.impl.ManpowerDirectorHoldingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ManpowerDirectorHoldingLocalService
 * @generated
 */
public class ManpowerDirectorHoldingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.nps.manpower.service.impl.ManpowerDirectorHoldingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
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
	public static ManpowerDirectorHolding addManpowerDirectorHolding(
		ManpowerDirectorHolding manpowerDirectorHolding) {

		return getService().addManpowerDirectorHolding(manpowerDirectorHolding);
	}

	/**
	 * Creates a new manpower director holding with the primary key. Does not add the manpower director holding to the database.
	 *
	 * @param manpowerDirectorHoldingId the primary key for the new manpower director holding
	 * @return the new manpower director holding
	 */
	public static ManpowerDirectorHolding createManpowerDirectorHolding(
		long manpowerDirectorHoldingId) {

		return getService().createManpowerDirectorHolding(
			manpowerDirectorHoldingId);
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
	public static ManpowerDirectorHolding deleteAllDirectorShareHolding(
			long manpowerEmployeeId, int currentStatus, int deletedStatus)
		throws PortalException {

		return getService().deleteAllDirectorShareHolding(
			manpowerEmployeeId, currentStatus, deletedStatus);
	}

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
	public static ManpowerDirectorHolding deleteManpowerDirectorHolding(
			long manpowerDirectorHoldingId)
		throws PortalException {

		return getService().deleteManpowerDirectorHolding(
			manpowerDirectorHoldingId);
	}

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
	public static ManpowerDirectorHolding deleteManpowerDirectorHolding(
		ManpowerDirectorHolding manpowerDirectorHolding) {

		return getService().deleteManpowerDirectorHolding(
			manpowerDirectorHolding);
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
	 * @param long manpowerDirectorHoldingId
	 * @param int status
	 */
	public static ManpowerDirectorHolding deleteShareHolding(
			long manpowerDirectorHoldingId, int status)
		throws PortalException {

		return getService().deleteShareHolding(
			manpowerDirectorHoldingId, status);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerDirectorHoldingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerDirectorHoldingModelImpl</code>.
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

	public static ManpowerDirectorHolding fetchManpowerDirectorHolding(
		long manpowerDirectorHoldingId) {

		return getService().fetchManpowerDirectorHolding(
			manpowerDirectorHoldingId);
	}

	/**
	 * Returns the manpower director holding matching the UUID and group.
	 *
	 * @param uuid the manpower director holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower director holding, or <code>null</code> if a matching manpower director holding could not be found
	 */
	public static ManpowerDirectorHolding
		fetchManpowerDirectorHoldingByUuidAndGroupId(
			String uuid, long groupId) {

		return getService().fetchManpowerDirectorHoldingByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	public static List<ManpowerDirectorHolding> getAllByManpowerAndStatus(
		long manpowerEmployeeId, int status) {

		return getService().getAllByManpowerAndStatus(
			manpowerEmployeeId, status);
	}

	/**
	 * @param int status
	 */
	public static List<ManpowerDirectorHolding> getAllByStatus(int status) {
		return getService().getAllByStatus(status);
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
	 * Returns the manpower director holding with the primary key.
	 *
	 * @param manpowerDirectorHoldingId the primary key of the manpower director holding
	 * @return the manpower director holding
	 * @throws PortalException if a manpower director holding with the primary key could not be found
	 */
	public static ManpowerDirectorHolding getManpowerDirectorHolding(
			long manpowerDirectorHoldingId)
		throws PortalException {

		return getService().getManpowerDirectorHolding(
			manpowerDirectorHoldingId);
	}

	/**
	 * Returns the manpower director holding matching the UUID and group.
	 *
	 * @param uuid the manpower director holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower director holding
	 * @throws PortalException if a matching manpower director holding could not be found
	 */
	public static ManpowerDirectorHolding
			getManpowerDirectorHoldingByUuidAndGroupId(
				String uuid, long groupId)
		throws PortalException {

		return getService().getManpowerDirectorHoldingByUuidAndGroupId(
			uuid, groupId);
	}

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
	public static List<ManpowerDirectorHolding> getManpowerDirectorHoldings(
		int start, int end) {

		return getService().getManpowerDirectorHoldings(start, end);
	}

	/**
	 * Returns all the manpower director holdings matching the UUID and company.
	 *
	 * @param uuid the UUID of the manpower director holdings
	 * @param companyId the primary key of the company
	 * @return the matching manpower director holdings, or an empty list if no matches were found
	 */
	public static List<ManpowerDirectorHolding>
		getManpowerDirectorHoldingsByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().getManpowerDirectorHoldingsByUuidAndCompanyId(
			uuid, companyId);
	}

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
	public static List<ManpowerDirectorHolding>
		getManpowerDirectorHoldingsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<ManpowerDirectorHolding> orderByComparator) {

		return getService().getManpowerDirectorHoldingsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of manpower director holdings.
	 *
	 * @return the number of manpower director holdings
	 */
	public static int getManpowerDirectorHoldingsCount() {
		return getService().getManpowerDirectorHoldingsCount();
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
	public static ManpowerDirectorHolding saveShareHolding(
		long manpowerDirectorHoldingId, long manpowerEmployeeId,
		String companyName, String concern, String shareHolding,
		java.util.Date concernDate, int status) {

		return getService().saveShareHolding(
			manpowerDirectorHoldingId, manpowerEmployeeId, companyName, concern,
			shareHolding, concernDate, status);
	}

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
	public static ManpowerDirectorHolding updateManpowerDirectorHolding(
		ManpowerDirectorHolding manpowerDirectorHolding) {

		return getService().updateManpowerDirectorHolding(
			manpowerDirectorHolding);
	}

	public static ManpowerDirectorHoldingLocalService getService() {
		return _service;
	}

	private static volatile ManpowerDirectorHoldingLocalService _service;

}