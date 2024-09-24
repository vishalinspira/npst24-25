package com.nps.erm.command;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.nps.erm.constants.ErmFieldName;
import com.nps.erm.constants.ErmInformationPortletKeys;
import com.nps.erm.constants.NpstErmConstant;
import com.nps.erm.model.ErmInformation;
import com.nps.erm.service.ErmInformationLocalServiceUtil;
import com.nps.erm.util.ErmUtil;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.xmlbeans.impl.values.NamespaceManager;
import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.NameMappingConstants;
import npst.common.constant.NpstCommonConstant;
import npst.common.util.NpstCommonUtil;

@Component(immediate = true, property = {
		"javax.portlet.name="
				+ ErmInformationPortletKeys.ERMINFORMATION,
		"mvc.command.name=saveErmInformation",
}, service = MVCActionCommand.class)
public class SaveErmInformationActionCommand extends BaseMVCActionCommand{

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		
		try {
			ErmInformation ermInformation=null;
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
			long ermInformationId=ParamUtil.getLong(actionRequest, ErmFieldName.ERM_INFORMATION_ID);
			String batchType=ParamUtil.getString(actionRequest, ErmFieldName.BATCH_TYPE);
			logger.info("batchType :-  "+batchType);
			long transactedAmount=ParamUtil.getLong(actionRequest, ErmFieldName.TRANSACTED_AMOUNT);
			String pran=ParamUtil.getString(actionRequest, ErmFieldName.PRAN);
			Date transactionDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ErmFieldName.TRANSACTION_DATE));
			String transactionMode=ParamUtil.getString(actionRequest, ErmFieldName.TRANSACTION_MODE);
			Date transactionSettlementDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ErmFieldName.TRANSACTION_SETTLEMENT_DATE));
			String tokenNo=ParamUtil.getString(actionRequest, ErmFieldName.TOKEN_NO);
			String rectificationRequestMode=ParamUtil.getString(actionRequest, ErmFieldName.RECTIFICATION_REQUEST_MODE);
			Date rectificationDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ErmFieldName.RECTIFICATION_DATE));
			long remittedAmount=ParamUtil.getLong(actionRequest, ErmFieldName.REMITTED_AMOUNT);
			Date remittedDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ErmFieldName.REMITTED_DATE));
			String tierType=ParamUtil.getString(actionRequest, ErmFieldName.TIER_TYPE);
			String transactionType=ParamUtil.getString(actionRequest, ErmFieldName.TRANSACTION_TYPE);
			long transferAmount=ParamUtil.getLong(actionRequest, ErmFieldName.TRANSFER_AMOUNT);
			String transferAmount1=ParamUtil.getString(actionRequest, ErmFieldName.TRANSFER_AMOUNT1);
			String transferAmount2=ParamUtil.getString(actionRequest, ErmFieldName.TRANSFER_AMOUNT2);
			Date documentationsDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ErmFieldName.DOCUMENTATIONS_DATE));
			long rectificationAmount=ParamUtil.getLong(actionRequest, ErmFieldName.RECTIFICATION_AMOUNT);
			String caseNo=ParamUtil.getString(actionRequest, ErmFieldName.CASE_NO);
			String subscriberName=ParamUtil.getString(actionRequest, ErmFieldName.SUBSCRIBER_NAME);
			String enpsOrderId=ParamUtil.getString(actionRequest, ErmFieldName.ENPS_ORDER_ID);
			String rectificationType=ParamUtil.getString(actionRequest, ErmFieldName.RECTIFICATION_TYPE);
			Date rectificationRequestDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ErmFieldName.RECTIFICATION_REQUEST_DATE));
			Date grievanceReceivedDate=NpstCommonUtil.getDate(ParamUtil.getString(actionRequest, ErmFieldName.GRIEVANCE_RECEIVED_DATE));
			String grievanceText=ParamUtil.getString(actionRequest, ErmFieldName.GRIEVANCE_TEXT);
			String stipulated=ParamUtil.getString(actionRequest, ErmFieldName.STIPULATED);

			long ermFolderId=NpstCommonUtil.createOrGetFolderId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, ErmFieldName.ERM_MAIN_FOLDER, serviceContext);
			long selfDeclarationFolderId=NpstCommonUtil.createOrGetFolderId(ermFolderId, ErmFieldName.ERM_SELF_DECLARATION_FOLDER, serviceContext);
			long accountStatementFolderId=NpstCommonUtil.createOrGetFolderId(ermFolderId, ErmFieldName.ERM_ACCOUNT_STATEMENT_FOLDER, serviceContext);
			long transactionsStatementFolderId=NpstCommonUtil.createOrGetFolderId(ermFolderId, ErmFieldName.ERM_TRANSACTIONS_STATEMENT_FOLDER, serviceContext);
			long documentNameFolderId=NpstCommonUtil.createOrGetFolderId(ermFolderId, ErmFieldName.ERM_DOCUMET_FOLDER, serviceContext);
			long selfDeclarationFileId=0;
			long accountStatementFileId=0;
			long transactionsStatementFileId=0;
			long documentNameFileId=0;
			try {
				ermInformation=ErmInformationLocalServiceUtil.fetchErmInformation(ermInformationId);
				 selfDeclarationFileId=NpstCommonUtil.fileUpload(uploadRequest, selfDeclarationFolderId, ErmFieldName.SELF_DECLARATION_FILE, serviceContext);
				}catch (Exception e) {
					logger.info(ermInformation);
					if(ermInformation!=null && !ermInformation.equals(null)) {
						selfDeclarationFileId=ermInformation.getSelfDeclarationFileId();
					}
					logger.error("error while uploading selfDeclarationFileId  "+selfDeclarationFileId +"   "+e.getMessage());
				}
			
			try {
				accountStatementFileId=NpstCommonUtil.fileUpload(uploadRequest, accountStatementFolderId, ErmFieldName.ACCOUNT_STATEMENT_FILE, serviceContext);
				}catch (Exception e) {
					if(ermInformation!=null && !ermInformation.equals(null)) {
						accountStatementFileId=ermInformation.getAccountStatementFileId();
					}
					logger.error("error while uploading accountStatementFileId  "+accountStatementFileId+"    "+e.getMessage());
				}
			try {
				 transactionsStatementFileId=NpstCommonUtil.fileUpload(uploadRequest, transactionsStatementFolderId, ErmFieldName.TRANSACTIONS_STATEMENT_FILE, serviceContext);
				}catch (Exception e) {
					if(ermInformation!=null && !ermInformation.equals(null)) {
						transactionsStatementFileId=ermInformation.getTransactionsStatementFileId();
					}
					logger.error("error while uploading transactionsStatementFileId  "+transactionsStatementFileId+"    "+e.getMessage());
				}
			
			try {
				documentNameFileId=NpstCommonUtil.fileUpload(uploadRequest, documentNameFolderId, ErmFieldName.DOCUMENT_FILE, serviceContext);
				}catch (Exception e) {
					if(ermInformation!=null && !ermInformation.equals(null)) {
						documentNameFileId=ermInformation.getDocumentNameFileId();
					}
					logger.error("error while uploading documentNameFileId  "+documentNameFileId+"    "+e.getMessage());
				}
			
			if(ermInformationId>0) {
				logger.info("before save erm:  "+ ermInformationId);
				ermInformation = ErmInformationLocalServiceUtil.updateErmInformation(ermInformationId, batchType, transactedAmount, pran, transactionDate, transactionMode, transactionSettlementDate, tokenNo, rectificationRequestMode, rectificationDate, remittedAmount, remittedDate, tierType, transactionType, transferAmount,transferAmount1,transferAmount2, documentationsDate, rectificationAmount, caseNo, subscriberName, enpsOrderId, rectificationType, rectificationRequestDate, grievanceReceivedDate, grievanceText, stipulated, "", selfDeclarationFileId, accountStatementFileId, transactionsStatementFileId, documentNameFileId);
				logger.info("after save erm:  "+ ermInformationId);
				if(serviceContext.getUserId()==ermInformation.getUserId()) {
					logger.info("before resubmit form");
				ErmUtil.resubmitForm(ermInformation.getPrimaryKey(), NpstErmConstant.RESUBMIT_TRANSTION_NAME, "resubmit", serviceContext.getUserId());
			
				}}else {
					String pfmName=NpstCommonUtil.getExpandoValue(serviceContext.getCompanyId(), serviceContext.getUserId(), User.class.getName(), NpstCommonConstant.GRIVANCES_TYPE);
					//pfmName=NameMappingConstants.CRA_NAME_MAP.get(pfmName);
							logger.info("pfmName: "+pfmName);
					ermInformation =ErmInformationLocalServiceUtil.addErmInformation(ermInformationId, batchType, transactedAmount, pran, transactionDate, transactionMode, transactionSettlementDate, tokenNo, rectificationRequestMode, rectificationDate, remittedAmount, remittedDate, tierType, transactionType, transferAmount,transferAmount1,transferAmount2, documentationsDate, rectificationAmount, caseNo, subscriberName, enpsOrderId, rectificationType, rectificationRequestDate, grievanceReceivedDate, grievanceText, stipulated, "", selfDeclarationFileId, accountStatementFileId, transactionsStatementFileId, documentNameFileId,pfmName);
			}
			
			actionRequest.setAttribute(ErmFieldName.ERM_INFORMATION_ID, ermInformation.getErmInformationId()+"");
			
		} catch (Exception e) {
			logger.error(e.getCause()+"    "+e.getMessage());
		}
		SessionMessages.add(actionRequest,"success-key");
		actionResponse.setRenderParameter("mvcPath", "/view.jsp");
	}
	
	Log logger=LogFactoryUtil.getLog(SaveErmInformationActionCommand.class.getName());

}
