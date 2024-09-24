<%@ include file="/init.jsp" %>

<div class="nps-page-main nps-upload-report nps-statement-wrapper">
   <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Quarterly Stewardship Report Scrutiny</h4>
            <form class="form" id="qrStewardshipRepo" action="#" method="post">
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
                  <div class="col-md-6">
                     <p>1. Did any *conflict of interest situation occurred during
                        the quarter? Provide list of conflict of interest situations as
                        per your stewardship policy with details on conflicts which
                        occurred during the quarter.
                     </p>
                     <br>
                     <p>*As per principle 2 of common stewardship code issued by
                        PFRDA, institutional investor should have a policy on how they
                        manage conflicts of interest situation in fulfilling their
                        stewardship responsibilities and publicly disclose it.
                     </p>
                     <p>The policy has to address the identification of possible
                        situations where conflict of interest may arise and procedure
                        incase such a situation arises.
                     </p>
                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="conflict" id="conflict_1" name="conflict" value="Yes" disabled>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="conflict" id="conflict_2" name="conflict" value="No" disabled>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="conflict" id="conflict_3" name="conflict" value="NA" disabled>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                   <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="conflict_rem" placeholder="Remarks if any" name="<portlet:namespace/>conflict_rem"></textarea>
                    </div>
                 </div> 
                  
               </div>
               <div class="row">
                  <div class="col-md-6">
                     <p>2. Did any #monitoring situation occurred during the quarter
                        in respect of any investee company for Equity or Debt
                        investments?
                     </p>
                     <p>Provide list of monitoring triggers as per your
                        stewardship policy with details on triggers which occurred
                        during the quarter.
                     </p>
                     <br>
                     <p>#As per principle 3 of common stewardship code issued by
                        PFRDA, institutional investor should have a policy on continuous
                        monitoring of their investee companies in respect of all aspects
                        they consider important.
                     </p>
                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_1" name="monitoring" value="Yes"disabled>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_2" name="monitoring" value="No" disabled>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="monitoring" id="monitoring_3" name="monitoring" value="NA" disabled>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" name="<portlet:namespace/>monitoring_rem"></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               <div class="row">
                  <div class="col-md-6">
                     <p>3. For the resolutions voted during thequarter have you
                        abstained for any of the resolution except for conflict of
                        interest resolutions like common directors, group company etc.
                        If Yes, provide details of such resolutions with detailed
                        rationale.
                     </p>
                  </div>
                  <div class="col-md-3">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_1" name="resolutions" value="Yes" disabled>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_2" name="resolutions" value="No" disabled>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="resolutions" id="resolutions_3" name="resolutions" value="NA" disabled>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                 <div class="col-md-3">
                    <div class="form-group">
                    	<textarea class="form-control" id="resolutions_rem" placeholder="Remarks if any" name="<portlet:namespace/>resolutions_rem"></textarea>
                    </div>
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
               
               <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                           <label>Annex A - Details of Conflict of interest </label>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_gURL ? "javascript:void(0);" : Annexure_gURL}" ${empty Annexure_gURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureA_rem" placeholder="Remarks if any" name="<portlet:namespace/>annexureA_rem"></textarea>
                        </div>
                     </div>
        		</div><!-- row one end -->
        		
               <div class="row">
                  <div class="col-md-6">
                     <label>Annex B (1) - Details of Monitoring situations </label>
                  </div>
                  <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_gURL ? "javascript:void(0);" : Annexure_gURL}" ${empty Annexure_gURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureB_I_rem" placeholder="Remarks if any" name="<portlet:namespace/>annexureB_I_rem"></textarea>
                        </div>
                     </div> 
                   </div>
               
               <div class="row">
                  <div class="col-md-6">
                     <label>Annex B (2) - Debt investments </label>
                  </div>
                  
                  <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_gURL ? "javascript:void(0);" : Annexure_gURL}" ${empty Annexure_gURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>
                  
                   <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureB_II_rem" placeholder="Remarks if any" name="<portlet:namespace/>annexureB_II_rem"></textarea>
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
                           <a href="${empty Annexure_gURL ? "javascript:void(0);" : Annexure_gURL}" ${empty Annexure_gURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>
                   <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annexureC_rem" placeholder="Remarks if any" name="<portlet:namespace/>annexureC_rem"></textarea>
                        </div>
                   </div> 
               </div>
               
               <br>
               <div class="row text-center">
                  <div class="col-md-12">
                     <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
                  </div>
               </div>
            </form>
         </div>
      </div>
   </div>
</div>

<script>
$("#form_comp").on('submit', (function(e) {
	    $.ajax({
	            url: '${saveQtrlyStewardshipSecutinyURL}',
	            processData: false,
	            contentType: false,
	            data: new FormData(this),
	            type: "post",
	            success: function(data) {
	            	$(".animaion").hide();
	            	if(data == "Success") {
	            		toastr.success('Form has been submitted successfully!');
   		            	$("#qrStewardshipRepo")[0].reset();
	            	} else {
	            		console.log("Error Occured in ajax call");
	            		toastr.error('An error occured while submitting the form. Please try again');
	            	}
	            },
	            error: function() {
	            	$(".animaion").hide();
	            	toastr.error('An error occured while submitting the form. Please try again');
	            	console.log("Error Occured in ajax call");
	            },
	            complete: function(){
	            	$(".animaion").hide();
	            	//$(".annexure-upload").val(null);
					//setTimeout(function() { location.reload(true); }, 5000);
		        }
	        });
			
	}));
</script>