
<%@page import="com.nps.manpower.constants.ManpowerHeaderConstant"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="com.nps.manpower.constants.DropdownValues"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Set"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="npst.common.constant.NpstCommonConstant"%>
<%@page import="npst.common.constant.NpstRoleConstant"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.nps.manpower.util.ManpowerUtil"%>
<%@page import="com.nps.manpower.constants.ManpowerStatusConstant"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldName"%>
<%@page import="com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.ManpowerEmployee"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldLabel"%>
<%@ include file="/init.jsp" %>

<portlet:renderURL var="employeeFilterURL">
    <portlet:param name="mvcRenderCommandName" value="/employee/committee/filter" />
    <portlet:param name="jspName" value="/view-investment-committee.jsp" />
</portlet:renderURL>

 <%
 
 List<ManpowerEmployee> manpowerEmployees=null;
 Set<String> pfmTypes= null;
 String myPfm=null;
    
 	int employeeStatus=Integer.parseInt(ManpowerStatusConstant.ACTIVE_EMPLOYEE);
 	employeeStatus=ParamUtil.getInteger(request, ManpowerEmployeeFieldName.STATUS);
	String  pfmName=ParamUtil.getString(request, ManpowerEmployeeFieldName.PFM_NAME);
 	if(pfmName.equals("") || pfmName.equals(null) || pfmName==""){
 		pfmName=ManpowerStatusConstant.ALL_PFM;
 	}
	 if(isNpstUser){
	 		pfmTypes=NpstCommonUtil.getUserType(user.getCompanyId(), NpstRoleConstant.PFM_ROLES, NpstCommonConstant.PFM_NAME);
	 		manpowerEmployees=ManpowerUtil.getCommitteEmployee(employeeStatus, pfmName, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_INVESTMENT));
	 				ManpowerUtil.getFilterEmployee(employeeStatus, 1,pfmName);
	 	}else{
	 		myPfm=NpstCommonUtil.getExpandoValue(user.getCompanyId(), user.getUserId(), User.class.getName(), NpstCommonConstant.PFM_NAME);
	 		manpowerEmployees=ManpowerUtil.getCommitteEmployee(employeeStatus, myPfm, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_INVESTMENT));
	 	}
	 %>
	 	<%-- String backUrl=ParamUtil.getString(request, ManpowerEmployeeFieldName.BACK_URL);
	 	if(backUrl!=null && backUrl!="" && !backUrl.equalsIgnoreCase(null)){
	 	%>
	 	<a href="<%=backUrl%>">Back</a>
	 	<%} %> --%>
<h2 class="text-center"><%=ManpowerHeaderConstant.VIEW_INVEST_COMMITTEE %></h2>
<br><div style="width: 10%; float: right; margin-top: -2rem;">
<a href="<%=backURL%>" class="btn btn-primary" style="border-radius: 50px;"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a>
</div>

<div style="width: 50%; margin-bottom: -1rem;">
<select id="filter">
<%for(Entry<String,String> entry:DropdownValues.FILTER_EMPLOYEE_MAP.entrySet()){
	String isSelected = "";
	if(Integer.parseInt(entry.getKey())==employeeStatus ){
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
<table id="investmentCommitteeTable" class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th style="width: 10%;">Sr.No</th>
				<th style="width: 30%;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_NAME%></th>
				<%-- <th style="width: 25%;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_QUALIFICATION%></th>
				<th style="width: 15%;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_EXPERIENCE%></th> --%>
				<th style="width: 30%;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DESIGNATION%></th>
				<th style="width: 30%;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_APPOINTMENTDATE%></th>
				<%-- <th style="width: 15%;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_FORM_MBP%></th>
				<th style="width: 15%;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_BIODATA_FILE_ID%></th> --%>
				<%-- <%if(employeeStatus+""==ManpowerStatusConstant.NON_ACTIVE_EMPLOYEE || ManpowerStatusConstant.NON_ACTIVE_EMPLOYEE.equalsIgnoreCase(employeeStatus+"")  ){ %>
				<th ><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_RESIGNATION_DATE%></th>
				<%} %> --%>
				<th ><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_RESIGNATION_DATE%></th>
			</tr>
		</thead>
		<tbody>
					<%
					int count=1;
					for(ManpowerEmployee manpowerEmployee:manpowerEmployees){ 
					String biodataFileUrl=NpstCommonUtil.getFileDownloadURL(manpowerEmployee.getBiodataFileId(), themeDisplay);
						if(biodataFileUrl==null || biodataFileUrl.equals(null)){
							biodataFileUrl="#";
						}
						String formMvpFileUrl=NpstCommonUtil.getFileDownloadURL(manpowerEmployee.getFormMbp(), themeDisplay);
						if(formMvpFileUrl==null || formMvpFileUrl.equals(null)){
							formMvpFileUrl="#";
						}
						%>
					<tr>
						<td><%=count++ %></td>
						<td class="tbdata"><%=manpowerEmployee.getName() %></td>
						<%-- <td class="tbdata"><%=manpowerEmployee.getQualification() %></td>
						<td class="tbdata"><%=ManpowerUtil.experienceFormat(manpowerEmployee.getExperience()) %></td> --%>
						 <td class="tbdata"><%=manpowerEmployee.getIsDirector()>0? manpowerEmployee.getDependency():manpowerEmployee.getDesignation() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerEmployee.getAppointmentDate()) %></td>
						<%-- <td class="tbdata"><a href="<%=formMvpFileUrl%>">Download</a></td>
						<td class="tbdata"><a href="<%=biodataFileUrl%>">Download</a></td> --%>
						<%-- <%if(employeeStatus+""==ManpowerStatusConstant.NON_ACTIVE_EMPLOYEE || ManpowerStatusConstant.NON_ACTIVE_EMPLOYEE.equalsIgnoreCase(employeeStatus+"")  ){ %>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerEmployee.getResignationDate()) %></td>
						<%} %> --%>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerEmployee.getResignationDate()) %></td>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>


<script>


$(document).ready(function() {
    $('#investmentCommitteeTable').DataTable({
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

</script>
   
   
  <script>

var select = document.getElementById('filter');
var pfmName = document.getElementById('pfmName');

select.addEventListener('change', function handleChange(event) {
	var statusValue=event.target.value;
	var pfmType=$("#pfmName").val();
	window.location.href="<%=employeeFilterURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.STATUS%>="+ statusValue+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.PFM_NAME%>="+pfmType;
});

pfmName.addEventListener('change', function handleChange(event) {
	var pfmType=event.target.value;
	var statusValue=$("#filter").val();
	window.location.href="<%=employeeFilterURL%>&<portlet:namespace /><%=ManpowerEmployeeFieldName.STATUS%>="+ statusValue+"&<portlet:namespace /><%=ManpowerEmployeeFieldName.PFM_NAME%>="+pfmType;
});
</script>