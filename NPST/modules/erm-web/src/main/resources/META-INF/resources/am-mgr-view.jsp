

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
 <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.7/css/jquery.dataTables.css">

<%
	//List<ErmModal> ermModals= ErmUtil.getAssignedRecords(user.getCompanyId(), user.getUserId());
//List<ErmModal> ermModals= ErmUtil.getAllRecords(user.getCompanyId(), user.getUserId());
List<ErmModal> ermModals= ErmUtil.getMyAndMyRolesRecords(user.getCompanyId(), user.getUserId());
//List<ErmInformation> ermModals= ErmInformationLocalServiceUtil.getErmInformations(-1, -1);
%>


<div class="container" id="directorDiv">
<div>

<table id="directorTable"
		class="display table table-striped display nowrap cell-border" style="width: 100%"
		cellspacing="10">
		<thead>
			<tr class="border">
				<th>Sr.No</th>
				<th><%=ErmFieldLabel.TABLE_VIEW_GRIEVANCE_TEXT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_PRAN%></th>

			</tr>
		</thead>
		<tbody>
					<%
					int count=1;
					//for(ErmInformation ermInformation:ermModals){ 
					for(ErmModal ermModal:ermModals){ 
					ErmInformation ermInformation=ermModal.getErmInformation(); 
						%>
						<portlet:renderURL var="editErmURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/add-erm-information.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value="<%=ermInformation.getErmInformationId()+"" %>" />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
					<tr>
				
        				<%String workflowUrl=ErmUtil.getWorkflowUrl(request, themeDisplay,ermModal.getWorkflowTaskId(),LiferayWindowState.NORMAL); %>
        				<td><aui:input class="batch" name="batch<%=count %>" label="1" type="checkbox" value="<%=ermModal.getWorkflowTaskId() %>"></aui:input></td>
						<td><%=count++ %></td>
						<td class="tbdata"><%=ermInformation.getCaseNo() %></td>
						<td class="tbdata"><a href="<%= ermModal.isSelfAsignee()?editErmURL:'#'%>"><%=ermInformation.getPran() %></a></td>
						<td class="tbdata"><%=ermModal.getAssignedTo() %></td>
						<td class="tbdata"><%=ermModal.getRemarks() %></td>
						<td class="tbdata"><%=ermModal.getIntermediaryName() %></td>
						<td class="tbdata"><a href="<%= workflowUrl%>">Review</a></td>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>
</div>


<!-- <script>

$(document).ready(function() {
    $('#directorTable').DataTable({
    	"dom": 'lrtip',
    	"bLengthChange" : false,
	     "scrollX": true
  });
} ); 


</script>
    -->
   
   