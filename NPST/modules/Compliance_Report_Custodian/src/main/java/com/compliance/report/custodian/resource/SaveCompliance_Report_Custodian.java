package com.compliance.report.custodian.resource;

import com.compliance.report.custodian.constants.compliance_report_custodianPortletKeys;
import com.compliance.report.custodian.util.ComplianceReportCustodianPdfUtil;
import com.compliance.report.custodian.util.CustodianUtil;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.service.CustodianCompFormLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;





@Component(property = { "javax.portlet.name=" + compliance_report_custodianPortletKeys.COMPLIANCE_REPORT_CUSTODIAN,
		"mvc.command.name=/compformcustodian/save" }, service = MVCResourceCommand.class)

public class SaveCompliance_Report_Custodian implements MVCResourceCommand {
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		boolean isError = false;
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long pdf_file_fileEntryId = 0l;
	/*	try {
			pdf_file_fileEntryId = Compliance_report_util.addDocuments(resourceRequest, "Quarterly_compliance_certificate_pdf_file", "pdf");
			_log.info("Quarterly_compliance_certificate_pdf_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
		} catch (Exception e) {
			isError = true;
			_log.error(e.getMessage(), e);
		}
*/
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		_log.info("reportUploadLogId::"+reportUploadLogId);
		Date formDate = ParamUtil.getDate(resourceRequest, "formDate", dateFormat);
		String remarks_i_i = ParamUtil.getString(resourceRequest, "remarks_i_i");
		//String observe_i_i = ParamUtil.getString(resourceRequest, "observe_i_i");
		String remarks_i_ii = ParamUtil.getString(resourceRequest, "remarks_i_ii");
		//String observe_i_ii = ParamUtil.getString(resourceRequest, "observe_i_ii");
		String remarks_ii = ParamUtil.getString(resourceRequest, "remarks_ii");
		//String observe_ii = ParamUtil.getString(resourceRequest, "observe_ii");
		String remarks_iii = ParamUtil.getString(resourceRequest, "remarks_iii");
		//String observe_iii = ParamUtil.getString(resourceRequest, "observe_iii");
		String remarks_iv = ParamUtil.getString(resourceRequest, "remarks_iv");
		//String observe_iv = ParamUtil.getString(resourceRequest, "observe_iv");
		String remarks_v = ParamUtil.getString(resourceRequest, "remarks_v");
		//String observe_v = ParamUtil.getString(resourceRequest, "observe_v");
		String remarks_vi = ParamUtil.getString(resourceRequest, "remarks_vi");
		//String observe_vi = ParamUtil.getString(resourceRequest, "observe_vi");
		String remarks_vii = ParamUtil.getString(resourceRequest, "remarks_vii");
		//String observe_vii = ParamUtil.getString(resourceRequest, "observe_vii");
		String remarks_viii = ParamUtil.getString(resourceRequest, "remarks_viii");
		//String observe_viii = ParamUtil.getString(resourceRequest, "observe_viii");
		String remarks_ix = ParamUtil.getString(resourceRequest, "remarks_ix");
		//String observe_ix = ParamUtil.getString(resourceRequest, "observe_ix");
		String remarks_x = ParamUtil.getString(resourceRequest, "remarks_x");
		//String observe_x = ParamUtil.getString(resourceRequest, "observe_x");
		String remarks_xi = ParamUtil.getString(resourceRequest, "remarks_xi");
		//String observe_xi = ParamUtil.getString(resourceRequest, "observe_xi");
		String remarks_xii = ParamUtil.getString(resourceRequest, "remarks_xii");
		//String observe_xii = ParamUtil.getString(resourceRequest, "observe_xii");
		String remarks_xiii = ParamUtil.getString(resourceRequest, "remarks_xiii");
		//String observe_xiii = ParamUtil.getString(resourceRequest, "observe_xiii");
		String month = ParamUtil.getString(resourceRequest, "month");
		String signature = ParamUtil.getString(resourceRequest, "signature");
		String employeeName = ParamUtil.getString(resourceRequest, "employeeName");
		String designation = ParamUtil.getString(resourceRequest, "designation");
		String date_3 = ParamUtil.getString(resourceRequest, "date_3");
		String place = ParamUtil.getString(resourceRequest, "place");
		long createdBy = themeDisplay.getUserId();
		Date reportDate =ParamUtil.getDate(resourceRequest,"reportDate",dateFormat);
		long qcfile_id = Compliance_report_util.addDocuments(resourceRequest, "qcfile", StringPool.BLANK);
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {

		}
		
		
		String dDate[]=dateFormat.format(reportDate).split("-");

