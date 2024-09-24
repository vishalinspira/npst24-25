<%@page import="nps.common.service.util.RegexUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="javax.portlet.RenderRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.document.library.kernel.model.DLFileEntry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@ include file="/init.jsp" %>

<%
	List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");
	

	boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
	boolean isPfmAM = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_AM, false);
	boolean isDocSigned = false;
	JSONArray urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
	String fileEntryId = "0";
	JSONObject signature = JSONFactoryUtil.createJSONObject();
%>

<p>Report Name: ${reportName}</p>
<p>Upload Date: ${uploadDate}</p>

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
	JSONArray fileList = object.getJSONArray("fileList");
	if(Validator.isNotNull(fileList) && fileList.length() > 0){%>
	<%=object.getString("version")+" :- " %>
	<%for(int j = 0; j<fileList.length(); j++){
		DLFileEntry dlFileEntry=null;
	try{
		long fileId=Long.valueOf(fileList.getJSONObject(j).getString("fileEntryId"));
		 dlFileEntry= DLFileEntryLocalServiceUtil.getDLFileEntry(fileId);
		
	}catch(Exception e){}
	%>
	<%if(Validator.isNotNull(dlFileEntry)){ %>
	<span class="file-preview-span" data-index='<%= i%>'> <a href="<%=fileList.getJSONObject(j).getString("downloadURL")%>"> <%=RegexUtil.removeStartingDigits(dlFileEntry.getTitle())%></a></span>
	<%} %>
		<%-- <span class="file-preview-span" data-index='<%= i%>'> <a href="<%=fileList.getJSONObject(j).getString("downloadURL")%>"> <%=" Attachment:"+(j+1)%></a></span> --%>
			<% if(j != (fileList.length()-1)){ %>
			 ,
			<% } %>
	<%} %><br>
<%}else{
%>
	<span class="file-preview-span" data-index='<%= i%>'><a href="<%=object.getString("downloadURL")%>"> <%=object.getString("version")%></a></span>
	<% if(i != (urlArray.length()-1)){ %>
	 ,
	<% } %>
<% } 
}%>
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
<script type="text/javascript">
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
		$("h6.signedmsg").append(" by "+sigsub.split(",")[0].split("=")[1]); 
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

let urlArray = <%= urlArray%>;
$(".file-preview-span").click(function(){
	let index = $(this).attr("data-index");
	let jsonObject = urlArray[index];
	
	$(".file-preview-section").removeClass("show").addClass("hide");
	$("#no-preview-error").removeClass("show").addClass("hide");
	
	let previewObject = jsonObject.previewObject;
	
	if(previewObject.status){
		$("#download-link").attr("href",jsonObject.downloadURL);
		$(".file-preview-section").removeClass("hide").addClass("show");
		if(jsonObject.isExcel){
			let baseCode = previewObject.baseCode;
			$(".file-preview-section").html('<embed src="data:application/pdf;base64,'+baseCode+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&" type="application/pdf" id="file-preview-object" width="100%" height="500" />');
		}else{
			let previewURL = previewObject.previewURL;
			$(".file-preview-section").html('<embed src="'+previewURL+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&" type="application/pdf" id="file-preview-object" width="100%" height="500" />');
		}
		
	}else{
		$("#no-preview-error").removeClass("hide").addClass("show");
		$("#error-message").html(previewObject.message);
		$("#download-link").attr("href","javascript:void(0);");
	}
	
	$("#previewModal").modal("show");
});

$(".previewModalClose").click(function(){
	$("#previewModal").modal("hide");
});


</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>

<%@include file="/asset/preview_file.jsp" %>
<style>
.file-preview-span {
	cursor: pointer;
}
.modal {
    display: none;
	}
</style>
