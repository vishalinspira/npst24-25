<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.daily.average.service.model.ScrutinyInputQuarterlyInterval"%>
<%@page import="com.daily.average.service.model.InputQuarterlyInterval"%>
<%@page import="com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil"%>
<%@page import="Compliance_certificate_at_quarterly_interval.util.PrepopulateScrutinyData"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.average.service.model.InputQuarterlyInterval"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@ include file="/init.jsp" %>


<%@page import="Compliance_certificate_at_quarterly_interval.constants.Compliance_certificate_at_quarterly_intervalPortletKeys"%>

<portlet:resourceURL id="<%=Compliance_certificate_at_quarterly_intervalPortletKeys.COMPLIANCE_CERTIFICATE_AT_QUARTERLY_INTERVAL_PDF_GENERATE%>" var="pdfGenerationResourceURL" /> 

<!-- <portlet:actionURL name="quaterendedUpload" var="quaterendedUploadURL"> -->

<!-- </portlet:actionURL> -->

<% 
PrepopulateScrutinyData sd = new PrepopulateScrutinyData();
sd.pre_populate_scrutiny_data(request);
//getting inputQuarterlyInterval from asset render. Type casting the object to inputQuarterlyInterval.
ScrutinyInputQuarterlyInterval scrutinyInputQuarterlyInterval = (ScrutinyInputQuarterlyInterval) request.getAttribute("scrutinyInputQuarterlyInterval"); 
SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
long reportuploadlogId=(Long)request.getAttribute("reportUploadLogId");
//long reportuploadlogId=ParamUtil.getLong(request, "reportuploadlogId");
//out.println("reportuploadlogId:  "+reportuploadlogId);
InputQuarterlyInterval inputQuarterlyInterval=null;
try{
 inputQuarterlyInterval = InputQuarterlyIntervalLocalServiceUtil.fetchInputQuarterlyInterval(reportuploadlogId);
}catch(Exception e){
	inputQuarterlyInterval=InputQuarterlyIntervalLocalServiceUtil.createInputQuarterlyInterval(0);
}
if(Validator.isNull(inputQuarterlyInterval)){
	inputQuarterlyInterval=InputQuarterlyIntervalLocalServiceUtil.createInputQuarterlyInterval(0);
}

%>
<%-- <h1><%=reportuploadlogId %></h1>
<h4>${reportUploadLogId }</h4>
<h1><%=inputQuarterlyInterval %></h1> --%>

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



