<%@ include file="/init.jsp" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/>

<portlet:resourceURL id="/nonUnanimousReport/save" var="nonunanimousreportURL"></portlet:resourceURL>

<%-- <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> NON UNANIMOUS REPORT
                </div>
            </div>
        </div> --%>
	
	<div class="nps-page-main nps-upload-report nps-statement-wrapper">
	<div class="row">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
	            <h4>NON- Unanimous Voting</h4>
				<form id="Non-unanimous-report-Form" class="row form">
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
					  <input type="hidden" class="rowcount_secound_table" name="<portlet:namespace />rowcount_secound_table">
						
						<div class="col-lg-12">
							<p class=" my-3 font-weight-bold subject-line">Quarterly proxy voting report summary for Q2 FY  
								<input class="form-control d-inline w-10" type="month" id="Fromdate" name='<portlet:namespace/>Fromdate'><strong> : 
								</strong><input class="form-control d-inline w-10" type="month" id="Todate" name='<portlet:namespace/>Todate'><br> 
								(as per SES platform and consensus voting report)
							</p> 
						</div>
							
					<div class="statement-wrapper">
	                  <div id="table" class="table-editable">
	                  	<div class="text-end">
	                   		<button id="addRowVoting" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
						</div>
						<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
	                   <div class="table-cont" > 
	                       <table class="table" id="myTableVoting">
	                       	<thead>
								<tr>
									<th>Sr no. </th>
									<th>Name Of the PFM </th>
									<th>No. of resolutions requiring voting </th>
									<th>Voted For </th>
									<th>Abstained </th>
									<th>Voted Against</th>
									<th data-orderable="false">Remove</th>
								</tr>
							</thead>
							
							<tbody>
								<tr>
									<td>
										<input type="text" class="form-control sr_no" id="sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="1"><br>
									</td>
									<td>
										<input type="text" class="form-control name_of_the_pfm" id="name_of_the_pfm" name="<portlet:namespace />name_of_the_pfm1"><br>
									</td>
									<td>
										<input type="text" class="form-control no_of_resolutions" id="no_of_resolutions" name="<portlet:namespace />no_of_resolutions1" onkeypress="return onlyNumberKey(event)"><br>
									</td>
									<td>
										<input type="text" class="form-control voted_for" id="voted_for" name="<portlet:namespace />voted_for1" onkeypress="return onlyNumberKey(event)"><br>
									</td>
									<td>
										<input type="text" class="form-control abstained" id="abstained" name="<portlet:namespace />abstained1" onkeypress="return onlyNumberKey(event)"><br>
									</td>
									<td>
										<input type="text" class="form-control voted_against" id="voted_against" name="<portlet:namespace />voted_against1" onkeypress="return onlyNumberKey(event)"><br>
									</td>
									<td class="text-center">
									</td>
									
								</tr> 
							</tbody>
						</table>
						<br><br>
					</div>
			       </div>
			     </div>
					<!-- 2nd data-table ------------------------------ -->
					
						<br><br>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<p>
										Resolutions with non-unanimous voting 
									</p>
								</div>
							</div>
						</div>
			
				<div class="statement-wrapper">
                  	<div id="table" class="table-editable">
                  		<div class="text-end">
                   			<button id="addRowResolution" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
						</div>
						<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
                  	 <div class="table-cont" > 
                       	<table class="table" id="myTableResolution">
                       		<thead>
								<tr>
									<th>Sr no. </th>
									<th>Name Of the Company </th>
									<th>Meeting Date </th>
									<th>Resolution </th>
									<th>SES Recommendation </th>
									<th>SES Reason for Recommendation</th>
									<th>PFs Voted For </th>
									<th>PFs Voted Abstain </th>
									<th>PFs Voted Against </th>
									<th>Final Vote  For / Against </th>
									<th>PFs Reason for non-unanimous voting </th>
									<th data-orderable="false">Remove</th>
								</tr>
							</thead>
							
							<tbody>
								<tr>
									<td>
										<input type="text" class="form-control sr_no_two" id="sr_no_two" name="<portlet:namespace />sr_no_two" readonly="readonly" value="1"><br>
									</td>
									<td>
										<input type="text" class="form-control name_of_the_company" id="name_of_the_company" name="<portlet:namespace />name_of_the_company1"><br>
									</td>
									<td>
										<input type="date" class="form-control  meeting_date" id="meeting_date" name="<portlet:namespace />meeting_date1"><br>
									</td>
									<td>
										<input type="text" class="form-control resolution" id="resolution" name="<portlet:namespace />resolution1"><br>
									</td>
									<td>
										<input type="text" class="form-control ses_recommendation" id="ses_recommendation" name="<portlet:namespace />ses_recommendation1"><br>
									</td>
									<td>
										<input type="text" class="form-control ses_reason" id="ses_reason" name="<portlet:namespace />ses_reason1"><br>
									</td>
									<td>
										<input type="text" class="form-control pfs_voted_for" id="pfs_voted_for" name="<portlet:namespace />pfs_voted_for1"><br>
									</td>
									<td>
										<input type="text" class="form-control pfs_voted_abstain" id="pfs_voted_abstain" name="<portlet:namespace />pfs_voted_abstain1"><br>
									</td>
									<td>
										<input type="text" class="form-control pfs_voted_against" id="pfs_voted_against" name="<portlet:namespace />pfs_voted_against1"><br>
									</td>
									<td>
										<input type="text" class="form-control final_vote" id="final_vote" name="<portlet:namespace />final_vote1"><br>
									</td>
									<td>
										<input type="text" class="form-control pfs_reason" id="pfs_reason" name="<portlet:namespace />pfs_reason1"><br>
									</td>
									<td class="text-center">
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
	input.error {
			border:1px solid red !important;
		}
