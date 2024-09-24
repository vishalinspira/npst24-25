
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.nps.manpower.service.ChangeInEmployeeLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.ChangeInEmployee"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.manpower.util.ManpowerUtil"%>
<%@page import="com.nps.manpower.constants.ManpowerStatusConstant"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldName"%>
<%@page import="com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.ManpowerEmployee"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldLabel"%>
<%@ include file="/init.jsp" %>
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.css" type="text/css"  rel="stylesheet">
 <%
 	long manpowerEmployeeId=ParamUtil.getLong(request,ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID );
 List<ChangeInEmployee> changeInEmployees = null;
 if(manpowerEmployeeId>0){
  changeInEmployees =  ChangeInEmployeeLocalServiceUtil.getAllByEmployeeId(manpowerEmployeeId);
 }
 //boolean hasPermission=ManpowerUtil.hasPermission(NpstRoleConstant.test, user.getUserId(), user.getCompanyId());
 %> 
 
<div class="dataTable-container">

<table id="directorTable" class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th style="background: dodgerblue;">Sr.No</th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_NAME%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DIRECTOR_TYPE%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_MEMBERSHIP_TYPE%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DEPENDENCY%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DIN%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_APPOINTMENTDATE%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_QUALIFICATION%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_EXPERIENCE%></th>
				<%-- <th><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_COMMITTEE_MEMBERSHIP_TYPE%></th> --%>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_FORM_MBP%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_BIODATA_FILE_ID%></th>
			</tr>
		</thead>
		<tbody>
					<% int count=1;
					for(ChangeInEmployee changeInEmployee:changeInEmployees){
						String biodataFileUrl=NpstCommonUtil.getFileDownloadURL(changeInEmployee.getBiodataFileId(), themeDisplay);
						if(biodataFileUrl==null || biodataFileUrl.equals(null)){
							biodataFileUrl="#";
						}
						String formMvpFileUrl=NpstCommonUtil.getFileDownloadURL(changeInEmployee.getFormMbp(), themeDisplay);
						if(formMvpFileUrl==null || formMvpFileUrl.equals(null)){
							formMvpFileUrl="#";
						}
						%>
					<tr>
						<td><%=count++ %></td>
						<td class="tbdata"><%=changeInEmployee.getName() %></td>
						<td class="tbdata"><%=changeInEmployee.getDirectorType() %></td>
						<td class="tbdata"><%=changeInEmployee.getMembershipType() %></td>
						<td class="tbdata"><%=changeInEmployee.getDependency() %></td>
						<td class="tbdata"><%=changeInEmployee.getDin() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(changeInEmployee.getAppointmentDate()) %></td>
						<td class="tbdata"><%=changeInEmployee.getQualification() %></td>
						<td class="tbdata"><%=ManpowerUtil.experienceFormat(changeInEmployee.getExperience()) %></td>
						<%-- <td class="tbdata"><button class="btn btn-primary" onClick='addCompositionCommittee("<%=changeInEmployee.getManpowerEmployeeId() %>")'>CC</button></td> --%>
						<td class="tbdata"><a href="<%=formMvpFileUrl%>">Download</a></td>
						<td class="tbdata"><a href="<%=biodataFileUrl%>">Download</a></td>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>


<script>

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


</script>
   
   
   