

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="nps.common.service.constants.NameMappingConstants"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="npst.common.constant.NpstCommonConstant"%>
<%@page import="npst.common.constant.NpstRoleConstant"%>
<%@page import="com.nps.erm.constants.ErmConstantValues"%>
<%@page import="com.nps.erm.constants.NpstErmConstant"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="java.util.Set"%>
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

<portlet:renderURL var="ermFilterURL">
    <portlet:param name="mvcRenderCommandName" value="/erm/filter" />
    <portlet:param name="jspName" value="/create-batch.jsp" />
</portlet:renderURL>


<portlet:resourceURL var="performWorkflowURL" id="performErmWorkflowTask"/>
<%
SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NpstErmConstant.DATE_FORMAT);
Set<String> userTypes=NpstCommonUtil.getUserType(user.getCompanyId(), NpstRoleConstant.CRA_ROLES, NpstCommonConstant.GRIVANCES_TYPE);
String  craName=ParamUtil.getString(request, ErmFieldName.CRA_NAME);
System.out.println("craName:  "+craName);
if(!userTypes.contains(craName)){
	craName=NpstErmConstant.ALL_CRA;
	}

List<ErmModal> ermModals= ErmUtil.getErmModalsByCRA(user.getCompanyId(), user.getUserId(),craName);
Set<String> duplicatePran=ErmUtil.getDuplicatePran();
%>
<%-- String backUrl=ParamUtil.getString(request, ErmFieldName.BACK_URL);
if(backUrl!=null && backUrl!="" && !backUrl.equalsIgnoreCase(null)){
%>
<a href="<%=backUrl%>">Back</a>
<%} %> --%>
<portlet:resourceURL var="generateBatchURL" id="generateBatch"/>
<portlet:renderURL var="pendingBatchURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
			<portlet:param name="mvcPath" value="/pending-batch.jsp" />
			<portlet:param name="<%=ErmFieldName.BACK_URL %>" value="<%=themeDisplay.getURLCurrent() %>" />
	</portlet:renderURL>

<button class="btn btn-primary" onclick="createBatch()">Create Batch</button>
<br><div style="width: 10%; float: right; margin-top: -2rem;">
<a href="<%=backURL%>" class="btn btn-primary" style="border-radius: 50px;"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a>
</div>
<div style="width: 15%; float: right; margin-top: -2rem;">
<a href="<%=pendingBatchURL%>" class="btn btn-primary" style="border-radius: 50px;">Pending Batch</a>
</div>

<div style="width: 50%; margin-bottom: -1rem;">
    <select id="craName" style="width: 23%;">
    <option value="<%=NpstErmConstant.ALL_CRA%>" selected>All</option>
<%for(String craType:userTypes){
	String isSelected = "";
	if(craType.equalsIgnoreCase(craName) ){
		isSelected=" selected";
	}
	%>
<option value="<%=craType%>" <%=isSelected%> ><%=NameMappingConstants.CRA_NAME_MAP.get(craType) %></option>

<%} %>
    </select>   
    
