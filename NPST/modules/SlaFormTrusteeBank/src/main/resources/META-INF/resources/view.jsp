<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/srlform/save" var="srlformUrl"></portlet:resourceURL>


	<style>
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
		            <p  class="back_btn"><a class="backbtn" href="/web/guest/daily-average"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
		       </div>
		   </div>
		 </div>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-12">
			   <div class="nps-report">
			      <div class="">
			         <div class="content">
			            <h4>SLA Form</h4>
			            <aui:form id="myForm" method="post">
			                  <div class="row">
			                     <div class="col">
			                        <!-- <div class="form-group"> -->
		                        	<input type="hidden" name="dlfileid">
									<input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }">
									<input type="hidden" value="${reportMasterId }" name="reportMasterId" class="reportMasterId">
			                    	<div class="row">
										<div class="col-lg-6 col-md-6 col-sm-12 col-12">
											<div class="nps-input-box mt-0">
												<label for="name" class="pl-2">Report Due Date</label>
												<input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
											</div>
										</div>
									</div>
								</div>
							</div>
							<hr/>
	                        <div class="row">
		                           <div class="col-md-1">
		                              <h5>S.NO</h5>
		                           </div>
		                           <div class="col-md-2">
		                              <h5>Nature Of Activity </h5>
		                           </div>
		                           <div class="col-md-2">
		                              <h5>Cut-Off Time </h5>
		                           </div>
		                           <div class="col-md-2">
		                              <h5>Activity completed within timeline</h5>
		                           </div>
		                           <div class="col-md-3">
		                              <h5>Actual Time in case of deviation</h5>
		                           </div>
		                           <div class="col-md-2">
		                              <h5>Reason for deviation</h5>
		                           </div>
		                        </div>
	                        <hr/>
							<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                                 <p>1.</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Return of unidentified Funds</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>T+1 day(For funds received on T day)</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="oney" name='<portlet:namespace/>one' value="Yes">
	                                 <label class="form-check-label" for="oney">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="onen" name='<portlet:namespace/>one' value="No">
	                                 <label class="form-check-label" for="onen">&nbsp; No</label> 
	                              </div>
	                              <label class="error" for="one"></label>
	                           </div>
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <input class="form-control" type="datetime-local" id="time_1" name="<portlet:namespace/>time_1">
	                                 <label class="error" for="time_1"></label>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="remarks_1" placeholder="Remarks" name="<portlet:namespace/>remarks_1" ></textarea>
			                        	<label class="error" for="remarks_1"></label>
			                        </div>
	                          	</div>
                        	</div>
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                                 <p>2.</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Upload of Normal Fund receipt confirmation file </p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>By 9:15 AM on T+1 day(for clear funds received on T Day).</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="twoy" name='<portlet:namespace/>two' value="Yes">
	                                 <label class="form-check-label" for="twoy">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="twon" name='<portlet:namespace/>two' value="No">
	                                 <label class="form-check-label" for="twon">&nbsp; No</label> 
	                              </div>
	                              <label class="error" for="two"></label>
	                           </div>
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <input class="form-control" type="datetime-local" id="time_2" name="<portlet:namespace/>time_2">
	                                 <label class="error" for="time_2"></label>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="remarks_2" placeholder="Remarks" name="<portlet:namespace/>remarks_2" ></textarea>
			                       		<label class="error" for="remarks_2"></label>
			                        </div>
	                          	</div>
                        	</div>
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                                 <p>3.</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Upload of D- Remit Fund receipt confirmation file</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>By 10:30 AM on T Day(For clear funds received between 9:30:01 AM on T-1 and 9:30:00 AM on T)</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="threey" name='<portlet:namespace/>three' value="Yes">
	                                 <label class="form-check-label" for="threey">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="threen" name='<portlet:namespace/>three' value="No">
	                                 <label class="form-check-label" for="threen">&nbsp; No</label> 
	                              </div>
	                              <label class="error" for="three"></label>
	                           </div>
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <input class="form-control" type="datetime-local" id="time_3" name="<portlet:namespace/>time_3">
	                                 <label class="error" for="time_3"></label>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="remarks_3" placeholder="Remarks" name="<portlet:namespace/>remarks_3" ></textarea>
			                       		<label class="error" for="remarks_3"></label>
			                        </div>
	                          	</div>
                        	</div>
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                                 <p>4.</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Download of Pay in instruction files</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>By 11:30 AM</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="foury" name='<portlet:namespace/>four' value="Yes">
	                                 <label class="form-check-label" for="foury">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="fourn" name='<portlet:namespace/>four' value="No">
	                                 <label class="form-check-label" for="fourn">&nbsp; No</label> 
	                              </div>
	                              <label class="error" for="four"></label>
	                           </div>
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <input class="form-control" type="datetime-local" id="time_4" name="<portlet:namespace/>time_4">
	                                 <label class="error" for="time_4"></label>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="remarks_4" placeholder="Remarks" name="<portlet:namespace/>remarks_4" ></textarea>
			                        	<label class="error" for="remarks_4"></label>
			                        </div>
	                          	</div>
                        	</div>
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                                 <p>5.</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Cut-Off time for confirmation of transfer of funds to PFs </p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>PF Transaction processing: 1:30PM</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="fivey" name='<portlet:namespace/>five' value="Yes">
	                                 <label class="form-check-label" for="fivey">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="fiven" name='<portlet:namespace/>five' value="No">
	                                 <label class="form-check-label" for="fiven">&nbsp; No</label> 
	                              </div>
	                              <label class="error" for="five"></label>
	                           </div>
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <input class="form-control" type="datetime-local" id="time_5" name="<portlet:namespace/>time_5">
	                                 <label class="error" for="time_5"></label>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="remarks_5" placeholder="Remarks" name="<portlet:namespace/>remarks_5" ></textarea>
			                        	<label class="error" for="remarks_5"></label>
			                        </div>
	                          	</div>
                        	</div>
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                                 <p>6.</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Cut-Off time for confirmation of transfer of funds to withdrawal account</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>EOD</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="sixy" name='<portlet:namespace/>six' value="Yes">
	                                 <label class="form-check-label" for="sixy">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="sixn" name='<portlet:namespace/>six' value="No">
	                                 <label class="form-check-label" for="sixn">&nbsp; No</label> 
	                              </div>
	                              <label class="error" for="six"></label>
	                           </div>
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <input class="form-control" type="datetime-local" id="time_6" name="<portlet:namespace/>time_6">
	                                 <label class="error" for="time_6"></label>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="remarks_6" placeholder="Remarks" name="<portlet:namespace/>remarks_6" ></textarea>
			                        	<label class="error" for="remarks_6"></label>
			                        </div>
	                          	</div>
                        	</div>
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                                 <p>7.</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Transfer of Matched and Booked funds to PFs</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Within 25 min from download of pay-in</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="seveny" name='<portlet:namespace/>seven' value="Yes">
	                                 <label class="form-check-label" for="seveny">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="sevenn" name='<portlet:namespace/>seven' value="No">
	                                 <label class="form-check-label" for="sevenn">&nbsp; No</label> 
	                              </div>
	                              <label class="error" for="seven"></label>
	                           </div>
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <input class="form-control" type="datetime-local" id="time_7" name="<portlet:namespace/>time_7">
	                                 <label class="error" for="time_7"></label>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="remarks_7" placeholder="Remarks" name="<portlet:namespace/>remarks_7" ></textarea>
			                        	<label class="error" for="remarks_7"></label>
			                        </div>
	                          	</div>
                        	</div>
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                                 <p>8.</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>Upload of statements and closing balance of various accounts</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-group">
	                              	<p>EOD</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="eighty" name='<portlet:namespace/>eight' value="Yes">
	                                 <label class="form-check-label" for="eighty">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input class="" type="radio" id="eightn" name='<portlet:namespace/>eight' value="No">
	                                 <label class="form-check-label" for="eightn">&nbsp; No</label> 
	                              </div>
	                              <label class="error" for="oneia"></label>
	                           </div>
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <input class="form-control" type="datetime-local" id="time_8" name="<portlet:namespace/>time_8">
	                                 <label class="error" for="time_8"></label>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="remarks_8" placeholder="Remarks" name="<portlet:namespace/>remarks_8" ></textarea>
			                       		<label class="error" for="remarks_8"></label>
			                        </div>
	                          	</div>
                        	</div>
                        	
							<div class="row">
		                         <div class="col-md-12">
		                            <div class="text-center">
		                               <input type="button" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
						         </div>
						      </div>
						   </div>
						
		               </aui:form>
		        	</div>
				</div>
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
	</style>
