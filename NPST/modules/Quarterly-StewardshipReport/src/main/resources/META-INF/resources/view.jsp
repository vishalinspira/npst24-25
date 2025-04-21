<%@page import="com.quarterly.stewardship.report.util.Pre_Populate_scrutinydata"%>
<%@page import="com.daily.average.service.service.QtrStewardshipReportScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.QtrStewardshipReportScrutiny"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@ include file="/init.jsp" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>


<% 
	Pre_Populate_scrutinydata sd = new Pre_Populate_scrutinydata();
	sd.pre_populate_scrutiny_data(request);
	QtrStewardshipReportScrutiny qrscrutinystewardshipDetails = Validator.isNotNull(request.getAttribute("qtrstewardshipscrutiny")) ? (QtrStewardshipReportScrutiny) request.getAttribute("qtrstewardshipscrutiny") : QtrStewardshipReportScrutinyLocalServiceUtil.createQtrStewardshipReportScrutiny(0);
	

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
                <p  class="back_btn"><a class="backbtn" href="/web/guest/quarterly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
       </div>
     </div>
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Quarterly Stewardship Report</h4>
            <form class="form" id="qrStewardshipRepo" action="#" method="post">
	            <input type="hidden" value="${reportUploadLogId }" name="reportUploadLogId" class="reportUploadLogId" />
			 	<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" name="reportDate" value="${reportDate }" readonly="readonly">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>For the Quarter Ended </label>
                        <input type="month" class="date_1" id="date_1" name="date_1">
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                        <div>
                           <p class="font-weight-bold mb-0">To,</p>
                           <p class=" font-weight-bold mb-0">National Pension System Trust,</p>
                           <p class=" font-weight-bold mb-0">Tower B, B-302, Third Floor,</p>
                           <p class="font-weight-bold mb-0">World Trade Center,</p>
                           <p class="font-weight-bold mb-0">Nauroji Nagar,</p>
                           <p class="font-weight-bold mb-0">New Delhi-110029</p>
                        </div>
                     </div>
                  </div>
               </div>
               <br>
               <p>Sir,</p>
               <br>
               <p>In my opinion and to the best of my information, I report
                  the following in respect of the quarter mentioned above:
               </p>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>1.</p>
                  </div>
                  <div class="col-md-5">
                     <p>Did any *conflict of interest situation occurred during the quarter? 
                     </p>
                     <br>
                     <p>*Refer Schedule VI-Code of Conduct of PFRDA (Pension Fund) Regulations, 2015.
                     </p><br>
                     <p>(As per principle 2, institutional investor should have a policy on how they manage conflicts of interest situation in fulfilling their stewardship responsibilities and publicly disclose it. The policy has to address the identification of possible situations where conflict of interest may arise and procedure in case such a situation arises.)
                     </p>
                     <p>(Details to be provided in Annexure A)</p><br>
             
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="conflict" id="conflict_1" name="conflict" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="conflict" id="conflict_2" name="conflict" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="conflict" id="conflict_3" name="conflict" value="NA">
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="conflict_rem1"  placeholder="Remarks if any" name="conflict_rem1"></textarea>
                 </div> 
                  
                   <div class="col-md-2">
                    <textarea class="form-control" id="conflict_rem" readonly="readonly" placeholder="Remarks if any" name="conflict_rem"><%=qrscrutinystewardshipDetails.getConflict_of_interest_rem() == null ? "" : qrscrutinystewardshipDetails.getConflict_of_interest_rem()%></textarea>
                 </div> 
                 
               </div>
               <div class="row">
                  <div class="col-md-1">
                     <p>2.</p>
                  </div>
                  <div class="col-md-5">
                     <p>Did any monitoring situation occur during the quarter in respect of any investee company for Equity or Debt investments?
                     </p>
                     <p>(As per principle 3 of common stewardship code issued by PFRDA, institutional investor should have a policy on continuous monitoring of their investee companies in respect of all aspects they consider important.)
                     </p>
                      <p>(Details to be provided in Annexure B)</p><br>
                    <!--  <p>#As per principle 3 of common stewardship code issued by
                        PFRDA, institutional investor should have a policy on continuous
                        monitoring of their investee companies in respect of all aspects
                        they consider important.
                     </p> -->
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_1" name="monitoring" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_2" name="monitoring" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_3" name="monitoring" value="NA">
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="monitoring_rem1" placeholder="Remarks if any" name="monitoring_rem1"></textarea>
                 </div> 
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="monitoring_rem" readonly="readonly" placeholder="Remarks if any" name="monitoring_rem"><%=qrscrutinystewardshipDetails.getMonitoring_situation_rem() == null ? "" : qrscrutinystewardshipDetails.getMonitoring_situation_rem()%></textarea>
                 </div>  
                 
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3.</p>
                  </div>
                  <div class="col-md-5">
                     <p>Did PFM engage with any investee company during the quarter?
                     </p><br>
                     <p>(As per principle 4 of common stewardship code issued by PFRDA, institutional investor should have a policy identifying circumstances for active intervention in the investee companies and the manner of such interventions.)</p>
                      <p>(Details to be provided in Annexure C)</p><br>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_1" name="resolutions" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_2" name="resolutions" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_3" name="resolutions" value="NA">
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="resolutions_rem1"  placeholder="Remarks if any" name="resolutions_rem1"></textarea>
                 </div>
                  <div class="col-md-2">
                    <textarea class="form-control" id="resolutions_rem" readonly="readonly" placeholder="Remarks if any" name="resolutions_rem"><%=qrscrutinystewardshipDetails.getResolutions_voted_rem() == null ? "" : qrscrutinystewardshipDetails.getResolutions_voted_rem()%></textarea>
                 </div> 
               </div>
               <br>
               
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p>4.</p>
                  </div>
                  <div class="col-md-5">
                     <p>Did any situation occurred during the quarter requiring collaboration with other institutional investors? 
                     </p>
                     <p>(As per principle 4 of common stewardship code issued by PFRDA, institutional investor should have a policy for collaboration with other institutional investors, to preserve the interest of the ultimate investors.)
                     </p>
 					<p>(Details to be provided in Annexure D)</p><br>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="insInvestorSituation" id="insInvestorSituation_1" name="insInvestorSituation" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="insInvestorSituation" id="insInvestorSituation_2" name="insInvestorSituation" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="insInvestorSituation_3" name="insInvestorSituation" value="NA">
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="insInvestorSituation_rem1" placeholder="Remarks if any" name="insInvestorSituation_rem1"></textarea>
                 </div> 
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="insInvestorSituation_rem" readonly="readonly" placeholder="Remarks if any" name="insInvestorSituation_rem"><%=qrscrutinystewardshipDetails.getInsInvestorSituation_rem() == null ? "" : qrscrutinystewardshipDetails.getInsInvestorSituation_rem()%></textarea>
                 </div>  
                 
               </div>
               <br>
                 
             <%--    
                    <div class="row">
                  <div class="col-md-1">
                     <p>5.</p>
                  </div>
                  <div class="col-md-5">
                     <p>Was there any adverse alert during the quarter relating to any of the investee company in Pension funds' portfolio?
                     </p>
                      <p>(Details to be provided in Annexure E)</p><br>
                    
                     <br>

                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="adversealert" id="adversealert" name="adversealert" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="adversealert" i
                        d="adversealert" name="adversealert" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="adversealert" id="adversealert" name="adversealert" value="NA">
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="adversealert_rem1" placeholder="Remarks if any" name="adversealert_rem1"></textarea>
                 </div> 
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="adversealert_rem" readonly="readonly" placeholder="Remarks if any" name="adversealert_rem"><%=qrscrutinystewardshipDetails.getAdversealert_rem() == null ? "" : qrscrutinystewardshipDetails.getAdversealert_rem()%></textarea>
                 </div>  
                 
               </div>
               <br>
               
             --%>   
                 <div class="row">
                  <div class="col-md-1">
                     <p>5.</p>
                  </div>
                  <div class="col-md-5">
                     <p>For the resolutions voted during the quarter have you abstained for any of the resolution except for conflict of interest resolutions like common directors, group company etc. If yes, provide details of such resolutions with detailed rationale.
                     </p>
                     <p>(Details to be provided in Annexure E)</p><br>
                    

                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutionsVoted1" id="resolutionsVoted1_1" name="resolutionsVoted1" value="Yes">
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutionsVoted1" id="resolutionsVoted1_2" name="resolutionsVoted1" value="No">
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutionsVoted1" id="resolutionsVoted2" name="resolutionsVoted1" value="NA">
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="resolutionsVoted_rem1" placeholder="Remarks if any" name="resolutionsVoted_rem1"></textarea>
                 </div> 
                  
                  <div class="col-md-2">
                    <textarea class="form-control" id="resolutionsVoted1_rem" readonly="readonly" placeholder="Remarks if any" name="resolutionsVoted1_rem"><%=qrscrutinystewardshipDetails.getResolutionsVoted1_rem() == null ? "" : qrscrutinystewardshipDetails.getResolutionsVoted1_rem()%></textarea>
                 </div>  
                 
               </div>
               <br>
               
               
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
                        <input type="text" class="place" id="place" name="place">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="roles">Role:</label><br>
                     <input type="text" name="roles" class="roles" id="roles">
                     <!-- <select class="w-100" name="roles" id="roles">
                        <option value="">Select</option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                     </select> -->
                     <label id="error-roles" class="error-message text-danger"></label>
                  </div>
               </div>
               <br>
           <%--     <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Stewardship report along with Annexures A to F (PDF)  </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile1">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureA" name="annexureA" accept=".pdf"/>
                           </div>
                           <label id="error1" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureA_rem" placeholder="Remarks if any"  name="annexureA_rem" ><%=qrscrutinystewardshipDetails.getAnnexure_a() == null ? "" : qrscrutinystewardshipDetails.getAnnexure_a()%></textarea>
                    </div>
                 </div> 
                 
               </div> --%>
               
                <div class="row">
                   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Stewardship report along with Annexures A to E (Soft Copy)  </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile2">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureB_I" name="annexureB_I" accept=".xlsx"/>
                           </div>
                           <label id="error2" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureB_I_rem" placeholder="Remarks if any"  name="annexureB_I_rem" ><%=qrscrutinystewardshipDetails.getAnnexure_b_i() == null ? "" : qrscrutinystewardshipDetails.getAnnexure_b_i()%></textarea>
                    </div>
                 </div> 
               </div>
               <br> 
              <%--  <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annex B (2) - Debt investments </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile3">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureB_II" name="annexureB_II" accept=".xlsx"/>
                           </div>
                           <label id="error3" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureB_II_rem" placeholder="Remarks if any" name="annexureB_II_rem" ><%=qrscrutinystewardshipDetails.getAnnexure_b_ii() == null ? "" : qrscrutinystewardshipDetails.getAnnexure_b_ii()%></textarea>
                    </div>
                 </div> 
                 
               </div>
               <br> --%>
              <%--  <div class="row">
                   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annex C - Resolutions where PFM has abstained from voting. </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile4">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureC" name="annexureC" accept=".xlsx"/>
                           </div>
                           <label id="error4" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureC_rem" placeholder="Remarks if any"  name="annexureC_rem" ><%=qrscrutinystewardshipDetails.getAnnexure_c() == null ? "" : qrscrutinystewardshipDetails.getAnnexure_c()%></textarea>
                    </div>
                 </div>  
                 
               </div> --%>
               <div class="row" id="sub">
	               <div class="row text-center">
	                  <div class="col-md-12">
	                     <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
	                  </div>
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