</div>
<div class="dataTable-container" >
<table id="createBatchTable" class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th></th>
				<%-- <th><%=ErmFieldLabel.TABLE_VIEW_BATCH_TYPE%></th> --%>
				<th><%=ErmFieldLabel.TABLE_VIEW_CRA_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_PRAN%></th>
				<!-- <th>pran dup</th> -->
				<th><%=ErmFieldLabel.TABLE_VIEW_SUBSCRIBER_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TRANSACTION_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TRANSACTED_AMOUNT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_AMOUNT%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_TYPE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_DOCUMENT_SUBMISSIONDATE%></th>
				
				<th><%=ErmFieldLabel.TABLE_VIEW_FORWARD_TO_NPST_DATE%></th>
				<%-- <th><%=ErmFieldLabel.TABLE_VIEW_CREATE_DATE%></th> --%>
				<th><%=ErmFieldLabel.TABLE_VIEW_CRA_REMARK%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_STIPULATED_TIME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SUBMISSON_STIPULATED_TIME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_BANK_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_BANK_STATEMENT_NAME%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_TXN_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SELF_RECTIFIED_DATE%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_SUBSCRIBER_SECTOR%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_DUPLICATE_PRAN%></th>
				
				<th><%=ErmFieldLabel.TABLE_VIEW_NPS_REMARK%></th>
				<th><%=ErmFieldLabel.TABLE_VIEW_RECOMMENDATION%></th>
				
				<%-- <th><%=ErmFieldLabel.TABLE_VIEW_STATUS%></th> --%>
				
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
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value="<%=ermInformation.getErmInformationId()+"" %>" />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
							
							<portlet:renderURL var="ermPriorViewURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
								<portlet:param name="mvcPath" value="/prior-view.jsp" />
								<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value="<%=ermInformation.getErmInformationId()+"" %>" />
								<portlet:param name="backURL" value="themeDisplay.getURLCurrent()" />
							</portlet:renderURL>
							  <portlet:renderURL  var="viewPranURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>" >
					  		<portlet:param name="<%=ErmFieldName.PRAN %>" value="<%=ermInformation.getPran() %>" />
					  		<portlet:param name="mvcPath" value="/view-pran-records.jsp" />
        				 </portlet:renderURL>
        				<%-- <%if(ermModal.isCraCheck() || ermModal.isSubcriberCheck()){ %> 
					<tr style="background-color:red;">
					<%}else{ %> --%>
					<tr>
					<%-- <%} %> --%>
				
        				<%-- <%String workflowUrl=ErmUtil.getWorkflowUrl(request, themeDisplay,ermModal.getWorkflowTaskId(),LiferayWindowState.NORMAL); %> --%>
        				<td><input class="batch" name="batch<%=count %>" label="" type="checkbox" value="<%=ermInformation.getErmInformationId() %>"></input></td>
					<%-- 	<td><%=count++ %></td> --%>
						<td class="tbdata"><%=ermModal.getIntermediaryName() %></td>
						<td class="tbdata">
						<%if(duplicatePran.contains(ermInformation.getPran())){ %>
						<button class="btn btn-link bg-warning" onClick='viewPranRecords("<%= viewPranURL%>")'><%=ermInformation.getPran() %></button>
						<%}else{ %>
						<%=ermInformation.getPran() %>
						<%} %>
						</td>
						<%-- <td class="tbdata"><a href="<%= ermModal.isAssignable()?editErmURL:'#'%>"><%=ermInformation.getPran() %></a></td> --%>
						<td class="tbdata">
						<a style="color: #6b6c7e;" target="_blank" href="<%= ermModal.isAssignable()?editErmURL:'#'%>"><%=ermInformation.getSubscriberName() %></a>						 
						
						</td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getTransactionDate()) %></td>
						<td class="tbdata"><%=ermInformation.getTransactedAmount() %></td>
						<td class="tbdata"><%=ermInformation.getRectificationAmount() %></td>
						<td class="tbdata"><%=ermInformation.getRectificationType() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getDocumentationsDate()) %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getFwdnpstDate()) %></td>
						
						<%-- <td class="tbdata"><%=NpstCommonUtil.getDateString(ermInformation.getCreateDate()) %></td> --%>
						<td class="tbdata"><%=ermInformation.getNoNpstRemark() %></td>
						<%if(ermModal.isSubcriberCheck()){ %>
						<td style="background-color:red;">
						<%}else{ %>
						<td>
						<%} %>
					
						<%-- <%=ermModal.isSubcriberCheck()?"No":"Yes"%>
						<input type="hidden" id="submissonStipulatedTime<%=ermInformation.getErmInformationId() %>" value='<%=ermModal.isSubcriberCheck()?"No":"Yes"%>'></input> --%>
						
						<select id="submissonStipulatedTime<%=ermInformation.getErmInformationId() %>">
						 <option value="Yes" <%=!ermModal.isSubcriberCheck()?"selected":""%> >Yes</option>
						<option value="No" <%=ermModal.isSubcriberCheck()?"selected":""%> >No</option>
						</select>
						
						
						</td>
						
						<%if(ermModal.isCraCheck()){ %>
						<td style="background-color:red;">
						<%}else{ %>
						<td>
						<%} %>
						
						<select id="stipulatedTime<%=ermInformation.getErmInformationId() %>">
						<%-- <%for(Entry<String,String> entry:ErmConstantValues.STIPULATED_TIME_MAP.entrySet()){
							boolean isSelected=false;
							 if(entry.getKey().equalsIgnoreCase(ermInformation.getStipulated())){
								 isSelected=true; 
							 }
						
						%> --%>
						 <option value="Yes" <%=!ermModal.isCraCheck()?"selected":""%> >Yes</option>
						<option value="No" <%=ermModal.isCraCheck()?"selected":""%> >No</option>
                       
                      <%--   <%} %> --%>
						</select>

						</td>
						
						<td><input class="bankName" placeholder="This is required." name="<%=ErmFieldName.BANK_NAME %><%=ermInformation.getErmInformationId() %>" id="<%=ErmFieldName.BANK_NAME %><%=ermInformation.getErmInformationId() %>" label="" type="text" value=""></input></td>
						<td><input class="bankStatementName" placeholder="This is required." name="<%=ErmFieldName.BANK_STATEMENT_NAME %><%=ermInformation.getErmInformationId() %>" id="<%=ErmFieldName.BANK_STATEMENT_NAME %><%=ermInformation.getErmInformationId() %>" label="" type="text" value=""></input></td>
						<td><input class="txnDate" placeholder="This is required." name="<%=ErmFieldName.TXN_DATE %><%=ermInformation.getErmInformationId() %>" id="<%=ErmFieldName.TXN_DATE %><%=ermInformation.getErmInformationId() %>" label="" type="date" value="<%=ermInformation.getTxnDate() != null ? simpleDateFormat.format(ermInformation.getTxnDate()) : null%>"></input></td>
						<td><input class="selfrectifiedDate" placeholder="This is required." name="<%=ErmFieldName.SELF_RECTIFIED_DATE %><%=ermInformation.getErmInformationId() %>" id="<%=ErmFieldName.SELF_RECTIFIED_DATE %><%=ermInformation.getErmInformationId() %>" label="" type="date" value="<%=ermInformation.getSelfRectifiedDate() != null ? simpleDateFormat.format(ermInformation.getSelfRectifiedDate()) : null%>" > </input></td>
						<td>
						<select id="subscriberSector<%=ermInformation.getErmInformationId() %>">
						<option value=""  >Select</option>
						<option value="CG" <%=ermInformation.getSubscriberSector().equalsIgnoreCase("CG")?"selected1":"" %> >CG</option>
						<option value="SG" <%=ermInformation.getSubscriberSector().equalsIgnoreCase("SG")?"selected1":"" %>  >SG</option>
						<option value="Corporate" <%=ermInformation.getSubscriberSector().equalsIgnoreCase("Corporate")?"selected1":"" %> >Corporate</option>
						<option value="UOS-PoP" <%=ermInformation.getSubscriberSector().equalsIgnoreCase("UOS-PoP")?"selected1":"" %> >UOS-PoP</option>
						<option value="UOS-eNPS" <%=ermInformation.getSubscriberSector().equalsIgnoreCase("UOS-eNPS")?"selected1":"" %> >UOS-eNPS</option>

						</select>
						
					<%-- 	<input class="subcriberSector" placeholder="This is required." name="<%=ErmFieldName.SUBSCRIBER_SECTOR %><%=ermInformation.getErmInformationId() %>" id="<%=ErmFieldName.SUBSCRIBER_SECTOR %><%=ermInformation.getErmInformationId() %>" label="" type="text" value="<%=ermInformation.getSubscriberSector() %>"></input> --%>
						
						</td>
						
						
						
						
						<td><%=duplicatePran.contains(ermInformation.getPran())?"Yes":"No" %></td>
						
						<td class="tbdata">
						<input class="remark" id="remark<%=ermInformation.getErmInformationId() %>" name="remark<%=ermInformation.getErmInformationId() %>" label="" type="text" value="<%=ermInformation.getNpstRemark() %>"></input>
						</td>
						<td class="tbdata">
						<select id="recommendation<%=ermInformation.getErmInformationId() %>">
						<%--  <%for(Entry<String,String> entry:ErmConstantValues.RECOMMENDATION_MAP.entrySet()){
						 boolean isSelected=false;
						 if(entry.getKey().equalsIgnoreCase(ermInformation.getRecommendation())){
							 isSelected=true; 
						 }
						 %>
                        <option value="<%=entry.getKey() %>" <%=isSelected?"selected":""%>><%=entry.getValue() %></option>
                        <%} %> --%>
                        <option value="Accepted" <%=ermInformation.getRecommendation().equalsIgnoreCase("Accepted")?"selected":""%> >Accepted</option>
                        <option value="Rejected" <%=ermInformation.getRecommendation().equalsIgnoreCase("Rejected")?"selected":""%> >Rejected</option>
                        
                        
                        
							</select>
						</td>
						
					<%-- 	<td class="tbdata"><%=ermInformation.getErmStatus() %></td> --%>
						<td class="tbdata">
						
						<liferay-ui:icon-menu  markupView="lexicon" direction="left-side"   showWhenSingleIcon="<%= false %>">
						 <button class="btn btn-link" style="color: #6b6c7e;" onClick='performWorkflowAction("<%=NpstErmConstant.NPST_REJECT_TRANS_NAME %>","<%=ermInformation.getErmInformationId()%>")'><liferay-ui:icon image="" message="<%=NpstErmConstant.NPST_REJECT_TRANS_NAME %>" /></button><br>
						  <%--  <a href="<%=performWorkflowURL%>&<portlet:namespace/>action=<%=transitionName%>">Approve</a> --%>
							<a style="color: #6b6c7e;" target="_blank" href="<%= ermModal.isAssignable()?editErmURL:'#'%>"><liferay-ui:icon image="" message="Edit" /></a>						 
						
						   </liferay-ui:icon-menu>
						
						
						
						
						</td>
						
						
						<%-- <td><input class="duplicatePran" placeholder="This is required." name="<%=ErmFieldName.DUPLICATE_PRAN %><%=ermInformation.getErmInformationId() %>" id="<%=ErmFieldName.DUPLICATE_PRAN %><%=ermInformation.getErmInformationId() %>" label="" type="text" value="<%=ermInformation.getPran() %>"></input></td> --%>
					
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
              <aui:input class="fname" type="date" label="<%=ErmFieldLabel.BATCH_TIME_PERIOD_FROM %>" name="<%=ErmFieldName.BATCH_TIME_PERIOD_FROM %>" id="<%=ErmFieldName.BATCH_TIME_PERIOD_FROM %>" />
              <aui:input class="fname" type="date" label="<%=ErmFieldLabel.BATCH_TIME_PERIOD_To %>" name="<%=ErmFieldName.BATCH_TIME_PERIOD_To %>" id="<%=ErmFieldName.BATCH_TIME_PERIOD_To %>" />
             <%--  <aui:input class="fname" type="date" name="<%=ErmFieldName.RECEIPT_CUTOFF_DATE %>" id="<%=ErmFieldName.RECEIPT_CUTOFF_DATE %>" /> --%>
            <div class="text-center">
            <button class="text-center btn btn-primary" type="submit" onClick="generateBatch()">Generate</button>
        </div>
        </div>
      </div>
      
      </div>
    </div>
    

    
