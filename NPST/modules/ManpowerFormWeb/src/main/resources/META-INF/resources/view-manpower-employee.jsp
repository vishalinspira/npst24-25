
<%@page import="com.nps.manpower.constants.ManpowerHeaderConstant"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="java.util.Set"%>
<%@page import="npst.common.constant.NpstRoleConstant"%>
<%@page import="npst.common.constant.NpstCommonConstant"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.nps.manpower.constants.DropdownValues"%>
<%@page import="com.nps.manpower.util.ManpowerUtil"%>
<%@page import="com.nps.manpower.constants.ManpowerStatusConstant"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldName"%>
<%@page import="com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.ManpowerEmployee"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldLabel"%>
<%@ include file="/init.jsp" %>


<portlet:renderURL var="addManpowerEmployeeURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/save-manpower-employee.jsp" />
	<portlet:param name="<%=ManpowerEmployeeFieldName.IS_DIRECTOR%>" value="0" />
</portlet:renderURL>

<portlet:renderURL var="addCompositionCommitteeURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/save-composition.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewHistoryManpowerEmployeeURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/view-employee-history.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewDesignationHistoryURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/designation-history.jsp" />
</portlet:renderURL>

<portlet:renderURL var="employeeFilterURL">
    <portlet:param name="mvcRenderCommandName" value="/employee/filter" />
</portlet:renderURL>


<portlet:actionURL name="deleteManpowerEmployee" var="deleteManpowerEmployeeURL" />
 <%
 List<ManpowerEmployee> manpowerEmployees=null;
 Set<String> pfmTypes= null;
 String myPfm=null;
 	int filterType=Integer.parseInt(ManpowerStatusConstant.ACTIVE_EMPLOYEE);
 	filterType=ParamUtil.getInteger(request, ManpowerEmployeeFieldName.FILTER_TYPE);
	String  pfmName=ParamUtil.getString(request, ManpowerEmployeeFieldName.PFM_NAME);
 	if(pfmName.equals("") || pfmName.equals(null) || pfmName==""){
 		pfmName=ManpowerStatusConstant.ALL_PFM;
 	}    
 	if(isNpstUser){
 		pfmTypes=NpstCommonUtil.getUserType(user.getCompanyId(), NpstRoleConstant.PFM_ROLES, NpstCommonConstant.PFM_NAME);
 		 manpowerEmployees=ManpowerUtil.getFilterEmployee(filterType, 0,pfmName);
 	}else{
 		myPfm=NpstCommonUtil.getExpandoValue(user.getCompanyId(), user.getUserId(), User.class.getName(), NpstCommonConstant.PFM_NAME);
 		 manpowerEmployees=ManpowerUtil.getFilterEmployee(filterType, 0,myPfm);
 	}
 	%>
 	<%-- String backUrl=ParamUtil.getString(request, ManpowerEmployeeFieldName.BACK_URL);
 	if(backUrl!=null && backUrl!="" && !backUrl.equalsIgnoreCase(null)){
 	
 	<a href="<%=backUrl%>">Back</a>
 	<%} %> --%>

<h2 class="text-center"><%=ManpowerHeaderConstant.VIEW_MANPOWER_EMPLOYEE %></h2>
<br><div style="width: 10%; float: right; margin-top: -2rem;">
<a href="<%=backURL%>" class="btn btn-primary" style="border-radius: 50px;"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a>
</div>

<div style="width: 50%; margin-bottom: -1rem;">

<select id="filter">
<%for(Entry<String,String> entry:DropdownValues.FILTER_EMPLOYEE_MAP.entrySet()){
	String isSelected = "";
	if(Integer.parseInt(entry.getKey())==filterType ){
		isSelected=" selected";
	}
	%>
<option value="<%=entry.getKey()%>" <%=isSelected%> ><%=entry.getValue() %></option>

<%} %>
    </select>
    
<%if(isNpstUser){ %>
    <select id="pfmName">
    <option value="<%=ManpowerStatusConstant.ALL_PFM%>" selected>All</option>
<%for(String pfmType:pfmTypes){
	String isSelected = "";
	if(pfmType.equalsIgnoreCase(pfmName) ){
		isSelected=" selected";
	}
	%>
<option value="<%=pfmType%>" <%=isSelected%> ><%=pfmType %></option>

<%} %>
    </select>
<% }else{%>
<input type="hidden" id="pfmName" value="<%=myPfm%>"></input>
<%} %> 
</div>

