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

import com.nps.manpower.model.ChangeInEmployee;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ChangeInEmployee. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeInEmployeeLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ChangeInEmployeeLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.nps.manpower.service.impl.ChangeInEmployeeLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the change in employee local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ChangeInEmployeeLocalServiceUtil} if injection and service tracking are not available.
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
	@Indexable(type = IndexableType.REINDEX)
	public ChangeInEmployee addChangeInEmployee(
		ChangeInEmployee changeInEmployee);

	/**
	 * Creates a new change in employee with the primary key. Does not add the change in employee to the database.
	 *
	 * @param changeInEmployeeId the primary key for the new change in employee
	 * @return the new change in employee
	 */
	@Transactional(enabled = false)
	public ChangeInEmployee createChangeInEmployee(long changeInEmployeeId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

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
	@Indexable(type = IndexableType.DELETE)
	public ChangeInEmployee deleteChangeInEmployee(
		ChangeInEmployee changeInEmployee);

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
	@Indexable(type = IndexableType.DELETE)
	public ChangeInEmployee deleteChangeInEmployee(long changeInEmployeeId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeInEmployeeModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ChangeInEmployeeModelImpl</code>.
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
	public ChangeInEmployee fetchChangeInEmployee(long changeInEmployeeId);

	/**
	 * Returns the change in employee matching the UUID and group.
	 *
	 * @param uuid the change in employee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching change in employee, or <code>null</code> if a matching change in employee could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeInEmployee fetchChangeInEmployeeByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * @param int committeeMembershipType
	 * @param long manpowerEmployeeId
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getAllByCommitteeTypeAndEmployeeId(
		int committeeMembershipType, long manpowerEmployeeId);

	/**
	 * @param String designation
	 * @param long manpowerEmployeeId
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getAllByDesignationAndEmployeeId(
		String designation, long manpowerEmployeeId);

	/**
	 * @param long manpowerEmployeeId
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getAllByEmployeeId(long manpowerEmployeeId);

	/**
	 * @param int isDirector
	 * @param long manpowerEmployeeId
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getAllByIsDirectorAndEmployeeId(
		int isDirector, long manpowerEmployeeId);

	/**
	 * @param int isDirector
	 * @param String membershipType
	 * @param long manpowerEmployeeId
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee>
		getAllByIsDirectorAndMembershipTypeAndEmployeeId(
			int isDirector, String membershipType, long manpowerEmployeeId);

	/**
	 * @param String membershipType
	 * @param long manpowerEmployeeId
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getAllByMembershipTypeAndEmployeeId(
		String membershipType, long manpowerEmployeeId);

	/**
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getAllByStatus(int status);

	/**
	 * @param String designation
	 * @param long manpowerEmployeeId
	 * @param int committeeMembershipType
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee>
		getAllDesignationAndCommitteeTypeAndEmployeeId(
			String designation, long manpowerEmployeeId,
			int committeeMembershipType);

	/**
	 * Returns the change in employee with the primary key.
	 *
	 * @param changeInEmployeeId the primary key of the change in employee
	 * @return the change in employee
	 * @throws PortalException if a change in employee with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeInEmployee getChangeInEmployee(long changeInEmployeeId)
		throws PortalException;

	/**
	 * Returns the change in employee matching the UUID and group.
	 *
	 * @param uuid the change in employee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching change in employee
	 * @throws PortalException if a matching change in employee could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeInEmployee getChangeInEmployeeByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getChangeInEmployees(int start, int end);

	/**
	 * Returns all the change in employees matching the UUID and company.
	 *
	 * @param uuid the UUID of the change in employees
	 * @param companyId the primary key of the company
	 * @return the matching change in employees, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getChangeInEmployeesByUuidAndCompanyId(
		String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeInEmployee> getChangeInEmployeesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ChangeInEmployee> orderByComparator);

	/**
	 * Returns the number of change in employees.
	 *
	 * @return the number of change in employees
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChangeInEmployeesCount();

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
	public ChangeInEmployee saveChangeInEmployee(
		long manpowerEmployeeId, String designation, String name,
		Date appointmentDate, String contactNo, String email,
		String qualification, String experience, String deputation,
		String linkedinId, Date approvingAppointmentDate, long biodataFileId,
		int committeeMembershipType, String din, String directorType,
		String membershipType, String dependency, long formMbp, int status,
		int isDirector, Date resignationDate);

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
	@Indexable(type = IndexableType.REINDEX)
	public ChangeInEmployee updateChangeInEmployee(
		ChangeInEmployee changeInEmployee);

}