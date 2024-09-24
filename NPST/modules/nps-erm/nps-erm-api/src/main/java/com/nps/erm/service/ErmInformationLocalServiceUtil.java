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

import com.nps.erm.model.ErmInformation;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ErmInformation. This utility wraps
 * <code>com.nps.erm.service.impl.ErmInformationLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ErmInformationLocalService
 * @generated
 */
public class ErmInformationLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.nps.erm.service.impl.ErmInformationLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the erm information to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermInformation the erm information
	 * @return the erm information that was added
	 */
	public static ErmInformation addErmInformation(
		ErmInformation ermInformation) {

		return getService().addErmInformation(ermInformation);
	}

	public static ErmInformation addErmInformation(
		long ermInformationId, String batchType, long transactedAmount,
		String pran, java.util.Date transactionDate, String transactionMode,
		java.util.Date transactionSettlementDate, String tokenNo,
		String rectificationRequestMode, java.util.Date rectificationDate,
		long remittedAmount, java.util.Date remittedDate, String tierType,
		String transactionType, long transferAmount, String transferAmount1,
		String transferAmount2, java.util.Date documentationsDate,
		long rectificationAmount, String caseNo, String subscriberName,
		String enpsOrderId, String rectificationType,
		java.util.Date rectificationRequestDate,
		java.util.Date grievanceReceivedDate, String grievanceText,
		String stipulated, String remark, long selfDeclarationFileId,
		long accountStatementFileId, long transactionsStatementFileId,
		long documentNameFileId, String pfmName) {

		return getService().addErmInformation(
			ermInformationId, batchType, transactedAmount, pran,
			transactionDate, transactionMode, transactionSettlementDate,
			tokenNo, rectificationRequestMode, rectificationDate,
			remittedAmount, remittedDate, tierType, transactionType,
			transferAmount, transferAmount1, transferAmount2,
			documentationsDate, rectificationAmount, caseNo, subscriberName,
			enpsOrderId, rectificationType, rectificationRequestDate,
			grievanceReceivedDate, grievanceText, stipulated, remark,
			selfDeclarationFileId, accountStatementFileId,
			transactionsStatementFileId, documentNameFileId, pfmName);
	}

	/**
	 * Creates a new erm information with the primary key. Does not add the erm information to the database.
	 *
	 * @param ermInformationId the primary key for the new erm information
	 * @return the new erm information
	 */
	public static ErmInformation createErmInformation(long ermInformationId) {
		return getService().createErmInformation(ermInformationId);
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
	 * Deletes the erm information from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermInformation the erm information
	 * @return the erm information that was removed
	 */
	public static ErmInformation deleteErmInformation(
		ErmInformation ermInformation) {

		return getService().deleteErmInformation(ermInformation);
	}

	/**
	 * Deletes the erm information with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermInformationId the primary key of the erm information
	 * @return the erm information that was removed
	 * @throws PortalException if a erm information with the primary key could not be found
	 */
	public static ErmInformation deleteErmInformation(long ermInformationId)
		throws PortalException {

		return getService().deleteErmInformation(ermInformationId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmInformationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmInformationModelImpl</code>.
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

	public static ErmInformation fetchErmInformation(long ermInformationId) {
		return getService().fetchErmInformation(ermInformationId);
	}

	/**
	 * Returns the erm information matching the UUID and group.
	 *
	 * @param uuid the erm information's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erm information, or <code>null</code> if a matching erm information could not be found
	 */
	public static ErmInformation fetchErmInformationByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchErmInformationByUuidAndGroupId(uuid, groupId);
	}

	public static List<ErmInformation> findByBatch(String batchNo) {
		return getService().findByBatch(batchNo);
	}

	public static List<ErmInformation> findByBatchNoAndBatchStatus(
		String batchNo, int batchStatus) {

		return getService().findByBatchNoAndBatchStatus(batchNo, batchStatus);
	}

	public static List<ErmInformation> findByPfmName(String pfmName) {
		return getService().findByPfmName(pfmName);
	}

	public static List<ErmInformation> findByPran(long groupId, String pran) {
		return getService().findByPran(groupId, pran);
	}

	public static List<ErmInformation> findByUserId(long userId) {
		return getService().findByUserId(userId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the erm information with the primary key.
	 *
	 * @param ermInformationId the primary key of the erm information
	 * @return the erm information
	 * @throws PortalException if a erm information with the primary key could not be found
	 */
	public static ErmInformation getErmInformation(long ermInformationId)
		throws PortalException {

		return getService().getErmInformation(ermInformationId);
	}

	/**
	 * Returns the erm information matching the UUID and group.
	 *
	 * @param uuid the erm information's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erm information
	 * @throws PortalException if a matching erm information could not be found
	 */
	public static ErmInformation getErmInformationByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getErmInformationByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the erm informations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.nps.erm.model.impl.ErmInformationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erm informations
	 * @param end the upper bound of the range of erm informations (not inclusive)
	 * @return the range of erm informations
	 */
	public static List<ErmInformation> getErmInformations(int start, int end) {
		return getService().getErmInformations(start, end);
	}

	/**
	 * Returns all the erm informations matching the UUID and company.
	 *
	 * @param uuid the UUID of the erm informations
	 * @param companyId the primary key of the company
	 * @return the matching erm informations, or an empty list if no matches were found
	 */
	public static List<ErmInformation> getErmInformationsByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getErmInformationsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of erm informations matching the UUID and company.
	 *
	 * @param uuid the UUID of the erm informations
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of erm informations
	 * @param end the upper bound of the range of erm informations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching erm informations, or an empty list if no matches were found
	 */
	public static List<ErmInformation> getErmInformationsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ErmInformation> orderByComparator) {

		return getService().getErmInformationsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of erm informations.
	 *
	 * @return the number of erm informations
	 */
	public static int getErmInformationsCount() {
		return getService().getErmInformationsCount();
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
	 * Updates the erm information in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ErmInformationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ermInformation the erm information
	 * @return the erm information that was updated
	 */
	public static ErmInformation updateErmInformation(
		ErmInformation ermInformation) {

		return getService().updateErmInformation(ermInformation);
	}

	public static ErmInformation updateErmInformation(
		long ermInformationId, String batchType, long transactedAmount,
		String pran, java.util.Date transactionDate, String transactionMode,
		java.util.Date transactionSettlementDate, String tokenNo,
		String rectificationRequestMode, java.util.Date rectificationDate,
		long remittedAmount, java.util.Date remittedDate, String tierType,
		String transactionType, long transferAmount, String transferAmount1,
		String transferAmount2, java.util.Date documentationsDate,
		long rectificationAmount, String caseNo, String subscriberName,
		String enpsOrderId, String rectificationType,
		java.util.Date rectificationRequestDate,
		java.util.Date grievanceReceivedDate, String grievanceText,
		String stipulated, String remark, long selfDeclarationFileId,
		long accountStatementFileId, long transactionsStatementFileId,
		long documentNameFileId) {

		return getService().updateErmInformation(
			ermInformationId, batchType, transactedAmount, pran,
			transactionDate, transactionMode, transactionSettlementDate,
			tokenNo, rectificationRequestMode, rectificationDate,
			remittedAmount, remittedDate, tierType, transactionType,
			transferAmount, transferAmount1, transferAmount2,
			documentationsDate, rectificationAmount, caseNo, subscriberName,
			enpsOrderId, rectificationType, rectificationRequestDate,
			grievanceReceivedDate, grievanceText, stipulated, remark,
			selfDeclarationFileId, accountStatementFileId,
			transactionsStatementFileId, documentNameFileId);
	}

	public static ErmInformation updateStatus(
		long userId, long ermInformationId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return getService().updateStatus(
			userId, ermInformationId, status, serviceContext);
	}

	public static ErmInformationLocalService getService() {
		return _service;
	}

	private static volatile ErmInformationLocalService _service;

}