.modal-dialog {
	margin: 5% 40%;
}

.modal-content {
	width: 20vw;
}
</style>

<script type="text/javascript">

$(document).ready(function() {

	/* toastr.options = {
		  "debug": false,
		  "positionClass": "toast-bottom-right",
		  "onclick": null,
		  "fadeIn": 300,
		  "fadeOut": 1000,
		  //"timeOut": 6000,
		  "extendedTimeOut": 1000
		} */
	
	/* render file name in choose file starts */
/* 	$('#annexureA').bind('change', function () {
		  var filename = $("#annexureA").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile1").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile1").text(filename.replace("C:\\fakepath\\", ""));
		  }
	}); */
	
	 $('#annexureB_I').bind('change', function () {
		  var filename = $("#annexureB_I").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile2").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile2").text(filename.replace("C:\\fakepath\\", ""));
		  }
	});
	
	 /*
	$('#annexureB_II').bind('change', function () {
		  var filename = $("#annexureB_II").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile3").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile3").text(filename.replace("C:\\fakepath\\", ""));
		  }
	});
	
	$('#annexureC').bind('change', function () {
		  var filename = $("#annexureC").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile4").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile4").text(filename.replace("C:\\fakepath\\", ""));
		  }
	}); */
	
	/* render file name in choose file ends */
	
	$.validator.addMethod("lettersonly", function(value, element) {
		  //return this.optional(element) || /^[a-zA-Z/s]+$/i.test(value);
		 return this.optional(element) || /^[a-z ]+$/i.test(value);
		}, "Please enter characters only");
	
	/* $.validator.addMethod('annexureA_validateFileName', function(value, element) {
		let filename = 'Annex A - Details of Conflict of interest.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annex A - Details of Conflict of interest.xlsx"); */
	
	
