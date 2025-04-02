
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.nps.manpower.constants.ManpowerHeaderConstant"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="java.util.Set"%>
<%@page import="npst.common.constant.NpstCommonConstant"%>
<%@page import="npst.common.constant.NpstRoleConstant"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.manpower.constants.DropdownValues"%>
<%@page import="java.util.Map.Entry"%>
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
	<portlet:param name="mvcPath" value="/save-manpower-director.jsp" />
	<portlet:param name="<%=ManpowerEmployeeFieldName.IS_DIRECTOR%>" value="1" />
</portlet:renderURL>
<portlet:renderURL var="viewDirectorHistoryURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/director-history.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewDirectorShareHoldingURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/view-shareholding.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewHistoryManpowerEmployeeURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/view-director-history.jsp" />
</portlet:renderURL>

<portlet:renderURL var="addCompositionCommitteeURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/save-composition.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewDirectorShareHoldingHistoryURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="mvcPath" value="/view-shareholding-history.jsp" />
</portlet:renderURL>

<portlet:renderURL var="employeeFilterURL">
    <portlet:param name="mvcRenderCommandName" value="/employee/filter" />
</portlet:renderURL>


<portlet:actionURL name="deleteManpowerEmployee" var="deleteManpowerEmployeeURL" />

 <%
 
 List<ManpowerEmployee> manpowerDirectors=null;
 Set<String> pfmTypes= null;
 String myPfm=null;
    
 	int filterType=Integer.parseInt(ManpowerStatusConstant.ACTIVE_EMPLOYEE);
 	filterType=ParamUtil.getInteger(request, ManpowerEmployeeFieldName.FILTER_TYPE);
 	String  pfmName=ParamUtil.getString(request, ManpowerEmployeeFieldName.PFM_NAME);
 	if(pfmName.equals("") || pfmName.equals(null) || pfmName==""){
 		pfmName=ManpowerStatusConstant.ALL_PFM;
 	}
 			
	 System.out.println("isNpstUser:  "+isNpstUser);
	 if(isNpstUser){
	 		pfmTypes=NpstCommonUtil.getUserType(user.getCompanyId(), NpstRoleConstant.PFM_ROLES, NpstCommonConstant.PFM_NAME);
	 		manpowerDirectors=ManpowerUtil.getFilterEmployee(filterType, 1,pfmName);
	 	}else{
	 		myPfm=NpstCommonUtil.getExpandoValue(user.getCompanyId(), user.getUserId(), User.class.getName(), NpstCommonConstant.PFM_NAME);
	 		manpowerDirectors=ManpowerUtil.getFilterEmployee(filterType, 1,myPfm);
	 	}
	 %>
	 	<%-- String backUrl=ParamUtil.getString(request, ManpowerEmployeeFieldName.BACK_URL);
	 	if(backUrl!=null && backUrl!="" && !backUrl.equalsIgnoreCase(null)){
	 	%>
	 	<a href="<%=backUrl%>">Back</a>
	 	<%} %> --%>

 <h2 class="text-center"><%=ManpowerHeaderConstant.VIEW_DIRECTOR %></h2>
<br><div style="width: 10%; float: right; margin-top: -2rem;">
<a href="<%=backURL%>" class="btn btn-primary" style="border-radius: 50px;"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a>
</div>

<div style="width: 50%; margin-bottom: -1rem;">
<select id="filter">
<%for(Entry<String,String> entry:DropdownValues.FILTER_EMPLOYEE_MAP.entrySet()){
	String isSelected = "";
	if(Integer.parseInt(entry.getKey())==filterType ){
		isSelected=" selected";
	}%>
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
	}%>
<option value="<%=pfmType%>" <%=isSelected%> ><%=pfmType %></option>

<%} %>
    </select>
<% }else{%>
<input type="hidden" id="pfmName" value="<%=myPfm%>"></input>
<%} %>    
    
