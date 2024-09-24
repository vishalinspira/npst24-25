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
 long ermInfoId=ParamUtil.getLong(request, ErmFieldName.ERM_INFORMATION_ID);
 ErmInformation ermInformation=null;
 
 System.out.println("ermInfoId  :  "+ermInfoId);
 if(ermInfoId<=0){
	 ermInformation=ErmInformationLocalServiceUtil.createErmInformation(0);
 }else{
	 ermInformation=ErmInformationLocalServiceUtil.getErmInformation(ermInfoId);
 }

 %>
 
 <portlet:actionURL name="saveErmInformation" var="saveErmURL" />
  
  <aui:form action="<%=saveErmURL %>" method="POST" >
<h1>Section A: Case information</h1>
 <div>
 <aui:input type="hidden" name="<%=ErmFieldName.ERM_INFORMATION_ID %>" label="" value="<%=ermInformation.getErmInformationId() %>" > 
 <aui:input label="Old" name="<%=ErmFieldName.BATCH_TYPE %>" type="radio" value="" checked="true" />
<aui:input label="New" name="<%=ErmFieldName.BATCH_TYPE %>" type="radio" value="" />
 </aui:input>
 a)	The Subscriber made a contribution of Rs.  <div style=" width: 12rem; display: inline-block;">
  <aui:input type="text" name="<%=ErmFieldName.TRANSACTED_AMOUNT %>" label="" > 
  <aui:validator name="digits"/> 
 </aui:input>
</div> 
   in his PRAN <div style=" width: 12rem; display: inline-block;"> 
   <aui:input type="text" name="<%=ErmFieldName.PRAN %>" label="" value="<%= ermInformation.getPran()%>" >
 <aui:validator name="digits"/>
  <aui:validator errorMessage="Please enter 12 digits PRAN number" name="rangeLength">[12,12]</aui:validator>
  </aui:input>
  </div> on <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.TRANSACTION_DATE %>" ></aui:input> </div>
  through      <div style=" width: 12rem; display: inline-block;">
 <aui:select name="<%=ErmFieldName.TRANSACTION_MODE %>" label="">
  <aui:option value="e-NPS portal">e-NPS portal</aui:option>
  <aui:option value="D-Remit">D-Remit</aui:option>
</aui:select>
</div>
</div>
<div>

b)	The contribution was settled in CRA system i.e., units got credited in the said PRAN on
  <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.TRANSACTION_SETTLEMENT_DATE %>" label="" ></aui:input> </div>
  </div>

c)	The subscriber has raised a grievance request in CRA grievance system Token No.
<div style=" width: 12rem; display: inline-block;">
 <aui:input type="text" name="<%=ErmFieldName.TOKEN_NO %>" label="">
 <aui:validator name="digits"/>
 </aui:input>
 </div>
 Email

 <div style=" width: 12rem; display: inline-block;">
 <aui:select name="<%=ErmFieldName.RECTIFICATION_REQUEST_MODE %>" label="">
  <aui:option value="Email">Email</aui:option>
  <aui:option value="Other">Other</aui:option>
</aui:select>
</div>
   dated <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.RECTIFICATION_DATE %>" label=""/></div>  through for rectification of erroneous contribution stating that he mistakenly remitted
     Rs. 
     <div style=" width: 12rem; display: inline-block;"> 
      <aui:input type="text" name="<%=ErmFieldName.REMITTED_AMOUNT %>" label="">
 <aui:validator name="digits"/>
 </aui:input> 
 </div>
    on <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.REMITTED_DATE %>" label=""/> </div>
    in <div style=" width: 12rem; display: inline-block;">
      <aui:select name="<%=ErmFieldName.TIER_TYPE %>" label="">
  <aui:option value="Tier1">Tier1</aui:option>
  <aui:option value="Tier2">Tier2</aui:option>
</aui:select>
</div>
   Hence subscriber has requested to
   <div style=" width: 12rem; display: inline-block;">
      <aui:select name="<%=ErmFieldName.TRANSACTION_TYPE %>" label="">
  <aui:option value="Refund">Refund</aui:option>
  <aui:option value="Transfer">Transfer</aui:option>
</aui:select>
</div>
 the amount of    Rs. 
 <div style=" width: 12rem; display: inline-block;"> 
  <aui:input type="text" name="<%=ErmFieldName.RECTIFICATION_REQUEST_DATE %>" label="" >
 <aui:validator name="digits"/>
 </aui:input> 
