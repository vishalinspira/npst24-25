<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.daily.average.service.service.CustodianCompFormScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.service.CustodianCompFormLocalServiceUtil"%>
<%@page import="com.compliance.report.custodian.util.ComplianceReportCustodianUtil"%>
<%@page import="com.daily.average.service.model.CustodianCompFormScrutiny"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.compliance.report.custodian.resource.Pre_Populate_scrutinydata"%>
<%@page import="com.daily.average.service.model.CustodianCompForm"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/compformcustodian/save" var="compformcustodianURL" /> 


<% 
ComplianceReportCustodianUtil complianceReportCustodianUtil = new ComplianceReportCustodianUtil();
boolean isNonNPSUser = complianceReportCustodianUtil.isNonNpsUser(themeDisplay.getUserId());
Pre_Populate_scrutinydata sd = new Pre_Populate_scrutinydata();
sd.pre_populate_scrutiny_data(request);
//boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;
SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
SimpleDateFormat reportDateFormat = new SimpleDateFormat("MMMM/yyyy");
long reportuploadlogId=0;
String formDate1="";
try{
	String dDate[]=((String)request.getAttribute("reportDate")).split("-");
	int month=Integer.parseInt(dDate[1]);
	int year=Integer.parseInt(dDate[0]);
	if(month==1){
		year=year-1;
		month=12;
	}else{
		month=month-1;	
	}
	formDate1=month+"/"+year;
}catch(Exception e1){}
CustodianCompForm custodianCompForm=null;
try{
	reportuploadlogId=(Long)request.getAttribute("reportUploadLogId");
	custodianCompForm = CustodianCompFormLocalServiceUtil.fetchCustodianCompForm(reportuploadlogId);
}catch(Exception e){
	custodianCompForm=CustodianCompFormLocalServiceUtil.createCustodianCompForm(0);
}
if(Validator.isNull(custodianCompForm)){
	custodianCompForm=CustodianCompFormLocalServiceUtil.createCustodianCompForm(0);
}
/*if(Validator.isNull(custodianCompFormScrutiny)){
	custodianCompFormScrutiny=CustodianCompFormScrutinyLocalServiceUtil.createCustodianCompFormScrutiny(0);
}
*/
//CustodianCompForm custodianCompForm = (CustodianCompForm) request.getAttribute("custodianCompForm"); 

CustodianCompFormScrutiny custodianCompFormScrutiny = Validator.isNotNull(request.getAttribute("custodianCompFormScrutiny")) ? (CustodianCompFormScrutiny) request.getAttribute("custodianCompFormScrutiny") : CustodianCompFormScrutinyLocalServiceUtil.createCustodianCompFormScrutiny(0);

JSONObject obj = Validator.isNotNull(sd.pre_populate_maker_data(request)) ? sd.pre_populate_maker_data(request) : JSONFactoryUtil.createJSONObject();



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
            <p  class="back_btn"><a class="backbtn" href="/web/guest/upload-custodian-maker"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
        </div>
     </div>
  </div>
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <div class="">
               <div class="">
                  <h4>Quarterly compliance certificate</h4>
                  <form id="myForm-compliance-report" action="#" method="post">
                     <div class="statement-wrapper">
