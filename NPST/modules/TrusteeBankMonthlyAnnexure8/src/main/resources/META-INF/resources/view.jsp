<%@ include file="/init.jsp" %>

<portlet:resourceURL var="addClosingBalResourceURL" id="/closingbal/save"></portlet:resourceURL>
<portlet:resourceURL id="/rejectionreturn/getreportlogdetails" var="getreportlogdetailsURL"></portlet:resourceURL>

<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/> -->
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

<%-- <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Annexure 8 - Closing Balance Confirmation For NPS Trust Collection Accounts
                </div>
            </div>
        </div> --%>
	
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
       		<h4>Closing Balance Confirmation (monthly)</h4>
           	<form class="row form" id="closingBalForm">
				<input type="hidden" name="dlfileid"/>
				<input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId"/>
				<input type="hidden" value="25" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
				<div class="col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" readonly="readonly">
                        <!-- <input type="text" id="name"  disabled> -->
                    </div>
                </div>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="nps-input-box file-upload col-lg-6 col-md-6 col-sm-12 col-12">
			                           <label>Report</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile1">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName1">Choose File</div>
			                              <input type="file" class="chooseFile" id="anx_8" name="anx_8" accept=".pdf" />
			                           </div>
			                            <label id="error-anx_8" class="error-message text-danger"></label>
			                           <br>
			                        </div>
                </div>
<%--                 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <label>Date</label>
						<input type="date" id="closing-balance-date" name='<portlet:namespace/>performantdate'>
						<span id="date-error" class="text-danger"></span>
						<label class="error" for="<portlet:namespace/>performantdate"></label>
                    </div>
                </div>
				<div class="class="col-lg-12">
	                	<p class="px-0 py-2 border-bottom-0">The Chief Executive Officer</p>
	                	<p class="px-0 py-2 border-bottom-0">National Pension System (NPS) Trust</p>
	            </div>
                <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <input type="text" id="address-line-1" name='<portlet:namespace/>addressline1' placeholder="Address Line 1" required>
                        <label class="error" for="<portlet:namespace/>addressline1"></label>
                    </div>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <input type="text" id="address-line-2" name='<portlet:namespace/>addressline2'placeholder="Address Line 2" required>
                        <label class="error" for="<portlet:namespace/>addressline2"></label>
                    </div>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <input type="text" id="address-line-3" name='<portlet:namespace/>addressline3' placeholder="Address Line 3" required>
                        <label class="error" for="<portlet:namespace/>addressline3"></label>
                    </div>
                </div>

				<div class="col-lg-12">
						<p class=" my-3 font-weight-bold subject-line">Dear Sir, </p>
				</div>
			
				<div class="col-lg-12">
			         <p class="text-center my-3 font-weight-bold">Sub: Bank Balance Confirmation As On</p>
			    </div>
		
				<div class="col-lg-12">
					<p>
						We hereby confirm availability of bank balance as per detail
						mentioned below in NPS Trust A/C - CENTRAL GOVERNMENT account
						maintained with our Corporate Banking Branch as on <input
						class="form-control d-inline w-25" type="date" id="confirm-date" name='<portlet:namespace/>confirmdate'>
					</p>
					<span class="text-danger" id="confirm-date-error" for="<portlet:namespace/>confirmdate"></span>
				</div>
			
				<div class="statement-wrapper">
                  <div id="table" class="table-editable">
                  	<div class="text-end">
                   		<button id="addRow" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
					</div>
					<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
                   <div class="table-cont" > 
                       <table class="table" id="myTable">
							<thead>
								<tr>
									<th>Account Number</th>
									<th>Name Of Account Holder </th>
									<th>Total Balance (Amount In Rs.)</th>
									<th>Cleared Balance (Amount In Rs.)</th>
									<th>Uncleared Balance (Amount In Rs.)</th>
									<th data-orderable="false">Remove</th>
								</tr>
							</thead>
				
							<tbody>
								<tr>
									<td>
										<input type="text" class="form-control account-number digits" id="account-number" name="<portlet:namespace />accountnumber0" maxlength="16" ><br>
										<span id="account-number-error" style="color:red; font-size:12px" for="<portlet:namespace />accountnumber0"></span>
									</td>
									<td>
										<input type="text" class="form-control account-holder" id="account-holder" name="<portlet:namespace />accountholder0" placeholder="NPS TRUST A/C - CENTRAL GOVERNMENT"><br>
										<span id="account-holder-error" style="color:red; font-size:12px"></span>
									</td>
									<td>
										<input type="text" class="form-control total-balance digits" id="total-balance" name="<portlet:namespace />totalbalance0"><br>
										<span id="total-balance-error" style="color:red; font-size:12px"></span>
									</td>
									<td>
										<input type="text" class="form-control cleared-balance digits" id="cleared-balance" name="<portlet:namespace />clearedbalance0"><br>
										<span id="cleared-balance-error" style="color:red; font-size:12px"></span>
									</td>
									<td>
										<input type="text" class="form-control uncleared-balance digits" id="uncleared-balance" name="<portlet:namespace />unclearedbalance0"><br>
										<span id="uncleared-balance-error" style="color:red; font-size:12px"></span>
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
		      		<div class="col-lg-12">
					      <h4>Yours faithfully</h4>   
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                <div class="nps-input-box mt-0">
		                    <input type="text" class="form-control alpha" id="name" name='<portlet:namespace/>name' placeholder="Name">
		                    <label class="error" for="<portlet:namespace/>name"></label>
		                </div>
			        </div>
			        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			        </div>
			       <div class="col-lg-6 col-md-6 col-sm-12 col-12">
				       	<div class="nps-input-box mt-0">
							<input type="text" class="form-control alpha" id="designation" name='<portlet:namespace/>designation' placeholder="Designation">
							<label class="error" for="<portlet:namespace/>designation"></label>
						</div>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12 col-12">
			        </div>	 --%>
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
		
		/* label.error {
			display: none !important;
		}  */
	</style>


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

