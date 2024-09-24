package com.nps.manPower.resource;

import com.daily.average.service.model.Manpowerform_ii;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.Manpowerform_iiLocalService;
import com.daily.average.service.service.Manpowerform_iiLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.pfm.service.service.IntrstOfDirecInOtherCompLocalService;
import com.daily.pfm.service.service.IntrstOfDirectorsOfPfLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.nps.manPower.constants.ManPowerForm2PortletKeys;
import com.nps.manPower.util.DocumentUtil;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.CommonRoleService;


@Component(property = { 
		"javax.portlet.name=" + ManPowerForm2PortletKeys.MANPOWERFORM2,
		"mvc.command.name=" +ManPowerForm2PortletKeys.manPower ,
		}, 
service = MVCResourceCommand.class)

public class SaveManPowerForm2 extends BaseMVCResourceCommand{

	private static Log _log = LogFactoryUtil.getLog(SaveManPowerForm2.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("Serve Resource method");
		
		boolean isError = false;
		
		try {
			
			manPower(resourceRequest, resourceResponse);
			
		} catch (Exception e) {
			isError = true;
			_log.error(e);
		}

		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				_log.info("Success");
				writer.print("Success");
			}
			else {
				_log.info("Error");
				writer.print("Error");
			}
		} catch (Exception e) {
			_log.error(e);
		}	

		
	}

	public void manPower(ResourceRequest resourceRequest,ResourceResponse resourceResponse) {
		_log.info("Inside manPower" );
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
	
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		//table-1
		String directorName = ParamUtil.getString(resourceRequest, "director");
		Date  submissionDate= ParamUtil.getDate(resourceRequest, "submission", dateFormat);
		long fileEntryId=DocumentUtil.addDocuments(resourceRequest,"form");
		_log.info("file id ::::"+fileEntryId);
		
		_log.info(" directorName ::" + directorName);
		_log.info(" submissionDate ::" + submissionDate);
		_log.info(":::::::::::   1  :::::::::::: " );
		
		//table-2
		for(int i=1;i<=3;i++) {
			String companies1 = ParamUtil.getString(resourceRequest, "companies_"+String.valueOf(i));
			String nature1 = ParamUtil.getString(resourceRequest, "nature_"+String.valueOf(i));
			String shareholding1 = ParamUtil.getString(resourceRequest, "shareholding_"+String.valueOf(i));
			Date  dateOfinterest1= ParamUtil.getDate(resourceRequest, "date_"+String.valueOf(i), dateFormat);
				
			
			_log.info(" companies ::" + companies1 + " nature1 :::" + nature1 + " shareholding:::: " + shareholding1 + " dateOfinterest::: " + dateOfinterest1);
			try {
				intrstOfDirecInOtherCompLocalService.addIntrstOfDirecInOtherComp(companies1, nature1, shareholding1, dateOfinterest1, new Date(), themeDisplay.getUserId(), reportUploadLogId);
			} catch (Exception e) {
				 _log.error(e);
			}
		
		}
		
		/*
		 * String companies2 = ParamUtil.getString(resourceRequest, "companies_2");
		 * String nature2 = ParamUtil.getString(resourceRequest, "nature_2"); String
		 * shareholding2 = ParamUtil.getString(resourceRequest, "shareholding_2"); Date
		 * dateOfinterest2= ParamUtil.getDate(resourceRequest, "date_2", dateFormat);
		 * 
		 * String companies3 = ParamUtil.getString(resourceRequest, "companies_3");
		 * String nature3 = ParamUtil.getString(resourceRequest, "nature_3"); String
		 * shareholding3 = ParamUtil.getString(resourceRequest, "shareholding_3"); Date
		 * dateOfinterest3= ParamUtil.getDate(resourceRequest, "date_3", dateFormat);
		 */
		
		/*
		 * _log.info(" companies ::" + companies1); _log.info(" nature ::" + nature1);
		 * _log.info(" shareholding ::" + shareholding1); _log.info(" dateOfinterest ::"
		 * + dateOfinterest1); _log.info("::::::::::::::::  2:::::::::::::::::: " );
		 * _log.info(" companies ::" + companies2); _log.info(" nature ::" + nature2);
		 * _log.info(" shareholding ::" + shareholding2); _log.info(" dateOfinterest ::"
		 * + dateOfinterest2);
		 * 
		 * _log.info("::::::::::   3    :::::::::::::::::::::: " );
		 * _log.info(" companies ::" + companies3); _log.info(" nature ::" + nature3);
		 * _log.info(" shareholding ::" + shareholding3); _log.info(" dateOfinterest ::"
		 * + dateOfinterest3);
		 */
		try {
			intrstOfDirectorsOfPfLocalService.addIntrstOfDirectorsOfPf(directorName, submissionDate, fileEntryId, new Date(), themeDisplay.getUserId(), reportUploadLogId);
		} catch (Exception e) {
			 _log.error(e);
		}
		
		//Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		//Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
		Long createdBy = themeDisplay.getUserId();
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error("Exception on getting servicecontext : "+e.getMessage(),e);
		}
		
		long pdf_file_fileEntryId = 0l;
		try {
			_log.info("pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
			pdf_file_fileEntryId = DocumentUtil.addDocuments(resourceRequest, "Manpower_Form_2_File", "pdf");
			_log.info("Manpower_Form_2_File -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		
		try {
			//List<ReportUploadLog> reportUploadLog = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndUploadedAndIntermediaryid(reportMasterId, 0, 0);
			long intermediaryid = commonRoleService.getUserIntermediaryId(2, createdBy, themeDisplay.getCompanyId());
			String intermediaryname = IntermediaryListLocalServiceUtil.fetchIntermediaryList(intermediaryid).getIntermediaryname();
			ReportUploadLog reportUploadLog= ReportUploadLogLocalServiceUtil.addReportUploadLogWithIntermediary(133, new Date(), new Date(), createdBy, 
					fileEntryId, false, intermediaryname, intermediaryid);
					//reportUploadLogLocalService.addReportUploadLog(133, reportDate, new Date(), createdBy, 4, false);
			
			Manpowerform_ii manpowerform_ii =manpowerform_iiLocalService.addManpowerform_ii(reportUploadLog.getReportUploadLogId(), new Date(), createdBy);
			//Manpowerform_ii manpowerform_ii = Manpowerform_iiLocalServiceUtil.createManpowerform_ii(reportUploadLog.getReportUploadLogId());
			//manpowerform_ii.setCreatedby(createdBy);
			//manpowerform_ii.setCreatedon(new Date());
			_log.info("statusByUserName"+statusByUserName);
			Manpowerform_iiLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, 
					reportUploadLog.getReportUploadLogId(), WorkflowConstants.STATUS_PENDING, createdBy, statusByUserName, new Date(), serviceContext, "", true);
			/*(new Date(), createdBy,
					fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING,
					createdBy, statusByUserName, new Date(), serviceContext, "", true, new Date());*/
		} catch (Exception e) {
			
		}
	
	}
	
	@Reference
	IntrstOfDirecInOtherCompLocalService intrstOfDirecInOtherCompLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	Manpowerform_iiLocalService manpowerform_iiLocalService;
	
	
	@Reference
	IntrstOfDirectorsOfPfLocalService intrstOfDirectorsOfPfLocalService;
	
	@Reference
	private CommonRoleService commonRoleService;
}
