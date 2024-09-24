		<%@ include file="/init.jsp" %>
		
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
		
		<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
		taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
		taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
		taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
		
		<liferay-theme:defineObjects />
		
		<portlet:defineObjects />
		
		<portlet:resourceURL id="/furtherprocess/save" var="savefurtherprocessURL"></portlet:resourceURL>
		
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
      <div class="col-lg-7 col-md-10 col-sm-12 col-12">
         <div class="nps-report">
            <div class="form_block">
               <div class=" content">
                  <div class="row formrow">
                     <h4>A. Q01 Further Processed NSDL</h4>
                     <aui:form id="fileinfo" method="POST" >
                        <input type="hidden" name="dlfileid"/>
                        <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
                        <input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                        <div class="nps-input-box mt-0">
                           <label>Report Due Date</label>
                           <input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
                        </div>
                        <div class="nps-input-box file-upload">
                           <label>Report </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" id="chooseFile" name="furtherprocessFile" />
                           </div>
                           <label id="error-furtherprocessFile" class="error-message text-danger"></label>
                        </div>
                        <div class="nps-input-box">
                           <label for="name">Remarks</label>
                           <textarea id="remarks" name="remarks" rows="4" cols=""></textarea>
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
			            <img src="/o/Liferay-NPS-Theme/images/file-upload.png"  alt="Upload Reports" /> A. Q01 Further Processed NSDL
			        </div>
			    </div>
			</div>
			
			<div class="form_block mx-4">
				<div class=" content">
				  <div class="row formrow">
				    <div class="col">
						<aui:form id="fileinfo" method="POST" >
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<div class="fileUploadInput">
										<input type="file" class="form-control" name="furtherprocessFile" />
										<button type="button" class="btn file-btn">Choose File</button>
									</div>
									<label id="error-furtherprocessFile" class="error-message"></label>
								</div>
							</div>
						</div>
						<button id="upload" class="nps-btn" name="Submit" type="button">Save</button>
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
		<div class=" animaion" style="display:none;">
			 	<div class="row">
					<div class="loading-animation"></div>
				</div>
			</div>
		</div> --%>
		
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
			toastr.options = {
					  "debug": false,
					  "positionClass": "toast-bottom-right",
					  "onclick": null,
					  "fadeIn": 300,
					  "fadeOut": 1000,
					  "timeOut": 9000,
					  "extendedTimeOut": 1000
					}
			
			$(".file-upload .common-btn").click(function() {
				$(this).parent().children("input[type='file']").trigger("click");
			});
		});
		
		$(function(){
		    $('#upload').on('click', function(){
		    	if ($('input[name="furtherprocessFile"]').get(0).files.length === 0) {
		    	    console.log("No files selected.");
		    	    $("#error-furtherprocessFile").html("Please select a file to upload");
		    	    return false;
		    	}else if($('input[name="furtherprocessFile"]').get(0).files[0].name != "A. Q01 Further Processed-CEO Update data - September 21_v1 0- NSDL.xlsx"){
		    		console.log("No files selected.");
		    	    $("#error-furtherprocessFile").html("Please upload A. Q01 Further Processed-CEO Update data - September 21_v1 0- NSDL.xlsx");
		    	    return false;
		    	}
		    	
		        var fd = new FormData($("form.form")[0]);
		        $(".content").hide();
		        $(".animaion").show();
		        $("#error-furtherprocessFile").html("");
		        $.ajax({
		            url: '<%=savefurtherprocessURL %>',  
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
		           		 	$('input[name="furtherprocessFile"').val("");
		            	}else{
		            		console.log(data);
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
		            cache: false,
		            contentType: false,
		            processData: false
		        });
		    });
		});
		</script>
