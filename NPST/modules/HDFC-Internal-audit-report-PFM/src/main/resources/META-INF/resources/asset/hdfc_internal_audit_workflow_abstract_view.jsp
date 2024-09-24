<%@page import="com.internal.audit.report.pfm.util.NPSUserPre_Populate_scrutinydata"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="com.internal.audit.report.pfm.util.DocumentUploadUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.Collections"%>
<%@page import="com.daily.average.service.service.HDFCInternal_Audit_ReportLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.HDFCInternal_Audit_Report"%>
<%@ include file="/init.jsp" %>
<div class="modal fade" id="errorExcel" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true" style="display: none; left:auto;">
  <div class="modal-dialog modal-sm ">
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
<% 

boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;

System.out.println("isNonNPSUser asset rendererrr:::::" + isNonNPSUser);

HDFCInternal_Audit_Report auditreport = Validator.isNotNull(request.getAttribute("auditreport")) ? (HDFCInternal_Audit_Report) request.getAttribute("auditreport") : HDFCInternal_Audit_ReportLocalServiceUtil.createHDFCInternal_Audit_Report(0);
System.out.println("auditreport: " + auditreport.toString());
JSONObject json_two = JSONFactoryUtil.createJSONObject();

	try {
		if(auditreport.getHdfc_audit_report_contex()!= null) {
			
			JSONObject json_one = JSONFactoryUtil.createJSONObject(auditreport.getHdfc_audit_report_contex());
			
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
						
		}
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	
	
	NPSUserPre_Populate_scrutinydata sd = new NPSUserPre_Populate_scrutinydata();
	sd.pre_populate_scrutiny_data(request);
	
	HDFCInternal_Audit_Report scrutinyAuditReport = Validator.isNotNull(request.getAttribute("hdfcIARScrutiny")) ? (HDFCInternal_Audit_Report) request.getAttribute("hdfcIARScrutiny") : HDFCInternal_Audit_ReportLocalServiceUtil.createHDFCInternal_Audit_Report(0);
	System.out.println("scrutinyAuditReport: " + scrutinyAuditReport.toString());
	JSONObject json_four = JSONFactoryUtil.createJSONObject();

		try {
			if(auditreport.getHdfc_audit_report_contex()!= null) {
				
				JSONObject json_three = JSONFactoryUtil.createJSONObject(auditreport.getHdfc_audit_report_contex());
				
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Board Meeting")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Investment Operational Manual/Procedure")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Investment Committee")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Investment Policy")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Risk Management Committee")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Risk Management Policy")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Pattern of Investment")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Front office dealing procedure")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Back Office Procedure")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Exposure & Prudential Norms")));
				
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Custodian Controls")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Verification of Other investments")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Units Accounting")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Investment Bank accounts")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Appointment of Brokers")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Non-Performing Investments")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Inter Scheme Transfer")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Investment Deals verification")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Accounting")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("NAV")));
				
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Disclosure")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Periodical returns to Authority/ Trust")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Compliances")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Internal Audit/ PFM Audit/ Scheme Audit")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Marketing & distribution")));
				json_four = DocumentUploadUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Other regulatory compliances")));
							
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");
	
   boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
	boolean isDocSigned = false;
	
	JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
	String fileEntryId = "0";
	JSONObject signature = JSONFactoryUtil.createJSONObject();
	
%>


