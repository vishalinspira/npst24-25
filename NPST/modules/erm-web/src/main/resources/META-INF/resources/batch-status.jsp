
<%@page import="nps.common.service.constants.NameMappingConstants"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.erm.model.ErmBatchInformation"%>
<%@page import="com.nps.erm.modal.ErmBatchModal"%>
<%@page import="com.nps.erm.constants.ErmFieldName"%>
<%@page import="com.nps.erm.util.ErmUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.nps.erm.service.ErmInformationLocalServiceUtil"%>
<%@page import="com.nps.erm.model.ErmInformation"%>
<%@page import="com.nps.erm.constants.ErmFieldLabel"%>
<%@page import="java.util.List"%>
<%@page import="com.nps.erm.modal.ErmModal"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/init.jsp" %>
<%

List<ErmBatchModal> ermBatchModals= ErmUtil.getAllErmBatchModal(user.getCompanyId(), user.getUserId());
//List<ErmInformation> ermBatchModal= ErmInformationLocalServiceUtil.getErmInformations(-1, -1);
%>

<%-- String backUrl=ParamUtil.getString(request, ErmFieldName.BACK_URL);
if(backUrl!=null && backUrl!="" && !backUrl.equalsIgnoreCase(null)){
%>
<a href="<%=backUrl%>">Back</a>
<%} %> --%>

<br><div style="width: 10%; float: right; margin-top: -3rem;">
<a href="<%=backURL%>" class="btn btn-primary" style="border-radius: 50px;"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a>
</div>

<ul class="nav nav-tabs border-0 mb-3 nps-report-main" style="margin-top: -3rem;">
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link active p-0 tab_active_btn"> 
                    	Batch Status
                    </a>
                </li>
            </ul>
<div class="dataTable-container">

<table id="batchStatusTable" class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th><%=ErmFieldLabel.TABLE_VIEW_ERM_BATCH_NUMBER %></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_CRA_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_BATCH_TIME_PERIOD%></th>
				<%-- <th><%=ErmFieldLabel.TABLE_VIEW_RECEIPT_CUTOFF_DATE%></th> --%>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECEIVED_CASES_NO%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_ASSIGNEE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_CREATE_DATE%></th>
				<th>Action</th>

			</tr>
		</thead>
		<tbody>
					<%
					int count=1;
					//for(ErmInformation ermInformation:ermBatchModal){ 
					for(ErmBatchModal ermBatchModal:ermBatchModals){ 
					ErmBatchInformation batchInformation=ermBatchModal.getErmBatchInformation(); 
						%>
					<%-- 	<portlet:renderURL var="editErmURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/add-erm-information.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value="<%=ermInformation.getErmInformationId()+"" %>" />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL> --%>
					<tr>
				
        				<%//String workflowUrl=ErmUtil.getWorkflowUrl(request, themeDisplay,ermModal.getWorkflowTaskId(),LiferayWindowState.NORMAL);
        				
        				%>
        				<portlet:renderURL var="viewErmBatchRecordsURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
								<portlet:param name="mvcPath" value="/view-batch-records.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_BATCH_NUMBER_ID %>" value="<%=batchInformation.getErmBatchInformationId()+"" %>" />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL> 
        				 <portlet:actionURL name="performWorkflowTask" var="performWorkflowURL" >
        					 <portlet:param name="batchId" value="<%=batchInformation.getErmBatchInformationId()+"" %>" />
        				 </portlet:actionURL>
        				  <portlet:actionURL name="deleteBatch" var="deleteBatchURL" >
        					 <portlet:param name="batchId" value="<%=batchInformation.getErmBatchInformationId()+"" %>" />
        				 </portlet:actionURL>
        				
						<td><%=batchInformation.getBatchNo() %></td>
						<td class="tbdata"><%=NameMappingConstants.CRA_NAME_MAP.get( batchInformation.getPfmName()) %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodFrom())+" To "+NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodTo())%></td>
						
						<%-- <td class="tbdata"><%=NpstCommonUtil.getDateString(batchInformation.getCutOffDate()) %></td> --%>
						<td class="tbdata">
						<button class="btn btn-link" onClick='viewBatchRecords("<%=viewErmBatchRecordsURL %>")'><%=ermBatchModal.getReceivedCaseNo() %></button>
						<%-- <a href="<%=viewErmBatchRecordsURL%>"><%=ermBatchModal.getReceivedCaseNo() %></a> --%>
						</td>
						<td><%=ermBatchModal.getAssignedTo() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(batchInformation.getCreateDate()) %></td>
						<td>
						
						<portlet:resourceURL var="exportURL" id="/erm/batch/download">
						<portlet:param name="<%=ErmFieldName.ERM_BATCH_INFORMATION_ID %>" value="<%=String.valueOf(batchInformation.getErmBatchInformationId()) %>" />
						</portlet:resourceURL>

						<a href="<%=exportURL%>">download1</a>
						<portlet:resourceURL var="exportURL2" id="/erm/batch/download2">
						<portlet:param name="<%=ErmFieldName.ERM_BATCH_INFORMATION_ID %>" value="<%=String.valueOf(batchInformation.getErmBatchInformationId()) %>" />
						</portlet:resourceURL>

						<a href="<%=exportURL2%>">download2</a>
						</td>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>
<script>

$(document).ready(function() {
	 
	 $('#batchStatusTable').DataTable({
			searching : true,
			info: false,
			pagination : "bootstrap",
			filter : true,
			destroy : true,
			lengthMenu : [ 5, 10, 25 ],
			pageLength : 10,
			"dom": '<"top"f>rt<"bottom"lp><"clear">',					
		});       
} ); 

function viewBatchRecords(url){
	  Liferay.Util.openWindow({
	    dialog: {
	        centered: true,
	        height: 500,
	        modal: true,
	        width: 700,
	        cssClass: "ui-model",
	        destroyOnHide: true,
           resizable: true,
	    },
	    id: "<portlet:namespace />popUpId",
	    title: "Batch Records",
	    uri: url
	});  
}

</script>

   