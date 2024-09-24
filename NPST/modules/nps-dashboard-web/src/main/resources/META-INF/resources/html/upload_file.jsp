<%@ include file="/init.jsp"%>

<!-- Modal -->
		<div class="modal fade" id="success_tic" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
		  <div class="modal-dialog modal-sm">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body text-center">
		      	<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>      
		        <span class="head">Data is Approved.</span>
		      </div>       
		    </div>
		  </div>
		</div>

<div class="card_blocks">
	<div class="row">
		<div class="col">
			<div class="page_title">
				<img src="<%=request.getContextPath()%>/images/dashboard_icons/upload.png" alt="Upload Reports" class="filter-white">
				Upload Report
			</div>
		</div>
	</div>
	
	<div class="form_block">
		<div class=" content">
		  <div class="row formrow">
		    <div class="col">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<div class="fileUploadInput">
								<input type="file" class="form-control" id="reportsFileId"/>
								<button type="button" class="btn file-btn">Choose File</button>
							</div>
							<label id="error-reporyFile" class="error-message"></label>
						</div>
					</div>
				</div>
				
				<button id="upload-file" class="nps-btn" onclick="uploadReportsToDL();" type="button">Save</button>
		    </div>    
		  </div>
		  <div class="row">
		  	<div  class="col">
		    	<div id="output"></div>
		    </div>
		  </div>
		</div>
	</div>
	
	<div class="animaion" style="display: none;">
		<div class="row">
			<div class="loading-animation"></div>
		</div>
	</div>
</div>

	<style>
		.modal-dialog {
	margin: 5% 40%;
}

.modal-content {
	width: 20vw;
}
		
		.modal {
		display: none;
			}

	</style>

<script>
$(document).ready(function() {
	$(".fileUploadInput .file-btn").click(function() {
		$(this).parent().children("input[type='file']").trigger("click");
	});
});

function uploadReportsToDL() {
	if ($('#reportsFileId').get(0).files.length === 0) {
	    $("#error-reporyFile").html("Please select a file to upload");
	    return false;
	}
	
	let urlObject = new URL("${uploadReportsResourceCommandURL}");
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	
	var formData = new FormData();
	formData.append('<portlet:namespace/>npsUpdatedReport', $("#reportsFileId")[0].files[0]);
	
	var filename = $("#reportsFileId").val().split('\\').pop(); 
	
	if(!filename.endsWith(".xlsx")) {
		$("#error-reporyFile").html("File is Not of xlxs format. Please upload a proper file.");
		$(".animaion").hide();
		return;
	}
	$("#error-reporyFile").html("");
	$(".animaion").show();
	$.ajax({
	  	type: "POST",	
		enctype: 'multipart/form-data',
		processData: false,
		contentType: false,
		cache: false,
		url: urlObject,
		data: formData,
		success: function(data) {
			$(".animaion").hide();
			$('#success_tic').modal('show');
			$('#reportsFileId').val("");
		},
		error: function() {
			$(".animaion").hide();
			toastr.error('An error occured while submitting the form. Please try again');
        },
        complete: function(){
        	$(".animaion").hide();
        }
	}); 
}

</script>