<div class="dataTable-container">
<table id="keyPersonalTable" class="dataTable-table table table-bordered table-responsive">
		<!-- class="display table table-striped display nowrap" style="width: 100%"> -->
		<thead>
			<tr class="border">
				<th>Sr.No</th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DESIGNATION%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_NAME%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_APPOINTMENTDATE%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_CONTACT_NO%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_EMAIL%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_QUALIFICATION%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_EXPERIENCE%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DEPUTATION%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_LINKEDINID%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_APPROVING_APPOINTMENT_DATE%></th>
				<%if(!isNpstUser){ %>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_COMMITTEE_MEMBERSHIP_TYPE%></th>
				<%} %>
				<%-- <th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_FORM_MBP%></th> --%>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_BIODATA_FILE_ID%></th>
				<%if(filterType+""!=ManpowerStatusConstant.ACTIVE_EMPLOYEE && !ManpowerStatusConstant.ACTIVE_EMPLOYEE.equalsIgnoreCase(filterType+"")  ){ %>
				 <th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_RESIGNATION_DATE%></th>
				<%} %>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_ACTION%></th>
				
			</tr>
		</thead>
		<tbody>
			<% int count=1;
			for(ManpowerEmployee manpowerEmployee:manpowerEmployees){ 
				String fileUrl=NpstCommonUtil.getFileDownloadURL(manpowerEmployee.getBiodataFileId(), themeDisplay);
				if(fileUrl==null || fileUrl.equals(null)){
					fileUrl="#";
				}
				/* String formMvpFileUrl=NpstCommonUtil.getFileDownloadURL(manpowerEmployee.getFormMbp(), themeDisplay);
				if(formMvpFileUrl==null || formMvpFileUrl.equals(null)){
					formMvpFileUrl="#";
				} */
			%>
					<tr>
						<td><%=count++ %></td>
						<td class="tbdata"><%=manpowerEmployee.getDesignation() %></td>
						<td class="tbdata"><%=manpowerEmployee.getName() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerEmployee.getAppointmentDate()) %></td>
						<td class="tbdata"><%=manpowerEmployee.getContactNo() %></td>
						<td class="tbdata"><%=manpowerEmployee.getEmail() %></td>
						<td class="tbdata"><%=manpowerEmployee.getQualification() %></td>
						<td class="tbdata"><%=ManpowerUtil.experienceFormat(manpowerEmployee.getExperience()) %></td>
						<td class="tbdata"><%=manpowerEmployee.getDeputation() %></td>
						<td class="tbdata"><%=manpowerEmployee.getLinkedinId() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerEmployee.getApprovingAppointmentDate()) %></td>
						<%if(!isNpstUser){ %>
						<td class="tbdata"><button class="btn btn-primary" onClick='addCompositionCommittee("<%=manpowerEmployee.getManpowerEmployeeId() %>")'><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_COMMITTEE_MEMBERSHIP_TYPE%></button></td>
						<%} %>
						<%-- <td class="tbdata"><a href="<%=formMvpFileUrl%>">Download</a></td> --%>
						<td class="tbdata"><a href="<%=fileUrl%>">Download</a></td>
						<%if(filterType+""!=ManpowerStatusConstant.ACTIVE_EMPLOYEE && !ManpowerStatusConstant.ACTIVE_EMPLOYEE.equalsIgnoreCase(filterType+"")  ){ %>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerEmployee.getResignationDate()) %></td>
						<%} %>
						<td class="tbdata">
						
						<liferay-ui:icon-menu  markupView="lexicon" direction="left-side"   showWhenSingleIcon="<%= false %>">
						 <% if (hasPermission){ %>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='addManpowerEmployee("<%=manpowerEmployee.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="Edit" /></button><br>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='deleteManpowerEmployee("<%=manpowerEmployee.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="Resignation" /></button><br>
						<% } %>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='viewHistoryManpowerEmployee("<%=manpowerEmployee.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="Changes" /></button>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='viewDesignationHistory("<%=manpowerEmployee.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="History" /></button>
						</liferay-ui:icon-menu>
						
						</td>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>

<div>

<%if(hasPermission) {%>
<button class="btn btn-primary" onClick="addManpowerEmployee(0)">Add</button>
<%} %>
</div>


 <div id="modalOne" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopup()" style="float: right;">&times;</a><br>
           <h3>do you want to remove this record?</h3>
              <aui:input class="fname" type="date" name="<%=ManpowerEmployeeFieldName.RESIGNATION_DATE %>" id="<%=ManpowerEmployeeFieldName.RESIGNATION_DATE %>" />
            <span><b>Status:</b>Inactive</span><br>
            <div class="text-center">
            <button class="text-center btn btn-primary" type="submit" onClick="deleteEmployee()">Submit</button>
        </div>
        </div>
      </div>
      
      </div>
    </div>
     
    

<script>
<%-- function deleteManpowerEmployee(manpowerEmployeeId){
	window.location.href="<%=deleteManpowerEmployeeURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;
	
} --%>

function addManpowerEmployee(manpowerEmployeeId){
	 var url="<%=addManpowerEmployeeURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;
	  Liferay.Util.openWindow({
	    dialog: {
	        centered: true,
	        height: 500,
	        modal: true,
	        width: 700,
	        cssClass: "ui-model",
	        destroyOnHide: true,
            resizable: true,
            after: {
          	  destroy: function(event) {
          	 location.reload();
          	  }
          	  }
	    },
	    id: "<portlet:namespace />popUpId",
	    title: "<%=ManpowerHeaderConstant.ADD_MANPOWER_EMPLOYEE_FORM%>",
	    uri: url
	});  
}
function addCompositionCommittee(manpowerEmployeeId){
	 var url="<%=addCompositionCommitteeURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;
	  Liferay.Util.openWindow({
	    dialog: {
	        centered: true,
	        height: 500,
	        modal: true,
	        width: 700,
	        cssClass: "ui-model",
	        destroyOnHide: true,
           resizable: true,
           after: {
         	  destroy: function(event) {
         	 location.reload();
         	  }
         	  }
	    },
	    id: "<portlet:namespace />popUpId",
	    title: "<%=ManpowerHeaderConstant.ADD_COMPOSITION_COMMITTEE_FORM%>",
	    uri: url
	});  
}

function viewHistoryManpowerEmployee(manpowerEmployeeId){
	 var url="<%=viewHistoryManpowerEmployeeURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;
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
	    title: "<%=ManpowerHeaderConstant.VIEW_MANPOWER_CHANGES%>",
	    uri: url
	});  
}

