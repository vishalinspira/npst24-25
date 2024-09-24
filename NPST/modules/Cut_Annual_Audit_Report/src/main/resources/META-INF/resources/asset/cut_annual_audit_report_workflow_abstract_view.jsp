<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="Cut_Annual_Audit_Report.Util.NPSUserPrepopulateScrutinyData"%>
<%@page import="com.daily.average.service.service.CustAnnualReportscrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.CustAnnualReportscrutiny"%>
<%@page import="Cut_Annual_Audit_Report.Util.PrepopulateScrutinyData"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Cut_Annual_Audit_Report.Util.Cut_Annual_Audit_ReportDocumentUtil"%>
<%@page import="com.daily.average.service.service.persistence.CustAnnualAuditReportUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="com.daily.average.service.service.CustAnnualAuditReportLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.CustAnnualAuditReport"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.Validator"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.1.1/css/all.min.css" />
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
CustAnnualAuditReport custannualdetails = Validator.isNotNull(request.getAttribute("custAnnualAuditReport")) ? (CustAnnualAuditReport) request.getAttribute("custAnnualAuditReport") : CustAnnualAuditReportLocalServiceUtil.createCustAnnualAuditReport(0);
boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;

System.out.println("isNonNPSUser asset rendererrr:::::" + isNonNPSUser);

JSONObject json_two = JSONFactoryUtil.createJSONObject();
SimpleDateFormat sdfist = new SimpleDateFormat("dd/MM/yyyy h:mm a");
try {
	if(custannualdetails.getCust_report_details()!=null) {
		JSONObject json_one = JSONFactoryUtil.createJSONObject(custannualdetails.getCust_report_details());
		
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
		
	}
	
} catch(Exception e) {
}

/* Pre populate data for asset view */

NPSUserPrepopulateScrutinyData sd = new NPSUserPrepopulateScrutinyData();
sd.prePopulateScrutinyData(request);
		
CustAnnualReportscrutiny custAnnualReportscrutiny = Validator.isNotNull(request.getAttribute("custAnnualScrutiny")) ? (CustAnnualReportscrutiny) request.getAttribute("custAnnualScrutiny") : CustAnnualReportscrutinyLocalServiceUtil.createCustAnnualReportscrutiny(0);		


	JSONObject json_four = JSONFactoryUtil.createJSONObject();
	JSONObject json_three = JSONFactoryUtil.createJSONObject();
	try {
		if(custAnnualReportscrutiny.getCust_report_remarks()!=null && !custAnnualReportscrutiny.getCust_report_remarks().trim().equals("")) {
			json_three = JSONFactoryUtil.createJSONObject(custAnnualReportscrutiny.getCust_report_remarks());
			
		}else{
			json_three = JSONFactoryUtil.createJSONObject(Cut_Annual_Audit_ReportDocumentUtil.emptyJsonScrutinyRemarksdata);
		}
		
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Regulatory parameters")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Operational parameters")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Timely Reporting")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Custodian Billing")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Custodian")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Infrastructure")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Protection")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Record maintenance")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Grievance Redressal")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Other parameters")));
		json_four = Cut_Annual_Audit_ReportDocumentUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Governance Structure")));
		
	} catch(Exception e) {
}


	
	List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");

	boolean isCustodianSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_SUPERVISOR, false);
	boolean isDocSigned = false;
	JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
	String fileEntryId = "0";
	JSONObject signature = JSONFactoryUtil.createJSONObject();
%>
 <div class="custom-modal-ui hide"  id="success-modal">
	<div class="modal-head-ui">
		<h2 class="modal-title-ui">Report Message</h2>
		<a class="modal-close-ui x-mark" href="#0">&times;</a>
		<div class="modal-content-ui text-center">
			<i class="" id="icon"></i>
			<span id="output"></span>
		</div>
	</div>
</div>

<div class="nps-page-main nps-upload-report nps-statement-wrapper">

<p>Report Name: ${reportName}</p>
<p>Upload Date: ${uploadDate}</p>

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
	<span class="file-preview-span" data-index='<%= i%>'>
	 	<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a>  </span>
	<% if(i != (urlArray.length()-1)){ %>
	 ,
	<% } %>
