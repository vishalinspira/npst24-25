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
<%@page import="com.quarterly.stewardship.report.util.NPSUserPre_Populate_scrutinydata"%>
<%@page import="com.daily.average.service.service.QtrStewardshipReportScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.QtrStewardshipReportScrutiny"%>
<%@ include file="/init.jsp" %>

<%@page import="com.daily.average.service.service.QtrStewardshipReportLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.QtrStewardshipReport"%>

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
	boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;
	QtrStewardshipReport stewardshipDetails = Validator.isNotNull(request.getAttribute("qtrStewardshipReport")) ? (QtrStewardshipReport) request.getAttribute("qtrStewardshipReport") : QtrStewardshipReportLocalServiceUtil.createQtrStewardshipReport(0);
	
	/* Pre populate scrutiny data */
	NPSUserPre_Populate_scrutinydata sd = new NPSUserPre_Populate_scrutinydata();
	sd.pre_populate_scrutiny_data(request);
	QtrStewardshipReportScrutiny qrscrutinystewardshipDetails = Validator.isNotNull(request.getAttribute("qtrstewardshipscrutiny")) ? (QtrStewardshipReportScrutiny) request.getAttribute("qtrstewardshipscrutiny") : QtrStewardshipReportScrutinyLocalServiceUtil.createQtrStewardshipReportScrutiny(0);
	
	List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");
	
	boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
	boolean isDocSigned = false;
	
	JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
	String fileEntryId = "0";
	JSONObject signature = JSONFactoryUtil.createJSONObject();
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
		<a class="modal-close-ui x-mark" href="#">&times;</a>		
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
	<%-- <span class="file-preview-span" data-index='<%= i%>'> <%=object.getString("version")%> --%>
		<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a> 
	<!-- </span> -->
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
            <h4>Quarterly Stewardship Report</h4>
            <form class="form" id="qrStewardshipRepo" action="#" method="post">
            	<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="<%=stewardshipDetails.getReportUploadLogId()%>"/>
				<input type="hidden"value="<%=stewardshipDetails.getReportMasterId()%>" name="reportMasterId" class="reportMasterId"/>
               <!-- <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>For the Month Ended </label>
                        <input type="date" class="date_1" id="date_1" name="date_1">
                     </div>
                  </div>
               </div>
               <br> -->
               <div class="row">
                  <div class="col-md-5">
                     <p>1. Did any *conflict of interest situation occurred during the quarter? 
                     </p>
                     <br>
                     <p>*Refer Schedule VI-Code of Conduct of PFRDA (Pension Fund) Regulations, 2015.
                     </p><br>
                     <p>(As per principle 2, institutional investor should have a policy on how they manage conflicts of interest situation in fulfilling their stewardship responsibilities and publicly disclose it. The policy has to address the identification of possible situations where conflict of interest may arise and procedure in case such a situation arises.)
                     </p>
                     <p>(Details to be provided in Annexure A)</p><br>
                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                     <input type="hidden" name="reportUploadLogId" value="<%=stewardshipDetails.getReportUploadLogId()%>">
                        <input type="radio" class="conflict" id="conflict_1" name="conflict" value="Yes" disabled
                        <%=stewardshipDetails.getConflict_of_interest() != null && stewardshipDetails.getConflict_of_interest().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="conflict" id="conflict_2" name="conflict" value="No" disabled
                        <%=stewardshipDetails.getConflict_of_interest() != null && stewardshipDetails.getConflict_of_interest().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="conflict" id="conflict_3" name="conflict" value="NA" disabled
                        <%=stewardshipDetails.getConflict_of_interest() != null && stewardshipDetails.getConflict_of_interest().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                   <div class="col-md-2">
                    <textarea class="form-control" id="conflict_rem1"  placeholder="Remarks if any" name="conflict_rem1" ><%=stewardshipDetails.getConflict_rem1() == null ? "" : stewardshipDetails.getConflict_rem1()%></textarea>
                 </div> 
                   <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="conflict_rem"  placeholder="Remarks if any" name="conflict_rem" ><%=qrscrutinystewardshipDetails.getConflict_of_interest_rem() == null ? "" : qrscrutinystewardshipDetails.getConflict_of_interest_rem()%></textarea>
                    </div>
                 </div> 
                  
               </div>
               <div class="row">
                  <div class="col-md-5">
                      <p>2. Did any monitoring situation occur during the quarter in respect of any investee company for Equity or Debt investments?
                     </p>
                     <p>(As per principle 3 of common stewardship code issued by PFRDA, institutional investor should have a policy on continuous monitoring of their investee companies in respect of all aspects they consider important.)
                     </p>
                      <p>(Details to be provided in Annexure B)</p><br>
                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_1" name="monitoring" value="Yes"disabled
                        <%=stewardshipDetails.getMonitoring_situation() != null && stewardshipDetails.getMonitoring_situation().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_2" name="monitoring" value="No" disabled
                        <%=stewardshipDetails.getMonitoring_situation() != null && stewardshipDetails.getMonitoring_situation().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_3" name="monitoring" value="NA" disabled
                        <%=stewardshipDetails.getMonitoring_situation() != null && stewardshipDetails.getMonitoring_situation().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                    <textarea class="form-control" id="monitoring_rem1" placeholder="Remarks if any" name="monitoring_rem1" ><%=stewardshipDetails.getMonitoring_rem1() == null ? "" : stewardshipDetails.getMonitoring_rem1()%></textarea>
                 </div>
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="monitoring_rem"  placeholder="Remarks if any" name="monitoring_rem" ><%=qrscrutinystewardshipDetails.getMonitoring_situation_rem() == null ? "" : qrscrutinystewardshipDetails.getMonitoring_situation_rem()%></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               <div class="row">
                  <div class="col-md-5">
                     <p> 3. Did PFM engage with any investee company during the quarter?
                     </p><br>
                     <p>(As per principle 4 of common stewardship code issued by PFRDA, institutional investor should have a policy identifying circumstances for active intervention in the investee companies and the manner of such interventions.)</p>
                      <p>(Details to be provided in Annexure C)</p><br>
                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_1" name="resolutions" value="Yes" disabled
                        <%=stewardshipDetails.getResolutions_voted() != null && stewardshipDetails.getResolutions_voted().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_2" name="resolutions" value="No" disabled
                        <%=stewardshipDetails.getResolutions_voted() != null && stewardshipDetails.getResolutions_voted().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_3" name="resolutions" value="NA" disabled
                        <%=stewardshipDetails.getResolutions_voted() != null && stewardshipDetails.getResolutions_voted().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  <div class="col-md-2">
                    <textarea class="form-control" id="resolutions_rem1"  placeholder="Remarks if any" name="resolutions_rem1" ><%=stewardshipDetails.getResolutions_rem1() == null ? "" : stewardshipDetails.getResolutions_rem1()%></textarea>
                 </div>
                 <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="resolutions_rem"  placeholder="Remarks if any" name="resolutions_rem" ><%=qrscrutinystewardshipDetails.getResolutions_voted_rem() == null ? "" : qrscrutinystewardshipDetails.getResolutions_voted_rem()%></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
                <div class="row">
                  <!-- <div class="col-md-1">
                     <p>4.</p>
                  </div> -->
                  <div class="col-md-5">
                    <p>4. Did any situation occurred during the quarter requiring collaboration with other institutional investors? 
                     </p>
                     <p>(As per principle 4 of common stewardship code issued by PFRDA, institutional investor should have a policy for collaboration with other institutional investors, to preserve the interest of the ultimate investors.)
                     </p>
 					<p>(Details to be provided in Annexure D)</p><br>

                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="insInvestorSituation" disabled id="insInvestorSituation_1" name="insInvestorSituation" value="Yes"
                        <%=stewardshipDetails.getInsInvestorSituation() != null && stewardshipDetails.getInsInvestorSituation().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="insInvestorSituation" disabled id="insInvestorSituation_2" name="insInvestorSituation" value="No"
                        <%=stewardshipDetails.getInsInvestorSituation() != null && stewardshipDetails.getInsInvestorSituation().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" disabled id="insInvestorSituation_3" name="insInvestorSituation" value="NA"
                        <%=stewardshipDetails.getInsInvestorSituation() != null && stewardshipDetails.getInsInvestorSituation().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="insInvestorSituation_rem1" placeholder="Remarks if any" name="insInvestorSituation_rem1"><%=stewardshipDetails.getInsInvestorSituation_rem1() == null ? "" : stewardshipDetails.getInsInvestorSituation_rem1()%></textarea>
                 </div> 
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="insInvestorSituation_rem" placeholder="Remarks if any" name="insInvestorSituation_rem"><%=qrscrutinystewardshipDetails.getInsInvestorSituation_rem() == null ? "" : qrscrutinystewardshipDetails.getInsInvestorSituation_rem()%></textarea>
                 </div>  
                 
               </div>
               <br>
               
                 <%

               	
            	   if(stewardshipDetails.getAdversealert() != null && !stewardshipDetails.getAdversealert().trim().isEmpty()){
                	 
                %>
                              <div class="row">
                  <!-- <div class="col-md-1">
                     <p>5.</p>
                  </div> -->
                  <div class="col-md-5">
                     <p>5. Was there any adverse alert during the quarter relating to any of the investee company in Pension funds' portfolio?
                     </p>
                      <p>(Details to be provided in Annexure E)</p><br>
                    
                     <br>

                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="adversealert" disabled id="adversealert" name="adversealert" value="Yes"
                           <%=stewardshipDetails.getAdversealert() != null && stewardshipDetails.getAdversealert().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="adversealert" disabled id="adversealert" name="adversealert" value="No"
                         <%=stewardshipDetails.getAdversealert() != null && stewardshipDetails.getAdversealert().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="adversealert" disabled id="adversealert" name="adversealert" value="NA"
                        <%=stewardshipDetails.getAdversealert() != null && stewardshipDetails.getAdversealert().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="adversealert_rem1" placeholder="Remarks if any" name="adversealert_rem1"><%=stewardshipDetails.getAdversealert_rem1() == null ? "" : stewardshipDetails.getAdversealert_rem1()%></textarea>
                 </div> 
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="adversealert_rem"  placeholder="Remarks if any" name="adversealert_rem"><%=qrscrutinystewardshipDetails.getAdversealert_rem() == null ? "" : qrscrutinystewardshipDetails.getAdversealert_rem()%></textarea>
                 </div>  
                 
               </div>
               <br>
               <% } %>
                 <div class="row">
                 <!--  <div class="col-md-1">
                     <p>6.</p>
                  </div> -->
                  <div class="col-md-5">
                   <%

                  if(stewardshipDetails.getAdversealert() != null && !stewardshipDetails.getAdversealert().trim().isEmpty()){
                %>
                     <p>6. For the resolutions voted during the quarter have you abstained for any of the resolution except for conflict of interest resolutions like common directors, group company etc. If yes, provide details of such resolutions with detailed rationale.
                     </p>
                     <p>(Details to be provided in Annexure F)</p><br>
                    
                  <% } else {%>  
                  
                     <p>5. For the resolutions voted during the quarter have you abstained for any of the resolution except for conflict of interest resolutions like common directors, group company etc. If yes, provide details of such resolutions with detailed rationale.
                     </p>
                     <p>(Details to be provided in Annexure E)</p><br>
                     <% } %>
                     
                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutionsVoted1" disabled id="resolutionsVoted1_1" name="resolutionsVoted1" value="Yes"
                         <%=stewardshipDetails.getResolutionsVoted1() != null && stewardshipDetails.getResolutionsVoted1().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutionsVoted1" disabled id="resolutionsVoted1_2" name="resolutionsVoted1" value="No"
                         <%=stewardshipDetails.getResolutionsVoted1() != null && stewardshipDetails.getResolutionsVoted1().equalsIgnoreCase("No") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutionsVoted1" disabled id="resolutionsVoted2" name="resolutionsVoted1" value="NA"
                         <%=stewardshipDetails.getResolutionsVoted1() != null && stewardshipDetails.getResolutionsVoted1().equalsIgnoreCase("NA") ? "checked" : "" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="resolutionsVoted1_rem1" placeholder="Remarks if any" name="resolutionsVoted1_rem1"><%=stewardshipDetails.getResolutionsVoted1_rem1() == null ? "" : stewardshipDetails.getResolutionsVoted1_rem1()%></textarea>
                 </div> 
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="resolutionsVoted1_rem"  placeholder="Remarks if any" name="resolutionsVoted1_rem"><%=qrscrutinystewardshipDetails.getResolutionsVoted1_rem() == null ? "" : qrscrutinystewardshipDetails.getResolutionsVoted1_rem()%></textarea>
                 </div>  
                 
               </div>
               <br>
               
               
               <!-- <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Date: </label>
                        <input type="text" class="date_2" id="date_2" name="date_2">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Name: </label>
                        <input type="text" class="employeeName" id="employeeName" name="employeeName">
                     </div>
                  </div>
               </div>
               <br> -->
               <!-- <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Place: </label>
                        <input type="text" class="place" id="place" name="place">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="roles">Role:</label><br>
                     <select class="w-100" name="roles" id="roles">
                        <option value="">Select</option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                     </select>
                     <label id="error-roles" class="error-message text-danger"></label>
                  </div>
               </div>
               <br> -->
               <!-- <div class="row">
                  <div class="col-md-6">
                     <label>Annex A - Details of Conflict of interest </label>
                  </div>
                  
                  <div class="col-md-3">
                     <textarea class="annexureA_rem" id="annexureA_rem" placeholder="Remarks if any" name="annexureA_rem"></textarea>
                  </div>
                  
               </div> -->
               
              <%--  <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                           <label>Stewardship report along with Annexures A to F (PDF) </label>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_aURL ? "javascript:void(0);" : Annexure_aURL}" ${empty Annexure_aURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureA_rem" placeholder="Remarks if any" name="annexureA_rem" ><%=qrscrutinystewardshipDetails.getAnnexure_a() == null ? "" : qrscrutinystewardshipDetails.getAnnexure_a()%></textarea>
                        </div>
                     </div>
        		</div> --%>
        		
        		<!-- row one end -->
        		
               <div class="row">
                  <div class="col-md-6">
                     <label>Stewardship report along with Annexures A to E (Soft Copy) </label>
                  </div>
                  <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                         <%--   <a href="${empty Annexure_gURL ? "javascript:void(0);" : Annexure_gURL}" ${empty Annexure_gURL ? "" : "download"}>Click here to download</a> --%>
                           <a href="${empty Annexure_b_iURL ? "javascript:void(0);" : Annexure_b_iURL}" ${empty Annexure_b_iURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureB_I_rem" placeholder="Remarks if any" name="annexureB_I_rem" ><%=qrscrutinystewardshipDetails.getAnnexure_b_i() == null ? "" : qrscrutinystewardshipDetails.getAnnexure_b_i()%></textarea>
                        </div>
                     </div> 
                   </div>
               
               
               <%if(!isNonNPSUser){ %>
               
               <h4>Historical Files</h4>
                <a href="${empty Annexure_aURL ? "javascript:void(0);" : Annexure_aURL}" ${empty Annexure_aURL ? "" : "download"}>Annexure A</a><br>
                 <a href="${empty Annexure_b_iURL ? "javascript:void(0);" : Annexure_b_iURL}" ${empty Annexure_b_iURL ? "" : "download"}> Annexure B(1)</a><br>
                
                <a href="${empty Annexure_b_iiURL ? "javascript:void(0);" : Annexure_b_iiURL}" ${empty Annexure_b_iiURL ? "" : "download"}>Annex B (2) - Debt investments</a><br>
                
                <a href="${empty Annexure_cURL ? "javascript:void(0);" : Annexure_cURL}" ${empty Annexure_cURL ? "" : "download"}>Annex C - Resolutions where PFM has abstained from voting.</a>
               
               <%} %>
               
               
               
        <%--        <div class="row">
                  <div class="col-md-6">
                     <label>Annex B (2) - Debt investments </label>
                  </div>
                  
                  <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_b_iiURL ? "javascript:void(0);" : Annexure_b_iiURL}" ${empty Annexure_b_iiURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>
                  
                   <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureB_II_rem" placeholder="Remarks if any" name="annexureB_II_rem" ><%=qrscrutinystewardshipDetails.getAnnexure_b_ii() == null ? "" : qrscrutinystewardshipDetails.getAnnexure_b_ii()%></textarea>
                        </div>
                   </div> 
               </div>
               
               
               <div class="row">
                  <div class="col-md-6">
                     <label>Annex C - Resolutions where PFM has abstained from voting. </label>
                  </div>
                  <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_cURL ? "javascript:void(0);" : Annexure_cURL}" ${empty Annexure_cURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>
                   <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureC_rem" placeholder="Remarks if any" name="annexureC_rem" ><%=qrscrutinystewardshipDetails.getAnnexure_c() == null ? "" : qrscrutinystewardshipDetails.getAnnexure_c()%></textarea>
                        </div>
                   </div> 
               </div>
 --%>               
               <br>
               <div class="row text-center">
                  <div class="col-md-12">
                     <!-- <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit"> -->
                        <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" <%=isNonNPSUser ? "disabled": "" %>>Submit</button>
                  <!--  <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" >Submit</button>--> 
			         <a class="button" id="pop-up-trigger"  href="#success-modal"></a>
                  </div>
               </div>
            </form>
         </div>
      </div>
   </div>
