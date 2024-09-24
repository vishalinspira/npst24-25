
<%@page import="java.util.Set"%>
<%@ include file="/init.jsp" %>

<p>
	<b><liferay-ui:message key="feeletter.caption"/></b>
</p>
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
		

		
		
		
<portlet:resourceURL id="feeletter" var="generateFeeLetterURL"></portlet:resourceURL>

   <div class="container">
	                    <form id="fileinfo" method="POST" >
	                  
	                        <div class="nps-input-box file-upload">
	                            <label for="name">Report </label>
	                            <div class="file-select">
	                              <!--  <div class="file-select-name" id="noFile">File Name</div>
	                               <div class="file-select-button common-btn" id="fileName">Choose File</div> -->
	                               <!-- accept=".xlsx" -->
	                               <input type="file" class="chooseFile" id="chooseFile" name="<portlet:namespace />feeletter"  />                               
	                            </div>
	                            <label id="error-feeletter" class="error-message text-danger"></label>
	                        </div>

	                        <div class="nps-input-box">
	                        	<button id="upload" class="common-btn d-inline-block" name="Submit" type="button">Save</button>
	                    
	                        </div>
	                    </form>
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
		});
		
		$(function(){
		    $('#upload').on('click', function(){
		    	
		        var fd = new FormData($("form.form")[0]);
		        $(".content").hide();
		        $(".animaion").show();
		        $("#error-feeletter").html("");
		        $.ajax({
		            url: '<%=generateFeeLetterURL %>',  
		            type: 'POST',
		            data: fd,
		            success:function(data){
		                try {
		                	//data = JSON.parse(data);
		                } catch (e) {
		                	console.log("error while parsing the json data");
		                }
		            		
		            	
		            },
		            error: function() {
		            	$(".animaion").hide();
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
	                