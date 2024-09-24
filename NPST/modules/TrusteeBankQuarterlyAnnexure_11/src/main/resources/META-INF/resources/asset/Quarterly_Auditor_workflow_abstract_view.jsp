<%@page import="compliance.service.model.QuarterlyAuditor"%>
<%@ include file="/init.jsp" %>

<%
QuarterlyAuditor quarterlyAuditor = (QuarterlyAuditor) request.getAttribute("quarterlyAuditor");
%>

<div class="mt-4">
	<table class="table table-bordered table-striped ">
		<tbody>
			<tr>
				<th>Serial No.</th>
				<td><%=quarterlyAuditor.getId()%></td>
			</tr>
			<tr>
				<th>Register No.</th>
				<td><%=quarterlyAuditor.getRegno()%></td>
			</tr>
			<tr>
				<th>Bank Name</th>
				<td><%=quarterlyAuditor.getBankname()%></td>
			</tr>
			<tr>
				<th>Company Name</th>
				<td><%=quarterlyAuditor.getCompanyname()%></td>
			</tr>
			<tr>
				<th>Partner Company Name</th>
				<td><%=quarterlyAuditor.getPartnercompanyname()%></td>
			</tr>
			<tr>
				<th>Place</th>
				<td><%=quarterlyAuditor.getPlace()%></td>
			</tr>
		</tbody>
	</table>
</div>