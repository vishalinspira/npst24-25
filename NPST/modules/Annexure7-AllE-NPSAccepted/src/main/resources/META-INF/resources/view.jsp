<%@page import="Annexure7.AllE.NPSAccepted.constants.Annexure7AllENPSAcceptedPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=Annexure7AllENPSAcceptedPortletKeys.eNpsAccepted%>" var="eNPSAccepted" /> 
<!-- 1. -->
<!-- Modal -->
<div id="success_tic" class="modal fade success_modal" role="dialog">
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
			      <h6>DATA SUBMITTED SUCCESSFULLY.</h6>
			    </div>
  			</div>
		</div>
	</div>
</div>

<div class="card_blocks">
	<div class="row">
	    <div class="col">
	        <div class="page_title">
	            <img src="/o/Liferay-NPS-Theme/images/file-upload.png" alt="Upload Reports" /> Annexure7 - All E-NPS Accepted
	        </div>
	    </div>
	</div>
	
	<div class="form_block">
		<div class=" content">
		  <div class="row formrow">
		    <div class="col">
				<aui:form id="fileinfo" method="POST" >
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<div class="fileUploadInput">
									<input type="file" class="form-control" name="eNPSAcceptedFile" />
									<button type="button" class="btn file-btn">Choose File</button>
								</div>
								<label id="error-eNPSAcceptedFile" class="error-message"></label>
							</div>
						</div>
					</div>
					
					<button id="upload" class="nps-btn" name="Submit" type="button">Save</button>
					<!-- <button id="loading" name="load">Loading</button>-->
				</aui:form>
		    </div>    
		  </div>
		  
		  <div class="row">
		  	<div  class="col">
		    	<div id="output"></div>
		    </div>
		  </div>
		  
		</div>
	</div>
	<div class="animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
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
	});

	$(function(){
	    $('#upload').on('click', function(){
	    	if ($('input[name="eNPSAcceptedFile"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-eNPSAcceptedFile").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="eNPSAcceptedFile"]').get(0).files[0].name != "Annexure 7 MIS Collection Rejection (monthly).xlsx"){
	    		console.log("No files selected.");
	    		$("#error-eNPSAcceptedFile").html("Please upload Annexure 7 MIS Collection Rejection (monthly).xlsx");
	    	    return false;
	    	}
	    	
	        var formData = new FormData($("form.form")[0]);
	        $(".content").hide();
	        $(".animaion").show();
	        $("#error-eNPSAcceptedFile").html("");
	        $.ajax({

	            type: "POST",
	            enctype: 'multipart/form-data', //plz look at this if error occurs
	            processData: false,
	            contentType: false,
	            cache: false,
	            url: '${eNPSAccepted}',
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
	           		 	$('input[name="eNPSAcceptedFile"').val("");
	           		 	setTimeout(function() {
				        	window.location = "/dashboard";
				        }, 3000);
	            	}else{
	            		console.log(data);
	            		var downloadUrl = data.downloadUrl;
	            		console.log(downloadUrl);
	            		$('#output').html("An error occured. To download the error excel file click <a href="+downloadUrl+">here</a>");
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
	});  
  
</script>