</div>
<div class="dataTable-container">
<table id="directorTable" class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th>Sr.No</th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_NAME%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DIRECTOR_TYPE%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_MEMBERSHIP_TYPE%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DEPENDENCY%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DIN%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_APPOINTMENTDATE%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_QUALIFICATION%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_EXPERIENCE%></th>
				<%if(!isNpstUser){ %>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_COMMITTEE_MEMBERSHIP_TYPE%></th>
				<%} %>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_FORM_MBP%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_BIODATA_FILE_ID%></th>
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DIRECTOR_SHARE_HOLDING%></th>
					<%if(filterType+""!=ManpowerStatusConstant.ACTIVE_EMPLOYEE && !ManpowerStatusConstant.ACTIVE_EMPLOYEE.equalsIgnoreCase(filterType+"")  ){ %>
				 <th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_RESIGNATION_DATE%></th>
				<%} %>
				
				<th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_ACTION%></th> 
				
			</tr>
		</thead>
		<tbody>
					<% int count=1;
					for(ManpowerEmployee manpowerDirector:manpowerDirectors){
						String biodataFileUrl=NpstCommonUtil.getFileDownloadURL(manpowerDirector.getBiodataFileId(), themeDisplay);
						if(biodataFileUrl==null || biodataFileUrl.equals(null)){
							biodataFileUrl="#";
						}
						String formMvpFileUrl=NpstCommonUtil.getFileDownloadURL(manpowerDirector.getFormMbp(), themeDisplay);
						if(formMvpFileUrl==null || formMvpFileUrl.equals(null)){
							formMvpFileUrl="#";
						}
						%>
					<tr>
						<td><%=count++ %></td>
						<td class="tbdata"><%=HtmlUtil.escape(manpowerDirector.getName()) %></td>
						<td class="tbdata"><%=HtmlUtil.escape(manpowerDirector.getDirectorType()) %></td>
						<td class="tbdata"><%=HtmlUtil.escape(manpowerDirector.getMembershipType()) %></td>
						<td class="tbdata"><%=HtmlUtil.escape(manpowerDirector.getDependency()) %></td>
						<td class="tbdata"><%=HtmlUtil.escape(manpowerDirector.getDin()) %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerDirector.getAppointmentDate()) %></td>
						<td class="tbdata"><%=HtmlUtil.escape(manpowerDirector.getQualification()) %></td>
						<td class="tbdata"><%=ManpowerUtil.experienceFormat(manpowerDirector.getExperience()) %></td>
						<%if(!isNpstUser){ %>
						<td class="tbdata"><button class="btn btn-primary" onClick='addCompositionCommittee("<%=manpowerDirector.getManpowerEmployeeId() %>")'><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_COMMITTEE_MEMBERSHIP_TYPE%></button></td>
						<%} %>
						<td class="tbdata"><a href="<%=formMvpFileUrl%>">Download</a></td>
						<td class="tbdata"><a href="<%=biodataFileUrl%>">Download</a></td>
						<td class="tbdata"><button class="btn btn-primary" onClick='viewDirectorShareHolding("<%=manpowerDirector.getManpowerEmployeeId() %>")'>Share Holding</button></td>
						<%if(filterType+""!=ManpowerStatusConstant.ACTIVE_EMPLOYEE && !ManpowerStatusConstant.ACTIVE_EMPLOYEE.equalsIgnoreCase(filterType+"")  ){ %>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerDirector.getResignationDate()) %></td>
						<%} %>
						<td class="tbdata">
					
						<liferay-ui:icon-menu  markupView="lexicon" direction="left-side"   showWhenSingleIcon="<%= false %>">
						<%if(hasPermission){ %>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='addManpowerEmployee("<%=manpowerDirector.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="Edit" /></button><br>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='deleteManpowerEmployee("<%=manpowerDirector.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="Resignation" /></button><br>
						<%} %>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='viewHistoryManpowerEmployee("<%=manpowerDirector.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="Changes" /></button><br>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='viewDirectorShareHoldingHistory("<%=manpowerDirector.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="Holdings" /></button>
						<button class="btn btn-link" style="color: #6b6c7e;" onClick='viewDesignationHistory("<%=manpowerDirector.getManpowerEmployeeId() %>")'><liferay-ui:icon image="" message="History" /></button>
						</liferay-ui:icon-menu>

						</td>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>
<%if(hasPermission){ %>
<button class="btn btn-primary" onClick="addManpowerEmployee(0)">Add</button>
<%} %>


 <div id="modalOne" class="modal mt-5" style="display:none; margin-left: 30rem;	">
      <div class="container">
      <div class="modal-content" style="width: 23rem; margin-top: 5rem;">
        <div class="contact-form" style="padding: 0.5rem;">
          <a class="close" onClick="closePopup()" style="float: right;">&times;</a><br>
           <h3>do you want to remove this record?</h3>
              <aui:input class="fname" type="date" label="<%=ManpowerEmployeeFieldLabel.RESIGNATION_DATE %>" name="<%=ManpowerEmployeeFieldName.RESIGNATION_DATE %>" id="<%=ManpowerEmployeeFieldName.RESIGNATION_DATE %>" />
            <span><b>Status:</b>Inactive</span><br>
            <div class="text-center">
            <button class="text-center btn btn-primary" type="submit" onClick="deleteEmployee()">Submit</button>
        </div>
        </div>
      </div>
      
      </div>
    </div>
    
    
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
		    window.location.href="<%=deleteManpowerEmployeeURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId +"&<portlet:namespace /><%=ManpowerEmployeeFieldName.RESIGNATION_DATE%>="+resignationDate;
		    document.getElementById("modalOne").style.display = "none";
	   }
	   
   }
   
   function closePopup(){
	   document.getElementById("modalOne").style.display = "none";
   }
   
    </script>

