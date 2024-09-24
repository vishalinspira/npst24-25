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
 <aui:input type="hidden" name="<%=ErmFieldName.ERM_INFORMATION_ID %>" label="" value="<%=ermInformation.getErmInformationId() %>" > 
 <%-- <aui:input label="Old" name="<%=ErmFieldName.BATCH_TYPE %>" type="radio" value="<%=ErmConstantValues.OLD_BATCH_TYPE %>" checked="<%=(ermInformation.getBatchType()=="" ||ermInformation.getBatchType()==null)?true:(ermInformation.getBatchType().equals(ErmConstantValues.OLD_BATCH_TYPE)?true:false) %>" /> --%>
<aui:input label="New" name="<%=ErmFieldName.BATCH_TYPE %>" type="radio" value="<%=ErmConstantValues.NEW_BATCH_TYPE %>" checked="<%=ermInformation.getBatchType().equals(ErmConstantValues.NEW_BATCH_TYPE)?true:true %>" />
 </aui:input>
 a)	The Subscriber made a contribution of Rs.  <div style=" width: 12rem; display: inline-block;">
  <aui:input type="text" name="<%=ErmFieldName.TRANSACTED_AMOUNT %>" placeholder="Enter amount" id="<%=ErmFieldName.TRANSACTED_AMOUNT %>" label="" value="<%=ermInformation.getTransactedAmount() %>"> 
    <aui:validator name="required" />
  <aui:validator name="digits"/> 
 </aui:input>
</div> 
   in their PRAN <div style=" width: 12rem; display: inline-block;"> 
   <aui:input type="text" name="<%=ErmFieldName.PRAN %>" id="<%=ErmFieldName.PRAN %>" label="" value="<%= ermInformation.getPran()%>" >
 <aui:validator name="digits"/>
  <aui:validator name="required" />
  <aui:validator errorMessage="Please enter 12 digits PRAN number" name="rangeLength">[12,12]</aui:validator>
  </aui:input>
  </div> on <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.TRANSACTION_DATE %>" id="<%=ErmFieldName.TRANSACTION_DATE %>" label="" value="<%=ermInformation.getTransactionDate() != null ? simpleDateFormat.format(ermInformation.getTransactionDate()) : null%>" >
   <aui:validator name="required" />
   
   <aui:validator  name="custom"  errorMessage="<%=NpstErmConstant.DATE_ERROR_MESSAGE %>" >
	function (val, fieldNode, ruleValue) {
	return dateValidate(val);
	}
