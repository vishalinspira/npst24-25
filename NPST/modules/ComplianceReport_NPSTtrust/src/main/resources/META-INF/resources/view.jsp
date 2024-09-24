<%@page import="ComplianceReport_NPSTtrust.constants.ComplianceReport_NPSTtrustPortletKeys"%> 
<%@ include file="/init.jsp" %>

<!-- <portlet:resourceURL id="/annexureiva/save" var="annexureivaURL"></portlet:resourceURL> -->
<portlet:resourceURL id="<%=ComplianceReport_NPSTtrustPortletKeys.ComplianceReportNPSTtrust%>" var="saveComplianceReportURL" /> 
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
			      <h6>DATA SUBMITED SUCCESSFULY.</h6>
			    </div>
  			</div>
		</div>
	</div>
</div>

<div class="card_blocks">
 <div class="row mb-3">
         <div class="col-12">
            <div class="text-right">
                 <p  class="back_btn"><a class="backbtn" href="/web/guest/upload-cra-maker"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
         </div>
  </div>
	<div class="row">
	    <div class="col">
	        <div class="page_title">
	            <img src="/o/Liferay-NPS-Theme/images/file-upload.png" alt="Upload Reports" /> Compliance report_NPST Trust
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
									<input type="file" class="form-control" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="reportnpst" />
									<button type="button" class="btn file-btn">Choose File</button>
								</div>
								<label id="error-reportnpst" class="error-message"></label>
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
	    	if ($('input[name="reportnpst"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-reportnpst").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="reportnpst"]').get(0).files[0].name != "Compliance report_NPST Trust June 2021.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-reportnpst").html("Please upload Compliance report_NPST Trust June 2021.xlsx");
	    	    return false;
	    	}
	    	
	        var formData = new FormData($("form.form")[0]);
	        $(".content").hide();
	        $(".animaion").show();
	        $("#error-votingreport").html("");
	        $.ajax({

	            type: "POST",
	            enctype: 'multipart/form-data', //plz look at this if error occurs
	            processData: false,
	            contentType: false,
	            cache: false,
	            url: '${saveComplianceReportURL}',
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
	           		 	$('input[name="reportnpst"').val("");
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