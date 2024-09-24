<%@ include file="/init.jsp" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />



<portlet:resourceURL id="/annexvi/save" var="annexviURL"></portlet:resourceURL>
<portlet:resourceURL id="/rejectionreturn/getreportlogdetails" var="getreportlogdetailsURL"></portlet:resourceURL>

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
	            <p  class="back_btn"><a class="backbtn" href="/web/guest/weekly-average"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
	        </div>
	     </div>
	  </div>
        <div class="row">
            <div class="col-lg-7 col-md-10 col-sm-12 col-12">
                <div class="nps-report">
                    <h4>Weekly Consolidated Collection Report</h4>
                    <div class="content">
                    <aui:form id="fileinfo" method="POST" >
                    <input type="hidden" name="dlfileid"/>
                    <input type="hidden" name="reportUploadLogId" class="reportUploadLogId"/>
							<fieldset class="row mb-3">
			                  <label class="p-3">CRA</label>
			                  <div class="col-sm-12">
			                    <div class="form-check form-check-inline">
			                      <input class="reportMasterId" type="radio" name="<portlet:namespace/>reportMasterId" id="gridRadios1" value="7" >
			                      <label class="form-check-label ml-1" for="gridRadios1">
			                        PCRA
			                      </label>
			                    </div>
			                    <div class="form-check form-check-inline">
			                      <input class="reportMasterId" type="radio" name="<portlet:namespace/>reportMasterId" id="gridRadios2" value="20">
			                      <label class="form-check-label ml-1" for="gridRadios2">
			                        KCRA
			                      </label>
			                    </div>
			                    <div class="form-check form-check-inline">
			                      <input class="reportMasterId" type="radio" name="<portlet:namespace/>reportMasterId" id="gridRadios3" value="125">
			                      <label class="form-check-label ml-1" for="gridRadios3">
			                        CCRA
			                      </label>
			                    </div>
			                  </div> 
			                </fieldset>
	                    <div class="nps-input-box mt-0">
                            <label for="name">Report Due Date</label>
                            <input class="reportDate" type="date" readonly="readonly">
                        </div>
                        <div class="nps-input-box file-upload">
                            <label for="name">Report </label>
                            <div class="file-select">
                               <div class="file-select-name" id="noFile">File Name</div>
                               <div class="file-select-button common-btn" id="fileName">Choose File</div>
                               <input type="file" class="chooseFile" id="chooseFile" name="annexviFile" />                               
                            </div>
                            <label id="error-annexviFile" class="error-message text-danger"></label>
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

<!-- Modal -->
<%-- <div id="success_tic" class="modal fade success_modal" role="dialog">
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
			      <h6>Data sent for review.</h6>
			    </div>
  			</div>
		</div>
	</div>
</div>
<div class="card_blocks">
	<div class="row">
	    <div class="col">
	        <div class="page_title">
	            <img src="/o/Liferay-NPS-Theme/images/file-upload.png" alt="Upload Reports" /> Weekly Consolidated Collection Report
	        </div>
	    </div>
	</div>
	
	<div class="form_block mx-4">
		<div class=" content">
		  <div class="row formrow">
		    <div class="col">
				<aui:form id="fileinfo" method="POST" >
												<input type="hidden" name="dlfileid"/>
							<input type="hidden" name="reportUploadLogId" class="reportUploadLogId"/>
							<fieldset class="row mb-3">
			                  <label class="p-3">CRA</label>
			                  <div class="col-sm-12">
			                    <div class="form-check form-check-inline">
			                      <input class="form-check-input reportMasterId" type="radio" name="<portlet:namespace/>reportMasterId" id="gridRadios1" value="7" >
			                      <label class="form-check-label ml-1" for="gridRadios1">
			                        NSDL
			                      </label>
			                    </div>
			                    <div class="form-check form-check-inline">
			                      <input class="form-check-input reportMasterId" type="radio" name="<portlet:namespace/>reportMasterId" id="gridRadios2" value="20">
			                      <label class="form-check-label ml-1" for="gridRadios2">
			                        Kfintech
			                      </label>
			                    </div>
			                  </div> 
			                </fieldset>
			                <div class="row">
			                	<div class="col-md-6">
			                		<div class="form-group">
			                			<label>Report Due Date</label>
			                			<input class="form-control reportDate" type="date" value="" readonly="readonly">
			                		</div>
			                	</div> 
			                	<div class="col-md-6">
			                		<div class="form-group">
			                			<label>Remarks</label>
			                			<textarea name="remarks" id="remarks"  class="form-control" rows="" cols=""></textarea>
			                		</div>
			                	</div>
			                </div>
		                <div class="row">
							<div class="col-6">
								<div class="form-group mt-4">
									<div class="fileUploadInput">
										<input type="file" class="form-control" name="annexviFile" />
										<button type="button" class="btn file-btn">Choose File</button>
									</div>
									<label id="error-annexviFile" class="error-message"></label>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-12 ">
								<button id="upload" class="nps-btn" name="Submit" type="button">Save</button>
							</div>
						</div>
				</aui:form>
		    </div>    
		  </div>
		  <div class="row mt-2">
		  	<div  class="col">
		    	<div id="output"></div>
		    </div>
		  </div>
		</div>
	</div>
	<div class=" animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
	</div>
