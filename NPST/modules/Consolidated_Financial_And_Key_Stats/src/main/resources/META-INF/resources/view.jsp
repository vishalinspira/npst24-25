<%@page import="com.consolidated.financial.key.stats.constants.Consolidated_Financial_And_Key_StatsPortletKeys"%>
<%@ include file="/init.jsp" %>

 <portlet:resourceURL id="<%= Consolidated_Financial_And_Key_StatsPortletKeys.FINANCIAL_KEY_STATS%>" var="saveConsolidatedFinancialAndKeyStats" /> 

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
                <p  class="back_btn"><a class="backbtn" href=" "><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
       </div>
     </div>
     
   <div class="row">
      <div class="col-lg-7 col-md-10 col-sm-12 col-12">
         <div class="nps-report">
            <div class="form_block">
               <div class=" content">
                  <div class="row formrow">
                  <h4>Consolidated Financial And Key Stats</h4>
                     <aui:form id="fileinfo" method="POST" >
                        <input type="hidden" name="dlfileid"/>
                        <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
                        <input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                        <div class="nps-input-box mt-0">
                           <label>Report Due Date</label>
                           <input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
                        </div>
                       
                       
                        <div class="nps-input-box file-upload">
                           <label>Report:Finanacial And Key Stats.pdf </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_1" >File Name</div>
                              <div class="file-select-button common-btn" id="fileName" >Choose File</div>
                              <input type="file" id="chooseFile_1" name="financialAndKeyStatsFormPdf" accept=".pdf"/>
                           </div>
                           <label id="error-financialAndKeyStatsFormPdf" class="error-message text-danger"></label>
                       </div>
                       
                         <div class="nps-input-box file-upload">
                           <label>Report:Consolidated Financial And Key Stats.xlsx </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile_2">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" id="chooseFile_2" name="financialAndKeyStatsFormXlsx" accept=".xlsx" />
                           </div>
                            <label id="error-financialAndKeyStatsFormXlsx" class="error-message text-danger"></label>
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
	  
	  
	});


$(function(){
	$('#upload').on('click', function(){
		if ($('input[name="financialAndKeyStatsFormPdf"]').get(0).files.length === 0) {
		    console.log("No files selected.");
		    $("#error-financialAndKeyStatsFormPdf").html("Please select a file to upload");
		    return false;
		}
		else if($('input[name="financialAndKeyStatsFormPdf"]').get(0).files[0].name != "Financial and Key Stats.pdf"){
			console.log("No files selected.");
			$("#error-financialAndKeyStatsFormPdf").html("Please upload Financial and Key Stats.pdf");
		    return false;
		}
	    	if ($('input[name="financialAndKeyStatsFormXlsx"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-financialAndKeyStatsFormXlsx").html("Please select a file to upload");
	    	    return false;
	    	}
	    	else if($('input[name="financialAndKeyStatsFormXlsx"]').get(0).files[0].name != "Consolidated Financial And Key Stats.xlsx"){
	    		console.log("No files selected.");
	    		$("#error-financialAndKeyStatsFormXlsx").html("Please upload Consolidated Financial And Key Stats.xlsx");
	    	    return false;
	    	}
	
	    	var formData = new FormData($("form.form")[0]);
	        $(".content").hide();
	        $(".animaion").show();
	        $("#error-financialAndKeyStatsFormPdf").html("");
	        $("#error-financialAndKeyStatsFormXlsx").html("");
	        
	        $.ajax({

	            type: "POST",
	            enctype: 'multipart/form-data', 
	            processData: false,
	            contentType: false,
	            cache: false,
	            url: '${saveConsolidatedFinancialAndKeyStats}',
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
	            	$('#output').html(data.msg);	
	            	$('#errorExcel').modal('show');
	            	}
	            },
	            error: function() {
	           		console.log("Error Occured in ajax call");
	           		$(".animaion").hide();
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