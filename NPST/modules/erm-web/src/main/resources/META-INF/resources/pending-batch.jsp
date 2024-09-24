
<%@page import="nps.common.service.constants.NameMappingConstants"%>
<%@page import="com.nps.erm.constants.NpstErmConstant"%>
<%@page import="com.liferay.petra.string.StringPool"%>
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

<style>	
.table {	
    position: relative;	
    display: table;	
    margin: 10px auto;	
    padding: 0;	
    width: 100%;	
    height: auto;	
    border-collapse: collapse;	
    text-align: center;	
}	
.table tr {	
  margin: 0;	
  padding: 0;	
  width: 100%;	
  }	
 .table th.column1{	
 width: 6%;	
}	
 .table th.tbdata{	
  width: 22%;	
}	
</style>
<%
//List<ErmBatchModal> ermBatchModals= ErmUtil.getAllErmBatchModal(user.getCompanyId(), user.getUserId());
List<ErmBatchModal> ermBatchModals= ErmUtil.getMyAndMyRolesBatch(user.getCompanyId(), user.getUserId());
//List<ErmInformation> ermBatchModal= ErmInformationLocalServiceUtil.getErmInformations(-1, -1);
%>
<%-- String backUrl=ParamUtil.getString(request, ErmFieldName.BACK_URL);
if(backUrl!=null && backUrl!="" && !backUrl.equalsIgnoreCase(null)){
%>
<a href="<%=backUrl%>">Back</a>
<%} %> --%>


<portlet:renderURL var="crapendingBatchURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
			<portlet:param name="mvcPath" value="/cra-pending-batch.jsp" />
			<portlet:param name="<%=ErmFieldName.BACK_URL %>" value="<%=themeDisplay.getURLCurrent() %>" />
	</portlet:renderURL>


<br><div style="width: 10%; float: right; margin-top: -2rem;">
<a href="<%=backURL%>" class="btn btn-primary" style="border-radius: 50px;"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a>
</div>
<div style="width: 17%; float: right; margin-top: -2rem;">
<a href="<%=crapendingBatchURL%>" class="btn btn-primary" style="border-radius: 50px;">CRA Pending Batch</a>
</div>

<ul class="nav nav-tabs border-0 mb-3 nps-report-main" style="margin-top: -3rem;">
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link active p-0 tab_active_btn"> 
                    	Pending Batch
                    </a>
                </li>
            </ul>
<div class="dataTable-container">