</div> --%>
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
function getreportUploadLogId(){
	var fd = new FormData($("form.form")[0]);
	$.ajax({
        url: '<%=getreportlogdetailsURL %>',  
        type: 'POST',
        data: fd,
        success:function(data){
        	console.log(data);
        	try {
            	data = JSON.parse(data);
            	if(data.status){
            		$("#upload").prop('disabled', false);
            		$(".reportUploadLogId").val(data.reportUploadLogId);
                	
                	//var now = new Date(data.reportDate);
                	//var today = now.getFullYear()+"-"+(now.month)+"-"+(now.day) ;
                	$(".reportDate").val(data.ReportDate);
            	}else{
            		$("#upload").prop('disabled', true);
            	}
            	
            } catch (e) {
            	console.log("error while parsing the json data",e);
            }
        },
        error: function() {
        	//$(".animaion").hide();
        	//toastr.error('An error occured while submitting the form');
       		console.log("Error Occured in ajax call");
        },
        complete: function(){
				console.log("Complete");
	     },
        cache: false,
        contentType: false,
        processData: false
    });
}


$(function(){
	
	$(".reportMasterId").on('click', function(){
		 getreportUploadLogId();
	 });
	
    $('#upload').on('click', function(){
    	if ($('input[name="annexviFile"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-annexviFile").html("Please select a file to upload");
    	    return false;
    	}else if($('input[name="annexviFile"]').get(0).files[0].name != "Weekly Consolidated Collection Report.xlsx"){
    		console.log("No files selected.");
    	    $("#error-annexviFile").html("Please upload Weekly Consolidated Collection Report.xlsx");
    	    return false;
    	}
    	
        var fd = new FormData($("form.form")[0]);
        $(".content").hide();
        $(".animaion").show();
        $("#error-annexviFile").html("");
        $.ajax({
            url: '<%=annexviURL %>',  
            type: 'POST',
            data: fd,
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
            		//var pdfURL = data.pdfURL;
           		 	//$('#output').html("<label>Please download the pdf here. </label> <a class='ml-2' href="+pdfURL+"><i class='fa fa-download'></i></a>");
           		 	$('#success_tic').modal('show');
           		 	$(".formrow").show();
           		 	$('input[name="annexviFile"').val("");
           		 	$('#remarks').val("");
            	}else if(data.sheeterror){
            		$('#output').html("An error occourd.Following sheets not found  "+data.errorSheetNameList);
            		$('#errorExcel').modal('show');
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
            		//$('#output').html("An error occourd. To download the error excel file click <a href="+downloadUrl+">here</a>");
            		$('#output').html(data.msg);
            		$('#errorExcel').modal('show');
            	}
            },
            error: function() {
            	$(".animaion").hide();
            	$(".content").show();
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
    $('#errorExcel').on('hidden.bs.modal', function (e) {
    	location.reload(); 
    }); 
    $('#success_tic').on('hidden.bs.modal', function (e) {
    	location.reload(); 
    }); 
});
</script>