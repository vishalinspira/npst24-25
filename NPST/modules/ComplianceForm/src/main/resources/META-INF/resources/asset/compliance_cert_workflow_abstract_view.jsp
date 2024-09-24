<%@page import="compliance.service.model.Compliance_Cert"%>
<%@ include file="/init.jsp" %>

<%
Compliance_Cert compliance_Cert = (Compliance_Cert) request.getAttribute("compliance_Cert");
%>
<div class="mt-4">
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
				<th>Serial No.</th>
				<td><%=compliance_Cert.getCompid()%></td>
			</tr>
			<tr>
				<th>Compofficer Name1.</th>
				<td><%=compliance_Cert.getCompofficername1()%></td>
			</tr>
			<tr>
				<th>Compofficer Name2t</th>
				<td><%=compliance_Cert.getCompofficername2()%></td>
			</tr>
			<tr>
				<th>Certificate Number</th>
				<td><%=compliance_Cert.getCertnumber()%></td>
			</tr>
		</tbody>
	</table>
</div>