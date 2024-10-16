
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="com.daily.average.service.service.MnCompCertificateScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.MnCompCertificateScrutiny"%>
<%@page import="com.monthly.compcertificate.util.NPSUserPre_Populate_scrutinydata"%>
<%@page import="com.daily.average.service.service.MnCompCertificateLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.MnCompCertificate"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.Validator"%>

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
   MnCompCertificate mnCompCertificatedetails = Validator.isNotNull(request.getAttribute("mnCompCertificate")) ? (MnCompCertificate) request.getAttribute("mnCompCertificate") : MnCompCertificateLocalServiceUtil.createMnCompCertificate(0);
   
   boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;
   boolean isAssignable = Validator.isNotNull(request.getAttribute("isAssignable")) ? (boolean) request.getAttribute("isAssignable") : false;
   boolean isSelfAsignee = Validator.isNotNull(request.getAttribute("isSelfAsignee")) ? (boolean) request.getAttribute("isSelfAsignee") : false;
   /* Pre populate data for asset view */
  SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
  NPSUserPre_Populate_scrutinydata sd = new NPSUserPre_Populate_scrutinydata();
  sd.pre_populate_scrutiny_data(request);
		  
  boolean isSupervisor  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
  boolean isDocSigned = false;
		  
  MnCompCertificateScrutiny mnCompCertificateScrutiny = Validator.isNotNull(request.getAttribute("mnCompCertificateScrutiny")) ? (MnCompCertificateScrutiny) request.getAttribute("mnCompCertificateScrutiny") : MnCompCertificateScrutinyLocalServiceUtil.createMnCompCertificateScrutiny(0);

  List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");
	
	boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
	String dDate[]=dateFormat.format(mnCompCertificatedetails.getReportDate()).split("-");

	int month=Integer.parseInt(dDate[0]);
	int year=Integer.parseInt(dDate[2]);
	if(month==1){
		year=year-1;
		month=12;
	}else{
		month=month-1;	
	}
	String formDate1=month+"/"+year;
	
	String fileEntryId = "0";
	JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
	JSONObject signature = JSONFactoryUtil.createJSONObject();
  
%>


 <div class="custom-modal-ui hide"  id="success-modal">
	<div class="modal-head-ui">
		<h2 class="modal-title-ui">Report Message</h2>
		<a class="modal-close-ui x-mark" href="#0">&times;</a>
		<div class="modal-content-ui text-center">
			<i class="" id="icon"></i>
			<span id="output"></span>
		</div>
	</div>
</div>