<!-- <script type="text/javascript">
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

.page_title {
	font-size: 18px;
}

#myTable label.error {
	display: block !important;
}
</style>
 -->
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
	/* $(".fileUploadInput .file-btn").click(function() {
		$(this).parent().children("input[type='file']").trigger("click");
	}); */
});

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


$('#anx_8').bind('change', function () {
	  var filename = $("#anx_8").val();
	  if (/^\s*$/.test(filename)) {
	     $(".file-upload").removeClass('active');
	     $("#noFile1").text("No file chosen...");
	  }
	  else {
	     $(".file-upload").addClass('active');
	     $("#noFile1").text(filename.replace("C:\\fakepath\\", ""));
	  }
	});
	
	

$(function(){
		 getreportUploadLogId();
	 
    $('#btn-submit').on('click', function(){
    	if ($('input[name="anx_8"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-anx_8").html("Please select a file to upload");
    	    return false;
    	}
    	
    	
        var fd = new FormData($("form.form")[0]);
    	$(".nps-report").hide();
			$(".animaion").show();
        $("#error-anx_8").html("");

        $.ajax({
            url: '${addClosingBalResourceURL}', 
            type: 'POST',
            data: fd,
            success:function(data){
            	
            	$(".nps-report").show();
	            	$(".animaion").hide();
	            	$('#success_tic').modal('show');
	            	//$("#monthlyCompCertificate")[0].reset();
	             	$('#success_tic').on('hidden.bs.modal', function (e) {
	             		location.reload(); 
	           		})
	           		
	           		
            	/* $(".content").show();
                $(".animaion").hide();
                $(".formrow").hide();
                try {
                	data = JSON.parse(data);
                } catch (e) {
                	console.log("error while parsing the json data");
                }
            	if(data.status){
            		//var pdfURL = data.pdfURL;
           		 	//$('#output').html("<label>Please download the pdf here. </label> <a class='ml-2' href="+pdfURL+"><i class='fa fa-download'></i></a>");
           		 	
           		 	$('#success_tic').modal('show');
           		 	$(".formrow").show();
           		 $('input[name="anx_8"').val("");
           		 	$('#remarks').val("");
            	}else{
            		console.log(data);
            		var downloadUrl = data.downloadUrl;
            		console.log(downloadUrl);
            		$('#output').html("An error occourd. To download the error excel file click <a href="+downloadUrl+">here</a>");
            	} */
            },
            error: function() {
            	$(".animaion").hide();
        		$('#output').html('An error occured while submitting the form. Please try again');
        		$('#errorExcel').modal('show');
	            	console.log("Error Occured in ajax call");
	            	
            	/* $(".animaion").hide();
            	toastr.error('An error occured while submitting the form');
           		console.log("Error Occured in ajax call"); */
            },
            complete: function(){
            	$(".animaion").hide();
				console.log("Complete");
            	if($("#sub").hasClass("hide")){
	            	$("#sub").removeClass("hide");
            	}
				console.log("Complete");
	        },
            cache: false,
            contentType: false,
            processData: false
        });
    });
/*     $('#errorExcel').on('hidden.bs.modal', function (e) {
    	location.reload(); 
    }); 
    $('#success_tic').on('hidden.bs.modal', function (e) {
    	location.reload(); 
    }); */
});



</script>


