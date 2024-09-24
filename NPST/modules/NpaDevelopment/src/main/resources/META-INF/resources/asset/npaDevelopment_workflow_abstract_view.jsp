<%@page import="nps.common.service.util.CommonRoleService"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.daily.average.service.service.MnNpaDevelopmentLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.MnNpaDevelopment"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@ include file="/init.jsp" %>

<% 
	MnNpaDevelopment npaDevelop = Validator.isNotNull(request.getAttribute("npaDevelopment")) ? (MnNpaDevelopment) request.getAttribute("npaDevelopment") : MnNpaDevelopmentLocalServiceUtil.createMnNpaDevelopment(0);
	JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
	if(npaDevelop!=null) {
		jsonArray = JSONFactoryUtil.createJSONArray(npaDevelop.getTabledetails());
	}
	
	boolean isNonNpsUser=CommonRoleService.isNonNpsUser(user.getUserId());
	List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");
	
	boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
	boolean isDocSigned = false;
	
	JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
	String fileEntryId = "0";
	JSONObject signature = JSONFactoryUtil.createJSONObject();

  
	
%>

<div class="nps-page-main nps-upload-report nps-statement-wrapper">

<div>
	<span>File Version : </span>
<% 
JSONObject jo = JSONFactoryUtil.createJSONObject();
for(int i=0; i<urlArray.length();i++){
	JSONObject object = urlArray.getJSONObject(i);
	isDocSigned = Validator.isNotNull(object.getString("signature")); 
	fileEntryId = object.getString("fileEntryId");
	signature = JSONFactoryUtil.createJSONObject();
	if(isDocSigned){
		signature = JSONFactoryUtil.createJSONObject(object.getString("signature"));
	}
%>
<%if(isNonNpsUser){ %>
<span class="file-preview-span" data-index='<%= i%>'>
		<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a> 
	</span>
		<% if(i != (urlArray.length()-1)){ %>
	 ,
	<% } %>
<%} %>

<%if(!isNonNpsUser){
	if(isDocSigned){
	%>
<span class="file-preview-span" data-index='<%= i%>'>
		<a href="<%= object.getString("downloadURL")%>"><%=object.getString("version")%></a> 
	</span>
		<% if(i != (urlArray.length()-1)){ %>
	 ,
	<% } %>
<%}} %>
	

<% } %>
</div>
 
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
            <h4>Development in Default securities</h4>
            <form class="form" id="npaDevelopment" action="#" method="post">
               <div class="statement-wrapper">
                  <div id="table" class="table-editable">
                     <div class="table-cont">
                        <table id="myTable_1" class="table css-serial">
                           <thead>
                              <tr>
                                 <!-- <th>Sr.No</th> -->
                                 <th>Name of Security</th>
                                 <th>Legal Case details</th>
                                 <th>Current Status</th>
                              </tr>
                           </thead>
                           <tbody>
							
							<%
								for (int i=0; i < jsonArray.length(); i++) {
									JSONObject jsonObject = jsonArray.getJSONObject(i);
									jo = jsonObject;
							%>		
								<tr>      
									<td><%=jsonObject.getString("nameOfSecurity") %></td>
							        <td><%=jsonObject.getString("legalCaseDetails") %></td>
							        <td><%=jsonObject.getString("currentStatus") %></td>
							    </tr>
							<% 
								}
							%>
                           </tbody>
                        </table>
                        <br><br>
                     </div>
                  </div>
               </div>
            </form>
         </div>
      </div>
   </div>
</div>
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
				/* if(CertInfo.eMail === themeDisplay.getUserEmailAddress()){  */	
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
			            	console.log("complete : "+data);
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
	console.log(<%=jo%>);
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>
	        

  
