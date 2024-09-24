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

import com.nps.manpower.model.ChangeShareHolding;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ChangeShareHolding. This utility wraps
 * <code>com.nps.manpower.service.impl.ChangeShareHoldingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeShareHoldingLocalService
 * @generated
 */
public class ChangeShareHoldingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.nps.manpower.service.impl.ChangeShareHoldingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
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
	public static ChangeShareHolding addChangeShareHolding(
		ChangeShareHolding changeShareHolding) {

		return getService().addChangeShareHolding(changeShareHolding);
	}

	/**
	 * Creates a new change share holding with the primary key. Does not add the change share holding to the database.
	 *
	 * @param changeShareHoldingId the primary key for the new change share holding
	 * @return the new change share holding
	 */
	public static ChangeShareHolding createChangeShareHolding(
		long changeShareHoldingId) {

		return getService().createChangeShareHolding(changeShareHoldingId);
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
	 * Deletes the change share holding from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeShareHoldingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeShareHolding the change share holding
	 * @return the change share holding that was removed
	 */
	public static ChangeShareHolding deleteChangeShareHolding(
		ChangeShareHolding changeShareHolding) {

		return getService().deleteChangeShareHolding(changeShareHolding);
	}

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
	public static ChangeShareHolding deleteChangeShareHolding(
			long changeShareHoldingId)
		throws PortalException {

		return getService().deleteChangeShareHolding(changeShareHoldingId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeShareHoldingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeShareHoldingModelImpl</code>.
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

	public static ChangeShareHolding fetchChangeShareHolding(
		long changeShareHoldingId) {

		return getService().fetchChangeShareHolding(changeShareHoldingId);
	}

	/**
	 * Returns the change share holding matching the UUID and group.
	 *
	 * @param uuid the change share holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching change share holding, or <code>null</code> if a matching change share holding could not be found
	 */
	public static ChangeShareHolding fetchChangeShareHoldingByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchChangeShareHoldingByUuidAndGroupId(
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
	public static List<ChangeShareHolding> getAllByManpowerAndStatus(
		long manpowerEmployeeId, int status) {

		return getService().getAllByManpowerAndStatus(
			manpowerEmployeeId, status);
	}

	/**
	 * @param long manpowerEmployeeId
	 */
	public static List<ChangeShareHolding> getAllByManpowerId(
		long manpowerEmployeeId) {

		return getService().getAllByManpowerId(manpowerEmployeeId);
	}

	/**
	 * @param int status
	 */
	public static List<ChangeShareHolding> getAllByStatus(int status) {
		return getService().getAllByStatus(status);
	}

	/**
	 * Returns the change share holding with the primary key.
	 *
	 * @param changeShareHoldingId the primary key of the change share holding
	 * @return the change share holding
	 * @throws PortalException if a change share holding with the primary key could not be found
	 */
	public static ChangeShareHolding getChangeShareHolding(
			long changeShareHoldingId)
		throws PortalException {

		return getService().getChangeShareHolding(changeShareHoldingId);
	}

	/**
	 * Returns the change share holding matching the UUID and group.
	 *
	 * @param uuid the change share holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching change share holding
	 * @throws PortalException if a matching change share holding could not be found
	 */
	public static ChangeShareHolding getChangeShareHoldingByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getChangeShareHoldingByUuidAndGroupId(
			uuid, groupId);
	}

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
	public static List<ChangeShareHolding> getChangeShareHoldings(
		int start, int end) {

		return getService().getChangeShareHoldings(start, end);
	}

	/**
	 * Returns all the change share holdings matching the UUID and company.
	 *
	 * @param uuid the UUID of the change share holdings
	 * @param companyId the primary key of the company
	 * @return the matching change share holdings, or an empty list if no matches were found
	 */
	public static List<ChangeShareHolding>
		getChangeShareHoldingsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getChangeShareHoldingsByUuidAndCompanyId(
			uuid, companyId);
	}

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
	public static List<ChangeShareHolding>
		getChangeShareHoldingsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<ChangeShareHolding> orderByComparator) {

		return getService().getChangeShareHoldingsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of change share holdings.
	 *
	 * @return the number of change share holdings
	 */
	public static int getChangeShareHoldingsCount() {
		return getService().getChangeShareHoldingsCount();
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
	public static ChangeShareHolding saveChangeInShareHolding(
		long manpowerEmployeeId, String companyName, String concern,
		String shareHolding, java.util.Date concernDate, int status) {

		return getService().saveChangeInShareHolding(
			manpowerEmployeeId, companyName, concern, shareHolding, concernDate,
			status);
	}

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
	public static ChangeShareHolding updateChangeShareHolding(
		ChangeShareHolding changeShareHolding) {

		return getService().updateChangeShareHolding(changeShareHolding);
	}

	public static ChangeShareHoldingLocalService getService() {
		return _service;
	}

	private static volatile ChangeShareHoldingLocalService _service;

}