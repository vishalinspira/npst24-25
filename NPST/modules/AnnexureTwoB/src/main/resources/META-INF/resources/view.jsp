<%@ include file="/init.jsp" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/>

<portlet:resourceURL id="/AnnexureTwoBForm/save" var="annexuretwobformtURL"></portlet:resourceURL>

<%-- <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> ANNEXURE 2b - NPS TRUST OBSERVATION ON COMPLIANCE REPORT.
                </div>
            </div>
        </div> --%>
	
<div class="nps-page-main nps-upload-report nps-statement-wrapper">
	<div class="row">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
	            <h4>Annexure 2b - NPS Trust observation on Compliance report</h4>
					<form class="row form" id="Annexure-twoB-Form" >
						<input type="hidden" name="dlfileid"/>
						<input type="hidden" name="<portlet:namespace/>reportUploadLogId" id="reportUploadLogId" class="reportUploadLogId"/>
						<input type="hidden" value="26" name="<portlet:namespace/>reportMasterId" id="reportMasterId" class="reportMasterId"/>
		                <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                    <div class="nps-input-box mt-0">
		                        <label for="name">Report Due Date</label>
		                        <input class="reportDate" type="date" readonly="readonly">
		                        <!-- <input type="text" id="name"  disabled> -->
		                    </div>
		                </div>
					 	<input type="hidden" class="rowcount_first_table" name="<portlet:namespace />rowcount_first_table">
						<div class="row">
							<div class="col-md-8">
								<div class="form-group">
									<p>
										<strong>Following are the observation of NPS Trust </strong>
									</p>
								</div>
							</div>
							
							<div class="col-md-4">
								<div class="form-group">
									<p>
										<strong>Date &nbsp; : &nbsp;</strong> <input class="form-control formdate-dline fromdate" type="date" name='<portlet:namespace/>fromdate'>
									</p>
								</div>
							</div>
						</div>
						
					<br><br>
						<div class="form-inline row">
							<div class="col-md-12">
								<div class="form-group">
									<p>
										<strong>File number &nbsp; : </strong> &nbsp;<input class="form-control file_number" type="text" name='<portlet:namespace/>file_number' onkeypress="return onlyNumberKey(event)">
									</p>
								</div>
							</div>
						</div>
						
						<br>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<p>
										To <br>
										Name  &nbsp; &nbsp; &nbsp; <strong> : </strong> &nbsp;&nbsp; <input class="form-control col-md-4 to_name" type="text" name='<portlet:namespace/>to_name'><br>
										Address  &nbsp;&nbsp; <strong> : </strong> &nbsp;&nbsp; <textarea class="form-control col-md-4 to_address" rows="4" cols="50" maxlength="200" name='<portlet:namespace/>to_address'></textarea><br><br><br>
										Dear Sir
									</p>
								</div>
							</div>
						</div>
						
						<div class="form-inline row">
							<div class="col-md-12">
								<div class="form-group">
									<p>
										<strong>Subject : Quarterly compliance report for quarter ended Month </strong> &nbsp;<input class="form-control d-inline w-10 ended_date" type="month" id="ended_date" name='<portlet:namespace/>ended_date'> <br>
										<strong>Submitted by the</strong> &nbsp;<input class="form-control by_the_custodian" type="text" name='<portlet:namespace/>by_the_custodian'>
									</p>
								</div>
							</div>
						</div>
						
						<br>
						<div class="form-inline row">
							<div class="col-md-12">
								<div class="form-group">
									<p>
										This is with reference to the subject cited above.<br>
										The Compliance Certificate along with annexures as submitted by your office for Quarter &nbsp; <input class="form-control quarter" type="text" name='<portlet:namespace/>quarter'> <br>
										of FY <span id="set_subject_date"></span> has been analyzed and following are the comments of NPS Trust on the same:
									</p>
								</div>
							</div>
						</div>
						<div class="statement-wrapper">
			               	<div id="table" class="table-editable">
			               		<div class="table-cont" >
									<table id="myTableDiscription" class="table">
							
										<thead>
											<tr>
												<th>Sr no. </th>
												<th>Discriptions </th>
												<th>Remarks </th>
												<th>NPS Trust Observations </th>
											</tr>
										</thead>
										
										<tbody>
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="1"><br>
												</td>
												<td>
													<p>Whether custodian received clear funds from PFs on T+0 basis before entering deals.<br><br>
													Number of cases where clear funds not received before entering deal and the reasons thereof.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks1" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation1" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="2"><br>
												</td>
												<td>
													<p>Number of cases where deal was not settled bhy custodian on settlement date and the reasons thereof.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks2" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation2" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="3"><br>
												</td>
												<td>
													<p>Number of cases where DIP/DIS was not provided by the PF.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks3" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation3" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="4"><br>
												</td>
												<td>
													<p>Details of the cases where the Custodian holding and PF's scheme holding differs.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks4" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation4" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="5"><br>
												</td>
												<td>
													<p>Details of the cases where the custodian holding differs from NSDL/CSDL/RBI.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks5" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation5" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="6"><br>
												</td>
												<td>
													<p>Number of grievances/ complaints received from the PFs and time taken for their redressal.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks6" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation6" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="7"><br>
												</td>
												<td>
													<p>Number of cases where corporate actions was late informed to PFs and the reasons thereof.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks7" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation7" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="8"><br>
												</td>
												<td>
													<p>Whether custodian has requisites authorization/PoA from PFs.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks8" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation8" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="9"><br>
												</td>
												<td>
													<p>Details of securities which are encumbered, pledged, hypothecated or any charge or lien marked.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks9" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation9" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="10"><br>
												</td>
												<td>
													<p>Delay in receipt of the interest/redemption value/bonus/corporate actions in respect of the securities belonging to PFs.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks10" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation10" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="11"><br>
												</td>
												<td>
													<p>Details of TDS deducted on interest/coupon received.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks11" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation11" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="12"><br>
												</td>
												<td>
													<p>Details of assignment or delegation of its function related to NPS.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks12" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation12" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											
											<tr>
												<td>
													<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="13"><br>
												</td>
												<td>
													<p>Securities held by PFs not forming a part of F&O segment.</p><br>
												</td>
												<td>
													<textarea class="form-control remarks" name="<portlet:namespace />remarks13" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												<td>
													<textarea class="form-control nps_trust_observation" name="<portlet:namespace />nps_trust_observation13" rows="4" cols="50" maxlength="2000"></textarea><br>
												</td>
												
											</tr> 
											</tbody>
										</table>
										<br><br>
									</div>
								</div>
							</div>
					<div class="nps-input-box">
                    	<button id="btn-submit" class="common-btn d-inline-block" name="Submit" type="button" >Save</button>
                        <!-- <a href="javascript:;" class="common-btn d-inline-block">save</a> -->
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
	


