<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/saveAspReport/data" var="AspReportFileURl"></portlet:resourceURL>

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
                <p  class="back_btn"><a class="backbtn" href="/web/guest/monthly-report-am"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
       </div>
     </div>
        <div class="row">
            <div class="col-lg-7 col-md-10 col-sm-12 col-12">
                <div class="nps-report">
                    <h4>Asp Report</h4>
                    <div class="content">
                    <aui:form id="fileinfo" method="POST" >
                    <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
					<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
	                    <div class="nps-input-box mt-0">
                            <label for="name">Report Due Date</label>
                            <input class="reportDate" type="date"  value="${reportDate }" >
                        </div>
                         <div class="nps-input-box file-upload file-upload_excel">
                            <label for="name">Asp Report Excel</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile">File Name</div>
                               <div class="file-select-button common-btn" id="fileName">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile" name="AspReportExcel" accept=".xlsx"/>                               
                            </div>
                            <label id="error-AspReportExcel" class="error-message text-danger"></label>
                         </div>  
                        <div class="nps-input-box file-upload file-upload_i">
                            <label for="name">Bajaj Allianz Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_i">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_i">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_i" name="AspReportPdf_i"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_i" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_ii">
                            <label for="name">Canara HSBC ASP Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_ii">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_ii">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_ii" name="AspReportPdf_ii"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_ii" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_iii">
                            <label for="name">Edelweiss Tokio ASP REPORT</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_iii">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_iii">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_iii" name="AspReportPdf_iii"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_iii" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_iv">
                            <label for="name">HDFC Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_iv">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_iv">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_iv" name="AspReportPdf_iv"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_iv" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_v">
                            <label for="name">ICICI Prudential Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_v">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_v">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_v" name="AspReportPdf_v"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_v" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_vi">
                            <label for="name">IndiaFirst Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_vi">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_vi">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_vi" name="AspReportPdf_vi"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_vi" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_vii">
                            <label for="name">Kotak Mahindra Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_vii">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_vii">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_vii" name="AspReportPdf_vii"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_vii" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_viii">
                            <label for="name">LIC Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_viii">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_viii">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_viii" name="AspReportPdf_viii"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_viii" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_ix">
                            <label for="name">Max Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_ix">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_ix">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_ix" name="AspReportPdf_ix"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_ix" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_x">
                            <label for="name">SBI Asp Report </label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_x">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_x">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_x" name="AspReportPdf_x"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_x" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_xi">
                            <label for="name">Star Union Dai-ichi Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_xi">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_xi">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_xi" name="AspReportPdf_xi"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_xi" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box file-upload file-upload_xii">
                            <label for="name">TATA AIA Asp Report</label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile_xii">File Name</div>
                               <div class="file-select-button common-btn" id="fileName_xii">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile_xii" name="AspReportPdf_xii"  accept=".pdf"/>                               
                            </div>
                            <label id="error-AspReportPdf_xii" class="error-message text-danger"></label>
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
		  console.log("chosen file is--->",$('input[name="AspReportExcel"]').get(0).files);
		  var filename = $("#chooseFile").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_excel").removeClass('active');
		     $("#noFile").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_i').bind('change', function () {
		  var filename = $("#chooseFile_i").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_i").removeClass('active');
		     $("#noFile_i").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_i").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_ii').bind('change', function () {
		  var filename = $("#chooseFile_ii").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_ii").removeClass('active');
		     $("#noFile_ii").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_ii").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_iii').bind('change', function () {
		  var filename = $("#chooseFile_iii").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_iii").removeClass('active');
		     $("#noFile_iii").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_iii").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	$('#chooseFile_iv').bind('change', function () {
		  var filename = $("#chooseFile_iv").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_iv").removeClass('active');
		     $("#noFile_iv").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_iv").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_v').bind('change', function () {
		  var filename = $("#chooseFile_v").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_v").removeClass('active');
		     $("#noFile_v").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_v").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_vi').bind('change', function () {
		  var filename = $("#chooseFile_vi").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_vi").removeClass('active');
		     $("#noFile_vi").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_vi").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	$('#chooseFile_vii').bind('change', function () {
		  var filename = $("#chooseFile_vii").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-k_vii").removeClass('active');
		     $("#noFile_vii").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_vii").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_viii').bind('change', function () {
		  var filename = $("#chooseFile_viii").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_viii").removeClass('active');
		     $("#noFile_viii").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_viii").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_ix').bind('change', function () {
		  var filename = $("#chooseFile_ix").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_ix").removeClass('active');
		     $("#noFile_ix").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_ix").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	$('#chooseFile_x').bind('change', function () {
		  var filename = $("#chooseFile_x").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_x").removeClass('active');
		     $("#noFile_x").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_x").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_xi').bind('change', function () {
		  var filename = $("#chooseFile_xi").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_xi").removeClass('active');
		     $("#noFile_xi").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_xi").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#chooseFile_xii').bind('change', function () {
		  var filename = $("#chooseFile_xii").val();
		  console.log("file has been choosen......",filename);
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload_xii").removeClass('active');
		     $("#noFile_xii").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_xii").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	
});

