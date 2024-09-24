<%@ include file="/init.jsp" %>
		
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
		
		<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
		taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
		taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
		taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
		
		<liferay-theme:defineObjects />
		
		<portlet:defineObjects />
		
		
		
		<portlet:resourceURL id="/npstrustfeepfm/save" var="savenpstrustfeepfmURL"></portlet:resourceURL>
		
		
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
	            <p  class="back_btn"><a class="backbtn" href="/web/guest/monthly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
	        </div>
	     </div>
	  </div>
        <div class="row">
            <div class="col-lg-7 col-md-10 col-sm-12 col-12">
                <div class="nps-report">
                    <h4>NPS Trust Fee</h4>		
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
                              <input type="file" class="chooseFile" id="chooseFile" name="npstrustfeepfmFile" />                               
                           </div>
                           <label id="error-npstrustfeepfmFile" class="error-message text-danger"></label>
                       </div>
                       <div class="nps-input-box">
                           <label for="name">Remarks</label>
                           <textarea name="remarks" id="remarks" class="" rows="4" cols="" maxlength="1000"></textarea>
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

		<div class=" animaion" style="display:none;">
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
		});
		
		$(function(){
		    $('#upload').on('click', function(){
		    	if ($('input[name="npstrustfeepfmFile"]').get(0).files.length === 0) {
		    	    console.log("No files selected.");
		    	    $("#error-npstrustfeepfmFile").html("Please select a file to upload");
		    	    return false;
		    	}else if($('input[name="npstrustfeepfmFile"]').get(0).files[0].name != "NPS Trust Fee.xlsx"){
		    		console.log("No files selected.");
		    	    $("#error-npstrustfeepfmFile").html("Please upload NPS Trust Fee.xlsx");
		    	    return false;
		    	}
		    	
		        var fd = new FormData($("form.form")[0]);
		        $(".content").hide();
		        $(".animaion").show();
		        $("#error-npstrustfeepfmFile").html("");
		        $.ajax({
		            url: '<%=savenpstrustfeepfmURL %>',  
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
		           		 	$('input[name="npstrustfeepfmFile"').val("");
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
		            	//	$('#output').html("An error occourd. To download the error excel file click <a href="+downloadUrl+">here</a>");
		            	$('#output').html(data.msg);
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
		            cache: false,
		            contentType: false,
		            processData: false
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
