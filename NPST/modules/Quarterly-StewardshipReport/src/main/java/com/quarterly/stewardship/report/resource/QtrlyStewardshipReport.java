package com.quarterly.stewardship.report.resource;


import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
import com.daily.average.service.service.QtrStewardshipReportLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.quarterly.stewardship.report.constants.QuarterlyStewardshipReportPortletKeys;
import com.quarterly.stewardship.report.util.QuartelyStewardshipReportPdfUtil;
import com.quarterly.stewardship.report.util.StewardshipRepDocumentUtil;
import com.quarterly.stewardship.report.util.UploadDocumentUtil;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;







@Component(property = { 
		"javax.portlet.name=" + QuarterlyStewardshipReportPortletKeys.QUARTERLYSTEWARDSHIPREPORT,
		"mvc.command.name=" + QuarterlyStewardshipReportPortletKeys.qrStewardshipReport, 
		}, 
service = MVCResourceCommand.class)


public class QtrlyStewardshipReport extends BaseMVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(QtrlyStewardshipReport.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("Serve Resource method");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isError = false;
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		String companies = ParamUtil.getString(resourceRequest, "companies");
		String strDate = ParamUtil.getString(resourceRequest, "date_1");
		String conflict = ParamUtil.getString(resourceRequest, "conflict");
		String monitoring = ParamUtil.getString(resourceRequest, "monitoring");
		String resolutions = ParamUtil.getString(resourceRequest, "resolutions");
		String date_2 = ParamUtil.getString(resourceRequest, "date_2");
		String employeeName = ParamUtil.getString(resourceRequest, "employeeName");
		String place = ParamUtil.getString(resourceRequest, "place");
		String roles = ParamUtil.getString(resourceRequest, "roles");
		
		String conflictRem1 = ParamUtil.getString(resourceRequest,"conflict_rem1");
		String monitoringRem1 = ParamUtil.getString(resourceRequest,"monitoring_rem1");
		String resolutionsRem1 = ParamUtil.getString(resourceRequest, "resolutions_rem1");
		
		//String annexureARem = ParamUtil.getString(resourceRequest, "annexureA_rem");
		String annexureBOneRem = ParamUtil.getString(resourceRequest, "annexureB_I_rem");
		String annexureBTwoRem = ParamUtil.getString(resourceRequest, "annexureB_II_rem");
		String annexureCRem = ParamUtil.getString(resourceRequest, "annexureC_rem");
		
		String resolutionsVoted1 = ParamUtil.getString(resourceRequest, "resolutionsVoted1");
		String adversealert = ParamUtil.getString(resourceRequest, "adversealert");
		String insInvestorSituation = ParamUtil.getString(resourceRequest, "insInvestorSituation");
		
		String resolutionsVotedRem1 = ParamUtil.getString(resourceRequest,"resolutionsVoted_rem1");
		String adversealertRem1 = ParamUtil.getString(resourceRequest,"adversealert_rem1");
		String insInvestorSituationRem1 = ParamUtil.getString(resourceRequest, "insInvestorSituation_rem1");
		
		
		
		
		_log.info(conflictRem1+"conflict_rem1---"+monitoringRem1+"monitoring_rem1---"+resolutionsRem1+"resolutions_rem1---");
		_log.info("---<< annexureARem--->>>>>"+annexureBOneRem+"annexureBOneRem--->>>>>>>"+annexureBTwoRem+"annexureBTwoRem---"+annexureCRem+"annexureCRem >>>>>>>>>>");
		
		//long annexure_a = StewardshipRepDocumentUtil.addDocuments(resourceRequest, "annexureA");
		long annexure_a = 0;
		//long annexure_b_i = 0;
		long annexure_b_ii =0; 
		long annexure_c = 0;
		 
		long annexure_b_i = StewardshipRepDocumentUtil.addDocuments(resourceRequest, "annexureB_I",StringPool.BLANK);
		//long annexure_b_ii = StewardshipRepDocumentUtil.addDocuments(resourceRequest, "annexureB_II");
		//long annexure_c = StewardshipRepDocumentUtil.addDocuments(resourceRequest, "annexureC");
		
		DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
		long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		long createdBy = themeDisplay.getUserId();
		
		String dDate[]=dateFormat.format(reportDate).split("-");

		int month=Integer.parseInt(dDate[1]);
		int year=Integer.parseInt(dDate[0]);
		if(month==1){
			year=year-1;
			month=12;
		}else{
			month=month-1;	
		}
		String formDate1=month+"/"+year;
		
		long pdf_file_fileEntryId = 0l;
		
	
			
		
		//try {
			QtrStewardshipReport qrstewardship = QtrStewardshipReportLocalServiceUtil.fetchQtrStewardshipReport(reportUploadLogId);
			
			File file=QuartelyStewardshipReportPdfUtil.QuartelyStewardshipReportPdf(reportUploadLogId,dateFormat.format(reportDate), formDate1,conflict,conflictRem1,
					monitoring,monitoringRem1,resolutions,resolutionsRem1,insInvestorSituation,insInvestorSituationRem1,adversealert,adversealertRem1,resolutionsVoted1,
					resolutionsVotedRem1,companies, date_2, place, employeeName, roles);
			
			try {
				pdf_file_fileEntryId = UploadDocumentUtil.uploadFile(file, themeDisplay, resourceRequest);
			//	_log.info("compliance_certificate_pdf_file -------------  pdf_file_fileEntryId  :::: "+fileEntryId);
			} catch (Exception e) {
				isError = true;
				_log.error(e.getMessage(), e);
			}
			
			try {
			 pdf_file_fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
			
				if(pdf_file_fileEntryId>0) {			
				_log.info(qrstewardship);
				boolean reupload = false;
				if(Validator.isNotNull(qrstewardship)) {
					reupload = true;
				}
				
			
				QtrStewardshipReportLocalServiceUtil.addQtrStewardshipReport(reportUploadLogId, strDate, conflict, monitoring, resolutions,conflictRem1,monitoringRem1,
					resolutionsRem1,resolutionsVoted1, adversealert, insInvestorSituation,resolutionsVotedRem1,adversealertRem1,insInvestorSituationRem1,date_2, employeeName, 
					place, roles, annexure_a, annexure_b_i, annexure_b_ii, annexure_c, "",annexureBOneRem,annexureBTwoRem,annexureCRem,
					new Date(), createdBy);
			
			if(qrstewardship != null) {
				
				QtrStewardshipReportLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, 
						pdf_file_fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
						createdBy, statusByUserName, 
						new Date(), serviceContext, "",false);
			}else {
				QtrStewardshipReportLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, 
						pdf_file_fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
						createdBy, statusByUserName, 
						new Date(), serviceContext, "",true);	
			}
		}
				}
				catch (Exception e) {
			isError = true;
			_log.error("Exception in Persisting the table data one  : "+e);
		}
	/*	}
		
		catch (Exception e) {
			_log.error(e);
		}	
		*/
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
	
	@SuppressWarnings("deprecation")
/*	public long pdfUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext2) {
		Date date = new Date();
		File file = new File(filepath);
		String mimeType = "application/pdf";
		String title = "Quarterly Stewardship Report" +date.getTime()+ ".pdf";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {
			Folder folder = getFolder(themeDisplay);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), title, mimeType, title, description, "",
					file, serviceContext);
			return entry.getFileEntryId();
			//FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			//downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			_log.info(e.getMessage());
			 _log.error(e);
		}
		return 0l;
	}
	*/
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Quarterly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
    
	
	private long uploadFile(File file,ThemeDisplay themeDisplay,ResourceRequest resourceRequest){
		Date date=new Date();
	
		String fileName = date.getTime()+"_"+"Quarterly_Stewardship_Report"+".pdf";
		String title = fileName; 
		String description = "Quarterly Stewardship Report PDF";
		

		String mimeType =  MimeTypesUtil.getContentType(file);

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = StewardshipRepDocumentUtil.getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();
		}catch (Exception e) {
			_log.error("error while uploading file:  "+e.getMessage());
		}
		return repositoryId;
			
	
	}
	
	
	@Reference
	QtrStewardshipReportLocalService qtrStewardshipReportLocalService;
	
}
