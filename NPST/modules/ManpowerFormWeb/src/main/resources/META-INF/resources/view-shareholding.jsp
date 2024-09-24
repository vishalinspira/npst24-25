
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.nps.manpower.util.ManpowerUtil"%>
<%@page import="com.nps.manpower.constants.ManpowerDirectorHoldingFieldName"%>
<%@page import="com.nps.manpower.constants.ManpowerDirectorHoldingFieldLabel"%>
<%@page import="com.nps.manpower.service.ManpowerDirectorHoldingLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.ManpowerDirectorHolding"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
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
<portlet:renderURL var="addShareHoldingURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	<portlet:param name="mvcPath" value="/save-shareholding.jsp" />
	<portlet:param name="<%=ManpowerEmployeeFieldName.IS_DIRECTOR%>" value="1" />
</portlet:renderURL>

<portlet:actionURL name="deleteShareHolding" var="deleteShareHoldingURL" />

 <%
 	long manpowerEmployeeId=ParamUtil.getLong(request,ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
	 String empId=(String)request.getAttribute(ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
 	if(manpowerEmployeeId<=0 &&(empId!=null && !empId.equals(null))){
		manpowerEmployeeId=Long.parseLong(empId);
	}
 	System.out.println("view shareholding manpower employee ID: "+manpowerEmployeeId);
  List<ManpowerDirectorHolding> manpowerDirectorHoldings=null;
  if(manpowerEmployeeId>0){
 	 manpowerDirectorHoldings = ManpowerDirectorHoldingLocalServiceUtil.getAllByManpowerAndStatus(manpowerEmployeeId, Integer.parseInt(ManpowerStatusConstant.ACTIVE_EMPLOYEE));
  }
 // boolean hasPermission=ManpowerUtil.hasPermission(NpstRoleConstant.test, user.getUserId(), user.getCompanyId());
 %> 


<div class="dataTable-container" id="shareHoldingDiv">

<table id="shareHoldingTable"
		class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th style="background: dodgerblue;">Sr.No</th>
				<th style="background: dodgerblue;"><%=ManpowerDirectorHoldingFieldLabel.TABLE_VIEW_COMPANY_NAME%></th>
				<th style="background: dodgerblue;"><%=ManpowerDirectorHoldingFieldLabel.TABLE_VIEW_CONCERN%></th>
				<th style="background: dodgerblue;"><%=ManpowerDirectorHoldingFieldLabel.TABLE_VIEW_SHARE_HOLDING%></th>
				<th style="background: dodgerblue;"><%=ManpowerDirectorHoldingFieldLabel.TABLE_VIEW_CONCERN_DATE%></th>
				<%if(hasPermission){ %>
				<th style="background: dodgerblue;"><%=ManpowerEmployeeFieldLabel.TABLE_VIEW_ACTION%></th> 
				<%} %>
			</tr>
		</thead>
		<tbody>
					<%
					int count=1;
					for(ManpowerDirectorHolding manpowerDirectorHolding:manpowerDirectorHoldings){ %>
					<tr>
						<td><%=count++ %></td>
						<td class="tbdata"><%=manpowerDirectorHolding.getCompanyName() %></td>
						<td class="tbdata"><%=manpowerDirectorHolding.getConcern() %></td>
						<td class="tbdata"><%=manpowerDirectorHolding.getShareHolding() %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(manpowerDirectorHolding.getConcernDate()) %></td>
						<%if(hasPermission){ %>
						<td class="tbdata">
						<liferay-ui:icon-menu  markupView="lexicon" direction="left-side"   showWhenSingleIcon="<%= false %>">
					<button class="btn btn-link" style="color: #6b6c7e;" onClick='addShareHolding("<%=manpowerDirectorHolding.getManpowerDirectorHoldingId() %>")'><liferay-ui:icon image="" message="Edit" /></button><br>
					<button class="btn btn-link" style="color: #6b6c7e;" onClick='deleteShareHolding("<%=manpowerDirectorHolding.getManpowerDirectorHoldingId() %>")'><liferay-ui:icon image="" message="Delete" /></button>	
						</liferay-ui:icon-menu>
						</td>
						<%} %>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>
<%if(hasPermission){ %>
<br>
<button class="btn btn-primary ml-3" onClick="addShareHolding(0)">Add</button>
<%} %>



<script>

<%-- function deleteShareHolding(manpowerShareHoldingId){
	window.location.href="<%=deleteShareHoldingURL%>&<portlet:namespace /><%=ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID%>="+ manpowerShareHoldingId;
	
} --%>

function deleteShareHolding(manpowerShareHoldingId) {
  let text = "Do you want to delete this record?";
  if (confirm(text) == true) {
	  window.location.href="<%=deleteShareHoldingURL%>&<portlet:namespace /><%=ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID%>="+ manpowerShareHoldingId+"&<portlet:namespace /><%=ManpowerDirectorHoldingFieldName.MANPOWER_EMPLOYEE_ID%>=<%=manpowerEmployeeId%>";
  } else {
    
  }
  
}


function addShareHolding(manpowerShareHoldingId){
	 var url="<%=addShareHoldingURL%>&<portlet:namespace /><%=ManpowerDirectorHoldingFieldName.MANPOWER_DIRECTOR_HOLDING_ID%>="+ manpowerShareHoldingId+"&<portlet:namespace /><%=ManpowerDirectorHoldingFieldName.MANPOWER_EMPLOYEE_ID%>=<%=manpowerEmployeeId%>";
	 console.log("url:  "+url);
	 window.location.href=url;

/* 	  Liferay.Util.openWindow({
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
	    title: "Share Holding Form",
	    uri: url
	});  */ 
}

$(document).ready(function() {
    $('#shareHoldingTable').DataTable({
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
   
   
   