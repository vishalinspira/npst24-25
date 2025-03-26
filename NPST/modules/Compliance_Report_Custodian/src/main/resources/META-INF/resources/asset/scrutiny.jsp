<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.compliance.report.custodian.util.NPSUserPrepopulateScrutinyData"%>
<%@page import="com.daily.average.service.service.CustodianCompFormScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.CustodianCompFormScrutiny"%>
<%@page import="com.compliance.report.custodian.resource.Pre_Populate_scrutinydata"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.average.service.model.CustodianCompForm"%>
<%@ include file="/init.jsp" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.1.1/css/all.min.css" />
<portlet:resourceURL id="/compformcustodianscrutiny/save" var="compformcustodianURL" /> 

<!-- <portlet:actionURL name="quaterendedUpload" var="quaterendedUploadURL"> -->

<!-- </portlet:actionURL> -->
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
	<% 
//getting inputQuarterlyInterval from asset render. Type casting the object to inputQuarterlyInterval.
boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;
System.out.println("isNonNPSUser asset rendererrr compliance report:::::" + isNonNPSUser);
CustodianCompForm custodianCompForm = (CustodianCompForm) request.getAttribute("custodianCompForm"); 
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

/* Pre populate data for asset view */

NPSUserPrepopulateScrutinyData sd = new NPSUserPrepopulateScrutinyData();
sd.prePopulateScrutinyData(request);

CustodianCompFormScrutiny custodianCompFormScrutiny = Validator.isNotNull(request.getAttribute("custodianCompFormScrutiny")) ? (CustodianCompFormScrutiny) request.getAttribute("custodianCompFormScrutiny") : CustodianCompFormScrutinyLocalServiceUtil.createCustodianCompFormScrutiny(0);



List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");


boolean isCustodianSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_SUPERVISOR, false);
boolean isDocSigned = false;
boolean isAssignable = Validator.isNotNull(request.getAttribute("isAssignable")) ? (boolean) request.getAttribute("isAssignable") : false;
boolean isSelfAsignee = Validator.isNotNull(request.getAttribute("isSelfAsignee")) ? (boolean) request.getAttribute("isSelfAsignee") : false;
JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
String fileEntryId = "0";
JSONObject signature = JSONFactoryUtil.createJSONObject();
%>

<p>Report Name: ${reportName}</p>
<p>Upload Date: ${uploadDate}</p>

<div class="nps-page-main nps-upload-report nps-statement-wrapper">

<div>
	<span>File Version : </span>
