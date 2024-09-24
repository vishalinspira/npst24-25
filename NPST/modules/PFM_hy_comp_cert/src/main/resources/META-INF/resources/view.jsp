<%@page import="nps.common.service.util.PfmHyccUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil"%>
<%@page import="com.nps.pfm.hy.comp.cert.util.PfmHyccNonNps"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil"%>
<%@page import="com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.PFM_hy_comp_cert"%>
<%@ include file="/init.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:resourceURL id="/half_yearly_comp/save" var="half_yearly_compUrl"></portlet:resourceURL>

<%
PFM_hy_comp_cert_Scrutiny pfm_hy_comp_certScrutiny = Validator.isNotNull(request.getAttribute("pfm_hy_comp_cert_Scrutiny")) ? (PFM_hy_comp_cert_Scrutiny) request.getAttribute("pfm_hy_comp_cert_Scrutiny") : PFM_hy_comp_cert_ScrutinyLocalServiceUtil.createPFM_hy_comp_cert_Scrutiny(0);
PfmHyccNonNps pfmHyccNonNps = new PfmHyccNonNps();
boolean isNonNPSUser = pfmHyccNonNps.isNonNpsUser(themeDisplay.getUserId());

long reportuploadlogId=0;
//out.println("reportuploadlogId:  "+reportuploadlogId);
PFM_hy_comp_cert pfm_hy_comp_cert=null;
JSONObject jsonObject=null;
try{
	reportuploadlogId=(Long)request.getAttribute("reportUploadLogId");
	jsonObject=PfmHyccUtil.getHyComp_JSON_data(reportuploadlogId);
	pfm_hy_comp_cert = PFM_hy_comp_certLocalServiceUtil.fetchPFM_hy_comp_cert(reportuploadlogId);
}catch(Exception e){
	pfm_hy_comp_cert=PFM_hy_comp_certLocalServiceUtil.createPFM_hy_comp_cert(0);
}
if(Validator.isNull(pfm_hy_comp_cert)){
	pfm_hy_comp_cert=PFM_hy_comp_certLocalServiceUtil.createPFM_hy_comp_cert(0);
}
%>


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

<div class="nps-page-main nps-upload-report nps-statement-wrapper">
   <div class="row mb-3">
      <div class="col-12">
         <div class="text-right">
            <p  class="back_btn"><a class="backbtn" href="/web/guest/half-yearly"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
         </div>
      </div>
   </div>
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Half - yearly Compliance Certificates</h4>
            <form class="form" id="annualComplains" action="#" method="post">
               <input type="hidden" value="${reportUploadLogId }" name="reportUploadLogId" class="reportUploadLogId" />
               <input type="hidden" value="${reportMasterId }" name="reportMasterId" class="reportMasterId"/>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" name="reportDate" value="${reportDate }" readonly="readonly">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <label class="pl-3">For the half - year ended</label>
                        <input type="text" class="rounded border-0 p-1 ml-3" id="year" name='year'>
                    </div>
				  </div>
               </div>
               <div class="row pt-3">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Half Yearly Compliance Certificate</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="hy_comp_certificate_file" name="hy_comp_certificate_file" accept=".pdf"/>
                           </div>
                           
                           <br>
                        </div>
                        <label id="error-hy_comp_certificate_file" class="error-message text-danger"></label>
                     </div>
                  </div>
               </div>
               
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="col-md-9">
                        <div>
                           <p class="font-weight-bold mb-0">To,</p>
<p class=" font-weight-bold mb-0">National Pension System Trust,</p>
<p class=" font-weight-bold mb-0">Tower B, B-302, Third Floor,</p>
<p class="font-weight-bold mb-0">World Trade Center,</p>
<p class="font-weight-bold mb-0">Nauroji Nagar,</p>
<p class="font-weight-bold mb-0">New Delhi-110029</p>
                        </div>
                     </div>
                  </div>
               </div>
                <br>
               <p>Sir,</p>
               <br>
               <p>In my/our opinion and to the best of my/our information and according to the examinations carried out by
					me/us and explanations furnished to me/us, I/We certify the following in respect of the period mentioned
					above.
               </p>
               <br>
              <hr/>
