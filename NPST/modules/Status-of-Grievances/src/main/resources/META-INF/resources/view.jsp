<%@ include file="/init.jsp" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/>

<portlet:resourceURL id="/TransferringofGrievancesForm/save" var="TransferringofGrievancesformtURL"></portlet:resourceURL>

        <div class="card_blocks">
            <div class="row">
                <div class="col">
                    <div class="page_title">
                        <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Transferring of Grievances to PFRDA
                    </div>
                </div>
            </div>

            <div class="form_block mx-4">
                <form id="Transferring_of_Grievances_to_PFRDA" action="#" method="post">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p class="mb-0">File No : <input class="form-check-input file_number" type="text"  name='<portlet:namespace/>file_number'></p><br>
                                        <p class="mb-0">Date : <input class="form-check-input date" type="date"  name='<portlet:namespace/>date'></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <p class="font-weight-bold mb-0">Name :</p>
                                <p class="font-weight-bold mb-0"><input class="form-check-input name" type="text"  name='<portlet:namespace/>name'></p><br>
                                <p class="font-weight-bold">Address :</p>
                                <p class="mb-0"><textarea class="form-control address col-md-4" name="<portlet:namespace />address" rows="4" cols="50" maxlength="2000"></textarea></p>
                            </div>
                        </div>
                    </div>

                    <p class="subject_class"><strong>Subject: </strong> Status of Grievances handled at NPS Trust in the Month</p>

                    <p>Madam</p>

                    <p>
                        NPS Trust is handling escalations as per PFRDA (Redressal of Subscriber Grievance) Regulations, 2015. The summary of grievances handled at NPS Trust for the month of <input class="p-1 rounded border nps_trust_month_of" type="text" name="<portlet:namespace />nps_trust_month_of" /> is as below:-
                    </p>

                    <div class="row">
                        <div class="col-12 text-center mt-5">
                            <h5>REFERRALS HANDLED AT NPS TRUST</h5>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <table class="table table-bordered w-100 mb-5">
                                <tr>
                                    <th>Referrals</th>
                                    <th>Opening Balance (NPST)</th>
                                    <th>Escalated to NPST (out of total opening balance)</th>
                                    <th>Received during the month</th>
                                    <th>Reviewed & assigned by NPST</th>
                                    <th>Resolved during the month</th>
                                    <th>Outstanding at the end of the month</th>
                                </tr>
                                <tr class="text-center">
                                    <td colspan="7"><input class="form-check-input table_date" type="date"  name='<portlet:namespace/>table_date'></td>
                                </tr>
                                <tr>
                                    <td>Escalated to NPST</td>
                                    <td><textarea class="form-control escalated_to_npst" name="<portlet:namespace />opening_balance1" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control escalated_to_npst" name="<portlet:namespace />escalated_to_npst1" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control escalated_to_npst" name="<portlet:namespace />received_during_the_month1" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control escalated_to_npst" name="<portlet:namespace />reviewed1" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control escalated_to_npst" name="<portlet:namespace />resolved_during_the_month1" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control escalated_to_npst" name="<portlet:namespace />outstanding_at_the_end_of_the_month1" rows="4" cols="50" maxlength="2000"></textarea></td>
                                </tr>
                                <tr>
                                    <td>Raised against NPST</td>
                                    <td><textarea class="form-control raised_against_npst" name="<portlet:namespace />opening_balance2" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control raised_against_npst" name="<portlet:namespace />escalated_to_npst2" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control raised_against_npst" name="<portlet:namespace />received_during_the_month2" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control raised_against_npst" name="<portlet:namespace />reviewed2" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control raised_against_npst" name="<portlet:namespace />resolved_during_the_month2" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control raised_against_npst" name="<portlet:namespace />outstanding_at_the_end_of_the_month2" rows="4" cols="50" maxlength="2000"></textarea></td>
                                </tr>
                                <tr>
                                    <td>Total</td>
                                    <td><textarea class="form-control total" name="<portlet:namespace />opening_balance3" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control total" name="<portlet:namespace />escalated_to_npst3" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control total" name="<portlet:namespace />received_during_the_month3" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control total" name="<portlet:namespace />reviewed3" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control total" name="<portlet:namespace />resolved_during_the_month3" rows="4" cols="50" maxlength="2000"></textarea></td>
                                    <td><textarea class="form-control total" name="<portlet:namespace />outstanding_at_the_end_of_the_month3" rows="4" cols="50" maxlength="2000"></textarea></td>
                                </tr>
                            </table>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col">
                                        <label>Yours faithfully, </label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p class="bold-text">(Samir Raheja)</p>
                                        <p class="bold-text">General Manager</p>
                                        <p>Copy to: Ms. Mamta Rohit, Executive Director, PFRDA, New Delhi - 110 016</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row text-center">
                        <div class="col-md-12">
                            <input type="submit" class="nps-btn" id="submit" value="Submit">
                        </div>
                    </div>

                </form>

            </div>

			<div class="animaion" style="display:none;">
			 	<div class="row">
					<div class="loading-animation"></div>
				</div>
			</div> 

        </div>
        
 <style type="text/css">

.page_title {
	font-size: 18px;
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
			  "timeOut": 9000,
			  "extendedTimeOut": 1000
			}
	
});


$("#Transferring_of_Grievances_to_PFRDA").validate({
  	rules: {
	
  }  

});

$(".animaion").hide();
$('#Transferring_of_Grievances_to_PFRDA').on('submit', function(e){ 
	e.preventDefault();
	
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
    
    $('.date').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.name').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.address').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.nps_trust_month_of').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.table_date').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.escalated_to_npst').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    
    $('.raised_against_npst').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
    
    $('.total').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
	
	
	if($( "#Transferring_of_Grievances_to_PFRDA" ).valid()){
		
        var fd = new FormData(this);
        $(".animaion").show();
        $.ajax({
            url: '${TransferringofGrievancesformtURL}',  
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
               		$("#Transferring_of_Grievances_to_PFRDA")[0].reset();
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