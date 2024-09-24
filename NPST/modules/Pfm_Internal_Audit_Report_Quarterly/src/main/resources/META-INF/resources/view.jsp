<%@page import="com.pfm.internal.audit.report.quarterly.util.PMFIARUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.daily.average.service.service.PFM_executive_summaryLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.PFM_executive_summary"%>
<%@page import="com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.pfm.internal.audit.report.quarterly.util.PfmIarNonNps"%>
<%@page import="com.daily.pfm.service.service.Pfm_Qr_IARScrutinyLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.pfm.service.model.Pfm_Qr_IARScrutiny"%>
<%@page import="com.pfm.internal.audit.report.quarterly.util.NPSUserPre_Populate_scrutinydata"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/pfminternalauditreport/save" var="pfminternalauditreportURL"></portlet:resourceURL>	
	
<%
NPSUserPre_Populate_scrutinydata sd = new NPSUserPre_Populate_scrutinydata();
sd.pre_populate_scrutiny_data(request);
Pfm_Qr_IARScrutiny pfm_Qr_IARScrutiny = Validator.isNotNull(request.getAttribute("pfm_Qr_IARScrutiny")) ? (Pfm_Qr_IARScrutiny) request.getAttribute("pfm_Qr_IARScrutiny") : Pfm_Qr_IARScrutinyLocalServiceUtil.createPfm_Qr_IARScrutiny(0);
JSONObject json_four = JSONFactoryUtil.createJSONObject();

