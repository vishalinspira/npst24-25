<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/saveInvestmentManagement/data" var="InvestmentManagementFileURl"></portlet:resourceURL>

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

    <div class="nps-page-main nps-upload-report">
    	    <div class="row">
	     <div class="col-12">
	        <div class="text-right">
	            <p  class="back_btn"><a class="backbtn" href="/web/guest/monthly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
	        </div>
	     </div>
	  </div>
        <div class="row">
            <div class="col-lg-7 col-md-10 col-sm-12 col-12">
                <div class="nps-report">
                    <h4>Investment Management Fee and NPS Trust fee</h4>
                    <div class="content">
                    <aui:form id="fileinfo" method="POST" >
                    <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
					<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
	                    <div class="nps-input-box mt-0">
                            <label for="name">Report Due Date</label>
                            <input class="reportDate" type="date"  value="${reportDate }" readonly="readonly">
                        </div>
                        <div class="nps-input-box file-upload file-upload_i">
                            <label for="name">Investment Management Fee </label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile">File Name</div>
                               <div class="file-select-button common-btn" id="fileName">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile" name="InvestmentManagementFile" accept=".xlsx"/>                               
                            </div>
                            <label id="error-InvestmentManagementFile" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_ii">
                            <label for="name">Approval Letter & Tax Invoice </label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_i">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_i">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_i" name="tax_invoicefor_imf"  accept=".pdf"/>                               
                            </div>
                            <label id="error-tax_invoicefor_imf" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_iii">
                            <label for="name">Auditor's certificate verifying the daily Aum</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_ii">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_ii">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_ii" name="audit_cert_vdaum"  accept=".pdf"/>                               
                            </div>
                            <label id="error-audit_cert_vdaum" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_iv">
                            <label for="name">NPS Trust Fee</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_iii">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_iii">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_iii" name="nps_trust_fee"  accept=".xlsx"/>                               
                            </div>
                            <label id="error-nps_trust_fee" class="error-message text-danger"></label>
                        </div>
                        
                       <!--  <div class="nps-input-box file-upload file-upload_v">
                            <label for="name">Fee Letter</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_v">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_v">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_v" name="nps_trust_fee_pdf"  accept=".pdf"/>                               
                            </div>
                            <label id="error-nps_trust_fee_pdf" class="error-message text-danger"></label>
                        </div> -->
                        
                        <div class="nps-input-box">
                            <label for="name">Remarks</label>
                            <textarea name="remarks" id="remarks" class="" rows="4" cols="" maxlength="1000"></textarea>
                        </div>
                        <div class="nps-input-box">
                        	<button id="upload" class="common-btn d-inline-block" name="Submit" type="button" ${reportUploadLogExist?"":"disabled"}>Save</button>
                           <!-- <a href="javascript:;" class="common-btn d-inline-block">save</a> -->
                        </div>
                    </aui:form>
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



<script type="text/javascript">
var url='<%= InvestmentManagementFileURl%>';

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
	$(".fileUploadInput .file-btn").click(function() {
		$(this).parent().children("input[type='file']").trigger("click");
	});
	
	$('#chooseFile').bind('change', function () {
		  var filename = $("#chooseFile").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_i").removeClass('active');
		     $("#noFile").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_i').bind('change', function () {
		  var filename = $("#chooseFile_i").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_ii").removeClass('active');
		     $("#noFile_i").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_i").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_ii').bind('change', function () {
		  var filename = $("#chooseFile_ii").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_iii").removeClass('active');
		     $("#noFile_ii").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_ii").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_iii').bind('change', function () {
		  var filename = $("#chooseFile_iii").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_iv").removeClass('active');
		     $("#noFile_iii").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_iii").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_v').bind('change', function () {
		  var filename = $("#chooseFile_v").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_v").removeClass('active');
		     $("#noFile_v").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_v").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
});

