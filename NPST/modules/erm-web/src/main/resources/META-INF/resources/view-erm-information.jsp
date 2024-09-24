<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="npst.common.constant.NpstCommonConstant"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.nps.erm.constants.ErmConstantValues"%>
<%@page import="com.nps.erm.constants.NpstErmConstant"%>
<%@page import="com.nps.erm.service.ErmInformationLocalServiceUtil"%>
<%@page import="com.nps.erm.model.ErmInformation"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.erm.constants.ErmFieldName"%>
<%@ include file="/init.jsp" %>
 
<%@page import="com.nps.erm.constants.ErmFieldLabel"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>

 <style>
 .npstborder{
 border-top: 0px;
 border-left: 0px;
 border-right: 0px;
 }
 h1 {
  text-align: center;
}
 </style>
 <%
 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NpstCommonConstant.DATE_FORMAT);
 long ermInfoId=ParamUtil.getLong(request, ErmFieldName.ERM_INFORMATION_ID);
 ErmInformation ermInformation=null;
 if(ermInfoId<=0){
	 try{
	 String ermId=(String)request.getAttribute(ErmFieldName.ERM_INFORMATION_ID);
	 if(ermId!="" && !ermId.equalsIgnoreCase("") && !ermId.equalsIgnoreCase(null) && ermId!=null){
		 System.out.println("ermInfoId  :  "+ermInfoId);
		 ermInfoId=Long.valueOf(ermId); 
	 }}catch(Exception e){}
 }
 
 if(ermInfoId<=0){
	 ermInformation=ErmInformationLocalServiceUtil.createErmInformation(0);
 }else{
	 ermInformation=ErmInformationLocalServiceUtil.getErmInformation(ermInfoId);
 }

 System.out.println(ermInformation.getBatchType());
 System.out.println(ermInformation.getBatchType()=="" || ermInformation.getBatchType()==null);
 %>
 
 <portlet:actionURL name="saveErmInformation" var="saveErmURL" />
  
  <aui:form action="<%=saveErmURL %>" method="POST" >
<h1>Section A: Case information</h1>
 <div>
 <aui:input readonly="true" type="hidden" name="<%=ErmFieldName.ERM_INFORMATION_ID %>" label="" value="<%=ermInformation.getErmInformationId() %>" > 
 <%-- <aui:input label="Old" name="<%=ErmFieldName.BATCH_TYPE %>" type="radio" value="<%=ErmConstantValues.OLD_BATCH_TYPE %>" checked="<%=(ermInformation.getBatchType()=="" ||ermInformation.getBatchType()==null)?true:(ermInformation.getBatchType().equals(ErmConstantValues.OLD_BATCH_TYPE)?true:false) %>" /> --%>
<aui:input readonly="true" label="New" name="<%=ErmFieldName.BATCH_TYPE %>" type="radio" value="<%=ErmConstantValues.NEW_BATCH_TYPE %>" checked="<%=ermInformation.getBatchType().equals(ErmConstantValues.NEW_BATCH_TYPE)?true:true %>" />
 </aui:input>
 a)	The Subscriber made a contribution of Rs.  <div style=" width: 12rem; display: inline-block;">
  <aui:input readonly="true" type="text" name="<%=ErmFieldName.TRANSACTED_AMOUNT %>"  id="<%=ErmFieldName.TRANSACTED_AMOUNT %>" label="" value="<%=ermInformation.getTransactedAmount() %>"> 
 </aui:input>
</div> 
   in their PRAN <div style=" width: 12rem; display: inline-block;"> 
   <aui:input type="text" readonly="true" name="<%=ErmFieldName.PRAN %>" id="<%=ErmFieldName.PRAN %>" label="" value="<%= ermInformation.getPran()%>" >
  </aui:input>
  </div> on <div style=" width: 12rem; display: inline-block;"><aui:input readonly="true" type="date" name="<%=ErmFieldName.TRANSACTION_DATE %>" id="<%=ErmFieldName.TRANSACTION_DATE %>" label="" value="<%=ermInformation.getTransactionDate() != null ? simpleDateFormat.format(ermInformation.getTransactionDate()) : null%>" >
  </aui:input> </div>
  through      <div style=" width: 12rem; display: inline-block;">
  
  <aui:input type="text" readonly="true"  label="" name="<%=ErmFieldName.TRANSACTION_MODE %>" value="<%= ermInformation.getTransactionMode()%>" ></aui:input>

</div>
</div>
<div>

