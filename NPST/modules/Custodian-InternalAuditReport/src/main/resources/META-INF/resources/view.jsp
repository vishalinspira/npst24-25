
<%@page import="com.custodian.iar.util.CustodianIARUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.service.CustIARScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.CustIARScrutiny"%>
<%@page import="com.custodian.iar.util.Pre_Populate_scrutinydata"%>
<%@ include file="/init.jsp" %>

<%
	Pre_Populate_scrutinydata sd = new Pre_Populate_scrutinydata();
	sd.pre_populate_scrutiny_data(request);
	
	CustIARScrutiny custiarscrutiny = Validator.isNotNull(request.getAttribute("custIARScrutiny")) ? (CustIARScrutiny) request.getAttribute("custIARScrutiny") : CustIARScrutinyLocalServiceUtil.createCustIARScrutiny(0);
	
	System.out.println("JSP custiarscrutiny ------------------- "+custiarscrutiny.toString());
	
	JSONObject json_two = JSONFactoryUtil.createJSONObject();
	
	try {
		if(custiarscrutiny.getNps_trust_comments()!=null && !custiarscrutiny.getNps_trust_comments().trim().equals("")) {
			
			JSONObject json_one = JSONFactoryUtil.createJSONObject(custiarscrutiny.getNps_trust_comments());
				
 			json_two = CustodianIARUtil.merge(JSONFactoryUtil.createJSONObject(json_one.getString("Board Meeting")),JSONFactoryUtil.createJSONObject(json_one.getString("Operational Manual/Procedure")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Risk Management Policy")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Risk Management Committee")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Adequacy of Infrastructure")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Settlement Processing")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Scheme wise reconciliation of Holdings for each Pension Funds")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Dealing Procedure (Front office) for deals executed by Pension funds")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Back office Procedure")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Safe Keeping")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Asset Servicing")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Reporting to Pension Funds/NPS Trust")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Reporting Pension Funds / NPS Trust")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Accounting")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Valuation of Asset Under Custody (AUC)")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Disclosure")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Periodical returns to Authority/ NPS Trust")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Compliances")));
			
			json_two = CustodianIARUtil.merge(json_two,JSONFactoryUtil.createJSONObject(json_one.getString("Internal Audit/ Custodian/ Scheme Audit"))); 
					
