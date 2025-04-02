<%@page import="com.nps.cust.fee.letter.constants.PFMConstants"%>
<%@page import="java.util.Map.Entry"%>

<%@page import="npst.common.constant.NpstCommonConstant"%>
<%@page import="npst.common.constant.NpstRoleConstant"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="java.util.Set"%>
<%@ include file="/init.jsp" %>

<%
Set<String> pfmTypes= NpstCommonUtil.getUserType(user.getCompanyId(), NpstRoleConstant.PFM_ROLES, NpstCommonConstant.PFM_NAME);
%>
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
		
		
		
<portlet:resourceURL id="feeletter" var="generateFeeLetterURL"></portlet:resourceURL>

   <div class="container">
	                    <aui:form id="fileinfo" method="POST" >
	                  
		                    <div class="nps-input-box mt-0">
	                            <label style="color:black;" for="name">PFM</label>
	                          <!--   <input class="" type="text" value="" name="pfm"> -->
	                            
	                            <select id="pfmName" name="<portlet:namespace/>pfmName">
	                            <option value="<%=PFMConstants.DSP_ADDRESS %>" ><%=PFMConstants.DSP_ADDRESS %></option>
	                            <option value="<%=PFMConstants.MAX_ADDRESS%>" ><%=PFMConstants.MAX_ADDRESS%></option>
	                            <option value="<%=PFMConstants.ABS_ADDRESS%>" ><%=PFMConstants.ABS_ADDRESS%></option>
	                            <option value="<%=PFMConstants.TATA_ADDRESS%>" ><%=PFMConstants.TATA_ADDRESS%></option>
	                            <option value="<%=PFMConstants.APF_ADDRESS%>" ><%=PFMConstants.APF_ADDRESS%></option>
	                            <option value="<%=PFMConstants.KOTAK_ADDRESS%>" ><%=PFMConstants.KOTAK_ADDRESS%></option>
	                            <option value="<%=PFMConstants.ICICI_ADDRESS%>" ><%=PFMConstants.ICICI_ADDRESS%></option>
	                            <option value="<%=PFMConstants.HDFC_ADDRESS%>" ><%=PFMConstants.HDFC_ADDRESS%></option>
	                            <option value="<%=PFMConstants.UTI_ADDRESS%>" ><%=PFMConstants.UTI_ADDRESS%></option>
	                            <option value="<%=PFMConstants.LIC_ADDRESS%>" ><%=PFMConstants.LIC_ADDRESS%></option>
	                            <option value="<%=PFMConstants.SBI_ADDRESS%>" ><%=PFMConstants.SBI_ADDRESS%></option>