<script type="text/javascript">


$(document).ready(function() {
	
	//getreportUploadLogId();
	
	toastr.options = {
	  "debug": false,
	  "positionClass": "toast-bottom-right",
	  "onclick": null,
	  "fadeIn": 300,
	  "fadeOut": 1000,
	  "timeOut": 9000,
	  "extendedTimeOut": 1000
	}
	$("form.form").validate({
		  rules: {
			<portlet:namespace/>one: {
		      required: true,
		      
		    },
		    <portlet:namespace/>time_1: {
			      required: "#onen:checked",
			},
		    <portlet:namespace/>remarks_1: {
			      required: "#onen:checked"
			},
		    <portlet:namespace/>two: {
			      required: true
			},
			<portlet:namespace/>time_2: {
			      required: "#twon:checked",
			},
		    <portlet:namespace/>remarks_2: {
			      required: "#twon:checked"
			},
			<portlet:namespace/>three: {
				required: true
			},
			<portlet:namespace/>time_3: {
			      required: "#threen:checked",
			},
		    <portlet:namespace/>remarks_3: {
			      required: "#threen:checked"
			},
			<portlet:namespace/>four: {
				required: true
			},
			<portlet:namespace/>time_4: {
			      required: "#fourn:checked",
			},
		    <portlet:namespace/>remarks_4: {
			      required: "#fourn:checked"
			},
			<portlet:namespace/>five:{
				required: true
			},
			<portlet:namespace/>time_5: {
			      required: "#fiven:checked",
			},
		    <portlet:namespace/>remarks_5: {
			      required: "#fiven:checked"
			},
			<portlet:namespace/>six:{
				required: true
			},
			<portlet:namespace/>time_6: {
			      required: "#sixn:checked",
			},
		    <portlet:namespace/>remarks_6: {
			      required: "#sixn:checked"
			},
			<portlet:namespace/>seven:{
				required: true
			},
			<portlet:namespace/>time_7: {
			      required: "#sevenn:checked",
			},
		    <portlet:namespace/>remarks_7: {
			      required: "#sevenn:checked"
			},
			<portlet:namespace/>eight:{
				required: true,			
			},
			<portlet:namespace/>time_8: {
			      required: "#eightn:checked",
			},
		    <portlet:namespace/>remarks_8: {
			      required: "#eightn:checked"
			},
			
		  }
		});
	
	 $('#btn-submit').on('click', function(){
	    	if($( "form.form" ).valid()){
	    		
		        var fd = new FormData($("form.form")[0]);
		        $(".content").hide();
		        $(".animaion").show();
		        $.ajax({
		            url: '${srlformUrl}',  
		            type: 'POST',
		            data: fd,
		            cache: false,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	$(".content").show();
		                $(".animaion").hide();
		                $("form.form")[0].reset();
		                try {
		                	data = JSON.parse(data);
		                } catch (e) {
		                	console.log("error while parsing the json data");
		                }
		                if(data.status){
	                    	//toastr.success('Form has been submitted successfully!');
	                    	$('#success_tic').modal('show');
	   		            	$('#success_tic').on('hidden.bs.modal', function (e) {
		            			location.reload(); 
		            		})
		                }else{
	                    	//toastr.error('An error occured while parsing the json data. Please try again');
		                	console.log("Error Occured in ajax call");
		            		$('#output').html("An error occured while submitting the form. Please try again");
			            	$('#errorExcel').modal('show');
		                }
		            },
		            error: function() {
		            	$(".animaion").hide();
		           		console.log("Error Occured in ajax call");
		           		console.log("Error Occured in ajax call");
	            		$('#output').html("An error occured while submitting the form. Please try again");
		            	$('#errorExcel').modal('show');
		           		//toastr.error('An error occured while submitting the form. Please try again');
		            },
		            complete: function(){
		            	$(".animaion").hide();
						console.log("Complete");
			        }
		        });
	    	}/* else{
	    		toastr.error('Please fill the required field(s).');
	    	} */
	    });
	 $('#errorExcel').on('hidden.bs.modal', function (e) {
			location.reload(); 
	 })
	//});
});
</script>