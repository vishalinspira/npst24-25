
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.erm.constants.NpstErmConstant"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.nps.erm.constants.ErmConstantValues"%>
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
List<ErmModal> ermModals= ErmUtil.getErmByUserId(user.getCompanyId(), user.getUserId());
%>
<%-- String backUrl=ParamUtil.getString(request, ErmFieldName.BACK_URL);
if(backUrl!=null && backUrl!="" && !backUrl.equalsIgnoreCase(null)){
%>
<a href="<%=backUrl%>">Back</a>
<%} %> --%>
<br><div style="width: 10%; float: right; margin-top: -3rem;">
<a href="<%=backURL%>" class="btn btn-primary" style="border-radius: 50px;"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a>
</div>

	 <portlet:resourceURL var="performWorkflowURL" id="performErmWorkflowTask"/>

<ul class="nav nav-tabs border-0 mb-3 nps-report-main" style="margin-top: -3rem;">
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link active p-0 tab_active_btn"> 
                    	My Submissons
                    </a>
                </li>
            </ul>

<div class="dataTable-container">
<table id="pendingForReviewTable" class="dataTable-table table table-bordered table-responsive" style="width: 100%">
		<thead>
			<tr class="border">
				
				<th><%=ErmFieldLabel.TABLE_VIEW_CRA_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_PRAN%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SUBSCRIBER_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TRANSACTION_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TRANSACTED_AMOUNT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_AMOUNT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_REQUEST_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_TYPE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_REMARK%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_NPS_REMARK%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_STATUS%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_CREATE_DATE%></th>
				
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
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value='<%=ermInformation.getErmInformationId()+"" %>' />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
							
							<portlet:renderURL var="ermPriorViewURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/prior-view.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value='<%=ermInformation.getErmInformationId()+"" %>' />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
							
								<portlet:renderURL var="viewErmURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/view-erm-information.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value='<%=ermInformation.getErmInformationId()+"" %>' />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
							
							
					<tr>
				
        				<%-- <%String workflowUrl=ErmUtil.getWorkflowUrl(request, themeDisplay,ermModal.getWorkflowTaskId(),LiferayWindowState.NORMAL); %> --%>
        				<%-- <td>
        				<input class="batch" name="batch<%=count %>" label="" type="checkbox" value="<%=ermInformation.getErmInformationId() %>"></input></td> --%>
					<%-- 	<td><%=count++ %></td> --%>
						<td class="tbdata"><%=ermModal.getIntermediaryName() %></td>
						<%-- <td class="tbdata"><a href="<%= ermModal.isAssignable()?editErmURL:viewErmURL%>"><%=ermInformation.getPran() %></a></td> --%>
						<td class="tbdata"><a target="_blank" href="<%=viewErmURL%>"><%=ermInformation.getPran() %></a></td>
						<td class="tbdata"><%=ermInformation.getSubscriberName() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getTransactionDate()) %></td>
						<td class="tbdata"><%=ermInformation.getTransactedAmount() %></td>
						<td class="tbdata"><%=ermInformation.getRectificationAmount() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getRectificationRequestDate()) %></td>
						<td class="tbdata"><%=ermInformation.getRectificationType() %></td>
						<td class="tbdata"><%=ermInformation.getNoNpstRemark() %></td>
						<td class="tbdata"><%=ermInformation.getNpstRemark() %></td>
						<td class="tbdata"><%=ermInformation.getErmStatus() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getCreateDate()) %></td>
					</tr>
					
					<%} %>
		</tbody>
	</table>
</div>

<script>

function performWorkflowAction(transName,ermId){
	var commentId="#comment"+ermId;
	var processId="#process"+ermId;
	var comment=$(commentId).val();
	var process=$(processId).val();
	console.log("ermId:  "+ermId+ " proc: "+process);
	ajaxCall(ermId,comment,transName,process);
	}

</script>

<script type="text/javascript">
function ajaxCall(ermId,comment,transName,process){

    AUI().use('aui-io-request', function(A){
        A.io.request("<%=performWorkflowURL%>", {
               method: 'post',
               data: {
                   <portlet:namespace /><%=ErmFieldName.COMMENT%>: comment,
                   <portlet:namespace /><%=ErmFieldName.ERM_INFORMATION_ID%>: ermId,
                   <portlet:namespace />action: transName,
                   <portlet:namespace />process: process,
               },
               on: {
                       success: function() {
                       /*  alert(this.get('responseData')); */
                       setTimeout(function () {
  							console.log('Hello world');
  							location.reload();
							}, 300);
                        /* location.reload(); */
                    }
              }
        });
    });
}
</script>

<script>

$(document).ready(function() {
  	 
  	 $('#pendingForReviewTable').DataTable({
  			searching : true,
  			"ordering": false,
  			info: false,
  			pagination : "bootstrap",
  			filter : true,
  			destroy : true,
  			lengthMenu : [ 5, 10, 25 ],
  			pageLength : 10,
  			"dom": '<"top"f>rt<"bottom"lp><"clear">',					
  		});       
  } ); 

</script>
   