$(function(){
    $('#upload').on('click', function(){
    	$(".error-message").text("");
    	let errorBool = true;
    	
    	 if ($('input[name="InvestmentManagementFile"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-InvestmentManagementFile").html("Please select a file to upload");
    	    errorBool= false;
    	}else if($('input[name="InvestmentManagementFile"]').get(0).files[0].name != "Investment Management Fee.xlsx"){
    		console.log("No files selected.");
    	    $("#error-InvestmentManagementFile").html("Please upload Investment Management Fee.xlsx");
    	    errorBool= false;
    	}
    	if ($('input[name="tax_invoicefor_imf"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-tax_invoicefor_imf").html("Please select a file to upload");
    	    errorBool= false;
    	}else if($('input[name="tax_invoicefor_imf"]').get(0).files[0].name != "Approval Letter & Tax Invoice.pdf"){
    		console.log("No files selected.");
    	    $("#error-tax_invoicefor_imf").html("Please upload Approval Letter & Tax Invoice.pdf");
    	    errorBool= false;
    	}
    	if ($('input[name="audit_cert_vdaum"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-audit_cert_vdaum").html("Please select a file to upload");
    	    errorBool= false;
    	}else if($('input[name="audit_cert_vdaum"]').get(0).files[0].name != "Auditor's certificate verifying the daily Aum.pdf"){
    		console.log("No files selected.");
    	    $("#error-audit_cert_vdaum").html("Please upload Auditor's certificate verifying the daily Aum.pdf");
    	    errorBool= false;
    	}
    	if ($('input[name="nps_trust_fee"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-nps_trust_fee").html("Please select a file to upload");
    	    errorBool= false;
    	}else if($('input[name="nps_trust_fee"]').get(0).files[0].name != "NPS Trust Fee.xlsx"){
    		console.log("No files selected.");
    	    $("#error-nps_trust_fee").html("Please upload NPS Trust Fee.xlsx");
    	    errorBool= false;
    	}
    	
    	/* if ($('input[name="nps_trust_fee_pdf"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-nps_trust_fee_pdf").html("Please select a file to upload");
    	    errorBool= false;
    	}else if($('input[name="nps_trust_fee_pdf"]').get(0).files[0].name != "NPS Trust Fee.pdf"){
    		console.log("No files selected.");
    	    $("#error-nps_trust_fee_pdf").html("Please upload NPS Trust Fee.pdf");
    	    errorBool= false;
    	} */
    	
    	if(!errorBool){
    		return errorBool;
    	}
    	
        var fd = new FormData($("form.form")[0]);
        $(".content").hide();
        $(".animaion").show();
        $("#error-InvestmentManagementFile").html("");
        $("#error-tax_invoicefor_imf").html("");
        $("#error-audit_cert_vdaum").html("");
        $("#error-nps_trust_fee").html("");
        //$("#error-nps_trust_fee_pdf").html("");
        $.ajax({
        	type: "POST",
            processData: false,
            contentType: false,
            cache: false,
            url: url,
            data: fd,
            success:function(data){
            	$(".content").show();
                $(".animaion").hide();
                $(".formrow").hide(); 
                  try {
                	 console.log(data);
                	data = JSON.parse(data);
                } catch (e) {
                	console.log("error while parsing the json data");
                }
            	if(data.status){
            		//var pdfURL = data.pdfURL;
           		 	//$('#output').html("<label>Please download the pdf here. </label> <a class='ml-2' href="+pdfURL+"><i class='fa fa-download'></i></a>");
           		 	$('#success_tic').modal('show');
           		 	$(".formrow").show();
           		 	//$('input[name="InvestmentManagementFile"').val("");
	           		/*  setTimeout(function() {
	 	        		window.location = "/dashboard";
	 	        	}, 3000); */
            	}else if(data.sheeterror){
            		$('#output').html("An error occourd.Following sheets not found : "+data.errorSheetNameList);
            		$('#errorExcel').modal('show');
            		/* $('#errorExcel').on('hidden.bs.modal', function (e) {
            			location.reload(); 
                     }) */
            	}else if(data.apierror){
            		$('#output').html("An error occourd while validating the file.");
            		$('#errorExcel').modal('show');
            		/* $('#errorExcel').on('hidden.bs.modal', function (e) {
            			location.reload(); 
            		}) */
            	}else{
            		console.log(data);
            		var downloadUrl = data.downloadUrl;
            		console.log(downloadUrl);
            		//$('#output').html("An error occourd. To download the error excel file click <a href="+downloadUrl+">here</a>");
            		$('#output').html(data.msg);
            		$('#errorExcel').modal('show');
            	}    
            },
            error: function() {
            	$(".animaion").hide();
            	toastr.error('An error occured while submitting the form');
           		console.log("Error Occured in ajax call");
            },
            complete: function(){
				console.log("Complete");
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
</script>
