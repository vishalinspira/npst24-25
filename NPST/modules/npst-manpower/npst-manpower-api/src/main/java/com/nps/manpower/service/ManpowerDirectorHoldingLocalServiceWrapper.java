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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ManpowerDirectorHoldingLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ManpowerDirectorHoldingLocalService
 * @generated
 */
public class ManpowerDirectorHoldingLocalServiceWrapper
	implements ManpowerDirectorHoldingLocalService,
			   ServiceWrapper<ManpowerDirectorHoldingLocalService> {

	public ManpowerDirectorHoldingLocalServiceWrapper(
		ManpowerDirectorHoldingLocalService
			manpowerDirectorHoldingLocalService) {

		_manpowerDirectorHoldingLocalService =
			manpowerDirectorHoldingLocalService;
	}

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
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
		addManpowerDirectorHolding(
			com.nps.manpower.model.ManpowerDirectorHolding
				manpowerDirectorHolding) {

		return _manpowerDirectorHoldingLocalService.addManpowerDirectorHolding(
			manpowerDirectorHolding);
	}

	/**
	 * Creates a new manpower director holding with the primary key. Does not add the manpower director holding to the database.
	 *
	 * @param manpowerDirectorHoldingId the primary key for the new manpower director holding
	 * @return the new manpower director holding
	 */
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
		createManpowerDirectorHolding(long manpowerDirectorHoldingId) {

		return _manpowerDirectorHoldingLocalService.
			createManpowerDirectorHolding(manpowerDirectorHoldingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manpowerDirectorHoldingLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * @param long manpowerEmployeeId
	 * @param int currentStatus
	 * @param int deletedStatus
	 */
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
			deleteAllDirectorShareHolding(
				long manpowerEmployeeId, int currentStatus, int deletedStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manpowerDirectorHoldingLocalService.
			deleteAllDirectorShareHolding(
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
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
			deleteManpowerDirectorHolding(long manpowerDirectorHoldingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manpowerDirectorHoldingLocalService.
			deleteManpowerDirectorHolding(manpowerDirectorHoldingId);
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
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
		deleteManpowerDirectorHolding(
			com.nps.manpower.model.ManpowerDirectorHolding
				manpowerDirectorHolding) {

		return _manpowerDirectorHoldingLocalService.
			deleteManpowerDirectorHolding(manpowerDirectorHolding);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manpowerDirectorHoldingLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * @param long manpowerDirectorHoldingId
	 * @param int status
	 */
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding deleteShareHolding(
			long manpowerDirectorHoldingId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manpowerDirectorHoldingLocalService.deleteShareHolding(
			manpowerDirectorHoldingId, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _manpowerDirectorHoldingLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _manpowerDirectorHoldingLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _manpowerDirectorHoldingLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _manpowerDirectorHoldingLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _manpowerDirectorHoldingLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _manpowerDirectorHoldingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
		fetchManpowerDirectorHolding(long manpowerDirectorHoldingId) {

		return _manpowerDirectorHoldingLocalService.
			fetchManpowerDirectorHolding(manpowerDirectorHoldingId);
	}

	/**
	 * Returns the manpower director holding matching the UUID and group.
	 *
	 * @param uuid the manpower director holding's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower director holding, or <code>null</code> if a matching manpower director holding could not be found
	 */
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
		fetchManpowerDirectorHoldingByUuidAndGroupId(
			String uuid, long groupId) {

		return _manpowerDirectorHoldingLocalService.
			fetchManpowerDirectorHoldingByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _manpowerDirectorHoldingLocalService.getActionableDynamicQuery();
	}

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	@Override
	public java.util.List<com.nps.manpower.model.ManpowerDirectorHolding>
		getAllByManpowerAndStatus(long manpowerEmployeeId, int status) {

		return _manpowerDirectorHoldingLocalService.getAllByManpowerAndStatus(
			manpowerEmployeeId, status);
	}

	/**
	 * @param int status
	 */
	@Override
	public java.util.List<com.nps.manpower.model.ManpowerDirectorHolding>
		getAllByStatus(int status) {

		return _manpowerDirectorHoldingLocalService.getAllByStatus(status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _manpowerDirectorHoldingLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _manpowerDirectorHoldingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the manpower director holding with the primary key.
	 *
	 * @param manpowerDirectorHoldingId the primary key of the manpower director holding
	 * @return the manpower director holding
	 * @throws PortalException if a manpower director holding with the primary key could not be found
	 */
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
			getManpowerDirectorHolding(long manpowerDirectorHoldingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manpowerDirectorHoldingLocalService.getManpowerDirectorHolding(
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
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
			getManpowerDirectorHoldingByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manpowerDirectorHoldingLocalService.
			getManpowerDirectorHoldingByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<com.nps.manpower.model.ManpowerDirectorHolding>
		getManpowerDirectorHoldings(int start, int end) {

		return _manpowerDirectorHoldingLocalService.getManpowerDirectorHoldings(
			start, end);
	}

	/**
	 * Returns all the manpower director holdings matching the UUID and company.
	 *
	 * @param uuid the UUID of the manpower director holdings
	 * @param companyId the primary key of the company
	 * @return the matching manpower director holdings, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.nps.manpower.model.ManpowerDirectorHolding>
		getManpowerDirectorHoldingsByUuidAndCompanyId(
			String uuid, long companyId) {

		return _manpowerDirectorHoldingLocalService.
			getManpowerDirectorHoldingsByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<com.nps.manpower.model.ManpowerDirectorHolding>
		getManpowerDirectorHoldingsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.nps.manpower.model.ManpowerDirectorHolding>
					orderByComparator) {

		return _manpowerDirectorHoldingLocalService.
			getManpowerDirectorHoldingsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of manpower director holdings.
	 *
	 * @return the number of manpower director holdings
	 */
	@Override
	public int getManpowerDirectorHoldingsCount() {
		return _manpowerDirectorHoldingLocalService.
			getManpowerDirectorHoldingsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _manpowerDirectorHoldingLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manpowerDirectorHoldingLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding saveShareHolding(
		long manpowerDirectorHoldingId, long manpowerEmployeeId,
		String companyName, String concern, String shareHolding,
		java.util.Date concernDate, int status) {

		return _manpowerDirectorHoldingLocalService.saveShareHolding(
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
	@Override
	public com.nps.manpower.model.ManpowerDirectorHolding
		updateManpowerDirectorHolding(
			com.nps.manpower.model.ManpowerDirectorHolding
				manpowerDirectorHolding) {

		return _manpowerDirectorHoldingLocalService.
			updateManpowerDirectorHolding(manpowerDirectorHolding);
	}

	@Override
	public ManpowerDirectorHoldingLocalService getWrappedService() {
		return _manpowerDirectorHoldingLocalService;
	}

	@Override
	public void setWrappedService(
		ManpowerDirectorHoldingLocalService
			manpowerDirectorHoldingLocalService) {

		_manpowerDirectorHoldingLocalService =
			manpowerDirectorHoldingLocalService;
	}

	private ManpowerDirectorHoldingLocalService
		_manpowerDirectorHoldingLocalService;

}