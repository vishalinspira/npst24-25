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

import com.nps.manpower.model.ChangeInEmployee;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ChangeInEmployee. This utility wraps
 * <code>com.nps.manpower.service.impl.ChangeInEmployeeLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeInEmployeeLocalService
 * @generated
 */
public class ChangeInEmployeeLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.nps.manpower.service.impl.ChangeInEmployeeLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the change in employee to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeInEmployeeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeInEmployee the change in employee
	 * @return the change in employee that was added
	 */
	public static ChangeInEmployee addChangeInEmployee(
		ChangeInEmployee changeInEmployee) {

		return getService().addChangeInEmployee(changeInEmployee);
	}

	/**
	 * Creates a new change in employee with the primary key. Does not add the change in employee to the database.
	 *
	 * @param changeInEmployeeId the primary key for the new change in employee
	 * @return the new change in employee
	 */
	public static ChangeInEmployee createChangeInEmployee(
		long changeInEmployeeId) {

		return getService().createChangeInEmployee(changeInEmployeeId);
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
	 * Deletes the change in employee from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeInEmployeeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeInEmployee the change in employee
	 * @return the change in employee that was removed
	 */
	public static ChangeInEmployee deleteChangeInEmployee(
		ChangeInEmployee changeInEmployee) {

		return getService().deleteChangeInEmployee(changeInEmployee);
	}

	/**
	 * Deletes the change in employee with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeInEmployeeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeInEmployeeId the primary key of the change in employee
	 * @return the change in employee that was removed
	 * @throws PortalException if a change in employee with the primary key could not be found
	 */
	public static ChangeInEmployee deleteChangeInEmployee(
			long changeInEmployeeId)
		throws PortalException {

		return getService().deleteChangeInEmployee(changeInEmployeeId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeInEmployeeModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeInEmployeeModelImpl</code>.
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

	public static ChangeInEmployee fetchChangeInEmployee(
		long changeInEmployeeId) {

		return getService().fetchChangeInEmployee(changeInEmployeeId);
	}

	/**
	 * Returns the change in employee matching the UUID and group.
	 *
	 * @param uuid the change in employee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching change in employee, or <code>null</code> if a matching change in employee could not be found
	 */
	public static ChangeInEmployee fetchChangeInEmployeeByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchChangeInEmployeeByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * @param int committeeMembershipType
	 * @param long manpowerEmployeeId
	 */
	public static List<ChangeInEmployee> getAllByCommitteeTypeAndEmployeeId(
		int committeeMembershipType, long manpowerEmployeeId) {

		return getService().getAllByCommitteeTypeAndEmployeeId(
			committeeMembershipType, manpowerEmployeeId);
	}

	/**
	 * @param String designation
	 * @param long manpowerEmployeeId
	 */
	public static List<ChangeInEmployee> getAllByDesignationAndEmployeeId(
		String designation, long manpowerEmployeeId) {

		return getService().getAllByDesignationAndEmployeeId(
			designation, manpowerEmployeeId);
	}

	/**
	 * @param long manpowerEmployeeId
	 */
	public static List<ChangeInEmployee> getAllByEmployeeId(
		long manpowerEmployeeId) {

		return getService().getAllByEmployeeId(manpowerEmployeeId);
	}

	/**
	 * @param int isDirector
	 * @param long manpowerEmployeeId
	 */
	public static List<ChangeInEmployee> getAllByIsDirectorAndEmployeeId(
		int isDirector, long manpowerEmployeeId) {

		return getService().getAllByIsDirectorAndEmployeeId(
			isDirector, manpowerEmployeeId);
	}

	/**
	 * @param int isDirector
	 * @param String membershipType
	 * @param long manpowerEmployeeId
	 */
	public static List<ChangeInEmployee>
		getAllByIsDirectorAndMembershipTypeAndEmployeeId(
			int isDirector, String membershipType, long manpowerEmployeeId) {

		return getService().getAllByIsDirectorAndMembershipTypeAndEmployeeId(
			isDirector, membershipType, manpowerEmployeeId);
	}

	/**
	 * @param String membershipType
	 * @param long manpowerEmployeeId
	 */
	public static List<ChangeInEmployee> getAllByMembershipTypeAndEmployeeId(
		String membershipType, long manpowerEmployeeId) {

		return getService().getAllByMembershipTypeAndEmployeeId(
			membershipType, manpowerEmployeeId);
	}

	/**
	 * @param int status
	 */
	public static List<ChangeInEmployee> getAllByStatus(int status) {
		return getService().getAllByStatus(status);
	}

	/**
	 * @param String designation
	 * @param long manpowerEmployeeId
	 * @param int committeeMembershipType
	 */
	public static List<ChangeInEmployee>
		getAllDesignationAndCommitteeTypeAndEmployeeId(
			String designation, long manpowerEmployeeId,
			int committeeMembershipType) {

		return getService().getAllDesignationAndCommitteeTypeAndEmployeeId(
			designation, manpowerEmployeeId, committeeMembershipType);
	}

	/**
	 * Returns the change in employee with the primary key.
	 *
	 * @param changeInEmployeeId the primary key of the change in employee
	 * @return the change in employee
	 * @throws PortalException if a change in employee with the primary key could not be found
	 */
	public static ChangeInEmployee getChangeInEmployee(long changeInEmployeeId)
		throws PortalException {

		return getService().getChangeInEmployee(changeInEmployeeId);
	}

	/**
	 * Returns the change in employee matching the UUID and group.
	 *
	 * @param uuid the change in employee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching change in employee
	 * @throws PortalException if a matching change in employee could not be found
	 */
	public static ChangeInEmployee getChangeInEmployeeByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getChangeInEmployeeByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the change in employees.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeInEmployeeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of change in employees
	 * @param end the upper bound of the range of change in employees (not inclusive)
	 * @return the range of change in employees
	 */
	public static List<ChangeInEmployee> getChangeInEmployees(
		int start, int end) {

		return getService().getChangeInEmployees(start, end);
	}

	/**
	 * Returns all the change in employees matching the UUID and company.
	 *
	 * @param uuid the UUID of the change in employees
	 * @param companyId the primary key of the company
	 * @return the matching change in employees, or an empty list if no matches were found
	 */
	public static List<ChangeInEmployee> getChangeInEmployeesByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getChangeInEmployeesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of change in employees matching the UUID and company.
	 *
	 * @param uuid the UUID of the change in employees
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of change in employees
	 * @param end the upper bound of the range of change in employees (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching change in employees, or an empty list if no matches were found
	 */
	public static List<ChangeInEmployee> getChangeInEmployeesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ChangeInEmployee> orderByComparator) {

		return getService().getChangeInEmployeesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of change in employees.
	 *
	 * @return the number of change in employees
	 */
	public static int getChangeInEmployeesCount() {
		return getService().getChangeInEmployeesCount();
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
	 * @param long manpowerEmployeeId
	 * @param String designation
	 * @param String name
	 * @param Date appointmentDate
	 * @param String contactNo
	 * @param String email
	 * @param String qualification
	 * @param String experience
	 * @param String deputation
	 * @param String linkedinId
	 * @param Date approvingAppointmentDate
	 * @param long biodataFileId
	 * @param int committeeMembershipType
	 * @param String din
	 * @param String directorType
	 * @param String membershipType
	 * @param String dependency
	 * @param String formMbp
	 * @param int status
	 * @param int isDirector
	 * @param Date fromDate
	 * @param Date toDate
	 * @param Date resignationDate
	 */
	public static ChangeInEmployee saveChangeInEmployee(
		long manpowerEmployeeId, String designation, String name,
		java.util.Date appointmentDate, String contactNo, String email,
		String qualification, String experience, String deputation,
		String linkedinId, java.util.Date approvingAppointmentDate,
		long biodataFileId, int committeeMembershipType, String din,
		String directorType, String membershipType, String dependency,
		long formMbp, int status, int isDirector,
		java.util.Date resignationDate) {

		return getService().saveChangeInEmployee(
			manpowerEmployeeId, designation, name, appointmentDate, contactNo,
			email, qualification, experience, deputation, linkedinId,
			approvingAppointmentDate, biodataFileId, committeeMembershipType,
			din, directorType, membershipType, dependency, formMbp, status,
			isDirector, resignationDate);
	}

	/**
	 * Updates the change in employee in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChangeInEmployeeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param changeInEmployee the change in employee
	 * @return the change in employee that was updated
	 */
	public static ChangeInEmployee updateChangeInEmployee(
		ChangeInEmployee changeInEmployee) {

		return getService().updateChangeInEmployee(changeInEmployee);
	}

	public static ChangeInEmployeeLocalService getService() {
		return _service;
	}

	private static volatile ChangeInEmployeeLocalService _service;

}