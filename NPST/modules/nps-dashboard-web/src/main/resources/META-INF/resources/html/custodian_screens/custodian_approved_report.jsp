
<%@page import="com.nps.dashboard.web.util.FetchCompanyReportUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="nps.common.service.constants.NameMappingConstants"%>
<%@page import="com.daily.average.service.model.ReportStatusLog"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="../../init.jsp"%>
<%
String department = ParamUtil.getString(request, "department");
String url = dashboardBaseURL+"#sdd"+department;
FetchCompanyReportUtil fcr=new FetchCompanyReportUtil();
List<ReportStatusLog> reports=fcr.getReports(NPSCompany.CUSTODIAN, "APPROVED_REPORT_SUMMERY",themeDisplay);
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
%>

	<!-- <nav aria-label="breadcrumb">
	  <ol class="breadcrumb bg-transparent">
	  	<li class="breadcrumb-item">Dashboard</li>
	    <li class="breadcrumb-item active" aria-current="page">Report Status</li>
	  </ol>
	</nav> -->
<div class="nps-page-main pending-table mt-md-0 mt-sm-3 mt-3">

  <div class="row">
     <div class="col-12">
        <div class="text-right">
            <p  class="back_btn"><a class="backbtn" href="<%=url %>" ><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
        </div>
     </div>
  </div>

   <div class="nps-pending-table">
       <div class="mydataTable">
            <ul class="nav nav-tabs border-0 mb-3 nps-report-main">
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link active p-0 tab_active_btn"> 
                    	<!-- Report Status  -->
                    	Approved Report
                    </a>
                </li>
            </ul>
            
            <div class="tab-content">
                <div class="tab-pane active py-3" id="report-status">
                	<table id="report_status_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
						<thead>
							<tr>
								<th>Report Name</th>
								<th>Intermediary name</th>
								<th>Due Date</th>
								<th>Upload Date</th>
								<!-- <th>Assigned To</th>
								<th>Status</th>	 -->
								<th>Remarks</th>
								<th>Report Summary</th>
							</tr>
						</thead>
						<tbody>
						<%if(Validator.isNotNull(reports)){ 
						for(ReportStatusLog reportStatusLog:reports){
						//String url1=reportSummaryMVCRenderCommandURL+"&<portlet:namespace/>workflowInstanceId="+reportStatusLog.getWorkflowInstanceId()+"&<portlet:namespace/>reportUploadLogId="+reportStatusLog.getReportUploadLogId();
						
						%>
						<tr>
	
							<td class="tbdata"><a href="<%=reportStatusLog.getFileUrl()%>"><%=reportStatusLog.getReportName() %></a></td>
							<td class="tbdata"><%=Validator.isNotNull(NameMappingConstants.CRA_NAME_MAP.get(reportStatusLog.getIntermediaryname()))?NameMappingConstants.CRA_NAME_MAP.get(reportStatusLog.getIntermediaryname()):reportStatusLog.getIntermediaryname() %></td>
							<td class="tbdata"><%=dateFormat.format(reportStatusLog.getReportDate()) %></td>
							<td class="tbdata"><%=dateFormat.format(reportStatusLog.getCreateDate()) %></td>
							
							<%-- <td class="tbdata"><%=reportStatusLog.getAssignedTo() %></td>
							<td class="tbdata"><%=(reportStatusLog.getStatus_() != null && !reportStatusLog.getStatus_().isEmpty() ? reportStatusLog.getStatus_().toUpperCase() : WorkflowConstants.getStatusLabel(reportStatusLog.getStatus()).toUpperCase()) %></td>
							 --%>
							<td class="tbdata"><%=reportStatusLog.getRemarks() %></td>
							<td class="tbdata"><a href='<%=reportSummaryMVCRenderCommandURL+"&"%><portlet:namespace/><%="workflowInstanceId="+reportStatusLog.getWorkflowInstanceId()+"&"%><portlet:namespace/><%="reportUploadLogId="+reportStatusLog.getReportUploadLogId()%>'>View</a></td>
						</tr>
						<%}} %>
		</tbody>
					</table>
                </div>
			</div>
		</div>
	</div>
</div>

<!-- jakeer changes  -->
<div class="animaion" style="display:none;">
 	<div class="row">
		<div class="loading-animation"></div>
	</div>
</div>

<script>

let department = '<%= department %>';
$(document).ready(function(){
	
	var table=$('#report_status_table').DataTable({
		searching : true,
		pagination : "bootstrap",
		filter : true,
		destroy : true,
		lengthMenu : [ 5, 10, 25],
		pageLength : 10,
		order: [[3, 'desc']],
		language: {
	            sLengthMenu: " _MENU_",
	            "info": "Showing  _START_  to  _END_  of  _TOTAL_  entries",
	            oPaginate: {
	                sNext: 'NEXT',
	                sPrevious: 'PREV'
	            }
	     },
		"dom": '<"top"f>rt<"bottom"<"left"li>p><"clear">'
    });

	//createReportsTable();
});

</script>