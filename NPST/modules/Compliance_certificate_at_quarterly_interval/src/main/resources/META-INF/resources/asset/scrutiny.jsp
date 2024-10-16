<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.ScrutinyInputQuarterlyInterval"%>
<%@page import="Compliance_certificate_at_quarterly_interval.util.NPSUserPrepopulateScrutinyData"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.average.service.model.InputQuarterlyInterval"%>
<%@ include file="/init.jsp" %>
<%@page import="Compliance_certificate_at_quarterly_interval.util.ComplianceCertificateAtQuartelyIntervalUtil"%>


<%@page import="Compliance_certificate_at_quarterly_interval.constants.Compliance_certificate_at_quarterly_intervalPortletKeys"%>

<portlet:resourceURL id="<%=Compliance_certificate_at_quarterly_intervalPortletKeys.input_quarterly_intervalPortletKeys%>" var="InputcomplianceResourceURL" /> 


<!-- <portlet:actionURL name="quaterendedUpload" var="quaterendedUploadURL"> -->

<!-- </portlet:actionURL> -->


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.1.1/css/all.min.css" />

<div class="modal fade" id="errorExcel" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true" style="display: none; left:auto;">
  <div class="modal-dialog modal-sm ">
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

<% 
//getting inputQuarterlyInterval from asset render. Type casting the object to inputQuarterlyInterval.
InputQuarterlyInterval inputQuarterlyInterval = (InputQuarterlyInterval) request.getAttribute("inputQuarterlyInterval"); 
SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
long uploadLogId=inputQuarterlyInterval.getReportUploadLogId();
//List<PFM_executive_summary> executive_summaries=PFM_executive_summaryLocalServiceUtil.getExecutiveSummaryByReportUploadLogId(uploadLogId);
/* pre populate data for asset view */
boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;
boolean isAssignable = Validator.isNotNull(request.getAttribute("isAssignable")) ? (boolean) request.getAttribute("isAssignable") : false;
boolean isSelfAsignee = Validator.isNotNull(request.getAttribute("isSelfAsignee")) ? (boolean) request.getAttribute("isSelfAsignee") : false;
NPSUserPrepopulateScrutinyData sd = new NPSUserPrepopulateScrutinyData();
sd.pre_populate_scrutiny_data(request);
ScrutinyInputQuarterlyInterval scrutinyInputQuarterlyInterval = (ScrutinyInputQuarterlyInterval) request.getAttribute("scrutinyInputQuarterlyInterval"); 


List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");

boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
boolean isDocSigned = false;
String dDate[]=dateFormat.format(inputQuarterlyInterval.getReportDate()).split("-");

int month=Integer.parseInt(dDate[0]);
int year=Integer.parseInt(dDate[2]);
if(month==1){
	year=year-1;
	month=12;
}else{
	month=month-1;	
}
String formDate1=month+"/"+year;


JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
String fileEntryId = "0";
JSONObject signature = JSONFactoryUtil.createJSONObject();
%>
	<div class="custom-modal-ui hide"  id="success-modal">
		<div class="modal-head-ui">
			<h2 class="modal-title-ui">Report Message</h2>
			<a class="modal-close-ui x-mark" href="#">&times;</a>
			<div class="modal-content-ui text-center">
				<i class="" id="icon"></i>
				<span id="output"></span>
			</div>
		</div>
	</div>
<div class="nps-page-main nps-upload-report nps-statement-wrapper">
<div>
	<span>File Version : </span>
<% for(int i=0; i<urlArray.length();i++){
	JSONObject object = urlArray.getJSONObject(i);
	isDocSigned = Validator.isNotNull(object.getString("signature")); 
	fileEntryId = object.getString("fileEntryId");
	signature = JSONFactoryUtil.createJSONObject();
	if(isDocSigned){
		signature = JSONFactoryUtil.createJSONObject(object.getString("signature"));
	}
%>


<%if(isNonNPSUser){ %>
<span class="file-preview-span" data-index='<%= i%>'>
		<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a> 
	</span>
		<% if(i != (urlArray.length()-1)){ %>
	 ,
	<% } %>
<%} %>

<%if(!isNonNPSUser){
	if(isDocSigned){
	%>
<span class="file-preview-span" data-index='<%= i%>'>
		<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a> 
	</span>
		<% if(i != (urlArray.length()-1)){ %>
	 ,
	<% } %>
<%}} %>


<%-- 	<span class="file-preview-span" data-index='<%= i%>'>
	 	<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a> 
	 </span>
	<% if(i != (urlArray.length()-1)){ %>
	 ,
	<% } %> --%>
	
<% } %>
</div>
<%-- <h1><%=fileEntryId %></h1> --%>
<% if(isPfmSupervisor){ %>
	<div class="panel-body">
		
		<% if(!isDocSigned){ %>
			<button id="signFile" class="btn  btn-primary">Sign File</button>
		<% }else{ %>
			<button id="signFile" class="btn  btn-primary" disabled="disabled">Sign File</button>
			<h6 class="signedmsg">The report has been Authenticated</h6>
		<% } %>
	</div>
<% }else{ %>
	<div class="panel-body">
		<% if(isDocSigned){ %>
			<h6 class="signedmsg">The report has been Authenticated</h6>
		<% } %>
	</div>
<% } %>


	
   <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <div class="">
               <div class="content">
                  <h4>INPUT - Compliance certificate at quarterly interval</h4>

                  <form id="myForm" method="post">
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
											<input class="reportDate" type="text" value="<%= dateFormat.format(inputQuarterlyInterval.getReportDate()) %>" readonly="readonly">
										</div>
									</div>
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
					                    <div class="nps-input-box mt-0">
					                        <label class="pl-3">For the Quarter Ended</label>
					                        <input type="text" class="rounded border-0 p-1 ml-3" id="formDate" value="<%= formDate1 %>" readonly="readonly" name='formDate'>
					                    </div>
				                	</div>
								</div>
								<br>
								<%-- <div class="row">
				                     <div class="col-md-6">
				                        <div class="form-check form-check-inline">
				                            <p>Compliance Certificate</p>
				                        </div>
				                    </div>
				                    <div class="col-md-3">
				                        <div class="form-check form-check-inline">
				                            <!-- <a>Click here to download</a> -->
				                            <a href="${empty annex_comp_certificate ? "javascript:void(0);" : annex_comp_certificate}" ${empty annex_comp_certificate ? "" : "download"}>Click here to download</a>
				                        </div>
				                    </div> 
				
				                    <div class="col-md-3">
				                        <div class="form-group">
				                        	<textarea class="form-control" id="annex_comp_certificate_rem" name="annex_comp_certificate_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_comp_certificate_rem():"" %></textarea>
				                        </div>
				                    </div>
				        		</div> --%>
                             
                              <br>		
                              <div class="row">
                                 <div class="col-md-4">
                                    <p class="font-weight-bold mb-0">To,</p>
