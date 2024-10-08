<%@page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.daily.average.service.service.DARScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.DARScrutiny"%>
<%@page import="com.detailedauditreportform.util.NPSUserPre_Populate_scrutinydata"%>
<%@page import="com.detailedauditreportform.util.DARUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.daily.average.service.service.DARLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.DAR"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="java.util.List"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
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
	
	
	DAR dar = Validator.isNotNull(request.getAttribute("dar")) ? (DAR) request.getAttribute("dar") : DARLocalServiceUtil.createDAR(0);
	
	JSONObject json_two = JSONFactoryUtil.createJSONObject();
	
	try {
		if(dar.getDar_details()!=null) {
			
			json_two = JSONFactoryUtil.createJSONObject(dar.getDar_details());
				
		}
		
	} catch(Exception e) {
		
	}
	
	/* Pre populate data for asset view */
	
	NPSUserPre_Populate_scrutinydata sd = new NPSUserPre_Populate_scrutinydata();
	sd.pre_populate_scrutiny_data(request);
	
	DARScrutiny darScrutiny = Validator.isNotNull(request.getAttribute("darScrutiny")) ? (DARScrutiny) request.getAttribute("darScrutiny") : DARScrutinyLocalServiceUtil.createDARScrutiny(0);
	
	JSONObject json_four = JSONFactoryUtil.createJSONObject();
	
	/* try {
		if(darScrutiny.getManagement_comments()!=null && !darScrutiny.getManagement_comments().trim().equals("")) {
			
			json_four = JSONFactoryUtil.createJSONObject(darScrutiny.getManagement_comments());
			
		}
	} catch(Exception e) {
		
	}  */
	List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");

	boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
	boolean isDocSigned = false;
	JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
	String fileEntryId = "0";
	JSONObject signature = JSONFactoryUtil.createJSONObject();
%>

<!-- <div class="custom-modal-ui hide"  id="success-modal">
	<div class="modal-head-ui">
		<h2 class="modal-title-ui">Report Message</h2>
		<a class="modal-close-ui x-mark" href="#0">&times;</a>
		<div class="modal-content-ui text-center">
			<i class="" id="icon"></i>
			<span id="output"></span>
		</div>
	</div>
