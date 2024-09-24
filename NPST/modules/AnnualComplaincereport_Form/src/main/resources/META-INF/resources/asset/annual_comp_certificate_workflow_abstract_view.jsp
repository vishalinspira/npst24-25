<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.daily.average.service.service.AnnualCompCertificateScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.AnnualCompCertificateScrutiny"%>
<%@page import="AnnualComplaincereport_Form.util.NPSUserPre_Populate_scrutinydata"%>
<%@ include file="/init.jsp" %>
<%@page import="AnnualComplaincereport_Form.constants.AnnualComplaincereport_FormPortletKeys"%>

<%@page import="com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.AnnualCompCertificate"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.1.1/css/all.min.css" />


<% 
AnnualCompCertificate annualCompCertificatedetails = Validator.isNotNull(request.getAttribute("annualCompCertificate")) ? (AnnualCompCertificate) request.getAttribute("annualCompCertificate") : AnnualCompCertificateLocalServiceUtil.createAnnualCompCertificate(0);

/* pre populate data for asset view */
boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;
boolean isAssignable = Validator.isNotNull(request.getAttribute("isAssignable")) ? (boolean) request.getAttribute("isAssignable") : false;
boolean isSelfAsignee = Validator.isNotNull(request.getAttribute("isSelfAsignee")) ? (boolean) request.getAttribute("isSelfAsignee") : false;
NPSUserPre_Populate_scrutinydata sd = new NPSUserPre_Populate_scrutinydata();
sd.pre_populate_scrutiny_data(request);
AnnualCompCertificateScrutiny annualCompScrutinydetails = Validator.isNotNull(request.getAttribute("annualCompScrutiny")) ? (AnnualCompCertificateScrutiny) request.getAttribute("annualCompScrutiny") : AnnualCompCertificateScrutinyLocalServiceUtil.createAnnualCompCertificateScrutiny(0);
long uploadLogId=annualCompCertificatedetails.getReportUploadLogId();

List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");

boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
boolean isDocSigned = false;
SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
String fileEntryId = "0";
JSONObject signature = JSONFactoryUtil.createJSONObject();

String dDate[]=dateFormat.format(annualCompCertificatedetails.getReportDate()).split("-");

int month=Integer.parseInt(dDate[0]);
int year=Integer.parseInt(dDate[2]);
if(month==1){
	year=year-1;
	month=12;
}else{
	month=month-1;	
}
String formDate1=month+"/"+year;
if(Validator.isNull(annualCompScrutinydetails)){
	annualCompScrutinydetails=AnnualCompCertificateScrutinyLocalServiceUtil.createAnnualCompCertificateScrutiny(0);
}

%>

<!-- <style>
.modal {
    display: none;
	}
	
.modal#success_tic {    
	top: 30%; 
}

