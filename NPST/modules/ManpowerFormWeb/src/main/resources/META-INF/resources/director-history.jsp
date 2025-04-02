
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
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
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.css" type="text/css"  rel="stylesheet">
 <%
 
 long manpowerEmployeeId=ParamUtil.getLong(request, ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
 ManpowerEmployee manpowerEmp =null;
 try{
	 manpowerEmp=ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
 }catch(Exception e){}
 List<ManpowerEmployee> manpowerEmployees=ManpowerEmployeeLocalServiceUtil.getAllByDependencyAndPFM(manpowerEmp.getDependency(), manpowerEmp.getPfmName());
 %>

<div class="dataTable-container">
<table id="directorHistoryTable" class="dataTable-table table table-bordered table-responsive">
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
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_COMMITTEE_MEMBERSHIP_TYPE%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_FORM_MBP%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_BIODATA_FILE_ID%></th>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_STATUS%></th>
				
			</tr>
		</thead>
		<tbody>
					<% int count=1;
					for(ManpowerEmployee manpowerDirector:manpowerEmployees){
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
						<td class="tbdata"><%=HtmlUtil.escape(manpowerDirector.getExperience()) %></td>
						<td class="tbdata"><%=HtmlUtil.escape(manpowerDirector.getCommitteeMembershipType()) %></td>
						<td class="tbdata"><a href="<%=formMvpFileUrl%>">Download</a></td>
						<td class="tbdata"><a href="<%=biodataFileUrl%>">Download</a></td>
						<td class="tbdata"><%=(manpowerDirector.getStatus()==0)?"Active":"InActive" %></td>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>

<script>


$(document).ready(function() {
    $('#directorHistoryTable').DataTable({
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


   