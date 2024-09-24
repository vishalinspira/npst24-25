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
 * Provides a wrapper for {@link CompositionCommitteeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CompositionCommitteeLocalService
 * @generated
 */
public class CompositionCommitteeLocalServiceWrapper
	implements CompositionCommitteeLocalService,
			   ServiceWrapper<CompositionCommitteeLocalService> {

	public CompositionCommitteeLocalServiceWrapper(
		CompositionCommitteeLocalService compositionCommitteeLocalService) {

		_compositionCommitteeLocalService = compositionCommitteeLocalService;
	}

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
	@Override
	public com.nps.manpower.model.CompositionCommittee addCompositionCommittee(
		com.nps.manpower.model.CompositionCommittee compositionCommittee) {

		return _compositionCommitteeLocalService.addCompositionCommittee(
			compositionCommittee);
	}

	/**
	 * Creates a new composition committee with the primary key. Does not add the composition committee to the database.
	 *
	 * @param compositionCommitteeId the primary key for the new composition committee
	 * @return the new composition committee
	 */
	@Override
	public com.nps.manpower.model.CompositionCommittee
		createCompositionCommittee(long compositionCommitteeId) {

		return _compositionCommitteeLocalService.createCompositionCommittee(
			compositionCommitteeId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _compositionCommitteeLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * @param long manpowerEmployeeId
	 * @param int currentStatus
	 * @param int deletedStatus
	 */
	@Override
	public com.nps.manpower.model.CompositionCommittee
			deleteAllCompositionCommittee(
				long manpowerEmployeeId, int currentStatus, int deletedStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _compositionCommitteeLocalService.deleteAllCompositionCommittee(
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
	@Override
	public com.nps.manpower.model.CompositionCommittee
		deleteCompositionCommittee(
			com.nps.manpower.model.CompositionCommittee compositionCommittee) {

		return _compositionCommitteeLocalService.deleteCompositionCommittee(
			compositionCommittee);
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
	@Override
	public com.nps.manpower.model.CompositionCommittee
			deleteCompositionCommittee(long compositionCommitteeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _compositionCommitteeLocalService.deleteCompositionCommittee(
			compositionCommitteeId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _compositionCommitteeLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * @param long compositionCommitteeId
	 * @param int status
	 */
	@Override
	public com.nps.manpower.model.CompositionCommittee deleteShareHolding(
			long compositionCommitteeId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _compositionCommitteeLocalService.deleteShareHolding(
			compositionCommitteeId, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _compositionCommitteeLocalService.dynamicQuery();
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

		return _compositionCommitteeLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _compositionCommitteeLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _compositionCommitteeLocalService.dynamicQuery(
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

		return _compositionCommitteeLocalService.dynamicQueryCount(
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

		return _compositionCommitteeLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.nps.manpower.model.CompositionCommittee
		fetchCompositionCommittee(long compositionCommitteeId) {

		return _compositionCommitteeLocalService.fetchCompositionCommittee(
			compositionCommitteeId);
	}

	/**
	 * Returns the composition committee matching the UUID and group.
	 *
	 * @param uuid the composition committee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching composition committee, or <code>null</code> if a matching composition committee could not be found
	 */
	@Override
	public com.nps.manpower.model.CompositionCommittee
		fetchCompositionCommitteeByUuidAndGroupId(String uuid, long groupId) {

		return _compositionCommitteeLocalService.
			fetchCompositionCommitteeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _compositionCommitteeLocalService.getActionableDynamicQuery();
	}

	/**
	 * @param String designation
	 */
	@Override
	public java.util.List<com.nps.manpower.model.CompositionCommittee>
		getAllByDesignation(String designation) {

		return _compositionCommitteeLocalService.getAllByDesignation(
			designation);
	}

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	@Override
	public java.util.List<com.nps.manpower.model.CompositionCommittee>
		getAllByManpowerAndStatus(long manpowerEmployeeId, int status) {

		return _compositionCommitteeLocalService.getAllByManpowerAndStatus(
			manpowerEmployeeId, status);
	}

	/**
	 * @param int status
	 */
	@Override
	public java.util.List<com.nps.manpower.model.CompositionCommittee>
		getAllByStatus(int status) {

		return _compositionCommitteeLocalService.getAllByStatus(status);
	}

	/**
	 * Returns the composition committee with the primary key.
	 *
	 * @param compositionCommitteeId the primary key of the composition committee
	 * @return the composition committee
	 * @throws PortalException if a composition committee with the primary key could not be found
	 */
	@Override
	public com.nps.manpower.model.CompositionCommittee getCompositionCommittee(
			long compositionCommitteeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _compositionCommitteeLocalService.getCompositionCommittee(
			compositionCommitteeId);
	}

	/**
	 * Returns the composition committee matching the UUID and group.
	 *
	 * @param uuid the composition committee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching composition committee
	 * @throws PortalException if a matching composition committee could not be found
	 */
	@Override
	public com.nps.manpower.model.CompositionCommittee
			getCompositionCommitteeByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _compositionCommitteeLocalService.
			getCompositionCommitteeByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<com.nps.manpower.model.CompositionCommittee>
		getCompositionCommittees(int start, int end) {

		return _compositionCommitteeLocalService.getCompositionCommittees(
			start, end);
	}

	/**
	 * Returns all the composition committees matching the UUID and company.
	 *
	 * @param uuid the UUID of the composition committees
	 * @param companyId the primary key of the company
	 * @return the matching composition committees, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.nps.manpower.model.CompositionCommittee>
		getCompositionCommitteesByUuidAndCompanyId(
			String uuid, long companyId) {

		return _compositionCommitteeLocalService.
			getCompositionCommitteesByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<com.nps.manpower.model.CompositionCommittee>
		getCompositionCommitteesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.nps.manpower.model.CompositionCommittee>
					orderByComparator) {

		return _compositionCommitteeLocalService.
			getCompositionCommitteesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of composition committees.
	 *
	 * @return the number of composition committees
	 */
	@Override
	public int getCompositionCommitteesCount() {
		return _compositionCommitteeLocalService.
			getCompositionCommitteesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _compositionCommitteeLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _compositionCommitteeLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _compositionCommitteeLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _compositionCommitteeLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.nps.manpower.model.CompositionCommittee saveCompositionCommittee(
		long compositionCommitteeId, long manpowerEmployeeId,
		long committeeMemberShipType, String designation, String name,
		String email, String membershipType,
		java.util.Date committeeAppointmentDate, int status) {

		return _compositionCommitteeLocalService.saveCompositionCommittee(
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
	@Override
	public com.nps.manpower.model.CompositionCommittee
		updateCompositionCommittee(
			com.nps.manpower.model.CompositionCommittee compositionCommittee) {

		return _compositionCommitteeLocalService.updateCompositionCommittee(
			compositionCommittee);
	}

	@Override
	public CompositionCommitteeLocalService getWrappedService() {
		return _compositionCommitteeLocalService;
	}

	@Override
	public void setWrappedService(
		CompositionCommitteeLocalService compositionCommitteeLocalService) {

		_compositionCommitteeLocalService = compositionCommitteeLocalService;
	}

	private CompositionCommitteeLocalService _compositionCommitteeLocalService;

}