<!--  <div id="modalTwo" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopupTwo()" style="float: right;">&times;</a><br>
           <h3>Please select CRA.</h3>
              
        </div>
      </div>
      
      </div>
    </div> -->   
    
     <div id="modalThree" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopupTwo()" style="float: right;">&times;</a><br>
           <h3 id="popupmsg" class="text-center"></h3>
             <br>

        </div>
      </div>
      
      </div>
    </div>
    
         <div id="modalFour" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopupFour()" style="float: right;">&times;</a><br>
           <h3 id="popupmsg4" class="text-center"></h3>
             <br>

        </div>
      </div>
      
      </div>
    </div>
    
     <div id="modalFive" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopupFive()" style="float: right;">&times;</a><br>
           <h3 id="popupmsg5" class="text-center"></h3>
             <br>
<div class="text-center">
            <button class="text-center btn btn-primary m-3" type="submit" onClick="performConfirmAction()">Yes</button>

            <button class="text-center btn btn-danger m-3" type="submit" onClick="closePopupFive()">No</button>

            
        </div>
        </div>
      </div>
      
      </div>
    </div>
    
       <script>
       $(document).ready(function() {
         	 
         	 $('#createBatchTable').DataTable({
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


   function createBatch(){

	   console.log("create batch called.");	
	   var craValue=$("#craName").val();
	   var ermIds="";
	   var bankName="";
	   var txnDate="";
	   var selfRectifiedDate="";
	   var subscriberSector="";
	   var bankStatementName="";
	 //  var duplicatePran="";

	   var checkbankName=false;
	   var checktxnDate=false;
	   var checkselfRectifiedDate=false;
	   var checksubscriberSector=false;
	   var checkbankStatementName=false;
	   //var checkduplicatePran=false;
	   
	   $('input:checkbox.batch').each(function () {
	   if(this.checked){
	       var ermId = $(this).val();
	       	ermIds=ermIds+ermId;
	       	
	    	var bankNameId="#<%=ErmFieldName.BANK_NAME%>"+ermId;
	    	var txnDateId="#<%=ErmFieldName.TXN_DATE%>"+ermId;
	    	var selfRectifiedDateId="#<%=ErmFieldName.SELF_RECTIFIED_DATE%>"+ermId;
	    	var subscriberSectorId="#<%=ErmFieldName.SUBSCRIBER_SECTOR%>"+ermId;
	    	var bankStatementNameId="#<%=ErmFieldName.BANK_STATEMENT_NAME%>"+ermId;
	    	<%-- var duplicatePranId="#<%=ErmFieldName.DUPLICATE_PRAN%>"+ermId; --%>
	    	
	    	console.log("log1");
	    	console.log(bankNameId);
	    	console.log(txnDateId);
	    	console.log(selfRectifiedDateId);
	    	console.log(subscriberSectorId);
	    	console.log(bankStatementNameId);
	    	//console.log(duplicatePranId);
	    	var bName=$(bankNameId).val();
	    	var tDate=$(txnDateId).val();
	    	var rectiDate=$(selfRectifiedDateId).val();
	    	var subSector=$(subscriberSectorId).val();
	    	var bStatementName=$(bankStatementNameId).val();
	    	//var dupPran=$(duplicatePranId).val();
	    	
	        	bankName=bankName+"<%=NpstCommonConstant.DOUBLE_COLON%>"+bName;
	       	 	txnDate=txnDate+"<%=NpstCommonConstant.DOUBLE_COLON%>"+tDate;
	       	 	selfRectifiedDate=selfRectifiedDate+"<%=NpstCommonConstant.DOUBLE_COLON%>"+rectiDate;
	       	 	subscriberSector=subscriberSector+"<%=NpstCommonConstant.DOUBLE_COLON%>"+subSector;
	       	 bankStatementName=bankStatementName+"<%=NpstCommonConstant.DOUBLE_COLON%>"+bStatementName;
	       	 	<%-- duplicatePran=duplicatePran+"<%=NpstCommonConstant.DOUBLE_COLON%>"+dupPran; --%>
	     	   console.log("log3");
	     	   	console.log(bName);
	     	   	console.log(tDate);
	     	   	console.log(rectiDate);
	     	   	console.log(subSector);
	     		console.log(bStatementName);
	     	   //	console.log(dupPran);
	       	 if(bName=="" || bName==null || bName=="undefined"){
	    		 checkbankName=true;
	    		}
	       	if(bStatementName=="" || bStatementName==null || bStatementName=="undefined"){
	       		checkbankStatementName=true;
	    		}
	    	 if(tDate=="" || tDate==null || tDate=="undefined"){
	    		 checktxnDate=true;
	    		}
	    	 if(rectiDate=="" || rectiDate==null || rectiDate=="undefined"){
	    		 checkselfRectifiedDate=true;
	    		}
	    	 if(subSector=="" || subSector==null || subSector=="undefined"){
	    		 checksubscriberSector=true;
	    		}
	    	 
	    	/*  if(dupPran=="" || dupPran==null || dupPran=="undefined"){
	    		 checkduplicatePran=true;
	    		} */
	   }
	   });

	   console.log("log3");
   	console.log(checkbankName);
   	console.log(checktxnDate);
   	console.log(checkselfRectifiedDate);
   	console.log(checksubscriberSector);
   	console.log(checkbankStatementName);
   	//console.log(checkduplicatePran);
	   
	   if(craValue==<%=NpstErmConstant.ALL_CRA%>){
		   $("#popupmsg").text("Please select CRA. ");
		   document.getElementById("modalThree").style.display = "block";  
	   }else{
		   if(ermIds==""){
			   $("#popupmsg").text("Please select atleast one record.  ");
			   document.getElementById("modalThree").style.display = "block"; 
		   }else{
			  if(checkbankName==true || checktxnDate==true || checkselfRectifiedDate==true || checksubscriberSector==true || checkbankStatementName==true){
				  if(checkbankName==true){
					   $("#popupmsg").text("Bank name is required. ");
					   document.getElementById("modalThree").style.display = "block"; 
				   }
				  if(checkbankStatementName==true){
					   $("#popupmsg").text("Name on bank statement  is required. ");
					   document.getElementById("modalThree").style.display = "block"; 
				   }
				   if(checktxnDate==true){
					   $("#popupmsg").text("Transaction date is required. ");
					   document.getElementById("modalThree").style.display = "block"; 
				   }
				   if(checkselfRectifiedDate==true){
					   $("#popupmsg").text("Self rectified date is required. ");
					   document.getElementById("modalThree").style.display = "block"; 
				   }
				   if(checksubscriberSector==true){
					   $("#popupmsg").text("Subscriber sector is required. ");
					   document.getElementById("modalThree").style.display = "block"; 
				   }
				   /* if(checkduplicatePran==true){
					   $("#popupmsg").text("Duplicate pran is required. ");
					   document.getElementById("modalThree").style.display = "block"; 
				   } */
			  }else{
				  document.getElementById("modalOne").style.display = "block";    
			  }
		   }
	   }  
   }
   
   function closePopup(){
	   document.getElementById("modalOne").style.display = "none";
   }
   
   function closePopupTwo(){ 
	   document.getElementById("modalThree").style.display = "none";
   }
   
   function closePopupFour(){ 
	   document.getElementById("modalFour").style.display = "none";
	   location.reload();
   }
   
   function closePopupFive(){ 
	   document.getElementById("modalFive").style.display = "none";
	   //location.reload();
   }
   
    </script>
    
    <script>
    var revertRemark="";
    var revertRemarkId="";
    var revertErmId="";
    var revertTransName="";
    function performWorkflowAction(actionName,id){
    	revertRemarkId="#remark"+id;
   	revertRemark=$(revertRemarkId).val();
   	revertErmId=id;
   	revertTransName=actionName;
   	 $("#popupmsg5").text("Do You want to  "+actionName+" this record? ");
   	 

   	document.getElementById("modalFive").style.display = "block";
   	}
    
function performConfirmAction(){
    	
    	ajaxCallActionPerform(revertErmId,revertRemark,revertTransName,"");
    }
    
function ajaxCallActionPerform(ermId,comment,transName,recomendation){

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

<script>

function generateBatch(){
console.log("generate batch called.");	
var ermIds="";
var remarks="";
var recommendations="";
var stipulatedTimes="";
var submissionStipulatedTimes="";
var bankName="";
var txnDate="";
var selfRectifiedDate="";
var subscriberSector="";
var bankStatementName="";
//var duplicatePran="";

$('input:checkbox.batch').each(function () {
if(this.checked){
    var ermId = $(this).val();
    var remarkId="#remark"+ermId;
	var recommendationId="#recommendation"+ermId;
	var stipulatedTimeId="#stipulatedTime"+ermId;
	var submissonStipulatedTimeId="#submissonStipulatedTime"+ermId;
	
	var bankNameId="#<%=ErmFieldName.BANK_NAME%>"+ermId;
	var txnDateId="#<%=ErmFieldName.TXN_DATE%>"+ermId;
	var selfRectifiedDateId="#<%=ErmFieldName.SELF_RECTIFIED_DATE%>"+ermId;
	
	var subscriberSectorId="#<%=ErmFieldName.SUBSCRIBER_SECTOR%>"+ermId;
	var bankStatementNameId="#<%=ErmFieldName.BANK_STATEMENT_NAME%>"+ermId;
	<%-- var duplicatePranId="#<%=ErmFieldName.DUPLICATE_PRAN%>"+ermId; --%>
	
	console.log("log4");
	console.log(bankNameId);
	console.log(txnDateId);
	console.log(selfRectifiedDateId);
	console.log(subscriberSectorId);
	console.log(bankStatementNameId);
	//console.log(duplicatePranId);
	
    if(ermIds==""){
    	ermIds=ermIds+ermId;
    	
    	remarks=remarks+$(remarkId).val();
    	recommendations=recommendations+$(recommendationId).val();
    	stipulatedTimes=stipulatedTimes+$(stipulatedTimeId).val();
    	submissionStipulatedTimes=submissionStipulatedTimes+$(submissonStipulatedTimeId).val();
    	
    	 bankName=bankName+$(bankNameId).val();
    	 txnDate=txnDate+$(txnDateId).val();
    	 selfRectifiedDate=selfRectifiedDate+$(selfRectifiedDateId).val();
    	 subscriberSector=subscriberSector+$(subscriberSectorId).val();
    	 bankStatementName=bankStatementName+$(bankStatementNameId).val();
    	 /* duplicatePran=duplicatePran+$(duplicatePranId).val(); */

    }else{
    	ermIds=ermIds+","+ermId;
    	remarks=remarks+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(remarkId).val();
    	recommendations=recommendations+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(recommendationId).val();
    	stipulatedTimes=stipulatedTimes+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(stipulatedTimeId).val();
    	submissionStipulatedTimes=submissionStipulatedTimes+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(submissonStipulatedTimeId).val();
    	
    	bankName=bankName+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(bankNameId).val();
   	 	txnDate=txnDate+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(txnDateId).val();
   	 	selfRectifiedDate=selfRectifiedDate+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(selfRectifiedDateId).val();
   	 	subscriberSector=subscriberSector+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(subscriberSectorId).val();
   	 bankStatementName=bankStatementName+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(bankStatementNameId).val();
   	 	<%-- duplicatePran=duplicatePran+"<%=NpstCommonConstant.DOUBLE_COLON%>"+$(duplicatePranId).val(); --%>

    }
}
});



 var batchTimePeriodFrom=$("#<portlet:namespace/><%=ErmFieldName.BATCH_TIME_PERIOD_FROM%>").val();
var batchTimePeriodTo=$("#<portlet:namespace/><%=ErmFieldName.BATCH_TIME_PERIOD_To%>").val();
console.log(ermIds);
console.log(remarks);
console.log(recommendations);
console.log(stipulatedTimes);
console.log(bankName);
console.log(txnDate);
console.log(selfRectifiedDate);
console.log(subscriberSector);
console.log(bankStatementName);
var from = new Date(batchTimePeriodFrom).getTime();
var to = new Date(batchTimePeriodTo).getTime();
console.log("from : "+from+ " to : "+to);
//if(ermIds==""){
if(from<to){

	//comment for testing purpose
		ajaxCall(ermIds,batchTimePeriodFrom,batchTimePeriodTo,remarks,recommendations,stipulatedTimes,submissionStipulatedTimes,bankName,bankStatementName,txnDate,selfRectifiedDate,subscriberSector);
	document.getElementById("modalOne").style.display = "none";
}else{
	 $("#popupmsg").text("From date should be before than to date. ");
	   document.getElementById("modalThree").style.display = "block";  
	//alert("From date should always be before than to date.");
} 

/* }else{
	
	$("#popupmsg").text("Please select atleast one record.  ");
	   document.getElementById("modalThree").style.display = "block"; 
}
 */
console.log(ermIds);
	}

</script>

<script type="text/javascript">
function ajaxCall(ermIds,batchTimePeriodFrom,batchTimePeriodTo,remarks,recommendations,stipulatedTimes,submissionStipulatedTimes,bName,bStatement,transDate,selfRectdate,subscrSector){

    AUI().use('aui-io-request', function(A){
        A.io.request('<%=generateBatchURL%>', {
               method: 'post',
               data: {
                   <portlet:namespace />ermIds: ermIds,
                   <portlet:namespace /><%=ErmFieldName.REMARK%>: remarks,
                   <portlet:namespace /><%=ErmFieldName.RECOMMENDATION%>: recommendations,
                   <portlet:namespace /><%=ErmFieldName.STIPULATED_TIMES%>: stipulatedTimes,
                   <portlet:namespace /><%=ErmFieldName.DOCUMENT_SUBMISSION_STIPULATED_TIME%>: submissionStipulatedTimes,
                   <portlet:namespace /><%=ErmFieldName.BATCH_TIME_PERIOD_FROM%>: batchTimePeriodFrom,
                   <portlet:namespace /><%=ErmFieldName.BATCH_TIME_PERIOD_To%>: batchTimePeriodTo,
                   <portlet:namespace /><%=ErmFieldName.BANK_NAME%>: bName,
                   <portlet:namespace /><%=ErmFieldName.BANK_STATEMENT_NAME%>: bStatement,
                   <portlet:namespace /><%=ErmFieldName.TXN_DATE%>: transDate,
                   <portlet:namespace /><%=ErmFieldName.SELF_RECTIFIED_DATE%>: selfRectdate,
                   <portlet:namespace /><%=ErmFieldName.SUBSCRIBER_SECTOR%>: subscrSector,
                   
               },
               on: {
                       success: function() {
                    	   $("#popupmsg4").text(this.get('responseData'));
                    	   document.getElementById("modalFour").style.display = "block";  
                    	   //alert(this.get('responseData'));
                        	
                    }
              }
        });
    });
}


function viewPranRecords(url){
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
	    title: "Pran Records",
	    uri: url
	});  
}

</script>
   
    <script>
var craName = document.getElementById('craName');

craName.addEventListener('change', function handleChange(event) {
	var craType=event.target.value;
	window.location.href="<%=ermFilterURL%>&<portlet:namespace /><%=ErmFieldName.CRA_NAME%>="+ craType;

});
</script>
   