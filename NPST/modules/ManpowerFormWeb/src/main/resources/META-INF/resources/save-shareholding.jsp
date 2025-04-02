 <%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.nps.manpower.constants.DropdownValues"%>
<%@page import="java.util.Map"%>
<%@page import="com.nps.manpower.service.ManpowerDirectorHoldingLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.ManpowerDirectorHolding"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.manpower.constants.ManpowerDirectorHoldingFieldLabel"%>
<%@page import="com.nps.manpower.constants.ManpowerDirectorHoldingFieldName"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldName"%>
<%@ include file="/init.jsp" %>

<portlet:actionURL name="saveShareHolding" var="saveShareHoldingURL" />

<%
	 long manpowerEmployeeId=ParamUtil.getLong(request,ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
String empId=(String)request.getAttribute(ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);

	System.out.println("jsp manpowerEmployeeId  "+manpowerEmployeeId);
	long  manpowerDirectorHoldingId=ParamUtil.getLong(request, ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID);
	String directorHoldingId=(String)request.getAttribute(ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID);
try{
	if(manpowerEmployeeId<=0 &&(empId!=null && !empId.equals(null))){
		manpowerEmployeeId=Long.parseLong(empId);
	}
	if(manpowerDirectorHoldingId<=0 &&(directorHoldingId!=null && !directorHoldingId.equals(null))){
		manpowerDirectorHoldingId=Long.parseLong(directorHoldingId);
	}
}catch(Exception e){
	
}
	ManpowerDirectorHolding manpowerDirectorHolding=null;
	
	if(manpowerDirectorHoldingId>0){
		manpowerDirectorHolding=ManpowerDirectorHoldingLocalServiceUtil.getManpowerDirectorHolding(manpowerDirectorHoldingId); 
	}else{
		manpowerDirectorHolding=ManpowerDirectorHoldingLocalServiceUtil.createManpowerDirectorHolding(0);
		manpowerDirectorHolding.setManpowerEmployeeId(manpowerEmployeeId);
	}
%>

<div class="row">
 
               <div class=" container">
                  <div class="row formrow">
                  <!-- <h4>Share Holding  Form </h4> -->
                     <aui:form id="shareHolding" action="<%=saveShareHoldingURL %>" method="POST" > 
                        <aui:input type="hidden" name="<%=ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID%>" value="<%=manpowerDirectorHolding.getManpowerDirectorHoldingId() %>"/>
                        <aui:input type="hidden" name="<%=ManpowerDirectorHoldingFieldName.MANPOWER_EMPLOYEE_ID%>" value="<%=manpowerDirectorHolding.getManpowerEmployeeId() %>"/>
                         <div class="row">
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="text" label="<%=ManpowerDirectorHoldingFieldLabel.COMPANY_NAME%>" name="<%=ManpowerDirectorHoldingFieldName.COMPANY_NAME%>" placeholder="" value="<%=HtmlUtil.escape(manpowerDirectorHolding.getCompanyName()) %>" required="true"/>
                        </div>
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="text" label="<%=ManpowerDirectorHoldingFieldLabel.CONCERN%>" name="<%=ManpowerDirectorHoldingFieldName.CONCERN%>" placeholder="" value="<%=HtmlUtil.escape(manpowerDirectorHolding.getConcern()) %>"  required="true"/>
                        </div>
                        </div>
                         <div class="row">
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="text"  label="<%=ManpowerDirectorHoldingFieldLabel.SHARE_HOLDING%>" name="<%=ManpowerDirectorHoldingFieldName.SHARE_HOLDING%>" placeholder="" value="<%=HtmlUtil.escape(manpowerDirectorHolding.getShareHolding()) %>" required="true"/>
                        </div>
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="date" label="<%=ManpowerDirectorHoldingFieldLabel.CONCERN_DATE%>" name="<%=ManpowerDirectorHoldingFieldName.CONCERN_DATE%>" placeholder="" value="<%=manpowerDirectorHolding.getConcernDate() %>" required="true"/>
                        </div>
                        </div>                   		
						 <div class="row">

                        <div class="col-md-6 col-lg-6 col-sm-6">
                        <aui:select label="<%=ManpowerDirectorHoldingFieldLabel.STATUS%>" name="<%=ManpowerDirectorHoldingFieldName.STATUS%>" required="true" >
                        <%for(Entry<Integer,String> entry:DropdownValues.STATUS_MAP.entrySet()){ 
                        	 boolean isSelected = false;
   							if ((manpowerDirectorHolding.getStatus() == entry.getKey() )){
   	  							isSelected = true;}
                        %>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
  					
						</aui:select>
                        
                        </div>
                        </div>
                      <%--  <div class="row">
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="date" label="<%=ManpowerDirectorHoldingFieldLabel.FROM_DATE%>" name="<%=ManpowerEmployeeFieldName.FROM_DATE%>" placeholder="" value="<%=manpowerDirectorHolding.getFromDate() %>"/>
                        </div>
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="date" label="<%=ManpowerDirectorHoldingFieldLabel.TO_DATE%>" name="<%=ManpowerEmployeeFieldName.TO_DATE %>" placeholder="" value="<%=manpowerDirectorHolding.getToDate() %>"/>
                        </div>
                        </div>
                     --%>  
                
                        <div class="nps-input-box">
                           <aui:button id="submit" class="common-btn d-inline-block center" name="Submit" type="submit" value="Save"></aui:button>
                        </div>
                     </aui:form>
                  </div>
               </div>
            </div>
</div> 



