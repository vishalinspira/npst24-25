<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.CraEnps"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL var="savecraenpsURL" id="/craenps/save"></portlet:resourceURL>
<portlet:resourceURL id="/rejectionreturn/getreportlogdetails" var="getreportlogdetailsURL"></portlet:resourceURL>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/>
<% 
 JSONObject resultJsonObject = (JSONObject)request.getAttribute("resultJsonObject");
 List<CraEnps> craEnps = (List<CraEnps>) request.getAttribute("craEnps");

 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

%>
<%-- <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Annexure 8 - Closing Balance Confirmation For NPS Trust Collection Accounts
                </div>
            </div>
        </div> --%>
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
<div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
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
                 <p  class="back_btn"><a class="backbtn" href="/web/guest/upload-cra-maker"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
         </div>
  </div>
    
	<div class="row">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
       		<h4>eNPS Death Claim Data</h4>
           	<form class="row form" id="closingBalForm">
				<input type="hidden" name="dlfileid"/>
				<input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" value="<%=resultJsonObject.get("reportUploadLogId")%>"/>
				<input type="hidden" value="123" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
				<div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" readonly="readonly" value="<%=resultJsonObject.get("reportDate")%>">
                    </div>
                </div>

		
			
				<div class="statement-wrapper">
                  <div id="table" class="table-editable">
					<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
                   <div class="table-cont" > 
                       <table class="table" id="myTable">
							<thead>
								<tr>
									<th>Sr. No</th>
									<th>PRAN</th>
									<th>Name of Subscriber</th>
									<th>Name of Claiment</th>
									<th>Date of Authorisation of claim </th>
									<th>Date of dispatch of claim form </th>
									<th>Tracking ID</th>
									<th>Date of receipt of authorised form</th>
									<th>Date of claim processing</th>
									<th>TAT</th>
								</tr>
							</thead>
				
							<tbody>
							   <%
							    if(Validator.isNotNull(craEnps) && craEnps.size()>0){
							    	for(CraEnps craEnps2 : craEnps){
							    		%>
							    		<tr>
											<td>
												<input type="hidden" name="<portlet:namespace />id[]" value="<%=craEnps2.getId()%>">
												<input type="text" class="form-control srno digits" id="<portlet:namespace />srno" name="<portlet:namespace />srno[]"
												 maxlength="16" readonly="readonly" value="<%=craEnps2.getSrno()%>"><br>
												<span id="srno-error" style="color:red; font-size:12px" for="<portlet:namespace />srno"></span>
											</td>
											<td>
												<input type="text" class="form-control pran digits" id="<portlet:namespace />pran"
												 name="<portlet:namespace />pran[]" readonly="readonly" value="<%=craEnps2.getPran()%>"><br>
												<span for="<portlet:namespace />pran" style="color:red; font-size:12px"></span>
											</td>
											<td>
												<input type="text" class="form-control subName " id="<portlet:namespace />subName"
												 name="<portlet:namespace />subName[]" readonly="readonly" value="<%=craEnps2.getSubName()%>"><br>
												<span for="<portlet:namespace />subName" style="color:red; font-size:12px"></span>
											</td>
											<td>
												<input type="text" class="form-control claimentName " id="<portlet:namespace />claiment_name"
												 name="<portlet:namespace />claimentName[]" readonly="readonly" value="<%=craEnps2.getClaimentName()%>"><br>
												<span for="<portlet:namespace />claimentName" style="color:red; font-size:12px"></span>
											</td>
											<td>
												<input type="date" class="form-control authClaimDate " id="authClaimDate"
												 name="<portlet:namespace />authClaimDate[]" readonly="readonly" value="<%=simpleDateFormat.format(craEnps2.getAuthClaimDate())%>"><br>
												<span for="<portlet:namespace />authClaimDate" style="color:red; font-size:12px"></span>
											</td>
											<td>
												<input type="date" class="form-control claimDispatchDate " id="claimDispatchDate"
												 name="<portlet:namespace />claimDispatchDate[]" readonly="readonly" value="<%=simpleDateFormat.format(craEnps2.getClaimDispatchDate())%>"><br>
												<span for="<portlet:namespace />claimDispatchDate" style="color:red; font-size:12px"></span>
											</td>
											<td>
												<input type="text" class="form-control trackId" id="trackId"
												 name="<portlet:namespace />trackId[]" readonly="readonly" value="<%=craEnps2.getTrackId()%>"><br>
												<span for="<portlet:namespace />trackId" style="color:red; font-size:12px"></span>
											</td>
											<td>
												<input type="date" class="form-control receiptAuthFormDate " data-srno="<%=craEnps2.getSrno()%>" id="receiptAuthFormDate"
												 name="<portlet:namespace />receiptAuthFormDate[]" ><br>
												<span for="<portlet:namespace />receiptAuthFormDate" style="color:red; font-size:12px"></span>
											</td>
											<td>
												<input type="date" class="form-control claimProcessDate " data-srno="<%=craEnps2.getSrno()%>" id="claimProcessDate"
												 name="<portlet:namespace />claimProcessDate[]"><br>
												<span for="<portlet:namespace />claimProcessDate" style="color:red; font-size:12px"></span>
											</td>
											
											<td>
												<input type="text" class="form-control tat " id="tat" data-srno="<%=craEnps2.getSrno()%>"
												 name="<portlet:namespace />tat[]" readonly="readonly"><br>
												<span for="<portlet:namespace />tat" style="color:red; font-size:12px"></span>
											</td>
											
										</tr>
							    		<%
							    	}
							    }
							   %>
								
							</tbody>
				
						</table>
						<br><br>
						</div>
			          </div>
			      </div>
					<div class="nps-input-box">
		                	<button id="btn-submit" class="common-btn d-inline-block" name="Submit" type="button" >Save</button>
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
		input.error {
			border:1px solid red !important;
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

function calculateTAT(srno){
	   var date1 = new Date($(".receiptAuthFormDate[data-srno='"+srno+"']").val());
	   var date2 = new Date($(".claimProcessDate[data-srno='"+srno+"']").val());
	     
	   console.log(date1);
	   console.log(date2);
	   // To calculate the time difference of two dates
	   var Difference_In_Time = date2.getTime() - date1.getTime();
	     
	   // To calculate the no. of days between two dates
	   var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
	   return Difference_In_Days;
}



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
	
	$('#success-message').hide();
	$('#error-message').hide();
	
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
	    $('#closing-balance-date').attr('max', maxDate);
	    $('#confirm-date').attr('max', maxDate);
	});
	
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z]+$/i.test(value);
		}, "Please enter characters only"); 
	
	$("#closingBalForm").validate({
	  	rules: {
		"<portlet:namespace/>receiptAuthFormDate[]" : {
	      	required: true
	    },
	    "<portlet:namespace/>claimProcessDate[]": {
		    required: true
		},
	  },

	});
	
	
    
    $("#myTable").on('click','.remove',function(){
        $(this).closest('tr').remove();
     });
  //$("#closingBalForm").on('submit', (function(e) {
    $("#btn-submit").click(function() {	
    	//e.preventDefault();
    	
    	if($( "#closingBalForm" ).valid()){
    		//if(registerSubmitFunction()) {
    		
    		console.log("Inside button click");
    		console.log("Valid ", $( "#closingBalForm" ).valid());
    		
    		
    		var formData = new FormData($("#closingBalForm")[0]);
    		
    		
    		console.log("formData: ", JSON.stringify(formData));
    		$(".animaion").show();
    		$(".nps-upload-report").hide();
    		$.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                url: '<%=savecraenpsURL%>',
                data: formData,
                success: function(data) {
                	console.log("Inside Success");
                	//$('#success-message').show();
                	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
                	$(".animaion").hide();
                	$("#closingBalForm")[0].reset();
                	$('#success_tic').modal('show');
           		 	$(".nps-upload-report").show();
           		 	$('input[name="SBIRequestForDataReport"').val("");
                },
                error: function() {
                	//$('#error-message').show();
                	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
                	$(".animaion").hide();
                	$(".nps-upload-report").show();
                	console.log("Error Occured in ajax call");
                	$('#output').html("An error occured while submitting the form. Please try again</a>");
            		$('#errorExcel').modal('show');
                },
                complete: function(){
                	$(".animaion").hide();
                	$(".nps-upload-report").show();
    	        }

            });
    	}

    });	
    $('#success_tic').on('hidden.bs.modal', function (e) {
		location.reload(); 
    });
    $('#errorExcel').on('hidden.bs.modal', function (e) {
		location.reload(); 
    });
    
    $(".receiptAuthFormDate , .claimProcessDate").change(function(){
    	let srno = $(this).attr("data-srno");
    	let tat  = calculateTAT(srno);
    	$(".tat[data-srno='"+srno+"']").val(tat);
    })
    
});

$(document).ready(function() {
    $(document).on('keypress', ".alpha", function(e){
        var keyCode = e.keyCode || e.which;
   	 
        //Regex for Valid Characters i.e. Alphabets.
        var regex = /^[A-Za-z]+$/;
 
        //Validate TextBox value against the Regex.
        var isValid = regex.test(String.fromCharCode(keyCode));
 
        return isValid;
    	
    });
    
    
    $(document).on('keypress', ".digits", function(evt){
        // Only ASCII character in that range allowed
        var ASCIICode = (evt.which) ? evt.which : evt.keyCode
        if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 45 || ASCIICode > 57))
            return false;
        return true;
    	
    });
});

	


</script>