<%@page import="SES.NonUnanimousVoting.constants.SESNonUnanimousVotingPortletKeys"%>
<%@ include file="/init.jsp" %>

<!-- <portlet:resourceURL var="addClosingBalResourceURL" id="/closingbal/save"></portlet:resourceURL> -->
<portlet:resourceURL id="<%=SESNonUnanimousVotingPortletKeys.sesNonUnanimous%>" var="sesNonUnanimousResourceURL" />
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/> -->

<div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> SES Non Unanimous Voting Amongst PFMs
                </div>
            </div>
        </div>
	
	<div class="form_block mx-4">
		<form class="form" id="sesNonUnanimous" action="#" method="post">
		<!-- <aui:form id="sesNonUnanimous" method="post"> -->

			<div class="row">
				<div class="col-md-12 text-right">
					<button type="button" class="nps-btn btn-addrow" id="addRow">Add Row</button>
				</div>
			</div>
			<br>
			
			<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
			<input type="hidden" class="countrows" name="<portlet:namespace />countrows">
			<table id="myTable" class="table table-bordered table-striped">
	
				<thead>
					<tr>
						<th>Name of the Co.</th>
						<th>Meeting Date</th>
						<th>Resolution</th>
						<th>PF's Voted For</th>
						<th>PF's Voted Abstain</th>
						<th>PF's Voted Against</th>
						<th>Final Vote For/Against</th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control company-name" id="company-name" name="<portlet:namespace />company_name_0"><br>
						</td>
						<td>
							<input type="date" class="form-control meeting-date" id="meeting-date" name="<portlet:namespace />meeting_date_0"><br>
						</td>
						<td>
							<textarea class="form-control resolution" id="resolution" name="<portlet:namespace />resolution_0"></textarea><br>
						</td>
						<td>
							<textarea class="form-control voted-for" id="voted-for" name="<portlet:namespace />voted_for_0"></textarea><br>
						</td>
						<td>
							<textarea class="form-control voted-abstain" id="voted-abstain" name="<portlet:namespace />voted_abstain_0"></textarea><br>
						</td>
						<td>
							<textarea class="form-control voted-against" id="voted-against" name="<portlet:namespace />voted_against_0"></textarea><br>
						</td>
						<td>
							<div class="form-check form-check-inline">
                            	<input class="form-check-input final-vote" type="radio" id="vote-for-0" name='<portlet:namespace/>final_vote_0' value="For">&nbsp; For
                        	</div>
                        	<div class="form-check form-check-inline">
                            	<input class="form-check-input final-vote" type="radio" id="vote-against-0" name='<portlet:namespace/>final_vote_0' value="Against">&nbsp; Against
                        	</div>
							<!-- <p id="error_p"></p> -->							 
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>

			<div class="row text-center">
	            <div class="col-md-12">
	                <input type="submit" class="nps-btn" id="btn-submit" value="Submit">
	            </div>
	        </div>
	
		</form>
		<!-- </aui:form> -->
	
	</div>
	
	<div class="animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
	</div> 
	
</div>

<script type="text/javascript">
var table = null;
$(document).ready(function() {
	table = $("#myTable").DataTable();
});
</script>

<style type="text/css">
#myTable_length, #myTable_filter, #myTable_info, #myTable_paginate {
	display: none;
}

label.error {
	display: none !important;
}

input.error {
	border-color: red;
}

/* .page_title {
	font-size: 18px;
} */

