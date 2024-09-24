package com.nps.erm.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.nps.erm.constants.ErmConstantValues;
import com.nps.erm.constants.ErmFieldName;
import com.nps.erm.constants.ErmInformationPortletKeys;
import com.nps.erm.constants.NpstErmConstant;
import com.nps.erm.modal.ErmModal;
import com.nps.erm.model.ErmBatchInformation;
import com.nps.erm.model.ErmInformation;
import com.nps.erm.service.ErmBatchInformationLocalServiceUtil;
import com.nps.erm.service.ErmInformationLocalServiceUtil;
import com.nps.erm.util.CreateBatchPDFUtil;
import com.nps.erm.util.ERMFormPDFUtil;
import com.nps.erm.util.ErmUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import npst.common.constant.NpstCommonConstant;
import npst.common.util.NpstCommonUtil;

/**
 * @author VishalKumar
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ErmInformation", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ErmInformationPortletKeys.ERMINFORMATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class ErmInformationPortlet extends MVCPortlet {


	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		log.info("resource Id: "+resourceRequest.getResourceID());
		
		if(resourceRequest.getResourceID().equalsIgnoreCase("performErmWorkflowTask")) {
			PrintWriter pw=resourceResponse.getWriter();
			try {
			long ermId=ParamUtil.getLong(resourceRequest, ErmFieldName.ERM_INFORMATION_ID);
			String[] ermIds= {String.valueOf(ermId)};
			String action=ParamUtil.getString(resourceRequest, "action");
			String process=ParamUtil.getString(resourceRequest, "process");
			String comment=ParamUtil.getString(resourceRequest, ErmFieldName.COMMENT);
			String recommendation=ParamUtil.getString(resourceRequest, ErmFieldName.RECOMMENDATION);
			ErmUtil.performWorkflowTasks(ermIds, action, comment,process,recommendation, serviceContext.getUserId(),serviceContext.getCompanyId());
			log.info("ermId:  "+ermId+"   action:  "+action+"   comment:  "+comment);
			pw.write("success");
			pw.close();
			}catch (Exception e) {
				log.info(e.getMessage());
			}
		}else if(resourceRequest.getResourceID().equalsIgnoreCase("generateBatch")) {
			PrintWriter pw=resourceResponse.getWriter();
		String ids=ParamUtil.getString(resourceRequest, "ermIds");
		String remarks[]=ParamUtil.getString(resourceRequest, ErmFieldName.REMARK).split(NpstCommonConstant.DOUBLE_COLON);
		String recommendations[]=ParamUtil.getString(resourceRequest, ErmFieldName.RECOMMENDATION).split(NpstCommonConstant.DOUBLE_COLON);
		String stipulatedTimes[]=ParamUtil.getString(resourceRequest, ErmFieldName.STIPULATED_TIMES).split(NpstCommonConstant.DOUBLE_COLON);
		String submissionStipulatedTimes[]=ParamUtil.getString(resourceRequest, ErmFieldName.STIPULATED_TIMES).split(NpstCommonConstant.DOUBLE_COLON);
		
		String bankNames[]=ParamUtil.getString(resourceRequest, ErmFieldName.BANK_NAME).split(NpstCommonConstant.DOUBLE_COLON);
		String bankStatementsNames[]=ParamUtil.getString(resourceRequest, ErmFieldName.BANK_STATEMENT_NAME).split(NpstCommonConstant.DOUBLE_COLON);
		String txnDates[]=ParamUtil.getString(resourceRequest, ErmFieldName.TXN_DATE).split(NpstCommonConstant.DOUBLE_COLON);
		String selfRectDates[]=ParamUtil.getString(resourceRequest, ErmFieldName.SELF_RECTIFIED_DATE).split(NpstCommonConstant.DOUBLE_COLON);
		String subscribSectorr[]=ParamUtil.getString(resourceRequest, ErmFieldName.SUBSCRIBER_SECTOR).split(NpstCommonConstant.DOUBLE_COLON);
		
		//Date cutOffDate=NpstCommonUtil.getDate(ParamUtil.getString(resourceRequest, ErmFieldName.RECEIPT_CUTOFF_DATE));
		Date batchTimePeriodFrom=NpstCommonUtil.getDate(ParamUtil.getString(resourceRequest, ErmFieldName.BATCH_TIME_PERIOD_FROM));
		Date batchTimePeriodTo=NpstCommonUtil.getDate(ParamUtil.getString(resourceRequest, ErmFieldName.BATCH_TIME_PERIOD_To));
		log.info("remarks size : "+remarks.length+" recommendations size:  "+recommendations.length+"  stipulatedTimes size : "+stipulatedTimes.length+" submissionStipulatedTimes size:  "+submissionStipulatedTimes.length);
		log.info("batchTimePeriodFrom : "+batchTimePeriodFrom+"     batchTimePeriodTo : "+batchTimePeriodTo);
		String stipulatedTime=null;
		String ermIds[]=ids.split(StringPool.COMMA);
		try {
		if(isSameBatchType(ermIds)) {
			long batchNo=0;
			String batchType=ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermIds[0])).getBatchType();
			long previousBatchId=0;
			try {
			List<ErmBatchInformation>batchInformations=ErmBatchInformationLocalServiceUtil.getErmBatchInformations(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			previousBatchId=batchInformations.get(batchInformations.size()-1).getErmBatchInformationId();
			}catch (Exception e) {
			log.error(e.getMessage());
			}
			ErmBatchInformation ermBatchInformation =ErmBatchInformationLocalServiceUtil.createErmBatchInformation(CounterLocalServiceUtil.increment(ErmBatchInformation.class.getName()));
			ermBatchInformation=ErmBatchInformationLocalServiceUtil.addErmBatchInformation(ids, batchType, batchTimePeriodFrom,batchTimePeriodTo, new Date(), stipulatedTime, "",previousBatchId);
		String craName="";
			ErmInformation ermInformation=null;
		int index=0;
		 batchNo=ErmUtil.getMaxBatchValue()+1;
		for(String ermId:ermIds) {
			String remark="";
			String recommendation="";
			String stipulated="";
			String submissionStipulatedTime="";
			String bankName="";
			String bankStatementName="";
			Date txnDate=null;
			Date selfRectDate=null;
			String subsSector="";
			try {
				
				try {
			 remark=remarks[index];
				}catch (Exception e) {
				log.error(e.getMessage());
			}
			//log.info("remark info --   "+remark);
				try {
			 recommendation=recommendations[index];
			}catch (Exception e) {
				log.error(e.getMessage());
			}
			//log.info("recommendation info --   "+recommendation);
			//log.info("stipulated time: "+stipulatedTimes.length);
			
			//log.info("stipulated info --   "+stipulated);
			
			//log.info("ermId:  "+ermId+"   remark:  "+remark+"   recommendation  :"+recommendation+"       stipulated:  "+stipulated);
			
			ermInformation= ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
			craName=ermInformation.getPfmName();
			ermBatchInformation.setPfmName(craName);
			 ermInformation.setRemark(remark);
			 ermInformation.setNpstRemark(remark);
			 ermInformation.setRecommendation(recommendation);
			 if(batchType.equalsIgnoreCase(ErmConstantValues.NEW_BATCH_TYPE)) {
				 try {
				  stipulated=stipulatedTimes[index];
			 }catch (Exception e) {
					log.error(e.getMessage());
				}
				 try {
				  submissionStipulatedTime=submissionStipulatedTimes[index];
			 }catch (Exception e) {
					log.error(e.getMessage());
				}
				 
				 try {
					  bankName=bankNames[index];
				 }catch (Exception e) {
						log.error(e.getMessage());
					}
				 try {
					  bankStatementName=bankStatementsNames[index];
				 }catch (Exception e) {
						log.error(e.getMessage());
					}
				 try {
					  txnDate=NpstCommonUtil.getDate(txnDates[index]);
				 }catch (Exception e) {
						log.error(e.getMessage());
					}
				 try {
					  selfRectDate=NpstCommonUtil.getDate(selfRectDates[index]);
				 }catch (Exception e) {
						log.error(e.getMessage());
					}
				 try {
					  subsSector=subscribSectorr[index];
				 }catch (Exception e) {
						log.error(e.getMessage());
					}
				 
			  ermInformation.setStipulated(stipulated);
			  ermInformation.setSubmissionStipulatedTime(submissionStipulatedTime);
			 }
			 ermInformation.setBankName(bankName);
			 ermInformation.setBankStatmentName(bankStatementName);
			 ermInformation.setSelfRectifiedDate(selfRectDate);
			 ermInformation.setTxnDate(txnDate);
			 ermInformation.setSubscriberSector(subsSector);
			 
			 ermInformation.setBatchNo(batchNo+"");
			 ermBatchInformation.setBatchNo(batchNo);
			 log.info("bank name: "+bankName+"  rect date: "+selfRectDate+" txn date: "+txnDate+"  sector:  "+subsSector);
			 ermInformation.setBatchStatus(NpstErmConstant.BATCH_PENDING);
			 log.info("erm Id: "+ermId+" ermInformationid :  "+ermInformation.getErmInformationId()+"   Batch Id: "+ ermBatchInformation.getErmBatchInformationId() );
			 ErmInformationLocalServiceUtil.updateErmInformation(ermInformation);
			 ErmBatchInformationLocalServiceUtil.updateErmBatchInformation(ermBatchInformation);
			 index=index+1;
			}catch (Exception e) {
				log.error(e);
				//log.error(e.getMessage());
			}	
					}
	//	List<ErmBatchInformation> batchInformations= ErmBatchInformationLocalServiceUtil.getErmBatchInformations(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		//log.info("Batch Number : "+ ermBatchInformation.getErmBatchInformationId());
		log.info("Batch Number : "+ batchNo+"");
		pw.write("Batch Number : "+ batchNo);
		pw.close();
		}else {
			pw.write("Kindly select same batch type.");
		}
		
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		}else if(resourceRequest.getResourceID().equalsIgnoreCase("/erm/batch/download")) {
			long batchId =ParamUtil.getLong(resourceRequest, ErmFieldName.ERM_BATCH_INFORMATION_ID);
			try {
			ErmBatchInformation batchInformation= ErmBatchInformationLocalServiceUtil.getErmBatchInformation(batchId);
			
			File file=ErmUtil.exportToExcel1(batchInformation);
			//File file=CreateBatchPDFUtil.createBatchPDF(serviceContext.getCompanyId() ,serviceContext.getUserId(),batchId);
			
				InputStream inputStream = new FileInputStream(file);
				//long fileId=NpstCommonUtil.fileUpload(file, 0, ContentTypes.APPLICATION_TEXT, file.getName(), serviceContext);
				PortletResponseUtil.sendFile(resourceRequest, resourceResponse,"File1.csv", inputStream, ContentTypes.APPLICATION_TEXT);
			} catch (Exception e) {
				log.error("error while downloading the file"+e.getMessage());
			}
			
			
		}else if(resourceRequest.getResourceID().equalsIgnoreCase("/erm/batch/download2")) {
			long batchId =ParamUtil.getLong(resourceRequest, ErmFieldName.ERM_BATCH_INFORMATION_ID);
			try {
			ErmBatchInformation batchInformation= ErmBatchInformationLocalServiceUtil.getErmBatchInformation(batchId);
			
			File file=ErmUtil.exportToExcel2(batchInformation);
			//File file=CreateBatchPDFUtil.createBatchPDF(serviceContext.getCompanyId() ,serviceContext.getUserId(),batchId);
			
				InputStream inputStream = new FileInputStream(file);
				//long fileId=NpstCommonUtil.fileUpload(file, 0, ContentTypes.APPLICATION_TEXT, file.getName(), serviceContext);
				PortletResponseUtil.sendFile(resourceRequest, resourceResponse,"File2.csv", inputStream, ContentTypes.APPLICATION_TEXT);
			} catch (Exception e) {
				log.error("error while downloading the file"+e.getMessage());
			}
			
			
		}else if(resourceRequest.getResourceID().equalsIgnoreCase("/erm/formpdf/download")) {
			long ermId=ParamUtil.getLong(resourceRequest, ErmFieldName.ERM_INFORMATION_ID);
			try {
				File file=ERMFormPDFUtil.createFormPDF(ermId);
					InputStream inputStream = new FileInputStream(file);
					PortletResponseUtil.sendFile(resourceRequest, resourceResponse,"ERM.pdf", inputStream, ContentTypes.APPLICATION_TEXT);
				} catch (Exception e) {
					log.error("error while downloading the file"+e.getMessage());
				}
		}
		else if(resourceRequest.getResourceID().equalsIgnoreCase("/erm/excel/export")) {
			//long ermId=ParamUtil.getLong(resourceRequest, ErmFieldName.ERM_INFORMATION_ID);
			try {
				ServiceContext context=ServiceContextThreadLocal.getServiceContext();
				List<ErmModal> ermModals= ErmUtil.getErmStatusModal(context.getCompanyId(), context.getUserId());
				File file=ErmUtil.exportExcel(ermModals);
					InputStream inputStream = new FileInputStream(file);
					PortletResponseUtil.sendFile(resourceRequest, resourceResponse,"Exported.csv", inputStream, ContentTypes.APPLICATION_TEXT);
				} catch (Exception e) {
					log.error("error while downloading the file"+e.getMessage());
				}
		}
		//log.info("erm IDs : "+ermIds);
	}
	
		
	/**
	 * 
	 * @param ermIds
	 * @return
	 */
	private  boolean isSameBatchType(String ermIds[]) {
		ErmInformation ermInformation=null;
		boolean isSameBatchType=true;
		String batchType="";
		for(String ermId:ermIds) {
			try {
				ermInformation=ErmInformationLocalServiceUtil.getErmInformation(Long.valueOf(ermId));
			if(Validator.isNull(batchType)) {
				batchType=ermInformation.getBatchType();			
				}else {
					if(!batchType.equals(ermInformation.getBatchType())) {
						return false;
					}
				}
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return isSameBatchType;
		
	}
	
	@ProcessAction(name = "updateBatch")
	public void updateBatch(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		try {
			long ermInformationId=ParamUtil.getLong(actionRequest, ErmFieldName.ERM_INFORMATION_ID);
			String batchNumber=ParamUtil.getString(actionRequest,ErmFieldName.ERM_BATCH_NUMBER_ID);
			String isCancelable=ParamUtil.getString(actionRequest,"isCancelable");
			
			ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(ermInformationId);
			//ErmBatchInformation batchInformation=ErmBatchInformationLocalServiceUtil.getErmBatchInformation(Long.valueOf(ermInformation.getBatchNo()));
			
			ErmBatchInformation batchInformation=ErmBatchInformationLocalServiceUtil.findByBatchNo(Long.valueOf(ermInformation.getBatchNo()));
			String ermIds[]=batchInformation.getErmInformationIds().split(StringPool.COMMA);
			String ermInfortionIds="";
			log.info("ermInformationId  :  "+ermInformationId);
			for(String ermId:ermIds) {
				log.info("ermId: "+ ermId +"  ermInformationId:  "+ermInformationId);
				if(ermId!=ermInformationId+"" && !ermId.equals(ermInformationId+"") ) {
					if(ermInfortionIds=="" || ermInfortionIds.equals("")) {
						ermInfortionIds=ermInfortionIds+ermId;
					}else {
						ermInfortionIds=ermInfortionIds+StringPool.COMMA+ermId;
					}
				}else {
					continue;
				}
			}
			log.info("ermInfortionIds---------  :  "+ermInfortionIds);
			ermInformation.setBatchNo("");
			 ermInformation.setBatchStatus(NpstErmConstant.BATCH_CANCELLED);
			batchInformation.setErmInformationIds(ermInfortionIds);
			ErmInformationLocalServiceUtil.updateErmInformation(ermInformation);
			ErmBatchInformationLocalServiceUtil.updateErmBatchInformation(batchInformation);
			actionRequest.setAttribute(ErmFieldName.ERM_BATCH_NUMBER_ID,batchNumber);
			actionRequest.setAttribute("isCancelable",isCancelable);
			actionResponse.setRenderParameter("mvcPath", "/view-batch-records.jsp");
		}catch (Exception e) {
			log.error(e);
		}
			
	}

	
	@ProcessAction(name = "deleteBatch")
	public void deleteBatch(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		long batchId=ParamUtil.getLong(actionRequest, ErmFieldName.ERM_BATCH_NUMBER_ID);
		ErmBatchInformationLocalServiceUtil.deleteErmBatch(batchId,NpstErmConstant.BATCH_CANCELLED);	
		actionResponse.setRenderParameter("mvcPath", "/pending-batch.jsp");
	}
	
	@ProcessAction(name = "performErmWorkflowTask")
	public void performErmWorkflowTask(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
	
		long ermId=ParamUtil.getLong(actionRequest, ErmFieldName.ERM_INFORMATION_ID);
		String[] ermIds= {String.valueOf(ermId)};
		String action=ParamUtil.getString(actionRequest, "action");
		String jspName=ParamUtil.getString(actionRequest, "jspName");
		String comment=ParamUtil.getString(actionRequest, ErmFieldName.COMMENT);
		ErmUtil.performWorkflowTasks(ermIds, action, comment,"","", serviceContext.getUserId(),serviceContext.getCompanyId());
		log.info("ermId:  "+ermId+"   action:  "+action+"   comment:  "+comment);
		actionResponse.setRenderParameter("mvcPath", jspName);
	}
	
		@ProcessAction(name = "performWorkflowTask")
		public void performWorkflowTask(ActionRequest actionRequest, ActionResponse actionResponse)
				throws IOException, PortletException, PortalException {
			ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
			long batchId=ParamUtil.getLong(actionRequest, ErmFieldName.ERM_BATCH_NUMBER_ID);
			ErmBatchInformation batchInformation=ErmBatchInformationLocalServiceUtil.getErmBatchInformation(batchId);
			String action=ParamUtil.getString(actionRequest, "action");
			log.info("batch Id: "+ batchId+ " ---  action :- "+action);
			ErmUtil.performWorkflowTasks(batchInformation.getErmInformationIds().split(StringPool.COMMA), action, action,"","", serviceContext.getUserId(),serviceContext.getCompanyId());
			batchInformation.setSubmissionDate(new Date());
			ErmBatchInformationLocalServiceUtil.updateErmBatchInformation(batchInformation);
			actionResponse.setRenderParameter("mvcPath", "/pending-batch.jsp");
		}
	
	public static Log log=LogFactoryUtil.getLog(ErmInformationPortlet.class.getName());
	
}