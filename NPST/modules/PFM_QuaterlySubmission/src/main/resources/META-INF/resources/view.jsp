<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/savepmar/data" var="PFMMARDFileURl"></portlet:resourceURL>

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
                <p  class="back_btn"><a class="backbtn" href="/web/guest/quarterly-report-am"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
       </div>
     </div>
        <div class="row">
            <div class="col-lg-7 col-md-10 col-sm-12 col-12">
                <div class="nps-report">
                    <h4>Quarterly submission of sch V forms and Regulation 12 (ii) forms</h4>
                    <div class="content">
                    <aui:form id="fileinfo" method="POST" >
                    <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
					<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
	                    <div class="nps-input-box mt-0">
                            <label for="name">Report Due Date</label>
                            <input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
                        </div>
                        <div class="nps-input-box file-upload file-upload_i">
                            <label for="name">Report </label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile">File Name</div>
                               <div class="file-select-button common-btn" id="fileName">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile" name="unaditedfinancial" accept=".pdf"/>                               
                            </div>
                            <label id="error-unaditedfinancial" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_ii">
                            <label for="name">Report </label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_i">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_i">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_i" name="unaditedfinancial_i" accept=".pdf"/>                               
                            </div>
                            <label id="error-unaditedfinancial_i" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_iii">
                            <label for="name">Report </label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_ii">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_ii">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_ii" name="unaditedfinancial_ii" accept=".xlsx"/>                               
                            </div>
                            <label id="error-unaditedfinancial_ii" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box">
                            <label for="name">Remarks</label>
                            <textarea name="remarks" id="remarks" class="" rows="4" cols=""></textarea>
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
});

$(function(){
    $('#upload').on('click', function(){
    	
    	/* if ($('input[name="unaditedfinancial"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-unaditedfinancial").html("Please select a file to upload");
    	    return false;
    	}else if($('input[name="unaditedfinancial"]').get(0).files[0].name != "Half yearly unaudited financial report.pdf"){
    		console.log("No files selected.");
    	    $("#error-unaditedfinancial").html("Please upload Half yearly unaudited financial report.pdf");
    	    return false;
    	} */
    	
        var fd = new FormData($("form.form")[0]);
        $(".content").hide();
        $(".animaion").show();
        $("#error-unaditedfinancial").html("");
        $("#error-unaditedfinancial_i").html("");
        $("#error-unaditedfinancial_ii").html("");
        $.ajax({
        	type: "POST",
            processData: false,
            contentType: false,
            cache: false,
            url: '${PFMMARDFileURl}',
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
           		 	//$('input[name="unaditedfinancial"').val("");
	           		 /* setTimeout(function() {
	 	        		window.location = "/dashboard";
	 	        	}, 3000); */
            	}else{
            		console.log(data);
            		var downloadUrl = data.downloadUrl;
            		console.log(downloadUrl);
            		$('#output').html("An error occourd.");
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
	});
	$('#errorExcel').on('hidden.bs.modal', function (e) {
         location.reload(); 
    });
	
	
});
</script>
