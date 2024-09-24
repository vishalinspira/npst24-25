<%@page import="grievance.top.five.entities.month.constants.GrievanceTopFiveEntitiesMonthPortletKeys"%>
<%@ include file="/init.jsp"%>

<portlet:resourceURL id="<%=GrievanceTopFiveEntitiesMonthPortletKeys.UPLOAD_EXCEL_RESOURCE_COMMAND%>" var="uploadReportsResourceCommandURL"/>

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
            <div class="col-lg-7 col-md-10 col-sm-12 col-12">
                <div class="nps-report">
                    <h4>Top 5 Entities Month</h4>
                    <div class="content">
	                    <aui:form id="fileinfo" method="POST" >
	                    <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
						<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
		                    <div class="nps-input-box mt-0">
	                            <label for="name">Report Due Date</label>
	                            <input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
	                        </div>
	                        <div class="nps-input-box file-upload">
	                            <label for="name">Report </label>
	                            <div class="file-select">
	                               <div class="file-select-name" id="noFile">File Name</div>
	                               <div class="file-select-button common-btn" id="fileName">Choose File</div>
	                               <input type="file" class="chooseFile" id="chooseFile" name="reportsFileId" />                               
	                            </div>
	                            <label id="error-reporyFile" class="error-message text-danger"></label>
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
	<!-- <div class="form_block">
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
 -->
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
    // urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	
	var formData = new FormData();
	formData.append('<portlet:namespace/>grienvanceExcelFile', $("#reportsFileId")[0].files[0]);
	
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
			console.log("Success ::::: ", data);
			$(".animaion").hide();
			$('#success_tic').modal('show');
			$('#reportsFileId').val("");
		},
		error: function() {
			$(".animaion").hide();
			console.log("Error");
			toastr.error('An error occured while submitting the form. Please try again');
        },
        complete: function(){
        	console.log("Completed");
        	$(".animaion").hide();
        }
	}); 
}

</script>