<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/annenine_am/save" var="Uplaodannenine_amURL" /> 
<portlet:resourceURL id="/annenine_am/getreportlogdetails" var="getreportlogdetailsURL"/>
		
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
	
<div class="nps-page-main nps-upload-report">
        <div class="row">
	     <div class="col-12">
	        <div class="text-right">
	            <p  class="back_btn"><a class="backbtn" href="/web/guest/monthly-am-report"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
	        </div>
	     </div>
	  </div>
    <div class="row">
        <div class="col-lg-7 col-md-10 col-sm-12 col-12">
            <div class="nps-report">
                <h4>Performance Report & Deviation</h4>
                <div class="content">
                <aui:form id="fileinfo" method="POST" >
                <input type="hidden" name="dlfileid"/>
                <input type="hidden" name="reportUploadLogId" class="reportUploadLogId"/>
                <input type="hidden" value="34" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                 <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" readonly="readonly">
                    </div>
                    <div class="nps-input-box file-upload">
                        <label for="name">Report </label>
                        <div class="file-select">
                           <div class="file-select-name" id="noFile">File Name</div>
                           <div class="file-select-button common-btn" id="fileName">Choose File</div>
                           <input type="file" class="chooseFile" id="chooseFile" name="annenine_amFile" />                               
                        </div>
                        <label id="error-annenine_amFile" class="error-message text-danger"></label>
                    </div>
                    
			                        <div class="nps-input-box file-upload">
			                           <label>Performance Report and Deviation</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile1">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName1">Choose File</div>
			                              <input type="file" class="chooseFile" id="annenine_amFileDoc" name="annenine_amFileDoc" />
			                           </div>
			                            <label id="error-annenine_amFileDoc" class="error-message text-danger"></label>
			                           <br>
			                        </div>

                    
                    <div class="nps-input-box">
                        <label for="name">Remarks</label>
                        <textarea name="remarks" id="remarks" class="" rows="4" cols="" maxlength="1000"></textarea>
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
</div>	
<div class="animaion" style="display:none;">
 	<div class="row">
		<div class="loading-animation"></div>
	</div>
</div>

<!-- Modal -->
<%-- <div id="success_tic" class="modal fade success_modal" role="dialog">
	<div class="modal-dialog">
	    <!-- Modal content-->
	    <div class="modal-content">
      		<a class="close" href="#" data-dismiss="modal">&times;</a>
     		<div class="page-body">
     			<h1 style="text-align:center;">
					<div class="checkmark-circle">
  						<div class="background"></div>
  						<div class="checkmark draw"></div>
					</div>
				</h1>
   				<div class="head">
			      <h6>Data sent for review.</h6>
			    </div>
  			</div>
		</div>
	</div>
</div>
<div class="card_blocks">
	<div class="row">
	    <div class="col">
	        <div class="page_title">
	            <img src="/o/Liferay-NPS-Theme/images/file-upload.png" alt="Upload Reports" />Performance Report & Deviation
	        </div>
	    </div>
	</div>
	
	<div class="form_block mx-4">
		<div class=" content">
		  <div class="row formrow">
		    <div class="col">
				<aui:form id="fileinfo" method="POST" >
				<input type="hidden" name="dlfileid"/>
				<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
				<input type="hidden" value="34" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
				 <div class="row">
                	<div class="col-md-6">
                		<div class="form-group">
                			<label>Report Due Date</label>
                			<input class="form-control reportDate" type="date" value="" readonly="readonly">
                		</div>
                	</div> 
                	<div class="col-md-6">
                		<div class="form-group">
                			<label>Remarks</label>
                			<textarea name="remarks" id="remarks"  class="form-control" rows="" cols=""></textarea>
                		</div>
                	</div>
			       </div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<div class="fileUploadInput">
									<input type="file" class="form-control" name="annenine_amFile" />
									<button type="button" class="btn file-btn">Choose File</button>
								</div>
								<label id="error-annenine_amFile" class="error-message"></label>
							</div>
						</div>
					</div>
					<button id="upload" class="nps-btn" name="Submit" type="button">Save</button>
				</aui:form>
		    </div>    
		  </div>
		  <div class="row mt-2">
		  	<div  class="col">
		    	<div id="output"></div>
		    </div>
		  </div>
		</div>
	</div>
	<div class=" animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
	</div>
</div> --%>

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
	$(".fileUploadInput .file-btn").click(function() {
		$(this).parent().children("input[type='file']").trigger("click");
	});
});


$('#annenine_amFileDoc').bind('change', function () {
	  var filename = $("#annenine_amFileDoc").val();
	  if (/^\s*$/.test(filename)) {
	     $(".file-upload").removeClass('active');
	     $("#noFile1").text("No file chosen...");
	  }
	  else {
	     $(".file-upload").addClass('active');
	     $("#noFile1").text(filename.replace("C:\\fakepath\\", ""));
	  }
	});
	
	

function getreportUploadLogId(){
	var fd = new FormData($("form.form")[0]);
	$.ajax({
        url: '${getreportlogdetailsURL}', 
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


$(function(){
		 getreportUploadLogId();
	 
    $('#upload').on('click', function(){
    	if ($('input[name="annenine_amFile"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-annenine_amFile").html("Please select a file to upload");
    	    return false;
    	}else if($('input[name="annenine_amFile"]').get(0).files[0].name != "Performance Report & Deviation.pdf"){
    		console.log("No files selected.");
    	    $("#error-annenine_amFile").html("Please upload Performance Report & Deviation.pdf");
    	    return false;
    	}
    	
    	
    	if ($('input[name="annenine_amFileDoc"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-annenine_amFileDoc").html("Please select a file to upload");
    	    return false;
    	}else if($('input[name="annenine_amFileDoc"]').get(0).files[0].name != "Performance Report & Deviation.docx"){
    		console.log("No files selected.");
    	    $("#error-annenine_amFileDoc").html("Please upload Performance Report & Deviation.docx");
    	    return false;
    	}
    	
    	
        var fd = new FormData($("form.form")[0]);
        $(".content").hide();
        $(".animaion").show();
        $("#error-annenine_amFile").html("");
        $("#error-annenine_amFileDoc").html("");
        $.ajax({
            url: '${Uplaodannenine_amURL}', 
            type: 'POST',
            data: fd,
            success:function(data){
            	$(".content").show();
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
           		 	$('input[name="annenine_amFile"').val("");
           		 $('input[name="annenine_amFileDoc"').val("");
           		 	$('#remarks').val("");
            	}else{
            		console.log(data);
            		var downloadUrl = data.downloadUrl;
            		console.log(downloadUrl);
            		$('#output').html("An error occourd. To download the error excel file click <a href="+downloadUrl+">here</a>");
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
    $('#errorExcel').on('hidden.bs.modal', function (e) {
    	location.reload(); 
    }); 
    $('#success_tic').on('hidden.bs.modal', function (e) {
    	location.reload(); 
    });
});
</script>
