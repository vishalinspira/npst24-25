<%@page import="Cut_Annual_Audit_Report.Util.PrepopulateScrutinyData"%>
<%@page import="com.daily.average.service.service.CustAnnualReportscrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.CustAnnualReportscrutiny"%>
<%@page import="Cut_Annual_Audit_Report.Util.Cut_Annual_Audit_ReportDocumentUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.daily.average.service.service.CustAnnualAuditReportLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.CustAnnualAuditReport"%>
<%@ include file="/init.jsp" %>

<% 
	PrepopulateScrutinyData sd = new PrepopulateScrutinyData();
	sd.prePopulateScrutinyData(request);
	
	CustAnnualReportscrutiny custAnnualReportscrutiny = Validator.isNotNull(request.getAttribute("custAnnualScrutiny")) ? (CustAnnualReportscrutiny) request.getAttribute("custAnnualScrutiny") : CustAnnualReportscrutinyLocalServiceUtil.createCustAnnualReportscrutiny(0);
	
	System.out.println("JSP custAnnualReportscrutiny ------------------- "+ custAnnualReportscrutiny.toString());
	
	JSONObject json_two = JSONFactoryUtil.createJSONObject();
	JSONObject json_one = JSONFactoryUtil.createJSONObject();
	try {
		if(custAnnualReportscrutiny.getCust_report_remarks()!=null && !custAnnualReportscrutiny.getCust_report_remarks().trim().equals("")) {
			json_one = JSONFactoryUtil.createJSONObject(custAnnualReportscrutiny.getCust_report_remarks());
			
		}else{
			json_one = JSONFactoryUtil.createJSONObject(Cut_Annual_Audit_ReportDocumentUtil.emptyJsonScrutinyRemarksdata);
		}
		
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Regulatory parameters")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Operational parameters")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Timely Reporting")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Custodian Billing")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Custodian")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Infrastructure")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Protection")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Record maintenance")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Grievance Redressal")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Other parameters")));
		json_two = Cut_Annual_Audit_ReportDocumentUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Governance Structure")));
		
	} catch(Exception e) {
	}
	
%>

<div class="modal fade" id="success_tic" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center">
      	<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>      
        Data sent for Review.
      </div>       
    </div>
  </div>
</div>
<div class="modal fade" id="errorExcel" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center text-danger">
      	<i class="fas fa-times-circle  fa-4x d-block mb-4"></i>      
        <span id="output"></span>
      </div>       
    </div>
  </div>