</div>
<div>
d)	The subscriber has provided the complete documentations on <div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="<%=ErmFieldName.DOCUMENTATIONS_DATE %>" label=""/> </div>
</div>
<div>
e)	As per the request, error rectification needs to be carried out for Rs 
<div style=" width: 12rem; display: inline-block;"> 
 <aui:input type="text" name="<%=ErmFieldName.RECTIFICATION_AMOUNT %>" label="" >
 <aui:validator name="digits"/>
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
			 <td  class="tbdata">Case No </td>
			 <td class="tbdata">
			 <aui:input type="text" name="caseNo" label="">
			 <aui:validator name="alphanum"/>
			</aui:input>
			
			 </td>
		</tr>	
		<tr>
			 <td>2</td>
			 <td  class="tbdata">PRAN </td>
			  <td class="tbdata">
             <aui:input type="text" name="pran" id="pran" readonly="true" label=""></aui:input>
			 </td>
		</tr>		
			<tr>
			 <td>3</td>
			 <td  class="tbdata">Name of the subscriber </td>
			 <td class="tbdata">
             <aui:input type="text" name="subscriberName" label="">
			 <aui:validator name="alpha"/>
			</aui:input>
			
			 </td>
		</tr>	
			<tr>
			 <td>4</td>
			 <td  class="tbdata">Transaction Mode (eNPS/D-Remit) </td>
			  <td class="tbdata">
             <aui:input type="text" name="transMode" id="transMode" label="" readonly="true"></aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>5</td>
			 <td  class="tbdata">Transaction Date</td>
             <td class="tbdata">
            <aui:input type="text" name="transactionDate" id="transactionDate" label="" readonly="true"></aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>6</td>
			 <td  class="tbdata">eNPS Order ID/Ref NO (ACK_ID)</td>
			  <td class="tbdata">
			  <aui:input type="text" name="ENPS_ORDER_ID" label="">
			 <aui:validator name="alphanum"/>
			</aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>7</td>
			 <td  class="tbdata">Transaction Settlement Date on CRA</td>
             <td class="tbdata">
            <aui:input type="text" name="transactionSettlementDate" id="transactionSettlementDate" label="" readonly="true"></aui:input>
			
			 </td>
		</tr>
			<tr>
			 <td>8</td>
			 <td  class="tbdata">Amount Transacted in Rs.</td>
             <td class="tbdata">
            <aui:input type="text" name="transactedAmount" id="transactedAmount" label=""></aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>9</td>
			 <td  class="tbdata">Tier Type (Tier I / Tier II)</td>
             <td class="tbdata">
            <aui:input type="text" name="tierType" id="tierType" label="" readonly="true"></aui:input>
			
			 </td>
		</tr>
			<tr>
			 <td>10</td>
			 <td  class="tbdata">Type of Rectification</td>
             <td class="tbdata">
            <aui:select name="rectificationType" id="rectificationType" label=""> 
            <aui:option value="Type1">Type1</aui:option>
            <aui:option value="Type2">Type2</aui:option>
            </aui:select>
			 </td>
		</tr>
		<tr>
			 <td>11</td>
			 <td  class="tbdata">Amount required to be rectified in Rs.</td>
             <td class="tbdata">
            <aui:input type="text" name="rectificationAmount" id="rectificationAmount" label="" readonly="true" ></aui:input>
			
			 </td>
		</tr>
		<tr>
			 <td>12</td>
			 <td  class="tbdata">Rectification Request Mode (CGMS/Direct Mail-Letter from sub/Req from NPST-PFRDA)</td>
             <td class="tbdata">
            <aui:input type="text" name="rectificationRequestMode" id="rectificationRequestMode" label="" readonly="true"></aui:input>
			
			 </td>
		</tr>
			<tr>
			 <td>13</td>
			 <td  class="tbdata">Rectification Request Received Date by CRA/NPST (DD-MM-YYYY)</td>
             <td class="tbdata">
            <aui:input type="text" name="rectificationRequestDate" id="rectificationRequestDate" label="" ></aui:input>
			
			 </td>
		</tr>	
		</tbody>
</table>
<h1>Section C: Details for Reference</h1>

a)	Grievance received by CRA in CGMS / mail on (date):<div style=" width: 12rem; display: inline-block;"><aui:input type="date" name="rectificationRequestMode" label=""/></div>
b)	Grievance Text raised by Subscriber :<div style=" width: 12rem; display: inline-block;"><aui:input type="text" name="rectificationRequestMode" id="rectificationRequestMode" label=""/></div>

<div>

<div class="row">
<div class="col-md-6 col-lg-6 col-sm-6">
<aui:input type="file" name="name" ></aui:input>
</div>
<div class="col-md-6 col-lg-6 col-sm-6">
<aui:input type="file" name="name" ></aui:input>
</div>
</div>

<div class="row">
<div class="col-md-6 col-lg-6 col-sm-6">
<aui:input type="file" name="name" ></aui:input>
</div>
<div class="col-md-6 col-lg-6 col-sm-6">
<aui:input type="file" name="name" ></aui:input>
</div>

</div>
</div>

 <aui:button id="submit" class="common-btn d-inline-block center" name="Submit" type="submit" value="Save"></aui:button>

</aui:form>