<% } %>
</div>
<% if(isCustodianSupervisor){ %>
	<div class="panel-body">
		<% if(!isDocSigned){ %>
			<button id="signFile" class="btn  btn-primary">Sign File</button>
		<% }else{ %>
			<button id="signFile" class="btn  btn-primary" disabled="disabled">Sign File</button>
			<h6>The report has been Authenticated</h6>
		<% } %>
	</div>
<% }else{ %>
	<div class="panel-body">
		<% if(isDocSigned){ %>
			<h6 class="signedmsg">The report has been Authenticated</h6>
		<% } %>
	</div>
<% } %>

   <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <!-- <h4>Annual Audit Report</h4> -->
            <form class="form" id="annualAuditReport" action="#" method="post">
            <input type="hidden" value="${reportUploadLogId }" name="reportUploadLogId" class="reportUploadLogId" />
			<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <span>${sdfist.format(custannualdetails.getReportDate() == null ? "" : custannualdetails.getReportDate()) } </span>
                     </div>
                  </div>
                   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>For the year ended</label>
                        <span>${sdfist.format(custannualdetails.getDate_() == null ? "" : custannualdetails.getDate_()) }</span>
                     </div>
                  </div>
               </div>
              
               <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                           <label>Annual Audit Report (PDF Upload)</label>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Audit_pdf ? "javascript:void(0);" : Audit_pdf}" ${empty Audit_pdf ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureA_rem" placeholder="Remarks if any" name="audit_pdf_file_remarks" <%=isNonNPSUser ? "disabled": "" %>><%=custAnnualReportscrutiny.getAudit_pdf_file_remarks() == null ? "" : custAnnualReportscrutiny.getAudit_pdf_file_remarks() %></textarea>
                        </div>
                     </div>
        		</div><!-- row one end -->
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
                        <input type="radio" disabled="disabled" class="regulatorya1" id="comments_regulatorya" name="comments_regulatorya_1" 
                        <%=json_two.get("comments_regulatorya_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="regulatorya1" id="comments_regulatorya1" name="comments_regulatorya_1" value="No"
                        <%=json_two.get("comments_regulatorya_1").equals("No") ? "checked" : "" %> >
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya_1" id="remarks_regulatorya_1" name="remarks_regulatorya_1"
                   readonly value="<%=json_two.get("remarks_regulatorya_1") %>" >
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya_1" id="management_regulatorya_1" name="management_regulatorya_1"
                    readonly value="<%=json_two.get("management_regulatorya_1") %>" >
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="regulatorya2" id="comments_regulatorya" name="comments_regulatorya_2" 
                        <%=json_two.get("comments_regulatorya_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="regulatorya2" id="comments_regulatorya2" name="comments_regulatorya_2" 
                        <%=json_two.get("comments_regulatorya_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya_2" id="remarks_regulatorya2" name="remarks_regulatorya_2"
                   readonly value="<%=json_two.get("remarks_regulatorya_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya_2" id="management_regulatorya2" name="management_regulatorya_2"
                    readonly value="<%=json_two.get("management_regulatorya_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="regulatorya3" id="comments_regulatorya" name="comments_regulatorya_3" 
                        <%=json_two.get("comments_regulatorya_3").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="regulatorya3" id="comments_regulatorya3" name="comments_regulatorya_3" 
                        <%=json_two.get("comments_regulatorya_3").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya_3" id="remarks_regulatorya3" name="remarks_regulatorya_3"
                   readonly value="<%=json_two.get("remarks_regulatorya_3") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya_3" id="management_regulatorya3" name="management_regulatorya_3"
                    readonly value="<%=json_two.get("management_regulatorya_3") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_3") %></textarea>
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
                        <input type="radio" disabled="disabled" class="regulatorya4" id="comments_regulatorya" name="comments_regulatorya_4" 
                        <%=json_two.get("comments_regulatorya_4").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="regulatorya4" id="comments_regulatorya4" name="comments_regulatorya_4" 
                        <%=json_two.get("comments_regulatorya_4").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya4" id="remarks_regulatorya4" name="remarks_regulatorya_4"
                   readonly value="<%=json_two.get("remarks_regulatorya_4") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya4" id="management_regulatorya4" name="management_regulatorya_4"
                    readonly value="<%=json_two.get("management_regulatorya_4") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_4"<%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_4") %></textarea>
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
                        <input type="radio" disabled="disabled" class="regulatorya5" id="comments_regulatorya" name="comments_regulatorya_5" 
                        <%=json_two.get("comments_regulatorya_5").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="regulatorya5" id="comments_regulatorya5" name="comments_regulatorya_5" 
                        <%=json_two.get("comments_regulatorya_5").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya5" id="remarks_regulatorya5" name="remarks_regulatorya_5"
                  readonly value="<%=json_two.get("remarks_regulatorya_5") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya5" id="management_regulatorya5" name="management_regulatorya_5"
                    readonly value="<%=json_two.get("management_regulatorya_5") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_5") %></textarea>
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
                        <input type="radio" disabled="disabled" class="regulatorya6" id="comments_regulatorya" name="comments_regulatorya_6" 
                        <%=json_two.get("comments_regulatorya_6").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="regulatorya6" id="comments_regulatorya6" name="comments_regulatorya_6" 
                        <%=json_two.get("comments_regulatorya_6").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya6" id="remarks_regulatorya6" name="remarks_regulatorya_6"
                   readonly value="<%=json_two.get("remarks_regulatorya_6") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya6" id="management_regulatorya6" name="management_regulatorya_6"
                    readonly value="<%=json_two.get("management_regulatorya_6") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_6") %></textarea>
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
                        <input type="radio" disabled="disabled" class="regulatorya7" id="comments_regulatorya" name="comments_regulatorya_7" 
                        <%=json_two.get("comments_regulatorya_7").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="regulatorya7" id="comments_regulatorya7" name="comments_regulatorya_7" 
                        <%=json_two.get("comments_regulatorya_7").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya7" id="remarks_regulatorya7" name="remarks_regulatorya_7"
                   readonly value="<%=json_two.get("remarks_regulatorya_7") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya7" id="management_regulatorya7" name="management_regulatorya_7"
                    readonly value="<%=json_two.get("management_regulatorya_7") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_7") %></textarea>
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
                        <input type="radio" disabled="disabled" class="comments_regulatorya8" id="comments_regulatorya8" name="comments_regulatorya_8" 
                        <%=json_two.get("comments_regulatorya_8").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="comments_regulatorya8" id="comments_regulatorya" name="comments_regulatorya_8" 
                        <%=json_two.get("comments_regulatorya_8").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya8" id="remarks_regulatorya8" name="remarks_regulatorya_8"
                   readonly value="<%=json_two.get("remarks_regulatorya_8") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya8" id="management_regulatorya8" name="management_regulatorya_8"
                    readonly value="<%=json_two.get("management_regulatorya_8") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_8") %></textarea>
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
                        <input type="radio" disabled="disabled" class="regulatorya9" id="comments_regulatorya" name="comments_regulatorya_9" 
                        <%=json_two.get("comments_regulatorya_9").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="regulatorya9" id="comments_regulatorya9" name="comments_regulatorya_9" 
                        <%=json_two.get("comments_regulatorya_9").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_regulatorya9" id="remarks_regulatorya9" name="remarks_regulatorya_9"
                   readonly value="<%=json_two.get("remarks_regulatorya_9") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_regulatorya9" id="remarks_regulatorya9" name="management_regulatorya_9"
                    readonly value="<%=json_two.get("management_regulatorya_9") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="regulatory_rem_9" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("regulatory_rem_9") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb1" id="comments_operationalb" name="comments_operationalb_1" 
                        <%=json_two.get("comments_operationalb_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb1" id="comments_operationalb_1" name="comments_operationalb_1" 
                        <%=json_two.get("comments_operationalb_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_1" id="remarks_operationalb1" name="remarks_operationalb_1"
                   readonly value="<%=json_two.get("remarks_operationalb_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_1" id="management_operationalb1" name="management_operationalb_1"
                    readonly value="<%=json_two.get("management_operationalb_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb2" id="comment_operationalb" name="management_operationalb_2" 
                        <%=json_two.get("comments_operationalb_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb2" id="management_operationalb2" name="management_operationalb_2" 
                        <%=json_two.get("comments_operationalb_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_2" id="remarks_operationalb2" name="remarks_operationalb_2"
                  readonly value="<%=json_two.get("remarks_operationalb_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_2" id="management_operationalb2" name="management_operationalb_2"
                    readonly value="<%=json_two.get("management_operationalb_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb3" id="comments_operationalb" name="comments_operationalb_3" 
                        <%=json_two.get("comments_operationalb_3").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb3" id="comments_operationalb3" name="comments_operationalb_3" 
                        <%=json_two.get("comments_operationalb_3").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_3" id="remarks_operationalb3" name="remarks_operationalb_3"
                   readonly value="<%=json_two.get("remarks_operationalb_3") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_3" id="management_operationalb3" name="management_operationalb_3"
                    readonly value="<%=json_two.get("management_operationalb_3") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_3") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb4" id="comments_operationalb" name="comments_operationalb_4" 
                        <%=json_two.get("comments_operationalb_4").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb4" id="comments_operationalb4" name="comments_operationalb_4" 
                        <%=json_two.get("comments_operationalb_4").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_4" id="remarks_operationalb4" name="remarks_operationalb_4"
                  readonly value="<%=json_two.get("remarks_operationalb_4") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_4" id="management_operationalb4" name="management_operationalb_4"
                    readonly value="<%=json_two.get("management_operationalb_4") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_4") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb5" id="comments_operationalb" name="comments_operationalb_5" 
                        <%=json_two.get("comments_operationalb_5").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb5" id="comments_operationalb5" name="comments_operationalb_5" 
                        <%=json_two.get("comments_operationalb_5").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_5" id="remarks_operationalb5" name="remarks_operationalb_5"
                  readonly value="<%=json_two.get("remarks_operationalb_5") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_5" id="management_operationalb5" name="management_operationalb_5"
                   readonly value="<%=json_two.get("management_operationalb_5") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_5") %></textarea>
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
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="eligibilityIa" id="conflict_1" name="comments_operationalb_6" 
                        <%=json_two.get("comments_operationalb_6").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="eligibilityIa" id="comments_operational" name="comments_operationalb_6" 
                        <%=json_two.get("comments_operationalb_6").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_6" id="governancek1" name="remarks_operationalb_6"
                   readonly value="<%=json_two.get("remarks_operationalb_6") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_6" id="governancek1" name="management_operationalb_6"
                    readonly value="<%=json_two.get("management_operationalb_6") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_6") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb7" id="comments_operationalb6" name="comments_operationalb_7" 
                        <%=json_two.get("comments_operationalb_7").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb7" id="comments_operationalb" name="comments_operationalb_7" 
                        <%=json_two.get("comments_operationalb_7").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_7" id="remarks_operationalb6" name="remarks_operationalb_7"
                   readonly value="<%=json_two.get("remarks_operationalb_7") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_7" id="management_operationalb6" name="management_operationalb_7"
                    readonly value="<%=json_two.get("management_operationalb_7") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_7") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb7" id="comments_operationalb7" name="comments_operationalb_8" 
                        <%=json_two.get("comments_operationalb_8").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb7" id="operationalb7" name="comments_operationalb_8" 
                        <%=json_two.get("comments_operationalb_8").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_8" id="remarks_operationalb7" name="remarks_operationalb_8"
                   readonly value="<%=json_two.get("remarks_operationalb_8") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_8" id="management_operationalb7" name="management_operationalb_8"
                   readonly value="<%=json_two.get("management_operationalb_8") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_8") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb8" id="management_operationalb8" name="comments_operationalb_9" 
                        <%=json_two.get("comments_operationalb_9").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb8" id="operationalb8" name="comments_operationalb_9" 
                        <%=json_two.get("comments_operationalb_9").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_9" id="remarks_operationalb8" name="remarks_operationalb_9"
                   readonly value="<%=json_two.get("remarks_operationalb_9") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_9" id="management_operationalb8" name="management_operationalb_9"
                    readonly value="<%=json_two.get("management_operationalb_9") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_9" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_9") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb9" id="operationalb" name="comments_operationalb_10" 
                        <%=json_two.get("comments_operationalb_10").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb9" id="operationalb9" name="comments_operationalb_10" 
                        <%=json_two.get("comments_operationalb_10").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_10" id="governancek1" name="remarks_operationalb_10"
                   readonly value="<%=json_two.get("remarks_operationalb_10") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_10" id="governancek1" name="management_operationalb_10"
                    readonly value="<%=json_two.get("management_operationalb_10") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_10" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_10") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb10" id="operationalb" name="comments_operationalb_11" 
                        <%=json_two.get("comments_operationalb_11").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb10" id="operationalb10" name="comments_operationalb_11" 
                        <%=json_two.get("comments_operationalb_11").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_11" id="governancek1" name="remarks_operationalb_11"
                   readonly value="<%=json_two.get("remarks_operationalb_11") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_11" id="governancek1" name="management_operationalb_11"
                    readonly value="<%=json_two.get("management_operationalb_11") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_11" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_11") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb11" id="operationalb" name="comments_operationalb_12" 
                        <%=json_two.get("comments_operationalb_12").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb11" id="operationalb11" name="comments_operationalb_12" 
                        <%=json_two.get("comments_operationalb_12").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_12" id="governancek1" name="remarks_operationalb_12"
                   readonly value="<%=json_two.get("remarks_operationalb_12") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="remarks_operationalb_12" id="governancek1" name="management_operationalb_12"
                    readonly value="<%=json_two.get("management_operationalb_12") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_12" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_12") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb12" id="operationalb" name="comments_operationalb_13" 
                        <%=json_two.get("comments_operationalb_13").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb12" id="operationalb12" name="comments_operationalb_13" 
                        <%=json_two.get("comments_operationalb_13").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_13" id="governancek1" name="remarks_operationalb_13"
                   readonly value="<%=json_two.get("remarks_operationalb_13") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_13" id="governancek1" name="management_operationalb_13"
                   readonly value="<%=json_two.get("management_operationalb_13") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_13" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_13") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb13" id="operationalb" name="comments_operationalb_14" 
                        <%=json_two.get("comments_operationalb_14").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb13" id="operationalb13" name="comments_operationalb_14" 
                        <%=json_two.get("comments_operationalb_14").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_14" id="governancek1" name="remarks_operationalb_14"
                   readonly value="<%=json_two.get("remarks_operationalb_14") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_14" id="governancek1" name="management_operationalb_14"
                    readonly value="<%=json_two.get("management_operationalb_14") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_14" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_14") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb14" id="operationalb" name="comments_operationalb_15" 
                        <%=json_two.get("comments_operationalb_15").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb14" id="operationalb14" name="comments_operationalb_15" 
                        <%=json_two.get("comments_operationalb_15").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_15" id="governancek1" name="remarks_operationalb_15"
                   readonly value="<%=json_two.get("remarks_operationalb_15") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_15" id="governancek1" name="management_operationalb_15"
                    readonly value="<%=json_two.get("management_operationalb_15") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_15" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_15") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb15" id="operationalb" name="comments_operationalb_16" 
                        <%=json_two.get("comments_operationalb_16").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb15" id="operationalb15" name="comments_operationalb_16" 
                        <%=json_two.get("comments_operationalb_16").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                    <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_16" id="governancek1" name="remarks_operationalb_16"
                   readonly value="<%=json_two.get("remarks_operationalb_16") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_16" id="governancek1" name="management_operationalb_16"
                   readonly value="<%=json_two.get("management_operationalb_16") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_16" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_16") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb16" id="conflict" name="comments_operationalb_17" 
                        <%=json_two.get("comments_operationalb_17").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb16" id="conflict_2" name="comments_operationalb_17" 
                        <%=json_two.get("comments_operationalb_17").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_17" id="governancek1" name="remarks_operationalb_17"
                   readonly value="<%=json_two.get("remarks_operationalb_17") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_17" id="governancek1" name="management_operationalb_17"
                   readonly value="<%=json_two.get("management_operationalb_17") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_17" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_17") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb17" id="operationalb" name="comments_operationalb_18" 
                        <%=json_two.get("comments_operationalb_18").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb17" id="operationalb17" name="comments_operationalb_18" 
                        <%=json_two.get("comments_operationalb_18").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_18" id="governancek1" name="remarks_operationalb_18"
                   readonly value="<%=json_two.get("remarks_operationalb_18") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_18" id="governancek1" name="management_operationalb_18"
                   readonly value="<%=json_two.get("management_operationalb_18") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_18" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_18") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb18" id="operationalb" name="comments_operationalb_19" 
                        <%=json_two.get("comments_operationalb_19").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb18" id="operationalb18" name="comments_operationalb_19" 
                        <%=json_two.get("comments_operationalb_19").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_19" id="governancek1" name="remarks_operationalb_19"
                  readonly value="<%=json_two.get("remarks_operationalb_19") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_19" id="governancek1" name="management_operationalb_19"
                   readonly value="<%=json_two.get("management_operationalb_19") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_19" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_19") %></textarea>
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
                        <input type="radio" disabled="disabled" class="operationalb19" id="operationalb" name="comments_operationalb_20" 
                        <%=json_two.get("comments_operationalb_20").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="operationalb19" id="operationalb19" name="comments_operationalb_20"
                        <%=json_two.get("comments_operationalb_20").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_operationalb_20" id="governancek1" name="remarks_operationalb_20"
                 readonly value="<%=json_two.get("remarks_operationalb_20") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_20" id="governancek1" name="management_operationalb_20"
                  readonly value="<%=json_two.get("management_operationalb_20") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_20" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_20") %></textarea>
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
	                   <input type="text" class="remarks_operationalb_21" id="governancek1" name="remarks_operationalb_21"
	                 readonly value="<%=json_two.get("remarks_operationalb_21") %>">
	               </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_21" id="governancek1" name="management_operationalb_21"
                   readonly value="<%=json_two.get("management_operationalb_21") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_21" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_21") %></textarea>
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
                   <input type="text" class="remarks_operationalb_22" id="governancek1" name="remarks_operationalb_22"
                  readonly value="<%=json_two.get("remarks_operationalb_22") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_operationalb_22" id="governancek1" name="management_operationalb_22"
                   readonly value="<%=json_two.get("management_operationalb_22") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="operational_rem_22" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("operational_rem_22") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc1" id="timelyc" name="comments_timelyc_1" 
                        <%=json_two.get("comments_timelyc_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc1" id="timelyc1" name="comments_timelyc_1" 
                        <%=json_two.get("comments_timelyc_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_1" id="governancek1" name="remarks_timelyc_1"
                  readonly value="<%=json_two.get("remarks_timelyc_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="mangement_timelyc_1" id="governancek1" name="management_timelyc_1"
                  readonly value="<%=json_two.get("management_timelyc_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc2" id="timelyc" name="comments_timelyc_2" 
                         <%=json_two.get("comments_timelyc_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc2" id="timelyc2" name="comments_timelyc_2" 
                         <%=json_two.get("comments_timelyc_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_2" id="governancek1" name="remarks_timelyc_2"
                 readonly value="<%=json_two.get("remarks_timelyc_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="mangement_timelyc_2" id="governancek1" name="management_timelyc_2"
                  readonly value="<%=json_two.get("management_timelyc_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc3" id="timelyc3" name="comments_timelyc_3" 
                         <%=json_two.get("comments_timelyc_3").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc3" id="conflict_2" name="comments_timelyc_3" 
                         <%=json_two.get("comments_timelyc_3").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_3" id="governancek1" name="remarks_timelyc_3"
                 readonly value="<%=json_two.get("remarks_timelyc_3") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_3" id="governancek1" name="management_timelyc_3"
                  readonly value="<%=json_two.get("management_timelyc_3") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_3") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc4" id="timelyc" name="comments_timelyc_4" 
                         <%=json_two.get("comments_timelyc_4").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc4" id="timelyc4" name="comments_timelyc_4" 
                         <%=json_two.get("comments_timelyc_4").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_4" id="governancek1" name="remarks_timelyc_4"
                  readonly value="<%=json_two.get("remarks_timelyc_4") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_4" id="governancek1" name="management_timelyc_4"
                   readonly value="<%=json_two.get("management_timelyc_4") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_4") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc5" id="timelyc" name="comments_timelyc_5" 
                         <%=json_two.get("comments_timelyc_5").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc5" id="timelyc5" name="comments_timelyc_5" 
                         <%=json_two.get("comments_timelyc_5").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_5" id="governancek1" name="remarks_timelyc_5"
                 readonly value="<%=json_two.get("remarks_timelyc_5") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_5" id="governancek1" name="management_timelyc_5"
                 readonly value="<%=json_two.get("management_timelyc_5") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_5") %></textarea>
                  </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p>6</p>
                  </div>
                  <div class="col-md-3">
                     <p>
                     </p>Daily saleable Holding Report
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc6" id="timelyc" name="comments_timelyc_6" 
                         <%=json_two.get("comments_timelyc_6").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc6" id="timelyc6" name="comments_timelyc_6" 
                         <%=json_two.get("comments_timelyc_6").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_6" id="governancek1" name="remarks_timelyc_6"
                  readonly value="<%=json_two.get("remarks_timelyc_6") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_6" id="governancek1" name="management_timelyc_6"
                   readonly value="<%=json_two.get("management_timelyc_6") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_6") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc7" id="timelyc" name="comments_timelyc_7" 
                         <%=json_two.get("comments_timelyc_7").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc7" id="timelyc7" name="comments_timelyc_7"
                         <%=json_two.get("comments_timelyc_7").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_7" id="governancek1" name="remarks_timelyc_7"
                  readonly value="<%=json_two.get("remarks_timelyc_7") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_7" id="governancek1" name="management_timelyc_7"
                  readonly value="<%=json_two.get("management_timelyc_7") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_7") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc8" id="timelyc" name="comments_timelyc_8" 
                         <%=json_two.get("comments_timelyc_8").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>8
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc8" id="timelyc8" name="comments_timelyc_8" 
                         <%=json_two.get("comments_timelyc_8").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_8" id="governancek1" name="remarks_timelyc_8"
                 readonly value="<%=json_two.get("remarks_timelyc_8") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_8" id="governancek1" name="management_timelyc_8"
                  readonly value="<%=json_two.get("management_timelyc_8") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_8") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc8" id="timelyc" name="comments_timelyc_9"
                         <%=json_two.get("comments_timelyc_9").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc8" id="timelyc8" name="comments_timelyc_9" 
                         <%=json_two.get("comments_timelyc_9").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_9" id="governancek1" name="remarks_timelyc_9"
                 readonly value="<%=json_two.get("remarks_timelyc_9") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_9" id="governancek1" name="management_timelyc_9"
                  readonly value="<%=json_two.get("management_timelyc_9") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_9" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_9") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc10" id="timelyc" name="comments_timelyc_10" 
                         <%=json_two.get("comments_timelyc_10").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc10" id="timelyc10" name="comments_timelyc_10" 
                         <%=json_two.get("comments_timelyc_10").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_10" id="governancek1" name="remarks_timelyc_10"
                  readonly value="<%=json_two.get("remarks_timelyc_10") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_10" id="governancek1" name="management_timelyc_10"
                   readonly value="<%=json_two.get("management_timelyc_10") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_10" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_10") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc11" id="timelyc" name="comments_timelyc_11" 
                         <%=json_two.get("comments_timelyc_11").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc11" id="timelyc11" name="comments_timelyc_11" 
                         <%=json_two.get("comments_timelyc_11").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_11" id="governancek1" name="remarks_timelyc_11"
                readonly value="<%=json_two.get("remarks_timelyc_11") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_11" id="governancek1" name="management_timelyc_11"
                 readonly value="<%=json_two.get("management_timelyc_11") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_11" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_11") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc12" id="timelyc" name="comments_timelyc_12" 
                         <%=json_two.get("comments_timelyc_12").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc12" id="timelyc12" name="comments_timelyc_12"
                         <%=json_two.get("comments_timelyc_12").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_12" id="governancek1" name="remarks_timelyc_12"
                  readonly value="<%=json_two.get("remarks_timelyc_12") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_12" id="governancek1" name="management_timelyc_12"
                   readonly value="<%=json_two.get("management_timelyc_12") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_12" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_12") %></textarea>
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
                        <input type="radio" disabled="disabled" class="timelyc13" id="timelyc" name="comments_timelyc_13" 
                         <%=json_two.get("comments_timelyc_13").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="timelyc13" id="timelyc13" name="comments_timelyc_13" 
                         <%=json_two.get("comments_timelyc_13").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_timelyc_13" id="governancek1" name="remarks_timelyc_13"
                  readonly value="<%=json_two.get("remarks_timelyc_13") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_timelyc_13" id="governancek1" name="management_timelyc_13"
                   readonly value="<%=json_two.get("management_timelyc_13") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="timely_rem_13" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("timely_rem_13") %></textarea>
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
                        <input type="radio" disabled="disabled" class="billingd1" id="billingd" name="comments_billingd_1" 
                         <%=json_two.get("comments_billingd_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="billingd1" id="billingd1" name="comments_billingd_1" 
                        <%=json_two.get("comments_billingd_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_billingd_1" id="governancek1" name="remarks_billingd_1"
                  readonly value="<%=json_two.get("remarks_billingd_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="mangement_billingd_1" id="governancek1" name="management_billingd_1"
                   readonly value="<%=json_two.get("management_billingd_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="billingd_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("billingd_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="billingd2" id="billingd" name="comments_billingd_2" 
                         <%=json_two.get("comments_billingd_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="billingd2" id="billingd2" name="comments_billingd_2" 
                        <%=json_two.get("comments_billingd_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_billingd_2" id="governancek1" name="remarks_billingd_2"
                 readonly value="<%=json_two.get("remarks_billingd_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_billingd_2" id="governancek1" name="management_billingd_2"
                   readonly value="<%=json_two.get("management_billingd_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="billingd_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("billingd_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="custodiane1" id="custodiane" name="comments_custodiane_1" 
                        <%=json_two.get("comments_custodiane_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="custodiane1" id="custodiane1" name="comments_custodiane_1" 
                          <%=json_two.get("comments_custodiane_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_custodiane_1" id="governancek1" name="remarks_custodiane_1"
                  readonly value="<%=json_two.get("remarks_custodiane_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_custodiane_1" id="governancek1" name="management_custodiane_1"
                   readonly value="<%=json_two.get("management_custodiane_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("custodiane_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="custodiane2" id="custodiane" name="comments_custodiane_2" 
                          <%=json_two.get("comments_custodiane_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="custodiane2" id="custodiane1" name="comments_custodiane_2" 
                          <%=json_two.get("comments_custodiane_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_custodiane_2" id="governancek1" name="remarks_custodiane_2"
                  readonly value="<%=json_two.get("remarks_custodiane_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_custodiane_2" id="governancek1" name="management_custodiane_2"
                  readonly value="<%=json_two.get("management_custodiane_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("custodiane_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="custodiane3" id="custodiane" name="comments_custodiane_3" 
                          <%=json_two.get("comments_custodiane_3").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="custodiane3" id="custodiane3" name="comments_custodiane_3" 
                          <%=json_two.get("comments_custodiane_3").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_custodiane_3" id="governancek1" name="remarks_custodiane_3"
                  readonly value="<%=json_two.get("remarks_custodiane_3") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_custodiane_3" id="governancek1" name="management_custodiane_3"
                   readonly value="<%=json_two.get("management_custodiane_3") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("custodiane_rem_3") %></textarea>
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
                        <input type="radio" disabled="disabled" class="custodiane4" id="custodiane" name="comments_custodiane_4" 
                          <%=json_two.get("comments_custodiane_4").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="custodiane4" id="custodiane4" name="comments_custodiane_4"
                          <%=json_two.get("comments_custodiane_4").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="place" id="remarks_custodiane_4" name="remarks_custodiane_4"
                  readonly value="<%=json_two.get("remarks_custodiane_4") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="place" id="management_custodiane_4" name="management_custodiane_4"
                   readonly value="<%=json_two.get("management_custodiane_4") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("custodiane_rem_4") %></textarea>
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
                        <input type="radio" disabled="disabled" class="custodiane5" id="custodiane" name="comments_custodiane_5" 
                          <%=json_two.get("comments_custodiane_5").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="custodiane5" id="custodiane5" name="comments_custodiane_5"
                          <%=json_two.get("comments_custodiane_5").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_custodiane_5" id="governancek1" name="remarks_custodiane_5"
                  readonly value="<%=json_two.get("remarks_custodiane_5") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_custodiane_5" id="governancek1" name="management_custodiane_5"
                   readonly value="<%=json_two.get("management_custodiane_5") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="custodiane_rem_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("custodiane_rem_5") %></textarea>
                  </div>
               </div>
               <br>
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
                        <input type="radio" disabled="disabled" class="infrastructuref1" id="infrastructuref" name="comments_infrastructuref_1" 
                          <%=json_two.get("comments_infrastructuref_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref1" id="infrastructuref1" name="comments_infrastructuref_1" 
                        <%=json_two.get("comments_infrastructuref_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_1" id="governancek1" name="remarks_infrastructuref_1"
                  readonly value="<%=json_two.get("remarks_infrastructuref_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_1" id="governancek1" name="management_infrastructuref_1"
                   readonly value="<%=json_two.get("management_infrastructuref_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref2" id="conflict_1" name="comments_infrastructuref_2" 
                        <%=json_two.get("comments_infrastructuref_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref2" id="conflict_2" name="comments_infrastructuref_2"
                        <%=json_two.get("comments_infrastructuref_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_2" id="governancek1" name="remarks_infrastructuref_2"
                  readonly value="<%=json_two.get("remarks_infrastructuref_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_2" id="governancek1" name="management_infrastructuref_2"
                  readonly value="<%=json_two.get("management_infrastructuref_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref3" id="conflict_" name="comments_infrastructuref_3" 
                        <%=json_two.get("comments_infrastructuref_3").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref3" id="conflict_2" name="comments_infrastructuref_3" 
                        <%=json_two.get("comments_infrastructuref_3").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_3" id="governancek1" name="remarks_infrastructuref_3"
                  readonly value="<%=json_two.get("remarks_infrastructuref_3") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_3" id="governancek1" name="management_infrastructuref_3"
                   readonly value="<%=json_two.get("management_infrastructuref_3") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_3") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref4" id="conflict_1" name="comments_infrastructuref_4" 
                        <%=json_two.get("comments_infrastructuref_4").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref4" id="conflict_2" name="comments_infrastructuref_4" 
                        <%=json_two.get("comments_infrastructuref_4").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_4" id="governancek1" name="remarks_infrastructuref_4"
                  readonly value="<%=json_two.get("remarks_infrastructuref_4") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_4" id="governancek1" name="management_infrastructuref_4"
                   readonly value="<%=json_two.get("management_infrastructuref_4") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_4") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref5" id="infrastructuref" name="comments_infrastructuref_5" 
                        <%=json_two.get("comments_infrastructuref_5").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref5" id="infrastructuref5" name="comments_infrastructuref_5" 
                        <%=json_two.get("comments_infrastructuref_5").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_5" id="governancek1" name="remarks_infrastructuref_5"
                  readonly value="<%=json_two.get("remarks_infrastructuref_5") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_5" id="governancek1" name="management_infrastructuref_5"
                   readonly value="<%=json_two.get("management_infrastructuref_5") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_5") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref6" id="infrastructuref" name="comments_infrastructuref_6" 
                        <%=json_two.get("comments_infrastructuref_6").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="eligibilityIa" id="infrastructuref6" name="commets_infrastructuref_6" 
                        <%=json_two.get("comments_infrastructuref_6").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_6" id="governancek1" name="remarks_infrastructuref_6"
                  readonly value="<%=json_two.get("remarks_infrastructuref_6") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_6" id="governancek1" name="management_infrastructuref_6"
                   readonly value="<%=json_two.get("management_infrastructuref_6") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_6") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref7" id="infrastructuref" name="comments_infrastructuref_7" 
                        <%=json_two.get("comments_infrastructuref_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref7" id="infrastructuref7" name="comments_infrastructuref_7" 
                        <%=json_two.get("comments_infrastructuref_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_7" id="governancek1" name="remarks_infrastructuref_7"
                  readonly value="<%=json_two.get("remarks_infrastructuref_7") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_7" id="governancek1" name="management_infrastructuref_7"
                   readonly value="<%=json_two.get("management_infrastructuref_7") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_7" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_7") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref8" id="infrastructuref" name="comments_infrastructuref_8" 
                        <%=json_two.get("comments_infrastructuref_8").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref8" id="infrastructuref8" name="comments_infrastructuref_8" 
                        <%=json_two.get("comments_infrastructuref_8").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_8" id="governancek1" name="remarks_infrastructuref_8"
                  readonly value="<%=json_two.get("remarks_infrastructuref_8") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_8" id="governancek1" name="management_infrastructuref_8"
                   readonly value="<%=json_two.get("management_infrastructuref_8") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_8" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_8") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref9" id="infrastructuref" name="comments_infrastructuref_9" 
                        <%=json_two.get("comments_infrastructuref_9").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref9" id="infrastructuref9" name="comments_infrastructuref_9" 
                        <%=json_two.get("comments_infrastructuref_9").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_9" id="governancek1" name="remarks_infrastructuref_9"
                  readonly value="<%=json_two.get("remarks_infrastructuref_9") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_9" id="governancek1" name="management_infrastructuref_9"
                   readonly value="<%=json_two.get("management_infrastructuref_9") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_9" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_9") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref10" id="infrastructuref1" name="comments_infrastructuref_10" 
                        <%=json_two.get("comments_infrastructuref_10").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref10" id="infrastructuref10" name="comments_infrastructuref_10"
                        <%=json_two.get("comments_infrastructuref_10").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_10" id="governancek1" name="remarks_infrastructuref_10"
                  readonly value="<%=json_two.get("remarks_infrastructuref_10") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_10" id="governancek1" name="management_infrastructuref_10"
                  readonly value="<%=json_two.get("management_infrastructuref_10") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_10" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_10") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref11" id="infrastructure" name="comments_infrastructuref_11" 
                        <%=json_two.get("comments_infrastructuref_11").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref11" id="infrastructuref11" name="comments_infrastructuref_11"
                        <%=json_two.get("comments_infrastructuref_11").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_11" id="governancek1" name="remarks_infrastructuref_11"
                  readonly value="<%=json_two.get("remarks_infrastructuref_11") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_2" id="governancek1" name="management_infrastructuref_11"
                  readonly value="<%=json_two.get("management_infrastructuref_11") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_11" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_11") %></textarea>
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
                        <input type="radio" disabled="disabled" class="infrastructuref12" id="infrastructuref1" name="comments_infrastructuref_12" 
                        <%=json_two.get("comments_infrastructuref_12").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="infrastructuref12" id="infrastructuref12" name="comments_infrastructuref_12" 
                        <%=json_two.get("comments_infrastructuref_12").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_infrastructuref_12" id="governancek1" name="remarks_infrastructuref_12"
                  readonly value="<%=json_two.get("remarks_infrastructuref_12") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_infrastructuref_12" id="governancek1" name="management_infrastructuref_12"
                   readonly value="<%=json_two.get("management_infrastructuref_12") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="infrastructuref_rem_12" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("infrastructuref_rem_12") %></textarea>
                  </div>
               </div>
               <br>
<!-- F  Protection of Assets of  Beneficial Owners  end -->      
              
<!-- G  Protection of Assets of  Beneficial Owners  start -->      
               <div class="row">
                 <div class="col-md-1">
                   <p><b>G.</b></p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Protection of Assets of  Beneficial Owners </b></p> 
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
                        <input type="radio" disabled="disabled" class="protectiong1" id="protectiong" name="comments_protectiong_1" 
                        <%=json_two.get("comments_protectiong_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="protectiong1" id="protectiong1" name="comments_protectiong_1" 
                        <%=json_two.get("comments_protectiong_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_protectiong_1" id="governancek1" name="remarks_protectiong_1"
                  readonly value="<%=json_two.get("remarks_protectiong_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_protectiong_1" id="governancek1" name="management_protectiong_1"
                   readonly value="<%=json_two.get("management_protectiong_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("protectiong_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="protectiong2" id="conflict_1" name="comments_protectiong_2" 
                        <%=json_two.get("comments_protectiong_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="protectiong2" id="conflict_2" name="comments_protectiong_2" 
                        <%=json_two.get("comments_protectiong_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_protectiong_2" id="ma" name="remarks_protectiong_2"
                  readonly value="<%=json_two.get("remarks_protectiong_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_protectiong_2" id="governancek1" name="management_protectiong_2"
                   readonly value="<%=json_two.get("management_protectiong_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("protectiong_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="protectiong3" id="protectiong" name="comments_protectiong_3" 
                        <%=json_two.get("comments_protectiong_3").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="protectiong3" id="protectiong3" name="comments_protectiong_3" 
                        <%=json_two.get("comments_protectiong_3").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_protectiong_3" id="governancek1" name="remarks_protectiong_3"
                  readonly value="<%=json_two.get("remarks_protectiong_3") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_protectiong_3" id="governancek1" name="management_protectiong_3"
                   readonly value="<%=json_two.get("management_protectiong_3") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("protectiong_rem_3") %></textarea>
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
                        <input type="radio" disabled="disabled" class="protectiong4" id="protectiong" name="comments_protectiong_4" 
                        <%=json_two.get("comments_protectiong_4").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="protectiong4" id="protectiong4" name="comments_protectiong_4" 
                        <%=json_two.get("comments_protectiong_4").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_protectiong_4" id="governancek1" name="remarks_protectiong_4"
                  readonly value="<%=json_two.get("remarks_protectiong_4") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_protectiong_4" id="governancek1" name="management_protectiong_4"
                   readonly value="<%=json_two.get("management_protectiong_4") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("protectiong_rem_4") %></textarea>
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
                        <input type="radio" disabled="disabled" class="protectiong5" id="protectiong" name="comments_protectiong_5" 
                        <%=json_two.get("comments_protectiong_5").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="protectiong5" id="protectiong5" name="comments_protectiong_5" 
                        <%=json_two.get("comments_protectiong_5").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_protectiong_5" id="governancek1" name="remarks_protectiong_5"
                  readonly value="<%=json_two.get("remarks_protectiong_5") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_protectiong_5" id="governancek1" name="management_protectiong_5"
                  readonly value="<%=json_two.get("management_protectiong_5") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_5" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("protectiong_rem_5") %></textarea>
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
                        <input type="radio" disabled="disabled" class="protectiong6" id="protectiong" name="comments_protectiong_6" 
                        <%=json_two.get("comments_protectiong_6").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="protectiong6" id="protectiong6" name="comments_protectiong_6" 
                        <%=json_two.get("comments_protectiong_6").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_protectiong_6" id="governancek" name="remarks_protectiong_6"
                  readonly value="<%=json_two.get("remarks_protectiong_6") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_protectiong_6" id="governancek1" name="management_protectiong_6"
                  readonly value="<%=json_two.get("management_protectiong_6") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="protectiong_rem_6" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("protectiong_rem_6") %></textarea>
                  </div>
               </div>
               <br>
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
                        <input type="radio" disabled="disabled" class="recordh1" id="recordh" name="comments_recordh_1" 
                        <%=json_two.get("comments_recordh_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="recordh1" id="recordh1" name="comments_recordh_1" 
                        <%=json_two.get("comments_recordh_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_recordh_1" id="governancek1" name="remarks_recordh_1"
                  readonly value="<%=json_two.get("remarks_recordh_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_recordh_1" id="governancek1" name="management_recordh_1"
                   readonly value="<%=json_two.get("management_recordh_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="recordh_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("recordh_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="recordh2" id="conflict_1" name="comments_recordh_2" 
                        <%=json_two.get("comments_recordh_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="recordh2" id="conflict_2" name="comments_recordh_2" 
                        <%=json_two.get("comments_recordh_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="remarks_recordh_2" id="governancek1" name="remarks_recordh_2"
                  readonly value="<%=json_two.get("remarks_recordh_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_recordh_2" id="governancek1" name="management_recordh_2"
                   readonly value="<%=json_two.get("management_recordh_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="recordh_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("recordh_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="recordh3" id="recordh3" name="comments_recordh_3" 
                        <%=json_two.get("comments_recordh_3").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="recordh3" id="recordh" name="comments_recordh_3" 
                        <%=json_two.get("comments_recordh_3").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_recordh_3" id="governancek1" name="remarks_recordh_3"
                  readonly value="<%=json_two.get("remarks_recordh_3") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_recordh_3" id="governancek1" name="management_recordh_3"
                   readonly value="<%=json_two.get("management_recordh_3") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="recordh_rem_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("recordh_rem_3") %></textarea>
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
                        <input type="radio" disabled="disabled" class="recordh4" id="recordh4" name="comments_recordh_4" 
                        <%=json_two.get("comments_recordh_4").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="recordh4" id="conflict_2" name="comments_recordh_4" 
                        <%=json_two.get("comments_recordh_4").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_recordh_4" id="governancek1" name="remarks_recordh_4"
                  readonly value="<%=json_two.get("remarks_recordh_4") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_recordh_4" id="governancek1" name="management_recordh_4"
                   readonly value="<%= json_two.get("management_recordh_4") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="recordh_rem_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("recordh_rem_4") %></textarea>
                  </div>
               </div>
               <br>
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
                        <input type="radio" disabled="disabled" class="grievancei1" id="grievancei1" name="commets_grievancei_1" 
                        <%=json_two.get("commets_grievancei_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="grievancei1" id="conflict_2" name="commets_grievancei_1"
                         <%=json_two.get("commets_grievancei_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_grievancei_1" id="governancek1" name="remarks_grievancei_1"
                 readonly value="<%=json_two.get("remarks_grievancei_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_grievancei_1" id="governancek1" name="management_grievancei_1"
                   readonly value="<%=json_two.get("management_grievancei_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="grievancei_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("grievancei_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="grievancei2" id="grievance2" name="commets_grievancei_2"
                         <%=json_two.get("commets_grievancei_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="grievancei2" id="grievancei2" name="commets_grievancei_2" 
                         <%=json_two.get("commets_grievancei_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_grievancei_2" id="governancek1" name="remarks_grievancei_2"
                 readonly value="<%=json_two.get("remarks_grievancei_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_grievancei_2" id="governancek1" name="management_grievancei_2"
                  readonly value="<%=json_two.get("management_grievancei_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="grievancei_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("grievancei_rem_2") %></textarea>
                  </div>
               </div>
               <br>
              
              
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
                        <input type="radio" disabled="disabled" class="otherj1" id="otherj1" name="comments_otherj_1" 
                         <%=json_two.get("comments_otherj_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="otherj1" id="otherj1" name="comments_otherj_1" 
                         <%=json_two.get("comments_otherj_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_otherj_1" id="governancek1" name="remarks_otherj_1"
                  readonly value="<%=json_two.get("remarks_otherj_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_otherj_1" id="governancek1" name="management_otherj_1"
                  readonly value="<%=json_two.get("management_otherj_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="otherj_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("otherj_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="otherj2" id="conflict_1" name="comments_otherj_2" 
                         <%=json_two.get("comments_otherj_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="otherj2" id="conflict_2" name="comments_otherj_2" 
                         <%=json_two.get("comments_otherj_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                 <div class="col-md-2">
                   <input type="text" class="remarks_otherj_2" id="governancek1" name="remarks_otherj_2"
                  readonly value="<%=json_two.get("remarks_otherj_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_otherj_2" id="governancek1" name="management_otherj_2"
                   readonly value="<%=json_two.get("management_otherj_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="otherj_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("otherj_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="governancek1" id="governance" name="comments_governancek_1" 
                         <%=json_two.get("comments_governancek_1").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="governancek1" id="governancek" name="comments_governancek_1" 
                        <%=json_two.get("comments_governancek_1").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_governancek_1" id="governancek1" name="remarks_governancek_1"
                  readonly value="<%=json_two.get("remarks_governancek_1") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_governancek_1" id="governancek1" name="management_governancek_1"
                  readonly value="<%=json_two.get("management_governancek_1") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="governancek_rem_1" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("governancek_rem_1") %></textarea>
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
                        <input type="radio" disabled="disabled" class="governancek2" id="governancek2" name="comments_governancek_2" 
                        <%=json_two.get("comments_governancek_2").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="governancek2" id="conflict_2" name="comments_governancek_2" 
                        <%=json_two.get("comments_governancek_2").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_governancek_2" id="governancek2" name="remarks_governancek_2"
                  readonly value="<%=json_two.get("remarks_governancek_2") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_governancek_2" id="governancek2" name="management_governancek_2"
                   readonly value="<%=json_two.get("management_governancek_2") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="governancek_rem_2" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("governancek_rem_2") %></textarea>
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
                        <input type="radio" disabled="disabled" class="governancek3" id="conflict_1" name="comments_governancek_3" 
                        <%=json_two.get("comments_governancek_3").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="governancek3" id="conflict_2" name="comments_governancek_3" 
                        <%=json_two.get("comments_governancek_3").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remakrs_governancek_3" id="place" name="remarks_governancek_3"
                  readonly value="<%=json_two.get("remarks_governancek_3") %>">
                  </div>
                  
                   <div class="col-md-2">
                    <input type="text" class="management_governancek_3" id="place" name="management_governancek_3"
                   readonly value="<%=json_two.get("management_governancek_3") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="governancek_rem_3" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("governancek_rem_3") %></textarea>
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
                        <input type="radio" disabled="disabled" class="governancek4" id="governancek3" name="comments_governancek_4" 
                        <%=json_two.get("comments_governancek_4").equals("Yes") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" disabled="disabled" class="governancek4" id="governancek3" name="comments_governancek_4" 
                        <%=json_two.get("comments_governancek_4").equals("No") ? "checked" : "" %> readonly>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   <input type="text" class="remarks_governancek_4" id="comments_governancek_3" name="remarks_governancek_4" 
                  readonly value="<%=json_two.get("remarks_governancek_4") %>">
                  </div>
                  
                   <div class="col-md-2">
                   <input type="text" class="management_governancek_4" id="place" name="management_governancek_4"
                  readonly value="<%=json_two.get("management_governancek_4") %>">
                   </div>

                   <div class="col-md-2">
                    <textarea  id="monitoring_rem" placeholder="Remarks if any" name="governancek_rem_4" <%=isNonNPSUser ? "disabled": "" %>><%=json_four.get("governancek_rem_4") %></textarea>
                  </div>
               </div>
               <hr>
<!--  k  Governance Structure of Custodian   end -->
               <br>
               <div class="row text-center">
                  <div class="col-md-12">
                     <%-- <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" <%=isNonNPSUser ? "disabled": "" %> value="Submit"> --%>
                     <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" <%=isNonNPSUser ? "disabled": "" %>>Submit</button>
                      <a class="button" id="pop-up-trigger"  href="#success-modal"></a>
                  </div>
               </div>
            </form>
         </div>
      </div>
   </div>
</div>



<style>
	select {
    background-color: #E9F3FC;
    color: #111111;
    border-radius: 5px;
    padding: 5px 20px;
}

.common-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    min-width: 130px;
    height: 50px;
    border-radius: 5px;
    color: #ffffff;
    background: linear-gradient(90deg, #0E62B1 0%, #3195F3 100%);
    transition: all 0.5s;
    text-transform: uppercase;
}

	input.error {
		border-color: red;
	}
	
	.modal-open .modal {
    display: block !important;
    overflow-y: hidden;
}

/*NEW MODAL UI PROPERTIES*/
.custom-modal-ui {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	background: rgba(0, 0, 0, 0.7);
	transition: opacity 500ms;
	visibility: hidden; 
	opacity: 0;
	z-index: 999;
}

.custom-modal-ui:target {
	visibility: visible;
	opacity: 1;
}

.modal-head-ui {
	margin: 15% auto;
	background: #fff;
	border-radius: 5px;
	width: 30%;
	position: relative;
	transition: all 2s ease-in-out;
}

.modal-head-ui .modal-close-ui {
	position: absolute;
    top: 5px;
    right: 15px;
    transition: all 200ms;
    font-size: 30px;
    font-weight: 400;
    text-decoration: none;
    color: #817d7d;
}

.modal-head-ui .modal-content-ui {
	max-height: 30%;
	overflow: auto;
	padding: 25px;
}

.modal-title-ui {
    border-bottom: 1px solid #ddd;
    padding: 15px;
    color: #fff;
}

button[disabled], button[disabled]:hover, input[type=button]:disabled {
    border: 1px solid #e5e5e5;
    background: #e5e5e5;
    color: #cec9c9 !important;     
    cursor: not-allowed;
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
	$( ".x-mark" ).click(function() {
		$("#success-modal").addClass("hide");
		
		if($("#icon").hasClass("fas fa-check-circle text-success fa-4x d-block mb-4")){
			$("#icon").removeClass("fas fa-check-circle text-success fa-4x d-block mb-4");
		}
		if($("#icon").hasClass("fas fa-times-circle text-danger  fa-4x d-block mb-4")){
			$("#icon").removeClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
		}
		
	});
	

	
	$("#annualAuditReport").on('submit', (function(e) {
		console.log("Inside ajax");
		
		e.preventDefault();
		
				//default
				if($("#success-modal").hasClass("hide")){
					$("#success-modal").removeClass("hide");
				}
				
					var formData = new FormData($("#annualAuditReport")[0]);
					
					let scrURL = "/web/guest/annual-audit-report?p_p_id=Cut_Annual_Audit_Report_Cut_Annual_Audit_ReportPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=Savecut_annualaditScrutinyReport&p_p_cacheability=cacheLevelPage";
					
					$(".animaion").show();
					$.ajax({
			            type: "POST",
			            enctype: 'multipart/form-data',
			            processData: false,
			            contentType: false,
			            cache: false,
			            url: scrURL,
			            data: formData,
			            success: function(data) {
			            	$(".animaion").hide();
			            	if(data == "Success") {
			        			//success message
			        	 		$('#output').html(' Data sent for Review.');
			        			$("#icon").addClass("fas fa-check-circle text-success fa-4x d-block mb-4");
			        			$('.x-mark').attr('href', "#");
		   		            	$("#annualAuditReport")[0].reset();
			            	} else {
			            		console.log("Error Occured in ajax call");
			        			//error message
			        			$('#output').html(' An error occured while submitting the form. Please try again.');
			        			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
			            	}
			            },
			            error: function() {
			            	$(".animaion").hide();
		        			//error message
		        			$('#output').html(' An error occured while submitting the form. Please try again.');
		        			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
			            	console.log("Error Occured in ajax call");
			            },
			            complete: function(){
			            	$(".animaion").hide();
			            	//trigger-message
			            	$("#success-modal").show();
			            	$('#pop-up-trigger')[0].click();
				        }
			
			        });


		}));
    
});

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>