<style type="text/css">

.d-inline {
    display: inline !important;
    width: 15%;
}

.formdate-dline {
    display: inline !important;
    width: 49%;
}

.page_title {
	font-size: 18px;
}

.grid-container{
	grid-column-gap: 20px;
	margin-left: auto;
}

</style>
<script type="text/javascript">

$( ".quarter" ).on( "click", function() {
  	var setdate = $('#ended_date').val();
	$( "#set_subject_date" ).text(setdate.substring(0, 4));
});

$(document).ready(function() {
	toastr.options = {
			  "debug": false,
			  "positionClass": "toast-bottom-right",
			  "onclick": null,
			  "fadeIn": 300,
			  "fadeOut": 1000,
			  "timeOut": 9000,
			  "extendedTimeOut": 1000
			}
	
});

function onlyNumberKey(evt) {
    
    // Only ASCII character in that range allowed
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 45 || ASCIICode > 57))
        return false;
    return true;
}

$("#Annexure-twoB-Form").validate({
  	rules: {
	
  }  

});



$(".animaion").hide();
$('#Annexure-twoB-Form').on('submit', function(e){ 
	e.preventDefault();
	
    $('.fromdate').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.file_number').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.to_name').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.to_address').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.ended_date').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.quarter').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.by_the_custodian').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    
    $('.remarks').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.nps_trust_observation').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
	
	
	if($( "#Annexure-twoB-Form" ).valid()){
		
        var fd = new FormData(this);
        $(".animaion").show();
        $.ajax({
            url: '${annexuretwobformtURL}',  
            type: 'POST',
            cache: false,
            data:fd,
            contentType: false,
            processData: false,
            success:function(data){
            	console.log("result - ",data);        		
          		if(data == "success"){
          			 $(".animaion").hide();
               		toastr.success('Form has been submitted successfully!');
               		$("#Annexure-twoB-Form")[0].reset();
          		}else{
          			console.log("Error Occured in back-end call");
               		$(".animaion").hide();
               		toastr.error('An error occured while submitting the form - Contact Admin');
          		}
            },
            error: function() {
           		console.log("Error Occured in ajax call");
           		$(".animaion").hide();
           		toastr.error('An error occured while submitting the form');
            },
            complete: function(){
            	$(".animaion").hide();
	        }
        });
	}else{
		toastr.error('Please fill the required field(s).');
		}
});

</script>