<div class="nps-page-main nps-upload-report nps-statement-wrapper">
   
    <div class="row mb-3">
        <div class="col-12">
           <div class="text-right">
                <p  class="back_btn"><a class="backbtn" href="/web/guest/quarterly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
       </div>
     </div>
     
   <div class="row"  id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <div class="">
               <div class="content">
                  <h4>Quaterly Compliance Certificate</h4>
                  <aui:form id="myForm" method="post">
                     <div class="statement-wrapper">
                        <input type="hidden" class="rowcount_one" name="rowcount_one">
                        <input type="hidden" class="rowcount_two" name="rowcount_two">
                        <input type="hidden" class="rowcount_three" name="rowcount_three">
                        <input type="hidden" class="rowcount_four" name="rowcount_four">
                        <input type="hidden" class="rowcount_five" name="rowcount_five">
                        <input type="hidden" class="rowcount_six" name="rowcount_six">
                        <input type="hidden" class="rowcount_seven" name="rowcount_seven">
                        <div class="row">
                           <div class="col">
                              <!-- <div class="form-group"> -->
                              	<input type="hidden" name="dlfileid">
								<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }">
								<input type="hidden" value="${reportMasterId }" name="reportMasterId" class="reportMasterId">
		                        <div class="row">
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
											<div class="nps-input-box mt-0">
												<label for="name" class="pl-2">Report Due Date</label>
												<input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
										</div>
									</div>
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
					                    <div class="nps-input-box mt-0">
					                        <label class="pl-3">For the Quarter Ended</label>
					                        <input type="date" class="rounded border-0 p-1 ml-3" id="formDate" name='formDate'>
					                    </div>
				                	</div>
								</div>
								
								<div class="row pt-3">
				                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
				                     <div class="nps-input-box file-upload">
				                        <div class="nps-input-box mt-0">
				                           <label>Compliance Certificate </label>
				                           <div class="file-select">
				                              <div class="file-select-name" id="noFile10">File Name</div>
				                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
				                              <input type="file" class="annexure-upload" id="annex_comp_certificate" name="annex_comp_certificate" accept=".pdf"/>
				                           </div>
				                           <label id="error10" class="error-message text-danger"></label>
				                           <br>
				                        </div>
				                     </div>
				                  </div>
					                  <div class="col-lg-6 col-md-6 col-sm-12 col-12 pt-4">
					                  	<div class="form-group">
				                        	<textarea class="form-control" id="annex_comp_certificate_rem" placeholder="Remarks if any" name="annex_comp_certificate_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_comp_certificate_rem():"" %></textarea>
				                        </div>
					                  </div>
				                 </div>
                             
                              <br>		
                              <div class="row">
                                 <div class="col-md-4">
                                    <p class="bold-text">To,</p>
                                    <p class="mb-0">The Chief Executive Officer</p>
                                    <p class="mb-0">NPS Trust</p>
                                    <p class="mb-0">14th Floor, IFCI Tower,,</p>
                                    <p class="mb-0">61, Nehru Place,N. Delhi - 110019</p>
                                 </div>
                              </div>
                              <br>
                              <div class="row">
                                 <div class="col-md-12">
                                    <p class="mb-2">Sir,</p>                                     
                                    <p class="mb-2">In my/our opinion and to the best of my/our information and according to the examinations carried
                                       out by me/us and explanations furnished to me/us, I/We certify the following in respect of the quarter mentioned above.
                                    </p>
                                 </div>
                              </div>
                              <!-- </div> -->
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <h5>SL.NO</h5>
                           </div>
                           <div class="col-md-6">
                              <h5>Parameters </h5>
                           </div>
                           <div class="col-md-2">
                              <h5>Yes or No</h5>
                           </div>
                           <div class="col-md-3">
                              <h5>Remarks</h5>
                           </div>
                        </div>
                        <hr/>
                        <h5>1. Management ,Sponser, Net-worth</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1ia">
                                 <p>a) Whether composition of the Board, Investment committee and Risk Management committeeis as per PFRDA (Pension Fund) regulations?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIay" name='oneia' value="Yes" >
                                 <label class="form-check-label" for="OneIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIan" name='oneia' value="No">
                                 <label class="form-check-label" for="OneIan">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIana" name='oneia' value="NA">
                                 <label class="form-check-label" for="OneIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneia_rem" placeholder="Remarks if any" name="oneia_rem" value="<%=inputQuarterlyInterval.getOneia_remark() %>" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1ib">
                                 <p>b) Whether Principal officer and key personnel have been appointed as per PFRDA (Pension Fund) regulations.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIby" name='oneib' value="Yes" >
                                 <label class="form-check-label" for="OneIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIbn" name='oneib' value="No">
                                 <label class="form-check-label" for="OneIbn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIbna" name='oneib' value="NA">
                                 <label class="form-check-label" for="OneIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneib_rem" placeholder="Remarks if any"  name="oneib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iia">
                                 <p>a) Whether bio-data of all the directors along with their interest in other companies has been filed with the NPS Trust within 15 days of their appointment?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIay" name='oneiia' value="Yes" >
                                 <label class="form-check-label" for="OneIIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIan" name='oneiia' value="No">
                                 <label class="form-check-label" for="OneIIan">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIana" name='oneiia' value="NA">
                                 <label class="form-check-label" for="OneIIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneiia_rem" placeholder="Remarks if any" name="oneiia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iib">
                                 <p>b) Whether subsequent change in the interest of the directors havebeen filed with the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIby" name='oneiib' value="Yes" >
                                 <label class="form-check-label" for="OneIIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIbn" name='oneiib' value="No">
                                 <label class="form-check-label" for="OneIIbn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIbna" name='oneiib' value="NA">
                                 <label class="form-check-label" for="OneIIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneiib_rem" placeholder="Remarks if any" name="oneiib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iic">
                                 <p>c) Whether minimum 51% of the directors have adequate professional experience in finance and financial services related fields.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIcy" name='oneiic' value="Yes" >
                                 <label class="form-check-label" for="OneIIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIcn" name='oneiic' value="No">
                                 <label class="form-check-label" for="OneIIcn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIcna" name='oneiic' value="NA">
                                 <label class="form-check-label" for="OneIIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiic"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneiic_rem" placeholder="Remarks if any" name="oneiic_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiic_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iid">
                                 <p>d) Whether change in key personnel has been intimated to the PFRDA within 15days of the occurrence of such change.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIcy" name='oneiid' value="Yes" >
                                 <label class="form-check-label" for="OneIIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIcn" name='oneiid' value="No">
                                 <label class="form-check-label" for="OneIIcn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIcna" name='oneiid' value="NA">
                                 <label class="form-check-label" for="OneIIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiid"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneiid_rem" placeholder="Remarks if any" name="oneiid_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiid_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iiia">
                                 <p>a) Whether the Sponsor and the Pension Fund(PFM) fulfill and comply with the eligibility requirements as specified under the PFRDA (Pension Fund) Regulations?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIIay" name='oneiiia' value="Yes" >
                                 <label class="form-check-label" for="OneIIIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIIan" name='oneiiia' value="No">
                                 <label class="form-check-label" for="OneIIIan">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIIana" name='oneiiia' value="NA">
                                 <label class="form-check-label" for="OneIIIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiiia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneiiia_rem" placeholder="Remarks if any" name="oneiiia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiiia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iiib">
                                 <p>b) Whether there is any material change in the information or particulars previously furnished which have a bearing onPFMs certificate of registration?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIIby" name='oneiiib' value="Yes" >
                                 <label class="form-check-label" for="OneIIIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIIbn" name='oneiiib' value="No">
                                 <label class="form-check-label" for="OneIIIbn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIIIbna" name='oneiiib' value="NA">
                                 <label class="form-check-label" for="OneIIIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiiib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneiiib_rem" placeholder="Remarks if any" name="oneiiib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiiib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iv)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iva">
                                 <p>a) Whether there is any major change in the management or ownership or shareholding pattern or any change in controlling interest of the Sponsor?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIVay" name='oneiva' value="Yes">
                                 <label class="form-check-label" for="OneIVay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIVan" name='oneiva' value="No">
                                 <label class="form-check-label" for="OneIVan">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="OneIVana" name='oneiva' value="NA">
                                 <label class="form-check-label" for="OneIVana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiva"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="oneiva_rem" placeholder="Remarks if any" name="oneiva_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiva_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(v)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1va">
                                 <p> Whether PFM is maintaining minimum Tangible Net-worth as stipulated by PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneVy" name='onev' value="Yes">
                                 <label class="form-check-label" for="OneVy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="OneVn" name='onev' value="No">
                                 <label class="form-check-label" for="OneVn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="OneVna" name='onev' value="NA">
                                 <label class="form-check-label" for="OneVna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="onev"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="onev_rem" placeholder="Remarks if any" name="onev_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOnev_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>2. Investment Policy</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2ia">
                                 <p>a) Whether Investment Policyhasbeen drawn in accordance with the investment guidelines approved by the PFRDA and has been approved by the Board of Directors (BOD) of the PFM?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIay" name='twoia' value="Yes">
                                 <label class="form-check-label" for="TwoIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIan" name='twoia' value="No">
                                 <label class="form-check-label" for="TwoIan">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIana" name='twoia' value="NA">
                                 <label class="form-check-label" for="TwoIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="twoia_rem" placeholder="Remarks if any" name="twoia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2ib">
                                 <p>b) Whether Investment Policy is being reviewed periodically (minimum half yearly basis) by the PFM?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIby" name='twoib' value="Yes">
                                 <label class="form-check-label" for="TwoIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIbn" name='twoib' value="No">
                                 <label class="form-check-label" for="TwoIbn">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIbna" name='twoib' value="NA">
                                 <label class="form-check-label" for="TwoIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="twoib_rem" placeholder="Remarks if any" name="twoib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row pb-3"></div>
                        <div class="row pt-3"></div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6 pt-3">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2ic">
                                 <p>c) Whether the PFM has submitted details of the Investment Policy reviewed by its board to the NPS Trust within 30days of such review.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIcy" name='twoic' value="Yes">
                                 <label class="form-check-label" for="TwoIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIcn" name='twoic' value="No">
                                 <label class="form-check-label" for="TwoIcn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="TwoIcna" name='twoic' value="NA">
                                 <label class="form-check-label" for="TwoIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoic"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="twoic_rem" placeholder="Remarks if any" name="twoic_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoic_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row pt-3"></div>
                        <div class="row">
                           <div class="col-md-2">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-7">
                              <div class="form-group">
                                 <p>Please provide the following:</p>
                                 <div class="row">
                                    <div class="col-sm-1"></div>
                                    <div class="col-sm-11">
                                       <p>i) Initial BOD approval date</p>
                                       <p>ii) Last BOD review date </p>
                                       <p>iii) Frequency of review in a year as decided by the BOD</p>
                                    </div>
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-3"></div>
                        </div>
                        <hr/>
                        <div class="row">
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group"><p>(ii)</p>
	                              </div>
	                           </div>
	                           <div class="col-md-8 pt-3">
	                              <div class="form-group">
	                                 <p>Please mention (individually), whether Investment Policy covers the following:</p>
	                              </div>
	                           </div>
	                           <div class="col-md-3">
	                           </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-6">
	                              <div class="form-group">
	                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iia">
	                                 <p>i) Prudential norms, Income recognition, Asset classification and Provisioning</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIoney" name='twoiione' value="Yes">
	                                 <label class="form-check-label" for="TwoIIoney">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIonen" name='twoiione' value="No">
	                                 <label class="form-check-label" for="TwoIIonen">&nbsp; No</label> 
	                              </div>
	                                <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIonena" name='twoiione' value="NA">
	                                 <label class="form-check-label" for="TwoIIonena">&nbsp; NA</label> 
	                              </div>
	                              <label class="error" for="twoiione"></label>
	                           </div>
	                           <div class="col-md-3">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="twoiione_rem" placeholder="Remarks if any"  name="twoiione_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiione_remark():"" %></textarea>
			                        </div>
                           		</div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-6 pt-3">
	                              <div class="form-group">
	                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iib">
	                                 <p>ii) Sector limits as stipulated in the Investment guidelines </p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIItwoy" name='twoiitwo' value="Yes">
	                                 <label class="form-check-label" for="TwoIItwoy">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIItwon" name='twoiitwo' value="No">
	                                 <label class="form-check-label" for="TwoIItwon">&nbsp; No</label> 
	                              </div>
	                               <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIItwona" name='twoiitwo' value="NA">
	                                 <label class="form-check-label" for="TwoIItwona">&nbsp; NA</label> 
	                              </div>
	                              <label class="error" for="twoiitwo"></label>
	                           </div>
	                           <div class="col-md-3">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="twoiitwo_rem" placeholder="Remarks if any" name="twoiitwo_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiitwo_remark():"" %></textarea>
			                        </div>
	                           </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-6 pt-3">
	                              <div class="form-group">
	                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iic">
	                                 <p>iii) Sponsor and Non-Sponsor Group limits as stipulated in the Investment guidelines</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIthreey" name='twoiithree' value="Yes">
	                                 <label class="form-check-label" for="TwoIIthreey">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIthreen" name='twoiithree' value="No">
	                                 <label class="form-check-label" for="TwoIIthreen">&nbsp; No</label> 
	                              </div>
	                                 <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIthreena" name='twoiithree' value="NA">
	                                 <label class="form-check-label" for="TwoIIthreena">&nbsp; NA</label> 
	                              </div>
	                              <label class="error" for="twoiithree"></label>
	                           </div>
	                           <div class="col-md-3">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="twoiithree_rem" placeholder="Remarks if any" name="twoiithree_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiithree_remark():"" %></textarea>
			                        </div>
	                           </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-6 pt-3">
	                              <div class="form-group">
	                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iid">
	                                 <p>iv) Liquidity and Asset/liability management </p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIfoury" name='twoiifour' value="Yes" >
	                                 <label class="form-check-label" for="TwoIIfoury">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIfourn" name='twoiifour' value="No">
	                                 <label class="form-check-label" for="TwoIIfourn">&nbsp; No</label> 
	                              </div>
	                               <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIfourna" name='twoiifour' value="NA">
	                                 <label class="form-check-label" for="TwoIIfourna">&nbsp; NA</label> 
	                              </div>
	                              <label class="error" for="twoiifour"></label>
	                           </div>
	                           <div class="col-md-3">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="twoiifour_rem" placeholder="Remarks if any" name="twoiifour_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiifour_remark():"" %></textarea>
			                        </div>
	                           </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-6 pt-3">
	                              <div class="form-group">
	                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iie">
	                                 <p>v) Stop Loss Limits</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIfivey" name='twoiifive' value="Yes">
	                                 <label class="form-check-label" for="TwoIIfivey">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIfiven" name='twoiifive' value="No">
	                                 <label class="form-check-label" for="TwoIIfiven">&nbsp; No</label> 
	                              </div>
	                                <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIfivena" name='twoiifive' value="NA">
	                                 <label class="form-check-label" for="TwoIIfivena">&nbsp; NA</label> 
	                              </div>
	                              <label class="error" for="twoiifive"></label>
	                           </div>
	                           <div class="col-md-3">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="twoiifive_rem" placeholder="Remarks if any" name="twoiifive_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiifive_remark():"" %></textarea>
			                        </div>
	                           </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-6 pt-3">
	                              <div class="form-group">
	                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iif">
	                                 <p>vi)Broker limit</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIsixy" name='twoiisix' value="Yes">
	                                 <label class="form-check-label" for="TwoIIsixy">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIsixn" name='twoiisix' value="No">
	                                 <label class="form-check-label" for="TwoIIsixn">&nbsp; No</label> 
	                              </div>
	                               <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIsixna" name='twoiisix' value="NA">
	                                 <label class="form-check-label" for="TwoIIsixna">&nbsp; NA</label> 
	                              </div>
	                              <label class="error" for="twoiisix"></label>
	                           </div>
	                           <div class="col-md-3">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="twoiisix_rem" placeholder="Remarks if any" name="twoiisix_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiisix_remark():"" %></textarea>
			                        </div>
	                           </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-6 pt-3">
	                              <div class="form-group">
	                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iig">
	                                 <p>vii) Investment audits</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIseveny" name='twoiiseven' value="Yes">
	                                 <label class="form-check-label" for="TwoIIseveny">&nbsp; Yes</label> 
	                              </div>
	                              <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIsevenn" name='twoiiseven' value="No">
	                                 <label class="form-check-label" for="TwoIIsevenn">&nbsp; No</label> 
	                              </div>
	                               <div class="form-check form-check-inline">
	                                 <input type="radio" id="TwoIIsevenna" name='twoiiseven' value="NA">
	                                 <label class="form-check-label" for="TwoIIsevenna">&nbsp; NA</label> 
	                              </div>
	                              <label class="error" for="twoiiseven"></label>
	                           </div>
	                           <div class="col-md-3">
	                           		<div class="form-group">
			                        	<textarea class="form-control" id="twoiiseven_rem" placeholder="Remarks if any" name="twoiiseven_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiiseven_remark():"" %></textarea>
			                        </div>
	                           </div>
	                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>3. Risk Management Policy</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3ia">
                                 <p>a) Whether Risk Management Policy hasbeen drawn in accordance with the guidelines approved by the PFRDA and has been approved by the Board of Directors?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeAy" name='threea' value="Yes">
                                 <label class="form-check-label" for="ThreeAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeAn" name='threea' value="No">
                                 <label class="form-check-label" for="ThreeAn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeAna" name='threea' value="NA">
                                 <label class="form-check-label" for="ThreeAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threea"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="threea_rem" placeholder="Remarks if any" name="threea_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreea_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3ib">
                                 <p>b) Whether Risk Management Policy is being reviewed periodically (minimum half yearly basis) by the PFM?</p>
                              </div>
                           </div>
                          <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeBy" name='threeb' value="Yes">
                                 <label class="form-check-label" for="ThreeBy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeBn" name='threeb' value="No">
                                 <label class="form-check-label" for="ThreeBn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeBna" name='threeb' value="NA">
                                 <label class="form-check-label" for="ThreeBna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threeb"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="threeb_rem" placeholder="Remarks if any" name="threeb_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeb_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row pb-3"></div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3ic">
                                 <p>c) Whether the PFM has submitted details of the Risk Management Policy reviewed by its board to the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeCy" name='threec' value="Yes">
                                 <label class="form-check-label" for="ThreeCy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeCn" name='threec' value="No">
                                 <label class="form-check-label" for="ThreeCn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="ThreeCna" name='threec' value="NA">
                                 <label class="form-check-label" for="ThreeCna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threec"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="threec_rem" placeholder="Remarks if any" name="threec_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreec_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-8">
                              <div class="form-group">
                                 <p>Please mention (individually), whether Risk Management policy covers the following:</p>
                              </div>
                              <div class="col-md-3"></div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3iia">
                                 <p>1. Risk Management functions</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 1.&nbsp;
                                 <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCiy" name='threeci' value="Yes">
                                    <label class="form-check-label" for="ThreeCiy">&nbsp; Yes</label> 
                                 </div>
                                 <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCin" name='threeci' value="No">
                                    <label class="form-check-label" for="ThreeCin">&nbsp; No</label> 
                                 </div>
                                   <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCina" name='threeci' value="NA">
                                    <label class="form-check-label" for="ThreeCina">&nbsp; NA</label> 
                                 </div>
                                 <label class="error" for="threeci"></label>
                              </div>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="threeci_rem" placeholder="Remarks if any" name="threeci_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeci_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3iib">
                                 <p>2. Disaster recovery and business continuity plans</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 2.&nbsp;
                                 <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCiiy" name='threecii' value="Yes">
                                    <label class="form-check-label" for="ThreeCiiy">&nbsp; Yes</label> 
                                 </div>
                                 <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCiin" name='threecii' value="No">
                                    <label class="form-check-label" for="ThreeCiin">&nbsp; No</label> 
                                 </div>
                                    <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCiina" name='threecii' value="NA">
                                    <label class="form-check-label" for="ThreeCiina">&nbsp; NA</label> 
                                 </div>
                                 <label class="error" for="threecii"></label>
                              </div>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="threecii_rem" placeholder="Remarks if any" name="threecii_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreecii_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3iic">
                                 <p>3. Insurance cover against risks</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 3.&nbsp;
                                 <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCiiiy" name='threeciii' value="Yes">
                                    <label class="form-check-label" for="ThreeCiiiy">&nbsp; Yes</label> 
                                 </div>
                                 <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCiiin" name='threeciii' value="No">
                                    <label class="form-check-label" for="ThreeCiiin">&nbsp; No</label> 
                                 </div>
                                    <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCiiina" name='threeciii' value="NA">
                                    <label class="form-check-label" for="ThreeCiiina">&nbsp; NA</label> 
                                 </div>
                                 <label class="error" for="threeciii"></label>
                              </div>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="threeciii_rem" placeholder="Remarks if any" name="threeciii_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeciii_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3iid">
                                 <p>4. Ensuring risk adjusted returns to subscribers consistent with the protection, safety and liquidity of such funds.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 4.&nbsp;
                                 <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCivy" name='threeciv' value="Yes">
                                    <label class="form-check-label" for="ThreeCivy">&nbsp; Yes</label> 
                                 </div>
                                 <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCivn" name='threeciv' value="No">
                                    <label class="form-check-label" for="ThreeCivn">&nbsp; No</label> 
                                 </div>
                                   <div class="form-check form-check-inline">
                                    <input type="radio" id="ThreeCivna" name='threeciv' value="NA">
                                    <label class="form-check-label" for="ThreeCivna">&nbsp; NA</label> 
                                 </div>
                                 <label class="error" for="threeciv"></label>
                              </div>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="threeciv_rem" placeholder="Remarks if any" name="threeciv_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeciv_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>4. Reporting of Investment Deviations</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4a">
                                 <p>a) Whether the PFM has ensured that all investments are made as per the investment guidelines?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FourAy" name='foura' value="Yes">
                                 <label class="form-check-label" for="FourAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FourAn" name='foura' value="No">
                                 <label class="form-check-label" for="FourAn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="FourAna" name='foura' value="NA">
                                 <label class="form-check-label" for="FourAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="foura"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="foura_rem" placeholder="Remarks if any" name="foura_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFoura_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4b">
                                 <p>b) In case of a deviation (downgrade to a rating not permitted under the regulations for corporate bonds or any other non-compliance in any scheme/asset class post investment), whether the PFM has recorded an internal note justifying its decision to hold such securities where deviation has occurred. </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FourBy" name='fourb' value="Yes">
                                 <label class="form-check-label" for="FourBy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FourBn" name='fourb' value="No">
                                 <label class="form-check-label" for="FourBn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="FourBna" name='fourb' value="NA">
                                 <label class="form-check-label" for="FourBna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fourb"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fourb_rem" placeholder="Remarks if any" name="fourb_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFourb_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4c">
                                 <p>c) Whether all such investment deviations are being reported to the Investment Committee and Board of the PFM for their approval to continue to remain invested in these securities.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FourCy" name='fourc' value="Yes">
                                 <label class="form-check-label" for="FourCy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FourCn" name='fourc' value="No">
                                 <label class="form-check-label" for="FourCn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="FourCna" name='fourc' value="NA">
                                 <label class="form-check-label" for="FourCna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fourc"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fourc_rem" placeholder="Remarks if any" name="fourc_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFourc_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-9">
                              <div class="form-group">
                                 <p>(Please provide details of the deviations that occurred in the quarter in Annexure 2 along with details of justification note and Investment Committee & Board approval extracts)</p>
                              </div>
                           </div>
                           <div class="col-md-3"></div>
                        </div>
                        <hr/>
                        <h5>5. Code of Conduct</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5ia">
                                 <p>Whether the PFM is engaged in any other business activity except those relating to pension schemes or funds, regulated by the PFRDA.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIy" name='fivei' value="Yes">
                                 <label class="form-check-label" for="FiveIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIn" name='fivei' value="No">
                                 <label class="form-check-label" for="FiveIn">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIna" name='fivei' value="NA">
                                 <label class="form-check-label" for="FiveIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivei"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fivei_rem" placeholder="Remarks if any" name="fivei_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivei_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5ib">
                                 <p>Whether the PFM has ensured that adequate disclosures are made to the PFRDA, the NPS Trust or subscriber in a comprehensible and timely manner.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIIy" name='fiveii' value="Yes">
                                 <label class="form-check-label" for="FiveIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIIn" name='fiveii' value="No">
                                 <label class="form-check-label" for="FiveIIn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIIna" name='fiveii' value="NA">
                                 <label class="form-check-label" for="FiveIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fiveii"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fiveii_rem" placeholder="Remarks if any" name="fiveii_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveii_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5ic">
                                 <p>Whether the PFM has ensured that there has not been any misrepresentation or submission of any misleading information to the PFRDA, NPS Trust or subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIIIy" name='fiveiii' value="Yes">
                                 <label class="form-check-label" for="FiveIIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIIIn" name='fiveiii' value="No">
                                 <label class="form-check-label" for="FiveIIIn">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIIIna" name='fiveiii' value="NA">
                                 <label class="form-check-label" for="FiveIIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fiveiii"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fiveiii_rem" placeholder="Remarks if any" name="fiveiii_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveiii_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iv)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5id">
                                 <p>Whether the PFM has divulged to anybody, either orally or in writing, directly or indirectly any confidential information about the PFRDA, the NPS Trust or subscribers, which has come to its knowledge, without taking prior permission of the PFRDA,the NPS Trust except where such disclosures are required to be made in compliance with any law for the time being in force.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIVy" name='fiveiv' value="Yes">
                                 <label class="form-check-label" for="FiveIVy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIVn" name='fiveiv' value="No">
                                 <label class="form-check-label" for="FiveIVn">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveIVna" name='fiveiv' value="NA">
                                 <label class="form-check-label" for="FiveIVna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fiveiv"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fiveiv_rem" placeholder="Remarks if any" name="fiveiv_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveiv_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(v)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iia">
                                 <p>a) Whether the PFM has made adequate disclosures of potential areas of conflict of duties or interest to the PFRDA, the NPS Trust or subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVay" name='fiveva' value="Yes">
                                 <label class="form-check-label" for="FiveVay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVan" name='fiveva' value="No">
                                 <label class="form-check-label" for="FiveVan">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVana" name='fiveva' value="NA">
                                 <label class="form-check-label" for="FiveVana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fiveva"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fiveva_rem" placeholder="Remarks if any" name="fiveva_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveva_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iib">
                                 <p>b) Whether the PFM hasestablished a mechanism to resolve any conflict of interest situation in an equitable manner, which may arise in the conduct of business.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVby" name='fivevb' value="Yes">
                                 <label class="form-check-label" for="FiveVby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVbn" name='fivevb' value="No">
                                 <label class="form-check-label" for="FiveVbn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVbna" name='fivevb' value="NA">
                                 <label class="form-check-label" for="FiveVbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivevb"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fivevb_rem" placeholder="Remarks if any" name="fivevb_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevb_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iic">
                                 <p>c) Whether the PFM is satisfied that there has been no instances of self-dealing or front running or insider trading by any of the directors and Key personnel through their accounts or through their family members, relatives or friends. </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVcy" name='fivevc' value="Yes">
                                 <label class="form-check-label" for="FiveVcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVcn" name='fivevc' value="No">
                                 <label class="form-check-label" for="FiveVcn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVcna" name='fivevc' value="NA">
                                 <label class="form-check-label" for="FiveVcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivevc"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fivevc_rem" placeholder="Remarks if any" name="fivevc_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevc_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-8">
                              <div class="form-group">
                                 <p>Kindly provide list of directors and key personnel with status of submissions of self-declarations in Annexure 3.</p>
                              </div>
                           </div>
                           <div class="col-md-3"></div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(vi)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iiia">
                                 <p>a) Whether PFM has promptly informed the PFRDA and the NPS Trust, if there is any change in the registration status or any penal action taken by any Authority or any material change in financials which may adversely affect the interest of the subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVIay" name='fivevia' value="Yes">
                                 <label class="form-check-label" for="FiveVIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVIan" name='fivevia' value="No">
                                 <label class="form-check-label" for="FiveVIan">&nbsp; No</label> 
                              </div>
                                  <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVIana" name='fivevia' value="NA">
                                 <label class="form-check-label" for="FiveVIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivevia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fivevia_rem" placeholder="Remarks if any" name="fivevia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iiib">
                                 <p>b) Whether the PFM has promptly informed the PFRDA and the NPS Trust about any action, legal proceedings initiated against it in respect of any material breach or non-compliance by it of any law, rules, regulations and directions of the PFRDA or any other regulatory body.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVIby" name='fivevib' value="Yes">
                                 <label class="form-check-label" for="FiveVIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVIbn" name='fivevib' value="No">
                                 <label class="form-check-label" for="FiveVIbn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="FiveVIbna" name='fivevib' value="NA">
                                 <label class="form-check-label" for="FiveVIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivevib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="fivevib_rem" placeholder="Remarks if any" name="fivevib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>6. Internal Auditors</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="6ia">
                                 <p>Whether appointment of the Internal Auditor and scope of internal audit is as per the Regulations/Guidance note issued by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIy" name='sixi' value="Yes">
                                 <label class="form-check-label" for="SixIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIn" name='sixi' value="No">
                                 <label class="form-check-label" for="SixIn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIna" name='sixi' value="NA">
                                 <label class="form-check-label" for="SixIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sixi"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sixi_rem" placeholder="Remarks if any" name="sixi_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixi_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="6iia">
                                 <p>a) Whether the PFM has produced to the Auditors such books, accounts, records and other documents in its custody or control and furnish such statement and information relating to its activities entrusted to its by the PFRDA, as it or he may require, within such reasonable time may be specified.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIay" name='sixiia' value="Yes">
                                 <label class="form-check-label" for="SixIIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIan" name='sixiia' value="No">
                                 <label class="form-check-label" for="SixIIan">&nbsp; No</label> 
                              </div>
                                  <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIana" name='sixiia' value="NA">
                                 <label class="form-check-label" for="SixIIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sixiia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sixiia_rem" placeholder="Remarks if any" name="sixiia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="6iib">
                                 <p>b) Whether the PFM has allowed Auditor's reasonable access to the premises occupied by it and also extend reasonable facility for examining any books, records, documents and computer data in the possession of the PFM.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIby" name='sixiib' value="Yes">
                                 <label class="form-check-label" for="SixIIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIbn" name='sixiib' value="No">
                                 <label class="form-check-label" for="SixIIbn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIbna" name='sixiib' value="NA">
                                 <label class="form-check-label" for="SixIIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sixiib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sixiib_rem" placeholder="Remarks if any" name="sixiib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="6iic">
                                 <p>c)Whether audit observations till previous quarter have been closed and suggestions of PFRDA/NPS Trust thereto have been complied with?</p>
                               </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIcy" name='sixiic' value="Yes">
                                 <label class="form-check-label" for="SixIIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIcn" name='sixiic' value="No">
                                 <label class="form-check-label" for="SixIIcn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="SixIIcna" name='sixiic' value="NA">
                                 <label class="form-check-label" for="SixIIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sixiic"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sixiic_rem" placeholder="Remarks if any" name="sixiic_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiic_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>7. Related Party engagement / transaction </h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7ia">
                                 <p>a) Whether any transactions or engagement have been carried out by the PFM with a related party except investments of National Pension SystemTrust funds?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIay" name='sevenia' value="Yes">
                                 <label class="form-check-label" for="SevenIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIan" name='sevenia' value="No">
                                 <label class="form-check-label" for="SevenIan">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIana" name='sevenia' value="NA">
                                 <label class="form-check-label" for="SevenIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sevenia_rem" placeholder="Remarks if any" name="sevenia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7ib">
                                 <p>b) Whether prior permission of the NPS Trust was taken before entering into such engagement/transaction?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIby" name='sevenib' value="Yes">
                                 <label class="form-check-label" for="SevenIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIbn" name='sevenib' value="No">
                                 <label class="form-check-label" for="SevenIbn">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIbna" name='sevenib' value="NA">
                                 <label class="form-check-label" for="SevenIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sevenib_rem" placeholder="Remarks if any" name="sevenib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7ic">
                                 <p>c)  Whether such engagement/transactions have been disclosed to the NPS Trust in its periodic reports.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIcy" name='sevenic' value="Yes">
                                 <label class="form-check-label" for="SevenIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIcn" name='sevenic' value="No">
                                 <label class="form-check-label" for="SevenIcn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIcna" name='sevenic' value="NA">
                                 <label class="form-check-label" for="SevenIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenic"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sevenic_rem" placeholder="Remarks if any" name="sevenic_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenic_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7id">
                                 <p>d) Whether such related party engagements / transactions aredone at fair market price?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIdy" name='sevenid' value="Yes">
                                 <label class="form-check-label" for="SevenIdy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIdn" name='sevenid' value="No">
                                 <label class="form-check-label" for="SevenIdn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIdna" name='sevenid' value="NA">
                                 <label class="form-check-label" for="SevenIdna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenid"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sevenid_rem" placeholder="Remarks if any" name="sevenid_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenid_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7ea">
                                 <p>e) Whether such transaction is recurring in nature?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIey" name='sevenie' value="Yes">
                                 <label class="form-check-label" for="SevenIey">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIen" name='sevenie' value="No">
                                 <label class="form-check-label" for="SevenIen">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="SevenIena" name='sevenie' value="NA">
                                 <label class="form-check-label" for="SevenIena">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenie"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="sevenie_rem" placeholder="Remarks if any" name="sevenie_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenie_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>8. Operations / Data Security / Infrastructure</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8ia">
                                 <p>a)Whether the PFM has complied with circular no. PFRDA/2017/30/PF/4 dated09.10.2017 onguidelines on outsourcing of activities by the Pension Fund? </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIay" name='eightia' value="Yes">
                                 <label class="form-check-label" for="EightIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIan" name='eightia' value="No">
                                 <label class="form-check-label" for="EightIan">&nbsp; No</label> 
                              </div>
                                  <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIana" name='eightia' value="NA">
                                 <label class="form-check-label" for="EightIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightia_rem" placeholder="Remarks if any" name="eightia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8ib">
                                 <p>b) Whether the PFM has complied with the reporting requirements of the circular no. PFRDA/2017/30/PF/4 dated 09.10.2017.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIby" name='eightib' value="Yes">
                                 <label class="form-check-label" for="EightIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIbn" name='eightib' value="No">
                                 <label class="form-check-label" for="EightIbn">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIbna" name='eightib' value="NA">
                                 <label class="form-check-label" for="EightIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightib_rem" placeholder="Remarks if any" name="eightib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8iia">
                                 <p>Whether all investments are held in the name of NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIIy" name='eightii' value="Yes">
                                 <label class="form-check-label" for="EightIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIIn" name='eightii' value="No">
                                 <label class="form-check-label" for="EightIIn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIIna" name='eightii' value="NA">
                                 <label class="form-check-label" for="EightIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="EightII"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightii_rem" placeholder="Remarks if any" name="eightii_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightii_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8iiia">
                                 <p>Whether PFM has pledged or hypothecated or lent any scheme assets which would amount to leverage on schemeâs portfolio?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIIIy" name='eightiii' value="Yes">
                                 <label class="form-check-label" for="EightIIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIIIn" name='eightiii' value="No">
                                 <label class="form-check-label" for="EightIIIn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIIIna" name='eightiii' value="NA">
                                 <label class="form-check-label" for="EightIIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightiii"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightiii_rem" placeholder="Remarks if any" name="eightiii_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightiii_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iv)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8iva">
                                 <p>Whether all charges/fees debited to the schemes aredeterminedas stipulated by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIVy" name='eightiv' value="Yes">
                                 <label class="form-check-label" for="EightIVy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIVn" name='eightiv' value="No">
                                 <label class="form-check-label" for="EightIVn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="EightIVna" name='eightiv' value="NA">
                                 <label class="form-check-label" for="EightIVna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightiv"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightiv_rem" placeholder="Remarks if any" name="eightiv_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightiv_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(v)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8va">
                                 <p>Whether all interest,dividendsor any such accrual income and proceeds of redemption/sale were collected on due dates and promptly credited to the scheme accounts?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVy" name='eightv' value="Yes">
                                 <label class="form-check-label" for="EightVy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVn" name='eightv' value="No">
                                 <label class="form-check-label" for="EightVn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVna" name='eightv' value="NA">
                                 <label class="form-check-label" for="EightVna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightv"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightv_rem" placeholder="Remarks if any" name="eightv_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightv_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(vi)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8via">
                                 <p>a) Whether the PFM has taken adequate and necessary steps to ensure that the data and records pertaining to its activities are maintained and are intact.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIay" name='eightvia' value="Yes">
                                 <label class="form-check-label" for="EightVIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIan" name='eightvia' value="No">
                                 <label class="form-check-label" for="EightVIan">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIana" name='eightvia' value="NA">
                                 <label class="form-check-label" for="EightVIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightvia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightvia_rem" placeholder="Remarks if any" name="eightvia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightvia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8vib">
                                 <p>b) Whether the PFM has ensured that for electronic records and data, up-to-date backup is always available with it.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIby" name='eightvib' value="Yes">
                                 <label class="form-check-label" for="EightVIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIbn" name='eightvib' value="No">
                                 <label class="form-check-label" for="EightVIbn">&nbsp; No</label> 
                              </div>
                                  <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIbna" name='eightvib' value="NA">
                                 <label class="form-check-label" for="EightVIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightvib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightvib_rem" placeholder="Remarks if any" name="eightvib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightvib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(vii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8viia">
                                 <p>a) Whether the PFM has maintained adequate infrastructure facilities to be able to discharge its services to the satisfaction of the PFRDA, the NPS Trust.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIay" name='eightviia' value="Yes">
                                 <label class="form-check-label" for="EightVIIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIan" name='eightviia' value="No">
                                 <label class="form-check-label" for="EightVIIan">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIana" name='eightviia' value="NA">
                                 <label class="form-check-label" for="EightVIIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightviia"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightviia_rem" placeholder="Remarks if any" name="eightviia_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviia_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8viib">
                                 <p>b) Whether the operating procedures and systems of the PFM are well documented and backed by operation manuals.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIby" name='eightviib' value="Yes">
                                 <label class="form-check-label" for="EightVIIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIbn" name='eightviib' value="No">
                                 <label class="form-check-label" for="EightVIIbn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIbna" name='eightviib' value="NA">
                                 <label class="form-check-label" for="EightVIIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightviib"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightviib_rem" placeholder="Remarks if any" name="eightviib_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviib_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(viii)</p>
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8viiia">
                                 <p>Whether the PFM has informed the entities in which investment of NPS funds have been made that interest received on the said investment is not liable for deduction of tax at source under the Income Tax Act, 1961</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIIy" name='eightviii' value="Yes">
                                 <label class="form-check-label" for="EightVIIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIIn" name='eightviii' value="No">
                                 <label class="form-check-label" for="EightVIIIn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="EightVIIIna" name='eightviii' value="NA">
                                 <label class="form-check-label" for="EightVIIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightviii"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="eightviii_rem" placeholder="Remarks if any" name="eightviii_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviii_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>9. Brokers empanelment:</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="9ia">
                                 <p>* Whether Brokers empanelment is done in accordance to the guidelines issued by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="NineAy" name='ninea' value="Yes">
                                 <label class="form-check-label" for="NineAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="NineAn" name='ninea' value="No">
                                 <label class="form-check-label" for="NineAn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="NineAna" name='ninea' value="NA">
                                 <label class="form-check-label" for="NineAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="ninea"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="ninea_rem" placeholder="Remarks if any" name="ninea_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getNinea_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8ib">
                                 <p>* Whether prescribed limit of percentage of transactions through any single broker is complied with?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="NineBy" name='nineb' value="Yes">
                                 <label class="form-check-label" for="NineBy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="NineBn" name='nineb' value="No">
                                 <label class="form-check-label" for="NineBn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input type="radio" id="NineBna" name='nineb' value="NA">
                                 <label class="form-check-label" for="NineBna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="nineb"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="nineb_rem" placeholder="Remarks if any" name="nineb_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getNineb_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>10. Inter-Scheme Investment Parameter</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="10ia">
                                 <p>Whether all such Inter-Scheme transfers are in conformity with the investment objective of the scheme to which such transfer has been made?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="Teny" name='ten' value="Yes">
                                 <label class="form-check-label" for="Teny">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="Tenn" name='ten' value="No">
                                 <label class="form-check-label" for="Tenn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="Tenna" name='ten' value="NA">
                                 <label class="form-check-label" for="Tenna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="ten"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="ten_rem" placeholder="Remarks if any" name="ten_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTen_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>11. Voting Obligation</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="11ia">
                                 <p>a) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenAy" name='elevena' value="Yes">
                                 <label class="form-check-label" for="ElevenAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenAn" name='elevena' value="No">
                                 <label class="form-check-label" for="ElevenAn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenAna" name='elevena' value="NA">
                                 <label class="form-check-label" for="ElevenAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="elevena"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="elevena_rem" placeholder="Remarks if any" name="elevena_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getElevena_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="11ib">
                                 <p>b)Whether, quarterly voting report has been submitted to the NPS Trust in compliance to Circular PFRDA/2017/17/PF/1 dated 20.04.2017?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenBy" name='elevenb' value="Yes">
                                 <label class="form-check-label" for="ElevenBy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenBn" name='elevenb' value="No">
                                 <label class="form-check-label" for="ElevenBn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenBna" name='elevenb' value="NA">
                                 <label class="form-check-label" for="ElevenBna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="elevenb"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="elevenb_rem" placeholder="Remarks if any" name="elevenb_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getElevenb_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>12. Reports and Disclosures as per Schedule V of PFRDA (PF) Regulations</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="12ia">
                                 <p>Whether quarterly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the quarter.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenAy" name='twelve' value="Yes">
                                 <label class="form-check-label" for="ElevenAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenAn" name='twelve' value="No">
                                 <label class="form-check-label" for="ElevenAn">&nbsp; No</label> 
                              </div>
                                  <div class="form-check form-check-inline">
                                 <input type="radio" id="ElevenAna" name='twelve' value="NA">
                                 <label class="form-check-label" for="ElevenAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twelve"></label>
                           </div>
                           <div class="col-md-3">
                           		<div class="form-group">
		                        	<textarea class="form-control" id="twelve_rem" placeholder="Remarks if any" name="twelve_rem" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwelve_remark():"" %></textarea>
		                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>Note:</h5>
                        <div class="row">
                        	<div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-11">
                              <div class="form-group">
                           
                                 <p>1.Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended thereto</p>
                              </div>
                           </div>
                         </div> 
                          <div class="row">
                          	<div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-6">
                              <div class="form-group">
                                 <p>2.This Compliance Certificate shall be put up to the Board at its meeting which to be held on
                                 	<input class="" type="date" id="notedate" name='notedate'>
                                 </p>
                              </div>
                           </div >
                           <div class="col-md-5">
                              <div class="form-group">
                              </div>
                           </div>
                         </div>
                         <div class="row">
                         	<div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-11">
                              <div class="form-group">
                                 <p>and the Remarks if any related thereto would be forwarded to NPS Trust subsequently.</p>
                              </div>
                           </div>
                         </div> 
                         <div class="row">
                        	<div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-11">
                              <div class="form-group">
                                 <p>Certified that the Information given, herein is correct and complete to the best of my/our knowledge and belief.</p>
                              </div>
                           </div>
                         </div> 
                         <%-- <div class="row">
		                 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <label for="companies">For</label><br>
		                     <select class="w-100" name='companies' id="companies">
		                     	<option value="">Select</option>
		                        <option value="Aditya Birla Sun Life Pension Management Limited">Aditya Birla Sun Life Pension Management Limited </option>
		                        <option value="HDFC Pension Fund Management Limited">HDFC Pension Management Company Limited </option>
		                        <option value="ICICI Prudential Pension Funds Management Company Limited">ICICI Prudential Pension Funds Management Company Limited </option>
		                        <option value="Kotak Mahindra Pension Fund Limited">Kotak Mahindra Pension Fund Limited </option>
		                        <option value="LIC Pension Fund Limited">LIC Pension Fund Limited </option>
		                        <option value="SBI Pension Funds Private Limited">SBI Pension Funds Private Limited </option>
		                        <option value="UTI Retirement Solutions Limited">UTI Retirement Solutions Limited </option>
		                     </select>
		                  </div>
		               </div >
		               <div class="row"></div>
                       <div class="row">
                           <div class="col-md-12">
                              <div class="form-group">
                                 <div class="row">
                                    <div class="col-md-6">
                                       <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                          <div class="form-group">
                                             <label>Date:</label>
                                             <input type="text" class="date_3" id="date_3" name="date_3" >
                                             <span id="date_3-error-1" class="text-danger"></span>
                                          </div>
                                       </div>
                                       <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                          <div class="form-group">
                                             <label>Place:</label>
                                             <input type="text" class="place" id="place" name="place">
                                             <span id="place-error-1" class="text-danger"></span>
                                          </div>
                                       </div>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-12"">
                                       <div class="col-md-6">
                                          <div class="form-group">
                                             <label>CEO Name</label>
                                             <input type="text" class="signature2 border-0 p-1" id="ceoname" name='ceoname'>
                                          </div>
                                       </div>
                                       <div class="col-lg-6 col-md-6 col-sm-12 col-12">
					                     <label for="ceonameii"></label><br>
					                     <select class="w-100" name='ceonameii' id="ceonameii">
					                     	<option value="">Select</option>
					                        <option value="CEO">CEO</option>
					                        
					                     </select>
					                  </div>
                                    </div>
                                 </div>
                              </div>
                           </div>
                        </div> --%>
                        
                         <div class="row">
		                 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <label for="companies">For</label><br>
		                     <!-- <select class="w-100" name='companies' id="companies">
		                     	<option value="">Select</option>
		                        <option value="Aditya Birla Sun Life Pension Management Limited">Aditya Birla Sun Life Pension Management Limited </option>
		                        <option value="HDFC Pension Fund Management Limited">HDFC Pension Management Company Limited </option>
		                        <option value="ICICI Prudential Pension Funds Management Company Limited">ICICI Prudential Pension Funds Management Company Limited </option>
		                        <option value="Kotak Mahindra Pension Fund Limited">Kotak Mahindra Pension Fund Limited </option>
		                        <option value="LIC Pension Fund Limited">LIC Pension Fund Limited </option>
		                        <option value="SBI Pension Funds Private Limited">SBI Pension Funds Private Limited </option>
		                        <option value="UTI Retirement Solutions Limited">UTI Retirement Solutions Limited </option>
		                     </select> -->
		                     <input type="text" class="w-100" readonly="readonly" name='companies' value="<%=companies %>">
		                     <label id="error-comanies" class="error-message text-danger"></label>
		                  </div>
		               </div>
		               <br>
                       <div class="row"> 
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box mt-0">
			                        <label>Date:</label>
                                    <input type="text" class="date_3" id="date_3" name="date_3" >
                                    <span id="date_3-error-1" class="text-danger"></span>
			                     </div>
			                  </div>
			                  
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box mt-0">
			                         <label>CEO Name</label>
                                    <input type="text" class="signature2 border-0 p-1" id="ceoname" name='ceoname'>
			                     </div>
			                  </div>
                         </div>
	                          <div class="row"> 
	                               <div class="col-lg-6 col-md-6 col-sm-12 col-12">
				                     <div class="nps-input-box mt-0">
				                         <label>Place:</label>
	                                    <input type="text" class="place" id="place" name="place">
	                                    <span id="place-error-1" class="text-danger"></span>
				                     </div>
				                   </div>
				                   
				                   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
				                     <div class="nps-input-box mt-0">
				                          <label for="ceonameii">Role</label><br>
				                          <input type="text" class="ceonameii" id="ceonameii" name="ceonameii">
						                   <!-- <select class="w-100" name='ceonameii' id="ceonameii">
						                   	<option value="">Select</option>
						                    <option value="CEO">CEO</option>
						                  </select> -->
				                     </div>
				                   </div>
	                           </div>
                        <hr/>
                        
                        
                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box file-upload">
			                        <div class="nps-input-box mt-0">
			                           <label>Annexure 1 : Details of composition of Board of Directors, Investment Committee, Risk Management Committee, Principal Officer and Key Personnel.</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile0">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
			                              <input type="file" class="annexure-upload" id="annex_i" name="annex_i" accept=".xlsx"/>
			                           </div>
			                           <label id="error0" class="error-message text-danger"></label>
			                           <br>
			                        </div>
			                     </div>
			                  </div>
			                   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                   	<div class="form-group">
		                        	<textarea class="form-control" id="annex_i_rem" placeholder="Remarks if any" name="annex_i_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_i_rem():"" %></textarea>
		                        </div>
			           </div>
                    
                        <div class="row">
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box file-upload">
			                        <div class="nps-input-box mt-0">
			                           <label>Board of Directors - a)Composition details</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile1">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
			                              <input type="file" class="annexure-upload" id="board_a" name="board_a" accept=".xlsx"/>
			                           </div>
			                           <label id="error1" class="error-message text-danger"></label>
			                           <br>
			                        </div>
			                     </div>
			                  </div>
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                  	<div class="form-group">
		                        	<textarea class="form-control" id="board_a_rem" placeholder="Remarks if any" name="board_a_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getBoard_b_rem():"" %></textarea>
		                        </div>
			                  </div>
		                 </div>
		                 <div class="row">
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box file-upload">
			                        <div class="nps-input-box mt-0">
			                           <label>Board of Directors - b)Details of meetings held in last 4 quarters</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile2">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
			                              <input type="file" class="annexure-upload" id="board_b" name="board_b" accept=".xlsx"/>
			                           </div>
			                           <label id="error2" class="error-message text-danger"></label>
			                           <br>
			                        </div>
			                     </div>
			                  </div>
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                  	<div class="form-group">
		                        	<textarea class="form-control" id="board_b_rem" placeholder="Remarks if any" name="board_b_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getBoard_b_rem():"" %></textarea>
		                        </div>
			                  </div>
		               </div>
		               <br>
		               <div class="row">
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box file-upload">
			                        <div class="nps-input-box mt-0">
			                           <label>Investment Committee - a)Composition details</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile3">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
			                              <input type="file" class="annexure-upload" id="investment_a" name="investment_a" accept=".xlsx"/>
			                           </div>
			                           <label id="error3" class="error-message text-danger"></label>
			                           <br>
			                        </div>
			                     </div>
			                  </div>
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                  	<div class="form-group">
		                        	<textarea class="form-control" id="investment_a_rem" placeholder="Remarks if any" name="investment_a_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getInvestment_b_rem():"" %></textarea>
		                        </div>
			                  </div>
		                 </div>
		                 <div class="row">
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box file-upload">
			                        <div class="nps-input-box mt-0">
			                           <label>Investment Committee - b)Details of meetings held in last 4 quarters</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile4">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
			                              <input type="file" class="annexure-upload" id="investment_b" name="investment_b" accept=".xlsx"/>
			                           </div>
			                           <label id="error4" class="error-message text-danger"></label>
			                           <br>
			                        </div>
			                     </div>
			                  </div>
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                  	<div class="form-group">
		                        	<textarea class="form-control" id="investment_b_rem" placeholder="Remarks if any" name="investment_b_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getInvestment_b_rem():"" %></textarea>
		                        </div>
			                  </div>
		               </div>
		               <br>
		               
	               	   <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <div class="nps-input-box file-upload">
		                        <div class="nps-input-box mt-0">
		                           <label>Risk Management Committee - a)Composition details</label>
		                           <div class="file-select">
		                              <div class="file-select-name" id="noFile5">File Name</div>
		                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
		                              <input type="file" class="annexure-upload" id="risk_a" name="risk_a" accept=".xlsx"/>
		                           </div>
		                           <label id="error5" class="error-message text-danger"></label>
		                           <br>
		                        </div>
		                     </div>
	                  		</div>
	                  		 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                  		 	<div class="form-group">
	                        		<textarea class="form-control" id="risk_a_rem" placeholder="Remarks if any" name="risk_a_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getRisk_a_rem():"" %></textarea>
	                        	</div>
		                  	</div>
	                  </div>
	                  <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <div class="nps-input-box file-upload">
		                        <div class="nps-input-box mt-0">
		                           <label>Risk Management Committee - b)Details of meetings held in last 4 quarters</label>
		                           <div class="file-select">
		                              <div class="file-select-name" id="noFile6">File Name</div>
		                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
		                              <input type="file" class="annexure-upload" id="risk_b" name="risk_b" accept=".xlsx"/>
		                           </div>
		                           <label id="error6" class="error-message text-danger"></label>
		                           <br>
		                        </div>
		                     </div>
		                  </div>
		                   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                   	<div class="form-group">
	                        	<textarea class="form-control" id="risk_b_rem" placeholder="Remarks if any" name="risk_b_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getRisk_b_rem():"" %></textarea>
	                        </div>
		                  </div>
	               	  </div>
		               <div class="row">
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box file-upload">
			                        <div class="nps-input-box mt-0">
			                           <label>Annexure 2 - Deviations to Investment guidelines</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile7">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
			                              <input type="file" class="annexure-upload" id="annex_ii" name="annex_ii" accept=".xlsx"/>
			                           </div>
			                           <label id="error7" class="error-message text-danger"></label>
			                           <br>
			                        </div>
			                     </div>
			                  </div>
			                   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                   	<div class="form-group">
		                        	<textarea class="form-control" id="annex_ii_rem" placeholder="Remarks if any" name="annex_ii_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_ii_rem():"" %></textarea>
		                        </div>
			                  </div>
		                 </div>
		                 <div class="row">
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                     <div class="nps-input-box file-upload">
			                        <div class="nps-input-box mt-0">
			                           <label>Annexure 3: List of Directors and Key personnel with status of submission of self-declaration w.r.t. dealing in securities</label>
			                           <div class="file-select">
			                              <div class="file-select-name" id="noFile8">File Name</div>
			                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
			                              <input type="file" class="annexure-upload" id="annex_iii" name="annex_iii" accept=".xlsx"/>
			                           </div>
			                           <label id="error8" class="error-message text-danger"></label>
			                           <br>
			                        </div>
			                     </div>
			                  </div>
			                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
			                  	<div class="form-group">
		                        	<textarea class="form-control" id="annex_iii_rem" placeholder="Remarks if any" name="annex_iii_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_iii_rem():"" %></textarea>
		                        </div>
			                  </div>
		               </div>
		               <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <div class="nps-input-box file-upload">
		                        <div class="nps-input-box mt-0">
		                           <label>Annexure 4 a Report on broker wise business</label>
		                           <div class="file-select">
		                              <div class="file-select-name" id="noFile9">File Name</div>
		                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
		                              <input type="file" class="annexure-upload" id="annex_iv" name="annex_iv" accept=".xlsx"/>
		                           </div>
		                           <label id="error9" class="error-message text-danger"></label>
		                           <br>
		                        </div>
		                     </div>
		                  </div>
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                  		<div class="form-group">
		                        	<textarea class="form-control" id="annex_iv_rem" placeholder="Remarks if any" name="annex_iv_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_iv_rem():"" %></textarea>
		                        </div>
			              </div>
		                </div>
		               <br>
                        <div class="row" id="sub">
                           <div class="col-md-12">
                              <div class="text-center">
                                 <input type="button" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
                                  <button id="pdfButton">PDF  </button> 
                              </div>
                           </div>
                        </div>
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
	select {
    background-color: #E9F3FC;
    color: #111111;
    border-radius: 5px;
    padding: 5px 20px;
}
</style>


<script> 
        
        $('#pdfButton').on('click', function() { 
            var pdfCheckBoxArray = []; 
            $("input:checkbox[name=pdfbox]:checked").each(function() { 
            	pdfCheckBoxArray.push($(this).val()); 
            }); 
            window.location.href="<%=pdfGenerationResourceURL%>&<portlate:namespace/>pdfCheckBox"+pdfCheckBoxArray;
			console.log(pdfCheckBoxArray);
        }); 
    </script>
