<%@page import="com.daily.average.service.model.AuditorCertificate"%>
<%@ include file="/init.jsp" %>
<%
AuditorCertificate auditorCertificate = (AuditorCertificate) request.getAttribute("auditorCertificate");
%>

<div class="mt-4">
	<table class="table table-bordered table-striped ">
		<tbody>
			<tr>
				<th>Serial No.</th>
				<td><%=auditorCertificate.getAuditid()%></td>
			</tr>
			<tr>
				<th>Name</th>
				<td><%=auditorCertificate.getName()%></td>
			</tr>
			<tr>
				<th>Amount</th>
				<td><%=auditorCertificate.getAmount()%></td>
			</tr>
			<tr>
				<th>Company Name</th>
				<td><%=auditorCertificate.getCompname()%></td>
			</tr>
			<tr>
				<th>Company Partner</th>
				<td><%=auditorCertificate.getCompartner()%></td>
			</tr>
			<tr>
				<th>Date</th>
				<td><%=auditorCertificate.getDate()%></td>
			</tr>
			<tr>
				<th>Place</th>
				<td><%=auditorCertificate.getPlace()%></td>
			</tr>
		</tbody>
	</table>
</div>