.modal-open .modal {
    display: block !important;
    overflow-y: hidden;
}
</style>


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
</div> -->

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
	<div class="panel-body">
		
		<% if(!isDocSigned){ %>
			<button id="signFile" class="btn  btn-primary">Sign File</button>
		<% }else{ %>
			<button id="signFile" class="btn  btn-primary" disabled="disabled">Sign File</button>
			<h6 class="signedmsg">The report has been Authenticated</h6>
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
            <h4>Annual Compliance Certificate</h4>
            <form id="annualComplainsscrutiny" action="#" method="post">
                <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="<%=annualCompCertificatedetails.getReportUploadLogId()%>"/>
				<input type="hidden"value="<%=annualCompCertificatedetails.getReportMasterId()%>" name="reportMasterId" class="reportMasterId"/>
                         <div class="row">
                                 <div class="col-md-9">
                                    <p class="font-weight-bold mb-0">To,</p>
<p class=" font-weight-bold mb-0">National Pension System Trust,</p>
<p class=" font-weight-bold mb-0">Tower B, B-302, Third Floor,</p>
<p class="font-weight-bold mb-0">World Trade Center,</p>
<p class="font-weight-bold mb-0">Nauroji Nagar,</p>
<p class="font-weight-bold mb-0">New Delhi-110029</p>
                                 </div>
                              </div>
                              <br>
               <br>
               <p>Sir,</p>
               <br>
               <p>In my opinion and to the best of my information and according 
               to the examinations carried out by me and explanations furnished to me,
                I certify the following in respect of the year mentioned above.
               </p>
               <br>
                             <hr/>
<div class="row">
<div class="col-md-1">
<h5>SL.NO</h5>
</div>
<div class="col-md-5">
<h5>Parameters </h5>
</div>
<div class="col-md-2">
<h5>Yes/No/NA</h5>
</div>
<div class="col-md-2">
<h5>PFM Remarks</h5>
</div>
<div class="col-md-2">
<h5>NPST Remarks</h5>
</div>
</div>
<hr/>
               
               
               
               <div class="row">
                 <div class="col-md-1">
                   <p>1.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Eligibility requirement related</b></p> 
                 </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>a) Whether Sponsor(s) and PFM are complying withthe eligibility requirements of PFRDA (PF) 
                         regulations 2015 and certificate of registration issued to it by PFRDA.
                     </p>
                  </div>
                 
                  <div class="col-md-2">
                   <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                     <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value=<%=annualCompCertificatedetails.getReportUploadLogId()%>/>
                        <input type="radio" class="eligibilityIa" id="conflict_1" name="eligibilityIa" value="Yes"
                         <%= annualCompCertificatedetails.getEligibilityia().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                        
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIa" id="conflict_2" name="eligibilityIa" value="No"
                        <%= annualCompCertificatedetails.getEligibilityia().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIa" id="conflict_3" name="eligibilityIa"  value="NA"
                        <%= annualCompCertificatedetails.getEligibilityia().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                  <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityia()%></label> 
                         </div>
                     <% } %>
                     </div>
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIa_rem" placeholder="Remarks if any" name="eligibilityIa_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIa_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIa_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIa_npsrem" placeholder="Remarks if any" name="eligibilityIa_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityia_rem() == null ? "" : annualCompScrutinydetails.getEligibilityia_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>b) Whether PFM is conducting its activities in accordance with the PFRDA Act,
                      applicable regulations in force and any guidelines, notifications or circulars 
                      issued by the Authority along with the operational agreement executed between the NPS 
                      Trust and PFM.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIb" id="monitoring_1" name="eligibilityIb" value="Yes"
                         <%= annualCompCertificatedetails.getEligibilityib().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIb" id="monitoring_2" name="eligibilityIb" value="No"
                         <%= annualCompCertificatedetails.getEligibilityib().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIb" id="monitoring_3" name="eligibilityIb" value="NA"
                         <%= annualCompCertificatedetails.getEligibilityib().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                 
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityib()%></label> 
                         </div>
                     <% } %> 
                      </div>                
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIb_rem" placeholder="Remarks if any" name="eligibilityIb_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIb_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIb_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIb_npsrem" placeholder="Remarks if any" name="eligibilityIb_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityib_rem() == null ? "" : annualCompScrutinydetails.getEligibilityib_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>c) Whether Sponsor(s) is maintaining minimum Tangible Net-worth as stipulated by PFRDA
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIc" id="resolutions_1" name="eligibilityIc" value="Yes"
                         <%= annualCompCertificatedetails.getEligibilityic().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIc" id="resolutions_2" name="eligibilityIc" value="No"
                         <%= annualCompCertificatedetails.getEligibilityic().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIc" id="resolutions_3" name="eligibilityIc"  value="NA"
                         <%= annualCompCertificatedetails.getEligibilityic().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityIc_rem()%></label> 
                         </div>
                     <% } %>  
                     </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIc_rem" placeholder="Remarks if any" name="eligibilityIc_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIc_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIc_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIc_npsrem" placeholder="Remarks if any" name="eligibilityIc_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityic_rem() == null ? "" : annualCompScrutinydetails.getEligibilityic_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>d) Whether Sponsor(s) has contributed minimum Tangible Net-worth of the PFM as stipulated by the PFRDA
                     </p>
                  </div>
                   <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                 
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityId" id="resolutions_1" name="eligibilityId" value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityid().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityId" id="resolutions_2" name="eligibilityId" value="No"
                        <%= annualCompCertificatedetails.getEligibilityid().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityId" id="resolutions_3" name="eligibilityId" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityid().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityid()%></label> 
                         </div>
                     <% } %>     
                     </div>            
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityId_rem" placeholder="Remarks if any" name="eligibilityId_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityId_rem() == null ? "" : annualCompCertificatedetails.getEligibilityId_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityId_npsrem" placeholder="Remarks if any" name="eligibilityId_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityid_rem() == null ? "" : annualCompScrutinydetails.getEligibilityid_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>e) Whether direct or indirect holding by a foreign company in the PFM is in compliance to PFRDA Act,
                      regulations and other communications.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIe" id="resolutions_1" name="eligibilityIe" value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityie().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIe" id="resolutions_2" name="eligibilityIe"  value="No"
                        <%= annualCompCertificatedetails.getEligibilityie().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIe" id="resolutions_3" name="eligibilityIe" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityie().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityie()%></label> 
                         </div>
                     <% } %>   
                     </div>            
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIe_rem" placeholder="Remarks if any" name="eligibilityIe_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIe_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIe_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIe_npsrem" placeholder="Remarks if any" name="eligibilityIe_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityie_rem() == null ? "" : annualCompScrutinydetails.getEligibilityie_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>f) Whether annual fee payable to Authority has been paid as specified by the Authority and within the timelines.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIf" id="resolutions_1" name="eligibilityIf"  value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityif().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIf" id="resolutions_2" name="eligibilityIf"  value="No"
                        <%= annualCompCertificatedetails.getEligibilityif().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIf" id="resolutions_3" name="eligibilityIf"  value="NA"
                        <%= annualCompCertificatedetails.getEligibilityif().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityif()%></label> 
                         </div>
                     <% } %>       
                     </div>         
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIf_rem" placeholder="Remarks if any" name="eligibilityIf_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIf_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIf_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIf_npsrem" placeholder="Remarks if any" name="eligibilityIf_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityif_rem() == null ? "" : annualCompScrutinydetails.getEligibilityif_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>g) Whether there is any change in the regulatory license (s) issued to the Sponsor(s).
                        Statement showing current status of the sponsor(s) regulatory licenses is provided in Annexure1
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIg" id="resolutions_1" name="eligibilityIg" value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityig().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIg" id="resolutions_2" name="eligibilityIg" value="No"
                        <%= annualCompCertificatedetails.getEligibilityig().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIg" id="resolutions_3" name="eligibilityIg" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityig().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityig()%></label> 
                         </div>
                     <% } %>   
                     </div>             
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIg_rem" placeholder="Remarks if any" name="eligibilityIg_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIg_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIg_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIg_npsrem" placeholder="Remarks if any" name="eligibilityIg_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityig_rem() == null ? "" : annualCompScrutinydetails.getEligibilityig_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>h) Whether there is any change in the name of the PFM or Sponsor(s) 
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIh" id="resolutions_1" name="eligibilityIh" value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityih().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIh" id="resolutions_2" name="eligibilityIh"  value="No"
                        <%= annualCompCertificatedetails.getEligibilityih().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIh" id="resolutions_3" name="eligibilityIh" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityih().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityih()%></label> 
                         </div>
                     <% } %>   
                     </div>             
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIh_rem" placeholder="Remarks if any" name="eligibilityIh_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIh_rem()== null ? "" : annualCompCertificatedetails.getEligibilityIh_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIh_npsrem" placeholder="Remarks if any" name="eligibilityIh_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityih_rem() == null ? "" : annualCompScrutinydetails.getEligibilityih_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>i) Whether sponsor(s) periodically review the activities of the pension fund.(Incase of irregularities sponsor shall immediately report to the Authority.)
                     
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIi" id="resolutions_1" name="eligibilityIi" value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityii().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIi" id="resolutions_2" name="eligibilityIi"  value="No"
                        <%= annualCompCertificatedetails.getEligibilityii().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIi" id="resolutions_3" name="eligibilityIi" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityii().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                 
                  <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityii()%></label> 
                         </div>
                     <% } %>  
                      </div>                
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIi_rem" placeholder="Remarks if any" name="eligibilityIi_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIi_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIi_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIi_npsrem" placeholder="Remarks if any" name="eligibilityIi_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityii_rem() == null ? "" : annualCompScrutinydetails.getEligibilityii_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>j) Whether the sponsor of a pension fund or the pension fund itself hold any equity stake in any other pensionfund regulated by the Authority 
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIj" id="resolutions_1" name="eligibilityIj"  value="Yes"
                        <%=annualCompCertificatedetails.getEligibilityij().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIj" id="resolutions_2" name="eligibilityIj" value="No"
                        <%=annualCompCertificatedetails.getEligibilityij().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIj" id="resolutions_3" name="eligibilityIj" value="NA"
                        <%=annualCompCertificatedetails.getEligibilityij().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                     <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityij()%></label> 
                         </div>
                     <% } %>  
                     </div>             
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIj_rem" placeholder="Remarks if any" name="eligibilityIj_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIj_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIj_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIj_npsrem" placeholder="Remarks if any" name="eligibilityIj_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityij_rem() == null ? "" : annualCompScrutinydetails.getEligibilityij_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>k) Whether Sponsor(s) holds directly or indirectly more than permissible stake in CRA, Trustee Bank or Custodian.
                          Statement of sponsor(s) holdingin intermediaries to be provide details in Annexure 2.
                     </p>
                  </div>
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                  
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIk" id="resolutions_1" name="eligibilityIk"   value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityik().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIk" id="resolutions_2" name="eligibilityIk" value="No"
                        <%= annualCompCertificatedetails.getEligibilityik().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIk" id="resolutions_3" name="eligibilityIk" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityik().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                 
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityik()%></label> 
                         </div>
                     <% } %>  
                      </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIk_rem" placeholder="Remarks if any" name="eligibilityIk_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIk_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIk_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIk_npsrem" placeholder="Remarks if any" name="eligibilityIk_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityik_rem() == null ? "" : annualCompScrutinydetails.getEligibilityik_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>l) Whether PFM has executed such agreements as specified by the Authority in the interest of subscribers with the parties, 
                      including other intermediaries, like Investment Management agreement and NDA with NPS Trust, Service contracts such as for custody
                      arrangements and transfer agency of the securities etc., and copy of such agreements have been submitted to NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                  
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIl" id="resolutions_1" name="eligibilityIl" value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityil().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIl" id="resolutions_2" name="eligibilityIl" value="No"
                        <%= annualCompCertificatedetails.getEligibilityil().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIl" id="resolutions_3" name="eligibilityIl" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityil().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityil()%></label> 
                         </div>
                     <% } %>   
                     </div>             
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIl_rem" placeholder="Remarks if any" name="eligibilityIl_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIl_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIl_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIl_npsrem" placeholder="Remarks if any" name="eligibilityIl_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityil_rem() == null ? "" : annualCompScrutinydetails.getEligibilityil_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>m) Whether PFM has failed to take prior approval 
                     from authority of any major change in management or ownership or shareholding pattern or any change 
                     in controlling interest of the Sponsor(s) of the pension fund.Statement regarding Net-worth of the Sponsor(s) 
                     and PFM is provided in Annexure 3.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIm" id="resolutions_1" name="eligibilityIm"  value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityim().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIm" id="resolutions_2" name="eligibilityIm" value="No"
                        <%= annualCompCertificatedetails.getEligibilityim().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIm" id="resolutions_3" name="eligibilityIm" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityim().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityim()%></label> 
                         </div>
                     <% } %>  
                     </div>              
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIm_rem" placeholder="Remarks if any" name="eligibilityIm_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIm_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIm_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIm_npsrem" placeholder="Remarks if any" name="eligibilityIm_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityim_rem() == null ? "" : annualCompScrutinydetails.getEligibilityim_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>n) Whether the sponsor or pension fund or its principal officer or key management 
                     personnel has been convicted by a court forany offence involving moral turpitude, economic offence, securities laws or fraud;
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIn" id="resolutions_1" name="eligibilityIn" value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityin().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIn" id="resolutions_2" name="eligibilityIn" value="No"
                        <%= annualCompCertificatedetails.getEligibilityin().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIn" id="resolutions_3" name="eligibilityIn" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityin().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                 
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityin()%></label> 
                         </div>
                     <% } %>  
                      </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIn_rem" placeholder="Remarks if any" name="eligibilityIn_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIn_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIn_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIn_npsrem" placeholder="Remarks if any" name="eligibilityIn_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityin_rem() == null ? "" : annualCompScrutinydetails.getEligibilityin_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>o) Whether an order of winding up has been passed against the Sponsor(s) or pension fund
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIo" id="resolutions_1" name="eligibilityIo"  value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityio().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIo" id="resolutions_2" name="eligibilityIo"  value="No"
                        <%= annualCompCertificatedetails.getEligibilityio().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIo" id="resolutions_3" name="eligibilityIo" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityio().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityio()%></label> 
                         </div>
                     <% } %>  
                     </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIo_rem" placeholder="Remarks if any" name="eligibilityIo_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIo_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIo_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIo_npsrem" placeholder="Remarks if any" name="eligibilityIo_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityio_rem() == null ? "" : annualCompScrutinydetails.getEligibilityio_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>p) Whether Sponsor(s) or PFM or Key promoter has been declared insolvent
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIp" id="resolutions_1" name="eligibilityIp" value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityip().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIp" id="resolutions_2" name="eligibilityIp" value="No"
                        <%= annualCompCertificatedetails.getEligibilityip().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIp" id="resolutions_3" name="eligibilityIp" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityip().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityip()%></label> 
                         </div>
                     <% } %>  
                     </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIp_rem" placeholder="Remarks if any" name="eligibilityIp_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIp_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIp_rem() %></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIp_npsrem" placeholder="Remarks if any" name="eligibilityIp_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityip_rem() == null ? "" : annualCompScrutinydetails.getEligibilityip_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>q) Whether any order restraining, prohibiting or debarring the Sponsor(s) or PFM or its principal officer or key management personnel 
                     from dealing in securities in the capital market or from accessing the capital market has been passed by any regulatory authority or court
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIq" id="resolutions_1" name="eligibilityIq"   value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityiq().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIq" id="resolutions_2" name="eligibilityIq" value="No"
                        <%= annualCompCertificatedetails.getEligibilityiq().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIq" id="resolutions_3" name="eligibilityIq" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityiq().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityiq()%></label> 
                         </div>
                     <% } %>  
                     </div>              
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIq_rem" placeholder="Remarks if any" name="eligibilityIq_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIq_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIq_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIq_npsrem" placeholder="Remarks if any" name="eligibilityIq_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityiq_rem() == null ? "" : annualCompScrutinydetails.getEligibilityiq_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>r) Whether any order withdrawing or refusing to grant any license or approval to the sponsor or pension fund or its 
                        whole timedirector or managing partner which has a bearing on the capital market, has been passed by the concerned 
                        financialsector regulator or any other regulatory authority
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIr" id="resolutions_1" name="eligibilityIr"  value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityir().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIr" id="resolutions_2" name="eligibilityIr"   value="No"
                        <%= annualCompCertificatedetails.getEligibilityir().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIr" id="resolutions_3" name="eligibilityIr"   value="NA"
                        <%= annualCompCertificatedetails.getEligibilityir().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityir()%></label> 
                         </div>
                     <% } %>  
                     </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIr_rem" placeholder="Remarks if any" name="eligibilityIr_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIr_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIr_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIr_npsrem" placeholder="Remarks if any" name="eligibilityIr_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityir_rem() == null ? "" : annualCompScrutinydetails.getEligibilityir_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p> s) Whether there is any notice served of any action or investigation or other proceedings
                      of any nature whatsoever, against the sponsor orpension fund, or its Chief Executive Officer, 
                      any of its directors or employees, or a related group concern, by anygovernmental or statutory authority which would restrain, 
                      prohibit or otherwise challenge or impede the performance of obligations as sponsor or pension fund of the pension schemes regulated by theAuthority, 
                      and that there isadverse proceedings against it from anyfinancial sector regulator including the RBI, IRDA or SEBI, 
                     of a nature that couldadversely affect the ability to provide the services as sponsor or pension fund for the pension schemesregulated bythe Authority;
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIs" id="resolutions_1" name="eligibilityIs"  value="Yes"
                        <%= annualCompCertificatedetails.getEligibilityis().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIs" id="resolutions_2" name="eligibilityIs" value="No"
                        <%= annualCompCertificatedetails.getEligibilityis().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIs" id="resolutions_3" name="eligibilityIs" value="NA"
                        <%= annualCompCertificatedetails.getEligibilityis().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                 
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getEligibilityis()%></label> 
                         </div>
                     <% } %> 
                      </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIs_rem" placeholder="Remarks if any" name="eligibilityIs_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getEligibilityIs_rem() == null ? "" : annualCompCertificatedetails.getEligibilityIs_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIs_npsrem" placeholder="Remarks if any" name="eligibilityIs_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getEligibilityis_rem() == null ? "" : annualCompScrutinydetails.getEligibilityis_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <hr>
               
                <div class="row">
                 <div class="col-md-1">
                   <p>2.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Books of Accounts, Financial statements, Annual and Periodic reports</b></p> 
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>a) Whether PFM has maintained books and records about the operation 
                     of pension schemes to ensure compliance with the provisions of the Income-tax Act, 
                     the companies Actor under any otherAct in force and in such manner as may be required or called for by the Authority
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIa" id="resolutions_1" name="bookIIa" value="Yes"
                         <%= annualCompCertificatedetails.getBooka().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIa" id="resolutions_2" name="bookIIa" value="No"
                         <%= annualCompCertificatedetails.getBooka().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIa" id="resolutions_3" name="bookIIa" value="NA"
                         <%= annualCompCertificatedetails.getBooka().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getBooka()%></label> 
                         </div>
                     <% } %>  
                       </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="bookIIa_rem" placeholder="Remarks if any" name="bookIIa_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getBookIIa_rem() == null ? "" : annualCompCertificatedetails.getBookIIa_rem()%></textarea>
                    </div>
                 </div>
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="bookIIa_npsrem" placeholder="Remarks if any" name="bookIIa_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getBooka_rem() == null ? "" : annualCompScrutinydetails.getBooka_rem()%></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>b) Whether PFM has prepared financial statements, 
                     annual report in compliance to regulation 19 (1) and schedule VII of PFRDA (PF) Regulations 
                     2015 and PFRDA (Preparation of financial statements and auditors
                      report of schemes under national pension system) guidelines 2012 and subsequent amendments.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIb" id="resolutions_1" name="bookIIb" value="Yes"
                        <%= annualCompCertificatedetails.getBookb().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIb" id="resolutions_2" name="bookIIb" value="No"
                        <%= annualCompCertificatedetails.getBookb().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIb" id="resolutions_3" name="bookIIb" value="NA"
                        <%= annualCompCertificatedetails.getBookb().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getBookb()%></label> 
                         </div>
                     <% } %>   
                     </div>             
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="bookIIb_rem" placeholder="Remarks if any" name="bookIIb_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getBookIIb_rem() == null ? "" : annualCompCertificatedetails.getBookIIb_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="bookIIb_npsrem" placeholder="Remarks if any" name="bookIIb_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getBookb_rem() == null ? "" : annualCompScrutinydetails.getBookb_rem()%></textarea>
                    </div>
                 </div>
               </div>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>c) Whether PFM has furnished to NPS Trust periodic reports including unaudited provisional 
                     financial statements (Balance Sheet, Revenue Account, notes and schedules) of each scheme and annual 
                     report within the specified time.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIc" id="resolutions_1" name="bookIIc" value="Yes"
                        <%= annualCompCertificatedetails.getBookc().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIc" id="resolutions_2" name="bookIIc" value="No"
                        <%= annualCompCertificatedetails.getBookc().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIc" id="resolutions_3" name="bookIIc" value="NA"
                        <%= annualCompCertificatedetails.getBookc().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>

                     <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getBookc()%></label> 
                         </div>
                     <% } %>       
                                       </div>        
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="bookIIc_rem" placeholder="Remarks if any" name="bookIIc_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getBookIIc_rem() == null ? "" : annualCompCertificatedetails.getBookIIc_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="bookIIc_npsrem" placeholder="Remarks if any" name="bookIIc_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getBookc_rem() == null ? "" : annualCompScrutinydetails.getBookc_rem()%></textarea>
                    </div>
                 </div>
               </div>
              
               <hr>
               
                <div class="row">
                 <div class="col-md-1">
                   <p>3.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Audit of Scheme Accounts</b></p> 
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>a) Whether PFM has got its scheme accounts audited by the auditor appointed by the NPS Trust within specified timelines.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="audita" id="resolutions_1" name="audita" value="Yes"
                         <%= annualCompCertificatedetails.getAudita().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="audita" id="resolutions_2" name="audita" value="No"
                         <%= annualCompCertificatedetails.getAudita().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="audita" id="resolutions_3" name="audita" value="NA"
                         <%= annualCompCertificatedetails.getAudita().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                  <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getAudita()%></label> 
                         </div>
                     <% } %> 
                     </div>                 
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="audita_rem" placeholder="Remarks if any" name="audita_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getAudita_rem() == null ? "" : annualCompCertificatedetails.getAudita_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="audita_npsrem" placeholder="Remarks if any" name="audita_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getAudita_rem() == null ? "" : annualCompScrutinydetails.getAudita_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>b)  Whether Audited Annual report and other information have been submitted to NPS Trust after approval of 
                     the board of directors of the pension fund, within specified timelines.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditb" id="resolutions_1" name="auditb" value="Yes"
                         <%= annualCompCertificatedetails.getAuditb().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditb" id="resolutions_2" name="auditb"  value="No"
                         <%= annualCompCertificatedetails.getAuditb().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditb" id="resolutions_3" name="auditb" value="NA"
                         <%= annualCompCertificatedetails.getAuditb().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getAuditb()%></label> 
                         </div>
                     <% } %>   
                     </div>             
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="auditb_rem" placeholder="Remarks if any" name="auditb_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getAuditb_rem() == null ? "" : annualCompCertificatedetails.getAuditb_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="auditb_npsrem" placeholder="Remarks if any" name="auditb_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getAuditb_rem() == null ? "" : annualCompScrutinydetails.getAuditb_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>c) Whether latest audited annual report has been placed on PFMs website within specified timelines.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditc" id="resolutions_1" name="auditc" value="Yes"
                         <%= annualCompCertificatedetails.getAuditc().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditc" id="resolutions_2" name="auditc" value="No"
                         <%= annualCompCertificatedetails.getAuditc().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditc" id="resolutions_3" name="auditc" value="NA"
                         <%= annualCompCertificatedetails.getAuditc().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getAuditc()%></label> 
                         </div>
                     <% } %>
                     </div>                 
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="auditc_rem" placeholder="Remarks if any" name="auditc_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getAuditc_rem() == null ? "" : annualCompCertificatedetails.getAuditc_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="auditc_npsrem" placeholder="Remarks if any" name="auditc_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getAuditc_rem() == null ? "" : annualCompScrutinydetails.getAuditc_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <hr>
               
               
             <div class="row">
                 <div class="col-md-1">
                   <p>4.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Stewardship</b></p> 
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>a) Whether PFM has complied with circular PFRDA/2018/01/PF/01 on Common Stewardship Code dated 04.05.2018.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipa" id="resolutions_1" name="stewardshipa" value="Yes"
                        <%= annualCompCertificatedetails.getStewardshipa().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipa" id="resolutions_2" name="stewardshipa" value="No"
                        <%= annualCompCertificatedetails.getStewardshipa().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipa" id="resolutions_3" name="stewardshipa" value="NA"
                        <%= annualCompCertificatedetails.getStewardshipa().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                  <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getStewardshipa()%></label> 
                         </div>
                     <% } %> 
                     </div>                 
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipa_rem" placeholder="Remarks if any" name="stewardshipa_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getStewardshipa_rem() == null ? "" : annualCompCertificatedetails.getStewardshipa_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipa_npsrem" placeholder="Remarks if any" name="stewardshipa_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getStewardshipa_rem() == null ? "" : annualCompScrutinydetails.getStewardshipa_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>b) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust;and 
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipb" id="resolutions_1" name="stewardshipb" value="Yes"
                        <%= annualCompCertificatedetails.getStewardshipb().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipb" id="resolutions_2" name="stewardshipb"  value="No"
                        <%= annualCompCertificatedetails.getStewardshipb().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipb" id="resolutions_3" name="stewardshipb" value="NA"
                        <%= annualCompCertificatedetails.getStewardshipb().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getStewardshipb()%></label> 
                         </div>
                     <% } %>    
                     </div>             
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipb_rem" placeholder="Remarks if any" name="stewardshipb_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getStewardshipb_rem() == null ? "" : annualCompCertificatedetails.getStewardshipb_rem()%></textarea>
                    </div>
                 </div> 
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipb_npsrem" placeholder="Remarks if any" name="stewardshipb_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getStewardshipb_rem() == null ? "" : annualCompScrutinydetails.getStewardshipb_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>c) Whether, annual voting report has been submitted to the NPS Trust.(Circular PFRDA/2017/17/PF/1 dated 20.04.2017)

                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipc" id="resolutions_1" name="stewardshipc" value="Yes"
                        <%= annualCompCertificatedetails.getStewardshipc().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipc" id="resolutions_2" name="stewardshipc" value="No"
                        <%= annualCompCertificatedetails.getStewardshipc().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipc" id="resolutions_3" name="stewardshipc" value="NA"
                        <%= annualCompCertificatedetails.getStewardshipc().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getStewardshipc()%></label> 
                         </div>
                     <% } %>  
                     </div>              
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipc_rem" placeholder="Remarks if any" name="stewardshipc_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getStewardshipc_rem() == null ? "" : annualCompCertificatedetails.getStewardshipc_rem()%></textarea>
                    </div>
                 </div> 
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipc_npsrem" placeholder="Remarks if any" name="stewardshipc_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getStewardshipc_rem() == null ? "" : annualCompScrutinydetails.getStewardshipc_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <hr>
                 <div class="row">
                 <div class="col-md-1">
                   <p>5.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Others</b></p> 
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>a) Whether all interest, dividends or any such accrual income and proceeds of redemption/sale were collected on due dates 
                     and promptly credited to the scheme accounts.Statement showing amount of income accrued but not realized as on closing date of 
                     the financial year is provided in Annexure 4.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersa" id="resolutions_1" name="othersa" value="Yes"
                        <%= annualCompCertificatedetails.getOthersa().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersa" id="resolutions_2" name="othersa" value="No"
                        <%= annualCompCertificatedetails.getOthersa().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersa" id="resolutions_3" name="othersa" value="NA"
                        <%= annualCompCertificatedetails.getOthersa().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getOthersa()%></label> 
                         </div>
                     <% } %>  
                     </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersa_rem" placeholder="Remarks if any" name="othersa_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getOthersa_rem() == null ? "" : annualCompCertificatedetails.getOthersa_rem()%></textarea>
                    </div>
                 </div> 
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersa_npsrem" placeholder="Remarks if any" name="othersa_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getOthersa_rem() == null ? "" : annualCompScrutinydetails.getOthersa_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>b) Whether any of the core/non-core activities of the PFM, as defined in circular 
                     no. PFRDA/2017/30/PF/4 dated 09th October 2017, has been outsourced to a third party service provider by
                      the PFM.Statement showing list of activities outsourced is provided in Annexure 5.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersb" id="resolutions_1" name="othersb" value="Yes"
                        <%= annualCompCertificatedetails.getOthersb().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersb" id="resolutions_2" name="othersb" value="No"
                        <%= annualCompCertificatedetails.getOthersb().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersb" id="resolutions_3" name="othersb" value="NA"
                        <%= annualCompCertificatedetails.getOthersb().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                  <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getOthersb()%></label> 
                         </div>
                     <% } %>   
                     </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersb_rem" placeholder="Remarks if any" name="othersb_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getOthersb_rem() == null ? "" : annualCompCertificatedetails.getOthersb_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersb_npsrem" placeholder="Remarks if any" name="othersb_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getOthersb_rem() == null ? "" : annualCompScrutinydetails.getOthersb_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>c) Whether comprehensive service level agreements have been executed with outsourcing service providers covering terms of 
                     contracts in consonance with to the provisions of PFRDA Act, rules, 
                     regulations, guidelines and directions issued by the authority and copies of all such 
                     agreements have been submitted to NPS Trust.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersc" id="resolutions_1" name="othersc" value="Yes"
                        <%= annualCompCertificatedetails.getOthersc().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersc" id="resolutions_2" name="othersc" value="No"
                        <%= annualCompCertificatedetails.getOthersc().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersc" id="resolutions_3" name="othersc" value="NA"
                        <%= annualCompCertificatedetails.getOthersc().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getOthersc()%></label> 
                         </div>
                     <% } %>   
                     </div>             
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersc_rem" placeholder="Remarks if any" name="othersc_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getOthersc_rem() == null ? "" : annualCompCertificatedetails.getOthersc_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersc_npsrem" placeholder="Remarks if any" name="othersc_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getOthersc_rem() == null ? "" : annualCompScrutinydetails.getOthersc_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>d) Incase any award has been passed against the PFM under the Pension fund Regulatory 
                     and Development Authority (Redressal of Subscriber Grievance) Regulations, 2015, whether PFM has complied with such award.
                     </p>
                  </div>
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                  
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersd" id="resolutions_1" name="othersd" value="Yes"
                        <%= annualCompCertificatedetails.getOthersd().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersd" id="resolutions_2" name="othersd" value="No"
                        <%= annualCompCertificatedetails.getOthersd().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersd" id="resolutions_3" name="othersd" value="NA"
                         <%= annualCompCertificatedetails.getOthersd().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                    <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getOthersd()%></label> 
                         </div>
                     <% } %> 
                     </div>               
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersd_rem" placeholder="Remarks if any" name="othersd_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getOthersd_rem() == null ? "" : annualCompCertificatedetails.getOthersd_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersd_npsrem" placeholder="Remarks if any" name="othersd_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getOthersd_rem() == null ? "" : annualCompScrutinydetails.getOthersd_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>e) Whether PFM has complied with Cyber Security policy for Intermediaries issued vide circular PFRDA/2019/2/REG dated 07.01.2019.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherse" id="resolutions_1" name="otherse" value="Yes"
                         <%= annualCompCertificatedetails.getOtherse().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherse" id="resolutions_2" name="otherse" value="No"
                         <%= annualCompCertificatedetails.getOtherse().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherse" id="resolutions_3" name="otherse" value="NA"
                         <%= annualCompCertificatedetails.getOtherse().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                 
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getOtherse()%></label> 
                         </div>
                     <% } %>
                      </div>                 
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="otherse_rem" placeholder="Remarks if any" name="otherse_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getOtherse_rem() == null ? "" : annualCompCertificatedetails.getOtherse_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="otherse_npsrem" placeholder="Remarks if any" name="otherse_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getOtherse_rem() == null ? "" : annualCompScrutinydetails.getOtherse_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-3">
                     <p>f) Whether PFM has ensured dissemination of adequate, accurate, explicit and timely information about the investment policies, 
                     investment objectives, financial position and general affairs of the scheme to the subscribers in a fairly simple language.
                     </p>
                  </div>
                  
                  <div class="col-md-2">
                  <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersf" id="resolutions_1" name="othersf" value="Yes"
                         <%= annualCompCertificatedetails.getOthersf().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersf" id="resolutions_2" name="othersf" value="No"
                         <%= annualCompCertificatedetails.getOthersf().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersf" id="resolutions_3" name="othersf" value="NA"
                         <%= annualCompCertificatedetails.getOthersf().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  
                   <% } else {%>
                        <div class="form-check form-check-inline">
                         <label class="form-check-label" >&nbsp; <%= annualCompCertificatedetails.getOthersf()%></label> 
                         </div>
                     <% } %>
                     </div>
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersf_rem" placeholder="Remarks if any" name="othersf_rem" <%=isNonNPSUser ? "": "disabled" %>><%=annualCompCertificatedetails.getOthersf_rem() == null ? "" : annualCompCertificatedetails.getOthersf_rem()%></textarea>
                    </div>
                 </div> 
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersf_npsrem" placeholder="Remarks if any" name="othersf_npsrem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getOthersf_rem() == null ? "" : annualCompScrutinydetails.getOthersf_rem()%></textarea>
                    </div>
                 </div>
               </div>
               <hr>
               
                <div class="row">
		                 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <label for="companies">For</label><br>
		                    
		                     <input type="text" class="w-100" readonly="readonly" name='companies' value="<%=companies %>">
		                     <label id="error-comanies" class="error-message text-danger"></label>
		                  </div>
		               </div>
		               <br>
		               
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                           <label>Annexures 1: Current status of the sponsor(s) regulatory licenses </label>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexurei ? "javascript:void(0);" : Annexurei}" ${empty Annexurei ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureI_rem" placeholder="Remarks if any" name="annexureI_rem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getAnnexurei_rem() == null ? "" : annualCompScrutinydetails.getAnnexurei_rem()%></textarea>
                        </div>
                     </div>
        		</div><!-- row one end -->
        		
        		
        		
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                           <label>Annexures 2: Sponsor(s) holding in Intermediary</label>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexureii ? "javascript:void(0);" : Annexure_gURL}" ${empty Annexureii ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureII_rem" placeholder="Remarks if any" name="annexureII_rem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getAnnexureii_rem() == null ? "" : annualCompScrutinydetails.getAnnexureii_rem()%></textarea>
                        </div>
                     </div>
        		</div><!-- row one end -->
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                           <label>Annexures 3: Statement of Sponsor(s) and PFMs Net-worth</label>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexureiii ? "javascript:void(0);" : Annexureiii}" ${empty Annexureiii ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureIII_rem" placeholder="Remarks if any" name="annexureIII_rem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getAnnexureiii_rem() == null ? "" : annualCompScrutinydetails.getAnnexureiii_rem()%></textarea>
                        </div>
                     </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                           <label>Annexures 4: Statement showing amount of income accrued but not realized as on closing date of the financial year.</label>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexureiv ? "javascript:void(0);" : Annexureiv}" ${empty Annexureiv ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureIV_rem" placeholder="Remarks if any" name="annexureIV_rem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getAnnexureiv_rem() == null ? "" : annualCompScrutinydetails.getAnnexureiv_rem()%></textarea>
                        </div>
                     </div>
        		</div><!-- row one end -->
        		
        		
        		
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                           <label>Annexure 5: List of activities outsourced</label>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexurev ? "javascript:void(0);" : Annexurev}" ${empty Annexurev ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureV_rem" placeholder="Remarks if any" name="annexureV_rem" <%=isNonNPSUser ? "disabled": "" %>><%=annualCompScrutinydetails.getAnnexurev_rem() == null ? "" : annualCompScrutinydetails.getAnnexurev_rem()%></textarea>
                        </div>
                     </div>
        		</div><!-- row one end -->
        		
        		
        		 <% if(!isNonNPSUser){ %>
		                 <div class="row">
				                
					                  <div class="col-lg-12 col-md-12 col-sm-12 col-12 pt-12">
					                  	<div class="form-group">
				                        	<textarea class="form-control" id="npsRemark" placeholder="AM Remarks" name="npsRemark"  ><%= (Validator.isNotNull(annualCompScrutinydetails))? annualCompScrutinydetails.getNpsRemark():"" %></textarea>
				                        </div>
					                  </div>
				                 </div>
				                 <br>
				                 <%} %>
				                 
   <%if(!(isPfmSupervisor && isDocSigned) && (isAssignable || isSelfAsignee)){ %> 
               <div class="row text-center">
                  <div class="col-md-12">
                     <!-- <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit"> -->
                     <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3"  id="btn-submit">Submit</button>
                     <a class="button" id="pop-up-trigger"  href="#success-modal"></a>
                  </div>
               </div>
               <%} %>
               
            </form>
         </div>
      </div>
   </div>
