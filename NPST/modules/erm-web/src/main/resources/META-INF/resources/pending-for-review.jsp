
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
List<ErmModal> ermModals= ErmUtil.getAssignableErm(user.getCompanyId(), user.getUserId());
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
                    	Pending for Review
                    </a>
                </li>
            </ul>
<div class="dataTable-container">
<table id="pendingForReviewTable" class="dataTable-table table table-bordered table-responsive">
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
				<th><%=ErmFieldLabel.TABLE_VIEW_COMMENT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_NPS_REMARK%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_STATUS%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_CREATE_DATE%></th>
				<th>Action</th>
				

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
							
							<portlet:renderURL var="viewErmURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/view-erm-information.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value='<%=ermInformation.getErmInformationId()+"" %>' />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
							
							<portlet:renderURL var="ermPriorViewURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/prior-view.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value='<%=ermInformation.getErmInformationId()+"" %>' />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
							
							
					<tr>
				
        				<%-- <%String workflowUrl=ErmUtil.getWorkflowUrl(request, themeDisplay,ermModal.getWorkflowTaskId(),LiferayWindowState.NORMAL); %> --%>
        				<%-- <td>
        				<input class="batch" name="batch<%=count %>" label="" type="checkbox" value="<%=ermInformation.getErmInformationId() %>"></input></td> --%>
					<%-- 	<td><%=count++ %></td> --%>
						<td class="tbdata"><%=ermModal.getIntermediaryName() %></td>
						<%-- <td class="tbdata"><a href="<%= ermModal.isAssignable()?editErmURL:'#'%>"><%=ermInformation.getPran() %></a></td> --%>
						<%if(ermInformation.getErmStatus().equalsIgnoreCase("Rejected") || ermInformation.getIsForwardToNpst()!=1){ %>
						<td class="tbdata"><a target="_blank" href="<%= editErmURL%>"><%=ermInformation.getPran() %></a></td>
						<%}else{ %>
						<td class="tbdata"><a target="_blank" href="<%= viewErmURL%>"><%=ermInformation.getPran() %></a></td>
						<%} %>
						<td class="tbdata"><%=ermInformation.getSubscriberName() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getTransactionDate()) %></td>
						<td class="tbdata"><%=ermInformation.getTransactedAmount() %></td>
						<td class="tbdata"><%=ermInformation.getRectificationAmount() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getDocumentationsDate()) %></td>
						<td class="tbdata"><%=ermInformation.getRectificationType() %></td>
						<%-- <td class="tbdata"><a href="<%= workflowUrl%>">Review</a></td> --%>
						<td><input class="comment" id="comment<%=ermInformation.getErmInformationId() %>" name="comment<%=ermInformation.getErmInformationId() %>" label="" value="<%=ermInformation.getNoNpstRemark() %>" type="text"></input>
						<%if(!isNpstUser&& ermInformation.getBatchStatus()==NpstErmConstant.NPST_FIRST_LEVEL_APPROVED){ %>
						<select name="process<%=ermInformation.getErmInformationId()%>" id="process<%=ermInformation.getErmInformationId()%>">
						 <%for(Entry<String,String> entry:ErmConstantValues.PROCESS_MAP.entrySet()){%>
                        <option value="<%=entry.getKey() %>"><%=entry.getValue() %></option>
                        <%} %>
							</select>
						<%} else{%>
						<input type="hidden" id="process<%=ermInformation.getErmInformationId()%>" value=""></input>
						<%} %>
						
						</td>
						<td class="tbdata"><%=ermInformation.getNpstRemark() %></td>
						<td class="tbdata"><%=ermInformation.getErmStatus() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getCreateDate()) %></td>
						<td class="tbdata">
						<liferay-ui:icon-menu  markupView="lexicon" direction="left-side"   showWhenSingleIcon="<%= false %>">
						<%for(String transitionName:ermModal.getTransitionNames()){
							//String url=performWorkflowURL+"&<portlet:namespace/>action="+transitionName;	
						%>
						<%-- <portlet:actionURL name="performErmWorkflowTask" var="performWorkflowURL" >
        					 <portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value='<%=ermInformation.getErmInformationId()+"" %>' />
        					  <portlet:param name="action" value='<%=transitionName+"" %>' />
        					  <portlet:param name="jspName" value='/pending-for-review.jsp' />
        				 </portlet:actionURL> --%>
        				 
        			<%-- 	 <portlet:resourceURL var="performWorkflowURL" id="performErmWorkflowTask">
        				 	<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value='<%=ermInformation.getErmInformationId()+"" %>' />
        					  <portlet:param name="action" value='<%=transitionName+"" %>' />
        					  <portlet:param name="jspName" value='/pending-for-review.jsp' />
        				 </portlet:resourceURL> --%>
        				 <button class="btn btn-link" style="color: #6b6c7e;" onClick='performWorkflowAction("<%=transitionName %>","<%=ermInformation.getErmInformationId()%>")'><liferay-ui:icon image="" message="<%=transitionName %>" /></button><br>
						<%-- <button onClick='performWorkflowAction("<%=performWorkflowURL %>","<%=ermInformation.getErmInformationId()%>")'><%=transitionName %></button> --%>
						  <%--  <a href="<%=performWorkflowURL%>&<portlet:namespace/>action=<%=transitionName%>">Approve</a> --%>
						  <%} %>
						  </liferay-ui:icon-menu>
						  </td>
						
						<%-- <td class="tbdata"><a href="<%= workflowUrl%>">Review</a></td> --%>
					</tr>
					
					<%} %>
		</tbody>
	</table>
</div>


 <div id="modalOne" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopup()" style="float: right;">&times;</a><br>
           <h3 id="popupmsg" class="text-center">do you want to remove this record?</h3>
             <br>
            <div class="text-center">
            <button class="text-center btn btn-primary m-3" type="submit" onClick="performConfirmAction()">Yes</button>

            <button class="text-center btn btn-danger m-3" type="submit" onClick="closePopup()">No</button>

            
        </div>
        </div>
      </div>
      
      </div>
    </div>
    
    
<script>
function closePopup(){
	   document.getElementById("modalOne").style.display = "none";
}
var commentId="";
var processId="";
var comment="";
var process="";
var ermId=0;
var transName="";
function performConfirmAction(){
	
	console.log("ermId:  "+ermId+ " proc: "+process);
	<%-- console.log(url+"&<portlet:namespace/>"+<%=ErmFieldName.COMMENT%>+"="+comment);
	window.location.href=url+"&<portlet:namespace/>"+<%=ErmFieldName.COMMENT%>+"="+comment; --%>
	ajaxCall(ermId,comment,transName,process);
}


function performWorkflowAction(actionName,id){
	 commentId="#comment"+id;
	 processId="#process"+id;
	 comment=$(commentId).val();
	 process=$(processId).val();
	 ermId=id;
	 transName=actionName;
	 $("#popupmsg").text("Do You want to  "+actionName+" this record? ");
	 

	document.getElementById("modalOne").style.display = "block";
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
   