</div> -->

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
			<span class="file-preview-span" data-index='<%= i%>'> 
				<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a> 
			</span>
			<% if(i != (urlArray.length()-1)){ %>
			 ,
			<% } %>
		<% } %>
	</div>

	<% if(isPfmSupervisor){ %>
		<div>
		<% if(!isDocSigned){ %>
			<button id="signFile" class="btn  btn-primary">Sign File</button>
		<% }else{ %>
			<button id="signFile" class="btn  btn-primary" disabled="disabled">Sign File</button>
			<h6>The report has been Authenticated</h6>
		<% } %>
	</div>
	<% }else{ %>
	<div >
		<% if(isDocSigned){ %>
			<h6 class="signedmsg">The report has been Authenticated</h6>
		<% } %>
	</div>
	<% } %>

   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Detailed Audit Report</h4>
            <form class="form" id="detailedAuditReportScrutiny" action="#" method="post">
            	<input type="hidden" vaule="<%= String.valueOf(isNonNPSUser) %>" name="isNonNPSUser"/>
               <input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="<%=dar.getReportUploadLogId()%>"/>
               <input type="hidden" value="<%=dar.getReportMasterId()%>" name="reportMasterId" class="reportMasterId"/>
               <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Internal Detailed Audit Report</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty DARFile_1_URL ? "javascript:void(0);" : DARFile_1_URL}" ${empty DARFile_1_URL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="iar_rem" placeholder="Remarks if any"  name="dar_pdf_rem_1"><%=darScrutiny.getDar_file_rem_1() == null ? "" : darScrutiny.getDar_file_rem_1() %></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Extracts of board minutes approving compliance to DAR</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty DARFile_2_URL ? "javascript:void(0);" : DARFile_2_URL}" ${empty DARFile_2_URL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="iar_rem" placeholder="Remarks if any"  name="dar_pdf_rem_2"><%=darScrutiny.getDar_file_rem_2() == null ? "" : darScrutiny.getDar_file_rem_2() %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Attachment 1</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty DARFile_3_URL ? "javascript:void(0);" : DARFile_3_URL}" ${empty DARFile_3_URL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="iar_rem" placeholder="Remarks if any"  name="dar_pdf_rem_3"><%=darScrutiny.getDar_file_rem_3() == null ? "" : darScrutiny.getDar_file_rem_2() %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Attachment 2</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty DARFile_4_URL ? "javascript:void(0);" : DARFile_4_URL}" ${empty DARFile_4_URL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="iar_rem" placeholder="Remarks if any"  name="dar_pdf_rem_4"><%=darScrutiny.getDar_file_rem_4() == null ? "" : darScrutiny.getDar_file_rem_2() %></textarea>
                        </div>
                    </div>
        		</div>
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Attachment 3</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty DARFile_5_URL ? "javascript:void(0);" : DARFile_5_URL}" ${empty DARFile_5_URL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="iar_rem" placeholder="Remarks if any"  name="dar_pdf_rem_5"><%=darScrutiny.getDar_file_rem_5() == null ? "" : darScrutiny.getDar_file_rem_2() %></textarea>
                        </div>
                    </div>
        		</div><!-- row two end -->
               
               <!-- <p>AUDIT REPORT OF CUSTODIAL OPERATIONS FOR PENSION FUNDS FOR THE PERIOD FROM
                  <input type="date" class="border-0 date_1" id="date_1" name="date_1">
                  <label id="error2" class="error-message text-danger"></label>
                  <label>to</label>  
                  <input type="date" class="border-0 date_2" id="date_2" name="date_2">
                  <label id="error3" class="error-message text-danger"></label>
                  </p>
                  <br> -->
               <div class="statement-wrapper">
                  <div id="table" class="table-editable">
                     <div class="table-cont">
                        <table id="myTable_1" class="table css-serial table-responsive">
                           <thead>
                              <tr>
                                 <!-- <th class="col-1">S.No</th>
                                    <th class="col-2">Parameters</th>
                                    <th class="col-2">Auditors Comment</th>
                                    <th class="col-1">Management Comments</th>	 -->
                                 <th>S.No</th>
                                 <th class="col-6">Parameters</th>
                                 <th>Auditors Comment</th>
                                 <th>Management Comments</th>
                                 <th>NPS Comments</th>
                              </tr>
                           </thead>
                           <tbody>
                              <!-- 1 -->
                              <tr>
                                 <td><strong>1.</strong></td>
                                 <td><strong>Obligations and Authorisations</strong></td>
                                 <td></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td>1.1</td>
                                 <td>
                                    <p>Pension Fund (PFM) should have a minimum net worth
                                       of Rs. 50 Crores or as specified by PFRDA from time to time.
                                       Please mention the net-worth of PFM as on 31st March, 2023.
                                    </p>
                                    <p>(Net worth = Paid-up capital + Free reserves including
                                       share premium but excluding revaluation reserves +
                                       Investment fluctuation reserve + Credit balance in profit &
                                       loss account - debit balance in profit and loss account -
                                       Accumulated losses - Intangible assets)
                                    </p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_1" name="obligations_auditor_rem_1" rows="4" ></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_1" name="obligations_management_rem_1" rows="4" ></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_1" name="obligations_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.2</td>
                                 <td>
                                    <p>Whether PFM has acted as the
										Investment Manager of the schemes with respect to the
										investment of the funds in the name of the NPS Trust in
										accordance with the investment policies, guidelines,
										circulars or directions issued by the PFRDA from time to
										time.</p>
								 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_2" name="obligations_auditor_rem_2" rows="4" ></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_2" name="obligations_management_rem_2" rows="4" ></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_2" name="obligations_nps_rem_2" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.3</td>
                                 <td>
                                    <p>In the course of day to day investment management of the Schemes, whether PFM has taken investment decisions and managed the Scheme in accordance with the Investment Management agreement, provisions of the Act, Rules, Guidelines, Directions, Notifications, Circulars or Regulations and amendments thereunder.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_3" name="obligations_auditor_rem_3" rows="4" ></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_3" name="obligations_management_rem_3" rows="4"></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_3" name="obligations_nps_rem_3" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.4</td>
                                 <td>
                                    <p>Whether PFM has managed the NPS schemes in accordance with the scheme investment guidelines and clarifications issued by the PFRDA from time to time, as specified under Regulation 14 of the PFRDA (Pension Fund) Regulations, 2015 including subsequent amendments.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_4" name="obligations_auditor_rem_4" rows="4" ></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_4" name="obligations_management_rem_4" rows="4" ></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_4" name="obligations_nps_rem_4" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.5</td>
                                 <td>
                                    <p>Whether PFM has invested funds made available to it by the Trustee Bank within the timelines specified by the PFRDA or the NPS Trust from time to time.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_5" name="obligations_auditor_rem_5" rows="4" ></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_5" name="obligations_management_rem_5" rows="4"> </textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_5" name="obligations_nps_rem_5" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.6</td>
                                 <td>
                                    <p>Whether the Pension Fund has exercised all due diligence, prudence, promptness, reasonable care, professional skill and vigilance in carrying out its duties and in protecting the rights and interests of the subscribers. Investment decisions should be taken by the Pension Fund with emphasis on ensuring safety of contribution and ensure safety and optimising returns in the best interest of the subscribers.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_6" name="obligations_auditor_rem_6" rows="4" ><%=json_two.get("obligations_auditor_rem_6") == null ? "" : json_two.get("obligations_auditor_rem_6") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_6" name="obligations_management_rem_6" rows="4" ><%=json_four.get("obligations_management_rem_6") == null ? "" : json_four.get("obligations_management_rem_6") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_6" name="obligations_nps_rem_6" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.7</td>
                                 <td>
                                    <p>Whether PFM has made Investments in debt instruments (other than government securities) with credit rating in accordance with investment guidelines issued by PFRDA. 
                                       Details of investments held in securities with credit rating not as per investment guidelines should be disclosed.
                                    </p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_7" name="obligations_auditor_rem_7" rows="4" ><%=json_two.get("obligations_auditor_rem_7") == null ? "" : json_two.get("obligations_auditor_rem_7") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_7" name="obligations_management_rem_7" rows="4" ><%=json_four.get("obligations_management_rem_7") == null ? "" : json_four.get("obligations_management_rem_7") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_7" name="obligations_nps_rem_7" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.8</td>
                                 <td>
                                    <p>Pending deployment of funds as per investment objective, whether the moneys under the respective Schemes have been invested in short-term debt instruments and related investments and whether such investment is within permissible limits for such investments.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_8" name="obligations_auditor_rem_8" rows="4" ><%=json_two.get("obligations_auditor_rem_8") == null ? "" : json_two.get("obligations_auditor_rem_8") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_8" name="obligations_management_rem_8" rows="4" ><%=json_four.get("obligations_management_rem_8") == null ? "" : json_four.get("obligations_management_rem_8") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_8" name="obligations_nps_rem_8" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.9</td>
                                 <td>
                                    <p>Whether PFM has undertaken any transaction in nature of speculative transactions or dealing in investments.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_9" name="obligations_auditor_rem_9" rows="4" ><%=json_two.get("obligations_auditor_rem_9") == null ? "" : json_two.get("obligations_auditor_rem_9") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_9" name="obligations_management_rem_9" rows="4" ><%=json_four.get("obligations_management_rem_9") == null ? "" : json_four.get("obligations_management_rem_9") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_9" name="obligations_nps_rem_9" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.10</td>
                                 <td>
                                    <p>As stipulated in the PFRDA (PF) Regulations 2015, whether PFM has deployed well qualified professionals / officers with track record of integrity to manage the funds. 
                                       (Details of key personnel along with their past experience submitted as annexure___)
                                    </p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_10" name="obligations_auditor_rem_10" rows="4" ><%=json_two.get("obligations_auditor_rem_10") == null ? "" : json_two.get("obligations_auditor_rem_10") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_10" name="obligations_management_rem_10" rows="4" ><%=json_four.get("obligations_management_rem_10") == null ? "" : json_four.get("obligations_management_rem_10") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_10" name="obligations_nps_rem_10" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.11</td>
                                 <td>
                                    <p>Whether PFM has managed the Fund / schemes independently of other activities and has taken adequate steps to ensure that the interests of the subscribers are not being compromised in any manner.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_11" name="obligations_auditor_rem_11" rows="4" ><%=json_two.get("obligations_auditor_rem_11") == null ? "" : json_two.get("obligations_auditor_rem_11") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_11" name="obligations_management_rem_11" rows="4" ><%=json_four.get("obligations_management_rem_11") == null ? "" : json_four.get("obligations_management_rem_11") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_11" name="obligations_nps_rem_11" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.12</td>
                                 <td>
                                    The PFM has
                                    <p>(i) not undertaken any other business activities except activities relating to pension fund for schemes regulated by the PFRDA or any other activity as permitted by the PFRDA and</p>
                                    <p>(ii) not charged any fees on investment of its own assets to the schemes.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_12" name="obligations_auditor_rem_12" rows="4" ><%=json_two.get("obligations_auditor_rem_12") == null ? "" : json_two.get("obligations_auditor_rem_12") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_12" name="obligations_management_rem_12" rows="4" ><%=json_four.get("obligations_management_rem_12") == null ? "" : json_four.get("obligations_management_rem_12") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_12" name="obligations_nps_rem_12" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.13</td>
                                 <td>
                                    <p>Whether PFM has maintained books and records and facilitated audit trail of transactions and business continuity at all times.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_13" name="obligations_auditor_rem_13" rows="4" ><%=json_two.get("obligations_auditor_rem_13") == null ? "" : json_two.get("obligations_auditor_rem_13") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_13" name="obligations_management_rem_13" rows="4" ><%=json_four.get("obligations_management_rem_13") == null ? "" : json_four.get("obligations_management_rem_13") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_13" name="obligations_nps_rem_13" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.14</td>
                                 <td>
                                    <p>Whether PFM has kept and maintained proper books of accounts, records and documents, for each scheme so as to give a true and fair view of the state of affairs of the scheme and shall intimate to PFRDA and NPS Trust the place where such books of account, records and documents are maintained, if not maintained at the registered office of the PFM. 
                                       PFM is solely responsible for the maintenance and correctness of books of accounts, records and documents.
                                    </p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_14" name="obligations_auditor_rem_14" rows="4" ><%=json_two.get("obligations_auditor_rem_14") == null ? "" : json_two.get("obligations_auditor_rem_14") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_14" name="obligations_management_rem_14" rows="4" ><%=json_four.get("obligations_management_rem_14") == null ? "" : json_four.get("obligations_management_rem_14") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_14" name="obligations_nps_rem_14" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.15</td>
                                 <td>
                                    <p>Whether PFM has complied with the disclosure requirements specified by the PFRDA or NPS Trust from time to time and as specified in Schedule V of PFRDA (PF) Regulations, 2015 including subsequent amendments.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_15" name="obligations_auditor_rem_15" rows="4" ><%=json_two.get("obligations_auditor_rem_15") == null ? "" : json_two.get("obligations_auditor_rem_15") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_15" name="obligations_management_rem_15" rows="4" ><%=json_four.get("obligations_management_rem_15") == null ? "" : json_four.get("obligations_management_rem_15") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_15" name="obligations_nps_rem_15" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.16</td>
                                 <td>
                                    <p>Whether PFM has provided information regarding performance, NAV history, portfolio composition under its schemes and scheme financials to subscribers on a periodic basis as specified by the PFRDA, the NPS Trust from time to time including uploading of such information in electronic form on the website of the PFM.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_16" name="obligations_auditor_rem_16" rows="4" ><%=json_two.get("obligations_auditor_rem_16") == null ? "" : json_two.get("obligations_auditor_rem_16") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_16" name="obligations_management_rem_16" rows="4" ><%=json_four.get("obligations_management_rem_16") == null ? "" : json_four.get("obligations_management_rem_16") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_16" name="obligations_nps_rem_16" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.17</td>
                                 <td>
                                    <p>Whether PFM has undertaken valuation of the schemes in accordance with the Regulation 15 of PFRDA (Pension Fund) Regulations 2015, guidelines or directions issued by the PFRDA from time to time.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_17" name="obligations_auditor_rem_17" rows="4" ><%=json_two.get("obligations_auditor_rem_17") == null ? "" : json_two.get("obligations_auditor_rem_17") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_17" name="obligations_management_rem_17" rows="4" ><%=json_four.get("obligations_management_rem_17") == null ? "" : json_four.get("obligations_management_rem_17") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_17" name="obligations_nps_rem_17" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.18</td>
                                 <td>
                                    <p>Whether PFM has declared the correct Net Asset Value (NAV) of schemes managed by it for each business day.</p>
                                    <p>PFM shall be wholly responsible for the scheme accounting, NAV of schemes and scheme units outstanding.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_18" name="obligations_auditor_rem_18" rows="4" ><%=json_two.get("obligations_auditor_rem_18") == null ? "" : json_two.get("obligations_auditor_rem_18") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_18" name="obligations_management_rem_18" rows="4" ><%=json_four.get("obligations_management_rem_18") == null ? "" : json_four.get("obligations_management_rem_18") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_18" name="obligations_nps_rem_18" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.19</td>
                                 <td>
                                    <p>Whether PFM has submitted all periodical report on the functioning of the fund including reports specified in Schedule V of PFRDA (Pension Fund) Regulations 2015, to the NPS Trust and has it taken reasonable steps to ensure that these reports are complete and accurate in all material aspects to the extent the necessary information is within the reasonable control of the PFM.</p>
                                    <p>Whether reports were submitted at monthly/quarterly or at such intervals and in specified formats or in such manner as required or called by the NPS Trust or the PFRDA, of its activities and the compliances with the guidelines.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_19" name="obligations_auditor_rem_19" rows="4" ><%=json_two.get("obligations_auditor_rem_19") == null ? "" : json_two.get("obligations_auditor_rem_19") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_19" name="obligations_management_rem_19" rows="4" ><%=json_four.get("obligations_management_rem_19") == null ? "" : json_four.get("obligations_management_rem_19") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_19" name="obligations_nps_rem_19" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.20</td>
                                 <td>
                                    <p>Whether PFM has facilitated and co-ordinated with other intermediaries and other entities inter-alia through agreements, technological platforms for undertaking its functional obligations.  Whether PFM has provided electronic interconnectivity to the PFRDA, NPS Trust, Central Recordkeeping Agency, Custodian, Trustee Bank and other service providers as advised or specified by the PFRDA or NPS Trust from time to time.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_20" name="obligations_auditor_rem_20" rows="4" ><%=json_two.get("obligations_auditor_rem_20") == null ? "" : json_two.get("obligations_auditor_rem_20") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_20" name="obligations_management_rem_20" rows="4" ><%=json_four.get("obligations_management_rem_20") == null ? "" : json_four.get("obligations_management_rem_20") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_20" name="obligations_nps_rem_20" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.21</td>
                                 <td>
                                    <p>PFM has not given any undue or unfair advantage to any associates or deal with any of the associates of the PFM or Sponsor in any manner detrimental to interest of the subscribers.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_21" name="obligations_auditor_rem_21" rows="4" ><%=json_two.get("obligations_auditor_rem_21") == null ? "" : json_two.get("obligations_auditor_rem_21") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_21" name="obligations_management_rem_21" rows="4" ><%=json_four.get("obligations_management_rem_21") == null ? "" : json_four.get("obligations_management_rem_21") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_21" name="obligations_nps_rem_21" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.22</td>
                                 <td>
                                    <p>Whether PFM has utilised the services of its sponsor or any of its associates, employees or their relatives, for the purpose of any securities transaction.</p>
                                    <p>PFM shall ensure exclusivity and segregation of its business activities from its sponsors.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_22" name="obligations_auditor_rem_22" rows="4" ><%=json_two.get("obligations_auditor_rem_22") == null ? "" : json_two.get("obligations_auditor_rem_22") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_22" name="obligations_management_rem_22" rows="4" ><%=json_four.get("obligations_management_rem_22") == null ? "" : json_four.get("obligations_management_rem_22") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_22" name="obligations_nps_rem_22" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.23</td>
                                 <td>
                                    <p>Whether the PFM has taken prior approval of NPS Trust for any proposed transactions or engagement of a related party and provided reasons for the proposed transactions or engagement (other than investments of NPS Trust funds within the specified threshold limit).</p>
                                    <p>Whether it has made disclosure of such transactions undertaken to the NPS Trust in its periodic reports.
                                       The term ‘related party’ shall have the meaning assigned to it under the Companies Act, 2013. In the event the term ‘related party’ is specified by the PFRDA as having meaning other than the Companies Act, 2013, the said term defined by the PFRDA shall prevail.
                                    </p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_23" name="obligations_auditor_rem_23" rows="4" ><%=json_two.get("obligations_auditor_rem_23") == null ? "" : json_two.get("obligations_auditor_rem_23") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_23" name="obligations_management_rem_23" rows="4" ><%=json_four.get("obligations_management_rem_23") == null ? "" : json_four.get("obligations_management_rem_23") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_23" name="obligations_nps_rem_23" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.24</td>
                                 <td>
                                    <p>Whether PFM has purchased or sold securities through any broker which is average of 5% or more of the aggregate purchases and sale of securities under all schemes made by the PFM and has it recorded in writing the justification for exceeding the limit of 5% and reported all such investments to the PFRDA or the NPS Trust on a quarterly basis, provided that the aforesaid limit of 5% shall apply on annual basis.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_24" name="obligations_auditor_rem_24" rows="4" ><%=json_two.get("obligations_auditor_rem_24") == null ? "" : json_two.get("obligations_auditor_rem_24") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_24" name="obligations_management_rem_24" rows="4" ><%=json_four.get("obligations_management_rem_24") == null ? "" : json_four.get("obligations_management_rem_24") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_24" name="obligations_nps_rem_24" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.25</td>
                                 <td>
                                    <p>Whether PFM has invested any part of the pension fund outside the territory of India either directly or indirectly.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_25" name="obligations_auditor_rem_25" rows="4" ><%=json_two.get("obligations_auditor_rem_25") == null ? "" : json_two.get("obligations_auditor_rem_25") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_25" name="obligations_management_rem_25" rows="4" ><%=json_four.get("obligations_management_rem_25") == null ? "" : json_four.get("obligations_management_rem_25") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_25" name="obligations_nps_rem_25" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.26</td>
                                 <td>
                                    <p>Whether PFM has constituted an Investment Committee and a Risk Management Committee as per PFRDA (Pension Fund) Regulations 2015. The constitution and functions of the Investment / Monitoring Committee are as set forth in schedule X of the PFRDA (Pension Fund) Regulations 2015 and amendments thereto.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_26" name="obligations_auditor_rem_26" rows="4" ><%=json_two.get("obligations_auditor_rem_26") == null ? "" : json_two.get("obligations_auditor_rem_26") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_26" name="obligations_management_rem_26" rows="4" ><%=json_four.get("obligations_management_rem_26") == null ? "" : json_four.get("obligations_management_rem_26") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_26" name="obligations_nps_rem_26" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.27</td>
                                 <td>
                                    <p>Whether PFM has collected interest on maturity, redemption and sale proceeds relating to the investments, on due dates and credited them to the appropriate scheme accounts of the NPS Trust in time.</p>
                                    <p>While doing so, whether PFM has informed the relevant institutions and authorities that the interest received by the NPS Trust is not liable for deduction of tax at source, dividend distribution tax and securities transaction tax under the Income-tax Act.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_27" name="obligations_auditor_rem_27" rows="4" ><%=json_two.get("obligations_auditor_rem_27") == null ? "" : json_two.get("obligations_auditor_rem_27") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_27" name="obligations_management_rem_27" rows="4" ><%=json_four.get("obligations_management_rem_27") == null ? "" : json_four.get("obligations_management_rem_27") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_27" name="obligations_nps_rem_27" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.28</td>
                                 <td>
                                    <p>If any tax is deducted at source (TDS) on such investments, whether PFM has reimbursed the NPS Trust, of the said amounts, being deducted as tax, upon being notified by the NPS Trust.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_28" name="obligations_auditor_rem_28" rows="4" ></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_28" name="obligations_management_rem_28" rows="4" ></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_28" name="obligations_nps_rem_28" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.29</td>
                                 <td>
                                    <p>Whether PFM has exercised the voting rights on behalf of the NPS Trust as per the voting policy issued by the PFRDA in co-ordination with the NPS Trust.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_29" name="obligations_auditor_rem_29" rows="4" ><%=json_two.get("obligations_auditor_rem_29") == null ? "" : json_two.get("obligations_auditor_rem_29") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_29" name="obligations_management_rem_29" rows="4" ><%=json_four.get("obligations_management_rem_29") == null ? "" : json_four.get("obligations_management_rem_29") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_29" name="obligations_nps_rem_29" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.30</td>
                                 <td>
                                    <p>The Investment account of the PFM with the designated branch of the Trustee Bank shall be operated by it, whereas the NPS Trust shall maintain the account as per the agreement with the Trustee Bank.</p>
                                    <p>The funds shall be credited to a separate investment account in the name of the NPS Trust with the designated branch of the Trustee Bank. All receipts, payments, income, expenses and cost of transactions of investments shall be debited or credited to this account. PFM shall not be entitled to recover its remuneration/fee/charges, if any, from this account.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_30" name="obligations_auditor_rem_30" rows="4" ><%=json_two.get("obligations_auditor_rem_30") == null ? "" : json_two.get("obligations_auditor_rem_30") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_30" name="obligations_management_rem_30" rows="4" ><%=json_four.get("obligations_management_rem_30") == null ? "" : json_four.get("obligations_management_rem_30") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_30" name="obligations_nps_rem_30" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.31</td>
                                 <td>
                                    <p>Whether PFM has complied with the award of the Ombudsman, if any, passed under the PFRDA (Redressal of Subscriber Grievance) Regulations, 2015.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_31" name="obligations_auditor_rem_31" rows="4" ><%=json_two.get("obligations_auditor_rem_31") == null ? "" : json_two.get("obligations_auditor_rem_31") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_31" name="obligations_management_rem_31" rows="4" ><%=json_four.get("obligations_management_rem_31") == null ? "" : json_four.get("obligations_management_rem_31") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_31" name="obligations_nps_rem_31" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.32</td>
                                 <td>
                                    <p>Whether PFM has timely addressed investor grievances and complaints forwarded by PFRDA or NPS Trust or received directly by it.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_32" name="obligations_auditor_rem_32" rows="4" ><%=json_two.get("obligations_auditor_rem_32") == null ? "" : json_two.get("obligations_auditor_rem_32") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_32" name="obligations_management_rem_32" rows="4" ><%=json_four.get("obligations_management_rem_32") == null ? "" : json_four.get("obligations_management_rem_32") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_32" name="obligations_nps_rem_32" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.33</td>
                                 <td>
                                    <p>All securities and other assets acquired by pension fund on behalf of the NPS Trust be kept at custodian facilities in compliance to Schedule IX in PFRDA (Pension Fund) Regulations 2015.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_33" name="obligations_auditor_rem_33" rows="4" ><%=json_two.get("obligations_auditor_rem_33") == null ? "" : json_two.get("obligations_auditor_rem_33") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_33" name="obligations_management_rem_33" rows="4" ><%=json_four.get("obligations_management_rem_33") == null ? "" : json_four.get("obligations_management_rem_33") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_33" name="obligations_nps_rem_33" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.34</td>
                                 <td>
                                    <p>Whether PFM has entered into custodial arrangements with the NPS custodian to carry out custodial services for the schemes of the fund in accordance with the provisions of the PFRDA (Pension Fund) Regulations, 2015 and subsequent amendments thereto.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_34" name="obligations_auditor_rem_34" rows="4" ><%=json_two.get("obligations_auditor_rem_34") == null ? "" : json_two.get("obligations_auditor_rem_34") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_34" name="obligations_management_rem_34" rows="4" ><%=json_four.get("obligations_management_rem_34") == null ? "" : json_four.get("obligations_management_rem_34") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_34" name="obligations_nps_rem_34" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.35</td>
                                 <td>
                                    <p>Whether PFM has loaded CCIL settlement charges, CBRICS settlement charges, NSDL settlement charges and SEBI charges onto the Net Asset Value on a daily basis as per methodologies prescribed by NPS Trust and accrued charges are remitted to custodian after receiving approval from NPS Trust.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_35" name="obligations_auditor_rem_35" rows="4" ><%=json_two.get("obligations_auditor_rem_35") == null ? "" : json_two.get("obligations_auditor_rem_35") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_35" name="obligations_management_rem_35" rows="4" ><%=json_four.get("obligations_management_rem_35") == null ? "" : json_four.get("obligations_management_rem_35") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_35" name="obligations_nps_rem_35" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.36</td>
                                 <td>
                                    <p>Whether PFM has loaded Custodian fees onto the Net Asset Value on a daily basis in compliance of PFRDA (Custodial of Securities) Regulations 2015 and accrued charges are remitted to custodian after receiving approval from NPS Trust.</p>
                                    <p>Whether custodian fees is charged as per rate approved and communicated by the PFRDA.</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_36" name="obligations_auditor_rem_36" rows="4" ><%=json_two.get("obligations_auditor_rem_36") == null ? "" : json_two.get("obligations_auditor_rem_36") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_36" name="obligations_management_rem_36" rows="4" ><%=json_four.get("obligations_management_rem_36") == null ? "" : json_four.get("obligations_management_rem_36") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_36" name="obligations_nps_rem_36" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.37</td>
                                 <td>
                                    <p>Whether PFM has assisted NPS Trust in monitoring the trends and identify slippages of performing assets and income recognition, classification, provisioning and recovery of non-performing assets.</p>
                                    <p>Whether PFM has initiated recovery measures for NPA’s in schemes</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_37" name="obligations_auditor_rem_37" rows="4" ><%=json_two.get("obligations_auditor_rem_37") == null ? "" : json_two.get("obligations_auditor_rem_37") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_37" name="obligations_management_rem_37" rows="4" ><%=json_four.get("obligations_management_rem_37") == null ? "" : json_four.get("obligations_management_rem_37") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_37" name="obligations_nps_rem_37" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>1.38</td>
                                 <td>
                                    <p>Whether PFM has within sixty days from the date of closure of each financial year forward to the NPS Trust a copy of the audited annual report and other information including details of investments and deposits held by it so that its entire scheme-wise portfolio is disclosed to the NPS Trust. The financial statement of the schemes should be approved at a meeting of the board of directors of the PFM and forwarded to NPS Trust. The Board of Trustees of the NPS Trust shall, approve and counter sign the financial statement of the schemes of the PFM, after it is approved and signed by the board of directors of the PFM. (PFRDA (Pension Fund) Regulations 2015)</p>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_auditor_rem_38" name="obligations_auditor_rem_38" rows="4" ></textarea>
                                 </td>
                                 <td>
                                    <textarea class="obligations" id="obligations_management_rem_38" name="obligations_management_rem_38" rows="4" ></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="obligations" id="obligations_nps_rem_38" name="obligations_nps_rem_38" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <!-- 2 -->
                              <tr>
                                 <td><strong>2.</strong></td>
                                 <td><strong>Code of Conduct</strong></td>
                                 <td></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td>2.1</td>
                                 <td>
                                    <p>Whether PFM has prevented conflicts of interest that may arise while discharging its obligations. It must pro-actively identify and disclose any conflicts of interest that arise or may arise. These may relate to the PFM itself or to service providers engaged by it. PFM must promptly advise the NPS Trust as to how these conflicts of interest are to be managed prior to taking any action affected by these conflicts of interest.</p>
                                 </td>
                                 <td>
                                    <textarea class="conduct" id="conduct_auditor_rem_1" name="conduct_auditor_rem_1" rows="4" ><%=json_two.get("conduct_auditor_rem_1") == null ? "" : json_two.get("conduct_auditor_rem_1") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="conduct" id="conduct_management_rem_1" name="conduct_management_rem_1" rows="4" ><%=json_four.get("conduct_management_rem_1") == null ? "" : json_four.get("conduct_management_rem_1") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="conduct" id="conduct_nps_rem_1" name="conduct_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>2.2</td>
                                 <td>
                                    <p>Whether PFM has ensured, at all times, separation between its staff responsible for investments, settlement and book-keeping, distribution and sales, if any. Whether PFM has ensured, at all times, adequate firewalling between the shareholders fund and the Scheme Fund.</p>
                                 </td>
                                 <td>
                                    <textarea class="conduct" id="conduct_auditor_rem_2" name="conduct_auditor_rem_2" rows="4" ><%=json_two.get("conduct_auditor_rem_2") == null ? "" : json_two.get("conduct_auditor_rem_2") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="conduct" id="conduct_management_rem_2" name="conduct_management_rem_2" rows="4" ><%=json_four.get("conduct_management_rem_2") == null ? "" : json_four.get("conduct_management_rem_2") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="conduct" id="conduct_nps_rem_2" name="conduct_nps_rem_2" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>2.3</td>
                                 <td>
                                    <p>Whether PFM has complied with the Guidelines on Outsourcing of activities by Pension Funds as issued by the PFRDA. Whether it has provided information on outsourcing arrangements, if any, to the NPS Trust, provided that core functions of the PFM shall not be outsourced in any manner. PFM shall not absolve itself of any responsibility for the outsourced activities and shall be liable for the same.</p>
                                 </td>
                                 <td>
                                    <textarea class="conduct" id="conduct_auditor_rem_3" name="conduct_auditor_rem_3" rows="4" ><%=json_two.get("conduct_auditor_rem_3") == null ? "" : json_two.get("conduct_auditor_rem_3") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="conduct" id="conduct_management_rem_3" name="conduct_management_rem_3" rows="4" ><%=json_four.get("conduct_management_rem_3") == null ? "" : json_four.get("conduct_management_rem_3") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="conduct" id="conduct_nps_rem_3" name="conduct_nps_rem_3" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>2.4</td>
                                 <td>
                                    <p>PFM shall at all times abide by the Code of Conduct as specified under Schedule VI of the PFRDA (Pension Fund) Regulations, 2015 and subsequent amendments thereto.</p>
                                 </td>
                                 <td>
                                    <textarea class="conduct" id="conduct_auditor_rem_4" name="conduct_auditor_rem_4" rows="4" ><%=json_two.get("conduct_auditor_rem_4") == null ? "" : json_two.get("conduct_auditor_rem_4") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="conduct" id="conduct_management_rem_4" name="conduct_management_rem_4" rows="4" ><%=json_four.get("conduct_management_rem_4") == null ? "" : json_four.get("conduct_management_rem_4") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="conduct" id="conduct_nps_rem_4" name="conduct_nps_rem_4" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <!-- 3 -->
                              <tr>
                                 <td><strong>3.</strong></td>
                                 <td><strong>Management Fees</strong></td>
                                 <td></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td>3.1</td>
                                 <td>
                                    <p>Whether PFM has loaded Investment management fees (IMF) onto the Net Asset Value on a daily basis as per methodologies prescribed by NPS Trust and accrued fees to be collected by PFM after receiving approval from NPS Trust.</p>
                                    <p>IMF to be charged as per rate approved for the PFM and communicated by PFRDA to it. </p>
                                 </td>
                                 <td>
                                    <textarea class="fees" id="fees_auditor_rem_1" name="fees_auditor_rem_1" rows="4" ><%=json_two.get("fees_auditor_rem_1") == null ? "" : json_two.get("fees_auditor_rem_1") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="fees" id="fees_management_rem_1" name="fees_management_rem_1" rows="4" ><%=json_four.get("fees_management_rem_1") == null ? "" : json_four.get("fees_management_rem_1") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="fees" id="fees_nps_rem_1" name="fees_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>3.2</td>
                                 <td>
                                    <p>PFM to implement the following towards deductions and payment of withholding tax;</p>
                                    <p>a) PFM (on behalf of NPS Trust) is liable to deduct withholding tax liability (TDS) in respect of Investment Management Fee and Custodian Fee and the same should be accrued on a daily basis in the books of accounts </p>
                                    <p>b) Payment of withholding tax to the Central Government is to be made by PFM within the timelines prescribed under sub rule (2) of Rule 30 of Income Tax Rule i.e. on a monthly basis without waiting for the approval of these fees by NPS Trust. </p>
                                 </td>
                                 <td>
                                    <textarea class="fees" id="fees_auditor_rem_2" name="fees_auditor_rem_2" rows="4" ><%=json_two.get("fees_auditor_rem_2") == null ? "" : json_two.get("fees_auditor_rem_2") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="fees" id="fees_management_rem_2" name="fees_management_rem_2" rows="4" ><%=json_four.get("fees_management_rem_2") == null ? "" : json_four.get("fees_management_rem_2") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="fees" id="fees_nps_rem_2" name="fees_nps_rem_2" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>3.3</td>
                                 <td>
                                    <p>Whether PFM has accrued (daily accrual) NPS Trust fees in the scheme accounts towards the recovery of NPS Trust expenses. </p>
                                    <p>NPS Trust fees to be accrued/charged as per rates approved by the PFRDA. Pension Fund to debit the particular schemes after receiving approval from NPS Trust. </p>
                                 </td>
                                 <td>
                                    <textarea class="fees" id="fees_auditor_rem_3" name="fees_auditor_rem_3" rows="4" ><%=json_two.get("fees_auditor_rem_3") == null ? "" : json_two.get("fees_auditor_rem_3") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="fees" id="fees_management_rem_3" name="fees_management_rem_3" rows="4" ><%=json_four.get("fees_management_rem_3") == null ? "" : json_four.get("fees_management_rem_3") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="fees" id="fees_nps_rem_3" name="fees_nps_rem_3" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <!-- 4 -->
                              <tr>
                                 <td><strong>4.</strong></td>
                                 <td><strong>Audit</strong></td>
                                 <td></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td>4.1</td>
                                 <td>
                                    <p>Whether PFM has ensured that the scheme accounts are audited in the manner specified under Regulation 19 and 29 of the PFRDA (Pension Fund) Regulations 2015 and in accordance with the PFRDA (Appointment of Auditors) Guidance Note 2012.</p>
                                 </td>
                                 <td>
                                    <textarea class="audit" id="audit_auditor_rem_1" name="audit_auditor_rem_1" rows="4" ><%=json_two.get("audit_auditor_rem_1") == null ? "" : json_two.get("audit_auditor_rem_1") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="audit" id="audit_management_rem_1" name="audit_management_rem_1" rows="4" ><%=json_four.get("audit_management_rem_1") == null ? "" : json_four.get("audit_management_rem_1") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="audit" id="audit_nps_rem_1" name="audit_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>4.2</td>
                                 <td>
                                    <p>Whether PFM has appointed the Internal Auditors in terms of PFRDA (Appointment of Internal Auditor) Guidance Note 2013 or any subsequent guidelines/circulars etc. issued by the PFRDA on it from time to time.</p>
                                 </td>
                                 <td>
                                    <textarea class="audit" id="audit_auditor_rem_2" name="audit_auditor_rem_2" rows="4" ><%=json_two.get("audit_auditor_rem_2") == null ? "" : json_two.get("audit_auditor_rem_2") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="audit" id="audit_management_rem_2" name="audit_management_rem_2" rows="4" ><%=json_four.get("audit_management_rem_2") == null ? "" : json_four.get("audit_management_rem_2") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="audit" id="audit_nps_rem_2" name="audit_nps_rem_2" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>4.3</td>
                                 <td>
                                    <p>Whether the annual report is prepared in accordance with Schedule VII of the PFRDA (Pension Fund) Regulations 2015.</p>
                                 </td>
                                 <td>
                                    <textarea class="audit" id="audit_auditor_rem_3" name="audit_auditor_rem_3" rows="4" ><%=json_two.get("audit_auditor_rem_3") == null ? "" : json_two.get("audit_auditor_rem_3") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="audit" id="audit_management_rem_3" name="audit_management_rem_3" rows="4" ><%=json_four.get("audit_management_rem_3") == null ? "" : json_four.get("audit_management_rem_3") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="audit" id="audit_nps_rem_3" name="audit_nps_rem_3" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>4.4</td>
                                 <td>
                                    <p>The annual report and portfolio details of each scheme shall be prominently placed on the website of the PFMs after approval by the NPS Trust within ninety days from the date of closure of each financial year. (PFRDA (Pension Fund) Regulations 2015)</p>
                                 </td>
                                 <td>
                                    <textarea class="audit" id="audit_auditor_rem_4" name="audit_auditor_rem_4" rows="4" ><%=json_two.get("audit_auditor_rem_4") == null ? "" : json_two.get("audit_auditor_rem_4") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="audit" id="audit_management_rem_4" name="audit_management_rem_4" rows="4" ><%=json_four.get("audit_management_rem_4") == null ? "" : json_four.get("audit_management_rem_4") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="audit" id="audit_nps_rem_4" name="audit_nps_rem_4" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <!-- 5 -->
                              <tr>
                                 <td><strong>5.</strong></td>
                                 <td><strong>Cyber Security, Information Technology & Infrastructure</strong></td>
                                 <td></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td>5.1</td>
                                 <td>
                                    <p>Whether PFM has complied with the Cyber Security Policy as prescribed by PFRDA.</p>
                                    <p>Pension Fund to submit certificate on half yearly basis in Format prescribed vide circular dated 07.01.2019.</p>
                                 </td>
                                 <td>
                                    <textarea class="security" id="security_auditor_rem_1" name="security_auditor_rem_1" rows="4" ><%=json_two.get("security_auditor_rem_1") == null ? "" : json_two.get("security_auditor_rem_1") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="security" id="security_management_rem_1" name="security_management_rem_1" rows="4" ><%=json_four.get("security_management_rem_1") == null ? "" : json_four.get("security_management_rem_1") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="security" id="security_nps_rem_1" name="security_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>5.2</td>
                                 <td>
                                    <p>Whether Pension Fund has undergone any system/ software migration during the year and if so whether migration audit is conducted by the management/ independent professional and any observation on migration audit and how same are dealt in.</p>
                                 </td>
                                 <td>
                                    <textarea class="security" id="security_auditor_rem_2" name="security_auditor_rem_2" rows="4" ><%=json_two.get("security_auditor_rem_2") == null ? "" : json_two.get("security_auditor_rem_2") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="security" id="security_management_rem_2" name="security_management_rem_2" rows="4" ><%=json_four.get("security_management_rem_2") == null ? "" : json_four.get("security_management_rem_2") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="security" id="security_nps_rem_2" name="security_nps_rem_2" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>5.3</td>
                                 <td>
                                    <p>Whether PFM has appropriate Information security policy and standards to protect information within its control, available in digital or hard form, from unauthorised access. Whether PFM has ensured that its officers or supervised agents comply with these policies and standards.</p>
                                    <p>Whether PFM has made appropriate arrangement for back up of the data and has kept the NPS Trust and PFRDA informed of the same.</p>
                                 </td>
                                 <td>
                                    <textarea class="security" id="security_auditor_rem_3" name="security_auditor_rem_3" rows="4" ><%=json_two.get("security_auditor_rem_3") == null ? "" : json_two.get("security_auditor_rem_3") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="security" id="security_management_rem_3" name="security_management_rem_3" rows="4" ><%=json_four.get("security_management_rem_3") == null ? "" : json_four.get("security_management_rem_3") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="security" id="security_nps_rem_3" name="security_nps_rem_3" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>5.4</td>
                                 <td>
                                    <p>Whether PFM has infrastructure including trading systems, Information technology platforms, MIS system, processes, business continuity plan, disaster recovery mechanisms, procedures and controls</p>
                                 </td>
                                 <td>
                                    <textarea class="security" id="security_auditor_rem_4" name="security_auditor_rem_4" rows="4" ><%=json_two.get("security_auditor_rem_4") == null ? "" : json_two.get("security_auditor_rem_4") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="security" id="security_management_rem_4" name="security_management_rem_4" rows="4" ><%=json_four.get("security_management_rem_4") == null ? "" : json_four.get("security_management_rem_4") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="security" id="security_nps_rem_4" name="security_nps_rem_4" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>6</td>
                                 <td>
                                    <p>Whether foreign holding in the PFM is restricted to the extent and as permitted in accordance with Section 24 of the PFRDA Act, 2013, the Rules, Regulations issued thereunder and such other and further clarifications/ directions/ notifications/ amendments issued by PFRDA from time to time.</p>
                                 </td>
                                 <td>
                                    <textarea class="foreign_holding" id="foreign_holding_auditor_rem_1" name="foreign_holding_auditor_rem_1" rows="4" ><%=json_two.get("foreign_holding_auditor_rem_1") == null ? "" : json_two.get("foreign_holding_auditor_rem_1") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="foreign_holding" id="foreign_holding_management_rem_1" name="foreign_holding_management_rem_1" rows="4" ><%=json_four.get("foreign_holding_management_rem_1") == null ? "" : json_four.get("foreign_holding_management_rem_1") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="foreign_holding" id="foreign_holding_nps_rem_1" name="foreign_holding_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>7</td>
                                 <td>
                                    <p>Whether PFM has taken legal proceedings on behalf of the NPS Trust as may be necessary for the protection of the interest of the subscribers of NPS and has engaged advocates, counsel or other representatives for the effective execution of the claims on behalf of the NPS Trust</p>
                                 </td>
                                 <td>
                                    <textarea class="legal_proceedings" id="legal_proceedings_auditor_rem_1" name="legal_proceedings_auditor_rem_1" rows="4" ><%=json_two.get("legal_proceedings_auditor_rem_1") == null ? "" : json_two.get("legal_proceedings_auditor_rem_1") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="legal_proceedings" id="legal_proceedings_management_rem_1" name="legal_proceedings_management_rem_1" rows="4" ><%=json_four.get("legal_proceedings_management_rem_1") == null ? "" : json_four.get("legal_proceedings_management_rem_1") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="foreign_holding" id="foreign_holding_nps_rem_1" name="foreign_holding_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>8</td>
                                 <td>
                                    <p>Whether PFM has ensured that the NPS Trust is promptly informed of any notice or any action, legal proceedings or investigation or other proceedings of any nature whatsoever, against the sponsor or PFM, or any of its directors or employees, or a related party, by any government, judicial, quasi-judicial, regulatory or statutory authority which would restrain, prohibit or otherwise challenge or impede the performance of obligations or could adversely affect the ability to provide the services as sponsor or PFM for the pension schemes regulated by PFRDA.</p>
                                 </td>
                                 <td>
                                    <textarea class="informed" id="informed_auditor_rem_1" name="informed_auditor_rem_1" rows="4" ><%=json_two.get("informed_auditor_rem_1") == null ? "" : json_two.get("informed_auditor_rem_1") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="informed" id="informed_management_rem_1" name="informed_management_rem_1" rows="4" ><%=json_four.get("informed_management_rem_1") == null ? "" : json_four.get("informed_management_rem_1") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="informed" id="informed_nps_rem_1" name="informed_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                              <tr>
                                 <td>9</td>
                                 <td>
                                    <p>Any other matters, which you may like to bring to the attention of the management of PFM or Board of NPS Trust?</p>
                                 </td>
                                 <td>
                                    <textarea class="other_matters" id="other_matters_auditor_rem_1" name="other_matters_auditor_rem_1" rows="4" ><%=json_two.get("other_matters_auditor_rem_1") == null ? "" : json_two.get("other_matters_auditor_rem_1") %></textarea>
                                 </td>
                                 <td>
                                    <textarea class="other_matters" id="other_matters_management_rem_1" name="other_matters_management_rem_1" rows="4" ><%=json_four.get("other_matters_management_rem_1") == null ? "" : json_four.get("other_matters_management_rem_1") %></textarea>
                                 </td>
                                 <td>
                                 	<textarea class="other_matters" id="other_matters_nps_rem_1" name="other_matters_nps_rem_1" rows="4"></textarea>
                              	 </td>
                              </tr>
                           </tbody>
                        </table>
                        <br><br>
                     </div>
                  </div>
               </div>
               
               <div class="row text-center" id="sub">
                  <div class="col-md-12">
                  	 <button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" >Submit</button>
                     <%-- <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3"  id="btn-submit" value="Submit"> --%>
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

