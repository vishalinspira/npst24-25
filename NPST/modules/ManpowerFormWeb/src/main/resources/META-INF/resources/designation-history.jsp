
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
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.css" type="text/css"  rel="stylesheet">


 <%
 
 long manpowerEmployeeId=ParamUtil.getLong(request, ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
 ManpowerEmployee manpowerEmp =null;
 try{
	 manpowerEmp=ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
 }catch(Exception e){}
 List<ManpowerEmployee> manpowerEmployees=ManpowerEmployeeLocalServiceUtil.getAllByDesignationAndPFM(manpowerEmp.getDesignation(), manpowerEmp.getPfmName());
 	%>

<div class="dataTable-container">
<table id="designationTable" class="dataTable-table table table-bordered table-responsive">
		<!-- class="display table table-striped display nowrap" style="width: 100%"> -->
<thead>
			<tr class="border">
				<th style="background: dodgerblue;">Sr.No</th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DESIGNATION%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_NAME%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_APPOINTMENTDATE%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_CONTACT_NO%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_EMAIL%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_QUALIFICATION%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_EXPERIENCE%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_DEPUTATION%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_LINKEDINID%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_APPROVING_APPOINTMENT_DATE%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_COMMITTEE_MEMBERSHIP_TYPE%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_BIODATA_FILE_ID%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_STATUS%></th>
				
			</tr>
		</thead>
		<tbody>
			<% int count=1;
			for(ManpowerEmployee manpowerEmployee:manpowerEmployees){ 
				String fileUrl=NpstCommonUtil.getFileDownloadURL(manpowerEmployee.getBiodataFileId(), themeDisplay);
				if(fileUrl==null || fileUrl.equals(null)){
					fileUrl="#";
				}
				String formMvpFileUrl=NpstCommonUtil.getFileDownloadURL(manpowerEmployee.getFormMbp(), themeDisplay);
				if(formMvpFileUrl==null || formMvpFileUrl.equals(null)){
					formMvpFileUrl="#";
				}
			%>
					<tr>
						<td><%=count++ %></td>
						<td class="tbdata"><%=manpowerEmployee.getDesignation() %></td>
						<td class="tbdata"><%=manpowerEmployee.getName() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerEmployee.getAppointmentDate()) %></td>
						<td class="tbdata"><%=manpowerEmployee.getContactNo() %></td>
						<td class="tbdata"><%=manpowerEmployee.getEmail() %></td>
						<td class="tbdata"><%=manpowerEmployee.getQualification() %></td>
						<td class="tbdata"><%=manpowerEmployee.getExperience() %></td>
						<td class="tbdata"><%=manpowerEmployee.getDeputation() %></td>
						<td class="tbdata"><%=manpowerEmployee.getLinkedinId() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerEmployee.getApprovingAppointmentDate()) %></td>
						<%-- <td class="tbdata"><a href="<%=formMvpFileUrl%>">Download</a></td> --%>
						<td class="tbdata"><%=DropdownValues.COMMITTEE_MEMBERSHIP_TYPE_MAP.get(manpowerEmployee.getCommitteeMembershipType()) %></td>
						<td class="tbdata"><a href="<%=fileUrl%>">Download</a></td>
						<td class="tbdata"><%=(manpowerEmployee.getStatus()==0)?"Active":"InActive" %></td>
					</tr>
					<%} %>
		</tbody>

	</table>
</div>
<script>

$(document).ready(function() {
	 
	 $('#designationTable').DataTable({
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