/* .d-inline {
    display: inline !important;
    width: 14%;
}

.page_title {
	font-size: 18px;
} */

</style>

<script type="text/javascript">
var tableVoting = null;
var tableResolution = null;
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
	
	tableVoting = $("#myTableVoting").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
	
	tableResolution = $("#myTableResolution").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
});

$(function(){
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();

    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var maxDate = year + '-' + month + '-' + day;    
    $('#meeting_date').attr('min', maxDate);
});


function onlyNumberKey(evt) {
    
    // Only ASCII character in that range allowed
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 45 || ASCIICode > 57))
        return false;
    return true;
}

$("#Non-unanimous-report-Form").validate({
  	rules: {
  		<portlet:namespace/>Fromdate: {
  	      	required: true
  	    },
  	  <portlet:namespace/>Todate: {
        	required: true
      },
  		<portlet:namespace/>name_of_the_pfm1: {
      	required: true
    },
    <portlet:namespace/>no_of_resolutions1: {
	    required: true
	},
	<portlet:namespace/>voted_for1:{
		required: true
	},
	<portlet:namespace/>abstained1:{
		required: true
	},
	<portlet:namespace/>voted_against1:{
		required: true
	},
	<portlet:namespace/>name_of_the_company1:{
		required: true
	},
	<portlet:namespace/>meeting_date1:{
		required: true
	},
	<portlet:namespace/>resolution1:{
		required: true
	}, 
	<portlet:namespace/>ses_recommendation1:{
		required: true
	},
	<portlet:namespace/>ses_reason1:{
		required: true
	},
	<portlet:namespace/>pfs_voted_for1:{
		required: true
	},
	<portlet:namespace/>pfs_voted_abstain1:{
		required: true
	},
	<portlet:namespace/>pfs_voted_against1:{
		required: true
	},
	<portlet:namespace/>final_vote1:{
		required: true
	},
	<portlet:namespace/>pfs_reason1:{
		required: true
	},
  },  

}); 

var counterFT = 2;