try {
	if(pfm_Qr_IARScrutiny.getNps_comments()!=null && !pfm_Qr_IARScrutiny.getNps_comments().trim().equals("")) {	
		json_four = JSONFactoryUtil.createJSONObject(pfm_Qr_IARScrutiny.getNps_comments());	
	}
} catch(Exception e) {} 
%>
<% 
PfmIarNonNps  pfmIarNonNps = new PfmIarNonNps();
boolean isNonNPSUser = pfmIarNonNps.isNonNpsUser(themeDisplay.getUserId());
long reportuploadlogId=0;
List<PFM_executive_summary> executive_summaries=null;
JSONObject jsonObject=null;
//out.println("reportuploadlogId:  "+reportuploadlogId);
Pfm_Qr_Internal_Audit_Report pfm_Qr_IAR=null;
try{
	reportuploadlogId=(Long)request.getAttribute("reportUploadLogId");
	 executive_summaries=PFM_executive_summaryLocalServiceUtil.getExecutiveSummaryByReportUploadLogId(reportuploadlogId);
	 pfm_Qr_IAR = Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.fetchPfm_Qr_Internal_Audit_Report(reportuploadlogId);
	 jsonObject=PMFIARUtil.getIAR_JSON_data(reportuploadlogId);
}catch(Exception e){
	System.out.println(e);
	pfm_Qr_IAR=Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.createPfm_Qr_Internal_Audit_Report(0);
}
if(Validator.isNull(pfm_Qr_IAR)){
	pfm_Qr_IAR=Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.createPfm_Qr_Internal_Audit_Report(0);
}
if(Validator.isNull(pfm_Qr_IARScrutiny)){
	pfm_Qr_IARScrutiny=Pfm_Qr_IARScrutinyLocalServiceUtil.createPfm_Qr_IARScrutiny(0);
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
                 <p  class="back_btn"><a class="backbtn" href="/web/guest/quarterly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
         </div>
     </div>

	<div class="row" id="canvasD">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
       		<h4>Quaterly Internal Audit Report</h4>
				<form class="row form" id="myForm" >
				<input type="hidden" name="dlfileid"/>
				<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
				<input type="hidden" value="value="${reportMasterId }" name="reportMasterId" class="reportMasterId"/>
				<input type="hidden" id="rowCountFT" name="<portlet:namespace />rowCountFT">
					<div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <label for="name">Report Due Date</label>
	                        <input class="reportDate" type="date" readonly="readonly" value="${reportDate}">
	                        <!-- <input type="text" id="name"  disabled> -->
	                    </div>
	                </div>
				<div class="row pt-3">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <!-- <label>Audit Report</label> -->
                           <label>Signed IAR in PDF</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile10">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annex_comp_certificate" name="annex_comp_certificate" accept=".pdf"/>
                           </div>
                           
                           <br>
                        </div>
                        <label id="error-annex_comp_certificate" class="error-message text-danger"></label>
                     </div>
                  </div>
	                  <div class="col-lg-6 col-md-6 col-sm-12 col-12 pt-4">
	                  	<div class="form-group">
                        	<textarea class="form-control" id="annex_comp_certificate_rem" placeholder="Remarks if any" name="annex_comp_certificate_rem" readonly ><%=(Validator.isNotNull(pfm_Qr_IARScrutiny))? pfm_Qr_IARScrutiny.getAnnex_comp_certificate_rem():"" %></textarea>
                        </div>
	                  </div>
                 </div>
                 <div class="row pt-3">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Signed Extracts of board minutes approving IAR</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile11">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="extracts_board_pdf" name="extracts_board_pdf" accept=".pdf"/>
                           </div>
                           
                           <br>
                        </div>
                        <label id="error-extracts_board_pdf" class="error-message text-danger"></label>
                     </div>
                  </div>
	                  <div class="col-lg-6 col-md-6 col-sm-12 col-12 pt-4">
	                  	<div class="form-group">
                        	<textarea class="form-control" id="extracts_board_pdf_rem" placeholder="Remarks if any" name="extracts_board_pdf_rem" readonly><%=(Validator.isNotNull(pfm_Qr_IARScrutiny))? pfm_Qr_IARScrutiny.getExtracts_board_pdf_rem():"" %></textarea>
                        </div>
	                  </div>
                 </div>
                 <div class="row pt-3">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>IAR in word</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile12">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annex_comp_certificate_i" name="annex_comp_certificate_i" accept=".docx"/>
                           </div>
                           <label id="error12" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
	                  <div class="col-lg-6 col-md-6 col-sm-12 col-12 pt-4">
	                  	<div class="form-group">
                        	<textarea class="form-control" id="annex_comp_certificate_rem_i" placeholder="Remarks if any" name="annex_comp_certificate_rem_i" readonly><%=(Validator.isNotNull(pfm_Qr_IARScrutiny))? pfm_Qr_IARScrutiny.getAnnex_comp_certificate_rem_i():"" %></textarea>
                        </div>
	                  </div>
                 </div>
                 <div class="row pt-3">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexes to IAR</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile13">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annex_comp_certificate_ii" name="annex_comp_certificate_ii" />
                           </div>
                           <label id="error13" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
	                  <div class="col-lg-6 col-md-6 col-sm-12 col-12 pt-4">
	                  	<div class="form-group">
                        	<textarea class="form-control" id="annex_comp_certificate_rem_ii" placeholder="Remarks if any" name="annex_comp_certificate_rem_ii" readonly><%=(Validator.isNotNull(pfm_Qr_IARScrutiny))? pfm_Qr_IARScrutiny.getAnnex_comp_certificate_rem_ii():"" %></textarea>
                        </div>
	                  </div>
                 </div>
                 <div class="row pt-3">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Others(if any)</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile14">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annex_comp_certificate_iii" name="annex_comp_certificate_iii"/>
                           </div>
                           <label id="error14" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
	                  <div class="col-lg-6 col-md-6 col-sm-12 col-12 pt-4">
	                  	<div class="form-group">
                        	<textarea class="form-control" id="annex_comp_certificate_rem_iii" placeholder="Remarks if any" name="annex_comp_certificate_rem_iii" readonly><%=(Validator.isNotNull(pfm_Qr_IARScrutiny))? pfm_Qr_IARScrutiny.getAnnex_comp_certificate_rem_iii():"" %></textarea>
                        </div>
	                  </div>
                 </div>
				<div class="statement-wrapper">
                	<div id="table" class="table-editable">
                		<div class="table-cont" >
							<table class="table css-serial table-responsive" id="example1">
								<thead>
									<tr>
										<th>Sl.No</th>
										<th>Broad description</th>
										<th>Sampling </th>
										<th>Sub- description</th>
										<th>Observations</th>
										<th>Management Response</th>
										<th>NPS Comment</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th rowspan="5">1.</th>
										<th rowspan="5">Board Meeting</th>
										<td>100%</td>
										<td>Composition of Board</td>
										<td><textarea class="observations_1_1 " name="observations_1_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_1_1"):"" %></textarea></td>
										<td><textarea class="management_1_1 " name="management_1_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_1_1"):"" %></textarea></td>
										<td>
											<textarea class="1" id="1_nps_rem_1" name="1_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("1_nps_rem_1"):"" %></textarea>
										</td>
									</tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Frequency of meeting</td>
		                                <td><textarea class="observations_1_2 " name="observations_1_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_1_2"):"" %></textarea></td>
										<td><textarea class="management_1_2 " name="management_1_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_1_2"):"" %></textarea></td>
										<td>
											<textarea class="1" id="1_nps_rem_2" name="1_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("1_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Minutes of meeting</td>
		                                <td><textarea class="observations_1_3 " name="observations_1_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_1_3"):"" %></textarea></td>
										<td><textarea class="management_1_3 " name="management_1_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_1_3"):"" %></textarea></td>
										<td>
											<textarea class="1" id="1_nps_rem_3" name="1_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("1_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Recommendations</td>
		                                <td><textarea class="observations_1_5 " name="observations_1_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_1_4"):"" %></textarea></td>
										<td><textarea class="management_1_5 " name="management_1_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_1_4"):"" %></textarea></td>
										<td>
											<textarea class="1" id="1_nps_rem_4" name="1_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("1_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Delegation of Authority</td>
		                                <td><textarea class="observations_1_4 " name="observations_1_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_1_5"):"" %></textarea></td>
										<td><textarea class="management_1_4 " name="management_1_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_1_5"):"" %></textarea></td>
										<td>
											<textarea class="1" id="1_nps_rem_5" name="1_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("1_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="7">2.</th>
		                                <th rowspan="7">Investment Operational Manual/Procedure</th>
		                                <td>100%</td>
		                                <td>To be approved by Board / IC</td>
		                                <td><textarea class="observations_2_1 " name="observations_2_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_2_1"):"" %></textarea></td>
										<td><textarea class="management_2_1 " name="management_2_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_2_1"):"" %></textarea></td>
	                               		<td>
											<textarea class="2" id="2_nps_rem_1" name="2_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("2_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                
		                                <td>100%</td>
		                                <td>Amendments, if any, to be approved by the board</td>
		                                <td><textarea class="observations_2_2 " name="observations_2_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_2_2"):"" %></textarea></td>
										<td><textarea class="management_2_2 " name="management_2_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_2_2"):"" %></textarea></td>
	                               		<td>
											<textarea class="2" id="2_nps_rem_2" name="2_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("2_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Frequency of review</td>
		                                <td><textarea class="observations_2_3 " name="observations_2_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_2_3"):"" %></textarea></td>
										<td><textarea class="management_2_3 " name="management_2_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_2_3"):"" %></textarea></td>
										<td>
											<textarea class="2" id="2_nps_rem_3" name="2_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("2_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Procedure for credit appraisal and market risk</td>
		                                <td><textarea class="observations_2_4 " name="observations_2_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_2_4"):"" %></textarea></td>
										<td><textarea class="management_2_4 " name="management_2_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_2_4"):"" %></textarea></td>
										<td>
											<textarea class="2" id="2_nps_rem_4" name="2_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("2_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Procedure for security documents execution</td>
		                                <td><textarea class="observations_2_5 " name="observations_2_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_2_5"):"" %></textarea></td>
										<td><textarea class="management_2_5 " name="management_2_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_2_5"):"" %></textarea></td>
										<td>
											<textarea class="2" id="2_nps_rem_5" name="2_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("2_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Income recognition policy - accruals</td>
		                                <td><textarea class="observations_2_6 " name="observations_2_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_2_6"):"" %></textarea></td>
										<td><textarea class="management_2_6 " name="management_2_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_2_6"):"" %></textarea></td>
										<td>
											<textarea class="2" id="2_nps_rem_6" name="2_nps_rem_6"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("2_nps_rem_6"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Periodic credit review</td>
		                                <td><textarea class="observations_2_7 " name="observations_2_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_2_7"):"" %></textarea></td>
										<td><textarea class="management_2_7 " name="management_2_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_2_7"):"" %></textarea></td>
										<td>
											<textarea class="2" id="2_nps_rem_7" name="2_nps_rem_7"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("2_nps_rem_7"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
										<th rowspan="5">3.</th>
										<th rowspan="5">Investment Committee (IC)</th>
										<td>100%</td>
										<td>Composition of IC</td>
										<td><textarea class="observations_3_1 " name="observations_3_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_3_1"):"" %></textarea></td>
										<td><textarea class="management_3_1 " name="management_3_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_3_1"):"" %></textarea></td>
										<td>
											<textarea class="3" id=3_nps_rem_1" name="3_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("3_nps_rem_1"):"" %></textarea>
										</td>
									</tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Frequency of meeting</td>
		                                <td><textarea class="observations_3_2 " name="observations_3_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_3_2"):"" %></textarea></td>
										<td><textarea class="management_3_2 " name="management_3_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_3_2"):"" %></textarea></td>
										<td>
											<textarea class="3" id="3_nps_rem_2" name="3_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("3_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Minutes of meeting</td>
		                                <td><textarea class="observations_3_3 " name="observations_3_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_3_3"):"" %></textarea></td>
										<td><textarea class="management_3_3 " name="management_3_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_3_3"):"" %></textarea></td>
										<td>
											<textarea class="3" id="3_nps_rem_3" name="3_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("3_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Recommendations</td>
		                                <td><textarea class="observations_3_4 " name="observations_3_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_3_4"):"" %></textarea></td>
										<td><textarea class="management_3_4 " name="management_3_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_3_4"):"" %></textarea></td>
										<td>
											<textarea class="3" id="3_nps_rem_4" name="3_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("3_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Delegation of Authority</td>
		                                <td><textarea class="observations_3_5 " name="observations_3_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_3_5"):"" %></textarea></td>
										<td><textarea class="management_3_5 " name="management_3_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_3_5"):"" %></textarea></td>
										<td>
											<textarea class="3" id="3_nps_rem_5" name="3_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("3_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
										<th rowspan="8">4.</th>
										<th rowspan="8">Investment Policy(IP)</th>
										<td>100%</td>
										<td>Approved by BOD</td>
										<td><textarea class="observations_4_1 " name="observations_4_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_4_1"):"" %></textarea></td>
										<td><textarea class="management_4_1 " name="management_4_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_4_1"):"" %></textarea></td>
										<td>
											<textarea class="4" id="4_nps_rem_1" name="4_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("4_nps_rem_1"):"" %></textarea>
										</td>
									</tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Valuation principles</td>
		                                <td><textarea class="observations_4_2 " name="observations_4_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_4_2"):"" %></textarea></td>
										<td><textarea class="management_4_2 " name="management_4_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_4_2"):"" %></textarea></td>
										<td>
											<textarea class="4" id="4_nps_rem_2" name="4_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("4_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Adherence to regulatory guidelines for valuation</td>
		                                <td><textarea class="observations_4_3 " name="observations_4_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_4_3"):"" %></textarea></td>
										<td><textarea class="management_4_3 " name="management_4_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_4_3"):"" %></textarea></td>
										<td>
											<textarea class="4" id="4_nps_rem_3" name="4_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("4_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Frequency of review</td>
		                                <td><textarea class="observations_4_4 " name="observations_4_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_4_4"):"" %></textarea></td>
										<td><textarea class="management_4_4 " name="management_4_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_4_4"):"" %></textarea></td>
										<td>
											<textarea class="4" id="4_nps_rem_4" name="4_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("4_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Liquidity</td>
		                                <td><textarea class="observations_4_5 " name="observations_4_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_4_5"):"" %></textarea></td>
										<td><textarea class="management_4_5 " name="management_4_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_4_5"):"" %></textarea></td>
										<td>
											<textarea class="4" id="4_nps_rem_5" name="4_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("4_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                  <tr>
		                                <td>100%</td>
		                                <td>Prudential norms</td>
		                                <td><textarea class="observations_4_6 " name="observations_4_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_4_6"):"" %></textarea></td>
										<td><textarea class="management_4_6 " name="management_4_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_4_6"):"" %></textarea></td>
										<td>
											<textarea class="4" id="4_nps_rem_6" name="4_nps_rem_6"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("4_nps_rem_6"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Exposure limits</td>
		                                <td><textarea class="observations_4_7 " name="observations_4_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_4_7"):"" %></textarea></td>
										<td><textarea class="management_4_7 " name="management_4_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_4_7"):"" %></textarea></td>
										<td>
											<textarea class="4" id="4_nps_rem_7" name="4_nps_rem_7"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("4_nps_rem_7"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Stop loss limits in securities trading</td>
		                                <td><textarea class="observations_4_8 " name="observations_4_8"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_4_8"):"" %></textarea></td>
										<td><textarea class="management_4_8 " name="management_4_8"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_4_8"):"" %></textarea></td>
										<td>
											<textarea class="4" id="4_nps_rem_8" name="4_nps_rem_8"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("4_nps_rem_8"):"" %></textarea>
										</td>
	                                </tr>
	                                 <tr>
		                                <th rowspan="4">5.</th>
		                                <th rowspan="4">Risk Management Committee</th>
		                                <td>100%</td>
		                                <td>Composition of Risk Committee</td>
		                                <td><textarea class="observations_5_1 " name="observations_5_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_5_1"):"" %></textarea></td>
										<td><textarea class="management_5_1 " name="management_5_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_5_1"):"" %></textarea></td>
										<td>
											<textarea class="5" id="5_nps_rem_1" name="5_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("5_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Frequency of meeting</td>
		                                <td><textarea class="observations_5_2 " name="observations_5_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_5_2"):"" %></textarea></td>
										<td><textarea class="management_5_2 " name="management_5_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_5_2"):"" %></textarea></td>
										<td>
											<textarea class="5" id="5_nps_rem_2" name="5_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("5_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Minutes of meeting</td>
		                                <td><textarea class="observations_5_3 " name="observations_5_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_5_3"):"" %></textarea></td>
										<td><textarea class="management_5_3 " name="management_5_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_5_3"):"" %></textarea></td>
										<td>
											<textarea class="5" id="5_nps_rem_3" name="5_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("5_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Recommendations</td>
		                                <td><textarea class="observations_5_4 " name="observations_5_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_5_4"):"" %></textarea></td>
										<td><textarea class="management_5_4 " name="management_5_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_5_4"):"" %></textarea></td>
										<td>
											<textarea class="5" id="5_nps_rem_4" name="5_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("5_nps_rem_4"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
	                                <tr>
	                                	<th rowspan="13">6.</th>
										<th rowspan="13">Risk Management Policy</th>
		                                <td>100%</td>
		                                <td>Approved by BOD</td>
		                                <td><textarea class="observations_6_1 " name="observations_6_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_1"):"" %></textarea></td>
										<td><textarea class="management_6_1 " name="management_6_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_1"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_1" name="6_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_1"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Frequency of review </td>
		                                <td><textarea class="observations_6_2 " name="observations_6_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_2"):"" %></textarea></td>
										<td><textarea class="management_6_2 " name="management_6_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_2"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_2" name="6_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Disaster recovery strategy</td>
		                                <td><textarea class="observations_6_3 " name="observations_6_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_3"):"" %></textarea></td>
										<td><textarea class="management_6_3 " name="management_6_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_3"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_3" name="6_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Business Continuity Plan</td>
		                                <td><textarea class="observations_6_4 " name="observations_6_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_4"):"" %></textarea></td>
										<td><textarea class="management_6_4 " name="management_6_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_4"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_4" name="6_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>IT System Audit</td>
		                                <td><textarea class="observations_6_5 " name="observations_6_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_5"):"" %></textarea></td>
										<td><textarea class="management_6_5 " name="management_6_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_5"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_5" name="6_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Data Integrity</td>
		                                <td><textarea class="observations_6_6 " name="observations_6_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_6"):"" %></textarea></td>
										<td><textarea class="management_6_6 " name="management_6_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_6"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_6" name="6_nps_rem_6"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_6"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Operational risk management</td>
		                                <td><textarea class="observations_6_7 " name="observations_6_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_7"):"" %></textarea></td>
										<td><textarea class="management_6_7 " name="management_6_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_7"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_7" name="6_nps_rem_7"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_7"):"" %></textarea>
										</td>
	                                </tr>
	                                 <tr>
		                                <td>100%</td>
		                                <td>Market risk management</td>
		                                <td><textarea class="observations_6_8 " name="observations_6_8"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_8"):"" %></textarea></td>
										<td><textarea class="management_6_8 " name="management_6_8"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_8"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_8" name="6_nps_rem_8"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_8"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Credit Risk Management</td>
		                                <td><textarea class="observations_6_9 " name="observations_6_9"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_9"):"" %></textarea></td>
										<td><textarea class="management_6_9 " name="management_6_9"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_9"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_9" name="6_nps_rem_9"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_9"):"" %></textarea>
										</td>
	                               </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Counter party management</td>
		                                <td><textarea class="observations_6_10 " name="observations_6_10"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_10"):"" %></textarea></td>
										<td><textarea class="management_6_10 " name="management_6_10"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_10"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_10" name="6_nps_rem_10"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_10"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Brokers Review</td>
		                                <td><textarea class="observations_6_11 " name="observations_6_11"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_11"):"" %></textarea></td>
										<td><textarea class="management_6_11 " name="management_6_11"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_11"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_11" name="6_nps_rem_11"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_11"):"" %></textarea>
										</td>
	                                </tr>
	                                 <tr>
		                                <td>100%</td>
		                                <td>Employee dealing Guidelines</td>
		                                <td><textarea class="observations_6_12 " name="observations_6_12"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_12"):"" %></textarea></td>
										<td><textarea class="management_6_12 " name="management_6_12"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_12"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_12" name="6_nps_rem_12"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_12"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Insurance cover against risk</td>
		                                <td><textarea class="observations_6_13 " name="observations_6_13"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_6_13"):"" %></textarea></td>
										<td><textarea class="management_6_13 " name="management_6_13"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_6_13"):"" %></textarea></td>
										<td>
											<textarea class="6" id="6_nps_rem_13" name="6_nps_rem_13"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("6_nps_rem_13"):"" %></textarea>
										</td>
	                               </tr>
	                            	<tr>
		                                <th rowspan="9">7.</th>
		                                <th rowspan="9">Pattern of Investment</th>
		                                <td>100%</td>
		                                <td>Verify pattern of investment :</td>
		                                <td><textarea class="observations_7_1 " name="observations_7_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_1"):"" %></textarea></td>
										<td><textarea class="management_7_1 " name="management_7_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_1"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_1" name="7_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Scheme CG, SG,NPS LITE,APY</td>
		                                <td><textarea class="observations_7_2 " name="observations_7_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_2"):"" %></textarea></td>
										<td><textarea class="management_7_2 " name="management_7_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_2"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_2" name="7_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>*Scheme E Tier I & II</td>
		                                <td><textarea class="observations_7_3 " name="observations_7_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_3"):"" %></textarea></td>
										<td><textarea class="management_7_3 " name="management_7_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_3"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_3" name="7_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>*Scheme c Tier I & II</td>
		                                <td><textarea class="observations_7_4 " name="observations_7_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_4"):"" %></textarea></td>
										<td><textarea class="management_7_4 " name="management_7_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_4"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_4" name="7_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>*Scheme g Tier I & II</td>
		                                <td><textarea class="observations_7_5 " name="observations_7_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_5"):"" %></textarea></td>
										<td><textarea class="management_7_5 " name="management_7_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_5"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_5" name="7_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>* Corporate CG scheme</td>
		                                <td><textarea class="observations_7_6 " name="observations_7_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_6"):"" %></textarea></td>
										<td><textarea class="management_7_6 " name="management_7_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_6"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_6" name="7_nps_rem_6"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_6"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>APY Fund Scheme</td>
		                                <td><textarea class="observations_7_7 " name="observations_7_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_7"):"" %></textarea></td>
										<td><textarea class="management_7_7 " name="management_7_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_7"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_7" name="7_nps_rem_7"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_7"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Scheme A Tier I</td>
		                                <td><textarea class="observations_8_8 " name="observations_7_8"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_8"):"" %></textarea></td>
										<td><textarea class="management_7_8 " name="management_7_8"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_8"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_8" name="7_nps_rem_8"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_8"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Scheme TTS Tier II</td>
		                                <td><textarea class="observations_7_9 " name="observations_7_9"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_7_9"):"" %></textarea></td>
										<td><textarea class="management_7_9 " name="management_7_9"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_7_9"):"" %></textarea></td>
										<td>
											<textarea class="7" id="7_nps_rem_9" name="7_nps_rem_9"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("7_nps_rem_9"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="4">8.</th>
		                                <th rowspan="4">Dealing Procedure(Front office)</th>
		                                <td>100%</td>
		                                <td>Installation of voice Recording Machine</td>
		                                <td><textarea class="observations_8_1 " name="observations_8_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_8_1"):"" %></textarea></td>
										<td><textarea class="management_8_1 " name="management_8_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_8_1"):"" %></textarea></td>
										<td>
											<textarea class="8" id="8_nps_rem_1" name="8_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("8_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>System and procedure of dealing in Equity and Debt</td>
		                                <td><textarea class="observations_8_2 " name="observations_8_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_8_2"):"" %></textarea></td>
										<td><textarea class="management_8_2 " name="management_8_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_8_2"):"" %></textarea></td>
										<td>
											<textarea class="8" id="8_nps_rem_2" name="8_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("8_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Deployment of as Dealer / Fund Manager</td>
		                                <td><textarea class="observations_8_3 " name="observations_8_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_8_3"):"" %></textarea></td>
										<td><textarea class="management_8_3 " name="management_8_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_8_3"):"" %></textarea></td>
										<td>
											<textarea class="8" id="8_nps_rem_3" name="8_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("8_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Maintenance of records for investment justifications</td>
		                                <td><textarea class="observations_8_4 " name="observations_8_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_8_4"):"" %></textarea></td>
										<td><textarea class="management_8_4 " name="management_8_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_8_4"):"" %></textarea></td>
										<td>
											<textarea class="8" id="8_nps_rem_4" name="8_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("8_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="4">9.</th>
		                                <th rowspan="4">Back office procedure</th>
		                                <td>100%</td>
		                                <td>Deployment of separate officials</td>
		                                <td><textarea class="observations_9_1 " name="observations_9_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_9_1"):"" %></textarea></td>
										<td><textarea class="management_9_1 " name="management_9_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_9_1"):"" %></textarea></td>
										<td>
											<textarea class="9" id="9_nps_rem_1" name="9_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("9_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>No password sharing between front office and back office</td>
		                                <td><textarea class="observations_9_2 " name="observations_9_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_9_2"):"" %></textarea></td>
										<td><textarea class="management_9_2 " name="management_9_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_9_2"):"" %></textarea></td>
										<td>
											<textarea class="9" id="9_nps_rem_2" name="9_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("9_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Accounting and settlement of deal</td>
		                                <td><textarea class="observations_9_3 " name="observations_9_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_9_3"):"" %></textarea></td>
										<td><textarea class="management_9_3 " name="management_9_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_9_3"):"" %></textarea></td>
										<td>
											<textarea class="9" id="9_nps_rem_3" name="9_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("9_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Deal execution through STP</td>
		                                <td><textarea class="observations_9_4 " name="observations_9_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_9_4"):"" %></textarea></td>
										<td><textarea class="management_9_4 " name="management_9_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_9_4"):"" %></textarea></td>
										<td>
											<textarea class="9" id="9_nps_rem_4" name="9_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("9_nps_rem_4"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <th rowspan="6">10.</th>
		                                <th rowspan="6">Exposure and Prudential norms</th>
		                                <td>100%</td>
		                                <td>Investment in promoter group</td>
		                                <td><textarea class="observations_10_1 " name="observations_10_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_10_1"):"" %></textarea></td>
										<td><textarea class="management_10_1 " name="management_10_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_10_1"):"" %></textarea></td>
										<td>
											<textarea class="10" id="10_nps_rem_1" name="10_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("10_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Stipulated norms - Investee co.</td>
		                                <td><textarea class="observations_10_2 " name="observations_10_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_10_2"):"" %></textarea></td>
										<td><textarea class="management_10_2 " name="management_10_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_10_2"):"" %></textarea></td>
										<td>
											<textarea class="10" id="10_nps_rem_2" name="10_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("10_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Stipulated norms - Group Co.</td>
		                                <td><textarea class="observations_10_3 " name="observations_10_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_10_3"):"" %></textarea></td>
										<td><textarea class="management_10_3 " name="management_10_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_10_3"):"" %></textarea></td>
										<td>
											<textarea class="10" id="10_nps_rem_3" name="10_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("10_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Stipulated norms - Industry group</td>
		                                <td><textarea class="observations_10_4 " name="observations_10_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_10_4"):"" %></textarea></td>
										<td><textarea class="management_10_4 " name="management_10_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_10_4"):"" %></textarea></td>
										<td>
											<textarea class="10" id="10_nps_rem_4" name="10_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("10_nps_rem_4"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Limit monitoring - through system</td>
		                                <td><textarea class="observations_10_5 " name="observations_10_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_10_5"):"" %></textarea></td>
										<td><textarea class="management_10_5 " name="management_10_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_10_5"):"" %></textarea></td>
										<td>
											<textarea class="10" id="10_nps_rem_5" name="10_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("10_nps_rem_5"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Limit and alert management - Internal Norms and Regulatory norms.</td>
		                                <td><textarea class="observations_10_6 " name="observations_10_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_10_6"):"" %></textarea></td>
										<td><textarea class="management_10_6 " name="management_10_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_10_6"):"" %></textarea></td>
										<td>
											<textarea class="10" id="10_nps_rem_6" name="10_nps_rem_6"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("10_nps_rem_6"):"" %></textarea>
										</td>
	                              </tr>
	                              <tr>
		                                <th rowspan="2">11.</th>
		                                <th rowspan="2">Custodian Controls</th>
		                                <td>100%</td>
		                                <td>Reconciliation of securities with Custodian data</td>
		                                <td><textarea class="observations_11_1 " name="observations_11_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_11_1"):"" %></textarea></td>
										<td><textarea class="management_11_1 " name="management_11_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_11_1"):"" %></textarea></td>
										<td>
											<textarea class="11" id="11_nps_rem_1" name="11_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("11_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Controls over Physical holding</td>
		                                <td><textarea class="observations_11_2 " name="observations_11_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_11_2"):"" %></textarea></td>
										<td><textarea class="management_11_2 " name="management_11_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_11_2"):"" %></textarea></td>
										<td>
											<textarea class="11" id="11_nps_rem_2" name="11_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("11_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="2">12.</th>
		                                <th rowspan="2">Verification of other investments</th>
		                                <td>End of reporting period</td>
		                                <td>Reconciliation of Mutual Fund holding with Statement of Account received from MF</td>
		                                <td><textarea class="observations_12_1 " name="observations_12_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_12_1"):"" %></textarea></td>
										<td><textarea class="management_12_1 " name="management_12_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_12_1"):"" %></textarea></td>
										<td>
											<textarea class="12" id="12_nps_rem_1" name="12_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("12_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>End of reporting period</td>
		                                <td>
		                                	Physical verification of fixed deposit 
		                                	receipts in respect of Fixed Deposits placed with Banks
		                                </td>
		                                <td><textarea class="observations_12_2 " name="observations_12_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_12_2"):"" %></textarea></td>
										<td><textarea class="management_12_2 " name="management_12_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_12_2"):"" %></textarea></td>
										<td>
											<textarea class="12" id="12_nps_rem_2" name="12_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("12_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="3">13.</th>
		                                <th rowspan="3">Units accounting</th>
		                                <td>25%</td>
		                                <td>Reconciliation of units with CRA on daily basis</td>
		                                <td><textarea class="observations_13_1 " name="observations_13_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_13_1"):"" %></textarea></td>
										<td><textarea class="management_13_1 " name="management_13_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_13_1"):"" %></textarea></td>
										<td>
											<textarea class="13" id="13_nps_rem_1" name="13_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("13_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Correctness of NAV applied</td>
		                                <td><textarea class="observations_13_2 " name="observations_13_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_13_2"):"" %></textarea></td>
										<td><textarea class="management_13_2 " name="management_13_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_13_2"):"" %></textarea></td>
										<td>
											<textarea class="13" id="13_nps_rem_2" name="13_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("13_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                
	                               <tr>
		                                <td>100%</td>
		                                <td>Appropriateness of accounting of unit premium</td>
		                                <td><textarea class="observations_13_3 " name="observations_13_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_13_3"):"" %></textarea></td>
										<td><textarea class="management_13_3 " name="management_13_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_13_3"):"" %></textarea></td>
										<td>
											<textarea class="13" id="13_nps_rem_3" name="13_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("13_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="3">14.</th>
		                                <th rowspan="3">Investment bank accounts</th>
		                                <td>25%</td>
		                                <td>Bank Reconciliation on daily basis</td>
		                                <td><textarea class="observation_14_1 " name="observations_14_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_14_1"):"" %></textarea></td>
										<td><textarea class="management_14_1 " name="management_14_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_14_1"):"" %></textarea></td>
										<td>
											<textarea class="14" id="14_nps_rem_1" name="14_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("14_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                                <tr>
		                                <td>25%</td>
		                                <td>Identification of idle funds</td>
		                                <td><textarea class="observations_14_2 " name="observations_14_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_14_2"):"" %></textarea></td>
										<td><textarea class="management_14_2 " name="management_14_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_14_2"):"" %></textarea></td>
										<td>
											<textarea class="14" id="14_nps_rem_2" name="14_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("14_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>25%</td>
		                                <td>Timely deployment of fund</td>
		                                <td><textarea class="observations_14_3 " name="observations_14_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_14_3"):"" %></textarea></td>
										<td><textarea class="management_14_3 " name="management_14_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_14_3"):"" %></textarea></td>
										<td>
											<textarea class="14" id="14_nps_rem_3" name="14_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("14_nps_rem_3"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <th rowspan="2">15.</th>
		                                <th rowspan="2">Appointment of Brokers</th>
		                                <td>100%</td>
		                                <td>Guidelines on empanelment of brokers dated 14th Sep 2012</td>
		                                <td><textarea class="observation_15_1 " name="observations_15_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_15_1"):"" %></textarea></td>
										<td><textarea class="management_15_1 " name="management_15_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_15_1"):"" %></textarea></td>
										<td>
											<textarea class="15" id="15_nps_rem_1" name="15_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("15_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Ceiling per broker as per IMA</td>
		                                <td><textarea class="observations_15_2 " name="observations_15_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_15_2"):"" %></textarea></td>
										<td><textarea class="management_15_2 " name="management_15_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_15_2"):"" %></textarea></td>
										<td>
											<textarea class="15" id="15_nps_rem_2" name="15_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("15_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="3">16.</th>
		                                <th rowspan="3">Non Performing Investments</th>
		                                <td>100%</td>
		                                <td>Classification</td>
		                                <td><textarea class="observation_16_1 " name="observations_16_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_16_1"):"" %></textarea></td>
										<td><textarea class="management_16_1 " name="management_16_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_16_1"):"" %></textarea></td>
										<td>
											<textarea class="16" id="16_nps_rem_1" name="16_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("16_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Income Recognition</td>
		                                <td><textarea class="observations_16_2 " name="observations_16_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_16_2"):"" %></textarea></td>
										<td><textarea class="management_16_2 " name="management_16_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_16_2"):"" %></textarea></td>
										<td>
											<textarea class="16" id="16_nps_rem_2" name="16_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("16_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Provisions</td>
		                                <td><textarea class="observations_16_3 " name="observations_16_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_16_3"):"" %></textarea></td>
										<td><textarea class="management_16_3 " name="management_16_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_16_3"):"" %></textarea></td>
										<td>
											<textarea class="16" id="16_nps_rem_3" name="16_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("16_nps_rem_3"):"" %></textarea>
										</td>
	                               </tr>
	                                <tr>
		                                <th rowspan="2">17.</th>
		                                <th rowspan="2">Inter Scheme Transfer</th>
		                                <td>100%</td>
		                                <td>Traded securities - Rates, authorisation and documentation</td>
		                                <td><textarea class="observations_17_1 " name="observations_17_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_17_1"):"" %></textarea></td>
										<td><textarea class="management_17_1 " name="management_17_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_17_1"):"" %></textarea></td>
										<td>
											<textarea class="17" id="17_nps_rem_1" name="17_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("17_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Non traded securities - Need/Justification of off market transaction, fairness of price and internal authorisation</td>
		                                <td><textarea class="observations_17_2 " name="observations_17_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_17_2"):"" %></textarea></td>
										<td><textarea class="management_17_2 " name="management_17_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_17_2"):"" %></textarea></td>
										<td>
											<textarea class="17" id="17_nps_rem_2" name="17_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("17_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="11">18.</th>
		                                <th rowspan="11">Investment Deals verification</th>
		                                <td>75%</td>
		                                <td>Accuracy of calculation of Investable surplus</td>
		                                <td><textarea class="observations_18_1 " name="observations_18_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_1"):"" %></textarea></td>
										<td><textarea class="management_18_1 " name="management_18_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_1"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_1" name="18_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>75%</td>
		                                <td>Review of Daily sale purchase register</td>
		                                <td><textarea class="observations_18_2 " name="observations_18_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_2"):"" %></textarea></td>
										<td><textarea class="management_18_2 " name="management_18_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_2"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_2" name="18_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>75%</td>
		                                <td>Journal vouchers</td>
		                                <td><textarea class="observations_18_3 " name="observations_18_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_3"):"" %></textarea></td>
										<td><textarea class="management_18_3 " name="management_18_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_3"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_3" name="18_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>75%</td>
		                                <td>Investment Ledgers</td>
		                                <td><textarea class="observations_18_4 " name="observations_18_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_4"):"" %></textarea></td>
										<td><textarea class="management_18_4 " name="management_18_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_4"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_4" name="18_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_4"):"" %></textarea>
										</td>
	                               </tr>
	                                <tr>
		                                <td>75%</td>
		                                <td>Verification of authorisation, price and documentation</td>
		                                <td><textarea class="observations_18_5 " name="observations_18_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_5"):"" %></textarea></td>
										<td><textarea class="management_18_5 " name="management_18_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_5"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_5" name="18_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>75%</td>
		                                <td>Counter party confirmation</td>
		                                <td><textarea class="observations_18_6 " name="observations_18_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_6"):"" %></textarea></td>
										<td><textarea class="management_18_6 " name="management_18_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_6"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_6" name="18_nps_rem_6"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_6"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>75%</td>
		                                <td>Contract note from brokers</td>
		                                <td><textarea class="observations_18_7 " name="observations_18_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_7"):"" %></textarea></td>
										<td><textarea class="management_18_7 " name="management_18_7"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_7"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_7" name="18_nps_rem_7"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_7"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>75%</td>
		                                <td>Brokers Bills</td>
		                                <td><textarea class="observations_18_8 " name="observations_18_8"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_8"):"" %></textarea></td>
										<td><textarea class="management_18_8 " name="management_18_8"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_8"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_8" name="18_nps_rem_8"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_8"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>25%</td>
		                                <td>Deal tickets</td>
		                                <td><textarea class="observations_18_9 " name="observations_18_9"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_9"):"" %></textarea></td>
										<td><textarea class="management_18_9 " name="management_18_9"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_9"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_9" name="18_nps_rem_9"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_9"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>25%</td>
		                                <td>DIS/DIP statement & Intimation to the custodian</td>
		                                <td><textarea class="observations_18_10 " name="observations_18_10"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_10"):"" %></textarea></td>
										<td><textarea class="management_18_10 " name="management_18_10"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_10"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_10" name="18_nps_rem_10"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_10"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>25%</td>
		                                <td>Verification of timely and accurate capturing of trades</td>
		                                <td><textarea class="observations_18_11 " name="observations_18_11"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_18_11"):"" %></textarea></td>
										<td><textarea class="management_18_11 " name="management_18_11"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_18_11"):"" %></textarea></td>
										<td>
											<textarea class="18" id="18_nps_rem_11" name="18_nps_rem_11"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("18_nps_rem_11"):"" %></textarea>
										</td>
	                               </tr>
	                               
	                               <tr>
		                                <th rowspan="6">19.</th>
		                                <th rowspan="6">Accounting</th>
		                                <td>100%</td>
		                                <td>Compliance with accounting Standards</td>
		                                <td><textarea class="observation_19_1 " name="observations_19_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_19_1"):"" %></textarea></td>
										<td><textarea class="management_19_1 " name="management_19_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_19_1"):"" %></textarea></td>
										<td>
											<textarea class="19" id="19_nps_rem_1" name="19_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("19_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Adherence to accounting policy</td>
		                                <td><textarea class="observations_19_2 " name="observations_19_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_19_2"):"" %></textarea></td>
										<td><textarea class="management_19_2 " name="management_19_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_19_2"):"" %></textarea></td>
										<td>
											<textarea class="19" id="19_nps_rem_2" name="19_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("19_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>50%</td>
		                                <td>Accounting of income receipt</td>
		                                <td><textarea class="observations_19_3 " name="observations_19_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_19_3"):"" %></textarea></td>
										<td><textarea class="management_19_3 " name="management_19_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_19_3"):"" %></textarea></td>
										<td>
											<textarea class="19" id="19_nps_rem_3" name="19_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("19_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>25%</td>
		                                <td>Corporate action-bonus, rights,dividend, interest receivable</td>
		                                <td><textarea class="observations_19_4 " name="observations_19_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_19_4"):"" %></textarea></td>
										<td><textarea class="management_19_4 " name="management_19_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_19_4"):"" %></textarea></td>
										<td>
											<textarea class="19" id="19_nps_rem_4" name="19_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("19_nps_rem_4"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>25%</td>
		                                <td>Reversal of brokerage on daily basis</td>
		                                <td><textarea class="observations_19_5 " name="observations_19_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_19_5"):"" %></textarea></td>
										<td><textarea class="management_19_5 " name="management_19_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_19_5"):"" %></textarea></td>
										<td>
											<textarea class="19" id="19_nps_rem_5" name="19_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("19_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Outsourcing (100% of all items under the scope of 'accounting', if it is outsourced)</td>
		                                <td><textarea class="observations_19_6 " name="observations_19_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_19_6"):"" %></textarea></td>
										<td><textarea class="management_19_6 " name="management_19_6"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_19_6"):"" %></textarea></td>
										<td>
											<textarea class="19" id="19_nps_rem_6" name="19_nps_rem_6"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("19_nps_rem_6"):"" %></textarea>
										</td>
	                                </tr>
	                                 <tr>
		                                <th rowspan="5">20.</th>
		                                <th rowspan="5">NAV</th>
		                                <td>100%</td>
		                                <td>Valuation of investments</td>
		                                <td><textarea class="observations_20_1 " name="observations_20_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_20_1"):"" %></textarea></td>
										<td><textarea class="management_20_1 " name="management_20_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_20_1"):"" %></textarea></td>
										<td>
											<textarea class="20" id="20_nps_rem_1" name="20_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("20_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                                <tr>
		                                <td>50%</td>
		                                <td>NAV Calculation</td>
		                                <td><textarea class="observations_20_2 " name="observations_20_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_20_2"):"" %></textarea></td>
										<td><textarea class="management_20_2 " name="management_20_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_20_2"):"" %></textarea></td>
										<td>
											<textarea class="20" id="20_nps_rem_2" name="20_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("20_nps_rem_2"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>10%</td>
		                                <td>Accruals of coupons</td>
		                                <td><textarea class="observations_20_3 " name="observations_20_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_20_3"):"" %></textarea></td>
										<td><textarea class="management_20_3 " name="management_20_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_20_3"):"" %></textarea></td>
										<td>
											<textarea class="20" id="20_nps_rem_3" name="20_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("20_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Scrutiny of expenses charged to scheme on daily basis (i.e Investment Management fee, Custodian charges, SEBI Charges and CCIL Charges)</td>
		                                <td><textarea class="observations_20_4 " name="observations_20_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_20_4"):"" %></textarea></td>
										<td><textarea class="management_20_4 " name="management_20_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_20_4"):"" %></textarea></td>
										<td>
											<textarea class="20" id="20_nps_rem_4" name="20_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("20_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Outsourcing (100% of all items under the scope of 'NAV', if it is outsourced)</td>
		                                <td><textarea class="observations_20_5 " name="observations_20_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_20_5"):"" %></textarea></td>
										<td><textarea class="management_20_5 " name="management_20_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_20_5"):"" %></textarea></td>
										<td>
											<textarea class="20" id="20_nps_rem_5" name="20_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("20_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="5">21.</th>
		                                <th rowspan="5">Disclosure</th>
		                                <td>100%</td>
		                                <td>Daily NAV disclosure/uploading to CRA</td>
		                                <td><textarea class="observations_21_1 " name="observations_21_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_21_1"):"" %></textarea></td>
										<td><textarea class="management_21_1 " name="management_21_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_21_1"):"" %></textarea></td>
										<td>
											<textarea class="21" id="21_nps_rem_1" name="21_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("21_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>NAV History</td>
		                                <td><textarea class="observations_21_2 " name="observations_21_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_21_2"):"" %></textarea></td>
										<td><textarea class="management_21_2 " name="management_21_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_21_2"):"" %></textarea></td>
										<td>
											<textarea class="21" id="21_nps_rem_2" name="21_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("21_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Portfolio Disclosure</td>
		                                <td><textarea class="observations_21_3 " name="observations_21_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_21_3"):"" %></textarea></td>
										<td><textarea class="management_21_3 " name="management_21_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_21_3"):"" %></textarea></td>
										<td>
											<textarea class="21" id="21_nps_rem_3" name="21_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("21_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Half Yearly Financial statement</td>
		                                <td><textarea class="observations_21_4 " name="observations_21_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_21_4"):"" %></textarea></td>
										<td><textarea class="management_21_4 " name="management_21_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_21_4"):"" %></textarea></td>
										<td>
											<textarea class="21" id="21_nps_rem_4" name="21_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("21_nps_rem_4"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Annual report</td>
		                                <td><textarea class="observations_21_5 " name="observations_21_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_21_5"):"" %></textarea></td>
										<td><textarea class="management_21_5 " name="management_21_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_21_5"):"" %></textarea></td>
										<td>
											<textarea class="21" id="21_nps_rem_5" name="21_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("21_nps_rem_5"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <th rowspan="4">22.</th>
		                                <th rowspan="4">Periodical returns to Authority / Trust</th>
		                                <td>100%</td>
		                                <td>Timely submission</td>
		                                <td><textarea class="observations_22_1 " name="observations_22_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_22_1"):"" %></textarea></td>
										<td><textarea class="management_22_1 " name="management_22_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_22_1"):"" %></textarea></td>
										<td>
											<textarea class="22" id="22_nps_rem_1" name="22_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("22_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Accuracy of data</td>
		                                <td><textarea class="observations_22_2 " name="observations_22_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_22_2"):"" %></textarea></td>
										<td><textarea class="management_22_2 " name="management_22_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_22_2"):"" %></textarea></td>
										<td>
											<textarea class="22" id="22_nps_rem_2" name="22_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("22_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Procedure of generation of data and report</td>
		                                <td><textarea class="observations_22_3 " name="observations_22_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_22_3"):"" %></textarea></td>
										<td><textarea class="management_22_3 " name="management_22_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_22_3"):"" %></textarea></td>
										<td>
											<textarea class="22" id="22_nps_rem_3" name="22_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("22_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>procedure for capturing Down grading of investment</td>
		                                <td><textarea class="observations_22_4 " name="observations_22_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_22_4"):"" %></textarea></td>
										<td><textarea class="management_22_4 " name="management_22_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_22_4"):"" %></textarea></td>
										<td>
											<textarea class="22" id="22_nps_rem_4" name="22_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("22_nps_rem_4"):"" %></textarea>
										</td>
	                               </tr>
	                              <tr>
		                                <th rowspan="5">23.</th>
		                                <th rowspan="5">Compliances</th>
		                                <td>100%</td>
		                                <td>Compliance to clauses of IMA</td>
		                                <td><textarea class="observations_23_1 " name="observations_23_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_23_1"):"" %></textarea></td>
										<td><textarea class="management_23_1 " name="management_23_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_23_1"):"" %></textarea></td>
										<td>
											<textarea class="23" id="23_nps_rem_1" name="23_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("23_nps_rem_1"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Compliance to Guidelines and Guidance note</td>
		                                <td><textarea class="observations_23_2 " name="observations_23_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_23_2"):"" %></textarea></td>
										<td><textarea class="management_23_2 " name="management_23_2"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_23_2"):"" %></textarea></td>
										<td>
											<textarea class="23" id="23_nps_rem_2" name="23_nps_rem_2"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("23_nps_rem_2"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
	                                	<td>100%</td>
		                                <td>Compliance to Internal Guidelines, Operational manual</td>
		                                <td><textarea class="observations_23_3 " name="observations_23_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_23_3"):"" %></textarea></td>
										<td><textarea class="management_23_3 " name="management_23_3"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_23_3"):"" %></textarea></td>
										<td>
											<textarea class="23" id="23_nps_rem_3" name="23_nps_rem_3"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("23_nps_rem_3"):"" %></textarea>
										</td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>Adequacy and efficacy of Internal Control system and procedures</td>
		                                <td><textarea class="observations_23_4 " name="observations_23_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_23_4"):"" %></textarea></td>
										<td><textarea class="management_23_4 " name="management_23_4"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_23_4"):"" %></textarea></td>
										<td>
											<textarea class="23" id="23_nps_rem_4" name="23_nps_rem_4"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("23_nps_rem_4"):"" %></textarea>
										</td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>Adequacy and efficacy of operating systems/software's including investment and accounting systems to ensure:a) System should be capable of maintaining access logs b) Auditor should review access logs and confirm no unauthorized access c) System modification/change request process is documented with proper delegation of authority and all modification requests and authorization records are maintained.</td>
		                                <td><textarea class="observations_23_5 " name="observations_23_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_23_5"):"" %></textarea></td>
										<td><textarea class="management_23_5 " name="management_23_5"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_23_5"):"" %></textarea></td>
										<td>
											<textarea class="23" id="23_nps_rem_5" name="23_nps_rem_5"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("23_nps_rem_5"):"" %></textarea>
										</td>
	                               </tr>
	                              
	                               <tr>
	                               		<th>24.</th>
		                                <th>Internal Audit/ PFM Audit/ Scheme Audit</th>
		                                <td>100%</td>
		                                <td>To see the exceptions of audit & compliance there of</td>
		                                <td><textarea class="observations_24_1 " name="observations_24_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_24_1"):"" %></textarea></td>
										<td><textarea class="management_24_1 " name="management_24_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_24_1"):"" %></textarea></td>
										<td>
											<textarea class="24" id="24_nps_rem_1" name="24_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("24_nps_rem_1"):"" %></textarea>
										</td>
	                              </tr>
	                              <tr>
	                               		<th>25.</th>
		                                <th>Marketing and distribution</th>
		                                <td>100%</td>
		                                <td>To see the exceptions of audit & compliance there of</td>
		                                <td><textarea class="observations_25_1 " name="observations_25_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("observations_25_1"):"" %></textarea></td>
										<td><textarea class="management_25_1 " name="management_25_1"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("management_25_1"):"" %></textarea></td>
										<td>
											<textarea class="25" id="25_nps_rem_1" name="25_nps_rem_1"  readonly="readonly"><%=(Validator.isNotNull(jsonObject))? jsonObject.get("25_nps_rem_1"):"" %></textarea>
										</td>
	                              </tr> 
								</tbody>  
							</table>
								<br><br>
							</div>
						</div>
					</div>
					
					
					
					
					
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
                              if(Validator.isNotNull(executive_summaries)){
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
                              <%}}else{ %>
                               <tr>
                                 <td><input type="hidden" class="rowIndex" id="rowIndex" value="1" name="<portlet:namespace />rowIndex[]"/></td>
                                 <td>
                                    <textarea type="text" class="boardDiscription" id="boardDiscription"  name="<portlet:namespace />boardDiscription[]"></textarea>
                                 </td>
                                 <td>
                                    <textarea type="text" class="auditorObservation" id="auditorObservation" name="<portlet:namespace />auditorObservation[]"></textarea>
                                 </td>
                                 <td>
                                    <textarea type="text" class="riskRating" id="riskRating" name="<portlet:namespace />riskRating[]"></textarea>
                                 </td>
                                  <td>
                                    <textarea type="text" class="managementResponse" id="managementResponse"  name="<portlet:namespace />managementResponse[]"></textarea>
                                 </td>
                                  <td>
                                    <textarea type="text" class="npstComment" id="npstComment" name="<portlet:namespace />npstComment[]"></textarea>
                                 </td>
                                 <td class="text-center">
                                 <%if(index!=1){ %>
                                 <button class="btn btn-primary btn-sm remove" id="removeRow" type="button" style="padding:5px 10px"><i class="fas fa-trash-alt"></i>
                                 </button>
                                 <%} %>
                                 </td>
                              
                              <%} %>
                           </tbody>
                        </table>
                        <br><br>
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
	