</div>

<style>
  /*  .common-btn {
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
				$.ajax({
		            url: '/o/dsc/signPDF',  
		            type: 'POST',
		            cache: false,
		            data: fd,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	console.log(data);
		            	//location.reload(); 
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


$("#qrStewardshipRepo").on('submit', (function(e) {
	e.preventDefault(); 
	
	//default
	if($("#success-modal").hasClass("hide")){
		$("#success-modal").removeClass("hide");
	}
	
	let saveQtrlyStewardshipScrutinyURL = "/web/guest/stewardship-report?p_p_id=com_quarterly_stewardship_report_QuarterlyStewardshipReportPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=SaveQuarterlyStewardshipReportScrutiny&p_p_cacheability=cacheLevelPage";
	    $.ajax({
	            url: saveQtrlyStewardshipScrutinyURL,
	            processData: false,
	            contentType: false,
	            data: new FormData(this),
	            type: "post",
	            success: function(data) {
	            	//$(".animaion").hide();
	            	if(data == "Success") {
	            		/* //toastr.success('Form has been submitted successfully!');
   		            	//$("#qrStewardshipRepo")[0].reset();
 		            	$('#success_tic').modal('show');
   		            	$('#success_tic').on('hidden.bs.modal', function (e) {
	            			location.reload(); 
	            		}) */
	            		
	            		//success message
	            	 		$('#output').html(' Data sent for Review.');
	            			$("#icon").addClass("fas fa-check-circle text-success fa-4x d-block mb-4");
	            			$('.x-mark').attr('href', "#");
	            			$("#qrStewardshipRepo")[0].reset();
	            	} else {
	            		//error message
            			$('#output').html(' An error occured while submitting the form. Please try again.');
            			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
   		            	console.log("Error Occured in ajax call");
	            	}
	            },
	            error: function() {
	            	 console.log("Error error:::", error);
		        		//error message
		        		$('#output').html(' An error occured while submitting the form. Please try again.');
		        		$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
	            },
	            complete: function(){
	            	//$(".animaion").hide();
        			$("#success-modal").show();
        			$('#pop-up-trigger')[0].click();
		        }
	        });
			
	}));
</script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>