b)	The contribution was settled in CRA system i.e., units got credited in the said PRAN on
  <div style=" width: 12rem; display: inline-block;"><aui:input type="date" readonly="true" name="<%=ErmFieldName.TRANSACTION_SETTLEMENT_DATE %>" id="<%=ErmFieldName.TRANSACTION_SETTLEMENT_DATE %>" label="" value="<%=ermInformation.getTransactionSettlementDate() != null ? simpleDateFormat.format(ermInformation.getTransactionSettlementDate()) : null%>">
  </aui:input> </div>
  </div>

c)	The subscriber has raised a grievance request in CRA grievance system through 
<div style=" width: 8rem; display: inline-block;">
<aui:input type="text" readonly="true"  label="" name="<%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" value="<%= ermInformation.getRectificationRequestMode()%>" ></aui:input>

 </div>
 <div id="tokenfileddiv" style=" width: 8rem; display: inline-block;"></div>

   dated <div style=" width: 12rem; display: inline-block;"><aui:input type="date" readonly="true" name="<%=ErmFieldName.RECTIFICATION_DATE %>" id="<%=ErmFieldName.RECTIFICATION_DATE %>" label=""  value="<%=ermInformation.getRectificationDate() != null ? simpleDateFormat.format(ermInformation.getRectificationDate()) : null%>">
   </aui:input></div>  for rectification of erroneous contribution stating that they mistakenly remitted
     Rs. 
     <div style=" width: 12rem; display: inline-block;"> 
      <aui:input type="text" readonly="true" name="<%=ErmFieldName.REMITTED_AMOUNT %>"  id="<%=ErmFieldName.REMITTED_AMOUNT %>" label="" value="<%=ermInformation.getRemittedAmount() %>">
 </aui:input> 
 </div>
    on <div style=" width: 12rem; display: inline-block;"><aui:input type="date" readonly="true" name="<%=ErmFieldName.REMITTED_DATE %>" id="<%=ErmFieldName.REMITTED_DATE %>" label="" value="<%=ermInformation.getRemittedDate() != null ? simpleDateFormat.format(ermInformation.getRemittedDate()) : null%>">
    </aui:input> </div>
    
      in Tier I <div style=" width: 12rem; display: inline-block;">
       <aui:input type="text" name="<%=ErmFieldName.TIER_TYPE %>" readonly="true" id="<%=ErmFieldName.TIER_TYPE %>"  label="" value="<%=ermInformation.getTierType() %>"></aui:input>

</div>
   Hence subscriber has requested to
   <div style=" width: 12rem; display: inline-block;">
   <aui:input type="text" readonly="true"  label="" name="<%=ErmFieldName.TRANSACTION_TYPE %>" value="<%= ermInformation.getTransactionType()%>" ></aui:input>
</div>
 the amount of    Rs. 
 <div style=" width: 8rem; display: inline-block;"> 
  <aui:input type="number" name="<%=ErmFieldName.TRANSFER_AMOUNT %>" readonly="true" id="<%=ErmFieldName.TRANSFER_AMOUNT %>"  label="" value="<%=ermInformation.getTransferAmount() %>">
 </aui:input> 
</div>


 <div style=" width: 8rem; display: inline-block;"> 
  <aui:input type="text" name="<%=ErmFieldName.TRANSFER_AMOUNT1 %>" readonly="true" id="<%=ErmFieldName.TRANSFER_AMOUNT1 %>"  label="" value="<%=ermInformation.getTransferAmount1() %>"> 
 </aui:input> 
</div>


<div>
d)	The subscriber has provided <div style=" width: 8rem; display: inline-block;"> 
  <aui:input type="text" name="<%=ErmFieldName.TRANSFER_AMOUNT2 %>" readonly="true" id="<%=ErmFieldName.TRANSFER_AMOUNT2 %>"  label="" value="<%=ermInformation.getTransferAmount2() %>"> 
 </aui:input> </div>  complete documentation on <div style=" width: 8rem; display: inline-block;"><aui:input type="date" readonly="true" name="<%=ErmFieldName.DOCUMENTATIONS_DATE %>" id="<%=ErmFieldName.DOCUMENTATIONS_DATE %>" label="" value="<%=ermInformation.getDocumentationsDate() != null ? simpleDateFormat.format(ermInformation.getDocumentationsDate()) : null%>" >
</aui:input>

 </div>
</div>
<div>
e)	As per the request, error rectification needs to be carried out for Rs 
<div style=" width: 12rem; display: inline-block;"> 
 <aui:input type="number" name="<%=ErmFieldName.RECTIFICATION_AMOUNT %>" readonly="true"  id="<%=ErmFieldName.RECTIFICATION_AMOUNT %>" label="" value="<%=ermInformation.getRectificationAmount() %>" > 
 </aui:input> 
 </div>
</div>