/*	.css-serial td:first-child:before {
	  counter-increment: serial-number;  /* Increment the serial number counter */
/*	  content: counter(serial-number);  /* Display the counter */
/*	}*/
	
	.modal {
    display: none;
	}

	/* label.error {
		display: none !important;
	} */
</style>	



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
    	width: 100%;
	}
</style>


 <script type="text/javascript">
$(document).ready(function() {
	
	toastr.options = {
	  "debug": false,
	  "positionClass": "toast-bottom-right",
	  "onclick": null,
	  "fadeIn": 300,
	  "fadeOut": 1000,
	  "timeOut": 6000,
	  "extendedTimeOut": 1000
	}
	
	
	var count = 1;
	
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
       
       $('.boardDiscription').each(function() {
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
/* 		$('.npstComment').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    }); */
       
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

</script>

	<script type="text/javascript">
$(document).ready(function() {
	$("textarea").each(function () {
		this.setAttribute("style", "height:" + (this.scrollHeight) + "px;overflow-y:hidden;");
	}).on("input", function () {
		this.style.height = 0;
		this.style.height = (this.scrollHeight) + "px";
	});
	<%if(pfm_Qr_IARScrutiny.getNps_comments()!=null && !pfm_Qr_IARScrutiny.getNps_comments().trim().equals("")) {%>
	let npscomment = ${pfm_Qr_IARScrutiny.getNps_comments()};
	console.log("npscomment:----", npscomment);
	$.each( npscomment, function( k, v ) {
		//console.log(k,"npscomment:----", v);
		$.each( v, function( key, value ) {
			console.log(key,"npscomment:----", value);
			$("textarea[name='"+key+"']").val(value);
		})
		});
	<%}%>
		$("form.form").validate({
		  rules: {
				observations_i_a: {
			      required: true
			    },
			    management_i_a: {
				      required: true
				},
				observations_i_b: {
					required: true
				},
				management_i_b: {
					required: true
				},
				observations_i_c:{
					required: true
				},
				management_i_c:{
					required: true
				},
				observations_i_e:{
					required: true
				},
				management_i_e:{
					required: true
				},
				observations_i_d:{
					required: true
				},
				management_i_d:{
					required: true
				},
				observations_ii_a:{
					required: true
				},
				management_ii_a: {
				      required: true
			    },
			    observations_ii_b: {
				      required: true
				},
				management_ii_b: {
					required: true
				},
				observations_ii_c: {
					required: true
				},
				management_ii_c:{
					required: true
				},
				observations_ii_d:{
					required: true
				},
				management_ii_d:{
					required: true
				},
				observations_ii_e:{
					required: true
				},
				management_ii_e:{
					required: true
				},
				observations_ii_f:{
					required: true
				},
				management_ii_f:{
					required: true
				},
				observations_ii_g:{
					required: true
				},
				management_ii_g:{
					required: true
				},
				observations_iii_a:{
					required: true
				},
				management_iii_a:{
					required: true
				},
				observations_iii_b: {
				      required: true
			    },
			    management_iii_b: {
				      required: true
				},
				observations_iii_c: {
					required: true
				},
				management_iii_c: {
					required: true
				},
				observations_iii_d:{
					required: true
				},
				management_iii_d:{
					required: true
				},
				observations_iii_e:{
					required: true
				},
				management_iii_e:{
					required: true
				},
				observations_iv_a:{
					required: true
				},
				management_iv_a:{
					required: true
				},
				observations_iv_b: {
				      required: true
			    },
			    management_iv_b: {
				      required: true
				},
				observations_iv_c: {
					required: true
				},
				management_iv_c: {
					required: true
				},
				observations_iv_d:{
					required: true
				},
				management_iv_d:{
					required: true
				},
				observations_iv_e:{
					required: true
				},
				management_iv_e:{
					required: true
				},
				observations_iv_f:{
					required: true
				},
				management_iv_f: {
				      required: true
			    },
			    observations_iv_g: {
				      required: true
				},
				management_iv_g: {
					required: true
				},
				observations_iv_h: {
					required: true
				},
				management_iv_h:{
					required: true
				},
				observations_v_a:{
					required: true
				},
				management_v_a:{
					required: true
				},
				observations_v_b:{
					required: true
				},
				management_v_b: {
				      required: true
			    },
			    observations_v_c: {
				      required: true
				},
				management_v_c: {
					required: true
				},
				observations_v_d: {
					required: true
				},
				management_v_d:{
					required: true
				},
				observations_vi_a:{
					required: true
				},
				management_vi_a:{
					required: true
				},
				observations_vi_b:{
					required: true
				},
				management_vi_b:{
					required: true
				},
				observations_vi_c: {
					required: true
				},
				management_vi_c: {
					required: true
				},
				observations_vi_d:{
					required: true
				},
				management_vi_d:{
					required: true
				},
				observations_vi_e:{
					required: true
				},
				management_vi_e:{
					required: true
				},
				observations_vi_f:{
					required: true
				},
				management_vi_f:{
					required: true
				},
				observations_vi_g:{
					required: true
				},
				management_vi_g:{
					required: true
				},
				observations_vi_h: {
					required: true
				},
				management_vi_h: {
					required: true
				},
				observations_vi_i:{
					required: true
				},
				management_vi_i:{
					required: true
				},
				observations_vi_j:{
					required: true
				},
				management_vi_j:{
					required: true
				},
				observations_vi_k:{
					required: true
				},
				management_vi_k:{
					required: true
				},
				observations_vi_l:{
					required: true
				},
				management_vi_l:{
					required: true
				},
				observations_vi_m:{
					required: true
				},
				management_vi_m:{
					required: true
				},
				observations_vii_a:{
					required: true
				},
				management_vii_a: {
					required: true
				},
				observations_vii_b: {
					required: true
				},
				management_vii_b:{
					required: true
				},
				observations_vii_c:{
					required: true
				},
				management_vii_c:{
					required: true
				},
				observations_vii_d:{
					required: true
				},
				management_vii_d:{
					required: true
				},
				observations_vii_e: {
					required: true
				},
				management_vii_e: {
					required: true
				},
				observations_vii_f: {
					required: true
				},
				management_vii_f: {
					required: true
				},
				observations_viii_a:{
					required: true
				},
				management_viii_a:{
					required: true
				},
				observations_viii_b:{
					required: true
				},
				management_viii_b:{
					required: true
				},
				observations_viii_c:{
					required: true
				},
				management_viii_c: {
					required: true
				},
				observations_viii_d:{
					required: true
				},
				management_viii_d: {
					required: true
				},
				observations_ix_a: {
					required: true
				},
				management_ix_a:{
					required: true
				},
				observations_ix_b:{
					required: true
				},
				management_ix_b:{
					required: true
				},
				observations_ix_c:{
					required: true
				},
				management_ix_c:{
					required: true
				},
				observations_ix_d: {
					required: true
				},
				management_ix_d: {
					required: true
				},
				observations_x_a:{
					required: true
				},
				management_x_a:{
					required: true
				},
				observations_x_b:{
					required: true
				},
				management_x_b:{
					required: true
				},
				observations_x_c:{
					required: true
				},
				management_x_c: {
					required: true
				},
				observations_x_d: {
					required: true
				},
				management_x_d:{
					required: true
				},
				observations_x_e:{
					required: true
				},
				management_x_e:{
					required: true
				},
				observations_x_f:{
					required: true
				},
				management_x_f:{
					required: true
				},
				observations_xi_a: {
					required: true
				},
				management_xi_a: {
					required: true
				},
				observations_xi_b:{
					required: true
				},
				management_xi_b:{
					required: true
				},
				observations_xii_a:{
					required: true
				},
				management_xii_a:{
					required: true
				},
				observations_xii_b:{
					required: true
				},
				management_xii_b: {
					required: true
				},
				observations_xiii_a: {
					required: true
				},
				management_xiii_a:{
					required: true
				},
				observations_xiii_b:{
					required: true
				},
				management_xiii_b:{
					required: true
				},
				observations_xiii_c:{
					required: true
				},
				management_xiii_c:{
					required: true
				},
				observations_xiv_a: {
					required: true
				},
				management_xiv_a:{
					required: true
				},
				observations_xiv_b:{
					required: true
				},
				management_xiv_b:{
					required: true
				},
				observations_xiv_c:{
					required: true
				},
				management_xiv_c:{
					required: true
				},
				observations_xv_a:{
					required: true
				},
				management_xv_a:{
					required: true
				},
				observations_xv_b:{
					required: true
				},
				management_xv_b:{
					required: true
				},
				observations_xvi_a:{
					required: true
				},
				management_xvi_a:{
					required: true
				},
				observations_xvi_b:{
					required: true
				},
				management_xvi_b:{
					required: true
				},
				observations_xvi_c:{
					required: true
				},
				management_xvi_c:{
					required: true
				},
				observations_xvii_a:{
					required: true
				},
				management_xvii_a:{
					required: true
				},
				observations_xvii_b:{
					required: true
				},
				management_xvii_b:{
					required: true
				},
				observations_xviii_a:{
					required: true
				},
				management_xviii_a:{
					required: true
				},
				observations_xviii_b:{
					required: true
				},
				management_xviii_b:{
					required: true
				},
				observations_xviii_c:{
					required: true
				},
				management_xviii_c:{
					required: true
				},
				observations_xviii_d:{
					required: true
				},
				management_xviii_d:{
					required: true
				},
				observations_xviii_e:{
					required: true
				},
				management_xviii_e:{
					required: true
				},
				observations_xviii_f:{
					required: true
				},
				management_xviii_f:{
					required: true
				},
				observations_xviii_g:{
					required: true
				},
				management_xviii_g:{
					required: true
				},
				observations_xviii_h:{
					required: true
				},
				management_xviii_h:{
					required: true
				},
				observations_xviii_i:{
					required: true
				},
				management_xviii_i:{
					required: true
				},
				observations_xviii_j:{
					required: true
				},
				management_xviii_j:{
					required: true
				},
				observations_xviii_k:{
					required: true
				},
				management_xviii_k:{
					required: true
				},
				observations_xix_a:{
					required: true
				},
				management_xix_a:{
					required: true
				},
				observations_xix_b:{
					required: true
				},
				management_xix_b:{
					required: true
				},
				observations_xix_c:{
					required: true
				},
				management_xix_c:{
					required: true
				},
				observations_xix_d:{
					required: true
				},
				management_xix_d:{
					required: true
				},
				observations_xix_e:{
					required: true
				},
				management_xix_e:{
					required: true
				},
				observations_xix_f:{
					required: true
				},
				management_xix_f:{
					required: true
				},
				observations_xx_a:{
					required: true
				},
				management_xx_a:{
					required: true
				},
				observations_xx_b:{
					required: true
				},
				management_xx_b:{
					required: true
				},
				observations_xx_c:{
					required: true
				},
				management_xx_c:{
					required: true
				},
				observations_xx_d:{
					required: true
				},
				management_xx_d:{
					required: true
				},
				observations_xx_e:{
					required: true
				},
				management_xx_e:{
					required: true
				},
				observations_xxi_a:{
					required: true
				},
				management_xxi_a:{
					required: true
				},
				observations_xxi_b:{
					required: true
				},
				management_xxi_b:{
					required: true
				},
				observations_xxi_c:{
					required: true
				},
				management_xxi_c:{
					required: true
				},
				observations_xxi_d:{
					required: true
				},
				management_xxi_d:{
					required: true
				},
				observations_xxi_e:{
					required: true
				},
				management_xxi_e:{
					required: true
				},
				observations_xxii_a:{
					required: true
				},
				management_xxii_a:{
					required: true
				},
				observations_xxii_b:{
					required: true
				},
				management_xxii_b:{
					required: true
				},
				observations_xxii_c:{
					required: true
				},
				management_xxii_c:{
					required: true
				},
				observations_xxii_d:{
					required: true
				},
				management_xxii_d:{
					required: true
				},
				observations_xxiii_a:{
					required: true
				},
				management_xxiii_a:{
					required: true
				},
				observations_xxiii_b:{
					required: true
				},
				management_xxiii_b:{
					required: true
				},
				observations_xxiii_c:{
					required: true
				},
				management_xxiii_c:{
					required: true
				},
				observations_xxiii_d:{
					required: true
				},
				management_xxiii_d:{
					required: true
				},
				observations_xxiii_e:{
					required: true
				},
				management_xxiii_e:{
					required: true
				},
				observations_xxiv_a:{
					required: true
				},
				management_xxiv_a:{
					required: true
				},
				observations_xxv_a:{
					required: true
				},
				management_xxv_a:{
					required: true
				},
				/*extracts_board_pdf:{
					required: true,
					//extracts_board_pdfvalidateFileName:true
				},
				annex_comp_certificate:{
					required: true,
				},*/
			}
		});
		
		$('#annex_comp_certificate').bind('change', function () {
			  var filename = $("#annex_comp_certificate").val();
			  if (/^\s*$/.test(filename)) {
			     $(".file-upload").removeClass('active');
			     $("#noFile10").text("No file chosen...");
			  }
			  else {
			     $(".file-upload").addClass('active');
			     $("#noFile10").text(filename.replace("C:\\fakepath\\", ""));
			  }
			});
		$('#extracts_board_pdf').bind('change', function () {
			  var filename = $("#extracts_board_pdf").val();
			  if (/^\s*$/.test(filename)) {
			     $(".file-upload").removeClass('active');
			     $("#noFile11").text("No file chosen...");
			  }
			  else {
			     $(".file-upload").addClass('active');
			     $("#noFile11").text(filename.replace("C:\\fakepath\\", ""));
			  }
			});
		$('#annex_comp_certificate_i').bind('change', function () {
			  var filename = $("#annex_comp_certificate_i").val();
			  if (/^\s*$/.test(filename)) {
			     $(".file-upload").removeClass('active');
			     $("#noFile12").text("No file chosen...");
			  }
			  else {
			     $(".file-upload").addClass('active');
			     $("#noFile12").text(filename.replace("C:\\fakepath\\", ""));
			  }
			});
		$('#annex_comp_certificate_ii').bind('change', function () {
			  var filename = $("#annex_comp_certificate_ii").val();
			  if (/^\s*$/.test(filename)) {
			     $(".file-upload").removeClass('active');
			     $("#noFile13").text("No file chosen...");
			  }
			  else {
			     $(".file-upload").addClass('active');
			     $("#noFile13").text(filename.replace("C:\\fakepath\\", ""));
			  }
			});
		$('#annex_comp_certificate_iii').bind('change', function () {
			  var filename = $("#annex_comp_certificate_iii").val();
			  if (/^\s*$/.test(filename)) {
			     $(".file-upload").removeClass('active');
			     $("#noFile14").text("No file chosen...");
			  }
			  else {
			     $(".file-upload").addClass('active');
			     $("#noFile14").text(filename.replace("C:\\fakepath\\", ""));
			  }
			});
		
		/* $.validator.addMethod('extracts_board_pdfvalidateFileName', function(value, element) {
			let filename = 'Extracts of board minutes approving IAR.pdf';
		   return this.optional(element) || (value === filename); 
		}, 'Please upload file Extracts of board minutes approving IAR.pdf'); */
		
		
		$('#btn-submit').on('click', function(){ 
			let error = false;
			if ($('input[name="annex_comp_certificate"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-annex_comp_certificate").html("Please select a file to upload");
	    	    error = false;
	    	}else if($('input[name="annex_comp_certificate"]').get(0).files[0].name != "Signed IAR in PDF.pdf"){
	    		console.log("No files selected.");
	    	    $("#error-annex_comp_certificate").html("Please upload Signed IAR in PDF.pdf");
	    	    error = false;
	    	}else{
	    		error = true;
	    		$("#error-annex_comp_certificate").html("");
	    	}
			if ($('input[name="extracts_board_pdf"]').get(0).files.length === 0) {
	    	    console.log("No files selected.");
	    	    $("#error-extracts_board_pdf").html("Please select a file to upload");
	    	    error = false;
	    	}else if($('input[name="extracts_board_pdf"]').get(0).files[0].name != "Signed Extracts of board minutes approving IAR.pdf"){
	    		console.log("No files selected.");
	    	    $("#error-extracts_board_pdf").html("Please upload Signed Extracts of board minutes approving IAR.pdf");
	    	    error = false;
	    	}else{
	    		error = true;
	    		$("#error-extracts_board_pdf").html("");
	    	}
	    	
	    	
	    	if($("form.form").valid() && error){
	    		$("#sub").addClass("hide");
	    		var rc = $("#myTable_1 tbody tr").length;
    			console.log("rowCountFT ", rc);
    			$("#rowCountFT").val(rc);
	    		
		        var fd = new FormData($("form.form")[0]);
		        
		       /*  var htmlWidth = $("#canvasD").width();
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
	 				doc.save("split-to-multi-page.pdf");
				     window.open(doc.output('bloburl')); 
				     
					var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
				     
					console.log("file::: ", file);
					
					fd.append("compliance_certificate_pdf_file", file); */

			        $(".content").hide();
			        $(".animaion").show();
			        $.ajax({
			            url: '<%=pfminternalauditreportURL %>',  
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
				
			/* });	 */
	    	}else{
	    		toastr.error('Please fill the required field(s).');
	    		return false;
	    		//$('#output').html("Please fill the required field.");
	    	}
	    });
//	});
});
	</script>
	