<table id="pendingBatchTable" class="dataTable-table table table-bordered table-responsive" style="width:100%">
		<thead>
			<tr>
				<th class="column1"><%=ErmFieldLabel.TABLE_VIEW_ERM_BATCH_NUMBER %></th>
				<th class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_CRA_NAME%></th>
				<th class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_BATCH_TIME_PERIOD%></th>
				<%-- <th class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECEIPT_CUTOFF_DATE%></th> --%>
				<th class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECEIVED_CASES_NO%></th>
				<th class="tbdata">Status</th>
				<th class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_CREATE_DATE%></th>
				<th class="column1">Action</th>
				<th class="column1">Download</th>

			</tr>
		</thead>
		<tbody>
					<%
					int count=1;
					//for(ErmInformation ermInformation:ermBatchModal){ 
					for(ErmBatchModal ermBatchModal:ermBatchModals){ 
					ErmBatchInformation batchInformation=ermBatchModal.getErmBatchInformation(); 
					ErmInformation ermInformation=ErmInformationLocalServiceUtil.getErmInformation(Long.parseLong(batchInformation.getErmInformationIds().split(StringPool.COMMA)[0]));
						%>
					 	
					<tr>
				
        				<%//String workflowUrl=ErmUtil.getWorkflowUrl(request, themeDisplay,ermModal.getWorkflowTaskId(),LiferayWindowState.NORMAL);
        				
        				%>
        				<portlet:renderURL var="viewErmBatchRecordsURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
								<portlet:param name="mvcPath" value="/view-batch-records.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_BATCH_NUMBER_ID %>" value="<%=batchInformation.getErmBatchInformationId()+"" %>" />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
								<portlet:param name="isCancelable" value="true" />
							</portlet:renderURL> 
        				 <portlet:actionURL name="performWorkflowTask" var="performWorkflowURL" >
        					 <portlet:param name="<%=ErmFieldName.ERM_BATCH_NUMBER_ID %>" value="<%=batchInformation.getErmBatchInformationId()+"" %>" />
        				 </portlet:actionURL>
        				  <portlet:actionURL name="deleteBatch" var="deleteBatchURL" >
        					 <portlet:param name="<%=ErmFieldName.ERM_BATCH_NUMBER_ID %>" value="<%=batchInformation.getErmBatchInformationId()+"" %>" />
        					 
        				 </portlet:actionURL>
        				
						<td><%=batchInformation.getBatchNo() %></td>
						<td class="tbdata"><%=NameMappingConstants.CRA_NAME_MAP.get(batchInformation.getPfmName()) %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodFrom())+" To "+NpstCommonUtil.getDateString(batchInformation.getBatchTimePeriodTo())%></td>
						
						<%-- <td class="tbdata"><%=NpstCommonUtil.getDateString(batchInformation.getCutOffDate()) %></td> --%>
						<td class="tbdata">
						<button class="btn btn-link" onClick='viewBatchRecords("<%=viewErmBatchRecordsURL %>")'><%=ermBatchModal.getReceivedCaseNo() %></button>
						<%-- <a href="<%=viewErmBatchRecordsURL%>"><%=ermBatchModal.getReceivedCaseNo() %></a> --%>
						</td>
						<td class="tbdata"><%=ermBatchModal.getBatchStatus() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(batchInformation.getCreateDate()) %></td>
						<td>
						<liferay-ui:icon-menu  markupView="lexicon" direction="left-side"   showWhenSingleIcon="<%= false %>">
						<%for(String transitionName:ermBatchModal.getTransitionNames()){ %>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='performWorkflowAction("<%=performWorkflowURL%>&<portlet:namespace/>action=<%=transitionName%>","<%=transitionName%>")' ><liferay-ui:icon image="" message="<%=transitionName %>" /></button><br>
						  <%--  <a href="<%=performWorkflowURL%>&<portlet:namespace/>action=<%=transitionName%>">Approve</a> --%>
						
						 
						  <%}if(ermInformation.getBatchStatus()!=NpstErmConstant.NPST_FIRST_LEVEL_APPROVED){ %>
						  <button class="btn btn-link" style="color: #6b6c7e;" onClick='removeBatchRecord("<%=deleteBatchURL %>")' ><liferay-ui:icon image="" message="Delete" /></button><br>
						   <%} %>
						    <button class="btn btn-link" style="color: #6b6c7e;" onClick='viewBatchRecords("<%=viewErmBatchRecordsURL %>")' ><liferay-ui:icon image="" message="Edit" /></button><br>
						   </liferay-ui:icon-menu>
						  
						</td>
						
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


 <div id="modalOne" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopup()" style="float: right;">&times;</a><br>
           <h3 id="popupmsg" class="text-center"></h3>
             <br>
            <div class="text-center">
            <button class="text-center btn btn-primary m-3" type="submit" onClick="performConfirmAction()">Yes</button>

            <button class="text-center btn btn-danger m-3" type="submit" onClick="closePopup()">No</button>

            
        </div>
        </div>
      </div>
      
      </div>
    </div>
    
    
     <div id="modalTwo" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopupTwo()" style="float: right;">&times;</a><br>
           <h3 id="popupmsg2" class="text-center"></h3>
             <br>
            <div class="text-center">
            <button class="text-center btn btn-primary m-3" type="submit" onClick="removeBatch()">Yes</button>

            <button class="text-center btn btn-danger m-3" type="submit" onClick="closePopupTwo()">No</button>

            
        </div>
        </div>
      </div>
      
      </div>
    </div>


<script>

var batchURL="";
var txName="";

function removeBatchRecord(url){
	batchURL=url;
	 $("#popupmsg2").text("Are you sure you want to delete? ");
	 
 	document.getElementById("modalTwo").style.display = "block";
}


function removeBatch(){
location.href=batchURL;
}


function performWorkflowAction(url,transName){
	batchURL=url;
	txName=transName;
	 $("#popupmsg").text("Do You want to  "+transName+" this Batch? ");
	 
 	document.getElementById("modalOne").style.display = "block";
}


function performConfirmAction(){
location.href=batchURL;
}

function closePopup(){
	   document.getElementById("modalOne").style.display = "none";
}
 
 function closePopupTwo(){
	   document.getElementById("modalTwo").style.display = "none";
}

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
           on : {
				close : function(event) {
					location.reload();
				},
				destroy : function(event) {
						location.reload();	
				}
		},
	    },
	    id: "<portlet:namespace />popUpId",
	    title: "Batch Records",
	    uri: url,
	    
	    
	});  
}

</script>

<script>

$(document).ready(function() {
  	 
  	 $('#pendingBatchTable').DataTable({
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
   