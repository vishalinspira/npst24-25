<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="com.custodian.iar.util.NPSUserPrepopulateScrutinyData"%>
<%@page import="com.daily.average.service.service.CustIARScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.CustIARScrutiny"%>
<%@page import="com.custodian.iar.util.Pre_Populate_scrutinydata"%>
<%@page import="com.custodian.iar.util.CustodianIARUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.daily.average.service.service.CustIARLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.CustIAR"%>
<%@ include file="/init.jsp" %>

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
    
boolean isNonNPSUser = Validator.isNotNull(request.getAttribute("isNonNPSUser")) ? (boolean) request.getAttribute("isNonNPSUser") : false;

System.out.println("isNonNPSUser asset rendererrr:::::" + isNonNPSUser);
	
	
	CustIAR custIAR = Validator.isNotNull(request.getAttribute("custIAR")) ? (CustIAR) request.getAttribute("custIAR") : CustIARLocalServiceUtil.createCustIAR(0);
	
	JSONObject json_two = JSONFactoryUtil.createJSONObject();
	
	try {
		if(custIAR.getIar_details()!=null) {
			
			JSONObject json_one = JSONFactoryUtil.createJSONObject(custIAR.getIar_details());
				
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
	
	/* Pre populate data for asset view */
	
	NPSUserPrepopulateScrutinyData sd = new NPSUserPrepopulateScrutinyData();
	sd.prePopulateScrutinyData(request);
	
	CustIARScrutiny custiarscrutiny = Validator.isNotNull(request.getAttribute("custIARScrutiny")) ? (CustIARScrutiny) request.getAttribute("custIARScrutiny") : CustIARScrutinyLocalServiceUtil.createCustIARScrutiny(0);
	
	JSONObject json_four = JSONFactoryUtil.createJSONObject();
	
	try {
		if(custiarscrutiny.getNps_trust_comments()!=null && !custiarscrutiny.getNps_trust_comments().trim().equals("")) {
			
			JSONObject json_three = JSONFactoryUtil.createJSONObject(custiarscrutiny.getNps_trust_comments());
				
 			json_four = CustodianIARUtil.merge(JSONFactoryUtil.createJSONObject(json_three.getString("Board Meeting")),JSONFactoryUtil.createJSONObject(json_three.getString("Operational Manual/Procedure")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Risk Management Policy")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Risk Management Committee")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Adequacy of Infrastructure")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Settlement Processing")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Scheme wise reconciliation of Holdings for each Pension Funds")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Dealing Procedure (Front office) for deals executed by Pension funds")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Back office Procedure")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Safe Keeping")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Asset Servicing")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Reporting to Pension Funds/NPS Trust")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Reporting Pension Funds / NPS Trust")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Accounting")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Valuation of Asset Under Custody (AUC)")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Disclosure")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Periodical returns to Authority/ NPS Trust")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Compliances")));
			
			json_four = CustodianIARUtil.merge(json_four,JSONFactoryUtil.createJSONObject(json_three.getString("Internal Audit/ Custodian/ Scheme Audit"))); 
		}
	} catch(Exception e) {
		
	}
	List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");

	boolean isCustodianSupervisor  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_SUPERVISOR, false);
	boolean isDocSigned = false;
	JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
	String fileEntryId = "0";
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
<div class="nps-page-main nps-upload-report nps-statement-wrapper">
   <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-repor">
            <h4>Internal Audit Report</h4>
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
					
					JSONArray fileList = object.getJSONArray("fileList");
					if(Validator.isNotNull(fileList) && fileList.length() > 0){%>
					<%=object.getString("version")+" :- " %>
					<%for(int j = 0; j<fileList.length(); j++){%>
						<span class="file-preview-span" data-index='<%= i%>'> <a href="<%=fileList.getJSONObject(j).getString("downloadURL")%>"> <%=" Attachment:"+(j+1)%></a></span>
							<% if(j != (fileList.length()-1)){ %>
							 ,
							<% } %>
					<%} %><br>
				<%}else{
				%>
					
					<span class="file-preview-span" data-index='<%= i%>'> <a href="<%=object.getString("downloadURL")%>"><%=object.getString("version")%></a></span>
					<% if(i != (urlArray.length()-1)){ %>
					 ,
					<% } %>
				<% } 
				 }%>
				 
				 
				</div>
				<% if(isCustodianSupervisor){ %>
					<div>
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
            <form class="form" id="custodianAuditReportScrutiny" action="#" method="post">
            	<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="<%=custIAR.getReportUploadLogId()%>"/>
				<input type="hidden" value="<%=custIAR.getReportMasterId()%> name="reportMasterId" class="reportMasterId"/>
               <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Internal Audit report</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Internal_audit_reportURL ? "javascript:void(0);" : Internal_audit_reportURL}" ${empty Internal_audit_reportURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="iar_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "disabled": "" %> name="iar_rem"><%=custiarscrutiny.getIar_file_rem() == null ? "" : custiarscrutiny.getIar_file_rem() %></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
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
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_1" name="boardMeeting_Observe_1" readonly
                                    value="<%=json_two.get("boardMeeting_Observe_1") %>">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_1" name="boardMeeting_Res_1" readonly
                                    value="<%=json_two.get("boardMeeting_Res_1") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_1" name="boardMeeting_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("boardMeeting_Comm_1") == null ? "" : json_four.get("boardMeeting_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Frequency of meeting</td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_2" name="boardMeeting_Observe_2" readonly
                                    value="<%=json_two.get("boardMeeting_Observe_2") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_2" name="boardMeeting_Res_2" readonly
                                    value="<%=json_two.get("boardMeeting_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_2" name="boardMeeting_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("boardMeeting_Comm_2") == null ? "" : json_four.get("boardMeeting_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td></td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_3" name="boardMeeting_Observe_3" readonly
                                    value="<%=json_two.get("boardMeeting_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_3" name="boardMeeting_Res_3" readonly
                                    value="<%=json_two.get("boardMeeting_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_3" name="boardMeeting_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("boardMeeting_Comm_3") == null ? "" : json_four.get("boardMeeting_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance with observations of the Board</td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_4" name="boardMeeting_Observe_4" readonly
                                    value="<%=json_two.get("boardMeeting_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_4" name="boardMeeting_Res_4" readonly
                                    value="<%=json_two.get("boardMeeting_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_4" name="boardMeeting_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("boardMeeting_Comm_4") == null ? "" : json_four.get("boardMeeting_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Delegation of Authority</td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Observe_5" name="boardMeeting_Observe_5" readonly
                                    value="<%=json_two.get("boardMeeting_Observe_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting" id="boardMeeting_Res_5" name="boardMeeting_Res_5" readonly
                                    value="<%=json_two.get("boardMeeting_Res_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="boardMeeting_Comm" id="boardMeeting_Comm_5" name="boardMeeting_Comm_5" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("boardMeeting_Comm_5") == null ? "" : json_four.get("boardMeeting_Comm_5") %>">
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
                                    <input type="text" class="operational" id="operational_Observe_1" name="operational_Observe_1" readonly
                                    value="<%=json_two.get("operational_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Res_1" name="operational_Res_1" readonly
                                    value="<%=json_two.get("operational_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="operational_Comm" id="operational_Comm_1" name="operational_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("operational_Comm_1") == null ? "" : json_four.get("operational_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Amendments, if any, to be approved by the board</td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Observe_2" name="operational_Observe_2" readonly
                                    value="<%=json_two.get("operational_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Res_2" name="operational_Res_2" readonly
                                    value="<%=json_two.get("operational_Res_2") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="operational_Comm" id="operational_Comm_2" name="operational_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("operational_Comm_2") == null ? "" : json_four.get("operational_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Frequency of review</td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Observe_3" name="operational_Observe_3" readonly
                                    value="<%=json_two.get("operational_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Res_3" name="operational_Res_3" readonly
                                    value="<%=json_two.get("operational_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="operational_Comm" id="operational_Comm_3" name="operational_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("operational_Comm_3") == null ? "" : json_four.get("operational_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance with Procedure for security documents execution as laid down Operational Manual.</td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Observe_4" name="operational_Observe_4" readonly
                                    value="<%=json_two.get("operational_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="operational" id="operational_Res_4" name="operational_Res_4" readonly
                                    value="<%=json_two.get("operational_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="operational_Comm" id="operational_Comm_4" name="operational_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("operational_Comm_4") == null ? "" : json_four.get("operational_Comm_4") %>">
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
                                    <input type="text" class="risk" id="risk_Observe_1" name="risk_Observe_1" readonly
                                    value="<%=json_two.get("risk_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_1" name="risk_Res_1" readonly
                                    value="<%=json_two.get("risk_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_1" name="risk_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_1") == null ? "" : json_four.get("risk_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Frequency of review </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_2" name="risk_Observe_2" readonly
                                    value="<%=json_two.get("risk_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_2" name="risk_Res_2" readonly
                                    value="<%=json_two.get("risk_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_2" name="risk_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_2") == null ? "" : json_four.get("risk_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Disaster recovery strategy</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_3" name="risk_Observe_3" readonly
                                    value="<%=json_two.get("risk_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_3" name="risk_Res_3" readonly
                                    value="<%=json_two.get("risk_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_3" name="risk_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_3") == null ? "" : json_four.get("risk_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Business Continuity Plan</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_4" name="risk_Observe_4" readonly
                                    value="<%=json_two.get("risk_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_4" name="risk_Res_4" readonly
                                    value="<%=json_two.get("risk_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_4" name="risk_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_4") == null ? "" : json_four.get("risk_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>IT System Audit</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_5" name="risk_Observe_5" readonly
                                    value="<%=json_two.get("risk_Observe_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_5" name="risk_Res_5" readonly
                                    value="<%=json_two.get("risk_Res_5") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_5" name="risk_Comm_5" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_5") == null ? "" : json_four.get("risk_Comm_5") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Data Integrity</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_6" name="risk_Observe_6" readonly
                                    value="<%=json_two.get("risk_Observe_6") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_6" name="risk_Res_6" readonly
                                    value="<%=json_two.get("risk_Res_6") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_6" name="risk_Comm_6" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_6") == null ? "" : json_four.get("risk_Comm_6") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Operational risk management</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_7" name="risk_Observe_7" readonly
                                    value="<%=json_two.get("risk_Observe_7") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_7" name="risk_Res_7" readonly
                                    value="<%=json_two.get("risk_Res_7") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_7" name="risk_Comm_7" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_7") == null ? "" : json_four.get("risk_Comm_7") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Employee dealing Guidelines</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_8" name="risk_Observe_8" readonly
                                    value="<%=json_two.get("risk_Observe_8") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_8" name="risk_Res_8" readonly
                                    value="<%=json_two.get("risk_Res_8") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_8" name="risk_Comm_8" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_8") == null ? "" : json_four.get("risk_Comm_8") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Comprehensive Insurance cover against risk</td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Observe_9" name="risk_Observe_9" readonly
                                    value="<%=json_two.get("risk_Observe_9") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk" id="risk_Res_9" name="risk_Res_9" readonly
                                    value="<%=json_two.get("risk_Res_9") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="risk_Comm" id="risk_Comm_9" name="risk_Comm_9" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("risk_Comm_9") == null ? "" : json_four.get("risk_Comm_9") %>">
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
                                    <input type="text" class="riskCommitee" id="riskCommitee_Observe_1" name="riskCommitee_Observe_1" readonly
                                    value="<%=json_two.get("riskCommitee_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Res_1" name="riskCommitee_Res_1" readonly
                                    value="<%=json_two.get("riskCommitee_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee_Comm" id="riskCommitee_Comm_1" name="riskCommitee_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("riskCommitee_Comm_1") == null ? "" : json_four.get("riskCommitee_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Frequency of meeting</td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Observe_2" name="riskCommitee_Observe_2" readonly
                                    value="<%=json_two.get("riskCommitee_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Res_2" name="riskCommitee_Res_2" readonly
                                    value="<%=json_two.get("riskCommitee_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee_Comm" id="riskCommitee_Comm_2" name="riskCommitee_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("riskCommitee_Comm_2") == null ? "" : json_four.get("riskCommitee_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Minutes of meeting</td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Observe_3" name="riskCommitee_Observe_3" readonly
                                    value="<%=json_two.get("riskCommitee_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Res_3" name="riskCommitee_Res_3" readonly
                                    value="<%=json_two.get("riskCommitee_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee_Comm" id="riskCommitee_Comm_3" name="riskCommitee_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("riskCommitee_Comm_3") == null ? "" : json_four.get("riskCommitee_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Implementation of observations of the risk management committee</td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Observe_4" name="riskCommitee_Observe_4" readonly
                                    value="<%=json_two.get("riskCommitee_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee" id="riskCommitee_Res_4" name="riskCommitee_Res_4" readonly
                                    value="<%=json_two.get("riskCommitee_Res_4") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="riskCommitee_Comm" id="riskCommitee_Comm_4" name="riskCommitee_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("riskCommitee_Comm_4") == null ? "" : json_four.get("riskCommitee_Comm_4") %>">
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
                                    <input type="text" class="infra" id="infra_Observe_1" name="infra_Observe_1" readonly
                                    value="<%=json_two.get("infra_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Res_1" name="infra_Res_1" readonly
                                    value="<%=json_two.get("infra_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="infra_Comm" id="infra_Comm_1" name="infra_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("infra_Comm_1") == null ? "" : json_four.get("infra_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequacy of IT Infrastructure</td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Observe_2" name="infra_Observe_2" readonly
                                    value="<%=json_two.get("infra_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Res_2" name="infra_Res_2" readonly
                                    value="<%=json_two.get("infra_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="infra_Comm" id="infra_Comm_2" name="infra_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("infra_Comm_2") == null ? "" : json_four.get("infra_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequacy of information Security Infrastructure</td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Observe_3" name="infra_Observe_3" readonly
                                    value="<%=json_two.get("infra_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Res_3" name="infra_Res_3" readonly
                                    value="<%=json_two.get("infra_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="infra_Comm" id="infra_Comm_3" name="infra_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("infra_Comm_3") == null ? "" : json_four.get("infra_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequacy of HR Infrastructure</td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Observe_4" name="infra_Observe_4" readonly
                                    value="<%=json_two.get("infra_Observe_4") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="infra" id="infra_Res_4" name="infra_Res_4" readonly
                                    value="<%=json_two.get("infra_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="infra_Comm" id="infra_Comm_4" name="infra_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("infra_Comm_4") == null ? "" : json_four.get("infra_Comm_4") %>">
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
                                    <input type="text" class="processing" id="processing_Observe_1" name="processing_Observe_1" readonly
                                    value="<%=json_two.get("processing_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_1" name="processing_Res_1" readonly
                                    value="<%=json_two.get("processing_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_1" name="processing_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("processing_Comm_1") == null ? "" : json_four.get("processing_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Order matching with files received from SE's and confirmation of match orders to SE's</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_2" name="processing_Observe_2" readonly
                                    value="<%=json_two.get("processing_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_2" name="processing_Res_2" readonly
                                    value="<%=json_two.get("processing_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_2" name="processing_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("processing_Comm_2") == null ? "" : json_four.get("processing_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Fund Settlement</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_3" name="processing_Observe_3" readonly
                                    value="<%=json_two.get("processing_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_3" name="processing_Res_3" readonly
                                    value="<%=json_two.get("processing_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_3" name="processing_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("processing_Comm_3") == null ? "" : json_four.get("processing_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Confirmed and unconfirmed trades</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_4" name="processing_Observe_4" readonly
                                    value="<%=json_two.get("processing_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_4" name="processing_Res_4" readonly
                                    value="<%=json_two.get("processing_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_4" name="processing_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("processing_Comm_4") == null ? "" : json_four.get("processing_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Securities settlement</td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Observe_5" name="processing_Observe_5" readonly
                                    value="<%=json_two.get("processing_Observe_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing" id="processing_Res_5" name="processing_Res_5" readonly
                                    value="<%=json_two.get("processing_Res_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="processing_Comm" id="processing_Comm_5" name="processing_Comm_5" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("processing_Comm_5") == null ? "" : json_four.get("processing_Comm_5") %>">
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
                                    <input type="text" class="holdings" id="holdings_Observe_1" name="holdings_Observe_1" readonly
                                    value="<%=json_two.get("holdings_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_1" name="holdings_Res_1" readonly
                                    value="<%=json_two.get("holdings_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_1" name="holdings_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("holdings_Comm_1") == null ? "" : json_four.get("holdings_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Scheme E Tier I & II</td>
                                 <td>
                                   <input type="text" class="holdings" id="holdings_Observe_2" name="holdings_Observe_2" readonly
                                    value="<%=json_two.get("holdings_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_2" name="holdings_Res_2" readonly
                                    value="<%=json_two.get("holdings_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_2" name="holdings_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("holdings_Comm_2") == null ? "" : json_four.get("holdings_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Scheme C Tier I & II</td>
                                 <td>
                                   <input type="text" class="holdings" id="holdings_Observe_3" name="holdings_Observe_3" readonly
                                    value="<%=json_two.get("holdings_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_3" name="holdings_Res_3" readonly
                                    value="<%=json_two.get("holdings_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_3" name="holdings_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("holdings_Comm_3") == null ? "" : json_four.get("holdings_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Scheme G Tier I & II</td>
                                  <td>
                                   <input type="text" class="holdings" id="holdings_Observe_4" name="holdings_Observe_4" readonly
                                    value="<%=json_two.get("holdings_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_4" name="holdings_Res_4" readonly
                                    value="<%=json_two.get("holdings_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_4" name="holdings_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("holdings_Comm_4") == null ? "" : json_four.get("holdings_Comm_4") %>">
                                 </td>
                              </tr>
                              
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Corporate CG scheme</td>
                                  <td>
                                   <input type="text" class="holdings" id="holdings_Observe_5" name="holdings_Observe_5" readonly
                                    value="<%=json_two.get("holdings_Observe_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_5" name="holdings_Res_5" readonly
                                    value="<%=json_two.get("holdings_Res_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_5" name="holdings_Comm_5" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("holdings_Comm_5") == null ? "" : json_four.get("holdings_Comm_5") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>APY or Any other scheme regulated by PFRDA</td>
                                 <td>
                                   <input type="text" class="holdings" id="holdings_Observe_6" name="holdings_Observe_6" readonly
                                    value="<%=json_two.get("holdings_Observe_6") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_6" name="holdings_Res_6" readonly
                                    value="<%=json_two.get("holdings_Res_6") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_6" name="holdings_Comm_6" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("holdings_Comm_6") == null ? "" : json_four.get("holdings_Comm_6") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Reconciliation between Pension Scheme a/c and CGSL</td>
                                  <td>
                                   <input type="text" class="holdings" id="holdings_Observe_7" name="holdings_Observe_7" readonly
                                    value="<%=json_two.get("holdings_Observe_7") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings" id="holdings_Res_7" name="holdings_Res_7" readonly
                                    value="<%=json_two.get("holdings_Res_7") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="holdings_Comm" id="holdings_Comm_7" name="holdings_Comm_7" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("holdings_Comm_7") == null ? "" : json_four.get("holdings_Comm_7") %>">
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
                                    <input type="text" class="deals" id="deals_Observe_1" name="deals_Observe_1" readonly
                                    value="<%=json_two.get("deals_Observe_1") %>" >
                                 </td>
                                 <td rowspan="2">
                                    <input type="text" class="deals" id="deals_Res_1" name="deals_Res_1" readonly
                                    value="<%=json_two.get("deals_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="deals_Comm" id="deals_Comm_1" name="deals_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("deals_Comm_1") == null ? "" : json_four.get("deals_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>System and procedure of dealing in Equity and Debt</td>
                                 <td>
                                    <input type="text" class="deals" id="deals_Observe_2" name="deals_Observe_2" readonly
                                    value="<%=json_two.get("deals_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="deals_Comm" id="deals_Comm_2" name="deals_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("deals_Comm_2") == null ? "" : json_four.get("deals_Comm_2") %>">
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
                                    <input type="text" class="office" id="office_Observe_1" name="office_Observe_1" readonly
                                    value="<%=json_two.get("office_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="office" id="office_Res_1" name="office_Res_1" readonly
                                    value="<%=json_two.get("office_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="office_Comm" id="office_Comm_1" name="office_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("office_Comm_1") == null ? "" : json_four.get("office_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>No password sharing between officers</td>
                                 <td>
                                    <input type="text" class="office" id="office_Observe_2" name="office_Observe_2" readonly
                                    value="<%=json_two.get("office_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="office" id="office_Res_2" name="office_Res_2" readonly
                                    value="<%=json_two.get("office_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="office_Comm" id="office_Comm_2" name="office_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("office_Comm_2") == null ? "" : json_four.get("office_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50% of each Scheme</td>
                                 <td>50% of each Scheme</td>
                                 <td>Accounting and settlement of deal</td>
                                 <td>
                                    <input type="text" class="office" id="office_Observe_3" name="office_Observe_3" readonly
                                    value="<%=json_two.get("office_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="office" id="office_Res_3" name="office_Res_3" readonly
                                    value="<%=json_two.get("office_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="office_Comm" id="office_Comm_3" name="office_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("office_Comm_3") == null ? "" : json_four.get("office_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Deal execution through STP</td>
                                 <td> 
                                    <input type="text" class="office" id="office_Observe_4" name="office_Observe_4" readonly
                                    value="<%=json_two.get("office_Observe_4") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="office" id="office_Res_4" name="office_Res_4" readonly
                                    value="<%=json_two.get("office_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="office_Comm" id="office_Comm_4" name="office_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("office_Comm_4") == null ? "" : json_four.get("office_Comm_4") %>">
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
                                    <input type="text" class="safe" id="safe_Observe_1" name="safe_Observe_1" readonly
                                    value="<%=json_two.get("safe_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_1" name="safe_Res_1" readonly
                                    value="<%=json_two.get("safe_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_1" name="safe_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("safe_Comm_1") == null ? "" : json_four.get("safe_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Intimating about free holdings in the securities a/c to the Pension Funds</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_2" name="safe_Observe_2" readonly
                                    value="<%=json_two.get("safe_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_2" name="safe_Res_2" readonly
                                    value="<%=json_two.get("safe_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_2" name="safe_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("safe_Comm_2") == null ? "" : json_four.get("safe_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Physical securities if any are properly coded and stored in High security vaults</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_3" name="safe_Observe_3" readonly
                                    value="<%=json_two.get("safe_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_3" name="safe_Res_3" readonly
                                    value="<%=json_two.get("safe_Res_3") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_3" name="safe_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("safe_Comm_3") == null ? "" : json_four.get("safe_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Closed circuit TV with hidden camera</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_4" name="safe_Observe_4" readonly
                                    value="<%=json_two.get("safe_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_4" name="safe_Res_4" readonly
                                    value="<%=json_two.get("safe_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_4" name="safe_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("safe_Comm_4") == null ? "" : json_four.get("safe_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Access Control</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_5" name="safe_Observe_5" readonly
                                    value="<%=json_two.get("safe_Observe_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_5" name="safe_Res_5" readonly
                                    value="<%=json_two.get("safe_Res_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_5" name="safe_Comm_5" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("safe_Comm_5") == null ? "" : json_four.get("safe_Comm_5") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Full time security staff</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_6" name="safe_Observe_6" readonly
                                    value="<%=json_two.get("safe_Observe_6") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_6" name="safe_Res_6" readonly
                                    value="<%=json_two.get("safe_Res_6") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_6" name="safe_Comm_6" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("safe_Comm_6") == null ? "" : json_four.get("safe_Comm_6") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequate Insurance of securities</td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Observe_7" name="safe_Observe_7" readonly
                                    value="<%=json_two.get("safe_Observe_7") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe" id="safe_Res_7" name="safe_Res_7" readonly
                                    value="<%=json_two.get("safe_Res_7") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="safe_Comm" id="safe_Comm_7" name="safe_Comm_7" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("safe_Comm_7") == null ? "" : json_four.get("safe_Comm_7") %>">
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
                                    <input type="text" class="asset" id="asset_Observe_1" name="asset_Observe_1" readonly
                                    value="<%=json_two.get("asset_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Res_1" name="asset_Res_1" readonly
                                    value="<%=json_two.get("asset_Res_1") %>">
                                 </td>
                                 <td>
                                    <input type="text" class="asset_Comm" id="asset_Comm_1" name="asset_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("asset_Comm_1") == null ? "" : json_four.get("asset_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Eligibility computation for all types of events both monetary and non-monetary</td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Observe_2" name="asset_Observe_2" readonly
                                    value="<%=json_two.get("asset_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Res_2" name="asset_Res_2" readonly
                                    value="<%=json_two.get("asset_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="asset_Comm" id="asset_Comm_2" name="asset_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("asset_Comm_2") == null ? "" : json_four.get("asset_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Timely receipt of all the dues from the Issuer and registrar of companies</td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Observe_3" name="asset_Observe_3" readonly
                                    value="<%=json_two.get("asset_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Res_3" name="asset_Res_3" readonly
                                    value="<%=json_two.get("asset_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="asset_Comm" id="asset_Comm_3" name="asset_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("asset_Comm_4") == null ? "" : json_four.get("asset_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Receipt and matching of Corporate Actions</td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Observe_4" name="asset_Observe_4" readonly
                                     value="<%=json_two.get("asset_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="asset" id="asset_Res_4" name="asset_Res_4" readonly
                                     value="<%=json_two.get("asset_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="asset_Comm" id="asset_Comm_4" name="asset_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("asset_Comm_4") == null ? "" : json_four.get("asset_Comm_4") %>">
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
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_1" name="reportingToPF_Observe_1" readonly
                                    value="<%=json_two.get("reportingToPF_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_1" name="reportingToPF_Res_1" readonly
                                    value="<%=json_two.get("reportingToPF_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_1" name="reportingToPF_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToPF_Comm_1") == null ? "" : json_four.get("reportingToPF_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50%</td>
                                 <td>50%</td>
                                 <td>Daily transaction statement (on next day)</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_2" name="reportingToPF_Observe_2" readonly
                                    value="<%=json_two.get("reportingToPF_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_2" name="reportingToPF_Res_2" readonly
                                    value="<%=json_two.get("reportingToPF_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_2" name="reportingToPF_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToPF_Comm_2") == null ? "" : json_four.get("reportingToPF_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Daily shortage report</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_3" name="reportingToPF_Observe_3" readonly
                                    value="<%=json_two.get("reportingToPF_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_3" name="reportingToPF_Res_3" readonly
                                    value="<%=json_two.get("reportingToPF_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_3" name="reportingToPF_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToPF_Comm_3") == null ? "" : json_four.get("reportingToPF_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50%</td>
                                 <td>50%</td>
                                 <td>Report of Corporate action</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_4" name="reportingToPF_Observe_4" readonly
                                    value="<%=json_two.get("reportingToPF_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_4" name="reportingToPF_Res_4" readonly
                                    value="<%=json_two.get("reportingToPF_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_4" name="reportingToPF_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToPF_Comm_4") == null ? "" : json_four.get("reportingToPF_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Corporate Action receipt reports on daily basis.</td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Observe_5" name="reportingToPF_Observe_5" readonly
                                    value="<%=json_two.get("reportingToPF_Observe_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF" id="reportingToPF_Res_5" name="reportingToPF_Res_5" readonly
                                    value="<%=json_two.get("reportingToPF_Res_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToPF_Comm" id="reportingToPF_Comm_5" name="reportingToPF_Comm_5" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToPF_Comm_5") == null ? "" : json_four.get("reportingToPF_Comm_5") %>">
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
                                    <input type="text" class="reportingToNps" id="reportingToNps_Observe_1" name="reportingToNps_Observe_1" readonly
                                    value="<%=json_two.get("reportingToNps_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Res_1" name="reportingToNps_Res_1" readonly
                                    value="<%=json_two.get("reportingToNps_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps_Comm" id="reportingToNps_Comm_1" name="reportingToNps_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToNps_Comm_1") == null ? "" : json_four.get("reportingToNps_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Outstanding Corporate Action</td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Observe_2" name="reportingToNps_Observe_2" readonly
                                    value="<%=json_two.get("reportingToNps_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Res_2" name="reportingToNps_Res_2" readonly
                                    value="<%=json_two.get("reportingToNps_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps_Comm" id="reportingToNps_Comm_2" name="reportingToNps_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToNps_Comm_2") == null ? "" : json_four.get("reportingToNps_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Ex-Date Report</td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Observe_3" name="reportingToNps_Observe_3" readonly
                                    value="<%=json_two.get("reportingToNps_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Res_3" name="reportingToNps_Res_3" readonly
                                    value="<%=json_two.get("reportingToNps_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps_Comm" id="reportingToNps_Comm_3" name="reportingToNps_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToNps_Comm_3") == null ? "" : json_four.get("reportingToNps_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>MIS to NPS Trust and PFRDA</td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Observe_4" name="reportingToNps_Observe_4" readonly
                                    value="<%=json_two.get("reportingToNps_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps" id="reportingToNps_Res_4" name="reportingToNps_Res_4" readonly
                                    value="<%=json_two.get("reportingToNps_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="reportingToNps_Comm" id="reportingToNps_Comm_4" name="reportingToNps_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("reportingToNps_Comm_4") == null ? "" : json_four.get("reportingToNps_Comm_4") %>">
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
                                    <input type="text" class="accounting" id="accounting_Observe_1" name="accounting_Observe_1" readonly
                                    value="<%=json_two.get("accounting_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_1" name="accounting_Res_1" readonly
                                    value="<%=json_two.get("accounting_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_1" name="accounting_Comm_1"  <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("accounting_Comm_1") == null ? "" : json_four.get("accounting_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>25%</td>
                                 <td>25%</td>
                                 <td>DIS/DIP statement & intimation to the custodian</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_2" name="accounting_Observe_2" readonly
                                    value="<%=json_two.get("accounting_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_2" name="accounting_Res_2" readonly
                                    value="<%=json_two.get("accounting_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_2" name="accounting_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("accounting_Comm_2") == null ? "" : json_four.get("accounting_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>25%</td>
                                 <td>25%</td>
                                 <td>Verification of timely and accurate Settlement of trade</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_3" name="accounting_Observe_3" readonly
                                    value="<%=json_two.get("accounting_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_3" name="accounting_Res_3" readonly
                                    value="<%=json_two.get("accounting_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_3" name="accounting_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("accounting_Comm_3") == null ? "" : json_four.get("accounting_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance with accounting standards</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_4" name="accounting_Observe_4" readonly
                                    value="<%=json_two.get("accounting_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_4" name="accounting_Res_4" readonly
                                    value="<%=json_two.get("accounting_Res_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_4" name="accounting_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("accounting_Comm_4") == null ? "" : json_four.get("accounting_Comm_4") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adherence to accounting policy</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_5" name="accounting_Observe_5" readonly
                                    value="<%=json_two.get("accounting_Observe_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_5" name="accounting_Res_5" readonly
                                    value="<%=json_two.get("accounting_Res_5") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_5" name="accounting_Comm_5" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("accounting_Comm_5") == null ? "" : json_four.get("accounting_Comm_5") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>25%</td>
                                 <td>25%</td>
                                 <td>Corporate action- bonus, rights, dividend, interest receivable</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_6" name="accounting_Observe_6" readonly
                                    value="<%=json_two.get("accounting_Observe_6") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_6" name="accounting_Res_6" readonly
                                    value="<%=json_two.get("accounting_Res_6") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_6" name="accounting_Comm_6" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("accounting_Comm_6") == null ? "" : json_four.get("accounting_Comm_6") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Outsourcing (100% of all items under the scope of accounting, if it is outsourced)</td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Observe_7" name="accounting_Observe_7" readonly
                                    value="<%=json_two.get("accounting_Observe_7") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting" id="accounting_Res_7" name="accounting_Res_7" readonly
                                    value="<%=json_two.get("accounting_Res_7") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="accounting_Comm" id="accounting_Comm_7" name="accounting_Comm_7" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("accounting_Comm_7") == null ? "" : json_four.get("accounting_Comm_7") %>">
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
                                    <input type="text" class="auc" id="auc_Observe_1" name="auc_Observe_1" readonly
                                    value="<%=json_two.get("auc_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Res_1" name="auc_Res_1" readonly
                                    value="<%=json_two.get("auc_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="auc_Comm" id="auc_Comm_1" name="auc_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("auc_Comm_1") == null ? "" : json_four.get("auc_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>50%</td>
                                 <td>50%</td>
                                 <td>Valuation of AUC – Debt as per guidelines</td>
                                  <td>
                                    <input type="text" class="auc" id="auc_Observe_2" name="auc_Observe_2" readonly
                                    value="<%=json_two.get("auc_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Res_2" name="auc_Res_2" readonly
                                    value="<%=json_two.get("auc_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="auc_Comm" id="auc_Comm_2" name="auc_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("auc_Comm_2") == null ? "" : json_four.get("auc_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>10%</td>
                                 <td>10%</td>
                                 <td>Deviations, if any</td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Observe_3" name="auc_Observe_3" readonly
                                    value="<%=json_two.get("auc_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="auc" id="auc_Res_3" name="auc_Res_3" readonly
                                    value="<%=json_two.get("auc_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="auc_Comm" id="auc_Comm_3" name="auc_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("auc_Comm_3") == null ? "" : json_four.get("auc_Comm_3") %>">
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
                                    <input type="text" class="disclosure" id="disclosure_Observe_1" name="disclosure_Observe_1" readonly
                                    value="<%=json_two.get("disclosure_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="disclosure" id="disclosure_Res_1" name="disclosure_Res_1" readonly
                                    value="<%=json_two.get("disclosure_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="disclosure_Comm" id="disclosure_Comm_1" name="disclosure_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("disclosure_Comm_1") == null ? "" : json_four.get("disclosure_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Annual report of Custodian</td>
                                 <td>
                                    <input type="text" class="disclosure" id="disclosure_Observe_2" name="disclosure_Observe_2" readonly
                                    value="<%=json_two.get("disclosure_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="disclosure" id="disclosure_Res_2" name="disclosure_Res_2" readonly
                                    value="<%=json_two.get("disclosure_Res_2") %>" > 
                                 </td>
                                 <td>
                                    <input type="text" class="disclosure_Comm" id="disclosure_Comm_2" name="disclosure_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("disclosure_Comm_2") == null ? "" : json_four.get("disclosure_Comm_2") %>">
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
                                    <input type="text" class="periodical" id="periodical_Observe_1" name="periodical_Observe_1" readonly
                                    value="<%=json_two.get("periodical_Observe_1") %>" > 
                                 </td>
                                 <td>
                                    <input type="text" class="periodical" id="periodical_Res_1" name="periodical_Res_1" readonly
                                    value="<%=json_two.get("periodical_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="periodical_Comm" id="periodical_Comm_1" name="periodical_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("periodical_Comm_1") == null ? "" : json_four.get("periodical_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Timely submission</td>
                                 <td>
                                    <input type="text" class="periodical" id="periodical_Observe_2" name="periodical_Observe_2" readonly
                                    value="<%=json_two.get("periodical_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="periodical" id="periodical_Res_2" name="periodical_Res_2" readonly
                                    value="<%=json_two.get("periodical_Res_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="periodical_Comm" id="periodical_Comm_2" name="periodical_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("periodical_Comm_2") == null ? "" : json_four.get("periodical_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Accuracy of data</td>
                                 <td rowspan="2">
                                    <input type="text" class="periodical" id="periodical_Observe_3" name="periodical_Observe_3" readonly
                                    value="<%=json_two.get("periodical_Observe_3") %> ">
                                 </td>
                                 <td rowspan="2">
                                    <input type="text" class="periodical" id="periodical_Res_3" name="periodical_Res_3" readonly
                                    value="<%=json_two.get("periodical_Res_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="periodical_Comm" id="periodical_Comm_3" name="periodical_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("periodical_Comm_3") == null ? "" : json_four.get("periodical_Comm_3") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Procedure of generation of data and report</td>
                                 <td>
                                    <input type="text" class="periodical_Comm" id="periodical_Comm_4" name="periodical_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("periodical_Comm_4") == null ? "" : json_four.get("periodical_Comm_4") %>">
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
                                    <input type="text" class="compliances" id="compliances_Observe_1" name="compliances_Observe_1" readonly
                                    value="<%=json_two.get("compliances_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Res_1" name="compliances_Res_1" readonly
                                    value="<%=json_two.get("compliances_Res_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="compliances_Comm" id="compliances_Comm_1" name="compliances_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("compliances_Comm_1") == null ? "" : json_four.get("compliances_Comm_1") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance to Guidelines and Guidance note</td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Observe_2" name="compliances_Observe_2" readonly
                                    value="<%=json_two.get("compliances_Observe_2") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Res_2" name="compliances_Res_2" readonly
                                    value="<%=json_two.get("compliances_Res_2") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances_Comm" id="compliances_Comm_2" name="compliances_Comm_2" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("compliances_Comm_2") == null ? "" : json_four.get("compliances_Comm_2") %>">
                                 </td>
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Compliance to Internal Guidelines, Operational manual</td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Observe_3" name="compliances_Observe_3" readonly
                                    value="<%=json_two.get("compliances_Observe_3") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Res_3" name="compliances_Res_3" readonly
                                    value="<%=json_two.get("compliances_Res_3") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances_Comm" id="compliances_Comm_3" name="compliances_Comm_3" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("compliances_Comm_3") == null ? "" : json_four.get("compliances_Comm_3") %>">
                                 </td>
                                 
                              </tr>
                              <tr>
                                 <td>100%</td>
                                 <td>100%</td>
                                 <td>Adequacy and efficacy of Internal Control system and procedures</td>
                                  <td>
                                    <input type="text" class="compliances" id="compliances_Observe_4" name="compliances_Observe_4" readonly
                                    value="<%=json_two.get("compliances_Observe_4") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="compliances" id="compliances_Res_4" name="compliances_Res_4" readonly
                                    value="<%=json_two.get("compliances_Res_4") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="compliances_Comm" id="compliances_Comm_4" name="compliances_Comm_4" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("compliances_Comm_4") == null ? "" : json_four.get("compliances_Comm_4") %>">
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
                                    <input type="text" class="internalAudit" id="internalAudit_Observe_1" name="internalAudit_Observe_1" readonly
                                    value="<%=json_two.get("internalAudit_Observe_1") %>" >
                                 </td>
                                 <td>
                                    <input type="text" class="internalAudit" id="internalAudit_Res_1" name="internalAudit_Res_1" readonly
                                    value="<%=json_two.get("internalAudit_Res_1") %> ">
                                 </td>
                                 <td>
                                    <input type="text" class="internalAudit_Comm" id="internalAudit_Comm_1" name="internalAudit_Comm_1" <%=isNonNPSUser ? "readonly": "" %> value="<%=json_four.get("internalAudit_Comm_1") == null ? "" : json_four.get("internalAudit_Comm_1") %>">
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                        <br><br>
                     </div>
                  </div>
               </div>
               
               <div class="row text-center">
                  <div class="col-md-12">
                     <!-- <input type="submit" class="common-btn d-inline-block border-0 mt-3" id="btn-submit"> -->
                     <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" <%=isNonNPSUser ? "disabled": "" %>>Submit</button>
                     <a class="button" id="pop-up-trigger"  href="#success-modal"></a>
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

.modal-open .modal {
    display: block !important;
    overflow-y: hidden;
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

<% if(!isDocSigned){ %>
	$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").bind("click", false);
<% }else{ %>
	let sigsub = signature.SelCertSubject.substring(signature.SelCertSubject.indexOf("CN="));
	$("h6.signedmsg").append(" by "+sigsub.split(",")[0].split("=")[1]); 
	$("a[id='<portlet:namespace/>Forward to NPSTtaskChangeStatusLink']").unbind("click");
<% } %>
$(function(){
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
			if(CertInfo.eMail === themeDisplay.getUserEmailAddress()){
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

$(document).ready(function() {
	
	$("#custodianAuditReportScrutiny").on('submit', (function(e) {
		console.log("Inside ajax");
		e.preventDefault();
		
		//default
		if($("#success-modal").hasClass("hide")){
			$("#success-modal").removeClass("hide");
		}
		
		var formData = new FormData($("#custodianAuditReportScrutiny")[0]);
		console.log(formData);
		let scrURL = "/web/guest/internal-audit-report?p_p_id=com_custodian_iar_CustodianInternalAuditReportPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=SaveCustIARScrutiny&p_p_cacheability=cacheLevelPage";
		$(".animaion").show();
		$.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            url: scrURL,
            data: formData,
            success: function(data) {
            	$(".animaion").hide();
            	if(data == "Success") {
	        			//success message
	        	 		$('#output').html(' Data sent for Review.');
	        			$("#icon").addClass("fas fa-check-circle text-success fa-4x d-block mb-4");
	        			$('.x-mark').attr('href', "#");
   		            	$("#custodianAuditReportScrutiny")[0].reset();
            	} else {
            		console.log("Error Occured in ajax call");
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
            	console.log("Error Occured in ajax call");
            },
            complete: function(){
            	$(".animaion").hide();
            	//trigger-message
            	$("#success-modal").show();
            	$('#pop-up-trigger')[0].click();
	        }

        });

		}));
	
});


</script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>