<p class=" font-weight-bold mb-0">National Pension System Trust,</p>
<p class=" font-weight-bold mb-0">Tower B, B-302, Third Floor,</p>
<p class="font-weight-bold mb-0">World Trade Center,</p>
<p class="font-weight-bold mb-0">Nauroji Nagar,</p>
<p class="font-weight-bold mb-0">New Delhi-110029</p>
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
                              <h5>SL.No</h5>
                           </div>
                           <div class="col-md-3">
                              <h5>Parameters </h5>
                           </div>
                           <div class="col-md-2">
                              <h5>Yes/No/NA</h5>
                           </div>
                           <div class="col-md-3">
                              <h5>PFM Remarks</h5>
                           </div>
                           <div class="col-md-3">
                              <h5>NPST Remarks</h5>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether composition of the Board, Investment committee and Risk Management committeeis as per PFRDA (Pension Fund) regulations?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                          
                           <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIay" name='oneia' value="Yes" <%=(inputQuarterlyInterval.getOneia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIan" name='oneia' value="No" <%=(inputQuarterlyInterval.getOneia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIan">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIana" name='oneia' value="NA" <%=(inputQuarterlyInterval.getOneia()).equals("NA")?"checked":""%>>
                                 <label class="form-check-label" for="OneIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= inputQuarterlyInterval.getOneia()%></label> 
                              </div>
                              <% } %>
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
                                          <textarea class="form-control" id="oneia_rem_intermediary"  name="oneia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneia_remark():"" %></textarea>
		                       </div>
		                    </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneia_rem"  name="oneia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneia_rem():"" %></textarea>
		                        </div>
		                    </div>
                           <div class="col-md-2">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether Principal officer and key personnel have been appointed as per PFRDA (Pension Fund) regulations.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIby" name='oneib' value="Yes" <%=(inputQuarterlyInterval.getOneib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIbn" name='oneib' value="No" <%=(inputQuarterlyInterval.getOneib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIbn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIbna" name='oneib' value="NA" <%=(inputQuarterlyInterval.getOneib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIbna">&nbsp; NA</label> 
                              </div>                            
                              <label class="error" for="oneib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOneib() %></label> 
                              </div>
                              <% } %>
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneib_rem_intermediary"  name="oneib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneib_rem"  name="oneib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneib_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether bio-data of all the directors along with their interest in other companies has been filed with the NPS Trust within 15 days of their appointment?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIay" name='oneiia' value="Yes" <%=(inputQuarterlyInterval.getOneiia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIan" name='oneiia' value="No"  <%=(inputQuarterlyInterval.getOneiia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIan">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIana" name='oneiia' value="NA" <%=(inputQuarterlyInterval.getOneiia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                              	<label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOneiia() %></label>
                              </div>
                               <% } %>
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiia_rem_intermediary"  name="oneiia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiia_rem"  name="oneiia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether subsequent change in the interest of the directors havebeen filed with the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIby" name='oneiib' value="Yes"   <%=(inputQuarterlyInterval.getOneiib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIbn" name='oneiib' value="No"   <%=(inputQuarterlyInterval.getOneiib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIbn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIbna" name='oneiib' value="NA"   <%=(inputQuarterlyInterval.getOneiib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOneiib() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
	                        <div class="form-group">
	                        	<textarea class="form-control" id="oneiib_rem_intermediary" name="oneiib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiib_remark():"" %></textarea>
	                        </div>
	                       </div>
                           <div class="col-md-3">
	                        <div class="form-group">
	                        	<textarea class="form-control" id="oneiib_rem" name="oneiib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiib_rem():"" %></textarea>
	                        </div>
	                       </div>
                           
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>c) Whether minimum 51% of the directors have adequate professional experience in finance and financial services related fields.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                           <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIcy" name='oneiic' value="Yes"   <%=(inputQuarterlyInterval.getOneiic()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIcn" name='oneiic' value="No"   <%=(inputQuarterlyInterval.getOneiic()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIcn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIcna" name='oneiic' value="NA"   <%=(inputQuarterlyInterval.getOneiic()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiic"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOneiic() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiic_rem_intermediary"  name="oneiic_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiic_remark():"" %></textarea>
		                        </div>
		                    </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiic_rem"  name="oneiic_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiic_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>d) Whether change in key personnel has been intimated to the PFRDA within 15days of the occurrence of such change.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                           <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIdy" name='oneiid' value="Yes"   <%=(inputQuarterlyInterval.getOneiid()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIdy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIdn" name='oneiid' value="No"   <%=(inputQuarterlyInterval.getOneiid()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIdn">&nbsp; No</label> 
                              </div>
                                  <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIdna" name='oneiid' value="NA"  <%=(inputQuarterlyInterval.getOneiid()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIdna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiid"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOneiid() %></label>
                              </div>
                                <% } %>                            
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiid_rem_intermediary"  name="oneiid_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiid_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiid_rem"  name="oneiid_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiid_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether the Sponsor and the Pension Fund(PFM) fulfill and comply with the eligibility requirements as specified under the PFRDA (Pension Fund) Regulations?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIIay" name='oneiiia' value="Yes"   <%=(inputQuarterlyInterval.getOneiiia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIIan" name='oneiiia' value="No"  <%=(inputQuarterlyInterval.getOneiiia()).equals("No")?"checked":"" %>> 
                                 <label class="form-check-label" for="OneIIIan">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIIana" name='oneiiia' value="NA"  <%=(inputQuarterlyInterval.getOneiiia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiiia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOneiiia() %></label>
                              </div>
                                <% } %>
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiiia_rem_intermediary_intermediary"  name="oneiiia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiiia_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiiia_rem"  name="oneiiia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiiia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether there is any material change in the information or particulars previously furnished which have a bearing onPFMs certificate of registration?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIIby" name='oneiiib' value="Yes"  <%=(inputQuarterlyInterval.getOneiiib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIIbn" name='oneiiib' value="No"  <%=(inputQuarterlyInterval.getOneiiib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIIbn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIIIbna" name='oneiiib' value="NA"  <%=(inputQuarterlyInterval.getOneiiib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIIIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiiib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOneiiib() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiiib_rem_intermediary"  name="oneiiib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiiib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiiib_rem"  name="oneiiib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiiib_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether there is any major change in the management or ownership or shareholding pattern or any change in controlling interest of the Sponsor?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneIvay" name='oneiva' value="Yes"  <%=(inputQuarterlyInterval.getOneiva()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIvay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Oneivan" name='oneiva' value="No"  <%=(inputQuarterlyInterval.getOneiva()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIvan">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Oneivana" name='oneiva' value="NA"  <%=(inputQuarterlyInterval.getOneiva()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneIvana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="oneiva"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOneiva() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiva_rem_intermediary"  name="oneiva_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiva_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiva_rem"  name="oneiva_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiva_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p> Whether PFM is maintaining minimum Tangible Net-worth as stipulated by PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="OneVy" name='onev' value="Yes"  <%=(inputQuarterlyInterval.getOnev()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="OneVy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Onevn" name='onev' value="No"  <%=(inputQuarterlyInterval.getOnev()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="OneVn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Onevna" name='onev' value="NA"  <%=(inputQuarterlyInterval.getOnev()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="OneVna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="onev"></label>
                              <% } else {%>
                             
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getOnev() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
	                       		<div class="form-group">
	                       			<textarea class="form-control" id="onev_rem_intermediary" name="onev_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOnev_remark():"" %></textarea>
	                       		</div>
	                  	 </div>
                           <div class="col-md-3">
	                       		<div class="form-group">
	                       			<textarea class="form-control" id="onev_rem" name="onev_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOnev_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether Investment Policyhasbeen drawn in accordance with the investment guidelines approved by the PFRDA and has been approved by the Board of Directors (BOD) of the PFM?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIay" name='twoia' value="Yes"  <%=(inputQuarterlyInterval.getTwoia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIan" name='twoia' value="No"  <%=(inputQuarterlyInterval.getTwoia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIan">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIana" name='twoia' value="NA"  <%=(inputQuarterlyInterval.getTwoia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoia() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twoia_rem_intermediary" name="twoia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twoia_rem" name="twoia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether Investment Policy is being reviewed periodically (minimum half yearly basis) by the PFM?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIby" name='twoib' value="Yes"  <%=(inputQuarterlyInterval.getTwoib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIbn" name='twoib' value="No"  <%=(inputQuarterlyInterval.getTwoib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIbn">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIbna" name='twoib' value="NA"  <%=(inputQuarterlyInterval.getTwoib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIbna">&nbsp; NA</label>
                              </div>
                              <label class="error" for="twoib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoib() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
                       			<div class="form-group">
                       				<textarea class="form-control" id="twoib_rem_intermediary" name="twoib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoib_remark():"" %></textarea>
                       			</div>
                  		 	</div>
                            <div class="col-md-3">
                       			<div class="form-group">
                       				<textarea class="form-control" id="twoib_rem" name="twoib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoib_rem():"" %></textarea>
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
                           <div class="col-md-3 pt-3">
                              <div class="form-group">
                                 <p>c) Whether the PFM has submitted details of the Investment Policy reviewed by its board to the NPS Trust within 30days of such review.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIcy" name='twoic' value="Yes"  <%=(inputQuarterlyInterval.getTwoic()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIcn" name='twoic' value="No"  <%=(inputQuarterlyInterval.getTwoic()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIcn">&nbsp; No</label> 
                              </div>
                                 <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIcna" name='twoic' value="NA"  <%=(inputQuarterlyInterval.getTwoic()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoic"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoic() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twoic_rem_intermediary" name="twoic_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoic_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twoic_rem" name="twoic_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoic_rem():"" %></textarea>
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
	                           <div class="col-md-3">
	                              <div class="form-group">
	                                 <p>i) Prudential norms, Income recognition, Asset classification and Provisioning</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIoney" name='twoiione' value="Yes"  <%=(inputQuarterlyInterval.getTwoiione()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIoney">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIonen" name='twoiione' value="No"  <%=(inputQuarterlyInterval.getTwoiione()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIonen">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIonena" name='twoiione' value="NA"  <%=(inputQuarterlyInterval.getTwoiione()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIonena">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoiione"></label>
                              <% } else {%>
	                              <div class="form-check form-check-inline">
	                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoiione() %></label>
                              	</div>
                              	  <% } %>
	                           </div>
	                           <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiione_rem_intermediary" name="twoiione_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiione_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiione_rem" name="twoiione_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiione_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-3 pt-3">
	                              <div class="form-group">
	                                 <p>ii) Sector limits as stipulated in the Investment guidelines </p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIItwoy" name='twoiitwo' value="Yes"  <%=(inputQuarterlyInterval.getTwoiitwo()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIItwoy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIItwon" name='twoiitwo' value="No"  <%=(inputQuarterlyInterval.getTwoiitwo()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIItwon">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIItwona" name='twoiitwo' value="NA"  <%=(inputQuarterlyInterval.getTwoiitwo()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIItwona">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoiitwo"></label>
                              <% } else {%>
	                              <div class="form-check form-check-inline">
	                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoiitwo() %></label>
                              	</div>
                              	  <% } %>
	                            </div>
	                             <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiitwo_rem_intermediary" name="twoiitwo_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiitwo_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiitwo_rem" name="twoiitwo_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiitwo_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-3 pt-3">
	                              <div class="form-group">
	                                 <p>iii) Sponsor and Non-Sponsor Group limits as stipulated in the Investment guidelines</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIthreey" name='twoiithree' value="Yes"  <%=(inputQuarterlyInterval.getTwoiithree()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIthreey">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIthreen" name='twoiithree' value="No"  <%=(inputQuarterlyInterval.getTwoiithree()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIthreen">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIthreena" name='twoiithree' value="NA"  <%=(inputQuarterlyInterval.getTwoiithree()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIthreena">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoiithree"></label>
                              <% } else {%>
	                              <div class="form-check form-check-inline">
	                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoiithree() %></label>
                              	</div>
                              	  <% } %>
	                           </div>
	                           <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiithree_rem_intermediary" name="twoiithree_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiithree_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiithree_rem" name="twoiithree_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiithree_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-3 pt-3">
	                              <div class="form-group">
	                                 <p>iv) Liquidity and Asset/liability management </p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIfoury" name='twoiifour' value="Yes"  <%=(inputQuarterlyInterval.getTwoiifour()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIfoury">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIfourn" name='twoiifour' value="No"  <%=(inputQuarterlyInterval.getTwoiifour()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIfourn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIfourna" name='twoiifour' value="NA"  <%=(inputQuarterlyInterval.getTwoiifour()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIfourna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoiifour"></label>
                              <% } else {%>
	                              <div class="form-check form-check-inline">
	                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoiifour() %></label>
                                 </div>
                                   <% } %>
	                           </div>
	                           <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiifour_rem_intermediary" name="twoiifour_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiifour_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiifour_rem" name="twoiifour_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiifour_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-3 pt-3">
	                              <div class="form-group">
	                                 <p>v) Stop Loss Limits</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIfivey" name='twoiifive' value="Yes"  <%=(inputQuarterlyInterval.getTwoiifive()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIfivey">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIfiven" name='twoiifive' value="No"  <%=(inputQuarterlyInterval.getTwoiifive()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIfiven">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIfivena" name='twoiifive' value="NA"  <%=(inputQuarterlyInterval.getTwoiifive()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIfivena">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoiifive"></label>
                              <% } else {%>
	                              <div class="form-check form-check-inline">
	                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoiifive() %></label>
                              	</div>
                              	  <% } %>
	                           </div>
	                            <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiifive_rem_intermediary" name="twoiifive_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiifive_remark():"" %></textarea>
			                        </div>
			                    </div>
			                    <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiifive_rem" name="twoiifive_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiifive_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-3 pt-3">
	                              <div class="form-group">
	                                 <p>vi)Broker limit</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIsixy" name='twoiisix' value="Yes"  <%=(inputQuarterlyInterval.getTwoiisix()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIsixy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIsixn" name='twoiisix' value="No"  <%=(inputQuarterlyInterval.getTwoiisix()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIsixn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIsixna" name='twoiisix' value="NA"  <%=(inputQuarterlyInterval.getTwoiisix()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIsixna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoiisix"></label>
                              <% } else {%>
	                              <div class="form-check form-check-inline">
	                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoiisix() %></label>
                              	</div>
                              	  <% } %>
	                           </div>
	                           <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiisix_rem_intermediary" name="twoiisix_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiisix_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiisix_rem" name="twoiisix_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiisix_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-3 pt-3">
	                              <div class="form-group">
	                                 <p>vii) Investment audits</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIseveny" name='twoiiseven' value="Yes"  <%=(inputQuarterlyInterval.getTwoiiseven()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIseveny">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIsevenn" name='twoiiseven' value="No"  <%=(inputQuarterlyInterval.getTwoiiseven()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIsevenn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="TwoIIsevenna" name='twoiiseven' value="NA"  <%=(inputQuarterlyInterval.getTwoiiseven()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="TwoIIsevenna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twoiiseven"></label>
                              <% } else {%>
	                              <div class="form-check form-check-inline">
	                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwoiiseven() %></label>
                              	</div>
                              	  <% } %>
	                           </div>
	                           <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiiseven_rem_intermediary" name="twoiiseven_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiiseven_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-3">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiiseven_rem" name="twoiiseven_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiiseven_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether Risk Management Policy hasbeen drawn in accordance with the guidelines approved by the PFRDA and has been approved by the Board of Directors?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeAy" name='threea' value="Yes"  <%=(inputQuarterlyInterval.getThreea()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeAn" name='threea' value="No"  <%=(inputQuarterlyInterval.getThreea()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeAn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeAna" name='threea' value="NA"  <%=(inputQuarterlyInterval.getThreea()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threea"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getThreea() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threea_rem_intermediary" name="threea_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreea_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threea_rem" name="threea_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreea_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether Risk Management Policy is being reviewed periodically (minimum half yearly basis) by the PFM?</p>
                              </div>
                           </div>
                          <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeBy" name='threeb' value="Yes"  <%=(inputQuarterlyInterval.getThreeb()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeBy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeBn" name='threeb' value="No"  <%=(inputQuarterlyInterval.getThreeb()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeBn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeBna" name='threeb' value="NA"  <%=(inputQuarterlyInterval.getThreeb()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeBna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threeb"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getThreeb() %></label>
                              </div>
                                <% } %>
                             </div>
                             <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeb_rem_intermediary" name="threeb_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeb_rem" name="threeb_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreeb_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row pb-3"></div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>c) Whether the PFM has submitted details of the Risk Management Policy reviewed by its board to the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCy" name='threec' value="Yes"  <%=(inputQuarterlyInterval.getThreec()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCn" name='threec' value="No"  <%=(inputQuarterlyInterval.getThreec()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCna" name='threec' value="NA"  <%=(inputQuarterlyInterval.getThreec()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threec"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getThreec() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
                        		<div class="form-group">
                        			<textarea class="form-control" id="threec_rem_intermediary" name="threec_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreec_remark():"" %></textarea>
                       	 		</div>
                    		</div>
                            <div class="col-md-3">
                        		<div class="form-group">
                        			<textarea class="form-control" id="threec_rem" name="threec_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreec_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>1. Risk Management functions</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 &nbsp;
                                     <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIy" name='threeci' value="Yes"  <%=(inputQuarterlyInterval.getThreeci()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIn" name='threeci' value="No"  <%=(inputQuarterlyInterval.getThreeci()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIna" name='threeci' value="NA"  <%=(inputQuarterlyInterval.getThreeci()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threeci"></label>
                              <% } else {%>
                                 <div class="form-check form-check-inline">
                                    <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getThreeci() %></label>
                              	</div>
                              	  <% } %>
                              </div>
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeci_rem_intermediary" name="threeci_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeci_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeci_rem" name="threeci_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreeci_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>2. Disaster recovery and business continuity plans</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 &nbsp;
                                  <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIIy" name='threecii' value="Yes"  <%=(inputQuarterlyInterval.getThreecii()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIIn" name='threecii' value="No"  <%=(inputQuarterlyInterval.getThreecii()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIIn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIIna" name='threecii' value="NA"  <%=(inputQuarterlyInterval.getThreecii()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threecii"></label>
                              <% } else {%>
                                 <div class="form-check form-check-inline">
                                    <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getThreecii() %></label>
                              	</div>
                              	  <% } %>
                              </div>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threecii_rem_intermediary" name="threecii_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreecii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threecii_rem" name="threecii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreecii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>3. Insurance cover against risks</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 &nbsp;
                                 <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIIIy" name='threeciii' value="Yes"  <%=(inputQuarterlyInterval.getThreeciii()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIIIn" name='threeciii' value="No"  <%=(inputQuarterlyInterval.getThreeciii()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIIIn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIIIna" name='threeciii' value="NA"  <%=(inputQuarterlyInterval.getThreeciii()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threeciii"></label>
                              <% } else {%>
                                 <div class="form-check form-check-inline">
                                    <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getThreeciii() %></label>
                              	</div>
                              	  <% } %>
                              </div>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeciii_rem_intermediary" name="threeciii_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeciii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeciii_rem" name="threeciii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreeciii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>4. Ensuring risk adjusted returns to subscribers consistent with the protection, safety and liquidity of such funds.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 &nbsp;
                                  <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIvy" name='threeciv' value="Yes"  <%=(inputQuarterlyInterval.getThreeciv()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIvy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIvn" name='threeciv' value="No"  <%=(inputQuarterlyInterval.getThreeciv()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIvn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ThreeCIvna" name='threeciv' value="NA"  <%=(inputQuarterlyInterval.getThreeciv()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ThreeCIvna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="threeciv"></label>
                              <% } else {%>
                                 <div class="form-check form-check-inline">
                                    <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getThreeciv() %></label>
                              	</div>
                              	  <% } %>
                              </div>
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeciv_rem_intermediary" name="threeciv_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeciv_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeciv_rem" name="threeciv_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreeciv_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether the PFM has ensured that all investments are made as per the investment guidelines?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourAy" name='foura' value="Yes"  <%=(inputQuarterlyInterval.getFoura()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FourAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourAn" name='foura' value="No"  <%=(inputQuarterlyInterval.getFoura()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FourAn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourAna" name='foura' value="NA"  <%=(inputQuarterlyInterval.getFoura()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FourAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="foura"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFoura() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="foura_rem_intermediary" name="foura_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFoura_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="foura_rem" name="foura_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFoura_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) In case of a deviation (downgrade to a rating not permitted under the regulations for corporate bonds or any other non-compliance in any scheme/asset class post investment), whether the PFM has recorded an internal note justifying its decision to hold such securities where deviation has occurred. </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourBy" name='fourb' value="Yes"  <%=(inputQuarterlyInterval.getFourb()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FourBy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourBn" name='fourb' value="No"  <%=(inputQuarterlyInterval.getFourb()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FourBn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourBna" name='fourb' value="NA"  <%=(inputQuarterlyInterval.getFourb()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FourBna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fourb"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFourb() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fourb_rem_intermediary" name="fourb_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFourb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fourb_rem" name="fourb_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFourb_rem():"" %></textarea>
		                        </div>
		                    </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>c) Whether all such investment deviations are being reported to the Investment Committee and Board of the PFM for their approval to continue to remain invested in these securities.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourCy" name='fourc' value="Yes"  <%=(inputQuarterlyInterval.getFourc()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FourCy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourCn" name='fourc' value="No"  <%=(inputQuarterlyInterval.getFourc()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FourCn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FourCna" name='fourc' value="NA"  <%=(inputQuarterlyInterval.getFourc()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FourCna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fourc"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFourc() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fourc_rem_intermediary" name="fourc_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFourc_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fourc_rem" name="fourc_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFourc_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether the PFM is engaged in any other business activity except those relating to pension schemes or funds, regulated by the PFRDA.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIy" name='fivei' value="Yes"  <%=(inputQuarterlyInterval.getFivei()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIn" name='fivei' value="No"  <%=(inputQuarterlyInterval.getFivei()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIna" name='fivei' value="NA"  <%=(inputQuarterlyInterval.getFivei()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivei"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFivei() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivei_rem_intermediary" name="fivei_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivei_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivei_rem" name="fivei_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivei_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether the PFM has ensured that adequate disclosures are made to the PFRDA, the NPS Trust or subscriber in a comprehensible and timely manner.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIIy" name='fiveii' value="Yes"  <%=(inputQuarterlyInterval.getFiveii()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIIn" name='fiveii' value="No"  <%=(inputQuarterlyInterval.getFiveii()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIIn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIIna" name='fiveii' value="NA"  <%=(inputQuarterlyInterval.getFiveii()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fiveii"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFiveii() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveii_rem_intermediary" name="fiveii_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveii_rem" name="fiveii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFiveii_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether the PFM has ensured that there has not been any misrepresentation or submission of any misleading information to the PFRDA, NPS Trust or subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                           <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIIIy" name='fiveiii' value="Yes"  <%=(inputQuarterlyInterval.getFiveiii()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIIIn" name='fiveiii' value="No"  <%=(inputQuarterlyInterval.getFiveiii()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIIIn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIIIna" name='fiveiii' value="NA"  <%=(inputQuarterlyInterval.getFiveiii()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fiveiii"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFiveiii() %></label>
                              </div>
                                <% } %>
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveiii_rem_intermediary" name="fiveiii_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveiii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveiii_rem" name="fiveiii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFiveiii_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether the PFM has divulged to anybody, either orally or in writing, directly or indirectly any confidential information about the PFRDA, the NPS Trust or subscribers, which has come to its knowledge, without taking prior permission of the PFRDA,the NPS Trust except where such disclosures are required to be made in compliance with any law for the time being in force.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIvy" name='fiveiv' value="Yes"  <%=(inputQuarterlyInterval.getFiveiv()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIvy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIvn" name='fiveiv' value="No"  <%=(inputQuarterlyInterval.getFiveiv()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIvn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveIvna" name='fiveiv' value="NA"  <%=(inputQuarterlyInterval.getFiveiv()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveIvna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fiveiv"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFiveiv() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveiv_rem_intermediary" name="fiveiv_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveiv_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveiv_rem" name="fiveiv_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFiveiv_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether the PFM has made adequate disclosures of potential areas of conflict of duties or interest to the PFRDA, the NPS Trust or subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVay" name='fiveva' value="Yes"  <%=(inputQuarterlyInterval.getFiveva()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVan" name='fiveva' value="No"  <%=(inputQuarterlyInterval.getFiveva()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVan">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVana" name='fiveva' value="NA"  <%=(inputQuarterlyInterval.getFiveva()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fiveva"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFiveva() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveva_rem_intermediary" name="fiveva_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveva_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveva_rem" name="fiveva_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFiveva_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether the PFM hasestablished a mechanism to resolve any conflict of interest situation in an equitable manner, which may arise in the conduct of business.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVby" name='fivevb' value="Yes"  <%=(inputQuarterlyInterval.getFivevb()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVbn" name='fivevb' value="No"  <%=(inputQuarterlyInterval.getFivevb()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVbn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVbna" name='fivevb' value="NA"  <%=(inputQuarterlyInterval.getFivevb()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivevb"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFivevb() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevb_rem_intermediary" name="fivevb_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevb_rem" name="fivevb_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivevb_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>c) Whether the PFM is satisfied that there has been no instances of self-dealing or front running or insider trading by any of the directors and Key personnel through their accounts or through their family members, relatives or friends. </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVcy" name='fivevc' value="Yes"  <%=(inputQuarterlyInterval.getFivevc()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVcn" name='fivevc' value="No"  <%=(inputQuarterlyInterval.getFivevc()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVcn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVcna" name='fivevc' value="NA"  <%=(inputQuarterlyInterval.getFivevc()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivevc"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFivevc() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevc_rem_intermediary" name="fivevc_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevc_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevc_rem" name="fivevc_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivevc_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether PFM has promptly informed the PFRDA and the NPS Trust, if there is any change in the registration status or any penal action taken by any Authority or any material change in financials which may adversely affect the interest of the subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVIay" name='fivevia' value="Yes"  <%=(inputQuarterlyInterval.getFivevia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVIan" name='fivevia' value="No"  <%=(inputQuarterlyInterval.getFivevia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVIan">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVIana" name='fivevia' value="NA"  <%=(inputQuarterlyInterval.getFivevia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivevia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFivevia() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevia_rem_intermediary" name="fivevia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevia_rem" name="fivevia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivevia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether the PFM has promptly informed the PFRDA and the NPS Trust about any action, legal proceedings initiated against it in respect of any material breach or non-compliance by it of any law, rules, regulations and directions of the PFRDA or any other regulatory body.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                                <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVIby" name='fivevib' value="Yes"  <%=(inputQuarterlyInterval.getFivevib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVIbn" name='fivevib' value="No"  <%=(inputQuarterlyInterval.getFivevib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVIbn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="FiveVIbna" name='fivevib' value="NA"  <%=(inputQuarterlyInterval.getFivevib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="FiveVIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="fivevib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getFivevib() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevib_rem_intermediary" name="fivevib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevib_rem" name="fivevib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivevib_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether appointment of the Internal Auditor and scope of internal audit is as per the Regulations/Guidance note issued by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIy" name='sixi' value="Yes"  <%=(inputQuarterlyInterval.getSixi()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIn" name='sixi' value="No"  <%=(inputQuarterlyInterval.getSixi()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIna" name='sixi' value="NA"  <%=(inputQuarterlyInterval.getSixi()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sixi"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSixi() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixi_rem_intermediary" name="sixi_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixi_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixi_rem" name="sixi_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSixi_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether the PFM has produced to the Auditors such books, accounts, records and other documents in its custody or control and furnish such statement and information relating to its activities entrusted to its by the PFRDA, as it or he may require, within such reasonable time may be specified.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIay" name='sixiia' value="Yes"  <%=(inputQuarterlyInterval.getSixiia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIan" name='sixiia' value="No"  <%=(inputQuarterlyInterval.getSixiia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIan">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIana" name='sixiia' value="NA"  <%=(inputQuarterlyInterval.getSixiia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sixiia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSixiia() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiia_rem_intermediary" name="sixiia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiia_rem" name="sixiia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSixiia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether the PFM has allowed Auditor's reasonable access to the premises occupied by it and also extend reasonable facility for examining any books, records, documents and computer data in the possession of the PFM.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                                <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIby" name='sixiib' value="Yes"  <%=(inputQuarterlyInterval.getSixiib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIbn" name='sixiib' value="No"  <%=(inputQuarterlyInterval.getSixiib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIbn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIbna" name='sixiib' value="NA"  <%=(inputQuarterlyInterval.getSixiib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sixiib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSixiib() %></label>
                              </div>
                                <% } %>
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiib_rem_intermediary" name="sixiib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiib_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiib_rem" name="sixiib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSixiib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>c)Whether audit observations till previous quarter have been closed and suggestions of PFRDA/NPS Trust thereto have been complied with?</p>
                               </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIcy" name='sixiic' value="Yes"  <%=(inputQuarterlyInterval.getSixiic()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIcn" name='sixiic' value="No"  <%=(inputQuarterlyInterval.getSixiic()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIcn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SixIIcna" name='sixiic' value="NA"  <%=(inputQuarterlyInterval.getSixiic()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SixIIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sixiic"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSixiic() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiic_rem_intermediary" name="sixiic_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiic_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiic_rem" name="sixiic_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSixiic_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether any transactions or engagement have been carried out by the PFM with a related party except investments of National Pension SystemTrust funds?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                          <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIay" name='sevenia' value="Yes"  <%=(inputQuarterlyInterval.getSevenia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIan" name='sevenia' value="No"  <%=(inputQuarterlyInterval.getSevenia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIan">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIana" name='sevenia' value="NA"  <%=(inputQuarterlyInterval.getSevenia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSevenia() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenia_rem_intermediary" name="sevenia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenia_rem" name="sevenia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether prior permission of the NPS Trust was taken before entering into such engagement/transaction?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIby" name='sevenib' value="Yes"  <%=(inputQuarterlyInterval.getSevenib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIbn" name='sevenib' value="No"  <%=(inputQuarterlyInterval.getSevenib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIbn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIbna" name='sevenib' value="NA"  <%=(inputQuarterlyInterval.getSevenib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSevenib() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenib_rem_intermediary" name="sevenib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenib_rem" name="sevenib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>c)  Whether such engagement/transactions have been disclosed to the NPS Trust in its periodic reports.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                           <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIcy" name='sevenic' value="Yes"  <%=(inputQuarterlyInterval.getSevenic()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIcy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIcn" name='sevenic' value="No"  <%=(inputQuarterlyInterval.getSevenic()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIcn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIcna" name='sevenic' value="NA"  <%=(inputQuarterlyInterval.getSevenic()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIcna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenic"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSevenic() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenic_rem_intermediary" name="sevenic_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenic_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenic_rem" name="sevenic_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenic_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>d) Whether such related party engagements / transactions aredone at fair market price?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIdy" name='sevenid' value="Yes"  <%=(inputQuarterlyInterval.getSevenid()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIdy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIdn" name='sevenid' value="No"  <%=(inputQuarterlyInterval.getSevenid()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIdn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIdna" name='sevenid' value="NA"  <%=(inputQuarterlyInterval.getSevenid()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIdna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenid"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSevenid() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenid_rem_intermediary" name="sevenid_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenid_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenid_rem" name="sevenid_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenid_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>e) Whether such transaction is recurring in nature?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIey" name='sevenie' value="Yes"  <%=(inputQuarterlyInterval.getSevenie()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIey">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIen" name='sevenie' value="No"  <%=(inputQuarterlyInterval.getSevenie()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIen">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="SevenIena" name='sevenie' value="NA"  <%=(inputQuarterlyInterval.getSevenie()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="SevenIena">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="sevenie"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getSevenie() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenie_rem_intermediary" name="sevenie_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenie_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenie_rem" name="sevenie_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenie_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a)Whether the PFM has complied with circular no. PFRDA/2017/30/PF/4 dated09.10.2017 onguidelines on outsourcing of activities by the Pension Fund? </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIay" name='eightia' value="Yes"  <%=(inputQuarterlyInterval.getEightia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIan" name='eightia' value="No"  <%=(inputQuarterlyInterval.getEightia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIan">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIana" name='eightia' value="NA"  <%=(inputQuarterlyInterval.getEightia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightia() %></label>
                              </div>
                                <% } %>
                            </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightia_rem_intermediary" name="eightia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightia_rem" name="eightia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether the PFM has complied with the reporting requirements of the circular no. PFRDA/2017/30/PF/4 dated 09.10.2017.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIby" name='eightib' value="Yes"  <%=(inputQuarterlyInterval.getEightib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIbn" name='eightib' value="No"  <%=(inputQuarterlyInterval.getEightib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIbn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIbna" name='eightib' value="NA"  <%=(inputQuarterlyInterval.getEightib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightib() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightib_rem_intermediary" name="eightib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightib_rem" name="eightib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightib_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether all investments are held in the name of NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                          <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIIy" name='eightii' value="Yes"  <%=(inputQuarterlyInterval.getEightii()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIIn" name='eightii' value="No"  <%=(inputQuarterlyInterval.getEightii()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIIn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIIna" name='eightii' value="NA"  <%=(inputQuarterlyInterval.getEightii()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightii"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightii() %></label>
                              </div>
                                <% } %>   
                           </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightii_rem_intermediary" name="eightii_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightii_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    
		                    <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightii_rem" name="eightii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightii_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether PFM has pledged or hypothecated or lent any scheme assets which would amount to leverage on schemeÃ¢ÂÂs portfolio?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIIIy" name='eightiii' value="Yes"   <%=(inputQuarterlyInterval.getEightiii()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIIIn" name='eightiii' value="No"  <%=(inputQuarterlyInterval.getEightiii()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIIIn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIIIna" name='eightiii' value="NA"  <%=(inputQuarterlyInterval.getEightiii()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightiii"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightiii() %></label>
                              </div>
                                <% } %>
                            </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightiii_rem_intermediary" name="eightiii_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightiii_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightiii_rem" name="eightiii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightiii_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether all charges/fees debited to the schemes aredeterminedas stipulated by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                                 <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIvy" name='eightiv' value="Yes"  <%=(inputQuarterlyInterval.getEightiv()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIvy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIvn" name='eightiv' value="No"  <%=(inputQuarterlyInterval.getEightiv()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIvn">&nbsp; No</label> 
                              </div>
                                <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIvna" name='eightiv' value="NA"  <%=(inputQuarterlyInterval.getEightiv()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIvna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightiv"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightiv() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightiv_rem_intermediary" name="eightiv_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightiv_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightiv_rem" name="eightiv_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightiv_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether all interest,dividendsor any such accrual income and proceeds of redemption/sale were collected on due dates and promptly credited to the scheme accounts?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVy" name='eightv' value="Yes"  <%=(inputQuarterlyInterval.getEightv()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVn" name='eightv' value="No"  <%=(inputQuarterlyInterval.getEightv()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVna" name='eightv' value="NA"  <%=(inputQuarterlyInterval.getEightv()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightv"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightv() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightv_rem_intermediary" name="eightv_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightv_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightv_rem" name="eightv_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightv_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether the PFM has taken adequate and necessary steps to ensure that the data and records pertaining to its activities are maintained and are intact.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIay" name='eightvia' value="Yes"  <%=(inputQuarterlyInterval.getEightvia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIan" name='eightvia' value="No"  <%=(inputQuarterlyInterval.getEightvia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIan">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIana" name='eightvia' value="NA"  <%=(inputQuarterlyInterval.getEightvia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightvia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightvia() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightvia_rem_intermediary" name="eightvia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightvia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightvia_rem" name="eightvia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightvia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether the PFM has ensured that for electronic records and data, up-to-date backup is always available with it.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                                  <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIby" name='eightvib' value="Yes"  <%=(inputQuarterlyInterval.getEightvib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIbn" name='eightvib' value="No"  <%=(inputQuarterlyInterval.getEightvib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIbn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIbna" name='eightvib' value="NA"  <%=(inputQuarterlyInterval.getEightvib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightvib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightvib() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightvib_rem_intermediary" name="eightvib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightvib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightvib_rem" name="eightvib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightvib_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether the PFM has maintained adequate infrastructure facilities to be able to discharge its services to the satisfaction of the PFRDA, the NPS Trust.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIay" name='eightviia' value="Yes"  <%=(inputQuarterlyInterval.getEightviia()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIay">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIan" name='eightviia' value="No"  <%=(inputQuarterlyInterval.getEightviia()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIan">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIana" name='eightviia' value="NA"  <%=(inputQuarterlyInterval.getEightviia()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIana">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightviia"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightviia() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviia_rem_intermediary" name="eightviia_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviia_rem" name="eightviia_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightviia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b) Whether the operating procedures and systems of the PFM are well documented and backed by operation manuals.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIby" name='eightviib' value="Yes"  <%=(inputQuarterlyInterval.getEightviib()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIby">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIbn" name='eightviib' value="No"  <%=(inputQuarterlyInterval.getEightviib()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIbn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIbna" name='eightviib' value="NA"  <%=(inputQuarterlyInterval.getEightviib()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIbna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightviib"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightviib() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviib_rem_intermediary" name="eightviib_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviib_rem" name="eightviib_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightviib_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether the PFM has informed the entities in which investment of NPS funds have been made that interest received on the said investment is not liable for deduction of tax at source under the Income Tax Act, 1961.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                                    <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIIy" name='eightviii' value="Yes"  <%=(inputQuarterlyInterval.getEightviii()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIIy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIIn" name='eightviii' value="No"  <%=(inputQuarterlyInterval.getEightviii()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIIn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightVIIIna" name='eightviii' value="NA"  <%=(inputQuarterlyInterval.getEightviii()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightVIIIna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightviii"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightviii() %></label>
                              </div>
                              <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviii_rem_intermediary" name="eightviii_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviii_rem" name="eightviii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightviii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        
                        
                        
                        
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ix)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                                <p>In case any Income tax has been deducted on the investment of NPS funds made by PFM, whether PFM has collected refund of such tax deducted within the same financial year.</p>
                                <p>In case any Income Tax has been deducted on the investment of NPS funds and PFM has not obtained the refund of such tax within the same financial year at its own cost and expense, the PFM fund shall reimburse the NPS Trust, of the said amounts, being deducted as tax, upon being notified by the NPS Trust.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIXy" name="eightix" value="Yes"  <%=(inputQuarterlyInterval.getEightix()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIXy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIXn" name="eightix" value="No"  <%=(inputQuarterlyInterval.getEightix()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIXn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="EightIXna" name="eightix" value="NA"  <%=(inputQuarterlyInterval.getEightix()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="EightIXna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightix"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightix() %></label>
                              </div>
                              <% } %>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightix_rem_intermediary" name="eightix_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightix_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviii_rem" name="eightix_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightix_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(x)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                                 <p>Whether proper amount of tax has been deducted and deposited on payment of investment management fees and the custodian fees by the PFM on behalf of NPS Trust and within the prescribed timelines.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Eightxy" name="eightx" value="Yes"  <%=(inputQuarterlyInterval.getEightx()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="Eightxy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Eightxn" name="eightx" value="No"  <%=(inputQuarterlyInterval.getEightx()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="Eightxn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Eightxna" name="eightx" value="NA"  <%=(inputQuarterlyInterval.getEightx()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="Eightxna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="eightx"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getEightx() %></label>
                              </div>
                              <% } %>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightx_rem_intermediary" name="eightx_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightx_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightx_rem" name="eightx_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightx_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>* Whether Brokers empanelment is done in accordance to the guidelines issued by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                                      <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="NineAy" name='ninea' value="Yes"  <%=(inputQuarterlyInterval.getNinea()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="NineAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="NineAn" name='ninea' value="No"  <%=(inputQuarterlyInterval.getNinea()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="NineAn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="NineAna" name='ninea' value="NA"  <%=(inputQuarterlyInterval.getNinea()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="NineAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="ninea"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getNinea() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="ninea_rem_intermediary" name="ninea_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getNinea_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="ninea_rem" name="ninea_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getNinea_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>* Whether prescribed limit of percentage of transactions through any single broker is complied with?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="NineBy" name='nineb' value="Yes"  <%=(inputQuarterlyInterval.getNineb()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="NineBy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="NineBn" name='nineb' value="No"  <%=(inputQuarterlyInterval.getNineb()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="NineBn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="NineBna" name='nineb' value="NA"  <%=(inputQuarterlyInterval.getNineb()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="NineBna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="nineb"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getNineb() %></label>
                              </div>
                               <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="nineb_rem_intermediary" name="nineb_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getNineb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="nineb_rem" name="nineb_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getNineb_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether all such Inter-Scheme transfers are in conformity with the investment objective of the scheme to which such transfer has been made?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Teny" name='ten' value="Yes"  <%=(inputQuarterlyInterval.getTen()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="Teny">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Tenn" name='ten' value="No"  <%=(inputQuarterlyInterval.getTen()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="Tenn">&nbsp; No</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Tenna" name='ten' value="NA"  <%=(inputQuarterlyInterval.getTen()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="Tenna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="ten"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTen() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="ten_rem_intermediary" name="ten_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTen_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="ten_rem" name="ten_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTen_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>a) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                                  <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ElevenAy" name='elevena' value="Yes"  <%=(inputQuarterlyInterval.getElevena()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ElevenAy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ElevenAn" name='elevena' value="No"  <%=(inputQuarterlyInterval.getElevena()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ElevenAn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ElevenAna" name='elevena' value="NA"  <%=(inputQuarterlyInterval.getElevena()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ElevenAna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="elevena"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getElevena() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="elevena_rem_intermediary" name="elevena_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getElevena_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="elevena_rem" name="elevena_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getElevena_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>b)Whether, quarterly voting report has been submitted to the NPS Trust in compliance to Circular PFRDA/2017/17/PF/1 dated 20.04.2017?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                            <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ElevenBy" name='elevenb' value="Yes"  <%=(inputQuarterlyInterval.getElevenb()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="ElevenBy">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ElevenBn" name='elevenb' value="No"  <%=(inputQuarterlyInterval.getElevenb()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="ElevenBn">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="ElevenBna" name='elevenb' value="NA"  <%=(inputQuarterlyInterval.getElevenb()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="ElevenBna">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="elevenb"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getElevenb() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="elevenb_rem_intermediary" name="elevenb_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getElevenb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="elevenb_rem" name="elevenb_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getElevenb_rem():"" %></textarea>
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
                           <div class="col-md-3">
                              <div class="form-group">
                                 <p>Whether quarterly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the quarter.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <% if (isNonNPSUser) { %>
                             <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Twelvey" name='twelve' value="Yes"  <%=(inputQuarterlyInterval.getTwelve()).equals("Yes")?"checked":"" %>>
                                 <label class="form-check-label" for="Twelvey">&nbsp; Yes</label> 
                              </div>
                              <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Twelven" name='twelve' value="No"  <%=(inputQuarterlyInterval.getTwelve()).equals("No")?"checked":"" %>>
                                 <label class="form-check-label" for="Twelven">&nbsp; No</label> 
                              </div>
                               <div class="form-check form-check-inline">
                                 <input class="" type="radio" id="Twelvena" name='twelve' value="NA"  <%=(inputQuarterlyInterval.getTwelve()).equals("NA")?"checked":"" %>>
                                 <label class="form-check-label" for="Twelvena">&nbsp; NA</label> 
                              </div>
                              <label class="error" for="twelve"></label>
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getTwelve() %></label>
                              </div>
                                <% } %>
                           </div>
                           <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twelve_rem_intermediary" name="twelve_rem_intermediary" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwelve_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-3">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twelve_rem" name="twelve_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwelve_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        
                       <%--  ------------------------------
                        
                        
                        <div class="text-end">
                        <button type="button" class="common-btn d-inline-block text-capitalize border-0 mb-3" id="addRow">Add Row</button>
                     </div>
                     <div class="table-cont">
                        <table id="myTable_1" class="table css-serial w-100 table-responsive">
                           <thead>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Board Discription&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                                 <th>Auditor Observation&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                                 <th>Risk Rating&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                                 <th>Management Response&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                                 <th>NPS Comment&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                                 <th data-orderable="false">Remove&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                              </tr>
                           </thead>
                           <tbody>
                              <% int index=0;
                              for(PFM_executive_summary executive_summary: executive_summaries){ 
                              index=index+1;
                              %>
                              <tr>
                                 <td><input type="hidden" class="rowIndex" id="rowIndex" value="1" name="<portlet:namespace />rowIndex[]"/></td>
                                 <td>
                                    <textarea type="text" class="boardDiscription" id="boardDiscription"  name="<portlet:namespace />boardDiscription[]"><%=executive_summary.getBoardDescription() %></textarea>
                                 </td>
                                 <td>
                                    <textarea type="text" class="auditorObservation" id="auditorObservation" name="<portlet:namespace />auditorObservation[]"><%=executive_summary.getAuditorobservation() %></textarea>
                                 </td>
                                 <td>
                                    <textarea type="text" class="riskRating" id="riskRating" name="<portlet:namespace />riskRating[]"><%=executive_summary.getRiskrating() %></textarea>
                                 </td>
                                  <td>
                                    <textarea type="text" class="managementResponse" id="managementResponse"  name="<portlet:namespace />managementResponse[]"><%=executive_summary.getManagementResponse() %></textarea>
                                 </td>
                                  <td>
                                    <textarea type="text" class="npstComment" id="npstComment" name="<portlet:namespace />npstComment[]"><%=executive_summary.getNpstComment() %></textarea>
                                 </td>
                                 <td class="text-center">
                                 <%if(index!=1){ %>
                                 <button class="btn btn-primary btn-sm remove" id="removeRow" type="button" style="padding:5px 10px"><i class="fas fa-trash-alt"></i>
                                 </button>
                                 <%} %>
                                 </td>
                              </tr>
                              <%} %>
                           </tbody>
                        </table>
                        <br><br>
                     </div>
 --%>
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
                           <div class="col-md-11">
                              <div class="form-group">
                                 <p>2.This Compliance Certificate(s)n shall be put up to the Board at its ensuing Board Meeting and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.
                                  </p>
                              </div>
                           </div >
                         </div>
                         <div class="row">
                         	<div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-11">
                              <div class="form-group">
                                 <p>and the remarks related thereto would be forwarded to NPS Trust subsequently.</p>
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
                         <div class="row">
		                 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <label for="companies">For</label><br>
		                     	<label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getCompanies() %></label>
		                  </div>
		               </div>
		               <div class="row"></div>
                       <div class="row">
                           <div class="col-md-12">
                              <div class="form-group">
                                 <div class="row">
                                    <div class="col-md-6">
                                       <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                          <div class="form-group">
											<label>Date:</label>
											<input type="text" class="date_3" id="date_3" value="<%=inputQuarterlyInterval.getDate_3() %>" readonly="readonly"name="date_3" readonly>
										</div>
									</div>
				                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                     <div class="form-group">
                                        <label>Place:</label>
                                        	<label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getPlace() %></label>
                                     </div>
                                  </div>
                               </div>
                               <div class="col-lg-6 col-md-6 col-sm-12 col-12"">
                                  <div class="col-md-6">
                                     <div class="form-group">
                                        <label>Name</label>
                                        	<label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getCeoname() %></label>
                                     </div>
                                  </div>
                                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
				                     <label for="ceonameii">Role:</label><br>
				                     	<label class="form-check-label">&nbsp; <%= inputQuarterlyInterval.getCeonameii() %></label>
				                  </div>
			                 </div>
			              </div>
			           </div>
			        </div>
			     </div>
			     <hr/>
			     <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure 1</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty annex_i ? "javascript:void(0);" : annex_i}" ${empty annex_i ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annex_i_rem" name="annex_i_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_i_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
			     <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Other</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty board_a ? "javascript:void(0);" : board_a}" ${empty board_a ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="board_a_rem" name="board_a_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getBoard_a_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
        		
        		 <% if(!isNonNPSUser){ %>
		                 <div class="row">
				                  <!-- <div class="col-lg-6 col-md-6 col-sm-12 col-12">
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
				                  </div> -->
					                  <div class="col-lg-12 col-md-12 col-sm-12 col-12 pt-12">
					                  	<div class="form-group">
				                        	<textarea class="form-control" id="annex_comp_certificate_rem" placeholder="AM Remarks" name="annex_comp_certificate_rem"  ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_comp_certificate_rem():"" %></textarea>
				                        </div>
					                  </div>
				                 </div>
				                 <br>
				                 <%} %>
        		<%--
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Board of Directors - b)Details of meetings held in last 4 quarters</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty board_b ? "javascript:void(0);" : board_b}" ${empty board_b ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="board_b_rem" name="board_b_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getBoard_b_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Investment Committee - a)Composition details</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty investment_a ? "javascript:void(0);" : investment_a}" ${empty investment_a ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="investment_a_rem" name="investment_a_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getInvestment_a_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Investment Committee - b)Details of meetings held in last 4 quarters</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty investment_b ? "javascript:void(0);" : investment_b}" ${empty investment_b ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="investment_b_rem" name="investment_b_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getInvestment_b_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Risk Management Committee - a)Composition details</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty risk_a ? "javascript:void(0);" : risk_a}" ${empty risk_a ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="risk_a_rem" name="risk_a_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getRisk_a_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Risk Management Committee - b)Details of meetings held in last 4 quarters</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty risk_b ? "javascript:void(0);" : risk_b}" ${empty risk_b ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="risk_b_rem" name="risk_b_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getRisk_b_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure 2: Deviations to Investment guidelines</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty annex_ii ? "javascript:void(0);" : annex_ii}" ${empty annex_ii ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annex_ii_rem" name="annex_ii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_ii_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure 3: List of Directors and Key Personnel with status of submission of self-declaration w.r.t. dealing in securities for quarter ended March 31, 2022</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty annex_iii ? "javascript:void(0);" : annex_iii}" ${empty annex_iii ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annex_iii_rem" name="annex_iii_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_iii_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure 4 â Report on broker wise business</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty annex_iv ? "javascript:void(0);" : annex_iv}" ${empty annex_iv ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="annex_iv_rem" name="annex_iv_rem" <%=isNonNPSUser ? "disabled": "" %>><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_iv_rem():"" %></textarea>
                        </div>
                    </div>
        		</div>
                     --%>
                      <%-- <%if(!((isPfmSupervisor && (inputQuarterlyInterval.getIsAmRejected()==1)) || (isNonNPSUser && isDocSigned && (inputQuarterlyInterval.getIsAmRejected()!=1)))){ %> --%>
                      
                    <%--  <%if(!(isPfmSupervisor && (inputQuarterlyInterval.getIsAmRejected()==1))){ %> --%>
     				
     				<%--  <%if(!(isPfmSupervisor && isDocSigned) && (isAssignable || isSelfAsignee)){ %> --%> 
     				
     				 <%if(!(isPfmSupervisor && isDocSigned) && (isAssignable)){ %>
     				 
     				 
     				<div class="row">
                     <div class="col-md-12">
                        <div class="text-center">
                           <!-- <input type="button" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit"> -->
                            <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" >Submit</button>
                        	<a class="button" id="pop-up-trigger"  href="#success-modal"></a>
                        </div>
                     </div>
                  </div>
                  <%} %>
                  <br>
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
	#myTable_1_length, #myTable_1_filter, #myTable_1_info, #myTable_1_paginate {
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
	
	.css-serial {
	  counter-reset: serial-number;  /* Set the serial number counter to 0 */
	}
	
	.css-serial td:first-child:before {
	  counter-increment: serial-number;  /* Increment the serial number counter */
	  content: counter(serial-number);  /* Display the counter */
	}
	
	.modal {
    display: none;
	}
    

     /* .common-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    min-width: 130px;
    height: 50px;
    border-radius: 5px;
    color: #ffffff;
    background: linear-gradient(90deg, #0E62B1 0%, #3195F3 100%);
    transition: all 0.5s;
    text-transform: uppercase;} */
    
    input.error {
	border-color: red;
}

select {
	background-color: #E9F3FC;
	color: #111111;
	border-radius: 5px;
	padding: 5px 20px;
}

.common-btn {
	display: flex;
	align-items: center;
	justify-content: center;
	min-width: 130px;
	height: 50px;
	border-radius: 5px;
	color: #ffffff;
	background: linear-gradient(90deg, #0E62B1 0%, #3195F3 100%);
	transition: all 0.5s;
	text-transform: uppercase;
}

.modal-open .modal {
	display: block !important;
	overflow-y: hidden;
}

/*NEW MODAL UI PROPERTIES*/
.custom-modal-ui {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	background: rgba(0, 0, 0, 0.7);
	transition: opacity 500ms;
	visibility: hidden; 
	opacity: 0;
	z-index: 999;
}

.custom-modal-ui:target {
	visibility: visible;
	opacity: 1;
}

.modal-head-ui {
	margin: 15% auto;
	background: #fff;
	border-radius: 5px;
	width: 30%;
	position: relative;
	transition: all 2s ease-in-out;
}

.modal-head-ui .modal-close-ui {
	position: absolute;
    top: 5px;
    right: 15px;
    transition: all 200ms;
    font-size: 30px;
    font-weight: 400;
    text-decoration: none;
    color: #817d7d;
}

.modal-head-ui .modal-content-ui {
	max-height: 30%;
	overflow: auto;
	padding: 25px;
}

.modal-title-ui {
    border-bottom: 1px solid #ddd;
    padding: 15px;
    color: #fff;
}

button[disabled], button[disabled]:hover, input[type=button]:disabled {
    border: 1px solid #e5e5e5;
    background: #e5e5e5;
    color: #cec9c9 !important;     
    cursor: not-allowed;
}
  
</style>

<script type="text/javascript">
<% if(isPfmSupervisor){ %>
var SDWebServerUrl = "<%=GetterUtil.getString(PropsUtil.get("com.nps.dsc.api.domain"), "https://dsc.npstrust.net")%>";
var SDWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
var sdWebServerUrl = "<%=GetterUtil.getString(PropsUtil.get("com.nps.dsc.api.domain"), "https://dsc.npstrust.net")%>";
var sdWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
let signature = <%= signature.toString() %>;
$(function(){
	 console.log("doc load", signature);
	<% if(!isDocSigned){ %>
		$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").bind("click", false);
	<% }else{ %>
		let sigsub = signature.SelCertSubject.substring(signature.SelCertSubject.indexOf("CN="));
		$("h6.signedmsg").append(" by "+sigsub.spl)[1]); 
		$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").unbind("click");
	<% } %> 
	
	$(document).on('click', '#signFile', function(event) {
		//e.preventDefault();
		console.log("in sigin");
		SignerDigital.getSelectedCertificate()
         .then(function(data){
        	 console.log(data);
			var CertInfo = JSON.parse(data);
			$("#signature").val(CertInfo.Cert);
			var fd = new FormData();
			fd.append("certificateJSON", encodeURIComponent(data));
			fd.append("fileEntryId", "<%=fileEntryId%>");
			fd.append("sDHubConnectionIdFromBrowser", sdHubConnectionId);
			fd.append("certificateFromBrowser", encodeURIComponent(CertInfo.Cert));
			/* if(CertInfo.eMail === themeDisplay.getUserEmailAddress()){ */
				if(CertInfo.eMail.toUpperCase() === themeDisplay.getUserEmailAddress().toUpperCase()){
				$.ajax({
		            url: '/o/dsc/signPDF',  
		            type: 'POST',
		            cache: false,
		            data: fd,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	console.log(data);
		            	location.reload(); 
		            },
		            error: function() {
		           		console.log("Error Occured in ajax call");
		           		
		            },
		            complete: function(){
			        }
		    	});
			}else{
				$('#output').html('Please select valid token for signature.');
        		$('#errorExcel').modal('show');
			}
			 
			//$("#submit").click();
		})
	})
})
<%}%>
$( "#x-mark" ).click(function() {
	$("#success-modal").addClass("hide");
	
	if($("#icon").hasClass("fas fa-check-circle text-success fa-4x d-block mb-4")){
		$("#icon").removeClass("fas fa-check-circle text-success fa-4x d-block mb-4");
	}
	if($("#icon").hasClass("fas fa-times-circle text-danger  fa-4x d-block mb-4")){
		$("#icon").removeClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
	}
	
});


$(document).ready(function() {
	$(function(){
		toastr.options = {
				  "debug": false,
				  "positionClass": "toast-bottom-right",
				  "onclick": null,
				  "fadeIn": 300,
				  "fadeOut": 1000,
				  "timeOut": 9000,
				  "extendedTimeOut": 1000
		}
		
		  $('.initialNext').click(function() {
		    	console.log("initialNext clicked");
		    	$('.initial_info').addClass('d-none');
		        $('.trustee_bank').removeClass('d-none');
		    });
		
		$('.initialNext').click(function() {
	    	console.log("initialNext clicked");
	    	$('.initial_info').addClass('d-none');
	        $('.trustee_bank').removeClass('d-none');
	    });
		
		$('.initialNext').click(function() {
	    	console.log("initialNext clicked");
	    	$('.initial_info').addClass('d-none');
	        $('.trustee_bank').removeClass('d-none');
	    });
		
		
		$("#myForm").validate({
			  rules: {
				/* oneia_rem: {
			      required: true
			    },
			    formdate_rem: {
				      required: true
				    },
			    oneib_rem: {
				      required: true
				},
				oneiia_rem: {
					required: true
				},
				oneiib_rem: {
					required: true
				},
				oneiic_rem: {
					required: true
				},
				oneiid_rem: {
					required: true
				},
				oneiiia_rem: {
					required: true
				},
				oneiiib_rem: {
					required: true
				},
				oneiva_rem: {
					required: true
				},
				onev_rem: {
					required: true
				},
				twoia_rem: {
					required: true
				},
				twoib_rem: {
					required: true
				},
				twoic_rem: {
					required: true
				},
				twoiione_rem: {
					required: true
				},
				twoiitwo_rem: {
					required: true
				},
				twoiithree_rem: {
					required: true
				},
				twoiifour_rem: {
					required: true
				},
				twoiifive_rem: {
					required: true
				},
				twoiisix_rem: {
					required: true
				},
				twoiiseven_rem: {
					required: true
				},
				threea_rem: {
					required: true
				},
				threeb_rem: {
					required: true
				},
				threec_rem: {
					required: true
				},
				threeci_rem: {
					required: true
				},
				threecii_rem: {
					required: true
				},
				threeciii_rem: {
					required: true
				},
				threeciv_rem: {
					required: true
				},
				foura_rem: {
					required: true
				},
				fourb_rem: {
					required: true
				},
				fourc_rem: {
					required: true
				},
				fivei_rem: {
					required: true
				},
				fiveii_rem: {
					required: true
				},
				fiveiii_rem: {
					required: true
				},
				fiveiv_rem: {
					required: true
				},
				fiveva_rem: {
					required: true
				},
				fivevb_rem: {
					required: true
				},
				fivevc_rem: {
					required: true
				},
				fivevia_rem: {
					required: true
				},
				fivevib_rem: {
					required: true
				},
				sixi_rem: {
					required: true
				},
				sixiia_rem: {
					required: true
				},
				sixiib_rem: {
					required: true
				},
				sixiic_rem: {
					required: true
				},
				sevenia_rem: {
					required: true
				},
				sevenib_rem: {
					required: true
				},
				sevenic_rem: {
					required: true
				},
				sevenid_rem: {
					required: true
				},
				sevenie_rem: {
					required: true
				},
				eightia_rem: {
					required: true
				},
				eightib_rem: {
					required: true
				},
				eightii_rem: {
					required: true
				},
				eightiii_rem: {
					required: true
				},
				eightiv_rem: {
					required: true
				},
				eightv_rem: {
					required: true
				},
				eightvia_rem: {
					required: true
				},
				
				eightvib_rem: {
					required: true
				},
				eightviia_rem: {
					required: true
				},
				eightviib_rem: {
					required: true
				},
				eightviii_rem: {
					required: true
				},
				ninea_rem: {
					required: true
				},
				nineb_rem: {
					required: true
				},
				ten_rem: {
					required: true
				},
				elevena_rem: {
					required: true
				},
				elevenb_rem: {
					required: true
				},
				twelve_rem: {
					required: true
				},
				board_a_rem: {
					required: true
				},
				board_b_rem: {
					required: true
				},
				investment_a_rem: {
					required: true
				},
				investment_b_rem: {
					required: true
				},
				risk_a_rem: {
					required: true
				},
				risk_b_rem: {
					required: true
				},
				annex_ii_rem: {
					required: true
				},
				annex_iii_rem: {
					required: true
				},
				annex_iv_rem: {
					required: true
				},
				annex_comp_certificate_rem: {
				      required: true
				    }, */
			  }
			});
			
			
    
		    
	    $('#btn-submit').on('click', function(e){ 
	    	console.log("submit called");
	    	 e.preventDefault(); 
			
			//default
			if($("#success-modal").hasClass("hide")){
				$("#success-modal").removeClass("hide");
			}
	    	
	    	if($( "#myForm" ).valid()){
	    		 //"http://uat.npstrust.net/web/guest/quartely-compliance-certificate?p_p_id=Compliance_certificate_at_quarterly_interval_Compliance_certificate_at_quarterly_intervalPortlet_scr&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=input_quarterly_intervalPortletKeys&p_p_cacheability=cacheLevelPage";
	    		let InputcomplianceResourceURL = "/web/guest/quartely-compliance-certificate?p_p_id=Compliance_certificate_at_quarterly_interval_Compliance_certificate_at_quarterly_intervalPortlet_scr&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=input_quarterly_intervalPortletKeysscr&p_p_cacheability=cacheLevelPage";

	    		var fd = new FormData($("#myForm")[0]);
		        $(".content").hide();
		        $(".animaion").show();
		        $.ajax({
		            url: InputcomplianceResourceURL,  
		            type: 'POST',
		            data: fd,
		            cache: false,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	$(".content").show();
		                $(".animaion").hide();
		                $("#myForm")[0].reset();
		                //try {
	                    	//data = JSON.parse(data);
	                    	//toastr.success('Form has been submitted successfully!');
	                    /*} catch (e) {
	                    	toastr.error('An error occured while parsing the json data. Please try again');
	                    	console.log("error while parsing the json data");
	                    }*/
	              		/* var pdfURL = data.pdfURL;
	           		 	$('#output').html("<label>Please download the pdf here. </label> <a class='ml-2' href="+pdfURL+"><i class='fa fa-download'></i></a>"); */
	           			 $('#output').html(' Data sent for Review.');
            			$("#icon").addClass("fas fa-check-circle text-success fa-4x d-block mb-4");
            			$('.x-mark').attr('href', "#");
   		            	//$("#form_comp")[0].reset();
   		            	
		            },
		            
		            
		            error: function() {
		            	$(".animaion").hide();
		           		console.log("Error Occured in ajax call");
		           		//toastr.error('An error occured while submitting the form. Please try again');
		            },
		            complete: function(){
		            	$(".animaion").hide();
						console.log("Complete");
						$("#success-modal").show();
	        			$('#pop-up-trigger')[0].click();
			        }
		        });
	    	} /* else{
	    		toastr.error('Please fill the required field(s).');
	    	}  */
	    });
	});
});
</script>

<!-- <script>

$("#addRow").click(function () {
    
    
    var htmlContent = '';
    htmlContent = '<tr>' + 
		'<td><input type="hidden" class="rowIndex" id="rowIndex-'+count+'" value="1" name="<portlet:namespace />rowIndex[]"/></td>' +
    	'<td><textarea type="text" class="boardDiscription" id="boardDiscription-'+count+'" name="<portlet:namespace />boardDiscription[]"></textarea></td>' +
    	'<td><textarea type="text" class="auditorObservation" id="auditorObservation-'+count+'" name="<portlet:namespace />auditorObservation[]"></textarea></td>' +
    	'<td><textarea type="text" class="riskRating" id="riskRating-'+count+'" name="<portlet:namespace />riskRating[]"></textarea></td>' +
    	'<td><textarea type="text" class="managementResponse" id="managementResponse-'+count+'" name="<portlet:namespace />managementResponse[]"></textarea></td>' +
    	'<td><textarea type="text" class="npstComment" id="npstComment-'+count+'" name="<portlet:namespace />npstComment[]"></textarea></td>' +
     '<td class="text-center"><button class="btn btn-primary btn-sm remove" id="removeRow" type="button" style="padding:5px 10px"><i class="fas fa-trash-alt"></i></button> </td>' +
    '</tr>';
    $('#myTable_1 tbody').append(htmlContent);
    count++;
    
    $('.auditorObservation').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		
		$('.auditorObservation').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.riskRating').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		$('.managementResponse').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		$('.npstComment').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
    
		$("textarea").each(function () {
	    	this.setAttribute("style", "height:" + (this.scrollHeight) + "px;overflow-y:hidden;");
	   	}).on("input", function () {
	   		this.style.height = 0;
	    	this.style.height = (this.scrollHeight) + "px";
	   	});
	 });
	    
 $("#myTable_1").on('click','.remove',function(){
     $(this).closest('tr').remove();
 });
 
 $("textarea").each(function () {
 	this.setAttribute("style", "height:" + (this.scrollHeight) + "px;overflow-y:hidden;");
	}).on("input", function () {
		this.style.height = 0;
 	this.style.height = (this.scrollHeight) + "px";
	});
 
 
});

</script> -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>