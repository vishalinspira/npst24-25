<%@page import="com.internal.audit.report.pfm.util.DocumentUploadUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.daily.average.service.service.HDFCInternal_Audit_Report_ScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.HDFCInternal_Audit_Report_Scrutiny"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.internal.audit.report.pfm.util.Pre_Populate_scrutinydata"%>
<%@ include file="/init.jsp" %>

<%
	Pre_Populate_scrutinydata sd = new Pre_Populate_scrutinydata();
	sd.pre_populate_scrutiny_data(request);
	
	HDFCInternal_Audit_Report_Scrutiny hdfciarscrutiny = Validator.isNotNull(request.getAttribute("hdfcIARScrutiny")) ? (HDFCInternal_Audit_Report_Scrutiny) request.getAttribute("hdfcIARScrutiny") : HDFCInternal_Audit_Report_ScrutinyLocalServiceUtil.createHDFCInternal_Audit_Report_Scrutiny(0);
	
	System.out.println("JSP hdfcIARScrutiny ------------------- "+hdfciarscrutiny.toString());
	
	JSONObject json_two = JSONFactoryUtil.createJSONObject();
	
	try {
		if(hdfciarscrutiny.getManagement_comments()!=null && !hdfciarscrutiny.getManagement_comments().trim().equals("")) {
			
			JSONObject json_one = JSONFactoryUtil.createJSONObject(hdfciarscrutiny.getManagement_comments());
			
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Board Meeting")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Investment Operational Manual/Procedure")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Investment Committee")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Investment Policy")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Risk Management Committee")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Risk Management Policy")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Pattern of Investment")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Front office dealing procedure")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Back Office Procedure")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Exposure & Prudential Norms")));
			
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Custodian Controls")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Verification of Other investments")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Units Accounting")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Investment Bank accounts")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Appointment of Brokers")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Non-Performing Investments")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Inter Scheme Transfer")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Investment Deals verification")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Accounting")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("NAV")));
			
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Disclosure")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Periodical returns to Authority/ Trust")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Compliances")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Internal Audit/ PFM Audit/ Scheme Audit")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Marketing & distribution")));
			json_two = DocumentUploadUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Other regulatory compliances")));
					
/* 			System.out.println("get json_two ----------> "+json_two.toString()); */
					
		}
		
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
      	<i class="fas fa-times-circle fa-4x d-block mb-4"></i>
      	<span id="output">An error occured while submitting the form. Please try again</span>
      </div>       
    </div>
  </div>
</div>

