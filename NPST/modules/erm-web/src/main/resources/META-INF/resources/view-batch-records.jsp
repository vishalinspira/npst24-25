

<%@page import="java.util.Map.Entry"%>
<%@page import="com.nps.erm.constants.ErmConstantValues"%>
<%@page import="com.nps.erm.constants.NpstErmConstant"%>
<%@page import="java.util.ArrayList"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="java.util.Set"%>
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
String batchNo=ParamUtil.getString(request, ErmFieldName.ERM_BATCH_NUMBER_ID);
boolean isCancelable=ParamUtil.getBoolean(request, "isCancelable");
if(batchNo=="" || batchNo.equalsIgnoreCase("") || batchNo==null || batchNo.equals(null)){
	batchNo=(String)request.getAttribute(ErmFieldName.ERM_BATCH_NUMBER_ID);
	isCancelable=Boolean.valueOf((String)request.getAttribute("isCancelable"));
}

System.out.println("batchNo : "+batchNo+ "isCancelable  "+isCancelable);
List<ErmModal> ermModals=new ArrayList();
try{
ermModals= ErmUtil.getErmModalBYBatchNo(batchNo,user.getCompanyId(), user.getUserId());
}catch(Exception e){}
Set<String> duplicatePran=ErmUtil.getDuplicatePran();
//List<ErmInformation> ermModals= ErmInformationLocalServiceUtil.getErmInformations(-1, -1);
%>
<portlet:resourceURL var="performWorkflowURL" id="performErmWorkflowTask"/>