<div class="row">
<div class="col-md-1">
<h5>SL.NO</h5>
</div>
<div class="col-md-5">
<h5>Parameters </h5>
</div>
<div class="col-md-2">
<h5>Yes/No/NA</h5>
</div>
<div class="col-md-2">
<h5>PFM Remarks</h5>
</div>
<div class="col-md-2">
<h5>NPST Remarks</h5>
</div>
</div>
<hr/>
     
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>1</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>Whether PFM has submitted copy of half-Yearly unaudited accounts
							of schemes within one month from the close of each half-year.</p>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input class="1" type="radio" id="1y" name='1_1' value="Yes" <%=(jsonObject.get("1_1")).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label" for="1y">&nbsp; Yes</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="1" type="radio" id="1n" name='1_1' value="No" <%=(jsonObject.get("1_1")).equals("No")?"checked":"" %>>
                        <label class="form-check-label" for="1n">&nbsp; No</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="1" type="radio" id="1n" name='1_1' value="NA" <%=(jsonObject.get("1_1")).equals("NA")?"checked":"" %>>
                        <label class="form-check-label" for="1n">&nbsp; NA</label> 
                     </div>
                     <label id="1-error" class="error" for="1_1"></label>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea class="form-control rem1" id="1_rem_1" placeholder="Remarks if any" name="1_rem_1" <%=(jsonObject.get("1_rem_1")) %>></textarea>
                 		</div>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea readonly="readonly" class="form-control rem1" id="1_npsrem_1" placeholder="Remarks if any" name="1_npsrem_1" <%=(jsonObject.get("1_npsrem_1")) %> ></textarea>
                 		</div>
                  </div>
               </div>
               <hr/>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>2</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>Whether PFM has disclosed on its website a copy of half-Yearly
							unaudited accounts of schemes along with scheme portfolio within
							one month from the close of each half-year</p>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input class="2" type="radio" id="2y" name='2_1' value="Yes" <%=(jsonObject.get("2_1")).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label" for="2y">&nbsp; Yes</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="2" type="radio" id="2n" name='2_1' value="No" <%=(jsonObject.get("2_1")).equals("No")?"checked":"" %>>
                        <label class="form-check-label" for="2n">&nbsp; No</label> 
                     </div>
                      <div class="form-check form-check-inline">
                        <input class="2" type="radio" id="2n" name='2_1' value="NA" <%=(jsonObject.get("2_1")).equals("NA")?"checked":"" %>>
                        <label class="form-check-label" for="2n">&nbsp; NA</label> 
                     </div>
                     <label id="2-error" class="error" for="2_1"></label>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea class="form-control rem2" id="2_rem_1" placeholder="Remarks if any" name="2_rem_1" <%=(jsonObject.get("2_rem_1")) %>></textarea>
                 </div>
                  </div>
                   <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea readonly="readonly" class="form-control rem1" id="2_npsrem_1" placeholder="Remarks if any" name="2_npsrem_1" <%=(jsonObject.get("2_npsrem_1")) %>></textarea>
                 		</div>
                  </div>
               </div>
               <hr/>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>3</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>Whether any change in interest of Directors has been submitted to
							NPS Trust every six months</p>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input class="3" type="radio" id="3y" name='3_1' value="Yes" <%=(jsonObject.get("3_1")).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label" for="3y">&nbsp; Yes</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="3" type="radio" id="3n" name='3_1' value="No" <%=(jsonObject.get("3_1")).equals("No")?"checked":"" %>>
                        <label class="form-check-label" for="3n">&nbsp; No</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="3" type="radio" id="3n" name='3_1' value="NA" <%=(jsonObject.get("3_1")).equals("NA")?"checked":"" %>>
                        <label class="form-check-label" for="3n">&nbsp; NA</label> 
                     </div>
                     
                     <label id="3-error" class="error" for="3_1"></label>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea class="form-control rem3" id="3_rem_1" placeholder="Remarks if any" name="3_rem_1" <%=(jsonObject.get("3_rem_1")) %>></textarea>
                 </div>
                  </div>
                   <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea readonly="readonly" class="form-control rem1" id="3_npsrem_1" placeholder="Remarks if any" name="3_npsrem_1" <%=(jsonObject.get("3_npsrem_1")) %>></textarea>
                 		</div>
                  </div>
               </div>
               <hr/>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>4</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>Whether PFM has submitted half-yearly certification by the Internal
							Auditor after it has been considered by the Board of PFM.</p>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input class="4" type="radio" id="4y" name='4_1' value="Yes" <%=(jsonObject.get("4_1")).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label" for="4y">&nbsp; Yes</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="4" type="radio" id="4n" name='4_1' value="No" <%=(jsonObject.get("4_1")).equals("No")?"checked":"" %>>
                        <label class="form-check-label" for="4n">&nbsp; No</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="4" type="radio" id="4n" name='4_1' value="NA" <%=(jsonObject.get("4_1")).equals("NA")?"checked":"" %>>
                        <label class="form-check-label" for="4n">&nbsp; NA</label> 
                     </div>
                     <label id="4-error" class="error" for="4_1"></label>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea class="form-control rem4" id="4_rem_1" placeholder="Remarks if any" name="4_rem_1" <%=(jsonObject.get("4_rem_1")) %>></textarea>
                 </div>
                  </div>
                   <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea readonly="readonly" class="form-control rem1" id="4_npsrem_1" placeholder="Remarks if any" name="4_npsrem_1" <%=(jsonObject.get("4_npsrem_1")) %> ></textarea>
                 		</div>
                  </div>
               </div>
               <hr/>
               <div class="row">
                <div class="col-md-12">
                     <div class="form-group">
                        <p>Note:</p>
                     </div>
                </div>
               </div>
               <div class="row">
                <div class="col-md-12">
                     <div class="form-group">
                        <p>1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been
						separately appended thereto.</p>
                     </div>
                </div>
               </div>
               <div class="row">
                <div class="col-md-12">
                     <div class="form-group">
                       <!--  <p>2) This Compliance Certificate(s) shall be put up to the Board on <input type="date" name="date1">/ has been put up to the
						Board on <input type="date" name="date2">.</p> -->
						<p class="ml-1">2) This Compliance Certificate(s)n shall be put up to the Board at its ensuing Board Meeting and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.  </p>
                     </div>
                </div>
               </div>
               <div class="row">
                <div class="col-md-12">
                     <div class="form-group">
                        <p>Certified that the Information given, herein is correct and complete to the best of my/our knowledge and belief.</p>
                     </div>
                </div>
               </div>
               
               <div class="row">
		                 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <label for="companies">For</label><br>
		                  
		                     <input type="text" class="w-100" readonly="readonly" name='companies' value="<%=companies %>">
		                     <label id="error-comanies" class="error-message text-danger"></label>
		                  </div>
		               </div>
		               <br>
		               
		               
               <div class="row">
                <div class="col-md-12">
                     <div class="form-check form-check-inline">
                     	<label class="form-check-label" for="date3">Date:</label> 
                        <input  type="date"  name='date3' >
                     </div>
                </div>
                <div class="col-md-12">
                     <div class="form-check form-check-inline">
                     	<label class="form-check-label" for="place">Place:</label> 
                        <input  type="text"  name='place' >
                     </div>
                </div>
               </div>
               
              <div class="row" id="sub">
                  <div class="col-md-12">
                     <div class="text-center">
                        <input type="button" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
                     </div>
                  </div>
              </div>
            </form>
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
	#example1_length, #example1_filter, #example1_info, #example1_paginate {
		display: none;
	}
	
	input.error {
		border-color: red;
	}
	
	select {
    background-color: #E9F3FC;
    color: #111111;
    border-radius: 5px;
    padding: 5px 20px;
	}
	
	.modal {
    	display: none;
	}
	
	.nps-body-main .nps-page-main.nps-statement-wrapper .table-cont table {
    	width: auto !important;
	}
