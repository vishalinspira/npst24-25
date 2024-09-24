<%@page import="TrusteeBankMonthlyAnnexure_9.constants.TrusteeBankMonthlyAnnexure_9PortletKeys"%>
<%@ include file="/init.jsp" %>

<!-- <portlet:resourceURL id="/performancerep/save" var="performanceRepResourceURL"/> -->
<portlet:resourceURL id="<%=TrusteeBankMonthlyAnnexure_9PortletKeys.performanceRep%>" var="performanceRepResourceURL" /> 
<portlet:resourceURL id="/rejectionreturn/getreportlogdetails" var="getreportlogdetailsURL"></portlet:resourceURL>


		<style>
			.modal {
			    display: none;
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
                 <p  class="back_btn"><a class="backbtn" href="/web/guest/monthly-average"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
         </div>
    </div>
    
	<div class="row">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
	            <h4>Performance Report & Deviation</h4>
	            <form class="row form" id="perfromanceRepForm">
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
	                <div class="row">
	                 <div class="nps-input-box file-upload col-lg-6 col-md-6 col-sm-12 col-12">
                        <label for="name">Report </label>
                        <div class="file-select">
                           <div class="file-select-name" id="noFile">File Name</div>
                           <div class="file-select-button common-btn" id="fileName">Choose File</div>
                           <input type="file" class="perfromanceRepFile" id="perfromanceRepFile" name="perfromanceRepFile" />                               
                        </div>
                        <label id="error-perfromanceRepFile" class="error-message text-danger"></label>
                    </div>
                    </div>
                    <div class="row">
                    <div class="nps-input-box col-lg-6 col-md-6 col-sm-12 col-12">
                        <label for="name">Remarks</label>
                        <textarea name="remarks" id="remarks" class="" rows="4" cols="" maxlength="1000"></textarea>
                    </div>
                    </div>
	               <%--  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <label>Date</label>
							<input type="date" class="" id="report-date" name='<portlet:namespace/>performantdate'>
							<span id="date-error" class="text-danger"></span>
	                    </div>
	                </div>
	                <div class="class="col-lg-12">
	                	<p class="px-0 py-2 border-bottom-0"><b>To, </b><br/>The Chief Executive Officer</p>
	                	<p class="px-0 py-2 border-bottom-0">National Pension System (NPS) Trust</p>
	                </div>
	                <div class="col-lg-4 col-md-4 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <input type="text" id="address-line-1" name='<portlet:namespace/>addressline1' placeholder="Address Line 1" required>
	                    </div>
	                </div>
	                <div class="col-lg-4 col-md-4 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <input type="text" id="address-line-2" name='<portlet:namespace/>addressline2'placeholder="Address Line 2" required>
	                    </div>
	                </div>
	                <div class="col-lg-4 col-md-4 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <input type="text" id="address-line-3" name='<portlet:namespace/>addressline3' placeholder="Address Line 3" required>
	                    </div>
	                </div>
	                <div class="col-lg-12">
	                       <p class=" my-3 font-weight-bold subject-line">Sub: Performance report of the Trustee Bank i.e. 
								<input type="text" class="form-control d-inline w-25" id="trusteeName" name='<portlet:namespace/>trusteeName'>
								for the month ending
								<input type="date" class="form-control d-inline w-25" id="date-2" name='<portlet:namespace/>date2'>
							</p> 
	                </div>
	                <div class="col-lg-12">
	                	<p class="font-weight-bold">Sir/Madam</p>
	                </div>
	                
	                <div class="col-lg-12">
	                	<p>Name and registration no. of the Trustee Bank:</p>
	                </div>
	                
	                <div class="col-lg-12">
	                	<p>2. Trustee Bank had ensured that it has provided banking facilities in accordance with the provisions of the directions issued by the Authority in terms of the Act, the rules and the regulations made there under, operational service level agreements executed with the National Pension System Trust and the approved standard operating procedure based on the Authority’s guidelines.</p>
	                </div>
	                
	                <div class="col-lg-12">
	                	<p>3. All the reports have been submitted to Authority/NPS Trust as prescribed by the Authority in the regulations/guidelines.</p>
	                </div>
	                <div class="col-lg-12">
	                	<p>4. The summarized data of Trustee Bank’s collection/rejection report, transaction details, average balance and balances of various accounts along with report of any deviation/incident is submitted as per Annexure 10 for information of the NPS Trust</p>
	                </div>
	                
	                
	                <div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <label>Signature</label>
	                    	<input class="form-control alpha" type="text" id="signature" name='<portlet:namespace/>signature'>
	                    </div>
	                </div>
	                <div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <label>Name of the officer</label>
                       		<input class="form-control alpha" type="text" id="officer-name" name='<portlet:namespace/>officername'>
	                    </div>
	                </div>
	                <div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <label>Date:</label>
                       		<input class="form-control" type="date" id="submitDate" name='<portlet:namespace/>submitDate'>
	                    </div>
	                </div>
	                <div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <label>Place:</label>
                        	<input class="form-control" type="text" id="place" name='<portlet:namespace/>place'>
	                    </div>
	                </div> --%>
	                
                    <div class="nps-input-box">
                    	<button id="btn-submit" class="common-btn d-inline-block" name="Submit" type="button" >Save</button>
                        <!-- <a href="javascript:;" class="common-btn d-inline-block">save</a> -->
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<%-- <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Annexure 9 - Formant Of Performance Report And Deviations From The Trustee Bank To NPS Trust
                </div>
            </div>
        </div> --%>
        
<%-- <div class="nps-body-main">
    <div class="nps-page-main nps-upload-report">
        <div class="row">
            <div class="col-lg-7 col-md-10 col-sm-12 col-12">
                <div class="nps-report">
        		<h4>Annexure 9 - Formant Of Performance Report And Deviations From The Trustee Bank To NPS Trust</h4>
            	<aui:form id="myForm" method="post">
				<input type="hidden" name="dlfileid"/>
				<input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId"/>
				<input type="hidden" value="26" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
				<div class="row">
	               	<div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" readonly="readonly">
                     </div>
	            </div>
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<label>Date</label>
							<input type="date" class="form-control" id="report-date" name='<portlet:namespace/>performantdate'>
							<span id="date-error" class="text-danger"></span>
						</div>
					</div>
				</div>
				
				<div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <p class="mb-0"><b>To, </b><br/>The Chief Executive Officer</p>
                            <p>National Pension System (NPS) Trust</p>
                        </div>
                    </div>
                </div>
				
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<div class="row">
								<div class="col">
									<input type="text" class="form-control" id="address-line-1" name='<portlet:namespace/>addressline1' placeholder="Address Line 1">
									<span id="ad1-error" class="text-danger"></span>
								</div>	
							</div><br>
							
							<div class="row">
								<div class="col">
									<input type="text" class="form-control" id="address-line-2" name='<portlet:namespace/>addressline2' placeholder="Address Line 2">
								</div>	
							</div><br>
							
							<div class="row">
								<div class="col">
									<input type="text" class="form-control" id="address-line-3" name='<portlet:namespace/>addressline3' placeholder="Address Line 3">
								</div>	
							</div><br>
			
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<div class="form-group"> 
							<p class="text-center my-3 font-weight-bold subject-line">Sub: Performance report of the Trustee Bank i.e. 
								<input type="text" class="form-control d-inline w-25" id="trusteeName" name='<portlet:namespace/>trusteeName'>
								for the month ending
								<input type="date" class="form-control d-inline w-25" id="date-2" name='<portlet:namespace/>date2'>
							</p>
						</div>
					</div>
				</div> 
				
				<div class="row">
                    <div class="col">
                        <div class="form-group">
                            <p class="font-weight-bold">Sir/Madam</p>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <p>Name and registration no. of the Trustee Bank:</p>
                        </div>
                    </div>
                </div>
				
				
			<div class="row">
				<div class="col">
					<p>2. Trustee Bank had ensured that it has provided banking facilities in accordance with the provisions of the directions issued by the Authority in terms of the Act, the rules and the regulations made there under, operational service level agreements executed with the National Pension System Trust and the approved standard operating procedure based on the Authority’s guidelines.</p>
				</div>	
			</div>
			
			<div class="row">
				<div class="col">
					<p>3. All the reports have been submitted to Authority/NPS Trust as prescribed by the Authority in the regulations/guidelines.</p>
				</div>	
			</div>
			
			<div class="row">
				<div class="col">
					<p>4. The summarized data of Trustee Bank’s collection/rejection report, transaction details, average balance and balances of various accounts along with report of any deviation/incident is submitted as per Annexure 10 for information of the NPS Trust</p>
				</div>	
			</div><br>
			
			<div class="row">
            	<div class="col">
	                <div class="form-group">
	                    <label>Signature</label>
	                    <input class="form-control" type="text" id="signature" name='<portlet:namespace/>signature'>
	                </div>
            	</div>
			
				<div class="col">
                   <div class="form-group">
                       <label>Name of the officer</label>
                       <input class="form-control" type="text" id="officer-name" name='<portlet:namespace/>officername'>
                   </div>
           		</div>
			
				<div class="col">
                   <div class="form-group">
                       <label>Date:</label>
                       <input class="form-control" type="date" id="submitDate" name='<portlet:namespace/>submitDate'>
                   </div>
            	</div>

				<div class="col">
                    <div class="form-group">
                        <label>Place:</label>
                        <input class="form-control" type="text" id="place" name='<portlet:namespace/>place'>
                    </div>
               	</div>
                	
            </div>
			
			<div class="nps-input-box">
                        	<button id="upload" class="common-btn d-inline-block" name="Submit" type="button" >Save</button>
                           <!-- <a href="javascript:;" class="common-btn d-inline-block">save</a> -->
                        </div>
                    </aui:form>
                </div>
            </div>
        </div>
    </div>
</div> --%>
<div class="animaion" style="display:none;">
 	<div class="row">
		<div class="loading-animation"></div>
	</div>
</div>

	<style>
		input.error {
			border:1px solid red !important;
		}
	</style>


<script type="text/javascript">

function getreportUploadLogId(){
	var fd = new FormData($("form.form")[0]);
	$.ajax({
        url: '<%=getreportlogdetailsURL %>',  
        type: 'POST',
        data: fd,
        success:function(data){
        	console.log(data);
        	try {
            	data = JSON.parse(data);
            	if(data.status){
            		$("#upload").prop('disabled', false);
            		$(".reportUploadLogId").val(data.reportUploadLogId);
                	
                	//var now = new Date(data.reportDate);
                	//var today = now.getFullYear()+"-"+(now.month)+"-"+(now.day) ;
                	$(".reportDate").val(data.ReportDate);
            	}else{
            		$("#upload").prop('disabled', true);
            	}
            	
            } catch (e) {
            	console.log("error while parsing the json data",e);
            }
        },
        error: function() {
        	//$(".animaion").hide();
        	//toastr.error('An error occured while submitting the form');
       		console.log("Error Occured in ajax call");
        },
        complete: function(){
				console.log("Complete");
	     },
        cache: false,
        contentType: false,
        processData: false
    });
}

$('#perfromanceRepFile').bind('change', function () {
	  var filename = $("#perfromanceRepFile").val();
	  if (/^\s*$/.test(filename)) {
	     $(".file-upload").removeClass('active');
	     $("#noFile").text("No file chosen...");
	  }
	  else {
	     $(".file-upload").addClass('active');
	     $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
	  }
	});

$(function(){
	 getreportUploadLogId();

$('#btn-submit').on('click', function(){
	console.log("submit called");
	if ($('input[name="perfromanceRepFile"]').get(0).files.length === 0) {
	    console.log("No files selected.");
	    $("#error-perfromanceRepFile").html("Please select a file to upload");
	    return false;
	}else if($('input[name="perfromanceRepFile"]').get(0).files[0].name != "Performance Report & Deviation.pdf"){
		console.log("No files selected.");
	    $("#error-perfromanceRepFile").html("Please upload Performance Report & Deviation.pdf");
	    return false;
	}
	
	
   var fd = new FormData($("form.form")[0]);
   $(".content").hide();
   $(".animaion").show();
   $("#error-perfromanceRepFile").html("");

   $.ajax({
       url: '${performanceRepResourceURL}', 
       type: 'POST',
       data: fd,
       success: function(data) {
       	console.log("Inside success");
       	$(".content").show();
       	$(".animaion").hide();
       	$('#success_tic').modal('show');
       	//$('#success-message').show();
       	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
       	//toastr.success('Form has been submitted successfully!');
       	//$("#perfromanceRepForm")[0].reset();
       },

       error: function() {
       	$(".animaion").hide();
       	$('#output').html("An error occourd while submitting the form.");
   		$('#errorExcel').modal('show');
       	//$('#error-message').show();
       	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
       	//toastr.error('An error occured while submitting the form. Please try again');
       	console.log("Error Occured in ajax call");
       },
		complete: function(){
			$(".animaion").hide();
       	//setTimeout(function() { location.reload(true); }, 5000);
       },
       cache: false,
       contentType: false,
       processData: false
   });
});
$('#success_tic').on('hidden.bs.modal', function (e) {
	location.reload(); 
})
$('#errorExcel').on('hidden.bs.modal', function (e) {
	location.reload(); 
})
});


<%-- $(document).ready(function() {
	
	getreportUploadLogId();
	


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
	   // $('#report-date').attr('max', maxDate);
	    //$('#date-1').attr('max', maxDate);
	    //$('#date-2').attr('max', maxDate);
	    //$('#submitDate').attr('max', maxDate);
	});
	
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z]+$/i.test(value);
		}, "Letters only please"); 
	
	/* $("#perfromanceRepForm").validate({
	  	rules: {
		<portlet:namespace/>performantdate: {
	      	required: true
	    },
	    <portlet:namespace/>addressline1: {
		    required: true
		},
		<portlet:namespace/>trusteeName:{
			required: true
		},
		<portlet:namespace/>date2:{
			required: true
		},
		<portlet:namespace/>signature:{
			required: true
		},
		<portlet:namespace/>officername:{
			required: true
		},
		<portlet:namespace/>submitDate:{
			required: true
		},
		<portlet:namespace/>place:{
			required: true
		},
	  }

	}); */
	//$("#perfromanceRepForm").on('submit', (function(e) {
		$("#btn-submit").click(function() {
			
			//e.preventDefault();
			
			if($( "#perfromanceRepForm" ).valid()){
			//if(registerQuarterlyAuditorForm()) {
				
				console.log("Inside button click");
				
				var reportDate = $("#report-date").val();
				//var ad1 = $("#address-line-1").val();
				//var ad2 = $("#address-line-2").val();
				//var ad3 = $("#address-line-3").val();
				//var trusteeName = $("#trusteeName").val();
				//var date2 = $("#date-2").val();
				//var officerName = $("#officer-name").val();
				//var submitDate = $("#submitDate").val();
				//var place = $("#place").val();
				var reportUploadLogId = $("#reportUploadLogId").val();
				var reportMasterId = $("#reportMasterId").val();
				
				var formData = new FormData();
				formData.append('<portlet:namespace/>repdate', reportDate);
				//formData.append('<portlet:namespace/>address1', ad1);
				//formData.append('<portlet:namespace/>address2', ad2);
				//formData.append('<portlet:namespace/>address3', ad3);
				//formData.append('<portlet:namespace/>trusteeName', trusteeName);
				//formData.append('<portlet:namespace/>date2', date2);
				//formData.append('<portlet:namespace/>officername', officerName);
				//formData.append('<portlet:namespace/>submitdate', submitDate);
				//formData.append('<portlet:namespace/>place', place);
				formData.append('<portlet:namespace/>reportUploadLogId',reportUploadLogId);
				formData.append('<portlet:namespace/>reportMasterId',reportMasterId);
				
				for (var value of formData.values()) {
					   console.log(value);
				}
				$(".content").hide();
				$(".animaion").show();
		        $.ajax({
			        url: '<%=performanceRepResourceURL%>',
			        type: "POST",
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
			        cache: false,
			        data:formData,
			        success: function(data) {
			        	console.log("Inside success");
			        	$(".content").show();
			        	$(".animaion").hide();
			        	$('#success_tic').modal('show');
			        	//$('#success-message').show();
		            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
		            	//toastr.success('Form has been submitted successfully!');
		            	//$("#perfromanceRepForm")[0].reset();
			        },
			        error: function() {
		            	$(".animaion").hide();
		            	$('#output').html("An error occourd while submitting the form.");
	            		$('#errorExcel').modal('show');
		            	//$('#error-message').show();
		            	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
		            	//toastr.error('An error occured while submitting the form. Please try again');
		            	console.log("Error Occured in ajax call");
		            },
					complete: function(){
						$(".animaion").hide();
			        	//setTimeout(function() { location.reload(true); }, 5000);
			        }
		    });
				
				//console.log("Exiting ajax");
				
			//}
			}

		});
		$('#success_tic').on('hidden.bs.modal', function (e) {
			location.reload(); 
		})
	    $('#errorExcel').on('hidden.bs.modal', function (e) {
			location.reload(); 
		})
	
});

function onlyNumberKey(evt) {
    
    // Only ASCII character in that range allowed
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 48 || ASCIICode > 57))
        return false;
    return true;
}

$(document).ready(function() {
    $(document).on('keypress', ".alpha", function(e){
        var keyCode = e.keyCode || e.which;
   	 
        //Regex for Valid Characters i.e. Alphabets.
        var regex = /^[A-Za-z ]+$/;
 
        //Validate TextBox value against the Regex.
        var isValid = regex.test(String.fromCharCode(keyCode));
 
        return isValid;
    	
    });
});
 --%>


</script>
