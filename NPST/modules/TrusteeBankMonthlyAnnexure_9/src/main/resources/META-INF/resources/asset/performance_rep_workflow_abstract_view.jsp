<%@page import="compliance.service.model.PerformanceRep"%>
<%@ include file="/init.jsp" %>

<%
PerformanceRep performanceRep = (PerformanceRep) request.getAttribute("performanceRep");
%>
<div class="mt-4">
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
				<th>Serial No.</th>
				<td><%=performanceRep.getId()%></td>
			</tr>
			<tr>
				<th>Trustee Bank Name.</th>
				<td><%=performanceRep.getTrusteebankname()%></td>
			</tr>
			<tr>
				<th>Office rName</th>
				<td><%=performanceRep.getOfficername()%></td>
			</tr>
			<tr>
				<th>Report Date</th>
				<td><%=performanceRep.getReportdate()%></td>
			</tr>
		</tbody>
	</table>
</div>