<%--                         <input type="hidden" class="rowcount_one" name="rowcount_one">
                        <input type="hidden" class="rowcount_two" name="rowcount_two">
                        <input type="hidden" class="rowcount_three" name="rowcount_three">
                        <input type="hidden" class="rowcount_four" name="rowcount_four">
                        <input type="hidden" class="rowcount_five" name="rowcount_five">
                        <input type="hidden" class="rowcount_six" name="rowcount_six">
                        <input type="hidden" class="rowcount_seven" name="rowcount_seven"> --%>
                        <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <div class="nps-input-box file-upload">
		                        <div class="nps-input-box mt-0">
		                           <label>Quarterly Compliance report</label>
		                           <div class="file-select">
		                              <div class="file-select-name" id="noFile1">Quarterly Compliance report</div>
		                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
		                              <input type="file" class="qcfile-upload" id="qcfile" name="qcfile" accept=".pdf"/>
		                           </div>
		                           <label id="error1" class="error-message text-danger"></label>
		                           <br>
		                        </div>
		                     </div>
		                  </div>
		                 </div>
                        <div class="row">
                           <div class="col">
                              <!-- <div class="form-group"> -->
                              	<input type="hidden" name="dlfileid">
                              	<input type="hidden" id="reportUploadLogId" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
								<input type="hidden" id="reportMasterId" value="${reportMasterId }" name="reportMasterId" class="reportMasterId"/>
		                        <div class="row">
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
											<div class="nps-input-box mt-0">
												<label for="name"  class="pl-2">Report Due Date</label>
											<input class="reportDate" type="date" value="${reportDate }" name="reportDate" readonly="readonly">
										</div>
									</div>
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
					                    <div class="nps-input-box mt-0">
					                        <label class="pl-3">For the Quarter Ended</label>
					                        <input type="date" class="rounded border-0 p-1 ml-3" id="formDate" name='formDate'>
					                    </div>
				                	</div>
								</div>
                             
                              <br>		
                              <div class="row">
                                 <div class="col-md-9">
                                   <p class=" mb-0">To,</p>
                                   <p class=" mb-0">National Pension System Trust,</p>
								   <p class="mb-0">Tower B, B-302, Third Floor,</p>
								   <p class=" mb-0">World Trade Center,</p>
                                   <p class="mb-0">Nauroji Nagar,</p>
                                   <p class="mb-0">New Delhi-110029</p>
                                 </div>
                              </div>
                              <br>
                           </div>
                        </div>
                        <hr />
                        <div class="row">
                           <div class="col-md-1">
                              <h5>Sr.NO</h5>
                           </div>
                           <div class="col-md-5">
                              <h5>Descriptions </h5>
                           </div>
                           <div class="col-md-4">
                              <h5>Remarks</h5>
                           </div>
                           <div class="col-md-2">
                              <h5>NPS Trust Observations</h5>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>1.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether custodian received clear funds from PFs on T+0 basis before entering deals.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_i_i"  name="remarks_i_i"  <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_i_i():"" %></textarea>
                           </div> 
                       <%--    <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_i_i"  name="prev_remarks_i_i" readonly><%=obj.get("prev_remarks_i_i") == null ? "" : obj.get("prev_remarks_i_i") %></textarea>
                           </div>
                           --%> 
                           <div class="col-md-3">
                              <textarea class="form-control" id="observe_i_i"  name="observe_i_i" readonly><%=custodianCompFormScrutiny.getObserve_i_i()%></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p></p>
                           </div>
                           <div class="col-md-5">
                              <p>Number of cases where clear funds not received before entering deal and the reasons thereof. </p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_i_ii"  name="remarks_i_ii"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_i_ii():"" %></textarea>
                           </div>
                         <%--   <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_i_ii"  name="prev_remarks_i_ii" readonly><%=obj.get("prev_remarks_i_ii") == null ? "" : obj.get("prev_remarks_i_ii") %></textarea>
                           </div>
                        --%>   <div class="col-md-3">
                              <textarea class="form-control" id="observe_i_ii"  name="observe_i_ii" readonly><%=custodianCompFormScrutiny.getObserve_i_ii() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>2.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Number of cases where deal was not settled by custodian on settlement date and the reasons thereof.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_ii"  name="remarks_ii"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_ii():"" %></textarea>
                           </div>
                         <%--  <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_ii"  name="prev_remarks_ii" readonly><%=obj.get("prev_remarks_ii") == null ? "" : obj.get("prev_remarks_ii") %></textarea>
                           </div>
                          --%>  <div class="col-md-3">
                              <textarea class="form-control" id="observe_ii"  name="observe_ii" readonly><%=custodianCompFormScrutiny.getObserve_ii() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>3.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Number of cases where DIP/DIS was not provided by the PF.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_iii"  name="remarks_iii"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_iii():"" %></textarea>
                           </div>
                        <%--   <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_iii"  name="prev_remarks_iii" readonly><%=obj.get("prev_remarks_iii") == null ? "" : obj.get("prev_remarks_iii") %></textarea>
                           </div>
                         --%>   <div class="col-md-3">
                              <textarea class="form-control" id="observe_iii"  name="observe_iii" readonly><%=custodianCompFormScrutiny.getObserve_iii() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>4.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Details of the cases where the Custodian holding and PF as scheme holding differs.</p>
                           </div>
                            <div class="col-md-3">
                              <textarea class="form-control" id="remarks_iv"  name="remarks_iv" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_iv():"" %></textarea>
                           </div>
                     <%--    <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_iv"  name="prev_remarks_iv" readonly><%=obj.get("prev_remarks_iv") == null ? "" : obj.get("prev_remarks_iv") %></textarea>
                           </div>
                        --%>      <div class="col-md-3">
                              <textarea class="form-control" id="observe_iv"  name="observe_iv" readonly><%=custodianCompFormScrutiny.getObserve_iv() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>5.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Details of the cases where the custodian holding differs from NSDL/CSDL/RBI.</p>
                           </div>
                            <div class="col-md-3">
                              <textarea class="form-control" id="remarks_v"  name="remarks_v"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_v():"" %></textarea>
                           </div>
                        <%--   <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_v"  name="prev_remarks_v" readonly><%=obj.get("prev_remarks_v") == null ? "" : obj.get("prev_remarks_v") %></textarea>
                           </div>
                         --%>   <div class="col-md-3">
                              <textarea class="form-control" id="observe_v"  name="observe_v" readonly><%=custodianCompFormScrutiny.getObserve_v() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>6.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Number of grievances/ complaints received from the PFs and time taken for their redressal.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_vi"  name="remarks_vi"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_vi():"" %></textarea>
                           </div>
                        <%--   <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_vi"  name="prev_remarks_vi" readonly><%=obj.get("prev_remarks_vi") == null ? "" : obj.get("prev_remarks_vi") %></textarea>
                           </div>
                        --%>     <div class="col-md-3">
                              <textarea class="form-control" id="observe_vi"  name="observe_vi" readonly><%=custodianCompFormScrutiny.getObserve_vi() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>7.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Number of cases where corporate actions was late informed to PFs and the reasons thereof.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_vii"  name="remarks_vii"><%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_vii():"" %></textarea>
                           </div>
                       <%--    <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_vii"  name="prev_remarks_vii" readonly><%=obj.get("prev_remarks_vii") == null ? "" : obj.get("prev_remarks_vii") %></textarea>
                           </div>
                         --%>   <div class="col-md-3">
                              <textarea class="form-control" id="observe_vii"  name="observe_vii" readonly><%=custodianCompFormScrutiny.getObserve_vii() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>8.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Whether custodian has requisites authorization/PoA from PFs.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_viii"  name="remarks_viii"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_viii():"" %></textarea>
                           </div>
                         <%--  <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_viii"  name="prev_remarks_viii" readonly><%=obj.get("prev_remarks_viii") == null ? "" : obj.get("prev_remarks_viii") %></textarea>
                           </div>
                         --%>   <div class="col-md-3">
                              <textarea class="form-control" id="observe_viii"  name="observe_viii" readonly><%=custodianCompFormScrutiny.getObserve_viii() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>9.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Details of securities which are encumbered, pledged, hypothecated or any charge or lien marked.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_ix"  name="remarks_ix"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_ix():"" %></textarea>
                           </div>
                        <%--    <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_ix"  name="prev_remarks_ix" readonly><%=obj.get("prev_remarks_ix") == null ? "" : obj.get("prev_remarks_ix") %></textarea>
                           </div>
                         --%>   <div class="col-md-3">
                              <textarea class="form-control" id="observe_ix"  name="observe_ix" readonly><%=custodianCompFormScrutiny.getObserve_ix() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>10.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Delay in receipt of the interest/redemption value/bonus/corporate actions in respect of the securities belonging to PFs.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_x"  name="remarks_x"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_x():"" %></textarea>
                           </div>
                         <%--   <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_x"  name="prev_remarks_x" readonly><%=obj.get("prev_remarks_x") == null ? "" : obj.get("prev_remarks_x") %></textarea>
                           </div>
                         --%>  <div class="col-md-3">
                              <textarea class="form-control" id="observe_x"  name="observe_x" readonly><%=custodianCompFormScrutiny.getObserve_x() %></textarea>
                           </div>
                        </div>
                        <hr />
                        <div class="row">
                           <div class="col-md-1">
                              <p>11.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Details of TDS deducted on interest/coupon received.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xi"  name="remarks_xi" <%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_xi():"" %></textarea>
                           </div>
                         <%--   <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_xi"  name="prev_remarks_xi" readonly><%=obj.get("prev_remarks_xi") == null ? "" : obj.get("prev_remarks_xi") %></textarea>
                           </div>
                           --%><div class="col-md-3">
                              <textarea class="form-control" id="observe_xi"  name="observe_xi" readonly><%=custodianCompFormScrutiny.getObserve_xi() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>12.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Details of assignment or delegation of its function related to NPS.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xii"  name="remarks_xii"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_xii():"" %></textarea>
                           </div>
                         <%-- <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_xii"  name="prev_remarks_xii" readonly><%=obj.get("prev_remarks_xii") == null ? "" : obj.get("prev_remarks_xii") %></textarea>
                           </div>
                          --%>   <div class="col-md-3">
                              <textarea class="form-control" id="observe_xii"  name="observe_xii" readonly><%=custodianCompFormScrutiny.getObserve_xii() %></textarea>
                           </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <p>13.</p>
                           </div>
                           <div class="col-md-5">
                              <p>Securities held by PFs not forming a part of F&O segment.</p>
                           </div>
                           <div class="col-md-3">
                              <textarea class="form-control" id="remarks_xiii"  name="remarks_xiii"<%=isNonNPSUser ? "": "disabled" %>><%= (Validator.isNotNull(custodianCompForm))? custodianCompForm.getRemarks_xiii():"" %></textarea>
                           </div>
                        <%--   <div class="col-md-2 hide">
                              <textarea class="form-control" id="prev_remarks_xiii"  name="prev_remarks_xiii" readonly><%=obj.get("prev_remarks_xiii") == null ? "" : obj.get("prev_remarks_xiii") %></textarea>
                           </div>
                         --%>   <div class="col-md-3">
                              <textarea class="form-control" id="observe_xiii"  name="observe_xiii" readonly><%=custodianCompFormScrutiny.getObserve_xiii() %></textarea >
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
                                 <!-- <p>2.  This Compliance Certificate(s) shall be put up to the Board on 
                                 	<input class="" type="date" id="month" name='month'>and the 
                                 </p> -->
                                 <p>2.This Compliance Certificate(s)n shall be put up to the Board at its ensuing Board Meeting and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.
                                 </p>
                              </div>
                           </div >
                          <!--  <div class="col-md-5">
                              <div class="form-group">
                              </div>
                           </div> -->
                         </div>
                         <div class="row">
                         	<div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-11">
                              <div class="form-group">
                                 <p>remarks related thereto would be forwarded to NPS Trust on subsequently.</p>
                              </div>
                           </div>
                         </div> 
                        <hr/>
                        <br>
		               <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <%-- <input type="text" class="employeeName border-0 p-2 w-100" id="employeeName" name="employeeName"> --%>
		                     <div class="nps-input-box mt-0">
		                        <label>Name: </label>
		                        <input type="text" class="employeeName" id="employeeName" name="employeeName">
		                     </div>
		                  </div>
		               </div>
		               <br>
		               <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                  	 <label for="roles">Designation</label><br>
		                  	 <input type="text" class="designation" id="designation" name="designation">
		                    <!--  <select class="w-100" name="designation" id="designation">
		                     	<option value="">Select</option>
		                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
		                     </select> -->
		                  </div>
		               </div>
		               
				                   
				                   
		               <br>
		               <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <div class="nps-input-box mt-0">
		                        <label>Date: </label>
		                        <input type="date" class="date_3" id="date_3" name="date_3" >
		                     </div>
		                  </div>
		               </div>
		               <br>
		               <div class="row">
		                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <div class="nps-input-box mt-0">
		                        <label>Place: </label>
		                        <input type="text" class="place" id="place" name="place">
		                     </div>
		                  </div>
		               </div>
		               <br>
                      <div class="row text-center" id="sub">
		                  <div class="col-md-12">
		                     <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" >Submit</button>
		                  </div>
		               </div>
                  </div>
               </form>
            </div>
         </div>
      </div>
   </div>
