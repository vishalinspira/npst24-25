<%@page import="com.valuationreports.constants.ValuationReportsPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=ValuationReportsPortletKeys.valuationRep%>" var="valuationReportsResourceURL" /> 

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
                 <p  class="back_btn"><a class="backbtn" href="/web/guest/weekly-report-am"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
         </div>
     </div>
     
   <div class="row">
      <div class="col-lg-7 col-md-10 col-sm-12 col-12">
         <div class="nps-report">
            <div class="content">
               <h4>Valuation Reports</h4>
               <aui:form id="fileinfo" method="POST" >
                  <input type="hidden" name="dlfileid"/>
                  <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
                  <input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                  <div class="nps-input-box mt-0">
                     <label>Report Due Date</label>
                     <input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
                  </div>
                  <div class="nps-input-box file-upload">
                     <label>Valuation Report CG </label>
                     <div class="file-select">
                        <div class="file-select-name" id="noFile_1">File Name</div>
                        <div class="file-select-button common-btn" id="fileName_1">Choose File</div>
                        <input type="file" id="chooseFile_1" name="valuation_rep_cg" />
                     </div>
                     <label id="error-valuation_rep_cg" class="error-message text-danger"></label>
                  </div>
                  
                  <div class="nps-input-box file-upload">
                     <label>Valuation Report SG </label>
                     <div class="file-select">
                        <div class="file-select-name" id="noFile_2">File Name</div>
                        <div class="file-select-button common-btn" id="fileName_2">Choose File</div>
                        <input type="file" id="chooseFile_2" name="valuation_rep_sg" />
                     </div>
                     <label id="error-valuation_rep_sg" class="error-message text-danger"></label>
                  </div>
                  
                  <div class="nps-input-box file-upload">
                     <label>Valuation Report Debt </label>
                     <div class="file-select">
                        <div class="file-select-name" id="noFile_3">File Name</div>
                        <div class="file-select-button common-btn" id="fileName_3">Choose File</div>
                        <input type="file" id="chooseFile_3" name="valuation_rep_debt" />
                     </div>
                     <label id="error-valuation_rep_debt" class="error-message text-danger"></label>
                  </div>
                  
                  <div class="nps-input-box file-upload">
                     <label>Valuation Report Corporate Bonds </label>
                     <div class="file-select">
                        <div class="file-select-name" id="noFile_4">File Name</div>
                        <div class="file-select-button common-btn" id="fileName_4">Choose File</div>
                        <input type="file" id="chooseFile_4" name="valuation_rep_corp_bonds" />
                     </div>
                     <label id="error-valuation_rep_corp_bonds" class="error-message text-danger"></label>
                  </div>
                  
                  <div class="nps-input-box file-upload">
                     <label>Valuation Report Equity </label>
                     <div class="file-select">
                        <div class="file-select-name" id="noFile_5">File Name</div>
                        <div class="file-select-button common-btn" id="fileName_5">Choose File</div>
                        <input type="file" id="chooseFile_5" name="valuation_rep_equity" />
                     </div>
                     <label id="error-valuation_rep_equity" class="error-message text-danger"></label>
                  </div>
                  
                  <div class="nps-input-box file-upload">
                     <label>Valuation Report TB </label>
                     <div class="file-select">
                        <div class="file-select-name" id="noFile_6">File Name</div>
                        <div class="file-select-button common-btn" id="fileName_6">Choose File</div>
                        <input type="file" id="chooseFile_6" name="valuation_rep_tb" />
                     </div>
                     <label id="error-valuation_rep_tb" class="error-message text-danger"></label>
                  </div>
                  
                  <div class="nps-input-box">
                     <label for="name">Remarks</label>
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
		
		
	});

	$(function(){
	    $('#upload').on('click', function(){
	    	
	    	if ($('input[name="valuation_rep_cg"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-valuation_rep_cg").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="valuation_rep_cg"]').get(0).files[0].name != "valuation_cg.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-valuation_rep_cg").html("Please upload valuation_cg.xlsx");
	    	    return false;
	    	}
	    	
	    	if ($('input[name="valuation_rep_sg"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-valuation_rep_sg").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="valuation_rep_sg"]').get(0).files[0].name != "valuation_sg.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-valuation_rep_sg").html("Please upload valuation_sg.xlsx");
	    	    return false;
	    	}
	    	
	    	if ($('input[name="valuation_rep_debt"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-valuation_rep_debt").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="valuation_rep_debt"]').get(0).files[0].name != "Valuation report Debt.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-valuation_rep_debt").html("Please upload Valuation report Debt.xlsx");
	    	    return false;
	    	} 
	    	
	    	if ($('input[name="valuation_rep_corp_bonds"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-valuation_rep_corp_bonds").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="valuation_rep_corp_bonds"]').get(0).files[0].name != "Valuation report Corporate bonds.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-valuation_rep_corp_bonds").html("Please upload Valuation report Corporate bonds.xlsx");
	    	    return false;
	    	}
	    	
	    	if ($('input[name="valuation_rep_equity"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-valuation_rep_equity").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="valuation_rep_equity"]').get(0).files[0].name != "Valuation report Equity.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-valuation_rep_equity").html("Please upload Valuation report Equity.xlsx");
	    	    return false;
	    	}
	    	
	    	if ($('input[name="valuation_rep_tb"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-valuation_rep_tb").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="valuation_rep_tb"]').get(0).files[0].name != "valuation_tb.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-valuation_rep_tb").html("Please upload valuation_tb.xlsx");
	    	    return false;
	    	}
	    	
	    	
	        var formData = new FormData($("form.form")[0]);
	        $(".content").hide();
	        $(".animaion").show();
	        $("#error-valuation_rep_cg").html("");
	        $("#error-valuation_rep_sg").html("");
	        $("#error-valuation_rep_debt").html("");
	        $("#error-valuation_rep_corp_bonds").html("");
	        $("#error-valuation_rep_equity").html("");
	        $("#error-valuation_rep_tb").html("");
	        $.ajax({

	            type: "POST",
	            enctype: 'multipart/form-data',
	            processData: false,
	            contentType: false,
	            cache: false,
	            url: '${valuationReportsResourceURL}',
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
	           		 	//$('input[name="SBIRequestForDataReport"').val("");
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
	            		console.log(data);
	            		var downloadUrl = data.downloadUrl;
	            		console.log(downloadUrl);
	            		$('#output').html("An error occured. To download the error excel file click <a href="+downloadUrl+">here</a>");
	            		$('#errorExcel').modal('show');
	            	}
	            },
	            error: function() {
	           		console.log("Error Occured in ajax call");
	           		$(".animaion").hide();
	           		//toastr.error('An error occured while submitting the form');
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
