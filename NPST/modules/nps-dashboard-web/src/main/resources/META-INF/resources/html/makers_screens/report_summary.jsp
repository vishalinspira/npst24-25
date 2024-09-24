<%@page import="nps.common.service.util.RegexUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%-- <%@page import="nps.common.service.util.PreviewFileURLUtil"%> --%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.document.library.kernel.model.DLFileEntry"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp"%>
<% 
	String dept = (String) request.getAttribute("dept");
	List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>)request.getAttribute("reportUploadFileLogs");
	JSONArray urlArray = (JSONArray) request.getAttribute("urlArray");
			
	/*if(dept.equalsIgnoreCase("PFM")){
		 urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
	}else{
		 urlArray = PreviewFileURLUtil.getPreviewMultiFileURL(themeDisplay, reportUploadFileLogs);
	}*/
	
	String fileEntryId = "0";
	boolean isDocSigned = false;
%>

	<nav aria-label="breadcrumb">
	  <ol class="breadcrumb bg-transparent">
	  	<li class="breadcrumb-item">Dashboard</li>
	  	<li class="breadcrumb-item">Report Status</li>
	    <li class="breadcrumb-item active" aria-current="page">Report Summary</li>
	  </ol>
	</nav>

<div class="nps-page-main pending-table mt-md-0 mt-sm-3 mt-3">
     <div class="row">
        <div class="col-12">
           <div class="text-right">
                <p  class="back_btn"><a class="backbtn" onclick="history.back()"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
        </div>
       </div>
   <div class="nps-pending-table">
       <div class="mydataTable">
            <ul class="nav nav-tabs border-0 mb-3 nps-report-main">
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link active p-0" href="#report-summary"> 
                    	Report Summary
                    </a>
                </li>
            </ul>
            
            <div class="tab-content">
                <div class="tab-pane active py-3" id="report-summary">
                	<p>Report Name: ${reportName}</p> <p>Due Date: ${dueDate}</p>
					<%-- <div>
					<%
						int i =1;
						for(ReportUploadFileLog reportUploadFileLog : reportUploadFileLogs){
							DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadFileLog.getFileEntryId());
					%>
						<span>File Version : </span>
						<span> 
							<a href="<%= (Validator.isNotNull(dlFileEntry) ? "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle() : "javascript:void(0);")%>"><%=i%></a>
						</span>
					<%
						i++;
						}
					%>
					</div> --%>
					<div>
					<span>File Version : </span>
					<%
					  if(urlArray.length() > 0)
						for(int i=0; i<urlArray.length();i++){
							JSONObject object = urlArray.getJSONObject(i);
							
							isDocSigned = Validator.isNotNull(object.getString("signature")); 
							fileEntryId = object.getString("fileEntryId");
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
									<% if(j != (fileList.length()-1)){
										if(Validator.isNotNull(dlFileEntry)){
										%>
									 ,
									<% }} %>
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
                	<table id="report_summary_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
						<thead>
							<tr>
								<th style="width: 0%;"> </th>
								<th>User Name</th>
								<th>Date</th>
								<th>Remarks</th>
							</tr>
						</thead>
					</table>
                </div>
			</div>
		</div>
	</div>
</div>

<script>

$(document).ready(function() {
	console.log(<%=urlArray%>);
	$.fn.dataTable.moment('DD-MM-YYYY hh:mm A');
	createReportsSummaryTable();
});

function createReportsSummaryTable() {
	$('#report_summary_table').DataTable({
		searching : false,
		pagination : "bootstrap",
		filter : true,
		data : ${jsonArray},
		destroy : true,
		lengthMenu : [ 5, 10, 25 ],
		pageLength : 10,
		order: [[2, 'desc']],
		language: {
	            sLengthMenu: " _MENU_",
	            "info": "Showing  _START_  to  _END_  of  _TOTAL_  entries",
	            oPaginate: {
	                sNext: 'NEXT',
	                sPrevious: 'PREV'
	            }
	     },
		"dom": '<"top"f>rt<"bottom"<"left"li>p><"clear">',
		"columns" : [
			{
		        'visible': false,
		        'searchable': false,
		        data : "id",
		        name : "id"
		    },
			{
				data : "userName",
				name : "userName"
			},
			{
				data : "createDate",
				name : "createDate"
			},
			{
				data : "remarks",
				name : "remarks",
				orderable: false
			}
		]					
	});
}

</script>