</div>

<style>
   /*   .common-btn {
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
    } */
    
    
 input.error {
	border-color: red;
}

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

<script>
var SDWebServerUrl = "<%=GetterUtil.getString(PropsUtil.get("com.nps.dsc.api.domain"), "https://dsc.npstrust.net")%>";
var SDWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
var sdWebServerUrl = "<%=GetterUtil.getString(PropsUtil.get("com.nps.dsc.api.domain"), "https://dsc.npstrust.net")%>";
var sdWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
let signature = <%= signature.toString() %>;
$(function(){
	console.log("doc load", signature);
	<% if(!isDocSigned){ %>
		$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").bind("click", false);
	<% }else{ %>
		let sigsub = signature.SelCertSubject.substring(signature.SelCertSubject.indexOf("CN="));
		$("h6.signedmsg").append(" by "+sigsub.split(",")[0].split("=")[1]); 
		$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").unbind("click");
	<% } %>
	
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
			if(CertInfo.eMail.toUpperCase() === themeDisplay.getUserEmailAddress().toUpperCase()){
			/* if(CertInfo.eMail === themeDisplay.getUserEmailAddress()){ */
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
$( ".x-mark" ).click(function() {
	$("#success-modal").addClass("hide");
	
	if($("#icon").hasClass("fas fa-check-circle text-success fa-4x d-block mb-4")){
		$("#icon").removeClass("fas fa-check-circle text-success fa-4x d-block mb-4");
	}
	if($("#icon").hasClass("fas fa-times-circle text-danger  fa-4x d-block mb-4")){
		$("#icon").removeClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
	}
	
});

$(document).ready(function() {
$("#annualComplainsscrutiny").on('submit', (function(e) {
	e.preventDefault();
	
				//default
				if($("#success-modal").hasClass("hide")){
					$("#success-modal").removeClass("hide");
				}
	        
				/* var formData = new FormData($("#annualComplainsscrutiny")[0]); */
				
				//console.log('formdata::',formData);
				
				/* for (var key of formData.entries()) {
			        console.log(key[0] + ', ' + key[1]);
			    } */
			    if($( "#annualComplainsscrutiny" ).valid()){
			    var fd = new FormData($("#annualComplainsscrutiny")[0]);
				let saveAnnualCompCertificateScrutinyURL = "/web/guest/annual-compliance-report?p_p_id=AnnualComplaincereport_Form_AnnualComplaincereport_FormPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=saveAnnualComplainceScrutinyReport&p_p_cacheability=cacheLevelPage";
				
				$.ajax({
		            url: saveAnnualCompCertificateScrutinyURL,
		            processData: false,
		            contentType: false,
		            data: fd,
		            type: "post",
		            success: function(data) {
		            		$('#output').html(' Data sent for Review.');
	            			$("#icon").addClass("fas fa-check-circle text-success fa-4x d-block mb-4");
	            			$('.x-mark').attr('href', "#");
	            			$("#annualComplainsscrutiny")[0].reset();
		            },
		            error: function() {
		            	$('#output').html(' An error occured while submitting the form. Please try again.');
            			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
   		            	console.log("Error Occured in ajax call");
		            },
		            complete: function(){
						$("#success-modal").show();
    			        $('#pop-up-trigger')[0].click();
    			        console.log("complete::::::::::::::");
			        }
		
		        });
}

	}));
	
});

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>