<%-- 										<%for(String pfmType:pfmTypes){%>
											<option value="<%=pfmType%>" ><%=pfmType %></option>
											<%} %> --%>
    							</select>
    
	                        </div>
	                        <div class="row">
	                        <div class="nps-input-box col-4 ">
	                            <label style="color:black;" for="letterDate">Letter Date</label>
	                             <input style="color:black; background-color: #E9F3FA;" type="text" class="letterDate" id="letterDate" name="<portlet:namespace/>letterDate"  />  
	                            <%--    <input style="color:black; background-color: #E9F3FA;" type="date" class="letterDate" id="letterDate" name="<portlet:namespace/>letterDate"  /> --%>  
	                            <label id="error-letterDate" class="error-message text-danger"></label>
	                            </div>
	                          <%--   <div class="nps-input-box col-4">
	                            <label style="color:black;" for="receivedDate">Received Date</label>
	                             <input style="color:black; background-color: #E9F3FA;" type="text" class="receivedDate" id="receivedDate" name="<portlet:namespace/>receivedDate"  />  
	                            <label id="error-receivedDate" class="error-message text-danger"></label>
	                            </div> --%>
	                            <div class="nps-input-box col-4">
	                            <label style="color:black; " for="quarter">Quarter</label>
	                             <input style="color:black; background-color: #E9F3FA;" type="text" class="quarter" id="quarter" name="<portlet:namespace/>quarter"  />  
	                           <label id="error-quarter" class="error-message text-danger"></label>
	                            </div>
	                            
	                            
	                            
	                            
	                        </div>
	                         
	                        <div class="row">
	                         <div class="nps-input-box col-4">
	                            <label style="color:black;" for="signeturies">Signatury</label>
	                            
	                             <select id="signeturies" name="<portlet:namespace/>signeturies">
							  <%for(Entry<String,String> entry:PFMConstants.SIGNETURIES_MAP.entrySet()){%>
								  <option value="<%=entry.getKey()%>" ><%=entry.getKey() %> -- <%=entry.getValue() %></option>
                        <%} %>
	                             </select>
	                             
	                            </div> 
	                        <div class="nps-input-box col-4 file-upload">
	                            <label style="color:black;" for="name">File </label>
	                            <div class="file-select">
	                              <!--  <div class="file-select-name" id="noFile">File Name</div>
	                               <div class="file-select-button common-btn" id="fileName">Choose File</div> -->
	                               <!-- accept=".xlsx" -->
	                               <input style="color:black;" type="file" class="chooseFile" id="chooseFile" name="feeletter" accept=".xlsx" />                               
	                            </div>
	                            <label id="error-feeletter" class="error-message text-danger"></label>
	                        </div>
	                        </div>
	                        

	                        <div class="nps-input-box">
	                        	<button id="generate" class="common-btn d-inline-block" name="generate" type="button">generate</button>
	                    
	                        </div>
	                    </aui:form>
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
			
			$(".file-upload .common-btn").click(function() {
				$(this).parent().children("input[type='file']").trigger("click");
			});
		});
		
		$(function(){
		    $('#generate').on('click', function(){
		    	
		    	
		    	if ($('input[name="feeletter"]').get(0).files.length === 0) {
		    	    console.log("No files selected.");
		    	    $("#error-feeletter").html("Please select a file to upload");
		    	    return false;
		    	}
		    	console.log($('#quarter').val());
		    	if ($('#quarter').val() == "" || $('#quarter').val()==null || $('#quarter').val()=="undefined") {
		    	    $("#error-quarter").html("This field is required.");
		    	    return false;
		    	}
		    	/* if ($('#receivedDate').val() == "" || $('#receivedDate').val()==null || $('#receivedDate').val()=="undefined") {
		    	    $("#error-letterDate").html("This field is required.");
		    	    return false;
		    	} */
		    	/* if ($('#letterDate').val() == "" || $('#letterDate').val()==null || $('#letterDate').val()=="undefined") {
		    	    $("#error-receivedDate").html("This field is required.");
		    	    return false;
		    	} */
		    	
		        var fd = new FormData($("form.form")[0]);
		        $(".content").hide();
		        $(".animaion").show();
		        $("#error-feeletter").html("");
		        $("#error-quarter").html("");
		        $("#error-letterDate").html("");
		       /*  $("#error-receivedDate").html(""); */
		        $("#generate").prop("disabled", true);
		        $.ajax({
		            url: '<%=generateFeeLetterURL %>',  
		            type: 'POST',
		            data: fd,
		            success:function(data){
		            	$(".content").show();
		                $(".animaion").hide();
		                $(".formrow").hide();
		                try {
		                	//data = JSON.parse(data);
		                } catch (e) {
		                	console.log("error while parsing the json data");
		                }
		            		console.log(data);
		            		var downloadUrl = data;
		            		console.log(downloadUrl);
		            		$("#generate").prop("disabled", false);
		            		$('#output').html("To download the Fee Letter click <a href="+downloadUrl+">here</a>");
		            		$('#errorExcel').modal('show');
		            	
		            },
		            error: function() {
		            	$(".animaion").hide();
		            	toastr.error('An error occured while submitting the form');
		           		console.log("Error Occured in ajax call");
		           		$("#generate").prop("disabled", false);
		            },
		            complete: function(){
		            	$("#generate").prop("disabled", false);
						console.log("Complete");
			        },
		            cache: false,
		            contentType: false,
		            processData: false
		        });
		    });
		    $('#errorExcel').on('hidden.bs.modal', function (e) {
		    	/* location.reload();  */
		    }); 
		    $('#success_tic').on('hidden.bs.modal', function (e) {
		    	/* location.reload(); */ 
		    });
		});
		</script>
	                