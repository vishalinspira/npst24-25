<%@ include file="/init.jsp" %>

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
                <p  class="back_btn"><a class="backbtn" href="/web/guest/quarterly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
       </div>
     </div>
     
   <div class="row">
      <div class="col-lg-7 col-md-10 col-sm-12 col-12">
         <div class="nps-report">
            <div class="form_block">
               <div class=" content">
                  <div class="row formrow">
                  <h4>Final Count</h4>
                     <aui:form id="fileinfo" method="POST" >
                        <input type="hidden" name="dlfileid"/>
                        <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
						<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                        <div class="nps-input-box mt-0">
                           <label>Report Due Date</label>
                           <input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
                        </div>
                        <div class="nps-input-box file-upload">
                           <label>Report </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" id="chooseFile" name="finalcount" />
                           </div>
                           <label id="error-finalcount" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box">
                           <label>Remarks</label>
                           <textarea id="remarks" name="remarks" rows="4" cols="" maxlength="1000"></textarea>
                        </div>
                        <div class="nps-input-box">
                           <button id="upload" class="common-btn d-inline-block" name="Submit" type="button" ${reportUploadLogExist?"":"disabled"}>Save</button>
                        </div>
                     </aui:form>
                  </div>
               </div>
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
	  
		$(".file-upload .common-btn").click(function() {
			$(this).parent().children("input[type='file']").trigger("click");
		});
	});

	$(function(){
	    $('#upload').on('click', function(){
	    	if ($('input[name="finalcount"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-finalcount").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="finalcount"]').get(0).files[0].name != "Final Count.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-finalcount").html("Please upload Final Count.xlsx");
	    	    return false;
	    	}
	    	
	        var formData = new FormData($("form.form")[0]);
	        $(".content").hide();
	        $(".animaion").show();
	        $("#error-finalcount").html("");
	        $.ajax({

	            type: "POST",
	            enctype: 'multipart/form-data',
	            processData: false,
	            contentType: false,
	            cache: false,
	            url: '${finalcounturl}',
	            data: formData,
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
	           		 	//$('#output').html("Data submited successfuly.");
	           		 	//var pdfURL = data.pdfURL;
           		 		//$('#output').html("<label>Please download the pdf here. </label> <a class='ml-2' href="+pdfURL+"><i class='fa fa-download'></i></a>");
	           		 	$('#success_tic').modal('show');
	           		 	$(".formrow").show();
	           		 	$('input[name="finalcount"').val("");
		           		/* setTimeout(function() {
				        	window.location = "/dashboard";
				        }, 3000); */
	            	}else{
	            		console.log(data);
	            		var downloadUrl = data.downloadUrl;
	            		console.log(downloadUrl);
	            		$('#output').html("An error occured. To download the error excel file click <a href="+downloadUrl+">here</a>");
	            		$('#errorExcel').modal('show');
	            		/* $('#errorExcel').on('hidden.bs.modal', function (e) {
	            			location.reload(); 
	            		}) */
	            	}
	            },
	            error: function() {
	           		console.log("Error Occured in ajax call");
	           		$(".animaion").hide();
	           		toastr.error('An error occured while submitting the form');
	            },
	            complete: function(){
					console.log("Complete");
					$(".animaion").hide();
		        },
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