<div class="nps-page-main nps-upload-report nps-statement-wrapper">
   
   <div>
		<span>File Version : </span>
		<% for(int i=0; i<urlArray.length();i++){
			JSONObject object = urlArray.getJSONObject(i);
			isDocSigned = Validator.isNotNull(object.getString("signature")); 
			fileEntryId = object.getString("fileEntryId");
			signature = JSONFactoryUtil.createJSONObject();
			if(isDocSigned){
				signature = JSONFactoryUtil.createJSONObject(object.getString("signature"));
			}
		%>
			<span class="file-preview-span" data-index='<%= i%>'> <%=object.getString("version")%>
				<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a> 
			</span>
			<% if(i != (urlArray.length()-1)){ %>
			 ,
			<% } %>
		<% } %>
	</div>

	<% if(isPfmSupervisor){ %>
		<div>
		<% if(!isDocSigned){ %>
			<button id="signFile" class="btn  btn-primary">Sign File</button>
		<% }else{ %>
			<button id="signFile" class="btn  btn-primary" disabled="disabled">Sign File</button>
			<h6>The report has been Authenticated</h6>
		<% } %>
	</div>
	<% }else{ %>
	<div >
		<% if(isDocSigned){ %>
			<h6 class="signedmsg">The report has been Authenticated</h6>
		<% } %>
	</div>
	<% } %>

   
   <div class="row">
   <div class="col-lg-12 col-md-12 col-sm-12 col-12">
      <div class="nps-report">
         <h4>Internal Audit Report</h4>
         <form class="form" id="pfm_internal_audit_report_scrutiny_form" action="#" method="post">
            <input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
            <input type="hidden" value="${reportMasterId }" name="reportMasterId" class="reportMasterId"/>
            <div class="row">
               <div class="col-md-6">
                  <div class="form-check form-check-inline">
                     <p>IAR Certificate by Auditor (PDF Upload)</p>
                  </div>
               </div>
               <div class="col-md-3">
                  <div class="form-check form-check-inline">
                     <a href="${empty certificate_pdf_file_url ? "javascript:void(0);" : certificate_pdf_file_url}" ${empty certificate_pdf_file_url ? "" : "download"}>Click here to download</a>
                  </div>
               </div>
               <div class="col-md-3">
                  <div class="form-group">
                     <textarea class="form-control boardMeeting"  placeholder="Remarks if any" name="certificate_pdf_remarks"></textarea>
                     &nbsp;&nbsp;<span class=" text-danger"></span>
                  </div>
               </div>
            </div>
            <!-- row one end -->
            <div class="row">
               <div class="col-md-6">
                  <div class="form-check form-check-inline">
                     <p>IAR File by Auditor (PDF Upload)</p>
                  </div>
               </div>
               <div class="col-md-3">
                  <div class="form-check form-check-inline">
                     <a href="${empty auditor_pdf_file_url ? "javascript:void(0);" : auditor_pdf_file_url}" ${empty auditor_pdf_file_url ? "" : "download"}>Click here to download</a>
                  </div>
               </div>
               <div class="col-md-3">
                  <div class="form-group">
                     <textarea class="form-control boardMeeting"  placeholder="Remarks if any" name="auditor_pdf_remarks"></textarea>
                     &nbsp;&nbsp;<span class=" text-danger"></span>
                  </div>
               </div>
            </div>
            <!-- row one end -->
            <div class="row">
               <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                  <div>
                     <p class="mb-0">Summary of Internal Audit Report :</p>
                  </div>
               </div>
            </div>
            <br>
            <div class="statement-wrapper">
               <div id="table" class="table-editable">
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
                              <td><textarea rows="3" name="boardMeeting_sample_1" readonly><%=json_two.get("boardMeeting_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="boardMeeting_complied_1" value="Yes" disabled
                                          <%=json_two.get("boardMeeting_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio" name="boardMeeting_complied_1" value="No" disabled
                                          <%=json_two.get("boardMeeting_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="boardMeeting_auditor_comment_1" readonly><%=json_two.get("boardMeeting_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="boardMeeting_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("boardMeeting_management_remark_1") == null ? "" : json_four.get("boardMeeting_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">1.2</td>
                              <td>Frequency of meeting</td>
                              <td><textarea rows="3"   name="boardMeeting_sample_2" readonly><%=json_two.get("boardMeeting_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio" name="boardMeeting_complied_2" value="Yes" disabled
                                          <%=json_two.get("boardMeeting_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio" name="boardMeeting_complied_2" value="No" disabled
                                          <%=json_two.get("boardMeeting_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="boardMeeting_auditor_comment_2" readonly><%=json_two.get("boardMeeting_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="boardMeeting_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("boardMeeting_management_remark_2") == null ? "" : json_four.get("boardMeeting_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">1.3</td>
                              <td>Minutes of the meeting</td>
                              <td><textarea rows="3"  name="boardMeeting_sample_3" readonly><%=json_two.get("boardMeeting_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="boardMeeting_complied_3" value="Yes" disabled
                                          <%=json_two.get("boardMeeting_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="boardMeeting_complied_3" value="No" disabled
                                          <%=json_two.get("boardMeeting_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="boardMeeting_auditor_comment_3" readonly><%=json_two.get("boardMeeting_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="boardMeeting_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("boardMeeting_management_remark_3") == null ? "" : json_four.get("boardMeeting_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">1.4</td>
                              <td>Recommendation</td>
                              <td><textarea rows="3"  name="boardMeeting_sample_4" readonly><%=json_two.get("boardMeeting_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="boardMeeting_complied_4" value="Yes" disabled
                                          <%=json_two.get("boardMeeting_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="boardMeeting_complied_4" value="No" disabled
                                          <%=json_two.get("boardMeeting_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="boardMeeting_auditor_comment_4" readonly><%=json_two.get("boardMeeting_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="boardMeeting_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("boardMeeting_management_remark_4") == null ? "" : json_four.get("boardMeeting_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">1.5</td>
                              <td>Delegation of authority</td>
                              <td><textarea rows="3"  name="boardMeeting_sample_5" readonly><%=json_two.get("boardMeeting_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="boardMeeting_complied_5" value="Yes" disabled
                                          <%=json_two.get("boardMeeting_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="boardMeeting_complied_5" value="No" disabled
                                          <%=json_two.get("boardMeeting_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="boardMeeting_auditor_comment_5" readonly><%=json_two.get("boardMeeting_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="boardMeeting_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("boardMeeting_management_remark_5") == null ? "" : json_four.get("boardMeeting_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Operational_sample_1" readonly><%=json_two.get("Operational_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_1" value="Yes" disabled
                                          <%=json_two.get("Operational_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_1" value="No" disabled
                                          <%=json_two.get("Operational_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Operational_auditor_comment_1" readonly><%=json_two.get("Operational_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Operational_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Operational_management_remark_1") == null ? "" : json_four.get("Operational_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">2.2</td>
                              <td>Amendments and approval</td>
                              <td><textarea rows="3"  name="Operational_sample_2" readonly><%=json_two.get("Operational_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_2" value="Yes" disabled
                                          <%=json_two.get("Operational_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_2" value="No" disabled
                                          <%=json_two.get("Operational_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Operational_auditor_comment_2" readonly><%=json_two.get("Operational_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Operational_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Operational_management_remark_2") == null ? "" : json_four.get("Operational_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">2.3</td>
                              <td>Frequency of review</td>
                              <td><textarea rows="3"  name="Operational_sample_3" readonly><%=json_two.get("Operational_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_3" value="Yes" disabled
                                          <%=json_two.get("Operational_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_3" value="No" disabled
                                          <%=json_two.get("Operational_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Operational_auditor_comment_3" readonly><%=json_two.get("Operational_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Operational_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Operational_management_remark_3") == null ? "" : json_four.get("Operational_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">2.4</td>
                              <td>Coverage</td>
                              <td><textarea rows="3"  name="Operational_sample_4" readonly><%=json_two.get("Operational_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_4" value="Yes" disabled
                                          <%=json_two.get("Operational_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_4" value="No" disabled
                                          <%=json_two.get("Operational_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Operational_auditor_comment_4" readonly><%=json_two.get("Operational_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Operational_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Operational_management_remark_4") == null ? "" : json_four.get("Operational_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <!-- sub start--> 
                           <tr>
                              <td class="text-center"></td>
                              <td>Procedure for Credit appraisal and Market risk</td>
                              <td><textarea rows="3"  name="Operational_sample_5" readonly><%=json_two.get("Operational_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_5" value="Yes" disabled
                                          <%=json_two.get("Operational_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_5" value="No" disabled
                                          <%=json_two.get("Operational_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Operational_auditor_comment_5" readonly><%=json_two.get("Operational_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Operational_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Operational_management_remark_5") == null ? "" : json_four.get("Operational_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Procedure for security documents execution</td>
                              <td><textarea rows="3"  name="Operational_sample_6" readonly><%=json_two.get("Operational_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_6" value="Yes" disabled
                                          <%=json_two.get("Operational_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_6" value="No" disabled
                                          <%=json_two.get("Operational_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Operational_auditor_comment_6" readonly><%=json_two.get("Operational_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Operational_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Operational_management_remark_6") == null ? "" : json_four.get("Operational_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Income recognition policy – accruals</td>
                              <td><textarea rows="3"  name="Operational_sample_7" readonly><%=json_two.get("Operational_sample_7") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_7" value="Yes" disabled
                                          <%=json_two.get("Operational_complied_7").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_7" value="No" disabled
                                          <%=json_two.get("Operational_complied_7").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Operational_auditor_comment_7" readonly><%=json_two.get("Operational_auditor_comment_7") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Operational_management_remark_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Operational_management_remark_7") == null ? "" : json_four.get("Operational_management_remark_7") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <!-- sub end -->
                           <tr>
                              <td class="text-center">2.5</td>
                              <td>Periodic Credit review</td>
                              <td><textarea rows="3"  name="Operational_sample_8"  readonly><%=json_two.get("Operational_sample_8") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_8" value="Yes" disabled
                                          <%=json_two.get("Operational_complied_8").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Operational_complied_8" value="No" disabled
                                          <%=json_two.get("Operational_complied_8").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Operational_auditor_comment_8"  readonly><%=json_two.get("Operational_auditor_comment_8") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Operational_management_remark_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Operational_management_remark_8") == null ? "" : json_four.get("Operational_management_remark_8") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Investment_sample_1" readonly><%=json_two.get("Investment_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_1" value="Yes" disabled
                                          <%=json_two.get("Investment_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_1" value="No" disabled
                                          <%=json_two.get("Investment_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Investment_auditor_comment_1" readonly><%=json_two.get("Investment_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Investment_auditor_management_remark_1") == null ? "" : json_four.get("Investment_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">3.2</td>
                              <td>Frequency of meeting</td>
                              <td><textarea rows="3"  name="Investment_sample_2" readonly><%=json_two.get("Investment_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_2" value="Yes" disabled
                                          <%=json_two.get("Investment_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_2" value="No" disabled
                                          <%=json_two.get("Investment_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Investment_auditor_comment_2" readonly><%=json_two.get("Investment_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Investment_auditor_management_remark_2") == null ? "" : json_four.get("Investment_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">3.3</td>
                              <td>Minutes of the meeting</td>
                              <td><textarea rows="3"  name="Investment_sample_3" readonly><%=json_two.get("Investment_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_3" value="Yes" disabled
                                          <%=json_two.get("Investment_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_3" value="No" disabled
                                          <%=json_two.get("Investment_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Investment_auditor_comment_3" readonly><%=json_two.get("Investment_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Investment_auditor_management_remark_3") == null ? "" : json_four.get("Investment_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">3.4</td>
                              <td>Review of Investment policy</td>
                              <td><textarea rows="3"  name="Investment_sample_4" readonly><%=json_two.get("Investment_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_4" value="Yes" disabled
                                          <%=json_two.get("Investment_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_4" value="No" disabled
                                          <%=json_two.get("Investment_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Investment_auditor_comment_4" readonly><%=json_two.get("Investment_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Investment_auditor_management_remark_4") == null ? "" : json_four.get("Investment_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">3.5</td>
                              <td>Recommendation</td>
                              <td><textarea rows="3"  name="Investment_sample_5" readonly><%=json_two.get("Investment_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_5" value="Yes" disabled
                                          <%=json_two.get("Investment_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_5" value="No" disabled
                                          <%=json_two.get("Investment_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Investment_auditor_comment_5" readonly><%=json_two.get("Investment_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Investment_auditor_management_remark_5") == null ? "" : json_four.get("Investment_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">3.6</td>
                              <td>Delegation of authority</td>
                              <td><textarea rows="3"  name="Investment_sample_6" readonly><%=json_two.get("Investment_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_6" value="Yes" disabled
                                          <%=json_two.get("Investment_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Investment_complied_6" value="No" disabled
                                          <%=json_two.get("Investment_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Investment_auditor_comment_6" readonly><%=json_two.get("Investment_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Investment_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Investment_auditor_management_remark_6") == null ? "" : json_four.get("Investment_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_1" readonly><%=json_two.get("InvestmentPolicy_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_1" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_1" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_1" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_1") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">4.2</td>
                              <td>Valuation Principles</td>
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_2" readonly><%=json_two.get("InvestmentPolicy_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_2" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_2" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_2" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_2") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">4.3</td>
                              <td>Adherence to regulatory guidelines for valuation</td>
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_3" readonly><%=json_two.get("InvestmentPolicy_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_3" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_3" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_3" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_3") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">4.4</td>
                              <td>Frequency of review</td>
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_4"  readonly><%=json_two.get("InvestmentPolicy_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_4" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_4" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_4" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_4") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">4.5</td>
                              <td>Coverage of investment policy w.r.t.</td>
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_5" readonly><%=json_two.get("InvestmentPolicy_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_5" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_5" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_5" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_5") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <!-- sub start--> 
                           <tr>
                              <td class="text-center"></td>
                              <td>Liquidity</td>
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_6" readonly><%=json_two.get("InvestmentPolicy_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_6" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_6" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_6" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_6") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Prudential norms</td>
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_7" readonly><%=json_two.get("InvestmentPolicy_sample_7") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_7" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_7").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_7" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_7").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_7" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_7") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_7") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_7") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Exposure limits</td>
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_8" readonly><%=json_two.get("InvestmentPolicy_sample_8") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_8" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_8").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_8" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_complied_8").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_8" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_8") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_8") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_8") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Stop Loss limits in securities trading</td>
                              <td><textarea rows="3"  name="InvestmentPolicy_sample_9" readonly><%=json_two.get("InvestmentPolicy_sample_9") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_9" value="Yes" disabled
                                          <%=json_two.get("InvestmentPolicy_sample_9").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InvestmentPolicy_complied_9" value="No" disabled
                                          <%=json_two.get("InvestmentPolicy_sample_9").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InvestmentPolicy_auditor_comment_9" readonly><%=json_two.get("InvestmentPolicy_auditor_comment_9") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InvestmentPolicy_auditor_management_remark_9" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InvestmentPolicy_auditor_management_remark_9") == null ? "" : json_four.get("InvestmentPolicy_auditor_management_remark_9") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="RiskManagement_sample_1" readonly><%=json_two.get("RiskManagement_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_1" value="Yes" disabled
                                          <%=json_two.get("RiskManagement_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_1" value="No" disabled
                                          <%=json_two.get("RiskManagement_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagement_auditor_comment_1" readonly><%=json_two.get("RiskManagement_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagement_auditor_management_remark_1") == null ? "" : json_four.get("RiskManagement_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">5.2</td>
                              <td>Frequency of meeting</td>
                              <td><textarea rows="3"  name="RiskManagement_sample_2" readonly><%=json_two.get("RiskManagement_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_2" value="Yes" disabled
                                          <%=json_two.get("RiskManagement_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_2" value="No" disabled
                                          <%=json_two.get("RiskManagement_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagement_auditor_comment_2" readonly><%=json_two.get("RiskManagement_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagement_auditor_management_remark_2") == null ? "" : json_four.get("RiskManagement_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">5.3</td>
                              <td>Minutes of the meeting</td>
                              <td><textarea rows="3"  name="RiskManagement_sample_3" readonly><%=json_two.get("RiskManagement_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_3" value="Yes" disabled
                                          <%=json_two.get("RiskManagement_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_3" value="No" disabled
                                          <%=json_two.get("RiskManagement_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagement_auditor_comment_3" readonly><%=json_two.get("RiskManagement_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagement_auditor_management_remark_3") == null ? "" : json_four.get("RiskManagement_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">5.4</td>
                              <td>Review of Risk Policy</td>
                              <td><textarea rows="3"  name="RiskManagement_sample_4" readonly><%=json_two.get("RiskManagement_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_4" value="Yes" disabled
                                          <%=json_two.get("RiskManagement_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_4" value="No" disabled
                                          <%=json_two.get("RiskManagement_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagement_auditor_comment_4" readonly><%=json_two.get("RiskManagement_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagement_auditor_management_remark_4") == null ? "" : json_four.get("RiskManagement_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">5.5</td>
                              <td>Recommendation</td>
                              <td><textarea rows="3"  name="RiskManagement_sample_5" readonly><%=json_two.get("RiskManagement_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_5" value="Yes" disabled
                                          <%=json_two.get("RiskManagement_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagement_complied_5" value="No" disabled
                                          <%=json_two.get("RiskManagement_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagement_auditor_comment_5" readonly><%=json_two.get("RiskManagement_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagement_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagement_auditor_management_remark_5") == null ? "" : json_four.get("RiskManagement_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_1" readonly><%=json_two.get("RiskManagementPolicy_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_1" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_1" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_1" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_1") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">6.2</td>
                              <td>Frequency of review</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_2" readonly><%=json_two.get("RiskManagementPolicy_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_2" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_2" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_2" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_2") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">6.3</td>
                              <td>Disaster Recovery strategy</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_3" readonly><%=json_two.get("RiskManagementPolicy_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_3" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_3" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_3" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_3") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">6.4</td>
                              <td>Business Continuity Plan</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_4" readonly><%=json_two.get("RiskManagementPolicy_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_4" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_4" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_4" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_4") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">6.5</td>
                              <td>IT system audit</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_5" readonly><%=json_two.get("RiskManagementPolicy_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_5" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_5" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_5" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_5") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">6.6</td>
                              <td>Data integrity</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_6" readonly><%=json_two.get("RiskManagementPolicy_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_6" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_6" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_6" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_6") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">6.7</td>
                              <td>Coverage of risk policy w.r.t. </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_7" readonly><%=json_two.get("RiskManagementPolicy_sample_7") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_7" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_7").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_7" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_7").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_7" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_7") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_7") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_7") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <!-- sub start -->
                           <tr>
                              <td class="text-center"></td>
                              <td>Operational risk management</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_8" readonly><%=json_two.get("RiskManagementPolicy_sample_8") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_8" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_8").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_8" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_8").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_8" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_8") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_8") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_8") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Market risk management</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_9" readonly><%=json_two.get("RiskManagementPolicy_sample_9") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_9" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_9").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_9" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_9").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_9" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_9") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_9" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_9") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_9") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Credit risk management</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_10" readonly><%=json_two.get("RiskManagementPolicy_sample_10") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_10" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_10").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_10" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_10").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_10" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_10") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_10" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_10") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_10") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Counterparty risk management </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_11" readonly><%=json_two.get("RiskManagementPolicy_sample_11") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_11" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_11").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_11" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_11").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_11" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_11") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_11" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_11") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_11") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Broker’s review</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_12"  readonly><%=json_two.get("RiskManagementPolicy_sample_12") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_12" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_12").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_12" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_12").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_12"  readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_12") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_12" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_12") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_12") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <!-- sub end -->
                           <tr>
                              <td class="text-center">6.8</td>
                              <td>Employee Dealing guidelines</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_13" readonly><%=json_two.get("RiskManagementPolicy_sample_13") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_13" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_13").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_13" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_13").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_13" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_13") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_13" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_13") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_13") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">6.9</td>
                              <td>Insurance Cover against risk</td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_sample_14" readonly><%=json_two.get("RiskManagementPolicy_sample_14") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_14" value="Yes" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_14").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="RiskManagementPolicy_complied_14" value="No" disabled
                                          <%=json_two.get("RiskManagementPolicy_complied_14").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="RiskManagementPolicy_auditor_comment_14" readonly><%=json_two.get("RiskManagementPolicy_auditor_comment_14") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="RiskManagementPolicy_auditor_management_remark_14" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("RiskManagementPolicy_auditor_management_remark_14") == null ? "" : json_four.get("RiskManagementPolicy_auditor_management_remark_14") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="PatternofInvestment_sample_1"  readonly><%=json_two.get("PatternofInvestment_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_1" value="Yes" disabled
                                          <%=json_two.get("PatternofInvestment_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_1" value="No" disabled
                                          <%=json_two.get("PatternofInvestment_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="PatternofInvestment_auditor_comment_1"  readonly><%=json_two.get("PatternofInvestment_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("PatternofInvestment_auditor_management_remark_1") == null ? "" : json_four.get("PatternofInvestment_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">7.2</td>
                              <td>Scheme E Tier I and II</td>
                              <td><textarea rows="3"  name="PatternofInvestment_sample_2"  readonly><%=json_two.get("PatternofInvestment_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_2" value="Yes" disabled
                                          <%=json_two.get("PatternofInvestment_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_2" value="No" disabled
                                          <%=json_two.get("PatternofInvestment_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="PatternofInvestment_auditor_comment_2" readonly><%=json_two.get("PatternofInvestment_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("PatternofInvestment_auditor_management_remark_2") == null ? "" : json_four.get("PatternofInvestment_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">7.3</td>
                              <td>Scheme C Tier I and II</td>
                              <td><textarea rows="3"  name="PatternofInvestment_sample_3" readonly><%=json_two.get("PatternofInvestment_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_3" value="Yes" disabled
                                          <%=json_two.get("PatternofInvestment_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_3" value="No" disabled
                                          <%=json_two.get("PatternofInvestment_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="PatternofInvestment_auditor_comment_3" readonly><%=json_two.get("PatternofInvestment_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("PatternofInvestment_auditor_management_remark_3") == null ? "" : json_four.get("PatternofInvestment_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">7.4</td>
                              <td>Scheme G Tier I and II</td>
                              <td><textarea rows="3"  name="PatternofInvestment_sample_4" readonly><%=json_two.get("PatternofInvestment_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_4" value="Yes" disabled
                                          <%=json_two.get("PatternofInvestment_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_4" value="No" disabled
                                          <%=json_two.get("PatternofInvestment_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="PatternofInvestment_auditor_comment_4" readonly><%=json_two.get("PatternofInvestment_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("PatternofInvestment_auditor_management_remark_4") == null ? "" : json_four.get("PatternofInvestment_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">7.5</td>
                              <td>Scheme Tax -Tier -2</td>
                              <td><textarea rows="3"  name="PatternofInvestment_sample_5" readonly><%=json_two.get("PatternofInvestment_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_5" value="Yes" disabled
                                          <%=json_two.get("PatternofInvestment_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="PatternofInvestment_complied_5" value="No" disabled
                                          <%=json_two.get("PatternofInvestment_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="PatternofInvestment_auditor_comment_5" readonly><%=json_two.get("PatternofInvestment_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="PatternofInvestment_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("PatternofInvestment_auditor_management_remark_5") == null ? "" : json_four.get("PatternofInvestment_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Frontoffice_sample_1" readonly><%=json_two.get("Frontoffice_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_1" value="Yes" disabled
                                          <%=json_two.get("Frontoffice_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_1" value="No" disabled
                                          <%=json_two.get("Frontoffice_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Frontoffice_auditor_comment_1" readonly><%=json_two.get("Frontoffice_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Frontoffice_auditor_management_remark_1") == null ? "" : json_four.get("Frontoffice_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">8.2</td>
                              <td>System and procedure of dealing in equity and debt (compliance with dealing room guidelines)</td>
                              <td><textarea rows="3"  name="Frontoffice_sample_2" readonly><%=json_two.get("Frontoffice_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_2" value="Yes" disabled
                                          <%=json_two.get("Frontoffice_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_2" value="No" disabled
                                          <%=json_two.get("Frontoffice_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Frontoffice_auditor_comment_2" readonly><%=json_two.get("Frontoffice_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Frontoffice_auditor_management_remark_2") == null ? "" : json_four.get("Frontoffice_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <!-- sub start -->
                           <tr>
                              <td class="text-center">a.</td>
                              <td>Review of CCTV recordings </td>
                              <td><textarea rows="3"  name="Frontoffice_sample_3" readonly><%=json_two.get("Frontoffice_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_3" value="Yes" disabled
                                          <%=json_two.get("Frontoffice_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_3" value="No" disabled
                                          <%=json_two.get("Frontoffice_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 &nbsp;&nbsp;<span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Frontoffice_auditor_comment_3" readonly><%=json_two.get("Frontoffice_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Frontoffice_auditor_management_remark_3") == null ? "" : json_four.get("Frontoffice_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">b.</td>
                              <td>Review of off-premises execution of investments</td>
                              <td><textarea rows="3"  name="Frontoffice_sample_4" readonly><%=json_two.get("Frontoffice_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_4" value="Yes" disabled
                                          <%=json_two.get("Frontoffice_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_4" value="No" disabled
                                          <%=json_two.get("Frontoffice_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Frontoffice_auditor_comment_4" readonly><%=json_two.get("Frontoffice_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Frontoffice_auditor_management_remark_4") == null ? "" : json_four.get("Frontoffice_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <!-- sub end -->
                           <tr>
                              <td class="text-center">8.3</td>
                              <td>Deployment of as Dealer or Fund Manager </td>
                              <td><textarea rows="3"  name="Frontoffice_sample_5" readonly><%=json_two.get("Frontoffice_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_5" value="Yes" disabled
                                          <%=json_two.get("Frontoffice_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_5" value="No" disabled
                                          <%=json_two.get("Frontoffice_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Frontoffice_auditor_comment_5" readonly><%=json_two.get("Frontoffice_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Frontoffice_auditor_management_remark_5") == null ? "" : json_four.get("Frontoffice_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">8.4</td>
                              <td>Maintenance of records of investment justification.</td>
                              <td><textarea rows="3"  name="Frontoffice_sample_6" readonly><%=json_two.get("Frontoffice_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_6" value="Yes" disabled
                                          <%=json_two.get("Frontoffice_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Frontoffice_complied_6" value="No" disabled
                                          <%=json_two.get("Frontoffice_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Frontoffice_auditor_comment_6" readonly><%=json_two.get("Frontoffice_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Frontoffice_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Frontoffice_auditor_management_remark_6") == null ? "" : json_four.get("Frontoffice_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Backoffice_sample_1" readonly><%=json_two.get("Backoffice_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Backoffice_complied_1" value="Yes" disabled
                                          <%=json_two.get("Backoffice_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Backoffice_complied_1" value="No" disabled
                                          <%=json_two.get("Backoffice_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Backoffice_auditor_comment_1" readonly><%=json_two.get("Backoffice_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Backoffice_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Backoffice_auditor_auditor_management_remark_1") == null ? "" : json_four.get("Backoffice_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">9.2</td>
                              <td>No password sharing between front office and back office</td>
                              <td><textarea rows="3"  name="Backoffice_sample_2" readonly><%=json_two.get("Backoffice_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Backoffice_complied_2" value="Yes" disabled
                                          <%=json_two.get("Backoffice_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Backoffice_complied_2" value="No" disabled
                                          <%=json_two.get("Backoffice_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Backoffice_auditor_comment_2" readonly><%=json_two.get("Backoffice_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Backoffice_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Backoffice_auditor_auditor_management_remark_2") == null ? "" : json_four.get("Backoffice_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">9.3</td>
                              <td>Accounting and Settlement of deals</td>
                              <td><textarea rows="3"  name="Backoffice_sample_3" readonly><%=json_two.get("Backoffice_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Backoffice_complied_3" value="Yes" disabled
                                          <%=json_two.get("Backoffice_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Backoffice_complied_3" value="No" disabled
                                          <%=json_two.get("Backoffice_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Backoffice_auditor_comment_3" readonly><%=json_two.get("Backoffice_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Backoffice_auditor_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Backoffice_auditor_auditor_management_remark_3") == null ? "" : json_four.get("Backoffice_auditor_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">9.4</td>
                              <td>Deal execution through STP</td>
                              <td><textarea rows="3"  name="Backoffice_sample_4" readonly><%=json_two.get("Backoffice_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Backoffice_complied_4" value="Yes" disabled
                                          <%=json_two.get("Backoffice_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Backoffice_complied_4" value="No" disabled
                                          <%=json_two.get("Backoffice_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Backoffice_auditor_comment_4" readonly><%=json_two.get("Backoffice_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Backoffice_auditor_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Backoffice_auditor_auditor_management_remark_4") == null ? "" : json_four.get("Backoffice_auditor_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Exposure_sample_1" readonly><%=json_two.get("Exposure_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_1" value="Yes" disabled
                                          <%=json_two.get("Exposure_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_1" value="No" disabled
                                          <%=json_two.get("Exposure_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Exposure_auditor_comment_1" readonly><%=json_two.get("Exposure_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Exposure_auditor_auditor_management_remark_1") == null ? "" : json_four.get("Exposure_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">10.2</td>
                              <td>Stipulated Norms-Investee Company</td>
                              <td><textarea rows="3"  name="Exposure_sample_2" readonly><%=json_two.get("Exposure_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_2" value="Yes" disabled
                                          <%=json_two.get("Exposure_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_2" value="No" disabled
                                          <%=json_two.get("Exposure_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Exposure_auditor_comment_2" readonly><%=json_two.get("Exposure_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Exposure_auditor_auditor_management_remark_2") == null ? "" : json_four.get("Exposure_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">10.3</td>
                              <td>Stipulated Norms-Group Company</td>
                              <td><textarea rows="3"  name="Exposure_sample_3" readonly><%=json_two.get("Exposure_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_3" value="Yes" disabled
                                          <%=json_two.get("Exposure_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_3" value="No" disabled
                                          <%=json_two.get("Exposure_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Exposure_auditor_comment_3" readonly><%=json_two.get("Exposure_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Exposure_auditor_auditor_management_remark_3") == null ? "" : json_four.get("Exposure_auditor_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">10.4</td>
                              <td>Stipulated Norms- Industry Group </td>
                              <td><textarea rows="3"  name="Exposure_sample_4" readonly><%=json_two.get("Exposure_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_4" value="Yes" disabled
                                          <%=json_two.get("Exposure_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_4" value="No" disabled
                                          <%=json_two.get("Exposure_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Exposure_auditor_comment_4" readonly><%=json_two.get("Exposure_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Exposure_auditor_auditor_management_remark_4") == null ? "" : json_four.get("Exposure_auditor_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">10.5</td>
                              <td>Limit Monitoring through system</td>
                              <td><textarea rows="3"  name="Exposure_sample_5" readonly><%=json_two.get("Exposure_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_5" value="Yes" disabled
                                          <%=json_two.get("Exposure_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_5" value="No" disabled
                                          <%=json_two.get("Exposure_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Exposure_auditor_comment_5"  readonly><%=json_two.get("Exposure_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Exposure_auditor_auditor_management_remark_5") == null ? "" : json_four.get("Exposure_auditor_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">10.6</td>
                              <td>Limit and Alert Management-Internal Norms and Regulatory Norms</td>
                              <td><textarea rows="3"  name="Exposure_sample_6" readonly><%=json_two.get("Exposure_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_6" value="Yes" disabled
                                          <%=json_two.get("Exposure_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Exposure_complied_6" value="No" disabled
                                          <%=json_two.get("Exposure_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Exposure_auditor_comment_6" readonly><%=json_two.get("Exposure_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Exposure_auditor_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Exposure_auditor_auditor_management_remark_6") == null ? "" : json_four.get("Exposure_auditor_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="CustodianControls_sample_1" readonly><%=json_two.get("CustodianControls_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="CustodianControls_complied_1" value="Yes" disabled
                                          <%=json_two.get("CustodianControls_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="CustodianControls_complied_1" value="No" disabled
                                          <%=json_two.get("CustodianControls_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="CustodianControls_auditor_comment_1" readonly><%=json_two.get("CustodianControls_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="CustodianControls_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("CustodianControls_auditor_auditor_management_remark_1") == null ? "" : json_four.get("CustodianControls_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">11.2</td>
                              <td>Controls over physical holding</td>
                              <td><textarea rows="3"  name="CustodianControls_sample_2" readonly><%=json_two.get("CustodianControls_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="CustodianControls_complied_2" value="Yes" disabled
                                          <%=json_two.get("CustodianControls_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="CustodianControls_complied_2" value="No" disabled
                                          <%=json_two.get("CustodianControls_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="CustodianControls_auditor_comment_2" readonly><%=json_two.get("CustodianControls_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="CustodianControls_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("CustodianControls_auditor_auditor_management_remark_2") == null ? "" : json_four.get("CustodianControls_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Otherinvestments_sample_1" readonly><%=json_two.get("Otherinvestments_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Otherinvestments_complied_1" value="Yes" disabled
                                          <%=json_two.get("Otherinvestments_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Otherinvestments_complied_1" value="No" disabled
                                          <%=json_two.get("Otherinvestments_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Otherinvestments_auditor_comment_1" readonly><%=json_two.get("Otherinvestments_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Otherinvestments_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Otherinvestments_auditor_auditor_management_remark_1") == null ? "" : json_four.get("Otherinvestments_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center"></td>
                              <td>Physical verification of Fixed Deposits receipts in respect of fixed deposits placed with banks.</td>
                              <td><textarea rows="3"  name="Otherinvestments_sample_2" readonly><%=json_two.get("Otherinvestments_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Otherinvestments_complied_2" value="Yes" disabled
                                          <%=json_two.get("Otherinvestments_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Otherinvestments_complied_2" value="No" disabled
                                          <%=json_two.get("Otherinvestments_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Otherinvestments_auditor_comment_2" readonly><%=json_two.get("Otherinvestments_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Otherinvestments_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Otherinvestments_auditor_auditor_management_remark_2") == null ? "" : json_four.get("Otherinvestments_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="UnitsAccounting_sample_1" readonly><%=json_two.get("UnitsAccounting_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="UnitsAccounting_complied_1" value="Yes" disabled
                                          <%=json_two.get("UnitsAccounting_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="UnitsAccounting_complied_1" value="No" disabled
                                          <%=json_two.get("UnitsAccounting_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="UnitsAccounting_auditor_comment_1" readonly><%=json_two.get("UnitsAccounting_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("UnitsAccounting_auditor_auditor_management_remark_1") == null ? "" : json_four.get("UnitsAccounting_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">13.2</td>
                              <td>Correctness of NAV applied</td>
                              <td><textarea rows="3"  name="UnitsAccounting_sample_2" readonly><%=json_two.get("UnitsAccounting_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="UnitsAccounting_complied_2" value="Yes" disabled
                                          <%=json_two.get("UnitsAccounting_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="UnitsAccounting_complied_2" value="No" disabled
                                          <%=json_two.get("UnitsAccounting_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="UnitsAccounting_auditor_comment_2" readonly><%=json_two.get("UnitsAccounting_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("UnitsAccounting_auditor_auditor_management_remark_2") == null ? "" : json_four.get("UnitsAccounting_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">13.3</td>
                              <td>Appropriateness of accounting of unit premium</td>
                              <td><textarea rows="3"  name="UnitsAccounting_sample_3" readonly><%=json_two.get("UnitsAccounting_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="UnitsAccounting_complied_3" value="Yes" disabled
                                          <%=json_two.get("UnitsAccounting_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="UnitsAccounting_complied_3" value="No" disabled
                                          <%=json_two.get("UnitsAccounting_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="UnitsAccounting_auditor_comment_3" readonly><%=json_two.get("UnitsAccounting_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="UnitsAccounting_auditor_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("UnitsAccounting_auditor_auditor_management_remark_3") == null ? "" : json_four.get("UnitsAccounting_auditor_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="BankReconciliation_sample_1" readonly><%=json_two.get("BankReconciliation_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="BankReconciliation_complied_1" value="Yes" disabled
                                          <%=json_two.get("BankReconciliation_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="BankReconciliation_complied_1" value="No" disabled
                                          <%=json_two.get("BankReconciliation_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="BankReconciliation_auditor_comment_1" readonly><%=json_two.get("BankReconciliation_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("BankReconciliation_auditor_auditor_management_remark_1") == null ? "" : json_four.get("BankReconciliation_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">14.2</td>
                              <td>Identification of idle funds</td>
                              <td><textarea rows="3"  name="BankReconciliation_sample_2" readonly><%=json_two.get("BankReconciliation_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="BankReconciliation_complied_2" value="Yes" disabled
                                          <%=json_two.get("BankReconciliation_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="BankReconciliation_complied_2" value="No" disabled
                                          <%=json_two.get("BankReconciliation_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="BankReconciliation_auditor_comment_2" readonly><%=json_two.get("BankReconciliation_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("BankReconciliation_auditor_auditor_management_remark_2") == null ? "" : json_four.get("BankReconciliation_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">14.3</td>
                              <td>Timely deployment of funds</td>
                              <td><textarea rows="3"  name="BankReconciliation_sample_3" readonly><%=json_two.get("BankReconciliation_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="BankReconciliation_complied_3" value="Yes" disabled
                                          <%=json_two.get("BankReconciliation_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="BankReconciliation_complied_3" value="No" disabled
                                          <%=json_two.get("BankReconciliation_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="BankReconciliation_auditor_comment_3" readonly><%=json_two.get("BankReconciliation_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="BankReconciliation_auditor_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("BankReconciliation_auditor_auditor_management_remark_3") == null ? "" : json_four.get("BankReconciliation_auditor_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Brokers_sample_1" readonly><%=json_two.get("Brokers_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Brokers_complied_1" value="Yes" disabled
                                          <%=json_two.get("Brokers_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio" name="Brokers_complied_1" value="No" disabled
                                          <%=json_two.get("Brokers_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Brokers_auditor_comment_1" readonly><%=json_two.get("Brokers_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Brokers_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Brokers_auditor_auditor_management_remark_1") == null ? "" : json_four.get("Brokers_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">15.2</td>
                              <td>Ceiling per broker as per IMA</td>
                              <td><textarea rows="3"  name="Brokers_sample_2" readonly><%=json_two.get("Brokers_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Brokers_complied_2" value="Yes" disabled
                                          <%=json_two.get("Brokers_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Brokers_complied_2" value="No" disabled
                                          <%=json_two.get("Brokers_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Brokers_auditor_comment_2" readonly><%=json_two.get("Brokers_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Brokers_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Brokers_auditor_auditor_management_remark_2") == null ? "" : json_four.get("Brokers_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">15.3</td>
                              <td>Broker Review</td>
                              <td><textarea rows="3"  name="Brokers_sample_3" readonly><%=json_two.get("Brokers_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Brokers_complied_3" value="Yes" disabled
                                          <%=json_two.get("Brokers_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Brokers_complied_3" value="No" disabled
                                          <%=json_two.get("Brokers_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Brokers_auditor_comment_3" readonly><%=json_two.get("Brokers_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Brokers_auditor_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Brokers_auditor_auditor_management_remark_3") == null ? "" : json_four.get("Brokers_auditor_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Non-Performing_sample_1" readonly><%=json_two.get("Non-Performing_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Non-Performing_complied_1" value="Yes" disabled
                                          <%=(Validator.isNotNull(json_two.get("Non-Performing_complied_2")) && json_two.get("Non-Performing_complied_1").equals("Yes")) ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Non-Performing_complied_1" value="No" disabled
                                          <%=(Validator.isNotNull(json_two.get("Non-Performing_complied_1")) && json_two.get("Non-Performing_complied_1").equals("No")) ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Non-Performing_auditor_comment_1"  readonly><%=json_two.get("Non-Performing_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Non-Performing_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Non-Performing_auditor_auditor_management_remark_1") == null ? "" : json_four.get("Non-Performing_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">16.2</td>
                              <td>Income Recognition</td>
                              <td><textarea rows="3"  name="Non-Performing_sample_2" readonly><%=json_two.get("Non-Performing_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Non-Performing_complied_2" value="Yes" disabled
                                          <%=(Validator.isNotNull(json_two.get("Non-Performing_complied_2")) && json_two.get("Non-Performing_complied_2").equals("Yes") )? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Non-Performing_complied_2" value="No" disabled
                                          <%=(Validator.isNotNull(json_two.get("Non-Performing_complied_2")) && json_two.get("Non-Performing_complied_2").equals("No") )? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Non-Performing_auditor_comment_2" readonly><%=json_two.get("Non-Performing_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Non-Performing_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Non-Performing_auditor_auditor_management_remark_2") == null ? "" : json_four.get("Non-Performing_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">16.3</td>
                              <td>Provisions</td>
                              <td><textarea rows="3"  name="Non-Performing_sample_3" readonly><%=json_two.get("Non-Performing_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Non-Performing_complied_3" value="Yes" disabled
                                          <%=(Validator.isNotNull(json_two.get("Non-Performing_complied_3")) && json_two.get("Non-Performing_complied_3").equals("Yes")) ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Non-Performing_complied_3" value="No" disabled
                                          <%=(Validator.isNotNull(json_two.get("Non-Performing_complied_3")) && json_two.get("Non-Performing_complied_3").equals("No")) ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Non-Performing_auditor_comment_3" readonly><%=json_two.get("Non-Performing_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Non-Performing_auditor_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Non-Performing_auditor_auditor_management_remark_3") == null ? "" : json_four.get("Non-Performing_auditor_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="InterSchemeTransfer_sample_1" readonly><%=json_two.get("InterSchemeTransfer_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InterSchemeTransfer_complied_1" value="Yes" disabled
                                          <%=json_two.get("InterSchemeTransfer_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InterSchemeTransfer_complied_1" value="No" disabled
                                          <%=json_two.get("InterSchemeTransfer_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InterSchemeTransfer_auditor_comment_1" readonly><%=json_two.get("InterSchemeTransfer_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InterSchemeTransfer_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InterSchemeTransfer_auditor_auditor_management_remark_1") == null ? "" : json_four.get("InterSchemeTransfer_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">17.2</td>
                              <td>Non-Traded-Need/Justification of off market transactions, fairness of price and internal authorization</td>
                              <td><textarea rows="3"  name="InterSchemeTransfer_sample_2" readonly><%=json_two.get("InterSchemeTransfer_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InterSchemeTransfer_complied_2" value="Yes" disabled
                                          <%=json_two.get("InterSchemeTransfer_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="InterSchemeTransfer_complied_2" value="No" disabled
                                          <%=json_two.get("InterSchemeTransfer_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="InterSchemeTransfer_auditor_comment_2" readonly><%=json_two.get("InterSchemeTransfer_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="InterSchemeTransfer_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("InterSchemeTransfer_auditor_auditor_management_remark_2") == null ? "" : json_four.get("InterSchemeTransfer_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Dealsverification_sample_1" readonly><%=json_two.get("Dealsverification_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_1" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_1" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_1" readonly><%=json_two.get("Dealsverification_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_1") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.2</td>
                              <td>Review of daily sale and purchase register with supporting documents</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_2" readonly><%=json_two.get("Dealsverification_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_2" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_2" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_2" readonly><%=json_two.get("Dealsverification_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_2") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.3</td>
                              <td>Journal Vouchers</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_3" readonly><%=json_two.get("Dealsverification_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_3" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_3" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_3" readonly><%=json_two.get("Dealsverification_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_3") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.4</td>
                              <td>Investment Ledgers</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_4" readonly><%=json_two.get("Dealsverification_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_4" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_4" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_4" readonly><%=json_two.get("Dealsverification_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_4") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.5</td>
                              <td>Verification of authorization, price and documentation</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_5" readonly><%=json_two.get("Dealsverification_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_5" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_5" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_5" readonly><%=json_two.get("Dealsverification_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_5") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.6</td>
                              <td>Counterparty confirmation</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_6" readonly><%=json_two.get("Dealsverification_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_6" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_6" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_6" readonly><%=json_two.get("Dealsverification_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_6") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.7</td>
                              <td>Contract Note from brokers</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_7" readonly><%=json_two.get("Dealsverification_sample_7") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_7" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_7").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_7" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_7").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_7" readonly><%=json_two.get("Dealsverification_auditor_comment_7") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_7") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_7") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.8</td>
                              <td>Broker Bills</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_8" readonly><%=json_two.get("Dealsverification_sample_8") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_8" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_8").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_8" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_8").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_8" readonly><%=json_two.get("Dealsverification_auditor_comment_8") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_8") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_8") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.9</td>
                              <td>Deal tickets</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_9" readonly><%=json_two.get("Dealsverification_sample_9") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_9" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_9").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_9" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_9").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_9" readonly><%=json_two.get("Dealsverification_auditor_comment_9") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_9" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_9") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_9") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.10</td>
                              <td>DIS/DIP statement and intimation to the custodian</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_10" readonly><%=json_two.get("Dealsverification_sample_10") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_sample_10" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_complied_10").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_10" value="No" disabled
                                          <%=json_two.get("Dealsverification_complied_10").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_10" readonly><%=json_two.get("Dealsverification_auditor_comment_10") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_10" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_10") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_10") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">18.11</td>
                              <td>Verification of timely and accurate capturing of trades</td>
                              <td><textarea rows="3"  name="Dealsverification_sample_11" readonly><%=json_two.get("Dealsverification_sample_11") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_11" value="Yes" disabled
                                          <%=json_two.get("Dealsverification_sample_11").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Dealsverification_complied_11" value="No" disabled
                                          <%=json_two.get("Dealsverification_sample_11").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Dealsverification_auditor_comment_11" readonly><%=json_two.get("Dealsverification_auditor_comment_11") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Dealsverification_auditor_auditor_management_remark_11" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Dealsverification_auditor_auditor_management_remark_11") == null ? "" : json_four.get("Dealsverification_auditor_auditor_management_remark_11") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="AccountingStandards_sample_1" readonly><%=json_two.get("AccountingStandards_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_1" value="Yes" disabled
                                          <%=json_two.get("AccountingStandards_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_1" value="No" disabled
                                          <%=json_two.get("AccountingStandards_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="AccountingStandards_auditor_comment_1" readonly><%=json_two.get("AccountingStandards_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("AccountingStandards_auditor_auditor_management_remark_1") == null ? "" : json_four.get("AccountingStandards_auditor_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">19.2</td>
                              <td>Adherence to accounting policy</td>
                              <td><textarea rows="3"  name="AccountingStandards_sample_2" readonly><%=json_two.get("AccountingStandards_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_2" value="Yes" disabled
                                          <%=json_two.get("AccountingStandards_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_2" value="No" disabled
                                          <%=json_two.get("AccountingStandards_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="AccountingStandards_auditor_comment_2" readonly><%=json_two.get("AccountingStandards_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("AccountingStandards_auditor_auditor_management_remark_2") == null ? "" : json_four.get("AccountingStandards_auditor_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">19.3</td>
                              <td>Accounting of income receipt</td>
                              <td><textarea rows="3"  name="AccountingStandards_sample_3" readonly><%=json_two.get("AccountingStandards_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_3" value="Yes" disabled
                                          <%=json_two.get("AccountingStandards_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_3" value="No" disabled
                                          <%=json_two.get("AccountingStandards_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="AccountingStandards_auditor_comment_3" readonly><%=json_two.get("AccountingStandards_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("AccountingStandards_auditor_auditor_management_remark_3") == null ? "" : json_four.get("AccountingStandards_auditor_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">19.4</td>
                              <td>Corporate action- bonus, rights, dividend, interest receivable</td>
                              <td><textarea rows="3"  name="AccountingStandards_sample_4" readonly><%=json_two.get("AccountingStandards_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_4" value="Yes" disabled
                                          <%=json_two.get("AccountingStandards_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_4" value="No" disabled
                                          <%=json_two.get("AccountingStandards_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="AccountingStandards_auditor_comment_4" readonly><%=json_two.get("AccountingStandards_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("AccountingStandards_auditor_auditor_management_remark_4") == null ? "" : json_four.get("AccountingStandards_auditor_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">19.5</td>
                              <td>Reversal of brokerage on daily basis</td>
                              <td><textarea rows="3"  name="AccountingStandards_sample_5" readonly><%=json_two.get("AccountingStandards_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_5" value="Yes" disabled
                                          <%=json_two.get("AccountingStandards_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_5" value="No" disabled
                                          <%=json_two.get("AccountingStandards_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="AccountingStandards_auditor_comment_5" readonly><%=json_two.get("AccountingStandards_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("AccountingStandards_auditor_auditor_management_remark_5") == null ? "" : json_four.get("AccountingStandards_auditor_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">19.6</td>
                              <td>Outsourcing </td>
                              <td><textarea rows="3"  name="AccountingStandards_sample_6" readonly><%=json_two.get("AccountingStandards_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_6" value="Yes" disabled
                                          <%=json_two.get("AccountingStandards_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="AccountingStandards_complied_6" value="No" disabled
                                          <%=json_two.get("AccountingStandards_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="AccountingStandards_auditor_comment_6" readonly><%=json_two.get("AccountingStandards_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="AccountingStandards_auditor_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("AccountingStandards_auditor_auditor_management_remark_6") == null ? "" : json_four.get("AccountingStandards_auditor_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Nav_sample_1" readonly><%=json_two.get("Nav_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_1" value="Yes" disabled
                                          <%=json_two.get("Nav_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_1" value="No" disabled
                                          <%=json_two.get("Nav_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_1" readonly><%=json_two.get("Nav_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_1") == null ? "" : json_four.get("Nav_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">20.2</td>
                              <td>NAV Calculation</td>
                              <td><textarea rows="3"  name="Nav_sample_2" readonly><%=json_two.get("Nav_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_2" value="Yes" disabled
                                          <%=json_two.get("Nav_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_2" value="No" disabled
                                          <%=json_two.get("Nav_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_2" readonly><%=json_two.get("Nav_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_2") == null ? "" : json_four.get("Nav_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">20.3</td>
                              <td>Trade verification</td>
                              <td><textarea rows="3"  name="Nav_sample_3" readonly><%=json_two.get("Nav_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_3" value="Yes" disabled
                                          <%=json_two.get("Nav_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_3" value="No" disabled
                                          <%=json_two.get("Nav_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_3" readonly><%=json_two.get("Nav_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_3") == null ? "" : json_four.get("Nav_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">20.4</td>
                              <td>Recording of Corporate Action on ex-date</td>
                              <td><textarea rows="3"  name="Nav_sample_4" readonly><%=json_two.get("Nav_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_4" value="Yes" disabled
                                          <%=json_two.get("Nav_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_4" value="No" disabled
                                          <%=json_two.get("Nav_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_4" readonly><%=json_two.get("Nav_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_4") == null ? "" : json_four.get("Nav_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">20.5</td>
                              <td>Creation of new security master</td>
                              <td><textarea rows="3"  name="Nav_sample_5" readonly><%=json_two.get("Nav_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_5" value="Yes" disabled 
                                          <%=json_two.get("Nav_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_5" value="No" disabled
                                          <%=json_two.get("Nav_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_5" readonly><%=json_two.get("Nav_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_5") == null ? "" : json_four.get("Nav_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">20.6</td>
                              <td>Unit capital booked in the system </td>
                              <td><textarea rows="3"  name="Nav_sample_6" readonly><%=json_two.get("Nav_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_6" value="Yes" disabled
                                          <%=json_two.get("Nav_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_6" value="No" disabled
                                          <%=json_two.get("Nav_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_6" readonly><%=json_two.get("Nav_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_6") == null ? "" : json_four.get("Nav_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">20.7</td>
                              <td>Accrual of Coupons </td>
                              <td><textarea rows="3"  name="Nav_sample_7" readonly><%=json_two.get("Nav_sample_7") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_7" value="Yes" disabled
                                          <%=json_two.get("Nav_complied_7").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_7" value="No" disabled
                                          <%=json_two.get("Nav_complied_7").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_7" readonly><%=json_two.get("Nav_auditor_comment_7") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_7") == null ? "" : json_four.get("Nav_auditor_management_remark_7") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">20.8</td>
                              <td>Scrutiny of expenses charged to the schemes on daily basis (i.e. Investment Management fees, custodian charges, SEBI charges and CCIL charges) </td>
                              <td><textarea rows="3"  name="Nav_sample_8" readonly><%=json_two.get("Nav_sample_8") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_8" value="Yes" disabled
                                          <%=json_two.get("Nav_complied_8").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_8" value="No" disabled
                                          <%=json_two.get("Nav_complied_8").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_8" readonly><%=json_two.get("Nav_auditor_comment_8") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_8") == null ? "" : json_four.get("Nav_auditor_management_remark_8") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">20.9</td>
                              <td>Outsourcing </td>
                              <td><textarea rows="3"  name="Nav_sample_9" readonly><%=json_two.get("Nav_sample_9") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_9" value="Yes" disabled
                                          <%=json_two.get("Nav_complied_9").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Nav_complied_9" value="No" disabled
                                          <%=json_two.get("Nav_complied_9").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Nav_auditor_comment_9" readonly><%=json_two.get("Nav_auditor_comment_9") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Nav_auditor_management_remark_9" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Nav_auditor_management_remark_9") == null ? "" : json_four.get("Nav_auditor_management_remark_9") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Disclosure_sample_1" readonly><%=json_two.get("Disclosure_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_1" value="Yes" disabled
                                          <%=json_two.get("Disclosure_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_1" value="No" disabled
                                          <%=json_two.get("Disclosure_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Disclosure_auditor_comment_1" readonly><%=json_two.get("Disclosure_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Disclosure_auditor_management_remark_1") == null ? "" : json_four.get("Disclosure_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">21.2</td>
                              <td>NAV History </td>
                              <td><textarea rows="3"  name="Disclosure_sample_2" readonly><%=json_two.get("Disclosure_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_2" value="Yes" disabled
                                          <%=json_two.get("Disclosure_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_2" value="No" disabled
                                          <%=json_two.get("Disclosure_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Disclosure_auditor_comment_2" readonly><%=json_two.get("Disclosure_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Disclosure_auditor_management_remark_2") == null ? "" : json_four.get("Disclosure_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">21.3</td>
                              <td>Portfolio Disclosure on monthly basis</td>
                              <td><textarea rows="3"  name="Disclosure_sample_3" readonly><%=json_two.get("Disclosure_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_3" value="Yes" disabled
                                          <%=json_two.get("Disclosure_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_3" value="No" disabled
                                          <%=json_two.get("Disclosure_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Disclosure_auditor_comment_3" readonly><%=json_two.get("Disclosure_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Disclosure_auditor_management_remark_3") == null ? "" : json_four.get("Disclosure_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">21.4</td>
                              <td>Half Yearly Financial statement</td>
                              <td><textarea rows="3"  name="Disclosure_sample_4" readonly><%=json_two.get("Disclosure_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_4" value="Yes" disabled
                                          <%=json_two.get("Disclosure_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_4" value="No" disabled
                                          <%=json_two.get("Disclosure_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Disclosure_auditor_comment_4" readonly><%=json_two.get("Disclosure_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Disclosure_auditor_management_remark_4") == null ? "" : json_four.get("Disclosure_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">21.5</td>
                              <td>Annual report</td>
                              <td><textarea rows="3"  name="Disclosure_sample_5" readonly><%=json_two.get("Disclosure_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_5" value="Yes" disabled
                                          <%=json_two.get("Disclosure_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Disclosure_complied_5" value="No" disabled
                                          <%=json_two.get("Disclosure_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Disclosure_auditor_comment_5" readonly><%=json_two.get("Disclosure_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Disclosure_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Disclosure_auditor_management_remark_5") == null ? "" : json_four.get("Disclosure_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Periodical_sample_1" readonly><%=json_two.get("Periodical_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Periodical_complied_1" value="Yes" disabled
                                          <%=json_two.get("Periodical_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Periodical_complied_1" value="No" disabled
                                          <%=json_two.get("Periodical_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Periodical_auditor_comment_1" readonly><%=json_two.get("Periodical_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Periodical_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Periodical_auditor_management_remark_1") == null ? "" : json_four.get("Periodical_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">22.2</td>
                              <td>Accuracy of data</td>
                              <td><textarea rows="3"  name="Periodical_sample_2" readonly><%=json_two.get("Periodical_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Periodical_complied_2" value="Yes" disabled
                                          <%=json_two.get("Periodical_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Periodical_complied_2" value="No" disabled
                                          <%=json_two.get("Periodical_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Periodical_auditor_comment_2" readonly><%=json_two.get("Periodical_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Periodical_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Periodical_auditor_management_remark_2") == null ? "" : json_four.get("Periodical_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">22.3</td>
                              <td>Procedure of generation of data and report</td>
                              <td><textarea rows="3"  name="Periodical_sample_3" readonly><%=json_two.get("Periodical_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Periodical_complied_3" value="Yes" disabled
                                          <%=json_two.get("Periodical_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Periodical_complied_3" value="No" disabled
                                          <%=json_two.get("Periodical_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Periodical_auditor_comment_3" readonly><%=json_two.get("Periodical_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Periodical_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Periodical_auditor_management_remark_3") == null ? "" : json_four.get("Periodical_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">22.4</td>
                              <td>Procedure for capturing Down grading of investment</td>
                              <td><textarea rows="3"  name="Periodical_sample_4" readonly><%=json_two.get("Periodical_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Periodical_complied_4" value="Yes" disabled
                                          <%=json_two.get("Periodical_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Periodical_complied_4" value="No" disabled
                                          <%=json_two.get("Periodical_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Periodical_auditor_comment_4" readonly><%=json_two.get("Periodical_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Periodical_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Periodical_auditor_management_remark_4") == null ? "" : json_four.get("Periodical_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Compliances_sample_1" readonly><%=json_two.get("Compliances_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Compliances_complied_1" value="Yes" disabled
                                          <%=json_two.get("Compliances_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Compliances_complied_1" value="No" disabled
                                          <%=json_two.get("Compliances_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Compliances_auditor_comment_1" readonly><%=json_two.get("Compliances_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Compliances_auditor_management_remark_1") == null ? "" : json_four.get("Compliances_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">23.2</td>
                              <td>Compliance to Guidelines and Guidance note</td>
                              <td><textarea rows="3"  name="Compliances_sample_2" readonly><%=json_two.get("Compliances_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Compliances_complied_2" value="Yes" disabled
                                          <%=json_two.get("Compliances_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Compliances_complied_2" value="No" disabled
                                          <%=json_two.get("Compliances_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Compliances_auditor_comment_2" readonly><%=json_two.get("Compliances_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Compliances_auditor_management_remark_2") == null ? "" : json_four.get("Compliances_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">23.3</td>
                              <td>Compliance to Internal Guidelines, Operational manual</td>
                              <td><textarea rows="3"  name="Compliances_sample_3" readonly><%=json_two.get("Compliances_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Compliances_complied_3" value="Yes" disabled
                                          <%=json_two.get("Compliances_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Compliances_complied_3" value="No" disabled
                                          <%=json_two.get("Compliances_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Compliances_auditor_comment_3" readonly><%=json_two.get("Compliances_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Compliances_auditor_management_remark_3") == null ? "" : json_four.get("Compliances_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">23.4</td>
                              <td>Adequacy and efficacy of Internal Control system and procedures</td>
                              <td><textarea rows="3"  name="Compliances_sample_4" readonly><%=json_two.get("Compliances_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Compliances_complied_4" value="Yes" disabled
                                          <%=json_two.get("Compliances_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Compliances_complied_4" value="No" disabled
                                          <%=json_two.get("Compliances_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Compliances_auditor_comment_4" readonly><%=json_two.get("Compliances_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Compliances_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Compliances_auditor_management_remark_4") == null ? "" : json_four.get("Compliances_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="IPFMS_sample_1" readonly><%=json_two.get("IPFMS_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="IPFMS_complied_1" value="Yes" disabled
                                          <%=json_two.get("IPFMS_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="IPFMS_complied_1" value="No" disabled
                                          <%=json_two.get("IPFMS_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="IPFMS_auditor_comment_1" readonly><%=json_two.get("IPFMS_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="IPFMS_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("IPFMS_auditor_management_remark_1") == null ? "" : json_four.get("IPFMS_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="Marketing&Distribution_sample_1" readonly><%=json_two.get("Marketing&Distribution_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Marketing&Distribution_complied_1" value="Yes" disabled
                                          <%=json_two.get("Marketing&Distribution_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="Marketing&Distribution_complied_1" value="No" disabled
                                          <%=json_two.get("Marketing&Distribution_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="Marketing&Distribution_auditor_comment_1" readonly><%=json_two.get("Marketing&Distribution_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="Marketing&Distribution_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("Marketing&Distribution_auditor_management_remark_1") == null ? "" : json_four.get("Marketing&Distribution_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
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
                              <td><textarea rows="3"  name="OtherRegulatory_sample_1" readonly><%=json_two.get("OtherRegulatory_sample_1") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_1" value="Yes" disabled
                                          <%=json_two.get("OtherRegulatory_complied_1").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_1" value="No" disabled
                                          <%=json_two.get("OtherRegulatory_complied_1").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="OtherRegulatory_auditor_comment_1" readonly><%=json_two.get("OtherRegulatory_auditor_comment_1") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_management_remark_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("OtherRegulatory_auditor_management_remark_1") == null ? "" : json_four.get("OtherRegulatory_auditor_management_remark_1") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">26.2</td>
                              <td>Appointment of compliance officer</td>
                              <td><textarea rows="3"  name="OtherRegulatory_sample_2" readonly><%=json_two.get("OtherRegulatory_sample_2") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_2" value="Yes" disabled
                                          <%=json_two.get("OtherRegulatory_complied_2").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_2" value="No" disabled
                                          <%=json_two.get("OtherRegulatory_complied_2").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="OtherRegulatory_auditor_comment_2" readonly><%=json_two.get("OtherRegulatory_auditor_comment_2") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_management_remark_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("OtherRegulatory_auditor_management_remark_2") == null ? "" : json_four.get("OtherRegulatory_auditor_management_remark_2") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">26.3</td>
                              <td>Payment of Annual Fees to PFRDA</td>
                              <td><textarea rows="3"  name="OtherRegulatory_sample_3" readonly><%=json_two.get("OtherRegulatory_sample_3") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_3" value="Yes" disabled
                                          <%=json_two.get("OtherRegulatory_complied_3").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_3" value="No" disabled
                                          <%=json_two.get("OtherRegulatory_complied_3").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="OtherRegulatory_auditor_comment_3" readonly><%=json_two.get("OtherRegulatory_auditor_comment_3") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_management_remark_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("OtherRegulatory_auditor_management_remark_3") == null ? "" : json_four.get("OtherRegulatory_auditor_management_remark_3") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">26.4</td>
                              <td>Whether the investment management fee is inclusive of brokerage but exclusive of custodian fee and applicable taxes. All other costs to be borne by the pension fund and not to be reimbursed or charged to the scheme by the pension fund.</td>
                              <td><textarea rows="3"  name="OtherRegulatory_sample_4" readonly><%=json_two.get("OtherRegulatory_sample_4") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_4" value="Yes" disabled
                                          <%=json_two.get("OtherRegulatory_complied_4").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_4" value="No" disabled
                                          <%=json_two.get("OtherRegulatory_complied_4").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="OtherRegulatory_auditor_comment_4" readonly><%=json_two.get("OtherRegulatory_auditor_comment_4") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_management_remark_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("OtherRegulatory_auditor_management_remark_4") == null ? "" : json_four.get("OtherRegulatory_auditor_management_remark_4") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">26.5</td>
                              <td>Whether the pension fund has loaded their charges (investment management fees) onto the net asset value on daily basis and the accrued charges (income) are collected by them at the end of each quarter, from the scheme bank accounts maintained with Trustee Bank after approval of the National Pension System Trust.</td>
                              <td><textarea rows="3"  name="OtherRegulatory_sample_5" readonly><%=json_two.get("OtherRegulatory_sample_5") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_5" value="Yes" disabled
                                          <%=json_two.get("OtherRegulatory_complied_5").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_5" value="No" disabled
                                          <%=json_two.get("OtherRegulatory_complied_5").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="OtherRegulatory_auditor_comment_5" readonly><%=json_two.get("OtherRegulatory_auditor_comment_5") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_management_remark_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("OtherRegulatory_auditor_management_remark_5") == null ? "" : json_four.get("OtherRegulatory_auditor_management_remark_5") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">26.6</td>
                              <td>Pension Funds shall not deal in speculative transactions</td>
                              <td><textarea rows="3"  name="OtherRegulatory_sample_6" readonly><%=json_two.get("OtherRegulatory_sample_6") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_6" value="Yes" disabled
                                          <%=json_two.get("OtherRegulatory_complied_6").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_6" value="No" disabled
                                          <%=json_two.get("OtherRegulatory_complied_6").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="OtherRegulatory_auditor_comment_6" readonly><%=json_two.get("OtherRegulatory_auditor_comment_6") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_management_remark_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("OtherRegulatory_auditor_management_remark_6") == null ? "" : json_four.get("OtherRegulatory_auditor_management_remark_6") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <tr>
                              <td class="text-center">26.7</td>
                              <td>Execution of agreements with the intermediaries under the NPS system before commencement of business </td>
                              <td><textarea rows="3"  name="OtherRegulatory_sample_7" readonly><%=json_two.get("OtherRegulatory_sample_7") %></textarea></td>
                              <td>
                                 <div class="d-flex">
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_7" value="Yes" disabled
                                          <%=json_two.get("OtherRegulatory_complied_7").equals("Yes") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; Yes</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                       <input type="radio"  name="OtherRegulatory_complied_7" value="No" disabled
                                          <%=json_two.get("OtherRegulatory_complied_7").equals("No") ? "checked" : "" %>>
                                       <label class="form-check-label">&nbsp; No</label>
                                    </div>
                                 </div>
                                 <span class="text-danger"></span>
                              </td>
                              <td><textarea rows="3"  name="OtherRegulatory_auditor_comment_7" readonly><%=json_two.get("OtherRegulatory_auditor_comment_7") %></textarea></td>
                              <td><textarea rows="3" class="boardMeeting" name="OtherRegulatory_auditor_management_remark_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("OtherRegulatory_auditor_management_remark_7") == null ? "" : json_four.get("OtherRegulatory_auditor_management_remark_7") %></textarea>&nbsp;&nbsp;<span class=" text-danger"></span></td>
                           </tr>
                           <!-- end --> 
                        </tbody>
                     </table>
                  </div>
               </div>
            </div>
            <div class="row">
               <div class="col-md-6">
                  <div class="form-check form-check-inline">
                     <p>IAR Certificate by Auditor (PDF Upload)</p>
                  </div>
               </div>
               <div class="col-md-3">
                  <div class="form-check form-check-inline">
                     <a href="${empty AnnexureForIARCircular_pdf_file_url ? "javascript:void(0);" : AnnexureForIARCircular_pdf_file_url}" ${empty AnnexureForIARCircular_pdf_file_url ? "" : "download"}>Click here to download</a>
                  </div>
               </div>
               <div class="col-md-3">
                  <div class="form-group">
                     <textarea class="form-control boardMeeting" id="Document_Ai" placeholder="Remarks if any" name="AnnexureForIARCircular_remarks"></textarea>
                     &nbsp;&nbsp;<span class=" text-danger"></span>
                  </div>
               </div>
            </div>
            <!-- row one end -->
            <div class="row">
               <div class="col-md-12">
                  <div class="text-center">
                     <!-- <input type="button" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit"> -->
                     <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" <%=isNonNPSUser ? "disabled": "" %>>Submit</button>
                     <a class="button" id="pop-up-trigger"  href="#success-modal"></a>
                  </div>
               </div>
            </div>
         </form>
      </div>
   </div>
</div>
</div>

<style>
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

</style>

<script type="text/javascript">
var SDWebServerUrl = "https://dsc.npstrust.net";
var SDWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
var sdWebServerUrl = "https://dsc.npstrust.net";
var sdWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";

<% if(!isDocSigned){ %>
	$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").bind("click", false);
<% }else{ %>
	let sigsub = signature.SelCertSubject.substring(signature.SelCertSubject.indexOf("CN="));
	$("h6.signedmsg").append(" by "+sigsub.split(",")[0].split("=")[1]); 
	$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").unbind("click");
<% } %>
$(function(){
	console.log("doc load");
	$(document).on('click', '#signFile', function(event) {
		//e.preventDefault();
		console.log("in sigin");
		SignerDigital.getSelectedCertificate()
         .then(function(data){
        	 console.log(data);
			var CertInfo = JSON.parse(data);
			$("#signature").val(CertInfo.Cert);
			var fd = new FormData();
			fd.append("certificateJSON", encodeURIComponent(data));
			fd.append("fileEntryId", "<%=fileEntryId%>");
			fd.append("sDHubConnectionIdFromBrowser", sdHubConnectionId);
			fd.append("certificateFromBrowser", encodeURIComponent(CertInfo.Cert));
			if(CertInfo.eMail === themeDisplay.getUserEmailAddress()){
				$.ajax({
		            url: '/o/dsc/signPDF',  
		            type: 'POST',
		            cache: false,
		            data: fd,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	console.log(data);
		            	location.reload(); 
		            },
		            error: function() {
		           		console.log("Error Occured in ajax call");
		           		
		            },
		            complete: function(){
			        }
		    	});
			}else{
				$('#output').html('Please select valid token for signature.');
        		$('#errorExcel').modal('show');
			}
			//$("#submit").click();
		})
	})
})

$(document).ready(function() {
	
	function validateForm(){
	    var isValid = false;    
	    var classname = "boardMeeting";    
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
	
	$("#pfm_internal_audit_report_scrutiny_form").on('submit', (function(e) {
		console.log("Inside ajax");
		
		e.preventDefault();
			
			if(!validateForm()){
				
					var formData = new FormData(document.getElementById("pfm_internal_audit_report_scrutiny_form"));
					var hdfciarpfmScrutinyResourceURL = "/web/guest/quaterly-internal-audit-report?p_p_id=com_internal_audit_report_pfm_HDFCInternalAuditReportPFMPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=save%2FHDFC-Internal-audit-report-scrutiny%2Fpfm&p_p_cacheability=cacheLevelPage";
					$(".animaion").show();
					$.ajax({
			            type: "POST",
			            enctype: 'multipart/form-data',
			            processData: false,
			            contentType: false,
			            cache: false,
			            url: hdfciarpfmScrutinyResourceURL,
			            data: formData,
			            success: function(data) {
			            	$(".animaion").hide();
			            	if(data == "Success") {
			            		//toastr.success('Form has been submitted successfully!');
		   		            	$("#pfm_internal_audit_report_scrutiny_form")[0].reset();
			            	} else {
			            		console.log("Error Occured in ajax call");
			            		//toastr.error('An error occured while submitting the form. Please try again');
			            	}
			            },
			            error: function() {
			            	$(".animaion").hide();
			            	//toastr.error('An error occured while submitting the form. Please try again');
			            	console.log("Error Occured in ajax call");
			            },
			            complete: function(){
			            	$(".animaion").hide();
				        }
			
			        });

			}

		}));
});	
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>
