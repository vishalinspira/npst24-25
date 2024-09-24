<%@page import="com.liferay.petra.string.StringPool"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.nps.manpower.constants.DropdownValues"%>
<%@page import="com.nps.manpower.model.ManpowerEmployee"%>
<%@page import="com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldLabel"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldName"%>
<%@ include file="/init.jsp" %>
<portlet:actionURL name="saveManpowerEmployee" var="saveManpowerEmployeeURL" />
<portlet:actionURL name="saveManpowerDirector" var="saveManpowerDirectorURL" />
<%
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
int isDirector=ParamUtil.getInteger(request, ManpowerEmployeeFieldName.IS_DIRECTOR);
String year="0";
String month="0";
System.out.println("isDirector"+isDirector);
String director=(String)request.getAttribute(ManpowerEmployeeFieldName.IS_DIRECTOR);
long manpowerEmployeeId=ParamUtil.getLong(request,ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
String empId=(String)request.getAttribute(ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);

try{
	if(isDirector==0 &&(director!=null && !director.equals(null))){
		isDirector=Integer.parseInt(director);
	}
	if(manpowerEmployeeId<=0 &&(empId!=null && !empId.equals(null))){
		manpowerEmployeeId=Long.parseLong(empId);
	}
}catch(Exception e){
	
}
ManpowerEmployee manpowerEmployee=null;
if(manpowerEmployeeId>0){
	manpowerEmployee=ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
}else{
	manpowerEmployee=ManpowerEmployeeLocalServiceUtil.createManpowerEmployee(0);
	manpowerEmployee.setIsDirector(isDirector);
}
try{
	String exp[]=manpowerEmployee.getExperience().split(StringPool.COLON);
	year=exp[0];
	month=exp[1];
}catch(Exception ee){}
%>
	
            <div class="row" id="directorDiv">
 
               <div class=" container">
                  <div class="row formrow">
                 <!--  <h4>Manpower Form </h4> -->

                         <aui:form id="director" name="director" action="<%=saveManpowerDirectorURL%>" method="POST" >
                         <aui:input type="hidden" name="<%=ManpowerEmployeeFieldName.IS_DIRECTOR%>" value="<%=manpowerEmployee.getIsDirector()%>"/>
                        <aui:input type="hidden" name="<%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>" value="<%=manpowerEmployee.getManpowerEmployeeId()%>"/>
                        
                        <div class="row">
                        <div class="col-md-4 col-lg-4 col-sm-4">
                        <aui:input label="<%=ManpowerEmployeeFieldLabel.NAME%>" type="text" name="<%=ManpowerEmployeeFieldName.NAME%>" placeholder="" value="<%=manpowerEmployee.getName()%>" required="true">
                        <aui:validator name="required"/> 
                        </aui:input>
               
                        </div>
                        
                         <div class="col-md-4 col-lg-4 col-sm-4">
                        <aui:input type="text" label="<%=ManpowerEmployeeFieldLabel.EMAIL %>" name="<%=ManpowerEmployeeFieldName.EMAIL %>" placeholder="" value="<%=manpowerEmployee.getEmail() %>" required="true">
                        <aui:validator name="email"/>
                        </aui:input> 
                        </div>
                        
                        <div class="col-md-4 col-lg-4 col-sm-4">
                        <aui:select label="<%=ManpowerEmployeeFieldLabel.DIRECTOR_TYPE%>" name="<%=ManpowerEmployeeFieldName.DIRECTOR_TYPE%>" required="true">
                         <%for(Entry<String,String> entry:DropdownValues.DIRECTOR_TYPE_MAP.entrySet()){ 
                        	 boolean isSelected = false;
  							if ((manpowerEmployee.getDirectorType() == entry.getKey())|| (manpowerEmployee.getDirectorType().equals(entry.getKey()))){
  							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
						</aui:select>
                        
                        </div>
                       
                        </div>
                        
                        <div class="row">
                         <div class="col-md-4 col-lg-4 col-sm-4">
                        <aui:select label="<%=ManpowerEmployeeFieldLabel.MEMBERSHIP_TYPE%>" name="<%=ManpowerEmployeeFieldName.MEMBERSHIP_TYPE%>" required="true">
                         <%for(Entry<String,String> entry:DropdownValues.MEMBERSHIP_TYPE_MAP.entrySet()){ 
                        	 boolean isSelected = false;
 							if ((manpowerEmployee.getMembershipType() == entry.getKey())|| (manpowerEmployee.getMembershipType().equals(entry.getKey()))){
 							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
						</aui:select>
                        
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-4">
                        <aui:select label="<%=ManpowerEmployeeFieldLabel.DEPENDENCY%>" name="<%=ManpowerEmployeeFieldName.DEPENDENCY%>" required="true">
                         <%for(Entry<String,String> entry:DropdownValues.DEPENDENCY_MAP.entrySet()){ 
                        	 boolean isSelected = false;
  							if ((manpowerEmployee.getDependency() == entry.getKey())|| (manpowerEmployee.getDependency().equals(entry.getKey()))){
  							isSelected = true;}%>
                        <aui:option value="<%=entry.getKey() %>" selected="<%=isSelected%>"><%=entry.getValue() %></aui:option>
                        <%} %>
						</aui:select>
                        
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-4">
                        
                        <aui:input type="text" label="<%=ManpowerEmployeeFieldLabel.DIN%>" name="<%=ManpowerEmployeeFieldName.DIN%>" placeholder="" value="<%=manpowerEmployee.getDin()%>" required="true">
                       <aui:validator errorMessage="Please enter 8 digits" name="rangeLength">[8,8]</aui:validator>
                       <aui:validator errorMessage="Please enter digit only" name="number"></aui:validator>
                        </aui:input>
                        </div>
                       
                        </div>
                        
                          <div class="row">
                           <div class="col-md-4 col-lg-4 col-sm-4">
                        
                         <aui:input label="<%=ManpowerEmployeeFieldLabel.APPOINTMENTDATE%>" type="date" name="<%=ManpowerEmployeeFieldName.APPOINTMENTDATE%>" placeholder="" required="true"
                         value="<%=manpowerEmployee.getAppointmentDate() != null
							? simpleDateFormat.format(manpowerEmployee.getAppointmentDate()) : null%>"/> 
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-4">
                        <aui:input type="text" label="<%=ManpowerEmployeeFieldLabel.QUALIFICATION%>" name="<%=ManpowerEmployeeFieldName.QUALIFICATION%>" placeholder="" value="<%=manpowerEmployee.getQualification()%>" required="true">
                        <aui:validator name="required"/>
                        </aui:input>
                        
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-4">
                        
                          <div class="row" style="margin-left: 0rem;">
                         <label><%=ManpowerEmployeeFieldName.EXPERIENCE %></label>
                         </div>
                           <div class="row" style="padding-left: 0.5rem;">
                           <div class="col-md-6 col-lg-6 col-sm-6 p-1">
                           <aui:select label=""  name="<%=ManpowerEmployeeFieldName.YEAR %>" required="true" showRequiredLabel="false" >
  							<aui:option value="">Year</aui:option>
  							 <% for(int i=0;i<=50;i++){ 
  						     boolean isSelected = false;
							if (year.equals(i+"") || year==i+""){
							isSelected = true;
							}%>  
                       		 <aui:option value="<%=i%>" selected="<%=isSelected%>"><%=i %></aui:option>
                        <%} %>
						</aui:select>
                           </div>
                           <div class="col-md-6 col-lg-6 col-sm-6 p-1">
                           <aui:select  label="" name="<%=ManpowerEmployeeFieldName.MONTH %>" required="true" showRequiredLabel="false">
  							<aui:option value="">Month</aui:option>
  							 <% for(int i=1;i<=12;i++){ 
  								 boolean isSelected = false;
  								if (month.equals(i+"") || month==i+""){ 				          
  								isSelected = true;
  								}%>  
                       		 <aui:option value="<%=i%>" selected="<%=isSelected%>"><%=i %></aui:option>
                        <%} %>
						</aui:select>
                           </div>
                         </div>
                        </div>
                        
                        </div>

                   		
						 <div class="row">
						  <div class="col-md-4 col-lg-4 col-sm-4">
                        
                        <aui:input type="file" label="<%=ManpowerEmployeeFieldLabel.BIODATA_FILE_ID%>" name="<%=ManpowerEmployeeFieldName.BIODATA_FILE_ID%>" placeholder="" value="<%=manpowerEmployee.getBiodataFileId()%>" required="<%=manpowerEmployee.getBiodataFileId()>0?false:true %>">
                        <aui:validator name="acceptFiles">'pdf'</aui:validator>
                        </aui:input>
                        </div>
                        <div class="col-md-4 col-lg-4 col-sm-4">
                         <aui:input type="file" label="<%=ManpowerEmployeeFieldLabel.FORM_MBP %>" name="<%=ManpowerEmployeeFieldName.FORM_MBP %>" placeholder="" value="<%=manpowerEmployee.getFormMbp() %>">
                          <aui:validator name="acceptFiles">'pdf'</aui:validator>
                         </aui:input>
                      	</div>
                        <div class="col-md-4 col-lg-4 col-sm-4">
                        <aui:select label="<%=ManpowerEmployeeFieldLabel.STATUS%>" name="<%=ManpowerEmployeeFieldName.STATUS%>" >
 							<%for(Entry<Integer,String> entry:DropdownValues.STATUS_MAP.entrySet()){ %>
                        <aui:option value="<%=entry.getKey() %>"><%=entry.getValue() %></aui:option>
                        <%} %>
						</aui:select>
                        
                        </div>
                    <%--<div class="col-md-4 col-lg-4 col-sm-4">
                        
                        <aui:input type="date" label="<%=ManpowerEmployeeFieldLabel.FROM_DATE%>" name="<%=ManpowerEmployeeFieldName.FROM_DATE%>" placeholder="" value="<%=manpowerEmployee.getFromDate()%>"/>
                        </div>
                        </div>
                         <div class="row">
                        
                        <div class="col-md-4 col-lg-4 col-sm-4">
                        
                        <aui:input type="date" label="<%=ManpowerEmployeeFieldLabel.TO_DATE%>" name="<%=ManpowerEmployeeFieldName.TO_DATE %>" placeholder="" value="<%=manpowerEmployee.getToDate() %>"/>
                        </div>
                        </div>
                     --%><div class="col-md-4 col-lg-4 col-sm-4"> 
                   <div class="nps-input-box text-center">
                           <aui:button id="submit" class="common-btn d-inline-block center" name="Submit" type="submit" value="save"></aui:button>
                    </div> 
                        </div>
                     </aui:form>
                    
                  </div>
               </div>
            </div>
            
            
           