$(function(){
    $('#upload').on('click', function(){
    	$(".error-message").text("");
    	let errorBool = true;
    	   if ($('input[name="AspReportExcel"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportExcel").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportExcel"]').get(0).files[0].name != "Asp Report Excel.xlsx"){
    		console.log("No files selected.");
    	    $("#error-AspReportExcel").html("Please upload Asp Report Excel.xlsx");
    	    errorBool = false;
    	} 
    	 if ($('input[name="AspReportPdf_i"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_i").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_i"]').get(0).files[0].name != "Bajaj Allianz Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_i").html("Please upload Bajaj Allianz Asp Report.pdf");
    	    errorBool = false;
    	}
    	if ($('input[name="AspReportPdf_ii"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_ii").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_ii"]').get(0).files[0].name != "Canara HSBC Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_ii").html("Please upload Canara HSBC Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_iii"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_iii").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_iii"]').get(0).files[0].name != "Edelweiss Tokio Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_iii").html("Please upload Edelweiss Tokio Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_iv"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_iv").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_iv"]').get(0).files[0].name != "HDFC Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_iv").html("Please upload HDFC Asp Report.pdf");
    	    errorBool = false;
    	} 
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_v"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_v").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_v"]').get(0).files[0].name != "ICICI Prudential Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_v").html("Please upload ICICI Prudential Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_vi"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_vi").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_vi"]').get(0).files[0].name != "IndiaFirst Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_vi").html("Please upload IndiaFirst Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_vii"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_vii").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_vii"]').get(0).files[0].name != "Kotak Mahindra Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_vii").html("Please upload Kotak Mahindra Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_viii"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_viii").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_viii"]').get(0).files[0].name != "LIC Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_viii").html("Please upload LIC Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_ix"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_ix").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_ix"]').get(0).files[0].name != "Max Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_ix").html("Please upload Max Asp Report.pdf");
    	    errorBool = false;
    	} 
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_x"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_x").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_x"]').get(0).files[0].name != "SBI Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_x").html("Please upload SBI Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_xi"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_xi").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_xi"]').get(0).files[0].name != "Star Union Dai-ichi Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_xi").html("Please upload Star Union Dai-ichi Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');
    	
    	if ($('input[name="AspReportPdf_xii"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-AspReportPdf_xii").html("Please select a file to upload");
    	    errorBool = false;
    	}else if($('input[name="AspReportPdf_xii"]').get(0).files[0].name != "TATA AIA Asp Report.pdf"){
    		console.log("No files selected.");
    	    $("#error-AspReportPdf_xii").html("Please upload TATA AIA Asp Report.pdf");
    	    errorBool = false;
    	}
    	else
    		console.log('you have selected proper file...');  
    	
    	if(!errorBool){
    		return errorBool;
    	}
    	console.log('report upload id--->',$('input[name="reportUploadLogId"]').val());
    	
        var fd = new FormData($("form.form")[0]);
        $(".content").hide();
        $(".animaion").show();
        $("#error-AspReportExcel").html("");
        $("#error-AspReportPdf_i").html("");
        $("#error-AspReportPdf_ii").html("");
        $("#error-AspReportPdf_iii").html("");
        $("#error-AspReportPdf_iv").html("");
        $("#error-AspReportPdf_v").html("");
        $("#error-AspReportPdf_vi").html("");
        $("#error-AspReportPdf_vii").html("");
        $("#error-AspReportPdf_viii").html("");
        $("#error-AspReportPdf_ix").html("");
        $("#error-AspReportPdf_x").html("");
        $("#error-AspReportPdf_xi").html("");
        $("#error-AspReportPdf_xii").html("");
       
        var formData = new FormData($("form.form")[0]);
        console.log('form data--->',formData);
        $.ajax({
        	type: "POST",
            url: '${AspReportFileURl}',
            data:formData,
            processData: false,
            contentType: false,
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
            		console.log('pdf has been uploaded--->',data);
            		//var pdfURL = data.pdfURL;
           		 	//$('#output').html("<label>Please download the pdf here. </label> <a class='ml-2' href="+pdfURL+"><i class='fa fa-download'></i></a>");
           		 	$('#success_tic').modal('show');
           		 	$(".formrow").show();
           		 	$('input[name="InvestmentManagementFile"').val("");
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
            		$('#errorExcel').on('hidden.bs.modal', function (e) {
            			location.reload(); 
            		})
            	}else{
            		console.log('data is not uploaded',data);
            		var downloadUrl = data.downloadUrl;
            		console.log(downloadUrl);
            		$('#output').html("An error occourd. To download the error excel file click <a href="+downloadUrl+">here</a>");
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
	        error:function(){
	        	console.log("data failed to pass");
	        }
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
