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

import com.nps.manpower.model.ManpowerEmployee;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ManpowerEmployee. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ManpowerEmployeeLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ManpowerEmployeeLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.nps.manpower.service.impl.ManpowerEmployeeLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the manpower employee local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ManpowerEmployeeLocalServiceUtil} if injection and service tracking are not available.
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
	@Indexable(type = IndexableType.REINDEX)
	public ManpowerEmployee addManpowerEmployee(
		ManpowerEmployee manpowerEmployee);

	/**
	 * Creates a new manpower employee with the primary key. Does not add the manpower employee to the database.
	 *
	 * @param manpowerEmployeeId the primary key for the new manpower employee
	 * @return the new manpower employee
	 */
	@Transactional(enabled = false)
	public ManpowerEmployee createManpowerEmployee(long manpowerEmployeeId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

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
	@Indexable(type = IndexableType.DELETE)
	public ManpowerEmployee deleteManpowerEmployee(long manpowerEmployeeId)
		throws PortalException;

	/**
	 * @param long manpowerEmployeeId
	 * @param int deletedStatus
	 */
	public ManpowerEmployee deleteManpowerEmployee(
			long manpowerEmployeeId, int deletedStatus, Date resignationDate)
		throws PortalException;

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
	@Indexable(type = IndexableType.DELETE)
	public ManpowerEmployee deleteManpowerEmployee(
		ManpowerEmployee manpowerEmployee);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerEmployeeModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.manpower.model.impl.ManpowerEmployeeModelImpl</code>.
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
	public ManpowerEmployee fetchManpowerEmployee(long manpowerEmployeeId);

	/**
	 * Returns the manpower employee matching the UUID and group.
	 *
	 * @param uuid the manpower employee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower employee, or <code>null</code> if a matching manpower employee could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManpowerEmployee fetchManpowerEmployeeByUuidAndGroupId(
		String uuid, long groupId);

	/**
	 * @param isDirector
	 * @param pfmName
	 * @return
	 */
	public List<ManpowerEmployee> findByIsDirectorAndPfmName(
		int isDirector, String pfmName);

	/**
	 * @param isDirector
	 * @param pfmName
	 * @param status
	 * @return
	 */
	public List<ManpowerEmployee> findByIsDirectorAndPfmNameAndStatus(
		int isDirector, String pfmName, int status);

	/**
	 * @param committeeMembershipType
	 * @param pfmName
	 * @return
	 */
	public List<ManpowerEmployee> findByPfmAndCommitteeMembershipType(
		int committeeMembershipType, String pfmName);

	/**
	 * @param committeeMembershipType
	 * @param pfmName
	 * @param status
	 * @return
	 */
	public List<ManpowerEmployee> findByPfmAndCommitteeMembershipTypeAndStatus(
		int committeeMembershipType, String pfmName, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByCommitteeMembershipType(
		int committeeMembershipType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByDependencyAndPFM(
		String dependency, String pfm);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByDesignation(String designation);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByDesignationAndPFM(
		String designation, String pfm);

	/**
	 * @param String din
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByDIN(String din);

	/**
	 * @param String email
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByEmail(String email);

	/**
	 * @param isDirector
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByIsDirector(int isDirector);

	/**
	 * @param int isDirector
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByIsDirectorAndStatus(
		int isDirector, int status);

	/**
	 * @param int status
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByStatus(int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getAllByStatusAndCommitteeMembershipType(
		int status, int committeeMembershipType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the manpower employee with the primary key.
	 *
	 * @param manpowerEmployeeId the primary key of the manpower employee
	 * @return the manpower employee
	 * @throws PortalException if a manpower employee with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManpowerEmployee getManpowerEmployee(long manpowerEmployeeId)
		throws PortalException;

	/**
	 * Returns the manpower employee matching the UUID and group.
	 *
	 * @param uuid the manpower employee's UUID
	 * @param groupId the primary key of the group
	 * @return the matching manpower employee
	 * @throws PortalException if a matching manpower employee could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManpowerEmployee getManpowerEmployeeByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getManpowerEmployees(int start, int end);

	/**
	 * Returns all the manpower employees matching the UUID and company.
	 *
	 * @param uuid the UUID of the manpower employees
	 * @param companyId the primary key of the company
	 * @return the matching manpower employees, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getManpowerEmployeesByUuidAndCompanyId(
		String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManpowerEmployee> getManpowerEmployeesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ManpowerEmployee> orderByComparator);

	/**
	 * Returns the number of manpower employees.
	 *
	 * @return the number of manpower employees
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getManpowerEmployeesCount();

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
	public ManpowerEmployee saveManpowerEmployee(
		long manpowerEmployeeId, String designation, String name,
		Date appointmentDate, String contactNo, String email,
		String qualification, String experience, String deputation,
		String linkedinId, Date approvingAppointmentDate, long biodataFileId,
		int committeeMembershipType, String din, String directorType,
		String membershipType, String dependency, long formMbp, int status,
		int isDirector, Date resignationDate, String pfmName);

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
	@Indexable(type = IndexableType.REINDEX)
	public ManpowerEmployee updateManpowerEmployee(
		ManpowerEmployee manpowerEmployee);

}