</div>
<div class="nps-page-main nps-upload-report nps-statement-wrapper">
   <div class="row mb-3">
      <div class="col-12">
         <div class="text-right">
            <p  class="back_btn"><a class="backbtn" href="/web/guest/upload-custodian-maker"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
         </div>
      </div>
   </div>
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Annual Audit Report</h4>
            <form class="form" id="annualComplains" action="#" method="post">
               <input type="hidden" value="${reportUploadLogId }" name="reportUploadLogId" class="reportUploadLogId" />
               <input type="hidden" value="${reportMasterId }" name="reportMasterId" class="reportMasterId"/>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" name="reportDate" value="${reportDate }" readonly="readonly">
                     </div>
                  </div>
               </div>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annual Audit Report (PDF Upload)</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile1">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annualreport" name="annualreport"  accept=".pdf"/>
                           </div>
                           <label id="error1" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                   <div class="col-lg-6 col-md-6 col-sm-12 col-12 mt-5">
                       <div class="form-check-inline w-100">
	                     <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_2" disabled><%=custAnnualReportscrutiny.getAudit_pdf_file_remarks() == null ? "" : custAnnualReportscrutiny.getAudit_pdf_file_remarks() %></textarea>
	                  </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="col-md-9">
                        <div>
                           <p class="font-weight-bold mb-0">To,</p>
                           <p class="font-weight-bold mb-0">The Chief Executive Officer</p>
                           <p class="font-weight-bold">NPS Trust</p>
                           <p class="font-weight-bold mb-0">First Floor, ICADR Building,</p>
                           <p class="font-weight-bold">Plot No.6, Vasant Kunj Institutional Area, Phase- II</p>
                           <p class="font-weight-bold">New Delhi 110070</p>
                        </div>
                     </div>
                  </div>
               </div>
               <br>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p><b>Sr.no.</b></p>
                  </div>
                  <div class="col-md-3">
                     <p><b>Particulars</b></p>
                  </div>
                  <div class="col-md-2">
                     <p><b>Comments of auditor (whether Custodian has complied with?)</b></p>
                  </div>
                  <div class="col-md-2">
                     <p><b>Remarks of auditor (In case non-compliance observed)</b></p>
                  </div>
                  <div class="col-md-2">
                     <p><b>Management Observations</b></p>
                  </div>
                  <div class="col-md-2">
                     <p><b>NPS Trust Observations</b></p>
                  </div>
               </div>
               <!-- A Regulatory parameters start-->
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p><b>A.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Regulatory parameters</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>General obligations of Custodian of Securities</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>The Custodian is ensuring that it is managed in accordance with the provisions of the PFRDA Act,
                        Trust Deed and all relevant regulations, guidelines and directions issued by the Authority and NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya1" id="comments_regulatorya" name="comments_regulatorya_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya1" id="comments_regulatorya1" name="comments_regulatorya_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya_1 form-control" id="remarks_regulatorya_1" name="remarks_regulatorya_1">
                     <label id="remarks_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya_1 form-control" id="management_regulatorya_1" name="management_regulatorya_1">
                     <label id="management_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea  class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_1" disabled><%=json_two.get("regulatory_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>The Custodian has entered into all such agreements, as specified in the PFRDA Regulations and guidelines issued from time to time, 
                        with either NPS Trust or any other intermediary of NPS.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya2" id="comments_regulatorya" name="comments_regulatorya_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya2" id="comments_regulatorya2" name="comments_regulatorya_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya_2 form-control" id="remarks_regulatorya2" name="remarks_regulatorya_2">
                     <label id="remarks_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya_2 form-control" id="management_regulatorya2" name="management_regulatorya_2">
                     <label id="management_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea  class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_2" disabled><%=json_two.get("regulatory_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3</p>
                  </div>
                  <div class="col-md-3">
                     <p>Custodian has taken reasonable care, prudence, professional skill and diligence while undertaking Custodial activities. 
                        Any deviation has been timely reported to NPS Trust/ PFRDA.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya3" id="comments_regulatorya" name="comments_regulatorya_3" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya3" id="comments_regulatorya3" name="comments_regulatorya_3" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya_3 form-control" id="remarks_regulatorya3" name="remarks_regulatorya_3">
                     <label id="remarks_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya_3 form-control" id="management_regulatorya3" name="management_regulatorya_3">
                     <label id="management_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea  class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_3" disabled><%=json_two.get("regulatory_rem_3") %></textarea>
                  </div>
               </div>
               <br>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>4</p>
                  </div>
                  <div class="col-md-3">
                     <p>Custodian is undertaking activities as Domestic Depository in terms of Depositories Act, 1996 or as permitted by SEBI
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya4" id="comments_regulatorya" name="comments_regulatorya_4" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya4" id="comments_regulatorya4" name="comments_regulatorya_4" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya4 form-control" id="remarks_regulatorya4" name="remarks_regulatorya_4">
                     <label id="remarks_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya4 form-control" id="management_regulatorya4" name="management_regulatorya_4">
                     <label id="management_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea  class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_4" disabled><%=json_two.get("regulatory_rem_4") %></textarea>
                  </div>
               </div>
               <br>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>5</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the Custodian is maintaining records of the decisions taken by its Board and sub-committees at its meetings and the minutes of the meetings?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya5" id="comments_regulatorya" name="comments_regulatorya_5" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya5" id="comments_regulatorya5" name="comments_regulatorya_5" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya5 form-control" id="remarks_regulatorya5" name="remarks_regulatorya_5">
                     <label id="remarks_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya5 form-control" id="management_regulatorya5" name="management_regulatorya_5">
                     <label id="management_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_5" disabled><%=json_two.get("regulatory_rem_5") %></textarea>
                  </div>
               </div>
               <br>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>6</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the Custodian has submitted on time the audited scheme financials, internal audit reports, inspection, compliance reports and any other reports, 
                        as specified by the Authority and / or NPS Trust, to the Authority / NPS Trust, as applicable?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya6" id="comments_regulatorya" name="comments_regulatorya_6" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya6" id="comments_regulatorya6" name="comments_regulatorya_6" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya6 form-control" id="remarks_regulatorya6" name="remarks_regulatorya_6">
                     <label id="remarks_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya6 form-control" id="management_regulatorya6" name="management_regulatorya_6">
                     <label id="management_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_6" disabled><%=json_two.get("regulatory_rem_6") %></textarea>
                  </div>
               </div>
               <br>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>7</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the observations and recommendations, if any, made by NPS Trust on the above referred reports were acted upon and / or responded to and on time?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya7" id="comments_regulatorya" name="comments_regulatorya_7" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya7" id="comments_regulatorya7" name="comments_regulatorya_7" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya7 form-control" id="remarks_regulatorya7" name="remarks_regulatorya_7">
                     <label id="remarks_7_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya7 form-control" id="management_regulatorya7" name="management_regulatorya_7">
                     <label id="management_7_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea  class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_7" disabled><%=json_two.get("regulatory_rem_7") %></textarea>
                  </div>
               </div>
               <br>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>8</p>
                  </div>
                  <div class="col-md-3">
                     <p>Does Custodian takes all necessary precautions to ensure that continuity of the record keeping is not lost or destroyed 
                        and that sufficient back up of records are available.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="comments_regulatorya8" id="comments_regulatorya8" name="comments_regulatorya_8" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="comments_regulatorya8" id="comments_regulatorya" name="comments_regulatorya_8" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya8 form-control" id="remarks_regulatorya8" name="remarks_regulatorya_8">
                     <label id="remarks_8_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya8 form-control" id="management_regulatorya8" name="management_regulatorya_8">
                     <label id="management_8_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_8" disabled><%=json_two.get("regulatory_rem_8") %></textarea>
                  </div>
               </div>
               <br>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>9</p>
                  </div>
                  <div class="col-md-3">
                     <p>Regulatory fee payment made on time at the rate as decided by the Authority..
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya9" id="comments_regulatorya" name="comments_regulatorya_9" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="regulatorya9" id="comments_regulatorya9" name="comments_regulatorya_9" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_regulatorya9 form-control" id="remarks_regulatorya9" name="remarks_regulatorya_9">
                     <label id="remarks_9_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_regulatorya_9 form-control" id="management_regulatorya9" name="management_regulatorya_9">
                     <label id="management_9_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_9" disabled><%=json_two.get("regulatory_rem_9") %></textarea>
                  </div>
               </div>
               <br>
               <!-- A Regulatory parameters end-->
               <!-- Operational parameters start -->       
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p><b>B.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Operational parameters</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>Operational Manual/Procedure has been approved by Board and subsequent amendments too.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb1" id="comments_operationalb" name="comments_operationalb_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb1" id="comments_operationalb_1" name="comments_operationalb_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_1 form-control" id="remarks_operationalb1" name="remarks_operationalb_1">
                     <label id="remarks_operationalb_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_1 form-control" id="management_operationalb1" name="management_operationalb_1">
                     <label id="management_operationalb_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_1" disabled><%=json_two.get("operational_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>What is the frequency of review of operational manual?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb2" id="comment_operationalb" name="comments_operationalb_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb2" id="management_operationalb2" name="comments_operationalb_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_2 form-control" id="remarks_operationalb2" name="remarks_operationalb_2">
                     <label id="remarks_operationalb_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_2 form-control" id="management_operationalb2" name="management_operationalb_2">
                     <label id="management_operationalb_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_2" disabled><%=json_two.get("operational_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3</p>
                  </div>
                  <div class="col-md-3">
                     <p>Compliance with procedure for security documents execution as laid down in Operational Manual.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb3" id="comments_operationalb" name="comments_operationalb_3" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb3" id="comments_operationalb3" name="comments_operationalb_3" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_3 form-control" id="remarks_operationalb3" name="remarks_operationalb_3">
                     <label id="remarks_operationalb_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_3 form-control" id="management_operationalb3" name="management_operationalb_3">
                     <label id="management_operationalb_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_3" disabled><%=json_two.get("operational_rem_3") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>4</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the Custodian acts upon communication in writing received from NPS Trust informing it of the deficiencies and reports to 
                        NPS Trust on the rectification of such deficiencies?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb4" id="comments_operationalb" name="comments_operationalb_4" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb4" id="comments_operationalb4" name="comments_operationalb_4" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_4 form-control" id="remarks_operationalb4" name="remarks_operationalb_4">
                     <label id="remarks_operationalb_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_4 form-control" id="management_operationalb4" name="management_operationalb_4">
                     <label id="management_operationalb_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_4" disabled><%=json_two.get("operational_rem_4") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>5</p>
                  </div>
                  <div class="col-md-3">
                     <p>The Custodian receives and hold all securities delivered to it in Safe Custody
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb5" id="comments_operationalb" name="comments_operationalb_5" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb5" id="comments_operationalb5" name="comments_operationalb_5" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_5 form-control" id="remarks_operationalb5" name="remarks_operationalb_5">
                     <label id="remarks_operationalb_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_5 form-control" id="management_operationalb5" name="management_operationalb_5">
                     <label id="management_operationalb_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_5" disabled><%=json_two.get("operational_rem_5") %></textarea>
                  </div>
               </div>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>and deal as may deem fit for the purposes of providing for the safekeeping thereof.
                     </p>
                  </div>
                  <div class="col-md-2">
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_6 form-control" id="remarks_operationalb_6" name="remarks_operationalb_6">
                     <label id="remarks_operationalb_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_6 form-control" id="management_operationalb_6" name="management_operationalb_6">
                     <label id="management_operationalb_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_6" disabled><%=json_two.get("operational_rem_6") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>6</p>
                  </div>
                  <div class="col-md-3">
                     <p>Neither any assets have been acquired out of subscribers funds nor any liability assumed which results in encumbrance of 
                        the property of the National Pension System Trust or that of the subscribers in any way.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb7" id="comments_operationalb6" name="comments_operationalb_7" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb7" id="comments_operationalb" name="comments_operationalb_7" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_7 form-control" id="remarks_operationalb_7" name="remarks_operationalb_7">
                     <label id="remarks_operationalb_7_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_7 form-control" id="management_operationalb_7" name="management_operationalb_7">
                     <label id="management_operationalb_7_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_7" disabled><%=json_two.get("operational_rem_7") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>7</p>
                  </div>
                  <div class="col-md-3">
                     <p>The Custodian have not assigned, transferred, hypothecated, pledged, lended, use or otherwise dispose of any assets and securities/ investible Fund, 
                        except pursuant to instruction from the Pension Fund/ NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb7" id="comments_operationalb7" name="comments_operationalb_8" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb7" id="operationalb7" name="comments_operationalb_8" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_8 form-control" id="remarks_operationalb7" name="remarks_operationalb_8">
                     <label id="remarks_operationalb_8_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_8 form-control" id="management_operationalb7" name="management_operationalb_8">
                     <label id="management_operationalb_8_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_8" disabled><%=json_two.get("operational_rem_8") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>8</p>
                  </div>
                  <div class="col-md-3">
                     <p>There is proper documented procedure for receipt of orders from Pension Funds at Custodians end?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb8" id="management_operationalb8" name="comments_operationalb_9" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb8" id="operationalb8" name="comments_operationalb_9" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_9 form-control" id="remarks_operationalb_9" name="remarks_operationalb_9">
                     <label id="remarks_operationalb_9_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_9 form-control" id="management_operationalb_9" name="management_operationalb_9">
                     <label id="management_operationalb_9_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_9" disabled><%=json_two.get("operational_rem_9") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>9</p>
                  </div>
                  <div class="col-md-3">
                     <p>How orders are matched with files received and confirmation of match orders provided? Is there a maker and checker concept?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb9" id="operationalb" name="comments_operationalb_10" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb9" id="operationalb9" name="comments_operationalb_10" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_10 form-control" id="remarks_operationalb_10" name="remarks_operationalb_10">
                     <label id="remarks_operationalb_10_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_10 form-control" id="management_operationalb_10" name="management_operationalb_10">
                     <label id="management_operationalb_10_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_10" disabled><%=json_two.get("operational_rem_10") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>10</p>
                  </div>
                  <div class="col-md-3">
                     <p>There is proper documented procedure for settling funds in the system?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb10" id="operationalb" name="comments_operationalb_11" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb10" id="operationalb10" name="comments_operationalb_11" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_11 form-control" id="remarks_operationalb_11" name="remarks_operationalb_11">
                     <label id="remarks_operationalb_11_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_11 form-control" id="management_operationalb_11" name="management_operationalb_11">
                     <label id="management_operationalb_11_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_11" disabled><%=json_two.get("operational_rem_11") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>11</p>
                  </div>
                  <div class="col-md-3">
                     <p>How does Custodian report confirmed and unconfirmed trades?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb11" id="operationalb" name="comments_operationalb_12" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb11" id="operationalb11" name="comments_operationalb_12" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_12 form-control" id="remarks_operationalb_12" name="remarks_operationalb_12">
                     <label id="remarks_operationalb_12_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_12 form-control" id="management_operationalb_12" name="management_operationalb_12">
                     <label id="management_operationalb_12_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_12" disabled><%=json_two.get("operational_rem_12") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>12</p>
                  </div>
                  <div class="col-md-3">
                     <p>How securities settlement takes place? Whether all the process as mentioned above is duly prescribed in the operational manual?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb12" id="operationalb" name="comments_operationalb_13" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb12" id="operationalb12" name="comments_operationalb_13" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_13 form-control" id="remarks_operationalb_13" name="remarks_operationalb_13">
                     <label id="remarks_operationalb_13_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_13 form-control" id="management_operationalb_13" name="management_operationalb_13">
                     <label id="management_operationalb_13_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_13" disabled><%=json_two.get("operational_rem_13") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>13</p>
                  </div>
                  <div class="col-md-3">
                     <p>How scheme-wise reconciliation of holdings for each Pension Funds happens? What is the frequency?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb13" id="operationalb" name="comments_operationalb_14" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb13" id="operationalb13" name="comments_operationalb_14" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_14 form-control" id="remarks_operationalb_14" name="remarks_operationalb_14">
                     <label id="remarks_operationalb_14_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_14 form-control" id="management_operationalb_14" name="management_operationalb_14">
                     <label id="management_operationalb_14_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_14" disabled><%=json_two.get("operational_rem_14") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>14</p>
                  </div>
                  <div class="col-md-3">
                     <p>How reconciliation between Pension scheme A/c and CGSL happens?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb14" id="operationalb" name="comments_operationalb_15" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb14" id="operationalb14" name="comments_operationalb_15" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_15 form-control" id="remarks_operationalb_15" name="remarks_operationalb_15">
                     <label id="remarks_operationalb_15_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_15 form-control" id="management_operationalb_15" name="management_operationalb_15">
                     <label id="management_operationalb_15_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_15" disabled><%=json_two.get("operational_rem_15") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>15</p>
                  </div>
                  <div class="col-md-3">
                     <p>Does Custodian ensure no sharing of password between officers?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb15" id="operationalb" name="comments_operationalb_16" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb15" id="operationalb15" name="comments_operationalb_16" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_16 form-control" id="remarks_operationalb_16" name="remarks_operationalb_16">
                     <label id="remarks_operationalb_16_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_16 form-control" id="management_operationalb_16" name="management_operationalb_16">
                     <label id="management_operationalb_16_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_16" disabled><%=json_two.get("operational_rem_16") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>16</p>
                  </div>
                  <div class="col-md-3">
                     <p>The custodian of securities ensures that the individual holdings of securities in the pension scheme accounts are reconciled with the depository holdings and Constituents' 
                        Subsidiary General Ledger (CSGL) account at the end of the day.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb16" id="conflict" name="comments_operationalb_17" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb16" id="conflict_2" name="comments_operationalb_17" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_17 form-control" id="remarks_operationalb_17" name="remarks_operationalb_17">
                     <label id="remarks_operationalb_17_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_17 form-control" id="management_operationalb_17" name="management_operationalb_17">
                     <label id="management_operationalb_17_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_17" disabled><%=json_two.get("operational_rem_17") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>17</p>
                  </div>
                  <div class="col-md-3">
                     <p>The custodian of securities maintains complete audit trail of the movement of securities in and out of the pension 
                        scheme accounts and is in position to provide the same whenever called for by the Authority or the National Pension System Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb17" id="operationalb" name="comments_operationalb_18" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb17" id="operationalb17" name="comments_operationalb_18" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_18 form-control" id="remarks_operationalb_18" name="remarks_operationalb_18">
                     <label id="remarks_operationalb_18_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_18 form-control" id="management_operationalb_18" name="management_operationalb_18">
                     <label id="management_operationalb_18_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_18" disabled><%=json_two.get("operational_rem_18") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>18</p>
                  </div>
                  <div class="col-md-3">
                     <p>The reports are generated with manual intervention or through system? 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb18" id="operationalb" name="comments_operationalb_19" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb18" id="operationalb18" name="comments_operationalb_19" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_19 form-control" id="remarks_operationalb_19" name="remarks_operationalb_19">
                     <label id="remarks_operationalb_19_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_19 form-control" id="management_operationalb_19" name="management_operationalb_19">
                     <label id="management_operationalb_19_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_19" disabled><%=json_two.get("operational_rem_19") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>19</p>
                  </div>
                  <div class="col-md-3">
                     <p>Neither the Custodian nor the Custodian's nominee has voted on any of the Securities
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb19" id="operationalb" name="comments_operationalb_20" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="operationalb19" id="operationalb19" name="comments_operationalb_20" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_20 form-control" id="remarks_operationalb_20" name="remarks_operationalb_20">
                     <label id="remarks_operationalb_20_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_20 form-control" id="management_operationalb_20" name="management_operationalb_20">
                     <label id="management_operationalb_20_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_20" disabled><%=json_two.get("operational_rem_20") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>held by the Custodian under its role as Custodian to NPS, except in accordance with Instructions of the Pension Funds/ NPS Trust.
                        The Custodian have promptly delivered all reports, notices, proxies or proxy soliciting material received by it with
                     </p>
                  </div>
                  <div class="col-md-2">
                     <p></p>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_21 form-control" id="remarks_operationalb_21" name="remarks_operationalb_21">
                     <label id="remarks_operationalb_21_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_21 form-control" id="management_operationalb_21" name="management_operationalb_21">
                     <label id="management_operationalb_21_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_21" disabled><%=json_two.get("operational_rem_21") %> </textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>respect to the Securities, relevant for the purpose of exercise of voting rights to the Pension Funds/ NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <p></p>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_operationalb_22 form-control" id="remarks_operationalb_22" name="remarks_operationalb_22">
                     <label id="remarks_operationalb_22_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_operationalb_22 form-control" id="management_operationalb_22" name="management_operationalb_22">
                     <label id="management_operationalb_22_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_22" disabled><%=json_two.get("operational_rem_22") %></textarea>
                  </div>
               </div>
               <br>
               <hr>
               <!-- C Timely Reporting  start -->             
               <div class="row">
                  <div class="col-md-1">
                     <p><b>C.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Timely Reporting</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether internal audit reports at regular intervals for audits conducted by auditors appointed by NPS Trust as well as by the Custodian itself and 
                        the deviations mentioned therein have been shared with the Board and reported to PFRDA and NPS Trust, as applicable?.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc1" id="timelyc" name="comments_timelyc_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc1" id="timelyc1" name="comments_timelyc_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_1 form-control" id="remarks_timelyc_1" name="remarks_timelyc_1">
                     <label id="remarks_timelyc_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_1 form-control" id="management_timelyc_1" name="management_timelyc_1">
                     <label id="management_timelyc_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_1" disabled><%=json_two.get("timely_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether compliance certificates at regular intervals have been submitted to NPS Trust?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc2" id="timelyc" name="comments_timelyc_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc2" id="timelyc2" name="comments_timelyc_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_2 form-control" id="remarks_timelyc_2" name="remarks_timelyc_2">
                     <label id="remarks_timelyc_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_2 form-control" id="mangement_timelyc_2" name="management_timelyc_2">
                     <label id="management_timelyc_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_2" disabled><%=json_two.get("timely_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether NPS Trusts observations on the reports of the auditor and compliance reports have been acted upon and / or responded to and on time?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc3" id="timelyc3" name="comments_timelyc_3" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc3" id="conflict_2" name="comments_timelyc_3" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_3 form-control" id="remarks_timelyc_3" name="remarks_timelyc_3">
                     <label id="remarks_timelyc_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_3 form-control" id="management_timelyc_3" name="management_timelyc_3">
                     <label id="management_timelyc_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_3" disabled><%=json_two.get("timely_rem_3") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>4</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether Custodian timely informs NPS Trust and PFs about the scripts moved out of Top 200 stocks issued by NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc4" id="timelyc" name="comments_timelyc_4" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc4" id="timelyc4" name="comments_timelyc_4" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_4 form-control" id="remarks_timelyc_4" name="remarks_timelyc_4">
                     <label id="remarks_timelyc_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_4 form-control" id="management_timelyc_4" name="management_timelyc_4">
                     <label id="management_timelyc_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_4" disabled><%=json_two.get("timely_rem_4") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>5</p>
                  </div>
                  <div class="col-md-3">
                     <p>Does Custodian submits holding confirmation with Pension Funds to NPS Trust within the stipulated timelines as prescribed by PFRDA 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc5" id="timelyc" name="comments_timelyc_5" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc5" id="timelyc5" name="comments_timelyc_5" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_5 form-control" id="remarks_timelyc_5" name="remarks_timelyc_5">
                     <label id="remarks_timelyc_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_5 form-control" id="management_timelyc_5" name="management_timelyc_5">
                     <label id="management_timelyc_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_5" disabled><%=json_two.get("timely_rem_5") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>6</p>
                  </div>
                  <div class="col-md-3">
                     <p>
                     </p>
                     Daily saleable Holding Report
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc6" id="timelyc" name="comments_timelyc_6" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc6" id="timelyc6" name="comments_timelyc_6" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_6 form-control" id="remarks_timelyc_6" name="remarks_timelyc_6">
                     <label id="remarks_timelyc_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_6 form-control" id="management_timelyc_6" name="management_timelyc_6">
                     <label id="management_timelyc_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_6" disabled><%=json_two.get("timely_rem_6") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>7</p>
                  </div>
                  <div class="col-md-3">
                     <p>Daily transaction statement (on next day)
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc7" id="timelyc" name="comments_timelyc_7" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc7" id="timelyc7" name="comments_timelyc_7" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_7 form-control" id="remarks_timelyc_7" name="remarks_timelyc_7">
                     <label id="remarks_timelyc_7_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_7 form-control" id="management_timelyc_7" name="management_timelyc_7">
                     <label id="management_timelyc_7_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_7" disabled><%=json_two.get("timely_rem_7") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>8</p>
                  </div>
                  <div class="col-md-3">
                     <p>Shortage report
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc8" id="timelyc" name="comments_timelyc_8" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc8" id="timelyc8" name="comments_timelyc_8" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_8 form-control" id="remarks_timelyc_8" name="remarks_timelyc_8">
                     <label id="remarks_timelyc_8_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_8 form-control" id="management_timelyc_8" name="management_timelyc_8">
                     <label id="management_timelyc_8_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_8" disabled><%=json_two.get("timely_rem_8") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>9</p>
                  </div>
                  <div class="col-md-3">
                     <p>Report of Corporate action
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc8" id="timelyc" name="comments_timelyc_9" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc8" id="timelyc8" name="comments_timelyc_9" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_9 form-control" id="remarks_timelyc_9" name="remarks_timelyc_9">
                     <label id="remarks_timelyc_9_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_9 form-control" id="management_timelyc_9" name="management_timelyc_9">
                     <label id="management_timelyc_9_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_9" disabled><%=json_two.get("timely_rem_9") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>10</p>
                  </div>
                  <div class="col-md-3">
                     <p>Corporate Action receipt reports 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc10" id="timelyc" name="comments_timelyc_10" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc10" id="timelyc10" name="comments_timelyc_10" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_10 form-control" id="remarks_timelyc_10" name="remarks_timelyc_10">
                     <label id="remarks_timelyc_10_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_10 form-control" id="management_timelyc_10" name="management_timelyc_10">
                     <label id="management_timelyc_10_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_10" disabled><%=json_two.get("timely_rem_10") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>11</p>
                  </div>
                  <div class="col-md-3">
                     <p>Put & Call Intimation
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc11" id="timelyc" name="comments_timelyc_11" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc11" id="timelyc11" name="comments_timelyc_11" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_11 form-control" id="remarks_timelyc_11" name="remarks_timelyc_11">
                     <label id="remarks_timelyc_11_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_11 form-control" id="management_timelyc_11" name="management_timelyc_11">
                     <label id="management_timelyc_11_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_11" disabled><%=json_two.get("timely_rem_11") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>12</p>
                  </div>
                  <div class="col-md-3">
                     <p>Outstanding Corporate Action
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc12" id="timelyc" name="comments_timelyc_12" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc12" id="timelyc12" name="comments_timelyc_12" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_12 form-control" id="remarks_timelyc_12" name="remarks_timelyc_12">
                     <label id="remarks_timelyc_12_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_12 form-control" id="management_timelyc_12" name="management_timelyc_12">
                     <label id="management_timelyc_12_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_12" disabled><%=json_two.get("timely_rem_12") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>13</p>
                  </div>
                  <div class="col-md-3">
                     <p>Ex-Date Report
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc13" id="timelyc" name="comments_timelyc_13" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="timelyc13" id="timelyc13" name="comments_timelyc_13" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_timelyc_13 form-control" id="remarks_timelyc_13" name="remarks_timelyc_13">
                     <label id="remarks_timelyc_13_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_timelyc_13 form-control" id="management_timelyc_13" name="management_timelyc_13">
                     <label id="management_timelyc_13_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_13" disabled><%=json_two.get("timely_rem_13") %></textarea>
                  </div>
               </div>
               <br>
               <!-- C  Timely Reporting end -->       
               <hr>
               <!-- d Custodian Billing start -->
               <div class="row">
                  <div class="col-md-1">
                     <p><b>D.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Custodian Billing </b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether Custodian is calculating and charging Asset Servicing Charges as per the guidelines and the same matches with data shared by PFs. 
                        The methods of computation and daily verification with PFs and SHCIL have been verified and deviations/suggestions, if any, were reported.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="billingd1" id="billingd" name="comments_billingd_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="billingd1" id="billingd1" name="comments_billingd_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_billingd_1 form-control" id="remarks_billingd_1" name="remarks_billingd_1">
                     <label id="remarks_billingd_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="mangement_billingd_1 form-control" id="mangement_billingd_1" name="management_billingd_1">
                     <label id="management_billingd_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="billingd_rem_1" disabled><%=json_two.get("billingd_rem_1") %></textarea>
                  </div>
               </div>
               <div class="row">
                  <div class="col-md-1">
                     <p>2.</p>
                  </div>
                  <div class="col-md-3">
                     <p>To check whether SEBI, NSDL, CCIL, CBRICS and other charges as claimed by the Custodian have been computed accurately..
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="billingd2" id="billingd" name="comments_billingd_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="billingd2" id="billingd2" name="comments_billingd_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_billingd_2 form-control" id="remarks_billingd_2" name="remarks_billingd_2">
                     <label id="remarks_billingd_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_billingd_2 form-control" id="management_billingd_2" name="management_billingd_2">
                     <label id="management_billingd_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="billingd_rem_2" disabled><%=json_two.get("billingd_rem_2") %></textarea>
                  </div>
               </div>
               <hr>
               <!-- d  Custodian Billing end-->             
               <!-- E  Custodian Administration start-->
               <div class="row">
                  <div class="col-md-1">
                     <p><b>E.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Custodian Administration </b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1.1</p>
                  </div>
                  <div class="col-md-3">
                     <p>List of dedicated total no. of resources supporting the Custodian systems and operations
                        (Managerial/ technical/ operations/ administrative etc) with name, qualifications and total experience and the activity performed by them.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane1" id="custodiane" name="comments_custodiane_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane1" id="custodiane1" name="comments_custodiane_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_custodiane_1 form-control" id="remarks_custodiane_1" name="remarks_custodiane_1">
                     <label id="remarks_custodiane_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_custodiane_1 form-control" id="management_custodiane_1" name="management_custodiane_1">
                     <label id="management_custodiane_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_1" disabled><%=json_two.get("custodiane_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2.2</p>
                  </div>
                  <div class="col-md-3">
                     <p>List of employees (with educational qualification and no. of years of experiences) engaged with Custodian handling NPS work.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane2" id="custodiane" name="comments_custodiane_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane2" id="custodiane1" name="comments_custodiane_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_custodiane_2 form-control" id="remarks_custodiane_2" name="remarks_custodiane_2">
                     <label id="remarks_custodiane_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_custodiane_2 form-control" id="management_custodiane_2" name="management_custodiane_2">
                     <label id="management_custodiane_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_2" disabled><%=json_two.get("custodiane_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3</p>
                  </div>
                  <div class="col-md-3">
                     <p>Does Custodian have employee dealing guidelines in place?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane3" id="custodiane" name="comments_custodiane_3" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane3" id="custodiane3" name="comments_custodiane_3" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_custodiane_3 form-control" id="remarks_custodiane_3" name="remarks_custodiane_3">
                     <label id="remarks_custodiane_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_custodiane_3 form-control" id="management_custodiane_3" name="management_custodiane_3">
                     <label id="management_custodiane_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_3" disabled><%=json_two.get("custodiane_rem_3") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>4.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Outsourcing of the activities and its agreements and approvals.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane4" id="custodiane" name="comments_custodiane_4" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane4" id="custodiane4" name="comments_custodiane_4" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="place form-control" id="remarks_custodiane_4" name="remarks_custodiane_4">
                     <label id="remarks_custodiane_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="place form-control" id="management_custodiane_4" name="management_custodiane_4">
                     <label id="management_custodiane_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_4" disabled><%=json_two.get("custodiane_rem_4") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>5.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Training of resources and its frequency.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane5" id="custodiane" name="comments_custodiane_5" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="custodiane5" id="custodiane5" name="comments_custodiane_5" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_custodiane_5 form-control" id="remarks_custodiane_5" name="remarks_custodiane_5">
                     <label id="remarks_custodiane_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_custodiane_5 form-control" id="management_custodiane_5" name="management_custodiane_5">
                     <label id="management_custodiane_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_5" disabled><%=json_two.get("custodiane_rem_5") %></textarea>
                  </div>
               </div>
               <br>
               <hr>
               <!-- E  Infrastructure end-->   
               <!-- F  Infrastructure start-->             
               <div class="row">
                  <div class="col-md-1">
                     <p><b>F.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Infrastructure</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>Details of infrastructure of Custodian (Prime site and DR site). 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref1" id="infrastructuref" name="comments_infrastructuref_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref1" id="infrastructuref1" name="comments_infrastructuref_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_1 form-control" id="remarks_infrastructuref_1" name="remarks_infrastructuref_1">
                     <label id="remarks_infrastructuref_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_1 form-control" id="management_infrastructuref_1" name="management_infrastructuref_1">
                     <label id="management_infrastructuref_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_1" disabled><%=json_two.get("infrastructuref_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the infrastructure of the Custodian is capable enough to render its role as Custodian effectively and efficiently?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref2" id="conflict_1" name="comments_infrastructuref_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref2" id="conflict_2" name="comments_infrastructuref_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_2 form-control" id="remarks_infrastructuref_2" name="remarks_infrastructuref_2">
                     <label id="remarks_infrastructuref_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_2 form-control" id="management_infrastructuref_2" name="management_infrastructuref_2">
                     <label id="management_infrastructuref_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_2" disabled><%=json_two.get("infrastructuref_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3</p>
                  </div>
                  <div class="col-md-3">
                     <p>When the last DR drill conducted and BCP was activated on both the above-mentioned sites.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref3" id="conflict_" name="comments_infrastructuref_3" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref3" id="conflict_2" name="comments_infrastructuref_3" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_3 form-control" id="remarks_infrastructuref_3" name="remarks_infrastructuref_3">
                     <label id="remarks_infrastructuref_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_3 form-control" id="management_infrastructuref_3" name="management_infrastructuref_3">
                     <label id="management_infrastructuref_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_3" disabled><%=json_two.get("infrastructuref_rem_3") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>4.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Custodian has provided foolproof electronic interconnectivity with the Pension Funds and other relevant parties.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref4" id="conflict_1" name="comments_infrastructuref_4" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref4" id="conflict_2" name="comments_infrastructuref_4" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_4 form-control" id="remarks_infrastructuref_4" name="remarks_infrastructuref_4">
                     <label id="remarks_infrastructuref_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_4 form-control" id="management_infrastructuref_4" name="management_infrastructuref_4">
                     <label id="management_infrastructuref_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_4" disabled><%=json_two.get("infrastructuref_rem_4") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>5.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the frequency of incremental data shipping to DR is real time?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref5" id="infrastructuref" name="comments_infrastructuref_5" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref5" id="infrastructuref5" name="comments_infrastructuref_5" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_5 form-control" id="remarks_infrastructuref_5" name="remarks_infrastructuref_5">
                     <label id="remarks_infrastructuref_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_5 form-control" id="management_infrastructuref_5" name="management_infrastructuref_5">
                     <label id="management_infrastructuref_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_5" disabled><%=json_two.get("infrastructuref_rem_5") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>6.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the frequency of syncing data at DR is real time?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref6" id="infrastructuref" name="comments_infrastructuref_6" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIa" id="infrastructuref6" name="comments_infrastructuref_6" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_6 form-control" id="remarks_infrastructuref_6" name="remarks_infrastructuref_6">
                     <label id="remarks_infrastructuref_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_6 form-control" id="management_infrastructuref_6" name="management_infrastructuref_6">
                     <label id="management_infrastructuref_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_6" disabled><%=json_two.get("infrastructuref_rem_6") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>7.</p>
                  </div>
                  <div class="col-md-3">
                     <p>How much time is required for activation of DR site ?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref7" id="infrastructuref" name="comments_infrastructuref_7" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref7" id="infrastructuref7" name="comments_infrastructuref_7" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_7 form-control" id="remarks_infrastructuref_7" name="remarks_infrastructuref_7">
                     <label id="remarks_infrastructuref_7_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_7 form-control" id="management_infrastructuref_7" name="management_infrastructuref_7">
                     <label id="management_infrastructuref_7_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_7" disabled><%=json_two.get("infrastructuref_rem_7") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>8.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the VPN facility is available with the concerned officials for primary and DR site?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref8" id="infrastructuref" name="comments_infrastructuref_8" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref8" id="infrastructuref8" name="comments_infrastructuref_8" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_8 form-control" id="remarks_infrastructuref_8" name="remarks_infrastructuref_8">
                     <label id="remarks_infrastructuref_8_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_8 form-control" id="management_infrastructuref_8" name="management_infrastructuref_8">
                     <label id="management_infrastructuref_8_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_8" disabled><%=json_two.get("infrastructuref_rem_8") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>9.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the transition between Primary and Secondary lease lines on real time basis?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref9" id="infrastructuref" name="comments_infrastructuref_9" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref9" id="infrastructuref9" name="comments_infrastructuref_9" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_9 form-control" id="remarks_infrastructuref_9" name="remarks_infrastructuref_9">
                     <label id="remarks_infrastructuref_9_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_9 form-control" id="management_infrastructuref_9" name="management_infrastructuref_9">
                     <label id="management_infrastructuref_9_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_9" disabled><%=json_two.get("infrastructuref_rem_9") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>10.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the Custodian has appointed full time CISOs and IT team to have proper check on cyber security related arrangements for both DR and BCP.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref10" id="infrastructuref1" name="comments_infrastructuref_10" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref10" id="infrastructuref10" name="comments_infrastructuref_10" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_10 form-control" id="remarks_infrastructuref_10" name="remarks_infrastructuref_10">
                     <label id="remarks_infrastructuref_10_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_10 form-control" id="management_infrastructuref_10" name="management_infrastructuref_10">
                     <label id="management_infrastructuref_10_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_10" disabled><%=json_two.get("infrastructuref_rem_10") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>11.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Does Custodian conduct IT audits annually or at less frequency and put up the same to the Risk and Audit Committee of the Board.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref11" id="infrastructure" name="comments_infrastructuref_11" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref11" id="infrastructuref11" name="comments_infrastructuref_11" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_11 form-control" id="remarks_infrastructuref_11" name="remarks_infrastructuref_11">
                     <label id="remarks_infrastructuref_11_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_2 form-control" id="management_infrastructuref_11" name="management_infrastructuref_11">
                     <label id="management_infrastructuref_11_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_11" disabled><%=json_two.get("infrastructuref_rem_11") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>12.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the Insurance policy taken by the Custodian is sufficient to fully cover the premises and data of NPS?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref12" id="infrastructuref1" name="comments_infrastructuref_12" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="infrastructuref12" id="infrastructuref12" name="comments_infrastructuref_12" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_infrastructuref_12 form-control" id="remarks_infrastructuref_12" name="remarks_infrastructuref_12">
                     <label id="remarks_infrastructuref_12_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_infrastructuref_12 form-control" id="management_infrastructuref_12" name="management_infrastructuref_12">
                     <label id="management_infrastructuref_12_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_12" disabled><%=json_two.get("infrastructuref_rem_12") %></textarea>
                  </div>
               </div>
               <br>
               <hr>
               <!-- F  Protection of Assets of  Beneficial Owners  end -->      
               <!-- G  Protection of Assets of  Beneficial Owners  start -->      
               <div class="row">
                  <div class="col-md-1">
                     <p><b>G.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Protection of Assets of Beneficial Owners </b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether segregated bank accounts and other records pertaining to activities under National Pension System are being maintained?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong1" id="protectiong" name="comments_protectiong_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong1" id="protectiong1" name="comments_protectiong_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_protectiong_1 form-control" id="remarks_protectiong_1" name="remarks_protectiong_1">
                     <label id="remarks_protectiong_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_protectiong_1 form-control" id="management_protectiong_1" name="management_protectiong_1">
                     <label id="management_protectiong_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_1" disabled><%=json_two.get("protectiong_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the Custodian is exercising all due diligence and vigilance in carrying out its duties and in protecting the rights and
                        interests of the subscribers and is complying with the terms and conditions of Service Level Agreements (SLA) & guidelines issued by PFRDA and NPS Trust?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong2" id="conflict_1" name="comments_protectiong_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong2" id="conflict_2" name="comments_protectiong_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_protectiong_2 form-control" id="remarks_protectiong_2" name="remarks_protectiong_2">
                     <label id="remarks_protectiong_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_protectiong_2 form-control" id="management_protectiong_2" name="management_protectiong_2">
                     <label id="management_protectiong_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_2" disabled><%=json_two.get("protectiong_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether Custodian is ensuring that any NPS Trust property under its custody / control is properly protected, held and administered by the proper persons?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong3" id="protectiong" name="comments_protectiong_3" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong3" id="protectiong3" name="comments_protectiong_3" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_protectiong_3 form-control" id="remarks_protectiong_3" name="remarks_protectiong_3">
                     <label id="remarks_protectiong_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_protectiong_3 form-control" id="management_protectiong_3" name="management_protectiong_3">
                     <label id="management_protectiong_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_3" disabled><%=json_two.get("protectiong_rem_3") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>4</p>
                  </div>
                  <div class="col-md-3">
                     <p>Where the Custodian is supervising the collection of any income due on assets held in the name of the National Pension System Trust, 
                        claiming any repayment of tax and holding any income received in trust for the beneficiaries,
                        the same are in accordance with the Trust Deed and the regulations, guidelines or directions issued by the Authority and NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong4" id="protectiong" name="comments_protectiong_4" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong4" id="protectiong4" name="comments_protectiong_4" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_protectiong_4 form-control" id="remarks_protectiong_4" name="remarks_protectiong_4">
                     <label id="remarks_protectiong_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_protectiong_4 form-control" id="management_protectiong_4" name="management_protectiong_4">
                     <label id="management_protectiong_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_4" disabled><%=json_two.get("protectiong_rem_4") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>5</p>
                  </div>
                  <div class="col-md-3">
                     <p>Where Custodian has custody or under its control any property of the Trust, whether it is being held in the interest of beneficiaries?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong5" id="protectiong" name="comments_protectiong_5" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong5" id="protectiong5" name="comments_protectiong_5" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_protectiong_5 form-control" id="remarks_protectiong_5" name="remarks_protectiong_5">
                     <label id="remarks_protectiong_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_protectiong_5 form-control" id="management_protectiong_5" name="management_protectiong_5">
                     <label id="management_protectiong_5_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_5" disabled><%=json_two.get("protectiong_rem_5") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>6</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether Custodian has adequate systems for internal controls to prevent any manipulation of records and documents including audits for securities and rights
                        or entitlements arising from the securities held under this agreement. 
                        The custodian of securities shall have appropriate safekeeping measures to ensure that such securities (assets or documents) are protected from theft or natural hazard.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong6" id="comments_protectiong_6" name="comments_protectiong_6" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="protectiong6" id="comments_protectiong_6" name="comments_protectiong_6" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_protectiong_6 form-control" id="remarks_protectiong_6" name="remarks_protectiong_6">
                     <label id="remarks_protectiong_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_protectiong_6 form-control" id="management_protectiong_6" name="management_protectiong_6">
                     <label id="management_protectiong_6_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_6" disabled><%=json_two.get("protectiong_rem_6") %></textarea>
                  </div>
               </div>
               <br>
               <hr>
               <!-- G  Protection of Assets of  Beneficial Owners end -->                
               <!-- H  Record maintenance and storage  start -->                  
               <div class="row">
                  <div class="col-md-1">
                     <p><b>H.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Record maintenance and storage</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the latest details and address of NPS Trust and its authorised officers are available with Custodian
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="recordh1" id="recordh" name="comments_recordh_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="recordh1" id="recordh1" name="comments_recordh_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_recordh_1 form-control" id="remarks_recordh_1" name="remarks_recordh_1">
                     <label id="remarks_recordh_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_recordh_1 form-control" id="management_recordh_1" name="management_recordh_1">
                     <label id="management_recordh_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="recordh_rem_1" disabled><%=json_two.get("recordh_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>Whether the latest details and address of PFs and its authorised officers are available with Custodian
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="recordh2" id="conflict_1" name="comments_recordh_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="recordh2" id="conflict_2" name="comments_recordh_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_recordh_2 form-control" id="remarks_recordh_2" name="remarks_recordh_2">
                     <label id="remarks_recordh_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_recordh_2 form-control" id="management_recordh_2" name="management_recordh_2">
                     <label id="management_recordh_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="recordh_rem_2" disabled><%=json_two.get("recordh_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3</p>
                  </div>
                  <div class="col-md-3">
                     <p>The custodian of securities create and maintain the records of securities held in its custody of
                        NPS Trust in such manner that the tracing of securities or obtaining duplicate of the documents is facilitated,
                        in the event of loss of original records for any reason.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="recordh3" id="comments_recordh_3" name="comments_recordh_3" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="recordh3" id="comments_recordh_3" name="comments_recordh_3" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_recordh_3 form-control" id="remarks_recordh_3" name="remarks_recordh_3">
                     <label id="remarks_recordh_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_recordh_3 form-control" id="management_recordh_3" name="management_recordh_3">
                     <label id="management_recordh_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="recordh_rem_3" disabled><%=json_two.get("recordh_rem_3") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>4</p>
                  </div>
                  <div class="col-md-3">
                     <p>Does Custodian have any record retention policy?
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="recordh4" id="recordh4" name="comments_recordh_4" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="recordh4" id="conflict_2" name="comments_recordh_4" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_recordh_4 form-control" id="remarks_recordh_4" name="remarks_recordh_4">
                     <label id="remarks_recordh_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_recordh_4 form-control" id="management_recordh_4" name="management_recordh_4">
                     <label id="management_recordh_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="recordh_rem_4" disabled><%=json_two.get("recordh_rem_4") %></textarea>
                  </div>
               </div>
               <br>
               <hr>
               <!-- H  Record maintenance and storage  end -->  
               <!-- I  Grievance Redressal  start -->   
               <div class="row">
                  <div class="col-md-1">
                     <p><b>I.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Grievance Redressal</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>All grievances raised/ registered against Custodian were resolved within TAT.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="grievancei1" id="grievancei1" name="commets_grievancei_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="grievancei1" id="conflict_2" name="commets_grievancei_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_grievancei_1 form-control" id="remarks_grievancei_1" name="remarks_grievancei_1">
                     <label id="remarks_grievancei_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_grievancei_1 form-control" id="management_grievancei_1" name="management_grievancei_1">
                     <label id="management_grievancei_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="grievancei_rem_1" disabled><%=json_two.get("grievancei_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>Assess quality of responses against grievances raised.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="grievancei2" id="grievance2" name="commets_grievancei_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="grievancei2" id="grievancei2" name="commets_grievancei_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_grievancei_2 form-control" id="remarks_grievancei_2" name="remarks_grievancei_2">
                     <label id="remarks_grievancei_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_grievancei_2 form-control" id="management_grievancei_2" name="management_grievancei_2">
                     <label id="management_grievancei_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="grievancei_rem_2" disabled><%=json_two.get("grievancei_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <hr>
               <!----I  Grievance Redressal  End -->   
               <!--  J  Other Parameters  start -->      
               <div class="row">
                  <div class="col-md-1">
                     <p><b>J.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Other Parameters</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>Custodian to produce necessary approval documents for the consents of appropriate authorities, licenses, 
                        permits and approvals as are necessary for carrying out its functions and obligations under those agreements.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherj1" id="otherj1" name="comments_otherj_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherj1" id="otherj1" name="comments_otherj_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_otherj_1 form-control" id="remarks_otherj_1" name="remarks_otherj_1">
                     <label id="remarks_otherj_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_otherj_1 form-control" id="management_otherj_1" name="management_otherj_1">
                     <label id="management_otherj_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="otherj_rem_1" disabled><%=json_two.get("otherj_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>Does Custodian have any written down operational policy of Exit Management Plan in place?.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherj2" id="conflict_1" name="comments_otherj_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherj2" id="conflict_2" name="comments_otherj_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_otherj_2 form-control" id="remarks_otherj_2" name="remarks_otherj_2">
                     <label id="remarks_otherj_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_otherj_2 form-control" id="management_otherj_2" name="management_otherj_2">
                     <label id="management_otherj_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="otherj_rem_2" disabled><%=json_two.get("otherj_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <hr>
               <!--  J  Other Parameters  End --> 
               <!--  k  Governance Structure of Custodian   start --> 
               <div class="row">
                  <div class="col-md-1">
                     <p><b>K.</b></p>
                  </div>
                  <div class="col-md-8">
                     <p><b>Governance Structure of Custodian</b></p>
                  </div>
               </div>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>1</p>
                  </div>
                  <div class="col-md-3">
                     <p>System of approval within the organization.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="governancek1" id="governance" name="comments_governancek_1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="governancek1" id="governancek" name="comments_governancek_1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_governancek_1 form-control" id="remarks_governancek_1" name="remarks_governancek_1">
                     <label id="remarks_governancek_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_governancek_1 form-control" id="management_governancek_1" name="management_governancek_1">
                     <label id="management_governancek_1_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="governancek_rem_1" disabled><%=json_two.get("governancek_rem_1") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2</p>
                  </div>
                  <div class="col-md-3">
                     <p>System of disposal of advisory received from PFRDA.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="governancek2" id="governancek2" name="comments_governancek_2" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="governancek2" id="conflict_2" name="comments_governancek_2" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_governancek_2 form-control" id="remarks_governancek_2" name="remarks_governancek_2">
                     <label id="remarks_governancek_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_governancek_2 form-control" id="management_governancek_2" name="management_governancek_2">
                     <label id="management_governancek_2_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="governancek_rem_2" disabled><%=json_two.get("governancek_rem_2") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Monitoring of advisory from PFRDA but pending at end of Custodian.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="governancek3" id="conflict_1" name="comments_governancek_3" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="governancek3" id="conflict_2" name="comments_governancek_3" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remakrs_governancek_3 form-control" id="remarks_governancek_3" name="remarks_governancek_3">
                     <label id="remarks_governancek_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_governancek_3 form-control" id="management_governancek_3" name="management_governancek_3">
                     <label id="management_governancek_3_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="governancek_rem_3" disabled><%=json_two.get("governancek_rem_3") %></textarea>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>4.</p>
                  </div>
                  <div class="col-md-3">
                     <p>Method of follow up of irregularities in workflow with outsourcing agencies or other linkages of Custodian and their existing arrangement.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="governancek4" id="governancek3" name="comments_governancek_4" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="governancek4" id="governancek3" name="comments_governancek_4" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="remarks_governancek_4 form-control" id="remarks_governancek_4" name="remarks_governancek_4" >
                     <label id="remarks_governancek_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <input type="text" class="management_governancek_4 form-control" id="management_governancek_4" name="management_governancek_4">
                     <label id="management_governancek_4_error" class="error-message text-danger"></label>
                  </div>
                  <div class="col-md-2">
                     <textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="governancek_rem_4" disabled><%=json_two.get("governancek_rem_4") %></textarea>
                  </div>
               </div>
               <hr>
               <!--  k  Governance Structure of Custodian   end -->
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Date: </label>
                        <input type="date" class="date_2" id="date_2" name="date_2">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Name: </label>
                        <input type="text" class="employeeName" id="employeeName" name="employeeName">
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Place: </label>
                        <input type="text" class="place1" id="place1" name="place1">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="roles">Role:</label><br>
                     <select class="w-100" name="roles" id="roles">
                        <option value="">Select</option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                     </select>
                     <label id="error-roles" class="error-message text-danger"></label>
                  </div>
               </div>
               <div class="row text-center" id="sub">
                  <div class="col-md-12">
                     <!-- <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit"> -->
                  	 <button class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit">Submit</button>
                  </div>
               </div>
            </form>
         </div>
      </div>
   </div>
</div>
<div class="animaion" style="display:none;">
   <div class="row">
      <div class="loading-animation"></div>
   </div>
</div>

<style>
	select {
    background-color: #E9F3FC;
    color: #111111;
    border-radius: 5px;
    padding: 5px 20px;
}

.modal {
    display: none;
}

textarea {
	border: 1px solid #0076c8; 
}

input, select {
    border: 1px solid #0076c8 !important;
    height: 40px !important;
}

</style>

<script type="text/javascript">

$(document).ready(function() {

	toastr.options = {
		  "debug": false,
		  "positionClass": "toast-bottom-right",
		  "onclick": null,
		  "fadeIn": 300,
		  "fadeOut": 1000,
		  //"timeOut": 6000,
		  "extendedTimeOut": 1000
		}
	
	
	 $('#annualreport').bind('change', function () {
		  var filename = $("#annualreport").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile1").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile1").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z\s]+$/i.test(value);
		}, "Please enter characters only"); 
	
	 $("#annualComplains").validate({
	  	rules: {
		    date_2: {
			    required: true
			},
			employeeName:{
				required: true,
				lettersonly: true
			},
			place1:{
				required: true,
				lettersonly: true
			},
			roles:{
				required: true
			},
			/* annualreport:{
				required: true
			}, */
			remarks_regulatorya_1:{
				required: true
			},
			management_regulatorya_1:{
				required: true
			},
			remarks_regulatorya_2:{
				required: true
			},
			management_regulatorya_2:{
				required: true
			},
			remarks_regulatorya_3:{
				required: true
			},
			management_regulatorya_3:{
				required: true
			},
			remarks_regulatorya_4:{
				required: true
			},
			management_regulatorya_4:{
				required: true
			},
			remarks_regulatorya_5:{
				required: true
			},
			management_regulatorya_5:{
				required: true
			},
			remarks_regulatorya_6:{
				required: true
			},
			management_regulatorya_6:{
				required: true
			},
			remarks_regulatorya_7:{
				required: true
			},
			management_regulatorya_7:{
				required: true
			},
			remarks_regulatorya_8:{
				required: true
			},
			management_regulatorya_8:{
				required: true
			},
			remarks_regulatorya_9:{
				required: true
			},
			management_regulatorya_9:{
				required: true
			},
			
			
			remarks_operationalb_1:{
				required: true
			},
			management_operationalb_1:{
				required: true
			},
			remarks_operationalb_2:{
				required: true
			},
			management_operationalb_2:{
				required: true
			},
			remarks_operationalb_3:{
				required: true
			},
			management_operationalb_3:{
				required: true
			},
			
			remarks_operationalb_4:{
				required: true
			},
			management_operationalb_4:{
				required: true
			},
			
			remarks_operationalb_5:{
				required: true
			},
			management_operationalb_5:{
				required: true
			},
			
			remarks_operationalb_6:{
				required: true
			},
			management_operationalb_6:{
				required: true
			},
			
			remarks_operationalb_7:{
				required: true
			},
			management_operationalb_7:{
				required: true
			},
			
			remarks_operationalb_8:{
				required: true
			},
			management_operationalb_8:{
				required: true
			},
			
			remarks_operationalb_9:{
				required: true
			},
			management_operationalb_9:{
				required: true
			},
			
			remarks_operationalb_10:{
				required: true
			},
			management_operationalb_10:{
				required: true
			},
			
			remarks_operationalb_11:{
				required: true
			},
			management_operationalb_11:{
				required: true
			},
			
			remarks_operationalb_12:{
				required: true
			},
			management_operationalb_12:{
				required: true
			},
			
			remarks_operationalb_13:{
				required: true
			},
			management_operationalb_13:{
				required: true
			},
			
			remarks_operationalb_14:{
				required: true
			},
			management_operationalb_14:{
				required: true
			},
			
			remarks_operationalb_15:{
				required: true
			},
			management_operationalb_15:{
				required: true
			},
			
			remarks_operationalb_16:{
				required: true
			},
			management_operationalb_16:{
				required: true
			},
			
			remarks_operationalb_17:{
				required: true
			},
			management_operationalb_17:{
				required: true
			},
			
			remarks_operationalb_18:{
				required: true
			},
			management_operationalb_18:{
				required: true
			},
			
			remarks_operationalb_19:{
				required: true
			},
			management_operationalb_19:{
				required: true
			},
			
			remarks_operationalb_20:{
				required: true
			},
			management_operationalb_20:{
				required: true
			},
			
			remarks_operationalb_21:{
				required: true
			},
			management_operationalb_21:{
				required: true
			},
			
			remarks_operationalb_22:{
				required: true
			},
			management_operationalb_22:{
				required: true
			},
			
			
			
			remarks_timelyc_1:{
				required: true
			},
			management_timelyc_1:{
				required: true
			},
			remarks_timelyc_2:{
				required: true
			},
	    	management_timelyc_2:{
				required: true
			},
			remarks_timelyc_3:{
				required: true
			},
			management_timelyc_3:{
				required: true
			},
			remarks_timelyc_4:{
				required: true
			},
		     management_timelyc_4:{
				required: true
			},
			remarks_timelyc_5:{
				required: true
			},
			management_timelyc_5:{ 
				required: true
			},
			remarks_timelyc_6:{
				required: true
			},
			management_timelyc_6:{
				required: true
			},
			remarks_timelyc_7:{
				required: true
			},
			management_timelyc_7:{
				required: true
			},
			remarks_timelyc_8:{
				required: true
			},
			management_timelyc_8:{
				required: true
			},
			remarks_timelyc_9:{
				required: true
			},
			management_timelyc_9:{
				required: true
			},
			remarks_timelyc_10:{
				required: true
			},
		     management_timelyc_10:{
				required: true
			},
			remarks_timelyc_11:{
				required: true
			},
			management_timelyc_11:{
				required: true
			},
			remarks_timelyc_12:{
				required: true
			},
			management_timelyc_12:{
				required: true
			},
			remarks_timelyc_13:{
				required: true
			},
			management_timelyc_13:{
				required: true
			},
			
			
			remarks_billingd_1:{
				required: true
			},
			management_billingd_1:{
				required: true
			},
			
			remarks_billingd_2:{
				required: true
			},
			management_billingd_2:{
				required: true
			},
			
			remarks_custodiane_1:{
				required: true
			},
			management_custodiane_1:{
				required: true
			},
			
			remarks_custodiane_2:{
				required: true
			},
			management_custodiane_2:{
				required: true
			},
			
			
			remarks_custodiane_3:{
				required: true
			},
			management_custodiane_3:{
				required: true
			},
			
			
			remarks_custodiane_4:{
				required: true
			},
			management_custodiane_4:{
				required: true
			},
			
			
			remarks_custodiane_5:{
				required: true
			},
			management_custodiane_5:{
				required: true
			},
			
			remarks_infrastructuref_1:{
				required: true
			},
			management_infrastructuref_1:{
				required: true
			},
			
			remarks_infrastructuref_2:{
				required: true
			},
			management_infrastructuref_2:{
				required: true
			},
			
			
			remarks_infrastructuref_3:{
				required: true
			},
			management_infrastructuref_3:{
				required: true
			},
			
			remarks_infrastructuref_4:{
				required: true
			},
			management_infrastructuref_4:{
				required: true
			},
			
			remarks_infrastructuref_5:{
				required: true
			},
			management_infrastructuref_5:{
				required: true
			},
			
			remarks_infrastructuref_6:{
				required: true
			},
			management_infrastructuref_6:{
				required: true
			},
			
			remarks_infrastructuref_7:{
				required: true
			},
			management_infrastructuref_7:{
				required: true
			},
			
			remarks_infrastructuref_8:{
				required: true
			},
			management_infrastructuref_8:{
				required: true
			},
			
			remarks_infrastructuref_9:{
				required: true
			},
			management_infrastructuref_9:{
				required: true
			},
			
			remarks_infrastructuref_10:{
				required: true
			},
			management_infrastructuref_10:{
				required: true
			},
			
			remarks_infrastructuref_11:{
				required: true
			},
			management_infrastructuref_11:{
				required: true
			},
			
			
			remarks_infrastructuref_12:{
				required: true
			},
			management_infrastructuref_12:{
				required: true
			},
			
			
			remarks_protectiong_1:{
				required: true
			},
			management_protectiong_1:{
				required: true
			},
			remarks_protectiong_2:{
				required: true
			},
			management_protectiong_2:{
				required: true
			},
			
			remarks_protectiong_3:{
				required: true
			},
			management_protectiong_3:{
				required: true
			},
			
			remarks_protectiong_4:{
				required: true
			},
			management_protectiong_4:{
				required: true
			},
			
			remarks_protectiong_5:{
				required: true
			},
			management_protectiong_5:{
				required: true
			},
			
			remarks_protectiong_6:{
				required: true
			},
			management_protectiong_6:{
				required: true
			},
			
			remarks_recordh_1:{
				required: true
			},
			management_recordh_1:{
				required: true
			},
			
			remarks_recordh_2:{
				required: true
			},
			management_recordh_2:{
				required: true
			},
			
			remarks_recordh_3:{
				required: true
			},
			management_recordh_3:{
				required: true
			},
			
			remarks_recordh_4:{
				required: true
			},
			management_recordh_4:{
				required: true
			},
			
			remarks_recordh_4:{
				required: true
			},
			management_recordh_4:{
				required: true
			},
			
			remarks_grievancei_1:{
				required: true
			},
			management_grievancei_1:{
				required: true
			},
			remarks_grievancei_2:{
				required: true
			},
			management_grievancei_2:{
				required: true
			}, 
			
			remarks_otherj_1:{
				required: true
			},
			management_otherj_1:{
				required: true
			},
			remarks_otherj_2:{
				required: true
			},
			management_otherj_2:{
				required: true
			},
				
			
			remarks_governancek_1:{
				required: true
			},
			management_governancek_1:{
				required: true
			},
			remarks_governancek_2:{
				required: true
			},
			management_governancek_2:{
				required: true
			},
			
			remarks_governancek_3:{
				required: true
			},
			management_governancek_3:{
				required: true
			},
			
			remarks_governancek_4:{
				required: true
			},
			management_governancek_4:{
				required: true
			},	
			
	  },
	  errorPlacement: function (error, element) {
         /* if (element.attr("name") == "annualreport") {
              error.appendTo("#error1");
          }  */
         if (element.attr("name") == "roles") {
              error.appendTo("#error-roles");
          }else if (element.attr("name") == "remarks_regulatorya_1") {
              error.appendTo("#remarks_1_error");
          }else if (element.attr("name") == "management_regulatorya_1") {
              error.appendTo("#management_1_error");
          }else if (element.attr("name") == "remarks_regulatorya_2") {
              error.appendTo("#remarks_2_error");
          }else if (element.attr("name") == "management_regulatorya_2") {
              error.appendTo("#management_2_error");
          }else if (element.attr("name") == "remarks_regulatorya_3") {
              error.appendTo("#remarks_3_error");
          }else if (element.attr("name") == "management_regulatorya_3") {
              error.appendTo("#management_3_error");
          }else if (element.attr("name") == "remarks_regulatorya_4") {
              error.appendTo("#remarks_4_error");
          }else if (element.attr("name") == "management_regulatorya_4") {
              error.appendTo("#management_4_error");
          }else if (element.attr("name") == "remarks_regulatorya_5") {
              error.appendTo("#remarks_5_error");
          }else if (element.attr("name") == "management_regulatorya_5") {
              error.appendTo("#management_5_error");
          }else if (element.attr("name") == "remarks_regulatorya_6") {
              error.appendTo("#remarks_6_error");
          }else if (element.attr("name") == "management_regulatorya_6") {
              error.appendTo("#management_6_error");
          }else if (element.attr("name") == "remarks_regulatorya_7") {
              error.appendTo("#remarks_7_error");
          }else if (element.attr("name") == "management_regulatorya_7") {
              error.appendTo("#management_7_error");
          }else if (element.attr("name") == "remarks_regulatorya_8") {
              error.appendTo("#remarks_8_error");
          }else if (element.attr("name") == "management_regulatorya_8") {
              error.appendTo("#management_8_error");
          }else if (element.attr("name") == "remarks_regulatorya_9") {
              error.appendTo("#remarks_9_error");
          }else if (element.attr("name") == "management_regulatorya_9") {
              error.appendTo("#management_9_error");
          }
          else if (element.attr("name") == "remarks_operationalb_1") {
              error.appendTo("#remarks_operationalb_1_error");
          }else if (element.attr("name") == "management_operationalb_1") {
              error.appendTo("#management_operationalb_1_error");
          }
          else if (element.attr("name") == "remarks_operationalb_2") {
              error.appendTo("#remarks_operationalb_2_error");
          }else if (element.attr("name") == "management_operationalb_2") {
              error.appendTo("#management_operationalb_2_error");
          }
          else if (element.attr("name") == "remarks_operationalb_3") {
              error.appendTo("#remarks_operationalb_3_error");
          }else if (element.attr("name") == "management_operationalb_3") {
              error.appendTo("#management_operationalb_3_error");
          }
          else if (element.attr("name") == "remarks_operationalb_4") {
              error.appendTo("#remarks_operationalb_4_error");
          }else if (element.attr("name") == "management_operationalb_4") {
              error.appendTo("#management_operationalb_4_error");
          }
          else if (element.attr("name") == "remarks_operationalb_5") {
              error.appendTo("#remarks_operationalb_5_error");
          }else if (element.attr("name") == "management_operationalb_5") {
              error.appendTo("#management_operationalb_5_error");
          }
          else if (element.attr("name") == "remarks_operationalb_6") {
              error.appendTo("#remarks_operationalb_6_error");
          }else if (element.attr("name") == "management_operationalb_6") {
              error.appendTo("#management_operationalb_6_error");
          }
          else if (element.attr("name") == "remarks_operationalb_7") {
              error.appendTo("#remarks_operationalb_7_error");
          }else if (element.attr("name") == "management_operationalb_7") {
              error.appendTo("#management_operationalb_7_error");
          }
          else if (element.attr("name") == "remarks_operationalb_8") {
              error.appendTo("#remarks_operationalb_8_error");
          }else if (element.attr("name") == "management_operationalb_8") {
              error.appendTo("#management_operationalb_8_error");
          }
          else if (element.attr("name") == "remarks_operationalb_9") {
              error.appendTo("#remarks_operationalb_9_error");
          }else if (element.attr("name") == "management_operationalb_9") {
              error.appendTo("#management_operationalb_9_error");
          }
          else if (element.attr("name") == "remarks_operationalb_10") {
              error.appendTo("#remarks_operationalb_10_error");
          }else if (element.attr("name") == "management_operationalb_10") {
              error.appendTo("#management_operationalb_10_error");
          } 
          else if (element.attr("name") == "remarks_operationalb_11") {
              error.appendTo("#remarks_operationalb_11_error");
          }else if (element.attr("name") == "management_operationalb_11") {
              error.appendTo("#management_operationalb_11_error");
          }
          else if (element.attr("name") == "remarks_operationalb_12") {
              error.appendTo("#remarks_operationalb_12_error");
          }else if (element.attr("name") == "management_operationalb_12") {
              error.appendTo("#management_operationalb_12_error");
          }
          else if (element.attr("name") == "remarks_operationalb_13") {
              error.appendTo("#remarks_operationalb_13_error");
          }else if (element.attr("name") == "management_operationalb_13") {
              error.appendTo("#management_operationalb_13_error");
          }
          else if (element.attr("name") == "remarks_operationalb_14") {
              error.appendTo("#remarks_operationalb_14_error");
          }else if (element.attr("name") == "management_operationalb_14") {
              error.appendTo("#management_operationalb_14_error");
          }
          else if (element.attr("name") == "remarks_operationalb_15") {
              error.appendTo("#remarks_operationalb_15_error");
          }else if (element.attr("name") == "management_operationalb_15") {
              error.appendTo("#management_operationalb_15_error");
          }
          else if (element.attr("name") == "remarks_operationalb_16") {
              error.appendTo("#remarks_operationalb_16_error");
          }else if (element.attr("name") == "management_operationalb_16") {
              error.appendTo("#management_operationalb_16_error");
          }
          else if (element.attr("name") == "remarks_operationalb_17") {
              error.appendTo("#remarks_operationalb_17_error");
          }else if (element.attr("name") == "management_operationalb_17") {
              error.appendTo("#management_operationalb_17_error");
          }
          else if (element.attr("name") == "remarks_operationalb_18") {
              error.appendTo("#remarks_operationalb_18_error");
          }else if (element.attr("name") == "management_operationalb_18") {
              error.appendTo("#management_operationalb_18_error");
          }
          else if (element.attr("name") == "remarks_operationalb_19") {
              error.appendTo("#remarks_operationalb_19_error");
          }else if (element.attr("name") == "management_operationalb_19") {
              error.appendTo("#management_operationalb_19_error");
          }
          else if (element.attr("name") == "remarks_operationalb_20") {
              error.appendTo("#remarks_operationalb_20_error");
          }else if (element.attr("name") == "management_operationalb_20") {
              error.appendTo("#management_operationalb_20_error");
          }
          else if (element.attr("name") == "remarks_operationalb_21") {
              error.appendTo("#remarks_operationalb_21_error");
          }else if (element.attr("name") == "management_operationalb_21") {
              error.appendTo("#management_operationalb_21_error");
          }
          else if (element.attr("name") == "remarks_operationalb_22") {
              error.appendTo("#remarks_operationalb_22_error");
          }else if (element.attr("name") == "management_operationalb_22") {
              error.appendTo("#management_operationalb_22_error");
          }
         
          else if (element.attr("name") == "remarks_timelyc_1") {
              error.appendTo("#remarks_timelyc_1_error");
          }else if (element.attr("name") == "management_timelyc_1") { 
              error.appendTo("#management_timelyc_1_error");
          }
         
          else if (element.attr("name") == "remarks_timelyc_2") {
              error.appendTo("#remarks_timelyc_2_error");
          }else if (element.attr("name") == "management_timelyc_2") {
              error.appendTo("#management_timelyc_2_error");
          }
         
          else if (element.attr("name") == "remarks_timelyc_3") {
              error.appendTo("#remarks_timelyc_3_error");
          }else if (element.attr("name") == "management_timelyc_3") {
              error.appendTo("#management_timelyc_3_error");
          }
         
          else if (element.attr("name") == "remarks_timelyc_4") {
              error.appendTo("#remarks_timelyc_4_error");
          }else if (element.attr("name") == "management_timelyc_4") {
              error.appendTo("#management_timelyc_4_error");
          }
         
          else if (element.attr("name") == "remarks_timelyc_5") {
              error.appendTo("#remarks_timelyc_5_error");
          }else if (element.attr("name") == "management_timelyc_5") {
              error.appendTo("#management_timelyc_5_error");
          }
         
         
          else if (element.attr("name") == "remarks_timelyc_6") {
              error.appendTo("#remarks_timelyc_6_error");
          }else if (element.attr("name") == "management_timelyc_6") {
              error.appendTo("#management_timelyc_6_error");
          }
         
          else if (element.attr("name") == "remarks_timelyc_7") {
              error.appendTo("#remarks_timelyc_7_error");
          }else if (element.attr("name") == "management_timelyc_7") {
              error.appendTo("#management_timelyc_7_error");
          }
         
         
          else if (element.attr("name") == "remarks_timelyc_8") {
              error.appendTo("#remarks_timelyc_8_error");
          }else if (element.attr("name") == "management_timelyc_8") {
              error.appendTo("#management_timelyc_8_error");
          }
         
          else if (element.attr("name") == "remarks_timelyc_9") {
              error.appendTo("#remarks_timelyc_9_error");
          }else if (element.attr("name") == "management_timelyc_9") {
              error.appendTo("#management_timelyc_9_error");
          }
         
         
          else if (element.attr("name") == "remarks_timelyc_10") {
              error.appendTo("#remarks_timelyc_10_error");
          }else if (element.attr("name") == "management_timelyc_10") {
              error.appendTo("#management_timelyc_10_error");
          }
         
         
          else if (element.attr("name") == "remarks_timelyc_11") {
              error.appendTo("#remarks_timelyc_11_error");
          }else if (element.attr("name") == "management_timelyc_11") {
              error.appendTo("#management_timelyc_11_error");
          }
         
          else if (element.attr("name") == "remarks_timelyc_12") {
              error.appendTo("#remarks_timelyc_12_error");
          }else if (element.attr("name") == "management_timelyc_12") {
              error.appendTo("#management_timelyc_12_error");
          }
         
         
          else if (element.attr("name") == "remarks_timelyc_13") {
              error.appendTo("#remarks_timelyc_13_error");
          }else if (element.attr("name") == "management_timelyc_13") {
              error.appendTo("#management_timelyc_13_error");
          }
         
          else if (element.attr("name") == "remarks_billingd_1") {
              error.appendTo("#remarks_billingd_1_error");
          }else if (element.attr("name") == "management_billingd_1") {
              error.appendTo("#management_billingd_1_error");
          }
         
         
          else if (element.attr("name") == "remarks_billingd_2") {
              error.appendTo("#remarks_billingd_2_error");
          }else if (element.attr("name") == "management_billingd_2") {
              error.appendTo("#management_billingd_2_error");
          }
         
         
          else if (element.attr("name") == "remarks_custodiane_1") {
              error.appendTo("#remarks_custodiane_1_error");
          }else if (element.attr("name") == "management_custodiane_1") {
              error.appendTo("#management_custodiane_1_error");
          }
         
          else if (element.attr("name") == "remarks_custodiane_2") {
              error.appendTo("#remarks_custodiane_2_error");
          }else if (element.attr("name") == "management_custodiane_2") {
              error.appendTo("#management_custodiane_2_error");
          }
         
         
          else if (element.attr("name") == "remarks_custodiane_3") {
              error.appendTo("#remarks_custodiane_3_error");
          }else if (element.attr("name") == "management_custodiane_3") {
              error.appendTo("#management_custodiane_3_error");
          }
         
          else if (element.attr("name") == "remarks_custodiane_4") {
              error.appendTo("#remarks_custodiane_4_error");
          }else if (element.attr("name") == "management_custodiane_4") {
              error.appendTo("#management_custodiane_4_error");
          }
         
         
          else if (element.attr("name") == "remarks_custodiane_5") {
              error.appendTo("#remarks_custodiane_5_error");
          }else if (element.attr("name") == "management_custodiane_5") {
              error.appendTo("#management_custodiane_5_error");
          }
         
         
          else if (element.attr("name") == "remarks_infrastructuref_1") {
              error.appendTo("#remarks_infrastructuref_1_error");
          }else if (element.attr("name") == "management_infrastructuref_1") {
              error.appendTo("#management_infrastructuref_1_error");
          }
         
         
         
          else if (element.attr("name") == "remarks_infrastructuref_2") {
              error.appendTo("#remarks_infrastructuref_2_error");
          }else if (element.attr("name") == "management_infrastructuref_2") {
              error.appendTo("#management_infrastructuref_2_error");
          }
         
          else if (element.attr("name") == "remarks_infrastructuref_3") {
              error.appendTo("#remarks_infrastructuref_3_error");
          }else if (element.attr("name") == "management_infrastructuref_3") {
              error.appendTo("#management_infrastructuref_3_error");
          }
         
         
          else if (element.attr("name") == "remarks_infrastructuref_4") {
              error.appendTo("#remarks_infrastructuref_4_error");
          }else if (element.attr("name") == "management_infrastructuref_4") {
              error.appendTo("#management_infrastructuref_4_error");
          }
         
          else if (element.attr("name") == "remarks_infrastructuref_5") {
              error.appendTo("#remarks_infrastructuref_5_error");
          }else if (element.attr("name") == "management_infrastructuref_5") {
              error.appendTo("#management_infrastructuref_5_error");
          }
         
          else if (element.attr("name") == "remarks_infrastructuref_6") {
              error.appendTo("#remarks_infrastructuref_6_error");
          }else if (element.attr("name") == "management_infrastructuref_6") {
              error.appendTo("#management_infrastructuref_6_error");
          }
         
         
          else if (element.attr("name") == "remarks_infrastructuref_7") {
              error.appendTo("#remarks_infrastructuref_7_error");
          }else if (element.attr("name") == "management_infrastructuref_7") {
              error.appendTo("#management_infrastructuref_7_error");
          }
         
         
          else if (element.attr("name") == "remarks_infrastructuref_8") {
              error.appendTo("#remarks_infrastructuref_8_error");
          }else if (element.attr("name") == "management_infrastructuref_8") {
              error.appendTo("#management_infrastructuref_8_error");
          }
         
         
          else if (element.attr("name") == "remarks_infrastructuref_8") {
              error.appendTo("#remarks_infrastructuref_8_error");
          }else if (element.attr("name") == "management_infrastructuref_8") {
              error.appendTo("#management_infrastructuref_8_error");
          }
         
         
          else if (element.attr("name") == "remarks_infrastructuref_9") {
              error.appendTo("#remarks_infrastructuref_9_error");
          }else if (element.attr("name") == "management_infrastructuref_9") {
              error.appendTo("#management_infrastructuref_9_error");
          }
         
         
          else if (element.attr("name") == "remarks_infrastructuref_10") {
              error.appendTo("#remarks_infrastructuref_10_error");
          }else if (element.attr("name") == "management_infrastructuref_10") {
              error.appendTo("#management_infrastructuref_10_error");
          }
         
         
          else if (element.attr("name") == "remarks_infrastructuref_11") {
              error.appendTo("#remarks_infrastructuref_11_error");
          }else if (element.attr("name") == "management_infrastructuref_11") {
              error.appendTo("#management_infrastructuref_11_error");
          }
         
          else if (element.attr("name") == "remarks_infrastructuref_12") {
              error.appendTo("#remarks_infrastructuref_12_error");
          }else if (element.attr("name") == "management_infrastructuref_12") {
              error.appendTo("#management_infrastructuref_12_error");
          }
         
         
          else if (element.attr("name") == "remarks_protectiong_1") {
              error.appendTo("#remarks_protectiong_1_error");
          }else if (element.attr("name") == "management_protectiong_1") {
              error.appendTo("#management_protectiong_1_error");
          }
         
          else if (element.attr("name") == "remarks_protectiong_2") {
              error.appendTo("#remarks_protectiong_2_error");
          }else if (element.attr("name") == "management_protectiong_2") {
              error.appendTo("#management_protectiong_2_error");
          }
         
          else if (element.attr("name") == "remarks_protectiong_3") {
              error.appendTo("#remarks_protectiong_3_error");
          }else if (element.attr("name") == "management_protectiong_3") {
              error.appendTo("#management_protectiong_3_error");
          }
         
         
          else if (element.attr("name") == "remarks_protectiong_4") {
              error.appendTo("#remarks_protectiong_4_error");
          }else if (element.attr("name") == "management_protectiong_4") {
              error.appendTo("#management_protectiong_4_error");
          }
         
         
          else if (element.attr("name") == "remarks_protectiong_5") {
              error.appendTo("#remarks_protectiong_5_error");
          }else if (element.attr("name") == "management_protectiong_5") {
              error.appendTo("#management_protectiong_5_error");
          }
         
         
          else if (element.attr("name") == "remarks_protectiong_6") {
              error.appendTo("#remarks_protectiong_6_error");
          }else if (element.attr("name") == "management_protectiong_6") {
              error.appendTo("#management_protectiong_6_error");
          }
         
         
          else if (element.attr("name") == "remarks_recordh_1") {
              error.appendTo("#remarks_recordh_1_error");
          }else if (element.attr("name") == "management_recordh_1") {
              error.appendTo("#management_recordh_1_error");
          }
         
          else if (element.attr("name") == "remarks_recordh_2") {
              error.appendTo("#remarks_recordh_2_error");
          }else if (element.attr("name") == "management_recordh_2") {
              error.appendTo("#management_recordh_2_error");
          }
         
          else if (element.attr("name") == "remarks_recordh_3") {
              error.appendTo("#remarks_recordh_3_error");
          }else if (element.attr("name") == "management_recordh_3") {
              error.appendTo("#management_recordh_3_error");
          }
         
         
          else if (element.attr("name") == "remarks_recordh_4") {
              error.appendTo("#remarks_recordh_4_error");
          }else if (element.attr("name") == "management_recordh_4") {
              error.appendTo("#management_recordh_4_error");
          }
         
         
          else if (element.attr("name") == "remarks_grievancei_1") {
              error.appendTo("#remarks_grievancei_1_error");
          }else if (element.attr("name") == "management_grievancei_1") {
              error.appendTo("#management_grievancei_1_error");
          }
         
          else if (element.attr("name") == "remarks_grievancei_2") {
              error.appendTo("#remarks_grievancei_2_error");
          }else if (element.attr("name") == "management_grievancei_2") {
              error.appendTo("#management_grievancei_2_error");
          }
         
         
          else if (element.attr("name") == "remarks_otherj_1") {
              error.appendTo("#remarks_otherj_1_error");
          }else if (element.attr("name") == "management_otherj_1") {
              error.appendTo("#management_otherj_1_error");
          }
         
          else if (element.attr("name") == "remarks_otherj_2") {
              error.appendTo("#remarks_otherj_2_error");
          }else if (element.attr("name") == "management_otherj_2") {
              error.appendTo("#management_otherj_2_error");
          }
         
         
          else if (element.attr("name") == "remarks_governancek_1") {
              error.appendTo("#remarks_governancek_1_error");
          }else if (element.attr("name") == "management_governancek_1") {
              error.appendTo("#management_governancek_1_error");
          }
         
          else if (element.attr("name") == "remarks_governancek_2") {
              error.appendTo("#remarks_governancek_2_error");
          }else if (element.attr("name") == "management_governancek_2") {
              error.appendTo("#management_governancek_2_error");
          }
         
          else if (element.attr("name") == "remarks_governancek_3") {
              error.appendTo("#remarks_governancek_3_error");
          }else if (element.attr("name") == "management_governancek_3") {
              error.appendTo("#management_governancek_3_error");
          }
         
          else if (element.attr("name") == "remarks_governancek_4") {
              error.appendTo("#remarks_governancek_4_error");
          }else if (element.attr("name") == "management_governancek_4") {
              error.appendTo("#management_governancek_4_error");
          }
          else {
        	  error.appendTo(element.parent().parent().after());
          }
      },

	}); 
	
	//regulatorya1
	
  $('.regulatorya1').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
  //remarks_regulatorya_1
  
 
	
  $('.regulatorya2').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  $('.regulatorya3').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  $('.regulatorya4').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  $('.regulatorya5').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  $('.regulatorya6').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  $('.regulatorya7').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  $('.regulatorya8').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  $('.regulatorya9').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  //operationalb1
  
   $('.operationalb1').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
 
  $('.operationalb2').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb3').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb4').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  $('.operationalb5').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb6').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb7').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb8').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb9').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb10').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb11').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb12').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb13').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb14').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb15').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb16').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb17').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb18').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
  
  
  $('.operationalb19').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
	
	//timelyc1
	
	
	$('.timelyc1').each(function() {
      $(this).rules("add", 
          {
              required: true,
              messages: {
                  required: "This field is required.",
              }
          }
      );
  });
	
	
	$('.timelyc2').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc3').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc4').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc5').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc6').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc7').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc8').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc9').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc10').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc11').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.timelyc12').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });

	$('.timelyc13').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	//billingd2

	$('.billingd1').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.billingd2').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	//custodiane5
	
		$('.custodiane1').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
		$('.custodiane2').each(function() {
		      $(this).rules("add", 
		          {
		              required: true,
		              messages: {
		                  required: "This field is required.",
		              }
		          }
		      );
		  });
		
		
		$('.custodiane3').each(function() {
		      $(this).rules("add", 
		          {
		              required: true,
		              messages: {
		                  required: "This field is required.",
		              }
		          }
		      );
		  });
		
		
		$('.custodiane4').each(function() {
		      $(this).rules("add", 
		          {
		              required: true,
		              messages: {
		                  required: "This field is required.",
		              }
		          }
		      );
		  });
		
		
		$('.custodiane5').each(function() {
		      $(this).rules("add", 
		          {
		              required: true,
		              messages: {
		                  required: "This field is required.",
		              }
		          }
		      );
		  });
		
	//infrastructuref12
	
	$('.infrastructuref1').each(function() {
		      $(this).rules("add", 
		          {
		              required: true,
		              messages: {
		                  required: "This field is required.",
		              }
		          }
		      );
		  });
	
	
	$('.infrastructuref2').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref3').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref4').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref5').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref6').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref7').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref8').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref9').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref10').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref11').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.infrastructuref12').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	//protectiong6
	
	$('.protectiong1').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.protectiong2').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.protectiong3').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.protectiong4').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	$('.protectiong5').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	$('.protectiong6').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	//recordh4
	
	
	$('.recordh1').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.recordh2').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.recordh3').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.recordh4').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	//grievancei2
	
	$('.grievancei1').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	$('.grievancei2').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	//otherj1
	
	
	$('.otherj1').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	$('.otherj2').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });  
	
	//governancek4
	
	  $('.governancek1').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	$('.governancek2').each(function() {
	     $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.governancek3').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
	
	$('.governancek4').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });

	
	$('.comments_regulatorya8').each(function() {
	      $(this).rules("add", 
	          {
	              required: true,
	              messages: {
	                  required: "This field is required.",
	              }
	          }
	      );
	  });
	
			

	$("#annualComplains").on('submit', (function(e) {
		console.log("Inside ajax");
		
		e.preventDefault();
		
		if ($('input[name="annualreport"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error1").html("Please select a file to upload");
    	    return false;
    	}
    	else if($('input[name="annualreport"]').get(0).files[0].name != "Annual Audit Report.pdf"){
    		console.log("No files selected.");
    		$("#error1").html("Please upload Annual Audit Report.pdf");
    	    return false;
    	}

			if($("#annualComplains" ).valid()){
					
					$("#sub").addClass("hide");
					
					var formData = new FormData($("form.form")[0]);

					var htmlWidth = $("#canvasD").width();
					var htmlHeight = $("#canvasD").height();
					var pdfWidth = htmlWidth + (15 * 2);
					var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
					var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

					var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;

					html2canvas($("#canvasD")[0], {imageTimeout: 5000, allowTaint: true }).then(function(canvas) {
					canvas.getContext('2d');

					var image = canvas.toDataURL("image/jpeg", 1.0);
					doc.addImage(image, 'JPEG', 15, 15, htmlWidth, htmlHeight);

					for (var i = 1; i <= pageCount; i++) {
						doc.addPage(pdfWidth, pdfHeight);
						doc.addImage(image, 'JPEG', 15, -(pdfHeight * i)+15, htmlWidth, htmlHeight);
					}

					console.log("doc.output('bloburl') ::: ", doc.output('arraybuffer'));
	/* 				doc.save("split-to-multi-page.pdf");
				     window.open(doc.output('bloburl')); */
				     
					var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
				     
					console.log("file::: ", file);
					
					formData.append("Custodian_Annual_Audit_Report_pdf_file", file);

					$(".nps-page-main").hide();
					$(".animaion").show();
					$.ajax({
			            type: "POST",
			            enctype: 'multipart/form-data',
			            processData: false,
			            contentType: false,
			            cache: false,
			            url: '${cutannualaditreportResourceURL}',
			            data: formData,
			            success: function(data) {
			            	$(".animaion").hide();
			            	$(".nps-page-main").show();
			            	//if(data == "Success") {
			            		//toastr.success('Form has been submitted successfully!');
			            		$('#success_tic').modal('show');
		   		            	$("#annualComplains")[0].reset();
		   		            	$('#success_tic').on('hidden.bs.modal', function (e) {
			            			location.reload(); 
			            		})
			            	/*} else {
			            		console.log("Error Occured in ajax call");
			            		//toastr.error('An error occured while submitting the form. Please try again');
			            	}*/
			            },
			            error: function() {
			            	$(".animaion").hide();
			            	//toastr.error('An error occured while submitting the form. Please try again');
			            	$('#output').html("An error occured while submitting the form. Please try again");
			            	$('#errorExcel').modal('show');
			            	console.log("Error Occured in ajax call");
			            },
			            complete: function(){
			            	$(".animaion").hide();
			            	if($("#sub").hasClass("hide")){
				            	$("#sub").removeClass("hide");
			            	}
			            	//$(".annexure-upload").val(null);
							//setTimeout(function() { location.reload(true); }, 5000);
				        }
			
			        });
					
				});

			}

		}));
    
});

</script>