#myTable label.error {
	display: block !important;
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
	  "timeOut": 6000,
	  "extendedTimeOut": 1000
	}
	
	$("#sesNonUnanimous").validate({
	  	rules: {
		<portlet:namespace/>company_name_0: {
	      	required: true
	    },
	    <portlet:namespace/>meeting_date_0: {
		    required: true
		},
		<portlet:namespace/>resolution_0:{
			required: true
		},
		<portlet:namespace/>voted_for_0:{
			required: true,
		},
		<portlet:namespace/>voted_abstain_0:{
			required: true
		},
		<portlet:namespace/>voted_against_0:{
			required: true
		},
		<portlet:namespace/>final_vote_0:{
			required: true,
		}, 
	  },

	});		
	
	$('#success-message').hide();
	$('#error-message').hide();
	
	/* $(function(){
	    var dtToday = new Date();

	    var month = dtToday.getMonth() + 1;
	    var day = dtToday.getDate();
	    var year = dtToday.getFullYear();

	    if(month < 10)
	        month = '0' + month.toString();
	    if(day < 10)
	        day = '0' + day.toString();

	    var maxDate = year + '-' + month + '-' + day;    
	    $('#closing-balance-date').attr('max', maxDate);
	    $('#confirm-date').attr('max', maxDate);
	}); */
	
	var count = 1; 
	//var count_2 = 1;
	//var count_3 = 2;
    $("#addRow").click(function () {
       $(".rowcount").val(count);
       var htmlContent = '';
       htmlContent = '<tr>' + 
       	
       	'<td><input type="text" class="form-control company-name" id="company-name-'+count+'" name="<portlet:namespace />company_name_'+count+'"> </td>' +
       	'<td><input type="date" class="form-control meeting-date" id="meeting-date-'+count+'" name="<portlet:namespace />meeting_date_'+count+'"> </td>' +
		'<td><textarea class="form-control resolution" id="resolution-'+count+'" name="<portlet:namespace />resolution_'+count+'"></textarea> </td>' +
		'<td><textarea class="form-control voted-for" id="voted-for-'+count+'" name="<portlet:namespace />voted_for_'+count+'"></textarea> </td>' +
		'<td><textarea class="form-control voted-abstain" id="voted-abstain-'+count+'" name="<portlet:namespace />voted_abstain_'+count+'"></textarea> </td>' +
		'<td><textarea class="form-control voted-against" id="voted-against-'+count+'" name="<portlet:namespace />voted_against_'+count+'"></textarea> </td>' +
		'<td><div class="form-check">' +
                 '<input class="form-check-input final-vote" type="radio" id="vote-for-'+count+'" name="<portlet:namespace />final_vote_'+count+'" value="For">&nbsp; For </div>' + 
              
             '<div class="form-check"> ' + 
                '<input class="form-check-input final-vote" type="radio" id="vote-against-'+count+'" name="<portlet:namespace />final_vote_'+count+'" value="Against">&nbsp; Against </div> </td>' +
         '<td class="text-center"><button class="btn btn-danger btn-sm btn-small remove" id="removeRow" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>' +
       '</tr>';

       $('#myTable tbody').append(htmlContent);
		count++;
		//count_2++;
		//count_3++;

		 $('.company-name').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		
		$('.meeting-date').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.resolution').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.voted-for').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.voted-abstain').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.voted-against').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		
		$('.final-vote').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    }); 
		
   });
    
    $("#myTable").on('click','.remove',function(){
        $(this).closest('tr').remove();
     });

    $("#sesNonUnanimous").on('submit', (function(e) {
    	//$("#btn-submit").click(function() {	
    		e.preventDefault();
    		
    		if($( "#sesNonUnanimous" ).valid()){

    			console.log("Inside button click");
    			console.log("Valid ", $( "#sesNonUnanimous" ).valid());
    			
    			//var table = $('#myTableVoting').DataTable();
    			//var countRow = table.data().count();
    			
    			//$(".countrows").val(countRow);
    			
    			//var ad1 = $("#address-line-1").val();
    			//console.log("ad1 ", ad1);
    			var companyName = $('.company-name').map(function(){return $(this).val();}).get();
    			var meetingDate = $('.meeting-date').map(function(){return $(this).val();}).get();
    			var resolution = $('.resolution').map(function(){return $(this).val();}).get();
    			var votedFor = $('.voted-for').map(function(){return $(this).val();}).get();
    			var votedAbstain = $('.voted-abstain').map(function(){return $(this).val();}).get();
    			var votedAgainst = $('.voted-against').map(function(){return $(this).val();}).get();
    			var finalVote = $('.final-vote').map(function(){return $(this).val();}).get();

    			//console.log("Eq array ", tableArray);
    			//if($("form.form").valid()){
    				var formData = new FormData($("form.form")[0]);
    				//var formData = new FormData();
    				
    				$(".animaion").show();
    				$.ajax({
    		            type: "POST",
    		            enctype: 'multipart/form-data',
    		            processData: false,
    		            contentType: false,
    		            cache: false,
    		            url: '${sesNonUnanimousResourceURL}',
    		            data: formData,
    		            success: function(data) {
    		            	console.log("Inside Success");
    		            	//$('#success-message').show();
    		            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
    		            	$(".animaion").hide();
    		            	toastr.success('Form has been submitted successfully!');
    		            	//$("form.form")[0].reset();
    		            	$("#sesNonUnanimous")[0].reset();
    		            },
    		            error: function() {
    		            	//$('#error-message').show();
    		            	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
    		            	$(".animaion").hide();
    		            	toastr.error('An error occured while submitting the form. Please try again');
    		            	console.log("Error Occured in ajax call");
    		            },
    		            complete: function(){
    		            	$(".animaion").hide();
    						setTimeout(function() { location.reload(true); }, 5000);
    			        }
    		
    		        });
    			//} 
    			 /* else {
    	    		toastr.error('Please fill the required field(s).');
    	   		}  */
    			
    			
    		}

    	}));
    
});



 

</script>