<h1>Section B: Transaction Details</h1>

 <table id="transactionDetailsTable"
		class="display table table-striped display nowrap cell-border" style="width: 100%">
		<thead>
			<tr class="border ">
				<th >Sr.No</th>
				<th>Particulars</th>
				<th>Details</th>
	        </tr>
	        
	    </thead>    
	    <tbody>
	    <tr>
			 <td class="tbdata">1</td>
			 <td  class="tbdata"><%=ErmFieldLabel.CASE_NO %> </td>
			 <td class="tbdata">
			 <aui:input type="text" readonly="true" name="<%=ErmFieldName.CASE_NO %>" id="<%=ErmFieldName.CASE_NO %>" label="" value="<%=ermInformation.getCaseNo() %>"></aui:input>
			
			 </td>
		</tr>	
		<tr>
			 <td>2</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_PRAN %> </td>
			  <td class="tbdata">
             <aui:input type="text" name="<%=ErmFieldName.AUTO_PRAN %>" id="<%=ErmFieldName.AUTO_PRAN %>" readonly="true" label="" value="<%=ermInformation.getPran() %>"></aui:input>
			 </td>
		</tr>		
			<tr>
			 <td>3</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_SUBSCRIBER_NAME %> </td>
			 <td class="tbdata">
             <aui:input type="text" readonly="true" name="<%=ErmFieldName.SUBSCRIBER_NAME %>" label="" value="<%=ermInformation.getSubscriberName() %>"></aui:input>
			
			 </td>
		</tr>	
			<tr>
			 <td>4</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_TRANSACTION_MODE %> </td>
			  <td class="tbdata">
             <aui:input type="text" name="<%=ErmFieldName.AUTO_TRANSACTION_MODE %>" id="<%=ErmFieldName.AUTO_TRANSACTION_MODE %>" value="<%=ermInformation.getTransactionMode() %>" label="" readonly="true"></aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>5</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_TRANSACTION_DATE %> </td>
             <td class="tbdata">
            <aui:input type="text" name="<%=ErmFieldName.AUTO_TRANSACTION_DATE %>" id="<%=ErmFieldName.AUTO_TRANSACTION_DATE %>" label="" readonly="true" value="<%=NpstCommonUtil.getDateString(ermInformation.getTransactionDate()) %>"></aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>6</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_ENPS_ORDER_ID %></td>
			  <td class="tbdata">
			  <aui:input type="text" name="<%=ErmFieldName.ENPS_ORDER_ID %>" readonly="true" label="" value="<%=ermInformation.getEnpsOrderId() %>">
			</aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>7</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_TRANSACTION_SETTLEMENT_DATE %></td>
             <td class="tbdata">
            <aui:input type="text" name="<%=ErmFieldName.AUTO_TRANSACTION_SETTLEMENT_DATE %>" id="<%=ErmFieldName.AUTO_TRANSACTION_SETTLEMENT_DATE %>" label="" readonly="true" value="<%=NpstCommonUtil.getDateString(ermInformation.getTransactionSettlementDate()) %>"></aui:input>
			
			 </td>
		</tr>
			<tr>
			 <td>8</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_TRANSACTED_AMOUNT %></td>
             <td class="tbdata">
            <aui:input type="text" name="<%=ErmFieldName.AUTO_TRANSACTED_AMOUNT %>" id="<%=ErmFieldName.AUTO_TRANSACTED_AMOUNT %>" value="<%=ermInformation.getTransactedAmount() %>" label="" readonly="true"></aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>9</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_TIER_TYPE %></td>
             <td class="tbdata">
            <aui:input type="text" name="<%=ErmFieldName.TIER_TYPE %>" id="<%=ErmFieldName.AUTO_TIER_TYPE %>" label="" readonly="true" value="Tier I"></aui:input>
			
			 </td>
		</tr>
			<tr>
			 <td>10</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_TYPE %></td>
             <td class="tbdata">
             <aui:input type="text" readonly="true"  label="" name="<%=ErmFieldName.RECTIFICATION_TYPE %>" value="<%= ermInformation.getRectificationType()%>" ></aui:input>
			 </td>
		</tr>
		<tr>
			 <td>11</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_AMOUNT %></td>
             <td class="tbdata">
            <aui:input type="text" name="<%=ErmFieldName.AUTO_RECTIFICATION_AMOUNT %>" id="<%=ErmFieldName.AUTO_RECTIFICATION_AMOUNT %>" label="" readonly="true" value="<%=ermInformation.getRectificationAmount() %>">
            </aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>12</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_REQUEST_MODE %></td>
             <td class="tbdata">
            <aui:input type="text" name="<%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE %>" readonly="true" id="<%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE %>" label=""  value="<%=ermInformation.getRectificationRequestMode() %>"></aui:input>
			
			 </td>
		</tr>
			<tr>
			 <td>13</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_REQUEST_DATE %></td>
             <td class="tbdata">
            <aui:input type="date" name="<%=ErmFieldName.RECTIFICATION_REQUEST_DATE %>" id="<%=ErmFieldName.RECTIFICATION_REQUEST_DATE %>" readonly="true" label="" value="<%=ermInformation.getRectificationRequestDate() != null ? simpleDateFormat.format(ermInformation.getRectificationRequestDate()) : null%>" >

            </aui:input>
			
			 </td>
		</tr>	
		</tbody>
