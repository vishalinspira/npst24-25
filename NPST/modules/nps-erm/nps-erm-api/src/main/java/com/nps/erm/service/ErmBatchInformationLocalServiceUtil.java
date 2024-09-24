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

package com.nps.erm.service;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.nps.erm.model.ErmBatchInformation;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ErmBatchInformation. This utility wraps
 * <code>com.nps.erm.service.impl.ErmBatchInformationLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ErmBatchInformationLocalService
 * @generated
 */
public class ErmBatchInformationLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.nps.erm.service.impl.ErmBatchInformationLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the erm batch information to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmBatchInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermBatchInformation the erm batch information
	 * @return the erm batch information that was added
	 */
	public static ErmBatchInformation addErmBatchInformation(
		ErmBatchInformation ermBatchInformation) {

		return getService().addErmBatchInformation(ermBatchInformation);
	}

	public static ErmBatchInformation addErmBatchInformation(
		String ermInformationIds, String batchType,
		java.util.Date batchTimePeriodFrom, java.util.Date batchTimePeriodTo,
		java.util.Date cutOffDate, String stipulatedTime, String remark,
		long previousBatchId) {

		return getService().addErmBatchInformation(
			ermInformationIds, batchType, batchTimePeriodFrom,
			batchTimePeriodTo, cutOffDate, stipulatedTime, remark,
			previousBatchId);
	}

	/**
	 * Creates a new erm batch information with the primary key. Does not add the erm batch information to the database.
	 *
	 * @param ermBatchInformationId the primary key for the new erm batch information
	 * @return the new erm batch information
	 */
	public static ErmBatchInformation createErmBatchInformation(
		long ermBatchInformationId) {

		return getService().createErmBatchInformation(ermBatchInformationId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static ErmBatchInformation deleteErmBatch(
		long ermBatchInformationId, int batchStatus) {

		return getService().deleteErmBatch(ermBatchInformationId, batchStatus);
	}

	/**
	 * Deletes the erm batch information from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmBatchInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermBatchInformation the erm batch information
	 * @return the erm batch information that was removed
	 */
	public static ErmBatchInformation deleteErmBatchInformation(
		ErmBatchInformation ermBatchInformation) {

		return getService().deleteErmBatchInformation(ermBatchInformation);
	}

	/**
	 * Deletes the erm batch information with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmBatchInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermBatchInformationId the primary key of the erm batch information
	 * @return the erm batch information that was removed
	 * @throws PortalException if a erm batch information with the primary key could not be found
	 */
	public static ErmBatchInformation deleteErmBatchInformation(
			long ermBatchInformationId)
		throws PortalException {

		return getService().deleteErmBatchInformation(ermBatchInformationId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmBatchInformationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmBatchInformationModelImpl</code>.
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

	public static ErmBatchInformation fetchErmBatchInformation(
		long ermBatchInformationId) {

		return getService().fetchErmBatchInformation(ermBatchInformationId);
	}

	/**
	 * Returns the erm batch information matching the UUID and group.
	 *
	 * @param uuid the erm batch information's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erm batch information, or <code>null</code> if a matching erm batch information could not be found
	 */
	public static ErmBatchInformation fetchErmBatchInformationByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchErmBatchInformationByUuidAndGroupId(
			uuid, groupId);
	}

	public static ErmBatchInformation findByBatchNo(long batchNo) {
		return getService().findByBatchNo(batchNo);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the erm batch information with the primary key.
	 *
	 * @param ermBatchInformationId the primary key of the erm batch information
	 * @return the erm batch information
	 * @throws PortalException if a erm batch information with the primary key could not be found
	 */
	public static ErmBatchInformation getErmBatchInformation(
			long ermBatchInformationId)
		throws PortalException {

		return getService().getErmBatchInformation(ermBatchInformationId);
	}

	/**
	 * Returns the erm batch information matching the UUID and group.
	 *
	 * @param uuid the erm batch information's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erm batch information
	 * @throws PortalException if a matching erm batch information could not be found
	 */
	public static ErmBatchInformation getErmBatchInformationByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getErmBatchInformationByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the erm batch informations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmBatchInformationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erm batch informations
	 * @param end the upper bound of the range of erm batch informations (not inclusive)
	 * @return the range of erm batch informations
	 */
	public static List<ErmBatchInformation> getErmBatchInformations(
		int start, int end) {

		return getService().getErmBatchInformations(start, end);
	}

	/**
	 * Returns all the erm batch informations matching the UUID and company.
	 *
	 * @param uuid the UUID of the erm batch informations
	 * @param companyId the primary key of the company
	 * @return the matching erm batch informations, or an empty list if no matches were found
	 */
	public static List<ErmBatchInformation>
		getErmBatchInformationsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getErmBatchInformationsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of erm batch informations matching the UUID and company.
	 *
	 * @param uuid the UUID of the erm batch informations
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of erm batch informations
	 * @param end the upper bound of the range of erm batch informations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching erm batch informations, or an empty list if no matches were found
	 */
	public static List<ErmBatchInformation>
		getErmBatchInformationsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<ErmBatchInformation> orderByComparator) {

		return getService().getErmBatchInformationsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of erm batch informations.
	 *
	 * @return the number of erm batch informations
	 */
	public static int getErmBatchInformationsCount() {
		return getService().getErmBatchInformationsCount();
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
	 * Updates the erm batch information in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmBatchInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermBatchInformation the erm batch information
	 * @return the erm batch information that was updated
	 */
	public static ErmBatchInformation updateErmBatchInformation(
		ErmBatchInformation ermBatchInformation) {

		return getService().updateErmBatchInformation(ermBatchInformation);
	}

	public static ErmBatchInformationLocalService getService() {
		return _service;
	}

	private static volatile ErmBatchInformationLocalService _service;

}