$('#addRowVoting').on( 'click', function () {
	 tableVoting = '<tr>' + 
     
			        '<td><input type="text" class="sr_no form-control" name="<portlet:namespace/>sr_no " readonly="readonly" value="'+counterFT+'"></td>' +
			        '<td><input type="text" class="name_of_the_pfm form-control" name="<portlet:namespace/>name_of_the_pfm'+counterFT+'"></td>' +
			        '<td><input type="text" class="no_of_resolutions form-control" name="<portlet:namespace/>no_of_resolutions'+counterFT+'"></td>' +
			        '<td><input type="text" class="voted_for form-control" name="<portlet:namespace/>voted_for'+counterFT+'"></td>' +
			        '<td><input type="text" class="abstained form-control" name="<portlet:namespace/>abstained'+counterFT+'"></td>' +
			        '<td><input type="text" class="voted_against form-control" name="<portlet:namespace/>voted_against'+counterFT+'"></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeOne" id="removeRowOne" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableVoting tbody').append(tableVoting);

    counterFT++;
	
     $('.name_of_the_pfm').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.no_of_resolutions').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.voted_for').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.abstained').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.voted_against').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });  
    
} );

 
$("#myTableVoting").on('click','.removeOne',function(){
    $(this).closest('tr').remove();
 });
 
 
var counterST = 2;

$('#addRowResolution').on( 'click', function () {
	tableResolution = '<tr>' + 
    
			        '<td><input type="text" class="sr_no_two form-control" name="<portlet:namespace/>sr_no_two " readonly="readonly" value="'+counterST+'"></td>' +
			        '<td><input type="text" class="name_of_the_company form-control" name="<portlet:namespace/>name_of_the_company'+counterST+'"></td>' +
			        '<td><input type="date" class="meeting_date form-control" name="<portlet:namespace/>meeting_date'+counterST+'"></td>' +
			        '<td><input type="text" class="resolution form-control" name="<portlet:namespace/>resolution'+counterST+'"></td>' +
			        '<td><input type="text" class="ses_recommendation form-control" name="<portlet:namespace/>ses_recommendation'+counterST+'"></td>' +
			        '<td><input type="text" class="ses_reason form-control" name="<portlet:namespace/>ses_reason'+counterST+'"></td>' +
			        '<td><input type="text" class="pfs_voted_for form-control" name="<portlet:namespace/>pfs_voted_for'+counterST+'"></td>' +
			        '<td><input type="text" class="pfs_voted_abstain form-control" name="<portlet:namespace/>pfs_voted_abstain'+counterST+'"></td>' +
			        '<td><input type="text" class="pfs_voted_against form-control" name="<portlet:namespace/>pfs_voted_against'+counterST+'"></td>' +
			        '<td><input type="text" class="final_vote form-control" name="<portlet:namespace/>final_vote'+counterST+'"></td>' +
			        '<td><input type="text" class="pfs_reason form-control" name="<portlet:namespace/>pfs_reason'+counterST+'"></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small remove" id="removeRow" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

    $('#myTableResolution tbody').append(tableResolution);

    counterST++;
	
    $('.name_of_the_company').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
   $('.meeting_date').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
   $('.resolution').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
   $('.ses_recommendation').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
   $('.ses_reason').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });  
   
   $('.pfs_voted_for').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
   $('.pfs_voted_abstain').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
   $('.pfs_voted_against').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
   $('.final_vote').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
   $('.pfs_reason').each(function() {
       $(this).rules("add", 
           {
	        	required: true,
               messages: {
                   required: "This field is required.",
               }
           }
       );
   });
   
} );

$("#myTableResolution").on('click','.remove',function(){
    $(this).closest('tr').remove();
 });
 
$(".animaion").hide();
$('#Non-unanimous-report-Form').on('submit', function(e){ 
	e.preventDefault();
	
	
	if($( "#Non-unanimous-report-Form" ).valid()){
		
		var rowCountFT = $("#myTableVoting tr").length -1;
		
		var rowCountST = $("#myTableResolution tr").length -1;
		
		$('.rowcount_first_table').val(rowCountFT);
		$('.rowcount_secound_table').val(rowCountST);
		
        var fd = new FormData(this);
        $(".animaion").show();
        $.ajax({
            url: '<%=nonunanimousreportURL%>',  
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
               		$("#Non-unanimous-report-Form")[0].reset();
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