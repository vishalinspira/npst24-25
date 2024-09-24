

<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
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
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.css" type="text/css"  rel="stylesheet">
<%
String pran=ParamUtil.getString(request, ErmFieldName.PRAN);
if(pran=="" || pran==null || pran.equals(null)){
	pran=(String)request.getAttribute(ErmFieldName.PRAN);
}

List<ErmModal> ermModals= ErmUtil.getErmModalByPran(user.getCompanyId(),themeDisplay.getScopeGroupId(), user.getUserId(), pran);
//List<ErmInformation> ermModals= ErmInformationLocalServiceUtil.getErmInformations(-1, -1);
%>
<portlet:resourceURL var="performWorkflowURL" id="performErmWorkflowTask"/>

<div  class="dataTable-container">

<table id="viewPranRecordTable" class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th><%=ErmFieldLabel.TABLE_VIEW_CRA_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_PRAN%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SUBSCRIBER_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TRANSACTION_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TRANSACTED_AMOUNT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_AMOUNT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_DOCUMENT_SUBMISSIONDATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_TYPE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_REMARK%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_NPS_REMARK%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECOMMENDATION%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_STATUS%></th>
				<%-- <th><%=ErmFieldLabel.TABLE_VIEW_PROCESS%></th> --%>
				<th><%=ErmFieldLabel.TABLE_VIEW_CREATE_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_ASSIGNEE%></th>
			</tr>
		</thead>
		<tbody>
					<%
					int count=1;
					//for(ErmInformation ermInformation:ermModals){ 
					for(ErmModal ermModal:ermModals){  
						ErmInformation ermInformation=ermModal.getErmInformation();
						/* String workflowUrl=ErmUtil.getWorkflowUrl(request, themeDisplay,ermModal.getWorkflowTaskId(),LiferayWindowState.POP_UP); */
						%>
					<tr>
					 <portlet:actionURL name="viewPranRecords" var="viewPranRecordsURL" >
					  		<portlet:param name="<%=ErmFieldName.PRAN %>" value="<%=ermInformation.getPran() %>" />
        			 </portlet:actionURL>
        				
						<td class="tbdata"><%=ermModal.getIntermediaryName() %></td>
						<td class="tbdata"><%=ermInformation.getPran() %></td>
						<td class="tbdata"><%=ermInformation.getSubscriberName() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getTransactionDate()) %></td>
						<td class="tbdata"><%=ermInformation.getTransactedAmount() %></td>
						<td class="tbdata"><%=ermInformation.getRectificationAmount() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getDocumentationsDate()) %></td>
						<td class="tbdata"><%=ermInformation.getRectificationType() %></td>
						
						<%-- <td>
						<liferay-ui:icon-menu  markupView="lexicon" direction="left-side"   showWhenSingleIcon="<%= false %>">
						<%if(ermModal.isAssignable()){
							for(String transitionName:ermModal.getTransitionNames()){%>							
							 <button class="btn btn-link" style="color: #6b6c7e;" onClick='performWorkflowAction("<%=transitionName %>","<%=ermInformation.getErmInformationId()%>")'><liferay-ui:icon image="" message="<%=transitionName %>" /></button><br>
							 <%}} %>
							 </liferay-ui:icon-menu>
							</td> --%>
						<%-- <td class="tbdata"><%if(ermModal.isAssignable()){%>
							<input class="comment" id="comment<%=ermInformation.getErmInformationId() %>" name="comment<%=ermInformation.getErmInformationId() %>" value="<%=ermInformation.getNpstRemark() %>" label="" type="text"></input>
							<%}else{ %>
							<%=ermInformation.getNpstRemark() %>
							<%} %>
							</td> --%>
						<td class="tbdata"><%=ermInformation.getNoNpstRemark() %></td>
						<td class="tbdata"><%=ermInformation.getNpstRemark() %></td>
						<td class="tbdata"><%=ermInformation.getRecommendation() %></td>
						<td class="tbdata"><%=ermInformation.getErmStatus() %></td>
						<%-- <td class="tbdata"><%=ermInformation.getProcess() %></td> --%>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getCreateDate()) %></td>
						<td class="tbdata"><%=ermModal.getAssignedTo() %></td>
<%-- 						<td class="tbdata"><%=ermInformation.getRecommendation() %></td>
						<td class="tbdata"><%=ermInformation.getStipulated() %></td> --%>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>
    
    <script>
    
    $(document).ready(function() {
   	 
   	 $('#viewPranRecordTable').DataTable({
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

function performWorkflowAction(transName,ermId){
	var commentId="#comment"+ermId;
	var comment=$(commentId).val();
	console.log("ermId:  "+ermId);
	<%-- console.log(url+"&<portlet:namespace/>"+<%=ErmFieldName.COMMENT%>+"="+comment);
	window.location.href=url+"&<portlet:namespace/>"+<%=ErmFieldName.COMMENT%>+"="+comment; --%>
	ajaxCall(ermId,comment,transName,process);
	}

</script>
<script type="text/javascript">
function ajaxCall(ermId,comment,transName){

    AUI().use('aui-io-request', function(A){
        A.io.request("<%=performWorkflowURL%>", {
               method: 'post',
               data: {
                   <portlet:namespace /><%=ErmFieldName.COMMENT%>: comment,
                   <portlet:namespace /><%=ErmFieldName.ERM_INFORMATION_ID%>: ermId,
                   <portlet:namespace />action: transName,
               },
               on: {
                       success: function() {
                       /*  alert(this.get('responseData')); */
                    	   setTimeout(function () {
     							console.log('Hello world');
     							location.reload();
   							}, 300);
                    }
              }
        });
    });
}
</script>
    