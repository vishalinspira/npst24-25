<%@page import="nps.common.service.util.PfmHyccUtil"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil"%>
<%@page import="com.nps.pfm.hy.comp.cert.util.PfmHyccNonNps"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.nps.pfm.hy.comp.cert.util.PrePopulateScrutinydata"%>
<%@page import="com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil"%>
<%@page import="com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny"%>
<%@page import="com.daily.average.service.model.PFM_hy_comp_cert"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@ include file="/init.jsp" %>


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
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

boolean isAssignable = Validator.isNotNull(request.getAttribute("isAssignable")) ? (boolean) request.getAttribute("isAssignable") : false;
boolean isSelfAsignee = Validator.isNotNull(request.getAttribute("isSelfAsignee")) ? (boolean) request.getAttribute("isSelfAsignee") : false;
/* Pre populate data for asset view */
boolean isSupervisor  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
boolean isDocSigned = false;
		  
List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");
	
	boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);

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
            <h4>Half - yearly Compliance Certificates</h4>
            <form class="form" id="scrutinyForm" action="#" method="post">
               <input type="hidden" value="${reportUploadLogId }" name="reportUploadLogId" class="reportUploadLogId" />
               <input type="hidden" value="${reportMasterId }" name="reportMasterId" class="reportMasterId"/>
               <div class="row">
                 <%--  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" name="reportDate" value="${reportDate }" >
                     </div>
                  </div> --%>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <label class="pl-3">For the half - year ended</label>
                        <input type="text" class="rounded border-0 p-1 ml-3" id="year" name='year' value="<%=pfm_hy_comp_cert.getYear()%>" readonly>
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
                   <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input class="1" type="radio" id="1y" name='1_1' value="Yes" <%=(jsonObject.get("1_1")).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label" for="1y">&nbsp; Yes</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="1" type="radio" id="1n" name='1_1' value="No" <%=(jsonObject.get("1_1")).equals("No")?"checked":"" %>>
                        <label class="form-check-label" for="1n">&nbsp; No</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="2" type="radio" id="1n" name='1_1' value="NA" <%=(jsonObject.get("1_1")).equals("NA")?"checked":"" %>>
                        <label class="form-check-label" for="1n">&nbsp; NA</label> 
                     </div>
                     <label id="1-error" class="error" for="1_1"></label>
                     <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%=(jsonObject.get("1_1"))%></label> 
                              </div>
                              <% } %>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea  class="form-control rem1" id="1_rem_1" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %> name="1_rem_1" ><%=(jsonObject.get("1_rem_1"))%></textarea>
                 		</div>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea  class="form-control rem1" id="1_npsrem_1" placeholder="Remarks if any" <%=isNonNPSUser ? "disabled": "" %> name="1_npsrem_1" ><%=(jsonObject.get("1_npsrem_1"))%></textarea>
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
                   <% if (isNonNPSUser) { %>
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
                     <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%=(jsonObject.get("2_1"))%></label> 
                              </div>
                              <% } %>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea  class="form-control rem2" id="2_rem_1" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %> name="2_rem_1" ><%=(jsonObject.get("2_rem_1"))%></textarea>
                 </div>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea  class="form-control rem2" id="2_npsrem_1" placeholder="Remarks if any" <%=isNonNPSUser ? "disabled": "" %> name="2_npsrem_1" ><%=(jsonObject.get("2_npsrem_1"))%></textarea>
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
                   <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input class="3" type="radio" id="3y" name='3_1' value="Yes" <%=(jsonObject.get("3_1")).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label" for="3y">&nbsp; Yes</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="3" type="radio" id="3n" name='3_1' value="No" <%=(jsonObject.get("3_1")).equals("No")?"checked":"" %>>
                        <label class="form-check-label" for="3n">&nbsp; No</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="2" type="radio" id="3n" name='3_1' value="NA" <%=(jsonObject.get("3_1")).equals("NA")?"checked":"" %>>
                        <label class="form-check-label" for="3n">&nbsp; NA</label> 
                     </div>
                     <label id="3-error" class="error" for="3_1"></label>
                     <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%=(jsonObject.get("3_1"))%></label> 
                              </div>
                              <% } %>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea  class="form-control rem3" id="3_rem_1" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %> name="3_rem_1" ><%=(jsonObject.get("3_rem_1"))%></textarea>
                 </div>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea  class="form-control rem1" id="3_npsrem_1" placeholder="Remarks if any" <%=isNonNPSUser ? "disabled": "" %> name="3_npsrem_1" ><%=(jsonObject.get("3_npsrem_1"))%></textarea>
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
                   <% if (isNonNPSUser) { %>
                     <div class="form-check form-check-inline">
                        <input class="4" type="radio" id="4y" name='4_1' value="Yes" <%=(jsonObject.get("4_1")).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label" for="4y">&nbsp; Yes</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="4" type="radio" id="4n" name='4_1' value="No" <%=(jsonObject.get("4_1")).equals("No")?"checked":"" %>>
                        <label class="form-check-label" for="4n">&nbsp; No</label> 
                     </div>
                     <div class="form-check form-check-inline">
                        <input class="2" type="radio" id="4n" name='4_1' value="NA" <%=(jsonObject.get("4_1")).equals("NA")?"checked":"" %>>
                        <label class="form-check-label" for="4n">&nbsp; NA</label> 
                     </div>
                     <label id="4-error" class="error" for="4_1"></label>
                     <% } else {%>
                              <div class="form-check form-check-inline">
                                 <label class="form-check-label" >&nbsp; <%=(jsonObject.get("4_1"))%></label> 
                              </div>
                              <% } %>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea  class="form-control rem4" id="4_rem_1" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %> name="4_rem_1" ><%=(jsonObject.get("4_rem_1"))%></textarea>
                 </div>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea  class="form-control rem4" id="4_npsrem_1" placeholder="Remarks if any" <%=isNonNPSUser ? "disabled": "" %> name="4_npsrem_1" ><%=(jsonObject.get("4_npsrem_1"))%></textarea>
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
                        <input  type="date"  name='date3' value="<%=dateFormat.format(pfm_hy_comp_cert.getDate3())%>" readonly>
                     </div>
                </div>
                <div class="col-md-12">
                     <div class="form-check form-check-inline">
                     	<label class="form-check-label" for="place">Place:</label> 
                        <input  type="text"  name='place' value="<%=pfm_hy_comp_cert.getHycc_place()%>" readonly>
                     </div>
                </div>
               </div>
               
                <% if(!isNonNPSUser){ %>
		                 <div class="row">
				                
					                  <div class="col-lg-12 col-md-12 col-sm-12 col-12 pt-12">
					                  	<div class="form-group">
				                        	<textarea class="form-control" id="npsRemark" placeholder="AM Remarks" name="npsRemark"  ><%= (Validator.isNotNull(pfm_hy_comp_certScrutiny))? pfm_hy_comp_certScrutiny.getNps_comments():"" %></textarea>
				                        </div>
					                  </div>
				                 </div>
				                 <br>
				                 <%} %>
               
              <div class="row" id="sub">
              <%if(!(isPfmSupervisor && isDocSigned) && (isAssignable || isSelfAsignee)){ %> 
                  <div class="col-md-12">
                     <div class="text-center">
                        <input type="button" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
                     </div>
                  </div>
                  <%} %>
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
		input.error {
			border:1px solid red !important;
		}
		
		/* label.error {
			display: none !important;
		} */
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

	
	