<div class="card_blocks"> 
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
<% if(isSupervisor){ %>
	<div>
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
</div>
        <div class="form_block mx-4">
        	<form class="form_one" id="form_comp" action="#" method="post">
        		<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="<%=mnCompCertificatedetails.getReportUploadLogId()%>"/>
				<input type="hidden"value="<%=mnCompCertificatedetails.getReportMasterId()%>" name="reportMasterId" class="reportMasterId"/>
				<input class="reportDate" type="hidden" value="<%= dateFormat.format(mnCompCertificatedetails.getReportDate()) %>" name="reportDate">
        	   <hr/>

               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="col-md-9">
<p class="font-weight-bold mb-0">To,</p>
<p class=" font-weight-bold mb-0">National Pension System Trust,</p>
<p class=" font-weight-bold mb-0">Tower B, B-302, Third Floor,</p>
<p class="font-weight-bold mb-0">World Trade Center,</p>
<p class="font-weight-bold mb-0">Nauroji Nagar,</p>
<p class="font-weight-bold mb-0">New Delhi-110029</p>
                     </div>
                  </div>
               </div>
               <br>
               <p>Sir,</p>
               <br>
               <p>In my opinion and to the best of my information and
                  according to the examinations carried out by me and explanations
                  furnished to me, I certify the following in respect of the month
                  mentioned above
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
        			<div class="col-md-12 form-group">
        				<p><strong>1. Purchase of securities</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>1.1</strong>  Whether purchase of securities adhere to the Investment guidelines issued by PFRDA. 
							(prescribed securities/ percentage/ limit/ prudential & exposure norms) Details of deviations provided in annexure A (i).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                    
                    <% if (isNonNPSUser) { %>
                           
	                     <div class="form-check form-check-inline">
	                        <input  class="form-check-input" type="radio" class="purchase1" id="purchase_1" name='purchase1' value="Yes"
	                        <%=mnCompCertificatedetails.getPurchase_of_securities() != null && mnCompCertificatedetails.getPurchase_of_securities().equalsIgnoreCase("Yes") ? "checked" : "" %>>
	                        <label class="form-check-label">&nbsp; Yes</label>
	                     </div>
	                     <div class="form-check form-check-inline">
	                        <input  class="form-check-input" type="radio" class="purchase1" id="purchase_2" name='purchase1' value="No"
	                          <%=mnCompCertificatedetails.getPurchase_of_securities() != null && mnCompCertificatedetails.getPurchase_of_securities().equalsIgnoreCase("No") ? "checked" : "" %>>
	                        <label class="form-check-label">&nbsp; No</label>
	                     </div>
	                     <div class="form-check form-check-inline">
	                        <input  class="form-check-input" type="radio" class="purchase1" id="purchase_3" name='purchase1' value="NA"
	                        <%=mnCompCertificatedetails.getPurchase_of_securities() != null && mnCompCertificatedetails.getPurchase_of_securities().equalsIgnoreCase("NA") ? "checked" : "" %>>
	                        <label class="form-check-label">&nbsp; NA</label>
	                     </div>
	                       
                              <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getPurchase_of_securities()%></label> 
                              </div>
                              <% } %>
                  </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                       		<textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_1_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getPurchase_of_sec_rem_1_1_2() ==null ?"":mnCompCertificatedetails.getPurchase_of_sec_rem_1_1_2() %></textarea>
                        	
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_1" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getPurchase_of_securities_rem() == null ? "" :  mnCompCertificateScrutiny.getPurchase_of_securities_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		 <div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>1.2 (a)</strong> Whether a detailed Investment process manual is prepared and approved by Board / board delegated authority and whether actual process is established as per approved manual.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                    
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='purchase1-2' id="section-1-1" value="Yes"  
                               <%=mnCompCertificatedetails.getDetailed_investment() != null && mnCompCertificatedetails.getDetailed_investment().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='purchase1-2' id="section-1-2" value="No"  
                               <%=mnCompCertificatedetails.getDetailed_investment() != null && mnCompCertificatedetails.getDetailed_investment().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='purchase1-2' id="section-1-3" value="NA" 
                               <%=mnCompCertificatedetails.getDetailed_investment() != null && mnCompCertificatedetails.getDetailed_investment().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getDetailed_investment()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2a_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getPurchase_of_sec_rem_1_2a_2() ==null ?"":mnCompCertificatedetails.getPurchase_of_sec_rem_1_2a_2() %></textarea>
                        	
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2a" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getDetailed_investment_rem() == null ? "" :  mnCompCertificateScrutiny.getDetailed_investment_rem()%></textarea>
                        </div>
                    </div>
        		 </div><!-- row one end -->
        		
        		 <div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether investments are approved by the committee/competent authority as per Approval delegation matrix</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='purchaseb' id="section-1-1" value="Yes" 
                               <%=mnCompCertificatedetails.getInvestments_approved() != null && mnCompCertificatedetails.getInvestments_approved().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='purchaseb' id="section-1-2" value="No" 
                             <%=mnCompCertificatedetails.getInvestments_approved() != null && mnCompCertificatedetails.getInvestments_approved().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='purchaseb' id="section-1-3" value="NA" 
                             <%=mnCompCertificatedetails.getInvestments_approved() != null && mnCompCertificatedetails.getInvestments_approved().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getInvestments_approved()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class=" form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2b_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getPurchase_of_sec_rem_1_2b_2() ==null ?"":mnCompCertificatedetails.getPurchase_of_sec_rem_1_2b_2() %></textarea>
                        	
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2b" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getInvestments_approved_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestments_approved_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(c)</strong> Whether each decision of investment is properly documented and record is maintained at individual transaction level.
                            (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='purchasec' id="section-1-1" value="Yes"  
                             <%=mnCompCertificatedetails.getDecision_of_investment() != null && mnCompCertificatedetails.getDecision_of_investment().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchasec' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getDecision_of_investment() != null && mnCompCertificatedetails.getDecision_of_investment().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchasec' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getDecision_of_investment() != null && mnCompCertificatedetails.getDecision_of_investment().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getDecision_of_investment()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2c_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getPurchase_of_sec_rem_1_2c_2() ==null ?"":mnCompCertificatedetails.getPurchase_of_sec_rem_1_2c_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2c" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getDecision_of_investment_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_of_investment_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(d)</strong> Whether investments for non-dematerialized securities are supported by physical certificates</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchased' type="radio" id="section-1-1" value="Yes"  
                            <%=mnCompCertificatedetails.getInvestments_non_dematerialized() != null && mnCompCertificatedetails.getInvestments_non_dematerialized().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchased' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getInvestments_non_dematerialized() != null && mnCompCertificatedetails.getInvestments_non_dematerialized().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchased' type="radio" id="section-1-3" value="NA"  
                            <%=mnCompCertificatedetails.getInvestments_non_dematerialized() != null && mnCompCertificatedetails.getInvestments_non_dematerialized().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getInvestments_non_dematerialized()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class=" form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2d_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getPurchase_of_sec_rem_1_2d_2()==null ?"":mnCompCertificatedetails.getPurchase_of_sec_rem_1_2d_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2d" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getInvest_non_dematerialized_rem() == null ? "" :  mnCompCertificateScrutiny.getInvest_non_dematerialized_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(e)</strong> Whether all investments from funds received under NPS schemes are held in the name of NPS Trust.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                    
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchasee' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getAll_investments_from_funds() != null && mnCompCertificatedetails.getAll_investments_from_funds().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchasee' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getAll_investments_from_funds() != null && mnCompCertificatedetails.getAll_investments_from_funds().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchasee' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getAll_investments_from_funds() != null && mnCompCertificatedetails.getAll_investments_from_funds().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getAll_investments_from_funds()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2e_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getPurchase_of_sec_rem_1_2e_2() ==null ?"":mnCompCertificatedetails.getPurchase_of_sec_rem_1_2e_2()  %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2e" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAll_investments_from_funds_rem() == null ? "" :  mnCompCertificateScrutiny.getAll_investments_from_funds_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		<!-- 1.1 over -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>1.3</strong> Whether delivery of securities is taken immediately on purchase as per settlement cycle/ terms for each transaction.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase1-3'type="radio" id="section-1-1" value="Yes" 
                             <%=mnCompCertificatedetails.getDelivery_of_securities_purch() != null && mnCompCertificatedetails.getDelivery_of_securities_purch().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase1-3' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getDelivery_of_securities_purch() != null && mnCompCertificatedetails.getDelivery_of_securities_purch().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase1-3' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getDelivery_of_securities_purch() != null && mnCompCertificatedetails.getDelivery_of_securities_purch().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getDelivery_of_securities_purch()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_3_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getPurchase_of_sec_rem_1_3_2() ==null ?"":mnCompCertificatedetails.getPurchase_of_sec_rem_1_3_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_3" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getDelivery_of_security_purch_rem() == null ? "" :  mnCompCertificateScrutiny.getDelivery_of_security_purch_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>1.4</strong> Whether any application/investment is done in Initial Public Offer (IPO), Follow on Public Offer (FPO) and/or Offer for sale (OFS) during the period? (As per PFRDA circular no. PFRDA/2021/32/REG-PF/4 dated 27.07.2021, such investments to be reported to NPS Trust within 30 days of making such investments)
							Details of Investments provided in Annexure B.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase1-4' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getInvestment_done_in_ipo() != null && mnCompCertificatedetails.getInvestment_done_in_ipo().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase1-4' type="radio" id="section-1-2" value="No" 
                             <%=mnCompCertificatedetails.getInvestment_done_in_ipo() != null && mnCompCertificatedetails.getInvestment_done_in_ipo().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase1-4' type="radio" id="section-1-3" value="NA"  
                            <%=mnCompCertificatedetails.getInvestment_done_in_ipo() != null && mnCompCertificatedetails.getInvestment_done_in_ipo().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getInvestment_done_in_ipo()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_4_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getPurchase_of_secu_rem_1_4_2() ==null ?"":mnCompCertificatedetails.getPurchase_of_secu_rem_1_4_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_4" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getInvestment_done_in_ipo_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestment_done_in_ipo_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>2. Securities held</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>2.1</strong>  Whether scheme investments adhere to the Investment guidelines issued by PFRDA. 
							(prescribed securities/ percentage/ limit/ prudential & exposure norms)
							Details of deviations provided in annexure A (ii).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-1' type="radio" id="section-1-1" value="Yes" 
                             <%=mnCompCertificatedetails.getScheme_investments() != null && mnCompCertificatedetails.getScheme_investments().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-1' type="radio" id="section-1-2" value="No" 
                             <%=mnCompCertificatedetails.getScheme_investments() != null && mnCompCertificatedetails.getScheme_investments().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-1' type="radio" id="section-1-3" value="NA" 
                             <%=mnCompCertificatedetails.getScheme_investments() != null && mnCompCertificatedetails.getScheme_investments().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getScheme_investments()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_1_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSecurities_held_2_1_2() ==null ?"":mnCompCertificatedetails.getSecurities_held_2_1_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_1" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getScheme_investments_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_investments_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group"> 
                            <p><strong>2.2 (a)</strong> Whether stop loss trigger has occurred for any security during the month as per Investment policy of the PFM. </p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2a' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getStop_loss_trigger() != null && mnCompCertificatedetails.getStop_loss_trigger().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2a' type="radio" id="section-1-2" value="No" 
                             <%=mnCompCertificatedetails.getStop_loss_trigger() != null && mnCompCertificatedetails.getStop_loss_trigger().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2a' type="radio" id="section-1-3" value="NA" 
                             <%=mnCompCertificatedetails.getStop_loss_trigger() != null && mnCompCertificatedetails.getStop_loss_trigger().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getStop_loss_trigger()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_2a_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSecurities_held_2_2a_2() ==null ?"":mnCompCertificatedetails.getSecurities_held_2_2a_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_2a" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getStop_loss_trigger_rem() == null ? "" :  mnCompCertificateScrutiny.getStop_loss_trigger_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether decision in such scenario is approved by the committee/competent authority as per Approval delegation matrix.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2b' type="radio" id="section-1-1" value="Yes" 
                             <%=mnCompCertificatedetails.getDecision_approved_by_committee() != null && mnCompCertificatedetails.getDecision_approved_by_committee().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2b' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getDecision_approved_by_committee() != null && mnCompCertificatedetails.getDecision_approved_by_committee().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2b' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getDecision_approved_by_committee() != null && mnCompCertificatedetails.getDecision_approved_by_committee().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getDecision_approved_by_committee()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_2b_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSecurities_held_2_2b_2() ==null ?"":mnCompCertificatedetails.getSecurities_held_2_2b_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_2b" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getDecision_appr_by_committee_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_appr_by_committee_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(c)</strong> Whether each decision along with rationale is properly documented and record is maintained at individual scrip level.
							(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)
							Details of stop loss occurred during the month and its decision provided in Annexure C.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2c'type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getDecision_properly_documented() != null && mnCompCertificatedetails.getDecision_properly_documented().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2c' type="radio" id="section-1-2" value="No"  
                             <%=mnCompCertificatedetails.getDecision_properly_documented() != null && mnCompCertificatedetails.getDecision_properly_documented().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-2c' type="radio" id="section-1-3" value="NA" 
                             <%=mnCompCertificatedetails.getDecision_properly_documented() != null && mnCompCertificatedetails.getDecision_properly_documented().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getDecision_properly_documented()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_2c_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSecurities_held_2_2c_2() ==null ?"":mnCompCertificatedetails.getSecurities_held_2_2c_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_2c" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getDecision_prop_documented_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_prop_documented_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<!-- 2.1 over abc -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>2.3</strong>  Whether inter-scheme transfer of securities complies with point 14 of the Investment Guideline circular number PFRDA/2021/29/REG-PF/3 dated 20.07.2021.
							Details of inter scheme transfer provided in Annexure D.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-3' type="radio" id="section-1-1" value="Yes"  
                            <%=mnCompCertificatedetails.getInter_scheme_transfer() != null && mnCompCertificatedetails.getInter_scheme_transfer().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-3' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getInter_scheme_transfer() != null && mnCompCertificatedetails.getInter_scheme_transfer().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-3' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getInter_scheme_transfer() != null && mnCompCertificatedetails.getInter_scheme_transfer().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getInter_scheme_transfer()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_3_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSecurities_held_2_3_2() ==null ?"":mnCompCertificatedetails.getSecurities_held_2_3_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_3" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getInter_scheme_transfer_rem() == null ? "" :  mnCompCertificateScrutiny.getInter_scheme_transfer_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>2.4</strong>  Whether investment is held in any equity shares of a body corporate which is not listed in top 200 list of stocks prepared by NPS Trust and is pending for rebalancing. 
							Details provided in Annexure E</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-4' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getInvestment_held_in_equity() != null && mnCompCertificatedetails.getInvestment_held_in_equity().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-4' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getInvestment_held_in_equity() != null && mnCompCertificatedetails.getInvestment_held_in_equity().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-4' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getInvestment_held_in_equity() != null && mnCompCertificatedetails.getInvestment_held_in_equity().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getInvestment_held_in_equity()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_4_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSecurities_held_2_4_2() ==null ?"":mnCompCertificatedetails.getSecurities_held_2_4_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_4" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getInvestment_held_in_equity_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestment_held_in_equity_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>2.5</strong>  Whether investment in any equity shares through IPO/FPO/OFS does not fulfil the market capitalization condition prescribed under investment guidelines post listing. 
							Details provided in Annexure F.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-5' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getInvestment_in_equity_shares() != null && mnCompCertificatedetails.getInvestment_in_equity_shares().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-5' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getInvestment_in_equity_shares() != null && mnCompCertificatedetails.getInvestment_in_equity_shares().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase2-5' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getInvestment_in_equity_shares() != null && mnCompCertificatedetails.getInvestment_in_equity_shares().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getInvestment_in_equity_shares()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class=" form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_5_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSecurities_held_2_5_2() ==null ?"":mnCompCertificatedetails.getSecurities_held_2_5_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_5" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getInvest_in_equity_shares_rem() == null ? "" :  mnCompCertificateScrutiny.getInvest_in_equity_shares_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>3. Sale of securities</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>3.1 (a)</strong> Whether disinvestments made are approved by the committee/competent authority as per delegation matrix.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                    
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-1a' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getDisinvestments_approved() != null && mnCompCertificatedetails.getDisinvestments_approved().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-1a' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getDisinvestments_approved() != null && mnCompCertificatedetails.getDisinvestments_approved().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-1a' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getDisinvestments_approved() != null && mnCompCertificatedetails.getDisinvestments_approved().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getDisinvestments_approved()%></label> 
                              </div>
                              <% } %>
                        
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Sale_of_securities_3_1a_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSale_of_securities_3_1a_2() ==null ?"":mnCompCertificatedetails.getSale_of_securities_3_1a_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Sale_of_securities_3_1a" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getDisinvestments_approved_rem() == null ? "" :  mnCompCertificateScrutiny.getDisinvestments_approved_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-check-inline form-group">
                            <p><strong>(b)</strong> Whether each decision of disinvestment is properly documented and record is maintained at individual transaction level.
							(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-1b' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getDecision_of_disinvestment() != null && mnCompCertificatedetails.getDecision_of_disinvestment().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-1b' type="radio" id="section-1-2" value="No" 
                             <%=mnCompCertificatedetails.getDecision_of_disinvestment() != null && mnCompCertificatedetails.getDecision_of_disinvestment().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-1b' type="radio" id="section-1-3" value="NA" 
                             <%=mnCompCertificatedetails.getDecision_of_disinvestment() != null && mnCompCertificatedetails.getDecision_of_disinvestment().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getDecision_of_disinvestment()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Sale_of_securities_3_1b_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSale_of_securities_3_1b_2() ==null ?"":mnCompCertificatedetails.getSale_of_securities_3_1b_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Sale_of_securities_3_1b" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getDecision_of_disinvestment_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_of_disinvestment_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<!--3.1 over abc  -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>3.2</strong>  Whether delivery of securities is given immediately on sale as per settlement cycle/ terms for each transaction.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                    
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-2' type="radio" id="section-1-1" value="Yes" 
                             <%=mnCompCertificatedetails.getDelivery_of_securities_sale() != null && mnCompCertificatedetails.getDelivery_of_securities_sale().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-2' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getDelivery_of_securities_sale() != null && mnCompCertificatedetails.getDelivery_of_securities_sale().equalsIgnoreCase("No") ? "checked" : "" %>>
                            
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase3-2' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getDelivery_of_securities_sale() != null && mnCompCertificatedetails.getDelivery_of_securities_sale().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getDelivery_of_securities_sale()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Sale_of_securities_3_2_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getSale_of_securities_3_2_2() ==null ?"":mnCompCertificatedetails.getSale_of_securities_3_2_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Sale_of_securities_3_2" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getDelivery_of_security_sale_rem() == null ? "" :  mnCompCertificateScrutiny.getDelivery_of_security_sale_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>4. Reports and Disclosures as per Schedule V of PFRDA (PF) Regulations</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>4.1 (a)</strong> Whether all investment charges (Investment management fees, Custodian charges, SEBI charges, NPS Trust fees etc.) are loaded onto the NAV on a daily basis.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                    <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1a' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getAll_investment_charges() != null && mnCompCertificatedetails.getAll_investment_charges().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1a' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getAll_investment_charges() != null && mnCompCertificatedetails.getAll_investment_charges().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1a' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getAll_investment_charges() != null && mnCompCertificatedetails.getAll_investment_charges().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getAll_investment_charges()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1a_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getReports_and_Disclosure_4_1a_2() ==null ?"":mnCompCertificatedetails.getReports_and_Disclosure_4_1a_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1a" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAll_investment_charges_rem() == null ? "" :  mnCompCertificateScrutiny.getAll_investment_charges_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether PFM has adhered to instructions of PFRDA to get the NAV verified by the auditors on a daily basis.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1b' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getPfm_adhered() != null && mnCompCertificatedetails.getPfm_adhered().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1b' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getPfm_adhered() != null && mnCompCertificatedetails.getPfm_adhered().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1b' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getPfm_adhered() != null && mnCompCertificatedetails.getPfm_adhered().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getPfm_adhered()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1b_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getReports_and_Disclosure_4_1b_2() ==null ?"":mnCompCertificatedetails.getReports_and_Disclosure_4_1b_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1b" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getPfm_adhered_rem() == null ? "" :  mnCompCertificateScrutiny.getPfm_adhered_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(c)</strong> Whether the records of the audit of NAV have been maintained by the pension fund for future inspection. </p>
                        </div>
                    </div>
                    <div class="col-md-3">
                    
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1c' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getRecords_of_the_audit_of_nav() != null && mnCompCertificatedetails.getRecords_of_the_audit_of_nav().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1c' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getRecords_of_the_audit_of_nav() != null && mnCompCertificatedetails.getRecords_of_the_audit_of_nav().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1c' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getRecords_of_the_audit_of_nav() != null && mnCompCertificatedetails.getRecords_of_the_audit_of_nav().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getRecords_of_the_audit_of_nav()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1c_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getReports_and_Disclosure_4_1c_2() ==null ?"":mnCompCertificatedetails.getReports_and_Disclosure_4_1c_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1c" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getRecords_of_audit_of_nav_rem() == null ? "" :  mnCompCertificateScrutiny.getRecords_of_audit_of_nav_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(d)</strong> Whether scheme-wise NAV (latest & historical) is uploaded daily on the PFM’s website within the prescribed time limit.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1d' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getScheme_wise_nav_uploaded() != null && mnCompCertificatedetails.getScheme_wise_nav_uploaded().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1d' type="radio" id="section-1-2" value="No" 
                             <%=mnCompCertificatedetails.getScheme_wise_nav_uploaded() != null && mnCompCertificatedetails.getScheme_wise_nav_uploaded().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1d' type="radio" id="section-1-3" value="NA" 
                             <%=mnCompCertificatedetails.getScheme_wise_nav_uploaded() != null && mnCompCertificatedetails.getScheme_wise_nav_uploaded().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getScheme_wise_nav_uploaded()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1d_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getReports_and_Disclosure_4_1d_2() ==null ?"":mnCompCertificatedetails.getReports_and_Disclosure_4_1d_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1d" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getScheme_wise_nav_uploaded_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_wise_nav_uploaded_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(e)</strong> Whether scheme-wise NAV is submitted daily to all the CRA’s within the prescribed time limit.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1e' type="radio" id="section-1-1" value="Yes" 
                             <%=mnCompCertificatedetails.getScheme_wise_nav_submitted() != null && mnCompCertificatedetails.getScheme_wise_nav_submitted().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1e' type="radio" id="section-1-2" value="No" 
                            <%=mnCompCertificatedetails.getScheme_wise_nav_submitted() != null && mnCompCertificatedetails.getScheme_wise_nav_submitted().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-1e' type="radio" id="section-1-3" value="NA" 
                            <%=mnCompCertificatedetails.getScheme_wise_nav_submitted() != null && mnCompCertificatedetails.getScheme_wise_nav_submitted().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getScheme_wise_nav_submitted()%> </label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1e_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getReports_and_Disclosure_4_1e_2() ==null ?"":mnCompCertificatedetails.getReports_and_Disclosure_4_1e_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1e" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getScheme_wise_nav_submitted_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_wise_nav_submitted_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<!--4.1 over abc  -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>4.2 (a)</strong> Whether monthly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the month.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                    
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-2a' type="radio" id="section-1-1" value="Yes" 
                            <%=mnCompCertificatedetails.getMonthly_reports_submitted() != null && mnCompCertificatedetails.getMonthly_reports_submitted().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-2a' type="radio" id="section-1-2" value="No" 
                             <%=mnCompCertificatedetails.getMonthly_reports_submitted() != null && mnCompCertificatedetails.getMonthly_reports_submitted().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-2a' type="radio" id="section-1-3" value="NA" 
                             <%=mnCompCertificatedetails.getMonthly_reports_submitted() != null && mnCompCertificatedetails.getMonthly_reports_submitted().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getMonthly_reports_submitted()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_2a_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getReports_and_Disclosure_4_2a_2() ==null ?"":mnCompCertificatedetails.getReports_and_Disclosure_4_2a_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_2a" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getMonthly_reports_submitted_rem() == null ? "" :  mnCompCertificateScrutiny.getMonthly_reports_submitted_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether script wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                     <% if (isNonNPSUser) { %>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-2b' type="radio" id="section-1-1" name="randd" value="Yes" 
                             <%=mnCompCertificatedetails.getScrip_wise_details() != null && mnCompCertificatedetails.getScrip_wise_details().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-2b' type="radio" id="section-1-2" name="randd" value="No" 
                             <%=mnCompCertificatedetails.getScrip_wise_details() != null && mnCompCertificatedetails.getScrip_wise_details().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='purchase4-2b' type="radio" id="section-1-3" name="randd" value="NA" 
                             <%=mnCompCertificatedetails.getScrip_wise_details() != null && mnCompCertificatedetails.getScrip_wise_details().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                        <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%= mnCompCertificatedetails.getScrip_wise_details()%></label> 
                              </div>
                              <% } %>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline  form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_2b_2"  <%=isNonNPSUser ? "": "disabled" %> ><%=mnCompCertificatedetails.getReports_and_Disclosure_4_2b_2() ==null ?"":mnCompCertificatedetails.getReports_and_Disclosure_4_2b_2() %></textarea>
                        
                        	<textarea class="form-control" name='Reports_and_Disclosures_4_2b' id="remarks_1_1" placeholder="Remarks if any" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getScrip_wise_details_rem() == null ? "" :  mnCompCertificateScrutiny.getScrip_wise_details_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<!-- document row -->
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>Documents Details</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure A (i): Breach in Investment guidelines - Securities Purchased.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_a_iURL ? "javascript:void(0);" : Annexure_a_iURL}" ${empty Annexure_a_iURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="Document_Ai" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_a_i_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_a_i_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		 <% if(!isNonNPSUser){ %>
		                 <div class="row">
				                
					                  <div class="col-lg-12 col-md-12 col-sm-12 col-12 pt-12">
					                  	<div class="form-group">
				                        	<textarea class="form-control" id="npsRemark" placeholder="AM Remarks" name="npsRemark"  ><%= (Validator.isNotNull(mnCompCertificateScrutiny))? mnCompCertificateScrutiny.getNpsRemark():"" %></textarea>
				                        </div>
					                  </div>
				                 </div>
				                 <br>
				                 <%} %>
        		
        		<%-- <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure A (ii): Breach in Investment guidelines - Securities held in portfolio.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_a_iiURL ? "javascript:void(0);" : Annexure_a_iiURL}" ${empty Annexure_a_iiURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="AnnexureAii" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_a_ii_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_a_ii_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end --> --%>
        		
        	<%-- 	<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure B: Details of application/investment in IPO/FPO/OFS.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_bURL ? "javascript:void(0);" : Annexure_bURL}" ${empty Annexure_bURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="AnnexureB" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_b_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_b_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end --> --%>
        		
        	<%-- 	<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure C: Details of stop loss triggered during the month.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_cURL ? "javascript:void(0);" : Annexure_cURL}" ${empty Annexure_cURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="AnnexureC" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_c_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_c_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end --> --%>
        		
        		<%-- <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure D: Details of inter-scheme transfer of securities.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_dURL ? "javascript:void(0);" : Annexure_dURL}" ${empty Annexure_dURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="AnnexureD" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_d_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_d_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end --> --%>
        		
        	<%-- 	<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure E: Details of stocks held outside the list of top 200 stocks (pending for rebalancing/rebalanced during the month) .</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_eURL ? "javascript:void(0);" : Annexure_eURL}" ${empty Annexure_eURL ? "" : "download"}>Click here to download</i></a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="AnnexureE" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_e_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_e_rem()%></textarea>
                        </div>
                    </div>
        		   </div><!-- row one end --> --%>
        		
        		<%-- <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure F: Details of sale of shares allotted in IPO/FPO/OFS where stock does not meet market capitalization condition post listing.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_fURL ? "javascript:void(0);" : Annexure_fURL}" ${empty Annexure_fURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="AnnexureF" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_f_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_f_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end --> --%>
        		