</table>
<h1>Section C: Details for Reference</h1>

a)	Grievance received by CRA in CGMS / mail on (date):<div style=" width: 12rem; display: inline-block;"><aui:input type="date" readonly="true" name="<%=ErmFieldName.GRIEVANCE_RECEIVED_DATE %>" label="" value="<%=ermInformation.getGrievanceReceivedDate() != null ? simpleDateFormat.format(ermInformation.getGrievanceReceivedDate()) : null%>"  >
</aui:input></div>
b)	Grievance Text raised by Subscriber :<div style=" width: 60rem; display: inline-block;"><aui:input type="textarea" readonly="true" name="<%=ErmFieldName.GRIEVANCE_TEXT %>"  label="" value="<%=ermInformation.getGrievanceText() %>">
</aui:input></div>

<div>

<div class="row">
<div class="col-md-6 col-lg-6 col-sm-6">

<%if(ermInformation.getSelfDeclarationFileId()>0){ %>
 <a class="btn" target="_blank" style="color:blue;" href="<%=NpstCommonUtil.getFileDownloadURL(ermInformation.getSelfDeclarationFileId(), themeDisplay)%>">Download <%=ErmFieldLabel.SELF_DECLARATION_FILE %> File</a>
<%} %>
</div>
<div class="col-md-6 col-lg-6 col-sm-6">
<%if(ermInformation.getAccountStatementFileId()>0){ %>
 <a class="btn" target="_blank" style="color:blue;" href="<%=NpstCommonUtil.getFileDownloadURL(ermInformation.getAccountStatementFileId(), themeDisplay)%>"><%=ErmFieldLabel.ACCOUNT_STATEMENT_FILE %></a>
<%} %>
</div>
</div>

<div class="row">
<div class="col-md-6 col-lg-6 col-sm-6">

<%if(ermInformation.getTransactionsStatementFileId()>0){ %>
 <a class="btn" target="_blank" style="color:blue;" href="<%=NpstCommonUtil.getFileDownloadURL(ermInformation.getTransactionsStatementFileId(), themeDisplay)%>"><%=ErmFieldLabel.TRANSACTIONS_STATEMENT_FILE %></a>
<%} %>
</div>
<div class="col-md-6 col-lg-6 col-sm-6">

<%if(ermInformation.getDocumentNameFileId()>0){ %>
 <a class="btn" target="_blank" style="color:blue;" href="<%=NpstCommonUtil.getFileDownloadURL(ermInformation.getDocumentNameFileId(), themeDisplay)%>"><%=ErmFieldLabel.DOCUMENT_FILE %></a>
<%} %>
</div>

	<portlet:resourceURL var="exportURL" id="/erm/formpdf/download">
						<portlet:param name="<%=ErmFieldName.ERM_INFORMATION_ID %>" value="<%=String.valueOf(ermInformation.getErmInformationId()) %>" />
						</portlet:resourceURL>

<div class="col-md-6 col-lg-6 col-sm-6">

 <a class="btn" target="_blank" style="color:blue;" href="<%=exportURL%>">Form PDF</a>

</div>



</div>
</div>


</aui:form>


<script>



$(document).ready(function(){
	    
	    var amt=$("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_REQUEST_MODE%>").val();
		 
		 var tokenLabel ="<%=ErmFieldLabel.TOKEN_NO%>";
		 var emailLabel ="<%=ErmFieldLabel.Email%>";

		 if(tokenLabel==amt){
			 $("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("CGMS"); 
			 $("#tokenfileddiv").append('<input type="text" name="<portlet:namespace/><%=ErmFieldName.TOKEN_NO %>" id="<portlet:namespace/><%=ErmFieldName.TOKEN_NO %>" label="" value="<%=ermInformation.getTokenNo() %>" readonly="true"  class="form-control" >');
		 }
	if(emailLabel==amt){
		$("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("Direct Mail-Letter from sub");	 
		 }
	if(amt=="Other"){
		$("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("Req from NPST-PFRDA"); 
	}
    
});

</script>