/* 			System.out.println("get json_two ----------> "+json_two.toString()); */
					
		}
		
	} catch(Exception e) {
		
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
            <p  class="back_btn"><a class="backbtn" href="/web/guest/upload-custodian-maker"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
        </div>
     </div>
  </div>
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Internal Audit Report</h4>
            <form class="form" id="custodianAuditReport" action="#" method="post">
            	<input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
				<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                <div class="row">
	                <div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                     <div class="nps-input-box file-upload">
	                        <div class="nps-input-box mt-0">
	                           <div class="nps-input-box mt-0">
		                    		<label for="name">Report Due Date</label>
		                    		<input class="reportDate" type="date" value="${reportDate }" readonly="readonly">
		                		</div>
	                           <br>
	                        </div>
	                     </div>
	                 </div>
                 </div>
            	<div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Internal Audit Report (PDF upload)</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="iar-upload" id="iarFile" name="iarFile" accept=".pdf"/>
                           </div>
                           <label id="error1" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                     <div class="col-lg-6 col-md-6 col-sm-12 col-12 mt-5">
                        <div class="form-check-inline w-100">
                        	<textarea class="w-100" id="iar_rem" placeholder="Remarks if any" readonly="readonly" name="iar_rem"><%=custiarscrutiny.getIar_file_rem() == null ? "" : custiarscrutiny.getIar_file_rem() %></textarea>
                        </div>
                    </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div>
                         <p class="font-weight-bold mb-0">To,</p>
                         <p class="font-weight-bold mb-0">The Chief Executive Officer</p>
                         <p class="font-weight-bold">NPS Trust</p>
                         <p class="mb-0">First Floor, ICADR Building,</p>
                         <p class="mb-0">Plot No.6, Vasant Kunj Institutional Area, Phase- II</p>
                         <p class="mb-0">New Delhi - 110070</p>
                      </div>
                  </div>
               </div>
               <br>
               <p>AUDIT REPORT OF CUSTODIAL OPERATIONS FOR PENSION FUNDS FOR THE PERIOD FROM
               		<input type="date" class="border-0 date_1" id="date_1" name="date_1">
               		<label id="error2" class="error-message text-danger"></label>
               		<label>to</label>  
               		<input type="date" class="border-0 date_2" id="date_2" name="date_2">
               		<label id="error3" class="error-message text-danger"></label>
               </p>
               <br>
               <div class="statement-wrapper">
                  <div id="table" class="table-editable">
                     <div class="table-cont">
                        <table id="myTable_1" class="table css-serial table-responsive">
                           <thead>
                              <tr>
                                 <th class="col-1">Sr.No</th>
                                 <th class="col-2">Broad description</th>
                                 <th class="col-2">Sampling as required Under Guidance Note</th>
                                 <th class="col-1">Sampling Reported by Auditor</th>
                                 <th class="col-1">Risk</th>
                                 <th class="col-2">Sub - description</th>
                                 <th class="col">Observations</th>
                                 <th class="col">Management Response</th>
                                 <th class="col">NPS Trust Comments</th>	
                              </tr>
                           </thead>
                           <tbody>
                           	<!-- 1 -->
                              <tr>
                                 <td class="text-center" rowspan="5">1.</td>
                                 <td rowspan="5">Board Meeting</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="5">Low</td>
                                 <td>Composition of Board</td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_1" name="boardMeeting_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_1" name="boardMeeting_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_1" name="boardMeeting_Comm_1" readonly  value="<%=json_two.get("boardMeeting_Comm_1") == null ? "" : json_two.get("boardMeeting_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Frequency of meeting</td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_2" name="boardMeeting_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_2" name="boardMeeting_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_2" name="boardMeeting_Comm_2" readonly value="<%=json_two.get("boardMeeting_Comm_2") == null ? "" : json_two.get("boardMeeting_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td></td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_3" name="boardMeeting_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_3" name="boardMeeting_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_3" name="boardMeeting_Comm_3" readonly value="<%=json_two.get("boardMeeting_Comm_3") == null ? "" : json_two.get("boardMeeting_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance with observations of the Board</td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_4" name="boardMeeting_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_4" name="boardMeeting_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_4" name="boardMeeting_Comm_4" readonly value="<%=json_two.get("boardMeeting_Comm_4") == null ? "" : json_two.get("boardMeeting_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Delegation of Authority</td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_5" name="boardMeeting_Observe_5">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_5" name="boardMeeting_Res_5">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_5" name="boardMeeting_Comm_5" readonly value="<%=json_two.get("boardMeeting_Comm_5") == null ? "" : json_two.get("boardMeeting_Comm_5") %>">
                                 </td>
                              </tr>
                              <!-- 2 -->
                              <tr>
                                 <td class="text-center"  rowspan="4">2.</td>
                                 <td rowspan="4">Operational Manual/Procedure</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="4">Low</td>
                                 <td>To be approved by Board</td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Observe_1" name="operational_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Res_1" name="operational_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="operational_Comm" id="operational_Comm_1" name="operational_Comm_1" readonly value="<%=json_two.get("operational_Comm_1") == null ? "" : json_two.get("operational_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Amendments, if any, to be approved by the board</td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Observe_2" name="operational_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Res_2" name="operational_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="operational_Comm" id="operational_Comm_2" name="operational_Comm_2" readonly value="<%=json_two.get("operational_Comm_2") == null ? "" : json_two.get("operational_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Frequency of review</td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Observe_3" name="operational_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Res_3" name="operational_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="operational_Comm" id="operational_Comm_3" name="operational_Comm_3" readonly value="<%=json_two.get("operational_Comm_3") == null ? "" : json_two.get("operational_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance with Procedure for security documents execution as laid down Operational Manual.</td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Observe_4" name="operational_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Res_4" name="operational_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="operational_Comm" id="operational_Comm_4" name="operational_Comm_4" readonly value="<%=json_two.get("operational_Comm_4") == null ? "" : json_two.get("operational_Comm_4") %>">
                                 </td>
                              </tr>
                              <!-- 3 -->
                              <tr>
                                 <td class="text-center" rowspan="9">3.</td>
                                 <td rowspan="9">Risk Management Policy</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="9">Low</td>
                                 <td>Approved by Board of Director</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_1" name="risk_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_1" name="risk_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_1" name="risk_Comm_1" readonly value="<%=json_two.get("risk_Comm_1") == null ? "" : json_two.get("risk_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Frequency of review </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_2" name="risk_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_2" name="risk_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_2" name="risk_Comm_2" readonly value="<%=json_two.get("risk_Comm_2") == null ? "" : json_two.get("risk_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Disaster recovery strategy</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_3" name="risk_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_3" name="risk_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_3" name="risk_Comm_3" readonly value="<%=json_two.get("risk_Comm_3") == null ? "" : json_two.get("risk_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Business Continuity Plan</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_4" name="risk_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_4" name="risk_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_4" name="risk_Comm_4" readonly value="<%=json_two.get("risk_Comm_4") == null ? "" : json_two.get("risk_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>IT System Audit</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_5" name="risk_Observe_5">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_5" name="risk_Res_5">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_5" name="risk_Comm_5" readonly value="<%=json_two.get("risk_Comm_5") == null ? "" : json_two.get("risk_Comm_5") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Data Integrity</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_6" name="risk_Observe_6">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_6" name="risk_Res_6">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_6" name="risk_Comm_6" readonly value="<%=json_two.get("risk_Comm_6") == null ? "" : json_two.get("risk_Comm_6") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Operational risk management</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_7" name="risk_Observe_7">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_7" name="risk_Res_7">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_7" name="risk_Comm_7" readonly value="<%=json_two.get("risk_Comm_7") == null ? "" : json_two.get("risk_Comm_7") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Employee dealing Guidelines</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_8" name="risk_Observe_8">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_8" name="risk_Res_8">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_8" name="risk_Comm_8" readonly value="<%=json_two.get("risk_Comm_8") == null ? "" : json_two.get("risk_Comm_8") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Comprehensive Insurance cover against risk</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_9" name="risk_Observe_9">
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_9" name="risk_Res_9">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_9" name="risk_Comm_9" readonly value="<%=json_two.get("risk_Comm_9") == null ? "" : json_two.get("risk_Comm_9") %>">
                                 </td>
                              </tr>
                              <!-- 4 -->
                              <tr>
                                 <td class="text-center" rowspan="4">4.</td>
                                 <td rowspan="4">Risk Management Committee</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="4">Low</td>
                                 <td>Composition of Risk Committee</td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Observe_1" name="riskCommitee_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Res_1" name="riskCommitee_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee_Comm" id="riskCommitee_Comm_1" name="riskCommitee_Comm_1" readonly value="<%=json_two.get("riskCommitee_Comm_1") == null ? "" : json_two.get("riskCommitee_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Frequency of meeting</td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Observe_2" name="riskCommitee_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Res_2" name="riskCommitee_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee_Comm" id="riskCommitee_Comm_2" name="riskCommitee_Comm_2" readonly value="<%=json_two.get("riskCommitee_Comm_2") == null ? "" : json_two.get("riskCommitee_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Minutes of meeting</td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Observe_3" name="riskCommitee_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Res_3" name="riskCommitee_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee_Comm" id="riskCommitee_Comm_3" name="riskCommitee_Comm_3" readonly value="<%=json_two.get("riskCommitee_Comm_3") == null ? "" : json_two.get("riskCommitee_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Implementation of observations of the risk management committee</td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Observe_4" name="riskCommitee_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Res_4" name="riskCommitee_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee_Comm" id="riskCommitee_Comm_4" name="riskCommitee_Comm_4" readonly value="<%=json_two.get("riskCommitee_Comm_4") == null ? "" : json_two.get("riskCommitee_Comm_4") %>">
                                 </td>
                              </tr>
                              <!-- 5 -->
                              <tr>
                                 <td class="text-center" rowspan="4">5.</td>
                                 <td rowspan="4">Adequacy of Infrastructure</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="4">Low</td>
                                 <td>Adequacy of Physical Infrastructure</td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Observe_1" name="infra_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Res_1" name="infra_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="infra_Comm" id="infra_Comm_1" name="infra_Comm_1" readonly value="<%=json_two.get("infra_Comm_1") == null ? "" : json_two.get("infra_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequacy of IT Infrastructure</td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Observe_2" name="infra_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Res_2" name="infra_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="infra_Comm" id="infra_Comm_2" name="infra_Comm_2" readonly value="<%=json_two.get("infra_Comm_2") == null ? "" : json_two.get("infra_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequacy of information Security Infrastructure</td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Observe_3" name="infra_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Res_3" name="infra_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="infra_Comm" id="infra_Comm_3" name="infra_Comm_3" readonly value="<%=json_two.get("infra_Comm_3") == null ? "" : json_two.get("infra_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequacy of HR Infrastructure</td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Observe_4" name="infra_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Res_4" name="infra_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="infra_Comm" id="infra_Comm_4" name="infra_Comm_4" readonly value="<%=json_two.get("infra_Comm_4") == null ? "" : json_two.get("infra_Comm_4") %>">
                                 </td>
                              </tr>
                              <!-- 6 -->
                              <tr>
                                 <td class="text-center" rowspan="5">6.</td>
                                 <td rowspan="5">Settlement Processing</td>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td class="text-center" rowspan="5">Low</td>
                                 <td>Receipt of orders from Pension Funds</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_1" name="processing_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_1" name="processing_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_1" name="processing_Comm_1" readonly value="<%=json_two.get("processing_Comm_1") == null ? "" : json_two.get("processing_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Order matching with files received from SE's and confirmation of match orders to SE's</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_2" name="processing_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_2" name="processing_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_2" name="processing_Comm_2" readonly value="<%=json_two.get("processing_Comm_2") == null ? "" : json_two.get("processing_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Fund Settlement</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_3" name="processing_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_3" name="processing_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_3" name="processing_Comm_3" readonly value="<%=json_two.get("processing_Comm_3") == null ? "" : json_two.get("processing_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Confirmed and unconfirmed trades</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_4" name="processing_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_4" name="processing_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_4" name="processing_Comm_4" readonly value="<%=json_two.get("processing_Comm_4") == null ? "" : json_two.get("processing_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Securities settlement</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_5" name="processing_Observe_5">
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_5" name="processing_Res_5">
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_5" name="processing_Comm_5" readonly value="<%=json_two.get("processing_Comm_5") == null ? "" : json_two.get("processing_Comm_5") %>">
                                 </td>
                              </tr>
                              <!-- 7 -->
                              <tr>
                                 <td class="text-center" rowspan="7">7.</td>
                                 <td rowspan="7">Scheme wise reconciliation of Holdings for each Pension Funds</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="7">Low</td>
                                 <td>Scheme CG, SG, NPS LITE</td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Observe_1" name="holdings_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_1" name="holdings_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_1" name="holdings_Comm_1" readonly value="<%=json_two.get("holdings_Comm_1") == null ? "" : json_two.get("holdings_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Scheme E Tier I & II</td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Observe_2" name="holdings_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_2" name="holdings_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_2" name="holdings_Comm_2" readonly value="<%=json_two.get("holdings_Comm_2") == null ? "" : json_two.get("holdings_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Scheme C Tier I & II</td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Observe_3" name="holdings_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_3" name="holdings_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_3" name="holdings_Comm_3" readonly value="<%=json_two.get("holdings_Comm_3") == null ? "" : json_two.get("holdings_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Scheme G Tier I & II</td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Observe_4" name="holdings_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_4" name="holdings_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_4" name="holdings_Comm_4" readonly value="<%=json_two.get("holdings_Comm_4") == null ? "" : json_two.get("holdings_Comm_4") %>">
                                 </td>
                              </tr>
                              
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Corporate CG scheme</td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Observe_5" name="holdings_Observe_5">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_5" name="holdings_Res_5">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_5" name="holdings_Comm_5" readonly value="<%=json_two.get("holdings_Comm_5") == null ? "" : json_two.get("holdings_Comm_5") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>APY or Any other scheme regulated by PFRDA</td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Observe_6" name="holdings_Observe_6">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_6" name="holdings_Res_6">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_6" name="holdings_Comm_6" readonly value="<%=json_two.get("holdings_Comm_6") == null ? "" : json_two.get("holdings_Comm_6") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Reconciliation between Pension Scheme a/c and CGSL</td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Observe_7" name="holdings_Observe_7">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_7" name="holdings_Res_7">
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_7" name="holdings_Comm_7" readonly value="<%=json_two.get("holdings_Comm_7") == null ? "" : json_two.get("holdings_Comm_7") %>">
                                 </td>
                              </tr>
                               <!-- 8 -->
                              <tr>
                                 <td class="text-center" rowspan="2">8.</td>
                                 <td rowspan="2">Dealing Procedure (Front office) for deals executed by Pension funds</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="2">Low</td>
                                 <td>Installation of voice Recording Machine</td>
                                 <td>
                                    <input type="text" class="deals" id="deals_Observe_1" name="deals_Observe_1">
                                 </td>
                                 <td rowspan="2">
                                    <input type="text" class="deals" id="deals_Res_1" name="deals_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="deals_Comm" id="deals_Comm_1" name="deals_Comm_1" readonly value="<%=json_two.get("deals_Comm_1") == null ? "" : json_two.get("deals_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>System and procedure of dealing in Equity and Debt</td>
                                 <td>
                                    <input type="text" class="deals" id="deals_Observe_2" name="deals_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="deals_Comm" id="deals_Comm_2" name="deals_Comm_2" readonly value="<%=json_two.get("deals_Comm_2") == null ? "" : json_two.get("deals_Comm_2") %>">
                                 </td>
                              </tr>
                              <!-- 9 -->
                              <tr>
                                 <td class="text-center" rowspan="4">9.</td>
                                 <td rowspan="4">Back office Procedure</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="4">Low</td>
                                 <td>Deployment of adequate staff</td>
                                 <td>
                                    <input type="text" class="office" id="office_Observe_1" name="office_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="office" id="office_Res_1" name="office_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="office_Comm" id="office_Comm_1" name="office_Comm_1" readonly value="<%=json_two.get("office_Comm_1") == null ? "" : json_two.get("office_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>No password sharing between officers</td>
                                 <td>
                                    <input type="text" class="office" id="office_Observe_2" name="office_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="office" id="office_Res_2" name="office_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="office_Comm" id="office_Comm_2" name="office_Comm_2" readonly value="<%=json_two.get("office_Comm_2") == null ? "" : json_two.get("office_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Accounting and settlement of deal</td>
                                 <td>
                                    <input type="text" class="office" id="office_Observe_3" name="office_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="office" id="office_Res_3" name="office_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="office_Comm" id="office_Comm_3" name="office_Comm_3" readonly value="<%=json_two.get("office_Comm_3") == null ? "" : json_two.get("office_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Deal execution through STP</td>
                                 <td>
                                    <input type="text" class="office" id="office_Observe_4" name="office_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="office" id="office_Res_4" name="office_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="office_Comm" id="office_Comm_4" name="office_Comm_4" readonly value="<%=json_two.get("office_Comm_4") == null ? "" : json_two.get("office_Comm_4") %>">
                                 </td>
                              </tr>
                              <!-- 10 -->
                              <tr>
                                 <td class="text-center" rowspan="7">10.</td>
                                 <td rowspan="7">Safe Keeping</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="7">Low</td>
                                 <td>Crediting securities to the designated demat account of specified Pension Funds</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_1" name="safe_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_1" name="safe_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_1" name="safe_Comm_1" readonly value="<%=json_two.get("safe_Comm_1") == null ? "" : json_two.get("safe_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Intimating about free holdings in the securities a/c to the Pension Funds</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_2" name="safe_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_2" name="safe_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_2" name="safe_Comm_2" readonly value="<%=json_two.get("safe_Comm_2") == null ? "" : json_two.get("safe_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Physical securities if any are properly coded and stored in High security vaults</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_3" name="safe_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_3" name="safe_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_3" name="safe_Comm_3" readonly value="<%=json_two.get("safe_Comm_3") == null ? "" : json_two.get("safe_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Closed circuit TV with hidden camera</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_4" name="safe_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_4" name="safe_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_4" name="safe_Comm_4" readonly value="<%=json_two.get("safe_Comm_4") == null ? "" : json_two.get("safe_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Access Control</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_5" name="safe_Observe_5">
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_5" name="safe_Res_5">
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_5" name="safe_Comm_5" readonly value="<%=json_two.get("safe_Comm_5") == null ? "" : json_two.get("safe_Comm_5") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Full time security staff</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_6" name="safe_Observe_6">
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_6" name="safe_Res_6">
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_6" name="safe_Comm_6" readonly value="<%=json_two.get("safe_Comm_6") == null ? "" : json_two.get("safe_Comm_6") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequate Insurance of securities</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_7" name="safe_Observe_7">
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_7" name="safe_Res_7">
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_7" name="safe_Comm_7" readonly value="<%=json_two.get("safe_Comm_7") == null ? "" : json_two.get("safe_Comm_7") %>">
                                 </td>
                              </tr>
                              <!-- 11 -->
                              <tr>
                                 <td class="text-center" rowspan="4">11.</td>
                                 <td rowspan="4">Asset Servicing</td>
                                 <td>50%</td>
                                 <td>50%</td>
                                 <td class="text-center" rowspan="4">Low</td>
                                 <td>Event tracking</td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Observe_1" name="asset_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Res_1" name="asset_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="asset_Comm" id="asset_Comm_1" name="asset_Comm_1" readonly value="<%=json_two.get("asset_Comm_1") == null ? "" : json_two.get("asset_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Eligibility computation for all types of events both monetary and non-monetary</td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Observe_2" name="asset_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Res_2" name="asset_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="asset_Comm" id="asset_Comm_2" name="asset_Comm_2" readonly value="<%=json_two.get("asset_Comm_2") == null ? "" : json_two.get("asset_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Timely receipt of all the dues from the Issuer and registrar of companies</td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Observe_3" name="asset_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Res_3" name="asset_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="asset_Comm" id="asset_Comm_3" name="asset_Comm_3" readonly value="<%=json_two.get("asset_Comm_3") == null ? "" : json_two.get("asset_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Receipt and matching of Corporate Actions</td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Observe_4" name="asset_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Res_4" name="asset_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="asset_Comm" id="asset_Comm_4" name="asset_Comm_4" readonly value="<%=json_two.get("asset_Comm_4") == null ? "" : json_two.get("asset_Comm_4") %>">
                                 </td>
                              </tr>
                              <!-- 12 -->
                              <tr>
                                 <td class="text-center" rowspan="5">12.</td>
                                 <td rowspan="5">Reporting to Pension Funds/NPS Trust</td>
                                 <td>50%</td>
                                 <td>50%</td>
                                 <td class="text-center" rowspan="5">Low</td>
                                 <td>Daily saleable Holding Report</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_1" name="reportingToPF_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_1" name="reportingToPF_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_1" name="reportingToPF_Comm_1" readonly value="<%=json_two.get("reportingToPF_Comm_1") == null ? "" : json_two.get("reportingToPF_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50%</td>
                                 <td>50%</td>
                                 <td>Daily transaction statement (on next day)</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_2" name="reportingToPF_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_2" name="reportingToPF_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_2" name="reportingToPF_Comm_2" readonly value="<%=json_two.get("reportingToPF_Comm_2") == null ? "" : json_two.get("reportingToPF_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Daily shortage report</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_3" name="reportingToPF_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_3" name="reportingToPF_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_3" name="reportingToPF_Comm_3" readonly value="<%=json_two.get("reportingToPF_Comm_3") == null ? "" : json_two.get("reportingToPF_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50%</td>
                                 <td>50%</td>
                                 <td>Report of Corporate action</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_4" name="reportingToPF_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_4" name="reportingToPF_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_4" name="reportingToPF_Comm_4" readonly value="<%=json_two.get("reportingToPF_Comm_4") == null ? "" : json_two.get("reportingToPF_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Corporate Action receipt reports on daily basis.</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_5" name="reportingToPF_Observe_5">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_5" name="reportingToPF_Res_5">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_5" name="reportingToPF_Comm_5" readonly value="<%=json_two.get("reportingToPF_Comm_5") == null ? "" : json_two.get("reportingToPF_Comm_5") %>">
                                 </td>
                              </tr>
                              <!-- 13 -->
                              <tr>
                                 <td class="text-center" rowspan="4">13.</td>
                                 <td rowspan="4">Reporting Pension Funds / NPS Trust</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="4">Low</td>
                                 <td>Put & Call Intimation</td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Observe_1" name="reportingToNps_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Res_1" name="reportingToNps_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps_Comm" id="reportingToNps_Comm_1" name="reportingToNps_Comm_1" readonly value="<%=json_two.get("reportingToNps_Comm_1") == null ? "" : json_two.get("reportingToNps_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Outstanding Corporate Action</td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Observe_2" name="reportingToNps_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Res_2" name="reportingToNps_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps_Comm" id="reportingToNps_Comm_2" name="reportingToNps_Comm_2" readonly value="<%=json_two.get("reportingToNps_Comm_2") == null ? "" : json_two.get("reportingToNps_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Ex-Date Report</td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Observe_3" name="reportingToNps_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Res_3" name="reportingToNps_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps_Comm" id="reportingToNps_Comm_3" name="reportingToNps_Comm_3" readonly value="<%=json_two.get("reportingToNps_Comm_3") == null ? "" : json_two.get("reportingToNps_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>MIS to NPS Trust and PFRDA</td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Observe_4" name="reportingToNps_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Res_4" name="reportingToNps_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps_Comm" id="reportingToNps_Comm_4" name="reportingToNps_Comm_4" readonly value="<%=json_two.get("reportingToNps_Comm_4") == null ? "" : json_two.get("reportingToNps_Comm_4") %>">
                                 </td>
                              </tr>
                              <!-- 14 -->
                              <tr>
                                 <td class="text-center" rowspan="7">14.</td>
                                 <td rowspan="7">Accounting</td>
                                 <td>25%</td>
                                 <td>25%</td>
                                 <td class="text-center" rowspan="7">Low</td>
                                 <td>Deal tickets</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_1" name="accounting_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_1" name="accounting_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_1" name="accounting_Comm_1" readonly value="<%=json_two.get("accounting_Comm_1") == null ? "" : json_two.get("accounting_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>25%</td>
                                 <td>25%</td>
                                 <td>DIS/DIP statement & intimation to the custodian</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_2" name="accounting_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_2" name="accounting_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_2" name="accounting_Comm_2" readonly value="<%=json_two.get("accounting_Comm_2") == null ? "" : json_two.get("accounting_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>25%</td>
                                 <td>25%</td>
                                 <td>Verification of timely and accurate Settlement of trade</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_3" name="accounting_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_3" name="accounting_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_3" name="accounting_Comm_3" readonly value="<%=json_two.get("accounting_Comm_3") == null ? "" : json_two.get("accounting_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance with accounting standards</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_4" name="accounting_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_4" name="accounting_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_4" name="accounting_Comm_4" readonly value="<%=json_two.get("accounting_Comm_4") == null ? "" : json_two.get("accounting_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adherence to accounting policy</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_5" name="accounting_Observe_5">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_5" name="accounting_Res_5">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_5" name="accounting_Comm_5" readonly value="<%=json_two.get("accounting_Comm_5") == null ? "" : json_two.get("accounting_Comm_5") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>25%</td>
                                 <td>25%</td>
                                 <td>Corporate action- bonus, rights, dividend, interest receivable</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_6" name="accounting_Observe_6">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_6" name="accounting_Res_6">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_6" name="accounting_Comm_6" readonly value="<%=json_two.get("accounting_Comm_6") == null ? "" : json_two.get("accounting_Comm_6") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Outsourcing (100% of all items under the scope of accounting, if it is outsourced)</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_7" name="accounting_Observe_7">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_7" name="accounting_Res_7">
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_7" name="accounting_Comm_7" readonly value="<%=json_two.get("accounting_Comm_7") == null ? "" : json_two.get("accounting_Comm_7") %>">
                                 </td>
                              </tr>
                              <!-- 15 -->
                              <tr>
                                 <td class="text-center" rowspan="3">15.</td>
                                 <td rowspan="3">Valuation of Asset Under Custody (AUC)</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="3">Low</td>
                                 <td>Valuation of AUC – Equity as per guidelines</td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Observe_1" name="auc_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Res_1" name="auc_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="auc_Comm" id="auc_Comm_1" name="auc_Comm_1" readonly value="<%=json_two.get("auc_Comm_1") == null ? "" : json_two.get("auc_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50%</td>
                                 <td>50%</td>
                                 <td>Valuation of AUC – Debt as per guidelines</td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Observe_2" name="auc_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Res_2" name="auc_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="auc_Comm" id="auc_Comm_2" name="auc_Comm_2" readonly value="<%=json_two.get("auc_Comm_2") == null ? "" : json_two.get("auc_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>10%</td>
                                 <td>10%</td>
                                 <td>Deviations, if any</td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Observe_3" name="auc_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Res_3" name="auc_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="auc_Comm" id="auc_Comm_3" name="auc_Comm_3" readonly value="<%=json_two.get("auc_Comm_3") == null ? "" : json_two.get("auc_Comm_3") %>">
                                 </td>
                              </tr>
                               <!-- 16 -->
                              <tr>
                                 <td class="text-center" rowspan="2">16.</td>
                                 <td rowspan="2">Disclosure</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="2">Low</td>
                                 <td>Half yearly financial statement</td>
                                 <td>
                                    <input type="text" class="disclosure" id="disclosure_Observe_1" name="disclosure_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="disclosure" id="disclosure_Res_1" name="disclosure_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="disclosure_Comm" id="disclosure_Comm_1" name="disclosure_Comm_1" readonly value="<%=json_two.get("disclosure_Comm_1") == null ? "" : json_two.get("disclosure_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Annual report of Custodian</td>
                                 <td>
                                    <input type="text" class="disclosure" id="disclosure_Observe_2" name="disclosure_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="disclosure" id="disclosure_Res_2" name="disclosure_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="disclosure_Comm" id="disclosure_Comm_2" name="disclosure_Comm_2" readonly value="<%=json_two.get("disclosure_Comm_2") == null ? "" : json_two.get("disclosure_Comm_2") %>">
                                 </td>
                              </tr>
                              <!-- 17 -->
                              <tr>
                                 <td class="text-center" rowspan="4">17.</td>
                                 <td rowspan="4">Periodical returns to Authority/ NPS Trust</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="4">Low</td>
                                 <td>In respect of reports submitted to PFRDA and NPST:-</td>
                                 <td>
                                    <input type="text" class="periodical" id="periodical_Observe_1" name="periodical_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="periodical" id="periodical_Res_1" name="periodical_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="periodical_Comm" id="periodical_Comm_1" name="periodical_Comm_1" readonly value="<%=json_two.get("periodical_Comm_1") == null ? "" : json_two.get("periodical_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Timely submission</td>
                                 <td>
                                    <input type="text" class="periodical" id="periodical_Observe_2" name="periodical_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="periodical" id="periodical_Res_2" name="periodical_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="periodical_Comm" id="periodical_Comm_2" name="periodical_Comm_2" readonly value="<%=json_two.get("periodical_Comm_2") == null ? "" : json_two.get("periodical_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Accuracy of data</td>
                                 <td rowspan="2">
                                    <input type="text" class="periodical" id="periodical_Observe_3" name="periodical_Observe_3">
                                 </td>
                                 <td rowspan="2">
                                    <input type="text" class="periodical" id="periodical_Res_3" name="periodical_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="periodical_Comm" id="periodical_Comm_3" name="periodical_Comm_3" readonly value="<%=json_two.get("periodical_Comm_3") == null ? "" : json_two.get("periodical_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Procedure of generation of data and report</td>
                                 <td>
                                    <input type="text" class="periodical_Comm" id="periodical_Comm_4" name="periodical_Comm_4" readonly value="<%=json_two.get("periodical_Comm_4") == null ? "" : json_two.get("periodical_Comm_4") %>">
                                 </td>
                              </tr>
                              <!-- 18 -->
                              <tr>
                                 <td class="text-center" rowspan="4">18.</td>
                                 <td rowspan="4">Compliances</td>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td class="text-center" rowspan="4">Low</td>
                                 <td>Compliance to clauses of Tripartite Agreement</td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Observe_1" name="compliances_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Res_1" name="compliances_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances_Comm" id="compliances_Comm_1" name="compliances_Comm_1" readonly value="<%=json_two.get("compliances_Comm_1") == null ? "" : json_two.get("compliances_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance to Guidelines and Guidance note</td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Observe_2" name="compliances_Observe_2">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Res_2" name="compliances_Res_2">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances_Comm" id="compliances_Comm_2" name="compliances_Comm_2" readonly value="<%=json_two.get("compliances_Comm_2") == null ? "" : json_two.get("compliances_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance to Internal Guidelines, Operational manual</td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Observe_3" name="compliances_Observe_3">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Res_3" name="compliances_Res_3">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances_Comm" id="compliances_Comm_3" name="compliances_Comm_3" readonly value="<%=json_two.get("compliances_Comm_3") == null ? "" : json_two.get("compliances_Comm_3") %>">
                                 </td>
                                 
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequacy and efficacy of Internal Control system and procedures</td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Observe_4" name="compliances_Observe_4">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Res_4" name="compliances_Res_4">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances_Comm" id="compliances_Comm_4" name="compliances_Comm_4" readonly value="<%=json_two.get("compliances_Comm_4") == null ? "" : json_two.get("compliances_Comm_4") %>">
                                 </td>
                              </tr>
                              <!-- 19 -->
                              <tr>
                                 <td class="text-center">19.</td>
                                 <td>Internal Audit/ Custodian/ Scheme Audit</td>
                                 <td></td>
                                 <td></td>
                                 <td class="text-center">Low</td>
                                 <td>To see the exceptions of previous internal audit & compliance there of</td>
                                 <td>
                                    <input type="text" class="internalAudit" id="internalAudit_Observe_1" name="internalAudit_Observe_1">
                                 </td>
                                 <td>
                                    <input type="text" class="internalAudit" id="internalAudit_Res_1" name="internalAudit_Res_1">
                                 </td>
                                 <td>
                                    <input type="text" class="internalAudit_Comm" id="internalAudit_Comm_1" name="internalAudit_Comm_1" readonly value="<%=json_two.get("internalAudit_Comm_1") == null ? "" : json_two.get("internalAudit_Comm_1") %>">
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                        <br><br>
                     </div>
                  </div>
               </div>
               
               <p>Certified that the information given, herein are correct and complete to the best of my knowledge and belief.</p>
               
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Name: </label>
                        <input type="text" class="employeeName" id="employeeName" name="employeeName">
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <!--  <label for="designation">Designation:</label><br>
                     <select class="w-100" name="designation" id="designation">
                        <option value="">Select</option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                     </select> -->
                     
                      <label for="roles">Designation</label><br>
		                  <input type="text" class="designation" id="designation" name="designation">
                     <label id="error-designation" class="error-message text-danger"></label>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Date: </label>
                        <input type="date" class="border-0 date_3" id="date_3" name="date_3">
                     </div>
                  </div>
               </div>
               <br>
               <div class="row text-center" id="sub">
                  <div class="col-md-12">
                     <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
                  </div>
               </div>
            </form>
         </div>
      </div>
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
	
	.modal {
    display: none;
	}

</style>

<script type="text/javascript">

$(document).ready(function() {

	
	/* render file name in choose file starts */
	$('#iarFile').bind('change', function () {
		  var filename = $("#iarFile").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	/* render file name in choose file ends */
	
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z ]+$/i.test(value);
		}, "Please enter characters only");
	
	$.validator.addMethod("dateCheck", function(value, element) {
	    if (!/Invalid|NaN/.test(new Date(value))) {
	        return new Date(value) >= new Date($("#date_1").val());
	    }

	    return isNaN(value) && isNaN($(params).val()) 
	        || (Number(value) >= Number($("#date_1").val())); 
		}, 'Must be greater than From Date.');
	
	 $("#custodianAuditReport").validate({
	  	rules: {
	  		/* iarFile:{
				required: true
			}, */
			date_1: {
		      	required: true
		    },
		    date_2: {
			    required: true,
			    dateCheck: true
			},
			date_3: {
			    required: true
			},
			designation: {
		      	required: true
		    },
			employeeName: {
		      	required: true,
		      	lettersonly: true
		    },
			
	  },
	  errorPlacement: function (error, element) {
         /* if (element.attr("name") == "iarFile") {
              error.appendTo("#error1");
          }  */
         if (element.attr("name") == "date_1") {
              error.appendTo("#error2");
          } else if (element.attr("name") == "date_2") {
              error.appendTo("#error3");
          } else if (element.attr("name") == "designation") {
              error.appendTo("#error-designation");
          } else {
        	  error.appendTo(element.parent().parent().after());
         }
      },

	}); 
	
	 $('.boardMeeting').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.operational').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.risk').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.riskCommitee').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.infra').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.processing').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.holdings').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.deals').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.office').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.safe').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.asset').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.reportingToPF').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.reportingToNps').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.accounting').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.auc').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.disclosure').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.periodical').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.compliances').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.internalAudit').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$("#custodianAuditReport").on('submit', (function(e) {
		console.log("Inside ajax");
		
		e.preventDefault();
		
		if ($('input[name="iarFile"]').get(0).files.length === 0) {
    	    console.log("No files selected.");
    	    $("#error1").html("Please select a file to upload");
    	    return false;
    	}
    	else if($('input[name="iarFile"]').get(0).files[0].name != "Internal Audit Report.pdf"){
    		console.log("No files selected.");
    		$("#error1").html("Please upload Internal Audit Report.pdf");
    	    return false;
    	}
		
			
			if($( "#custodianAuditReport" ).valid()){
				
					$("#sub").addClass("hide");
				
					var formData = new FormData($("form.form")[0]);
					
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
						
						formData.append("Custodian_Internal_Audit_Report_pdf_file", file);
					
					$(".animaion").show();
					$.ajax({
			            type: "POST",
			            enctype: 'multipart/form-data',
			            processData: false,
			            contentType: false,
			            cache: false,
			            url: '${custodianIARResourceURL}',
			            data: formData,
			            success: function(data) {
			            	$(".animaion").hide();
			            	if(data == "Success") {
		   		            	$('#success_tic').modal('show');
		   		         		$("#custodianAuditReport")[0].reset();
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

			}

		}));
    
});

</script>