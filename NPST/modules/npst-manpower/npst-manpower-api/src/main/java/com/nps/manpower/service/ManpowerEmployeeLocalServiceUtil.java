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

import com.nps.manpower.model.ManpowerEmployee;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ManpowerEmployee. This utility wraps
 * <code>com.nps.manpower.service.impl.ManpowerEmployeeLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ManpowerEmployeeLocalService
 * @generated
 */
public class ManpowerEmployeeLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.nps.manpower.service.impl.ManpowerEmployeeLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the manpower employee to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManpowerEmployeeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manpowerEmployee the manpower employee
	 * @return the manpower employee that was added
	 */
	public static ManpowerEmployee addManpowerEmployee(
		ManpowerEmployee manpowerEmployee) {

		return getService().addManpowerEmployee(manpowerEmployee);
	}

	/**
	 * Creates a new manpower employee with the primary key. Does not add the manpower employee to the database.
	 *
	 * @param manpowerEmployeeId the primary key for the new manpower employee
	 * @return the new manpower employee
	 */
	public static ManpowerEmployee createManpowerEmployee(
		long manpowerEmployeeId) {

		return getService().createManpowerEmployee(manpowerEmployeeId);
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
	 * Deletes the manpower employee with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManpowerEmployeeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manpowerEmployeeId the primary key of the manpower employee
	 * @return the manpower employee that was removed
	 * @throws PortalException if a manpower employee with the primary key could not be found
	 */
	public static ManpowerEmployee deleteManpowerEmployee(
			long manpowerEmployeeId)
		throws PortalException {

		return getService().deleteManpowerEmployee(manpowerEmployeeId);
	}

	/**
	 * @param long manpowerEmployeeId
	 * @param int deletedStatus
	 */
	public static ManpowerEmployee deleteManpowerEmployee(
			long manpowerEmployeeId, int deletedStatus,
			java.util.Date resignationDate)
		throws PortalException {

		return getService().deleteManpowerEmployee(
			manpowerEmployeeId, deletedStatus, resignationDate);
	}

	/**
	 * Deletes the manpower employee from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManpowerEmployeeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manpowerEmployee the manpower employee
	 * @return the manpower employee that was removed
	 */
	public static ManpowerEmployee deleteManpowerEmployee(
		ManpowerEmployee manpowerEmployee) {

		return getService().deleteManpowerEmployee(manpowerEmployee);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerEmployeeModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerEmployeeModelImpl</code>.
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

	public static ManpowerEmployee fetchManpowerEmployee(
		long manpowerEmployeeId) {

		return getService().fetchManpowerEmployee(manpowerEmployeeId);
	}

	/**
	 * Returns the manpower employee matching the UUID and group.
	 *
	 * @param uuid the manpower employee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower employee, or <code>null</code> if a matching manpower employee could not be found
	 */
	public static ManpowerEmployee fetchManpowerEmployeeByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchManpowerEmployeeByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * @param isDirector
	 * @param pfmName
	 * @return
	 */
	public static List<ManpowerEmployee> findByIsDirectorAndPfmName(
		int isDirector, String pfmName) {

		return getService().findByIsDirectorAndPfmName(isDirector, pfmName);
	}

	/**
	 * @param isDirector
	 * @param pfmName
	 * @param status
	 * @return
	 */
	public static List<ManpowerEmployee> findByIsDirectorAndPfmNameAndStatus(
		int isDirector, String pfmName, int status) {

		return getService().findByIsDirectorAndPfmNameAndStatus(
			isDirector, pfmName, status);
	}

	/**
	 * @param committeeMembershipType
	 * @param pfmName
	 * @return
	 */
	public static List<ManpowerEmployee> findByPfmAndCommitteeMembershipType(
		int committeeMembershipType, String pfmName) {

		return getService().findByPfmAndCommitteeMembershipType(
			committeeMembershipType, pfmName);
	}

	/**
	 * @param committeeMembershipType
	 * @param pfmName
	 * @param status
	 * @return
	 */
	public static List<ManpowerEmployee>
		findByPfmAndCommitteeMembershipTypeAndStatus(
			int committeeMembershipType, String pfmName, int status) {

		return getService().findByPfmAndCommitteeMembershipTypeAndStatus(
			committeeMembershipType, pfmName, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<ManpowerEmployee> getAllByCommitteeMembershipType(
		int committeeMembershipType) {

		return getService().getAllByCommitteeMembershipType(
			committeeMembershipType);
	}

	public static List<ManpowerEmployee> getAllByDependencyAndPFM(
		String dependency, String pfm) {

		return getService().getAllByDependencyAndPFM(dependency, pfm);
	}

	public static List<ManpowerEmployee> getAllByDesignation(
		String designation) {

		return getService().getAllByDesignation(designation);
	}

	public static List<ManpowerEmployee> getAllByDesignationAndPFM(
		String designation, String pfm) {

		return getService().getAllByDesignationAndPFM(designation, pfm);
	}

	/**
	 * @param String din
	 */
	public static List<ManpowerEmployee> getAllByDIN(String din) {
		return getService().getAllByDIN(din);
	}

	/**
	 * @param String email
	 */
	public static List<ManpowerEmployee> getAllByEmail(String email) {
		return getService().getAllByEmail(email);
	}

	/**
	 * @param isDirector
	 */
	public static List<ManpowerEmployee> getAllByIsDirector(int isDirector) {
		return getService().getAllByIsDirector(isDirector);
	}

	/**
	 * @param int isDirector
	 * @param int status
	 */
	public static List<ManpowerEmployee> getAllByIsDirectorAndStatus(
		int isDirector, int status) {

		return getService().getAllByIsDirectorAndStatus(isDirector, status);
	}

	/**
	 * @param int status
	 */
	public static List<ManpowerEmployee> getAllByStatus(int status) {
		return getService().getAllByStatus(status);
	}

	public static List<ManpowerEmployee>
		getAllByStatusAndCommitteeMembershipType(
			int status, int committeeMembershipType) {

		return getService().getAllByStatusAndCommitteeMembershipType(
			status, committeeMembershipType);
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
	 * Returns the manpower employee with the primary key.
	 *
	 * @param manpowerEmployeeId the primary key of the manpower employee
	 * @return the manpower employee
	 * @throws PortalException if a manpower employee with the primary key could not be found
	 */
	public static ManpowerEmployee getManpowerEmployee(long manpowerEmployeeId)
		throws PortalException {

		return getService().getManpowerEmployee(manpowerEmployeeId);
	}

	/**
	 * Returns the manpower employee matching the UUID and group.
	 *
	 * @param uuid the manpower employee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower employee
	 * @throws PortalException if a matching manpower employee could not be found
	 */
	public static ManpowerEmployee getManpowerEmployeeByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getManpowerEmployeeByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the manpower employees.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerEmployeeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of manpower employees
	 * @param end the upper bound of the range of manpower employees (not inclusive)
	 * @return the range of manpower employees
	 */
	public static List<ManpowerEmployee> getManpowerEmployees(
		int start, int end) {

		return getService().getManpowerEmployees(start, end);
	}

	/**
	 * Returns all the manpower employees matching the UUID and company.
	 *
	 * @param uuid the UUID of the manpower employees
	 * @param companyId the primary key of the company
	 * @return the matching manpower employees, or an empty list if no matches were found
	 */
	public static List<ManpowerEmployee> getManpowerEmployeesByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getManpowerEmployeesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of manpower employees matching the UUID and company.
	 *
	 * @param uuid the UUID of the manpower employees
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of manpower employees
	 * @param end the upper bound of the range of manpower employees (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching manpower employees, or an empty list if no matches were found
	 */
	public static List<ManpowerEmployee> getManpowerEmployeesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ManpowerEmployee> orderByComparator) {

		return getService().getManpowerEmployeesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of manpower employees.
	 *
	 * @return the number of manpower employees
	 */
	public static int getManpowerEmployeesCount() {
		return getService().getManpowerEmployeesCount();
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
	public static ManpowerEmployee saveManpowerEmployee(
		long manpowerEmployeeId, String designation, String name,
		java.util.Date appointmentDate, String contactNo, String email,
		String qualification, String experience, String deputation,
		String linkedinId, java.util.Date approvingAppointmentDate,
		long biodataFileId, int committeeMembershipType, String din,
		String directorType, String membershipType, String dependency,
		long formMbp, int status, int isDirector,
		java.util.Date resignationDate, String pfmName) {

		return getService().saveManpowerEmployee(
			manpowerEmployeeId, designation, name, appointmentDate, contactNo,
			email, qualification, experience, deputation, linkedinId,
			approvingAppointmentDate, biodataFileId, committeeMembershipType,
			din, directorType, membershipType, dependency, formMbp, status,
			isDirector, resignationDate, pfmName);
	}

	/**
	 * Updates the manpower employee in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManpowerEmployeeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manpowerEmployee the manpower employee
	 * @return the manpower employee that was updated
	 */
	public static ManpowerEmployee updateManpowerEmployee(
		ManpowerEmployee manpowerEmployee) {

		return getService().updateManpowerEmployee(manpowerEmployee);
	}

	public static ManpowerEmployeeLocalService getService() {
		return _service;
	}

	private static volatile ManpowerEmployeeLocalService _service;

}