<% 
for(int i=0; i<urlArray.length();i++){
	JSONObject object = urlArray.getJSONObject(i);
	isDocSigned = Validator.isNotNull(object.getString("signature")); 
	fileEntryId = object.getString("fileEntryId");
	signature = JSONFactoryUtil.createJSONObject();
	if(isDocSigned){
		signature = JSONFactoryUtil.createJSONObject(object.getString("signature"));
	}
%>
	<span class="file-preview-span" data-index='<%= i%>'><a href="<%=object.getString("downloadURL")%>"> <%=object.getString("version")%></a></span>
	<% if(i != (urlArray.length()-1)){ %>
	 ,
	<% } %>
<% } %>
</div>
<% if(isCustodianSupervisor){ %>
	<div class="panel-body">
		<% if(!isDocSigned){ %>
			<button id="signFile" class="btn  btn-primary">Sign File</button>
		<% }else{ %>
			<button id="signFile" class="btn  btn-primary" disabled="disabled">Sign File</button>
			<h6>The report has been Authenticated</h6>
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
                  <h4>Compliance Report</h4>
                  <form id="custodianCompFormScrutiny" method="post">
                     <div class="statement-wrapper">
                        <input type="hidden" class="rowcount_one" name="<portlet:namespace />rowcount_one">
                        <input type="hidden" class="rowcount_two" name="<portlet:namespace />rowcount_two">
                        <input type="hidden" class="rowcount_three" name="<portlet:namespace />rowcount_three">
                        <input type="hidden" class="rowcount_four" name="<portlet:namespace />rowcount_four">
                        <input type="hidden" class="rowcount_five" name="<portlet:namespace />rowcount_five">
                        <input type="hidden" class="rowcount_six" name="<portlet:namespace />rowcount_six">
                        <input type="hidden" class="rowcount_seven" name="<portlet:namespace />rowcount_seven">
                        <div class="row">
                           <div class="col">
                              <!-- <div class="form-group"> -->
                              	<input type="hidden" name="dlfileid">
                              	<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
								<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
		                        <div class="row">
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
											<div class="nps-input-box mt-0">
												<label for="name" class="pl-2">Report Due Date</label>
											<input class="reportDate" name="reportDate" type="date" value="<%= dateFormat.format(custodianCompForm.getReportDate()) %>" readonly="readonly">
										</div>
									</div>
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
					                    <div class="nps-input-box mt-0">
					                        <label class="pl-3">For the Quarter Ended</label>
					                        <input type="date" class="rounded border-0 p-1 ml-3" id="formDate" value="<%= dateFormat.format(custodianCompForm.getFormdate()) %>" readonly="readonly" name='<portlet:namespace/>formDate'>
					                    </div>
				                	</div>
								</div>
                             
                              <br>		
                              <div class="row">
                                 <div class="col-md-9">
                                   <p class=" mb-0">To,</p>
                                   <p class=" mb-0">The Chief Executive Officer</p>
                                   <p class=" mb-0">National Pension System Trust,</p>
								   <p class="mb-0">B-302, Third Floor,</p>
								   <p class=" mb-0">Tower B, World Trade Center,</p>
                                   <p class="mb-0">Nauroji Nagar, New Delhi-110029</p>
                                 </div>
                              </div>
                              <br>
                           </div>
                        </div>
                        <hr />
                        
                         <div class="row">
                        	
                           <div class="col-md-11">
 
                              <p>Dear Sir/Madam,</p>
                              <br>
                                 <p>In my/our opinion and to the best of my/our information and according to the examinations carried out by me/us and explanations furnished to me/us, I/We certify the following in respect of the quarter mentioned above.</p>
                             
                           </div>
                         </div> 
                         <br> 
                         <hr/>
                         
                        <div class="row">
                           <div class="col-md-1">
                              <h5>Sr.NO</h5>
                           </div>
                           <div class="col-md-5">
                              <h5>Descriptions </h5>
                           </div>
                           <div class="col-md-3">
                              <h5>Remarks</h5>
                           </div>
                           <div class="col-md-3">
                              <h5>NPS Trust Observations</h5>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>1.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian is compliant with the eligibility criteria as stipulated in the Regulation 8 of the PFRDA (Custodian of Securities) Regulations, 2015 and subsequent amendments thereof.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_i_i" readonly><%= custodianCompForm.getRemarks_i_i() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_i_i"  name="observe_i_i" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_i_i()%></textarea>
                           </div>
                        </div>
                        <hr/>
                 <%--        <div class="row">
                           <div class="col-md-1">
                              <p></p>
                           </div>
                           <div class="col-md-5">
                              <p>Number of cases where clear funds not received before entering deal and the reasons thereof. </p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_i_ii"  readonly><%= custodianCompForm.getRemarks_i_ii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_i_ii"  name="observe_i_ii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_i_ii()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        --%>
                        <div class="row">
                           <div class="col-md-1">
                              <p>2.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether custodian has requisite authorisation/ POA from Pension Funds.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_ii"  readonly><%= custodianCompForm.getRemarks_ii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_ii"  name="observe_ii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_ii()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>3.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether upon receipt of instructions and clear funds from the Pension Funds for purchase of securities, the custodian has made the payment and settled the transaction as per the standard settlement process. Please provide details of the deals settled otherwise.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_iii" readonly><%= custodianCompForm.getRemarks_iii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_iii"  name="observe_iii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_iii()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>4.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has received deal instructions from the Pension Funds as per the agreed procedure? Please provide details of deal instructions received otherwise.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_iv"  readonly><%= custodianCompForm.getRemarks_iv() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_iv"  name="observe_iv" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_iv()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>5.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has ensured that the individual holdings of securities in the pension scheme accounts are daily reconciled with the depository holdings and Constituents' Subsidiary General Ledger (CGSL) account. Please provide, details of deviations, if any.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_v"  readonly><%= custodianCompForm.getRemarks_v() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_v"  name="observe_v" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_v()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>6.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has kept the securities held under NPS Trust segregated from the other securities of the custodian/ other clients</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_vi"  readonly><%= custodianCompForm.getRemarks_vi() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_vi"  name="observe_vi" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_vi()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>7.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has encumbered, pledged, hypothecated or marked any charge or lien on the securities held under NPS, except pursuant to instructions from the Pension Funds and in accordance to guidelines issued by the Authority</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_vii"  readonly><%= custodianCompForm.getRemarks_vii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_vii"  name="observe_vii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_vii()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>8.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian of securities has informed the issuer of securities in a timely manner, the exemption from income tax available to NPS Trust. Please provide details of TDS deducted, if any, on interest/coupon received.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_viii"  readonly><%= custodianCompForm.getRemarks_viii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_viii"  name="observe_viii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_viii()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>9.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has, as per entitlements/ instructions from Pension Funds collected, received and deposited in the designated NPS account sale proceeds, interest, redemption value, and other corporate actions due on the holdings in respect of the securities under NPS as per the agreed timeline. Please provide details of deviations, if any.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_ix"  readonly><%= custodianCompForm.getRemarks_ix() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_ix"  name="observe_ix" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_ix()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>10.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has timely informed to the Pension Funds regarding the interest, redemption and other corporate actions due on their respective holdings in respect of the securities under NPS. Please provide details of delay in intimation, if any.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_x"  readonly><%= custodianCompForm.getRemarks_x() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_x"  name="observe_x" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_x()%></textarea>
                           </div>
                        </div>
                        <hr />
                        <div class="row">
                           <div class="col-md-1">
                              <p>11.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has furnished to the Pension Funds, scheme-wise holding and transaction wise details of all purchases and sales of securities relating to the pension scheme accounts at frequencies and timeline as agreed upon </p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xi"  readonly><%= custodianCompForm.getRemarks_xi() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_xi"  name="observe_xi" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_xi()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>12.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has assigned or delegated its duties/function under NPS to any third party. If yes, please provide details.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xii"  readonly><%= custodianCompForm.getRemarks_xii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_xii"  name="observe_xii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_xii()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>13.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether there are securities (equity) held by Pension Funds not forming part of Top 200 stocks published by NPS Trust</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xiii"  readonly><%= custodianCompForm.getRemarks_xiii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_xiii"  name="observe_xiii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_xiii()%></textarea>
                           </div>
                        </div>
                        <hr/>
                         <div class="row">
                           <div class="col-md-1">
                              <p>14.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether any grievances/ complaints have been received from the Pension Funds. If yes, please provide details of such grievances/complaints and time taken for their redressal.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xiv"  readonly><%= custodianCompForm.getRemarks_xiv() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_xiv"  name="observe_xiv" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_xiv()%></textarea>
                           </div>
                        </div>
                        <hr/>
                         <div class="row">
                           <div class="col-md-1">
                              <p>15.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the invoice raised by the custodian to the Pension Funds for the services rendered by it for the invoicing period is in terms of Regulation 16 of the PFRDA (Custodian of securities) Regulations, 2015 and subsequent amendments thereof and terms of appointment.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xv"  readonly><%= custodianCompForm.getRemarks_xv() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_xv"  name="observe_xv" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_xv()%></textarea>
                           </div>
                        </div>
                        <hr/>
                         <div class="row">
                           <div class="col-md-1">
                              <p>16.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has complied with the Code of Conduct as specified in the PFRDA (Custodian of securities) Regulations, 2015 and subsequent amendments thereof.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xvi"  readonly><%= custodianCompForm.getRemarks_xvi() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_xvi"  name="observe_xvi" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_xvi()%></textarea>
                           </div>
                        </div>
                        <hr/>
                         <div class="row">
                           <div class="col-md-1">
                              <p>17.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has adhered to the voting policy, cyber security policy and policy on adoption of cloud services issued by the Authority</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xvii"  readonly><%= custodianCompForm.getRemarks_xvii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_xvii"  name="observe_xvii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_xvii()%></textarea>
                           </div>
                        </div>
                        <hr/>
                         <div class="row">
                           <div class="col-md-1">
                              <p>18.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether the custodian has taken all measures necessary for prevention of fraud and has developed and implemented a fraud prevention and mitigation policy in accordance with Regulation 19(19) of PFRDA (Custodian of Securities) Regulations, 2015 and subsequent amendments thereof and guidelines issued by the Authority.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xviii"  readonly><%= custodianCompForm.getRemarks_xviii() %></textarea>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_xviii"  name="observe_xviii" <%=isNonNPSUser ? "disabled": "" %>><%=custodianCompFormScrutiny.getObserve_xviii()%></textarea>
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
                                 <p>1.	Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.</p>
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
                                 <%-- <p>2.  This Compliance Certificate(s) shall be put up to the Board on 
                                 	<input class="" type="text" id="month" value="<%= custodianCompForm.getMonth() %>" readonly >and the 
                                 </p> --%>
                                 <p>2.This Compliance Certificate(s) shall be put up to the Board on <%= custodianCompForm.getMonth()%> and the remarks related thereto would be forwarded to NPS Trust on subsequently. 
                                 </p>
                              </div>
                           </div >
                         <!--  <div class="col-md-5">
                              <div class="form-group">
                              </div>
                           </div>  --> 
                         </div>
                         <div class="row">
                         	<div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-11">
                              <div class="form-group">
                                 <p>Certified that the Information given, herein are correct and complete to the best of my/our knowledge and belief. </p>
                              </div>
                           </div>
                         </div> 
                        <hr/>
                        <br>
                        
		                <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <%-- <input type="text" class="employeeName border-0 p-2 w-100" id="employeeName" name="<portlet:namespace />employeeName">  --%>
		                     <div class="nps-input-box mt-0">
		                        <label>Name: </label>
		                        <input type="text" class="employeeName" id="employeeName" value="<%= custodianCompForm.getEmployeeName() %>" readonly >
		                     </div>
		                  </div>
		               </div>
		               <br>
		               <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                   <div class="nps-input-box mt-0">
		                  	 <label for="roles">Designation</label><br>
		                     <!-- <label class="form-check-label">&nbsp;  custodianCompForm.getDesignation() </label> -->	
		                     <input type="text" class="designation" id="designation" value="<%= custodianCompForm.getDesignation() %>" readonly >
		                  </div>
		                  </div>
		               </div>
		               <br>
		               
		               <%--   <div class="row">
		                  <div class="col-md-6">
		                     
		                        <label>Signature  </label>
		                  </div>      
		                   <div class="col-md-6">     
		                        <label>Signature </label>
		                    
		                  </div>
		               </div>
		               <br>
		               
		                <div class="row">
		                  <div class="col-md-6">
		                     
		                        <label>Compliance Officer  </label>
		                  </div>      
		                   <div class="col-md-6">     
		                        <label>Chief Executive Officer/Authorized signatory </label>
		                    
		                  </div>
		               </div>
		               <br>
		                --%>
		               <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <div class="nps-input-box mt-0">
		                        <label>Date: </label>
		                        <input type="date" class="date_3" id="date_3" value="<%= custodianCompForm.getDate_3() %>" readonly >
		                     </div>
		                  </div>
		               </div>
		               <br>
		               <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <div class="nps-input-box mt-0">
		                        <label>Place: </label>
		                        <input type="text" class="place" id="place" value="<%= custodianCompForm.getPlace() %>" readonly >
		                     </div>
		                  </div>
		               </div>
		               <br>
		               
		               <%if((isAssignable)|| (isSelfAsignee)){ %>
                      <div class="row text-center">
		                  <div class="col-md-12">
		                     <%-- <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" <%=isNonNPSUser ? "disabled": "" %> value="Submit"> --%>
		                     <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" <%=isNonNPSUser ? "disabled": "" %>>Submit</button>
		                     <a class="button" id="pop-up-trigger"  href="#success-modal"></a>
		                  </div>
		               </div>
		                <%} %>
                  </div>
               </form>
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

<style type="text/css">
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

	input.error {
		border-color: red;
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
var SDWebServerUrl = "https://dsc.npstrust.net";
var SDWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
var sdWebServerUrl = "https://dsc.npstrust.net";
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
$(document).ready(function() {
		$( ".x-mark" ).click(function() {
			$("#success-modal").addClass("hide");
			
			if($("#icon").hasClass("fas fa-check-circle text-success fa-4x d-block mb-4")){
				$("#icon").removeClass("fas fa-check-circle text-success fa-4x d-block mb-4");
			}
			if($("#icon").hasClass("fas fa-times-circle text-danger  fa-4x d-block mb-4")){
				$("#icon").removeClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
			}
			
		});
		
	$(function(){

			$('#btn-submit').on('click', function(e){ 
				
				e.preventDefault();
				console.log("Javascript starts");
				//default
				if($("#success-modal").hasClass("hide")){
					$("#success-modal").removeClass("hide");
				}
				
		        var fd = new FormData($("#custodianCompFormScrutiny")[0]);
		        console.log(fd);
		        let frmurl = "/web/guest/compliance-report?p_p_id=com_compliance_report_custodian_compliance_report_custodianPortlet_scr&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=%2Fcompformcustodianscrutiny%2Fsave&p_p_cacheability=cacheLevelPage";
		        $(".content").hide();
		        $(".animaion").show();
		        $.ajax({
		            url: frmurl,  
		            type: 'POST',
		            data: fd,
		            cache: false,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	$(".content").show();
		                $(".animaion").hide();
		                $("#custodianCompFormScrutiny")[0].reset();
		                try {
	                    	data = JSON.parse(data);
		        			//success message
		        	 		$('#output').html(' Data sent for Review.');
		        			$("#icon").addClass("fas fa-check-circle text-success fa-4x d-block mb-4");
		        			$('.x-mark').attr('href', "#");
	                    } catch (e) {
	                    	toastr.error('An error occured while parsing the json data. Please try again');
		        			//error message
		        			$('#output').html(' An error occured while submitting the form. Please try again.');
		        			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
	                    }
		            },
		            error: function() {
		            	$(".animaion").hide();
	        			//error message
	        			$('#output').html(' An error occured while submitting the form. Please try again.');
	        			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
		            },
		            complete: function(){
		            	$(".animaion").hide();
						console.log("Complete");
		            	//trigger-message
		            	$("#success-modal").show();
		            	$('#pop-up-trigger')[0].click();
			        }
		        });

	    });
	});
});
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>