<div class="dataTable-container">
<table id="batchRecordTable" class="dataTable-table table table-bordered table-responsive">
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
				<th>CRA comments</th>
				<%if(isCancelable){ %>
				<th>Action</th>
				<%} %>
				
				<%-- <th><%=ErmFieldLabel.TABLE_VIEW_STIPULATED%></th> --%>

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
						<portlet:renderURL var="editErmURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/add-erm-information.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value="<%=ermInformation.getErmInformationId()+"" %>" />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
					<tr>
					 <portlet:actionURL name="updateBatch" var="updateBatchURL" >
					  		<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value="<%=ermInformation.getErmInformationId()+"" %>" />
					  		 <portlet:param name="<%=ErmFieldName.ERM_BATCH_NUMBER_ID %>" value="<%=batchNo %>" />
        					 <portlet:param name="isCancelable" value="<%=String.valueOf(isCancelable) %>" />
        				 </portlet:actionURL>
        				 
        				  <portlet:renderURL  var="viewPranURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
					  		<portlet:param name="<%=ErmFieldName.PRAN %>" value="<%=ermInformation.getPran() %>" />
        				 </portlet:renderURL>
        				
						<td class="tbdata"><%=ermModal.getIntermediaryName() %></td>
						<td class="tbdata">
						<%-- <%if(duplicatePran.contains(ermInformation.getPran())){ %>
						<a class="bg-warning" href="<%=viewPranURL%>"><%=ermInformation.getPran() %></a>
						<%}else{ %>
						<%=ermInformation.getPran() %>
						<%} %> --%>
						
						<%=ermInformation.getPran() %>
						</td>
						<td class="tbdata"><%=ermInformation.getSubscriberName() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getTransactionDate()) %></td>
						<td class="tbdata"><%=ermInformation.getTransactedAmount() %></td>
						<td class="tbdata"><%=ermInformation.getRectificationAmount() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getDocumentationsDate()) %></td>
						<td class="tbdata"><%=ermInformation.getRectificationType() %></td>
						<td class="tbdata"><%=ermInformation.getNoNpstRemark() %></td>
						<td class="tbdata"><%if(ermModal.isAssignable() && isCancelable && ermInformation.getBatchStatus()!=NpstErmConstant.NPST_FIRST_LEVEL_APPROVED){%>
							<input class="comment" id="comment<%=ermInformation.getErmInformationId() %>" name="comment<%=ermInformation.getErmInformationId() %>" value="<%=ermInformation.getNpstRemark() %>" label="" type="text"></input>
							<%}else{ %>
							<%=ermInformation.getNpstRemark() %>
							<%} %>
							</td>
						
						<%-- <td class="tbdata"><%=ermInformation.getRecommendation() %></td> --%>
						
						<td class="tbdata">
						<%if(ermInformation.getBatchStatus()==NpstErmConstant.NPST_FIRST_LEVEL_APPROVED){ %>
						<%= ermInformation.getRecommendation()%>
						<%}else{ %>
						<select id="recommendation<%=ermInformation.getErmInformationId() %>">
						 <%for(Entry<String,String> entry:ErmConstantValues.RECOMMENDATION_MAP.entrySet()){
						 boolean isSelected=false;
						 if(entry.getKey().equalsIgnoreCase(ermInformation.getRecommendation())){
							 isSelected=true; 
						 }
						 %>
                        <option value="<%=entry.getKey() %>" <%=isSelected?"selected":""%>><%=entry.getValue() %></option>
                        <%} %>
							</select>
						<%} %>
						
						</td>
						
						
						
						<td class="tbdata"><%=ermInformation.getErmStatus() %></td>
						<%-- <td class="tbdata"><%=ermInformation.getProcess() %></td> --%>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getCreateDate()) %></td>
						<td class="tbdata"><%=ermModal.getAssignedTo() %></td>
						<td class="tbdata"><%=ermInformation.getProcess() %></td>
						<%if(isCancelable){ %>
						<td class="tbdata">
						<liferay-ui:icon-menu  markupView="lexicon" direction="left-side"   showWhenSingleIcon="<%= false %>">
						<%if(ermModal.isAssignable()){ %>
						
						<%for(String transitionName:ermModal.getTransitionNames()){%>
							 <button class="btn btn-link" style="color: #6b6c7e;" onClick='performWorkflowAction("<%=transitionName %>","<%=ermInformation.getErmInformationId()%>")'><liferay-ui:icon image="" message="<%=transitionName %>" /></button><br>
							 <%-- <button onClick='performWorkflowAction("<%=transitionName %>","<%=ermInformation.getErmInformationId()%>")'><%=transitionName %></button> --%>
							 <%}if(ermInformation.getBatchStatus()!=NpstErmConstant.NPST_FIRST_LEVEL_APPROVED){%>
							 <button class="btn btn-link" style="color: #6b6c7e;" onClick='removeBatchRecord("<%=updateBatchURL %>")'><liferay-ui:icon image="" message="Remove" /></button>
						
						<%}} %>
							 </liferay-ui:icon-menu>
						</td>
						<%} %>

						
						<%-- <td class="tbdata"><%=ermInformation.getStipulated() %></td> --%>
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
            <button class="text-center btn btn-primary m-3" type="submit" onClick="removeErmRecord()">Yes</button>

            <button class="text-center btn btn-danger m-3" type="submit" onClick="closePopupTwo()">No</button>

            
        </div>
        </div>
      </div>
      
      </div>
    </div>
    
    
    <script>
    
    $(document).ready(function() {
      	 
      	 $('#batchRecordTable').DataTable({
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
    
    
    var commentId="";
    var processId="";
    var recomendationId="";
    var recomendation="";
    var comment="";
    var process="";
    var ermId=0;
    var transName="";
    var ermURL="";
    
    function removeBatchRecord(url){
    	ermURL=url;
    	 $("#popupmsg2").text("Are you sure you want to delete? ");
    	 
     	document.getElementById("modalTwo").style.display = "block";
    }
    

    function removeErmRecord(){
	location.href=ermURL;
    }
    		
    
    function performConfirmAction(){
    	
    	console.log("ermId:  "+ermId+ " proc: "+process+"comment: "+comment);
    	ajaxCall(ermId,comment,transName,recomendation);
    }


    function performWorkflowAction(actionName,id){
    	 commentId="#comment"+id;
    	 processId="#process"+id;
    	 recomendationId="#recommendation"+id;
    	 comment=$(commentId).val();
    	 process=$(processId).val();
    	 recomendation=$(recomendationId).val();
    	 ermId=id;
    	 transName=actionName;
    	 $("#popupmsg").text("Do You want to  "+actionName+" this record? ");
    	 

    	document.getElementById("modalOne").style.display = "block";
    	}
    
    
    
    function closePopup(){
 	   document.getElementById("modalOne").style.display = "none";
 }
    
    function closePopupTwo(){
  	   document.getElementById("modalTwo").style.display = "none";
  }
    
    
/* function performWorkflowAction(transName,ermId){
	var commentId="#comment"+ermId;
	var comment=$(commentId).val();
	console.log("ermId:  "+ermId);
	ajaxCall(ermId,comment,transName,process);
	} */

</script>
<script type="text/javascript">
function ajaxCall(ermId,comment,transName,recomendation){

    AUI().use('aui-io-request', function(A){
        A.io.request("<%=performWorkflowURL%>", {
               method: 'post',
               data: {
                   <portlet:namespace /><%=ErmFieldName.COMMENT%>: comment,
                   <portlet:namespace /><%=ErmFieldName.ERM_INFORMATION_ID%>: ermId,
                   <portlet:namespace /><%=ErmFieldName.RECOMMENDATION%>: recomendation,
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
    
    
    