/* 	$.validator.addMethod('annexureB_I_validateFileName', function(value, element) {
		let filename = 'Annex B (1) - Details of Monitoring situations.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annex B (1) - Details of Monitoring situations.xlsx");
	
	$.validator.addMethod('annexureB_II_validateFileName', function(value, element) {
		let filename = 'Annex B (2) - Debt investments.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annex B (2) - Debt investments.xlsx");
	
	$.validator.addMethod('annexureC_validateFileName', function(value, element) {
		let filename = 'Annex C - Resolutions where PFM has abstained from voting.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annex C - Resolutions where PFM has abstained from voting.xlsx");
	
	 */
	
	 $("#qrStewardshipRepo").validate({
	  	rules: {
			date_1: {
		      	required: true
		    },
		    date_2: {
			    required: true
			},
			employeeName:{
				required: true,
				lettersonly: true
			},
			place:{
				required: true
			},
			roles:{
				required: true
			},
		/* 	annexureA:{
				required: true,
				//annexureA_validateFileName: true
			}, */
			annexureB_I:{
				required: true,
				//annexureA_validateFileName: true
			},
			/* annexureB_I:{
				required: true,
				annexureB_I_validateFileName: true
			},
			annexureB_II:{
				required: true,
				annexureB_II_validateFileName: true
			},
			annexureC:{
				required: true,
				annexureC_validateFileName: true
			}, */
		
	  },
	  errorPlacement: function (error, element) {
        /*  if (element.attr("name") == "annexureA") {
              error.appendTo("#error1"); */
         /*  } else if (element.attr("name") == "annexureB_I") {
              error.appendTo("#error2");
          } else if (element.attr("name") == "annexureB_II") {
              error.appendTo("#error3");
          } else if (element.attr("name") == "annexureC") {
              error.appendTo("#error4"); */
          //} 
         if (element.attr("name") == "roles") {
              error.appendTo("#error-roles");
          } else {
        	  error.appendTo(element.parent().parent().after());
          }
      },

	}); 
	
	$('.conflict').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.monitoring').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.resolutions').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.resolutionsVoted1').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
