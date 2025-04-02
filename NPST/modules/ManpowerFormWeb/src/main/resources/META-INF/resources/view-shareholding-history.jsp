
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="com.nps.manpower.util.ManpowerUtil"%>
<%@page import="com.nps.manpower.model.ChangeShareHolding"%>
<%@page import="com.nps.manpower.service.ChangeShareHoldingLocalServiceUtil"%>
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
  <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
 <script type="text/javascript"  charset="utf8" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.css" type="text/css"  rel="stylesheet">
<!-- <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css" />
<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js" ></script>
<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>
 -->
 <%
 	long manpowerEmployeeId=ParamUtil.getLong(request,ManpowerEmployeeFieldName.MANPOWER_EMPLOYEE_ID);
 	System.out.println("view shareholding manpower employee ID: "+manpowerEmployeeId);
  List<ChangeShareHolding> changeShareHoldings=null;
  if(manpowerEmployeeId>0){
	  changeShareHoldings = ChangeShareHoldingLocalServiceUtil.getAllByManpowerId(manpowerEmployeeId);
  }
 // boolean hasPermission=ManpowerUtil.hasPermission(NpstRoleConstant.test, user.getUserId(), user.getCompanyId());
 %> 


<div class="dataTable-container" id="shareHoldingDiv">
<table id="shareHoldingTable" class="dataTable-table table table-bordered table-responsive">
		<thead>
			<tr class="border">
				<th style="background: dodgerblue; width: 10%;">Sr.No</th>
				<th style="background: dodgerblue; width: 25%;"><%=ManpowerDirectorHoldingFieldLabel.TABLE_VIEW_COMPANY_NAME%></th>
				<th style="background: dodgerblue; width: 20%;"><%=ManpowerDirectorHoldingFieldLabel.TABLE_VIEW_CONCERN%></th>
				<th style="background: dodgerblue; width: 20%;"><%=ManpowerDirectorHoldingFieldLabel.TABLE_VIEW_SHARE_HOLDING%></th>
				<th style="background: dodgerblue; width: 25%;"><%=ManpowerDirectorHoldingFieldLabel.TABLE_VIEW_CONCERN_DATE%></th>
			</tr>
		</thead>
		<tbody>
					<%
					int count=1;
					for(ChangeShareHolding changeShareHolding:changeShareHoldings){ %>
					<tr>
						<td><%=count++ %></td>
						<td class="tbdata"><%=HtmlUtil.escape(changeShareHolding.getCompanyName()) %></td>
						<td class="tbdata"><%=HtmlUtil.escape(changeShareHolding.getConcern()) %></td>
						<td class="tbdata"><%=HtmlUtil.escape(changeShareHolding.getShareHolding()) %></td>
						<td class="tbdata"><%=NpstCommonUtil.getDateString(changeShareHolding.getConcernDate())%></td>
					</tr>
					<%} %>
		</tbody>
	</table>
</div>


<script>

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
   
   
   