		int month1=Integer.parseInt(dDate[1]);
		int year=Integer.parseInt(dDate[0]);
		if(month1==1){
			year=year-1;
			month1=12;
		}else{
			month1=month1-1;	
		}
		String formDate1=month1+"/"+year;
		
		_log.info("reportdate"+reportDate);
		File file=ComplianceReportCustodianPdfUtil.ComplianceReportCustodianPDF(reportUploadLogId,dateFormat.format(reportDate), formDate1,remarks_i_i,remarks_i_ii,remarks_ii,
				remarks_iii,remarks_iv,remarks_v,remarks_vi,remarks_vii,remarks_viii,remarks_ix,remarks_x,remarks_xi,remarks_xii,remarks_xiii,month,signature,employeeName,
				designation,date_3,place);
	
		
		
		try {
			 pdf_file_fileEntryId=uploadFile(file, themeDisplay, resourceRequest);
			 CustodianCompForm custodianCompForm = CustodianCompFormLocalServiceUtil.fetchCustodianCompForm(reportUploadLogId);
			
			_log.info("annual_compliance_report_pdf_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
			
			if(pdf_file_fileEntryId>0) {			
			_log.info(custodianCompForm);
			boolean reupload = false;
			if(Validator.isNotNull(custodianCompForm)) {
				reupload = true;
			}	
		
	/*	try {
			CustodianCompForm custodianCompForm = CustodianCompFormLocalServiceUtil.fetchCustodianCompForm(reportUploadLogId);
			
			_log.info(custodianCompForm);
			boolean reupload = false;
			if(Validator.isNotNull(custodianCompForm)) {
				_log.info("is not null");
				reupload = true;
			}
		*/	
			CustodianCompFormLocalServiceUtil.addCustodianCompForm(formDate,remarks_i_i, remarks_i_ii, remarks_ii, remarks_iii, 
					remarks_iv, remarks_v, remarks_vi, remarks_vii, remarks_viii, remarks_ix, remarks_x, remarks_xi, 
					remarks_xii, remarks_xiii, month, signature, employeeName, designation, date_3, place, reportUploadLogId, qcfile_id);
			
			if(custodianCompForm != null && reupload) {
				_log.info("custodianCompForm if "+custodianCompForm);
				CustodianCompFormLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, reportUploadLogId,
						WorkflowConstants.STATUS_PENDING, createdBy, statusByUserName, new Date(), serviceContext, "", false);
			}else {
				_log.info("custodianCompForm else "+custodianCompForm);
				CustodianCompFormLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, reportUploadLogId,
						WorkflowConstants.STATUS_PENDING, createdBy, statusByUserName, new Date(), serviceContext, "", true);
			}
			}} catch (Exception e) {
			isError = true;
			_log.error(e.getMessage(),e);
		}
		
		/*CustodianCompFormLocalServiceUtil.addCustodianCompForm(formDate,remarks_i_i, remarks_i_ii, remarks_ii, remarks_iii,
				remarks_iv, remarks_v, remarks_vi, remarks_vii, remarks_viii, remarks_ix, remarks_x, remarks_xi,
				remarks_xii, remarks_xiii, month, signature, employeeName, designation, date_3, place,
				reportUploadLogId, qcfile_id);*/

		/*
		 * (remarks_i_i, remarks_i_ii, remarks_ii, remarks_iii, remarks_iv, remarks_v,
		 * remarks_vi, remarks_vii, remarks_viii, remarks_ix, remarks_x, remarks_xi,
		 * remarks_xii, remarks_xiii, month, signature, employeeName, designation,
		 * date_3, place, reportUploadLogId, qcfile_id);
		 */

		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				writer.print("success");
			}else {
				writer.print("error");
			}

		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}
		private long uploadFile(File file,ThemeDisplay themeDisplay,ResourceRequest resourceRequest){
			Date date=new Date();
		
			String fileName = date.getTime()+"_"+"Compliance_report_custodian"+".pdf";
			String title = fileName; 
			String description = "Compliance report custodian PDF";
			

			String mimeType =  MimeTypesUtil.getContentType(file);

			long repositoryId = themeDisplay.getScopeGroupId();
		

			try {

				Folder folder = CustodianUtil.getFolder(themeDisplay);

				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),resourceRequest);

				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
						"", file, serviceContext);
				return fileEntry.getFileEntryId();
			}catch (Exception e) {
				_log.error("error while uploading file:  "+e.getMessage());
			}
			return repositoryId;
				

			
		}
		
	Log _log = LogFactoryUtil.getLog(SaveCompliance_Report_Custodian.class);
}