$(document).ready(function() {
	<%--
	<%if(pfm_hy_comp_cert.getHycc_details()!=null && !pfm_hy_comp_cert.getHycc_details().trim().equals("")) {%>
		let hyccdetails = ${pfm_hy_comp_cert.getHycc_details()};
		console.log("npsdetails:----", hyccdetails);
		$.each( hyccdetails, function( k, v ) {
			//console.log(k,"npscomment:----", v);
			$.each( v, function( key, value ) {
				console.log(key,"hyccdetails:----", value, $("input[name='"+key+"'][value='"+value+"']").attr("checked",true));
				//$("textarea[name='"+k+"'][value='"+v[1]+"']").prop("checked",true);
			})
		});
	<%}%>
		 <%if(pfm_hy_comp_cert_Scrutiny.getNps_comments()!=null && !pfm_hy_comp_cert_Scrutiny.getNps_comments().trim().equals("")) {%>
			let npscomment = ${pfm_hy_comp_cert_Scrutiny.getNps_comments()};
			console.log("npscomment:----", npscomment);
			$.each( npscomment, function( k, v ) {
				//console.log(k,"npscomment:----", v);
				$.each( v, function( key, value ) {
					console.log(key,"npscomment:----", value);
					$("textarea[name='"+key+"']").val(value);
				})
			});
		<%}%> --%>
		
	let scrURL = "/web/guest/compliance-certificate-at-half-yearly-interval?p_p_id=com_nps_pfm_hy_comp_cert_PFM_hy_comp_certPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=PFM_hy_comp_cert_Scrutiny&p_p_cacheability=cacheLevelPage";

		$('#btn-submit').on('click', function(){ 
	    	//if($("form.form").valid()){
		        var fd = new FormData($("#scrutinyForm")[0]);
		        console.log(fd);
		        $(".content").hide();
		        $(".animaion").show();
		        $.ajax({
		            url: scrURL,  
		            type: 'POST',
		            data: fd,
		            cache: false,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	console.log("Inside Success");
		            	$(".content").show();
		                $(".animaion").hide();
		                toastr.success('Form has been submitted successfully!');
		                //location.reload();
		                //$("form.form")[0].reset();
		                //$('#output').html("Data submited successfuly.");
		            },
		            error: function() {
		            	$(".animaion").hide();
		           		console.log("Error Occured in ajax call");
		           		toastr.error('An error occured while submitting the form');
		            },
		            complete: function(){
		            	$(".animaion").hide();
						console.log("Complete");
			        }
		        });
	    	/* }else{
	    		toastr.error('Please fill the required field(s).');
	    		//$('#output').html("Please fill the required field.");
	    	} */
	    });
	});

	</script>
	
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>