<script>

<%-- function deleteManpowerEmployee(manpowerEmployeeId){
	window.location.href="<%=deleteManpowerEmployeeURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;
	
} --%>

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

function viewDesignationHistory(manpowerEmployeeId){
	 var url="<%=viewDirectorHistoryURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;
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
	    title: "<%=ManpowerHeaderConstant.VIEW_DIRECTOR_HISTORY%>",
	    uri: url
	});  
}


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
	    title: "<%=ManpowerHeaderConstant.ADD_DIRECTOR_FORM%>",
	    uri: url
	});  
}

$(document).ready(function() {
    $('#directorTable').DataTable({
    	searching : true,
		info: false,
		pagination : "bootstrap",
		filter : true,
		destroy : true,
		lengthMenu : [ 5, 10, 25 ],
		pageLength : 10,
		"dom": '<"top"f>rt<"bottom"lp><"clear">',
  });
 
} ); 


function viewDirectorShareHolding(manpowerEmployeeId){
	console.log(manpowerEmployeeId);
	 var url="<%=viewDirectorShareHoldingURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;

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
	    title: "<%=ManpowerHeaderConstant.VIEW_SHARE_HOLDING%>",
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
	    title: "<%=ManpowerHeaderConstant.VIEW_DIRECTOR_CHANGES%>",
	    uri: url
	});  
}


function viewDirectorShareHoldingHistory(manpowerEmployeeId){
	 var url="<%=viewDirectorShareHoldingHistoryURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID%>="+ manpowerEmployeeId;
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
	    title: "<%=ManpowerHeaderConstant.VIEW_SHARE_HOLDING_HISTORY%>",
	    uri: url
	});  
}

</script>

   <script>


var select = document.getElementById('filter');
var pfmName = document.getElementById('pfmName');

select.addEventListener('change', function handleChange(event) {
	var filterValue=event.target.value;
	var pfmType=$("#pfmName").val();
	window.location.href="<%=employeeFilterURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.FILTER_TYPE%>="+ filterValue+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.IS_DIRECTOR%>="+1+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.PFM_NAME%>="+pfmType;
});

pfmName.addEventListener('change', function handleChange(event) {
	var pfmType=event.target.value;
	var filterValue=$("#filter").val();
	window.location.href="<%=employeeFilterURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.FILTER_TYPE%>="+ filterValue+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.IS_DIRECTOR%>="+1+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.PFM_NAME%>="+pfmType;

});
</script>
   
   
   