<div class="nps-page-main nps-upload-report nps-statement-wrapper nps-pdf-gen">
   <div class="row mb-3">
      <div class="col-12">
         <div class="text-right">
            <p  class="back_btn"><a class="backbtn" href="/web/guest/quarterly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
         </div>
      </div>
   </div>
   <div class="row" id="canvasD">
	<div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Internal Audit Report</h4>
            <form class="form" id="pfm_internal_audit_report_form" action="#" method="post">
               <input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
               <input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label for="name">Report Due Date</label>
                           <input class="reportDate" type="date" value="${reportDate }" name="<portlet:namespace/>reportDate" readonly="readonly">
                        </div>
                     </div>
                  </div>
               </div><br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label for="name">Period From</label>
                           <input type="date" class="externalFields"  id="period_from" name="period_from">
                           <%--&nbsp;&nbsp; --%><span class=" text-danger"></span>
                           <br>
                        </div>
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label for="name">To</label>
                           <input type="date" class="externalFields" id="period_to" name="period_to">
                            <%--&nbsp;&nbsp; --%><span class=" text-danger"></span>
                           <br>
                        </div>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Date : </label>
                        <input type="date" class="form_date externalFields" id="form_date" name="form_date">
                        <%--&nbsp;&nbsp;  --%><span class=" text-danger"></span>
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="roles"><strong>Pension Fund Name :</strong></label><br>
                     <!-- <select class="w-100 externalFields" name="pension_fund_name" id="pension_fund_name">
                        <option value="">Select</option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        </select> -->
                     <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>companies' value="<%=companies %>" readonly>
                     &nbsp;&nbsp;<span class=" text-danger"></span>
                  </div>
               </div>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>IAR Certificate by Auditor (PDF Upload)</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile1">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="certificate_pdf" name="certificate_pdf" accept=".pdf"/>
                           </div>
                           <%--&nbsp;&nbsp;  --%><span class=" text-danger"></span>
                           <br>
                        </div>
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>IAR File by Auditor (PDF Upload)</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile2">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="auditor_pdf" name="auditor_pdf" accept=".pdf"/>  
                           </div>
                           <%--&nbsp;&nbsp; --%><span class=" text-danger"></span>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Internal Audit Report (PDF Upload)</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_8">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="iAR1_pdf" name="intrnlAdtRprt_pdf" accept=".pdf"/>  
                           </div>
                           <%--&nbsp;&nbsp; --%><span class=" text-danger"></span>
                           <br>
                        </div>
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Extracts Of Board Minutes Approving IAR(PDF Upload)</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_9">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="iAR2_pdf" name="extrctBrdMinAprvng_pdf" accept=".pdf"/>  
                           </div>
                           <%--&nbsp;&nbsp; --%><span class=" text-danger"></span>
                           <br>
                        </div>
                     </div>
                  </div>
                     
               </div>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div>
                        <p class="mb-0">Summary of Internal Audit Report :</p>
                     </div>
                  </div>
               </div>
               <br>
               <div class="statement-wrapper">
                  <div id="table" class="table-editable cust-scroll">
                     <div class="table-cont">
                        <table id="Internal_Audit_Report_table" class="table css-serial">
                           <thead>
                              <tr>
                                 <th class="col-1">Sr.No</th>
                                 <th class="col-2">Areas</th>
                                 <th class="col-1">Sample</th>
                                 <th class="col-1">Complied</th>
                                 <th class="col-2">Auditors Comments</th>
                                 <th class="col-2">Management Comments</th>
                              </tr>
                           </thead>
                           <tbody>
                              <!-- start -->
                              <tr>
                                 <td class="text-center">1.</td>
                                 <td colspan="5">Board Meeting</td>
                              </tr>
                              <tr>
                                 <td class="text-center">1.1</td>
                                 <td>Composition of Board</td>
                                 <td><textarea rows="3" class="boardMeeting"  name="boardMeeting_sample_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="boardMeeting_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio" name="boardMeeting_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="boardMeeting_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" readonly><%=json_two.get("boardMeeting_management_remark_1") == null ? "" : json_two.get("boardMeeting_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">1.2</td>
                                 <td>Frequency of meeting</td>
                                 <td><textarea rows="3" class="boardMeeting"  name="boardMeeting_sample_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio" name="boardMeeting_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio" name="boardMeeting_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="boardMeeting_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("boardMeeting_management_remark_2") == null ? "" : json_two.get("boardMeeting_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">1.3</td>
                                 <td>Minutes of the meeting</td>
                                 <td><textarea rows="3" class="boardMeeting" name="boardMeeting_sample_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="boardMeeting_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="boardMeeting_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="boardMeeting_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("boardMeeting_management_remark_3") == null ? "" : json_two.get("boardMeeting_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">1.4</td>
                                 <td>Recommendation</td>
                                 <td><textarea rows="3" class="boardMeeting" name="boardMeeting_sample_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="boardMeeting_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="boardMeeting_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="boardMeeting_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("boardMeeting_management_remark_4") == null ? "" : json_two.get("boardMeeting_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">1.5</td>
                                 <td>Delegation of authority</td>
                                 <td><textarea rows="3" class="boardMeeting" name="boardMeeting_sample_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="boardMeeting_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="boardMeeting_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="boardMeeting_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("boardMeeting_management_remark_5") == null ? "" : json_two.get("boardMeeting_management_remark_5") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">2.</td>
                                 <td colspan="5">Investment Operational Manual / Procedure</td>
                              </tr>
                              <tr>
                                 <td class="text-center">2.1</td>
                                 <td>Approval</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Operational_management_remark_1") == null ? "" : json_two.get("Operational_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">2.2</td>
                                 <td>Amendments and approval</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_sample_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled></textarea><%=json_two.get("Operational_management_remark_2") == null ? "" : json_two.get("Operational_management_remark_2") %></td>
                              </tr>
                              <tr>
                                 <td class="text-center">2.3</td>
                                 <td>Frequency of review</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_sample_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Operational_management_remark_3") == null ? "" : json_two.get("Operational_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">2.4</td>
                                 <td>Coverage</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_sample_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Operational_management_remark_4") == null ? "" : json_two.get("Operational_management_remark_4") %></textarea></td>
                              </tr>
                              <!-- sub start--> 
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Procedure for Credit appraisal and Market risk</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_sample_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Operational_management_remark_5") == null ? "" : json_two.get("Operational_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Procedure for security documents execution</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_sample_6"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Operational_management_remark_6") == null ? "" : json_two.get("Operational_management_remark_6") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Income recognition policy - accruals</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_sample_7"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_7" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_7" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_auditor_comment_7"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Operational_management_remark_7") == null ? "" : json_two.get("Operational_management_remark_7") %></textarea></td>
                              </tr>
                              <!-- sub end -->
                              <tr>
                                 <td class="text-center">2.5</td>
                                 <td>Periodic Credit review</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_sample_8"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_8" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Operational_complied_8" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Operational_auditor_comment_8" rows="3"></textarea></td>
                                 <td><textarea rows="3" name="" disabled><%=json_two.get("Operational_management_remark_8") == null ? "" : json_two.get("Operational_management_remark_8") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">3.</td>
                                 <td colspan="5">Investment Committee</td>
                              </tr>
                              <tr>
                                 <td class="text-center">3.1</td>
                                 <td>Composition of Investment committee</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_sample_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled></textarea><%=json_two.get("Investment_auditor_management_remark_1") == null ? "" : json_two.get("Investment_auditor_management_remark_1") %></td>
                              </tr>
                              <tr>
                                 <td class="text-center">3.2</td>
                                 <td>Frequency of meeting</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_sample_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Investment_auditor_management_remark_2") == null ? "" : json_two.get("Investment_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">3.3</td>
                                 <td>Minutes of the meeting</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_sample_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Investment_auditor_management_remark_3") == null ? "" : json_two.get("Investment_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">3.4</td>
                                 <td>Review of Investment policy</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_sample_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Investment_auditor_management_remark_4") == null ? "" : json_two.get("Investment_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">3.5</td>
                                 <td>Recommendation</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_sample_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Investment_auditor_management_remark_5") == null ? "" : json_two.get("Investment_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">3.6</td>
                                 <td>Delegation of authority</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_sample_6"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Investment_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Investment_auditor_management_remark_6") == null ? "" : json_two.get("Investment_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">4.</td>
                                 <td colspan="5">Investment Policy</td>
                              </tr>
                              <tr>
                                 <td class="text-center">4.1</td>
                                 <td>Approved by BOD</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_1") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">4.2</td>
                                 <td>Valuation Principles</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_2") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">4.3</td>
                                 <td>Adherence to regulatory guidelines for valuation</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_3") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">4.4</td>
                                 <td>Frequency of review</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_4") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">4.5</td>
                                 <td>Coverage of investment policy w.r.t.</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_5") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <!-- sub start--> 
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Liquidity</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_6"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_6") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Prudential norms</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_7"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_7" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_7" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_7"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_7") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_7") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Exposure limits</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_8"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_8" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_8" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_8"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_8") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_8") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Stop Loss limits in securities trading</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_sample_9"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_9" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InvestmentPolicy_complied_9" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_comment_9"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InvestmentPolicy_auditor_management_remark_9") == null ? "" : json_two.get("InvestmentPolicy_auditor_management_remark_9") %></textarea></td>
                              </tr>
                              <!-- sub end -->
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">5.</td>
                                 <td colspan="5">Risk Management Committee</td>
                              </tr>
                              <tr>
                                 <td class="text-center">5.1</td>
                                 <td>Composition of Risk Management Committee</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_sample_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagement_auditor_management_remark_1") == null ? "" : json_two.get("RiskManagement_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">5.2</td>
                                 <td>Frequency of meeting</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_sample_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagement_auditor_management_remark_2") == null ? "" : json_two.get("RiskManagement_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">5.3</td>
                                 <td>Minutes of the meeting</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_sample_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagement_auditor_management_remark_3") == null ? "" : json_two.get("RiskManagement_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">5.4</td>
                                 <td>Review of Risk Policy</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_sample_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagement_auditor_management_remark_4") == null ? "" : json_two.get("RiskManagement_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">5.5</td>
                                 <td>Recommendation</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_sample_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagement_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagement_auditor_management_remark_5") == null ? "" : json_two.get("RiskManagement_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">6.</td>
                                 <td colspan="5">Risk Management Policy</td>
                              </tr>
                              <tr>
                                 <td class="text-center">6.1</td>
                                 <td>Approved by BOD</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_1") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">6.2</td>
                                 <td>Frequency of review</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_2") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">6.3</td>
                                 <td>Disaster Recovery strategy</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_3") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">6.4</td>
                                 <td>Business Continuity Plan</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_4") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">6.5</td>
                                 <td>IT system audit</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_5") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">6.6</td>
                                 <td>Data integrity</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_6"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_6") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">6.7</td>
                                 <td>Coverage of risk policy w.r.t. </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_7"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_7" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_7" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_7"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_7") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_7") %></textarea></td>
                              </tr>
                              <!-- sub start -->
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Operational risk management</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_8"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_8" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_8" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_8"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_8") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_8") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Market risk management</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_9"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_9" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_9" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_9"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_9") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_9") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Credit risk management</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_10"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_10" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_10" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_10"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_10") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_10") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Counterparty risk management </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_11"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_11" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_11" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_11"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_11") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_11") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Broker's review</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_12"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_12" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_12" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_12"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_12") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_12") %></textarea></td>
                              </tr>
                              <!-- sub end -->
                              <tr>
                                 <td class="text-center">6.8</td>
                                 <td>Employee Dealing guidelines</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_13"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_13" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_13" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_13"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_13") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_13") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">6.9</td>
                                 <td>Insurance Cover against risk</td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_sample_14"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_14" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="RiskManagementPolicy_complied_14" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_comment_14"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("RiskManagementPolicy_auditor_management_remark_14") == null ? "" : json_two.get("RiskManagementPolicy_auditor_management_remark_14") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">7.</td>
                                 <td colspan="5">Pattern of Investment</td>
                              </tr>
                              <tr>
                                 <td class="text-center">7.1</td>
                                 <td>Scheme A Tier I</td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_sample_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("PatternofInvestment_auditor_management_remark_1") == null ? "" : json_two.get("PatternofInvestment_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">7.2</td>
                                 <td>Scheme E Tier I and II</td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("PatternofInvestment_auditor_management_remark_2") == null ? "" : json_two.get("PatternofInvestment_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">7.3</td>
                                 <td>Scheme C Tier I and II</td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("PatternofInvestment_auditor_management_remark_3") == null ? "" : json_two.get("PatternofInvestment_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">7.4</td>
                                 <td>Scheme G Tier I and II</td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("PatternofInvestment_auditor_management_remark_4") == null ? "" : json_two.get("PatternofInvestment_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">7.5</td>
                                 <td>Scheme Tax -Tier -2</td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_sample_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="PatternofInvestment_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("PatternofInvestment_auditor_management_remark_5") == null ? "" : json_two.get("PatternofInvestment_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">8.</td>
                                 <td colspan="5">Front office dealing procedure</td>
                              </tr>
                              <tr>
                                 <td class="text-center">8.1</td>
                                 <td>Installation of voice recording machine</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Frontoffice_auditor_management_remark_1") == null ? "" : json_two.get("Frontoffice_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">8.2</td>
                                 <td>System and procedure of dealing in equity and debt (compliance with dealing room guidelines)</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Frontoffice_auditor_management_remark_2") == null ? "" : json_two.get("Frontoffice_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <!-- sub start -->
                              <tr>
                                 <td class="text-center">a.</td>
                                 <td>Review of CCTV recordings </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    &nbsp;&nbsp;<span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Frontoffice_auditor_management_remark_3") == null ? "" : json_two.get("Frontoffice_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">b.</td>
                                 <td>Review of off-premises execution of investments</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Frontoffice_auditor_management_remark_4") == null ? "" : json_two.get("Frontoffice_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <!-- sub end -->
                              <tr>
                                 <td class="text-center">8.3</td>
                                 <td>Deployment of as Dealer or Fund Manager </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_sample_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Frontoffice_auditor_management_remark_5") == null ? "" : json_two.get("Frontoffice_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">8.4</td>
                                 <td>Maintenance of records of investment justification.</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_sample_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Frontoffice_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Frontoffice_auditor_management_remark_6") == null ? "" : json_two.get("Frontoffice_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">9.</td>
                                 <td colspan="5">Back Office Procedure</td>
                              </tr>
                              <tr>
                                 <td class="text-center">9.1</td>
                                 <td>Deployment of separate officials</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Backoffice_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Backoffice_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Backoffice_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Backoffice_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Backoffice_auditor_auditor_management_remark_1") == null ? "" : json_two.get("Backoffice_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">9.2</td>
                                 <td>No password sharing between front office and back office</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Backoffice_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Backoffice_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Backoffice_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Backoffice_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Backoffice_auditor_auditor_management_remark_2") == null ? "" : json_two.get("Backoffice_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">9.3</td>
                                 <td>Accounting and Settlement of deals</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Backoffice_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Backoffice_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Backoffice_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Backoffice_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Backoffice_auditor_auditor_management_remark_3") == null ? "" : json_two.get("Backoffice_auditor_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">9.4</td>
                                 <td>Deal execution through STP</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Backoffice_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Backoffice_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Backoffice_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Backoffice_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Backoffice_auditor_auditor_management_remark_4") == null ? "" : json_two.get("Backoffice_auditor_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">10.</td>
                                 <td colspan="5">Exposure and Prudential Norms</td>
                              </tr>
                              <tr>
                                 <td class="text-center">10.1</td>
                                 <td>Investment in Promoter Group</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Exposure_auditor_auditor_management_remark_1") == null ? "" : json_two.get("Exposure_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">10.2</td>
                                 <td>Stipulated Norms-Investee Company</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Exposure_auditor_auditor_management_remark_2") == null ? "" : json_two.get("Exposure_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">10.3</td>
                                 <td>Stipulated Norms-Group Company</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Exposure_auditor_auditor_management_remark_3") == null ? "" : json_two.get("Exposure_auditor_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">10.4</td>
                                 <td>Stipulated Norms- Industry Group </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Exposure_auditor_auditor_management_remark_4") == null ? "" : json_two.get("Exposure_auditor_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">10.5</td>
                                 <td>Limit Monitoring through system</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_sample_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Exposure_auditor_auditor_management_remark_5") == null ? "" : json_two.get("Exposure_auditor_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">10.6</td>
                                 <td>Limit and Alert Management-Internal Norms and Regulatory Norms</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_sample_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Exposure_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Exposure_auditor_auditor_management_remark_6") == null ? "" : json_two.get("Exposure_auditor_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">11.</td>
                                 <td colspan="5">Custodian Controls</td>
                              </tr>
                              <tr>
                                 <td class="text-center">11.1</td>
                                 <td>Reconciliation of securities with SCHIL data</td>
                                 <td><textarea rows="3" class="boardMeeting" name="CustodianControls_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="CustodianControls_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="CustodianControls_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="CustodianControls_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("CustodianControls_auditor_auditor_management_remark_1") == null ? "" : json_two.get("CustodianControls_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">11.2</td>
                                 <td>Controls over physical holding</td>
                                 <td><textarea rows="3" class="boardMeeting" name="CustodianControls_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="CustodianControls_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="CustodianControls_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="CustodianControls_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("CustodianControls_auditor_auditor_management_remark_2") == null ? "" : json_two.get("CustodianControls_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">12.</td>
                                 <td colspan="5">Verification of Other investments</td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Reconciliation of Mutual Fund Holding with statement of account received from MF</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Otherinvestments_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Otherinvestments_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Otherinvestments_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Otherinvestments_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Otherinvestments_auditor_auditor_management_remark_1") == null ? "" : json_two.get("Otherinvestments_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center"></td>
                                 <td>Physical verification of Fixed Deposits receipts in respect of fixed deposits placed with banks.</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Otherinvestments_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Otherinvestments_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Otherinvestments_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Otherinvestments_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Otherinvestments_auditor_auditor_management_remark_2") == null ? "" : json_two.get("Otherinvestments_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">13.</td>
                                 <td colspan="5">Units Accounting</td>
                              </tr>
                              <tr>
                                 <td class="text-center">13.1</td>
                                 <td>Reconciliation of units with CRA on daily basis</td>
                                 <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="UnitsAccounting_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="UnitsAccounting_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("UnitsAccounting_auditor_auditor_management_remark_1") == null ? "" : json_two.get("UnitsAccounting_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">13.2</td>
                                 <td>Correctness of NAV applied</td>
                                 <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="UnitsAccounting_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="UnitsAccounting_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("UnitsAccounting_auditor_auditor_management_remark_2") == null ? "" : json_two.get("UnitsAccounting_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">13.3</td>
                                 <td>Appropriateness of accounting of unit premium</td>
                                 <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="UnitsAccounting_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="UnitsAccounting_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("UnitsAccounting_auditor_auditor_management_remark_3") == null ? "" : json_two.get("UnitsAccounting_auditor_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">14.</td>
                                 <td colspan="5">Investment Bank accounts</td>
                              </tr>
                              <tr>
                                 <td class="text-center">14.1</td>
                                 <td>Bank Reconciliation on daily basis</td>
                                 <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="BankReconciliation_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="BankReconciliation_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("BankReconciliation_auditor_auditor_management_remark_1") == null ? "" : json_two.get("BankReconciliation_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">14.2</td>
                                 <td>Identification of idle funds</td>
                                 <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="BankReconciliation_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="BankReconciliation_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("BankReconciliation_auditor_auditor_management_remark_2") == null ? "" : json_two.get("BankReconciliation_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">14.3</td>
                                 <td>Timely deployment of funds</td>
                                 <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="BankReconciliation_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="BankReconciliation_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("BankReconciliation_auditor_auditor_management_remark_3") == null ? "" : json_two.get("BankReconciliation_auditor_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">15.</td>
                                 <td colspan="5">Appointment of Brokers</td>
                              </tr>
                              <tr>
                                 <td class="text-center">15.1</td>
                                 <td>Guidelines of empanelment of brokers dated 08th April 2013.</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Brokers_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Brokers_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio" class="Brokers_complied_1" name="" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Brokers_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Brokers_auditor_auditor_management_remark_1") == null ? "" : json_two.get("Brokers_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">15.2</td>
                                 <td>Ceiling per broker as per IMA</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Brokers_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Brokers_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Brokers_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Brokers_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Brokers_auditor_auditor_management_remark_2") == null ? "" : json_two.get("Brokers_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">15.3</td>
                                 <td>Broker Review</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Brokers_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Brokers_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Brokers_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Brokers_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Brokers_auditor_auditor_management_remark_3") == null ? "" : json_two.get("Brokers_auditor_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">16.</td>
                                 <td colspan="5">Non-Performing Investments (PFRDA (Identification, Income Recognition and Provisioning of NPA) Guidance Note, 2013)</td>
                              </tr>
                              <tr>
                                 <td class="text-center">16.1</td>
                                 <td>Classification</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Non-Performing_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Non-Performing_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Non-Performing_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Non-Performing_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Non-Performing_auditor_auditor_management_remark_1") == null ? "" : json_two.get("Non-Performing_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">16.2</td>
                                 <td>Income Recognition</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Non-Performing_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Non-Performing_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Non-Performing_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Non-Performing_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Non-Performing_auditor_auditor_management_remark_2") == null ? "" : json_two.get("Non-Performing_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">16.3</td>
                                 <td>Provisions</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Non-Performing_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Non-Performing_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Non-Performing_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Non-Performing_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Non-Performing_auditor_auditor_management_remark_3") == null ? "" : json_two.get("Non-Performing_auditor_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">17.</td>
                                 <td colspan="5">Inter Scheme Transfer</td>
                              </tr>
                              <tr>
                                 <td class="text-center">17.1</td>
                                 <td>Traded Securities-Rates, Authorization and Documentation</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InterSchemeTransfer_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InterSchemeTransfer_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InterSchemeTransfer_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InterSchemeTransfer_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("InterSchemeTransfer_auditor_auditor_management_remark_1") == null ? "" : json_two.get("InterSchemeTransfer_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">17.2</td>
                                 <td>Non-Traded-Need/Justification of off market transactions, fairness of price and internal authorization</td>
                                 <td><textarea rows="3" class="boardMeeting" name="InterSchemeTransfer_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InterSchemeTransfer_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="InterSchemeTransfer_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="InterSchemeTransfer_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("InterSchemeTransfer_auditor_auditor_management_remark_2") == null ? "" : json_two.get("InterSchemeTransfer_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">18.</td>
                                 <td colspan="5">Investment Deals verification</td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.1</td>
                                 <td>Accuracy of calculation of investible surplus</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_1") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.2</td>
                                 <td>Review of daily sale and purchase register with supporting documents</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_2") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.3</td>
                                 <td>Journal Vouchers</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_3") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.4</td>
                                 <td>Investment Ledgers</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_4") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.5</td>
                                 <td>Verification of authorization, price and documentation</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_5") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.6</td>
                                 <td>Counterparty confirmation</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_6") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.7</td>
                                 <td>Contract Note from brokers</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_7"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_7" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_7" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_7"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_7") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_7") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.8</td>
                                 <td>Broker Bills</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_8"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_8" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_8" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_8"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_8") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_8") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.9</td>
                                 <td>Deal tickets</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_9"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_9" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_9" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_9"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_9") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_9") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.10</td>
                                 <td>DIS/DIP statement and intimation to the custodian</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_10"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_10" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_10" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_10"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_10") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_10") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">18.11</td>
                                 <td>Verification of timely and accurate capturing of trades</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_sample_11"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_11" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Dealsverification_complied_11" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_comment_11"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Dealsverification_auditor_auditor_management_remark_11") == null ? "" : json_two.get("Dealsverification_auditor_auditor_management_remark_11") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">19.</td>
                                 <td colspan="5">Accounting</td>
                              </tr>
                              <tr>
                                 <td class="text-center">19.1</td>
                                 <td>Compliance with accounting Standards</td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("AccountingStandards_auditor_auditor_management_remark_1") == null ? "" : json_two.get("AccountingStandards_auditor_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">19.2</td>
                                 <td>Adherence to accounting policy</td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("AccountingStandards_auditor_auditor_management_remark_2") == null ? "" : json_two.get("AccountingStandards_auditor_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">19.3</td>
                                 <td>Accounting of income receipt</td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("AccountingStandards_auditor_auditor_management_remark_3") == null ? "" : json_two.get("AccountingStandards_auditor_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">19.4</td>
                                 <td>Corporate action- bonus, rights, dividend, interest receivable</td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("AccountingStandards_auditor_auditor_management_remark_4") == null ? "" : json_two.get("AccountingStandards_auditor_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">19.5</td>
                                 <td>Reversal of brokerage on daily basis</td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_sample_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("AccountingStandards_auditor_auditor_management_remark_5") == null ? "" : json_two.get("AccountingStandards_auditor_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">19.6</td>
                                 <td>Outsourcing </td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_sample_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="AccountingStandards_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("AccountingStandards_auditor_auditor_management_remark_6") == null ? "" : json_two.get("AccountingStandards_auditor_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">20.</td>
                                 <td colspan="5">NAV</td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.1</td>
                                 <td>Valuation of Investments</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Nav_auditor_management_remark_1") == null ? "" : json_two.get("Nav_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.2</td>
                                 <td>NAV Calculation</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Nav_auditor_management_remark_2") == null ? "" : json_two.get("Nav_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.3</td>
                                 <td>Trade verification</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Nav_auditor_management_remark_3") == null ? "" : json_two.get("Nav_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.4</td>
                                 <td>Recording of Corporate Action on ex-date</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3"  disabled><%=json_two.get("Nav_auditor_management_remark_4") == null ? "" : json_two.get("Nav_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.5</td>
                                 <td>Creation of new security master</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Nav_auditor_management_remark_5") == null ? "" : json_two.get("Nav_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.6</td>
                                 <td>Unit capital booked in the system </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Nav_auditor_management_remark_6") == null ? "" : json_two.get("Nav_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.7</td>
                                 <td>Accrual of Coupons </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_7"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_7" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_7" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_7"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Nav_auditor_management_remark_7") == null ? "" : json_two.get("Nav_auditor_management_remark_7") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.8</td>
                                 <td>Scrutiny of expenses charged to the schemes on daily basis (i.e. Investment Management fees, custodian charges, SEBI charges and CCIL charges) </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_8"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_8" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_8" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_8"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Nav_auditor_management_remark_8") == null ? "" : json_two.get("Nav_auditor_management_remark_8") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">20.9</td>
                                 <td>Outsourcing </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_sample_9"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_9" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Nav_complied_9" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_comment_9"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Nav_auditor_management_remark_9") == null ? "" : json_two.get("Nav_auditor_management_remark_9") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">21.</td>
                                 <td colspan="5">Disclosure</td>
                              </tr>
                              <tr>
                                 <td class="text-center">21.1</td>
                                 <td>Daily NAV disclosure/uploading to CRA</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Disclosure_auditor_management_remark_1") == null ? "" : json_two.get("Disclosure_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">21.2</td>
                                 <td>NAV History </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Disclosure_auditor_management_remark_2") == null ? "" : json_two.get("Disclosure_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">21.3</td>
                                 <td>Portfolio Disclosure on monthly basis</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Disclosure_auditor_management_remark_3") == null ? "" : json_two.get("Disclosure_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">21.4</td>
                                 <td>Half Yearly Financial statement</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Disclosure_auditor_management_remark_4") == null ? "" : json_two.get("Disclosure_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">21.5</td>
                                 <td>Annual report</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_sample_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Disclosure_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Disclosure_auditor_management_remark_5") == null ? "" : json_two.get("Disclosure_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">22.</td>
                                 <td colspan="5">Periodical returns to Authority/ Trust</td>
                              </tr>
                              <tr>
                                 <td class="text-center">22.1</td>
                                 <td>Timely submission</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Periodical_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Periodical_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Periodical_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Periodical_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Periodical_auditor_management_remark_1") == null ? "" : json_two.get("Periodical_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">22.2</td>
                                 <td>Accuracy of data</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Periodical_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Periodical_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Periodical_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Periodical_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Periodical_auditor_management_remark_2") == null ? "" : json_two.get("Periodical_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">22.3</td>
                                 <td>Procedure of generation of data and report</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Periodical_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Periodical_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Periodical_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Periodical_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Periodical_auditor_management_remark_3") == null ? "" : json_two.get("Periodical_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">22.4</td>
                                 <td>Procedure for capturing Down grading of investment</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Periodical_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Periodical_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Periodical_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Periodical_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Periodical_auditor_management_remark_4") == null ? "" : json_two.get("Periodical_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">23.</td>
                                 <td colspan="5">Compliances</td>
                              </tr>
                              <tr>
                                 <td class="text-center">23.1</td>
                                 <td>Compliance to clauses of IMA</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Compliances_auditor_management_remark_1") == null ? "" : json_two.get("Compliances_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">23.2</td>
                                 <td>Compliance to Guidelines and Guidance note</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Compliances_auditor_management_remark_2") == null ? "" : json_two.get("Compliances_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">23.3</td>
                                 <td>Compliance to Internal Guidelines, Operational manual</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Compliances_auditor_management_remark_3") == null ? "" : json_two.get("Compliances_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">23.4</td>
                                 <td>Adequacy and efficacy of Internal Control system and procedures</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Compliances_auditor_management_remark_4") == null ? "" : json_two.get("Compliances_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">23.5</td>
                                 <td>Adequacy And Efficacy Of Operating Systems/Softwares Including Investment And Accounting Systems To Ensure</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Compliances_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Compliances_auditor_management_remark_4") == null ? "" : json_two.get("Compliances_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <!-- end --> 
                              <!-- start -->
                              <tr>
                                 <td class="text-center">24.</td>
                                 <td colspan="5">Internal Audit/ PFM Audit/ Scheme Audit</td>
                              </tr>
                              <tr>
                                 <td class="text-center">24.1</td>
                                 <td>To see the exceptions of audit and compliance there of</td>
                                 <td><textarea rows="3" class="boardMeeting" name="IPFMS_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="IPFMS_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="IPFMS_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="IPFMS_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("IPFMS_auditor_management_remark_1") == null ? "" : json_two.get("IPFMS_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">25.</td>
                                 <td colspan="5">Marketing and distribution</td>
                              </tr>
                              <tr>
                                 <td class="text-center">25.1</td>
                                 <td>Marketing and distribution setup/efforts</td>
                                 <td><textarea rows="3" class="boardMeeting" name="Marketing&Distribution_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Marketing&Distribution_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="Marketing&Distribution_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="Marketing&Distribution_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("Marketing&Distribution_auditor_management_remark_1") == null ? "" : json_two.get("Marketing&Distribution_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <!-- end -->
                              <!-- start -->
                              <tr>
                                 <td class="text-center">26.</td>
                                 <td colspan="5">Other regulatory compliances</td>
                              </tr>
                              <tr>
                                 <td class="text-center">26.1</td>
                                 <td>Minimum net worth requirement</td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_sample_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_1" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_1" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_comment_1"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("OtherRegulatory_auditor_management_remark_1") == null ? "" : json_two.get("OtherRegulatory_auditor_management_remark_1") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">26.2</td>
                                 <td>Appointment of compliance officer</td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_sample_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_2" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_2" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_comment_2"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("OtherRegulatory_auditor_management_remark_2") == null ? "" : json_two.get("OtherRegulatory_auditor_management_remark_2") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">26.3</td>
                                 <td>Payment of Annual Fees to PFRDA</td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_sample_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_3" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_3" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_comment_3"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("OtherRegulatory_auditor_management_remark_3") == null ? "" : json_two.get("OtherRegulatory_auditor_management_remark_3") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">26.4</td>
                                 <td>Whether the investment management fee is inclusive of brokerage but exclusive of custodian fee and applicable taxes. All other costs to be borne by the pension fund and not to be reimbursed or charged to the scheme by the pension fund.</td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_sample_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_4" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_4" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_comment_4"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("OtherRegulatory_auditor_management_remark_4") == null ? "" : json_two.get("OtherRegulatory_auditor_management_remark_4") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">26.5</td>
                                 <td>Whether the pension fund has loaded their charges (investment management fees) onto the net asset value on daily basis and the accrued charges (income) are collected by them at the end of each quarter, from the scheme bank accounts maintained with Trustee Bank after approval of the National Pension System Trust.</td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_sample_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_5" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_5" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_comment_5"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("OtherRegulatory_auditor_management_remark_5") == null ? "" : json_two.get("OtherRegulatory_auditor_management_remark_5") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">26.6</td>
                                 <td>Pension Funds shall not deal in speculative transactions</td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_sample_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_6" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_6" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_comment_6"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("OtherRegulatory_auditor_management_remark_6") == null ? "" : json_two.get("OtherRegulatory_auditor_management_remark_6") %></textarea></td>
                              </tr>
                              <tr>
                                 <td class="text-center">26.7</td>
                                 <td>Execution of agreements with the intermediaries under the NPS system before commencement of business</td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_sample_7"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td>
                                    <div class="d-flex">
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_7" value="Yes">
                                          <label class="form-check-label">&nbsp; Yes</label>
                                       </div>
                                       <div class="form-check form-check-inline">
                                          <input type="radio"  name="OtherRegulatory_complied_7" value="No">
                                          <label class="form-check-label">&nbsp; No</label>
                                       </div>
                                    </div>
                                    <span class="text-danger"></span>
                                 </td>
                                 <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_comment_7"></textarea>&nbsp;&nbsp;<span class="text-danger"></span></td>
                                 <td><textarea rows="3" disabled><%=json_two.get("OtherRegulatory_auditor_management_remark_7") == null ? "" : json_two.get("OtherRegulatory_auditor_management_remark_7") %></textarea></td>
                              </tr>
                              <!-- end -->  
                           </tbody>
                        </table>
                     </div>
                  </div>
               </div><br>
               
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexures for IAR circular </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile3">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureForIARCircular" name="AnnexureForIARCircular" accept=".xlsx"/>
                           </div>
                           &nbsp;&nbsp;<span class=" text-danger"></span>
                           <br>
                        </div>
                     </div>
                  </div>
               </div>
               
               <div class="row text-center" id="sub">
                  <div class="col-md-12">
                     <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
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
.cust-scroll .table-cont {
    overflow-x: visible !important;
}

.nps-pdf-gen .nps-input-box {
    margin-top: 0px !important;
}

.cust-scroll table{
	width: 100% !important;
}

#Internal_Audit_Report_table.table tbody td {
    padding: 5px !important;
    line-height: 1 !important;
}

#Internal_Audit_Report_table.table tbody td textarea {
    padding: 2px;
    margin: -1px;
}

.modal {
    display: none;
}

.modal-dialog {
	margin: 5% 40%;
}

.modal-content {
	width: 20vw;
}

.text-danger {
	border-color: red;
	font-weight: bold;
}

select {
    background-color: #E9F3FC;
    color: #111111;
    border-radius: 5px;
    padding: 5px 20px;
}

.boardMeeting {
	width: -webkit-fill-available;
}

table#Internal_Audit_Report_table tr td:nth-child(5) textarea{
    width: 200px;
}

table#Internal_Audit_Report_table tr td:nth-child(6) textarea{
    width: 200px;
}

/* .html2canvas-container{
	width: 3000px !important;
	height:3000px !important;
} */

</style>

<script type="text/javascript">

$(document).ready(function() {
	/* toastr.options = {
			
			  "debug": false,
			  "positionClass": "toast-bottom-right",
			  "onclick": null,
			  "fadeIn": 300,
			  "fadeOut": 1000,
			  "extendedTimeOut": 1000
	} */

	function validateForm(ArrayName){
	    var isValid = false;    
	    var classname = ArrayName;    
	    $('.' + classname + '').each(function (i, obj)  
	    {    
	        if (obj.value == '')  
	        {    
	            isValid = true;   
	            $(this).next().text("This field is required.");
	            return isValid;    
	        }
	        else
	        {
	        	$(this).next().text("");
	        }
	    }); 
	    
	    return isValid; 
	    
	}
	
	function validateRadio(){
		$('#Internal_Audit_Report_table tbody tr td input:radio').each(function () {
			var checked = $(this).is(':checked');
			
			if(!checked)
			{
                $(this).closest("div").parent().next().text("This field is required.");
                //$(this).closest("div").parent().find('span').text("This field is required.");
                return true;
			}
			else
			{
				console.log('Inside else of radio');
				$(this).closest("div").parent().next().text("");
				//$(this).parent().find('span').text("");
				//$(this).closest("div").parent().find('span').text("");
				return false;
			}
		});
	}
	
 	function validateFile(){
		var flag = false;
		
		bob:
		for(var i=0; i<3; i++){
			
			fl = false;
            var a =  $('.annexure-upload').get(i);
			if(a === undefined ){
				fl = true;
				break bob;
			}
			
			if(!fl){
			    var filevalue = $('.annexure-upload').get(i).id;
			    var that = $('#'+filevalue);
			    var Val = $('#'+filevalue).val();
			    console.log(Val);
			    
			    if(Val == '')
			    {
			    	$(that).closest("div.file-select").next().text("This field is required.");
			    	flag = true;
			    }
			    else
			    {
			    	$(that).closest("div.file-select").next().text("");
			    }
			}
		}
		
		return flag;
	} 
	
/* 	$(document).on('change', ".annexure-upload", function(){
		var fileInput = $(this).get(0);
		var filename = fileInput.files[0].name;
		$(this).closest("div").text(filename);
		$(this).closest("div.file-select").next().text("");
	}); */
	
	
	
	$('#certificate_pdf').bind('change', function () {
		  var filename = $("#certificate_pdf").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile1").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile1").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	
	$('#auditor_pdf').bind('change', function () {
		  var filename = $("#auditor_pdf").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile2").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile2").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	
	$('#annexureForIARCircular').bind('change', function () {
		  var filename = $("#annexureForIARCircular").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile3").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile3").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#iAR1_pdf').bind('change', function () {
		  var filename = $("#iAR1_pdf").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile_8").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_8").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	$('#iAR2_pdf').bind('change', function () {
		  var filename = $("#iAR2_pdf").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile_9").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_9").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	
		
	$("#pfm_internal_audit_report_form").on('submit', (function(e) {
		
		e.preventDefault();
		
		var validateInputField = false;
		var validateRadioButton = false;
		var validatefile = false;
		const ArrayNames = ["externalFields", "boardMeeting"]; // Operational
		
		ArrayNames.forEach(function (item, index) {
			validateInputField = validateForm(item);
			
			if(validateInputField)
			{
				validateInputField = true;
				return validateInputField;
			}
		});
		
		validateRadioButton = validateRadio();
 		validatefile = validateFile();
		
		if(!validateInputField && !validateRadioButton && !validatefile)
		{
			
			$("#canvasD .text-danger").text("");
			
			console.log("no empty fields");
			console.log("Inside ajax");
			$("#sub").addClass("hide");
			
			//var formdata = new FormData(document.getElementById("pfm_internal_audit_report_form"));
			var formdata = new FormData($("#pfm_internal_audit_report_form")[0]);
			$(".animaion").show();
			var htmlWidth = $("#canvasD").width();
			var htmlHeight = $("#canvasD").height();
			var pdfWidth = htmlWidth + (15 * 2);
			var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
			var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

			var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;

			html2canvas($("#canvasD")[0], {allowTaint: true}).then(function(canvas) {
				canvas.getContext('2d');

				var image = canvas.toDataURL("image/jpeg", 1.0);
				doc.addImage(image, 'JPEG', 15, 15, htmlWidth, htmlHeight);

				for (var i = 1; i <= pageCount; i++) {
					doc.addPage(pdfWidth, pdfHeight);
					doc.addImage(image, 'JPEG', 15, -(pdfHeight * i)+15, htmlWidth, htmlHeight+10);
				}

				console.log("doc.output('bloburl') ::: ", doc.output('arraybuffer'));
 				//doc.save("split-to-multi-page.pdf");
			    //window.open(doc.output('bloburl'));
			     
				var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
			     
				console.log("file::: ", file);
				
				formdata.append("qr_iar_pdf_file", file);
			
				$(".content").hide();
		        $(".animaion").show();
				
				$.ajax({
		            type: "POST",
		            enctype: 'multipart/form-data',
		            processData: false,
		            contentType: false,
		            cache: false,
		            url: '${hdfciarpfmResourceURL}',
		            data: formdata,
		            success: function(data) {
		            	$(".content").show();
		            	$(".animaion").hide();
		            	if(data == "Success") {
		            		$('#success_tic').modal('show');
		            	} else {
		            		console.log("Error Occured in ajax call");
		            		$('#output').html("An error occured while submitting the form. Please try again");
			            	$('#errorExcel').modal('show');
		            	}
		            },
		            error: function() {
		            	$(".animaion").hide();
		            	$('#output').html("An error occured while submitting the form. Please try again");
		            	$('#errorExcel').modal('show');
		            	
		            	console.log("Error Occured in ajax call");
		            },
		            complete: function(){
		            	$(".animaion").hide();
		            	if($("#sub").hasClass("hide")){
			            	$("#sub").removeClass("hide");
		            	}
		            	console.log("Complete");
			        }
		
		        });
		        //$(".animaion").hide(); //remove this code
			});
			$('#success_tic').on('hidden.bs.modal', function (e) {
				location.reload(); 
			})
			$('#errorExcel').on('hidden.bs.modal', function (e) {
				location.reload(); 
			})
			
		}
		else
		{
			console.log("empty fields");
		}
	}));
	console.log("${hdfciarpfmScrutinyResourceURL}")
	
});
</script>





