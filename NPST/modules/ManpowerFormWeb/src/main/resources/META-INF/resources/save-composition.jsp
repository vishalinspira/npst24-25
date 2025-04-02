
  <%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.ManpowerEmployee"%>
<%@page import="npst.common.constant.NpstCommonConstant"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.nps.manpower.constants.DropdownValues"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.nps.manpower.constants.ManpowerStatusConstant"%>
<%@page import="com.nps.manpower.service.CompositionCommitteeLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.CompositionCommittee"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldName"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.manpower.constants.ManpowerCompositionCommitteeFieldLabel"%>
<%@page import="com.nps.manpower.constants.ManpowerCompositionCommitteeFieldName"%>
<%@ include file="/init.jsp" %>
 					
 <portlet:actionURL name="saveCompositionCommittee" var="saveCompositionCommitteeURL" />

<%
SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NpstCommonConstant.DATE_FORMAT);
	 long manpowerEmployeeId=ParamUtil.getLong(request,ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
	String empId=(String)request.getAttribute(ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
	CompositionCommittee compositionCommittee=null;
	ManpowerEmployee manpowerEmployee=null;
	try{
		if(manpowerEmployeeId<=0 &&(empId!=null && !empId.equals(null))){
			manpowerEmployeeId=Long.parseLong(empId);
			}
		manpowerEmployee=ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
		compositionCommittee=CompositionCommitteeLocalServiceUtil.getAllByManpowerAndStatus(manpowerEmployeeId, Integer.parseInt(ManpowerStatusConstant.ACTIVE_EMPLOYEE)).get(0);
	
	}catch(Exception e){
		
	}

	if(compositionCommittee==null || compositionCommittee.equals(null)){
		compositionCommittee=CompositionCommitteeLocalServiceUtil.createCompositionCommittee(0);
		compositionCommittee.setManpowerEmployeeId(manpowerEmployeeId);
		
	}
%>
 					
 			<div class="row">
 
               <div class="container">
                  <div class="row formrow">
                <!--   <h4>Composition Form </h4> -->
                     <aui:form id="compositionForm" action="<%=saveCompositionCommitteeURL %>" method="POST" >
                     	<aui:input type="hidden"   name="<%=ManpowerCompositionCommitteeFieldName.MANPOWER_EMPLOYEE_ID%>" value="<%=compositionCommittee.getManpowerEmployeeId() %>"/>
                      	<aui:input type="hidden"   name="<%=ManpowerCompositionCommitteeFieldName.MANPOWER_COMPOSITION_COMMITTEE_ID%>" value="<%=compositionCommittee.getCompositionCommitteeId() %>"/>
 					<div class="row">
 					 <div class="col-md-6 col-lg-6 col-sm-6">
                   		<aui:select label="<%=ManpowerCompositionCommitteeFieldLabel.COMMITTEE_MEMBERSHIP_TYPE %>" name="<%=ManpowerCompositionCommitteeFieldName.COMMITTEE_MEMBERSHIP_TYPE %>" >
  							
  						<%for(Entry<Integer,String> entry:DropdownValues.COMMITTEE_MEMBERSHIP_TYPE_MAP.entrySet()){ 
  							 boolean isSelected = false;
  							if ((compositionCommittee.getCommitteeMemberShipType() == entry.getKey())){
  	  							isSelected = true;}
  						%>
                       
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
						</aui:select>
                        
                        </div>
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <%--  <aui:input type="text" label="<%=ManpowerCompositionCommitteeFieldLabel.DESIGNATION%>" name="<%=ManpowerCompositionCommitteeFieldName.DESIGNATION%>" placeholder="" value="<%=manpowerEmployee.getDesignation() %>"/> --%>
                       
                        <aui:select label="<%=ManpowerCompositionCommitteeFieldLabel.DESIGNATION %>" name="<%=ManpowerCompositionCommitteeFieldName.DESIGNATION %>" required="true" >
                         <%for(Entry<String,String> entry:DropdownValues.COMMITTEE_DESIGNATION_MAP.entrySet()){ 
                        	 boolean isSelected = false;
   							if ((compositionCommittee.getCommitteeDesignation() == entry.getKey() || compositionCommittee.getCommitteeDesignation().equals(entry.getKey()))){
   	  							isSelected = true;}
                         %>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
						</aui:select> 
                        </div>

                        </div>
                         <div class="row">
                          <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="text" label="<%=ManpowerCompositionCommitteeFieldLabel.NAME%>" name="<%=ManpowerCompositionCommitteeFieldName.NAME%>" placeholder="" value="<%=HtmlUtil.escape(manpowerEmployee.getName()) %>" required="true" readOnly="true">
                      
                          <aui:validator name="required"/>
                        </aui:input>
                        </div>
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="text" label="<%=ManpowerCompositionCommitteeFieldLabel.EMAIL%>" name="<%=ManpowerCompositionCommitteeFieldName.EMAIL%>" placeholder="" value="<%=HtmlUtil.escape(manpowerEmployee.getEmail()) %>" required="true" readOnly="true">
                        <aui:validator name="email"/>
                    
                        </aui:input>
                        </div>
                        
                        </div>
						<div class="row">
                        <div class="col-md-6 col-lg-6 col-sm-6">
                   		 <aui:input type="date" label="<%=ManpowerCompositionCommitteeFieldLabel.COMMITTEE_APPOINTMENT_DATE%>" name="<%=ManpowerCompositionCommitteeFieldName.COMMITTEE_APPOINTMENT_DATE%>" placeholder="" value="<%=compositionCommittee.getCommitteeAppointmentDate() != null
							? simpleDateFormat.format(compositionCommittee.getCommitteeAppointmentDate()) : null%>" required="true"/>
                        
                        </div>
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                          <aui:select label="<%=ManpowerCompositionCommitteeFieldLabel.MEMBERSHIP_TYPE%>" name="<%=ManpowerCompositionCommitteeFieldName.MEMBERSHIP_TYPE%>" required="true" >
                           <%for(Entry<String,String> entry:DropdownValues.MEMBERSHIP_TYPE_MAP.entrySet()){ 
                        	   boolean isSelected = false;
      							if ((compositionCommittee.getMembershipType() == entry.getKey() || compositionCommittee.getMembershipType().equals(entry.getKey()))){
      	  							isSelected = true;}
                           
                           %>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
						</aui:select>
                        </div>
                        </div>
                 <%--    <div class="row">
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="date"  label="<%=ManpowerCompositionCommitteeFieldLabel.FROM_DATE%>" name="<%=ManpowerCompositionCommitteeFieldName.FROM_DATE%>" placeholder="" value="<%=compositionCommittee.getFromDate() %>"/>
                        </div>
                        <div class="col-md-6 col-lg-6 col-sm-6">
                        
                        <aui:input type="date"  label="<%=ManpowerCompositionCommitteeFieldLabel.TO_DATE%>" name="<%=ManpowerCompositionCommitteeFieldName.TO_DATE %>" placeholder="" value="<%=compositionCommittee.getToDate() %>"/>
                        </div>
                        </div>
                    --%>      
                         <div class="nps-input-box">
                         <%if(hasPermission){ %>
                           <aui:button id="submit" class="common-btn d-inline-block center" name="Submit" type="submit" value="Save"></aui:button>
                       <%} %>
                        </div>
                     </aui:form>
                  </div>
               </div>
            </div>