

<%@page import="java.util.Set"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
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
List<ErmModal> ermModals= ErmUtil.getApprovedErm(user.getCompanyId(), user.getUserId());
//boolean isNpstUser=NpstCommonUtil.isNpstUser(user.getCompanyId(), user.getUserId());
Set<String> duplicatePran=ErmUtil.getDuplicatePran();
//List<ErmInformation> ermModals= ErmInformationLocalServiceUtil.getErmInformations(-1, -1);
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
                    	Approved ERM
                    </a>
                </li>
            </ul>
<div class="dataTable-container">

<table id="approvedReportTable" class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th>Sr.No</th>
				<th><%=ErmFieldLabel.TABLE_VIEW_CRA_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_PRAN%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SUBSCRIBER_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TRANSACTION_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TRANSACTED_AMOUNT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_AMOUNT%></th>
				
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_TYPE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_DOCUMENT_SUBMISSIONDATE%></th>
				<%if(isNpstUser){ %>
				<th><%=ErmFieldLabel.TABLE_VIEW_FORWARD_TO_NPST_DATE%></th>
				<%} %>
				<th><%=ErmFieldLabel.TABLE_VIEW_REMARK%></th>
				<%if(isNpstUser){ %>
				<th><%=ErmFieldLabel.TABLE_VIEW_BANK_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_BANK_STATEMENT_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TXN_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SELF_RECTIFIED_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SUBSCRIBER_SECTOR%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_STIPULATED_TIME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SUBMISSON_STIPULATED_TIME%></th>
				<%} %>
				<th><%=ErmFieldLabel.TABLE_VIEW_NPS_REMARK%></th>
				<%if(isNpstUser){ %>
				<th><%=ErmFieldLabel.TABLE_VIEW_NPS_DECISION_DATE%></th>
				<%} %>
				<%if(!isNpstUser){ %>
				<th><%=ErmFieldLabel.TABLE_VIEW_DECISION%></th>
				<%}else{ %>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECOMMENDATION%></th>
				<%} %>
				<%if(isNpstUser){ %>
				<th><%=ErmFieldLabel.TABLE_VIEW_DUPLICATE_PRAN%></th>
				<%} %>
				<%if(!isNpstUser){ %>
				<th><%=ErmFieldLabel.TABLE_VIEW_CREATE_DATE%></th>
				<%} %>
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
							<portlet:renderURL var="viewErmURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/view-erm-information.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value='<%=ermInformation.getErmInformationId()+"" %>' />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
					<tr>
				
        				<%-- <%String workflowUrl=ErmUtil.getWorkflowUrl(request, themeDisplay,ermModal.getWorkflowTaskId(),LiferayWindowState.NORMAL); %> --%>
        
        				<td><%=count++ %></td> 
						<td class="tbdata"><%=ermModal.getIntermediaryName() %></td>
						<%-- <td class="tbdata"><%=ermInformation.getPran() %></td> --%>
						<td class="tbdata"><a target="_blank" href="<%=viewErmURL%>"><%=ermInformation.getPran() %></a></td>
						
						<td class="tbdata"><%=ermInformation.getSubscriberName() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getTransactionDate()) %></td>
						<td class="tbdata"><%=ermInformation.getTransactedAmount() %></td>
						<td class="tbdata"><%=ermInformation.getRectificationAmount() %></td>
						<td class="tbdata"><%=ermInformation.getRectificationType() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getDocumentationsDate()) %></td>
						<%if(isNpstUser){ %>
							<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getFwdnpstDate()) %></td>
							<%} %>
						<td class="tbdata"><%=ermInformation.getNoNpstRemark() %></td>
						<%if(isNpstUser){ %>
						<td class="tbdata"><%=ermInformation.getBankName() %></td>
						<td class="tbdata"><%=ermInformation.getBankStatmentName() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getTxnDate()) %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getSelfRectifiedDate()) %></td>
						<td class="tbdata"><%=ermInformation.getSubscriberSector() %></td>
						<td class="tbdata"><%=ermInformation.getSubmissionStipulatedTime() %></td>
						<td class="tbdata"><%=ermInformation.getStipulated() %></td>
						
						<%} %>
						<td class="tbdata"><%=ermInformation.getNpstRemark() %></td>
						<%if(isNpstUser){ %>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getNpsDecisionDate()) %></td>
						<%} %>
						<td class="tbdata"><%=ermInformation.getRecommendation() %></td>
						<%if(isNpstUser){ %>
						<td><%=duplicatePran.contains(ermInformation.getPran())?"Yes":"No" %></td>
						<%} %>
						<%if(!isNpstUser){ %>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getCreateDate()) %></td>
						<%} %>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>

<script>
$(document).ready(function() {
	 
	 $('#approvedReportTable').DataTable({
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
   