</style>

<script type="text/javascript">
$(document).ready(function() {
	
	$("form.form").validate({
		  rules: {
			date1: {
		      required: true
		    },
		    year: {
			      required: true
			    },
		    date2: {
			      required: true
			},
			date3: {
				required: true
			},
			place: {
				required: true
			},
			1:{
				required: true
			},
			2:{
				required: true
			},
			3:{
				required: true
			},
			4:{
				required: true
			},
		  },
	});
	$('#hy_comp_certificate_file').bind('change', function () {
		  var filename = $("#hy_comp_certificate_file").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
		  }
	});
	$('#btn-submit').on('click', function(){ 
		let error = false;
		if ($('input[name="hy_comp_certificate_file"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error-hy_comp_certificate_file").html("Please select a file to upload");
    	    error = false;
    	}else if($('input[name="hy_comp_certificate_file"]').get(0).files[0].name != "Half Yearly Compliance Certificate.pdf"){
    		console.log("No files selected.");
    	    $("#error-hy_comp_certificate_file").html("Please upload Half Yearly Compliance Certificate.pdf");
    	    error = false;
    	}else{
    		console.log("Selected files.",);
    		error = true;
    		$("#error-hy_comp_certificate_file").html("");
    	}
		
		if($("form.form").valid() && error){
	    		$("#sub").addClass("hide");
	    		
		        var fd = new FormData($("form.form")[0]);
		        
		        var htmlWidth = $("#canvasD").width();
				var htmlHeight = $("#canvasD").height();
				var pdfWidth = htmlWidth + (15 * 2);
				var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
				var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

				var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;
		        
				html2canvas($("#canvasD")[0], { allowTaint: true }).then(function(canvas) {
					canvas.getContext('2d');

					var image = canvas.toDataURL("image/jpeg", 1.0);
					doc.addImage(image, 'JPEG', 15, 15, htmlWidth, htmlHeight);


					for (var i = 1; i <= pageCount; i++) {
						doc.addPage(pdfWidth, pdfHeight);
						doc.addImage(image, 'JPEG', 15, -(pdfHeight * i)+15, htmlWidth, htmlHeight);
					}
					
					console.log("doc.output('bloburl') ::: ", doc.output('arraybuffer'));
	/* 				doc.save("split-to-multi-page.pdf");
				     window.open(doc.output('bloburl')); */
				     
					var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
				     
					console.log("file::: ", file);
					
					fd.append("hycc_pdf_file", file);

			        $(".content").hide();
			        $(".animaion").show();
			        $.ajax({
			            url: '<%=half_yearly_compUrl %>',  
			            type: 'POST',
			            data: fd,
			            cache: false,
			            contentType: false,
			            processData: false,
			            success:function(data){
			            	$(".animaion").hide();
			            	if(data === "success") {
		   		            	$('#success_tic').modal('show');
		   		         		$("#detailedAuditReport")[0].reset();
		   		             	$('#success_tic').on('hidden.bs.modal', function (e) {
		   		             		location.reload(); 
		   		           		})
			            	} else {
			            		console.log("Error Occured in ajax call");
			            		$('#output').html('An error occured while submitting the form. Please try again');
			            		$('#errorExcel').modal('show');
			            	}
			            },
			            error: function() {
			            	$(".animaion").hide();
			            	$('#output').html('An error occured while submitting the form. Please try again');
			            	$('#errorExcel').modal('show');
			            },
			            complete: function(){
			            	$(".animaion").hide();
			            	if($("#sub").hasClass("hide")){
				            	$("#sub").removeClass("hide");
			            	}
			            	//$(".annexure-upload").val(null);
							//setTimeout(function() { location.reload(true); }, 5000);
				        }
			
			        });
				
			});	
	    	}else{
	    		toastr.error('Please fill the required field(s).');
	    		return false;
	    		//$('#output').html("Please fill the required field.");
	    	}
	    });
//	});
});
</script>
	