function viewDesignationHistory(manpowerEmployeeId){
	 var url="<%=viewDesignationHistoryURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;
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
	    title: "<%=ManpowerHeaderConstant.VIEW_MANPOWER_HISTORY%>",
	    uri: url
	});  
}

 $(document).ready(function() {
	 
	 $('#keyPersonalTable').DataTable({
			searching : true,
			info: false,
			pagination : "bootstrap",
			filter : true,
			destroy : true,
			lengthMenu : [ 5, 10, 25 ],
			pageLength : 10,
			"dom": '<"top"f>rt<"bottom"lp><"clear">',					
		});
	 
   /*  $('#keyPersonalTable').DataTable({
    	 "dom": 'lrtip', 
    	 "bLengthChange" : false,
	      "scrollX": true 
   }); */
    
    
} ); 



</script>


   <script>

var select = document.getElementById('filter');
var pfmName = document.getElementById('pfmName');

select.addEventListener('change', function handleChange(event) {
	var filterValue=event.target.value;
	var pfmType=$("#pfmName").val();
	window.location.href="<%=employeeFilterURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.FILTER_TYPE%>="+ filterValue+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.IS_DIRECTOR%>="+0+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.PFM_NAME%>="+pfmType;
  console.log(filterValue); 
});

pfmName.addEventListener('change', function handleChange(event) {
	var pfmType=event.target.value;
	var filterValue=$("#filter").val();
	window.location.href="<%=employeeFilterURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.FILTER_TYPE%>="+ filterValue+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.IS_DIRECTOR%>="+0+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.PFM_NAME%>="+pfmType;
  console.log(filterValue); 
});
</script>
   
   <script>
   var manpowerEmployeeId=0;
   function deleteManpowerEmployee(employeeId){
	   manpowerEmployeeId=employeeId;
	   document.getElementById("modalOne").style.display = "block";
   }
   
   
   function deleteEmployee(){
	   if(manpowerEmployeeId>0){
		   var resignationDate=$("#<portlet:namespace/>resignationDate").val();
		    console.log(resignationDate);
		    <%-- window.location.href="<%=deleteManpowerEmployeeURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.RESIGNATION_DATE%>="+resignationDate;
		    document.getElementById("modalOne").style.display = "none"; --%>
	   }
	   
   }
   
   function closePopup(){
	   document.getElementById("modalOne").style.display = "none";
   }
   
    </script>