</aui:validator>
  </aui:input> </div>
  through      <div style=" width: 12rem; display: inline-block;">
  
  
 <aui:select name="<%=ErmFieldName.TRANSACTION_MODE %>" id="<%=ErmFieldName.TRANSACTION_MODE %>" label="" required="true" value="<%=ermInformation.getTransactionMode() %>">
  <aui:option value="">Select</aui:option>
  <%for(Entry<String,String> entry:ErmConstantValues.TRANSACTION_MODE_MAP.entrySet()){
                        	 boolean isSelected = false;
							if ((ermInformation.getTransactionMode() == entry.getKey())|| (ermInformation.getTransactionMode().equals(entry.getKey()))){
							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
</aui:select>
</div>

</div>
<div>

b)	The contribution was settled in CRA system i.e., units got credited in the said PRAN on
  <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.TRANSACTION_SETTLEMENT_DATE %>" id="<%=ErmFieldName.TRANSACTION_SETTLEMENT_DATE %>" label="" value="<%=ermInformation.getTransactionSettlementDate() != null ? simpleDateFormat.format(ermInformation.getTransactionSettlementDate()) : null%>">
   <aui:validator name="required" />
     <aui:validator  name="custom"  errorMessage="cannot be before transaction date." >
	function (val, fieldNode, ruleValue) {

	    var transaction_date=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_DATE%>").val();
	  
	var result = false;
	var date1 = new Date(val).getTime();
	var date2 = new Date(transaction_date).getTime();
	console.log("good");
	if (date2 <= date1) {
	result = true;
	}
	return result;
	<!-- return dateValidate(val); -->
	}
</aui:validator>
<aui:validator  name="custom"  errorMessage="<%=NpstErmConstant.DATE_ERROR_MESSAGE %>" >
	function (val, fieldNode, ruleValue) {
	return dateValidate(val);
	}
</aui:validator>
  </aui:input> </div>
  </div>

c)	The subscriber has raised a grievance request in CRA grievance system through  
<div style=" width: 11rem; display: inline-block;">
<%--  <%=ErmFieldLabel.TOKEN_NO %><input label="<%=ErmFieldLabel.TOKEN_NO %>" id="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" onchange="getRectificationRequestMode(this);" name="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" type="radio" value="<%=ErmFieldLabel.TOKEN_NO %>" <%=ermInformation.getRectificationRequestMode().equals(ErmFieldLabel.TOKEN_NO)?"checked":"" %> required />
<%=ErmFieldLabel.Email %><input label="<%=ErmFieldLabel.Email %>" id="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" onchange="getRectificationRequestMode(this);" name="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" type="radio" value="<%=ErmFieldLabel.Email %>" <%=ermInformation.getRectificationRequestMode().equals(ErmFieldLabel.Email)?"checked":"" %> required />
 Other<input label="Other" id="<%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" onchange="getRectificationRequestMode(this);" name="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" type="radio" value="Other" <%=ermInformation.getRectificationRequestMode().equals("Other")?"checked":"" %> required /> --%>
 
 <aui:select name="<%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" id="<%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" label="" required="true" value="<%=ermInformation.getRectificationRequestMode() %>">
   <aui:option value="">Select</aui:option>
  <%for(Entry<String,String> entry:ErmConstantValues.RECTIFICATION_REQUEST_MODE_MAP.entrySet()){
                        	 boolean isSelected = false;
							if ((ermInformation.getRectificationRequestMode() == entry.getKey())|| (ermInformation.getRectificationRequestMode().equals(entry.getKey()))){
							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
</aui:select>
 
 
  <%-- <%=ErmFieldLabel.TOKEN_NO %><input label="<%=ErmFieldLabel.TOKEN_NO %>" id="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" onchange="getRectificationRequestMode(this);" name="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" type="radio" value="<%=ErmFieldLabel.TOKEN_NO %>" checked="<%=(ermInformation.getRectificationRequestMode()=="" ||ermInformation.getRectificationRequestMode()==null)?true:(ermInformation.getRectificationRequestMode().equals(ErmFieldLabel.TOKEN_NO)?true:false) %>" />
<%=ErmFieldLabel.Email %><input label="<%=ErmFieldLabel.Email %>" id="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" onchange="getRectificationRequestMode(this);" name="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" type="radio" value="<%=ErmFieldLabel.Email %>" checked="<%=ermInformation.getRectificationRequestMode().equals(ErmFieldLabel.TOKEN_NO)?true:false %>" />
 Other<input label="Other" id="<%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" onchange="getRectificationRequestMode(this);" name="<portlet:namespace/><%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" type="radio" value="Other" checked='<%=ermInformation.getRectificationRequestMode().equals("Other")?true:false %>' /> --%>
 </div>
 <div id="tokenfileddiv" style=" width: 8rem; display: inline-block;"></div>
<%--  <aui:input type="text" name="<%=ErmFieldName.TOKEN_NO %>" id="<%=ErmFieldName.TOKEN_NO %>" label="" value="<%=ermInformation.getTokenNo() %>">
  <aui:validator name="required" />
 </aui:input> --%>
 
<%-- Email

  <div style=" width: 12rem; display: inline-block;">
 <aui:select name="<%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" label="" required="true"  id="<%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" value="<%=ermInformation.getRectificationRequestMode() %>">
   <%for(Entry<String,String> entry:ErmConstantValues.RECTIFICATION_REQUEST_MODE_MAP.entrySet()){
                        	 boolean isSelected = false;
							if ((ermInformation.getRectificationRequestMode() == entry.getKey())|| (ermInformation.getRectificationRequestMode().equals(entry.getKey()))){
							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
</aui:select>
</div> --%>
   dated <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.RECTIFICATION_DATE %>" id="<%=ErmFieldName.RECTIFICATION_DATE %>" label=""  value="<%=ermInformation.getRectificationDate() != null ? simpleDateFormat.format(ermInformation.getRectificationDate()) : null%>">
    <aui:validator name="required" />
      <aui:validator  name="custom"  errorMessage="cannot be before transaction date." >
	function (val, fieldNode, ruleValue) {
		    var transaction_date=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_DATE%>").val();
	  
	var result = false;
	var date1 = new Date(val).getTime();
	var date2 = new Date(transaction_date).getTime();
	console.log("good");
	if (date2 <= date1) {
	result = true;
	}
	return result;
	
	<!-- return dateValidate(val); -->
	}
</aui:validator>
<aui:validator  name="custom"  errorMessage="<%=NpstErmConstant.DATE_ERROR_MESSAGE %>" >
	function (val, fieldNode, ruleValue) {
	return dateValidate(val);
	}
</aui:validator>
   </aui:input></div>  for rectification of erroneous contribution stating that they mistakenly remitted
     Rs. 
     <div style=" width: 12rem; display: inline-block;"> 
      <aui:input type="text" name="<%=ErmFieldName.REMITTED_AMOUNT %>" placeholder="Enter amount" id="<%=ErmFieldName.REMITTED_AMOUNT %>" label="" value="<%=ermInformation.getRemittedAmount() %>">
       <aui:validator name="required" />
 <aui:validator name="digits"/>
 </aui:input> 
 </div>
    on <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.REMITTED_DATE %>" id="<%=ErmFieldName.REMITTED_DATE %>" label="" value="<%=ermInformation.getRemittedDate() != null ? simpleDateFormat.format(ermInformation.getRemittedDate()) : null%>">
     <aui:validator name="required" />
       <aui:validator  name="custom"  errorMessage="<%=NpstErmConstant.DATE_ERROR_MESSAGE %>" >
	function (val, fieldNode, ruleValue) {
	return dateValidate(val);
	}
</aui:validator>

    </aui:input> </div>
    
      in Tier I <div style=" width: 12rem; display: inline-block;">
       <aui:input type="text" name="<%=ErmFieldName.TIER_TYPE %>" placeholder="Enter Text" id="<%=ErmFieldName.TIER_TYPE %>"  label="" value="<%=ermInformation.getTierType() %>">
       <aui:validator name="required" />
       </aui:input>

</div>

  <%--   in <div style=" width: 12rem; display: inline-block;">
      <aui:select name="<%=ErmFieldName.TIER_TYPE %>" id="<%=ErmFieldName.TIER_TYPE %>" label="" required="true" value="<%=ermInformation.getTierType() %>">
 <aui:option value="">Select</aui:option>
 <%for(Entry<String,String> entry:ErmConstantValues.TIER_TYPE_MAP.entrySet()){
                        	 boolean isSelected = false;
							if ((ermInformation.getTierType() == entry.getKey())|| (ermInformation.getTierType().equals(entry.getKey()))){
							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
</aui:select>
</div> --%>
   Hence subscriber has requested to
   <div style=" width: 12rem; display: inline-block;">
      <aui:select name="<%=ErmFieldName.TRANSACTION_TYPE %>" label="" required="true" id="<%=ErmFieldName.TRANSACTION_TYPE %>" value="<%=ermInformation.getTransactionType() %>">
  <aui:option value="">Select</aui:option>
 <%for(Entry<String,String> entry:ErmConstantValues.TRANSACTION_TYPE_MAP.entrySet()){
                        	 boolean isSelected = false;
							if ((ermInformation.getTransactionType() == entry.getKey())|| (ermInformation.getTransactionType().equals(entry.getKey()))){
							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
</aui:select>
</div>
 the amount of    Rs. 
 <div style=" width: 8rem; display: inline-block;"> 
  <aui:input type="number" name="<%=ErmFieldName.TRANSFER_AMOUNT %>" id="<%=ErmFieldName.TRANSFER_AMOUNT %>" placeholder="Enter amount" label="" value="<%=ermInformation.getTransferAmount() %>">
   <aui:validator name="required" />
 <aui:validator name="digits"/>
      <aui:validator  name="custom"  errorMessage="cannot be more than transacted amount." >
	function (val, fieldNode, ruleValue) {
	var result=false;
	 var remittedAmmount=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTED_AMOUNT%>").val();
	 if(Number(val)<= Number(remittedAmmount)){
	 result=true;
	 }
	return result;
	}
</aui:validator>

 
 </aui:input> 
</div>


 <div style=" width: 8rem; display: inline-block;"> 
  <aui:input type="text" name="<%=ErmFieldName.TRANSFER_AMOUNT1 %>" id="<%=ErmFieldName.TRANSFER_AMOUNT1 %>" placeholder="Enter text" label="" value="<%=ermInformation.getTransferAmount1() %>"> 
 </aui:input> 
</div>


<div>
d)	The subscriber has provided <div style=" width: 8rem; display: inline-block;"> 
  <aui:input type="text" name="<%=ErmFieldName.TRANSFER_AMOUNT2 %>" id="<%=ErmFieldName.TRANSFER_AMOUNT2 %>" placeholder="Enter text" label="" value="<%=ermInformation.getTransferAmount2() %>"> 
 </aui:input> </div>  complete documentation on <div style=" width: 8rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.DOCUMENTATIONS_DATE %>" id="<%=ErmFieldName.DOCUMENTATIONS_DATE %>" label="" value="<%=ermInformation.getDocumentationsDate() != null ? simpleDateFormat.format(ermInformation.getDocumentationsDate()) : null%>" >
 <aui:validator name="required" />
  <aui:validator  name="custom"  errorMessage="cannot be before transaction date." >
	function (val, fieldNode, ruleValue) {
		    var transaction_date=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_DATE%>").val();
	  
	var result = false;
	var date1 = new Date(val).getTime();
	var date2 = new Date(transaction_date).getTime();
	console.log("good");
	if (date2 <= date1) {
	result = true;
	}
	return result;
	
	<!-- return dateValidate(val); -->
	}
</aui:validator>
<aui:validator  name="custom"  errorMessage="<%=NpstErmConstant.DATE_ERROR_MESSAGE %>" >
	function (val, fieldNode, ruleValue) {
	return dateValidate(val);
	}
</aui:validator>
</aui:input>

 </div>
</div>
<div>
e)	As per the request, error rectification needs to be carried out for Rs 
<div style=" width: 12rem; display: inline-block;"> 
 <aui:input type="number" name="<%=ErmFieldName.RECTIFICATION_AMOUNT %>" placeholder="Enter amount" id="<%=ErmFieldName.RECTIFICATION_AMOUNT %>" label="" value="<%=ermInformation.getRectificationAmount() %>" >
  <aui:validator name="required" />
 <aui:validator name="digits"/>
  <aui:validator name="digits"/>
      <aui:validator  name="custom"  errorMessage="cannot be more than transacted amount." >
	function (val, fieldNode, ruleValue) {
	var result=false;
	 var remittedAmmount=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTED_AMOUNT%>").val();
	 if(Number(val)<= Number(remittedAmmount)){
	 result=true;
	 }
	return result;
	}
</aui:validator> 
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
			 <aui:input type="text" name="<%=ErmFieldName.CASE_NO %>" id="<%=ErmFieldName.CASE_NO %>" label="" value="<%=ermInformation.getCaseNo() %>">
			  <aui:validator name="required" />
			 <aui:validator name="alphanum"/>
			</aui:input>
			
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
             <aui:input type="text" name="<%=ErmFieldName.SUBSCRIBER_NAME %>" label="" value="<%=ermInformation.getSubscriberName() %>">
              <aui:validator name="required" />
			</aui:input>
			
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
			  <aui:input type="text" name="<%=ErmFieldName.ENPS_ORDER_ID %>" label="" value="<%=ermInformation.getEnpsOrderId() %>">
			   <aui:validator name="required" />
			 <aui:validator name="alphanum"/>
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
            <aui:select name="<%=ErmFieldName.RECTIFICATION_TYPE %>" id="<%=ErmFieldName.RECTIFICATION_TYPE %>" label="" required="true" value="<%=ermInformation.getRectificationType() %>"> 
            <aui:option value="">Select</aui:option>
             <%for(Entry<String,String> entry:ErmConstantValues.TYPE_OF_RECTIFICATION_MAP.entrySet()){
                        	 boolean isSelected = false;
							if ((ermInformation.getRectificationType() == entry.getKey())|| (ermInformation.getRectificationType().equals(entry.getKey()))){
							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>

            </aui:select>
			 </td>
		</tr>
		<tr>
			 <td>11</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_AMOUNT %></td>
             <td class="tbdata">
            <aui:input type="text" name="<%=ErmFieldName.AUTO_RECTIFICATION_AMOUNT %>" id="<%=ErmFieldName.AUTO_RECTIFICATION_AMOUNT %>" label="" readonly="true" value="<%=ermInformation.getRectificationAmount() %>">
            

       <aui:validator  name="custom"  errorMessage="This cannot be more than 8 amount transacted" >
	function (val, fieldNode, ruleValue) {
	var result=false;
	 var remittedAmmount=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTED_AMOUNT%>").val();
	 if(Number(val)<= Number(remittedAmmount)){
	 result=true;
	 }
	return result;
	}
</aui:validator>
            </aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>12</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_REQUEST_MODE %></td>
             <td class="tbdata">
            <aui:input type="text" name="<%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE %>" id="<%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE %>" label=""  value="<%=ermInformation.getRectificationRequestMode() %>"></aui:input>
			
			 </td>
		</tr>
			<tr>
			 <td>13</td>
			 <td  class="tbdata"><%=ErmFieldLabel.TABLE_VIEW_RECTIFICATION_REQUEST_DATE %></td>
             <td class="tbdata">
            <aui:input type="date" name="<%=ErmFieldName.RECTIFICATION_REQUEST_DATE %>" id="<%=ErmFieldName.RECTIFICATION_REQUEST_DATE %>" label="" value="<%=ermInformation.getRectificationRequestDate() != null ? simpleDateFormat.format(ermInformation.getRectificationRequestDate()) : null%>" >
             <aui:validator name="required" />
              <aui:validator  name="custom"  errorMessage="cannot be before transaction date." >
	function (val, fieldNode, ruleValue) {
		    var transaction_date=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_DATE%>").val();
	  
	var result = false;
	var date1 = new Date(val).getTime();
	var date2 = new Date(transaction_date).getTime();
	console.log("good");
	if (date2 <= date1) {
	result = true;
	}
	return result;
	
	<!-- return dateValidate(val); -->
	}
</aui:validator>
<aui:validator  name="custom"  errorMessage="<%=NpstErmConstant.DATE_ERROR_MESSAGE %>" >
	function (val, fieldNode, ruleValue) {
	return dateValidate(val);
	}
</aui:validator>
            </aui:input>
			
			 </td>
		</tr>	
		</tbody>
</table>
<h1>Section C: Details for Reference</h1>

a)	Grievance received by CRA in CGMS / mail on (date):<div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.GRIEVANCE_RECEIVED_DATE %>" label="" value="<%=ermInformation.getGrievanceReceivedDate() != null ? simpleDateFormat.format(ermInformation.getGrievanceReceivedDate()) : null%>"  >
 <aui:validator name="required" />
   <aui:validator  name="custom"  errorMessage="<%=NpstErmConstant.DATE_ERROR_MESSAGE %>" >
	function (val, fieldNode, ruleValue) {
	return dateValidate(val);
	}
</aui:validator>

</aui:input></div>
b)	Grievance Text raised by Subscriber :<div style=" width: 60rem; display: inline-block;"><aui:input type="textarea" name="<%=ErmFieldName.GRIEVANCE_TEXT %>"  label="" value="<%=ermInformation.getGrievanceText() %>">
 <aui:validator name="required" />
</aui:input></div>

<div>

<div class="row">
<div class="col-md-6 col-lg-6 col-sm-6">
<aui:input type="file" name="<%=ErmFieldName.SELF_DECLARATION_FILE %>" label="<%=ErmFieldLabel.SELF_DECLARATION_FILE %>" required="<%=ermInformation.getSelfDeclarationFileId()>0 ? false : true %>" >
<aui:validator name="acceptFiles">'pdf,doc,docx'</aui:validator>
</aui:input>
<%if(ermInformation.getSelfDeclarationFileId()>0){ %>
 <a class="btn" target="_blank" style="color:blue;" href="<%=NpstCommonUtil.getFileDownloadURL(ermInformation.getSelfDeclarationFileId(), themeDisplay)%>">Download <%=ErmFieldLabel.SELF_DECLARATION_FILE %> File</a>
<%} %>
</div>
<div class="col-md-6 col-lg-6 col-sm-6">
<aui:input type="file" name="<%=ErmFieldName.ACCOUNT_STATEMENT_FILE %>" label="<%=ErmFieldLabel.ACCOUNT_STATEMENT_FILE %>" required="<%=ermInformation.getAccountStatementFileId()>0?false:true %>" >
<aui:validator name="acceptFiles">'pdf,doc,docx'</aui:validator>
</aui:input>
<%if(ermInformation.getAccountStatementFileId()>0){ %>
 <a class="btn" target="_blank" style="color:blue;" href="<%=NpstCommonUtil.getFileDownloadURL(ermInformation.getAccountStatementFileId(), themeDisplay)%>"><%=ErmFieldLabel.ACCOUNT_STATEMENT_FILE %></a>
<%} %>
</div>
</div>

<div class="row">
<div class="col-md-6 col-lg-6 col-sm-6">
<aui:input type="file" name="<%=ErmFieldName.TRANSACTIONS_STATEMENT_FILE %>" label="<%=ErmFieldLabel.TRANSACTIONS_STATEMENT_FILE %>" required="<%=ermInformation.getTransactionsStatementFileId()>0?false:true %>">
<aui:validator name="acceptFiles">'pdf,doc,docx'</aui:validator>
</aui:input>
<%if(ermInformation.getTransactionsStatementFileId()>0){ %>
 <a target="_blank" class="btn" style="color:blue;" href="<%=NpstCommonUtil.getFileDownloadURL(ermInformation.getTransactionsStatementFileId(), themeDisplay)%>"><%=ErmFieldLabel.TRANSACTIONS_STATEMENT_FILE %></a>
<%} %>
</div>
<div class="col-md-6 col-lg-6 col-sm-6">

<aui:input type="file" name="<%=ErmFieldName.DOCUMENT_FILE %>" label="<%=ErmFieldLabel.DOCUMENT_FILE %>">
<aui:validator name="acceptFiles">'pdf,doc,docx'</aui:validator>
</aui:input>
<%if(ermInformation.getDocumentNameFileId()>0){ %>
 <a target="_blank" class="btn" style="color:blue;" href="<%=NpstCommonUtil.getFileDownloadURL(ermInformation.getDocumentNameFileId(), themeDisplay)%>"><%=ErmFieldLabel.DOCUMENT_FILE %></a>
<%} %>
</div>

</div>
</div>

 <aui:button id="submit" class="common-btn d-inline-block center" name="Submit" type="submit" value="Submit"></aui:button>

</aui:form>





<script>
function dateValidate(val){
	var result = false;
	var date1 = new Date(val).getTime();
	var date2 = new Date().getTime();
	console.log("good");
	if (date2 >date1) {
	result = true;
	}
	return result;
}


$(document).ready(function(){
  $("#<portlet:namespace /><%=ErmFieldName.PRAN%>").change(function(){
    var pran=$("#<portlet:namespace /><%=ErmFieldName.PRAN%>").val();
    $("#<portlet:namespace /><%=ErmFieldName.AUTO_PRAN%>").val(pran);
  });
  
  
  $("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_MODE%>").change(function(){
	    var transaction_mode=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_MODE%>").val();
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_TRANSACTION_MODE%>").val(transaction_mode);
	  });
  
  $("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_DATE%>").change(function(){
	    var transaction_date=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_DATE%>").val();
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_TRANSACTION_DATE%>").val(transaction_date);
	    $("#<portlet:namespace /><%=ErmFieldName.REMITTED_DATE%>").val(transaction_date);
	    
	    
	  });
  
  $("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_DATE%>").change(function(){
	    var rectification_date=$("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_DATE%>").val();
	    $("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_REQUEST_DATE%>").val(rectification_date);
	    $("#<portlet:namespace /><%=ErmFieldName.GRIEVANCE_RECEIVED_DATE%>").val(rectification_date);
	  });
  
  $("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_SETTLEMENT_DATE%>").change(function(){
	    var transaction_settlement_date=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_SETTLEMENT_DATE%>").val();
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_TRANSACTION_SETTLEMENT_DATE%>").val(transaction_settlement_date);
	  });
  
  $("#<portlet:namespace /><%=ErmFieldName.TRANSACTED_AMOUNT%>").change(function(){
	    var transacted_amt=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTED_AMOUNT%>").val();
	    var transacted_ammount=Number(transacted_amt);
	    if(Number(transacted_ammount)){
	    $("#<portlet:namespace /><%=ErmFieldName.TRANSACTED_AMOUNT%>").val(transacted_ammount);
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_TRANSACTED_AMOUNT%>").val(transacted_ammount);
	    $("#<portlet:namespace /><%=ErmFieldName.REMITTED_AMOUNT%>").val(transacted_ammount);
	    
	    
	    }
	  });
  
  $("#<portlet:namespace /><%=ErmFieldName.REMITTED_AMOUNT%>").change(function(){
	    var remittedAmt=$("#<portlet:namespace /><%=ErmFieldName.REMITTED_AMOUNT%>").val();
	    
	    var remittedAmmount=Number(remittedAmt);
	    if(Number(remittedAmmount)){
	    $("#<portlet:namespace /><%=ErmFieldName.REMITTED_AMOUNT%>").val(remittedAmmount);
	   
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_TRANSFER_AMOUNT%>").val(remittedAmmount);
	    }
	  });
  
  $("#<portlet:namespace /><%=ErmFieldName.TIER_TYPE%>").change(function(){
	    var tier_type=$("#<portlet:namespace /><%=ErmFieldName.TIER_TYPE%>").val();
	    <%-- $("#<portlet:namespace /><%=ErmFieldName.AUTO_TIER_TYPE%>").val(tier_type); --%>
	  });
  
  $("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_AMOUNT%>").change(function(){
	    var rectified_amt=$("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_AMOUNT%>").val();
	    
	    var rectified_ammount=Number(rectified_amt);
	    if(Number(rectified_ammount)){
	    $("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_AMOUNT%>").val(rectified_ammount);
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_AMOUNT%>").val(rectified_ammount);
	    }
	  });
  
<%--   $("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_REQUEST_MODE%>").change(function(){
	    var rectified_request_mode=$("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_REQUEST_MODE%>").val();
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val(rectified_request_mode);
	  }); --%> 
  
  $("#<portlet:namespace /><%=ErmFieldName.TRANSFER_AMOUNT%>").change(function(){
	    var transfer_amt=$("#<portlet:namespace /><%=ErmFieldName.TRANSFER_AMOUNT%>").val();
	    var transfer_amount=Number(transfer_amt);
	    if(Number(transfer_amount)){
	    $("#<portlet:namespace /><%=ErmFieldName.TRANSFER_AMOUNT%>").val(transfer_amount);
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_TRANSFER_AMOUNT%>").val(transfer_amount);
	    $("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_AMOUNT%>").val(transfer_amount);
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_AMOUNT%>").val(transfer_amount); 
	    }
  }); 
  
  $("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_TYPE%>").change(function(){
	    var rectification_type=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_TYPE%>").val();
	    <%-- $("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_TYPE%>").val(rectification_type); --%>
	  }); 
 
});

$("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_REQUEST_MODE%>").change(function(){
	var rectified_request_mode=$("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_REQUEST_MODE%>").val();
	var amt=rectified_request_mode;
	
	console.log(amt);
	 
	 var tokenLabel ="<%=ErmFieldLabel.TOKEN_NO%>";
	 var emailLabel ="<%=ErmFieldLabel.Email%>";

	 if(tokenLabel==amt){
		 $("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("CGMS"); 
		
		 $("#tokenfileddiv").append('<input type="text" name="<portlet:namespace/><%=ErmFieldName.TOKEN_NO %>" id="<portlet:namespace/><%=ErmFieldName.TOKEN_NO %>" label="" value="<%=ermInformation.getTokenNo() %>" required="true"  class="form-control" placeholder="This is required." >');
		 
	 }
if(emailLabel==amt){
	$("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("Direct Mail-Letter from sub");	
	 $("#<portlet:namespace/><%=ErmFieldName.TOKEN_NO %>").remove();
	 }
if(amt=="Other"){
	$("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("Req from NPST-PFRDA"); 
	$("#<portlet:namespace/><%=ErmFieldName.TOKEN_NO %>").remove();
} 

}); 


$(document).ready(function(){
	 var transaction_mode1=$("#<portlet:namespace /><%=ErmFieldName.TRANSACTION_MODE%>").val();
	    $("#<portlet:namespace /><%=ErmFieldName.AUTO_TRANSACTION_MODE%>").val(transaction_mode1);
	    
	    var amt=$("#<portlet:namespace /><%=ErmFieldName.RECTIFICATION_REQUEST_MODE%>").val();
		 
		 var tokenLabel ="<%=ErmFieldLabel.TOKEN_NO%>";
		 var emailLabel ="<%=ErmFieldLabel.Email%>";

		 if(tokenLabel==amt){
			 $("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("CGMS"); 
			 $("#tokenfileddiv").append('<input type="text" name="<portlet:namespace/><%=ErmFieldName.TOKEN_NO %>" id="<portlet:namespace/><%=ErmFieldName.TOKEN_NO %>" label="" value="<%=ermInformation.getTokenNo() %>" required="true"  class="form-control" placeholder="This is required." >');
		 }
	if(emailLabel==amt){
		$("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("Direct Mail-Letter from sub");	 
		 }
	if(amt=="Other"){
		$("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val("Req from NPST-PFRDA"); 
	}
	
	
	
	   <%--  $("#<portlet:namespace /><%=ErmFieldName.AUTO_RECTIFICATION_REQUEST_MODE%>").val(rectified_request_mode1); --%>
	    
	    var tier_type1=$("#<portlet:namespace /><%=ErmFieldName.TIER_TYPE%>").val();
	   <%--  $("#<portlet:namespace /><%=ErmFieldName.AUTO_TIER_TYPE%>").val(tier_type1); --%>
	    
	    
});


document.getElementById('<portlet:namespace /><%=ErmFieldName.TRANSFER_AMOUNT %>').addEventListener('keydown', function(e) {
    if (e.which === 38 || e.which === 40 || e.which === 107 || e.which === 109 || e.which === 187 || e.which === 189) {
        e.preventDefault();
    }
});

document.getElementById('<portlet:namespace /><%=ErmFieldName.RECTIFICATION_AMOUNT %>').addEventListener('keydown', function(e) {
	 if (e.which === 38 || e.which === 40 || e.which === 107 || e.which === 109 || e.which === 187 || e.which === 189) {
        e.preventDefault();
    }
});

function removeLeadingZerosRegex(val,id) { 
	let withZeroValue=c.val;
	var withoutZeroValue =Number(withZeroValue);
	   document.getElementById(id).value = withoutZeroValue;
	}

</script>

<style>
/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Firefox */
input[type=number] {
  -moz-appearance: textfield;
}
</style>