<%--         		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure G: Instances of delays in uploading NAV on website and submission to CRA.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_gURL ? "javascript:void(0);" : Annexure_gURL}" ${empty Annexure_gURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="AnnexureG" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_g_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_g_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure H: Segregation of Active and Passive deviations/breaches</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_hURL ? "javascript:void(0);" : Annexure_hURL}" ${empty Annexure_hURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="AnnexureH" <%=isNonNPSUser ? "disabled": "" %>><%=mnCompCertificateScrutiny.getAnnexure_h_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_h_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end --> --%>
        		
        		<div class="row text-center">
        		<%--  <%if(!(isPfmSupervisor && isDocSigned) && (isAssignable || isSelfAsignee)){ %> --%> 
			        
			         <%if(!(isPfmSupervisor && isDocSigned) && (isAssignable)){ %>
			         
			        <div class="col-md-12">
			        	<button type="submit" class="common-btn d-inline-block text-light border-0 mt-3"  id="btn-submit">Submit</button>
			        	<a class="button" id="pop-up-trigger"  href="#success-modal"></a>
			        </div>
			        <%} %>
		    	</div>
        	</form>
        </div>
</div>

<style>
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

var SDWebServerUrl = "<%=GetterUtil.getString(PropsUtil.get("com.nps.dsc.api.domain"), "https://dsc.npstrust.net")%>";
var SDWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
var sdWebServerUrl = "<%=GetterUtil.getString(PropsUtil.get("com.nps.dsc.api.domain"), "https://dsc.npstrust.net")%>";
var sdWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
let signature = <%= signature.toString() %>;
 <% if(!isDocSigned){ %>
	$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").bind("click", false);
<% }else{ %>
	let sigsub = signature.SelCertSubject.substring(signature.SelCertSubject.indexOf("CN="));
	$("h6.signedmsg").append(" by "+sigsub.split(",")[0].split("=")[1]); 
	$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").unbind("click");
<% } %> 
$(function(){
	$("textarea").each(function () {
		this.setAttribute("style", "height:" + (this.scrollHeight) + "px;overflow-y:hidden;");
	}).on("input", function () {
		this.style.height = 0;
		this.style.height = (this.scrollHeight) + "px";
	});
	console.log("doc load");
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
$( ".x-mark" ).click(function() {
		$("#success-modal").addClass("hide");
		
		if($("#icon").hasClass("fas fa-check-circle text-success fa-4x d-block mb-4")){
			$("#icon").removeClass("fas fa-check-circle text-success fa-4x d-block mb-4");
		}
		if($("#icon").hasClass("fas fa-times-circle text-danger  fa-4x d-block mb-4")){
			$("#icon").removeClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
		}
		
	});

$("#form_comp").on('submit', (function(e) {
		e.preventDefault();
		
		//default
		if($("#success-modal").hasClass("hide")){
			$("#success-modal").removeClass("hide");
		}
		
											
		let saveMonthlyComCertificateForm="/web/guest/monthly-compliance-certificate?p_p_id=com_monthly_compcertificate_MonthlyCompCertificateScrutinyPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=%2Fmonthly%2FsaveMonthlyComCertificateForm&p_p_cacheability=cacheLevelPage";
		//let saveMonthlyComCertificateForm="/web/guest/monthly-compliance-certificate?p_p_id=com_monthly_compcertificate_MonthlyCompCertificatePortlet_INSTANCE_4psqKMf57LvD&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=%2Fmonthly%2FsaveMonthlyComCertificateForm&p_p_cacheability=cacheLevelPage";
  	    $.ajax({
	            url: saveMonthlyComCertificateForm,
	            processData: false,
	            contentType: false,
	            data: new FormData(this),
	            type: "post",
	            success: function(data) {
	            	 console.log("data data:::", data);
	            	 if(data == "Success"){
	            		 
	            			//success message
	            	 		$('#output').html(' Data sent for Review.');
	            			$("#icon").addClass("fas fa-check-circle text-success fa-4x d-block mb-4");
	            			$('.x-mark').attr('href', "#");
	            			
	   		            	$("#form_comp")[0].reset();
	   		            	
	            	 }else{
	            			//error message
	            			$('#output').html(' An error occured while submitting the form. Please try again.');
	            			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
	   		            	console.log("Error Occured in ajax call");
	            	 }
	            },
	            error: function(xhr, status, error) {
	                console.log("Error error:::", error);
	        		//error message
	        		$('#output').html(' An error occured while submitting the form. Please try again.');
	        		$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
	            },
	            complete: function(){
	            	$(".animaion").hide();
        			$("#success-modal").show();
        			$('#pop-up-trigger')[0].click();
		        }
	        }); 
			
	}));
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>