</div>
</div>

<style type="text/css">
	.modal {
	    display: none;
	}

	select {
	    background-color: #E9F3FC;
	    color: #111111;
	    border-radius: 5px;
	    padding: 5px 20px;
	}
		
	input.error {
		border-color: red;
	}
</style>

<script type="text/javascript">
$(document).ready(function() {
	
	/*var pr_1a = $("#prev_remarks_i_i").text();
	var pr_1b = $("#prev_remarks_i_ii").text();
	var pr_2 = $("#prev_remarks_ii").text();
	var pr_3 = $("#prev_remarks_iii").text();
	var pr_4 = $("#prev_remarks_iv").text();
	var pr_5 = $("#prev_remarks_v").text();
	var pr_6 = $("#prev_remarks_vi").text();
	var pr_7 = $("#prev_remarks_vii").text();
	var pr_8 = $("#prev_remarks_viii").text();
	var pr_9 = $("#prev_remarks_ix").text();
	var pr_10 = $("#prev_remarks_x").text();
	var pr_11 = $("#prev_remarks_xi").text();
	var pr_12 = $("#prev_remarks_xii").text();
	var pr_13 = $("#prev_remarks_xiii").text();
	
	if(pr_1a !== "" && $("#prev_remarks_i_i").parent().hasClass("hide")){
		$("#prev_remarks_i_i").parent().removeClass("hide");
		$("#remarks_i_i").parent().removeClass("col-md-3");
		$("#remarks_i_i").parent().addClass("col-md-2");
		$("#observe_i_i").parent().removeClass("col-md-3");
		$("#observe_i_i").parent().addClass("col-md-2");
	}
	if(pr_1b !== "" && $("#prev_remarks_i_ii").parent().hasClass("hide")){
		$("#prev_remarks_i_ii").parent().removeClass("hide");
		$("#remarks_i_ii").parent().removeClass("col-md-3");
		$("#remarks_i_ii").parent().addClass("col-md-2");
		$("#observe_i_ii").parent().removeClass("col-md-3");
		$("#observe_i_ii").parent().addClass("col-md-2");
	}
	if(pr_2 !== "" && $("#prev_remarks_ii").parent().hasClass("hide")){
		$("#prev_remarks_ii").parent().removeClass("hide");
		$("#remarks_ii").parent().removeClass("col-md-3");
		$("#remarks_ii").parent().addClass("col-md-2");
		$("#observe_ii").parent().removeClass("col-md-3");
		$("#observe_ii").parent().addClass("col-md-2");
	}
	if(pr_3 !== "" && $("#prev_remarks_iii").parent().hasClass("hide")){
		$("#prev_remarks_iii").parent().removeClass("hide");
		$("#remarks_iii").parent().removeClass("col-md-3");
		$("#remarks_iii").parent().addClass("col-md-2");
		$("#observe_iii").parent().removeClass("col-md-3");
		$("#observe_iii").parent().addClass("col-md-2");
	}
	if(pr_4 !== "" && $("#prev_remarks_iv").parent().hasClass("hide")){
		$("#prev_remarks_iv").parent().removeClass("hide");
		$("#remarks_iv").parent().removeClass("col-md-3");
		$("#remarks_iv").parent().addClass("col-md-2");
		$("#observe_iv").parent().removeClass("col-md-3");
		$("#observe_iv").parent().addClass("col-md-2");
	}
	if(pr_5 !== "" && $("#prev_remarks_v").parent().hasClass("hide")){
		$("#prev_remarks_v").parent().removeClass("hide");
		$("#remarks_v").parent().removeClass("col-md-3");
		$("#remarks_v").parent().addClass("col-md-2");
		$("#observe_v").parent().removeClass("col-md-3");
		$("#observe_v").parent().addClass("col-md-2");
	}
	if(pr_6 !== "" && $("#prev_remarks_vi").parent().hasClass("hide")){
		$("#prev_remarks_vi").parent().removeClass("hide");
		$("#remarks_vi").parent().removeClass("col-md-3");
		$("#remarks_vi").parent().addClass("col-md-2");
		$("#observe_vi").parent().removeClass("col-md-3");
		$("#observe_vi").parent().addClass("col-md-2");
	}
	if(pr_7 !== "" && $("#prev_remarks_vii").parent().hasClass("hide")){
		$("#prev_remarks_vii").parent().removeClass("hide");
		$("#remarks_vii").parent().removeClass("col-md-3");
		$("#remarks_vii").parent().addClass("col-md-2");
		$("#observe_vii").parent().removeClass("col-md-3");
		$("#observe_vii").parent().addClass("col-md-2");
	}
	if(pr_8 !== "" && $("#prev_remarks_viii").parent().hasClass("hide")){
		$("#prev_remarks_viii").parent().removeClass("hide");
		$("#remarks_viii").parent().removeClass("col-md-3");
		$("#remarks_viii").parent().addClass("col-md-2");
		$("#observe_viii").parent().removeClass("col-md-3");
		$("#observe_viii").parent().addClass("col-md-2");
	}
	if(pr_9 !== "" && $("#prev_remarks_ix").parent().hasClass("hide")){
		$("#prev_remarks_ix").parent().removeClass("hide");
		$("#remarks_ix").parent().removeClass("col-md-3");
		$("#remarks_ix").parent().addClass("col-md-2");
		$("#observe_ix").parent().removeClass("col-md-3");
		$("#observe_ix").parent().addClass("col-md-2");
	}
	if(pr_10 !== "" && $("#prev_remarks_x").parent().hasClass("hide")){
		$("#prev_remarks_x").parent().removeClass("hide");
		$("#remarks_x").parent().removeClass("col-md-3");
		$("#remarks_x").parent().addClass("col-md-2");
		$("#observe_x").parent().removeClass("col-md-3");
		$("#observe_x").parent().addClass("col-md-2");
	}
	if(pr_11 !== "" && $("#prev_remarks_xi").parent().hasClass("hide")){
		$("#prev_remarks_xi").parent().removeClass("hide");
		$("#remarks_xi").parent().removeClass("col-md-3");
		$("#remarks_xi").parent().addClass("col-md-2");
		$("#observe_xi").parent().removeClass("col-md-3");
		$("#observe_xi").parent().addClass("col-md-2");
	}
	if(pr_12 !== "" && $("#prev_remarks_xii").parent().hasClass("hide")){
		$("#prev_remarks_xii").parent().removeClass("hide");
		$("#remarks_xii").parent().removeClass("col-md-3");
		$("#remarks_xii").parent().addClass("col-md-2");
		$("#observe_xii").parent().removeClass("col-md-3");
		$("#observe_xii").parent().addClass("col-md-2");
	}
	if(pr_13 !== "" && $("#prev_remarks_xiii").parent().hasClass("hide")){
		$("#prev_remarks_xiii").parent().removeClass("hide");
		$("#remarks_xiii").parent().removeClass("col-md-3");
		$("#remarks_xiii").parent().addClass("col-md-2");
		$("#observe_xiii").parent().removeClass("col-md-3");
		$("#observe_xiii").parent().addClass("col-md-2");
	}
	*/
		$('#qcfile').bind('change', function () {
			  var filename = $("#qcfile").val();
			  if (/^\s*$/.test(filename)) {
			     $(".file-upload").removeClass('active');
			     $("#noFile1").text("No file chosen...");
			  }
			  else {
			     $(".file-upload").addClass('active');
			     $("#noFile1").text(filename.replace("C:\\fakepath\\", ""));
			  }
			});
		
		
 		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z ]+$/i.test(value);
			}, "Please enter characters only"); 
		
		
		$("#myForm-compliance-report").validate({
		  rules: {
			 
			formDate: {
			      required: true
			  },
			remarks_i_i: {
		      required: true
		    },
		    remarks_i_ii: {
			      required: true
			},
			remarks_ii: {
				required: true
			},
			remarks_iii: {
				required: true
			},
			remarks_iv: {
				required: true
			},
			remarks_v: {
				required: true
			},
			remarks_vi: {
				required: true
			},
			remarks_vii: {
				required: true
			},
			remarks_viii: {
				required: true
			},
			remarks_ix: {
				required: true
			},
			remarks_x: {
				required: true
			},
			remarks_xi: {
				required: true
			},
			remarks_xii: {
				required: true
			},
			remarks_xiii: {
				required: true
			},
			/* month: {
				required: true
			}, */
			employeeName: {
				required: true,
				lettersonly: true
			},
			designation:{
				required: true
			},
			date_3: {
				required: true
			},
			place:{
				required: true
			},
		  }
		}); 
		
		$("#myForm-compliance-report").on('submit', (function(e){ 
				
				e.preventDefault();
				
				if ($('input[name="qcfile"]').get(0).files.length === 0) {
		    	    console.log("No files selected.");
		    	    $("#error1").html("Please select a file to upload");
		    	    return false;
		    	}
		    	else if($('input[name="qcfile"]').get(0).files[0].name != "Quarterly Compliance Report.pdf"){
		    		console.log("No files selected.");
		    		$("#error1").html("Please upload Quarterly Compliance Report.pdf");
		    	    return false;
		    	}
				
				//$('#success_tic').modal('show');
	    		if($("#myForm-compliance-report" ).valid()){
	    		
	    		$("#sub").addClass("hide");
	    			
	    		var fd = new FormData($("#myForm-compliance-report")[0]);
		        
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
					
					fd.append("Quarterly_compliance_certificate_pdf_file", file);
		        
		        
		        $(".content").hide();
		        
		        $.ajax({
		            url: '${compformcustodianURL}',  
		            type: 'POST',
		            data: fd,
		            cache: false,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	$(".content").show();
		                if(data == "success"){
	   		            	$('#success_tic').modal('show');
			                $("#myForm-compliance-report")[0].reset();
	   		             	$('#success_tic').on('hidden.bs.modal', function (e) {
	   		             		location.reload(); 
	   		           		})
		                }
		            },
		            error: function() {
	            		$('#output').html('An error occured while submitting the form. Please try again');
	            		$('#errorExcel').modal('show');
		            },
		            complete: function(){

						console.log("Complete");
						if($("#sub").hasClass("hide")){
			            	$("#sub").removeClass("hide");
		            	}
			        }
		        });
		        
	    	}); 
				
	    	}
		}));
	});
</script>