<script>

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
	<%if(dar.getDar_details()!=null) { %>
		let makercomment = ${dar.getDar_details()};
		//console.log("makercomment:----", makercomment);
		$.each( makercomment, function( k, v ) {
			//console.log(k,"makercomment:----", v);
			$.each( v, function( key, value ) {
				//console.log(key,"makercomment:----", value);
				$.each( value, function( key1, value1 ) {
					//console.log(key1,"makercomment:----", value1);
					$("textarea[name='"+key1+"']").val(value1);
				})
				/*$.each( value, function( key2, value2 ) {
					console.log(key2,"makercomment:----", value2);
					$("textarea[name='"+key2+"']").val(value2);
				});*/
			})
		});
	<%}%>
	<%if(darScrutiny.getManagement_comments()!=null && !darScrutiny.getManagement_comments().trim().equals("")) {%>
		let npscomment = ${darScrutiny.getManagement_comments()};
		//console.log("npscomment:----", npscomment);
		$.each( npscomment, function( k, v ) {
			//console.log(k,"npscomment:----", v);
			$.each( v, function( key, value ) {
				//console.log(key,"npscomment:----", value);
				$("textarea[name='"+key+"']").val(value);
			})
		});
	<%}%>
	$("#detailedAuditReportScrutiny").on('submit', (function(e) {
		console.log("Inside ajax");
		e.preventDefault();
		
		//default
		if($("#success-modal").hasClass("hide")){
			$("#success-modal").removeClass("hide");
		}
		
		var formData = new FormData($("#detailedAuditReportScrutiny")[0]);
		console.log(formData);
		let scrURL = "/web/guest/pfm-detailed-audit-report?p_p_id=com_detailedauditreportform_DetailedAuditReportFormPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=DetailedAuditReportScrutiny&p_p_cacheability=cacheLevelPage";
		$(".animaion").show();
		$(".nps-upload-report").hide();
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
	        			$(".animaion").hide();
	            		$(".nps-upload-report").show();
   		            	//$("#detailedAuditReportScrutiny")[0].reset();
            	} else {
            		console.log("Error Occured in ajax call");
        			//error message
        			$(".animaion").hide();
            		$(".nps-upload-report").show();
        			$('#output').html(' An error occured while submitting the form. Please try again.');
        			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
            	}
            },
            error: function() {
            	$(".animaion").hide();
            	$(".nps-upload-report").show();
    			//error message
    			$('#output').html(' An error occured while submitting the form. Please try again.');
    			$("#icon").addClass("fas fa-times-circle text-danger  fa-4x d-block mb-4");
            	console.log("Error Occured in ajax call");
            },
            complete: function(){
            	$(".animaion").hide();
            	$(".nps-upload-report").show();
            	//trigger-message
            	$("#success-modal").show();
            	//$('#pop-up-trigger')[0].click();
	        }

        });

		}));
	
});

</script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>