/*	$('.adversealert').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); */
	$('.insInvestorSituation').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$("#qrStewardshipRepo").on('submit', function(e) {
		//console.log("Inside ajax");
		
		e.preventDefault();
			
			if($( "#qrStewardshipRepo" ).valid()){
				
				$("#sub").addClass("hide");
				
				var formData = new FormData($("form.form")[0]);
				
				//var fd = new FormData($("form.form")[0]);

				var htmlWidth = $("#canvasD").width();
				var htmlHeight = $("#canvasD").height();
				var pdfWidth = htmlWidth + (15 * 2);
				var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
				var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

				var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;


				html2canvas($("#canvasD")[0], { allowTaint: true }).then(function(canvas) {
					canvas.getContext('2d');

					var image = canvas.toDataURL("image/jpeg", 1.0);
					doc.addImage(image, 'JPEG', 15, 15, htmlWidth, htmlHeight);


					for (var i = 1; i <= pageCount; i++) {
						doc.addPage(pdfWidth, pdfHeight);
						doc.addImage(image, 'JPEG', 15, -(pdfHeight * i)+15, htmlWidth, htmlHeight);
					}

					//console.log("doc.output('bloburl') ::: ", doc.output('arraybuffer'));
	/* 				doc.save("split-to-multi-page.pdf");
				     window.open(doc.output('bloburl')); */
				     
					var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
				     
					//console.log("file::: ", file);
					
					formData.append("stewardship_report_pdf_file", file);
					console.log("formData",formData);
					$(".nps-report").hide();
					$(".animaion").show();
					$.ajax({
			            type: "POST",
			            enctype: 'multipart/form-data',
			            processData: false,
			            contentType: false,
			            cache: false,
			            url: '${qrStewardshipReportResourceURL}',
			            data: formData,
			            success: function(data) {
			            	$(".nps-report").show();
			            	$(".animaion").hide();
			            	if(data == "Success") {
			            		//toastr.success('Form has been submitted successfully!');
		   		            	$('#success_tic').modal('show');
		   		            	$("#qrStewardshipRepo")[0].reset();
		   		            	$('#success_tic').on('hidden.bs.modal', function (e) {
			            			location.reload(); 
			            		})
			            	} else {
			            		console.log("Error Occured in ajax call");
			            		$('#output').html("An error occured while submitting the form. Please try again");
				            	$('#errorExcel').modal('show');
			            		//toastr.error('An error occured while submitting the form. Please try again');
			            	}
			            },
			            error: function() {
			            	$(".animaion").hide();
			            	//toastr.error('An error occured while submitting the form. Please try again');
			            	console.log("Error Occured in ajax call");
			            	$('#output').html("An error occured while submitting the form. Please try again");
			            	$('#errorExcel').modal('show');
			            },
			            complete: function(){
			            	$(".animaion").hide();
							console.log("Complete");
			            	if($("#sub").hasClass("hide")){
				            	$("#sub").removeClass("hide");
			            	}
				        }
			        }); 

				});
		    	
	    	}
		});
	$('#success_tic').on('hidden.bs.modal', function (e) {
		location.reload(); 
	})
	$('#errorExcel').on('hidden.bs.modal', function (e) {
		location.reload(); 
	})
});

</script>

