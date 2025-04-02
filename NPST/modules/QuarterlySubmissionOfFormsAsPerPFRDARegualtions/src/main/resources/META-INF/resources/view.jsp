<%@page import="com.quarterlysubmissionofforms.constants.QuarterlySubmissionOfFormsAsPerPFRDARegualtionsPortletKeys"%>
<%@ include file="/init.jsp" %>


 <portlet:resourceURL id="<%= QuarterlySubmissionOfFormsAsPerPFRDARegualtionsPortletKeys.quarterlySubmissionOfForms%>" var="saveQuarterlySubmissionPFRDA" /> 

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
                  <h4>Quarterly submission of forms as per PFRDA regulations</h4>
                     <aui:form id="fileinfo" method="POST" >
                        <input type="hidden" name="dlfileid"/>
                        <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
                        <input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                        <div class="nps-input-box mt-0">
                           <label>Report Due Date</label>
                           <input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
                        </div>
                       
                     <!--   comment for enable disable options -->
                        <!--  <div class="nps-input-box file-upload">
                           <label>Report:Form 1 - Report of transaction in securities by KP.xlsx </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_1">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" id="chooseFile_1" name="quarterlySubFormXlsx" accept=".xlsx" />
                           </div>
                            <label id="error-quarterlySubFormXlsx" class="error-message text-danger"></label>
                       </div> -->
                       
                       <div class="nps-input-box file-upload">
                           <label>Report:Form 1 - Report of transaction in securities by KP.pdf </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_2" >File Name</div>
                              <div class="file-select-button common-btn" id="fileName" >Choose File</div>
                              <input type="file" id="chooseFile_2" name="quarterlySubFormPdf" accept=".pdf"/>
                           </div>
                           <label id="error-quarterlySubFormPdf" class="error-message text-danger"></label>
                       </div>
                        <div class="nps-input-box file-upload">
                           <label>Report:Form 2 and 3 - Quarterly Compliance Forms.xlsx </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_3">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" id="chooseFile_3" name="quarterlyCompFormXlsx" accept=".xlsx" />
                           </div>
                            <label id="error-quarterlyCompFormXlsx" class="error-message text-danger"></label>
                       </div> 
                      
                      <!--   comment for enable disable options -->
                      <!--  <div class="nps-input-box file-upload">
                           <label>Report: Form 2 and 3 - Quarterly Compliance Forms.pdf </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_7" >File Name</div>
                              <div class="file-select-button common-btn" id="fileName" >Choose File</div>
                              <input type="file" id="chooseFile_7" name="annexureb" accept=".pdf"/>
                           </div>
                           <label id="error-annexureb" class="error-message text-danger"></label>
                       </div> -->
                       
                       <div class="nps-input-box file-upload">
                             <label>Report:Form - 4 Overview of Portfolio Positioning And Evaluation.pdf </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_4">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" id="chooseFile_4" name="form6Portfolio" accept=".pdf" />
                           </div>
                            <label id="error-form6Portfolio" class="error-message text-danger"></label>
                      </div>  
                     <!--  <div class="nps-input-box file-upload">
                             <label>Report:Form 3 Report by Director and KP.xlsx</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_5">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" id="chooseFile_5" name="form3ByKpXlsx" accept=".xlsx" />
                           </div>
                            <label id="error-form3ByKpXlsx" class="error-message text-danger"></label>
                          </div>    -->
                       
                       <div class="nps-input-box file-upload">
                           <label>Report:Form 3 Report by Director and KP.pdf </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_6" >File Name</div>
                              <div class="file-select-button common-btn" id="fileName" >Choose File</div>
                              <input type="file" id="chooseFile_6" name="form3ByKpPdf" accept=".pdf"/>
                           </div>
                           <label id="error-form3ByKpPdf" class="error-message text-danger"></label>
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
var url='<%=saveQuarterlySubmissionPFRDA %>';
  $(document).ready(function() {
	  /* toastr.options = {
			  "debug": false,
			  "positionClass": "toast-bottom-right",
			  "onclick": null,
			  "fadeIn": 300,
			  "fadeOut": 1000,
			  "timeOut": 9000,
			  "extendedTimeOut": 1000
			}
	   */
		$(".file-upload .common-btn").click(function() {
			$(this).parent().children("input[type='file']").trigger("click");
		});
	  
	  
	
		  $('#chooseFile_1').bind('change', function () {
			  var filename = $("#chooseFile_1").val();
			  if (/^\s*$/.test(filename)) {
			     $(".file-upload").removeClass('active');
			     $("#noFile_1").text("No file chosen...");
			  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_1").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	  $('#chooseFile_2').bind('change', function () {
		  var filename = $("#chooseFile_2").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile_2").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_2").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	  $('#chooseFile_3').bind('change', function () {
			  var filename = $("#chooseFile_3").val();
			  if (/^\s*$/.test(filename)) {
			     $(".file-upload").removeClass('active');
			     $("#noFile_3").text("No file chosen...");
			  }
			  else {
			     $(".file-upload").addClass('active');
			     $("#noFile_3").text(filename.replace("C:\\fakepath\\", ""));
		 	}
		});
	  $('#chooseFile_4').bind('change', function () {
				  var filename = $("#chooseFile_4").val();
				  if (/^\s*$/.test(filename)) {
				     $(".file-upload").removeClass('active');
				     $("#noFile_4").text("No file chosen...");
				  }
				  else {
				     $(".file-upload").addClass('active');
				     $("#noFile_4").text(filename.replace("C:\\fakepath\\", ""));
				  }
		});
	  $('#chooseFile_5').bind('change', function () {
					  var filename = $("#chooseFile_5").val();
					  if (/^\s*$/.test(filename)) {
					     $(".file-upload").removeClass('active');
					     $("#noFile_5").text("No file chosen...");
					  }
					  else {
					     $(".file-upload").addClass('active');
					     $("#noFile_5").text(filename.replace("C:\\fakepath\\", ""));
					  }
		});
	  $('#chooseFile_6').bind('change', function () {
		  var filename = $("#chooseFile_6").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile_6").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_6").text(filename.replace("C:\\fakepath\\", ""));
		  }
});
	  
	  $('#chooseFile_7').bind('change', function () {
		  var filename = $("#chooseFile_7").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile_7").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile_7").text(filename.replace("C:\\fakepath\\", ""));
		  }
});
	  
	});

	$(function(){
	    $('#upload').on('click', function(){
	    	$("#error-quarterlySubFormXlsx").html("");
	        $("#error-quarterlySubFormPdf").html("");
	        $("#error-quarterlyCompFormXlsx").html("");
	        $("#error-form6Portfolio").html("");
	        $("#error-form3ByKpXlsx").html("");
	        $("#error-form3ByKpPdf").html("");
	        $("#error-annexureb").html("");
	        
	        <!--   comment for enable disable options -->
	    	/* if ($('input[name="quarterlySubFormXlsx"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-quarterlySubFormXlsx").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="quarterlySubFormXlsx"]').get(0).files[0].name != "Form 1 - Report of transaction in securities by KP.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-quarterlySubFormXlsx").html("Please upload Form 1 - Report of transaction in securities by KP.xlsx");
	    	    return false;
	    	} */
	    	
	    	
	    	if ($('input[name="quarterlySubFormPdf"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-quarterlySubFormPdf").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="quarterlySubFormPdf"]').get(0).files[0].name != "Form 1 - Report of transaction in securities by KP.pdf"){
	    		console.log("No files selected.");
	    		$("#error-quarterlySubFormPdf").html("Please upload Form 1 - Report of transaction in securities by KP.pdf");
	    	    return false;
	    	}
	    	
	    	
	    	if ($('input[name="quarterlyCompFormXlsx"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-quarterlyCompFormXlsx").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="quarterlyCompFormXlsx"]').get(0).files[0].name != "Form 2 and 3 - Quarterly Compliance Forms.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-quarterlyCompFormXlsx").html("Please upload Form 2 and 3 - Quarterly Compliance Forms.xlsx");
	    	    return false;
	    	}
	    	
	    	if ($('input[name="form6Portfolio"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-form6Portfolio").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="form6Portfolio"]').get(0).files[0].name != "Form - 4 Overview of Portfolio Positioning And Evaluation.pdf"){
	    		console.log("No files selected.");
	    		$("#error-form6Portfolio").html("Please upload Form - 4 Overview of Portfolio Positioning And Evaluation.pdf");
	    	    return false;
	    	}
	    	
	    	<!--   comment for enable disable options -->
	    	/* if ($('input[name="form3ByKpXlsx"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-form3ByKpXlsx").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="form3ByKpXlsx"]').get(0).files[0].name != "Form 3 Report by Director and KP.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-form3ByKpXlsx").html("Please upload Form 3 Report by Director and KP.xlsx");
	    	    return false;
	    	} */
	    	
	    	if ($('input[name="form3ByKpPdf"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-form3ByKpPdf").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="form3ByKpPdf"]').get(0).files[0].name != "Form 3 Report by Director and KP.pdf"){
	    		console.log("No files selected.");
	    		$("#error-form3ByKpPdf").html("Please upload Form 3 Report by Director and KP.pdf");
	    	    return false;
	    	}
	    	
	    	<!--   comment for enable disable options -->
	    	
	    	/* if ($('input[name="annexureb"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-annexureb").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="annexureb"]').get(0).files[0].name != "Form 2 and 3 - Quarterly Compliance Forms.pdf"){
	    		console.log("No files selected.");
	    		$("#error-annexureb").html("Please upload Form 2 and 3 - Quarterly Compliance Forms.pdf");
	    	    return false;
	    	}
	    	 */
	    	
	    	
	    	
	    	
	        var formData = new FormData($("form.form")[0]);
	        $(".content").hide();
	        $(".animaion").show();
	        
	        
	        $.ajax({

	            type: "POST",
	            enctype: 'multipart/form-data', //plz look at this if error occurs
	            processData: false,
	            contentType: false,
	            cache: false,
	            url: url,
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
	           		 	$('#success_tic').modal('show');
	           		 	$(".formrow").show();
	           		 	//$('input[name="quarterlySubFormXlsx"').val("");
	           		    $('#remarks').val("");
	            	}else if(data.sheeterror){
	            		$('#output').html("An error occourd.Following sheet not Found  "+data.errorSheetNameList);
	            		$('#errorExcel').modal('show');
	            	}else if(data.apierror){
	            		$('#output').html("An error occourd while validating the file.");
	            		$('#errorExcel').modal('show');
	            		$('#errorExcel').on('hidden.bs.modal', function (e) {
	            			location.reload(); 
	            		})
	            	} else{
	            		console.log(data);
	            		var downloadUrl = data.downloadUrl;
	            		console.log(downloadUrl);
	            		//	$('#output').html("An error occured. To download the error excel file click <a href="+downloadUrl+">here</a>");
	            		$('#output').html(data.msg);
	            		if(!data.msg){
	            			$('#output').html("An error occourd while validating the file.");
	            		}
	            		$('#errorExcel').modal('show');
	            	}
	            },
	            error: function() {
	           		console.log("Error Occured in ajax call");
	           		$(".animaion").hide();
	           	//	toastr.error('An error occured while submitting the form');
	            },
	            complete: function(){
					console.log("Complete");
					$(".animaion").hide();
		        },
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